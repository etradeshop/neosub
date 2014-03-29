/*
 * @(#)ColorSlidersChooser.java  1.3  2005-09-05
 *
 * Copyright (c) 2004 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

package ch.randelshofer.quaqua.colorchooser;

import ch.randelshofer.quaqua.*;
import ch.randelshofer.quaqua.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;
import javax.swing.plaf.*;
import java.util.*;
/**
 * The ColorSlidersChooser contains four individual color slider pages: gray
 * slider, RGB sliders, CMYK sliders, and HTML sliders.
 *
 * @author  Werner Randelshofer
 * @version 1.3 2005-09-05 Get Font and icon from UIManager.
 * <br>1.2 2005-08-27 Keep track of last slider panel, and open this
 * panel, when the ColorSlidersChooser is recreated.
 * <br>1.1.1 2005-04-23 Localized form.
 * <br>1.0  30 March 2005  Created.
 */
public class ColorSlidersChooser extends AbstractColorChooserPanel
implements UIResource {
    private final static ResourceBundleUtil labels = ResourceBundleUtil.getBundle("ch.randelshofer.quaqua.Labels");
    
    /**
     * We store here the name of the last selected color sliders panel.
     * When the ColorSlidersChooser is recreated multiple times in the same
     * panel, the application 'remembers' which panel the user had opened
     * before.
     */
    private static int lastSelectedPanelIndex = 1;
    
    
    /** Creates new form. */
    public ColorSlidersChooser() {
        initComponents();
        slidersComboBox.setFont(UIManager.getFont("ColorChooser.font"));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        slidersComboBox = new javax.swing.JComboBox();
        slidersHolder = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        add(slidersComboBox, java.awt.BorderLayout.NORTH);

        slidersHolder.setLayout(new java.awt.CardLayout());

        add(slidersHolder, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents
    
    protected void buildChooser() {
        slidersHolder.add(new GrayChooser(),labels.getString("ColorChooser.grayScaleSlider"));
        slidersHolder.add(new RGBChooser(),labels.getString("ColorChooser.rgbSliders"));
        slidersHolder.add(new CMYKChooser(),labels.getString("ColorChooser.cmykSliders"));
        slidersHolder.add(new HSBChooser(),labels.getString("ColorChooser.hsbSliders"));
        slidersHolder.add(new HTMLChooser(),labels.getString("ColorChooser.htmlSliders"));
        DefaultComboBoxModel cbm = new DefaultComboBoxModel();
        cbm.addElement(labels.getString("ColorChooser.grayScaleSlider"));
        cbm.addElement(labels.getString("ColorChooser.rgbSliders"));
        cbm.addElement(labels.getString("ColorChooser.cmykSliders"));
        cbm.addElement(labels.getString("ColorChooser.hsbSliders"));
        cbm.addElement(labels.getString("ColorChooser.htmlSliders"));
        slidersComboBox.setModel(cbm);
        slidersComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    ((CardLayout) slidersHolder.getLayout()).show(slidersHolder, (String) evt.getItem());
                    lastSelectedPanelIndex = slidersComboBox.getSelectedIndex(); 
                }
            }
        });
        slidersComboBox.setSelectedIndex(lastSelectedPanelIndex);
    }
    
    public void installChooserPanel(JColorChooser enclosingChooser) {
        super.installChooserPanel(enclosingChooser);
        Component[] components = slidersHolder.getComponents();
        for (int i=0; i < components.length; i++) {
            AbstractColorChooserPanel ccp = (AbstractColorChooserPanel) components[i];
            ccp.installChooserPanel(enclosingChooser);
        }
    }
    
    /**
     * Invoked when the panel is removed from the chooser.
     * If override this, be sure to call <code>super</code>.
     */
    public void uninstallChooserPanel(JColorChooser enclosingChooser) {
        Component[] components = slidersHolder.getComponents();
        for (int i=0; i < components.length; i++) {
            AbstractColorChooserPanel ccp = (AbstractColorChooserPanel) components[i];
            ccp.uninstallChooserPanel(enclosingChooser);
        }
        super.uninstallChooserPanel(enclosingChooser);
    }
    
    public String getDisplayName() {
        return labels.getString("ColorChooser.colorSliders");
    }
    
    public Icon getLargeDisplayIcon() {
        return UIManager.getIcon("ColorChooser.colorSlidersIcon");
    }
    
    public Icon getSmallDisplayIcon() {
        return getLargeDisplayIcon();
    }
    
    public void updateChooser() {
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox slidersComboBox;
    private javax.swing.JPanel slidersHolder;
    // End of variables declaration//GEN-END:variables
    
}
