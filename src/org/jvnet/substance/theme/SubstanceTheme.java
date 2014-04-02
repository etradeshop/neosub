package org.jvnet.substance.theme;

import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.DefaultMetalTheme;

import org.jvnet.substance.*;
import org.jvnet.substance.color.ColorScheme;
import org.jvnet.substance.color.ColorSchemeEnum;


/**
 * Theme for <code>Substance</code> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceTheme extends DefaultMetalTheme {
	/**
	 * The first primary color.
	 */
	private ColorUIResource PRIMARY1;

	/**
	 * The second primary color.
	 */
	private ColorUIResource PRIMARY2;

	/**
	 * The third primary color.
	 */
	private ColorUIResource PRIMARY3;

	/**
	 * The first secondary color.
	 */
	private ColorUIResource SECONDARY1;

	/**
	 * The second secondary color.
	 */
	private ColorUIResource SECONDARY2;

	/**
	 * The third secondary color.
	 */
	private ColorUIResource SECONDARY3;

	/**
	 * The white color.
	 */
	private ColorUIResource WHITE;

	/**
	 * Color scheme enum for <code>this</code> theme.
	 */
	private ColorSchemeEnum colorSchemeEnum;

	

	/**
	 * Simple constructor. Made protected to prevent direct instantiation in
	 * client code.
	 * 
	 * @param colorSchemeEnum
	 *            Color scheme enum for <code>this</code> theme.
	 */
	protected SubstanceTheme(ColorSchemeEnum colorSchemeEnum) {
		this.colorSchemeEnum = colorSchemeEnum;
		this.PRIMARY1 = new ColorUIResource(this.colorSchemeEnum
				.getColorScheme().getMidColor());
		this.PRIMARY2 = new ColorUIResource(this.colorSchemeEnum
				.getColorScheme().getLightColor());
		this.PRIMARY3 = new ColorUIResource(this.colorSchemeEnum
				.getColorScheme().getExtraLightColor());

		this.SECONDARY1 = new ColorUIResource(this.colorSchemeEnum
				.getMetallic().getColorScheme().getMidColor());
		this.SECONDARY2 = new ColorUIResource(this.colorSchemeEnum
				.getMetallic().getColorScheme().getLightColor());
		this.SECONDARY3 = new ColorUIResource(this.colorSchemeEnum
				.getMetallic().getColorScheme().getExtraLightColor());

		this.WHITE = new ColorUIResource(this.colorSchemeEnum.getColorScheme()
				.getUltraLightColor().brighter());
	}

	/**
	 * Simple constructor accessible to user applications that wish to provide a
	 * custom color theme.
	 * 
	 * @param scheme
	 *            The color scheme object.
	 * @param isDark
	 *            Indication whether the color scheme is dark.
	 * @param displayName
	 *            The display name of the color scheme.
	 */
	public SubstanceTheme(ColorScheme scheme, boolean isDark, String displayName) {
		ColorSchemeEnum.setUserDefined(scheme, isDark, displayName);
		this.colorSchemeEnum = ColorSchemeEnum.USER_DEFINED;
		this.PRIMARY1 = new ColorUIResource(this.colorSchemeEnum
				.getColorScheme().getMidColor());
		this.PRIMARY2 = new ColorUIResource(this.colorSchemeEnum
				.getColorScheme().getLightColor());
		this.PRIMARY3 = new ColorUIResource(this.colorSchemeEnum
				.getColorScheme().getExtraLightColor());

		this.SECONDARY1 = new ColorUIResource(this.colorSchemeEnum
				.getMetallic().getColorScheme().getMidColor());
		this.SECONDARY2 = new ColorUIResource(this.colorSchemeEnum
				.getMetallic().getColorScheme().getLightColor());
		this.SECONDARY3 = new ColorUIResource(this.colorSchemeEnum
				.getMetallic().getColorScheme().getExtraLightColor());

		this.WHITE = new ColorUIResource(this.colorSchemeEnum.getColorScheme()
				.getUltraLightColor().brighter());

		SubstanceComboBoxUI.updateArrows();
		SubstanceIconFactory.updateIcons();
	}

	/**
	 * Returns the color scheme enum of the current theme.
	 * 
	 * @return Color scheme enum of the current theme.
	 */
	public ColorSchemeEnum getColorSchemeEnum() {
		return this.colorSchemeEnum;
	}

	/**
	 * Returns the name of the current theme.
	 * 
	 * @return The name of the current theme.
	 */
	public String getThemeName() {
		return this.colorSchemeEnum.name();
	}

	/**
	 * From Quaqua
	 */
	protected Icon createButtonStateIcon(final String location, final int states) {		
		return new ImageIcon(location);
	}

	/**
	 * From Quaqua
	 */
	protected Object makeButtonStateIcon(final String location, final int states) {
		return new UIDefaults.LazyValue() {
			public Object createValue(UIDefaults table) {
				return createButtonStateIcon(location, states);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.metal.MetalTheme#addCustomEntriesToTable(javax.swing.UIDefaults)
	 */
	@Override
	public void addCustomEntriesToTable(UIDefaults table) {
		// Apparently this function is called with null table
		// when the application is run with -Dswing.defaultlaf
		// setting. In this case, this function will be called
		// second time with correct table.
		if (table == null) {
			return;
		}

		super.addCustomEntriesToTable(table);
		// this.delegate.addCustomEntriesToTable(table);

		Icon menuArrowIcon = SubstanceImageCreator.getArrowIcon(7, 5,
				SwingConstants.EAST, SubstanceLookAndFeel.getColorScheme());

		Object listCellRendererActiveValue = new UIDefaults.ActiveValue() {
			public Object createValue(UIDefaults table) {
				return new SubstanceDefaultListCellRenderer.SubstanceUIResource();
			}
		};

		InsetsUIResource visualMargin = new InsetsUIResource(0, 0, 0, 0);

		Color controlText = this.colorSchemeEnum.getColorScheme()
				.getLightColor();
		Color foregroundColor = new ColorUIResource(this.colorSchemeEnum
				.getColorScheme().getForegroundColor());
		// System.out.println("Foreground: " + foregroundColor);
		Color backgroundColor = new ColorUIResource(this.colorSchemeEnum
				.isDark() ? this.colorSchemeEnum
		// /*.getColorScheme()*/.getMetallic().getColorScheme().getLightColor()//.brighter()
				.getColorScheme().getDarkColor().brighter()
				: this.colorSchemeEnum.getMetallic().getColorScheme()
						.getExtraLightColor());
		Color backgroundLightColor = new ColorUIResource(backgroundColor
				.brighter());

		// System.out.println("Background: " + backgroundColor);
		Color disabledForegroundColor = new ColorUIResource(
				this.colorSchemeEnum.isDark() ? this.colorSchemeEnum
						.getMetallic().getColorScheme().getForegroundColor()
						.darker() : this.colorSchemeEnum.getMetallic()
						.getColorScheme().getDarkColor());

		Color lineColor = new ColorUIResource(
				this.colorSchemeEnum.isDark() ? this.colorSchemeEnum
						.getColorScheme().getUltraLightColor()
						: this.colorSchemeEnum.getColorScheme().getMidColor());

		Color selectionBackgroundColor = new ColorUIResource(
				this.colorSchemeEnum.isDark() ? this.colorSchemeEnum
						.getColorScheme().getUltraLightColor().brighter()
						.brighter() : this.colorSchemeEnum.getColorScheme()
						.getExtraLightColor());

		Color selectionForegroundColor = new ColorUIResource(
				this.colorSchemeEnum.isDark() ? this.colorSchemeEnum
						.getColorScheme().getDarkColor() : this.colorSchemeEnum
						.getColorScheme().getUltraDarkColor());

		Border textBorder = new BorderUIResource(new SubstanceBorder());

		Object[] defaults = new Object[] {
				

			

				// Note: The following colors are used in color lists.
				// It is important that these colors are neutral (black, white
				// or a shade of gray with saturation 0).
				// If they aren't neutral, human perception of the color
				// is negatively affected.
				"ColorChooser.listSelectionBackground",
				new ColorUIResource(0xd4d4d4),

				"ColorChooser.listSelectionForeground",
				new ColorUIResource(0x000000),

				

				"control",
				controlText,

				"MenuBarUI",
				"org.jvnet.substance.SubstanceMenuBarUI",

				"Button.disabledText",
				disabledForegroundColor,

				"Button.foreground",
				foregroundColor,

				"CheckBox.background",
				backgroundColor,

				"CheckBox.disabledText",
				disabledForegroundColor,

				"CheckBox.foreground",
				foregroundColor,

				"CheckBoxMenuItem.acceleratorForeground",
				foregroundColor,

				"CheckBoxMenuItem.acceleratorSelectionForeground",
				foregroundColor,

				"CheckBoxMenuItem.background",
				backgroundColor,

				"CheckBoxMenuItem.borderPainted",
				Boolean.FALSE,

				"CheckBoxMenuItem.checkIcon",
				new IconUIResource(SubstanceIconFactory
						.getCheckBoxMenuItemIcon(10)),

				"CheckBoxMenuItem.disabledForeground",
				disabledForegroundColor,

				"CheckBoxMenuItem.foreground",
				foregroundColor,

				"CheckBoxMenuItem.selectionForeground",
				foregroundColor,

				"ColorChooser.background",
				backgroundColor,

				"ColorChooser.foreground",
				foregroundColor,

				"ComboBox.background",
				backgroundLightColor,

				"ComboBox.foreground",
				foregroundColor,

				"ComboBox.selectionForeground",
				foregroundColor,

				"ComboBox.disabledBackground",
				backgroundLightColor,

				"ComboBox.disabledForeground",
				disabledForegroundColor,

				"DesktopIcon.border",
				new SubstanceBorder(),

				"DesktopIcon.width",
				new Integer(140),

				"Desktop.background",
				backgroundColor,

				"Desktop.foreground",
				foregroundColor,

				"Dialog.background",
				backgroundColor,

				"EditorPane.background",
				backgroundLightColor,

				"EditorPane.border",
				textBorder,

				"EditorPane.foreground",
				foregroundColor,

				"EditorPane.caretForeground",
				foregroundColor,

				"EditorPane.inactiveBackground",
				backgroundLightColor,

				"EditorPane.inactiveForeground",
				disabledForegroundColor,

				"EditorPane.selectionBackground",
				selectionBackgroundColor,

				"EditorPane.selectionForeground",
				selectionForegroundColor,

				"FileChooser.upFolderIcon",
				new ImageIcon(SubstanceImageCreator
						.getTreeNodeIcon(SubstanceImageCreator.TreeIcon.UP)),

				"FileChooser.newFolderIcon",
				new ImageIcon(SubstanceImageCreator
						.getTreeNodeIcon(SubstanceImageCreator.TreeIcon.NONE)),

				"FileChooser.homeFolderIcon",
				new ImageIcon(SubstanceImageCreator.getHomeIcon()),

				"FileView.computerIcon",
				new ImageIcon(SubstanceImageCreator.getComputerIcon()),

				"FileView.directoryIcon",
				new ImageIcon(SubstanceImageCreator
						.getTreeNodeIcon(SubstanceImageCreator.TreeIcon.CLOSED)),

				"FileView.fileIcon",
				new ImageIcon(SubstanceImageCreator.getTreeLeafIcon()),

				"FileView.floppyDriveIcon",
				new ImageIcon(SubstanceImageCreator.getFloppyIcon()),

				"FileView.hardDriveIcon",
				new ImageIcon(SubstanceImageCreator.getDiskIcon()),

				"FormattedTextField.background",
				backgroundLightColor,

				"FormattedTextField.border",
				textBorder,

				"FormattedTextField.caretForeground",
				foregroundColor,

				"FormattedTextField.foreground",
				foregroundColor,

				"FormattedTextField.inactiveBackground",
				backgroundLightColor,

				"FormattedTextField.inactiveForeground",
				disabledForegroundColor,

				"FormattedTextField.selectionBackground",
				selectionBackgroundColor,

				"FormattedTextField.selectionForeground",
				selectionForegroundColor,

				"InternalFrame.border",
				new SubstancePaneBorder(),

				"InternalFrame.closeIcon",
				SubstanceImageCreator.getCloseIcon(this.colorSchemeEnum),

				"InternalFrame.iconifyIcon",
				SubstanceImageCreator.getMinimizeIcon(this.colorSchemeEnum),

				// maximize2
				"InternalFrame.maximizeIcon",
				SubstanceImageCreator.getMaximizeIcon(this.colorSchemeEnum),

				// restore
				"InternalFrame.minimizeIcon",
				SubstanceImageCreator.getRestoreIcon(this.colorSchemeEnum),

				"InternalFrame.paletteCloseIcon",
				SubstanceImageCreator.getCloseIcon(this.colorSchemeEnum),

				"Label.background",
				backgroundColor,

				"Label.foreground",
				foregroundColor,

				"Label.disabledForeground",
				disabledForegroundColor,

				"List.background",
				backgroundColor,

				"List.cellRenderer",
				listCellRendererActiveValue,

				"List.focusCellHighlightBorder",
				new SubstanceBorder(),

				"List.foreground",
				foregroundColor,

				"List.selectionBackground",
				new ColorUIResource(this.colorSchemeEnum.getColorScheme()
						.getLightColor()),

				"List.selectionForeground",
				foregroundColor,

				"Menu.arrowIcon",
				menuArrowIcon,

				"Menu.background",
				backgroundColor,

				"Menu.borderPainted",
				Boolean.FALSE,

				"Menu.disabledForeground",
				disabledForegroundColor,

				"Menu.foreground",
				foregroundColor,

				"Menu.selectionForeground",
				foregroundColor,

				"MenuBar.background",
				backgroundColor,

				"MenuItem.acceleratorForeground",
				foregroundColor,

				"MenuItem.acceleratorSelectionForeground",
				foregroundColor,

				"MenuItem.background",
				backgroundColor,

				"MenuItem.borderPainted",
				Boolean.FALSE,

				"MenuItem.disabledForeground",
				disabledForegroundColor,

				"MenuItem.foreground",
				foregroundColor,

				"MenuItem.selectionForeground",
				foregroundColor,

				"OptionPane.background",
				backgroundColor,

				"OptionPane.errorIcon",
				SubstanceImageCreator.getErrorMarkerIcon(31, colorSchemeEnum),

				"OptionPane.foreground",
				foregroundColor,

				"OptionPane.informationIcon",
				SubstanceImageCreator.getInfoMarkerIcon(32, colorSchemeEnum),

				"OptionPane.messageForeground",
				foregroundColor,

				"OptionPane.questionIcon",
				SubstanceImageCreator
						.getQuestionMarkerIcon(31, colorSchemeEnum),

				"OptionPane.warningIcon",
				SubstanceImageCreator.getWarningMarkerIcon(32, colorSchemeEnum),

				"Panel.background",
				backgroundColor,

				"Panel.foreground",
				foregroundColor,

				"PasswordField.background",
				backgroundLightColor,

				"PasswordField.border",
				textBorder,

				"PasswordField.caretForeground",
				foregroundColor,

				"PasswordField.foreground",
				foregroundColor,

				"PasswordField.inactiveBackground",
				backgroundLightColor,

				"PasswordField.inactiveForeground",
				disabledForegroundColor,

				"PasswordField.selectionBackground",
				selectionBackgroundColor,

				"PasswordField.selectionForeground",
				selectionForegroundColor,

				"PopupMenu.background",
				backgroundColor,

				"PopupMenu.border",
				new SubstanceBorder(),

				"ProgressBar.border",
				textBorder,

				"RadioButton.background",
				backgroundColor,

				"RadioButton.foreground",
				foregroundColor,

				"RadioButton.disabledText",
				disabledForegroundColor,

				"RadioButtonMenuItem.acceleratorForeground",
				foregroundColor,

				"RadioButtonMenuItem.acceleratorSelectionForeground",
				foregroundColor,

				"RadioButtonMenuItem.background",
				backgroundColor,

				"RadioButtonMenuItem.borderPainted",
				Boolean.FALSE,

				"RadioButtonMenuItem.checkIcon",
				new IconUIResource(SubstanceIconFactory
						.getRadioButtonMenuItemIcon(10)),

				"RadioButtonMenuItem.disabledForeground",
				disabledForegroundColor,

				"RadioButtonMenuItem.foreground",
				foregroundColor,

				"RadioButtonMenuItem.selectionForeground",
				foregroundColor,

				"RootPane.background",
				backgroundColor,

				"RootPane.border",
				new SubstancePaneBorder(),

				"ScrollPane.background",
				backgroundColor,

				"ScrollPane.foreground",
				foregroundColor,

				"ScrollPane.border",
				new BorderUIResource(new SubstanceBorder()),

				"Separator.background",
				backgroundColor,

				"Separator.foreground",
				lineColor,

				"Slider.altTrackColor",
				lineColor,

				"Slider.background",
				backgroundColor,

				"Slider.darkShadow",
				lineColor,

				"Slider.focus",
				lineColor,

				"Slider.foreground",
				lineColor,

				"Slider.highlight",
				backgroundLightColor,

				"Slider.horizontalThumbIcon",
				SubstanceIconFactory.getSliderHorizontalIcon(16, false),

				"Slider.shadow",
				lineColor,

				"Slider.tickColor",
				foregroundColor,

				"Slider.verticalThumbIcon",
				SubstanceIconFactory.getSliderVerticalIcon(16, false),

				"Spinner.border",
				new SubstanceBorder(),

				"Spinner.editorBorderPainted",
				Boolean.FALSE,

				"SplitPane.background",
				backgroundColor,

				"SplitPane.foreground",
				foregroundColor,

				"SplitPane.dividerFocusColor",
				backgroundColor,

				"SplitPaneDivider.draggingColor",
				backgroundColor,

				"TabbedPane.tabAreaBackground",
				backgroundColor,

				"TabbedPane.unselectedBackground",
				backgroundColor,

				"TabbedPane.background",
				backgroundColor,

				"TabbedPane.borderHightlightColor",
				new ColorUIResource(this.colorSchemeEnum.getColorScheme()
						.getMidColor()),

				"TabbedPane.contentAreaColor",
				new ColorUIResource(this.colorSchemeEnum.getColorScheme()
						.getUltraLightColor()),

				"TabbedPane.darkShadow",
				new ColorUIResource(this.colorSchemeEnum.getColorScheme()
						.getLightColor()),

				"TabbedPane.focus",
				foregroundColor,

				"TabbedPane.foreground",
				foregroundColor,

				"TabbedPane.highlight",
				new ColorUIResource(this.colorSchemeEnum.getColorScheme()
						.getLightColor()),

				"TabbedPane.light",
				new ColorUIResource(this.colorSchemeEnum.getColorScheme()
						.getLightColor()),

				"TabbedPane.selected",
				new ColorUIResource(this.colorSchemeEnum.getColorScheme()
						.getExtraLightColor()),

				"TabbedPane.selectHighlight",
				new ColorUIResource(this.colorSchemeEnum.getColorScheme()
						.getMidColor()),

				"TabbedPane.shadow",
				new ColorUIResource(this.colorSchemeEnum.getColorScheme()
						.getExtraLightColor()),

				"Table.background",
				backgroundLightColor,

				"Table.focusCellBackground",
				selectionBackgroundColor,

				"Table.focusCellForeground",
				foregroundColor,

				"Table.focusCellHighlightBorder",
				new BorderUIResource(new LineBorder(foregroundColor)),

				"Table.foreground",
				foregroundColor,

				"Table.gridColor",
				lineColor,

				"Table.scrollPaneBorder",
				new BorderUIResource(new SubstanceBorder()),

				"Table.selectionBackground",
				selectionBackgroundColor,

				"Table.selectionForeground",
				selectionForegroundColor,

				"TableHeader.foreground",
				foregroundColor,

				"TextArea.background",
				backgroundLightColor,

				"TextArea.border",
				textBorder,

				"TextArea.caretForeground",
				foregroundColor,

				"TextArea.foreground",
				foregroundColor,

				"TextArea.inactiveBackground",
				backgroundLightColor,

				"TextArea.inactiveForeground",
				disabledForegroundColor,

				"TextArea.selectionBackground",
				selectionBackgroundColor,

				"TextArea.selectionForeground",
				selectionForegroundColor,

				"TextField.background",
				backgroundLightColor,

				"TextField.border",
				textBorder,

				"TextField.caretForeground",
				foregroundColor,

				"TextField.foreground",
				foregroundColor,

				"TextField.inactiveBackground",
				backgroundLightColor,

				"TextField.inactiveForeground",
				disabledForegroundColor,

				"TextField.selectionBackground",
				selectionBackgroundColor,

				"TextField.selectionForeground",
				selectionForegroundColor,

				"TextPane.background",
				backgroundLightColor,

				"TextPane.border",
				textBorder,

				"TextPane.foreground",
				foregroundColor,

				"TextPane.caretForeground",
				foregroundColor,

				"TextPane.inactiveBackground",
				backgroundLightColor,

				"TextPane.inactiveForeground",
				disabledForegroundColor,

				"TextPane.selectionBackground",
				selectionBackgroundColor,

				"TextPane.selectionForeground",
				selectionForegroundColor,

				"TitledBorder.titleColor",
				foregroundColor,

				"ToolTip.foreground",
				foregroundColor,

				"Tree.background",
				backgroundColor,

				"Tree.selectionBackground",
				backgroundLightColor,

				"Tree.foreground",
				foregroundColor,

				"Tree.hash",
				lineColor,

				"Tree.selectionBorderColor",
				lineColor,

				"Tree.selectionForeground",
				foregroundColor,

				"Tree.textBackground",
				backgroundColor,

				"Tree.textForeground",
				foregroundColor,

				"TableHeader.cellBorder",
				null,

				"ToggleButton.foreground",
				foregroundColor,

				"ToggleButton.disabledText",
				disabledForegroundColor,

				"ToolBar.foreground",
				foregroundColor,

				"ToolTip.border",
				new LineBorder(foregroundColor),

				"ToolTip.borderInactive",
				textBorder,

				"ToolTip.backgroundInactive",
				new ColorUIResource(this.colorSchemeEnum.getMetallic()
						.getColorScheme().getExtraLightColor()),

				"ToolTip.foregroundInactive",
				disabledForegroundColor,

				"ToolBar.border",
				new SubstanceToolBarBorder(),

				"ToolBar.background",
				backgroundColor,

				"Tree.closedIcon",
				new IconUIResource(
						new ImageIcon(
								SubstanceImageCreator
										.getTreeNodeIcon(SubstanceImageCreator.TreeIcon.CLOSED))),

				"Tree.collapsedIcon",
				new IconUIResource(new ImageIcon(SubstanceImageCreator
						.getTreeIcon(this.colorSchemeEnum, true))),

				"Tree.expandedIcon",
				new IconUIResource(new ImageIcon(SubstanceImageCreator
						.getTreeIcon(this.colorSchemeEnum, false))),

				"Tree.leafIcon",
				new IconUIResource(new ImageIcon(SubstanceImageCreator
						.getTreeLeafIcon())),

				"Tree.openIcon",
				new IconUIResource(
						new ImageIcon(
								SubstanceImageCreator
										.getTreeNodeIcon(SubstanceImageCreator.TreeIcon.OPENED))),

				"TabbedPane.background", backgroundColor,

				"Viewport.background", backgroundColor,

				"Viewport.foreground", foregroundColor,

		};
		table.putDefaults(defaults);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.metal.MetalTheme#getPrimary1()
	 */
	protected ColorUIResource getPrimary1() {
		return this.PRIMARY1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.metal.MetalTheme#getPrimary2()
	 */
	protected ColorUIResource getPrimary2() {
		return this.PRIMARY2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.metal.MetalTheme#getPrimary3()
	 */
	protected ColorUIResource getPrimary3() {
		return this.PRIMARY3;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.metal.MetalTheme#getSecondary1()
	 */
	protected ColorUIResource getSecondary1() {
		return this.SECONDARY1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.metal.MetalTheme#getSecondary2()
	 */
	protected ColorUIResource getSecondary2() {
		return this.SECONDARY2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.metal.MetalTheme#getSecondary3()
	 */
	protected ColorUIResource getSecondary3() {
		return this.SECONDARY3;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.metal.MetalTheme#getWhite()
	 */
	protected ColorUIResource getWhite() {
		return this.WHITE;
	}
}
