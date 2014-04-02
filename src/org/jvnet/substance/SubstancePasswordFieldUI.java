package org.jvnet.substance;

import org.jvnet.substance.color.ColorSchemeEnum;

import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import javax.swing.text.*;
import java.awt.*;

/**
 * UI for password fields in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstancePasswordFieldUI extends BasicPasswordFieldUI {
	/**
	 * Dot diameter
	 */
	public static final int PASSWORD_DOT_DIAMETER = 7;

	/**
	 * Gap between dots
	 */
	public static final int PASSWORD_DOT_GAP = 2;

	/**
	 * Total dot width
	 */
	public static final int PASSWORD_DOT_WIDTH = PASSWORD_DOT_DIAMETER
			+ PASSWORD_DOT_GAP;

	/**
	 * Custom password view.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static class SubstancePasswordView extends PasswordView {

		/**
		 * Simple constructor.
		 * 
		 * @param element
		 *            The element
		 */
		public SubstancePasswordView(Element element) {
			super(element);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.text.PasswordView#drawEchoCharacter(java.awt.Graphics,
		 *      int, int, char)
		 */
		@Override
		protected int drawEchoCharacter(Graphics g, int x, int y, char c) {
			Container container = getContainer();
			if (!(container instanceof JPasswordField)) {
				return super.drawEchoCharacter(g, x, y, c);
			}

			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			JPasswordField field = (JPasswordField) container;

			ColorSchemeEnum colorSchemeEnum = field.isEnabled() ? SubstanceLookAndFeel
					.getColorScheme()
					: SubstanceLookAndFeel.getColorScheme().getMetallic();
            Color topColor = colorSchemeEnum.isDark() ?
                    colorSchemeEnum.getColorScheme().getExtraLightColor().brighter() :
                    colorSchemeEnum.getColorScheme().getUltraDarkColor();
            Color bottomColor = colorSchemeEnum.isDark() ?
                    colorSchemeEnum.getColorScheme().getUltraLightColor().brighter() :
                    colorSchemeEnum.getColorScheme().getDarkColor();
            graphics.setPaint(new GradientPaint(x, y - PASSWORD_DOT_DIAMETER,
					topColor, x, y,
					bottomColor));
			graphics.fillOval(x, y - PASSWORD_DOT_DIAMETER,
					PASSWORD_DOT_DIAMETER, PASSWORD_DOT_DIAMETER);

			return x + PASSWORD_DOT_WIDTH;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.text.PlainView#drawSelectedText(java.awt.Graphics,
		 *      int, int, int, int)
		 */
		@Override
		protected int drawSelectedText(Graphics g, int x, int y, int p0, int p1)
				throws BadLocationException {
			return super.drawSelectedText(g, x, y, p0, p1);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.text.View#modelToView(int, java.awt.Shape,
		 *      javax.swing.text.Position.Bias)
		 */
		@Override
		public Shape modelToView(int pos, Shape a, Position.Bias b)
				throws BadLocationException {
			Container c = getContainer();
			if (c instanceof JPasswordField) {
				JPasswordField f = (JPasswordField) c;
				if (!f.echoCharIsSet()) {
					return super.modelToView(pos, a, b);
				}

				Rectangle alloc = adjustAllocation(a).getBounds();
				int dx = (pos - getStartOffset()) * PASSWORD_DOT_WIDTH;
				alloc.x += dx;
				alloc.width = 1;
				return alloc;
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.text.View#viewToModel(float, float, java.awt.Shape,
		 *      javax.swing.text.Position.Bias[])
		 */
		@Override
		public int viewToModel(float fx, float fy, Shape a, Position.Bias[] bias) {
			bias[0] = Position.Bias.Forward;
			int n = 0;
			Container c = getContainer();
			if (c instanceof JPasswordField) {
				JPasswordField f = (JPasswordField) c;
				if (!f.echoCharIsSet()) {
					return super.viewToModel(fx, fy, a, bias);
				}
				a = adjustAllocation(a);
				Rectangle alloc = (a instanceof Rectangle) ? (Rectangle) a : a
						.getBounds();
				n = ((int) fx - alloc.x) / PASSWORD_DOT_WIDTH;
				if (n < 0) {
					n = 0;
				} else {
					if (n > (getStartOffset() + getDocument().getLength())) {
						n = getDocument().getLength() - getStartOffset();
					}
				}
			}
			return getStartOffset() + n;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.text.View#getPreferredSpan(int)
		 */
		@Override
		public float getPreferredSpan(int axis) {
			switch (axis) {
			case View.X_AXIS:
				Container c = getContainer();
				if (c instanceof JPasswordField) {
					JPasswordField f = (JPasswordField) c;
					if (f.echoCharIsSet()) {
						return PASSWORD_DOT_WIDTH * getDocument().getLength();
					}
				}
			}
			return super.getPreferredSpan(axis);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		c.addFocusListener(new FocusBorderListener(c));
		return new SubstancePasswordFieldUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.text.ViewFactory#create(javax.swing.text.Element)
	 */
	@Override
	public View create(Element elem) {
		return new SubstancePasswordView(elem);
	}

}