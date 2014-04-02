package org.jvnet.substance.watermark;

import java.awt.*;
import java.io.InputStream;

import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.SubstanceLookAndFeel;

/**
 * Implementation of {@link org.jvnet.substance.watermark.SubstanceWatermark},
 * drawing random Katakana glyphs as watermark.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceKatakanaWatermark implements SubstanceWatermark {
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
		graphics.drawImage(SubstanceKatakanaWatermark.watermarkImage, x, y, x
				+ width, y + height, x + dx, y + dy, x + dx + width, y + dy
				+ height, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#updateWatermarkImage()
	 */
	public boolean updateWatermarkImage() {
		// try loading the font
		ClassLoader cl = SubstanceKatakanaWatermark.class.getClassLoader();
		// InputStream is = cl.getResourceAsStream("resource/matrix code
		// nfi.ttf");
		InputStream is = cl.getResourceAsStream("resource/katakana.ttf");
		if (is != null) {
			try {
				Font kf = Font.createFont(Font.TRUETYPE_FONT, is);
				int fontSize = 14;
				Font font = kf.deriveFont(Font.BOLD, fontSize);
				Dimension screenDim = Toolkit.getDefaultToolkit()
						.getScreenSize();
				int screenWidth = screenDim.width;
				int screenHeight = screenDim.height;
				SubstanceKatakanaWatermark.watermarkImage = SubstanceImageCreator
						.getBlankImage(screenWidth, screenHeight);

				Graphics2D graphics = (Graphics2D) SubstanceKatakanaWatermark.watermarkImage
						.getGraphics().create();
				Color stampColor = SubstanceLookAndFeel.getColorScheme()
						.isDark() ? new Color(255, 255, 255, 25) : new Color(0,
						0, 0, 15);

				graphics.setColor(stampColor);

				graphics.setFont(font);
				int fontWidth = fontSize;
				int fontHeight = fontSize - 2;
				// for (int i=32; i<132; i++) {
				// fontWidth = Math.max(fontWidth,
				// graphics.getFontMetrics().charWidth((char)i));
				// }
				// int fontHeight = (int) (Math.ceil(graphics.getFontMetrics()
				// .getAscent()) + Math.ceil(graphics.getFontMetrics()
				// .getDescent()));
				int rows = screenHeight / fontHeight;
				int columns = screenWidth / fontWidth;
				for (int col = 0; col <= columns; col++) {
					for (int row = 0; row <= rows; row++) {
						// choose random katakana letter

						// int letterIndex = (int) (0x30A0 + Math.random()
						// * (0x30FF - 0x30A0));
						int letterIndex = (int) (33 + Math.random() * 95);
						char c = (char) letterIndex;
						graphics.drawString("" + c, col * fontWidth, fontHeight
								* (row + 1));
					}
				}

				graphics.dispose();
				return true;
			} catch (Exception exc) {
				// no watermark here :(
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#getDisplayName()
	 */
	public String getDisplayName() {
		return SubstanceKatakanaWatermark.getName();
	}

	public static String getName() {
		return "Katakana";
	}

	/* (non-Javadoc)
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#isDependingOnTheme()
	 */
	public boolean isDependingOnTheme() {
		return true;
	}
}
