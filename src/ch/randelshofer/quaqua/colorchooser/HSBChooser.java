/*
 * @(#)HSBChooser.java  1.2  2005-09-05
 *
 * Copyright (c) 2005 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entehue into
 * with Werner Randelshofer.
 */

package ch.randelshofer.quaqua.colorchooser;

import ch.randelshofer.quaqua.*;
import ch.randelshofer.quaqua.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
import javax.swing.plaf.*;
/**
 * A ColorChooser with HSB sliders.
 *
 * @author  Werner Randelshofer
 * @version 1.2 2005-09-05 Get font, spacing and icon from UIManager.
 * <br>1.1.1 2005-04-23 Localized form.
 * <br>1.0  29 March 2005  Created.
 */
public class HSBChooser
extends AbstractColorChooserPanel
implements UIResource {
    final static ResourceBundleUtil labels = ResourceBundleUtil.getBundle("ch.randelshofer.quaqua.Labels");
    
    private ColorSliderModel ccModel = new HSBColorSliderModel();
    
    /** Creates new form. */
    public HSBChooser() {
        initComponents();
        
        //
        Font font = UIManager.getFont("ColorChooser.font");
        hueLabel.setFont(font);
        hueSlider.setFont(font);
        hueField.setFont(font);
        hueFieldLabel.setFont(font);
        saturationLabel.setFont(font);
        saturationSlider.setFont(font);
        saturationField.setFont(font);
        saturationFieldLabel.setFont(font);
        brightnessLabel.setFont(font);
        brightnessSlider.setFont(font);
        brightnessField.setFont(font);
        brightnessFieldLabel.setFont(font);
        //
        int textSliderGap = UIManager.getInt("ColorChooser.textSliderGap");
        if (textSliderGap != 0) {
            Border fieldBorder = new EmptyBorder(0,textSliderGap,0,0);
            hueFieldPanel.setBorder(fieldBorder);
            saturationFieldPanel.setBorder(fieldBorder);
            brightnessFieldPanel.setBorder(fieldBorder);
        }
        
        ccModel.configureColorSlider(0, hueSlider);
        ccModel.configureColorSlider(1, saturationSlider);
        ccModel.configureColorSlider(2, brightnessSlider);
        hueField.setText(Integer.toString(hueSlider.getValue()));
        saturationField.setText(Integer.toString(saturationSlider.getValue()));
        brightnessField.setText(Integer.toString(brightnessSlider.getValue()));
        Insets borderMargin = (Insets) UIManager.getInsets("Component.visualMargin").clone();
        borderMargin.left = 3 - borderMargin.left;
        hueFieldLabel.putClientProperty("Quaqua.Component.visualMargin",borderMargin);
        saturationFieldLabel.putClientProperty("Quaqua.Component.visualMargin",borderMargin);
        brightnessFieldLabel.putClientProperty("Quaqua.Component.visualMargin",borderMargin);
        ccModel.getBoundedRangeModel(0).addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (! hueField.hasFocus()) {
                    hueField.setText(Integer.toString(ccModel.getBoundedRangeModel(0).getValue()));
                }
            }
        });
        ccModel.getBoundedRangeModel(1).addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (! saturationField.hasFocus()) {
                    saturationField.setText(Integer.toString(ccModel.getBoundedRangeModel(1).getValue()));
                }
            }
        });
        ccModel.getBoundedRangeModel(2).addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (! brightnessField.hasFocus()) {
                    brightnessField.setText(Integer.toString(ccModel.getBoundedRangeModel(2).getValue()));
                }
            }
        });
        hueField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent evt) {
                doChanged();
            }
            public void removeUpdate(DocumentEvent evt) {
                doChanged();
            }
            public void insertUpdate(DocumentEvent evt) {
                doChanged();
            }
            private void doChanged() {
                if (hueField.hasFocus()) {
                    try {
                        int value = Integer.decode(hueField.getText()).intValue();
                        if (0 <= value && value <= 359) {
                            ccModel.getBoundedRangeModel(0).setValue(value);
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        });
        saturationField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent evt) {
                doChanged();
            }
            public void removeUpdate(DocumentEvent evt) {
                doChanged();
            }
            public void insertUpdate(DocumentEvent evt) {
                doChanged();
            }
            private void doChanged() {
                if (saturationField.hasFocus()) {
                    try {
                        int value = Integer.decode(saturationField.getText()).intValue();
                        if (0 <= value && value <= 100) {
                            ccModel.getBoundedRangeModel(1).setValue(value);
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        });
        brightnessField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent evt) {
                doChanged();
            }
            public void removeUpdate(DocumentEvent evt) {
                doChanged();
            }
            public void insertUpdate(DocumentEvent evt) {
                doChanged();
            }
            private void doChanged() {
                if (brightnessField.hasFocus()) {
                    try {
                        int value = Integer.decode(brightnessField.getText()).intValue();
                        if (0 <= value && value <= 100) {
                            ccModel.getBoundedRangeModel(2).setValue(value);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(e);
                    }
                }
            }
        });
        ccModel.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                setColorToModel(ccModel.getColor());
            }
        });
        hueField.setMinimumSize(hueField.getPreferredSize());
        saturationField.setMinimumSize(saturationField.getPreferredSize());
        brightnessField.setMinimumSize(brightnessField.getPreferredSize());
        VisualMargin bm = new VisualMargin(false,false,true,false);
        hueLabel.setBorder(bm);
        saturationLabel.setBorder(bm);
        brightnessLabel.setBorder(bm);
    }
    
    protected void buildChooser() {
    }
    
    public String getDisplayName() {
        return labels.getString("ColorChooser.hsbSliders");
    }
    
    public Icon getLargeDisplayIcon() {
        return UIManager.getIcon("ColorChooser.colorSlidersIcon");
    }
    
    public Icon getSmallDisplayIcon() {
        return getLargeDisplayIcon();
    }
    
    public void updateChooser() {
        ccModel.setColor(getColorFromModel());
    }
    public void setColorToModel(Color color) {
        getColorSelectionModel().setSelectedColor(color);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        hueLabel = new javax.swing.JLabel();
        hueSlider = new javax.swing.JSlider();
        hueFieldPanel = new javax.swing.JPanel();
        hueField = new javax.swing.JTextField();
        hueFieldLabel = new javax.swing.JLabel();
        saturationLabel = new javax.swing.JLabel();
        saturationSlider = new javax.swing.JSlider();
        saturationFieldPanel = new javax.swing.JPanel();
        saturationField = new javax.swing.JTextField();
        saturationFieldLabel = new javax.swing.JLabel();
        brightnessLabel = new javax.swing.JLabel();
        brightnessSlider = new javax.swing.JSlider();
        brightnessFieldPanel = new javax.swing.JPanel();
        brightnessField = new javax.swing.JTextField();
        brightnessFieldLabel = new javax.swing.JLabel();
        springPanel = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        hueLabel.setText(labels.getString("ColorChooser.hsbHueText"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        add(hueLabel, gridBagConstraints);

        hueSlider.setMajorTickSpacing(359);
        hueSlider.setMaximum(359);
        hueSlider.setMinorTickSpacing(180);
        hueSlider.setPaintTicks(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        add(hueSlider, gridBagConstraints);

        hueFieldPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        hueField.setColumns(3);
        hueField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        hueField.setText("0");
        hueField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                hueFieldFocusLost(evt);
            }
        });

        hueFieldPanel.add(hueField);

        hueFieldLabel.setText("\u00b0");
        hueFieldPanel.add(hueFieldLabel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        add(hueFieldPanel, gridBagConstraints);

        saturationLabel.setText(labels.getString("ColorChooser.hsbSaturationText"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        add(saturationLabel, gridBagConstraints);

        saturationSlider.setMajorTickSpacing(100);
        saturationSlider.setMinorTickSpacing(50);
        saturationSlider.setPaintTicks(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        add(saturationSlider, gridBagConstraints);

        saturationFieldPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        saturationField.setColumns(3);
        saturationField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        saturationField.setText("0");
        saturationField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                saturationFieldFocusLost(evt);
            }
        });

        saturationFieldPanel.add(saturationField);

        saturationFieldLabel.setText("%");
        saturationFieldPanel.add(saturationFieldLabel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        add(saturationFieldPanel, gridBagConstraints);

        brightnessLabel.setText(labels.getString("ColorChooser.hsbBrightnessText"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        add(brightnessLabel, gridBagConstraints);

        brightnessSlider.setMajorTickSpacing(100);
        brightnessSlider.setMinorTickSpacing(50);
        brightnessSlider.setPaintTicks(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        add(brightnessSlider, gridBagConstraints);

        brightnessFieldPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        brightnessField.setColumns(3);
        brightnessField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        brightnessField.setText("0");
        brightnessField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                brightnessFieldFocusLost(evt);
            }
        });

        brightnessFieldPanel.add(brightnessField);

        brightnessFieldLabel.setText("%");
        brightnessFieldPanel.add(brightnessFieldLabel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        add(brightnessFieldPanel, gridBagConstraints);

        springPanel.setLayout(new java.awt.BorderLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 100;
        gridBagConstraints.weighty = 1.0;
        add(springPanel, gridBagConstraints);

    }//GEN-END:initComponents

    private void brightnessFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_brightnessFieldFocusLost
        brightnessField.setText(Integer.toString(ccModel.getBoundedRangeModel(2).getValue()));
    }//GEN-LAST:event_brightnessFieldFocusLost

    private void saturationFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_saturationFieldFocusLost
   saturationField.setText(Integer.toString(ccModel.getBoundedRangeModel(1).getValue()));
    }//GEN-LAST:event_saturationFieldFocusLost

    private void hueFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hueFieldFocusLost
         hueField.setText(Integer.toString(ccModel.getBoundedRangeModel(0).getValue()));
    }//GEN-LAST:event_hueFieldFocusLost
                
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField brightnessField;
    private javax.swing.JLabel brightnessFieldLabel;
    private javax.swing.JPanel brightnessFieldPanel;
    private javax.swing.JLabel brightnessLabel;
    private javax.swing.JSlider brightnessSlider;
    private javax.swing.JTextField hueField;
    private javax.swing.JLabel hueFieldLabel;
    private javax.swing.JPanel hueFieldPanel;
    private javax.swing.JLabel hueLabel;
    private javax.swing.JSlider hueSlider;
    private javax.swing.JTextField saturationField;
    private javax.swing.JLabel saturationFieldLabel;
    private javax.swing.JPanel saturationFieldPanel;
    private javax.swing.JLabel saturationLabel;
    private javax.swing.JSlider saturationSlider;
    private javax.swing.JPanel springPanel;
    // End of variables declaration//GEN-END:variables
    
}
