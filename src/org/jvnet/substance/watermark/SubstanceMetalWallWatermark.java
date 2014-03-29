package org.jvnet.substance.watermark;

import java.awt.*;
import java.awt.image.BufferedImage;

import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.SubstanceLookAndFeel;

/**
 * Simple implementation of {@link SubstanceWatermark}, drawing cross hatches
 * as watermark. 
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceMetalWallWatermark implements SubstanceWatermark {
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
		graphics.drawImage(SubstanceMetalWallWatermark.watermarkImage, x, y, x
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
		SubstanceMetalWallWatermark.watermarkImage = SubstanceImageCreator
				.getBlankImage(screenWidth, screenHeight);

		Graphics2D graphics = (Graphics2D) SubstanceMetalWallWatermark.watermarkImage
				.getGraphics().create();

		Color stampColorDark = SubstanceLookAndFeel.getColorScheme().isDark() ? new Color(
				255, 255, 255, 15)
				: new Color(0, 0, 0, 25);
		Color stampColorAll = SubstanceLookAndFeel.getColorScheme().isDark() ? new Color(
				255, 255, 255, 5)
				: new Color(0, 0, 0, 5);
		Color stampColorLight = SubstanceLookAndFeel.getColorScheme().isDark() ? new Color(
				255, 255, 255, 25)
				: new Color(0, 0, 0, 20);

		graphics.setColor(stampColorAll);
		graphics.fillRect(0, 0, screenWidth, screenHeight);

		BufferedImage tile = SubstanceImageCreator.getBlankImage(128, 128);
		this
				.drawTilePolygon(tile, new Polygon(new int[] { 0, 49, 49, 0 },
						new int[] { 0, 0, 17, 17 }, 4), stampColorLight,
						stampColorDark);
		this.drawBolt(tile, 3, 3, stampColorLight, stampColorDark);
		this.drawBolt(tile, 44, 3, stampColorLight, stampColorDark);
		this.drawBolt(tile, 44, 12, stampColorLight, stampColorDark);
		this.drawBolt(tile, 3, 12, stampColorLight, stampColorDark);

		this.drawTilePolygon(tile, new Polygon(
				new int[] { 66, 85, 85, 49, 49 },
				new int[] { 0, 0, 30, 30, 17 }, 5), stampColorLight,
				stampColorDark);
		this.drawBolt(tile, 67, 3, stampColorLight, stampColorDark);
		this.drawBolt(tile, 80, 3, stampColorLight, stampColorDark);
		this.drawBolt(tile, 80, 25, stampColorLight, stampColorDark);
		this.drawBolt(tile, 51, 25, stampColorLight, stampColorDark);
		
		this
				.drawTilePolygon(tile, new Polygon(
						new int[] { 86, 102, 102, 86 }, new int[] { 0, 0, 17,
								17 }, 4), stampColorLight, stampColorDark);
		this.drawBolt(tile, 88, 3, stampColorLight, stampColorDark);
		this.drawBolt(tile, 97, 3, stampColorLight, stampColorDark);
		this.drawBolt(tile, 97, 11, stampColorLight, stampColorDark);
		this.drawBolt(tile, 88, 11, stampColorLight, stampColorDark);
		
		this.drawTilePolygon(tile, new Polygon(new int[] { -1, 9, 9, -1 },
				new int[] { 22, 22, 36, 36 }, 4), stampColorLight,
				stampColorDark);
		this.drawBolt(tile, 5, 24, stampColorLight, stampColorDark);
		this.drawBolt(tile, 5, 31, stampColorLight, stampColorDark);

		this.drawTilePolygon(tile, new Polygon(
				new int[] { 118, 128, 128, 118 }, new int[] { 22, 22, 36, 36 },
				4), stampColorLight, stampColorDark);
		this.drawBolt(tile, 120, 24, stampColorLight, stampColorDark);
		this.drawBolt(tile, 120, 31, stampColorLight, stampColorDark);

		this.drawTilePolygon(tile, new Polygon(new int[] { 49, 85, 85, 45, 45,
				22, 0, 29 }, new int[] { 31, 31, 57, 57, 75, 75, 50, 50 }, 8),
				stampColorLight, stampColorDark);
		this.drawBolt(tile, 51, 33, stampColorLight, stampColorDark);
		this.drawBolt(tile, 80, 33, stampColorLight, stampColorDark);
		this.drawBolt(tile, 80, 52, stampColorLight, stampColorDark);
		this.drawBolt(tile, 51, 52, stampColorLight, stampColorDark);
		this.drawBolt(tile, 40, 70, stampColorLight, stampColorDark);
		this.drawBolt(tile, 24, 70, stampColorLight, stampColorDark);
		this.drawBolt(tile, 8, 52, stampColorLight, stampColorDark);
		this.drawBolt(tile, 30, 52, stampColorLight, stampColorDark);

		this.drawTilePolygon(tile, new Polygon(new int[] { 86, 96, 96, 86 },
				new int[] { 45, 45, 57, 57 }, 4), stampColorLight,
				stampColorDark);
		this.drawBolt(tile, 88, 47, stampColorLight, stampColorDark);
		this.drawBolt(tile, 88, 52, stampColorLight, stampColorDark);

		this.drawTilePolygon(tile, new Polygon(new int[] { 97, 118, 118, 127,
				127, 102, 102, 97 }, new int[] { 45, 45, 60, 60, 100, 69, 57,
				57 }, 8), stampColorLight, stampColorDark);
		this.drawBolt(tile, 99, 47, stampColorLight, stampColorDark);
		this.drawBolt(tile, 113, 47, stampColorLight, stampColorDark);
		this.drawBolt(tile, 119, 62, stampColorLight, stampColorDark);
		this.drawBolt(tile, 104, 67, stampColorLight, stampColorDark);
		this.drawBolt(tile, 115, 80, stampColorLight, stampColorDark);
		this.drawBolt(tile, 123, 91, stampColorLight, stampColorDark);

		this.drawTilePolygon(tile, new Polygon(new int[] { 46, 66, 66, 40, 40,
				32, 32, 0, 0, 22, 22, 46 }, new int[] { 58, 58, 85, 109, 127,
				127, 109, 109, 100, 100, 76, 76 }, 12), stampColorLight,
				stampColorDark);
		this.drawBolt(tile, 48, 58, stampColorLight, stampColorDark);
		this.drawBolt(tile, 61, 58, stampColorLight, stampColorDark);
		this.drawBolt(tile, 61, 82, stampColorLight, stampColorDark);
		this.drawBolt(tile, 34, 122, stampColorLight, stampColorDark);
		this.drawBolt(tile, 16, 102, stampColorLight, stampColorDark);
		this.drawBolt(tile, 3, 102, stampColorLight, stampColorDark);
		this.drawBolt(tile, 24, 77, stampColorLight, stampColorDark);
		this.drawBolt(tile, 43, 77, stampColorLight, stampColorDark);

		this.drawTilePolygon(tile, new Polygon(new int[] { 67, 102, 102, 85,
				85, 66, 66, 45 }, new int[] { 85, 85, 104, 104, 127, 127, 112,
				106 }, 8), stampColorLight, stampColorDark);
		this.drawBolt(tile, 68, 87, stampColorLight, stampColorDark);
		this.drawBolt(tile, 97, 87, stampColorLight, stampColorDark);
		this.drawBolt(tile, 97, 99, stampColorLight, stampColorDark);
		this.drawBolt(tile, 80, 104, stampColorLight, stampColorDark);
		this.drawBolt(tile, 80, 122, stampColorLight, stampColorDark);
		this.drawBolt(tile, 68, 122, stampColorLight, stampColorDark);
		this.drawBolt(tile, 52, 102, stampColorLight, stampColorDark);

		this.drawTilePolygon(tile, new Polygon(new int[] { 86, 127, 127, 102,
				93, 127, 127, 86 }, new int[] { 105, 105, 109, 109, 118, 117,
				127, 127 }, 8), stampColorLight, stampColorDark);
		this.drawBolt(tile, 88, 106, stampColorLight, stampColorDark);
		this.drawBolt(tile, 122, 122, stampColorLight, stampColorDark);
		this.drawBolt(tile, 88, 122, stampColorLight, stampColorDark);
		
		for (int row = 0; row < screenHeight; row += 128) {
			for (int col = 0; col < screenWidth; col += 128) {
				graphics.drawImage(tile, col, row, null);
			}
		}
		graphics.dispose();
		return true;
	}

	private void drawTilePolygon(BufferedImage tile, Polygon polygon,
			Color colorLight, Color colorDark) {
		int minX = tile.getWidth();
		int maxX = 0;
		for (int x : polygon.xpoints) {
			minX = Math.min(x, minX);
			maxX = Math.max(x, maxX);
		}
		int minY = tile.getHeight();
		int maxY = 0;
		for (int y : polygon.ypoints) {
			minY = Math.min(y, minY);
			maxY = Math.max(y, maxY);
		}

		Graphics2D graphics = (Graphics2D) tile.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setPaint(new GradientPaint(minX, minY, colorLight, maxX, maxY,
				colorDark));
		graphics.drawPolygon(polygon);
		graphics.setComposite(AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.1f));
		graphics.fillPolygon(polygon);

		graphics.dispose();
	}

	private void drawBolt(BufferedImage tile, int x, int y, Color colorLight,
			Color colorDark) {
		Graphics2D graphics = (Graphics2D) tile.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		graphics.setColor(colorDark);
		graphics.fillOval(x, y, 4, 4);
		graphics.setColor(colorLight);
		graphics.fillOval(x, y, 3, 3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#getDisplayName()
	 */
	public String getDisplayName() {
		return SubstanceMetalWallWatermark.getName();
	}

	public static String getName() {
		return "Metal Wall";
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
