package org.jvnet.substance;

import java.awt.*;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import org.jvnet.substance.color.ColorScheme;
import org.jvnet.substance.color.ColorSchemeEnum;
import org.jvnet.substance.comp.BasicRibbonBandUI;

/**
 * UI for ribbon bands in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceRibbonBandUI extends BasicRibbonBandUI {
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new SubstanceRibbonBandUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.substance.comp.BasicRibbonBandUI#paintBandTitle(java.awt.Graphics,
	 *      java.awt.Rectangle, java.lang.String)
	 */
	@Override
	protected void paintBandTitle(Graphics graphics, Rectangle titleRectangle,
			String title) {
		ColorScheme scheme = isUnderMouse ? SubstanceLookAndFeel
				.getColorScheme().getColorScheme() : ColorSchemeEnum.STEEL_BLUE
				.getColorScheme();

		graphics.drawImage(SubstanceImageCreator.getSimpleBackground(
				titleRectangle.width, titleRectangle.height, scheme
						.getDarkColor(), scheme.getMidColor()),
				titleRectangle.x, titleRectangle.y, null);
		Color mainColor = SubstanceLookAndFeel.getColorScheme()
				.getColorScheme().getForegroundColor();
		Color backColor = new Color(255 - mainColor.getRed(), 255 - mainColor
				.getGreen(), 255 - mainColor.getBlue());
		graphics.setFont(new Font("Tahoma", Font.BOLD, 11));
		int y = titleRectangle.y
				+ (titleRectangle.height + graphics.getFontMetrics()
						.getAscent()) / 2;
		graphics.setColor(mainColor);
		graphics.drawString(title, titleRectangle.x + 3, y);
		graphics.setColor(backColor);
		graphics.drawString(title, titleRectangle.x + 2, y - 1);
	}

}
