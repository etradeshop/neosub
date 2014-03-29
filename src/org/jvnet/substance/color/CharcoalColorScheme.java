package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Charcoal</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class CharcoalColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(110, 21, 27);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(94, 27, 36);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(61, 19, 29);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(50, 20, 22);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(35, 15, 10);

    /**
     * The main ultra-dark color.
     */
    private static final Color mainUltraDarkColor = new Color(13, 8, 4);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.white;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return CharcoalColorScheme.foregroundColor;
    }

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
    */
	public Color getUltraLightColor() {
		return CharcoalColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return CharcoalColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return CharcoalColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return CharcoalColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return CharcoalColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return CharcoalColorScheme.mainUltraDarkColor;
	}
}
