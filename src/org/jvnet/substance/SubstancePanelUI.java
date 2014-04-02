package org.jvnet.substance;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;

/**
 * UI for panels in <b>Substance</b> look and feel.
 *
 * @author Kirill Grouchnikov
 */
public class SubstancePanelUI extends BasicPanelUI {
    /**
     * UI instance.
     */
    private static final SubstancePanelUI INSTANCE = new SubstancePanelUI();

    /**
     * Background delegate.
     */
    private SubstanceFillBackgroundDelegate bgDelegate;

    /*
      * (non-Javadoc)
      *
      * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
      */
    public static ComponentUI createUI(JComponent b) {
        return INSTANCE;
    }

    /**
     * Simple constructor.
     */
    private SubstancePanelUI() {
        this.bgDelegate = new SubstanceFillBackgroundDelegate();
    }

    /* (non-Javadoc)
      * @see javax.swing.plaf.ComponentUI#update(java.awt.Graphics, javax.swing.JComponent)
      */
    @Override
    public void update(Graphics g, JComponent c) {
        if (!c.isShowing()) {
            return;
        }
        synchronized (c) {
            if (c.isOpaque()) {
                this.bgDelegate.update(g, c);
                super.paint(g, c);
            }
            else {
                super.paint(g, c);
            }
        }
    }
}
