package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Sunset</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class SunsetColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(255, 196, 56);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(255, 162, 45);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(255, 137, 41);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(254, 97, 30);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(197, 19, 55);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(115, 38, 80);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return SunsetColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return SunsetColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return SunsetColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return SunsetColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return SunsetColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return SunsetColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return SunsetColorScheme.mainUltraDarkColor;
	}
}
