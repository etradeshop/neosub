package test;

import java.awt.Color;

import org.jvnet.substance.color.ColorScheme;

/**
 * <b>Caribbean blue</b> color scheme - for close buttons.
 * 
 * @author Kirill Grouchnikov
 */
public class CaribbeanBlueColorScheme implements ColorScheme {
	/**
	 * The main ultra-light color.
	 */
	private static final Color mainUltraLightColor = new Color(131, 166, 202);

	/**
	 * The main extra-light color.
	 */
	private static final Color mainExtraLightColor = new Color(114, 155, 198);

	/**
	 * The main light color.
	 */
	private static final Color mainLightColor = new Color(94, 150, 195);

	/**
	 * The main medium color.
	 */
	private static final Color mainMidColor = new Color(53, 121, 176);

	/**
	 * The main dark color.
	 */
	private static final Color mainDarkColor = new Color(61, 112, 161);

    /**
     * The main ultra-dark color.
     */
    private static final Color mainUltraDarkColor = new Color(30, 75, 101);

    /**
     * The foreground color.
     */
    private static final Color foregroundColor = Color.black;

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getForegroundColor()
    */
    public Color getForegroundColor() {
        return CaribbeanBlueColorScheme.foregroundColor;
    }

    /* (non-Javadoc)
    * @see org.jvnet.substance.color.ColorScheme#getUltraLightColor()
    */
	public Color getUltraLightColor() {
		return CaribbeanBlueColorScheme.mainUltraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getExtraLightColor()
	 */
	public Color getExtraLightColor() {
		return CaribbeanBlueColorScheme.mainExtraLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getLightColor()
	 */
	public Color getLightColor() {
		return CaribbeanBlueColorScheme.mainLightColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getMidColor()
	 */
	public Color getMidColor() {
		return CaribbeanBlueColorScheme.mainMidColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getDarkColor()
	 */
	public Color getDarkColor() {
		return CaribbeanBlueColorScheme.mainDarkColor;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.color.ColorScheme#getUltraDarkColor()
	 */
	public Color getUltraDarkColor() {
		return CaribbeanBlueColorScheme.mainUltraDarkColor;
	}
}
