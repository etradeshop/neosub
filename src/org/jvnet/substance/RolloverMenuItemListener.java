package org.jvnet.substance;

import java.awt.event.MouseEvent;

import javax.swing.ButtonModel;
import javax.swing.JMenuItem;
import javax.swing.event.MouseInputListener;

/**
 * Menu item listener for rollover effects. Tracks the mouse motion interaction
 * for the associated menu item.
 * 
 * @author Kirill Grouchnikov
 */
public class RolloverMenuItemListener implements MouseInputListener {
	/**
	 * If the mouse pointer is currently inside the associated menu item area,
	 * <code>this</code> flag is <code>true</code>.
	 */
	private boolean isMouseInside;

	/**
	 * The associated menu item.
	 */
	private JMenuItem item;

	/**
	 * Simple constructor.
	 * 
	 * @param item
	 *            The associated menu item.
	 */
	public RolloverMenuItemListener(JMenuItem item) {
		this.item = item;
		this.isMouseInside = false;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		this.isMouseInside = true;
		this.item.getModel().setRollover(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		this.isMouseInside = false;
		this.item.getModel().setRollover(false);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		ButtonModel model = this.item.getModel();
		model.setRollover(false);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) {
		this.item.getModel().setRollover(this.isMouseInside);
	}

}
