package org.jvnet.substance;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;

/**
 * UI for menu bars in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceMenuBarUI extends BasicMenuBarUI {
	/**
	 * Delegate for painting the background.
	 */
	private static SubstanceFillBackgroundDelegate backgroundDelegate = new SubstanceFillBackgroundDelegate();

	/**
	 * Boolean flag to prevent infinite loop. Maybe need to use something more
	 * elegant.
	 */
	private static boolean inEvent = false;

	/**
	 * Map of all search panels (for all non-GC'd menu bars). Is used for
	 * resetting the icons on theme change.
	 */
	private static WeakHashMap<JMenuBar, SearchPanel> panels = new WeakHashMap<JMenuBar, SearchPanel>();

	/**
	 * Panel for searching the menus.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private class SearchPanel extends JPanel {
		/**
		 * Toggle button for showing / hiding search controls.
		 */
		private JToggleButton searchButton;

		/**
		 * Text field for entering search string.
		 */
		private JTextField searchStringField;

		/**
		 * The associated menu bar.
		 */
		private JMenuBar menuBar;

		/**
		 * The result buttons.
		 */
		private Map<Integer, JButton> resultButtons;

		/**
		 * Simple constructor.
		 * 
		 * @param menuBar
		 *            The associated menu bar.
		 */
		public SearchPanel(final JMenuBar menuBar) {
			this.menuBar = menuBar;
			this.setLayout(new SearchResultsLayout(this));

			// Search button (toggle) with tooltip.
			this.searchButton = new JToggleButton(SubstanceImageCreator
					.getSearchIcon(14, SubstanceLookAndFeel.getColorScheme()));
			this.searchButton.setPreferredSize(new Dimension(16, 16));
			this.searchButton
					.setToolTipText("Select to view menu search panel");
			this.add(this.searchButton);

			// Add action listener on the toggle button. Based on the
			// state of the toggle button, the search field and result buttons
			// will be set visible or invisible.
			this.searchButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							boolean toShow = searchButton.isSelected();
							searchStringField.setVisible(toShow);
							searchStringField.requestFocus();
							for (JButton resultButton : resultButtons.values()) {
								resultButton.setVisible(toShow);
							}
							repaint();
							revalidate();
						}
					});
				}
			});
			// add mouse listener to remove the search panel on mouse
			// click when CTRL button is pressed.
			this.searchButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if ((e.getModifiers() & InputEvent.CTRL_MASK) != 0) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								removeAll();
								repaint();
								menuBar.revalidate();
							}
						});
					}
				}
			});

			// Search field.
			this.searchStringField = new JTextField();
			this.searchStringField.setColumns(10);
			this.add(this.searchStringField);
			this.searchStringField.setVisible(false);
			this.searchStringField
					.setToolTipText("Enter search string and press 'Enter' button to search");

			// Map to hold the result buttons (need for the icon reset
			// on theme change and layout manager).
			this.resultButtons = new HashMap<Integer, JButton>();
			this.searchStringField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String searchString = searchStringField.getText()
							.toLowerCase();
					// See if there is at least one non-white space character.
					// This is fix for bug 54
					if (searchString.trim().length() == 0) {
						return;
					}

					// remove all old buttons
					for (JButton resultButton : resultButtons.values()) {
						remove(resultButton);
					}
					resultButtons.clear();
					// find all matching menu items / menus
					LinkedList<SearchResult> searchResults = findOccurences(searchString);
					int count = 0;
					for (SearchResult searchResult : searchResults) {
						// show only first 16 results.
						if (count == 16)
							break;
						// create new button with binary icon
						JButton resultButton = new JButton(
								SubstanceImageCreator.getHexaMarker(count + 1,
										SubstanceLookAndFeel.getColorScheme()));
						// set action listener (to show the menu).
						resultButton
								.addActionListener(new SearchResultListener(
										searchResult));
						// check if the path to the menu (item) has
						// only enabled items.
						resultButton.setEnabled(searchResult.isEnabled());
						add(resultButton);
						resultButtons.put(count + 1, resultButton);
						resultButton.setToolTipText("<html><body><b>"
								+ searchResult.toString()
								+ "</b><br>Click to locate menu</html>");
						count++;
					}
					repaint();
					menuBar.revalidate();
				}
			});
		}

		/**
		 * Returns all occurences of the specified string in the menus and menu
		 * items of the associated menu bar.
		 * 
		 * @param searchPattern
		 *            Pattern to search (no wildcards yet).
		 * @return All occurences of the specified string in the menus and menu
		 *         items of the associated menu bar.
		 */
		private LinkedList<SearchResult> findOccurences(String searchPattern) {
			LinkedList<SearchResult> result = new LinkedList<SearchResult>();

			LinkedList<JMenu> currentPath = new LinkedList<JMenu>();

			for (Component component : this.menuBar.getComponents()) {
				if (component instanceof JMenu) {
					JMenu menu = (JMenu) component;
					this.checkMenu(currentPath, menu, searchPattern, result);
				}
			}

			return result;
		}

		/**
		 * Recursively scans the specified menu (item) and updates the list that
		 * contains all occurences of the specified string in the contained
		 * menus and menu items.
		 * 
		 * @param currentPath
		 *            The path to the current menu (item).
		 * @param menuItem
		 *            The menu (item) itself that is being tested.
		 * @param searchPattern
		 *            Pattern to search (no wildcards yet).
		 * @param matchingResults
		 *            All occurences of the specified string up until now. After
		 *            <code>this</code> function returns, will also contain
		 *            all occurences of the specified string in the contained
		 *            menu (item)s.
		 */
		private void checkMenu(LinkedList<JMenu> currentPath,
				JMenuItem menuItem, String searchPattern,
				LinkedList<SearchResult> matchingResults) {
			String menuItemText = menuItem.getText();
			if (menuItemText.toLowerCase().contains(searchPattern)) {
				matchingResults.addLast(new SearchResult(this.menuBar,
						currentPath, menuItem));
			}
			if (menuItem instanceof JMenu) {
				JMenu menu = (JMenu) menuItem;
				currentPath.addLast(menu);
				for (Component menuComponent : menu.getMenuComponents()) {
					if (menuComponent instanceof JMenuItem) {
						JMenuItem childItem = (JMenuItem) menuComponent;
						checkMenu(currentPath, childItem, searchPattern,
								matchingResults);
					}
				}
				currentPath.removeLast();
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.Component#setVisible(boolean)
		 */
		@Override
		public void setVisible(boolean aFlag) {
			super.setVisible(aFlag);
			if (aFlag)
				this.searchStringField.requestFocus();
		}
	}

	/**
	 * Listener on the <code>search result</code> button. The action itself -
	 * show the associated menu path to the menu item that contains the string
	 * that has been specified during the search.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static class SearchResultListener implements ActionListener {
		/**
		 * The associated search result.
		 */
		private SearchResult searchResult;

		/**
		 * Simple constructor.
		 * 
		 * @param searchResult
		 *            The associated search result.
		 */
		public SearchResultListener(SearchResult searchResult) {
			super();
			this.searchResult = searchResult;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			// start opening the menus
			MenuElement[] menuElements = this.searchResult.menuElements;
			MenuSelectionManager.defaultManager().setSelectedPath(menuElements);
		}
	}

	/**
	 * Single result of menu search.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static class SearchResult {
		/**
		 * Path to the menu (item). The first element is always {@link JMenuBar},
		 * and after each {@link JMenu} there is it's
		 * {@link JMenu#getPopupMenu()}.
		 */
		private MenuElement[] menuElements;

		/**
		 * Simple constructor.
		 * 
		 * @param menuBar
		 *            The main menu bar.
		 * @param menuPath
		 *            The menus leading to the matching entry.
		 * @param menuLeaf
		 *            The menu (item) that matches the search pattern string.
		 */
		public SearchResult(JMenuBar menuBar, LinkedList<JMenu> menuPath,
				JMenuItem menuLeaf) {
			int count = 1;
			if (menuPath != null)
				count += 2 * menuPath.size();
			if (menuLeaf != null)
				count++;
			this.menuElements = new MenuElement[count];
			count = 0;

			// the first element is the menu bar itself
			this.menuElements[count++] = menuBar;
			if (menuPath != null) {
				for (JMenu menu : menuPath) {
					this.menuElements[count++] = menu;
					// important - don't forget the popup menu of the menu
					this.menuElements[count++] = menu.getPopupMenu();
				}
			}
			if (menuLeaf != null)
				this.menuElements[count] = menuLeaf;
		}

		/**
		 * Returns the path to the menu (item).
		 * 
		 * @return Path to the menu (item). The first element is always
		 *         {@link JMenuBar}, and after each {@link JMenu} there is it's
		 *         {@link JMenu#getPopupMenu()}.
		 */
		public MenuElement[] getMenuElements() {
			return this.menuElements;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			StringBuffer sb = new StringBuffer();
			if (this.menuElements != null) {
				String sep = "";
				for (MenuElement menu : menuElements) {
					if (menu instanceof JMenuItem) {
						sb.append(sep);
						sep = " -> ";
						sb.append(((JMenuItem) menu).getText());
					}
				}
			}
			return sb.toString();
		}

		/**
		 * Checks that all entries leading to the associated menu (item) are
		 * enabled.
		 * 
		 * @return <code>true</code> if all entries leading to the associated
		 *         menu (item) are enabled, <code>false</code> otherwise.
		 */
		public boolean isEnabled() {
			// all parts must be enabled
			for (MenuElement element : this.menuElements) {
				if (element instanceof JMenuItem) {
					JMenuItem menuItem = (JMenuItem) element;
					if (!menuItem.isEnabled())
						return false;
				}
			}
			return true;
		}
	}

	private static int getMenuItemCount(JMenuItem menuItem) {
		int result = 1;

		if (menuItem instanceof JMenu) {
			JMenu menu = (JMenu) menuItem;
			for (Component child : menu.getMenuComponents()) {
				if (child instanceof JMenuItem)
					result += getMenuItemCount((JMenuItem) child);
			}
		}

		return result;
	}

	private static int getMenuItemCount(JMenuBar menuBar) {
		int result = 0;

		for (int i = 0; i < menuBar.getMenuCount(); i++) {
			JMenu menu = menuBar.getMenu(i);
			if (menu != null) {
				result += getMenuItemCount(menu);
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		if (MemoryAnalyzer.isRunning()) {
			MemoryAnalyzer.enqueueUsage("SubstanceMenuBarUI to be created");
		}
		SubstanceMenuBarUI result = new SubstanceMenuBarUI();
		final JMenuBar jmb = (JMenuBar) c;
		boolean toAddListener = false;
		if (jmb instanceof SubstanceTitlePane.SubstanceMenuBar) {
			// ignore request - fix for bug 53. No search
			// functionality for the activation button of title bar.
		} else {
//			try {
				if (!panels.containsKey(jmb)) {
					SearchPanel searchPanel = result.new SearchPanel(jmb);
					jmb.add(searchPanel, jmb.getComponentCount() - 1);
					panels.put(jmb, searchPanel);
					toAddListener = true;
				}
//			} catch (Exception exc) {
//				exc.printStackTrace();
//			}
		}

		if (toAddListener) {
			// need to add a container listener that will move a newly added
			// JMenu one entry before the last (so that our search panel
			// will always be the last).
			jmb.addContainerListener(new ContainerAdapter() {
				public void componentAdded(ContainerEvent e) {
					if (!(e.getChild() instanceof JMenu))
						return;
					if (!inEvent) {
						boolean isTracingOn = MemoryAnalyzer.isRunning();
						inEvent = true;
						Component removed = null;
						for (int i = 0; i < jmb.getComponentCount(); i++) {
							if (jmb.getComponent(i) instanceof SearchPanel) {
								if (isTracingOn)
									MemoryAnalyzer.enqueueUsage("At " + i
											+ " have search panel");
								removed = jmb.getComponent(i);
								break;
							}
						}
						if (removed != null) {
							jmb.remove(removed);
							if (isTracingOn)
								MemoryAnalyzer.enqueueUsage("Adding at "
										+ (jmb.getComponentCount())
										+ " out of " + jmb.getComponentCount());
							jmb.add(removed, jmb.getComponentCount());
							// Show search panel only if have more than 40 menu
							// items in all menus.
							if (getMenuItemCount(jmb) > 40)
								removed.setVisible(true);
							else
								removed.setVisible(false);
						}
						inEvent = false;
					}
				}
			});
		}
		return new SubstanceMenuBarUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#update(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	@Override
	public void update(Graphics g, JComponent c) {
		boolean isOpaque = c.isOpaque();
		if (isOpaque) {
			backgroundDelegate.update(g, c);
		} else {
			super.update(g, c);
		}
	}

	/**
	 * Resets icons of all search panels according to the currently set theme.
	 */
	public static void reset() {
		for (SearchPanel searchPanel : panels.values()) {
			for (Map.Entry<Integer, JButton> entry : searchPanel.resultButtons
					.entrySet()) {
				int index = entry.getKey();
				JButton button = entry.getValue();

				button.setIcon(SubstanceImageCreator.getHexaMarker(index,
						SubstanceLookAndFeel.getColorScheme()));
			}
			searchPanel.searchButton.setIcon(SubstanceImageCreator
					.getSearchIcon(14, SubstanceLookAndFeel.getColorScheme()));
		}
	}

	/**
	 * Layout for the search panel. Note that {@link FlowLayout} is almost
	 * perfect for us, but we need the following:
	 * <ul>
	 * <li>Minimum size to be 16*16 (for the search icon)
	 * <li>When there isn't enough place for result buttons, they should
	 * continue (even if they are unseen) and not flow to the next line.
	 * </ul>
	 * 
	 * @author Kirill Grouchnikov
	 */
	private class SearchResultsLayout implements LayoutManager {
		/**
		 * The associated search panel.
		 */
		private SearchPanel searchPanel;

		/**
		 * Simple constructor.
		 * 
		 * @param searchPanel
		 *            The associated search panel.
		 */
		public SearchResultsLayout(SearchPanel searchPanel) {
			this.searchPanel = searchPanel;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
		 *      java.awt.Component)
		 */
		public void addLayoutComponent(String name, Component c) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
		 */
		public void removeLayoutComponent(Component c) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
		 */
		public Dimension preferredLayoutSize(Container c) {
			if (this.searchPanel.searchButton.isSelected())
				return c.getSize();
			else
				return new Dimension(16, 16);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
		 */
		public Dimension minimumLayoutSize(Container c) {
			// enough for the search icon
			return new Dimension(16, 16);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
		 */
		public void layoutContainer(Container c) {
			int height = c.getHeight();

			if (!this.searchPanel.searchButton.isVisible())
				return;
			// start from the toggle button
			int x = 0;
			int sbWidth = this.searchPanel.searchButton.getPreferredSize().width;
			int sbHeight = this.searchPanel.searchButton.getPreferredSize().height;
			this.searchPanel.searchButton.setBounds(x, (height - sbHeight) / 2,
					sbWidth, sbHeight);

			x += (sbWidth + 4);

			if (this.searchPanel.isVisible()) {
				// now - text field
				int tbWidth = this.searchPanel.searchStringField
						.getPreferredSize().width;
				int tbHeight = this.searchPanel.searchStringField
						.getPreferredSize().height;
				this.searchPanel.searchStringField.setBounds(x,
						(height - tbHeight) / 2, tbWidth, tbHeight);

				x += (tbWidth + 2);

				// result buttons
				int buttonCount = this.searchPanel.resultButtons.size();
				for (int i = 1; i <= buttonCount; i++) {
					JButton button = this.searchPanel.resultButtons.get(i);
					int bw = button.getPreferredSize().width;
					int bh = button.getPreferredSize().height;

					button.setBounds(x, (height - bh) / 2, bw, bh);
					x += (bw + 1);
				}
			}
		}
	}
}
