package org.jvnet.substance;

import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import java.awt.*;

/**
 * Border for toolbar.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceToolBarBorder extends AbstractBorder implements UIResource {
    /* (non-Javadoc)
     * @see javax.swing.border.Border#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
     */
	@Override
    public void paintBorder(Component c, Graphics g, int x, int y, int w,
                            int h) {

        Graphics2D graphics = (Graphics2D)g;
        graphics.translate(x, y);

        if (((JToolBar) c).isFloatable()) {
            if (((JToolBar) c).getOrientation() == SwingConstants.HORIZONTAL) {
                if (c.getComponentOrientation().isLeftToRight()) {
                    graphics.drawImage(SubstanceImageCreator.getDragImage(10,
                            c.getSize().height - 4),
                            2, 1, null);
                }
                else {
                    graphics.drawImage(SubstanceImageCreator.getDragImage(10,
                            c.getSize().height - 4), c.getBounds()
                            .width - 12, 1, null);
                }
            }
            else // vertical
            {
                graphics.drawImage(SubstanceImageCreator.getDragImage(
                        c.getSize().width - 4, 10),
                        2, 2, null);
            }

        }

        graphics.translate(-x, -y);
    }

    /* (non-Javadoc)
     * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
     */
	@Override
    public Insets getBorderInsets(Component c) {
        return getBorderInsets(c, new Insets(0, 0, 0, 0));
    }

	/* (non-Javadoc)
	 * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component, java.awt.Insets)
	 */
	@Override
    public Insets getBorderInsets(Component c, Insets newInsets) {
        newInsets.set(1, 2, 3, 2);

        JToolBar toolbar = (JToolBar)c;
        if (toolbar.isFloatable()) {
            if (toolbar.getOrientation() == SwingConstants.HORIZONTAL) {
                if (toolbar.getComponentOrientation().isLeftToRight()) {
                    newInsets.left = 16;
                }
                else {
                    newInsets.right = 16;
                }
            }
            else {// vertical
                newInsets.top = 16;
            }
        }

        Insets margin = toolbar.getMargin();

        if (margin != null) {
            newInsets.left += margin.left;
            newInsets.top += margin.top;
            newInsets.right += margin.right;
            newInsets.bottom += margin.bottom;
        }

        return newInsets;
    }
}
