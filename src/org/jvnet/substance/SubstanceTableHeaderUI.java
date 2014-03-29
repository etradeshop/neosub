package org.jvnet.substance;

import org.jvnet.substance.color.ColorSchemeEnum;

import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * UI for table headers in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceTableHeaderUI extends BasicTableHeaderUI {
	/**
	 * Delegate for painting the background.
	 */
	private static SubstanceGradientBackgroundDelegate backgroundDelegate = new SubstanceGradientBackgroundDelegate();

	/**
	 * Listener for table header.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static class TableHeaderListener implements ListSelectionListener {
		/**
		 * The associated table header UI.
		 */
		private SubstanceTableHeaderUI ui;

		/**
		 * Simple constructor.
		 * 
		 * @param ui
		 *            The associated table header UI
		 */
		public TableHeaderListener(SubstanceTableHeaderUI ui) {
			this.ui = ui;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		public void valueChanged(ListSelectionEvent e) {
			if (ui.header == null)
				return;
			if (ui.header.isValid())
				ui.header.repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent h) {
		SubstanceTableHeaderUI result = new SubstanceTableHeaderUI();
		JTableHeader tableHeader = (JTableHeader) h;
		TableColumnModel columnModel = tableHeader.getColumnModel();
		if (columnModel != null) {
			ListSelectionModel lsm = columnModel.getSelectionModel();
			if (lsm != null) {
				lsm.addListSelectionListener(new TableHeaderListener(result));
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	@Override
	public void paint(Graphics g, JComponent c) {
		if (header.getColumnModel().getColumnCount() <= 0) {
			return;
		}
		boolean ltr = header.getComponentOrientation().isLeftToRight();

		Rectangle clip = g.getClipBounds();
		Point left = clip.getLocation();
		Point right = new Point(clip.x + clip.width - 1, clip.y);

		TableColumnModel cm = header.getColumnModel();
		int[] selectedColumns = cm.getSelectedColumns();
		Set<Integer> selected = new HashSet<Integer>();
		for (int sel : selectedColumns)
			selected.add(sel);

		int cMin = header.columnAtPoint(ltr ? left : right);
		int cMax = header.columnAtPoint(ltr ? right : left);
		// This should never happen.
		if (cMin == -1) {
			cMin = 0;
		}
		// If the table does not have enough columns to fill the view we'll get
		// -1.
		// Replace this with the index of the last column.
		if (cMax == -1) {
			cMax = cm.getColumnCount() - 1;
		}

		TableColumn draggedColumn = header.getDraggedColumn();
		int columnWidth;
		Rectangle cellRect = header.getHeaderRect(ltr ? cMin : cMax);
		TableColumn aColumn;
		if (ltr) {
			for (int column = cMin; column <= cMax; column++) {
				aColumn = cm.getColumn(column);
				columnWidth = aColumn.getWidth();
				cellRect.width = columnWidth;
				if (aColumn != draggedColumn) {
					paintCell(g, cellRect, column, selected.contains(column));
				}
				cellRect.x += columnWidth;
			}
		} else {
			for (int column = cMax; column >= cMin; column--) {
				aColumn = cm.getColumn(column);
				columnWidth = aColumn.getWidth();
				cellRect.width = columnWidth;
				if (aColumn != draggedColumn) {
					paintCell(g, cellRect, column, selected.contains(column));
				}
				cellRect.x += columnWidth;
			}
		}

		// Paint the dragged column if we are dragging.
		if (draggedColumn != null) {
			int draggedColumnIndex = viewIndexForColumn(draggedColumn);
			Rectangle draggedCellRect = header
					.getHeaderRect(draggedColumnIndex);

			// Draw a gray well in place of the moving column.
			g.setColor(header.getParent().getBackground());
			g.fillRect(draggedCellRect.x, draggedCellRect.y,
					draggedCellRect.width, draggedCellRect.height);

			draggedCellRect.x += header.getDraggedDistance();

			// Fill the background.
			g.setColor(header.getBackground());
			g.fillRect(draggedCellRect.x, draggedCellRect.y,
					draggedCellRect.width, draggedCellRect.height);

			paintCell(g, draggedCellRect, draggedColumnIndex, selected
					.contains(draggedColumnIndex));
		}

		// Remove all components in the rendererPane.
		rendererPane.removeAll();
	}

	/**
	 * Retrieves renderer for the specified column header.
	 * 
	 * @param columnIndex
	 *            Column index.
	 * @return Renderer for the specified column header.
	 */
	private Component getHeaderRenderer(int columnIndex) {
		TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
		TableCellRenderer renderer = aColumn.getHeaderRenderer();
		if (renderer == null) {
			renderer = header.getDefaultRenderer();
		}
		return renderer.getTableCellRendererComponent(header.getTable(),
				aColumn.getHeaderValue(), false, false, -1, columnIndex);
	}

	/**
	 * Paints cell.
	 * 
	 * @param g
	 *            Graphic context.
	 * @param cellRect
	 *            Cell rectangle.
	 * @param columnIndex
	 *            Column index.
	 * @param isSelected
	 *            Selection indication.
	 */
	private void paintCell(Graphics g, Rectangle cellRect, int columnIndex,
			boolean isSelected) {
		Component component = getHeaderRenderer(columnIndex);
		ColorSchemeEnum colorSchemeEnum = isSelected ? SubstanceLookAndFeel
				.getColorScheme() : SubstanceLookAndFeel.getColorScheme().getMetallic();
		backgroundDelegate
				.update(g, component, cellRect, colorSchemeEnum, true);
		rendererPane.paintComponent(g, component, header, cellRect.x,
				cellRect.y, cellRect.width, cellRect.height, true);
	}

	/**
	 * Retrieves view index for the specified column.
	 * 
	 * @param aColumn
	 *            Table column.
	 * @return View index for the specified column.
	 */
	private int viewIndexForColumn(TableColumn aColumn) {
		TableColumnModel cm = header.getColumnModel();
		for (int column = 0; column < cm.getColumnCount(); column++) {
			if (cm.getColumn(column) == aColumn) {
				return column;
			}
		}
		return -1;
	}

}
