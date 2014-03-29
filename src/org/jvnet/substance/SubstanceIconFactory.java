package org.jvnet.substance;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.ButtonModel;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.plaf.SliderUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicSliderUI;

import org.jvnet.substance.color.ColorSchemeEnum;
import org.jvnet.substance.theme.SubstanceTheme;

/**
 * Icon factory for dynamically-changing icons.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceIconFactory {
	/**
	 * Icons for check box menu item in {@link SubstanceCheckBoxMenuItemUI}.
	 */
	private static Map<Integer, Icon> checkBoxMenuItemIcons = new HashMap<Integer, Icon>();

	/**
	 * Icons for radio button menu item in
	 * {@link SubstanceRadioButtonMenuItemUI}.
	 */
	private static Map<Integer, Icon> radioButtonMenuItemIcons = new HashMap<Integer, Icon>();;

	/**
	 * Icons for horizontal slider in {@link SubstanceSliderUI}.
	 */
	private static Map<String, Icon> sliderHorizontalIcons = new HashMap<String, Icon>();;

	/**
	 * Icons for vertical slider in {@link SubstanceSliderUI}.
	 */
	private static Map<String, Icon> sliderVerticalIcons = new HashMap<String, Icon>();;

	/**
	 * Resets image maps (used when setting new theme).
	 * 
	 * @see SubstanceLookAndFeel#setCurrentTheme(String)
	 * @see SubstanceLookAndFeel#setCurrentTheme(SubstanceTheme)
	 */
	static void reset() {
		checkBoxMenuItemIcons.clear();
		radioButtonMenuItemIcons.clear();
		sliderHorizontalIcons.clear();
		sliderVerticalIcons.clear();
	}

	/**
	 * Retrieves icon for check box menu item in
	 * {@link SubstanceCheckBoxMenuItemUI}.
	 * 
	 * @param size
	 *            The size of the icon to retrieve.
	 * @return Icon for check box menu item in
	 *         {@link SubstanceCheckBoxMenuItemUI}.
	 */
	public synchronized static Icon getCheckBoxMenuItemIcon(int size) {
		if (checkBoxMenuItemIcons.get(size) == null) {
			Icon icon = new CheckBoxMenuItemIcon(size);
			checkBoxMenuItemIcons.put(size, icon);
		}
		return checkBoxMenuItemIcons.get(size);
	}

	/**
	 * Retrieves icon for radio button menu item in
	 * {@link SubstanceRadioButtonMenuItemUI}.
	 * 
	 * @param size
	 *            The size of the icon to retrieve.
	 * @return Icon for radio button menu item in
	 *         {@link SubstanceRadioButtonMenuItemUI}.
	 */
	public static Icon getRadioButtonMenuItemIcon(int size) {
		if (radioButtonMenuItemIcons.get(size) == null) {
			Icon icon = new RadioButtonMenuItemIcon(size);
			radioButtonMenuItemIcons.put(size, icon);
		}
		return radioButtonMenuItemIcons.get(size);
	}

	/**
	 * Retrieves icon for horizontal slider in {@link SubstanceSliderUI}.
	 * 
	 * @param size
	 *            The size of the icon to retrieve.
	 * @return Icon for horizontal slider in {@link SubstanceSliderUI}.
	 */
	public static Icon getSliderHorizontalIcon(int size, boolean isMirrorred) {
		String key = size + ":" + isMirrorred;
		if (sliderHorizontalIcons.get(key) == null) {
			Icon icon = new SliderHorizontalIcon(size, isMirrorred);
			sliderHorizontalIcons.put(key, icon);
		}
		return sliderHorizontalIcons.get(key);
	}

	/**
	 * Retrieves icon for vertical slider in {@link SubstanceSliderUI}.
	 * 
	 * @param size
	 *            The size of the icon to retrieve.
	 * @return Icon for vertical slider in {@link SubstanceSliderUI}.
	 */
	public static Icon getSliderVerticalIcon(int size, boolean isMirrorred) {
		String key = size + ":" + isMirrorred;
		if (sliderVerticalIcons.get(key) == null) {
			Icon icon = new SliderVerticalIcon(size, isMirrorred);
			sliderVerticalIcons.put(key, icon);
		}
		return sliderVerticalIcons.get(key);
	}

	/**
	 * Icon for check box menu item in {@link SubstanceCheckBoxMenuItemUI}.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static class CheckBoxMenuItemIcon implements Icon, UIResource {
		/**
		 * Icon hash.
		 */
		private Map<ColorSchemeEnum, Map<ComponentState, Icon>> icons;

		/**
		 * The size of <code>this</code> icon.
		 */
		private int size;

		/**
		 * Simple constructor.
		 * 
		 * @param size
		 *            The size of <code>this</code> icon.
		 */
		public CheckBoxMenuItemIcon(int size) {
			this.icons = new HashMap<ColorSchemeEnum, Map<ComponentState, Icon>>();
			this.size = size;
		}

		/**
		 * Retrieves icon pack for the currently used theme.
		 * 
		 * @return Icons for the currently used theme.
		 */
		private synchronized Map<ComponentState, Icon> getCurrentThemeIcons() {
			ColorSchemeEnum currColorScheme = SubstanceLookAndFeel
					.getColorScheme();
			Map<ComponentState, Icon> currSchemeIcons = icons
					.get(currColorScheme);
			if (currSchemeIcons == null) {
				currSchemeIcons = new HashMap<ComponentState, Icon>();
				for (ComponentState state : ComponentState.values()) {
					currSchemeIcons.put(state, new ImageIcon(
							SubstanceImageCreator.getCheckBox(this.size + 2,
									state, currColorScheme)));
				}
				icons.put(currColorScheme, currSchemeIcons);
			}
			return currSchemeIcons;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#paintIcon(java.awt.Component,
		 *      java.awt.Graphics, int, int)
		 */
		public void paintIcon(Component c, Graphics g, int x, int y) {
			JMenuItem b = (JMenuItem) c;

			// get the icons that match the current color scheme
			Icon iconToDraw = this.getCurrentThemeIcons().get(
					ComponentState.getState(b.getModel(), b));

			g.translate(x, y);
			iconToDraw.paintIcon(c, g, 0, 0);
			g.translate(-x, -y);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#getIconWidth()
		 */
		public int getIconWidth() {
			return this.size + 2;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#getIconHeight()
		 */
		public int getIconHeight() {
			return this.size + 2;
		}
	}

	/**
	 * Icon for for radio button menu item in
	 * {@link SubstanceRadioButtonMenuItemUI}.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static class RadioButtonMenuItemIcon implements Icon, UIResource {
		/**
		 * Icon hash.
		 */
		private Map<ColorSchemeEnum, Map<ComponentState, Icon>> icons;

		/**
		 * The size of <code>this</code> icon.
		 */
		private int size;

		/**
		 * Simple constructor.
		 * 
		 * @param size
		 *            The size of <code>this</code> icon.
		 */
		public RadioButtonMenuItemIcon(int size) {
			icons = new HashMap<ColorSchemeEnum, Map<ComponentState, Icon>>();
			this.size = size;
		}

		/**
		 * Retrieves icon pack for the currently used theme.
		 * 
		 * @return Icons for the currently used theme.
		 */
		private synchronized Map<ComponentState, Icon> getCurrentThemeIcons() {
			ColorSchemeEnum currColorScheme = SubstanceLookAndFeel
					.getColorScheme();
			Map<ComponentState, Icon> currSchemeIcons = icons
					.get(currColorScheme);
			if (currSchemeIcons == null) {
				currSchemeIcons = new HashMap<ComponentState, Icon>();
				for (ComponentState state : ComponentState.values()) {
					currSchemeIcons.put(state, new ImageIcon(
							SubstanceImageCreator.getRadioButton(size, state,
									2, currColorScheme)));
				}
				icons.put(currColorScheme, currSchemeIcons);
			}
			return currSchemeIcons;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#paintIcon(java.awt.Component,
		 *      java.awt.Graphics, int, int)
		 */
		public void paintIcon(Component c, Graphics g, int x, int y) {
			JMenuItem b = (JMenuItem) c;

			Icon iconToDraw = this.getCurrentThemeIcons().get(
					ComponentState.getState(b.getModel(), b));

			g.translate(x, y);
			iconToDraw.paintIcon(c, g, 0, 0);
			g.translate(-x, -y);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#getIconWidth()
		 */
		public int getIconWidth() {
			return this.size + 2;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#getIconHeight()
		 */
		public int getIconHeight() {
			return this.size;
		}
	}

	/**
	 * Trackable slider (for sliders that do not use {@link SubstanceSliderUI}
	 * as their UI (such as
	 * {@link ch.randelshofer.quaqua.colorchooser.ColorSliderUI} from Quaqua).
	 * Uses reflection to implement the {@link Trackable} interface, fetching
	 * the value of {@link BasicSliderUI#thumbRect} field.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static class TrackableSlider implements Trackable {
		/**
		 * The associated slider.
		 */
		private JSlider slider;

		/**
		 * Reflection reference to {@link BasicSliderUI#thumbRect} field. If
		 * reflection failed, or no such field (for example the custom UI
		 * implements {@link SliderUI} directly, <code>this</code> field is
		 * <code>null</code>.
		 */
		private Field thumbRectField;

		/**
		 * Simple constructor.
		 * 
		 * @param slider
		 *            The associated slider.
		 */
		public TrackableSlider(JSlider slider) {
			this.slider = slider;

			SliderUI sliderUI = slider.getUI();
			if (sliderUI instanceof BasicSliderUI) {
				try {
					this.thumbRectField = BasicSliderUI.class
							.getDeclaredField("thumbRect");
					this.thumbRectField.setAccessible(true);
				} catch (Exception exc) {
					this.thumbRectField = null;
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jvnet.substance.Trackable#isInside(java.awt.event.MouseEvent)
		 */
		public boolean isInside(MouseEvent me) {
			try {
				Rectangle thumbB = (Rectangle) this.thumbRectField
						.get(this.slider.getUI());
				if (thumbB == null)
					return false;
				return thumbB.contains(me.getX(), me.getY());
			} catch (Exception exc) {
				return false;
			}
		}
	}

	/**
	 * Icon for horizontal slider in {@link SubstanceSliderUI}.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static class SliderHorizontalIcon implements Icon, UIResource {
		/**
		 * Icon hash.
		 */
		private Map<ColorSchemeEnum, Map<ComponentState, Icon>> icons;

		/**
		 * The size of <code>this</code> icon.
		 */
		private int size;

		private boolean isMirrorred;

		/**
		 * Contains models for the sliders whose UI is <b>not</b>
		 * {@link SubstanceSliderUI}. A model is used for rollover effects (as
		 * in {@link SubstanceSliderUI} and {@link SubstanceScrollBarUI}). Note
		 * that this is weak map (on keys) to allow disposing of unused keys.
		 */
		private WeakHashMap<JSlider, ButtonModel> models = new WeakHashMap<JSlider, ButtonModel>();

		/**
		 * Simple constructor.
		 * 
		 * @param size
		 *            The size of <code>this</code> icon.
		 */
		public SliderHorizontalIcon(int size, boolean isMirrorred) {
			icons = new HashMap<ColorSchemeEnum, Map<ComponentState, Icon>>();
			this.size = size;
			this.isMirrorred = isMirrorred;
		}

		/**
		 * Retrieves icon pack for the currently used theme.
		 * 
		 * @return Icons for the currently used theme.
		 */
		private synchronized Map<ComponentState, Icon> getCurrentThemeIcons() {
			ColorSchemeEnum currColorScheme = SubstanceLookAndFeel
					.getColorScheme();
			Map<ComponentState, Icon> currSchemeIcons = icons
					.get(currColorScheme);
			if (currSchemeIcons == null) {
				currSchemeIcons = new HashMap<ComponentState, Icon>();
				for (ComponentState state : ComponentState.values()) {
					BufferedImage stateImage = SubstanceImageCreator
							.getRoundedTriangleBackground(size, size - 1, 2,
									state, currColorScheme);
					if (this.isMirrorred)
						stateImage = SubstanceImageCreator.getRotated(
								stateImage, 2);
					currSchemeIcons.put(state, new ImageIcon(stateImage));
				}
				icons.put(currColorScheme, currSchemeIcons);
			}
			return currSchemeIcons;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#paintIcon(java.awt.Component,
		 *      java.awt.Graphics, int, int)
		 */
		public void paintIcon(Component c, Graphics g, int x, int y) {
			if (!(g instanceof Graphics2D)) {
				return;
			}

			JSlider slider = (JSlider) c;
			SliderUI sliderUI = slider.getUI();
			ComponentState state = ComponentState.ACTIVE;
			if (sliderUI instanceof SubstanceSliderUI) {
				state = ComponentState.getState(((SubstanceSliderUI) sliderUI)
						.getButtonModel(), null);
			} else {
				ButtonModel buttonModel = this.models.get(slider);
				if (buttonModel == null) {
					buttonModel = new DefaultButtonModel();
					RolloverControlListener listener = new RolloverControlListener(
							new TrackableSlider(slider), buttonModel);
					slider.addMouseListener(listener);
					slider.addMouseMotionListener(listener);
					this.models.put(slider, buttonModel);
				}
				state = ComponentState.getState(buttonModel, null);
			}
			Icon iconToDraw = this.getCurrentThemeIcons().get(state);

			// Icon iconToPaint = c.isEnabled() ? ENABLED : DISABLED;
			iconToDraw.paintIcon(c, g, x, y);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#getIconWidth()
		 */
		public int getIconWidth() {
			return this.size;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#getIconHeight()
		 */
		public int getIconHeight() {
			return this.size - 1;
		}
	}

	/**
	 * Icon for vertical slider in {@link SubstanceSliderUI}.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static class SliderVerticalIcon implements Icon, UIResource {
		/**
		 * Icon hash.
		 */
		private Map<ColorSchemeEnum, Map<ComponentState, Icon>> icons;

		/**
		 * The size of <code>this</code> icon.
		 */
		private int size;

		private boolean isMirrorred;

		/**
		 * Contains models for the sliders whose UI is <b>not</b>
		 * {@link SubstanceSliderUI}. A model is used for rollover effects (as
		 * in {@link SubstanceSliderUI} and {@link SubstanceScrollBarUI}). Note
		 * that this is weak map (on keys) to allow disposing of unused keys.
		 */
		private WeakHashMap<JSlider, ButtonModel> models = new WeakHashMap<JSlider, ButtonModel>();

		/**
		 * Simple constructor.
		 * 
		 * @param size
		 *            The size of <code>this</code> icon.
		 */
		public SliderVerticalIcon(int size, boolean isMirrorred) {
			icons = new HashMap<ColorSchemeEnum, Map<ComponentState, Icon>>();
			this.size = size;
			this.isMirrorred = isMirrorred;
		}

		/**
		 * Retrieves icon pack for the currently used theme.
		 * 
		 * @return Icons for the currently used theme.
		 */
		private synchronized Map<ComponentState, Icon> getCurrentThemeIcons() {
			ColorSchemeEnum currColorScheme = SubstanceLookAndFeel
					.getColorScheme();
			Map<ComponentState, Icon> currSchemeIcons = icons
					.get(currColorScheme);
			if (currSchemeIcons == null) {
				currSchemeIcons = new HashMap<ComponentState, Icon>();
				for (ComponentState state : ComponentState.values()) {
					BufferedImage stateImage = SubstanceImageCreator
							.getRoundedTriangleBackground(size - 1, size, 2,
									state, currColorScheme);
					if (this.isMirrorred)
						stateImage = SubstanceImageCreator.getRotated(
								stateImage, 1);
					else
						stateImage = SubstanceImageCreator.getRotated(
								stateImage, 3);
					currSchemeIcons.put(state, new ImageIcon(stateImage));
				}
				icons.put(currColorScheme, currSchemeIcons);
			}
			return currSchemeIcons;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#paintIcon(java.awt.Component,
		 *      java.awt.Graphics, int, int)
		 */
		public void paintIcon(Component c, Graphics g, int x, int y) {
			if (!(g instanceof Graphics2D)) {
				return;
			}
			JSlider slider = (JSlider) c;
			SliderUI sliderUI = slider.getUI();
			ComponentState state = ComponentState.ACTIVE;
			if (sliderUI instanceof SubstanceSliderUI) {
				state = ComponentState.getState(((SubstanceSliderUI) sliderUI)
						.getButtonModel(), null);
			} else {
				ButtonModel buttonModel = this.models.get(slider);
				if (buttonModel == null) {
					buttonModel = new DefaultButtonModel();
					RolloverControlListener listener = new RolloverControlListener(
							new TrackableSlider(slider), buttonModel);
					slider.addMouseListener(listener);
					slider.addMouseMotionListener(listener);
					this.models.put(slider, buttonModel);
				}
				state = ComponentState.getState(buttonModel, null);
			}
			Icon iconToDraw = this.getCurrentThemeIcons().get(state);
			iconToDraw.paintIcon(c, g, x, y);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#getIconWidth()
		 */
		public int getIconWidth() {
			return this.size - 1;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#getIconHeight()
		 */
		public int getIconHeight() {
			return this.size;
		}
	}

	public enum IconKind {
		CLOSE, MINIMIZE, MAXIMIZE, RESTORE;
	}

	private static final Map<IconKind, Map<ColorSchemeEnum, Icon>> icons = createIcons();

	private static Map<IconKind, Map<ColorSchemeEnum, Icon>> createIcons() {
		Map<IconKind, Map<ColorSchemeEnum, Icon>> result = new HashMap<IconKind, Map<ColorSchemeEnum, Icon>>();

		result.put(IconKind.CLOSE, new HashMap<ColorSchemeEnum, Icon>());
		result.put(IconKind.MINIMIZE, new HashMap<ColorSchemeEnum, Icon>());
		result.put(IconKind.MAXIMIZE, new HashMap<ColorSchemeEnum, Icon>());
		result.put(IconKind.RESTORE, new HashMap<ColorSchemeEnum, Icon>());
		for (ColorSchemeEnum colorSchemeEnum : ColorSchemeEnum.values()) {
			ColorSchemeEnum toTake = colorSchemeEnum.isDark() ? colorSchemeEnum
					: colorSchemeEnum.getMetallic();
			result.get(IconKind.CLOSE).put(colorSchemeEnum,
					SubstanceImageCreator.getCloseIcon(toTake));
			result.get(IconKind.MINIMIZE).put(colorSchemeEnum,
					SubstanceImageCreator.getMinimizeIcon(toTake));
			result.get(IconKind.MAXIMIZE).put(colorSchemeEnum,
					SubstanceImageCreator.getMaximizeIcon(toTake));
			result.get(IconKind.RESTORE).put(colorSchemeEnum,
					SubstanceImageCreator.getRestoreIcon(toTake));
		}

		return result;
	}

	/**
	 * Updates icons on the {@link ColorSchemeEnum#USER_DEFINED} enum.
	 */
	public static void updateIcons() {
		ColorSchemeEnum toTake = ColorSchemeEnum.USER_DEFINED.isDark() ? ColorSchemeEnum.USER_DEFINED
				: ColorSchemeEnum.USER_DEFINED.getMetallic();
		icons.get(IconKind.CLOSE).put(ColorSchemeEnum.USER_DEFINED,
				SubstanceImageCreator.getCloseIcon(toTake));
		icons.get(IconKind.MINIMIZE).put(ColorSchemeEnum.USER_DEFINED,
				SubstanceImageCreator.getMinimizeIcon(toTake));
		icons.get(IconKind.MAXIMIZE).put(ColorSchemeEnum.USER_DEFINED,
				SubstanceImageCreator.getMaximizeIcon(toTake));
		icons.get(IconKind.RESTORE).put(ColorSchemeEnum.USER_DEFINED,
				SubstanceImageCreator.getRestoreIcon(toTake));
	}

	public static Icon getTitlePaneIcon(IconKind iconKind,
			ColorSchemeEnum colorSchemeEnum) {
		return icons.get(iconKind).get(colorSchemeEnum);
	}

}