package org.jvnet.substance;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import java.awt.Color;
import java.awt.Component;

/**
 * Renderer for combo boxes.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceComboBoxRenderer extends BasicComboBoxRenderer {

	private JComboBox combo;

	/**
	 * Simple constructor.
	 */
	public SubstanceComboBoxRenderer(JComboBox combo) {
		super();
		this.combo = combo;
		this.setOpaque(true);
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
		
		if (isSelected) {
			setForeground(list.getSelectionForeground());
		} else {
			if (index % 2 == 0)
				setBackground(this.combo.getBackground());
			else {
				int r = this.combo.getBackground().getRed();
				int g = this.combo.getBackground().getGreen();
				int b = this.combo.getBackground().getBlue();
				setBackground(new Color((int) (0.96 * r), (int) (0.96 * g),
						(int) (0.96 * b)));
			}
			setForeground(this.combo.getForeground());
		}

		setFont(list.getFont());

		if (value instanceof Icon) {
			setIcon((Icon) value);
		} else {
			setText((value == null) ? "" : value.toString());
		}

		// for gradient background on selected item
		this.setOpaque(!isSelected && (index >= 0));
		return this;
	}

	/**
	 * UI resource for renderer (does nothing yet).
	 * 
	 * @author Kirill Grouchnikov
	 */
	public static class SubstanceUIResource extends SubstanceComboBoxRenderer
			implements javax.swing.plaf.UIResource {
		public SubstanceUIResource(JComboBox combo) {
			super(combo);
		}

	}
}
