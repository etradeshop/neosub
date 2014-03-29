package org.jvnet.substance.color;

/**
 * Enum for working with color schemes.
 * 
 * @author Kirill Grouchnikov
 */
public enum ColorSchemeEnum {
	/**
	 * Value for {@link LightGrayColorScheme}.
	 */
	LIGHT_GRAY(new LightGrayColorScheme(), false, "Light gray"),

	/**
	 * Value for {@link DarkGrayColorScheme}.
	 */
	DARK_GRAY(new DarkGrayColorScheme(), true, "Dark gray"),

	/**
	 * Value for {@link MetallicColorScheme}.
	 */
	METALLIC(new MetallicColorScheme(), false, "Metallic"),

	/**
	 * Value for {@link DarkMetallicColorScheme}.
	 */
	DARK_METALLIC(new DarkMetallicColorScheme(), true, "Dark metallic"),

	/**
	 * Value for {@link AquaColorScheme}.
	 */
	AQUA(new AquaColorScheme(), false, "Aqua"),

	/**
	 * Value for {@link LightAquaColorScheme}.
	 */
	LIGHT_AQUA(new LightAquaColorScheme(), false, "Light aqua"),

	/**
	 * Value for {@link OrangeColorScheme}.
	 */
	ORANGE(new OrangeColorScheme(), false, "Orange"),

	/**
	 * Value for {@link PurpleColorScheme}.
	 */
	PURPLE(new PurpleColorScheme(), false, "Purple"),

	/**
	 * Value for {@link LimeGreenColorScheme}.
	 */
	LIME_GREEN(new LimeGreenColorScheme(), false, "Lime green"),

	/**
	 * Value for {@link SunGlareColorScheme}.
	 */
	SUN_GLARE(new SunGlareColorScheme(), false, "Sun glare"),

	/**
	 * Value for {@link SunsetColorScheme}.
	 */
	SUNSET(new SunsetColorScheme(), false, "Sunset"),

	/**
	 * Value for {@link OliveColorScheme}.
	 */
	OLIVE(new OliveColorScheme(), false, "Olive"),

	/**
	 * Value for {@link TerracottaColorScheme}.
	 */
	TERRACOTTA(new TerracottaColorScheme(), false, "Terracotta"),

	/**
	 * Value for {@link SepiaColorScheme}.
	 */
	SEPIA(new SepiaColorScheme(), false, "Sepia"),

	/**
	 * Value for {@link SteelBlueColorScheme}.
	 */
	STEEL_BLUE(new SteelBlueColorScheme(), false, "Steel blue"),

	/**
	 * Value for {@link EbonyColorScheme}.
	 */
	EBONY(new EbonyColorScheme(), true, "Ebony"),

	/**
	 * Value for {@link CharcoalColorScheme}.
	 */
	CHARCOAL(new CharcoalColorScheme(), true, "Charcoal"),

	/**
	 * Value for {@link DarkVioletColorScheme}.
	 */
	DARK_VIOLET(new DarkVioletColorScheme(), true, "Dark violet"),

	/**
	 * Value for {@link BrownColorScheme}.
	 */
	BROWN(new BrownColorScheme(), false, "Brown"),

	/**
	 * Value for {@link BottleGreenColorScheme}.
	 */
	BOTTLE_GREEN(new BottleGreenColorScheme(), false, "Bottle green"),

	/**
	 * Value for {@link RaspberryColorScheme}.
	 */
	RASPBERRY(new RaspberryColorScheme(), false, "Raspberry"),

	/**
	 * Value for {@link RaspberryColorScheme}.
	 */
	BARBY_PINK(new BarbyPinkColorScheme(), false, "Barby pink"),

	/**
	 * Value for user-defined current scheme.
	 */
	USER_DEFINED(new AquaColorScheme(), false, "User defined");

	/**
	 * Corresponding color scheme.
	 */
	private ColorScheme colorScheme;

	private boolean isDark;

	private String displayName;

	/**
	 * Simple constructor.
	 * 
	 * @param colorScheme
	 *            corresponding color scheme.
	 */
	ColorSchemeEnum(ColorScheme colorScheme, boolean isDark, String displayName) {
		this.colorScheme = colorScheme;
		this.isDark = isDark;
		this.displayName = displayName;
	}

	/**
	 * Retrieves the corresponding color scheme.
	 * 
	 * @return Corresponding color scheme.
	 */
	public ColorScheme getColorScheme() {
		return colorScheme;
	}

	public boolean isDark() {
		return isDark;
	}

	public ColorSchemeEnum getMetallic() {
		if (this.isDark())
			return ColorSchemeEnum.DARK_METALLIC;
		else
			return ColorSchemeEnum.METALLIC;
	}

	public ColorSchemeEnum getGray() {
		if (this.isDark())
			return ColorSchemeEnum.DARK_GRAY;
		else
			return ColorSchemeEnum.LIGHT_GRAY;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * Sets parameters for the {@link #USER_DEFINED} enum.
	 * 
	 * @param scheme
	 *            Color scheme object.
	 * @param isDark
	 *            Indication whether the specified color scheme is dark.
	 * @param displayName
	 *            Display name for the specified color scheme.
	 */
	public static void setUserDefined(ColorScheme scheme, boolean isDark,
			String displayName) {
		USER_DEFINED.colorScheme = scheme;
		USER_DEFINED.isDark = isDark;
		USER_DEFINED.displayName = displayName;
	}
}
