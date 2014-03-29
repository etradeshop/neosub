package test;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

import org.jvnet.substance.*;
import org.jvnet.substance.SubstanceImageCreator.Side;
import org.jvnet.substance.color.ColorScheme;
import org.jvnet.substance.color.ColorSchemeEnum;

public class ThemeEditor extends JFrame {
    private enum ColorKind {
        FOREGROUND, ULTRA_DARK, DARK, MID, LIGHT, EXTRA_LIGHT, ULTRA_LIGHT
    }

    private Map<ColorKind, Color> currColors = new HashMap<ColorKind, Color>();

    private ColorScheme currColorScheme;

    private JComboBox themeCombobox;

    private JCheckBox isDarkCheckBox;

    private Map<ColorKind, ColorInfoPanel> colorPanels;

    private PreviewPanel previewPanel;

    private static class ColorInfoPanel extends JPanel {
        private JLabel colorKindLabel;

        private JLabel rLabel;

        private JLabel gLabel;

        private JLabel bLabel;

        private JTextField rTextField;

        private JTextField gTextField;

        private JTextField bTextField;

        private JButton colorChooseButton;

        private ThemeEditor themeEditor;

        private ColorKind colorKind;

        public ColorInfoPanel(final ThemeEditor themeEditor,
                              ColorKind colorKind, Color startValue) {
            this.themeEditor = themeEditor;
            this.colorKind = colorKind;
            this.setLayout(new GridLayout(1, 2));
            this.colorKindLabel = new JLabel(colorKind.name());
            this.colorKindLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
            this.add(this.colorKindLabel);

            JPanel others = new JPanel(new FlowLayout(FlowLayout.CENTER));
            this.rLabel = new JLabel("R:");
            this.gLabel = new JLabel("G:");
            this.bLabel = new JLabel("B:");
            this.rTextField = new JTextField("" + startValue.getRed());
            this.rTextField.setHorizontalAlignment(JTextField.RIGHT);
            this.rTextField.setColumns(4);
            this.rTextField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            colorsChanged();
                            themeEditor.repaint();
                        }
                    });
                }
            });
            this.rTextField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            colorsChanged();
                            themeEditor.repaint();
                        }
                    });
                }
            });
            this.gTextField = new JTextField("" + startValue.getGreen());
            this.gTextField.setHorizontalAlignment(JTextField.RIGHT);
            this.gTextField.setColumns(4);
            this.gTextField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            colorsChanged();
                            themeEditor.repaint();
                        }
                    });
                }
            });
            this.gTextField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            colorsChanged();
                            themeEditor.repaint();
                        }
                    });
                }
            });
            this.bTextField = new JTextField("" + startValue.getBlue());
            this.bTextField.setHorizontalAlignment(JTextField.RIGHT);
            this.bTextField.setColumns(4);
            this.bTextField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            colorsChanged();
                            themeEditor.repaint();
                        }
                    });
                }
            });
            this.bTextField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            colorsChanged();
                            themeEditor.repaint();
                        }
                    });
                }
            });

            others.add(rLabel);
            others.add(rTextField);
            others.add(gLabel);
            others.add(gTextField);
            others.add(bLabel);
            others.add(bTextField);

            this.colorChooseButton = new JButton("Choose");
            this.colorChooseButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Color color = JColorChooser.showDialog(themeEditor,
                            "Color chooser", new Color(Integer
                            .parseInt(rTextField.getText()), Integer
                            .parseInt(gTextField.getText()), Integer
                            .parseInt(bTextField.getText())));
                    if (color != null) {
                        rTextField.setText("" + color.getRed());
                        gTextField.setText("" + color.getGreen());
                        bTextField.setText("" + color.getBlue());
                        colorsChanged();
                    }
                }
            });
            others.add(this.colorChooseButton);
            this.add(others);
        }

        private void colorsChanged() {
            System.out.println("At " + this.colorKind + " -> ["
                    + this.rTextField.getText() + ", "
                    + this.gTextField.getText() + ", "
                    + this.bTextField.getText() + "]");
            this.themeEditor.currColors.put(this.colorKind, new Color(Integer
                    .parseInt(this.rTextField.getText()), Integer
                    .parseInt(this.gTextField.getText()), Integer
                    .parseInt(this.bTextField.getText())));
            this.themeEditor.syncColorSchemeFromUserInput();
        }

        public void setColor(Color color) {
            this.rTextField.setText("" + color.getRed());
            this.gTextField.setText("" + color.getGreen());
            this.bTextField.setText("" + color.getBlue());
            // this.colorsChanged();
        }
    }

    private static class PreviewPanel extends JPanel {
        private ThemeEditor themeEditor;

        public PreviewPanel(ThemeEditor themeEditor) {
            this.themeEditor = themeEditor;
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D graphics = (Graphics2D) g.create();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

            Font font11 = new Font("Tahoma", Font.PLAIN, 11);
            Font font12 = new Font("Tahoma", Font.PLAIN, 12);

            boolean isDark = this.themeEditor.isDarkCheckBox.isSelected();
            ColorSchemeEnum metalScheme = isDark ?
                    ColorSchemeEnum.DARK_METALLIC :
                    ColorSchemeEnum.METALLIC;
            ColorSchemeEnum grayScheme = isDark ?
                    ColorSchemeEnum.DARK_GRAY :
                    ColorSchemeEnum.LIGHT_GRAY;

            Color backgroundColor =
                    isDark ? ColorSchemeEnum.USER_DEFINED
                            .getColorScheme().getDarkColor().brighter()
                            : metalScheme.getColorScheme()
                            .getExtraLightColor();

            graphics.setColor(backgroundColor);
            graphics.fillRect(0, 0, this.getWidth(), this.getHeight());


            graphics.setColor(ColorSchemeEnum.USER_DEFINED.getColorScheme()
                    .getForegroundColor());
            graphics.setFont(font12);
            graphics.drawString("Regular button", 10, 20);
            graphics.setFont(font11);
            graphics.setColor(ColorSchemeEnum.USER_DEFINED.getColorScheme()
                    .getForegroundColor());
            graphics.drawImage(SubstanceImageCreator.getRoundedBackground(70,
                    20, 10, ColorSchemeEnum.USER_DEFINED, 0, null), 180, 5,
                    null);
            graphics.drawString("button", 200, 19);

            graphics
                    .drawImage(SubstanceImageCreator.getRoundedBackground(16,
                            16, 2, ColorSchemeEnum.USER_DEFINED, 0, null), 260,
                            7, null);

            graphics.drawImage(SubstanceImageCreator.getRoundedBackground(70,
                    20, 10, metalScheme, 0, null), 280, 5, null);
            graphics.drawString("button", 300, 19);

            graphics.drawImage(SubstanceImageCreator.getRoundedBackground(16,
                    16, 2, metalScheme, 0, null), 360, 7, null);

            graphics.setColor(ColorSchemeEnum.USER_DEFINED.getColorScheme()
                    .getForegroundColor());
            graphics.setFont(font12);
            graphics.drawString("Check / Radio", 10, 45);
            graphics.drawImage(SubstanceImageCreator.getCheckBox(16,
                    ComponentState.SELECTED, ColorSchemeEnum.USER_DEFINED),
                    180, 30, null);
            graphics.drawImage(SubstanceImageCreator.getCheckBox(16,
                    ComponentState.DEFAULT, ColorSchemeEnum.USER_DEFINED), 200,
                    30, null);
            graphics.drawImage(SubstanceImageCreator.getCheckBox(16,
                    ComponentState.ROLLOVER_SELECTED,
                    ColorSchemeEnum.USER_DEFINED), 220, 30, null);
            graphics.drawImage(SubstanceImageCreator.getCheckBox(16,
                    ComponentState.DISABLED_ACTIVE,
                    ColorSchemeEnum.USER_DEFINED), 240, 30, null);

            graphics.drawImage(SubstanceImageCreator.getRadioButton(12,
                    ComponentState.SELECTED, 0, ColorSchemeEnum.USER_DEFINED),
                    260, 32, null);
            graphics.drawImage(SubstanceImageCreator.getRadioButton(12,
                    ComponentState.DEFAULT, 0, ColorSchemeEnum.USER_DEFINED),
                    280, 32, null);
            graphics.drawImage(SubstanceImageCreator.getRadioButton(12,
                    ComponentState.ROLLOVER_SELECTED, 0,
                    ColorSchemeEnum.USER_DEFINED), 300, 32, null);
            graphics.drawImage(SubstanceImageCreator.getRadioButton(12,
                    ComponentState.DISABLED_ACTIVE, 0,
                    ColorSchemeEnum.USER_DEFINED), 320, 32, null);

            graphics.setColor(ColorSchemeEnum.USER_DEFINED.getColorScheme()
                    .getForegroundColor());
            graphics.setFont(font12);
            graphics.drawString("Combo / spinner / slider", 10, 70);
            graphics.drawImage(SubstanceImageCreator.getRoundedBackground(16,
                    16, 2, ColorSchemeEnum.USER_DEFINED, 0, Side.LEFT), 180,
                    57, null);
            SubstanceImageCreator.getArrowIcon(
                    SubstanceImageCreator.ARROW_WIDTH,
                    SubstanceImageCreator.ARROW_HEIGHT, SwingConstants.SOUTH,
                    ColorSchemeEnum.USER_DEFINED).paintIcon(this, graphics,
                    183, 62);
            graphics.drawImage(SubstanceImageCreator.getRoundedBackground(16,
                    16, 2, grayScheme, 0, Side.LEFT), 200, 57,
                    null);
            SubstanceImageCreator.getArrowIcon(
                    SubstanceImageCreator.ARROW_WIDTH,
                    SubstanceImageCreator.ARROW_HEIGHT, SwingConstants.SOUTH,
                    grayScheme).paintIcon(this, graphics, 203,
                    62);

            graphics.drawImage(SubstanceImageCreator.getFlipRoundedButton(16,
                    9, 0, ColorSchemeEnum.USER_DEFINED, 0, Side.BOTTOM, true),
                    220, 56, null);
            graphics.drawImage(SubstanceImageCreator.getFlipRoundedButton(16,
                    9, 0, ColorSchemeEnum.USER_DEFINED, 0, Side.TOP, true),
                    220, 65, null);
            SubstanceImageCreator.getArrowIcon(7, 5, SwingConstants.NORTH,
                    ColorSchemeEnum.USER_DEFINED).paintIcon(this, graphics,
                    224, 58);
            SubstanceImageCreator.getArrowIcon(7, 5, SwingConstants.SOUTH,
                    ColorSchemeEnum.USER_DEFINED).paintIcon(this, graphics,
                    224, 66);

            graphics.drawImage(SubstanceImageCreator.getFlipRoundedButton(16,
                    9, 0, grayScheme, 0, Side.BOTTOM, true),
                    240, 56, null);
            graphics.drawImage(SubstanceImageCreator.getFlipRoundedButton(16,
                    9, 0, ColorSchemeEnum.USER_DEFINED, 0, Side.TOP, true),
                    240, 65, null);
            SubstanceImageCreator.getArrowIcon(7, 5, SwingConstants.NORTH,
                    grayScheme).paintIcon(this, graphics, 244,
                    58);
            SubstanceImageCreator.getArrowIcon(7, 5, SwingConstants.SOUTH,
                    ColorSchemeEnum.USER_DEFINED).paintIcon(this, graphics,
                    244, 66);

            graphics.drawImage(SubstanceImageCreator.getFlipRoundedButton(16,
                    9, 0, ColorSchemeEnum.USER_DEFINED, 0, Side.BOTTOM, true),
                    260, 56, null);
            graphics.drawImage(SubstanceImageCreator.getFlipRoundedButton(16,
                    9, 0, grayScheme, 0, Side.TOP, true), 260,
                    65, null);
            SubstanceImageCreator.getArrowIcon(7, 5, SwingConstants.NORTH,
                    ColorSchemeEnum.USER_DEFINED).paintIcon(this, graphics,
                    264, 58);
            SubstanceImageCreator.getArrowIcon(7, 5, SwingConstants.SOUTH,
                    grayScheme).paintIcon(this, graphics, 264,
                    66);

            graphics.drawImage(SubstanceImageCreator.getFlipRoundedButton(16,
                    9, 0, grayScheme, 0, Side.BOTTOM, true),
                    280, 56, null);
            graphics.drawImage(SubstanceImageCreator.getFlipRoundedButton(16,
                    9, 0, grayScheme, 0, Side.TOP, true), 280,
                    65, null);
            SubstanceImageCreator.getArrowIcon(7, 5, SwingConstants.NORTH,
                    grayScheme).paintIcon(this, graphics, 284,
                    58);
            SubstanceImageCreator.getArrowIcon(7, 5, SwingConstants.SOUTH,
                    grayScheme).paintIcon(this, graphics, 284,
                    66);

            graphics.drawImage(
                    SubstanceImageCreator.getRoundedTriangleBackground(16, 15,
                            2, ComponentState.ACTIVE,
                            ColorSchemeEnum.USER_DEFINED), 300, 57, null);
            graphics.drawImage(SubstanceImageCreator
                    .getRoundedTriangleBackground(16, 15, 2,
                    ComponentState.DEFAULT,
                    ColorSchemeEnum.USER_DEFINED), 320, 57, null);
            graphics.drawImage(SubstanceImageCreator.getRotated(
                    SubstanceImageCreator.getRoundedTriangleBackground(15, 16,
                            2, ComponentState.ACTIVE,
                            ColorSchemeEnum.USER_DEFINED), 3), 340, 57, null);
            graphics.drawImage(SubstanceImageCreator.getRotated(
                    SubstanceImageCreator.getRoundedTriangleBackground(15, 16,
                            2, ComponentState.DEFAULT,
                            ColorSchemeEnum.USER_DEFINED), 3), 360, 57, null);

            graphics.setColor(ColorSchemeEnum.USER_DEFINED.getColorScheme()
                    .getForegroundColor());
            graphics.setFont(font12);
            graphics.drawString("Scroll bar", 10, 95);

            graphics.drawImage(SubstanceImageCreator.getRoundedBackground(16,
                    16, 0, ColorSchemeEnum.USER_DEFINED, 0, Side.RIGHT, true),
                    180, 82, null);
            SubstanceImageCreator.getArrowIcon(
                    SubstanceImageCreator.ARROW_WIDTH,
                    SubstanceImageCreator.ARROW_HEIGHT, SwingConstants.WEST,
                    ColorSchemeEnum.USER_DEFINED).paintIcon(this, graphics,
                    184, 86);
            graphics.drawImage(SubstanceImageCreator
                    .getCompositeRoundedBackground(
                    ColorSchemeEnum.USER_DEFINED, 180, 16, 8,
                    ComponentState.ACTIVE, ComponentState.DEFAULT,
                    false), 196, 82, null);
            graphics.drawImage(SubstanceImageCreator.getRoundedBackground(16,
                    16, 0, metalScheme, 0, Side.LEFT, true), 376,
                    82, null);
            SubstanceImageCreator.getArrowIcon(
                    SubstanceImageCreator.ARROW_WIDTH,
                    SubstanceImageCreator.ARROW_HEIGHT, SwingConstants.EAST,
                    ColorSchemeEnum.USER_DEFINED).paintIcon(this, graphics,
                    380, 86);

            graphics.drawImage(SubstanceImageCreator.getRoundedBackground(80,
                    16, 8, ColorSchemeEnum.USER_DEFINED, 0, null), 216, 82,
                    null);

            graphics.setColor(ColorSchemeEnum.USER_DEFINED.getColorScheme()
                    .getForegroundColor());
            graphics.setFont(font12);
            graphics.drawString("Tabs", 10, 120);

            graphics.drawImage(SubstanceImageCreator.getRoundedBackground(60,
                    17, 6, ColorSchemeEnum.USER_DEFINED,
                    ColorSchemeEnum.USER_DEFINED, 0, Side.BOTTOM, true, true),
                    180, 105, null);
            graphics.setFont(font11);
            graphics.setColor(ColorSchemeEnum.USER_DEFINED.getColorScheme()
                    .getForegroundColor());
            graphics.drawString("Tab", 200, 118);

            graphics.drawImage(SubstanceImageCreator.getRoundedBackground(60,
                    17, 6, metalScheme, metalScheme,
                    0, Side.BOTTOM, true, true), 250, 105, null);
            graphics.drawString("Tab", 270, 118);

            graphics.drawImage(SubstanceImageCreator.getRoundedBackground(60,
                    17, 6, grayScheme,
                    grayScheme, 0, Side.BOTTOM, true, true),
                    320, 105, null);
            graphics.setColor(grayScheme.getColorScheme()
                    .getDarkColor());
            graphics.drawString("Tab", 340, 118);

            graphics.setColor(ColorSchemeEnum.USER_DEFINED.getColorScheme()
                    .getForegroundColor());
            graphics.setFont(font12);
            graphics.drawString("Progress bars", 10, 145);

            SubstanceImageCreator.paintBorder(graphics, 180, 130, 100, 16,
                    metalScheme);
            SubstanceImageCreator.paintRectangularStripedBackground(graphics,
                    182, 132, 96, 13, metalScheme, null, 0, false);
            SubstanceImageCreator.paintLonghornProgressBar(g, 182, 132, 75, 12,
                    ColorSchemeEnum.USER_DEFINED, false);

            SubstanceImageCreator.paintBorder(graphics, 290, 130, 100, 16,
                    metalScheme);
            SubstanceImageCreator.paintRectangularStripedBackground(graphics,
                    292, 132, 96, 13, metalScheme, null, 0, false);
            SubstanceImageCreator.paintRectangularStripedBackground(graphics,
                    292, 132, 96, 12, ColorSchemeEnum.USER_DEFINED,
                    SubstanceImageCreator.getStripe(13), 20, false);

            graphics.setColor(ColorSchemeEnum.USER_DEFINED.getColorScheme()
                    .getForegroundColor());
            graphics.setFont(font12);
            graphics.drawString("Gradient background", 10, 170);

            SubstanceImageCreator.paintRectangularBackground(g, 180, 155, 100,
                    16, ColorSchemeEnum.USER_DEFINED, true, false);
            SubstanceImageCreator.paintRectangularBackground(g, 290, 155, 100,
                    16, ColorSchemeEnum.USER_DEFINED, false, false);

            graphics.setColor(ColorSchemeEnum.USER_DEFINED.getColorScheme()
                    .getForegroundColor());
            graphics.setFont(font12);
            graphics.drawString("Option pane icons", 10, 195);

            SubstanceImageCreator.getErrorMarkerIcon(31,
                    ColorSchemeEnum.USER_DEFINED).paintIcon(this, graphics,
                    180, 180);

            SubstanceImageCreator.getInfoMarkerIcon(32,
                    ColorSchemeEnum.USER_DEFINED).paintIcon(this, graphics,
                    220, 180);

            SubstanceImageCreator.getQuestionMarkerIcon(31,
                    ColorSchemeEnum.USER_DEFINED).paintIcon(this, graphics,
                    260, 180);

            SubstanceImageCreator.getWarningMarkerIcon(32,
                    ColorSchemeEnum.USER_DEFINED).paintIcon(this, graphics,
                    300, 180);

            graphics.dispose();
        }
    }

    private void syncColorSchemeFromUserInput() {
        this.currColorScheme = new ColorScheme() {
            public Color getDarkColor() {
                return currColors.get(ColorKind.ULTRA_DARK);
            }

            public Color getExtraLightColor() {
                return currColors.get(ColorKind.EXTRA_LIGHT);
            }

            public Color getForegroundColor() {
                return currColors.get(ColorKind.FOREGROUND);
            }

            public Color getLightColor() {
                return currColors.get(ColorKind.LIGHT);
            }

            public Color getMidColor() {
                return currColors.get(ColorKind.MID);
            }

            public Color getUltraDarkColor() {
                return currColors.get(ColorKind.ULTRA_DARK);
            }

            public Color getUltraLightColor() {
                return currColors.get(ColorKind.ULTRA_LIGHT);
            }
        };
        ColorSchemeEnum.setUserDefined(this.currColorScheme, false, "Editing");
    }

    private void syncColorsFromColorSchemeEnum(
            ColorSchemeEnum colorSchemeEnum) {
        this.currColors.put(ColorKind.ULTRA_DARK, colorSchemeEnum
                .getColorScheme().getDarkColor());
        this.currColors.put(ColorKind.EXTRA_LIGHT, colorSchemeEnum
                .getColorScheme().getExtraLightColor());
        this.currColors.put(ColorKind.FOREGROUND, colorSchemeEnum
                .getColorScheme().getForegroundColor());
        this.currColors.put(ColorKind.LIGHT, colorSchemeEnum.getColorScheme()
                .getLightColor());
        this.currColors.put(ColorKind.MID, colorSchemeEnum.getColorScheme()
                .getMidColor());
        this.currColors.put(ColorKind.ULTRA_DARK, colorSchemeEnum
                .getColorScheme().getUltraDarkColor());
        this.currColors.put(ColorKind.ULTRA_LIGHT, colorSchemeEnum
                .getColorScheme().getUltraLightColor());
        this.syncColorSchemeFromUserInput();

        this.colorPanels.get(ColorKind.FOREGROUND).setColor(
                colorSchemeEnum.getColorScheme().getForegroundColor());
        this.colorPanels.get(ColorKind.ULTRA_DARK).setColor(
                colorSchemeEnum.getColorScheme().getUltraDarkColor());
        this.colorPanels.get(ColorKind.DARK).setColor(
                colorSchemeEnum.getColorScheme().getDarkColor());
        this.colorPanels.get(ColorKind.MID).setColor(
                colorSchemeEnum.getColorScheme().getMidColor());
        this.colorPanels.get(ColorKind.LIGHT).setColor(
                colorSchemeEnum.getColorScheme().getLightColor());
        this.colorPanels.get(ColorKind.EXTRA_LIGHT).setColor(
                colorSchemeEnum.getColorScheme().getExtraLightColor());
        this.colorPanels.get(ColorKind.ULTRA_LIGHT).setColor(
                colorSchemeEnum.getColorScheme().getUltraLightColor());

        repaint();
    }

    public ThemeEditor() {
        super("Theme editor");

        ColorSchemeEnum[] cse = new ColorSchemeEnum[ColorSchemeEnum.values()
                .length - 1];
        int count = 0;
        for (ColorSchemeEnum e : ColorSchemeEnum.values()) {
            if (e != ColorSchemeEnum.USER_DEFINED) {
                cse[count++] = e;
            }
        }
        this.themeCombobox = new JComboBox(cse);
        this.themeCombobox.setSelectedItem(ColorSchemeEnum.AQUA);
        this.themeCombobox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                syncColorsFromColorSchemeEnum((ColorSchemeEnum) themeCombobox
                        .getSelectedItem());
            }
        });
        this.isDarkCheckBox = new JCheckBox("theme is dark");
        this.isDarkCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        this.colorPanels = new HashMap<ColorKind, ColorInfoPanel>();
        this.previewPanel = new PreviewPanel(this);

        this.setLayout(new GridLayout(2, 1));

        JPanel topPanel = new JPanel(new GridLayout(
                1 + ColorKind.values().length, 1));
        JPanel comboPanel = new JPanel();

        comboPanel.add(this.themeCombobox);
        comboPanel.add(this.isDarkCheckBox);
        topPanel.add(comboPanel);

        ColorSchemeEnum defaultEnum = ColorSchemeEnum.AQUA;
        // this.syncColorsFromColorSchemeEnum(defaultEnum);
        ColorInfoPanel foreCip = new ColorInfoPanel(this, ColorKind.FOREGROUND,
                defaultEnum.getColorScheme().getForegroundColor());
        ColorInfoPanel udCip = new ColorInfoPanel(this, ColorKind.ULTRA_DARK,
                defaultEnum.getColorScheme().getUltraDarkColor());
        ColorInfoPanel dCip = new ColorInfoPanel(this, ColorKind.DARK,
                defaultEnum.getColorScheme().getDarkColor());
        ColorInfoPanel mCip = new ColorInfoPanel(this, ColorKind.MID,
                defaultEnum.getColorScheme().getMidColor());
        ColorInfoPanel lCip = new ColorInfoPanel(this, ColorKind.LIGHT,
                defaultEnum.getColorScheme().getLightColor());
        ColorInfoPanel elCip = new ColorInfoPanel(this, ColorKind.EXTRA_LIGHT,
                defaultEnum.getColorScheme().getExtraLightColor());
        ColorInfoPanel ulCip = new ColorInfoPanel(this, ColorKind.ULTRA_LIGHT,
                defaultEnum.getColorScheme().getUltraLightColor());

        this.colorPanels.put(ColorKind.FOREGROUND, foreCip);
        this.colorPanels.put(ColorKind.ULTRA_DARK, udCip);
        this.colorPanels.put(ColorKind.DARK, dCip);
        this.colorPanels.put(ColorKind.MID, mCip);
        this.colorPanels.put(ColorKind.LIGHT, lCip);
        this.colorPanels.put(ColorKind.EXTRA_LIGHT, elCip);
        this.colorPanels.put(ColorKind.ULTRA_LIGHT, ulCip);

        topPanel.add(foreCip);
        topPanel.add(udCip);
        topPanel.add(dCip);
        topPanel.add(mCip);
        topPanel.add(lCip);
        topPanel.add(elCip);
        topPanel.add(ulCip);

        this.add(topPanel);

        this.add(previewPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        syncColorsFromColorSchemeEnum(defaultEnum);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new SubstanceLookAndFeel());
        }
        catch (Exception exc) {
        }
        ThemeEditor editor = new ThemeEditor();
        editor.pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        // center the frame in the physical screen
        editor.setLocation((d.width - editor.getPreferredSize().width) / 2,
                (d.height - editor.getPreferredSize().height) / 2);
        editor.setVisible(true);
    }

}
