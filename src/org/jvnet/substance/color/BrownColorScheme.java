package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Brown</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class BrownColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(240, 230, 170);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(230, 219, 142);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(217, 179, 89);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(190, 137, 27);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(162, 90, 26);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(94, 71, 57);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return BrownColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return BrownColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return BrownColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return BrownColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return BrownColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return BrownColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return BrownColorScheme.mainUltraDarkColor;
	}
}
