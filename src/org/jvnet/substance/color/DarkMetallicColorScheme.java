package org.jvnet.substance.color;

import java.awt.Color;

/**
 * <b>Dark metallic</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class DarkMetallicColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(125, 126, 128);

	/**
	 * The main extra light color.
	 */
	private static final Color mainExtraLightColor = new Color(120, 122, 125);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(100, 105, 110);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(90, 92, 95);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(40, 42, 45);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(16, 19, 21);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.white;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return DarkMetallicColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return DarkMetallicColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return DarkMetallicColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return DarkMetallicColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return DarkMetallicColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return DarkMetallicColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return DarkMetallicColorScheme.mainUltraDarkColor;
	}
}
