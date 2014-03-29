package org.jvnet.substance;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicOptionPaneUI;

/**
 * UI for option panes in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceOptionPaneUI extends BasicOptionPaneUI {

    /**
     * Creates a new SubstanceOptionPaneUI instance.
     */
    public static ComponentUI createUI(JComponent c) {
        return new SubstanceOptionPaneUI();
    }

    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicOptionPaneUI#installDefaults()
     */
    @Override
    protected void installDefaults() {
        super.installDefaults();
    }

}
