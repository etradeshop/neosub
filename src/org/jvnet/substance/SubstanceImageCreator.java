package org.jvnet.substance;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.*;
import java.util.*;

import javax.swing.*;

import org.jvnet.substance.color.ColorScheme;
import org.jvnet.substance.color.ColorSchemeEnum;

/**
 * Provides utility functions for creating various images for <b>Substance</b>
 * look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public final class SubstanceImageCreator {
	/**
	 * Default icon dimension.
	 */
	public static final int ICON_DIMENSION = 16;

	/**
	 * Combobox button arrow default width.
	 */
	public static final int ARROW_WIDTH = 9;

	/**
	 * Combobox button arrow default height.
	 */
	public static final int ARROW_HEIGHT = 6;

	/**
	 * Drag bump diameter.
	 */
	public static final int DRAG_BUMP_DIAMETER = 2;

	/**
	 * Corner enum (for creating rounded rectangles).
	 * 
	 * @author Kirill Grouchnikov
	 */
	public static enum Corner {
		TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
	}

	/**
	 * Corner enum (for creating rounded rectangles).
	 * 
	 * @author Kirill Grouchnikov
	 */
	public static enum Side {
		LEFT {
			public Side getAdjacentSide() {
				return TOP;
			}
		},
		RIGHT {
			public Side getAdjacentSide() {
				return BOTTOM;
			}
		},
		TOP {
			public Side getAdjacentSide() {
				return RIGHT;
			}
		},
		BOTTOM {
			public Side getAdjacentSide() {
				return LEFT;
			}
		};

		public abstract Side getAdjacentSide();
	}

	/**
	 * Enum for various tree node states.
	 * 
	 * @author Kirill Grouchnikov
	 */
	public static enum TreeIcon {
		CLOSED, OPENED, UP, NONE
	}

	/**
	 * Retrieves transparent image of specified dimension.
	 * 
	 * @param width
	 *            Image width.
	 * @param height
	 *            Image height.
	 * @return Transparent image of specified dimension.
	 */
	public static BufferedImage getBlankImage(int width, int height) {
	

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		// get graphics and set hints
		Graphics2D graphics = (Graphics2D) image.getGraphics().create();
		graphics.setColor(new Color(0, 0, 0, 0));
		graphics.setComposite(AlphaComposite.Src);
		graphics.fillRect(0, 0, width, height);
		graphics.dispose();

		return image;
	}

	/**
	 * Interpolates color.
	 * 
	 * @param color1
	 *            The first color
	 * @param color2
	 *            The second color
	 * @param color1Likeness
	 *            The closer this value is to 0.0, the closer the resulting
	 *            color will be to <code>color1</code>.
	 * @return Interpolated color.
	 */
	private static Color getInterpolatedColor(Color color1, Color color2,
			double color1Likeness) {
		int lr = color1.getRed();
		int lg = color1.getGreen();
		int lb = color1.getBlue();
		int dr = color2.getRed();
		int dg = color2.getGreen();
		int db = color2.getBlue();

		int r = (int) (color1Likeness * lr + (1.0 - color1Likeness) * dr);
		int g = (int) (color1Likeness * lg + (1.0 - color1Likeness) * dg);
		int b = (int) (color1Likeness * lb + (1.0 - color1Likeness) * db);

		r = Math.min(255, r);
		g = Math.min(255, g);
		b = Math.min(255, b);
		r = Math.max(0, r);
		g = Math.max(0, g);
		b = Math.max(0, b);
		return new Color(r, g, b);
	}

	/**
	 * Paints border instance of specified dimensions and status.
	 * 
	 * @param g
	 *            Graphics context.
	 * @param x
	 *            Component left X (in graphics context).
	 * @param y
	 *            Component top Y (in graphics context).
	 * @param width
	 *            Border width.
	 * @param height
	 *            Border height.
	 * @param borderSchemeEnum
	 *            Border color scheme enum.
	 */
	public static void paintBorder(Graphics g, int x, int y, int width,
			int height, ColorSchemeEnum borderSchemeEnum) {
		ColorScheme borderScheme = borderSchemeEnum.getColorScheme();

		Color topBorderColor = borderSchemeEnum.isDark() ? borderScheme
				.getExtraLightColor().brighter().brighter() : borderScheme
				.getDarkColor();
		Color bottomBorderColor = borderSchemeEnum.isDark() ? borderScheme
				.getUltraLightColor() : borderScheme.getMidColor();

		// get graphics and set hints
		Graphics2D graphics = (Graphics2D) g.create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// border
		GradientPaint gradient = new GradientPaint(x, y, topBorderColor, x, y
				+ height - 1, bottomBorderColor);
		graphics.setPaint(gradient);
		graphics.setStroke(new BasicStroke((float) 1.3, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));

		graphics.drawLine(x, y, x + width - 1, y);
		graphics.drawLine(x, y, x, y + height - 1);
		graphics.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
		graphics.drawLine(x + width - 1, y, x + width - 1, y + height - 1);

		graphics.dispose();
	}

	/**
	 * Retrieves check mark image for {@link SubstanceCheckBoxUI}.
	 * 
	 * @param dimension
	 *            Check mark dimension.
	 * @param isEnabled
	 *            Enabled status.
	 * @param colorSchemeEnum
	 *            Color scheme for the check mark.
	 * @return Check mark image.
	 */
	private static BufferedImage getCheckMark(int dimension, boolean isEnabled,
			ColorSchemeEnum colorSchemeEnum) {
		BufferedImage result = new BufferedImage(dimension, dimension,
				BufferedImage.TYPE_INT_ARGB);

		// set completely transparent
		for (int col = 0; col < dimension; col++) {
			for (int row = 0; row < dimension; row++) {
				result.setRGB(col, row, 0x0);
			}
		}

		// get graphics and set hints
		Graphics2D graphics = (Graphics2D) result.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics
				.setColor(colorSchemeEnum.getColorScheme().getForegroundColor());
		Stroke stroke = new BasicStroke((float) 0.15 * dimension,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

		graphics.setStroke(stroke);

		// create curved checkbox path
		GeneralPath path = new GeneralPath();

		path.moveTo((float) 0.25 * dimension, (float) 0.5 * dimension);
		path.quadTo((float) 0.4 * dimension, (float) 0.6 * dimension,
				(float) 0.5 * dimension, (float) 0.8 * dimension);
		path.quadTo((float) 0.55 * dimension, (float) 0.5 * dimension,
				(float) 0.85 * dimension, (float) 0);

		graphics.draw(path);

		return result;
	}

	/**
	 * Retrieves arrow icon.
	 * 
	 * @param width
	 *            Arrow width.
	 * @param height
	 *            Arrow height.
	 * @param direction
	 *            Arrow direction.
	 * @return Arrow image.
	 * @see SwingConstants#NORTH
	 * @see SwingConstants#WEST
	 * @see SwingConstants#SOUTH
	 * @see SwingConstants#EAST
	 */
	public static Icon getArrowIcon(int width, int height, int direction,
			ColorSchemeEnum colorSchemeEnum) {
		BufferedImage downArrowImage = getBlankImage(width, height);

		// get graphics and set hints
		Graphics2D graphics = (Graphics2D) downArrowImage.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		graphics
				.setColor(colorSchemeEnum.getColorScheme().getForegroundColor());
		Stroke stroke = new BasicStroke((float) 2.0, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER);

		graphics.setStroke(stroke);
		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 0);
		gp.lineTo((float) 0.5 * (width - 1), height - 2);
		gp.lineTo(width - 1, 0);
		graphics.draw(gp);
		// graphics.drawPolyline(new int[] { 0, width / 2, width - 1 }, new
		// int[] {
		// 0, height - 2, 0 }, 3);

		int quadrantCounterClockwise = 0;
		switch (direction) {
		case SwingConstants.NORTH:
			quadrantCounterClockwise = 2;
			break;
		case SwingConstants.WEST:
			quadrantCounterClockwise = 1;
			break;
		case SwingConstants.SOUTH:
			quadrantCounterClockwise = 0;
			break;
		case SwingConstants.EAST:
			quadrantCounterClockwise = 3;
			break;
		}
		BufferedImage arrowImage = getRotated(downArrowImage,
				quadrantCounterClockwise);

		return new ImageIcon(arrowImage);
	}

	/**
	 * Retrieves arrow icon.
	 * 
	 * @param width
	 *            Arrow width.
	 * @param height
	 *            Arrow height.
	 * @param direction
	 *            Arrow direction.
	 * @return Arrow image.
	 * @see SwingConstants#NORTH
	 * @see SwingConstants#WEST
	 * @see SwingConstants#SOUTH
	 * @see SwingConstants#EAST
	 */
	public static Icon getDoubleArrowIcon(int width, int height, int direction,
			ColorSchemeEnum colorSchemeEnum) {
		BufferedImage downArrowImage = getBlankImage(width, height);

		// get graphics and set hints
		Graphics2D graphics = (Graphics2D) downArrowImage.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		graphics
				.setColor(colorSchemeEnum.getColorScheme().getForegroundColor());
		Stroke stroke = new BasicStroke((float) 1.5, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER);

		graphics.setStroke(stroke);
		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 0);
		gp.lineTo((float) 0.5 * (width - 1), height - 4);
		gp.lineTo(width - 1, 0);
		graphics.draw(gp);

		GeneralPath gp2 = new GeneralPath();
		gp2.moveTo(0, 3);
		gp2.lineTo((float) 0.5 * (width - 1), height - 1);
		gp2.lineTo(width - 1, 3);
		graphics.draw(gp2);

		int quadrantCounterClockwise = 0;
		switch (direction) {
		case SwingConstants.NORTH:
			quadrantCounterClockwise = 2;
			break;
		case SwingConstants.WEST:
			quadrantCounterClockwise = 1;
			break;
		case SwingConstants.SOUTH:
			quadrantCounterClockwise = 0;
			break;
		case SwingConstants.EAST:
			quadrantCounterClockwise = 3;
			break;
		}
		BufferedImage arrowImage = getRotated(downArrowImage,
				quadrantCounterClockwise);

		return new ImageIcon(arrowImage);
	}

	/**
	 * Returns rotated image.
	 * 
	 * @param bi
	 *            Image to rotate.
	 * @param quadrantClockwise
	 *            Amount of quadrants to rotate in clockwise directio. The
	 *            rotation angle is 90 times this value.
	 * @return Rotated image.
	 */
	public static BufferedImage getRotated(final BufferedImage bi,
			int quadrantClockwise) {
		quadrantClockwise = quadrantClockwise % 4;
		int width = bi.getWidth();
		int height = bi.getHeight();
		if ((quadrantClockwise == 1) || (quadrantClockwise == 3)) {
			width = bi.getHeight();
			height = bi.getWidth();
		}
		BufferedImage biRot = getBlankImage(width, height);
		switch (quadrantClockwise) {
		case 0:
			return bi;
		case 1:
			// 90 deg
			for (int col = 0; col < width; col++) {
				for (int row = 0; row < height; row++) {
					biRot.setRGB(col, row, bi.getRGB(row, width - col - 1));
				}
			}
			return biRot;
		case 2:
			// 180 deg
			for (int col = 0; col < width; col++) {
				for (int row = 0; row < height; row++) {
					biRot.setRGB(col, row, bi.getRGB(width - col - 1, height
							- row - 1));
				}
			}
			return biRot;
		case 3:
			// 270 deg
			for (int col = 0; col < width; col++) {
				for (int row = 0; row < height; row++) {
					biRot.setRGB(col, row, bi.getRGB(height - row - 1, col));
				}
			}
			return biRot;
		}
		return null;
	}

	/**
	 * Translated the specified icon to grey scale.
	 * 
	 * @param icon
	 *            Icon.
	 * @return Greyscale version of the specified icon.
	 */
	public static Icon toGreyscale(Icon icon) {
		if (icon == null) {
			return null;
		}

		int width = icon.getIconWidth();
		int height = icon.getIconHeight();

		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		// set completely transparent
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				result.setRGB(col, row, 0x0);
			}
		}

		icon.paintIcon(null, result.getGraphics(), 0, 0);
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				int color = result.getRGB(col, row);
				int transp = (color >>> 24) & 0xFF;
				int oldR = (color >>> 16) & 0xFF;
				int oldG = (color >>> 8) & 0xFF;
				int oldB = (color >>> 0) & 0xFF;

				int newComp = (222 * oldR + 707 * oldG + 71 * oldB) / 1000;
				// (oldR + oldG + oldB) / 3;
				int newColor = (transp << 24) | (newComp << 16)
						| (newComp << 8) | newComp;

				result.setRGB(col, row, newColor);
			}
		}

		return new ImageIcon(result);
	}

	/**
	 * Makes the specified icon transparent.
	 * 
	 * @param icon
	 *            Icon.
	 * @param alpha
	 *            The opaqueness of the resulting image. The closer this value
	 *            is to 0.0, the more transparent resulting image will be.
	 * @return Transparent version of the specified icon.
	 */
	public static Icon makeTransparent(Icon icon, double alpha) {
		if (icon == null) {
			return null;
		}

		int width = icon.getIconWidth();
		int height = icon.getIconHeight();

		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		// set completely transparent
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				result.setRGB(col, row, 0x0);
			}
		}

		icon.paintIcon(null, result.getGraphics(), 0, 0);
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				int color = result.getRGB(col, row);
				int transp = (int) (alpha * ((color >>> 24) & 0xFF));
				int r = (color >>> 16) & 0xFF;
				int g = (color >>> 8) & 0xFF;
				int b = (color >>> 0) & 0xFF;

				int newColor = (transp << 24) | (r << 16) | (g << 8) | b;

				result.setRGB(col, row, newColor);
			}
		}

		return new ImageIcon(result);
	}

	/**
	 * Retrieves fuzzy oval map for shine-spot effect.
	 * 
	 * @param origImage
	 *            The original image. Is used as a clipping area.
	 * @param cornerRadius
	 *            Corner radius of the original image.
	 * @param side
	 *            Straight side (if any) of the original image.
	 * @return Fuzzy oval map for shine-spot effect.
	 */
	private static int[][] getFuzzyOvalOpacity(BufferedImage origImage,
			int cornerRadius, Side side) {
		int width = origImage.getWidth();
		int height = origImage.getHeight();
		int[][] result = new int[width][height];
		// set completely transparent
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				result[col][row] = 0;
			}
		}

		BufferedImage image = getBlankImage(width, height);
		// get graphics and set hints
		Graphics2D graphics = (Graphics2D) image.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setColor(Color.black);

		double ellipseHeight = (height / 2.0) - 1.0;
		double ellipseMidY = 1.0 + ellipseHeight / 1.8;

		double ellipseWidth = 0.9 * width;
		if (width >= height) {
			// horizontal buttons
			if (ellipseMidY < cornerRadius) {
				double dx = Math.sqrt(2.0 * cornerRadius * ellipseMidY
						- ellipseMidY * ellipseMidY);
				double delta = cornerRadius - dx;
				if (side != Side.LEFT) {
					ellipseWidth -= delta;
				}
				if (side != Side.RIGHT) {
					ellipseWidth -= delta;
				}
			}
			double ellipseTopWidth = 0.9 * width;
			if (ellipseMidY < cornerRadius) {
				double ellipseTopY = ellipseMidY - ellipseHeight / 2;
				double dx = Math.sqrt(2.0 * cornerRadius * ellipseTopY
						- ellipseTopY * ellipseTopY);
				double delta = cornerRadius - dx;
				if (side != Side.LEFT) {
					ellipseTopWidth -= delta;
				}
				if (side != Side.RIGHT) {
					ellipseTopWidth -= delta;
				}
			}
			double ellipseMidTopWidth = 0.9 * width;
			if (ellipseMidY < cornerRadius) {
				double ellipseMidTopY = ellipseMidY - ellipseHeight / 4;
				double dx = Math.sqrt(2.0 * cornerRadius * ellipseMidTopY
						- ellipseMidTopY * ellipseMidTopY);
				double delta = cornerRadius - dx;
				if (side != Side.LEFT) {
					ellipseMidTopWidth -= delta;
				}
				if (side != Side.RIGHT) {
					ellipseMidTopWidth -= delta;
				}
			}

			if ((side == Side.LEFT) || (side == Side.RIGHT)) {
				ellipseMidTopWidth = Math.min(ellipseMidTopWidth,
						0.9 * ellipseTopWidth);
				ellipseTopWidth = Math.min(ellipseTopWidth,
						0.8 * ellipseTopWidth);
			}

			double ellipseMidX = width / 2.0;

			GeneralPath shinePath = new GeneralPath();
			shinePath.moveTo((float) (ellipseMidX - ellipseTopWidth / 2),
					(float) (ellipseMidY - ellipseHeight / 2.0));
			shinePath.lineTo((float) (ellipseMidX + ellipseTopWidth / 2),
					(float) (ellipseMidY - ellipseHeight / 2.0));
			shinePath.quadTo((float) (ellipseMidX + ellipseMidTopWidth / 1.6),
					(float) (ellipseMidY - ellipseHeight / 4.0),
					(float) (ellipseMidX + ellipseWidth / 2),
					(float) ellipseMidY);
			shinePath.quadTo((float) (ellipseMidX + ellipseMidTopWidth / 1.6),
					(float) (ellipseMidY + ellipseHeight / 4.0),
					(float) (ellipseMidX + ellipseTopWidth / 2),
					(float) (ellipseMidY + ellipseHeight / 2.0));
			shinePath.lineTo((float) (ellipseMidX - ellipseTopWidth / 2),
					(float) (ellipseMidY + ellipseHeight / 2.0));
			shinePath.quadTo((float) (ellipseMidX - ellipseMidTopWidth / 1.6),
					(float) (ellipseMidY + ellipseHeight / 4.0),
					(float) (ellipseMidX - ellipseWidth / 2),
					(float) ellipseMidY);
			shinePath.quadTo((float) (ellipseMidX - ellipseMidTopWidth / 1.6),
					(float) (ellipseMidY - ellipseHeight / 4.0),
					(float) (ellipseMidX - ellipseTopWidth / 2),
					(float) (ellipseMidY - ellipseHeight / 2.0));
			graphics.fill(shinePath);
		} else {
			// vertical buttons
			double ellipseSideHeight = ellipseHeight / 2;
			double ellipseLeftX = width / 2.0 - ellipseWidth / 2.0;
			if (ellipseLeftX < cornerRadius) {
				double dy = Math.sqrt(2.0 * cornerRadius * ellipseLeftX
						- ellipseLeftX * ellipseLeftX);
				double delta = cornerRadius - dy;
				if (side != Side.TOP) {
					ellipseSideHeight -= delta;
				}
			}
			double ellipseMidSideHeight = ellipseHeight / 2;
			double ellipseMidLeftX = width / 2.0 - ellipseWidth / 2.5;
			if (ellipseMidLeftX < cornerRadius) {
				double dy = Math.sqrt(2.0 * cornerRadius * ellipseMidLeftX
						- ellipseMidLeftX * ellipseMidLeftX);
				double delta = cornerRadius - dy;
				if (side != Side.TOP) {
					ellipseMidSideHeight -= delta;
				}
			}

			if (side == Side.TOP) {
				ellipseMidSideHeight = Math.min(ellipseMidSideHeight,
						0.45 * ellipseHeight);
				ellipseSideHeight = Math.min(ellipseSideHeight,
						0.4 * ellipseHeight);
			}

			double ellipseMidX = width / 2.0;

			GeneralPath shinePath = new GeneralPath();
			shinePath.moveTo((float) (ellipseMidX - ellipseWidth / 2),
					(float) (ellipseMidY + ellipseSideHeight / 2.0));
			shinePath.lineTo((float) (ellipseMidX - ellipseWidth / 2),
					(float) (ellipseMidY - ellipseSideHeight / 2.0));
			shinePath.quadTo((float) (ellipseMidX - ellipseWidth / 2.5),
					(float) (ellipseMidY - ellipseMidSideHeight / 0.8),
					(float) (ellipseMidX),
					(float) (ellipseMidY - ellipseHeight / 2));
			shinePath.quadTo((float) (ellipseMidX + ellipseWidth / 2.5),
					(float) (ellipseMidY - ellipseMidSideHeight / 0.8),
					(float) (ellipseMidX + ellipseWidth / 2),
					(float) (ellipseMidY - ellipseSideHeight / 2));
			shinePath.lineTo((float) (ellipseMidX + ellipseWidth / 2),
					(float) (ellipseMidY + ellipseSideHeight / 2));
			shinePath.quadTo((float) (ellipseMidX + ellipseWidth / 2.5),
					(float) (ellipseMidY + ellipseMidSideHeight / 0.8),
					(float) (ellipseMidX),
					(float) (ellipseMidY + ellipseHeight / 2));
			shinePath.quadTo((float) (ellipseMidX + ellipseWidth / 2.5),
					(float) (ellipseMidY + ellipseMidSideHeight / 0.8),
					(float) (ellipseMidX + ellipseWidth / 2),
					(float) (ellipseMidY + ellipseSideHeight / 2));
			graphics.fill(shinePath);
		}

		int[][] temporary = new int[width][height];
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				int color = image.getRGB(col, row);
				int transp = (color >>> 24) & 0xFF;
				temporary[col][row] = transp;
			}
		}

		// start convolving. The convolution matrix will be condensed
		// at the top of the oval, and fuzzy at its bottom
		int minFuzziness = 1 + height / 50;
		int maxFuzziness = (width >= height) ? height / 5 : height / 20;
		for (int row = 0; row < height; row++) {
			int fuzziness = minFuzziness
					+ ((maxFuzziness - minFuzziness) * row / height);
			int sy = Math.max(0, row - fuzziness);
			int ey = Math.min(height - 1, row + fuzziness);
			for (int col = 0; col < width; col++) {
				int pixCount = 0;
				int transpSum = 0;
				int sx = Math.max(0, col - fuzziness);
				int ex = Math.min(width - 1, col + fuzziness);
				for (int colPix = sx; colPix <= ex; colPix++) {
					for (int rowPix = sy; rowPix <= ey; rowPix++) {
						pixCount++;
						transpSum += temporary[colPix][rowPix];
					}
				}
				int origColor = origImage.getRGB(col, row);
				int origTransp = (origColor >>> 24) & 0xFF;
				result[col][row] = (transpSum * origTransp) / (255 * pixCount);
			}
		}

		GeneralPath perim = new GeneralPath();
		boolean cornerTL = ((side == Side.LEFT) || (side == Side.TOP));
		boolean cornerTR = ((side == Side.RIGHT) || (side == Side.TOP));
		boolean cornerBL = ((side == Side.LEFT) || (side == Side.BOTTOM));
		boolean cornerBR = ((side == Side.RIGHT) || (side == Side.BOTTOM));

		if (cornerTL) {
			perim.moveTo(0, 0);
		} else {
			perim.moveTo(0, cornerRadius);
			perim.quadTo(0, 0, cornerRadius, 0);
		}
		if (cornerTR) {
			perim.lineTo(width - 1, 0);
		} else {
			perim.lineTo(width - 1 - cornerRadius, 0);
			perim.quadTo(width - 1, 0, width - 1, cornerRadius);
		}
		if (cornerBR) {
			perim.lineTo(width - 1, height - 1);
		} else {
			perim.lineTo(width - 1, height - 1 - cornerRadius);
			perim.quadTo(width - 1, height - 1, width - 1 - cornerRadius,
					height - 1);
		}
		if (cornerBL) {
			perim.lineTo(0, height - 1);
		} else {
			perim.lineTo(cornerRadius, height - 1);
			perim.quadTo(0, height - 1, 0, height - 1 - cornerRadius);
		}
		if (cornerTR) {
			perim.lineTo(0, 0);
		} else {
			perim.lineTo(0, cornerRadius);
		}

		BufferedImage tempImage = getBlankImage(width, height);
		Graphics2D tempGraphics = (Graphics2D) tempImage.getGraphics().create();
		tempGraphics.setColor(Color.black);
		tempGraphics.fill(perim);
		tempGraphics.draw(perim);

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				int transp = (tempImage.getRGB(col, row) >>> 24) & 0xFF;
				result[col][row] = transp * result[col][row] / 255;
			}
		}

		tempGraphics.dispose();

		graphics.dispose();

		return result;
	}

	/**
	 * Retrieves rounded background for the specified parameters.
	 * 
	 * @param width
	 *            Image width.
	 * @param height
	 *            Image height.
	 * @param cornerRadius
	 *            Corner radius.
	 * @param colorSchemeEnum
	 *            Color scheme for the border and the filling
	 * @param cyclePos
	 *            Cycle position index.
	 * @param side
	 *            Straight side (if not <code>null</code>).
	 * @return Rounded background for the specified parameters.
	 */
	public static BufferedImage getRoundedBackground(int width, int height,
			int cornerRadius, ColorSchemeEnum colorSchemeEnum, int cyclePos,
			Side side) {

		return getRoundedBackground(width, height, cornerRadius,
				colorSchemeEnum, colorSchemeEnum, cyclePos, side, false, true);
	}

	/**
	 * Retrieves radio button of the specified size that matches the specified
	 * component state.
	 * 
	 * @param dimension
	 *            Radio button size.
	 * @param componentState
	 *            Component state.
	 * @return Radio button of the specified size that matches the specified
	 *         component state.
	 */
	public static BufferedImage getRadioButton(int dimension,
			ComponentState componentState) {
		return getRadioButton(dimension, componentState, 0);
	}

	/**
	 * Retrieves radio button of the specified size that matches the specified
	 * component state.
	 * 
	 * @param dimension
	 *            Radio button size.
	 * @param componentState
	 *            Component state.
	 * @param offsetX
	 *            Offset on X axis - should be positive in order to see the
	 *            entire radio button.
	 * @return Radio button of the specified size that matches the specified
	 *         component state.
	 */
	public static BufferedImage getRadioButton(int dimension,
			ComponentState componentState, int offsetX) {
		return getRadioButton(dimension, componentState, offsetX,
				SubstanceLookAndFeel.getColorScheme());
	}

	/**
	 * Retrieves radio button of the specified size that matches the specified
	 * parameters.
	 * 
	 * @param dimension
	 *            Radio button dimension.
	 * @param componentState
	 *            Component state.
	 * @param offsetX
	 *            Offset on X axis - should be positive in order to see the
	 *            entire radio button.
	 * @param mainColorSchemeEnum
	 *            Color scheme.
	 * @return Radio button of the specified size that matches the specified
	 *         parameters.
	 */
	public static BufferedImage getRadioButton(int dimension,
			ComponentState componentState, int offsetX,
			ColorSchemeEnum mainColorSchemeEnum) {

		ComponentState.ColorSchemeKind kind = componentState
				.getColorSchemeKind();
		int cyclePos = componentState.getCycleCount();

		ColorSchemeEnum colorSchemeEnum = null;
		switch (kind) {
		case CURRENT:
			colorSchemeEnum = mainColorSchemeEnum;
			break;
		case REGULAR:
			colorSchemeEnum = SubstanceLookAndFeel.getColorScheme()
					.getMetallic();
			break;
		case DISABLED:
			colorSchemeEnum = SubstanceLookAndFeel.getColorScheme().getGray();
			break;
		}

		BufferedImage background = getRoundedBackground(dimension, dimension,
				dimension / 2, colorSchemeEnum, colorSchemeEnum, cyclePos,
				null, false, true);

		BufferedImage offBackground = getBlankImage(dimension + offsetX,
				dimension);
		Graphics2D graphics = (Graphics2D) offBackground.getGraphics();
		graphics.drawImage(background, offsetX, 0, null);

		if (componentState.isSelected()) {
			int rc = dimension / 2;
			Color markColor = colorSchemeEnum.getColorScheme()
					.getForegroundColor();

			graphics.translate(offsetX, 0);

			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setColor(markColor);

			graphics.fillOval(rc - dimension / 5, rc - dimension / 5,
					2 * dimension / 5, 2 * dimension / 5);
			graphics.translate(-offsetX, 0);
		}

		return offBackground;
	}

	/**
	 * Retrieves check box of the specified size that matches the specified
	 * component state.
	 * 
	 * @param dimension
	 *            Check box size.
	 * @param componentState
	 *            Component state.
	 * @return Check box of the specified size that matches the specified
	 *         component state.
	 */
	public static BufferedImage getCheckBox(int dimension,
			ComponentState componentState) {

		return getCheckBox(dimension, componentState, SubstanceLookAndFeel
				.getColorScheme());
	}

	/**
	 * Retrieves check box of the specified size that matches the specified
	 * component state.
	 * 
	 * @param dimension
	 *            Check box size.
	 * @param componentState
	 *            Component state.
	 * @return Check box of the specified size that matches the specified
	 *         component state.
	 */
	public static BufferedImage getCheckBox(int dimension,
			ComponentState componentState, ColorSchemeEnum mainColorSchemeEnum) {

		int offset = 2;
		int cornerRadius = 2;
		if (dimension <= 10) {
			offset = 1;
			cornerRadius = 2;
		}

		ComponentState.ColorSchemeKind kind = componentState
				.getColorSchemeKind();
		int cyclePos = componentState.getCycleCount();

		ColorSchemeEnum colorSchemeEnum = null;
		switch (kind) {
		case CURRENT:
			colorSchemeEnum = mainColorSchemeEnum;
			break;
		case REGULAR:
			colorSchemeEnum = SubstanceLookAndFeel.getColorScheme()
					.getMetallic();
			break;
		case DISABLED:
			colorSchemeEnum = SubstanceLookAndFeel.getColorScheme().getGray();
			break;
		}

		BufferedImage background = getRoundedBackground(dimension - offset,
				dimension - offset, cornerRadius, colorSchemeEnum,
				colorSchemeEnum, cyclePos, null, false, true);
		BufferedImage offBackground = getBlankImage(dimension, dimension);
		Graphics2D graphics = (Graphics2D) offBackground.getGraphics();
		graphics.drawImage(background, offset, offset, null);
		if (componentState.isSelected()) {
			BufferedImage checkMark = getCheckMark(dimension - offset,
					componentState.isEnabled(), colorSchemeEnum);
			graphics.drawImage(checkMark, offset, 0, null);
		}

		return offBackground;
	}

	/**
	 * Retrieves rounded background for the specified parameters.
	 * 
	 * @param width
	 *            Image width.
	 * @param height
	 *            Image height.
	 * @param cornerRadius
	 *            Corner radius.
	 * @param colorSchemeEnum
	 *            Color scheme for the border and the filling
	 * @param cyclePos
	 *            Cycle position index.
	 * @param side
	 *            Straight side (if not <code>null</code>).
	 * @param isSideOpen
	 *            If <code>true</code>, the above side will not have border.
	 */
	public static BufferedImage getRoundedBackground(int width, int height,
			int cornerRadius, ColorSchemeEnum colorSchemeEnum, int cyclePos,
			Side side, boolean isSideOpen) {
		return getRoundedBackground(width, height, cornerRadius,
				colorSchemeEnum, colorSchemeEnum, cyclePos, side, isSideOpen,
				true);
	}

	/**
	 * Retrieves rounded background for the specified parameters which is
	 * rotated by 90 degrees counter clock wise.
	 * 
	 * @param width
	 *            Image width.
	 * @param height
	 *            Image height.
	 * @param cornerRadius
	 *            Corner radius.
	 * @param colorSchemeEnum
	 *            Color scheme for the border and the filling
	 * @param cyclePos
	 *            Cycle position index.
	 * @param side
	 *            Straight side (if not <code>null</code>).
	 * @param isSideOpen
	 *            If <code>true</code>, the above side will not have border.
	 * @return Rounded background for the specified parameters.
	 */
	public static BufferedImage getFlipRoundedButton(int width, int height,
			int cornerRadius, ColorSchemeEnum colorSchemeEnum, int cyclePos,
			Side side, boolean isSideOpen) {
		BufferedImage bi = getRoundedBackground(height, width, cornerRadius,
				colorSchemeEnum, cyclePos, side.getAdjacentSide(), isSideOpen);
		return getRotated(bi, 3);
	}

	/**
	 * Retrieves composite background for the specified parameters. The
	 * composite background consists of three layers:
	 * <ol>
	 * <li>Layer that matches the <code>increased</code> state.
	 * <li>Layer that matches the <code>decreased</code> state.
	 * <li>Regular layer with rounded background.
	 * </ol>
	 * The layers are drawn in the following order:
	 * <ol>
	 * <li>The left half of the first layer
	 * <li>The right half of the second layer
	 * <li>The third layer
	 * </ol>
	 * Combined together, the layers create the image for scrollbar track with
	 * continuation of the arrow increase and decrease buttons.
	 * 
	 * @param width
	 *            Image width.
	 * @param height
	 *            Image height.
	 * @param cornerRadius
	 *            Corner radius.
	 * @param compDecrState
	 *            The <code>decreased</code> state.
	 * @param compIncrState
	 *            The <code>increased</code> state.
	 * @param flipSides
	 *            If <code>true</code>, the drawn halves of the first and the
	 *            second layers above will be swapped.
	 * @return Composite background for the specified parameters.
	 */
	public static BufferedImage getCompositeRoundedBackground(int width,
			int height, int cornerRadius, ComponentState compDecrState,
			ComponentState compIncrState, boolean flipSides) {
		return getCompositeRoundedBackground(SubstanceLookAndFeel
				.getColorScheme(), width, height, cornerRadius, compDecrState,
				compIncrState, flipSides);
	}

	public static BufferedImage getCompositeRoundedBackground(
			ColorSchemeEnum colorSchemeEnum, int width, int height,
			int cornerRadius, ComponentState compDecrState,
			ComponentState compIncrState, boolean flipSides) {

		// System.out.println("paint " + compDecrState.name() + ":"
		// + compIncrState.name());

		ComponentState.ColorSchemeKind decrKind = compDecrState
				.getColorSchemeKind();
		int decrCyclePos = compDecrState.getCycleCount();

		ColorSchemeEnum decrColorSchemeEnum = null;
		switch (decrKind) {
		case CURRENT:
			decrColorSchemeEnum = colorSchemeEnum;
			break;
		case REGULAR:
			decrColorSchemeEnum = colorSchemeEnum.getMetallic();
			break;
		case DISABLED:
			decrColorSchemeEnum = colorSchemeEnum.getGray();
			break;
		}

		BufferedImage decrLayer = getRoundedBackground(width, height, 0,
				decrColorSchemeEnum, decrColorSchemeEnum, decrCyclePos,
				flipSides ? Side.RIGHT : Side.LEFT, true, false);

		ComponentState.ColorSchemeKind incrKind = compIncrState
				.getColorSchemeKind();
		int incrCyclePos = compIncrState.getCycleCount();

		ColorSchemeEnum incrColorSchemeEnum = null;
		switch (incrKind) {
		case CURRENT:
			incrColorSchemeEnum = colorSchemeEnum;
			break;
		case REGULAR:
			incrColorSchemeEnum = colorSchemeEnum.getMetallic();
			break;
		case DISABLED:
			incrColorSchemeEnum = colorSchemeEnum.getGray();
			break;
		}

		BufferedImage incrLayer = getRoundedBackground(width, height, 0,
				incrColorSchemeEnum, incrColorSchemeEnum, incrCyclePos,
				flipSides ? Side.LEFT : Side.RIGHT, true, false);

		BufferedImage mainLayer = getRoundedBackground(width, height,
				cornerRadius, colorSchemeEnum.getGray(), colorSchemeEnum
						.getGray(), 0, null, false, true);

		BufferedImage result = getBlankImage(width, height);
		Graphics2D graphics = (Graphics2D) result.getGraphics();
		if (!flipSides) {
			graphics.drawImage(decrLayer, 0, 0, width / 2, height, 0, 0,
					width / 2, height, null);
			graphics.drawImage(incrLayer, width / 2, 0, width, height,
					width / 2, 0, width, height, null);
		} else {
			graphics.drawImage(incrLayer, 0, 0, width / 2, height, 0, 0,
					width / 2, height, null);
			graphics.drawImage(decrLayer, width / 2, 0, width, height,
					width / 2, 0, width, height, null);
		}
		graphics.drawImage(mainLayer, 0, 0, null);

		return result;
	}

	/**
	 * Retrieves rounded background for the specified parameters.
	 * 
	 * @param width
	 *            Image width.
	 * @param height
	 *            Image height.
	 * @param cornerRadius
	 *            Corner radius.
	 * @param borderSchemeEnum
	 *            Color scheme for the border.
	 * @param fillSchemeEnum
	 *            Color scheme for the filling.
	 * @param cyclePos
	 *            Cycle position index.
	 * @param side
	 *            Straight side (if not <code>null</code>).
	 * @param isSideOpen
	 *            If <code>true</code>, the above side will not have border.
	 * @param hasShine
	 *            If <code>true</code>, shine-spot will be added.
	 * @return Rounded background for the specified parameters.
	 */
	public static BufferedImage getRoundedBackground(int width, int height,
			int cornerRadius, ColorSchemeEnum borderSchemeEnum,
			ColorSchemeEnum fillSchemeEnum, int cyclePos, Side side,
			boolean isSideOpen, boolean hasShine) {

		BufferedImage image = getBlankImage(width, height);
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		boolean hasBorder = (borderSchemeEnum != null);
		ColorScheme borderScheme = hasBorder ? borderSchemeEnum
				.getColorScheme() : null;
		ColorScheme fillScheme = fillSchemeEnum.getColorScheme();

		Color topBorderColor = hasBorder ? borderScheme.getUltraDarkColor()
				: null;
		Color midBorderColor = hasBorder ? borderScheme.getDarkColor() : null;
		Color bottomBorderColor = hasBorder ? borderScheme.getMidColor() : null;

		double cycleCoef = 1.0 - cyclePos / 10.0;
		Color topFillColor = getInterpolatedColor(fillScheme.getDarkColor(),
				fillScheme.getLightColor(), cycleCoef);
		Color midFillColor = getInterpolatedColor(fillScheme.getMidColor(),
				fillScheme.getLightColor(), cycleCoef);
		Color bottomFillColor = getInterpolatedColor(fillScheme
				.getUltraLightColor(), fillScheme.getExtraLightColor(),
				cycleCoef);
		Color topShineColor = getInterpolatedColor(fillScheme
				.getUltraLightColor(), fillScheme.getExtraLightColor(),
				cycleCoef);
		Color bottomShineColor = getInterpolatedColor(fillScheme
				.getLightColor(), fillScheme.getUltraLightColor(), cycleCoef);

		graphics.setStroke(new BasicStroke((float) 1.3, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));

		GradientPaint gradient = new GradientPaint(0, 0, topFillColor, 0,
				height / 2, midFillColor);
		graphics.setPaint(gradient);

		// Fill background
		int vStripeLeft = (side == Side.LEFT) ? 0 : cornerRadius;
		int vStripeRight = (side == Side.RIGHT) ? width
				: (width - 1 - cornerRadius);

		int vStripeWidth = vStripeRight - vStripeLeft + 1;

		int hStripeTop = (side == Side.TOP) ? 0 : cornerRadius;
		int hStripeBottom = (side == Side.BOTTOM) ? height
				: (height - cornerRadius);
		int hStripeHeight = hStripeBottom - hStripeTop;

		// top portion of horizontal stripe
		graphics.fillRect(0, hStripeTop, width, (height / 2) - hStripeTop);
		// top portion of vertical stripe
		graphics.fillRect(vStripeLeft, 0, vStripeWidth, height / 2);

		// top-left
		if ((side != Side.TOP) && (side != Side.LEFT)) {
			graphics.fillArc(0, 0, 2 * cornerRadius, 2 * cornerRadius, 90, 90);
		}
		// top-right
		if ((side != Side.TOP) && (side != Side.RIGHT)) {
			graphics.fillArc(width - 1 - 2 * cornerRadius, 0, 2 * cornerRadius,
					2 * cornerRadius, 0, 90);
		}

		GradientPaint gradient2 = new GradientPaint(0, height / 2,
				midFillColor, 0, height - 2, bottomFillColor);
		graphics.setPaint(gradient2);

		// bottom portion of horizontal stripe
		graphics.fillRect(0, height / 2, width, hStripeBottom - height / 2);
		// bottom portion of vertical stripe
		graphics
				.fillRect(vStripeLeft, height / 2, vStripeWidth, 1 + height / 2);

		// bottom-left
		if ((side != Side.BOTTOM) && (side != Side.LEFT)) {
			graphics.fillArc(0, height - 2 * cornerRadius, 2 * cornerRadius,
					2 * cornerRadius, 180, 90);
		}
		// bottom-right
		if ((side != Side.BOTTOM) && (side != Side.RIGHT)) {
			graphics
					.fillArc(width - 1 - 2 * cornerRadius, height - 2
							* cornerRadius, 2 * cornerRadius, 2 * cornerRadius,
							270, 90);
		}

		if (hasShine) {
			int tsr = topShineColor.getRed();
			int tsg = topShineColor.getGreen();
			int tsb = topShineColor.getBlue();
			int bsr = bottomShineColor.getRed();
			int bsg = bottomShineColor.getGreen();
			int bsb = bottomShineColor.getBlue();

			int[][] fuzzyOval = getFuzzyOvalOpacity(image, cornerRadius, side);

			for (int row = 0; row < height / 2.4; row++) {
				double coef = Math.min(1.0, (double) row
						/ ((double) height / 2.4));
				// interpolate shine color
				int sr = (int) (tsr + coef * (bsr - tsr));
				int sg = (int) (tsg + coef * (bsg - tsg));
				int sb = (int) (tsb + coef * (bsb - tsb));
				for (int col = 0; col < width; col++) {
					int transp = fuzzyOval[col][row];
					if (transp == 0) {
						continue;
					}
					graphics.setColor(new Color(sr, sg, sb, transp));
					graphics.drawLine(col, row, col, row);
				}
			}
		}

		if (hasBorder) {
			// Draw border
			GradientPaint gradient3 = new GradientPaint(0, 0, topBorderColor,
					0, height / 2, midBorderColor);
			graphics.setPaint(gradient3);

			// left and right border for the top half
			boolean toDrawLeftSide = (side != Side.LEFT)
					|| ((side == Side.LEFT) && (!isSideOpen));
			boolean toDrawLeftSideFull = ((side == Side.LEFT) && (!isSideOpen));

			if (toDrawLeftSide) {
				if (toDrawLeftSideFull) {
					graphics.drawLine(0, 0, 0, height / 2);
				} else {
					graphics.drawLine(0, hStripeTop, 0, hStripeTop
							+ hStripeHeight / 2);
				}
			}

			boolean toDrawRightSide = (side != Side.RIGHT)
					|| ((side == Side.RIGHT) && (!isSideOpen));
			boolean toDrawRightSideFull = ((side == Side.RIGHT) && (!isSideOpen));

			if (toDrawRightSide) {
				if (toDrawRightSideFull) {
					graphics.drawLine(width - 1, 0, width - 1, height / 2);
				} else {
					graphics.drawLine(width - 1, hStripeTop, width - 1,
							hStripeTop + hStripeHeight / 2);
				}
			}

			boolean toDrawTopSide = (side != Side.TOP)
					|| ((side == Side.TOP) && (!isSideOpen));
			boolean toDrawTopSideFull = ((side == Side.TOP) && (!isSideOpen));

			if (toDrawTopSide) {
				if (toDrawTopSideFull) {
					graphics.drawLine(0, 0, width, 0);
				} else {
					graphics.drawLine(vStripeLeft, 0, vStripeRight, 0);
				}
			}

			// top-left
			if ((side != Side.TOP) && (side != Side.LEFT)) {
				graphics.drawArc(0, 0, 2 * cornerRadius, 2 * cornerRadius, 90,
						90);
			}
			// top-right
			if ((side != Side.TOP) && (side != Side.RIGHT)) {
				graphics.drawArc(width - 1 - 2 * cornerRadius, 0,
						2 * cornerRadius, 2 * cornerRadius, 0, 90);
			}

			GradientPaint gradient4 = new GradientPaint(0, height / 2,
					midBorderColor, 0, height - 2, bottomBorderColor);
			graphics.setPaint(gradient4);

			// left and right border for the bottom half
			if (toDrawLeftSide) {
				if (toDrawLeftSideFull) {
					graphics.drawLine(0, height / 2, 0, height);
				} else {
					graphics.drawLine(0, hStripeTop + hStripeHeight / 2, 0,
							hStripeBottom);
				}
			}

			if (toDrawRightSide) {
				if (toDrawRightSideFull) {
					graphics.drawLine(width - 1, height / 2, width - 1, height);
				} else {
					graphics.drawLine(width - 1,
							hStripeTop + hStripeHeight / 2, width - 1,
							hStripeBottom);
				}
			}

			boolean toDrawBottomSide = (side != Side.BOTTOM)
					|| ((side == Side.BOTTOM) && (!isSideOpen));
			boolean toDrawBottomSideFull = ((side == Side.BOTTOM) && (!isSideOpen));

			if (toDrawBottomSide) {
				if (toDrawBottomSideFull) {
					graphics.drawLine(0, height - 1, width, height - 1);
				} else {
					graphics.drawLine(vStripeLeft, height - 1, vStripeRight,
							height - 1);
				}
			}

			// bottom-left
			if ((side != Side.BOTTOM) && (side != Side.LEFT)) {
				graphics.drawArc(0, height - 1 - 2 * cornerRadius,
						2 * cornerRadius, 2 * cornerRadius, 180, 90);
			}
			// bottom-right
			if ((side != Side.BOTTOM) && (side != Side.RIGHT)) {
				graphics.drawArc(width - 1 - 2 * cornerRadius, height - 1 - 2
						* cornerRadius, 2 * cornerRadius, 2 * cornerRadius,
						270, 90);
			}
		}

		return image;
	}

	/**
	 * Retrieves rounded background with triangular bottom half for the
	 * specified parameters.
	 * 
	 * @param width
	 *            Image width.
	 * @param height
	 *            Image height.
	 * @param cornerRadius
	 *            Corner radius.
	 * @param componentState
	 *            Component state.
	 * @param mainColorSchemeEnum
	 *            Color scheme.
	 * @return Rounded background with triangular bottom half for the specified
	 *         parameters.
	 */
	public static BufferedImage getRoundedTriangleBackground(int width,
			int height, int cornerRadius, ComponentState componentState,
			ColorSchemeEnum mainColorSchemeEnum) {

		ComponentState.ColorSchemeKind kind = componentState
				.getColorSchemeKind();
		int cyclePos = componentState.getCycleCount();

		ColorSchemeEnum colorSchemeEnum = null;
		switch (kind) {
		case CURRENT:
			colorSchemeEnum = mainColorSchemeEnum;
			break;
		case REGULAR:
			colorSchemeEnum = SubstanceLookAndFeel.getColorScheme()
					.getMetallic();
			break;
		case DISABLED:
			colorSchemeEnum = SubstanceLookAndFeel.getColorScheme().getGray();
			break;
		}

		BufferedImage image = getBlankImage(width, height);
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		boolean hasBorder = (colorSchemeEnum != null);
		ColorScheme borderScheme = hasBorder ? colorSchemeEnum.getColorScheme()
				: null;
		ColorScheme fillScheme = colorSchemeEnum.getColorScheme();

		Color topBorderColor = hasBorder ? borderScheme.getUltraDarkColor()
				: null;
		Color midBorderColor = hasBorder ? borderScheme.getDarkColor() : null;
		Color bottomBorderColor = hasBorder ? borderScheme.getMidColor() : null;

		double cycleCoef = 1.0 - cyclePos / 10.0;
		Color topFillColor = getInterpolatedColor(fillScheme.getDarkColor(),
				fillScheme.getLightColor(), cycleCoef);
		Color midFillColor = getInterpolatedColor(fillScheme.getMidColor(),
				fillScheme.getLightColor(), cycleCoef);
		Color bottomFillColor = getInterpolatedColor(fillScheme
				.getUltraLightColor(), Color.white, cycleCoef);
		Color topShineColor = getInterpolatedColor(fillScheme
				.getUltraLightColor(), Color.white, cycleCoef);
		Color bottomShineColor = getInterpolatedColor(fillScheme
				.getLightColor(), fillScheme.getUltraLightColor(), cycleCoef);

		graphics.setStroke(new BasicStroke((float) 1.3, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));

		GradientPaint gradient = new GradientPaint(0, 0, topFillColor, 0,
				height / 2, midFillColor);
		graphics.setPaint(gradient);

		// Fill background
		int vStripeLeft = cornerRadius;
		int vStripeRight = width - 1 - cornerRadius;

		int vStripeWidth = vStripeRight - vStripeLeft + 1;

		int hStripeTop = cornerRadius;
		int hStripeBottom = height / 2;
		int hStripeHeight = hStripeBottom - hStripeTop;

		// horizontal stripe
		graphics.fillRect(0, hStripeTop, width, hStripeHeight);
		// vertical stripe
		graphics.fillRect(vStripeLeft, 0, vStripeWidth, height / 2);

		// top-left
		graphics.fillArc(0, 0, 2 * cornerRadius, 2 * cornerRadius, 90, 90);
		graphics.fillArc(width - 1 - 2 * cornerRadius, 0, 2 * cornerRadius,
				2 * cornerRadius, 0, 90);

		GradientPaint gradient2 = new GradientPaint(0, height / 2,
				midFillColor, 0, height - 2, bottomFillColor);
		graphics.setPaint(gradient2);

		Polygon triangle = new Polygon();
		triangle.addPoint(0, hStripeBottom);
		triangle.addPoint(width - 1, hStripeBottom);
		triangle.addPoint((width - 1) / 2, height - 1);

		graphics.fillPolygon(triangle);

		int tsr = topShineColor.getRed();
		int tsg = topShineColor.getGreen();
		int tsb = topShineColor.getBlue();
		int bsr = bottomShineColor.getRed();
		int bsg = bottomShineColor.getGreen();
		int bsb = bottomShineColor.getBlue();

		int[][] fuzzyOval = getFuzzyOvalOpacity(image, cornerRadius, null);
		for (int row = 0; row < height / 2.4; row++) {
			double coef = Math.min(1.0, (double) row / ((double) height / 2.4));
			// interpolate shine color
			int sr = (int) (tsr + coef * (bsr - tsr));
			int sg = (int) (tsg + coef * (bsg - tsg));
			int sb = (int) (tsb + coef * (bsb - tsb));
			for (int col = 0; col < width; col++) {
				int transp = fuzzyOval[col][row];
				graphics.setColor(new Color(sr, sg, sb, transp));
				graphics.drawLine(col, row, col, row);
			}
		}

		if (hasBorder) {
			// Draw border
			GradientPaint gradient3 = new GradientPaint(0, 0, topBorderColor,
					0, height / 2, midBorderColor);
			graphics.setPaint(gradient3);

			// left and right border for the top half
			graphics.drawLine(0, hStripeTop, 0, hStripeBottom - 1);
			graphics.drawLine(width - 1, hStripeTop, width - 1,
					hStripeBottom - 1);

			graphics.drawLine(vStripeLeft, 0, vStripeRight, 0);

			// top-left
			graphics.drawArc(0, 0, 2 * cornerRadius, 2 * cornerRadius, 90, 90);
			graphics.drawArc(width - 1 - 2 * cornerRadius, 0, 2 * cornerRadius,
					2 * cornerRadius, 0, 90);

			GradientPaint gradient4 = new GradientPaint(0, height / 2,
					midBorderColor, 0, height - 2, bottomBorderColor);
			graphics.setPaint(gradient4);

			graphics.drawLine(0, hStripeBottom, (width - 1) / 2, height - 1);
			graphics.drawLine(width - 1, hStripeBottom, (width - 1) / 2,
					height - 1);
		}

		return image;
	}

	/**
	 * Returns a one-pixel high line of the specified width that has gradient
	 * based on the parameters.
	 * 
	 * @param width
	 *            The width of the resulting image.
	 * @param colorLeft
	 *            The color of the leftmost pixel.
	 * @param colorRight
	 *            The color of the rightmost pixel.
	 * @param waypoints
	 *            Each entry in this map specifies color for some
	 *            <code>waypoint</code>. The pixels between the waypoints
	 *            will be colored based on the interpolation of the two closest
	 *            waypoints.
	 * @return One-pixel high line of the specified width that has gradient
	 *         based on the parameters.
	 */
	private static BufferedImage getOneLineGradient(int width, Color colorLeft,
			Color colorRight, Map<Integer, Color> waypoints) {
		BufferedImage image = new BufferedImage(width, 1,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics = (Graphics2D) image.getGraphics();

		java.util.List<Integer> waypointMarkers = new ArrayList<Integer>();
		if (waypoints != null) {
			for (Integer marker : waypoints.keySet()) {
				waypointMarkers.add(marker);
			}
		}
		Collections.sort(waypointMarkers);

		int[] markers = new int[waypointMarkers.size() + 2];
		Color[] colors = new Color[waypointMarkers.size() + 2];
		markers[0] = 0;
		colors[0] = colorLeft;
		int index = 1;
		for (Integer marker : waypointMarkers) {
			markers[index] = marker;
			colors[index] = waypoints.get(marker);
			index++;
		}
		markers[index] = width - 1;
		colors[index] = colorRight;

		for (int i = 0; i < (markers.length - 1); i++) {
			GradientPaint gradient = new GradientPaint(markers[i], 0,
					colors[i], markers[i + 1], 0, colors[i + 1]);
			graphics.setPaint(gradient);
			graphics.fillRect(markers[i], 0, markers[i + 1] - markers[i], 1);
		}

		return image;
	}

	/**
	 * Paints a one-pixel high line of the specified width that has gradient
	 * based on the parameters.
	 * 
	 * @param g
	 *            Graphics context.
	 * @param startX
	 *            X start coordinate.
	 * @param startY
	 *            Y start coordinate.
	 * @param dimension
	 *            The dimension of the resulting image. If
	 *            <code>isVertical</code> parameter is <code>true</code>,
	 *            the resulting painting will be 1 pixel high and
	 *            <code>dimension</code> pixels wide, otherwise it will be
	 *            <code>dimension</code> pixels high and 1 pixel wide.
	 * @param isVertical
	 *            Indication of horizontal / vertical orientation.
	 * @param colorLeft
	 *            The color of the leftmost pixel.
	 * @param colorRight
	 *            The color of the rightmost pixel.
	 * @param waypoints
	 *            Each entry in this map specifies color for some
	 *            <code>waypoint</code>. The pixels between the waypoints
	 *            will be colored based on the interpolation of the two closest
	 *            waypoints.
	 */
	private static void paintOneLineGradient(Graphics2D graphics, int x, int y,
			int dimension, boolean isVertical, Color colorLeft,
			Color colorRight, Map<Integer, Color> waypoints) {

		graphics.translate(x, y);
		java.util.List<Integer> waypointMarkers = new ArrayList<Integer>();
		if (waypoints != null) {
			for (Integer marker : waypoints.keySet()) {
				waypointMarkers.add(marker);
			}
		}
		Collections.sort(waypointMarkers);

		int[] markers = new int[waypointMarkers.size() + 2];
		Color[] colors = new Color[waypointMarkers.size() + 2];
		markers[0] = 0;
		colors[0] = colorLeft;
		int index = 1;
		for (Integer marker : waypointMarkers) {
			markers[index] = marker;
			colors[index] = waypoints.get(marker);
			index++;
		}
		markers[index] = dimension - 1;
		colors[index] = colorRight;

		if (!isVertical) {
			for (int i = 0; i < (markers.length - 1); i++) {
				GradientPaint gradient = new GradientPaint(markers[i], 0,
						colors[i], markers[i + 1], 0, colors[i + 1]);
				graphics.setPaint(gradient);
				graphics
						.fillRect(markers[i], 0, markers[i + 1] - markers[i], 1);
			}
		} else {
			for (int i = 0; i < (markers.length - 1); i++) {
				GradientPaint gradient = new GradientPaint(0, markers[i],
						colors[i], 0, markers[i + 1], colors[i + 1]);
				graphics.setPaint(gradient);
				graphics
						.fillRect(0, markers[i], 1, markers[i + 1] - markers[i]);
			}
		}
		graphics.translate(-x, -y);
	}

	/**
	 * Overlays light-colored echo below the specified image.
	 * 
	 * @param image
	 *            The input image.
	 * @param colorSchemeEnum
	 *            Color scheme for creating the echo.
	 * @param offsetX
	 *            X offset of the echo.
	 * @param offsetY
	 *            Y offset of the echo.
	 */
	private static void overlayEcho(BufferedImage image,
			ColorSchemeEnum colorSchemeEnum, int offsetX, int offsetY) {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage echo = getBlankImage(width, height);

		int echoColor = colorSchemeEnum.getColorScheme().getUltraLightColor()
				.getRGB();
		// compute echo
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				int color = image.getRGB(col, row);
				int transp = (color >>> 24) & 0xFF;
				if (transp == 255) {
					int newX = col + offsetX;
					if ((newX < 0) || (newX >= width)) {
						continue;
					}
					int newY = row + offsetY;
					if ((newY < 0) || (newY >= height)) {
						continue;
					}
					echo.setRGB(newX, newY, echoColor);
				}
			}
		}
		// overlay echo
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				int color = image.getRGB(col, row);
				int transp = (color >>> 24) & 0xFF;
				if (transp == 255) {
					continue;
				}
				image.setRGB(col, row, echo.getRGB(col, row));
			}
		}
	}

	/**
	 * Returns <code>minimize</code> icon.
	 * 
	 * @param colorSchemeEnum
	 *            Color scheme for the icon.
	 * @return <code>Minimize</code> icon.
	 */
	public static Icon getMinimizeIcon(ColorSchemeEnum colorSchemeEnum) {
		BufferedImage image = getBlankImage(ICON_DIMENSION, ICON_DIMENSION);
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		int start = (ICON_DIMENSION / 4) - 2;
		int end = (3 * ICON_DIMENSION / 4) - 1;
		int size = end - start - 3;
		graphics
				.setColor(colorSchemeEnum.getColorScheme().getForegroundColor());
		graphics.fillRect(start + 2, end - 2, size, 3);
		overlayEcho(image, colorSchemeEnum, 1, 1);
		return new ImageIcon(image);
	}

	/**
	 * Returns <code>restore</code> icon.
	 * 
	 * @param colorSchemeEnum
	 *            Color scheme for the icon.
	 * @return <code>Restore</code> icon.
	 */
	public static Icon getRestoreIcon(ColorSchemeEnum colorSchemeEnum) {
		BufferedImage image = getBlankImage(ICON_DIMENSION, ICON_DIMENSION);
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		int start = (ICON_DIMENSION / 4) - 2;
		int end = (3 * ICON_DIMENSION / 4) - 1;
		int size = end - start - 3;
		graphics
				.setColor(colorSchemeEnum.getColorScheme().getForegroundColor());
		graphics.drawRect(start, end - size + 1, size, size);
		graphics.drawLine(start, end - size + 2, start + size, end - size + 2);
		graphics.fillRect(end - size, start + 1, size + 1, 2);
		graphics.drawLine(end, start + 1, end, start + size + 1);
		graphics.drawLine(start + size + 2, start + size + 1, end, start + size
				+ 1);
		overlayEcho(image, colorSchemeEnum, 1, 1);
		return new ImageIcon(image);
	}

	/**
	 * Returns <code>maximize</code> icon.
	 * 
	 * @param colorSchemeEnum
	 *            Color scheme for the icon.
	 * @return <code>Maximize</code> icon.
	 */
	public static Icon getMaximizeIcon(ColorSchemeEnum colorSchemeEnum) {
		BufferedImage image = getBlankImage(ICON_DIMENSION, ICON_DIMENSION);
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		int start = (ICON_DIMENSION / 4) - 1;
		int end = (3 * ICON_DIMENSION / 4);
		graphics
				.setColor(colorSchemeEnum.getColorScheme().getForegroundColor());
		graphics.drawRect(start, start, end - start, end - start);
		graphics.drawLine(start, start + 1, end - start + 2, start + 1);
		overlayEcho(image, colorSchemeEnum, 1, 1);
		return new ImageIcon(image);
	}

	/**
	 * Returns <code>close</code> icon.
	 * 
	 * @param colorSchemeEnum
	 *            Color scheme for the icon.
	 * @return <code>Close</code> icon.
	 */
	public static Icon getCloseIcon(ColorSchemeEnum colorSchemeEnum) {
		BufferedImage image = getBlankImage(ICON_DIMENSION, ICON_DIMENSION);
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		int start = (ICON_DIMENSION / 4);
		int end = (3 * ICON_DIMENSION / 4) + 3;

		Stroke stroke = new BasicStroke((float) 2.0, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);

		graphics.setStroke(stroke);
		graphics
				.setColor(colorSchemeEnum.getColorScheme().getForegroundColor());
		graphics.drawLine(start, start, end - start, end - start);
		graphics.drawLine(start, end - start, end - start, start);
		overlayEcho(image, colorSchemeEnum, 1, 1);
		return new ImageIcon(image);
	}

	/**
	 * Returns rectangular gradient background.
	 * 
	 * @param width
	 *            Background width.
	 * @param height
	 *            Background height.
	 * @param colorSchemeEnum
	 *            Color scheme for the background.
	 * @param hasDarkBorder
	 *            If <code>true</code>, the resulting image will have dark
	 *            border.
	 * @return Background matching the parameters.
	 */
	public static BufferedImage getRectangularBackground(int width, int height,
			ColorSchemeEnum colorSchemeEnum, boolean hasDarkBorder) {
		BufferedImage image = getBlankImage(width, height);
		Graphics2D graphics = (Graphics2D) image.getGraphics();

		ColorScheme colorScheme = colorSchemeEnum.getColorScheme();
		Map<Integer, Color> gradColors = new HashMap<Integer, Color>();
		gradColors.put((int) (0.4 * height), colorScheme.getLightColor());
		gradColors.put((int) (0.5 * height), colorScheme.getMidColor());

		BufferedImage horLine = getOneLineGradient(height, colorScheme
				.getUltraLightColor(), colorScheme.getUltraLightColor(),
				gradColors);
		BufferedImage verLine = getRotated(horLine, 1);
		for (int col = 0; col < width; col++) {
			graphics.drawImage(verLine, col, 0, null);
		}
		if (hasDarkBorder) {
			graphics.setColor(colorScheme.getMidColor());
			graphics.drawLine(0, 0, width, 0);
			graphics.drawLine(0, 0, 0, height);
			graphics.drawLine(0, height - 1, width, height - 1);
			graphics.drawLine(width - 1, 0, width - 1, height);
		}
		return image;
	}

	public static void paintRectangularBackground(Graphics g, int startX,
			int startY, int width, int height, ColorSchemeEnum colorSchemeEnum,
			boolean hasDarkBorder, boolean isVertical) {
		Graphics2D graphics = (Graphics2D) g.create();
		graphics.translate(startX, startY);

		ColorScheme colorScheme = colorSchemeEnum.getColorScheme();

		if (!isVertical) {
			Map<Integer, Color> gradColors = new HashMap<Integer, Color>();
			gradColors.put((int) (0.4 * height), colorScheme.getLightColor());
			gradColors.put((int) (0.5 * height), colorScheme.getMidColor());

			BufferedImage horLine = getOneLineGradient(height, colorScheme
					.getUltraLightColor(), colorScheme.getUltraLightColor(),
					gradColors);
			BufferedImage verLine = getRotated(horLine, 1);
			for (int col = 0; col < width; col++) {
				graphics.drawImage(verLine, col, 0, null);
			}
		} else {
			Map<Integer, Color> gradColors = new HashMap<Integer, Color>();
			gradColors.put((int) (0.4 * width), colorScheme.getLightColor());
			gradColors.put((int) (0.5 * width), colorScheme.getMidColor());

			BufferedImage horLine = getOneLineGradient(width, colorScheme
					.getUltraLightColor(), colorScheme.getUltraLightColor(),
					gradColors);
			for (int row = 1; row < height; row++) {
				graphics.drawImage(horLine, 0, row, null);
			}
		}
		if (hasDarkBorder) {
			graphics.setColor(colorScheme.getMidColor());
			graphics.drawLine(0, 0, width - 1, 0);
			graphics.drawLine(0, 0, 0, height - 1);
			graphics.drawLine(0, height - 1, width - 1, height - 1);
			graphics.drawLine(width - 1, 0, width - 1, height - 1);
		}
		graphics.dispose();
	}

	/**
	 * Returns rectangular gradient background.
	 * 
	 * @param width
	 *            Background width.
	 * @param height
	 *            Background height.
	 * @param colorSchemeEnum
	 *            Color scheme for the background.
	 * @param hasDarkBorder
	 *            If <code>true</code>, the resulting image will have dark
	 *            border.
	 * @return Background matching the parameters.
	 */
	public static BufferedImage getSimpleBackground(int width, int height,
			Color colorTop, Color colorBottom) {
		BufferedImage image = getBlankImage(width, height);
		Graphics2D graphics = (Graphics2D) image.getGraphics();

		BufferedImage horLine = getOneLineGradient(height, colorTop,
				colorBottom, null);
		BufferedImage verLine = getRotated(horLine, 1);
		for (int col = 0; col < width; col++) {
			graphics.drawImage(verLine, col, 0, null);
		}
		return image;
	}

	/**
	 * Returns rectangular gradient background.
	 * 
	 * @param width
	 *            Background width.
	 * @param height
	 *            Background height.
	 * @param colorSchemeEnum
	 *            Color scheme for the background.
	 * @param hasDarkBorder
	 *            If <code>true</code>, the resulting image will have dark
	 *            border.
	 * @return Background matching the parameters.
	 */
	public static BufferedImage getBorderedBackground(int width, int height,
			Color colorTop, Color colorBottom) {
		BufferedImage image = getBlankImage(width, height);
		Graphics2D graphics = (Graphics2D) image.getGraphics();

		int offset = 3;
		BufferedImage horLine = getOneLineGradient(1 + height - 2 * offset,
				colorTop, colorBottom, null);
		BufferedImage verLine = getRotated(horLine, 1);
		for (int col = offset; col < (width - offset); col++) {
			graphics.drawImage(verLine, col, offset, null);
		}
		graphics.setPaint(new GradientPaint(0, 0, colorTop, 0, height,
				colorBottom));
		graphics.drawRect(0, 0, width - 1, height - 1);
		graphics.drawRect(1, 1, width - 3, height - 3);
		return image;
	}

	/**
	 * Returns rectangular gradient background with spots and optional
	 * replicated stripe image.
	 * 
	 * @param g
	 *            Graphics context.
	 * @param startX
	 *            X start coordinate.
	 * @param startY
	 *            Y start coordinate.
	 * @param width
	 *            Background width.
	 * @param height
	 *            Background height.
	 * @param colorSchemeEnum
	 *            Color scheme for the background.
	 * @param hasSpots
	 *            If <code>true</code>, the resulting image will have spots.
	 * @param stripeImage
	 *            Stripe image to replicate.
	 * @param stripeOffset
	 *            Offset of the first stripe replication.
	 * @param isVertical
	 *            Indication of horizontal / vertical orientation.
	 */
	public static void paintRectangularStripedBackground(Graphics g,
			int startX, int startY, int width, int height,
			ColorSchemeEnum colorSchemeEnum, BufferedImage stripeImage,
			int stripeOffset, boolean isVertical) {
		Graphics2D graphics = (Graphics2D) g.create(startX, startY, width,
				height);
		if (!isVertical) {
			ColorScheme colorScheme = colorSchemeEnum.getColorScheme();
			Map<Integer, Color> gradColors = new HashMap<Integer, Color>();
			gradColors.put((int) (0.2 * height), colorScheme.getLightColor());
			gradColors.put((int) (0.5 * height), colorScheme.getMidColor());
			gradColors.put((int) (0.8 * height), colorScheme.getLightColor());

			BufferedImage horLine = getOneLineGradient(height, colorScheme
					.getDarkColor(), colorScheme.getDarkColor(), gradColors);
			BufferedImage verLine = getRotated(horLine, 1);
			for (int col = 0; col < width; col++) {
				graphics.drawImage(verLine, col, 0, null);
			}

			if (stripeImage != null) {
				int stripeSize = stripeImage.getHeight();
				int stripeCount = width / stripeSize;
				stripeOffset = stripeOffset % (2 * stripeSize);
				for (int stripe = -2; stripe <= stripeCount; stripe += 2) {
					int stripePos = stripe * stripeSize + stripeOffset;

					graphics.drawImage(stripeImage, stripePos, 0, null);
				}
			}
		} else {
			ColorScheme colorScheme = colorSchemeEnum.getColorScheme();
			Map<Integer, Color> gradColors = new HashMap<Integer, Color>();
			gradColors.put((int) (0.2 * width), colorScheme.getLightColor());
			gradColors.put((int) (0.5 * width), colorScheme.getMidColor());
			gradColors.put((int) (0.8 * width), colorScheme.getLightColor());

			BufferedImage horLine = getOneLineGradient(width, colorScheme
					.getDarkColor(), colorScheme.getDarkColor(), gradColors);
			for (int row = 0; row < height; row++) {
				graphics.drawImage(horLine, 0, row, null);
			}

			if (stripeImage != null) {
				int stripeSize = stripeImage.getWidth();
				int stripeCount = height / stripeSize;
				stripeOffset = stripeOffset % (2 * stripeSize);
				for (int stripe = -2; stripe <= stripeCount; stripe += 2) {
					int stripePos = stripe * stripeSize + stripeOffset;

					graphics.drawImage(stripeImage, 0, stripePos, null);
				}
			}
		}
		graphics.dispose();
	}

	/**
	 * Returns diagonal stripe image.
	 * 
	 * @param baseSize
	 *            Stripe base in pixels.
	 * @return Diagonal stripe image.
	 */
	public static BufferedImage getStripe(int baseSize) {
		int width = (int) (1.8 * baseSize);
		int height = baseSize;
		BufferedImage result = getBlankImage(width, height);
		Graphics2D graphics = (Graphics2D) result.getGraphics();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Polygon polygon = new Polygon();
		polygon.addPoint(0, 0);
		polygon.addPoint(width - 1 - baseSize, 0);
		polygon.addPoint(width - 1, height - 1);
		polygon.addPoint(baseSize, height - 1);

		graphics.setColor(Color.white);
		graphics.fillPolygon(polygon);
		graphics.drawPolygon(polygon);

		float[] BLUR = { 0.10f, 0.10f, 0.10f, 0.10f, 0.30f, 0.10f, 0.10f,
				0.10f, 0.10f };
		ConvolveOp vBlurOp = new ConvolveOp(new Kernel(3, 3, BLUR));
		BufferedImage blurred = vBlurOp.filter(result, null);

		return blurred;
	}

	/**
	 * Returns drag bumps image.
	 * 
	 * @param width
	 *            Drag bumps width.
	 * @param height
	 *            Drag bumps height.
	 * @return Drag bumps image.
	 */
	public static BufferedImage getDragImage(int width, int height) {
		BufferedImage result = getBlankImage(width, height);
		Graphics2D graphics = (Graphics2D) result.getGraphics();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		ColorScheme colorScheme = SubstanceLookAndFeel.getColorScheme()
				.getColorScheme();

		int bumpCellSize = 2 * DRAG_BUMP_DIAMETER;
		int bumpRows = height / bumpCellSize;
		int bumpColumns = (width - 2) / bumpCellSize;

		int bumpRowOffset = (height - bumpCellSize * bumpRows) / 2;
		int bumpColOffset = 1 + (width - bumpCellSize * bumpColumns) / 2;

		for (int col = 0; col < bumpColumns; col++) {
			int cx = bumpColOffset + col * bumpCellSize;
			boolean isEvenCol = (col % 2 == 0);
			int offsetY = isEvenCol ? 0 : DRAG_BUMP_DIAMETER;
			for (int row = 0; row < bumpRows; row++) {
				int cy = offsetY + bumpRowOffset + row * bumpCellSize;
				graphics.setColor(colorScheme.getLightColor());
				graphics.fillOval(cx + 1, cy + 1, DRAG_BUMP_DIAMETER,
						DRAG_BUMP_DIAMETER);
				graphics.setColor(colorScheme.getDarkColor());
				graphics.fillOval(cx, cy, DRAG_BUMP_DIAMETER,
						DRAG_BUMP_DIAMETER);
			}
		}
		return result;
	}

	/**
	 * Retrieves tree icon.
	 * 
	 * @param colorSchemeEnum
	 *            Color scheme.
	 * @param isCollapsed
	 *            Collapsed state.
	 * @return Tree icon.
	 */
	public static BufferedImage getTreeIcon(ColorSchemeEnum colorSchemeEnum,
			boolean isCollapsed) {
		BufferedImage result = getBlankImage(10, 10);
		Graphics2D graphics = (Graphics2D) result.getGraphics();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);

		Polygon pol = new Polygon();
		pol.addPoint(2, 1);
		pol.addPoint(8, 1);
		pol.addPoint(9, 2);
		pol.addPoint(9, 8);
		pol.addPoint(8, 9);
		pol.addPoint(2, 9);
		pol.addPoint(1, 8);
		pol.addPoint(1, 2);

		Color tlFillColor = colorSchemeEnum.isDark() ? colorSchemeEnum
				.getColorScheme().getMidColor().brighter() : Color.white;
		Color brFillColor = colorSchemeEnum.isDark() ? colorSchemeEnum
				.getColorScheme().getDarkColor().brighter() : colorSchemeEnum
				.getColorScheme().getLightColor();
		graphics.setPaint(new GradientPaint(0, 0, tlFillColor, 9, 9,
				brFillColor));
		graphics.fillPolygon(pol);
		Color borderColor = colorSchemeEnum.isDark() ? colorSchemeEnum
				.getColorScheme().getUltraLightColor() : colorSchemeEnum
				.getColorScheme().getMidColor();
		Color signColor = colorSchemeEnum.isDark() ? colorSchemeEnum
				.getColorScheme().getUltraLightColor().brighter().brighter()
				: colorSchemeEnum.getColorScheme().getUltraDarkColor();
		graphics.setColor(borderColor);
		graphics.drawPolygon(pol);
		graphics.setColor(signColor);
		graphics.drawLine(3, 5, 7, 5);
		if (isCollapsed) {
			graphics.drawLine(5, 3, 5, 7);
		}

		return result;
	}

	/**
	 * Retrieves tree node icon.
	 * 
	 * @param colorSchemeEnum
	 *            Color scheme.
	 * @param treeIconKind
	 *            Tree node icon kind.
	 * @return Tree node icon.
	 */
	public static BufferedImage getTreeNodeIcon(TreeIcon treeIconKind) {
		BufferedImage result = getBlankImage(18, 18);
		Graphics2D graphics = (Graphics2D) result.getGraphics();

		ColorSchemeEnum colorSchemeEnum = SubstanceLookAndFeel.getColorScheme();
		ColorScheme colorScheme = SubstanceLookAndFeel.getColorScheme()
				.getColorScheme();

		Polygon backFolder = new Polygon();
		backFolder.addPoint(2, 3);
		backFolder.addPoint(7, 3);
		backFolder.addPoint(8, 4);
		backFolder.addPoint(14, 4);
		backFolder.addPoint(15, 5);
		backFolder.addPoint(15, 13);
		backFolder.addPoint(14, 14);
		backFolder.addPoint(2, 14);
		backFolder.addPoint(1, 13);
		backFolder.addPoint(1, 4);

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);

		Color gradColor1 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor().brighter() : colorScheme
				.getUltraLightColor();
		Color gradColor2 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor() : colorScheme.getMidColor();
		graphics.setPaint(new GradientPaint(0, 0, gradColor1, 17, 17,
				gradColor2));
		graphics.fillPolygon(backFolder);

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Color gradColor3 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor() : colorScheme.getDarkColor();
		Color gradColor4 = colorSchemeEnum.isDark() ? colorScheme
				.getLightColor() : colorScheme.getUltraDarkColor();
		graphics.setPaint(new GradientPaint(0, 0, gradColor3, 17, 17,
				gradColor4));
		graphics.drawPolygon(backFolder);

		switch (treeIconKind) {
		case CLOSED:
			Polygon closedFolder = new Polygon();
			closedFolder.addPoint(1, 8);
			closedFolder.addPoint(9, 8);
			closedFolder.addPoint(10, 7);
			closedFolder.addPoint(15, 7);
			closedFolder.addPoint(15, 13);
			closedFolder.addPoint(14, 14);
			closedFolder.addPoint(2, 14);
			closedFolder.addPoint(1, 13);

			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);

			graphics.setPaint(new GradientPaint(0, 0, gradColor1, 17, 17,
					gradColor2));
			graphics.fillPolygon(closedFolder);

			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			graphics.setPaint(new GradientPaint(0, 0, gradColor3, 17, 17,
					gradColor4));
			graphics.drawPolygon(closedFolder);
			break;
		case OPENED:
			Polygon openedFolder = new Polygon();
			openedFolder.addPoint(2, 13);
			openedFolder.addPoint(5, 7);
			openedFolder.addPoint(11, 7);
			openedFolder.addPoint(12, 8);
			openedFolder.addPoint(17, 8);
			openedFolder.addPoint(14, 14);
			openedFolder.addPoint(2, 14);

			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);

			graphics.setPaint(new GradientPaint(0, 0, gradColor1, 17, 17,
					gradColor2));
			graphics.fillPolygon(openedFolder);

			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			graphics.setPaint(new GradientPaint(0, 0, gradColor3, 17, 17,
					gradColor4));
			graphics.drawPolygon(openedFolder);
			break;
		case UP:
			Polygon arrow = new Polygon();
			arrow.addPoint(6, 14);
			arrow.addPoint(6, 10);
			arrow.addPoint(5, 10);
			arrow.addPoint(8, 7);
			arrow.addPoint(11, 10);
			arrow.addPoint(10, 10);
			arrow.addPoint(10, 14);

			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);

			graphics.setPaint(new GradientPaint(0, 0, gradColor1, 17, 17,
					gradColor2));
			graphics.fillPolygon(arrow);

			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			graphics.setPaint(new GradientPaint(0, 0, gradColor3, 17, 17,
					gradColor4));
			graphics.drawPolygon(arrow);
			break;

		case NONE:
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			graphics.setStroke(new BasicStroke(1.2f));
			graphics.drawLine(5, 9, 11, 9);
			graphics.drawLine(8, 6, 8, 12);
			graphics.drawLine(6, 7, 10, 11);
			graphics.drawLine(6, 11, 10, 7);
		}

		return result;
	}

	/**
	 * Retrieves tree leaf icon.
	 * 
	 * @return Tree leaf icon.
	 */
	public static BufferedImage getTreeLeafIcon() {
		BufferedImage result = getBlankImage(16, 20);
		Graphics2D graphics = (Graphics2D) result.getGraphics();

		ColorSchemeEnum colorSchemeEnum = SubstanceLookAndFeel.getColorScheme();
		ColorScheme colorScheme = colorSchemeEnum.getColorScheme();

		Polygon polygon = new Polygon();
		polygon.addPoint(13, 8);
		polygon.addPoint(13, 16);
		polygon.addPoint(12, 17);
		polygon.addPoint(3, 17);
		polygon.addPoint(2, 16);
		polygon.addPoint(2, 3);
		polygon.addPoint(3, 2);
		polygon.addPoint(9, 2);
		polygon.addPoint(13, 8);
		polygon.addPoint(6, 5);
		polygon.addPoint(9, 2);

		graphics.setClip(2, 2, 12, 16);

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);

		Color gradColor1 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor().brighter() : colorScheme
				.getUltraLightColor();
		Color gradColor2 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor() : colorScheme.getMidColor();
		graphics.setPaint(new GradientPaint(0, 0, gradColor1, 17, 17,
				gradColor2));
		graphics.fillPolygon(polygon);

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Color gradColor3 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor() : colorScheme.getDarkColor();
		Color gradColor4 = colorSchemeEnum.isDark() ? colorScheme
				.getLightColor() : colorScheme.getUltraDarkColor();
		graphics.setPaint(new GradientPaint(0, 0, gradColor3, 17, 17,
				gradColor4));
		graphics.drawPolygon(polygon);

		return result;
	}

	/**
	 * Retrieves home icon.
	 * 
	 * @return Home icon.
	 */
	public static BufferedImage getHomeIcon() {
		BufferedImage result = getBlankImage(18, 18);
		Graphics2D graphics = (Graphics2D) result.getGraphics();

		ColorSchemeEnum colorSchemeEnum = SubstanceLookAndFeel.getColorScheme();
		ColorScheme colorScheme = colorSchemeEnum.getColorScheme();

		Polygon outline = new Polygon();
		outline.addPoint(4, 16);
		outline.addPoint(4, 8);
		outline.addPoint(9, 2);
		outline.addPoint(12, 6);
		outline.addPoint(12, 2);
		outline.addPoint(14, 2);
		outline.addPoint(14, 16);

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);

		Color gradColor1 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor().brighter() : colorScheme
				.getUltraLightColor();
		Color gradColor2 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor() : colorScheme.getMidColor();
		graphics.setPaint(new GradientPaint(0, 0, gradColor1, 17, 17,
				gradColor2));
		graphics.fillPolygon(outline);

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Color gradColor3 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor() : colorScheme.getDarkColor();
		Color gradColor4 = colorSchemeEnum.isDark() ? colorScheme
				.getLightColor() : colorScheme.getUltraDarkColor();
		graphics.setPaint(new GradientPaint(0, 0, gradColor3, 17, 17,
				gradColor4));
		graphics.drawPolygon(outline);

		// roof
		graphics.setStroke(new BasicStroke(2.0f));
		graphics.setColor(colorScheme.getUltraDarkColor());
		graphics.drawLine(3, 9, 9, 2);
		graphics.drawLine(9, 2, 15, 9);

		// door
		graphics.setStroke(new BasicStroke(1.2f));
		graphics.drawLine(7, 16, 7, 10);
		graphics.drawLine(7, 10, 11, 10);
		graphics.drawLine(11, 16, 11, 10);

		// window
		graphics.setStroke(new BasicStroke(1.0f));
		graphics.drawRect(8, 6, 2, 2);

		return result;
	}

	/**
	 * Retrieves computer icon.
	 * 
	 * @return Computer icon.
	 */
	public static BufferedImage getComputerIcon() {
		BufferedImage result = getBlankImage(18, 18);
		Graphics2D graphics = (Graphics2D) result.getGraphics();

		ColorScheme colorScheme = SubstanceLookAndFeel.getColorScheme()
				.getColorScheme();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// tower
		graphics.setPaint(new GradientPaint(0, 0, colorScheme
				.getUltraLightColor(), 17, 17, colorScheme.getMidColor()));
		graphics.fillRect(1, 4, 4, 11);
		graphics.setPaint(new GradientPaint(0, 0, colorScheme.getDarkColor(),
				17, 17, colorScheme.getUltraDarkColor()));
		graphics.drawRect(1, 4, 4, 11);
		graphics.drawLine(2, 6, 4, 6);
		graphics.drawLine(2, 8, 4, 8);
		graphics.drawLine(3, 12, 3, 12);

		// screen
		graphics.setPaint(new GradientPaint(0, 0, colorScheme
				.getUltraLightColor(), 17, 17, colorScheme.getMidColor()));
		graphics.fillRoundRect(8, 4, 9, 8, 2, 2);
		graphics.setPaint(new GradientPaint(0, 0, colorScheme.getDarkColor(),
				17, 17, colorScheme.getUltraDarkColor()));
		graphics.drawRoundRect(7, 4, 9, 8, 2, 2);
		graphics.drawRoundRect(9, 6, 5, 4, 2, 2);
		graphics.fillRect(11, 12, 2, 3);
		graphics.drawLine(9, 15, 14, 15);

		float[] BLUR = { 0.03f, 0.03f, 0.03f, 0.03f, 0.86f, 0.03f, 0.03f,
				0.03f, 0.03f };
		ConvolveOp vBlurOp = new ConvolveOp(new Kernel(3, 3, BLUR));
		BufferedImage blurred = vBlurOp.filter(result, null);
		return blurred;
	}

	/**
	 * Retrieves disk icon.
	 * 
	 * @return Disk icon.
	 */
	public static BufferedImage getDiskIcon() {
		BufferedImage result = getBlankImage(18, 18);
		Graphics2D graphics = (Graphics2D) result.getGraphics();

		ColorSchemeEnum colorSchemeEnum = SubstanceLookAndFeel.getColorScheme();
		ColorScheme colorScheme = colorSchemeEnum.getColorScheme();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Color gradColor1 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor().brighter() : colorScheme
				.getUltraLightColor();
		Color gradColor2 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor() : colorScheme.getMidColor();
		graphics.setPaint(new GradientPaint(0, 0, gradColor1, 17, 17,
				gradColor2));
		graphics.fillRect(3, 4, 10, 10);
		graphics.fillArc(3, 2, 10, 4, 0, 180);
		graphics.fillArc(3, 12, 10, 4, 180, 180);

		Color gradColor3 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor() : colorScheme.getDarkColor();
		Color gradColor4 = colorSchemeEnum.isDark() ? colorScheme
				.getLightColor() : colorScheme.getUltraDarkColor();
		graphics.setPaint(new GradientPaint(0, 0, gradColor3, 17, 17,
				gradColor4));

		graphics.drawOval(3, 2, 10, 4);
		graphics.drawArc(3, 12, 10, 4, 180, 180);
		graphics.drawArc(3, 9, 10, 4, 180, 180);
		graphics.drawArc(3, 6, 10, 4, 180, 180);
		graphics.drawLine(3, 4, 3, 14);
		graphics.drawLine(13, 4, 13, 14);

		return result;
	}

	/**
	 * Retrieves floppy icon.
	 * 
	 * @return Floppy icon.
	 */
	public static BufferedImage getFloppyIcon() {
		BufferedImage result = getBlankImage(18, 18);
		Graphics2D graphics = (Graphics2D) result.getGraphics();

		ColorSchemeEnum colorSchemeEnum = SubstanceLookAndFeel.getColorScheme();
		ColorScheme colorScheme = colorSchemeEnum.getColorScheme();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Polygon outline = new Polygon();
		outline.addPoint(3, 3);
		outline.addPoint(13, 3);
		outline.addPoint(14, 4);
		outline.addPoint(14, 15);
		outline.addPoint(2, 15);
		outline.addPoint(2, 4);

		Color gradColor1 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor().brighter() : colorScheme
				.getUltraLightColor();
		Color gradColor2 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor() : colorScheme.getMidColor();
		graphics.setPaint(new GradientPaint(0, 0, gradColor1, 17, 17,
				gradColor2));
		graphics.fillPolygon(outline);

		Color gradColor3 = colorSchemeEnum.isDark() ? colorScheme
				.getUltraLightColor() : colorScheme.getDarkColor();
		Color gradColor4 = colorSchemeEnum.isDark() ? colorScheme
				.getLightColor() : colorScheme.getUltraDarkColor();
		graphics.setPaint(new GradientPaint(0, 0, gradColor3, 17, 17,
				gradColor4));

		graphics.drawPolygon(outline);

		graphics.drawRect(5, 3, 6, 3);
		graphics.fillRect(9, 3, 3, 3);

		graphics.drawRect(4, 9, 8, 6);
		graphics.drawLine(6, 11, 10, 11);
		graphics.drawLine(6, 13, 10, 13);

		return result;
	}

	/**
	 * Paints Longhorn-inspired rectangular gradient background with gradient
	 * horizontal translucent stripe.
	 * 
	 * @param g
	 *            Graphics context.
	 * @param startX
	 *            X start coordinate.
	 * @param startY
	 *            Y start coordinate.
	 * @param width
	 *            Background width.
	 * @param height
	 *            Background height.
	 * @param colorSchemeEnum
	 *            Color scheme for the background.
	 * @param isVertical
	 *            Indication of horizontal / vertical orientation.
	 */
	public static void paintLonghornProgressBar(Graphics g, int startX,
			int startY, int width, int height, ColorSchemeEnum colorSchemeEnum,
			boolean isVertical) {
		paintRectangularBackground(g, startX, startY, width, height,
				colorSchemeEnum, true, isVertical);

		Graphics2D graphics = (Graphics2D) g.create();
		graphics.translate(startX, startY);

		ColorScheme colorScheme = colorSchemeEnum.getColorScheme();

		if (!isVertical) {
			int topY = (int) Math.floor(0.45 * height);
			int endY = (int) Math.ceil(0.67 * height);

			Color ud = colorScheme.getMidColor();
			int rUd = ud.getRed();
			int gUd = ud.getGreen();
			int bUd = ud.getBlue();
			int tUd = 255;
			int xs = (int) (0.1 * (width - 2));
			int xe = (int) (0.9 * (width - 2));
			int fx = (width - 2) / 2 - xs + 1;
			int dfx = fx / 4;
			for (int y = topY; y < endY; y++) {
				Map<Integer, Color> gradColors = new HashMap<Integer, Color>();
				gradColors.put(xs, new Color(rUd, gUd, bUd, (int) (0.8 * tUd)));
				gradColors.put(xe, new Color(rUd, gUd, bUd, (int) (0.8 * tUd)));
				int dx = dfx + (y - startY) * (fx - dfx) / (endY - startY);
				gradColors.put(xs + dx, new Color(rUd, gUd, bUd,
						(int) (0.05 * tUd)));
				gradColors.put(xe - dx, new Color(rUd, gUd, bUd,
						(int) (0.05 * tUd)));
				paintOneLineGradient(graphics, 0, y, width, isVertical,
						new Color(rUd, gUd, bUd, tUd), new Color(rUd, gUd, bUd,
								tUd), gradColors);
			}
		} else {
			int leftX = (int) Math.floor(0.45 * width);
			int rightX = (int) Math.ceil(0.67 * width);

			Color ud = colorScheme.getMidColor();
			int rUd = ud.getRed();
			int gUd = ud.getGreen();
			int bUd = ud.getBlue();
			int tUd = 255;
			int ys = (int) (0.1 * (height - 2));
			int ye = (int) (0.9 * (height - 2));
			int fy = (height - 2) / 2 - ys + 1;
			int dfy = fy / 4;
			for (int x = leftX; x < rightX; x++) {
				Map<Integer, Color> gradColors = new HashMap<Integer, Color>();
				gradColors.put(ys, new Color(rUd, gUd, bUd, (int) (0.8 * tUd)));
				gradColors.put(ye, new Color(rUd, gUd, bUd, (int) (0.8 * tUd)));
				int dy = dfy + (x - startX) * (fy - dfy) / (rightX - startX);
				gradColors.put(ys + dy, new Color(rUd, gUd, bUd,
						(int) (0.05 * tUd)));
				gradColors.put(ye - dy, new Color(rUd, gUd, bUd,
						(int) (0.05 * tUd)));
				paintOneLineGradient(graphics, x, 0, height - 1, isVertical,
						new Color(rUd, gUd, bUd, tUd), new Color(rUd, gUd, bUd,
								tUd), gradColors);
			}
		}

		graphics.dispose();
	}

	/**
	 * Retrieves a single crayon of the specified color and dimensions for the
	 * crayon panel in color chooser.
	 * 
	 * @param mainColor
	 *            Crayon main color.
	 * @param width
	 *            Crayon width.
	 * @param height
	 *            Crayon height.
	 * @return Crayon image.
	 */
	public static BufferedImage getSingleCrayon(Color mainColor, int width,
			int height) {
		BufferedImage image = getBlankImage(width, height);

		int baseTop = (int) (0.2 * height);

		Graphics2D graphics = (Graphics2D) image.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		int r = mainColor.getRed();
		int g = mainColor.getGreen();
		int b = mainColor.getBlue();
		// light coefficient
		double lc = 0.8;
		int lr = (int) (r + (255 - r) * lc);
		int lg = (int) (g + (255 - g) * lc);
		int lb = (int) (b + (255 - b) * lc);
		// dark coefficient
		double dc = 0.05;
		int dr = (int) ((1.0 - dc) * r);
		int dg = (int) ((1.0 - dc) * g);
		int db = (int) ((1.0 - dc) * b);

		Color lightColor = new Color(lr, lg, lb);
		Color darkColor = new Color(dr, dg, db);

		Map<Integer, Color> fillColorsColor = new HashMap<Integer, Color>();
		fillColorsColor.put((int) (0.3 * width), darkColor);
		fillColorsColor.put((int) (0.5 * width), darkColor);
		fillColorsColor.put((int) (0.9 * width), lightColor);

		Image fillLineColor = getOneLineGradient(width, lightColor, lightColor,
				fillColorsColor);

		for (int y = baseTop; y < height; y++) {
			graphics.drawImage(fillLineColor, 0, y, null);
		}

		int dbwr = lr;
		int dbwg = lg;
		int dbwb = lb;
		int lbwr = 128 + dr / 4;
		int lbwg = 128 + dg / 4;
		int lbwb = 128 + db / 4;
		Map<Integer, Color> fillColorsBW = new HashMap<Integer, Color>();
		fillColorsBW.put((int) (0.3 * width), new Color(dbwr, dbwg, dbwb));
		fillColorsBW.put((int) (0.5 * width), new Color(dbwr, dbwg, dbwb));
		fillColorsBW.put((int) (0.9 * width), new Color(lbwr, lbwg, lbwb));

		Image fillLineBW = getOneLineGradient(width,
				new Color(lbwr, lbwg, lbwb), new Color(lbwr, lbwg, lbwb),
				fillColorsBW);

		int stripeTop = (int) (0.35 * height);
		int stripeHeight = (int) (0.04 * height);
		for (int y = stripeTop; y < (stripeTop + stripeHeight); y++) {
			graphics.drawImage(fillLineBW, 0, y, null);
		}
		graphics.setColor(new Color(lbwr, lbwg, lbwb));
		graphics.drawRect(0, stripeTop, width - 1, stripeHeight);

		// create cap path
		GeneralPath capPath = new GeneralPath();
		capPath.moveTo(0.5f * width - 3, 4);
		capPath.quadTo(0.5f * width, 0, 0.5f * width + 3, 4);
		capPath.lineTo(width - 3, baseTop);
		capPath.lineTo(2, baseTop);
		capPath.lineTo(0.5f * width - 3, 4);

		graphics.setClip(capPath);

		graphics.setPaint(new GradientPaint(0, baseTop / 2, lightColor,
				(int) (0.6 * width), baseTop, mainColor));
		graphics.fillRect(0, 0, width / 2, baseTop);
		graphics.setPaint(new GradientPaint(width, baseTop / 2, lightColor,
				(int) (0.4 * width), baseTop, mainColor));
		graphics.fillRect(width / 2, 0, width / 2, baseTop);

		graphics.setStroke(new BasicStroke((float) 1.3, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));

		graphics.setClip(null);
		graphics
				.setColor(new Color(64 + dr / 2, 64 + dg / 2, 64 + db / 2, 200));
		graphics.drawRect(0, baseTop, width - 1, height - baseTop - 1);
		graphics.draw(capPath);

		graphics.dispose();

		return image;
	}

	/**
	 * Crayon colors.
	 */
	private final static int[] crayonColors = { 0x800000, // Cayenne
			0x808000, // Asparagus
			0x008000, // Clover
			0x008080, // Teal
			0x000080, // Midnight
			0x800080, // Plum
			0x7f7f7f, // Tin
			0x808080, // Nickel

			0x804000, // Mocha
			0x408000, // Fern
			0x008040, // Moss
			0x004080, // Ocean
			0x400080, // Eggplant
			0x800040, // Maroon
			0x666666, // Steel
			0x999999, // Aluminium

			0xff0000, // Maraschino
			0xffff00, // Lemon
			0x00ff00, // Spring
			0x00ffff, // Turquoise
			0x0000ff, // Blueberry
			0xff00ff, // Magenta
			0x4c4c4c, // Iron
			0xb3b3b3, // Magnesium

			0xff8000, // Tangerine
			0x80ff00, // Lime
			0x00ff80, // Sea Foam
			0x0080ff, // Aqua
			0x8000ff, // Grape
			0xff0080, // Strawberry
			0x333333, // Tungsten
			0xcccccc, // Silver

			0xff6666, // Salmon
			0xffff66, // Banana
			0x66ff66, // Flora
			0x66ffff, // Ice
			0x6666ff, // Orchid
			0xff66ff, // Bubblegum
			0x191919, // Lead
			0xe6e6e6, // Mercury

			0xffcc66, // Cantaloupe
			0xccff66, // Honeydew
			0x66ffcc, // Spindrift
			0x66ccff, // Sky
			0xcc66ff, // Lavender
			0xff6fcf, // Carnation
			0x000000, // Licorice
			0xffffff, // Snow
	};

	/**
	 * Retrieves crayon X offset.
	 * 
	 * @param i
	 *            Crayon index.
	 * @return Crayon X offset.
	 */
	private static int crayonX(int i) {
		return (i % 8) * 22 + 4 + ((i / 8) % 2) * 11;
	}

	/**
	 * Retrieves crayon Y offset.
	 * 
	 * @param i
	 *            Crayon index.
	 * @return Crayon Y offset.
	 */
	private static int crayonY(int i) {
		return (i / 8) * 20 + 23;
	}

	/**
	 * Retrieves crayons image for the crayon panel of color chooser.
	 * 
	 * @return Crayons image.
	 */
	public static Image getCrayonsImage() {
		int iw = 195;
		int ih = 208;
		Image image = getBlankImage(iw, ih);
		Graphics2D graphics = (Graphics2D) image.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setColor(new Color(240, 240, 240));
		graphics.fillRect(0, 0, iw, ih);

		for (int i = 0; i < crayonColors.length; i++) {
			Color crayonColor = new Color(0xff000000 | crayonColors[i]);
			Image crayonImage = getSingleCrayon(crayonColor, 22, 120);
			graphics.drawImage(crayonImage, crayonX(i), crayonY(i), null);
		}

		graphics.setColor(new Color(190, 190, 190));
		graphics.drawRoundRect(0, 1, iw - 1, ih - 2, 4, 4);

		graphics.dispose();
		return image;
	}

	/**
	 * Returns an error marker of specified dimension with an <code>X</code>
	 * inside. The resulting image is a circular icon with a diagonal cross and
	 * border.
	 * 
	 * @param dimension
	 *            The diameter of the resulting marker.
	 * @return Error marker of specified dimension with an <code>X</code>
	 *         inside
	 */
	public static BufferedImage getErrorMarker(int dimension,
			ColorSchemeEnum colorSchemeEnum) {
		// new RGB image with transparency channel
		BufferedImage image = new BufferedImage(dimension, dimension,
				BufferedImage.TYPE_INT_ARGB);

		// create new graphics and set anti-aliasing hint
		Graphics2D graphics = (Graphics2D) image.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// background fill
		graphics.setColor(colorSchemeEnum.getColorScheme().getMidColor());
		graphics.fillOval(0, 0, dimension - 1, dimension - 1);

		// create spot in the upper-left corner using temporary graphics
		// with clip set to the icon outline
		GradientPaint spot = new GradientPaint(0, 0, new Color(255, 255, 255,
				200), dimension, dimension, new Color(255, 255, 255, 0));
		Graphics2D tempGraphics = (Graphics2D) graphics.create();
		tempGraphics.setPaint(spot);
		tempGraphics.setClip(new Ellipse2D.Double(0, 0, dimension - 1,
				dimension - 1));
		tempGraphics.fillRect(0, 0, dimension, dimension);
		tempGraphics.dispose();

		// draw outline of the icon
		graphics.setColor(colorSchemeEnum.getColorScheme().getUltraDarkColor());
		graphics.drawOval(0, 0, dimension - 1, dimension - 1);

		// draw the X sign using two paths
		float dimOuter = (float) (0.5f * Math.pow(dimension, 0.75));
		float dimInner = (float) (0.28f * Math.pow(dimension, 0.75));
		float ds = 0.28f * (dimension - 1);
		float de = 0.72f * (dimension - 1);

		// create the paths
		GeneralPath gp1 = new GeneralPath();
		gp1.moveTo(ds, ds);
		gp1.lineTo(de, de);
		GeneralPath gp2 = new GeneralPath();
		gp2.moveTo(de, ds);
		gp2.lineTo(ds, de);
		graphics.setStroke(new BasicStroke(dimOuter, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		graphics.setColor(colorSchemeEnum.getColorScheme().getUltraDarkColor());
		graphics.draw(gp1);
		graphics.draw(gp2);
		graphics.setStroke(new BasicStroke(dimInner, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		graphics.setColor(colorSchemeEnum.getColorScheme().getUltraLightColor()
				.brighter());
		graphics.draw(gp1);
		graphics.draw(gp2);

		// dispose
		graphics.dispose();
		return image;
	}

	/**
	 * Returns an error marker of specified dimension with an <code>X</code>
	 * inside. The resulting image is a circular icon with a diagonal cross and
	 * border.
	 * 
	 * @param dimension
	 *            The diameter of the resulting marker.
	 * @return Error marker of specified dimension with an <code>X</code>
	 *         inside
	 */
	public static Icon getErrorMarkerIcon(int dimension,
			ColorSchemeEnum colorSchemeEnum) {
		return new ImageIcon(getErrorMarker(dimension, colorSchemeEnum));
	}

	/**
	 * Returns a warning marker of specified dimension with an <code>!</code>
	 * inside. The resulting image is a triangular icon with an exclamation
	 * mark.
	 * 
	 * @param dimension
	 *            The side of the resulting marker.
	 * @return Warning marker of specified dimension with an <code>!</code>
	 *         inside.
	 */
	public static BufferedImage getWarningMarker(int dimension,
			ColorSchemeEnum colorSchemeEnum) {
		// new RGB image with transparency channel
		BufferedImage image = new BufferedImage(dimension, dimension,
				BufferedImage.TYPE_INT_ARGB);

		// create new graphics and set anti-aliasing hint
		Graphics2D graphics = (Graphics2D) image.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// create path for the outline
		GeneralPath iconOutlinePath = new GeneralPath();
		float d = dimension - 1;
		float d32 = (float) (0.1 * d * Math.sqrt(3.0) / 2.0);
		float height = (float) (1.1 * d * Math.sqrt(3.0) / 2.0);
		iconOutlinePath.moveTo(0.45f * d, d32);
		iconOutlinePath.quadTo(0.5f * d, 0, 0.55f * d, d32);
		iconOutlinePath.lineTo(0.95f * d, height - d32);
		iconOutlinePath.quadTo(d, height, 0.9f * d, height);
		iconOutlinePath.lineTo(0.1f * d, height);
		iconOutlinePath.quadTo(0, height, 0.05f * d, height - d32);
		iconOutlinePath.lineTo(0.45f * d, d32);

		// fill inside
		graphics.setColor(colorSchemeEnum.getColorScheme().getMidColor());
		graphics.fill(iconOutlinePath);

		// create spot in the upper-left corner using temporary graphics
		// with clip set to the icon outline
		GradientPaint spot = new GradientPaint(0, 0, new Color(255, 255, 255,
				200), dimension, dimension, new Color(255, 255, 255, 0));
		Graphics2D tempGraphics = (Graphics2D) graphics.create();
		tempGraphics.setPaint(spot);
		tempGraphics.setClip(iconOutlinePath);
		tempGraphics.fillRect(0, 0, dimension, dimension);
		tempGraphics.dispose();

		// draw outline of the icon
		graphics.setColor(colorSchemeEnum.getColorScheme().getUltraDarkColor());
		graphics.draw(iconOutlinePath);

		// draw the ! sign
		float dimOuter = (float) (0.5f * Math.pow(dimension, 0.75));
		float dimInner = (float) (0.28f * Math.pow(dimension, 0.75));
		GeneralPath markerPath = new GeneralPath();
		markerPath.moveTo((float) 0.5 * d, (float) 0.3 * height);
		markerPath.lineTo((float) 0.5 * d, (float) 0.6 * height);
		markerPath.moveTo((float) 0.5 * d, (float) 0.85 * height);
		markerPath.lineTo((float) 0.5 * d, (float) 0.85 * height);
		graphics.setStroke(new BasicStroke(dimOuter, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		graphics.setColor(colorSchemeEnum.getColorScheme().getUltraDarkColor());
		graphics.draw(markerPath);
		graphics.setStroke(new BasicStroke(dimInner, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		graphics.setColor(colorSchemeEnum.getColorScheme().getUltraLightColor()
				.brighter());
		graphics.draw(markerPath);

		// dispose
		graphics.dispose();
		return image;
	}

	/**
	 * Returns an info marker of specified dimension with an <code>!</code>
	 * inside. The resulting image is a triangular icon with an exclamation
	 * mark.
	 * 
	 * @param dimension
	 *            The side of the resulting marker.
	 * @return Info marker of specified dimension with an <code>!</code>
	 *         inside.
	 */
	public static BufferedImage getInfoMarker(int dimension,
			ColorSchemeEnum colorSchemeEnum) {
		// new RGB image with transparency channel
		BufferedImage image = new BufferedImage(dimension, dimension,
				BufferedImage.TYPE_INT_ARGB);

		// create new graphics and set anti-aliasing hint
		Graphics2D graphics = (Graphics2D) image.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// create path for the outline
		GeneralPath iconOutlinePath = new GeneralPath();
		float d = dimension - 1;
		float d32 = (float) (0.1 * d * Math.sqrt(3.0) / 2.0);
		float height = (float) (1.1 * d * Math.sqrt(3.0) / 2.0);
		iconOutlinePath.moveTo(0.45f * d, d32);
		iconOutlinePath.quadTo(0.5f * d, 0, 0.55f * d, d32);
		iconOutlinePath.lineTo(0.95f * d, height - d32);
		iconOutlinePath.quadTo(d, height, 0.9f * d, height);
		iconOutlinePath.lineTo(0.1f * d, height);
		iconOutlinePath.quadTo(0, height, 0.05f * d, height - d32);
		iconOutlinePath.lineTo(0.45f * d, d32);

		// fill inside
		graphics.setColor(colorSchemeEnum.getColorScheme().getMidColor());
		graphics.fill(iconOutlinePath);

		// create spot in the upper-left corner using temporary graphics
		// with clip set to the icon outline
		GradientPaint spot = new GradientPaint(0, 0, new Color(255, 255, 255,
				200), dimension, dimension, new Color(255, 255, 255, 0));
		Graphics2D tempGraphics = (Graphics2D) graphics.create();
		tempGraphics.setPaint(spot);
		tempGraphics.setClip(iconOutlinePath);
		tempGraphics.fillRect(0, 0, dimension, dimension);
		tempGraphics.dispose();

		// draw outline of the icon
		graphics.setColor(colorSchemeEnum.getColorScheme().getUltraDarkColor());
		graphics.draw(iconOutlinePath);

		// draw the ! sign
		float dimOuter = (float) (0.5f * Math.pow(dimension, 0.75));
		float dimInner = (float) (0.28f * Math.pow(dimension, 0.75));
		GeneralPath markerPath = new GeneralPath();
		markerPath.moveTo((float) 0.5 * d, (float) 0.3 * height);
		markerPath.lineTo((float) 0.5 * d, (float) 0.3 * height);
		markerPath.moveTo((float) 0.5 * d, (float) 0.55 * height);
		markerPath.lineTo((float) 0.5 * d, (float) 0.8 * height);
		graphics.setStroke(new BasicStroke(dimOuter, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		graphics.setColor(colorSchemeEnum.getColorScheme().getUltraDarkColor());
		graphics.draw(markerPath);
		graphics.setStroke(new BasicStroke(dimInner, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		graphics.setColor(colorSchemeEnum.getColorScheme().getUltraLightColor()
				.brighter());
		graphics.draw(markerPath);

		// dispose
		graphics.dispose();
		return image;
	}

	/**
	 * Returns an info marker of specified dimension with an <code>!</code>
	 * inside. The resulting image is a triangular icon with an exclamation
	 * mark.
	 * 
	 * @param dimension
	 *            The diameter of the resulting marker.
	 * @return Info marker of specified dimension with an <code>!</code>
	 *         inside
	 */
	public static Icon getInfoMarkerIcon(int dimension,
			ColorSchemeEnum colorSchemeEnum) {
		return new ImageIcon(getInfoMarker(dimension, colorSchemeEnum));
	}

	/**
	 * Returns a warning marker of specified dimension with an <code>!</code>
	 * inside. The resulting image is a triangular icon with an exclamation
	 * mark.
	 * 
	 * @param dimension
	 *            The side of the resulting marker.
	 * @return Warning marker of specified dimension with an <code>!</code>
	 *         inside.
	 */
	public static Icon getWarningMarkerIcon(int dimension,
			ColorSchemeEnum colorSchemeEnum) {
		return new ImageIcon(getWarningMarker(dimension, colorSchemeEnum));
	}

	/**
	 * Returns an error marker of specified dimension with an <code>X</code>
	 * inside. The resulting image is a circular icon with a question mark.
	 * 
	 * @param dimension
	 *            The diameter of the resulting marker.
	 * @return Error marker of specified dimension with an <code>?</code>
	 *         inside
	 */
	public static BufferedImage getQuestionMarker(int dimension,
			ColorSchemeEnum colorSchemeEnum) {
		// new RGB image with transparency channel
		BufferedImage image = new BufferedImage(dimension, dimension,
				BufferedImage.TYPE_INT_ARGB);

		// create new graphics and set anti-aliasing hint
		Graphics2D graphics = (Graphics2D) image.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// background fill
		graphics.setColor(colorSchemeEnum.getColorScheme().getMidColor());
		graphics.fillOval(0, 0, dimension - 1, dimension - 1);

		// create spot in the upper-left corner using temporary graphics
		// with clip set to the icon outline
		GradientPaint spot = new GradientPaint(0, 0, new Color(255, 255, 255,
				200), dimension, dimension, new Color(255, 255, 255, 0));
		Graphics2D tempGraphics = (Graphics2D) graphics.create();
		tempGraphics.setPaint(spot);
		tempGraphics.setClip(new Ellipse2D.Double(0, 0, dimension - 1,
				dimension - 1));
		tempGraphics.fillRect(0, 0, dimension, dimension);
		tempGraphics.dispose();

		// draw outline of the icon
		graphics.setColor(colorSchemeEnum.getColorScheme().getUltraDarkColor());
		graphics.drawOval(0, 0, dimension - 1, dimension - 1);

		// draw the ? sign
		float d = dimension - 1;
		float dimOuter = (float) (0.5f * Math.pow(dimension, 0.75));
		float dimInner = (float) (0.28f * Math.pow(dimension, 0.75));

		// create the paths
		GeneralPath markerPath = new GeneralPath();
		markerPath.moveTo((float) 0.3 * d, (float) 0.32 * d);
		markerPath.quadTo((float) 0.3 * d, (float) 0.18 * d, (float) 0.5 * d,
				(float) 0.18 * d);
		markerPath.quadTo((float) 0.7 * d, (float) 0.18 * d, (float) 0.7 * d,
				(float) 0.32 * d);
		markerPath.quadTo((float) 0.7 * d, (float) 0.45 * d, (float) 0.6 * d,
				(float) 0.45 * d);
		markerPath.quadTo((float) 0.5 * d, (float) 0.45 * d, (float) 0.5 * d,
				(float) 0.6 * d);
		markerPath.moveTo((float) 0.5 * d, (float) 0.85 * d);
		markerPath.lineTo((float) 0.5 * d, (float) 0.85 * d);
		graphics.setStroke(new BasicStroke(dimOuter, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		graphics.setColor(colorSchemeEnum.getColorScheme().getUltraDarkColor());
		graphics.draw(markerPath);
		graphics.setStroke(new BasicStroke(dimInner, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		graphics.setColor(colorSchemeEnum.getColorScheme().getUltraLightColor()
				.brighter());
		graphics.draw(markerPath);

		// dispose
		graphics.dispose();
		return image;
	}

	/**
	 * Returns an error marker of specified dimension with an <code>X</code>
	 * inside. The resulting image is a circular icon with a question mark.
	 * 
	 * @param dimension
	 *            The diameter of the resulting marker.
	 * @return Error marker of specified dimension with an <code>?</code>
	 *         inside
	 */
	public static Icon getQuestionMarkerIcon(int dimension,
			ColorSchemeEnum colorSchemeEnum) {
		return new ImageIcon(getQuestionMarker(dimension, colorSchemeEnum));
	}

	public static Icon getHexaMarker(int value, ColorSchemeEnum colorSchemeEnum) {
		BufferedImage result = getBlankImage(9, 9);

		value %= 16;
		Color offColor = null;
		Color onColor = null;
		offColor = colorSchemeEnum.isDark() ? colorSchemeEnum.getColorScheme()
				.getMidColor() : colorSchemeEnum.getColorScheme().getMidColor()
				.darker();
		onColor = colorSchemeEnum.isDark() ? getInterpolatedColor(
				colorSchemeEnum.getColorScheme().getUltraLightColor(),
				Color.white, 0.2) : colorSchemeEnum.getColorScheme()
				.getUltraDarkColor().darker();

		boolean bit1 = ((value & 0x1) != 0);
		boolean bit2 = ((value & 0x2) != 0);
		boolean bit3 = ((value & 0x4) != 0);
		boolean bit4 = ((value & 0x8) != 0);

		Graphics2D graphics = (Graphics2D) result.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setColor(bit1 ? onColor : offColor);
		graphics.fillOval(5, 5, 4, 4);
		graphics.setColor(bit2 ? onColor : offColor);
		graphics.fillOval(5, 0, 4, 4);
		graphics.setColor(bit3 ? onColor : offColor);
		graphics.fillOval(0, 5, 4, 4);
		graphics.setColor(bit4 ? onColor : offColor);
		graphics.fillOval(0, 0, 4, 4);

		graphics.dispose();
		return new ImageIcon(result);
	}

	public static Image getIconHexaMarker(int value,
			ColorSchemeEnum colorSchemeEnum) {
		BufferedImage result = getBlankImage(ICON_DIMENSION, ICON_DIMENSION);

		value %= 16;
		Color offColor = null;
		Color onColor = null;
		offColor = colorSchemeEnum.isDark() ? colorSchemeEnum.getColorScheme()
				.getMidColor() : colorSchemeEnum.getColorScheme().getMidColor()
				.darker();
		onColor = colorSchemeEnum.isDark() ? getInterpolatedColor(
				colorSchemeEnum.getColorScheme().getUltraLightColor(),
				Color.white, 0.2) : colorSchemeEnum.getColorScheme()
				.getUltraDarkColor().darker();

		boolean bit1 = ((value & 0x1) != 0);
		boolean bit2 = ((value & 0x2) != 0);
		boolean bit3 = ((value & 0x4) != 0);
		boolean bit4 = ((value & 0x8) != 0);

		Graphics2D graphics = (Graphics2D) result.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setColor(bit1 ? onColor : offColor);
		graphics.fillOval(9, 9, 6, 6);
		graphics.setColor(bit2 ? onColor : offColor);
		graphics.fillOval(9, 0, 6, 6);
		graphics.setColor(bit3 ? onColor : offColor);
		graphics.fillOval(0, 9, 6, 6);
		graphics.setColor(bit4 ? onColor : offColor);
		graphics.fillOval(0, 0, 6, 6);

		graphics.dispose();
		return result;
	}

	public static Icon getSearchIcon(int dimension,
			ColorSchemeEnum colorSchemeEnum) {
		BufferedImage result = getBlankImage(dimension, dimension);

		Graphics2D graphics = (Graphics2D) result.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Color color = colorSchemeEnum.getColorScheme().getForegroundColor();
		graphics.setColor(color);

		graphics.setStroke(new BasicStroke(1.5f));
		int xc = (int) (0.6 * dimension);
		int yc = (int) (0.45 * dimension);
		int r = (int) (0.3 * dimension);

		graphics.drawOval(xc - r, yc - r, 2 * r, 2 * r);

		graphics.setStroke(new BasicStroke(3.0f));
		GeneralPath handle = new GeneralPath();
		handle.moveTo((float) (xc - r / Math.sqrt(2.0)), (float) (yc + r
				/ Math.sqrt(2.0)));
		handle.lineTo(1.8f, dimension - 2.2f);
		graphics.draw(handle);

		graphics.dispose();
		return new ImageIcon(result);
	}

	public static Icon getRibbonBandExpandIcon(int dimension,
			ColorSchemeEnum colorSchemeEnum) {
		BufferedImage result = getBlankImage(dimension, dimension);

		Graphics2D graphics = (Graphics2D) result.getGraphics().create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Color color = colorSchemeEnum.getColorScheme().getForegroundColor();
		graphics.setColor(color);

		graphics.setStroke(new BasicStroke(1.8f));
		float dim = dimension;
		GeneralPath plus = new GeneralPath();
		plus.moveTo(0.28f * dim, 0.5f * dim);
		plus.lineTo(0.72f * dim, 0.5f * dim);
		plus.moveTo(0.5f * dim, 0.28f * dim);
		plus.lineTo(0.5f * dim, 0.72f * dim);
		graphics.draw(plus);

		graphics.dispose();
		return new ImageIcon(result);
	}

}
