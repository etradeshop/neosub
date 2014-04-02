package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Light gray</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class LightGrayColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(250, 250, 250);

	/**
	 * The main extra light color.
	 */
	private static final Color mainExtraLightColor = new Color(240, 240, 240);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(225, 225, 225);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(210, 210, 210);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(180, 180, 180);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(100, 100, 100);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return LightGrayColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return LightGrayColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return LightGrayColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return LightGrayColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return LightGrayColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return LightGrayColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return LightGrayColorScheme.mainUltraDarkColor;
	}
}
