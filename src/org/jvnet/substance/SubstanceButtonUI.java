package org.jvnet.substance;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

/**
 * UI for buttons in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceButtonUI extends MetalButtonUI {
	/**
	 * The default width of button
	 */
	public static final int DEFAULT_WIDTH = 70;

	/**
	 * The default height of button
	 */
	public static final int DEFAULT_HEIGHT = 20;

	/**
	 * Button kind in title pane.
	 * 
	 * @author Kirill Grouchnikov
	 */
	public enum ButtonTitleKind {
		NONE, REGULAR, CLOSE, REGULAR_DI, CLOSE_DI
	}

	/**
	 * UI instance.
	 */
	private static final SubstanceButtonUI INSTANCE = new SubstanceButtonUI();

	/**
	 * Painting delegate.
	 */
	private SubstanceBackgroundDelegate delegate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent b) {
		((AbstractButton) b).setRolloverEnabled(true);
		return INSTANCE;
	}

	/**
	 * Simple constructor.
	 */
	public SubstanceButtonUI() {
		this.delegate = new SubstanceBackgroundDelegate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicButtonUI#installDefaults(javax.swing.AbstractButton)
	 */
	@Override
	public void installDefaults(AbstractButton b) {
		super.installDefaults(b);
		b.setBorder(new Border() {
			public Insets getBorderInsets(Component c) {
				if (c instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) c;
					if (Utilities.isRibbonButton(button)
							|| Utilities.isButtonStripButton(button))
						return new Insets(0, 0, 0, 0);
					if (Utilities.hasText(button)) {
						return new Insets(0, DEFAULT_HEIGHT / 2, 0,
								DEFAULT_HEIGHT / 2);
					}
				}
				return new Insets(0, 0, 0, 0);
			}

			public boolean isBorderOpaque() {
				return false;
			}

			public void paintBorder(Component c, Graphics g, int x, int y,
					int width, int height) {
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicButtonUI#createButtonListener(javax.swing.AbstractButton)
	 */
	// @Override
	// protected BasicButtonListener createButtonListener(AbstractButton b) {
	// ActionListener[] existing = b.getActionListeners();
	// for (ActionListener existingL : existing) {
	// if (existingL instanceof RolloverButtonListener)
	// return (RolloverButtonListener) existingL;
	// }
	// return RolloverButtonListener.getListener(b);
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void update(Graphics g, JComponent c) {
		AbstractButton button = (AbstractButton) c;
		long cycle = 0;
		if (button instanceof JButton) {
			JButton jb = (JButton) button;
			if (jb.isDefaultButton()) {
				PulseTracker.update(jb);
			}
			cycle = PulseTracker.getCycles(jb);
		}
		this.delegate.updateBackground(g, button, cycle);
		paint(g, c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#getPreferredSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getPreferredSize(JComponent c) {
		Dimension result;
		boolean toTweakWidth = false;
		boolean toTweakHeight = false;
		AbstractButton button = (AbstractButton) c;

		Icon icon = button.getIcon();
		boolean hasIcon = Utilities.hasIcon(button);
		boolean hasText = Utilities.hasText(button);

		Dimension baseDimension = super.getPreferredSize(c);
		result = baseDimension;

		if (hasText) {
			int baseWidth = baseDimension.width;
			if (baseWidth < DEFAULT_WIDTH) {
				baseWidth = DEFAULT_WIDTH;
			}
			result = new Dimension(baseWidth, baseDimension.height);
			int baseHeight = result.height;
			if (baseHeight < DEFAULT_HEIGHT) {
				baseHeight = DEFAULT_HEIGHT;
			}
			result = new Dimension(result.width, baseHeight);
		}

		if (hasIcon) {
			// check the icon height
			int iconHeight = icon.getIconHeight();
			if (iconHeight > (result.getHeight() - 6)) {
				result = new Dimension(result.width, iconHeight);
				toTweakHeight = true;
			}
			int iconWidth = icon.getIconWidth();
			if (iconWidth > (result.getWidth() - 6)) {
				result = new Dimension(iconWidth, result.height);
				toTweakWidth = true;
			}
		}

		if (Utilities.isScrollBarButton(button)) {
			toTweakWidth = false;
			toTweakHeight = false;
		}

		if (toTweakWidth) {
			result = new Dimension(result.width + 6, result.height);
		}
		if (toTweakHeight) {
			result = new Dimension(result.width, result.height + 6);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicButtonUI#paintFocus(java.awt.Graphics,
	 *      javax.swing.AbstractButton, java.awt.Rectangle, java.awt.Rectangle,
	 *      java.awt.Rectangle)
	 */
	@Override
	protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect,
			Rectangle textRect, Rectangle iconRect) {
		// overriden to remove default metal effect
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicButtonUI#paintButtonPressed(java.awt.Graphics,
	 *      javax.swing.AbstractButton)
	 */
	@Override
	protected void paintButtonPressed(Graphics g, AbstractButton b) {
		// overriden to remove default metal effect
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#contains(javax.swing.JComponent, int,
	 *      int)
	 */
	@Override
	public boolean contains(JComponent c, int x, int y) {
		return SubstanceBackgroundDelegate.contains((JButton) c, x, y);
	}
}
