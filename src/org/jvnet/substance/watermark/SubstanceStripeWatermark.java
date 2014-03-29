package org.jvnet.substance.watermark;

import java.awt.*;

import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.SubstanceLookAndFeel;

/**
 * Simple implementation of
 * {@link org.jvnet.substance.watermark.SubstanceWatermark}, drawing darker
 * even lines as watermark. This is the default watermark implementation.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceStripeWatermark implements SubstanceWatermark {
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
		graphics.drawImage(SubstanceStripeWatermark.watermarkImage, x, y, x
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
		SubstanceStripeWatermark.watermarkImage = SubstanceImageCreator
				.getBlankImage(screenWidth, screenHeight);

		Graphics2D graphics = (Graphics2D) SubstanceStripeWatermark.watermarkImage
				.getGraphics().create();
		Color stampColor = SubstanceLookAndFeel.getColorScheme().isDark() ? new Color(
				255, 255, 255, 25)
				: new Color(0, 0, 0, 15);

		graphics.setColor(stampColor);
		for (int row = 0; row < screenHeight; row += 2) {
			graphics.drawLine(0, row, screenWidth, row);
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
		return SubstanceStripeWatermark.getName();
	}

	public static String getName() {
		return "Stripes";
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#isDependingOnTheme()
	 */
	public boolean isDependingOnTheme() {
		return true;
	}
}
