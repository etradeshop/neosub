package org.jvnet.substance;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import org.jvnet.substance.comp.BasicButtonStripUI;
import org.jvnet.substance.comp.JButtonStrip.StripOrientation;

/**
 * UI for button strips in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceButtonStripUI extends BasicButtonStripUI {
	/**
	 * The background delegate.
	 */
	private SubstanceBackgroundDelegate backgroundDelegate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new SubstanceButtonStripUI();
	}

	/**
	 * Simple constructor.
	 */
	public SubstanceButtonStripUI() {
		this.backgroundDelegate = new SubstanceBackgroundDelegate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.comp.BasicButtonStripUI#paintStripButtonBackground(java.awt.Graphics,
	 *      javax.swing.AbstractButton, boolean, boolean, int, int)
	 */
	@Override
	protected void paintStripButtonBackground(Graphics g,
			AbstractButton button, boolean isFirst, boolean isLast,
			int totalStripDimension, int relativeOffset) {
		this.backgroundDelegate.updateBackgroundInStrip(g, button,
				this.buttonStrip.getOrientation(), isFirst, isLast,
				totalStripDimension, relativeOffset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.comp.BasicButtonStripUI#paintStripButtonBorder(java.awt.Graphics,
	 *      javax.swing.AbstractButton, boolean, boolean)
	 */
	@Override
	protected void paintStripButtonBorder(Graphics g, AbstractButton button,
			boolean isFirst, boolean isLast) {
		if (this.buttonStrip.getOrientation() == StripOrientation.HORIZONTAL) {
			// need to put the right border for all buttons except the last
			if (!isLast) {
				Graphics2D graphics = (Graphics2D) g.create(
						button.getWidth() - 1, 0, 2, button.getHeight());
				SubstanceBorder sb = new SubstanceBorder();
				sb.paintBorder(buttonStrip, graphics, 1 - button.getWidth(), 0,
						button.getWidth(), button.getHeight());
				graphics.dispose();
			}
		} else {
			// need to put the bottom border for all buttons except the last
			if (!isLast) {
				Graphics2D graphics = (Graphics2D) g.create(0, button
						.getHeight() - 1, button.getWidth(), 2);
				SubstanceBorder sb = new SubstanceBorder();
				sb.paintBorder(buttonStrip, graphics, 0, 0,
						button.getWidth(), button.getHeight());
				graphics.dispose();
			}
		}
	}
}
