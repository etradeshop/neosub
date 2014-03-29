package test;

import org.jvnet.substance.SubstanceLookAndFeel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Kirill Grouchnikov
 */
public class TestColorPanel extends JFrame {
    public static void main(String ... args) throws Exception {
        UIManager.setLookAndFeel(new SubstanceLookAndFeel());
        TestColorPanel tcp = new TestColorPanel();
        tcp.setLayout(new BorderLayout());
        JPanel jp = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.drawString("Cool", 0, this.getHeight() / 2);
            }
        };
//        jp.setBackground(Color.GREEN);
//        jp.setBackground(Color.g);
        tcp.add(jp, BorderLayout.CENTER);
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bp.add(new JButton("button"));
        tcp.add(bp, BorderLayout.SOUTH);
        tcp.setPreferredSize(new
                Dimension(200, 200)
        );
        tcp.setSize(new
                Dimension(200, 200)
        );
        tcp.setVisible(true);
        tcp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
