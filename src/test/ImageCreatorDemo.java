package test;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.color.ColorSchemeEnum;

/**
 * Image creator demo.
 * 
 * @author Kirill Grouchnikov
 */
public class ImageCreatorDemo {

	/**
	 * Demo frame.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static final class ImageCreatorFrame extends JFrame {
		/**
		 * Simple constructor. Creates all the icons.
		 */
		public ImageCreatorFrame() {
			int width = 400;
			int height = 200;
			Dimension dim = new Dimension(width, height);
			this.setPreferredSize(dim);
			this.setSize(dim);

			// this.setResizable(false);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.Component#paint(java.awt.Graphics)
		 */
		public final void paint(Graphics g) {
//			g.setColor(Color.red);
//			g.fillRect(0, 0, getWidth(), getHeight());
			ColorSchemeEnum cse = ColorSchemeEnum.AQUA;
			g.drawImage(SubstanceImageCreator.getRoundedBackground(70, 20, 10,
					cse, 0, SubstanceImageCreator.Side.LEFT, false),
					230, 55, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(70, 20, 10,
					cse, 0, SubstanceImageCreator.Side.TOP, false),
					230, 80, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(70, 20, 10,
					cse, 0, SubstanceImageCreator.Side.RIGHT, false),
					230, 105, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(70, 20, 10,
					cse, 0, SubstanceImageCreator.Side.BOTTOM, false),
					230, 130, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(70, 20, 10,
					cse, 0, null, false), 230, 155, null);

			g.drawImage(SubstanceImageCreator.getRoundedBackground(70, 20, 10,
					cse, 0, SubstanceImageCreator.Side.LEFT, true),
					310, 55, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(70, 20, 10,
					cse, 0, SubstanceImageCreator.Side.TOP, true), 310,
					80, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(70, 20, 10,
					cse, 0, SubstanceImageCreator.Side.RIGHT, true),
					310, 105, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(70, 20, 10,
					cse, 0, SubstanceImageCreator.Side.BOTTOM, true),
					310, 130, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(70, 20, 10,
					cse, 0, null, true), 310, 155, null);

			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 10,
					cse, 0, SubstanceImageCreator.Side.LEFT, false),
					30, 55, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 10,
					cse, 0, SubstanceImageCreator.Side.TOP, false), 30,
					80, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 10,
					cse, 0, SubstanceImageCreator.Side.RIGHT, false),
					30, 105, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 10,
					cse, 0, SubstanceImageCreator.Side.BOTTOM, false),
					30, 130, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 10,
					cse, 0, null, false), 30, 155, null);

			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 2,
					cse, 0, SubstanceImageCreator.Side.LEFT, false),
					60, 55, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 2,
					cse, 0, SubstanceImageCreator.Side.TOP, false), 60,
					80, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 2,
					cse, 0, SubstanceImageCreator.Side.RIGHT, false),
					60, 105, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 2,
					cse, 0, SubstanceImageCreator.Side.BOTTOM, false),
					60, 130, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 2,
					cse, 0, null, false), 60, 155, null);

			BufferedImage imSouth = SubstanceImageCreator.getRoundedBackground(
					20, 20, 2, cse, 0,
					SubstanceImageCreator.Side.BOTTOM, false);
			BufferedImage imEast = SubstanceImageCreator.getRotated(imSouth, 1);
			BufferedImage imNorth = SubstanceImageCreator
					.getRotated(imSouth, 2);
			BufferedImage imWest = SubstanceImageCreator.getRotated(imSouth, 3);
			//			
			g.drawImage(imSouth, 90, 55, null);
			g.drawImage(imEast, 90, 80, null);
			g.drawImage(imNorth, 90, 105, null);
			g.drawImage(imWest, 90, 130, null);

			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 6,
					cse, 0, SubstanceImageCreator.Side.LEFT, true),
					120, 55, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 6,
					cse, 0, SubstanceImageCreator.Side.TOP, true), 120,
					80, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 6,
					cse, 0, SubstanceImageCreator.Side.RIGHT, true),
					120, 105, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 6,
					cse, 0, SubstanceImageCreator.Side.BOTTOM, true),
					120, 130, null);
			g.drawImage(SubstanceImageCreator.getRoundedBackground(20, 20, 6,
					cse, 0, null, false), 120, 155, null);

//			g.drawImage(SubstanceImageCreator.getRoundedTriangleBackground(15, 16, 4,
//					cse, cse, 0),
//					150, 55, null);

			
			// SubstanceImageCreator.getArrowIcon(20, 20, SwingConstants.SOUTH,
			// false).paintIcon(this, g, 90, 55);
			// SubstanceImageCreator.getArrowIcon(20, 20, SwingConstants.NORTH,
			// false).paintIcon(this, g, 90, 80);
			// SubstanceImageCreator.getArrowIcon(20, 20, SwingConstants.WEST,
			// false).paintIcon(this, g, 90, 105);
			// SubstanceImageCreator.getArrowIcon(20, 20, SwingConstants.EAST,
			// false).paintIcon(this, g, 90, 130);

			// g.drawImage(SubstanceImageCreator.getRoundedBackground(70, 20,
			// 10,
			// true, true, 0, SubstanceImageCreator.Side.LEFT), 230, 30, null);
			// g.drawImage(SubstanceImageCreator.getRoundedBackground(70, 20,
			// 10,
			// false, false, 0, SubstanceImageCreator.Side.LEFT), 80, 130,
			// null);
		}
	}

	/**
	 * Main function for running <code>this</code> demo.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ImageCreatorFrame icf = new ImageCreatorFrame();
		icf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		icf.setVisible(true);
	}
}
