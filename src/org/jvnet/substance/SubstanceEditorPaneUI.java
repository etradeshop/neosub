package org.jvnet.substance;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicEditorPaneUI;

/**
 * UI for editor panes in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceEditorPaneUI extends BasicEditorPaneUI {
    /* (non-Javadoc)
     * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
     */
    public static ComponentUI createUI(JComponent c) {
        return new SubstanceEditorPaneUI(c);
    }
    
    /**
     * Simple constructor.
     * 
     * @param c Component (editor pane).
     */
    public SubstanceEditorPaneUI(JComponent c) {
    	c.addFocusListener(new FocusBorderListener(c));
    }
}
