package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Steel blue</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class SteelBlueColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(149, 193, 219);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(130, 181, 212);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(118, 165, 195);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(108, 149, 178);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(38, 79, 111);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(47, 75, 99);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return SteelBlueColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return SteelBlueColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return SteelBlueColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return SteelBlueColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return SteelBlueColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return SteelBlueColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return SteelBlueColorScheme.mainUltraDarkColor;
	}
}
