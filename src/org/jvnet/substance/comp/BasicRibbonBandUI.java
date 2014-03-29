package org.jvnet.substance.comp;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;

/**
 * Basic UI for ribbon band {@link org.jvnet.substance.comp.JRibbonBand}.
 * 
 * @author Kirill Grouchnikov
 */
public class BasicRibbonBandUI extends RibbonBandUI {
	/**
	 * The height of the band header (title).
	 */
	public static final int BAND_HEADER_HEIGHT = 18;

	/**
	 * The height of the band control panel.
	 */
	public static final int BAND_CONTROL_PANEL_HEIGHT = 68;

	/**
	 * The associated ribbon band.
	 */
	protected JRibbonBand ribbonBand;

	/**
	 * Indication whether the mouse pointer is over the associated ribbon band.
	 */
	protected boolean isUnderMouse;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new BasicRibbonBandUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#installUI(javax.swing.JComponent)
	 */
	public void installUI(JComponent c) {
		this.ribbonBand = (JRibbonBand) c;
		this.ribbonBand.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				isUnderMouse = true;
				ribbonBand.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// check if mouse is still on one of the child components
				if (ribbonBand.contains(e.getPoint()))
					return;

				isUnderMouse = false;
				ribbonBand.repaint();
			}
		});
		if (this.ribbonBand.expandButton != null) {
			this.ribbonBand.expandButton
					.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									isUnderMouse = false;
									ribbonBand.repaint();
								}
							});
						}
					});
		}
		installDefaults(this.ribbonBand);
		c.setLayout(createLayoutManager());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
	 */
	public void uninstallUI(JComponent c) {
		c.setLayout(null);
		uninstallDefaults((JRibbonBand) c);
		this.ribbonBand = null;
	}

	/**
	 * Installs default parameters on the specified ribbon band.
	 * 
	 * @param rb
	 *            Ribbon band.
	 */
	protected void installDefaults(JRibbonBand rb) {
		LookAndFeel.installColorsAndFont(rb, "RibbonBand.background",
				"RibbonBand.foreground", "RibbonBand.font");
		LookAndFeel.installBorder(rb, "RibbonBand.border");
	}

	/**
	 * Uninstalls default parameters from the specified ribbon band.
	 * 
	 * @param rb
	 *            Ribbon band.
	 */
	protected void uninstallDefaults(JRibbonBand rb) {
		LookAndFeel.uninstallBorder(rb);
	}

	/**
	 * Invoked by <code>installUI</code> to create a layout manager object to
	 * manage the {@link JButtonStrip}.
	 * 
	 * @return a layout manager object
	 * 
	 * @see RibbonBandLayout
	 */
	protected LayoutManager createLayoutManager() {
		return new RibbonBandLayout();
	}

	/**
	 * Layout for the ribbon band.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private class RibbonBandLayout implements LayoutManager {

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
			JRibbonBand jrb = (JRibbonBand) c;
			return new Dimension(
					jrb.getControlPanel().getPreferredSize().width,
					BasicRibbonBandUI.BAND_HEADER_HEIGHT
							+ BasicRibbonBandUI.BAND_CONTROL_PANEL_HEIGHT);
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
			// need place for border
			ribbonBand.getControlPanel().setBounds(1, BAND_HEADER_HEIGHT,
					c.getWidth() - 2, BAND_CONTROL_PANEL_HEIGHT - 1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	@Override
	public void paint(Graphics g, JComponent c) {
		Graphics2D graphics = (Graphics2D) g.create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);

		String title = ribbonBand.getTitle();

		this.paintBandTitle(graphics, new Rectangle(0, 0, c.getWidth(),
				BAND_HEADER_HEIGHT), title);

		this.paintBandBackground(graphics, new Rectangle(0, BAND_HEADER_HEIGHT,
				c.getWidth(), BAND_CONTROL_PANEL_HEIGHT));

		graphics.dispose();
	}

	/**
	 * Paints band title pane.
	 * 
	 * @param g
	 *            Graphics context.
	 * @param titleRectangle
	 *            Rectangle for the title pane.
	 * @param title
	 *            Title string.
	 */
	protected void paintBandTitle(Graphics g, Rectangle titleRectangle,
			String title) {

		Graphics2D graphics = (Graphics2D) g.create();
		Color ribbonBandBackground = ribbonBand.getBackground();
		double topCoef = isUnderMouse ? 0.4 : 0.28;
		double bottomCoef = isUnderMouse ? 0.6 : 0.4;
		Color topColor = new Color((int) (topCoef * ribbonBandBackground
				.getRed()), (int) (topCoef * ribbonBandBackground.getGreen()),
				(int) (topCoef * ribbonBandBackground.getBlue()));
		Color bottomColor = new Color((int) (bottomCoef * ribbonBandBackground
				.getRed()),
				(int) (bottomCoef * ribbonBandBackground.getGreen()),
				(int) (bottomCoef * ribbonBandBackground.getBlue()));

		graphics.setPaint(new GradientPaint(titleRectangle.x, titleRectangle.y,
				topColor, titleRectangle.x, titleRectangle.y
						+ titleRectangle.height, bottomColor));

		graphics.fillRect(titleRectangle.x, titleRectangle.y,
				titleRectangle.width, titleRectangle.height);
		graphics.setFont(new Font("Tahoma", Font.BOLD, 11));
		int y = titleRectangle.y
				+ (titleRectangle.height + graphics.getFontMetrics()
						.getAscent()) / 2;
		graphics.setColor(topColor);
		graphics.drawString(title, titleRectangle.x + 3, y);
		graphics.setColor(ribbonBandBackground);
		graphics.drawString(title, titleRectangle.x + 2, y - 1);
		graphics.dispose();
	}

	/**
	 * Paints band background.
	 * 
	 * @param graphics
	 *            Graphics context.
	 * @param toFill
	 *            Rectangle for the background.
	 */
	protected void paintBandBackground(Graphics graphics, Rectangle toFill) {
		graphics.setColor(ribbonBand.getBackground());
		graphics.fillRect(toFill.x, toFill.y, toFill.width, toFill.height);
	}
}
