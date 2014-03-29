package test;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.jvnet.substance.SubstanceLookAndFeel;

/**
 * @author Kirill Grouchnikov
 */
public class TestJPanel extends JPanel {

    public TestJPanel() {
        //here getBackground() returns null
        setBorder(new LineBorder(getBackground().darker(), 1, true));
    }

    public static void main(String[] args) {
        //if i comment this try or use other LAF, everythig is ok
        try {
            org.jvnet.substance.SubstanceLookAndFeel feel = new org.jvnet.substance.SubstanceLookAndFeel();
            SubstanceLookAndFeel.setCurrentTheme(new org.jvnet.substance.theme.SubstanceAquaTheme());
            UIManager.setLookAndFeel(feel);
        } catch(Exception ex) {
        }

        TestJPanel panel = new TestJPanel();
    }
}
