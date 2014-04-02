package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Dark violet</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class DarkVioletColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(107, 22, 124);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(89, 19, 113);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(83, 17, 104);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(53, 6, 31);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(33, 1, 38);

    /**
     * The main ultra-dark color.
     */
    private static final Color mainUltraDarkColor = new Color(15, 1, 23);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.white;//new Color(255, 224, 190);

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return DarkVioletColorScheme.foregroundColor;
    }

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
    */
	public Color getUltraLightColor() {
		return DarkVioletColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return DarkVioletColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return DarkVioletColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return DarkVioletColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return DarkVioletColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return DarkVioletColorScheme.mainUltraDarkColor;
	}
}
