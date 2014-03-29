package org.jvnet.substance;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalScrollBarUI;

import org.jvnet.substance.color.ColorSchemeEnum;
import org.jvnet.substance.theme.SubstanceTheme;

/**
 * UI for scroll bars in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceScrollBarUI extends MetalScrollBarUI implements Trackable {
	/**
	 * Decrease button.
	 */
	protected JButton myDecreaseButton;

	/**
	 * Increase button.
	 */
	protected JButton myIncreaseButton;

	/**
	 * Surrogate button model for tracking the thumb transitions.
	 */
	private ButtonModel thumbModel;

	/**
	 * Stores computed images for vertical tracks.
	 */
	private static Map<String, BufferedImage> trackVerticalMap = new HashMap<String, BufferedImage>();

	/**
	 * Stores computed images for vertical thumbs.
	 */
	private static Map<String, BufferedImage> thumbVerticalMap = new HashMap<String, BufferedImage>();

	/**
	 * Stores computed images for horizontal tracks.
	 */
	private static Map<String, BufferedImage> trackHorizontalMap = new HashMap<String, BufferedImage>();

	/**
	 * Stores computed images for horizontal thumbs.
	 */
	private static Map<String, BufferedImage> thumbHorizontalMap = new HashMap<String, BufferedImage>();

	/**
	 * Resets image maps (used when setting new theme).
	 * 
	 * @see SubstanceLookAndFeel#setCurrentTheme(String)
	 * @see SubstanceLookAndFeel#setCurrentTheme(SubstanceTheme)
	 */
	static synchronized void reset() {
		trackHorizontalMap.clear();
		trackVerticalMap.clear();
		thumbHorizontalMap.clear();
		thumbVerticalMap.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent b) {
		return new SubstanceScrollBarUI(b);
	}

	/**
	 * Simple constructor.
	 * 
	 * @param b
	 *            Associated component.
	 */
	private SubstanceScrollBarUI(JComponent b) {
		super();
		this.thumbModel = new DefaultButtonModel();
		this.thumbModel.setArmed(false);
		this.thumbModel.setSelected(false);
		this.thumbModel.setPressed(false);
		this.thumbModel.setRollover(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicScrollBarUI#createDecreaseButton(int)
	 */
	@Override
	protected JButton createDecreaseButton(int orientation) {
		int width = scrollBarWidth / 2;
		if (width % 2 == 0) {
			width++;
		}
		int height = (int) (0.4 * scrollBarWidth);
		Icon icon = SubstanceImageCreator.getArrowIcon(width, height,
				orientation, SubstanceLookAndFeel.getColorScheme());

		myDecreaseButton = new SubstanceScrollButton(icon, orientation);
		myDecreaseButton.setPreferredSize(new Dimension(scrollBarWidth,
				scrollBarWidth));
		return myDecreaseButton;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicScrollBarUI#createIncreaseButton(int)
	 */
	@Override
	protected JButton createIncreaseButton(int orientation) {
		int width = scrollBarWidth / 2;
		if (width % 2 == 0) {
			width++;
		}
		int height = (int) (0.4 * scrollBarWidth);
		Icon icon = SubstanceImageCreator.getArrowIcon(width, height,
				orientation, SubstanceLookAndFeel.getColorScheme());

		myIncreaseButton = new SubstanceScrollButton(icon, orientation);
		myIncreaseButton.setPreferredSize(new Dimension(scrollBarWidth,
				scrollBarWidth));
		return myIncreaseButton;
	}

	/**
	 * Retrieves image for vertical track.
	 * 
	 * @param trackBounds
	 *            Track bounding rectangle.
	 * @param compDecrState
	 *            State of the decrease button.
	 * @param compIncrState
	 *            State of the increase button.
	 * @return Image for vertical track.
	 */
	private static synchronized BufferedImage getTrackVertical(
			Rectangle trackBounds, ComponentState compDecrState,
			ComponentState compIncrState) {
		int width = Math.max(1, trackBounds.width);
		int height = Math.max(1, trackBounds.height);
		String key = width + "*" + height + ":" + compDecrState.name() + ":"
				+ compIncrState.name();
		BufferedImage result = trackVerticalMap.get(key);
		if (result == null) {
			result = SubstanceImageCreator.getCompositeRoundedBackground(
					height, width, width / 2, compDecrState, compIncrState,
					true);
			result = SubstanceImageCreator.getRotated(result, 3);
			trackVerticalMap.put(key, result);
		}
		return result;
	}

	/**
	 * Retrieves image for horizontal track.
	 * 
	 * @param trackBounds
	 *            Track bounding rectangle.
	 * @param compDecrState
	 *            State of the decrease button.
	 * @param compIncrState
	 *            State of the increase button.
	 * @return Image for horizontal track.
	 */
	private static synchronized BufferedImage getTrackHorizontal(
			Rectangle trackBounds, ComponentState compDecrState,
			ComponentState compIncrState) {
		int width = Math.max(1, trackBounds.width);
		int height = Math.max(1, trackBounds.height);
		// System.out.println("search " + compDecrState.name() + ":" +
		// compIncrState.name());
		String key = width + "*" + height + ":" + compDecrState.name() + ":"
				+ compIncrState.name();
		BufferedImage result = trackHorizontalMap.get(key);
		if (result == null) {
			result = SubstanceImageCreator.getCompositeRoundedBackground(width,
					height, height / 2, compDecrState, compIncrState, false);
			trackHorizontalMap.put(key, result);
		}
		return result;
	}

	/**
	 * Retrieves image for vertical thumb.
	 * 
	 * @param scrollBar
	 *            The associated scroll bar.
	 * @param thumbBounds
	 *            Thumb bounding rectangle.
	 * @param buttonModel
	 *            Button model for tracking the thumb transitions.
	 * @return Image for vertical thumb.
	 */
	private static synchronized BufferedImage getThumbVertical(
			JScrollBar scrollBar, Rectangle thumbBounds, ButtonModel buttonModel) {
		int width = Math.max(1, thumbBounds.width);
		int height = Math.max(1, thumbBounds.height);

		ComponentState state = ComponentState.getState(buttonModel, null);
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

		// System.out.println(state.name());
		String key = width + ":" + height + ":" + kind.name() + ":" + cyclePos;
		BufferedImage result = thumbVerticalMap.get(key);
		if (result == null) {
			result = SubstanceImageCreator.getRoundedBackground(height, width,
					width / 2, colorSchemeEnum, cyclePos, null);
			result = SubstanceImageCreator.getRotated(result, 3);
			thumbVerticalMap.put(key, result);
		}
		return result;
	}

	/**
	 * Retrieves image for horizontal thumb.
	 * 
	 * @param scrollBar
	 *            The associated scroll bar.
	 * @param thumbBounds
	 *            Thumb bounding rectangle.
	 * @param buttonModel
	 *            Button model for tracking the thumb transitions.
	 * @return Image for horizontal thumb.
	 */
	private static synchronized BufferedImage getThumbHorizontal(
			JScrollBar scrollBar, Rectangle thumbBounds, ButtonModel buttonModel) {
		int width = Math.max(1, thumbBounds.width);
		int height = Math.max(1, thumbBounds.height);

		ComponentState state = ComponentState.getState(buttonModel, null);
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

		String key = width + ":" + height + ":" + kind.name() + ":" + cyclePos;
		BufferedImage result = thumbHorizontalMap.get(key);
		if (result == null) {
			result = SubstanceImageCreator.getRoundedBackground(width, height,
					height / 2, colorSchemeEnum, cyclePos, null);
			thumbHorizontalMap.put(key, result);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicScrollBarUI#paintTrack(java.awt.Graphics,
	 *      javax.swing.JComponent, java.awt.Rectangle)
	 */
	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		if (!c.isEnabled()) {
			return;
		}

		ComponentState compDecrState = ComponentState.DEFAULT;
		ComponentState compIncrState = ComponentState.DEFAULT;
		if (this.myDecreaseButton.isShowing()
				&& this.myIncreaseButton.isShowing()) {
			ButtonModel decrModel = this.myDecreaseButton.getModel();
			ButtonModel incrModel = this.myIncreaseButton.getModel();

			compDecrState = ComponentState.getState(decrModel,
					this.decreaseButton);
			compIncrState = ComponentState.getState(incrModel,
					this.increaseButton);

			// System.out.println(compDecrState.name() + ":" +
			// compIncrState.name());
		}

		BufferedImage trackImage = (scrollbar.getOrientation() == Adjustable.VERTICAL) ? getTrackVertical(
				trackBounds, compDecrState, compIncrState)
				: getTrackHorizontal(trackBounds, compDecrState, compIncrState);
		g.drawImage(trackImage, trackBounds.x, trackBounds.y, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicScrollBarUI#paintThumb(java.awt.Graphics,
	 *      javax.swing.JComponent, java.awt.Rectangle)
	 */
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		if (!c.isEnabled()) {
			return;
		}

		JScrollBar scrollBar = (JScrollBar) c;
		this.thumbModel.setSelected(this.thumbModel.isSelected() || isDragging);
		BufferedImage thumbImage = (scrollbar.getOrientation() == Adjustable.VERTICAL) ? getThumbVertical(
				scrollBar, thumbBounds, this.thumbModel)
				: getThumbHorizontal(scrollBar, thumbBounds, this.thumbModel);

		g.drawImage(thumbImage, thumbBounds.x, thumbBounds.y, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicScrollBarUI#installListeners()
	 */
	@Override
	protected void installListeners() {
		super.installListeners();
		// scrollbar.addMouseListener(new MouseAdapter());
//		BasicButtonListener incrListener = RolloverScrollBarButtonListener
//				.getListener(this.scrollbar, incrButton);
		MouseListener mouseListener = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				scrollbar.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				scrollbar.repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				scrollbar.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				scrollbar.repaint();
			}
		};

		incrButton.addMouseListener(mouseListener);
//		incrButton.addMouseMotionListener(incrListener);
//		BasicButtonListener decrListener = RolloverScrollBarButtonListener
//				.getListener(this.scrollbar, decrButton);
		decrButton.addMouseListener(mouseListener);
//		decrButton.addMouseMotionListener(decrListener);

		RolloverControlListener listener = new RolloverControlListener(this,
				this.thumbModel);
		this.scrollbar.addMouseListener(listener);
		this.scrollbar.addMouseMotionListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.Trackable#isInside(java.awt.event.MouseEvent)
	 */
	public boolean isInside(MouseEvent me) {
		Rectangle thumbB = this.getThumbBounds();
		if (thumbB == null)
			return false;
		return thumbB.contains(me.getX(), me.getY());
	}

	static String getMemoryUsage() {
		StringBuffer sb = new StringBuffer();
		sb.append("SubstanceScrollBarUI: \n");
		sb.append("\t" + trackHorizontalMap.size() + " track horizontal, "
				+ trackVerticalMap.size() + " track vertical, "
				+ thumbHorizontalMap.size() + " thumb horizontal, "
				+ thumbVerticalMap.size() + " thumb vertical");
		return sb.toString();
	}

}