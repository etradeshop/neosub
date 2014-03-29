package org.jvnet.substance;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicListUI;

/**
 * UI for lists in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceListUI extends BasicListUI {
	/**
	 * Local cache of JList's client property "List.isFileList"
	 */
	protected boolean isFileList;

	/**
	 * Local cache of JList's component orientation property
	 */
	protected boolean isLeftToRight;

	/**
	 * Delegate for painting the background.
	 */
	private static SubstanceGradientBackgroundDelegate backgroundDelegate = new SubstanceGradientBackgroundDelegate();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent list) {
		return new SubstanceListUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicListUI#installDefaults()
	 */
	@Override
	protected void installDefaults() {
		super.installDefaults();
		isFileList = Boolean.TRUE.equals(list
				.getClientProperty("List.isFileList"));
		isLeftToRight = list.getComponentOrientation().isLeftToRight();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicListUI#paintCell(java.awt.Graphics, int,
	 *      java.awt.Rectangle, javax.swing.ListCellRenderer,
	 *      javax.swing.ListModel, javax.swing.ListSelectionModel, int)
	 */
	@Override
	protected void paintCell(Graphics g, int row, Rectangle rowBounds,
			ListCellRenderer cellRenderer, ListModel dataModel,
			ListSelectionModel selModel, int leadIndex) {
		Object value = dataModel.getElementAt(row);
		boolean cellHasFocus = list.hasFocus() && (row == leadIndex);
		boolean isSelected = selModel.isSelectedIndex(row);

		Component rendererComponent = cellRenderer
				.getListCellRendererComponent(list, value, row, isSelected,
						cellHasFocus);

		int cx = rowBounds.x;
		int cy = rowBounds.y;
		int cw = rowBounds.width;
		int ch = rowBounds.height;

		if (isFileList) {
			// Shrink renderer to preferred size. This is mostly used on Windows
			// where selection is only shown around the file name, instead of
			// across the whole list cell.
			int w = Math
					.min(cw, rendererComponent.getPreferredSize().width + 4);
			if (!isLeftToRight) {
				cx += (cw - w);
			}
			cw = w;
		}

		if (isSelected) {
			backgroundDelegate.update(g, rendererComponent, new Rectangle(cx,
					cy, cw, ch), SubstanceLookAndFeel.getColorScheme(), true);
		}

		if (rendererComponent instanceof JComponent) {
			// Play with opaqueness to make our own gradient background
			// on selected elements to show.
			JComponent jRenderer = (JComponent) rendererComponent;
			synchronized (jRenderer) {
				boolean oldOpaque = jRenderer.isOpaque();
				jRenderer.setOpaque(!isSelected);
				rendererPane.paintComponent(g, rendererComponent, list, cx, cy,
						cw, ch, true);
				jRenderer.setOpaque(oldOpaque);
			}
		} else {
			rendererPane.paintComponent(g, rendererComponent, list, cx, cy, cw,
					ch, true);
		}

	}
}
