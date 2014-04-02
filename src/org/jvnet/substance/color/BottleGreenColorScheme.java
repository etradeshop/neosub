package org.jvnet.substance.color;

import java.awt.Color;

import org.jvnet.substance.color.ColorScheme;

/**
 * <b>Bottle green</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class BottleGreenColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(145, 209, 131);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(115, 197, 99);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(63, 181, 59);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(6, 139, 58);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(11, 75, 38);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(0, 14, 14);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return BottleGreenColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return BottleGreenColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return BottleGreenColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return BottleGreenColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return BottleGreenColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return BottleGreenColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return BottleGreenColorScheme.mainUltraDarkColor;
	}
}
