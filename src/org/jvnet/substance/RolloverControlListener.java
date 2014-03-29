package org.jvnet.substance;

import java.awt.Component;
import java.awt.event.*;

import javax.swing.ButtonModel;

/**
 * Control listener for rollover effects. Tracks the mouse motion interaction
 * for the associated {@link org.jvnet.substance.Trackable} control.
 * 
 * @author Kirill Grouchnikov
 */
public class RolloverControlListener implements MouseListener,
		MouseMotionListener {
	/**
	 * If the mouse pointer is currently inside the designated area (fetched
	 * from the associated {@link #trackableUI}), <code>this</code> flag is
	 * <code>true</code>.
	 */
	private boolean isMouseInside;

	/**
	 * Surrogate model for tracking control status.
	 */
	private ButtonModel model;

	/**
	 * Object that is queried for mouse events. This object is responsible for
	 * handling the designated (hot-spot) area of the associated control.
	 */
	private Trackable trackableUI;

	/**
	 * Simple constructor.
	 * 
	 * @param trackableUI
	 *            Object that is queried for mouse events.
	 * @param model
	 *            Surrogate model for tracking control status.
	 */
	public RolloverControlListener(Trackable trackableUI, ButtonModel model) {
		this.trackableUI = trackableUI;
		this.model = model;
		this.isMouseInside = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		Component component = (Component) e.getSource();
		if (!component.isEnabled())
			return;
		boolean isInside = this.trackableUI.isInside(e);
		this.isMouseInside = isInside;
		this.model.setRollover(isInside);
		this.model.setEnabled(component.isEnabled());
		component.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		Component component = (Component) e.getSource();
		if (!component.isEnabled())
			return;
		this.isMouseInside = false;
		this.model.setRollover(false);
		this.model.setEnabled(component.isEnabled());
		component.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		// System.out.println("mouse released [" + e.getX() + ":" + e.getY() +
		// "]");
		Component component = (Component) e.getSource();
		if (!component.isEnabled())
			return;
		this.model.setRollover(this.isMouseInside);
		this.model.setPressed(false);
		this.model.setArmed(false);
		this.model.setSelected(false);
		this.model.setEnabled(component.isEnabled());
		component.repaint();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		// System.out.println("mouse pressed [" + e.getX() + ":" + e.getY() +
		// "]");
		Component component = (Component) e.getSource();
		if (!component.isEnabled())
			return;
		this.model.setRollover(this.isMouseInside);
		if (this.isMouseInside) {
			this.model.setPressed(true);
			this.model.setArmed(true);
			this.model.setSelected(true);
		}
		this.model.setEnabled(component.isEnabled());
		component.repaint();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) {
		// System.out.println("mouse dragged [" + e.getX() + ":" + e.getY() +
		// "]");
		Component component = (Component) e.getSource();
		if (!component.isEnabled())
			return;
		boolean isInside = this.trackableUI.isInside(e);
		this.isMouseInside = isInside;
		this.model.setEnabled(component.isEnabled());
		component.repaint();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) {
		// System.out.println("mouse moved [" + e.getX() + ":" + e.getY() +
		// "]");
		Component component = (Component) e.getSource();
		if (!component.isEnabled())
			return;
		boolean isInside = this.trackableUI.isInside(e);
		// System.out.println("inside");
		this.isMouseInside = isInside;
		this.model.setRollover(isInside);
		this.model.setEnabled(component.isEnabled());
		component.repaint();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
	}
}
