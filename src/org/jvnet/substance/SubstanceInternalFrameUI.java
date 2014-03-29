package org.jvnet.substance;

import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalInternalFrameUI;

/**
 * UI for internal frames in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceInternalFrameUI extends MetalInternalFrameUI {
	/**
	 * Title pane
	 */
	private SubstanceInternalFrameTitlePane titlePane;

	/**
	 * Simple constructor.
	 * 
	 * @param b Associated internal frame.
	 */
	public SubstanceInternalFrameUI(JInternalFrame b) {
		super(b);
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new SubstanceInternalFrameUI((JInternalFrame) c);
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicInternalFrameUI#createNorthPane(javax.swing.JInternalFrame)
	 */
	@Override
	protected JComponent createNorthPane(JInternalFrame w) {
		titlePane = new SubstanceInternalFrameTitlePane(w);
		return titlePane;
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.metal.MetalInternalFrameUI#setPalette(boolean)
	 */
	@Override
	public void setPalette(boolean isPalette) {
		if (isPalette) {
			LookAndFeel.installBorder(frame, "InternalFrame.paletteBorder");
		} else {
			LookAndFeel.installBorder(frame, "InternalFrame.border");
		}
		titlePane.setPalette(isPalette);
	}
}
