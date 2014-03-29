package org.jvnet.substance;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import org.jvnet.substance.comp.BasicRibbonUI;

/**
 * UI for ribbon bands in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceRibbonUI extends BasicRibbonUI {
	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new SubstanceRibbonUI();
	}
}
