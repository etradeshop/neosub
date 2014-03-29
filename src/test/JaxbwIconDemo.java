package test;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jvnet.substance.color.ColorSchemeEnum;

/**
 * Image creator demo.
 * 
 * @author Kirill Grouchnikov
 */
public class JaxbwIconDemo {

	public static final int COLOR_CELL = 30;

	public static final int NAME_CELL = 120;

	private static final class IconFrame extends JFrame {
		/**
		 * Simple constructor. Creates all the icons.
		 */
		public IconFrame() {

			this.setLayout(new BorderLayout());
			this.add(new IconPanel(), BorderLayout.CENTER);

		}
	}

	/**
	 * Demo frame.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static final class IconPanel extends JPanel {
		/**
		 * Simple constructor. Creates all the icons.
		 */
		public IconPanel() {
			int width = 50;
			int height = 50;
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
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setColor(Color.white);
			g2.translate(15, 15);

//			g2.drawImage(SubstanceImageCreator.getRoundedBackground(16, 16, 2,
//					ColorSchemeEnum.LIGHT_AQUA, 0, null), 0, 0, null);

			g2.setColor(ColorSchemeEnum.AQUA.getColorScheme().getDarkColor());
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

			g2.setColor(ColorSchemeEnum.PURPLE.getColorScheme().getDarkColor());
			g2.setFont(new Font("Tahoma", Font.BOLD, 17));
			g2.drawString("J", 6.0f, 14.0f);
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Tahoma", Font.BOLD, 11));
			g2.drawString("w", 2.0f, 11.0f);
			
//			Stroke stroke = new BasicStroke(1.8f, BasicStroke.CAP_ROUND,
//					BasicStroke.JOIN_ROUND);
//			g2.setStroke(stroke);
//			GeneralPath path1 = new GeneralPath();
//			path1.moveTo(5.0f, 4.0f);
//			path1.quadTo(3.0f, 4.0f, 3.0f, 8.0f);
//			path1.quadTo(3.0f, 12.0f, 8.0f, 11.0f);
//			path1.moveTo(10.0f, 12.0f);
//			path1.quadTo(12.0f, 12.0f, 12.0f, 8.0f);
//			path1.quadTo(12.0f, 4.0f, 7.0f, 5.0f);
//			path1.lineTo(8.0f, 11.0f);
////			path1.moveTo(8.0f, 8.0f);
//			g2.draw(path1);
			
//			g2.setColor(Color.BLACK);
//			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//					RenderingHints.VALUE_ANTIALIAS_OFF);
//			g2.drawRect(-1, -1, 18, 18);
//			Stroke stroke2 = new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
//					BasicStroke.JOIN_ROUND);
//			g2.setStroke(stroke2);
		}
	}

	/**
	 * Main function for running <code>this</code> demo.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		IconFrame icf = new IconFrame();
		icf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		icf.pack();
		icf.setVisible(true);
	}
}
