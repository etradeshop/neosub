package org.jvnet.substance;

import java.awt.*;

import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToolTipUI;

/**
 * UI for tool tips in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceToolTipUI extends MetalToolTipUI {
    /**
     * UI instance.
     */
    static SubstanceToolTipUI INSTANCE = new SubstanceToolTipUI();

    /* (non-Javadoc)
     * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
     */
    public static ComponentUI createUI(JComponent c) {
    	JToolTip tooltip = (JToolTip)c;
    	tooltip.setOpaque(false);
    	Container parent = tooltip.getParent();
    	if (parent instanceof JComponent) {
    		((JComponent)parent).setOpaque(false);
    	}
//    	tooltip.getRootPane().setOpaque(false);
//    	((JComponent)tooltip.getRootPane().getParent()).setOpaque(false);
        return INSTANCE;
    }

	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics, javax.swing.JComponent)
	 */
	@Override
	public void paint(Graphics g, JComponent c) {
		synchronized (c) {
			boolean oldOpaque = c.isOpaque();
	        Dimension size = c.getSize();
            g.setColor(SubstanceLookAndFeel.getColorScheme().getColorScheme().getUltraLightColor());
            Insets ins = c.getInsets(); 
            g.fillRect(ins.left, ins.top, size.width - ins.left - ins.right, 
            		size.height - ins.top - ins.bottom);
			c.setOpaque(false);
			super.paint(g, c);
			c.setOpaque(oldOpaque);
		}
	}
}
