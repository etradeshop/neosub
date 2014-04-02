package org.jvnet.substance;

import java.awt.event.MouseEvent;

/**
 * General interface for UIs that wish to provide transition effects on one of
 * their components.
 * 
 * @author Kirill Grouchnikov
 */
public interface Trackable {
	/**
	 * Checks whether the mouse position of the specified event lies inside the
	 * area of the component designated for transition effects.
	 * 
	 * @param me
	 *            Mouse event.
	 * @return <code>true</code> if the mouse position of the specified event
	 *         lies inside the area of the component designated for transition
	 *         effects, <code>false</code> otherwise.
	 */
	public boolean isInside(MouseEvent me);
}
