package org.jvnet.substance;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.jvnet.substance.color.ColorSchemeEnum;

/**
 * Delegate for painting gradient backgrounds.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceGradientBackgroundDelegate {
	/**
	 * Hash for computed backgrounds.
	 * 
	 * @see #getBackground(int, int, ColorSchemeEnum, boolean)
	 */
	private static Map<String, BufferedImage> backgrounds = new HashMap<String, BufferedImage>();

	/**
	 * Resets image maps (used when setting new theme).
	 * 
	 * @see SubstanceLookAndFeel#setCurrentTheme(String)
	 * @see SubstanceLookAndFeel#setCurrentTheme(SubstanceTheme)
	 */
	static synchronized void reset() {
		backgrounds.clear();
	}

	/**
	 * Retrieves background matching the parameters.
	 * 
	 * @param width
	 *            Background width.
	 * @param height
	 *            Background height.
	 * @param colorSchemeEnum
	 *            Color scheme for the background.
	 * @param hasDarkBorder
	 *            If <code>true</code>, the resulting image will have dark
	 *            border.
	 * @return Background matching the parameters.
	 */
	private synchronized BufferedImage getBackground(int width, int height,
			ColorSchemeEnum colorSchemeEnum, boolean hasDarkBorder) {
		String key = width + "*" + height + ":" + colorSchemeEnum.toString()
				+ ":" + hasDarkBorder;
		if (!backgrounds.containsKey(key)) {
			BufferedImage newOne = SubstanceImageCreator
					.getRectangularBackground(width, height, colorSchemeEnum,
							hasDarkBorder);
			backgrounds.put(key, newOne);
		}
		return backgrounds.get(key);
	}

	/**
	 * Updates the specified component with the background that matches the
	 * provided parameters.
	 * 
	 * @param g
	 *            Graphic context.
	 * @param c
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
	public void update(Graphics g, Component c, int width, int height,
			ColorSchemeEnum colorSchemeEnum, boolean hasDarkBorder) {
		if (!c.isShowing())
			return;
		synchronized (c) {
			g.drawImage(getBackground(width, height, colorSchemeEnum,
					hasDarkBorder), 0, 0, null);
		}
	}

	/**
	 * Updates the specified component with the background that matches the
	 * provided parameters.
	 * 
	 * @param g
	 *            Graphic context.
	 * @param c
	 *            Component.
	 * @param rect
	 *            Background rectangle.
	 * @param colorSchemeEnum
	 *            Color scheme for the background.
	 * @param hasDarkBorder
	 *            If <code>true</code>, the resulting image will have dark
	 *            border.
	 */
	public void update(Graphics g, Component c, Rectangle rect,
			ColorSchemeEnum colorSchemeEnum, boolean hasDarkBorder) {
		synchronized (c) {
			g.drawImage(getBackground(rect.width, rect.height, colorSchemeEnum,
					hasDarkBorder), rect.x, rect.y, null);
		}
	}

	static String getMemoryUsage() {
		StringBuffer sb = new StringBuffer();
		sb.append("SubstanceGradientBackgroundDelegate: \n");
		sb.append("\t" + backgrounds.size() + " backgrounds");
		return sb.toString();
	}
}
