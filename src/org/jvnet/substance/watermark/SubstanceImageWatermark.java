package org.jvnet.substance.watermark;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.AccessControlException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.jvnet.substance.SubstanceImageCreator;

/**
 * Implementation of {@link SubstanceWatermark}, drawing specified image as
 * watermark.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceImageWatermark implements SubstanceWatermark {
	/**
	 * Watermark image (screen-sized).
	 */
	private static Image watermarkImage = null;

	/**
	 * The original image (as read from the disk).
	 */
	private BufferedImage origImage;

	/**
	 * Simple constructor. Uses {@link System#getProperty(String)} with
	 * <code>substancelaf.watermark.image</code> parameter to retrieve the
	 * location of the image. Note that this constructor can throw
	 * AccessControlException if this class is used in unsecure JNLP
	 * environment. However, in this case we must have access to local disk in
	 * any case since we need to read the image.
	 */
	public SubstanceImageWatermark() throws AccessControlException {
		this(System.getProperty("substancelaf.watermark.image"));
	}

	/**
	 * Creates an instance with specified image.
	 * 
	 * @param imageLocation
	 */
	public SubstanceImageWatermark(String imageLocation) {
		if (imageLocation != null) {
			try {
				if (imageLocation.startsWith("http")) {
					URL url = new URL(imageLocation);
					this.origImage = ImageIO.read(url);
				} else {
					this.origImage = ImageIO.read(new File(imageLocation));
				}
			} catch (Exception exc) {
				// ignore - probably specified incorrect file
				// or file is not image
			}
		}
	}

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
		graphics.drawImage(SubstanceImageWatermark.watermarkImage, x, y, x
				+ width, y + height, x + dx, y + dy, x + dx + width, y + dy
				+ height, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#updateWatermarkImage()
	 */
	public boolean updateWatermarkImage() {
		if (this.origImage == null) {
			return false;
		}

		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenDim.width;
		int screenHeight = screenDim.height;
		SubstanceImageWatermark.watermarkImage = SubstanceImageCreator
				.getBlankImage(screenWidth, screenHeight);

		Graphics2D graphics = (Graphics2D) SubstanceImageWatermark.watermarkImage
				.getGraphics().create();

		Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.2f);
		graphics.setComposite(comp);

		// decide if need to scale or center
		int origImageWidth = this.origImage.getWidth();
		int origImageHeight = this.origImage.getHeight();

		boolean isWidthFits = (origImageWidth <= screenWidth);
		boolean isHeightFits = (origImageHeight <= screenHeight);

		// see of need to scale or simply center
		if (isWidthFits && isHeightFits) {
			graphics.drawImage(this.origImage,
					(screenWidth - origImageWidth) / 2,
					(screenHeight - origImageHeight) / 2, null);
			graphics.dispose();
			return true;
		}
		if (isWidthFits) {
			// height doesn't fit
			double scaleFact = (double) screenHeight / (double) origImageHeight;
			int dx = (int) (screenWidth - scaleFact * origImageWidth) / 2;
			graphics.drawImage(this.origImage, dx, 0, screenWidth - dx,
					screenHeight, 0, 0, origImageWidth, origImageHeight, null);
			graphics.dispose();
			return true;
		}
		if (isHeightFits) {
			// width doesn't fit
			double scaleFact = (double) screenWidth / (double) origImageWidth;
			int dy = (int) (screenHeight - scaleFact * origImageHeight) / 2;
			graphics.drawImage(this.origImage, 0, dy, screenWidth, screenHeight
					- dy, 0, 0, origImageWidth, origImageHeight, null);
			graphics.dispose();
			return true;
		}
		// none fits
		double scaleFactY = (double) screenHeight / (double) origImageHeight;
		double scaleFactX = (double) screenWidth / (double) origImageWidth;
		double scaleFact = Math.min(scaleFactX, scaleFactY);
		int dx = Math.max(0,
				(int) (screenWidth - scaleFact * origImageWidth) / 2);
		int dy = Math.max(0,
				(int) (screenHeight - scaleFact * origImageHeight) / 2);
		graphics.drawImage(this.origImage, dx, dy, screenWidth - dx,
				screenHeight - dy, 0, 0, origImageWidth, origImageHeight, null);
		graphics.dispose();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#getDisplayName()
	 */
	public String getDisplayName() {
		return SubstanceImageWatermark.getName();
	}

	public static String getName() {
		return "Image";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.watermark.SubstanceWatermark#isDependingOnTheme()
	 */
	public boolean isDependingOnTheme() {
		return false;
	}
}