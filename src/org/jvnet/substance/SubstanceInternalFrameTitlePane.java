package org.jvnet.substance;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.WeakHashMap;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.plaf.metal.MetalInternalFrameTitlePane;

import org.jvnet.substance.color.ColorSchemeEnum;

/**
 * UI for internal frame title pane in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceInternalFrameTitlePane extends
		MetalInternalFrameTitlePane {
	/**
	 * Simple constructor.
	 * 
	 * @param f
	 *            Associated internal frame.
	 */
	public SubstanceInternalFrameTitlePane(JInternalFrame f) {
		super(f);
		this.setToolTipText(f.getTitle());
	}

	private static WeakHashMap<JInternalFrame, BufferedImage> snapshots = new WeakHashMap<JInternalFrame, BufferedImage>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (isPalette) {
			paintPalette(g);
			return;
		}

		boolean isSelected = frame.isSelected();

		int width = getWidth();
		int height = getHeight() + 2;

		ColorSchemeEnum colorSchemeEnum = isSelected ? SubstanceLookAndFeel
				.getColorScheme() : SubstanceLookAndFeel.getColorScheme()
				.getMetallic();

		g.drawImage(SubstanceImageCreator.getRectangularBackground(width,
				height, colorSchemeEnum, false), 0, 0, null);

		// offset of border
		int xOffset = 5;

		Icon icon = frame.getFrameIcon();
		if (icon != null) {
			int iconY = ((height / 2) - (icon.getIconHeight() / 2));
			icon.paintIcon(frame, g, xOffset, iconY);
			xOffset += icon.getIconWidth() + 5;
		}

		String theTitle = frame.getTitle();
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
		if (frame.isIconifiable()) {
			leftmostButton = iconButton;
		} else {
			if (frame.isMaximizable()) {
				leftmostButton = maxButton;
			} else {
				if (frame.isClosable()) {
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
			FontMetrics fm = frame.getFontMetrics(g.getFont());

			int titleWidth = rightTransitionStart - leftTransitionEnd - 30;
			theTitle = Utilities.clipString(fm, titleWidth, theTitle);
		}

		// draw the title (if needed)
		if (theTitle != null) {
			Graphics2D graphics = (Graphics2D) g;
			FontMetrics fm = frame.getFontMetrics(g.getFont());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicInternalFrameTitlePane#setButtonIcons()
	 */
	@Override
	protected void setButtonIcons() {
		super.setButtonIcons();
		Icon minIcon = SubstanceIconFactory.getTitlePaneIcon(
				SubstanceIconFactory.IconKind.RESTORE, SubstanceLookAndFeel
						.getColorScheme());
		Icon maxIcon = SubstanceIconFactory.getTitlePaneIcon(
				SubstanceIconFactory.IconKind.MAXIMIZE, SubstanceLookAndFeel
						.getColorScheme());
		Icon iconIcon = SubstanceIconFactory.getTitlePaneIcon(
				SubstanceIconFactory.IconKind.MINIMIZE, SubstanceLookAndFeel
						.getColorScheme());
		Icon closeIcon = SubstanceIconFactory.getTitlePaneIcon(
				SubstanceIconFactory.IconKind.CLOSE, SubstanceLookAndFeel
						.getColorScheme());
		if (frame.isIcon()) {
			if (minIcon != null) {
				iconButton.setIcon(minIcon);
			}
			if (maxIcon != null) {
				maxButton.setIcon(maxIcon);
			}
		} else {
			if (frame.isMaximum()) {
				if (iconIcon != null) {
					iconButton.setIcon(iconIcon);
				}
				if (minIcon != null) {
					maxButton.setIcon(minIcon);
				}
			} else {
				if (iconIcon != null) {
					iconButton.setIcon(iconIcon);
				}
				if (maxIcon != null) {
					maxButton.setIcon(maxIcon);
				}
			}
		}
		if (closeIcon != null) {
			closeButton.setIcon(closeIcon);
		}
	}

	/**
	 * Click correction listener that resets models of minimize and restore
	 * buttons on click (so that the rollover behaviour will be preserved
	 * correctly).
	 * 
	 * @author Kirill Grouchnikov.
	 */
	public static class ClickListener implements ActionListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			AbstractButton src = (AbstractButton) e.getSource();
			ButtonModel model = src.getModel();
			model.setArmed(false);
			model.setPressed(false);
			model.setRollover(false);
			model.setSelected(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicInternalFrameTitlePane#createButtons()
	 */
	@Override
	protected void createButtons() {
		super.createButtons();
		for (ActionListener listener : iconButton.getActionListeners())
			if (listener instanceof ClickListener)
				return;
		this.iconButton.addActionListener(new ClickListener());
		SubstanceBackgroundDelegate.trackTitleButton(this.iconButton,
				SubstanceButtonUI.ButtonTitleKind.REGULAR);
		SubstanceBackgroundDelegate.trackTitleButton(this.maxButton,
				SubstanceButtonUI.ButtonTitleKind.REGULAR);
		SubstanceBackgroundDelegate.trackTitleButton(this.closeButton,
				SubstanceButtonUI.ButtonTitleKind.CLOSE);
		// this.iconButton.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// // System.out.println("Draw button");
		// updateSnapshot(frame);
		// }
		// });

		this.frame.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
//				if ((JInternalFrame.IS_ICON_PROPERTY.equals(evt
//						.getPropertyName()))
//						&& (evt.getNewValue() == Boolean.TRUE)) {
//					// System.out.println("Draw property");
//					updateSnapshotRobot(frame);
//				}
				// apparently, listening on IS_ICON_PROPERTY property
				// is too late (the frame will be hidden by then).
				if ("ancestor".equals(evt.getPropertyName())) {
					updateSnapshot(frame);
				}
			}
		});

	}

	public static synchronized BufferedImage getSnapshot(JInternalFrame frame) {
		BufferedImage result = snapshots.get(frame);
		if (result != null)
			return result;
		// frame.setVisible(true);
		// updateSnapshot(frame);
		// frame.setVisible(false);
		// return snapshots.get(frame);
		return null;
	}

//	private static void updateSnapshotRobot(JInternalFrame frame) {
//		// hopefully the internal frame is still around for us to
//		// capture
//		if (!frame.isShowing())
//			return;
//
//		int x = frame.getLocationOnScreen().x;
//		int y = frame.getLocationOnScreen().y;
//		int width = frame.getWidth();
//		int height = frame.getHeight();
//
//		int frameWidth = frame.getWidth();
//		int frameHeight = frame.getHeight();
//
//		BufferedImage tempCanvas = new BufferedImage(frameWidth, frameHeight,
//				BufferedImage.TYPE_INT_ARGB);
//		Graphics tempCanvasGraphics = tempCanvas.getGraphics();
//		frame.paint(tempCanvasGraphics);
//		x = 0;
//		y = 0;
//		width = frameWidth;
//		height = frameHeight;
//
//		// Now we need to remove the border and the title pane :)
//		Border internalFrameBorder = UIManager
//				.getBorder("InternalFrame.border");
//		Insets borderInsets = internalFrameBorder.getBorderInsets(frame);
//		x += borderInsets.left;
//		y += borderInsets.top;
//		width -= (borderInsets.left + borderInsets.right);
//		height -= (borderInsets.top + borderInsets.bottom);
//
//		BasicInternalFrameUI frameUI = (BasicInternalFrameUI) frame.getUI();
//		JComponent frameTitlePane = frameUI.getNorthPane();
//
//		y += frameTitlePane.getHeight();
//		height -= frameTitlePane.getHeight();
//
//		// try to capture screen
//		try {
//			// Robot robot = new Robot();
//			// BufferedImage snapshot = robot
//			// .createScreenCapture(new Rectangle(x, y, width,
//			// height));
//
//			int maxWidth = UIManager.getInt("DesktopIcon.width");
//			int maxHeight = maxWidth;
//
//			// check if need to scale down
//			double coef = Math.min((double) maxWidth / (double) width,
//					(double) maxHeight / (double) height);
//			if (coef < 1.0) {
//				int sdWidth = (int) (coef * width);
//				int sdHeight = (int) (coef * height);
//				BufferedImage scaledDown = new BufferedImage(sdWidth, sdHeight,
//						BufferedImage.TYPE_INT_ARGB);
//				Graphics g = scaledDown.getGraphics();
//				g.drawImage(tempCanvas, 0, 0, sdWidth, sdHeight, 0, 0, width,
//						height, null);
//				// System.out.println("Putting " + frame.hashCode() + "
//				// -> " +
//				// scaledDown.hashCode());
//				snapshots.put(frame, scaledDown);
//			} else {
//				// System.out.println("Putting " + frame.hashCode() + "
//				// -> " +
//				// snapshot.hashCode());
//				snapshots.put(frame, tempCanvas);
//			}
//		} catch (Exception exc) {
//			// ignore - no thumbnail preview is stored
//		}
//	}
//
	private static void updateSnapshot(JInternalFrame frame) {
		if (!frame.isShowing())
			return;
		// Draw the current state of the internal frame to a
		// temp image (w/o border and decorations). It would be nice
		// to use Robot, but this frame may be partially obscured,
		// so we take our chances that the frame will be properly
		// drawn by the user code.
		int frameWidth = frame.getWidth();
		int frameHeight = frame.getHeight();

		int dx = 0;
		int dy = 0;
		// Now we need to remove the border and the title pane :)
		Border internalFrameBorder = UIManager
				.getBorder("InternalFrame.border");
		Insets borderInsets = internalFrameBorder.getBorderInsets(frame);
		dx += borderInsets.left;
		dy += borderInsets.top;
		frameWidth -= (borderInsets.left + borderInsets.right);
		frameHeight -= (borderInsets.top + borderInsets.bottom);

		BasicInternalFrameUI frameUI = (BasicInternalFrameUI) frame.getUI();
		JComponent frameTitlePane = frameUI.getNorthPane();

		dy += frameTitlePane.getHeight();
		frameHeight -= frameTitlePane.getHeight();

		// draw frame (note the canvas translation)
		BufferedImage tempCanvas = new BufferedImage(frameWidth, frameHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics tempCanvasGraphics = tempCanvas.getGraphics();
		tempCanvasGraphics.translate(-dx, -dy);
		frame.paint(tempCanvasGraphics);

		int maxWidth = UIManager.getInt("DesktopIcon.width");
		int maxHeight = maxWidth;

		// check if need to scale down
		double coef = Math.min((double) maxWidth / (double) frameWidth,
				(double) maxHeight / (double) frameHeight);
		if (coef < 1.0) {
			int sdWidth = (int) (coef * frameWidth);
			int sdHeight = (int) (coef * frameHeight);
			BufferedImage scaledDown = new BufferedImage(sdWidth, sdHeight,
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = scaledDown.getGraphics();
			g.drawImage(tempCanvas, 0, 0, sdWidth, sdHeight, 0, 0, frameWidth,
					frameHeight, null);
			// System.out.println("Putting " + frame.hashCode() + "
			// -> " + scaledDown.hashCode());
			snapshots.put(frame, scaledDown);
		} else {
			// System.out.println("Putting " + frame.hashCode() + "
			// -> " + snapshot.hashCode());
			snapshots.put(frame, tempCanvas);
		}
	}
}
