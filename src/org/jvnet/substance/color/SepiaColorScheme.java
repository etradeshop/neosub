package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Sepia</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class SepiaColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(220, 182, 150);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(205, 168, 135);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(195, 153, 128);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(187, 151, 102);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(157, 102, 72);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(154, 106, 84);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return SepiaColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return SepiaColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return SepiaColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return SepiaColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return SepiaColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return SepiaColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return SepiaColorScheme.mainUltraDarkColor;
	}
}
