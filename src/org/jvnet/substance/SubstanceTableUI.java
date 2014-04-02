package org.jvnet.substance;

import java.util.Date;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableUI;

/**
 * UI for tables in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceTableUI extends BasicTableUI {
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		JTable table = (JTable) c;
		// Override default renderers
		table.setDefaultRenderer(Object.class,
				new SubstanceDefaultTableCellRenderer());
		table.setDefaultRenderer(Icon.class,
				new SubstanceDefaultTableCellRenderer.IconRenderer());
		table.setDefaultRenderer(ImageIcon.class,
				new SubstanceDefaultTableCellRenderer.IconRenderer());
		table.setDefaultRenderer(Number.class,
				new SubstanceDefaultTableCellRenderer.NumberRenderer());
		table.setDefaultRenderer(Float.class,
				new SubstanceDefaultTableCellRenderer.DoubleRenderer());
		table.setDefaultRenderer(Double.class,
				new SubstanceDefaultTableCellRenderer.DoubleRenderer());
		table.setDefaultRenderer(Date.class,
				new SubstanceDefaultTableCellRenderer.DateRenderer());
		// fix for bug 56 - making default renderer for Boolean a check box.
		table.setDefaultRenderer(Boolean.class,
				new SubstanceDefaultTableCellRenderer.BooleanRenderer());

		return new SubstanceTableUI();
	}
}
