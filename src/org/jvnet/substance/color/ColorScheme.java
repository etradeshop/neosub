package org.jvnet.substance.color;

import java.awt.Color;

/**
 * General interface for color schemes.
 * 
 * @author Kirill Grouchnikov
 */
public interface ColorScheme {
    /**
	 * Retrieves the foreground color.
	 *
	 * @return Foreground color.
	 */
    public Color getForegroundColor();

    /**
	 * Retrieves the ultra-light color.
	 * 
	 * @return Ultra-light color.
	 */
	public Color getUltraLightColor();

	/**
	 * Retrieves the extra color.
	 * 
	 * @return Extra color.
	 */
	public Color getExtraLightColor();

	/**
	 * Retrieves the light color.
	 * 
	 * @return Light color.
	 */
	public Color getLightColor();

	/**
	 * Retrieves the medium color.
	 * 
	 * @return Medium color.
	 */
	public Color getMidColor();

	/**
	 * Retrieves the dark color.
	 * 
	 * @return Dark color.
	 */
	public Color getDarkColor();

	/**
	 * Retrieves the ultra-dark color.
	 * 
	 * @return Ultra-dark color.
	 */
	public Color getUltraDarkColor();

}
