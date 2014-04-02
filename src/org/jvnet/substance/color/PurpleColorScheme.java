package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Purple</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class PurpleColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(240, 220, 245);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(218, 209, 233);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(203, 175, 237);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(201, 135, 226);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(140, 72, 170);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(94, 39, 114);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return PurpleColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return PurpleColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return PurpleColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return PurpleColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return PurpleColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return PurpleColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return PurpleColorScheme.mainUltraDarkColor;
	}
}
