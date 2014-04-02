package org.jvnet.substance;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * Split pane divider in <code>Substance</code> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceSplitPaneDivider extends BasicSplitPaneDivider {
	/**
	 * Inset.
	 */
	private int inset = 2;

	/**
	 * Simple constructor.
	 * 
	 * @param ui
	 *            Associated UI.
	 */
	public SubstanceSplitPaneDivider(BasicSplitPaneUI ui) {
		super(ui);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		Rectangle clip = g.getClipBounds();
		Insets insets = getInsets();
		g.setColor(UIManager.getColor("SplitPane.dividerFocusColor"));
		g.fillRect(clip.x, clip.y, clip.width, clip.height);
		Dimension size = getSize();
		size.width -= inset * 2;
		size.height -= inset * 2;
		int drawX = inset;
		int drawY = inset;
		if (insets != null) {
			size.width -= (insets.left + insets.right);
			size.height -= (insets.top + insets.bottom);
			drawX += insets.left;
			drawY += insets.top;
		}
		super.paint(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicSplitPaneDivider#createLeftOneTouchButton()
	 */
	@Override
	protected JButton createLeftOneTouchButton() {
		JButton b = new JButton() {
			Icon verticalSplit = SubstanceImageCreator
					.getArrowIcon(7, 5, NORTH, SubstanceLookAndFeel.getColorScheme());

			Icon horizontalSplit = SubstanceImageCreator.getArrowIcon(7, 5,
					WEST, SubstanceLookAndFeel.getColorScheme());

			public void setBorder(Border b) {
			}

			public void paint(Graphics g) {
				JSplitPane splitPane = getSplitPaneFromSuper();
				if (splitPane != null) {
					int orientation = getOrientationFromSuper();

					if (orientation == JSplitPane.VERTICAL_SPLIT) {
						verticalSplit.paintIcon(splitPane, g, 1, 1);
					} else {
						horizontalSplit.paintIcon(splitPane, g, 1, 1);
					}
				}
			}

			// Don't want the button to participate in focus traversable.
			public boolean isFocusTraversable() {
				return false;
			}
		};
		b.setRequestFocusEnabled(false);
		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setOpaque(false);
		return b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicSplitPaneDivider#createRightOneTouchButton()
	 */
	@Override
	protected JButton createRightOneTouchButton() {
		JButton b = new JButton() {
			Icon verticalSplit = SubstanceImageCreator
					.getArrowIcon(7, 5, SOUTH, SubstanceLookAndFeel.getColorScheme());

			Icon horizontalSplit = SubstanceImageCreator.getArrowIcon(7, 5,
					EAST, SubstanceLookAndFeel.getColorScheme());

			public void setBorder(Border border) {
			}

			public void paint(Graphics g) {
				JSplitPane splitPane = getSplitPaneFromSuper();
				if (splitPane != null) {
					int orientation = getOrientationFromSuper();
					if (orientation == JSplitPane.VERTICAL_SPLIT) {
						verticalSplit.paintIcon(splitPane, g, 1, 1);
					} else {
						horizontalSplit.paintIcon(splitPane, g, 1, 1);
					}
				}
			}

			// Don't want the button to participate in focus traversable.
			public boolean isFocusTraversable() {
				return false;
			}
		};
		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setRequestFocusEnabled(false);
		b.setOpaque(false);
		return b;
	}

	/**
	 * Retrieves orientation of the associated split pane divider.
	 * 
	 * @return Orientation of the associated split pane divider.
	 */
	private int getOrientationFromSuper() {
		return super.orientation;
	}

	/**
	 * Retrieves the associated split pane.
	 * 
	 * @return The associated split pane.
	 */
	private JSplitPane getSplitPaneFromSuper() {
		return super.splitPane;
	}
}
