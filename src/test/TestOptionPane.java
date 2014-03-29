package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;

/**
 * @author Kirill Grouchnikov
 */
public class TestOptionPane {
	public static void main(String... args) throws Exception {
		UIManager.setLookAndFeel(new SubstanceLookAndFeel());

		// JOptionPane.showMessageDialog(null, "Sample message",
		// "Sample title", JOptionPane.INFORMATION_MESSAGE);

		JDialog jd = new JDialog();
		System.out.println("Setting to green");
		jd.setBackground(Color.GREEN);
		jd.setLayout(new FlowLayout());
		jd.add(new JButton("close"));
		jd.pack();
		jd.setPreferredSize(new Dimension(200, 200));
		jd.setSize(new Dimension(200, 200));
		jd.setLocation(500, 400);
		jd.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		jd.setVisible(true);
		System.out.println(jd.getBackground());
		System.out.println(jd.getRootPane().getBackground());
	}
}
