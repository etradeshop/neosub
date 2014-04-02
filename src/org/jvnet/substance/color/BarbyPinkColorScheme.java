package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Barby pink</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class BarbyPinkColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(240, 159, 242);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(239, 153, 235);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(238, 139, 230);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(231, 95, 193);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(150, 30, 101);

    /**
     * The main ultra-dark color.
     */
    private static final Color mainUltraDarkColor = new Color(111, 29, 78);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return BarbyPinkColorScheme.foregroundColor;
    }

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
    */
	public Color getUltraLightColor() {
		return BarbyPinkColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return BarbyPinkColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return BarbyPinkColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return BarbyPinkColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return BarbyPinkColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return BarbyPinkColorScheme.mainUltraDarkColor;
	}
}
