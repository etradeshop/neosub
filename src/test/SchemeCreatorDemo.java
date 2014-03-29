package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.color.ColorScheme;
import org.jvnet.substance.color.ColorSchemeEnum;
import org.jvnet.substance.theme.SubstanceTheme;

/**
 * Image creator demo.
 * 
 * @author Kirill Grouchnikov
 */
public class SchemeCreatorDemo {

	public static final int COLOR_CELL = 30;

	public static final int NAME_CELL = 120;

	private static final class SchemeCreatorFrame extends JFrame {
		/**
		 * Simple constructor. Creates all the icons.
		 */
		public SchemeCreatorFrame() {
			// int width = COLOR_CELL * 6 + NAME_CELL;
			// int height = COLOR_CELL * ColorSchemeEnum.values().length;
			// Dimension dim = new Dimension(width, height);
			// this.setPreferredSize(dim);
			// this.setSize(dim);

			this.setLayout(new BorderLayout());
			this.add(new SchemeCreatorPanel(), BorderLayout.CENTER);

			// this.setResizable(false);
		}
	}

	/**
	 * Demo frame.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static final class SchemeCreatorPanel extends JPanel {
		/**
		 * Simple constructor. Creates all the icons.
		 */
		public SchemeCreatorPanel() {
			int width = COLOR_CELL * 6 + NAME_CELL;
			int height = COLOR_CELL
					* SubstanceLookAndFeel.enumerateThemes().size();
			Dimension dim = new Dimension(width, height);
			this.setPreferredSize(dim);
			this.setMinimumSize(dim);
			this.setSize(dim);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.Component#paint(java.awt.Graphics)
		 */
		public final void paint(Graphics g) {
			try {
				int width = this.getWidth();
				int height = this.getHeight();
				int count = 0;
				for (Map.Entry<String, String> schemeEntry : SubstanceLookAndFeel
						.enumerateThemes().entrySet()) {
					String name = schemeEntry.getKey();
					SubstanceTheme theme = (SubstanceTheme) Class.forName(
							schemeEntry.getValue()).newInstance();
					ColorScheme scheme = theme.getColorSchemeEnum()
							.getColorScheme();

					g.setColor(Color.BLACK);
					g.setFont(new Font("Arial", Font.BOLD, 14));

					g
							.drawString(name, 5, (count + 1)
									* COLOR_CELL
									- (COLOR_CELL - g.getFontMetrics()
											.getHeight()) / 2);

					int x = NAME_CELL;
					int y = count * COLOR_CELL;

					g.setColor(scheme.getUltraLightColor());
					g.fillRect(x, y, COLOR_CELL, COLOR_CELL);
					x += COLOR_CELL;

					g.setColor(scheme.getExtraLightColor());
					g.fillRect(x, y, COLOR_CELL, COLOR_CELL);
					x += COLOR_CELL;

					g.setColor(scheme.getLightColor());
					g.fillRect(x, y, COLOR_CELL, COLOR_CELL);
					x += COLOR_CELL;

					g.setColor(scheme.getMidColor());
					g.fillRect(x, y, COLOR_CELL, COLOR_CELL);
					x += COLOR_CELL;

					g.setColor(scheme.getDarkColor());
					g.fillRect(x, y, COLOR_CELL, COLOR_CELL);
					x += COLOR_CELL;

					g.setColor(scheme.getUltraDarkColor());
					g.fillRect(x, y, COLOR_CELL, COLOR_CELL);

					count++;
				}
				g.setColor(new Color(128, 128, 128));
				g.drawLine(0, 0, width - 1, 0);
				g.drawLine(0, height - 1, width - 1, height - 1);
				g.drawLine(0, 0, 0, height - 1);
				g.drawLine(width - 1, 0, width - 1, height - 1);

				for (count = 0; count < ColorSchemeEnum.values().length; count++) {
					int yPos = COLOR_CELL * count;
					g.drawLine(0, yPos, width - 1, yPos);
				}

				for (count = 0; count < 6; count++) {
					int xPos = NAME_CELL + count * COLOR_CELL;
					g.drawLine(xPos, 0, xPos, height - 1);
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}

	/**
	 * Main function for running <code>this</code> demo.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SchemeCreatorFrame icf = new SchemeCreatorFrame();
		icf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		icf.pack();
		icf.setVisible(true);
	}
}
