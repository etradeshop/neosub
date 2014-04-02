package org.jvnet.substance;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSpinnerUI;

/**
 * UI for spinners in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceSpinnerUI extends BasicSpinnerUI {
	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new SubstanceSpinnerUI();
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicSpinnerUI#createNextButton()
	 */
	@Override
	protected Component createNextButton() {
		JComponent c = new SubstanceSpinnerButton(spinner,
				SubstanceImageCreator.getArrowIcon(7, 5, SwingConstants.NORTH, SubstanceLookAndFeel.getColorScheme()),
				SwingConstants.NORTH);
		c.setPreferredSize(new Dimension(16, 16));
		c.setMinimumSize(new Dimension(5, 5));

		installNextButtonListeners(c);
		return c;
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicSpinnerUI#createPreviousButton()
	 */
	@Override
	protected Component createPreviousButton() {
		JComponent c = new SubstanceSpinnerButton(spinner,
				SubstanceImageCreator.getArrowIcon(7, 5, SwingConstants.SOUTH, SubstanceLookAndFeel.getColorScheme()),
				SwingConstants.SOUTH);
		c.setPreferredSize(new Dimension(16, 16));
		c.setMinimumSize(new Dimension(5, 5));
		installPreviousButtonListeners(c);
		return c;
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicSpinnerUI#installListeners()
	 */
	@Override
	protected void installListeners() {
		super.installListeners();
		JComponent editor = spinner.getEditor();
		if (editor != null && editor instanceof JSpinner.DefaultEditor) {
			JTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
			if (tf != null) {
				tf.setBorder(new EmptyBorder(0, 1, 0, 1));
			}
		}
	}
}
