package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Lime green</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class LimeGreenColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(205, 255, 85);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(172, 255, 54);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(169, 248, 57);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(117, 232, 39);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(18, 86, 0);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(8, 62, 0);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return LimeGreenColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return LimeGreenColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return LimeGreenColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return LimeGreenColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return LimeGreenColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return LimeGreenColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return LimeGreenColorScheme.mainUltraDarkColor;
	}
}
