/*
 * @(#)ButtonStateIcon.java  2.0.1  2005-09-11
 *
 * Copyright (c) 2003 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

package ch.randelshofer.quaqua;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
/**
 * An Icon with different visuals reflecting the state of the AbstractButton
 * on which it draws on.
 *
 * @author  Werner Randelshofer
 * @version 2.0.1 2005-09-11 generateMissing icons can now deal with only one
 * provided icon image.
 * <br>2.0 2005-03-19 Reworked.
 * <br>1.0 October 5, 2003 Create..
 */
public class ButtonStateIcon implements Icon, UIResource {
    private final static int E = 0;
    private final static int EP = 1;
    private final static int ES = 2;
    private final static int EPS = 3;
    private final static int D = 4;
    private final static int DS = 5;
    private final static int I = 6;
    private final static int IS = 7;
    private final static int DI = 8;
    private final static int DIS = 9;
    private Icon[] icons;
    private int width, height;
    
    /**
     * Creates a new instance.
     * All icons must have the same dimensions.
     * If an icon is null, an icon is derived for the state from the
     * other icons.
     */
    public ButtonStateIcon(Icon e, Icon ep, Icon es, Icon eps, Icon d, Icon ds) {
        icons = new Icon[10];
        icons[E] = e;
        icons[EP] = ep;
        icons[ES] = es;
        icons[EPS] = eps;
        icons[D] = d;
        icons[DS] = ds;
        
        for (int i=0; i < icons.length; i++) {
            if (icons[i] != null) {
                width = icons[i].getIconWidth();
                height = icons[i].getIconHeight();
                break;
            }
        }
        generateMissingIcons();
    }
    /**
     * Creates a new instance.
     * All icons must have the same dimensions.
     *
     * The array indices are used to represente the following states:
     * [0] Enabled
     * [1] Enabled Pressed
     * [2] Enabled Selected
     * [3] Enabled Pressed Selected
     * [4] Disabled
     * [5] Disabled Selected
     * [6] Enabled Inactive
     * [7] Enabled Inactive Selected
     * [8] Disabled Inactive
     * [9] Disabled Inactive Selected
     *
     * If an array element is null, an icon is derived for the state from the
     * other icons.
     */
    public ButtonStateIcon(Image[] images) {
        this.icons = new Icon[10];
        for (int i=0, n = Math.min(images.length, icons.length); i < n; i++) {
            if (images[i] != null) {
                icons[i] = new ImageIcon(images[i]);
                width = icons[i].getIconWidth();
                height = icons[i].getIconHeight();
            }
        }
        generateMissingIcons();
    }
    /**
     * Creates a new instance.
     * All icons must have the same dimensions.
     * If an icon is null, nothing is drawn for this state.
     */
    public ButtonStateIcon(Icon[] icons) {
        this.icons = new Icon[10];
        System.arraycopy(icons, 0, this.icons, 0, Math.min(icons.length, this.icons.length));
        for (int i=0; i < icons.length; i++) {
            if (icons[i] != null) {
                width = icons[i].getIconWidth();
                height = icons[i].getIconHeight();
                break;
            }
        }
        generateMissingIcons();
    }
    
    private void generateMissingIcons() {
        if (icons[EP] == null) {
            icons[EP] = icons[E];
        }
        if (icons[ES] == null) {
            icons[ES] = icons[EP];
        }
        if (icons[EPS] == null) {
            icons[EPS] = icons[EP];
        }
        if (icons[D] == null) {
            icons[D] = icons[E];
        }
        if (icons[DS] == null) {
            icons[DS] = icons[ES];
        }
        if (icons[I] == null) {
            icons[I] = icons[E];
        }
        if (icons[IS] == null) {
            icons[IS] = icons[ES];
        }
        if (icons[DI] == null) {
            icons[DI] = icons[D];
        }
        if (icons[DIS] == null) {
            icons[DIS] = icons[DS];
        }
    }
    
    public int getIconHeight() {
        return height;
    }
    
    public int getIconWidth() {
        return width;
    }
    
    public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
        Icon icon = getIcon(c);
        if (icon != null) {
            icon.paintIcon(c, g, x, y);
        }
    }
    private Icon getIcon(Component c) {
        Icon icon;
        boolean isActive = QuaquaUtilities.isOnActiveWindow(c);
        
        if (c instanceof AbstractButton) {
            ButtonModel model = ((AbstractButton) c).getModel();
            if (isActive) {
                if (model.isEnabled()) {
                    if (model.isPressed() || model.isArmed()) {
                        if (model.isSelected()) {
                            icon = icons[EPS];
                        } else {
                            icon = icons[EP];
                        }
                    } else if (model.isSelected()) {
                        icon = icons[ES];
                    } else {
                        icon = icons[E];
                    }
                } else {
                    if (model.isSelected()) {
                        icon = icons[DS];
                    } else {
                        icon = icons[D];
                    }
                }
            } else {
                if (model.isEnabled()) {
                    if (model.isSelected()) {
                        icon = icons[IS];
                    } else {
                        icon = icons[I];
                    }
                } else {
                    if (model.isSelected()) {
                        icon = icons[DIS];
                    } else {
                        icon = icons[DI];
                    }
                }
            }
        } else {
            if (isActive) {
                if (c.isEnabled()) {
                    icon = icons[E];
                } else {
                    icon = icons[D];
                }
            } else {
                if (c.isEnabled()) {
                    icon = icons[I];
                } else {
                    icon = icons[DI];
                }
            }
        }
        return icon;
    }
}
