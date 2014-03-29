package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Ligh aqua</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class LightAquaColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(215, 238, 250);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(194, 224, 237);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(164, 227, 243);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(112, 206, 239);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(32, 180, 226);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(44, 47, 140);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return LightAquaColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return LightAquaColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return LightAquaColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return LightAquaColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return LightAquaColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return LightAquaColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return LightAquaColorScheme.mainUltraDarkColor;
	}
}
