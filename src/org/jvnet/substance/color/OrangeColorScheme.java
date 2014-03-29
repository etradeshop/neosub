package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Orange</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class OrangeColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	public static final Color mainUltraLightColor = new Color(255, 250, 235);

	/**
	 * The main light color.
	 */
	public static final Color mainExtraLightColor = new Color(255, 220, 180);

	/**
	 * The main light color.
	 */
	public static final Color mainLightColor = new Color(245, 200, 128);

	/**
	 * The main medium color.
	 */
	public static final Color mainMidColor = new Color(240, 170, 50);

	/**
	 * The main dark color.
	 */
	public static final Color mainDarkColor = new Color(229, 151, 0);

	/**
	 * The main ultra-dark color.
	 */
	public static final Color mainUltraDarkColor = new Color(180, 100, 0);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return OrangeColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return OrangeColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return OrangeColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return OrangeColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return OrangeColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return OrangeColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return OrangeColorScheme.mainUltraDarkColor;
	}
}
