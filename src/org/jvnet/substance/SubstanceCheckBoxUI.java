package org.jvnet.substance;

import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;

import org.jvnet.substance.theme.SubstanceTheme;

/**
 * UI for check boxes in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceCheckBoxUI extends SubstanceRadioButtonUI {
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent b) {
		return new SubstanceCheckBoxUI((JToggleButton) b);
	}

	/**
	 * Hash map for storing icons for each component state.
	 */
	private static Map<ComponentState, Icon> icons;

	/**
	 * Simple constructor.
	 * 
	 * @param button
	 *            The associated button.
	 */
	private SubstanceCheckBoxUI(JToggleButton button) {
		super(button);
		button.setRolloverEnabled(true);
	}

	/**
	 * Default checkbox dimension.
	 */
	private static final int DIMENSION = 15;

	/**
	 * Resets image maps (used when setting new theme).
	 * 
	 * @see SubstanceLookAndFeel#setCurrentTheme(String)
	 * @see SubstanceLookAndFeel#setCurrentTheme(SubstanceTheme)
	 */
	static synchronized void reset() {
		icons = new HashMap<ComponentState, Icon>();
		for (ComponentState state : ComponentState.values()) {
			icons.put(state, new ImageIcon(SubstanceImageCreator.getCheckBox(
					DIMENSION, state)));
		}
	}

	static {
		reset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicButtonUI#createButtonListener(javax.swing.AbstractButton)
	 */
//	@Override
//	protected BasicButtonListener createButtonListener(AbstractButton b) {
//		return RolloverButtonListener.getListener(b);
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicRadioButtonUI#getDefaultIcon()
	 */
	@Override
	public Icon getDefaultIcon() {
		ButtonModel model = button.getModel();
		return icons.get(ComponentState.getState(model, button));
	}

	static String getMemoryUsage() {
		StringBuffer sb = new StringBuffer();
		sb.append("SubstanceCheckBox: \n");
		sb.append("\t" + icons.size() + " icons");
		return sb.toString();
	}
}
