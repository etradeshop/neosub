package org.jvnet.substance;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalSliderUI;

import org.jvnet.substance.color.ColorSchemeEnum;

/**
 * UI for sliders in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceSliderUI extends MetalSliderUI implements Trackable {
	/**
	 * Background delegate.
	 */
	private SubstanceFillBackgroundDelegate bgDelegate;

	/**
	 * Surrogate button model for tracking the thumb transitions.
	 */
	private ButtonModel thumbModel;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new SubstanceSliderUI();
	}

	/**
	 * Simple constructor.
	 */
	public SubstanceSliderUI() {
		super();
		this.bgDelegate = new SubstanceFillBackgroundDelegate();
		this.thumbModel = new DefaultButtonModel();
		this.thumbModel.setArmed(false);
		this.thumbModel.setSelected(false);
		this.thumbModel.setPressed(false);
		this.thumbModel.setRollover(false);
	}

	/**
	 * Returns the rectangle of track for painting.
	 * 
	 * @return The rectangle of track for painting.
	 */
	private Rectangle getPaintTrackRect() {
		int trackLeft = 0, trackRight = 0, trackTop = 0, trackBottom = 0;
		if (slider.getOrientation() == JSlider.HORIZONTAL) {
			trackBottom = (trackRect.height - 1) - getThumbOverhang();
			trackTop = trackBottom - (getTrackWidth() - 1);
			trackRight = trackRect.width - 1;
		} else {
			if (slider.getComponentOrientation().isLeftToRight()) {
				trackLeft = (trackRect.width - getThumbOverhang())
						- getTrackWidth();
				trackRight = (trackRect.width - getThumbOverhang()) - 1;
			} else {
				trackLeft = getThumbOverhang();
				trackRight = getThumbOverhang() + getTrackWidth() - 1;
			}
			trackBottom = trackRect.height - 1;
		}
		return new Rectangle(trackRect.x + trackLeft, trackRect.y + trackTop,
				trackRight - trackLeft, trackBottom - trackTop);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicSliderUI#paintTrack(java.awt.Graphics)
	 */
	@Override
	public void paintTrack(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		boolean drawInverted = this.drawInverted();

		// Translate to the origin of the painting rectangle
		Rectangle paintRect = getPaintTrackRect();
		graphics.translate(paintRect.x, paintRect.y);

		// Width and height of the painting rectangle.
		int width = paintRect.width;
		int height = paintRect.height;

		ColorSchemeEnum borderColorSchemeEnum = slider.isEnabled() ? SubstanceLookAndFeel
				.getColorScheme()
				: SubstanceLookAndFeel.getColorScheme().getMetallic();

		Color borderColor1 = borderColorSchemeEnum.isDark() ? borderColorSchemeEnum
				.getColorScheme().getExtraLightColor()
				: borderColorSchemeEnum.getColorScheme().getUltraDarkColor();
		Color borderColor2 = borderColorSchemeEnum.isDark() ? borderColorSchemeEnum
				.getColorScheme().getUltraLightColor()
				: borderColorSchemeEnum.getColorScheme().getDarkColor();

		// draw border
		// graphics.setColor(borderColorSchemeEnum.getColorScheme().getDarkColor());
		graphics.setStroke(new BasicStroke((float) 1.1, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		if (slider.getOrientation() == JSlider.HORIZONTAL) {
			graphics.setPaint(new GradientPaint(0, 0, borderColor1, 0,
					height - 1, borderColor2));
			graphics.drawRoundRect(0, 0, width, height, height, height);
		} else {
			graphics.setPaint(new GradientPaint(0, 0, borderColor1, width - 1,
					0, borderColor2));
			graphics.drawRoundRect(0, 0, width, height, width, width);
		}

		Color fillColor1 = borderColorSchemeEnum.isDark() ? borderColorSchemeEnum
				.getColorScheme().getUltraLightColor()
				: borderColorSchemeEnum.getColorScheme().getUltraLightColor();
		Color fillColor2 = borderColorSchemeEnum.isDark() ? borderColorSchemeEnum
				.getColorScheme().getExtraLightColor()
				: borderColorSchemeEnum.getColorScheme().getLightColor();

		// fill selected portion
		if (slider.isEnabled()) {
			if (slider.getOrientation() == JSlider.HORIZONTAL) {
				int middleOfThumb = thumbRect.x + (thumbRect.width / 2)
						- paintRect.x;
				int fillMinX;
				int fillMaxX;

				graphics.setPaint(new GradientPaint(0, 1, fillColor1, 0,
						height - 1, fillColor2));

				if (drawInverted) {
					fillMinX = middleOfThumb;
					fillMaxX = width - 2;
				} else {
					fillMinX = 1;
					fillMaxX = middleOfThumb;
				}

				graphics.fillRoundRect(fillMinX, 1, fillMaxX - fillMinX + 2,
						height - 1, (height - 2), (height - 2));
			} else {
				int middleOfThumb = thumbRect.y + (thumbRect.height / 2)
						- paintRect.y;
				int fillMinY;
				int fillMaxY;

				graphics.setPaint(new GradientPaint(1, 0, fillColor1,
						width - 1, 0, fillColor2));

				if (drawInverted()) {
					fillMinY = 1;
					fillMaxY = middleOfThumb;
				} else {
					fillMinY = middleOfThumb;
					fillMaxY = height - 2;
				}
				graphics.fillRoundRect(1, fillMinY, width - 1, fillMaxY
						- fillMinY + 2, (width - 2), (width - 2));
			}
		}

		g.translate(-paintRect.x, -paintRect.y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	@Override
	public synchronized void paint(Graphics g, JComponent c) {
		// important to synchronize on the slider as we are
		// about to fiddle with its opaqueness
		synchronized (c) {
			this.bgDelegate.update(g, c);
			// remove opaqueness
			boolean isOpaque = c.isOpaque();
			c.setOpaque(false);
			super.paint(g, c);
			// restore opaqueness
			c.setOpaque(isOpaque);
		}
	}

	/**
	 * Returns the button model for tracking the thumb transitions.
	 * 
	 * @return Button model for tracking the thumb transitions.
	 */
	public ButtonModel getButtonModel() {
		return this.thumbModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.Trackable#isInside(java.awt.event.MouseEvent)
	 */
	public boolean isInside(MouseEvent me) {
		Rectangle thumbB = this.thumbRect;
		if (thumbB == null)
			return false;
		return thumbB.contains(me.getX(), me.getY());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicSliderUI#installListeners(javax.swing.JSlider)
	 */
	@Override
	protected void installListeners(JSlider slider) {
		super.installListeners(slider);

		RolloverControlListener listener = new RolloverControlListener(this,
				this.thumbModel);
		slider.addMouseListener(listener);
		slider.addMouseMotionListener(listener);
	}

}
