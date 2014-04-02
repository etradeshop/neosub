package org.jvnet.substance;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Listener for changing border on focus events.
 * 
 * @author Kirill Grouchnikov
 */
public class FocusBorderListener implements FocusListener {
	/**
	 * The component that is being tracked by <code>this</code> listener.
	 */
	private Component trackedComponent;

	/**
	 * The component that owns the focus.
	 */
	private static Component focusedComponent;

	/**
	 * Simple constructor.
	 * 
	 * @param c
	 *            The associated tracked component.
	 */
	public FocusBorderListener(Component c) {
		this.trackedComponent = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent e) {
		synchronized (FocusBorderListener.class) {
			focusedComponent = this.trackedComponent;
			this.trackedComponent.repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent e) {
		synchronized (FocusBorderListener.class) {
			if (focusedComponent == this.trackedComponent)
				focusedComponent = null;
			this.trackedComponent.repaint();
		}
	}

	/**
	 * Checks whether the specified component owns focus.
	 * 
	 * @param comp
	 *            Component.
	 * @return <code>true</code> if the specified component owns focus,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isFocused(Component comp) {
		return (focusedComponent == comp);
	}
}
