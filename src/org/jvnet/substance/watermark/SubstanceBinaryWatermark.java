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
public class SubstanceBinaryWatermark implements SubstanceWatermark {
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
		graphics.drawImage(SubstanceBinaryWatermark.watermarkImage, x, y, x
				+ width, y + height, x + dx, y + dy, x + dx + width, y + dy
				+ height, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#updateWatermarkImage()
	 */
	public boolean updateWatermarkImage() {
		int fontSize = 14;
		Font tahoma = new Font("Tahoma", Font.BOLD, fontSize);
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenDim.width;
		int screenHeight = screenDim.height;
		SubstanceBinaryWatermark.watermarkImage = SubstanceImageCreator
				.getBlankImage(screenWidth, screenHeight);

		Graphics2D graphics = (Graphics2D) SubstanceBinaryWatermark.watermarkImage
				.getGraphics().create();
		Color stampColor = SubstanceLookAndFeel.getColorScheme().isDark() ? new Color(
				255, 255, 255, 25)
				: new Color(0, 0, 0, 15);

		graphics.setColor(stampColor);

		graphics.setFont(tahoma);
		int fontWidth = fontSize - 4;
		int fontHeight = fontSize - 2;
		int rows = screenHeight / fontHeight;
		int columns = screenWidth / fontWidth;
		for (int col = 0; col <= columns; col++) {
			for (int row = 0; row <= rows; row++) {
				// choose random 0/1 letter
				char c = (Math.random() >= 0.5) ? '0' : '1';
				graphics.drawString("" + c, col * fontWidth, fontHeight
						* (row + 1));
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
		return SubstanceBinaryWatermark.getName();
	}

	public static String getName() {
		return "Binary";
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
