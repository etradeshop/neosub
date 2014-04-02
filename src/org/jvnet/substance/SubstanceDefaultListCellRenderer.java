package org.jvnet.substance;

import java.awt.Color;
import java.awt.Component;

import javax.swing.*;

/**
 * Default renderer for list cells.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceDefaultListCellRenderer extends DefaultListCellRenderer {

	/**
	 * Constructs a default renderer object for an item in a list.
	 */
	public SubstanceDefaultListCellRenderer() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList,
	 *      java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setComponentOrientation(list.getComponentOrientation());
		if (isSelected) {
			// setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			if (index % 2 == 0)
				setBackground(list.getBackground());
			else {
				int r = list.getBackground().getRed();
				int g = list.getBackground().getGreen();
				int b = list.getBackground().getBlue();
				setBackground(new Color((int) (0.96 * r), (int) (0.96 * g),
						(int) (0.96 * b)));
			}
			setForeground(list.getForeground());
		}

		if (value instanceof Icon) {
			setIcon((Icon) value);
			setText("");
		} else {
			setIcon(null);
			setText((value == null) ? "" : value.toString());
		}

		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setBorder(noFocusBorder);

		return this;
	}

	/**
	 * UI resource for renderer (does nothing yet).
	 * 
	 * @author Kirill Grouchnikov
	 */
	public static class SubstanceUIResource extends
			SubstanceDefaultListCellRenderer implements
			javax.swing.plaf.UIResource {
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList,
		 *      java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			return super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);
		}
	}

}
