package org.jvnet.substance;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextAreaUI;

/**
 * UI for text areas in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceTextAreaUI extends BasicTextAreaUI {
    /* (non-Javadoc)
     * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
     */
    public static ComponentUI createUI(JComponent c) {
        return new SubstanceTextAreaUI(c);
    }
    
    /**
     * Simple constructor.
     * 
     * @param c Component (text area).
     */
    public SubstanceTextAreaUI(JComponent c) {
    	c.addFocusListener(new FocusBorderListener(c));
    }
}
