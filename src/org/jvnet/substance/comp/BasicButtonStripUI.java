package org.jvnet.substance.comp;

import java.awt.*;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

import org.jvnet.substance.comp.JButtonStrip.StripOrientation;

/**
 * Basic UI for button strip {@link org.jvnet.substance.comp.JButtonStrip}.
 * 
 * @author Kirill Grouchnikov
 */
public class BasicButtonStripUI extends ButtonStripUI {
	/**
	 * The associated button strip.
	 */
	protected JButtonStrip buttonStrip;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new BasicButtonStripUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#installUI(javax.swing.JComponent)
	 */
	public void installUI(JComponent c) {
		this.buttonStrip = (JButtonStrip) c;
		c.setLayout(createLayoutManager());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
	 */
	public void uninstallUI(JComponent c) {
		c.setLayout(null);

		this.buttonStrip = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	@Override
	public void paint(Graphics g, JComponent c) {
		int buttonCount = this.buttonStrip.getButtonCount();
		for (int i = 0; i < buttonCount; i++) {
			AbstractButton currButton = this.buttonStrip.getButton(i);
			boolean isFirst = this.buttonStrip.isFirst(currButton);
			boolean isLast = this.buttonStrip.isLast(currButton);
			Graphics2D graphics = (Graphics2D) g.create(currButton.getX(),
					currButton.getY(), currButton.getWidth(), currButton
							.getHeight());
			if (this.buttonStrip.getOrientation() == StripOrientation.HORIZONTAL) {
				this.paintStripButton(graphics, currButton, isFirst, isLast, c
						.getWidth(), currButton.getX());
			}
			else {
				this.paintStripButton(graphics, currButton, isFirst, isLast, c
						.getHeight(), currButton.getY());
			}
			graphics.dispose();
		}
	}

	/**
	 * Paints a single strip button. This function should provide continuous
	 * appearance throughout the whole strip (especially if the painting uses
	 * gradients).
	 * 
	 * @param g
	 *            Graphics context.
	 * @param button
	 *            The button itself.
	 * @param isFirst
	 *            If <code>true</code>, the button is the first button in its
	 *            strip.
	 * @param isLast
	 *            If <code>true</code>, the button is the last button in its
	 *            strip.
	 * @param totalStripDimension
	 *            The total width / height of the button strip in pixels.
	 * @param relativeOffset
	 *            The location of the button relative to the strip.
	 */
	protected void paintStripButton(Graphics g, AbstractButton button,
			boolean isFirst, boolean isLast, int totalStripDimension,
			int relativeOffset) {

		this.paintStripButtonBackground(g, button, isFirst, isLast,
				totalStripDimension, relativeOffset);

		// get the original UI delegate
		BasicButtonUI buttonUI = (BasicButtonUI) button.getUI();
		// and ask it to paint the button contents
		buttonUI.paint(g, button);

		this.paintStripButtonBorder(g, button, isFirst, isLast);
	}

	/**
	 * Paints background of a single strip button. This function should provide
	 * continuous appearance throughout the whole strip (especially if the
	 * painting uses gradients).
	 * 
	 * @param g
	 *            Graphics context.
	 * @param button
	 *            The button itself.
	 * @param isFirst
	 *            If <code>true</code>, the button is the first button in its
	 *            strip.
	 * @param isLast
	 *            If <code>true</code>, the button is the last button in its
	 *            strip.
	 * @param totalStripDimension
	 *            The total width / height of the button strip in pixels.
	 * @param relativeOffset
	 *            The location of the button relative to the strip.
	 */
	protected void paintStripButtonBackground(Graphics g,
			AbstractButton button, boolean isFirst, boolean isLast,
			int totalStripDimension, int relativeOffset) {
		if (button.isOpaque()) {
			g.setColor(button.getBackground());
			g.fillRect(0, 0, button.getWidth(), button.getHeight());
		}
	}

	/**
	 * Paints border of a single strip button. This function should provide
	 * continuous appearance throughout the whole strip.
	 * 
	 * @param g
	 *            Graphics context.
	 * @param button
	 *            The button itself.
	 * @param isFirst
	 *            If <code>true</code>, the button is the first button in its
	 *            strip.
	 * @param isLast
	 *            If <code>true</code>, the button is the last button in its
	 *            strip.
	 */
	protected void paintStripButtonBorder(Graphics g, AbstractButton button,
			boolean isFirst, boolean isLast) {
		Border border = button.getBorder();
		if (border != null)
			border.paintBorder(button, g, 0, 0, button.getWidth(), button
					.getHeight());
	}

	/**
	 * Invoked by <code>installUI</code> to create a layout manager object to
	 * manage the {@link JButtonStrip}.
	 * 
	 * @return a layout manager object
	 * 
	 * @see ButtonStripLayout
	 */
	protected LayoutManager createLayoutManager() {
		return new ButtonStripLayout();
	}

	/**
	 * Layout for the button strip.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private class ButtonStripLayout implements LayoutManager {
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
			int width = 0;
			int height = 0;
			if (buttonStrip.getOrientation() == StripOrientation.HORIZONTAL) {
				for (int i = 0; i < buttonStrip.getButtonCount(); i++) {
					width += buttonStrip.getButton(i).getPreferredSize().width;
					height = Math.max(height, buttonStrip.getButton(i)
							.getPreferredSize().height);
				}
			} else {
				for (int i = 0; i < buttonStrip.getButtonCount(); i++) {
					height += buttonStrip.getButton(i).getPreferredSize().height;
					width = Math.max(width, buttonStrip.getButton(i)
							.getPreferredSize().width);
				}
			}
			return new Dimension(width, height);
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
			if (buttonStrip.getOrientation() == StripOrientation.HORIZONTAL) {
				int x = 0;
				int height = c.getHeight();
				for (int i = 0; i < buttonStrip.getButtonCount(); i++) {
					AbstractButton currButton = buttonStrip.getButton(i);
					currButton.setBounds(x, 0,
							currButton.getPreferredSize().width, height);
					x += currButton.getPreferredSize().width;
				}
			} else {
				int y = 0;
				int width = c.getWidth();
				for (int i = 0; i < buttonStrip.getButtonCount(); i++) {
					AbstractButton currButton = buttonStrip.getButton(i);
					currButton.setBounds(0, y, width, currButton
							.getPreferredSize().height);
					y += currButton.getPreferredSize().height;
				}
			}
		}
	}
}
