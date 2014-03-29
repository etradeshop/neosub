/*
 * @(#)ColorPalettesChooser.java  1.0  September 18, 2005
 *
 * Copyright (c) 2005 Werner Randelshofer
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
import javax.swing.colorchooser.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import java.util.*;

/**
 * ColorPalettesChooser.
 *
 * @author  Werner Randelshofer
 * @version 1.0 September 18, 2005 Created.
 */
public class ColorPalettesChooser extends AbstractColorChooserPanel implements UIResource {
    final static ResourceBundleUtil labels = ResourceBundleUtil.getBundle("ch.randelshofer.quaqua.Labels");
    
    /**
     * We store here the name of the last selected color sliders panel.
     * When the ColorSlidersChooser is recreated multiple times in the same
     * panel, the application 'remembers' which panel the user had opened
     * before.
     */
    private static int lastSelectedPalette = 0;
    
    /**
     * Creates a new instance.
     */
    public ColorPalettesChooser() {
        initComponents();
        //
        Font font = UIManager.getFont("ColorChooser.font");
        paletteLabel.setFont(font);
        paletteCombo.setFont(font);
        paletteScrollPane.setFont(font);
        paletteList.setFont(font);
        //
        paletteList.setCellRenderer(new PaletteEntryCellRenderer());
        //
        DefaultComboBoxModel cbm = new DefaultComboBoxModel(loadPalettes());
        paletteCombo.setModel(cbm);
        //updatePaletteList();
        
        paletteList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            /**
             * Called whenever the value of the selection changes.
             * @param e the event that characterizes the change.
             */
            public void valueChanged(ListSelectionEvent e) {
                PaletteEntry entry = (PaletteEntry) paletteList.getSelectedValue();
                if (entry != null) {
                    PaletteListModel lm = (PaletteListModel) paletteList.getModel();
                    lm.setClosestIndex(-1);
                    setColorToModel(entry.getColor());
                }
            }
        });
        
        paletteCombo.setSelectedIndex(lastSelectedPalette);
        
        loadPalettes();
        updatePaletteList();
    }
    
    /**
     * @return Vector<PaletteListModel>.
     */
    protected Vector loadPalettes() {
        Vector palettes = new Vector();
        
        Color[] colors;
        PaletteEntry[] entries;
        
        colors = DefaultPalettes.APPLE_COLORS;
        entries = new PaletteEntry[colors.length];
        for (int i=0; i < colors.length; i++) {
            entries[i] = new PaletteEntry(
            labels.getString("ColorChooser.apple."+Integer.toHexString(0xff000000|colors[i].getRGB()).substring(2)),
            colors[i]
            );
        }
        palettes.add(new PaletteListModel(
        labels.getString("ColorChooser.appleColors"),
        labels.getFormatted("ColorChooser.profileContainsNColors", new Object[] {labels.getString("ColorChooser.appleColors"), new Integer(entries.length)}),
        entries)
        );
        
        colors = DefaultPalettes.CRAYONS;
        entries = new PaletteEntry[colors.length];
        for (int i=0; i < colors.length; i++) {
            entries[i % 8 + colors.length - (i / 8) * 8 - 8] = new PaletteEntry(
            labels.getString("ColorChooser.crayon."+Integer.toHexString(0xff000000|colors[i].getRGB()).substring(2)),
            colors[i]
            );
        }
        palettes.add(new PaletteListModel(
        labels.getString("ColorChooser.crayons"),
        labels.getFormatted("ColorChooser.profileContainsNColors", new Object[] {labels.getString("ColorChooser.crayons"), new Integer(entries.length)}),
        entries)
        );
        
        colors = DefaultPalettes.WEB_SAFE_COLORS;
        entries = new PaletteEntry[colors.length];
        for (int i=0; i < colors.length; i++) {
            entries[i] = new PaletteEntry(
            Integer.toHexString(0xff000000|colors[i].getRGB()).substring(2).toUpperCase(),
            colors[i]
            );
        }
        palettes.add(new PaletteListModel(
        labels.getString("ColorChooser.webSafeColors"),
        labels.getFormatted("ColorChooser.profileContainsNColors", new Object[] {labels.getString("ColorChooser.webSafeColors"), new Integer(entries.length)}),
        entries)
        );
        
        return palettes;
    }
    
    private void updatePaletteList() {
        PaletteListModel palette = (PaletteListModel) paletteCombo.getSelectedItem();
        paletteList.setModel(palette);
        paletteCombo.setToolTipText(palette.getInfo());
        updateChooser();
    }
    
    protected void buildChooser() {
    }
    
    public String getDisplayName() {
        return labels.getString("ColorChooser.colorPalettes");
    }
    
    public javax.swing.Icon getLargeDisplayIcon() {
        return UIManager.getIcon("ColorChooser.colorPalettesIcon");
    }
    
    public Icon getSmallDisplayIcon() {
        return getLargeDisplayIcon();
    }
    
    public void updateChooser() {
        Color color;
        try {
            color = getColorFromModel();
        } catch (NullPointerException e) {
            return;
        }
        
        int rgb = color.getRGB() & 0xffffff;
        
        // Return quickly if color is the same as selected entry
        PaletteEntry entry = (PaletteEntry) paletteList.getSelectedValue();
        if (entry != null && (entry.getColor().getRGB() & 0xffffff) == rgb) {
            return;
        }
        
        // Search for entry and select it
        PaletteListModel lm = (PaletteListModel) paletteList.getModel();
        int i, n;
        for (i=0, n = lm.getSize(); i < n; i++) {
            entry = (PaletteEntry) lm.getElementAt(i);
            if ((entry.getColor().getRGB() & 0xffffff) == rgb) {
                break;
            }
        }
        if (i < n) {
            // Matching color found? Select it and scroll it to visible.
            lm.setClosestIndex(-1);
            paletteList.setSelectedIndex(i);
            paletteList.scrollRectToVisible(paletteList.getCellBounds(i,i));
        } else {
            // No matching color found? Clear selection,
            paletteList.clearSelection();
            int closest = lm.computeClosestIndex(color);
            lm.setClosestIndex(closest);
            if (closest != -1) {
                paletteList.scrollRectToVisible(paletteList.getCellBounds(closest,closest));
            }
        }
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

        paletteLabel = new javax.swing.JLabel();
        paletteCombo = new javax.swing.JComboBox();
        paletteScrollPane = new javax.swing.JScrollPane();
        paletteList = new javax.swing.JList();

        setLayout(new java.awt.GridBagLayout());

        paletteLabel.setLabelFor(paletteCombo);
        paletteLabel.setText(java.util.ResourceBundle.getBundle("ch/randelshofer/quaqua/Labels").getString("ColorChooser.list"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        add(paletteLabel, gridBagConstraints);

        paletteCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                paletteChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        add(paletteCombo, gridBagConstraints);

        paletteScrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        paletteScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        paletteList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        paletteScrollPane.setViewportView(paletteList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(paletteScrollPane, gridBagConstraints);

    }//GEN-END:initComponents
    
    private void paletteChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_paletteChanged
        updatePaletteList();
        lastSelectedPalette = paletteCombo.getSelectedIndex();
    }//GEN-LAST:event_paletteChanged
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox paletteCombo;
    private javax.swing.JLabel paletteLabel;
    private javax.swing.JList paletteList;
    private javax.swing.JScrollPane paletteScrollPane;
    // End of variables declaration//GEN-END:variables
    
}
