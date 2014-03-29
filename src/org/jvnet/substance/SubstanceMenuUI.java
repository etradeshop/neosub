package org.jvnet.substance;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuUI;

/**
 * UI for menus in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceMenuUI extends BasicMenuUI {
	/**
	 * Delegate for painting the background.
	 */
	private static SubstanceMenuBackgroundDelegate backgroundDelegate = new SubstanceMenuBackgroundDelegate();

	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new SubstanceMenuUI();
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicMenuItemUI#paintBackground(java.awt.Graphics, javax.swing.JMenuItem, java.awt.Color)
	 */
	@Override
	protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
		backgroundDelegate.paintBackground(g, menuItem, bgColor, true);
	}
}
