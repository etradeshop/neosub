package org.jvnet.substance;

import java.awt.Graphics;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;

/**
 * Delegate for painting filled backgrounds.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceFillBackgroundDelegate {
	/**
	 * Updates the background of the specified component on the specified
	 * graphic context.
	 * 
	 * @param g
	 *            Graphic context.
	 * @param c
	 *            Component.
	 */
	public void update(Graphics g, JComponent c) {
		boolean isInCellRenderer = (c.getParent() instanceof CellRendererPane);
		if ((!c.isShowing()) && (!isInCellRenderer)) {
			return;
		}

		synchronized (c) {
			if (c.isOpaque()) {
				// fill background
				g.setColor(c.getBackground());
				g.fillRect(0, 0, c.getWidth(), c.getHeight());

				// stamp watermark
				if (!isInCellRenderer)
					SubstanceLookAndFeel.getCurrentWatermark().drawWatermarkImage(
							g, c, 0, 0, c.getWidth(), c.getHeight());
			}
		}
	}
}
