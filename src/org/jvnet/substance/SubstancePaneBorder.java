package org.jvnet.substance;

import java.awt.*;

import javax.swing.SwingUtilities;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;

import org.jvnet.substance.color.ColorSchemeEnum;

/**
 * Root pane and internal frame border in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstancePaneBorder extends AbstractBorder implements UIResource {
	/**
	 * Default border thickness.
	 */
	private static final int BORDER_THICKNESS = 3;

	/**
	 * Default insets.
	 */
	private static final Insets INSETS = new Insets(BORDER_THICKNESS,
			BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.border.Border#paintBorder(java.awt.Component,
	 *      java.awt.Graphics, int, int, int, int)
	 */
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		Window window = SwingUtilities.getWindowAncestor(c);
		boolean isSelected = (window == null) ? true : window.isActive();

		// Current for active window, GRAY for inactive windows
		ColorSchemeEnum colorSchemeEnum = isSelected ? SubstanceLookAndFeel
				.getColorScheme() : SubstanceLookAndFeel.getColorScheme().getMetallic();
		Graphics2D graphics = (Graphics2D) g;

		// bottom and right in ultra dark
		graphics.setColor(colorSchemeEnum.getColorScheme().getUltraDarkColor());
		graphics.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
		graphics.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
		// top and left in dark
		graphics.setColor(colorSchemeEnum.getColorScheme().getDarkColor());
		graphics.drawLine(x, y, x + w - 2, y);
		graphics.drawLine(x, y, x, y + h - 2);
		// bottom and right in dark
		graphics.setColor(colorSchemeEnum.getColorScheme().getMidColor());
		graphics.drawLine(x + 1, y + h - 2, x + w - 2, y + h - 2);
		graphics.drawLine(x + w - 2, y + 1, x + w - 2, y + h - 2);
		// top and left in ultra light
		graphics
				.setColor(colorSchemeEnum.getColorScheme().getExtraLightColor());
		graphics.drawLine(x + 1, y + 1, x + w - 3, y + 1);
		graphics.drawLine(x + 1, y + 1, x + 1, y + h - 3);
		// inner in light
		graphics
				.setColor(colorSchemeEnum.getColorScheme().getExtraLightColor());
		graphics.drawRect(x + 2, y + 2, w - 5, h - 5);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
	 */
	@Override
	public Insets getBorderInsets(Component c) {
		return INSETS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component,
	 *      java.awt.Insets)
	 */
	@Override
	public Insets getBorderInsets(Component c, Insets newInsets) {
		newInsets.top = INSETS.top;
		newInsets.left = INSETS.left;
		newInsets.bottom = INSETS.bottom;
		newInsets.right = INSETS.right;
		return newInsets;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.border.Border#isBorderOpaque()
	 */
	@Override
	public boolean isBorderOpaque() {
		return false;
	}

}
