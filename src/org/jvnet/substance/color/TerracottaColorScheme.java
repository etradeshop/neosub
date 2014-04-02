package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Brick</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class TerracottaColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(250, 203, 125);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(248, 191, 114);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(239, 176, 105);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(227, 147, 88);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(195, 113, 63);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(163, 87, 64);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return TerracottaColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return TerracottaColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return TerracottaColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return TerracottaColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return TerracottaColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return TerracottaColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return TerracottaColorScheme.mainUltraDarkColor;
	}
}
