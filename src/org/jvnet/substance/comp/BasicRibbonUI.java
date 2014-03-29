package org.jvnet.substance.comp;

import java.awt.*;

import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.plaf.ComponentUI;

/**
 * Basic UI for ribbon {@link org.jvnet.substance.comp.JRibbon}.
 * 
 * @author Kirill Grouchnikov
 */
public class BasicRibbonUI extends RibbonUI {
	/**
	 * Height of the task bar.
	 */
	public static final int TASKBAR_HEIGHT = 22;

	/**
	 * Minimum width of a single ribbon band.
	 */
	public static final int BAND_MIN_WIDTH = 30;

	/**
	 * The associated ribbon.
	 */
	protected JRibbon ribbon;

	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new BasicRibbonUI();
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#installUI(javax.swing.JComponent)
	 */
	public void installUI(JComponent c) {
		this.ribbon = (JRibbon) c;
		c.setLayout(createLayoutManager());
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
	 */
	public void uninstallUI(JComponent c) {
		c.setLayout(null);

		this.ribbon = null;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.comp.RibbonUI#getTabBounds(org.jvnet.substance.comp.JButtonStrip, int)
	 */
	@Override
	public Rectangle getTabBounds(JButtonStrip pane, int index) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.comp.RibbonUI#tabForCoordinate(org.jvnet.substance.comp.JButtonStrip, int, int)
	 */
	@Override
	public int tabForCoordinate(JButtonStrip pane, int x, int y) {
		return 0;
	}

	/**
	 * Invoked by <code>installUI</code> to create a layout manager object to
	 * manage the {@link JRibbon}.
	 * 
	 * @return a layout manager object
	 * 
	 * @see RibbonLayout
	 */
	protected LayoutManager createLayoutManager() {
		return new RibbonLayout();
	}

	/**
	 * Layout for the ribbon.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private class RibbonLayout implements LayoutManager {
		/**
		 * Total ribbon height.
		 */
		public static final int TOTAL_HEIGHT = TASKBAR_HEIGHT
				+ BasicRibbonBandUI.BAND_HEADER_HEIGHT
				+ BasicRibbonBandUI.BAND_CONTROL_PANEL_HEIGHT;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
		 *      java.awt.Component)
		 */
		public void addLayoutComponent(String name, Component c) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
		 */
		public void removeLayoutComponent(Component c) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
		 */
		public Dimension preferredLayoutSize(Container c) {
			return new Dimension(c.getWidth(), TOTAL_HEIGHT);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
		 */
		public Dimension minimumLayoutSize(Container c) {
			return this.preferredLayoutSize(c);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
		 */
		public void layoutContainer(Container c) {
			// the top row - regular components and task buttons
			int x = 2;
			for (Component regComp : ribbon.getRegularComponents()) {
				int pw = regComp.getPreferredSize().width;
				regComp.setBounds(x, 0, pw, TASKBAR_HEIGHT);
				x += (pw + 2);
			}
			// task buttons
			for (JToggleButton taskToggleButton : ribbon.getTaskToggleButtons()) {
				int pw = taskToggleButton.getPreferredSize().width;
				taskToggleButton.setBounds(x, 0, pw, TASKBAR_HEIGHT);
				x += (pw + 10);
			}

			// the bottom row is panels
			int totalPreferredWidth = 0;
			for (JRibbonBand panel : ribbon.getBands()) {
				int pw = Math.max(BAND_MIN_WIDTH,
						panel.getPreferredSize().width);
				totalPreferredWidth += (pw + 2);
			}

			double coef = (double) c.getWidth() / totalPreferredWidth;
			// maximum 20% more than preferred width
			if (coef >= 1.2)
				coef = 1.2;

			x = 0;
			for (JRibbonBand panel : ribbon.getBands()) {
				int pw = Math.max(BAND_MIN_WIDTH,
						panel.getPreferredSize().width);
				int fw = (int) (coef * pw);
				panel.setBounds(x, TASKBAR_HEIGHT, fw,
						BasicRibbonBandUI.BAND_HEADER_HEIGHT
								+ BasicRibbonBandUI.BAND_CONTROL_PANEL_HEIGHT);
				panel.getControlPanel().setBounds(1,
						BasicRibbonBandUI.BAND_HEADER_HEIGHT, fw - 2,
						BasicRibbonBandUI.BAND_CONTROL_PANEL_HEIGHT - 1);

				if (panel.expandButton != null) {
					int ebpw = panel.expandButton.getPreferredSize().width;
					int ebph = panel.expandButton.getPreferredSize().height;
					panel.expandButton.setBounds(fw - 4 - ebpw,
							(BasicRibbonBandUI.BAND_HEADER_HEIGHT - ebph) / 2,
							ebpw, ebph);
				}

				panel.doLayout();
				x += (fw + 2);
			}
		}
	}
}
