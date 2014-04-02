package org.jvnet.substance.watermark;

import java.awt.*;

import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.SubstanceLookAndFeel;

/**
 * Implementation of {@link org.jvnet.substance.watermark.SubstanceWatermark},
 * drawing random binary (0-1) glyphs as watermark.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceMosaicWatermark implements SubstanceWatermark {
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
		graphics.drawImage(SubstanceMosaicWatermark.watermarkImage, x, y, x
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
		SubstanceMosaicWatermark.watermarkImage = SubstanceImageCreator
				.getBlankImage(screenWidth, screenHeight);

		Graphics2D graphics = (Graphics2D) SubstanceMosaicWatermark.watermarkImage
				.getGraphics().create();

		int sqDim = 7;
		int sqGap = 2;
		int cellDim = sqDim + sqGap;

		int rows = screenHeight / cellDim;
		int columns = screenWidth / cellDim;
		for (int col = 0; col <= columns; col++) {
			for (int row = 0; row <= rows; row++) {
				Color stampColor = SubstanceLookAndFeel.getColorScheme()
						.isDark() ? new Color(255, 255, 255,
						10 + (int) (15.0 * Math.random())) : new Color(0, 0, 0,
						5 + (int) (10.0 * Math.random()));
				graphics.setColor(stampColor);
				graphics.fillRect(col * cellDim, row * cellDim, sqDim, sqDim);
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
		return SubstanceMosaicWatermark.getName();
	}

	public static String getName() {
		return "Square mosaic";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#isDependingOnTheme()
	 */
	public boolean isDependingOnTheme() {
		return true;
	}
}
