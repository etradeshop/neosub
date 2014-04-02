package org.jvnet.substance;

import java.awt.Insets;

import javax.swing.*;
import javax.swing.plaf.UIResource;

/**
 * Scroll bar button in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceScrollButton extends JButton implements UIResource {
	/**
	 * Button orientation.
	 */
	private int orientation;

	/**
	 * Simple constructor.
	 * 
	 * @param scrollBarIcon
	 *            The icon.
	 * @param orientation
	 *            The button icon arrow orientation.
	 */
	public SubstanceScrollButton(Icon scrollBarIcon, int orientation) {
		// super(0, 0, false);
		setModel(new DefaultButtonModel() {
			@Override
			public void setArmed(boolean armed) {
				super.setArmed(isPressed() || armed);
			}
		});
		// this.setEnabled(scrollBar.isEnabled());
		// this.setFocusable(false);
		this.setRequestFocusEnabled(false);
		this.setMargin(new Insets(0, 0, 0, 2));
		this.orientation = orientation;
		this.setIcon(scrollBarIcon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#isFocusTraversable()
	 */
	@Override
	public boolean isFocusTraversable() {
		return false;
	}

	/**
	 * Returns side that corresponds to the orientation of the associated
	 * button.
	 * 
	 * @return Side that corresponds to the orientation of the associated
	 *         button.
	 */
	public SubstanceImageCreator.Side getSide() {
		switch (this.orientation) {
		case SwingConstants.NORTH:
			return SubstanceImageCreator.Side.BOTTOM;
		case SwingConstants.WEST:
			return SubstanceImageCreator.Side.RIGHT;
		case SwingConstants.SOUTH:
			return SubstanceImageCreator.Side.TOP;
		case SwingConstants.EAST:
			return SubstanceImageCreator.Side.LEFT;
		default:
			return null;
		}
	}

}
