package org.jvnet.substance;

import java.awt.*;

import javax.swing.*;

import org.jvnet.substance.color.ColorSchemeEnum;

/**
 * Delegate for painting background of menu items.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceMenuBackgroundDelegate {
	/**
	 * Delegate for painting gradient background.
	 */
	private static SubstanceGradientBackgroundDelegate activeBackgroundDelegate = new SubstanceGradientBackgroundDelegate();

	/**
	 * Delegate for painting fill background.
	 */
	private static SubstanceFillBackgroundDelegate fillBackgroundDelegate = new SubstanceFillBackgroundDelegate();

	/**
	 * Updates the specified component with the background that matches the
	 * provided parameters.
	 * 
	 * @param g
	 *            Graphic context.
	 * @param component
	 *            Component.
	 * @param width
	 *            Background width.
	 * @param height
	 *            Background height.
	 * @param colorSchemeEnum
	 *            Color scheme for the background.
	 * @param hasDarkBorder
	 *            If <code>true</code>, the resulting image will have dark
	 *            border.
	 */
	private void paintBackground(Graphics g, Component component, int width,
			int height, ColorSchemeEnum colorSchemeEnum, boolean hasDarkBorder) {
		activeBackgroundDelegate.update(g, component, width, height,
				colorSchemeEnum, hasDarkBorder);
	}

	/**
	 * Updates the specified menu item with the background that matches the
	 * provided parameters.
	 * 
	 * @param g
	 *            Graphic context.
	 * @param menuItem
	 *            Menu item.
	 * @param bgColor
	 *            Current background color.
	 * @param hasDarkBorder
	 *            If <code>true</code>, the resulting image will have dark
	 *            border.
	 */
	public void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor,
			boolean hasDarkBorder) {
		if (!menuItem.isShowing())
			return;
		ButtonModel model = menuItem.getModel();
		Color oldColor = g.getColor();
		int menuWidth = menuItem.getWidth();
		int menuHeight = menuItem.getHeight();

		if (menuItem.isOpaque()) {
			if (model.isArmed()
					|| (menuItem instanceof JMenu && model.isSelected())) {
				paintBackground(g, menuItem, menuWidth, menuHeight,
						SubstanceLookAndFeel.getColorScheme(), hasDarkBorder);
			} else {
				// menu item is opaque and selected (or armed) -
				// use background color of the item (with watermark)
				g.setColor(menuItem.getBackground());
				g.fillRect(0, 0, menuWidth, menuHeight);
				fillBackgroundDelegate.update(g, menuItem);
			}
			g.setColor(oldColor);
		} else if (model.isArmed()
				|| (menuItem instanceof JMenu && model.isSelected())) {
			paintBackground(g, menuItem, menuWidth, menuHeight,
					SubstanceLookAndFeel.getColorScheme(), hasDarkBorder);
			g.setColor(oldColor);
		}
	}

}
