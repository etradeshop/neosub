package org.jvnet.substance;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Default renderer for table cells.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceDefaultTableCellRenderer extends DefaultTableCellRenderer {
	/*
	 * Place to store the color the JLabel should be returned to after its
	 * foreground and background colors have been set to the selection
	 * background color.
	 */
	private Color unselectedForeground;

	/*
	 * Place to store the color the JLabel should be returned to after its
	 * foreground and background colors have been set to the selection
	 * background color.
	 */
	private Color unselectedBackground;

	/**
	 * Renderer for boolean columns.
	 *
	 * @author Kirill Grouchnikov
	 */
	static class BooleanRenderer extends JCheckBox implements TableCellRenderer {
		private static final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

		public BooleanRenderer() {
			super();
			setHorizontalAlignment(JLabel.CENTER);
			setBorderPainted(true);
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				super.setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				Color backgroundToSet = table.getBackground();
				if (row % 2 != 0) {
					int r = backgroundToSet.getRed();
					int g = backgroundToSet.getGreen();
					int b = backgroundToSet.getBlue();
					backgroundToSet = new Color((int) (0.96 * r),
							(int) (0.96 * g), (int) (0.96 * b));
				}
				super.setBackground(backgroundToSet);
			}
			setSelected((value != null && ((Boolean) value).booleanValue()));

			if (hasFocus) {
				setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
			} else {
				setBorder(noFocusBorder);
			}

			return this;
		}
	}

	/**
	 * Renderer for icon columns.
	 *
	 * @author Kirill Grouchnikov
	 */
	public static class IconRenderer extends SubstanceDefaultTableCellRenderer {
		public IconRenderer() {
			super();
			setHorizontalAlignment(JLabel.CENTER);
		}

		public void setValue(Object value) {
			setIcon((value instanceof Icon) ? (Icon) value : null);
			setText(null);
		}
	}

	/**
	 * Renderer for number columns.
	 *
	 * @author Kirill Grouchnikov
	 */
	public static class NumberRenderer extends
			SubstanceDefaultTableCellRenderer {
		public NumberRenderer() {
			super();
			setHorizontalAlignment(JLabel.RIGHT);
		}
	}

	/**
	 * Renderer for double columns.
	 *
	 * @author Kirill Grouchnikov
	 */
	public static class DoubleRenderer extends NumberRenderer {
		NumberFormat formatter;

		public DoubleRenderer() {
			super();
		}

		public void setValue(Object value) {
			if (formatter == null) {
				formatter = NumberFormat.getInstance();
			}
			setText((value == null) ? "" : formatter.format(value));
		}
	}

	/**
	 * Renderer for date columns.
	 *
	 * @author Kirill Grouchnikov
	 */
	public static class DateRenderer extends SubstanceDefaultTableCellRenderer {
		DateFormat formatter;

		public DateRenderer() {
			super();
		}

		public void setValue(Object value) {
			if (formatter == null) {
				formatter = DateFormat.getDateInstance();
			}
			setText((value == null) ? "" : formatter.format(value));
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setForeground(java.awt.Color)
	 */
	public void setForeground(Color c) {
		super.setForeground(c);
		unselectedForeground = c;
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setBackground(java.awt.Color)
	 */
	public void setBackground(Color c) {
		super.setBackground(c);
		unselectedBackground = c;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		if (isSelected) {
			super.setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		} else {
			super
					.setForeground((unselectedForeground != null) ? unselectedForeground
							: table.getForeground());
			Color backgroundToSet = (unselectedBackground != null) ? unselectedBackground
					: table.getBackground();
			if (row % 2 != 0) {
				int r = backgroundToSet.getRed();
				int g = backgroundToSet.getGreen();
				int b = backgroundToSet.getBlue();
				backgroundToSet = new Color((int) (0.96 * r), (int) (0.96 * g),
						(int) (0.96 * b));
			}
			super.setBackground(backgroundToSet);
		}

		setFont(table.getFont());

		if (hasFocus) {
			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
			if (!isSelected && table.isCellEditable(row, column)) {
				Color col;
				col = UIManager.getColor("Table.focusCellForeground");
				if (col != null) {
					super.setForeground(col);
				}
				col = UIManager.getColor("Table.focusCellBackground");
				if (col != null) {
					super.setBackground(col);
				}
			}
		} else {
			setBorder(noFocusBorder);
		}

		setValue(value);

		return this;
	}
}
