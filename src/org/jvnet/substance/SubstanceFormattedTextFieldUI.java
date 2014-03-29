package org.jvnet.substance;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicFormattedTextFieldUI;

/**
 * UI for formatted text fields in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceFormattedTextFieldUI extends BasicFormattedTextFieldUI {
    /* (non-Javadoc)
     * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
     */
    public static ComponentUI createUI(JComponent c) {
        return new SubstanceFormattedTextFieldUI(c);
    }
    
    /**
     * Simple constructor.
     * 
     * @param c Component (formatted text field).
     */
    public SubstanceFormattedTextFieldUI(JComponent c) {
    	c.addFocusListener(new FocusBorderListener(c));
    }
}
