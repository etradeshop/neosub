package org.jvnet.substance;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicDesktopIconUI;

import org.jvnet.substance.SubstanceInternalFrameTitlePane.ClickListener;
import org.jvnet.substance.color.ColorSchemeEnum;

/**
 * UI for desktop icons in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceDesktopIconUI extends BasicDesktopIconUI {
	/**
	 * Button for restoring the internal frame.
	 */
	private JButton restoreButton;

	/**
	 * Button for maximizing the internal frame.
	 */
	private JButton maximizeButton;

	/**
	 * Button for closing the internal frame.
	 */
	private JButton closeButton;

	/**
	 * Label with icon
	 */
	private JLabel iconLabel;

	/**
	 * Label with icon
	 */
	private JLabel titleLabel;

	/**
	 * Listener for title changes.
	 */
	private TitleListener titleListener;

	/**
	 * Listener on the title label (for the dragging purposes).
	 */
	private MouseInputListener labelMouseInputListener;

	/**
	 * Width of minimized component (desktop icon).
	 */
	private int width;

	/**
	 * Preview window (activated on hover).
	 */
	private JWindow previewWindow;

	/**
	 * Indicates whether the corresponding desktop icon is dragged.
	 */
	private boolean isInDrag;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new SubstanceDesktopIconUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicDesktopIconUI#installDefaults()
	 */
	@Override
	protected void installDefaults() {
		super.installDefaults();
		Font f = desktopIcon.getFont();
		if (f == null || f instanceof UIResource) {
			desktopIcon.setFont(UIManager.getFont("DesktopIcon.font"));
		}
		this.width = UIManager.getInt("DesktopIcon.width");
		this.desktopIcon.setBackground(SubstanceLookAndFeel.getColorScheme()
				.getColorScheme().getExtraLightColor());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicDesktopIconUI#installComponents()
	 */
	@Override
	protected void installComponents() {
		this.frame = desktopIcon.getInternalFrame();
		this.frame.setOpaque(false);
		Icon icon = this.frame.getFrameIcon();

		this.previewWindow = new JWindow();
		this.previewWindow.getContentPane().setLayout(new BorderLayout());

		this.titleLabel = new JLabel(this.frame.getTitle());
		desktopIcon.add(this.titleLabel);
		this.titleLabel.setToolTipText(this.frame.getTitle());
		this.titleLabel.setFont(desktopIcon.getFont());
		this.isInDrag = false;
		this.titleLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (isInDrag)
					return;
				final BufferedImage previewImage = SubstanceInternalFrameTitlePane
						.getSnapshot(frame);
				if (previewImage != null) {
					previewWindow.getContentPane().removeAll();
					previewWindow.getContentPane().add(
							new JLabel(new ImageIcon(previewImage)),
							BorderLayout.CENTER);
					previewWindow.setSize(previewImage.getWidth(), previewImage
							.getHeight());
					syncPreviewWindow(true);
					previewWindow.setVisible(true);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				isInDrag = false;
				previewWindow.setVisible(false);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				previewWindow.setVisible(false);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				isInDrag = false;
				syncPreviewWindow(true);
				previewWindow.setVisible(true);
			}
		});
		this.titleLabel.addMouseMotionListener(new MouseMotionAdapter() {
			// @Override
			// public void mouseMoved(MouseEvent e) {
			// syncPreviewWindow();
			// }
			@Override
			public void mouseDragged(MouseEvent e) {
				isInDrag = true;
				if (previewWindow.isVisible()) {
					syncPreviewWindow(false);
					previewWindow.setVisible(false);
				}
			}
		});

		this.iconLabel = new JLabel(icon);
		this.iconLabel.setOpaque(false);
		desktopIcon.add(this.iconLabel);

		this.restoreButton = new JButton(SubstanceImageCreator
				.getRestoreIcon(SubstanceLookAndFeel.getColorScheme()));
		// new JButton(title, icon);
		this.restoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previewWindow.setVisible(false);
				deiconize();
			}
		});
		desktopIcon.add(this.restoreButton);
		this.addClickCorrectionListener(this.restoreButton);
		this.maximizeButton = new JButton(SubstanceImageCreator
				.getMaximizeIcon(SubstanceLookAndFeel.getColorScheme()));
		// new JButton(title, icon);
		this.maximizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previewWindow.setVisible(false);
				try {
					if (frame.isMaximum()) {
						frame.setIcon(false);
					} else {
						frame.setMaximum(true);
					}
				} catch (PropertyVetoException pve) {
				}
			}
		});
		desktopIcon.add(this.maximizeButton);
		this.addClickCorrectionListener(this.maximizeButton);
		this.closeButton = new JButton(SubstanceImageCreator
				.getCloseIcon(SubstanceLookAndFeel.getColorScheme()));
		this.closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					frame.setClosed(true);
				} catch (PropertyVetoException pve) {
				}
			}
		});
		desktopIcon.add(this.closeButton);

		SubstanceBackgroundDelegate.trackTitleButton(this.restoreButton,
				SubstanceButtonUI.ButtonTitleKind.REGULAR_DI);
		SubstanceBackgroundDelegate.trackTitleButton(this.maximizeButton,
				SubstanceButtonUI.ButtonTitleKind.REGULAR_DI);
		SubstanceBackgroundDelegate.trackTitleButton(this.closeButton,
				SubstanceButtonUI.ButtonTitleKind.CLOSE_DI);

		desktopIcon.setLayout(new DesktopPaneLayout());

		desktopIcon.add(this.restoreButton);
		desktopIcon.add(this.maximizeButton);
		desktopIcon.add(this.closeButton);

		this.iconLabel.setOpaque(false);
		this.restoreButton.setOpaque(false);
		this.maximizeButton.setOpaque(false);
		this.closeButton.setOpaque(false);
		desktopIcon.setOpaque(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicDesktopIconUI#uninstallComponents()
	 */
	@Override
	protected void uninstallComponents() {
		desktopIcon.setLayout(null);
		desktopIcon.remove(this.iconLabel);
		desktopIcon.remove(this.restoreButton);
		desktopIcon.remove(this.maximizeButton);
		desktopIcon.remove(this.closeButton);
		this.removeClickCorrectionListener(this.restoreButton);
		this.removeClickCorrectionListener(this.maximizeButton);
		this.restoreButton = null;
		this.maximizeButton = null;
		this.closeButton = null;
		frame = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicDesktopIconUI#installListeners()
	 */
	@Override
	protected void installListeners() {
		super.installListeners();
		desktopIcon.getInternalFrame().addPropertyChangeListener(
				titleListener = new TitleListener());
		this.labelMouseInputListener = createMouseInputListener();
		this.titleLabel.addMouseMotionListener(this.labelMouseInputListener);
		this.titleLabel.addMouseListener(this.labelMouseInputListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicDesktopIconUI#uninstallListeners()
	 */
	@Override
	protected void uninstallListeners() {
		desktopIcon.getInternalFrame().removePropertyChangeListener(
				titleListener);
		titleListener = null;
		this.titleLabel.removeMouseMotionListener(this.labelMouseInputListener);
		this.titleLabel.removeMouseListener(this.labelMouseInputListener);
		this.labelMouseInputListener = null;
		super.uninstallListeners();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#getPreferredSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getPreferredSize(JComponent c) {
		// Desktop icons can not be resized. Their dimensions should
		// always be the minimum size. See getMinimumSize(JComponent c).
		return getMinimumSize(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#getMinimumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMinimumSize(JComponent c) {
		// For the desktop icon we will use the layout maanger to
		// determine the correct height of the component, but we want to keep
		// the width consistent according to the jlf spec.
		return new Dimension(width, desktopIcon.getLayout().minimumLayoutSize(
				desktopIcon).height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#getMaximumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMaximumSize(JComponent c) {
		// Desktop icons can not be resized. Their dimensions should
		// always be the minimum size. See getMinimumSize(JComponent c).
		return getMinimumSize(c);
	}

	/**
	 * Listener on title changes.
	 * 
	 * @author Kirill Grouchnikov
	 */
	class TitleListener implements PropertyChangeListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent e) {
			if (e.getPropertyName().equals("title")) {
				restoreButton.setText((String) e.getNewValue());
			}

			if (e.getPropertyName().equals("frameIcon")) {
				iconLabel.setIcon((Icon) e.getNewValue());
			}
		}
	}

	/**
	 * Custom layout for the desktop icon.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private class DesktopPaneLayout implements LayoutManager {
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
			int height = computeHeight();
			return new Dimension(height, height);
		}

		/**
		 * @return
		 */
		private int computeHeight() {
			FontMetrics fm = desktopIcon.getFontMetrics(desktopIcon.getFont());
			int fontHeight = fm.getHeight();
			fontHeight += 7;
			int iconHeight = SubstanceImageCreator.ICON_DIMENSION;

			int finalHeight = Math.max(fontHeight, iconHeight);
			return finalHeight;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
		 */
		public Dimension minimumLayoutSize(Container c) {
			return preferredLayoutSize(c);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
		 */
		public void layoutContainer(Container c) {
			int w = desktopIcon.getWidth();
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

			x = w;
			spacing = 4;
			x -= (spacing + buttonWidth);
			closeButton.setBounds(x, y, buttonWidth, buttonHeight);

			spacing = 6;
			x -= (spacing + buttonWidth);
			maximizeButton.setBounds(x, y, buttonWidth, buttonHeight);

			spacing = 2;
			x -= (spacing + buttonWidth);
			restoreButton.setBounds(x, y, buttonWidth, buttonHeight);

			iconLabel.setBounds(2, y, iconLabel.getIcon().getIconWidth(),
					iconLabel.getIcon().getIconHeight());

			int xs = 6 + iconLabel.getIcon().getIconWidth();
			titleLabel.setBounds(xs, y, x - xs - 5, buttonHeight);
		}
	}

	/**
	 * Adds correction listener on the specified button. Such listener will
	 * reset model when the specified button is clicked. The button should
	 * disappear on click (such as minimize or restore in our case), thus
	 * leading to inconsistent rollover effects when it's restored.
	 * 
	 * @param button
	 *            Button.
	 */
	private void addClickCorrectionListener(JButton button) {
		for (ActionListener listener : button.getActionListeners())
			if (listener instanceof ClickListener)
				return;
		button.addActionListener(new ClickListener());
	}

	/**
	 * Removes correction listener on the specified button. Such listener resets
	 * model when the specified button is clicked. The button should disappear
	 * on click (such as minimize or restore in our case), thus leading to
	 * inconsistent rollover effects when it's restored.
	 * 
	 * @param button
	 *            Button.
	 */
	private void removeClickCorrectionListener(JButton button) {
		ActionListener[] actionListeners = button.getActionListeners();
		for (ActionListener actionListener : actionListeners) {
			if (actionListener instanceof ClickListener) {
				button.removeActionListener(actionListener);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	public void paint(Graphics g, JComponent c) {
		JInternalFrame.JDesktopIcon di = (JInternalFrame.JDesktopIcon) c;
		di.setOpaque(false);

		int width = di.getWidth();
		int height = di.getHeight();

		ColorSchemeEnum colorSchemeEnum = true ? SubstanceLookAndFeel
				.getColorScheme() : SubstanceLookAndFeel.getColorScheme()
				.getMetallic();

		Graphics2D graphics = (Graphics2D) g.create();
		// the background is translucent
		graphics.setComposite(AlphaComposite.getInstance(
				AlphaComposite.SRC_ATOP, 0.6f));

		graphics.drawImage(SubstanceImageCreator.getRectangularBackground(
				width, height, colorSchemeEnum, false), 0, 0, null);

		di.paintComponents(graphics);

		graphics.dispose();
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#update(java.awt.Graphics, javax.swing.JComponent)
	 */
	@Override
	public void update(Graphics g, JComponent c) {
		this.paint(g, c);
	}

	/**
	 * Synchronizes the preview window.
	 * 
	 * @param toShow Indication whether the preview window is shown.
	 */
	private void syncPreviewWindow(boolean toShow) {
		if (toShow) {
			int x = this.desktopIcon.getLocationOnScreen().x;
			int y = this.desktopIcon.getLocationOnScreen().y;

			this.previewWindow.setLocation(x, y
					- this.previewWindow.getHeight());

		}
	}
}
