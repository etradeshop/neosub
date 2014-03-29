package org.jvnet.substance;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicDesktopPaneUI;

/**
 * UI for desktop panes in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceDesktopPaneUI extends BasicDesktopPaneUI {
	/**
	 * UI instance.
	 */
	private static final SubstanceDesktopPaneUI INSTANCE = new SubstanceDesktopPaneUI();

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
	private SubstanceDesktopPaneUI() {
		this.bgDelegate = new SubstanceFillBackgroundDelegate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#update(java.awt.Graphics,
	 *      javax.swing.JComponent)
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
			} else {
				super.paint(g, c);
			}
		}
		g.setColor(UIManager.getColor("Desktop.foreground"));
		g.drawRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
	}
}
