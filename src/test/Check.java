package test;

import org.jvnet.substance.color.CaribbeanBlueColorScheme;
import org.jvnet.substance.watermark.SubstanceCoffeeBeansWatermark;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.theme.SubstanceTheme;
import org.jvnet.substance.watermark.SubstanceWatermark;

public class Check extends JFrame {
	
	private boolean toUseThemeObjs;
	private boolean toUseWatermarksObjs;

	public Check() {
		super("Substance ");

this.setIconImage(SubstanceImageCreator.getIconHexaMarker(6,SubstanceLookAndFeel.getColorScheme()));
		this.setLayout(new BorderLayout());
		
	
		this.setPreferredSize(new Dimension(400, 400));
		this.setSize(this.getPreferredSize());
		this.setMinimumSize(this.getPreferredSize());

		JPanel themesPanel = new JPanel();
		themesPanel.setLayout(new FlowLayout());
		Vector<String> themeVector = new Vector<String>();

		Map<String, String> allThemes = SubstanceLookAndFeel.enumerateThemes();
		for (Map.Entry<String, String> themeEntry : allThemes.entrySet()) {
			final String themeClassName = themeEntry.getValue();
			themeVector.add(themeClassName);
		}
		
		JMenuBar jmb = new JMenuBar();
		JMenu jmTheme = new JMenu("Theme");

		ButtonGroup bgTheme = new ButtonGroup();
		for (Map.Entry<String, String> themeEntry : allThemes.entrySet()) {
			String themeName = themeEntry.getKey();
			final String themeClassName = themeEntry.getValue();

			JRadioButtonMenuItem jmiTheme = new JRadioButtonMenuItem(themeName);
			jmiTheme.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (toUseThemeObjs) {
						try {
		SubstanceLookAndFeel.setCurrentTheme((SubstanceTheme) Class.forName(themeClassName).newInstance());
						} catch (Exception exc) {
							exc.printStackTrace();
						}
					} else {
		SubstanceLookAndFeel.setCurrentTheme(themeClassName);
					}
					SwingUtilities.updateComponentTreeUI(getRootPane());
				}
			});
			if (themeName.equals(SubstanceLookAndFeel.getCurrentThemeName())) {
				jmiTheme.setSelected(true);
			}
			bgTheme.add(jmiTheme);
			jmTheme.add(jmiTheme);
		}

		JRadioButtonMenuItem jmiBottleGreenTheme = new JRadioButtonMenuItem("User - Caribbean blue");
		jmiBottleGreenTheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
	SubstanceLookAndFeel.setCurrentTheme(new SubstanceTheme(new CaribbeanBlueColorScheme(), false,"Caribbean blue"));
				} catch (Exception exc) {
					exc.printStackTrace();
				}
				SwingUtilities.updateComponentTreeUI(getRootPane());
				
			}
		});
		if ("Caribbean blue".equals(SubstanceLookAndFeel.getCurrentThemeName())) {
			jmiBottleGreenTheme.setSelected(true);
		}
		bgTheme.add(jmiBottleGreenTheme);
		jmTheme.add(jmiBottleGreenTheme);

		JRadioButtonMenuItem jmiRaspberryTheme = new JRadioButtonMenuItem("User - Crimson");
		jmiRaspberryTheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
	SubstanceLookAndFeel.setCurrentTheme(new SubstanceTheme(new CrimsonColorScheme(), false, "Crimson"));
				} catch (Exception exc) {
					exc.printStackTrace();
				}
				SwingUtilities.updateComponentTreeUI(getRootPane());
				
			}
		});
		if ("Crimson".equals(SubstanceLookAndFeel.getCurrentThemeName())) {jmiRaspberryTheme.setSelected(true);
		}
		bgTheme.add(jmiRaspberryTheme);
		jmTheme.add(jmiRaspberryTheme);
		jmb.add(jmTheme);
		
                JMenu jmWatermark = new JMenu("Watermark");
		ButtonGroup bgWatermark = new ButtonGroup();
		Map<String, String> allWatermarks = SubstanceLookAndFeel.enumerateWatermarks();
		for (Map.Entry<String, String> watermarkEntry : allWatermarks.entrySet()) {
			String watermarkName = watermarkEntry.getKey();
			final String watermarkClassName = watermarkEntry.getValue();
			JRadioButtonMenuItem jmiWatermark = new JRadioButtonMenuItem(watermarkName);
			jmiWatermark.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (toUseWatermarksObjs) {
						try {
SubstanceLookAndFeel.setCurrentWatermark((SubstanceWatermark) Class.forName(watermarkClassName).newInstance());
						} catch (Exception exc) {
							exc.printStackTrace();
						}
					} else {
			SubstanceLookAndFeel.setCurrentWatermark(watermarkClassName);
					}
					SwingUtilities.updateComponentTreeUI(getRootPane());
				}
			});
			if (watermarkName.equals(SubstanceLookAndFeel
					.getCurrentWatermarkName())) {
				jmiWatermark.setSelected(true);
			}
			bgWatermark.add(jmiWatermark);
			jmWatermark.add(jmiWatermark);
		}


		JRadioButtonMenuItem jmiCoffeeBeansWatermark = new JRadioButtonMenuItem("Coffee Beans");
		jmiCoffeeBeansWatermark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
			SubstanceLookAndFeel.setCurrentWatermark(new SubstanceCoffeeBeansWatermark());
				} catch (Exception exc) {
					exc.printStackTrace();
				}
				SwingUtilities.updateComponentTreeUI(getRootPane());
			}
		});
		if ("Coffee Beans".equals(SubstanceLookAndFeel.getCurrentWatermarkName())) {
			jmiCoffeeBeansWatermark.setSelected(true);
		}
		bgWatermark.add(jmiCoffeeBeansWatermark);
		jmWatermark.add(jmiCoffeeBeansWatermark);
		jmb.add(jmWatermark);
		this.setJMenuBar(jmb);

	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("org.jvnet.substance.SubstanceLookAndFeel");
			// UIManager
			// .setLookAndFeel("com.oyoaha.swing.plaf.oyoaha.OyoahaLookAndFeel");
			// UIManager.setLookAndFeel(new SubstanceLookAndFeel());
			// // UIManager.setLookAndFeel(new SubstanceLookAndFeel(
			// // new SubstancePurpleTheme()));
			// // SubstanceLookAndFeel.setCurrentTheme(new
			// SubstancePurpleTheme());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
	
		Check c = new Check();
		c.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				((JFrame) e.getComponent()).getRootPane().repaint();
			}
		});
		c.setPreferredSize(new Dimension(650, 500));
		c.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		// center the frame in the physical screen
		c.setLocation((d.width - c.getWidth()) / 2,(d.height - c.getHeight()) / 2);
		c.setVisible(true);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
