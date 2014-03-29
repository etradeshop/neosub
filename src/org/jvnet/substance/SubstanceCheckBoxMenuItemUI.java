package org.jvnet.substance;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;

/**
 * UI for check box menu items in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceCheckBoxMenuItemUI extends BasicCheckBoxMenuItemUI {
	/**
	 * Delegate for painting the background.
	 */
	private static SubstanceMenuBackgroundDelegate backgroundDelegate = new SubstanceMenuBackgroundDelegate();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent b) {
		JCheckBoxMenuItem item = (JCheckBoxMenuItem) b;
		// add rollover listener
		item.setRolloverEnabled(true);
		item.addMouseListener(new RolloverMenuItemListener(item));
		return new SubstanceCheckBoxMenuItemUI();
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicMenuItemUI#paintBackground(java.awt.Graphics, javax.swing.JMenuItem, java.awt.Color)
	 */
	@Override
	protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
		backgroundDelegate.paintBackground(g, menuItem, bgColor, true);
	}
}
