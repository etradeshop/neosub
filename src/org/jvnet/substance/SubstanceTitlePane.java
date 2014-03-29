package org.jvnet.substance;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;

import javax.swing.*;
import javax.swing.plaf.UIResource;

import org.jvnet.substance.color.ColorSchemeEnum;

/**
 * Title pane for <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceTitlePane extends JComponent {
	private static final int IMAGE_HEIGHT = 16;

	private static final int IMAGE_WIDTH = 16;

	/**
	 * PropertyChangeListener added to the JRootPane.
	 */
	private PropertyChangeListener propertyChangeListener;

	/**
	 * JMenuBar, typically renders the system menu items.
	 */
	private JMenuBar menuBar;

	/**
	 * Action used to close the Window.
	 */
	private Action closeAction;

	/**
	 * Action used to iconify the Frame.
	 */
	private Action iconifyAction;

	/**
	 * Action to restore the Frame size.
	 */
	private Action restoreAction;

	/**
	 * Action to restore the Frame size.
	 */
	private Action maximizeAction;

	/**
	 * Button used to maximize or restore the frame.
	 */
	private JButton toggleButton;

	/**
	 * Button used to minimize the frame
	 */
	private JButton minimizeButton;

	/**
	 * Button used to close the frame.
	 */
	private JButton closeButton;

	/**
	 * Listens for changes in the state of the Window listener to update the
	 * state of the widgets.
	 */
	private WindowListener windowListener;

	/**
	 * Window we're currently in.
	 */
	private Window window;

	/**
	 * JRootPane rendering for.
	 */
	private JRootPane rootPane;

	/**
	 * Buffered Frame.state property. As state isn't bound, this is kept to
	 * determine when to avoid updating widgets.
	 */
	private int state;

	/**
	 * SubstanceRootPaneUI that created us.
	 */
	private SubstanceRootPaneUI rootPaneUI;

	/**
	 * Simple constructor.
	 * 
	 * @param root
	 *            Root pane.
	 * @param ui
	 *            Root pane UI.
	 */
	public SubstanceTitlePane(JRootPane root, SubstanceRootPaneUI ui) {
		this.rootPane = root;
		this.rootPaneUI = ui;

		state = -1;

		installSubcomponents();
		installDefaults();

		setLayout(createLayout());

		this.setToolTipText(getTitle());
	}

	/**
	 * Uninstalls the necessary state.
	 */
	private void uninstall() {
		uninstallListeners();
		window = null;
		removeAll();
	}

	/**
	 * Installs the necessary listeners.
	 */
	private void installListeners() {
		if (this.window != null) {
			this.windowListener = new WindowHandler();
			this.window.addWindowListener(this.windowListener);
			this.propertyChangeListener = new PropertyChangeHandler();
			this.window.addPropertyChangeListener(this.propertyChangeListener);
		}
	}

	/**
	 * Uninstalls the necessary listeners.
	 */
	private void uninstallListeners() {
		if (this.window != null) {
			this.window.removeWindowListener(this.windowListener);
			this.window
					.removePropertyChangeListener(this.propertyChangeListener);
		}
	}

	/**
	 * Returns the <code>JRootPane</code> this was created for.
	 */
	public JRootPane getRootPane() {
		return rootPane;
	}

	/**
	 * Returns the decoration style of the <code>JRootPane</code>.
	 */
	private int getWindowDecorationStyle() {
		return getRootPane().getWindowDecorationStyle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#addNotify()
	 */
	public void addNotify() {
		super.addNotify();

		uninstallListeners();

		window = SwingUtilities.getWindowAncestor(this);
		if (window != null) {
			if (window instanceof Frame) {
				setState(((Frame) window).getExtendedState());
			} else {
				setState(0);
			}
			setActive(window.isActive());
			installListeners();
		}
		this.setToolTipText(getTitle());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#removeNotify()
	 */
	public void removeNotify() {
		super.removeNotify();

		uninstallListeners();
		window = null;
	}

	/**
	 * Adds any sub-Components contained in the <code>SubstanceTitlePane</code>.
	 */
	private void installSubcomponents() {
		int decorationStyle = getWindowDecorationStyle();
		if (decorationStyle == JRootPane.FRAME) {
			createActions();
			menuBar = createMenuBar();
			add(menuBar);
			createButtons();
			add(minimizeButton);
			add(toggleButton);
			add(closeButton);
		} else {
			if (decorationStyle == JRootPane.PLAIN_DIALOG
					|| decorationStyle == JRootPane.INFORMATION_DIALOG
					|| decorationStyle == JRootPane.ERROR_DIALOG
					|| decorationStyle == JRootPane.COLOR_CHOOSER_DIALOG
					|| decorationStyle == JRootPane.FILE_CHOOSER_DIALOG
					|| decorationStyle == JRootPane.QUESTION_DIALOG
					|| decorationStyle == JRootPane.WARNING_DIALOG) {
				createActions();
				createButtons();
				add(closeButton);
			}
		}
	}

	/**
	 * Installs the fonts and necessary properties on the MetalTitlePane.
	 */
	private void installDefaults() {
		setFont(UIManager.getFont("InternalFrame.titleFont", getLocale()));
	}

	/**
	 * Returns the <code>JMenuBar</code> displaying the appropriate system
	 * menu items.
	 */
	protected JMenuBar createMenuBar() {
		menuBar = new SubstanceMenuBar();
		menuBar.setFocusable(true);
		menuBar.setBorderPainted(true);
		menuBar.add(createMenu());
		return menuBar;
	}

	/**
	 * Create the <code>Action</code>s that get associated with the buttons
	 * and menu items.
	 */
	private void createActions() {
		closeAction = new CloseAction();
		if (getWindowDecorationStyle() == JRootPane.FRAME) {
			iconifyAction = new IconifyAction();
			restoreAction = new RestoreAction();
			maximizeAction = new MaximizeAction();
		}
	}

	/**
	 * Returns the <code>JMenu</code> displaying the appropriate menu items
	 * for manipulating the Frame.
	 */
	private JMenu createMenu() {
		JMenu menu = new JMenu("");
		menu.setOpaque(false);
		menu.setBackground(null);
		if (getWindowDecorationStyle() == JRootPane.FRAME) {
			addMenuItems(menu);
		}
		return menu;
	}

	/**
	 * Adds the necessary <code>JMenuItem</code>s to the passed in menu.
	 */
	private void addMenuItems(JMenu menu) {
		Locale locale = getRootPane().getLocale();
		JMenuItem mi = menu.add(restoreAction);

		mi = menu.add(iconifyAction);

		if (Toolkit.getDefaultToolkit().isFrameStateSupported(
				Frame.MAXIMIZED_BOTH)) {
			mi = menu.add(maximizeAction);
		}

		menu.add(new JSeparator());

		mi = menu.add(closeAction);
	}

	/**
	 * Returns a <code>JButton</code> appropriate for placement on the
	 * TitlePane.
	 */
	private JButton createTitleButton() {
		JButton button = new JButton();

		button.setFocusPainted(false);
		button.setFocusable(false);
		button.setOpaque(true);
		return button;
	}

	/**
	 * Creates the Buttons that will be placed on the TitlePane.
	 */
	private void createButtons() {
		closeButton = createTitleButton();
		closeButton.setAction(closeAction);
		closeButton.setText(null);
		closeButton.putClientProperty("paintActive", Boolean.TRUE);
		closeButton.setBorder(null);
		closeButton.setToolTipText("Close");
		closeButton.setIcon(SubstanceImageCreator
				.getCloseIcon(SubstanceLookAndFeel.getColorScheme()));

		SubstanceBackgroundDelegate.trackTitleButton(this.closeButton,
				SubstanceButtonUI.ButtonTitleKind.CLOSE);

		if (getWindowDecorationStyle() == JRootPane.FRAME) {
			Icon maximizeIcon = SubstanceIconFactory.getTitlePaneIcon(
					SubstanceIconFactory.IconKind.MAXIMIZE,
					SubstanceLookAndFeel.getColorScheme());
			;

			// SubstanceImageCreator.getMaximizeIcon(SubstanceLookAndFeel
			// .getColorScheme());
			Icon restoreSizeIcon = SubstanceIconFactory.getTitlePaneIcon(
					SubstanceIconFactory.IconKind.RESTORE, SubstanceLookAndFeel
							.getColorScheme());
			;

			// SubstanceImageCreator
			// .getRestoreIcon(SubstanceLookAndFeel.getColorScheme());

			minimizeButton = createTitleButton();
			minimizeButton.setAction(iconifyAction);
			minimizeButton.setText(null);
			minimizeButton.putClientProperty("paintActive", Boolean.TRUE);
			minimizeButton.setBorder(null);
			minimizeButton.setToolTipText("Minimize");
			minimizeButton.setIcon(SubstanceIconFactory.getTitlePaneIcon(
					SubstanceIconFactory.IconKind.MINIMIZE,
					SubstanceLookAndFeel.getColorScheme()));

			SubstanceBackgroundDelegate.trackTitleButton(this.minimizeButton,
					SubstanceButtonUI.ButtonTitleKind.REGULAR);

			toggleButton = createTitleButton();
			toggleButton.setAction(restoreAction);
			toggleButton.putClientProperty("paintActive", Boolean.TRUE);
			toggleButton.setBorder(null);
			toggleButton.setToolTipText("Maximize");
			toggleButton.setIcon(maximizeIcon);

			SubstanceBackgroundDelegate.trackTitleButton(this.toggleButton,
					SubstanceButtonUI.ButtonTitleKind.REGULAR);

		}
	}

	/**
	 * Returns the <code>LayoutManager</code> that should be installed on the
	 * <code>SubstanceTitlePane</code>.
	 */
	private LayoutManager createLayout() {
		return new TitlePaneLayout();
	}

	/**
	 * Updates state dependant upon the Window's active state.
	 */
	private void setActive(boolean isActive) {
		Boolean activeB = isActive ? Boolean.TRUE : Boolean.FALSE;

		if (getWindowDecorationStyle() == JRootPane.FRAME) {
			closeButton.putClientProperty("paintActive", activeB);
			minimizeButton.putClientProperty("paintActive", activeB);
			toggleButton.putClientProperty("paintActive", activeB);
		}
		getRootPane().repaint();
	}

	/**
	 * Sets the state of the Window.
	 */
	private void setState(int state) {
		setState(state, false);
	}

	/**
	 * Sets the state of the window. If <code>updateRegardless</code> is true
	 * and the state has not changed, this will update anyway.
	 */
	private void setState(int state, boolean updateRegardless) {
		Window w = getWindow();

		if (w != null && getWindowDecorationStyle() == JRootPane.FRAME) {
			if (this.state == state && !updateRegardless) {
				return;
			}
			Frame frame = getFrame();

			if (frame != null) {
				JRootPane rootPane = getRootPane();

				if (((state & Frame.MAXIMIZED_BOTH) != 0)
						&& (rootPane.getBorder() == null || (rootPane
								.getBorder() instanceof UIResource))
						&& frame.isShowing()) {
					rootPane.setBorder(null);
				} else {
					if ((state & Frame.MAXIMIZED_BOTH) == 0) {
						// This is a croak, if state becomes bound, this can
						// be nuked.
						rootPaneUI.installBorder(rootPane);
					}
				}
				if (frame.isResizable()) {
					if ((state & Frame.MAXIMIZED_BOTH) != 0) {
						updateToggleButton(restoreAction, SubstanceIconFactory
								.getTitlePaneIcon(
										SubstanceIconFactory.IconKind.RESTORE,
										SubstanceLookAndFeel.getColorScheme()));
						toggleButton.setToolTipText("Restore");
						maximizeAction.setEnabled(false);
						restoreAction.setEnabled(true);
					} else {
						updateToggleButton(maximizeAction, SubstanceIconFactory
								.getTitlePaneIcon(
										SubstanceIconFactory.IconKind.MAXIMIZE,
										SubstanceLookAndFeel.getColorScheme()));
						toggleButton.setToolTipText("Maximize");
						maximizeAction.setEnabled(true);
						restoreAction.setEnabled(false);
					}
					if (toggleButton.getParent() == null
							|| minimizeButton.getParent() == null) {
						add(toggleButton);
						add(minimizeButton);
						revalidate();
						repaint();
					}
					toggleButton.setText(null);
				} else {
					maximizeAction.setEnabled(false);
					restoreAction.setEnabled(false);
					if (toggleButton.getParent() != null) {
						remove(toggleButton);
						revalidate();
						repaint();
					}
				}
			} else {
				// Not contained in a Frame
				maximizeAction.setEnabled(false);
				restoreAction.setEnabled(false);
				iconifyAction.setEnabled(false);
				remove(toggleButton);
				remove(minimizeButton);
				revalidate();
				repaint();
			}
			closeAction.setEnabled(true);
			this.state = state;
		}
	}

	/**
	 * Updates the toggle button to contain the Icon <code>icon</code>, and
	 * Action <code>action</code>.
	 */
	private void updateToggleButton(Action action, Icon icon) {
		toggleButton.setAction(action);
		toggleButton.setIcon(icon);
		toggleButton.setText(null);
	}

	/**
	 * Returns the Frame rendering in. This will return null if the
	 * <code>JRootPane</code> is not contained in a <code>Frame</code>.
	 */
	private Frame getFrame() {
		Window window = getWindow();

		if (window instanceof Frame) {
			return (Frame) window;
		}
		return null;
	}

	/**
	 * Returns the <code>Window</code> the <code>JRootPane</code> is
	 * contained in. This will return null if there is no parent ancestor of the
	 * <code>JRootPane</code>.
	 */
	private Window getWindow() {
		return window;
	}

	/**
	 * Returns the String to display as the title.
	 */
	private String getTitle() {
		Window w = getWindow();

		if (w instanceof Frame) {
			return ((Frame) w).getTitle();
		} else {
			if (w instanceof Dialog) {
				return ((Dialog) w).getTitle();
			}
		}
		return null;
	}

	/**
	 * Renders the TitlePane.
	 */
	public void paintComponent(Graphics g) {
		// As state isn't bound, we need a convenience place to check
		// if it has changed. Changing the state typically changes the
		if (getFrame() != null) {
			setState(getFrame().getExtendedState());
		}
		JRootPane rootPane = getRootPane();
		Window window = getWindow();
		boolean isSelected = (window == null) ? true : window.isActive();
		int width = getWidth();
		int height = getHeight();

		ColorSchemeEnum colorSchemeEnum = isSelected ? SubstanceLookAndFeel
				.getColorScheme() : SubstanceLookAndFeel.getColorScheme()
				.getMetallic();

		g.drawImage(SubstanceImageCreator.getRectangularBackground(width,
				height + 1, colorSchemeEnum, false), 0, 0, null);

		// offset of border
		int xOffset = 5;

		String theTitle = getTitle();
		int leftTransitionStart = (menuBar == null) ? 0
				: menuBar.getWidth() + 10;
		int leftTransitionEnd = (menuBar == null) ? 0
				: (leftTransitionStart + 20);
		xOffset += leftTransitionEnd;

		int rightTransitionStart = (int) (0.7 * width);
		int rightTransitionEnd = Math.min(rightTransitionStart + 20,
				(int) (0.8 * width));

		// find the leftmost button for the right transition band
		JButton leftmostButton = null;
		Frame frame = getFrame();

		if (minimizeButton != null && (minimizeButton.getParent() != null)
				&& (minimizeButton.getBounds().width != 0)) {
			leftmostButton = minimizeButton;
		} else {
			if (toggleButton != null && (toggleButton.getParent() != null)
					&& (toggleButton.getBounds().width != 0)) {
				leftmostButton = toggleButton;
			} else {
				if (closeButton != null && closeButton.getParent() != null) {
					leftmostButton = closeButton;
				}
			}
		}

		if (leftmostButton != null) {
			Rectangle rect = leftmostButton.getBounds();
			rightTransitionEnd = rect.getBounds().x - 5;
			rightTransitionStart = Math.max((int) 0.7 * width,
					rightTransitionEnd - 20);
		}

		if (theTitle != null) {
			FontMetrics fm = rootPane.getFontMetrics(g.getFont());
			int titleWidth = rightTransitionStart - leftTransitionEnd - 30;
			theTitle = Utilities.clipString(fm, titleWidth, theTitle);
		}

		// draw the background image with cubified transition areas
		// g.drawImage(SubstanceImageCreator.getGradientCubesImage(rootPane,
		// width, height, colorSchemeEnum, leftTransitionStart,
		// leftTransitionEnd, rightTransitionStart, rightTransitionEnd),
		// 0, 0, null);

		// draw the title (if needed)
		if (theTitle != null) {
			Graphics2D graphics = (Graphics2D) g;
			FontMetrics fm = rootPane.getFontMetrics(g.getFont());
			int yOffset = ((height - fm.getHeight()) / 2) + fm.getAscent();

			Object oldAAValue = graphics
					.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			if (colorSchemeEnum.isDark()) {
				graphics.setColor(colorSchemeEnum.getColorScheme()
						.getUltraDarkColor());
			} else {
				graphics.setColor(colorSchemeEnum.getColorScheme()
						.getUltraLightColor());
			}
			graphics.drawString(theTitle, xOffset + 1, yOffset + 1);
			if (colorSchemeEnum.isDark()) {
				graphics.setColor(colorSchemeEnum.getColorScheme()
						.getForegroundColor());
			} else {
				graphics.setColor(colorSchemeEnum.getColorScheme()
						.getUltraDarkColor());
			}
			graphics.drawString(theTitle, xOffset, yOffset);
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					oldAAValue);
		}
	}

	/**
	 * Actions used to <code>close</code> the <code>Window</code>.
	 */
	private class CloseAction extends AbstractAction {
		public CloseAction() {
			super(UIManager.getString("MetalTitlePane.closeTitle", getLocale()));
		}

		public void actionPerformed(ActionEvent e) {
			Window window = getWindow();

			if (window != null) {
				window.dispatchEvent(new WindowEvent(window,
						WindowEvent.WINDOW_CLOSING));
			}
		}
	}

	/**
	 * Actions used to <code>iconfiy</code> the <code>Frame</code>.
	 */
	private class IconifyAction extends AbstractAction {
		public IconifyAction() {
			super(UIManager.getString("MetalTitlePane.iconifyTitle",
					getLocale()));
		}

		public void actionPerformed(ActionEvent e) {
			Frame frame = getFrame();
			if (frame != null) {
				frame.setExtendedState(state | Frame.ICONIFIED);
			}
		}
	}

	/**
	 * Actions used to <code>restore</code> the <code>Frame</code>.
	 */
	private class RestoreAction extends AbstractAction {
		public RestoreAction() {
			super(UIManager.getString("MetalTitlePane.restoreTitle",
					getLocale()));
		}

		public void actionPerformed(ActionEvent e) {
			Frame frame = getFrame();

			if (frame == null) {
				return;
			}

			if ((state & Frame.ICONIFIED) != 0) {
				frame.setExtendedState(state & ~Frame.ICONIFIED);
			} else {
				frame.setExtendedState(state & ~Frame.MAXIMIZED_BOTH);
			}
		}
	}

	/**
	 * Actions used to <code>restore</code> the <code>Frame</code>.
	 */
	private class MaximizeAction extends AbstractAction {
		public MaximizeAction() {
			super(UIManager.getString("MetalTitlePane.maximizeTitle",
					getLocale()));
		}

		public void actionPerformed(ActionEvent e) {
			Frame frame = getFrame();
			if (frame != null) {
				frame.setExtendedState(state | Frame.MAXIMIZED_BOTH);
			}
		}
	}

	/**
	 * Class responsible for drawing the system menu. Looks up the image to draw
	 * from the Frame associated with the <code>JRootPane</code>.
	 */
	class SubstanceMenuBar extends JMenuBar {
		private JButton activatorButton;

		private Rectangle activatorRectangle;

		public void paint(Graphics g) {
			Frame frame = getFrame();

			Image image = (frame != null) ? frame.getIconImage() : null;

			if (image != null) {
				double coef = Math.max((double) IMAGE_WIDTH
						/ (double) image.getWidth(null), (double) IMAGE_HEIGHT
						/ (double) image.getHeight(null));
				if (coef < 1.0)
					g.drawImage(image, 0, 0,
							(int) (coef * image.getWidth(null)),
							(int) (coef * image.getHeight(null)), null);
				else
					g.drawImage(image, 0, 0, null);
			} else {
				Icon icon = UIManager.getIcon("InternalFrame.icon");

				if (icon != null) {
					icon.paintIcon(this, g, 0, 0);
				}
			}
		}

		public Dimension getMinimumSize() {
			return getPreferredSize();
		}

		public Dimension getPreferredSize() {
			Dimension size = super.getPreferredSize();

			return new Dimension(Math.max(IMAGE_WIDTH, size.width), Math.max(
					size.height, IMAGE_HEIGHT));
		}
		// public SubstanceMenuBar() {
		// // Replace the icon, emulating it with a JButton. This
		// // button will provide a consistent LNF.
		// // this.activatorButton = new JButton();
		// // this.activatorButton.setFocusPainted(false);
		// // this.activatorButton.setFocusable(true);
		// // this.activatorButton.setOpaque(true);
		// // this.activatorButton.setIcon(SubstanceComboBoxUI.COMBO_ARROWS
		// // .get(SubstanceLookAndFeel.getColorScheme()));
		// // this.activatorButton.setPreferredSize(new Dimension(
		// // SubstanceImageCreator.ICON_DIMENSION + 1,
		// // SubstanceImageCreator.ICON_DIMENSION));
		// // this.activatorButton.setSize(this.activatorButton
		// // .getPreferredSize());
		// // this.activatorRectangle = new Rectangle(this.activatorButton
		// // .getSize());
		// // this.add(this.activatorButton);
		// }
		//
		// // public void paint(Graphics g) {
		// // // ask the button UI to draw the button
		// // this.activatorButton.getUI().update(g, this.activatorButton);
		// // }
		//
		// public Dimension getMinimumSize() {
		// return getPreferredSize();
		// }
		//
		// public Dimension getPreferredSize() {
		// Dimension size = super.getPreferredSize();
		//
		// return new Dimension(Math.max(this.activatorButton.getWidth(),
		// size.width), Math.max(size.height, this.activatorButton
		// .getHeight()));
		// }
	}

	private class TitlePaneLayout implements LayoutManager {
		public void addLayoutComponent(String name, Component c) {
		}

		public void removeLayoutComponent(Component c) {
		}

		public Dimension preferredLayoutSize(Container c) {
			int height = computeHeight();
			return new Dimension(height, height);
		}

		private int computeHeight() {
			FontMetrics fm = rootPane.getFontMetrics(getFont());
			int fontHeight = fm.getHeight();
			fontHeight += 7;
			int iconHeight = 0;
			if (getWindowDecorationStyle() == JRootPane.FRAME) {
				iconHeight = SubstanceImageCreator.ICON_DIMENSION;
			}

			int finalHeight = Math.max(fontHeight, iconHeight);
			return finalHeight;
		}

		public Dimension minimumLayoutSize(Container c) {
			return preferredLayoutSize(c);
		}

		public void layoutContainer(Container c) {
			int w = getWidth();
			int x;
			int y = 3;
			int spacing;
			int buttonHeight;
			int buttonWidth;

			if (closeButton != null && closeButton.getIcon() != null) {
				buttonHeight = closeButton.getIcon().getIconHeight();
				buttonWidth = closeButton.getIcon().getIconWidth();
			} else {
				buttonHeight = SubstanceImageCreator.ICON_DIMENSION;
				buttonWidth = SubstanceImageCreator.ICON_DIMENSION;
			}

			// assumes all buttons have the same dimensions
			// these dimensions include the borders
			spacing = 5;
			x = spacing;
			if (menuBar != null) {
				menuBar.setBounds(x, y, buttonWidth + 1, buttonHeight);
			}

			x = w;
			spacing = 4;
			x -= (spacing + buttonWidth);
			if (closeButton != null) {
				closeButton.setBounds(x, y, buttonWidth, buttonHeight);
			}

			if (getWindowDecorationStyle() == JRootPane.FRAME) {
				if (Toolkit.getDefaultToolkit().isFrameStateSupported(
						Frame.MAXIMIZED_BOTH)) {
					if (toggleButton.getParent() != null) {
						spacing = 10;
						x -= (spacing + buttonWidth);
						toggleButton.setBounds(x, y, buttonWidth, buttonHeight);
					}
				}

				if (minimizeButton != null
						&& minimizeButton.getParent() != null) {
					spacing = 2;
					x -= (spacing + buttonWidth);
					minimizeButton.setBounds(x, y, buttonWidth, buttonHeight);
				}
			}
		}
	}

	/**
	 * PropertyChangeListener installed on the Window. Updates the necessary
	 * state as the state of the Window changes.
	 */
	private class PropertyChangeHandler implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent pce) {
			String name = pce.getPropertyName();

			// Frame.state isn't currently bound.
			if ("resizable".equals(name) || "state".equals(name)) {
				Frame frame = getFrame();

				if (frame != null) {
					setState(frame.getExtendedState(), true);
				}
				if ("resizable".equals(name)) {
					getRootPane().repaint();
				}
			} else {
				if ("title".equals(name)) {
					repaint();
				} else {
					if ("componentOrientation".equals(name)
							|| "iconImage".equals(name)) {
						revalidate();
						repaint();
					}
				}
			}
		}
	}

	/**
	 * WindowListener installed on the Window, updates the state as necessary.
	 */
	private class WindowHandler extends WindowAdapter {
		public void windowActivated(WindowEvent ev) {
			setActive(true);
		}

		public void windowDeactivated(WindowEvent ev) {
			setActive(false);
		}
	}
}
