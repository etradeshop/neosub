package org.jvnet.substance;

import org.jvnet.substance.color.ColorSchemeEnum;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalProgressBarUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * UI for progress bars in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceProgressBarUI extends MetalProgressBarUI {
	/**
	 * Hash for computed stripe images.
	 */
	private static Map<String, BufferedImage> stripeMap = new HashMap<String, BufferedImage>();

	/**
	 * Resets image maps (used when setting new theme).
	 * 
	 * @see SubstanceLookAndFeel#setCurrentTheme(String)
	 * @see SubstanceLookAndFeel#setCurrentTheme(SubstanceTheme)
	 */
	static synchronized void reset() {
		stripeMap.clear();
	}

	/**
	 * The current state of the indeterminate animation's cycle. 0, the initial
	 * value, means paint the first frame. When the progress bar is
	 * indeterminate and showing, the default animation thread updates this
	 * variable by invoking incrementAnimationIndex() every repaintInterval
	 * milliseconds.
	 */
	private int animationIndex;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new SubstanceProgressBarUI();
	}

	/**
	 * Retrieves stripe image.
	 * 
	 * @param baseSize
	 *            Stripe base in pixels.
	 * @return Stripe image.
	 */
	private static synchronized BufferedImage getStripe(int baseSize,
			boolean isRotated) {
		String key = "" + baseSize + ":" + isRotated;
		BufferedImage result = stripeMap.get(key);
		if (result == null) {
			result = SubstanceImageCreator.getStripe(baseSize);
			if (isRotated) {
				result = SubstanceImageCreator.getRotated(result, 1);
			}
			stripeMap.put(key, result);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicProgressBarUI#paintDeterminate(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	@Override
	public void paintDeterminate(Graphics g, JComponent c) {
		if (!(g instanceof Graphics2D)) {
			return;
		}

		Insets b = progressBar.getInsets(); // area for border
		int barRectWidth = progressBar.getWidth() - (b.right + b.left);
		int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

		// amount of progress to draw
		int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

		Graphics2D graphics = (Graphics2D) g;

		// background
		if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
			SubstanceImageCreator.paintRectangularStripedBackground(graphics,
					b.left, b.top, barRectWidth, barRectHeight + 1,
					SubstanceLookAndFeel.getColorScheme().getGray(), null, 0,
					false);
		} else { // VERTICAL
			SubstanceImageCreator.paintRectangularStripedBackground(graphics,
					b.left, b.top, barRectWidth + 1, barRectHeight,
					SubstanceLookAndFeel.getColorScheme().getGray(), null, 0,
					true);
		}

		if (amountFull > 0) {

			ColorSchemeEnum fillColorSchemeEnum = progressBar.isEnabled() ? SubstanceLookAndFeel
					.getColorScheme()
					: SubstanceLookAndFeel.getColorScheme().getMetallic();
			if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
				if (c.getComponentOrientation().isLeftToRight()) {
					SubstanceImageCreator.paintLonghornProgressBar(g, b.left,
							b.top, amountFull, barRectHeight,
							fillColorSchemeEnum, false);
				} else {
					SubstanceImageCreator.paintLonghornProgressBar(g,
							barRectWidth + b.left, b.top, amountFull,
							barRectHeight, fillColorSchemeEnum, false);
				}
			} else { // VERTICAL
				SubstanceImageCreator.paintLonghornProgressBar(g, b.left,
						b.top, barRectWidth, amountFull, 
						fillColorSchemeEnum, true);
			}
		}

		// Deal with possible text painting
		if (progressBar.isStringPainted()) {
			paintString(g, b.left, b.top, barRectWidth, barRectHeight,
					amountFull, b);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicProgressBarUI#paintIndeterminate(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	@Override
	public void paintIndeterminate(Graphics g, JComponent c) {
		if (!(g instanceof Graphics2D)) {
			return;
		}

		Insets b = progressBar.getInsets(); // area for border
		int barRectWidth = progressBar.getWidth() - (b.right + b.left);
		int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

		int valComplete = animationIndex;// int)
		// (progressBar.getPercentComplete()
		// * barRectWidth);
		Graphics2D graphics = (Graphics2D) g;

		// background
		if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
			SubstanceImageCreator.paintRectangularStripedBackground(graphics,
					b.left, b.top, barRectWidth, barRectHeight + 1,
					SubstanceLookAndFeel.getColorScheme().getGray(), null, 0,
					false);
		} else { // VERTICAL
			SubstanceImageCreator.paintRectangularStripedBackground(graphics,
					b.left, b.top, barRectWidth + 1, barRectHeight,
					SubstanceLookAndFeel.getColorScheme().getGray(), null, 0,
					true);
		}

		ColorSchemeEnum fillColorSchemeEnum = progressBar.isEnabled() ? SubstanceLookAndFeel
				.getColorScheme()
				: SubstanceLookAndFeel.getColorScheme().getMetallic();
		if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
			SubstanceImageCreator.paintRectangularStripedBackground(graphics,
					b.left, b.top, barRectWidth, barRectHeight + 1,
					fillColorSchemeEnum, getStripe(barRectHeight + 1, false),
					valComplete, false);
		} else { // VERTICAL
			SubstanceImageCreator.paintRectangularStripedBackground(graphics,
					b.left, b.top, barRectWidth + 1, barRectHeight,
					fillColorSchemeEnum, getStripe(barRectWidth + 1, true),
					valComplete, true);
		}

		// Deal with possible text painting
		if (progressBar.isStringPainted()) {
			paintString(g, b.left, b.top, barRectWidth, barRectHeight,
					barRectWidth, b);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicProgressBarUI#incrementAnimationIndex()
	 */
	@Override
	protected void incrementAnimationIndex() {
		int newValue = animationIndex + 1;

		Insets b = progressBar.getInsets(); // area for border
		int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);
		int barRectWidth = progressBar.getWidth() - (b.right + b.left);
		int threshold = 0;
		if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
			threshold = 2 * barRectHeight + 1;
		} else {
			threshold = 2 * barRectWidth + 1;
		}
		animationIndex = newValue % threshold;
		progressBar.repaint();
	}

	static String getMemoryUsage() {
		StringBuffer sb = new StringBuffer();
		sb.append("SubstanceProgressBarUI: \n");
		sb.append("\t" + stripeMap.size() + " stripes");
		return sb.toString();
	}

}
