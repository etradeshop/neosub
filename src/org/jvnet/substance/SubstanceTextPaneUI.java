package org.jvnet.substance;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextPaneUI;

/**
 * UI for text panes in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceTextPaneUI extends BasicTextPaneUI {
    /* (non-Javadoc)
     * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
     */
    public static ComponentUI createUI(JComponent c) {
        return new SubstanceTextPaneUI(c);
    }
    
    /**
     * Simple constructor.
     * 
     * @param c Component (text pane).
     */
    public SubstanceTextPaneUI(JComponent c) {
    	c.addFocusListener(new FocusBorderListener(c));
    }
}
