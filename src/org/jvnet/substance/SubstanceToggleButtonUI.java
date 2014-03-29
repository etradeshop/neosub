package org.jvnet.substance;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToggleButtonUI;

/**
 * UI for toggle buttons in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceToggleButtonUI extends MetalToggleButtonUI {
	/**
	 * UI instance.
	 */
	private static final SubstanceToggleButtonUI INSTANCE = new SubstanceToggleButtonUI();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent b) {
		((AbstractButton)b).setRolloverEnabled(true);
		return INSTANCE;
	}

	/**
	 * Painting delegate.
	 */
	private SubstanceBackgroundDelegate delegate;

	/**
	 * Simple constructor.
	 */
	public SubstanceToggleButtonUI() {
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
						return new Insets(0,
								SubstanceButtonUI.DEFAULT_HEIGHT / 2, 0,
								SubstanceButtonUI.DEFAULT_HEIGHT / 2);
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
//	@Override
//	protected BasicButtonListener createButtonListener(AbstractButton b) {
//		b.setRolloverEnabled(true);
//		return RolloverButtonListener.getListener(b);
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#update(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	@Override
	public void update(Graphics g, JComponent c) {
		AbstractButton button = (AbstractButton) c;
		this.delegate.updateBackground(g, button, 0);
		paint(g, c);
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
	 * @see javax.swing.plaf.basic.BasicButtonUI#paintText(java.awt.Graphics,
	 *      javax.swing.JComponent, java.awt.Rectangle, java.lang.String)
	 */
	@Override
	protected void paintText(Graphics g, JComponent c, Rectangle textRect,
			String text) {
		AbstractButton b = (AbstractButton) c;
		ButtonModel model = b.getModel();
		if ((!model.isEnabled()) && model.isSelected()) {
			synchronized (c) {
				Color oldBack = b.getBackground();
				b.setBackground(disabledTextColor);
				super.paintText(g, c, textRect, text);
				b.setBackground(oldBack);
			}
		} else {
			super.paintText(g, c, textRect, text);
		}
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
			if (baseWidth < SubstanceButtonUI.DEFAULT_WIDTH)
				baseWidth = SubstanceButtonUI.DEFAULT_WIDTH;
			result = new Dimension(baseWidth, baseDimension.height);
			int baseHeight = result.height;
			if (baseHeight < SubstanceButtonUI.DEFAULT_HEIGHT)
				baseHeight = SubstanceButtonUI.DEFAULT_HEIGHT;
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
}