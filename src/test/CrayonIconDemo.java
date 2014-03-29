package test;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jvnet.substance.SubstanceImageCreator;

/**
 * Image creator demo.
 * 
 * @author Kirill Grouchnikov
 */
public class CrayonIconDemo {

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
			int width = 220;
			int height = 230;
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
			g2.drawImage(SubstanceImageCreator.getCrayonsImage(), 5, 5, null);
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
