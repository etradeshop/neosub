package org.jvnet.substance;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalTextFieldUI;

/**
 * UI for text fields in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceTextFieldUI extends MetalTextFieldUI {
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new SubstanceTextFieldUI(c);
	}

	/**
	 * Simple constructor.
	 * 
	 * @param c
	 *            Component (text field).
	 */
	public SubstanceTextFieldUI(JComponent c) {
		c.addFocusListener(new FocusBorderListener(c));
	}
}
