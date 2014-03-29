package org.jvnet.substance.color;

import java.awt.Color;

import org.jvnet.substance.color.ColorScheme;

/**
 * <b>Raspberry</b> color scheme.
 * 
 * @author Kirill Grouchnikov
 */
public class RaspberryColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(254, 166, 189);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(255, 152, 177);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(251, 110, 144);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(225, 52, 98);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(84, 28, 41);

	/**
	 * The main ultra-dark color.
	 */
	private static final Color mainUltraDarkColor = new Color(40, 0, 9);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return RaspberryColorScheme.foregroundColor;
    }

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
	 */
	public Color getUltraLightColor() {
		return RaspberryColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return RaspberryColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return RaspberryColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return RaspberryColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return RaspberryColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return RaspberryColorScheme.mainUltraDarkColor;
	}
}
