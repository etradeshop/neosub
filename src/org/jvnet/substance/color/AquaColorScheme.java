package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Aqua</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class AquaColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(194, 224, 237);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(164, 227, 243);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(112, 206, 239);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(32, 180, 226);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(44, 47, 140);

    /**
     * The main ultra-dark color.
     */
    private static final Color mainUltraDarkColor = new Color(30, 40, 100);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return AquaColorScheme.foregroundColor;
    }

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
    */
	public Color getUltraLightColor() {
		return AquaColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return AquaColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return AquaColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return AquaColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return AquaColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return AquaColorScheme.mainUltraDarkColor;
	}
}
