package org.jvnet.substance;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToolBarUI;

/**
 * UI for tool bars in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceToolBarUI extends MetalToolBarUI {
	/**
	 * Delegate for painting the background.
	 */
	private static SubstanceFillBackgroundDelegate backgroundDelegate = new SubstanceFillBackgroundDelegate();

	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new SubstanceToolBarUI();
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#update(java.awt.Graphics, javax.swing.JComponent)
	 */
	@Override
	public void update(Graphics g, JComponent c) {
		boolean isOpaque = c.isOpaque();
		if (isOpaque) {
			backgroundDelegate.update(g, c);
		} else {
			super.update(g, c);
		}
	}
}
