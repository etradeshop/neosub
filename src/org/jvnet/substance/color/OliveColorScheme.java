package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Olive</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class OliveColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(205, 212, 182);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(189, 192, 165);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(175, 183, 142);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(165, 174, 129);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(135, 142, 102);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(104, 111, 67);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return OliveColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return OliveColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return OliveColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return OliveColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return OliveColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return OliveColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return OliveColorScheme.mainUltraDarkColor;
	}
}
