/*
 * @(#)RGBChooser.java  1.1  2005-09-05
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
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
import javax.swing.plaf.*;
/**
 * RGBChooser.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2005-09-05 Get font, spacing and icon from UIManager.
 * <br>1.0  29 March 2005  Created.
 */
public class RGBChooser extends AbstractColorChooserPanel implements UIResource {
    final static ResourceBundleUtil labels = ResourceBundleUtil.getBundle("ch.randelshofer.quaqua.Labels");
    private ColorSliderModel ccModel = new RGBColorSliderModel();
    
    /** Creates new form. */
    public RGBChooser() {
        initComponents();
        
        //
        Font font = UIManager.getFont("ColorChooser.font");
        redLabel.setFont(font);
        redSlider.setFont(font);
        redField.setFont(font);
        greenLabel.setFont(font);
        greenSlider.setFont(font);
        greenField.setFont(font);
        blueLabel.setFont(font);
        blueSlider.setFont(font);
        blueField.setFont(font);
        //
        int textSliderGap = UIManager.getInt("ColorChooser.textSliderGap");
        if (textSliderGap != 0) {
            Insets fieldInsets = new Insets(0,textSliderGap,0,0);
            GridBagLayout layout = (GridBagLayout) getLayout();
            GridBagConstraints gbc;
            gbc = layout.getConstraints(redField);
            gbc.insets = fieldInsets;
            layout.setConstraints(redField, gbc);
            gbc = layout.getConstraints(greenField);
            gbc.insets = fieldInsets;
            layout.setConstraints(greenField, gbc);
            gbc = layout.getConstraints(blueField);
            gbc.insets = fieldInsets;
            layout.setConstraints(blueField, gbc);
        }
        
        ccModel.configureColorSlider(0, redSlider);
        ccModel.configureColorSlider(1, greenSlider);
        ccModel.configureColorSlider(2, blueSlider);
        
        redField.setText(Integer.toString(redSlider.getValue()));
        greenField.setText(Integer.toString(greenSlider.getValue()));
        blueField.setText(Integer.toString(blueSlider.getValue()));
        
        ccModel.getBoundedRangeModel(0).addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (! redField.hasFocus()) {
                    redField.setText(Integer.toString(ccModel.getValue(0)));
                }
            }
        });
        ccModel.getBoundedRangeModel(1).addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (! greenField.hasFocus()) {
                    greenField.setText(Integer.toString(ccModel.getValue(1)));
                }
            }
        });
        ccModel.getBoundedRangeModel(2).addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (! blueField.hasFocus()) {
                    blueField.setText(Integer.toString(ccModel.getValue(2)));
                }
            }
        });
        redField.getDocument().addDocumentListener(new DocumentListener() {
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
                if (redField.hasFocus()) {
                    try {
                        int value = Integer.decode(redField.getText()).intValue();
                        if (0 <= value && value <= 255) {
                            ccModel.setValue(0, value);
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        });
        greenField.getDocument().addDocumentListener(new DocumentListener() {
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
                if (greenField.hasFocus()) {
                    try {
                        int value = Integer.decode(greenField.getText()).intValue();
                        if (0 <= value && value <= 255) {
                            ccModel.setValue(1, value);
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        });
        blueField.getDocument().addDocumentListener(new DocumentListener() {
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
                if (blueField.hasFocus()) {
                    try {
                        int value = Integer.decode(blueField.getText()).intValue();
                        if (0 <= value && value <= 255) {
                            ccModel.setValue(2, value);
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
        redField.setMinimumSize(redField.getPreferredSize());
        greenField.setMinimumSize(greenField.getPreferredSize());
        blueField.setMinimumSize(blueField.getPreferredSize());
        VisualMargin bm = new VisualMargin(false,false,true,false);
        redLabel.setBorder(bm);
        greenLabel.setBorder(bm);
        blueLabel.setBorder(bm);
    }
    protected void buildChooser() {
    }
    
    public String getDisplayName() {
        return labels.getString("ColorChooser.rgbSliders");
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

        redLabel = new javax.swing.JLabel();
        redSlider = new javax.swing.JSlider();
        redField = new javax.swing.JTextField();
        greenLabel = new javax.swing.JLabel();
        greenSlider = new javax.swing.JSlider();
        greenField = new javax.swing.JTextField();
        blueLabel = new javax.swing.JLabel();
        blueSlider = new javax.swing.JSlider();
        blueField = new javax.swing.JTextField();
        springPanel = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        redLabel.setText(labels.getString("ColorChooser.rgbRedText"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        add(redLabel, gridBagConstraints);

        redSlider.setMajorTickSpacing(255);
        redSlider.setMaximum(255);
        redSlider.setMinorTickSpacing(128);
        redSlider.setPaintTicks(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        add(redSlider, gridBagConstraints);

        redField.setColumns(3);
        redField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        redField.setText("0");
        redField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                redFieldFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        add(redField, gridBagConstraints);

        greenLabel.setText(labels.getString("ColorChooser.rgbGreenText"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        add(greenLabel, gridBagConstraints);

        greenSlider.setMajorTickSpacing(255);
        greenSlider.setMaximum(255);
        greenSlider.setMinorTickSpacing(128);
        greenSlider.setPaintTicks(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        add(greenSlider, gridBagConstraints);

        greenField.setColumns(3);
        greenField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        greenField.setText("0");
        greenField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                greenFieldFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        add(greenField, gridBagConstraints);

        blueLabel.setText(labels.getString("ColorChooser.rgbBlueText"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        add(blueLabel, gridBagConstraints);

        blueSlider.setMajorTickSpacing(255);
        blueSlider.setMaximum(255);
        blueSlider.setMinorTickSpacing(128);
        blueSlider.setPaintTicks(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        add(blueSlider, gridBagConstraints);

        blueField.setColumns(3);
        blueField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        blueField.setText("0");
        blueField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                blueFieldFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        add(blueField, gridBagConstraints);

        springPanel.setLayout(new java.awt.BorderLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 100;
        gridBagConstraints.weighty = 1.0;
        add(springPanel, gridBagConstraints);

    }//GEN-END:initComponents

    private void blueFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_blueFieldFocusLost
     blueField.setText(Integer.toString(ccModel.getValue(2)));
    }//GEN-LAST:event_blueFieldFocusLost

    private void greenFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_greenFieldFocusLost
      greenField.setText(Integer.toString(ccModel.getValue(1)));
    }//GEN-LAST:event_greenFieldFocusLost

    private void redFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_redFieldFocusLost
       redField.setText(Integer.toString(ccModel.getValue(0)));
    }//GEN-LAST:event_redFieldFocusLost
                
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField blueField;
    private javax.swing.JLabel blueLabel;
    private javax.swing.JSlider blueSlider;
    private javax.swing.JTextField greenField;
    private javax.swing.JLabel greenLabel;
    private javax.swing.JSlider greenSlider;
    private javax.swing.JTextField redField;
    private javax.swing.JLabel redLabel;
    private javax.swing.JSlider redSlider;
    private javax.swing.JPanel springPanel;
    // End of variables declaration//GEN-END:variables
    
}
