package org.jvnet.substance;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicRadioButtonMenuItemUI;

/**
 * UI for radio button menu items in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceRadioButtonMenuItemUI extends BasicRadioButtonMenuItemUI {
	/**
	 * Delegate for painting the background.
	 */
	private static SubstanceMenuBackgroundDelegate backgroundDelegate = 
		new SubstanceMenuBackgroundDelegate();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent b) {
		JRadioButtonMenuItem item = (JRadioButtonMenuItem) b;
		// add rollover listener
		item.setRolloverEnabled(true);
		item.addMouseListener(new RolloverMenuItemListener(item));
		return new SubstanceRadioButtonMenuItemUI();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicMenuItemUI#paintBackground(java.awt.Graphics, javax.swing.JMenuItem, java.awt.Color)
	 */
	@Override
	protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
		backgroundDelegate.paintBackground(g, menuItem, bgColor, true);
	}

}
