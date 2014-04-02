package org.jvnet.substance.watermark;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.SubstanceLookAndFeel;

/**
 * Simple implementation of {@link SubstanceWatermark}, drawing cross hatches
 * as watermark.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceLatchWatermark implements SubstanceWatermark {
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
		graphics.drawImage(SubstanceLatchWatermark.watermarkImage, x, y, x
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
		SubstanceLatchWatermark.watermarkImage = SubstanceImageCreator
				.getBlankImage(screenWidth, screenHeight);

		Graphics2D graphics = (Graphics2D) SubstanceLatchWatermark.watermarkImage
				.getGraphics().create();

		Color stampColorDark = SubstanceLookAndFeel.getColorScheme().isDark() ? new Color(
				255, 255, 255, 15)
				: new Color(0, 0, 0, 15);
		Color stampColorAll = SubstanceLookAndFeel.getColorScheme().isDark() ? new Color(
				255, 255, 255, 5)
				: new Color(0, 0, 0, 5);
		Color stampColorLight = SubstanceLookAndFeel.getColorScheme().isDark() ? new Color(
				255, 255, 255, 25)
				: new Color(0, 0, 0, 10);

		graphics.setColor(stampColorAll);
		graphics.fillRect(0, 0, screenWidth, screenHeight);

		int dimension = 12;
		BufferedImage tile = SubstanceImageCreator.getBlankImage(dimension,
				dimension);
		GeneralPath latch1 = new GeneralPath();
		latch1.moveTo(0.45f * dimension, 0);
		latch1.quadTo(0.45f * dimension, 0.45f * dimension, 0.05f * dimension,
				0.45f * dimension);
		latch1.quadTo(0.15f * dimension, 0.15f * dimension, 0.45f * dimension,
				0);
		this.drawLatch(tile, latch1, stampColorLight, stampColorDark);
		
		GeneralPath latch2 = new GeneralPath();
		latch2.moveTo(0.55f * dimension, 0.55f * dimension);
		latch2.quadTo(0.75f * dimension, 0.4f * dimension, dimension,
				dimension);
		latch2.quadTo(0.4f * dimension, 0.75f * dimension, 0.5f * dimension,
				0.5f * dimension);
		this.drawLatch(tile, latch2, stampColorLight, stampColorDark);

		for (int row = 0; row < screenHeight; row += dimension) {
			for (int col = 0; col < screenWidth; col += dimension) {
				graphics.drawImage(tile, col, row, null);
			}
		}
		graphics.dispose();
		return true;
	}

	private void drawLatch(BufferedImage tile, GeneralPath latchOutline,
			Color colorLight, Color colorDark) {

		Graphics2D graphics = (Graphics2D) tile.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setColor(colorLight);
		graphics.draw(latchOutline);
		graphics.setColor(colorDark);
		graphics.setStroke(new BasicStroke(1.5f));
		graphics.fill(latchOutline);

		graphics.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#getDisplayName()
	 */
	public String getDisplayName() {
		return SubstanceLatchWatermark.getName();
	}

	public static String getName() {
		return "Latch";
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
