package org.jvnet.substance;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.metal.MetalSplitPaneUI;

/**
 * UI for split panes in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceSplitPaneUI extends MetalSplitPaneUI {
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent x) {
		return new SubstanceSplitPaneUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicSplitPaneUI#createDefaultDivider()
	 */
	@Override
	public BasicSplitPaneDivider createDefaultDivider() {
		return new SubstanceSplitPaneDivider(this);
	}
}
