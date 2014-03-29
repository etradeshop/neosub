package org.jvnet.substance.watermark;

import java.awt.*;
import java.awt.geom.GeneralPath;

import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.SubstanceLookAndFeel;

/**
 * Implementation of {@link org.jvnet.substance.watermark.SubstanceWatermark},
 * drawing random bubbles as watermark.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceBubblesWatermark implements SubstanceWatermark {
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
		graphics.drawImage(SubstanceBubblesWatermark.watermarkImage, x, y, x
				+ width, y + height, x + dx, y + dy, x + dx + width, y + dy
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
		SubstanceBubblesWatermark.watermarkImage = SubstanceImageCreator
				.getBlankImage(screenWidth, screenHeight);

		Graphics2D graphics = (Graphics2D) SubstanceBubblesWatermark.watermarkImage
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
				graphics.drawOval(xc - r, yc - r, 2 * r, 2 * r);

				GeneralPath shine = new GeneralPath();
				shine.moveTo(xc - 0.2f * r, yc - 0.8f * r);
				shine.quadTo(xc - 0.7f * r, yc - 0.6f * r, xc - 0.8f * r, yc
						- 0.1f * r);
				shine.lineTo(xc - 0.3f * r, yc + 0.2f * r);
				shine.quadTo(xc - 0.3f * r, yc - 0.4f * r, xc, yc - 0.6f * r);
				shine.lineTo(xc - 0.2f * r, yc - 0.8f * r);
				graphics.draw(shine);
				// graphics.fill(shine);
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
		return SubstanceBubblesWatermark.getName();
	}

	public static String getName() {
		return "Bubbles";
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#isDependingOnTheme()
	 */
	public boolean isDependingOnTheme() {
		return true;
	}
}
