package org.jvnet.substance;

import java.awt.Container;
import java.awt.FontMetrics;
import javax.swing.*;



/**
 * Various utility functions.
 * 
 * @author Kirill Grouchnikov
 */
public class Utilities {
	/**
	 * Private constructor. Is here to enforce using static methods only.
	 */
	private Utilities() {
	}

	/**
	 * Clips string based on specified font metrics and available width (in
	 * pixels). Returns the clipped string, which contains the beginning and the
	 * end of the input string separated by ellipses (...) in case the string is
	 * too long to fit into the specified width, and the origianl string
	 * otherwise.
	 * 
	 * @param metrics
	 *            Font metrics.
	 * @param availableWidth
	 *            Available width in pixels.
	 * @param fullText
	 *            String to clip.
	 * @return The clipped string, which contains the beginning and the end of
	 *         the input string separated by ellipses (...) in case the string
	 *         is too long to fit into the specified width, and the origianl
	 *         string otherwise.
	 */
	public static String clipString(FontMetrics metrics, int availableWidth,
			String fullText) {

		if (metrics.stringWidth(fullText) <= availableWidth)
			return fullText;

		String ellipses = "...";
		int ellipsesWidth = metrics.stringWidth(ellipses);
		if (ellipsesWidth > availableWidth)
			return "";

		String starter = "";
		String ender = "";

		int w = fullText.length();
		int w2 = (w / 2) + (w % 2);
		for (int i = 0; i < w2; i++) {
			String newStarter = starter + fullText.charAt(i);
			String newEnder = ender;
			if ((w - i) > w2)
				newEnder = fullText.charAt(w - i - 1) + newEnder;
			String newTitle = newStarter + ellipses + newEnder;
			if (metrics.stringWidth(newTitle) <= availableWidth) {
				starter = newStarter;
				ender = newEnder;
				continue;
			}
			return newTitle;
		}
		return fullText;
	}

	/**
	 * Checks whether the specified button has associated icon.
	 * 
	 * @param button
	 *            Button.
	 * @return If the button has associated icon, <code>true</code> is
	 *         returned, otherwise <code>false</code>.
	 */
	public static boolean hasIcon(AbstractButton button) {
		return (button.getIcon() != null);
	}

	/**
	 * Checks whether the specified button has associated text.
	 * 
	 * @param button
	 *            Button.
	 * @return If the button has associated text, <code>true</code> is
	 *         returned, otherwise <code>false</code>.
	 */
	public static boolean hasText(AbstractButton button) {
		String text = button.getText();
		if ((text != null) && (text.length() > 0)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks and answers if the specified button is in a combo box.
	 * 
	 * @param b
	 *            the button to check
	 * @return <code>true</code> if in tool bar, <code>false</code>
	 *         otherwise
	 */
	public static boolean isComboBoxButton(AbstractButton b) {
		Container parent = b.getParent();
		return parent != null
				&& (parent instanceof JComboBox || parent.getParent() instanceof JComboBox);
	}

	/**
	 * Checks and answers if the specified button is in a combo box.
	 * 
	 * @param b
	 *            the button to check
	 * @return <code>true</code> if in tool bar, <code>false</code>
	 *         otherwise
	 */
	public static boolean isScrollBarButton(AbstractButton b) {
		Container parent = b.getParent();
		return parent != null
				&& (parent instanceof JScrollBar || parent.getParent() instanceof JScrollBar);
	}

	/**
	 * Checks and answers if the specified button is in a title bar.
	 * 
	 * @param b
	 *            the button to check
	 * @return <code>true</code> if in title bar, <code>false</code>
	 *         otherwise
	 */
	public static boolean isTitleBarButton(AbstractButton b) {
		Container parent = b.getParent();
		boolean isInRootTitle = (parent != null && (parent instanceof SubstanceTitlePane || parent
				.getParent() instanceof SubstanceTitlePane));
		boolean isInInternalTitle = (parent != null && (parent instanceof SubstanceInternalFrameTitlePane || parent
				.getParent() instanceof SubstanceInternalFrameTitlePane));
		return isInRootTitle || isInInternalTitle;
	}

	/**
	 * Checks and answers if the specified button is in a spinner.
	 * 
	 * @param b
	 *            the button to check
	 * @return <code>true</code> if in spinner, <code>false</code> otherwise
	 */
	public static boolean isSpinnerButton(AbstractButton b) {
		Container parent = b.getParent();
		return parent != null
				&& (parent instanceof JSpinner || parent.getParent() instanceof JSpinner);
	}

	/**
	 * Checks and answers if the specified button is in a spinner.
	 * 
	 * @param b
	 *            the button to check
	 * @return <code>true</code> if in spinner, <code>false</code> otherwise
	 */
	public static boolean isToolBarButton(AbstractButton b) {
		Container parent = b.getParent();
		return parent != null
				&& (parent instanceof JToolBar || parent.getParent() instanceof JToolBar);
	}

	/**
	 * Checks and answers if the specified button is in a tabbed pane.
	 * 
	 * @param b
	 *            the button to check
	 * @return <code>true</code> if in spinner, <code>false</code> otherwise
	 */
	public static boolean isTabbedPaneButton(AbstractButton b) {
		Container parent = b.getParent();
		return parent != null && (parent instanceof JTabbedPane);
	}

	/**
	 * Checks answers if the specified button is in scroll control, such as
	 * scroll bar or tabbed pane (as tab scroller).
	 * 
	 * @param b
	 *            the button to check
	 * @return <code>true</code> if in scroll control, <code>false</code>
	 *         otherwise
	 */
	public static boolean isScrollButton(AbstractButton b) {
		return isTabbedPaneButton(b) || isScrollBarButton(b);
	}

	/**
	 * Checks and answers if the specified button is in a button strip.
	 * 
	 * @param b
	 *            the button to check
	 * @return <code>true</code> if in button strip, <code>false</code>
	 *         otherwise
	 */
	

	/**
	 * Checks and answers if the specified button is in a ribbon.
	 * 
	 * @param b
	 *            the button to check
	 * @return <code>true</code> if in ribbon, <code>false</code>
	 *         otherwise
	 */
	
}
