package org.jvnet.substance.watermark;

import java.awt.Component;
import java.awt.Graphics;

/**
 * Interface for watermarks.
 * 
 * @author Kirill Grouchnikov
 */
public interface SubstanceWatermark {
	/**
	 * Draws the watermark on the specified graphics context in the specified
	 * region.
	 * 
	 * @param graphics
	 *            Graphics context.
	 * @param c
	 *            Component that is painted.
	 * @param x
	 *            Left X of the region.
	 * @param y
	 *            Top Y of the region.
	 * @param width
	 *            Region width.
	 * @param height
	 *            Region height.
	 */
	public void drawWatermarkImage(Graphics graphics, Component c, int x,
			int y, int width, int height);

	/**
	 * Updates the current watermark image.
	 */
	public boolean updateWatermarkImage();

	/**
	 * Returns the display name of <code>this</code> watermark.
	 * 
	 * @return Display name of <code>this</code> watermark.
	 */
	public String getDisplayName();

	/**
	 * Returns indication whether <code>this</code> watermark depends on the
	 * current {@link org.jvnet.substance.theme.SubstanceTheme}.
	 * 
	 * @return <code>true</code> if <code>this</code> watermark depends on
	 *         the current {@link org.jvnet.substance.theme.SubstanceTheme},
	 *         <code>false</code> otherwise.
	 */
	public boolean isDependingOnTheme();
}
