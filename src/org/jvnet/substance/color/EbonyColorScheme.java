package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Ebony</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class EbonyColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(85, 85, 85);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(75, 75, 75);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(60, 60, 60);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(40, 40, 40);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(20, 20, 20);

    /**
     * The main ultra-dark color.
     */
    private static final Color mainUltraDarkColor = new Color(10, 10, 10);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.white;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return EbonyColorScheme.foregroundColor;
    }

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
    */
	public Color getUltraLightColor() {
		return EbonyColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return EbonyColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return EbonyColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return EbonyColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return EbonyColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return EbonyColorScheme.mainUltraDarkColor;
	}
}
