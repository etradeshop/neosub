package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Dark gray</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class DarkGrayColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(125, 125, 125);

	/**
	 * The main extra light color.
	 */
	private static final Color mainExtraLightColor = new Color(120, 120, 120);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(112, 112, 112);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(105, 105, 105);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(90, 90, 90);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(50, 50, 50);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return DarkGrayColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return DarkGrayColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return DarkGrayColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return DarkGrayColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return DarkGrayColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return DarkGrayColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return DarkGrayColorScheme.mainUltraDarkColor;
	}
}
