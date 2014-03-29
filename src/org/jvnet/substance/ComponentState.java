package org.jvnet.substance;

import javax.swing.*;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;

/**
 * This enum is used in order to provide uniform transition effects on mouse
 * events. The effects include different visual appearance of the corresponding
 * control when the mouse hovers over it (rollover), when it's pressed or
 * selected, disabled etc.<br>
 * Each enum value represents a single state and contains information that is
 * used by the UI delegates in order to correctly paint the corresponding
 * controls.
 * 
 * @author Kirill Grouchnikov
 */
public enum ComponentState {
	/**
	 * Disabled active. Used for disabled buttons that have been marked as
	 * <code>default</code>.
	 */
	DISABLED_ACTIVE(ColorSchemeKind.DISABLED, 0, false, false),

	/**
	 * Active. Used for enabled buttons that have been marked as
	 * <code>default</code>.
	 */
	ACTIVE(ColorSchemeKind.CURRENT, 0, false, true),

	/**
	 * Disabled selected.
	 */
	DISABLED_SELECTED(ColorSchemeKind.DISABLED, 10, true, false),

	/**
	 * Disabled and not selected.
	 */
	DISABLED_UNSELECTED(ColorSchemeKind.DISABLED, 0, false, false),

	/**
	 * Pressed selected.
	 */
	PRESSED_SELECTED(ColorSchemeKind.CURRENT, 4, true, true),

	/**
	 * Pressed and not selected.
	 */
	PRESSED_UNSELECTED(ColorSchemeKind.CURRENT, 8, false, true),

	/**
	 * Selected.
	 */
	SELECTED(ColorSchemeKind.CURRENT, 8, true, true),

	/**
	 * Selected and rolled over.
	 */
	ROLLOVER_SELECTED(ColorSchemeKind.CURRENT, 4, true, true),

	/**
	 * Not selected and rolled over.
	 */
	ROLLOVER_UNSELECTED(ColorSchemeKind.CURRENT, 0, false, true),

	/**
	 * Default state.
	 */
	DEFAULT(ColorSchemeKind.REGULAR, 0, false, true);

	/**
	 * Enum for color scheme kind. Is used in order to decouple the actual
	 * current color scheme and the decision on whether to use it.
	 * 
	 * @author Kirill Grouchnikov
	 */
	public static enum ColorSchemeKind {
		/**
		 * Current color scheme (e.g. {@link ColorSchemeEnum#AQUA}).
		 */
		CURRENT,

		/**
		 * Regular color scheme (usually {@link ColorSchemeEnum#METALLIC}).
		 */
		REGULAR,

		/**
		 * Disabled color scheme (usually {@link ColorSchemeEnum#LIGHT_GRAY}).
		 */
		DISABLED
	};

	/**
	 * The corresponding color scheme kind.
	 */
	private ColorSchemeKind colorSchemeKind;

	/**
	 * The corresponding cycle count. Should be a number between 0 and 10. This
	 * number is used to compute the foreground color of some component. The
	 * color is interpolated between two values (0 corresponds to usual color,
	 * 10 corresponds to very light version of the usual color).
	 */
	private int cycleCount;

	/**
	 * Indicates whether a component with <code>this</code> state is selected.
	 */
	private boolean isSelected;

	/**
	 * Indicates whether a component with <code>this</code> state is enabled.
	 */
	private boolean isEnabled;

	/**
	 * Simple constructor.
	 * 
	 * @param kind
	 *            The corresponding color scheme kind.
	 * @param count
	 *            The corresponding cycle count.
	 * @param isSelected
	 *            Indicates whether a component with <code>this</code> state
	 *            is selected.
	 * @param isEnabled
	 *            Indicates whether a component with <code>this</code> state
	 *            is enabled.
	 */
	ComponentState(ColorSchemeKind kind, int count, boolean isSelected,
			boolean isEnabled) {
		colorSchemeKind = kind;
		cycleCount = count;
		this.isSelected = isSelected;
		this.isEnabled = isEnabled;
	}

	/**
	 * Returns the corresponding color scheme kind
	 * 
	 * @return Corresponding color scheme kind
	 */
	public ColorSchemeKind getColorSchemeKind() {
		return colorSchemeKind;
	}

	/**
	 * Returns the corresponding cycle count.
	 * 
	 * @return Corresponding cycle count.
	 */
	public int getCycleCount() {
		return cycleCount;
	}

	/**
	 * Returns the indication of whether a component with <code>this</code>
	 * state is enabled.
	 * 
	 * @return Indication of whether a component with <code>this</code> state
	 *         is enabled.
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * Returns the indication of whether a component with <code>this</code>
	 * state is selected.
	 * 
	 * @return Indication of whether a component with <code>this</code> state
	 *         is selected.
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * Retrieves component state based on the button model (required parameter)
	 * and button itself (optional parameter).
	 * 
	 * @param model
	 *            Button model (required).
	 * @param button
	 *            Button (optional).
	 * @return The matching component state.
	 */
	public static ComponentState getState(ButtonModel model,
			AbstractButton button) {

//		System.out.println(button.getWidth() + "*" + button.getHeight() + ":"
//				+ (model.isEnabled() ? "enabled:" : "")
//				+ (model.isArmed() ? "armed:" : "")
//				+ (model.isSelected() ? "selected:" : "")
//				+ (model.isRollover() ? "rollover:" : "")
//				+ (model.isPressed() ? "pressed:" : ""));

		if (button != null) {
			if (button instanceof JButton) {
				JButton jb = (JButton) button;
				if (jb.isDefaultButton()) {
					if (model.isEnabled()) {
						if ((!model.isPressed()) && (!model.isArmed()))
							return ACTIVE;
					} else
						return DISABLED_ACTIVE;
				}
			}
		}

		if (!model.isEnabled()) {
			if (model.isSelected()) {
				return DISABLED_SELECTED;
			} else {
				return DISABLED_UNSELECTED;
			}
		} else if (model.isPressed() && model.isArmed()) {
			if (model.isSelected())
				return PRESSED_SELECTED;
			else
				return PRESSED_UNSELECTED;
		} else if (model.isSelected()) {
			if (((button == null) || (button.isRolloverEnabled()))
					&& model.isRollover()) {
				return ROLLOVER_SELECTED;
			} else {
				return SELECTED;
			}
		} else if (((button == null) || (button.isRolloverEnabled()))
				&& model.isRollover()) {
			return ROLLOVER_UNSELECTED;
		}

		return DEFAULT;
	}

}
