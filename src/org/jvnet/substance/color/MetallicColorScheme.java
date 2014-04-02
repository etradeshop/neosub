package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Metallic</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class MetallicColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(250, 252, 255);

	/**
	 * The main extra light color.
	 */
	private static final Color mainExtraLightColor = new Color(240, 245, 250);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(200, 210, 220);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(180, 185, 190);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(80, 85, 90);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(32, 37, 42);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return MetallicColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return MetallicColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return MetallicColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return MetallicColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return MetallicColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return MetallicColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return MetallicColorScheme.mainUltraDarkColor;
	}
}
