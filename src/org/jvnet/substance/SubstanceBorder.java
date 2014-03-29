package org.jvnet.substance;

import java.awt.*;

import javax.swing.border.Border;

/**
 * Gradient border for the <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceBorder implements Border {
	/**
	 * Default insets.
	 */
	public final static Insets INSETS = new Insets(2, 2, 2, 2);

	/**
	 * Paints border instance for the specified component.
	 * 
	 * @param c
	 *            The component.
	 * @param g
	 *            Graphics context.
	 * @param x
	 *            Component left X (in graphics context).
	 * @param y
	 *            Component top Y (in graphics context).
	 * @param width
	 *            Component width.
	 * @param height
	 *            Component height.
	 * @param isEnabled
	 *            Component enabled status.
	 * @param hasFocus
	 *            Component focus ownership status.
	 */
	private static void paintBorder(Component c, Graphics g, int x, int y,
			int width, int height, boolean isEnabled, boolean hasFocus) {
		boolean isFocused = FocusBorderListener.isFocused(c);

		if (isEnabled && isFocused) {
			SubstanceImageCreator.paintBorder(g, x, y, width, height,
					SubstanceLookAndFeel.getColorScheme());
		} else {
			if (isEnabled) {
				SubstanceImageCreator.paintBorder(g, x, y, width, height,
						SubstanceLookAndFeel.getColorScheme().getMetallic());
			} else {
				SubstanceImageCreator.paintBorder(g, x, y, width, height,
						SubstanceLookAndFeel.getColorScheme().getGray());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.border.Border#paintBorder(java.awt.Component,
	 *      java.awt.Graphics, int, int, int, int)
	 */
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		SubstanceBorder.paintBorder(c, g, x, y, width, height, c.isEnabled(), c
				.hasFocus());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
	 */
	public Insets getBorderInsets(Component c) {
		return INSETS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.border.Border#isBorderOpaque()
	 */
	public boolean isBorderOpaque() {
		return false;
	}
}
