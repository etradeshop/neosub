package org.jvnet.substance;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import javax.swing.*;

/**
 * Tracker for pulsating (default and focused) <code>JButton</code>s.
 * 
 * @author Kirill Grouchnikov
 */
public class PulseTracker implements ActionListener {
	/**
	 * Map (with weakly-referenced keys) of all trackers. For each default
	 * button which has not been claimed by GC, we have a tracker (with
	 * associated <code>Timer</code>).
	 */
	private static WeakHashMap<JButton, PulseTracker> trackers = new WeakHashMap<JButton, PulseTracker>();

	/**
	 * Map (with weakly-referenced keys) of cycle counts. For each default
	 * button which is shown <b>and</b> is in window that owns focus,
	 * <code>this</code> map contains the cycle count (for animation
	 * purposes). On each event of the associated <code>Timer</code> (see
	 * {@link #actionPerformed(ActionEvent)}), the counter is incremented by 1.
	 * For buttons that are in windows that lose focus, the counter is reverted
	 * back to 0 (animation stops).
	 */
	private static WeakHashMap<JButton, Long> cycles = new WeakHashMap<JButton, Long>();

	/**
	 * Waek reference to the associated button.
	 */
	private WeakReference<JButton> buttonRef;

	/**
	 * The associated timer.
	 */
	private Timer timer;

	/**
	 * Simple constructor.
	 * 
	 * @param jbutton
	 */
	private PulseTracker(JButton jbutton) {
		// Create weak reference.
		buttonRef = new WeakReference<JButton>(jbutton);
		// Create coalesced timer.
		timer = new Timer(50, this);
		timer.setCoalesce(true);
		// Store event handler and initial cycle count.
		PulseTracker.trackers.put(jbutton, this);
		PulseTracker.cycles.put(jbutton, (long) 0);
	}

	/**
	 * Recursively checks whether the specified component or one of its inner
	 * components has focus.
	 * 
	 * @param component
	 *            Component to check.
	 * @return <code>true</code> if the specified component or one of its
	 *         inner components has focus, <code>false</code> otherwise.
	 */
	private static boolean hasFocus(Component component) {
		if (component == null) {
			return false;
		}

		// check if the component itself is focus owner
		if (component.isFocusOwner()) {
			return true;
		}

		// recursively find if has focus owner component
		if (component instanceof Container) {
			for (Component comp : ((Container) component).getComponents()) {
				if (hasFocus(comp)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Recursively checks whether the specified component has visible glass
	 * pane.
	 * 
	 * @param component
	 *            Component to check.
	 * @return <code>true</code> if the specified component has visible glass
	 *         pane, <code>false</code> otherwise.
	 */
	private static boolean hasGlassPane(Component component) {
		if (component == null) {
			return false;
		}
		// check if the component has visible glass pane
		Component glassPane = null;
		if (component instanceof JDialog) {
			glassPane = ((JDialog) component).getGlassPane();
		}
		if (component instanceof JFrame) {
			glassPane = ((JFrame) component).getGlassPane();
		}
		if ((glassPane != null) && (glassPane.isVisible())) {
			return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {
		// get the button and check if it wasn't GC'd
		JButton jButton = buttonRef.get();
		if (jButton == null) {
			return;
		}
		if (PulseTracker.hasGlassPane(jButton.getTopLevelAncestor()))
			return;
		if (!jButton.isDefaultButton()) {
			// has since lost its default status
			PulseTracker tracker = trackers.get(jButton);
			tracker.stopTimer();
			tracker.buttonRef.clear();
			trackers.remove(jButton);
			cycles.remove(jButton);
		} else {
			if (!PulseTracker.hasFocus(jButton.getTopLevelAncestor())) {
				// no focus in button window - will restore original (not
				// animated) painting
				PulseTracker.update(jButton);
			} else {
				// check if it's enabled
				if (jButton.isEnabled()) {
					// increment cycle count for default focused buttons.
					long oldCycle = cycles.get(jButton);
					cycles.put(jButton, oldCycle + 1);
				} else {
					// revert to 0 if it's not enabled
					if (cycles.get(jButton) != 0) {
						cycles.put(jButton, (long) 0);
					}
				}
			}
		}
		jButton.repaint();
	}

	/**
	 * Starts the associated timer.
	 */
	private void startTimer() {
		if (!timer.isRunning()) {
			timer.start();
		}
	}

	/**
	 * Stops the associated timer.
	 */
	private void stopTimer() {
		if (timer.isRunning()) {
			timer.stop();
		}
	}

	/**
	 * Returns the status of the associated timer.
	 * 
	 * @return <code>true</code> is the associated timer is running,
	 *         <code>false</code> otherwise.
	 */
	private boolean isRunning() {
		return this.timer.isRunning();
	}

	/**
	 * Updates the state of the specified button which must be a default button
	 * in some window. The button state is determined based on focus ownership.
	 * 
	 * @param jButton
	 *            Button.
	 */
	public static synchronized void update(JButton jButton) {
		boolean hasFocus = PulseTracker.hasFocus(jButton.getTopLevelAncestor());
		PulseTracker tracker = trackers.get(jButton);
		if (!hasFocus) {
			// remove
			if (tracker == null) {
				return;
			}
			if (cycles.get(jButton) == 0) {
				return;
			}
			cycles.put(jButton, (long) 0);
			// System.out.println("r::" + trackers.size());
		} else {
			// add
			if (tracker != null) {
				tracker.startTimer();
				return;
			}
			tracker = new PulseTracker(jButton);
			tracker.startTimer();
			trackers.put(jButton, tracker);
			cycles.put(jButton, (long) 0);
			// System.out.println("a::" + trackers.size());
		}
	}

	/**
	 * Retrieves the current cycle count for the specified button.
	 * 
	 * @param jButton
	 *            Button.
	 * @return Current cycle count for the specified button.
	 */
	public static long getCycles(JButton jButton) {
		Long cycleCount = cycles.get(jButton);
		if (cycleCount == null) {
			return 0;
		}
		return cycleCount.longValue();
	}

	/**
	 * Retrieves the animation state for the specified button.
	 * 
	 * @param jButton
	 *            Button.
	 * @return <code>true</code> if the specified button is being animated,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isAnimating(JButton jButton) {
		PulseTracker tracker = trackers.get(jButton);
		if (tracker == null) {
			return false;
		}
		return tracker.isRunning();
	}

	
}
