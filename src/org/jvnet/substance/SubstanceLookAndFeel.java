package org.jvnet.substance;

import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

import org.jvnet.substance.color.ColorSchemeEnum;
import org.jvnet.substance.theme.*;
import org.jvnet.substance.watermark.*;

/**
 * Main class for <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceLookAndFeel extends MetalLookAndFeel {

	/**
	 * Simple constructor.
	 */
	public SubstanceLookAndFeel() {
		// set theme
		String paramTheme = null;
		try {
			paramTheme = System.getProperty("substancelaf.theme");
		} catch (Exception exc) {
			// probably running in unsecure JNLP - ignore
		}
		boolean isSetTheme = false;
		if (paramTheme != null) {
			isSetTheme = setCurrentTheme(paramTheme);
		}
		if (!isSetTheme) {
			try {
				setCurrentTheme(new SubstanceAquaTheme());
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		// set watermark
		String paramWatermark = null;
		try {
			paramWatermark = System.getProperty("substancelaf.watermark");
		} catch (Exception exc) {
			// probably running in unsecure JNLP - ignore
		}
		boolean isSetWatermark = false;
		if (paramWatermark != null) {
			isSetWatermark = setCurrentWatermark(paramWatermark);
		}
		if (!isSetWatermark) {
			try {
				setCurrentWatermark(new SubstanceStripeWatermark());
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		try {
			if (System.getProperty("substancelaf.useDecorations") != null) {
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);
			}
		} catch (Exception exc) {
			// probably running in unsecure JNLP - ignore
		}
		
	}

	/**
	 * Returns the current color scheme.
	 * 
	 * @return Current color scheme.
	 */
	public static ColorSchemeEnum getColorScheme() {
		MetalTheme theme = MetalLookAndFeel.getCurrentTheme();
		if (!(theme instanceof SubstanceTheme)) {
			return ColorSchemeEnum.AQUA;
		} else {
			return ((SubstanceTheme) theme).getColorSchemeEnum();
		}
	}

	/**
	 * Sets new theme.
	 * 
	 * @param themeClassName
	 *            Theme class name (full qualified).
	 * @return The status of the theme change.
	 */
	public static boolean setCurrentTheme(String themeClassName) {
		try {
			Class<?> themeClass = Class.forName(themeClassName);
			if (themeClass == null) {
				return false;
			}
			Object obj = themeClass.newInstance();
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof SubstanceTheme)) {
				return false;
			}
			return setCurrentTheme((SubstanceTheme) obj);
		} catch (Exception exc) {
			return false;
		}
	}

	/**
	 * Sets new theme.
	 * 
	 * @param theme
	 *            Theme object.
	 * @return The status of the theme change.
	 */
	public static boolean setCurrentTheme(SubstanceTheme theme) {
		MetalLookAndFeel.setCurrentTheme(theme);

		SubstanceIconFactory.reset();
		SubstanceBackgroundDelegate.reset();
		SubstanceCheckBoxUI.reset();
		SubstanceGradientBackgroundDelegate.reset();
		SubstanceProgressBarUI.reset();
		SubstanceRadioButtonUI.reset();
		SubstanceScrollBarUI.reset();
		SubstanceTabbedPaneUI.reset();
		SubstanceMenuBarUI.reset();

		theme.addCustomEntriesToTable(UIManager.getLookAndFeelDefaults());

		if (currentWatermark != null) {
			if (currentWatermark.isDependingOnTheme())
				currentWatermark.updateWatermarkImage();
		}
		return true;
	}

	/**
	 * Returns the current color scheme name.
	 * 
	 * @return Current color scheme name.
	 */
	public static String getCurrentThemeName() {
		return getColorScheme().getDisplayName();
	}

	/**
	 * Returns all available color schemes.
	 * 
	 * @return All available color schemes. Key - scheme name, value - scheme
	 *         class name.
	 */
	public static Map<String, String> enumerateThemes() {
		Map<String, String> result = new TreeMap<String, String>();
		result.put(ColorSchemeEnum.AQUA.getDisplayName(),
				SubstanceAquaTheme.class.getName());
		result.put(ColorSchemeEnum.LIGHT_AQUA.getDisplayName(),
				SubstanceLightAquaTheme.class.getName());
		result.put(ColorSchemeEnum.LIME_GREEN.getDisplayName(),
				SubstanceLimeGreenTheme.class.getName());
		result.put(ColorSchemeEnum.BROWN.getDisplayName(),
				SubstanceBrownTheme.class.getName());
		result.put(ColorSchemeEnum.ORANGE.getDisplayName(),
				SubstanceOrangeTheme.class.getName());
		result.put(ColorSchemeEnum.PURPLE.getDisplayName(),
				SubstancePurpleTheme.class.getName());
		result.put(ColorSchemeEnum.SUN_GLARE.getDisplayName(),
				SubstanceSunGlareTheme.class.getName());
		result.put(ColorSchemeEnum.SUNSET.getDisplayName(),
				SubstanceSunsetTheme.class.getName());
		result.put(ColorSchemeEnum.OLIVE.getDisplayName(),
				SubstanceOliveTheme.class.getName());
		result.put(ColorSchemeEnum.TERRACOTTA.getDisplayName(),
				SubstanceTerracottaTheme.class.getName());
		result.put(ColorSchemeEnum.SEPIA.getDisplayName(),
				SubstanceSepiaTheme.class.getName());
		result.put(ColorSchemeEnum.STEEL_BLUE.getDisplayName(),
				SubstanceSteelBlueTheme.class.getName());
		result.put(ColorSchemeEnum.EBONY.getDisplayName(),
				SubstanceEbonyTheme.class.getName());
		result.put(ColorSchemeEnum.CHARCOAL.getDisplayName(),
				SubstanceCharcoalTheme.class.getName());
		result.put(ColorSchemeEnum.DARK_VIOLET.getDisplayName(),
				SubstanceDarkVioletTheme.class.getName());
		result.put(ColorSchemeEnum.BOTTLE_GREEN.getDisplayName(),
				SubstanceBottleGreenTheme.class.getName());
		result.put(ColorSchemeEnum.RASPBERRY.getDisplayName(),
				SubstanceRaspberryTheme.class.getName());
		result.put(ColorSchemeEnum.BARBY_PINK.getDisplayName(),
				SubstanceBarbyPinkTheme.class.getName());
		return result;
	}

	/**
	 * The current watermark.
	 */
	private static SubstanceWatermark currentWatermark = null;

	/**
	 * Returns the current watermark name.
	 * 
	 * @return Current watermark name.
	 */
	public static String getCurrentWatermarkName() {
		if (currentWatermark == null)
			return null;
		return currentWatermark.getDisplayName();
	}

	/**
	 * Returns all available watermarks.
	 * 
	 * @return All available watermarks. Key - watermark name, value - watermark
	 *         class name.
	 */
	public static Map<String, String> enumerateWatermarks() {
		Map<String, String> result = new TreeMap<String, String>();
		result.put(SubstanceStripeWatermark.getName(),
				SubstanceStripeWatermark.class.getName());
		result.put(SubstanceKatakanaWatermark.getName(),
				SubstanceKatakanaWatermark.class.getName());
		result.put(SubstanceBubblesWatermark.getName(),
				SubstanceBubblesWatermark.class.getName());
		result.put(SubstanceCrosshatchWatermark.getName(),
				SubstanceCrosshatchWatermark.class.getName());
		result.put(SubstanceBinaryWatermark.getName(),
				SubstanceBinaryWatermark.class.getName());
		result.put(SubstanceMosaicWatermark.getName(),
				SubstanceMosaicWatermark.class.getName());
		result.put(SubstanceMetalWallWatermark.getName(),
				SubstanceMetalWallWatermark.class.getName());
		result.put(SubstanceLatchWatermark.getName(),
				SubstanceLatchWatermark.class.getName());
		return result;
	}

	/**
	 * Returns the current watermark.
	 * 
	 * @return The current watermark.
	 */
	public static SubstanceWatermark getCurrentWatermark() {
		return SubstanceLookAndFeel.currentWatermark;
	}

	/**
	 * Sets new watermark.
	 * 
	 * @param watermarkClassName
	 *            Watermark class name (full qualified).
	 * @return The status of the watermark change.
	 */
	public static boolean setCurrentWatermark(String watermarkClassName) {
		try {
			Class<?> watermarkClass = Class.forName(watermarkClassName);
			if (watermarkClass == null) {
				return false;
			}
			Object obj = watermarkClass.newInstance();
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof SubstanceWatermark)) {
				return false;
			}
			return setCurrentWatermark((SubstanceWatermark) obj);
		} catch (Exception exc) {
			return false;
		}
	}

	/**
	 * Sets new watermark.
	 * 
	 * @param currentWatermark
	 *            Watermark object.
	 * @return The status of the watermark change.
	 */
	public static boolean setCurrentWatermark(
			SubstanceWatermark currentWatermark) {
		SubstanceLookAndFeel.currentWatermark = currentWatermark;
		return currentWatermark.updateWatermarkImage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.LookAndFeel#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Substance look and feel by Kirill Grouchnikov";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.LookAndFeel#getID()
	 */
	@Override
	public String getID() {
		return "Substance";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.LookAndFeel#getName()
	 */
	@Override
	public String getName() {
		return "Substance";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.LookAndFeel#isNativeLookAndFeel()
	 */
	@Override
	public boolean isNativeLookAndFeel() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.LookAndFeel#isSupportedLookAndFeel()
	 */
	@Override
	public boolean isSupportedLookAndFeel() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicLookAndFeel#initClassDefaults(javax.swing.UIDefaults)
	 */
	@Override
	protected void initClassDefaults(UIDefaults table) {
		super.initClassDefaults(table);

		String UI_CLASSNAME_PREFIX = "org.jvnet.substance.Substance";
		Object[] uiDefaults = {

		"ColorChooserUI", UI_CLASSNAME_PREFIX +"ColorChooserUI",

		"ButtonUI", UI_CLASSNAME_PREFIX + "ButtonUI",

		"ButtonStripUI", UI_CLASSNAME_PREFIX + "ButtonStripUI",

		"CheckBoxUI", UI_CLASSNAME_PREFIX + "CheckBoxUI",

		"ComboBoxUI", UI_CLASSNAME_PREFIX + "ComboBoxUI",

		"CheckBoxMenuItemUI", UI_CLASSNAME_PREFIX + "CheckBoxMenuItemUI",

		"DesktopIconUI", UI_CLASSNAME_PREFIX + "DesktopIconUI",

		"DesktopPaneUI", UI_CLASSNAME_PREFIX + "DesktopPaneUI",

		"EditorPaneUI", UI_CLASSNAME_PREFIX + "EditorPaneUI",

		"FileChooserUI", UI_CLASSNAME_PREFIX + "FileChooserUI",

		"FormattedTextFieldUI", UI_CLASSNAME_PREFIX + "FormattedTextFieldUI",

		"InternalFrameUI", UI_CLASSNAME_PREFIX + "InternalFrameUI",

		"ListUI", UI_CLASSNAME_PREFIX + "ListUI",

		"MenuUI", UI_CLASSNAME_PREFIX + "MenuUI",

		"MenuItemUI", UI_CLASSNAME_PREFIX + "MenuItemUI",

		"OptionPaneUI", UI_CLASSNAME_PREFIX + "OptionPaneUI",

		"PanelUI", UI_CLASSNAME_PREFIX + "PanelUI",

		"PasswordFieldUI", UI_CLASSNAME_PREFIX + "PasswordFieldUI",

		"ProgressBarUI", UI_CLASSNAME_PREFIX + "ProgressBarUI",

		"RadioButtonUI", UI_CLASSNAME_PREFIX + "RadioButtonUI",

		"RadioButtonMenuItemUI", UI_CLASSNAME_PREFIX + "RadioButtonMenuItemUI",

		"RootPaneUI", UI_CLASSNAME_PREFIX + "RootPaneUI",

		"ScrollBarUI", UI_CLASSNAME_PREFIX + "ScrollBarUI",

		"SliderUI", UI_CLASSNAME_PREFIX + "SliderUI",

		"SpinnerUI", UI_CLASSNAME_PREFIX + "SpinnerUI",

		"SplitPaneUI", UI_CLASSNAME_PREFIX + "SplitPaneUI",

		"TabbedPaneUI", UI_CLASSNAME_PREFIX + "TabbedPaneUI",

		"TableUI", UI_CLASSNAME_PREFIX + "TableUI",

		"TableHeaderUI", UI_CLASSNAME_PREFIX + "TableHeaderUI",

		"TextAreaUI", UI_CLASSNAME_PREFIX + "TextAreaUI",

		"TextFieldUI", UI_CLASSNAME_PREFIX + "TextFieldUI",

		"TextPaneUI", UI_CLASSNAME_PREFIX + "TextPaneUI",

		"ToggleButtonUI", UI_CLASSNAME_PREFIX + "ToggleButtonUI",

		"ToolTipUI", UI_CLASSNAME_PREFIX + "ToolTipUI",

		"ToolBarUI", UI_CLASSNAME_PREFIX + "ToolBarUI", };
		table.putDefaults(uiDefaults);
		// JFrame.setDefaultLookAndFeelDecorated(true);
		// JDialog.setDefaultLookAndFeelDecorated(true);
		// try {
		// System.setProperty("sun.awt.noerasebackground", "true");
		// } catch (SecurityException se) {
		// new PrivilegedExceptionAction() {
		// public Object run() {
		// try {
		// System.setProperty("sun.awt.noerasebackground", "true");
		// System.out.println("Property set");
		// return null;
		// } catch (SecurityException se) {
		// return se;
		// }
		// }
		// }.run();
		// }
	}

}
