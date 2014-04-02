package org.jvnet.substance;

import javax.swing.*;
import java.awt.Insets;

/**
 * Combo box button in <b>Substance</b> look and feel.
 *
 * @author Kirill Grouchnikov
 */
final class SubstanceComboBoxButton extends JButton {
    /**
     * Left inset.
     */
    private static final int LEFT_INSET = 0;

    /**
     * Right inset.
     */
    private static final int RIGHT_INSET = 1;

    /**
     * Simple constructor.
     *
     * @param comboBox  The owner combo box.
     * @param comboIcon The button icon (down arrow).
     */
    public SubstanceComboBoxButton(JComboBox comboBox, Icon comboIcon) {
        super("");
        setModel(new DefaultButtonModel() {
            @Override
                    public void setArmed(boolean armed) {
                super.setArmed(isPressed() || armed);
            }
        });
        this.setEnabled(comboBox.isEnabled());
        this.setFocusable(false);
        this.setRequestFocusEnabled(comboBox.isEnabled());
        this.setBorder(null);
        this.setMargin(new Insets(0, LEFT_INSET, 0, RIGHT_INSET));
        this.setIcon(comboIcon);
        this.setDisabledIcon(
                SubstanceImageCreator.makeTransparent(comboIcon, 0.8));
    }
}
