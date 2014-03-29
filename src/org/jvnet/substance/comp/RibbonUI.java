package org.jvnet.substance.comp;

import java.awt.Rectangle;

import javax.swing.plaf.ComponentUI;

/**
 * UI for ribbon ({@link org.jvnet.substance.comp.JRibbon}).
 * 
 * @author Kirill Grouchnikov
 */
public abstract class RibbonUI extends ComponentUI {
	public abstract int tabForCoordinate(JButtonStrip pane, int x, int y);

	public abstract Rectangle getTabBounds(JButtonStrip pane, int index);
}
