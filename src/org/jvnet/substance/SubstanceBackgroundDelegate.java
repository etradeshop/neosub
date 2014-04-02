package org.jvnet.substance;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.AbstractButton;
import javax.swing.JButton;

import org.jvnet.substance.color.ColorSchemeEnum;

import org.jvnet.substance.theme.SubstanceTheme;

/**
 * Delegate class for painting backgrounds of buttons in <b>Substance</b> look
 * and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceBackgroundDelegate {
	/**
	 * Cache for background images with completely round corners. Each time
	 * {@link #getBackground(AbstractButton, int, int, boolean)} is called with
	 * <code>isRoundCorners</code> equal to <code>true</code>, it checks
	 * <code>this</code> map to see if it already contains such background. If
	 * so, the background from the map is returned.
	 */
	private static Map<String, BufferedImage> roundBackgrounds = new HashMap<String, BufferedImage>();

	/**
	 * Cache for background images with regular slightly-round corners. Each
	 * time {@link #getBackground(AbstractButton, int, int, boolean)} is called
	 * with <code>isRoundCorners</code> equal to <code>false</code>, it
	 * checks <code>this</code> map to see if it already contains such
	 * background. If so, the background from the map is returned.
	 */
	private static Map<String, BufferedImage> regularBackgrounds = new HashMap<String, BufferedImage>();

	/**
	 * Cache for background images for scrollbar backgrounds. Each time {@link
	 * #getPairwiseBackground(AbstractButton, int, int,
	 * SubstanceImageCreator.Side)} is called with <code>isRoundCorners</code>
	 * equal to <code>false</code>, it checks <code>this</code> map to see
	 * if it already contains such background. If so, the background from the
	 * map is returned.
	 */
	private static Map<String, BufferedImage> scrollBarBackgrounds = new HashMap<String, BufferedImage>();

	/**
	 * Cache for background images of toggle buttons. Each time
	 * {@link #getStripBackground(AbstractButton, int, int)} is called with
	 * <code>isRoundCorners</code> equal to <code>true</code>, it checks
	 * <code>this</code> map to see if it already contains such background. If
	 * so, the background from the map is returned.
	 */
	private static Map<String, BufferedImage> stripBackgrounds = new HashMap<String, BufferedImage>();

	/**
	 * Cache for background images of ribbon buttons. Each time
	 * {@link #getRibbonBackground(AbstractButton, int, int)} is called with
	 * <code>isRoundCorners</code> equal to <code>true</code>, it checks
	 * <code>this</code> map to see if it already contains such background. If
	 * so, the background from the map is returned.
	 */
	private static Map<String, BufferedImage> ribbonBackgrounds = new HashMap<String, BufferedImage>();

	/**
	 * Fill background delegate.
	 */
	private static SubstanceFillBackgroundDelegate fillDelegate = new SubstanceFillBackgroundDelegate();

	/**
	 * Cache that stores information on title pane buttons.
	 */
	private static WeakHashMap<AbstractButton, SubstanceButtonUI.ButtonTitleKind> titleButtons = new WeakHashMap<AbstractButton, SubstanceButtonUI.ButtonTitleKind>();

	/**
	 * Resets image maps (used when setting new theme).
	 * 
	 * @see SubstanceLookAndFeel#setCurrentTheme(String)
	 * @see SubstanceLookAndFeel#setCurrentTheme(SubstanceTheme)
	 */
	static synchronized void reset() {
		regularBackgrounds.clear();
		stripBackgrounds.clear();
		scrollBarBackgrounds.clear();
		roundBackgrounds.clear();
	}

	/**
	 * Marks the specified button as a title button.
	 * 
	 * @param button
	 *            Button to track.
	 * @param kind
	 *            Title kind.
	 */
	public static synchronized void trackTitleButton(AbstractButton button,
			SubstanceButtonUI.ButtonTitleKind kind) {
		titleButtons.put(button, kind);
	}

	/**
	 * Retrieves (title) kind of the specified button.
	 * 
	 * @param button
	 *            Button.
	 * @return (Title) kind of the specified button.
	 */
	public static synchronized SubstanceButtonUI.ButtonTitleKind getKind(
			AbstractButton button) {
		SubstanceButtonUI.ButtonTitleKind result = titleButtons.get(button);
		if (result == null)
			result = SubstanceButtonUI.ButtonTitleKind.NONE;
		return result;
	}

	/**
	 * Retrieves background image for the specified button.
	 * 
	 * @param button
	 *            Button.
	 * @param width
	 *            Button width.
	 * @param height
	 *            Button height.
	 * @param isRoundCorners
	 *            If <code>true</code>, the corners of the resulting button
	 *            will be completely rounded.
	 * @return Button background image.
	 */
	private static synchronized BufferedImage getBackground(
			AbstractButton button, int width, int height, boolean isRoundCorners) {
		ComponentState state = ComponentState.getState(button.getModel(),
				button);
		ComponentState.ColorSchemeKind kind = state.getColorSchemeKind();
		int cyclePos = state.getCycleCount();
		if (button instanceof JButton) {
			JButton jb = (JButton) button;
			if (jb.isDefaultButton()
					&& (state != ComponentState.PRESSED_SELECTED)
					&& (state != ComponentState.PRESSED_UNSELECTED)) {
				cyclePos = (int) (PulseTracker.getCycles(jb) % 20);
				if (cyclePos > 10) {
					cyclePos = 19 - cyclePos;
				}
			}
		}

		ColorSchemeEnum colorSchemeEnum = null;
		switch (kind) {
		case CURRENT:
			// SubstanceButtonUI.ButtonTitleKind buttonKind = getKind(button);
			// switch (buttonKind) {
			// case NONE:
			colorSchemeEnum = SubstanceLookAndFeel.getColorScheme();
			// break;
			// case REGULAR:
			// case REGULAR_DI:
			// colorSchemeEnum = ColorSchemeEnum.CARIBBEAN_BLUE;
			// break;
			// case CLOSE:
			// case CLOSE_DI:
			// colorSchemeEnum = ColorSchemeEnum.CRIMSON;
			// }
			break;
		case REGULAR:
			colorSchemeEnum = SubstanceLookAndFeel.getColorScheme()
					.getMetallic();
			break;
		case DISABLED:
			colorSchemeEnum = SubstanceLookAndFeel.getColorScheme().getGray();
			break;
		}

		String key = width + ":" + height + ":" + kind.name() + ":" + cyclePos
				+ ":" + colorSchemeEnum.name() + ":"
				+ button.getClass().getName();
		Map<String, BufferedImage> backgrounds = isRoundCorners ? roundBackgrounds
				: regularBackgrounds;
		if (!backgrounds.containsKey(key)) {
			BufferedImage newBackground;

			int radius = 2;
			if (isRoundCorners) {
				if (width > height) {
					radius = height / 2;
				} else {
					radius = width / 2;
				}
			}

			SubstanceImageCreator.Side side = null;
			if (button instanceof SubstanceComboBoxButton) {
				side = SubstanceImageCreator.Side.LEFT;
			}
			newBackground = SubstanceImageCreator.getRoundedBackground(width,
					height, radius, colorSchemeEnum, cyclePos, side);

			backgrounds.put(key, newBackground);
		}
		return backgrounds.get(key);
	}

	/**
	 * Retrieves background image for the specified button.
	 * 
	 * @param button
	 *            Button.
	 * @param width
	 *            Button width.
	 * @param height
	 *            Button height.
	 * @return Button background image.
	 */


	/**
	 * Retrieves background image for the specified button.
	 * 
	 * @param button
	 *            Button.
	 * @param width
	 *            Button width.
	 * @param height
	 *            Button height.
	 * @return Button background image.
	 */
	private static synchronized BufferedImage getRibbonBackground(
			AbstractButton button, int width, int height) {
		ComponentState state = ComponentState.getState(button.getModel(),
				button);
		ComponentState.ColorSchemeKind kind = state.getColorSchemeKind();
		int cyclePos = state.getCycleCount();

		boolean hasActiveBackground = (kind == ComponentState.ColorSchemeKind.CURRENT);
		if (!hasActiveBackground) {
			// current background + watermark will be drawn
			return null;
		}

		String key = width + ":" + height + ":" + kind.name() + ":" + cyclePos
				+ ":" + button.getClass().getName();
		if (!ribbonBackgrounds.containsKey(key)) {
			BufferedImage newBackground;

			newBackground = SubstanceImageCreator.getRoundedBackground(width,
					height, height / 3, SubstanceLookAndFeel.getColorScheme(),
					SubstanceLookAndFeel.getColorScheme(), cyclePos,
					SubstanceImageCreator.Side.BOTTOM, true, true);

			ribbonBackgrounds.put(key, newBackground);
		}
		return ribbonBackgrounds.get(key);
	}

	/**
	 * Retrieves background image for the specified button in button pair (such
	 * as scrollbar arrows, for example).
	 * 
	 * @param button
	 *            Button.
	 * @param width
	 *            Button width.
	 * @param height
	 *            Button height.
	 * @param side
	 *            Button orientation.
	 * @return Button background image.
	 */
	private static synchronized BufferedImage getPairwiseBackground(
			AbstractButton button, int width, int height,
			SubstanceImageCreator.Side side) {
		ComponentState state = ComponentState.getState(button.getModel(),
				button);
		ComponentState.ColorSchemeKind kind = state.getColorSchemeKind();
		int cyclePos = state.getCycleCount();

		ColorSchemeEnum colorSchemeEnum = null;
		switch (kind) {
		case CURRENT:
			colorSchemeEnum = SubstanceLookAndFeel.getColorScheme();
			break;
		case REGULAR:
			colorSchemeEnum = SubstanceLookAndFeel.getColorScheme()
					.getMetallic();
			break;
		case DISABLED:
			colorSchemeEnum = SubstanceLookAndFeel.getColorScheme().getGray();
			break;
		}

		String key = width + ":" + height + ":" + side.toString() + ":"
				+ cyclePos + ":" + colorSchemeEnum.name() + ":"
				+ button.getClass().getName();
		if (!scrollBarBackgrounds.containsKey(key)) {
			BufferedImage newBackground = null;

			// buttons will be rectangular to make two scrolls (horizontal
			// and vertical) touch the corners.
			int radius = 0;

			switch (side) {
			case TOP:
			case BOTTOM:
				// arrow in vertical bar
				newBackground = SubstanceImageCreator.getFlipRoundedButton(
						width, height, radius, colorSchemeEnum, cyclePos, side,
						true);
				break;
			case RIGHT:
			case LEFT:
				// arrow in horizontal bar
				newBackground = SubstanceImageCreator.getRoundedBackground(
						width, height, radius, colorSchemeEnum, cyclePos, side,
						true);
				break;
			}

			scrollBarBackgrounds.put(key, newBackground);
		}
		return scrollBarBackgrounds.get(key);
	}

	/**
	 * Simple constructor.
	 */
	public SubstanceBackgroundDelegate() {
		super();
	}

	/**
	 * Updates background of the specified button.
	 * 
	 * @param g
	 *            Graphic context.
	 * @param button
	 *            Button to update.
	 * @param cycleCount
	 *            Cycle count for transition effects.
	 */
	public void updateBackground(Graphics g, AbstractButton button,
			long cycleCount) {
		button.setOpaque(false);
		Graphics2D graphics = (Graphics2D) g;

		int width = button.getWidth();
		int height = button.getHeight();

		boolean isRoundCorners = SubstanceBackgroundDelegate
				.isRoundButton(button);

		if (Utilities.isScrollButton(button)) {
			SubstanceScrollButton ssbButton = (SubstanceScrollButton) button;
			graphics.drawImage(getPairwiseBackground(button, width, height,
					ssbButton.getSide()), 0, 0, null);
			return;
		}

		if (Utilities.isSpinnerButton(button)) {
			SubstanceSpinnerButton ssbButton = (SubstanceSpinnerButton) button;
			graphics.drawImage(getPairwiseBackground(button, width, height,
					ssbButton.getSide()), 0, 0, null);
			return;
		}

		

		SubstanceButtonUI.ButtonTitleKind buttonKind = getKind(button);
		if ((buttonKind == SubstanceButtonUI.ButtonTitleKind.CLOSE_DI)
				|| (buttonKind == SubstanceButtonUI.ButtonTitleKind.REGULAR_DI))
			graphics.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, 0.8f));
		graphics.drawImage(
				getBackground(button, width, height, isRoundCorners), 0, 0,
				null);
	}

	/**
	 * Updates background of the specified button.
	 * 
	 * @param g
	 *            Graphic context.
	 * @param button
	 *            Button to update.
	 * @param cycleCount
	 *            Cycle count for transition effects.
	 */
	

	/**
	 * Checks whether the specified button has round corners.
	 * 
	 * @param button
	 *            Button to check.
	 * @return <code>true</code> if the specified button has round corners,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isRoundButton(AbstractButton button) {
		return (!Utilities.isComboBoxButton(button))
				&& (!Utilities.isScrollButton(button))
				&& Utilities.hasText(button);
	}

	/**
	 * Returns <code>true</code> if the specified <i>x,y</i> location is
	 * contained within the look and feel's defined shape of the specified
	 * component. <code>x</code> and <code>y</code> are defined to be
	 * relative to the coordinate system of the specified component.
	 * 
	 * @param button
	 *            the component where the <i>x,y</i> location is being queried;
	 * @param x
	 *            the <i>x</i> coordinate of the point
	 * @param y
	 *            the <i>y</i> coordinate of the point
	 */
	public static boolean contains(AbstractButton button, int x, int y) {
		int width = button.getWidth();
		int height = button.getHeight();

		// rough estimation - outside the rectangle.
		if ((x < 0) || (y < 0) || (x > width) || (y > height))
			return false;

		boolean isRoundCorners = SubstanceBackgroundDelegate
				.isRoundButton(button);
		int radius = 2;
		if (Utilities.isScrollButton(button)
				|| Utilities.isSpinnerButton(button)) {
			radius = 0;
		}
		if (isRoundCorners) {
			if (width > height) {
				radius = height / 2;
			} else {
				radius = width / 2;
			}
		}

		// mirror
		if (x >= (width / 2)) {
			x = width - x;
		}
		if (y >= (height / 2)) {
			y = height - y;
		}

		// check corner
		if ((x < radius) && (y < radius)) {
			int dx = radius - x;
			int dy = radius - y;
			int diff = dx * dx + dy * dy - radius * radius;
			return (diff <= 0);
		} else {
			return true;
		}
	}

	

}
