package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Sun glare</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class SunGlareColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(255, 255, 209);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(248, 249, 160);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(255, 255, 80);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(252, 226, 55);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(106, 29, 0);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(67, 18, 0);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return SunGlareColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return SunGlareColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return SunGlareColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return SunGlareColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return SunGlareColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return SunGlareColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return SunGlareColorScheme.mainUltraDarkColor;
	}
}
