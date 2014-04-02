package org.jvnet.substance.watermark;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.watermark.SubstanceWatermark;

/**
 * Implementation of {@link org.jvnet.substance.watermark.SubstanceWatermark},
 * drawing random coffee beans as watermark.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceCoffeeBeansWatermark implements SubstanceWatermark {
	/**
	 * Watermark image (screen-sized).
	 */
	private static Image watermarkImage = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#drawWatermarkImage(java.awt.Graphics,
	 *      int, int, int, int)
	 */
	public void drawWatermarkImage(Graphics graphics, Component c, int x,
			int y, int width, int height) {
		int dx = c.getLocationOnScreen().x;
		int dy = c.getLocationOnScreen().y;
		graphics.drawImage(SubstanceCoffeeBeansWatermark.watermarkImage, x, y,
				x + width, y + height, x + dx, y + dy, x + dx + width, y + dy
						+ height, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#updateWatermarkImage()
	 */
	public boolean updateWatermarkImage() {
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenDim.width;
		int screenHeight = screenDim.height;
		SubstanceCoffeeBeansWatermark.watermarkImage = SubstanceImageCreator
				.getBlankImage(screenWidth, screenHeight);

		Graphics2D graphics = (Graphics2D) SubstanceCoffeeBeansWatermark.watermarkImage
				.getGraphics().create();
		Color stampColor = SubstanceLookAndFeel.getColorScheme().isDark() ? new Color(
				255, 255, 255, 25)
				: new Color(0, 0, 0, 15);

		int minBubbleRadius = 10;
		int maxBubbleRadius = 20;

		int cellSize = (minBubbleRadius + maxBubbleRadius);
		int rowCount = screenHeight / cellSize;
		int columnCount = screenWidth / cellSize;

		graphics.setColor(stampColor);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		for (int col = 0; col <= columnCount; col++) {
			for (int row = 0; row <= rowCount; row++) {
				// location
				int xc = (int) (col * cellSize + cellSize * Math.random());
				int yc = (int) (row * cellSize + cellSize * Math.random());
				int r = minBubbleRadius
						+ (int) (Math.random() * (maxBubbleRadius - minBubbleRadius));

				AffineTransform oldTransform = graphics.getTransform();
				graphics.setTransform(AffineTransform.getRotateInstance(2.0
						* Math.PI * Math.random(), xc, yc));

				GeneralPath bean = new GeneralPath();
				bean.moveTo(xc - r, yc - 0.1f * r);
				bean.quadTo(xc - r, yc - 0.6f * r, xc, yc - 0.6f * r);
				bean.quadTo(xc + r, yc - 0.6f * r, xc + r, yc - 0.1f * r);
				bean.lineTo(xc + r, yc + 0.1f * r);
				bean.quadTo(xc + r, yc + 0.6f * r, xc, yc + 0.6f * r);
				bean.quadTo(xc - r, yc + 0.6f * r, xc - r, yc + 0.1f * r);
				bean.lineTo(xc - r, yc - 0.1f * r);
				bean.lineTo(xc + r, yc - 0.1f * r);
				bean.lineTo(xc + r, yc + 0.1f * r);
				bean.lineTo(xc - r, yc + 0.1f * r);
				graphics.draw(bean);

				graphics.setTransform(oldTransform);
			}
		}

		graphics.dispose();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#getDisplayName()
	 */
	public String getDisplayName() {
		return SubstanceCoffeeBeansWatermark.getName();
	}

	public static String getName() {
		return "Coffee Beans";
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#isDependingOnTheme()
	 */
	public boolean isDependingOnTheme() {
		return true;
	}
}
