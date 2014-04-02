package org.jvnet.substance;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

import org.jvnet.substance.color.ColorSchemeEnum;

/**
 * UI for tabbed panes in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceTabbedPaneUI extends MetalTabbedPaneUI {
	/**
	 * Hash map for storing already computed backgrounds.
	 */
	private static Map<String, BufferedImage> backgroundMap = new HashMap<String, BufferedImage>();

	/**
	 * Resets image maps (used when setting new theme).
	 * 
	 * @see SubstanceLookAndFeel#setCurrentTheme(String)
	 * @see SubstanceLookAndFeel#setCurrentTheme(SubstanceTheme)
	 */
	static synchronized void reset() {
		backgroundMap.clear();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent tabPane) {
		return new SubstanceTabbedPaneUI();
	}

	/**
	 * Retrieves tab background.
	 * 
	 * @param width Tab width.
	 * @param height Tab height.
	 * @param cyclePos Tab cycle position (for rollover effects).
	 * @param side Tab open side.
	 * @param colorSchemeEnum Color scheme for coloring the background.
	 * @return Tab background of specified parameters.
	 */
	private static synchronized BufferedImage getTabBackground(int width,
			int height, int cyclePos, SubstanceImageCreator.Side side,
			ColorSchemeEnum colorSchemeEnum) {
		String key = width + ":" + height + ":" + cyclePos + ":"
				+ side.toString() + ":" + colorSchemeEnum.toString();
		BufferedImage result = backgroundMap.get(key);
		if (result == null) {
			result = SubstanceImageCreator.getRoundedBackground(width, height,
					height/3, colorSchemeEnum, colorSchemeEnum, cyclePos, side, true,
					true);
			backgroundMap.put(key, result);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintTabBackground(java.awt.Graphics,
	 *      int, int, int, int, int, int, boolean)
	 */
	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement,
			int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		BufferedImage backgroundImage = null;
		ColorSchemeEnum colorScheme = isSelected ? SubstanceLookAndFeel.getColorScheme()
				: SubstanceLookAndFeel.getColorScheme().getMetallic();
		int cyclePos = 0;
//		Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
//		Point winLoc = this.tabPane.getLocationOnScreen();
//		int mx = mouseLoc.x - winLoc.x;
//		int my = mouseLoc.y - winLoc.y;
//		if ((mx >= x) && (mx <= (x + w)) && (my >= y) && (my <= (y + h))) {
//			cyclePos = 4;
//		}
		switch (tabPlacement) {
		case LEFT:
			if (!isSelected) {
//				h -= 2;
//				y++;
			}
			backgroundImage = getTabBackground(w, h, cyclePos,
					SubstanceImageCreator.Side.RIGHT, colorScheme);
			y++;
			break;
		case RIGHT:
			if (!isSelected) {
//				h -= 2;
//				y++;
			}
			backgroundImage = getTabBackground(w, h, cyclePos,
					SubstanceImageCreator.Side.LEFT, colorScheme);
			break;
		case BOTTOM:
			if (!isSelected) {
//				w -= 2;
//				x++;
			}
			backgroundImage = getTabBackground(w, h, cyclePos,
					SubstanceImageCreator.Side.BOTTOM, colorScheme);
			backgroundImage = SubstanceImageCreator.getRotated(backgroundImage,
					2);
			break;
		case TOP:
		default:
			if (!isSelected) {
//				w -= 2;
//				x++;
			}
			backgroundImage = getTabBackground(w, h, cyclePos,
					SubstanceImageCreator.Side.BOTTOM, colorScheme);
		}

		if (backgroundImage != null) {
			g.drawImage(backgroundImage, x, y, null);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintFocusIndicator(java.awt.Graphics, int, java.awt.Rectangle[], int, java.awt.Rectangle, java.awt.Rectangle, boolean)
	 */
	@Override
	protected void paintFocusIndicator(Graphics g, int tabPlacement,
			Rectangle[] rects, int tabIndex, Rectangle iconRect,
			Rectangle textRect, boolean isSelected) {
		// empty to remove Metal functionality
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.metal.MetalTabbedPaneUI#paintHighlightBelowTab()
	 */
	@Override
	protected void paintHighlightBelowTab() {
		// empty to remove Metal functionality
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.metal.MetalTabbedPaneUI#paintLeftTabBorder(int, java.awt.Graphics, int, int, int, int, int, int, boolean)
	 */
	@Override
	protected void paintLeftTabBorder(int tabIndex, Graphics g, int x, int y,
			int w, int h, int btm, int rght, boolean isSelected) {
		// empty to remove Metal functionality
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.metal.MetalTabbedPaneUI#paintRightTabBorder(int, java.awt.Graphics, int, int, int, int, int, int, boolean)
	 */
	@Override
	protected void paintRightTabBorder(int tabIndex, Graphics g, int x, int y,
			int w, int h, int btm, int rght, boolean isSelected) {
		// empty to remove Metal functionality
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.metal.MetalTabbedPaneUI#paintTopTabBorder(int, java.awt.Graphics, int, int, int, int, int, int, boolean)
	 */
	@Override
	protected void paintTopTabBorder(int tabIndex, Graphics g, int x, int y,
			int w, int h, int btm, int rght, boolean isSelected) {
		// empty to remove Metal functionality
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.metal.MetalTabbedPaneUI#paintBottomTabBorder(int, java.awt.Graphics, int, int, int, int, int, int, boolean)
	 */
	@Override
	protected void paintBottomTabBorder(int tabIndex, Graphics g, int x, int y,
			int w, int h, int btm, int rght, boolean isSelected) {
		// empty to remove Metal functionality
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintTabBorder(java.awt.Graphics,
	 *      int, int, int, int, int, int, boolean)
	 */
	@Override
	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
			int x, int y, int w, int h, boolean isSelected) {
		// empty to remove Metal functionality
	}

    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#createScrollButton(int)
     */
	@Override
    protected JButton createScrollButton(int direction) {
		Icon icon = SubstanceImageCreator.getArrowIcon(9, 5,
				direction, SubstanceLookAndFeel.getColorScheme());

        return new SubstanceScrollButton(icon, direction);
    }
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#createChangeListener()
	 */
	@Override
	protected ChangeListener createChangeListener() {
		return new TabSelectionHandler();
	}

	/**
	 * Handler for tab selection.
	 * 
	 * @author Kirill Grouchnikov
	 */
	protected class TabSelectionHandler implements ChangeListener {
		/* (non-Javadoc)
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		public void stateChanged(ChangeEvent e) {
			JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
			tabbedPane.revalidate();
			tabbedPane.repaint();
		}
	}
	
}