package org.jvnet.substance;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalRadioButtonUI;

/**
 * UI for radio buttons in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceRadioButtonUI extends MetalRadioButtonUI {
	/**
	 * Default radio button dimension.
	 */
	private static final int DIMENSION = 12;

	/**
	 * Background delegate.
	 */
	private SubstanceFillBackgroundDelegate bgDelegate;

	/**
	 * Associated toggle button.
	 */
	protected JToggleButton button;

	/**
	 * Icons for all component states
	 */
	private static Map<ComponentState, Icon> icons;

	/**
	 * Resets image maps (used when setting new theme).
	 * 
	 * @see SubstanceLookAndFeel#setCurrentTheme(String)
	 * @see SubstanceLookAndFeel#setCurrentTheme(org.jvnet.substance.theme.SubstanceTheme)
	 */
	static synchronized void reset() {
		icons = new HashMap<ComponentState, Icon>();
		for (ComponentState state : ComponentState.values()) {
			icons.put(state, new ImageIcon(SubstanceImageCreator
					.getRadioButton(DIMENSION, state)));
		}
	}

	static {
		reset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent b) {
		return new SubstanceRadioButtonUI((JToggleButton) b);
	}

	/**
	 * Simple constructor.
	 * 
	 * @param button
	 *            Associated radio button.
	 */
	public SubstanceRadioButtonUI(JToggleButton button) {
		this.bgDelegate = new SubstanceFillBackgroundDelegate();
		this.button = button;
		button.setRolloverEnabled(true);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	@Override
	public synchronized void update(Graphics g, JComponent c) {
		// important to synchronize on the button as we are
		// about to fiddle with its opaqueness
		synchronized (c) {
			this.bgDelegate.update(g, c);
			// remove opaqueness
			boolean isOpaque = c.isOpaque();
			c.setOpaque(false);
			bgDelegate.update(g, c);
			super.paint(g, c);
			// restore opaqueness
			c.setOpaque(isOpaque);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicRadioButtonUI#paintFocus(java.awt.Graphics,
	 *      java.awt.Rectangle, java.awt.Dimension)
	 */
	@Override
	protected void paintFocus(Graphics g, Rectangle t, Dimension d) {
		// override to not show the focus rectangle from Metal
	}

	static String getMemoryUsage() {
		StringBuffer sb = new StringBuffer();
		sb.append("SubstanceRadioButtonUI: \n");
		sb.append("\t" + icons.size() + " icons");
		return sb.toString();
	}
}
