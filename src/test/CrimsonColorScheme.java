package test;

import java.awt.Color;

import org.jvnet.substance.color.ColorScheme;

/**
 * <b>Crimson</b> color scheme - for close buttons.
 * 
 * @author Kirill Grouchnikov
 */
public class CrimsonColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(175, 135, 153);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(183, 126, 140);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(194, 98, 101);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(175, 41, 27);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(141, 24, 18);

    /**
     * The main ultra-dark color.
     */
    private static final Color mainUltraDarkColor = new Color(116, 40, 31);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return CrimsonColorScheme.foregroundColor;
    }

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
    */
	public Color getUltraLightColor() {
		return CrimsonColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return CrimsonColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return CrimsonColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return CrimsonColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return CrimsonColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return CrimsonColorScheme.mainUltraDarkColor;
	}
}
