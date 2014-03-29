package test;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.lang.ref.*;
import java.text.DecimalFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jvnet.substance.SubstanceImageCreator;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.color.ColorSchemeEnum;
import org.jvnet.substance.comp.JButtonStrip;
import org.jvnet.substance.comp.JButtonStrip.StripOrientation;
import org.jvnet.substance.theme.SubstanceTheme;
import org.jvnet.substance.watermark.SubstanceWatermark;

public class Check extends JFrame {
	private JTabbedPane jtp;

	private static class NumberedPanel extends JPanel {
		private int number;

		public NumberedPanel(int number) {
			this.number = number;
		}

		@Override
		protected void paintComponent(Graphics g) {
			int w = this.getWidth();
			int h = this.getHeight();
			g.setColor(Color.white);
			g.fillRect(0, 0, w, h);
			g.setColor(Color.black);
			int size = Math.min(20, Math.min(w, h) / 2);
			g.setFont(new Font("Arial", Font.BOLD, size));
			g.drawString("" + number, (w - size) / 2, (h - size) / 2);
		}
	}

	private static class TabPlacementListener implements ActionListener {
		private JTabbedPane jtp;

		private int side;

		public TabPlacementListener(JTabbedPane jtp, int side) {
			this.jtp = jtp;
			this.side = side;
		}

		public void actionPerformed(ActionEvent e) {
			jtp.setTabPlacement(side);
		}
	}

	private static class SimpleDialog extends JDialog {
		public JButton b1;

		public SimpleDialog() {
			this.setLayout(new FlowLayout());
			b1 = new JButton("default");
			JButton b2 = new JButton("disabled");
			b2.setEnabled(false);
			JButton b3 = new JButton("regular");
			this.add(b1);
			this.add(b2);
			this.add(b3);

			this.getRootPane().setDefaultButton(b1);
			this.setTitle("Simple dialog");
		}
	}

	private static class ColorPanel extends JPanel {
		private String str;

		public ColorPanel(String str) {
			this.str = str;
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.black);
			Color bg = this.getBackground();
			g.drawString(this.str + " [" + bg.getRed() + ", " + bg.getGreen()
					+ ", " + bg.getBlue() + "]", 10, this.getHeight() / 2);
		}
	}

	private static class ScrollPanel extends JPanel {
		private JScrollPane sp;

		private JPanel panel;

		public ScrollPanel() {
			this.panel = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					int w = this.getWidth();
					int h = this.getHeight();

					int cols = 1 + w / 10;
					int rows = 1 + h / 10;
					g.setColor(Color.white);
					g.fillRect(0, 0, w, h);
					g.setColor(new Color(240, 240, 240));
					for (int i = 0; i < cols; i++) {
						for (int j = 0; j < rows; j++) {
							if (((i + j) % 2) == 0) {
								g.fillRect(i * 10, j * 10, 10, 10);
							}
						}
					}
				}
			};
			this.panel.setPreferredSize(new Dimension(800, 800));
			this.panel.setSize(this.getPreferredSize());
			this.panel.setMinimumSize(this.getPreferredSize());
			this.sp = new JScrollPane(this.panel,
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			this.setLayout(new BorderLayout());
			this.add(this.sp, BorderLayout.CENTER);

			JPanel buttons = new JPanel();
			buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
			JButton bt = new JButton("toggle enable / disable");
			bt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sp.setEnabled(!sp.isEnabled());
					updateEnabledState(sp, sp.isEnabled());
					System.out.println("Scroll pane is " + sp.isEnabled());
				}
			});
			buttons.add(bt);

			this.add(buttons, BorderLayout.SOUTH);
		}

		private void updateEnabledState(Container c, boolean enabled) {
			for (int counter = c.getComponentCount() - 1; counter >= 0; counter--) {
				Component child = c.getComponent(counter);

				child.setEnabled(enabled);
				if (child instanceof Container) {
					updateEnabledState((Container) child, enabled);
				}
			}
		}

	}

	private static class TabPanel extends JPanel {
		private JTabbedPane jtp;

		public TabPanel() {
			this.setLayout(new BorderLayout());
			jtp = new JTabbedPane();
			jtp.addTab("tab1", new NumberedPanel(1));
			jtp.addTab("tab2", new NumberedPanel(2));
			jtp.addTab("tab3", new NumberedPanel(3));
			jtp.addTab("tab4", new NumberedPanel(4));
			jtp.setEnabledAt(1, false);
			jtp.setEnabledAt(2, false);
			this.add(jtp, BorderLayout.CENTER);

			JPanel buttons = new JPanel();
			buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
			JButton bt = new JButton("top");
			bt
					.addActionListener(new TabPlacementListener(jtp,
							JTabbedPane.TOP));
			JButton bb = new JButton("bottom");
			bb.addActionListener(new TabPlacementListener(jtp,
					JTabbedPane.BOTTOM));
			JButton bl = new JButton("left");
			bl
					.addActionListener(new TabPlacementListener(jtp,
							JTabbedPane.LEFT));
			JButton br = new JButton("right");
			br.addActionListener(new TabPlacementListener(jtp,
					JTabbedPane.RIGHT));

			buttons.add(bt);
			buttons.add(bb);
			buttons.add(bl);
			buttons.add(br);

			JButton ba = new JButton("add tab");
			ba.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int count = 1 + jtp.getTabCount();
					jtp.addTab("tab" + count, new NumberedPanel(count));
				}
			});
			buttons.add(ba);

			JButton bp = new JButton("change policy");
			bp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int policy = jtp.getTabLayoutPolicy();
					int newPolicy = (policy == JTabbedPane.WRAP_TAB_LAYOUT) ? JTabbedPane.SCROLL_TAB_LAYOUT
							: JTabbedPane.WRAP_TAB_LAYOUT;
					jtp.setTabLayoutPolicy(newPolicy);
				}
			});
			buttons.add(bp);

			this.add(buttons, BorderLayout.SOUTH);

			this.setPreferredSize(new Dimension(400, 400));
			this.setSize(this.getPreferredSize());
			this.setMinimumSize(this.getPreferredSize());
		}
	}

	private static class SplitPanel extends JPanel {
		private JSplitPane jsp;

		public SplitPanel() {
			this.setLayout(new BorderLayout());
			jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					new NumberedPanel(1), new NumberedPanel(2));
			jsp.setOneTouchExpandable(true);
			jsp.setDividerLocation(100);
			this.add(jsp, BorderLayout.CENTER);

			JPanel buttons = new JPanel();
			buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
			JButton bt = new JButton("one-touch");
			bt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jsp.setOneTouchExpandable(!jsp.isOneTouchExpandable());
				}
			});

			JButton bb = new JButton("dir");
			bb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (jsp.getOrientation() == JSplitPane.VERTICAL_SPLIT) {
						jsp.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
					} else {
						jsp.setOrientation(JSplitPane.VERTICAL_SPLIT);
					}
				}
			});

			buttons.add(bt);
			buttons.add(bb);

			this.add(buttons, BorderLayout.SOUTH);

			this.setPreferredSize(new Dimension(400, 400));
			this.setSize(this.getPreferredSize());
			this.setMinimumSize(this.getPreferredSize());
		}
	}

	private static class DesktopPanel extends JPanel {
		private JDesktopPane jdp;

		private int count = 0;

		public DesktopPanel() {
			this.setLayout(new BorderLayout());
			jdp = new JDesktopPane();
			this.add(jdp, BorderLayout.CENTER);

			JPanel buttons = new JPanel();
			buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
			JButton bt = new JButton("add");
			bt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String title = "[" + count + "]Internal title ";
					int c = (int) (20 * Math.random());
					for (int i = 0; i < c; i++) {
						title += "0";
					}
					JInternalFrame jif = new JInternalFrame(title);
					jif.setFrameIcon(new ImageIcon(SubstanceImageCreator
							.getIconHexaMarker(count, SubstanceLookAndFeel
									.getColorScheme())));
					jif.setLayout(new FlowLayout());
					int comps = 5 + (int) (10 * Math.random());
					for (int i = 0; i < comps; i++) {
						double r = Math.random();
						if (r < 0.1) {
							jif.add(new JButton("button" + i));
						} else {
							if (r < 0.2) {
								jif.add(new JLabel("label" + i));
							} else {
								if (r < 0.3) {
									jif.add(new JRadioButton("radio" + i));
								} else {
									if (r < 0.4) {
										jif.add(new JCheckBox("check" + i));
									} else {
										if (r < 0.5) {
											jif.add(new JToggleButton("toggle"
													+ i));
										} else {
											if (r < 0.6) {
												jif.add(new JComboBox(
														new Object[] { "combo"
																+ i }));
											} else {
												if (r < 0.7) {
													jif.add(new JTextField(
															"text field" + i));
												} else {
													if (r < 0.8) {
														jif
																.add(new JPasswordField(
																		"password"
																				+ i));
													} else {
														if (r < 0.9) {
															jif
																	.add(new JSpinner());
														} else {
															jif
																	.add(new JList(
																			new Object[] { "list"
																					+ i }));
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
					count++;
					int width = 50 + (int) (400 * Math.random());
					int height = 50 + (int) (200 * Math.random());
					jif.setBounds(20 * (count % 10), 20 * (count % 10), width,
							height);
					jif.setBackground(new Color(128 + (int) (128 * Math
							.random()), 128 + (int) (128 * Math.random()),
							128 + (int) (128 * Math.random())));
					jif.setClosable(true);
					jif.setMaximizable(true);
					jif.setResizable(true);
					jif.setIconifiable(true);
					jdp.add(jif, 1);
					jif.show();
				}
			});

			buttons.add(bt);

			JButton minAll = new JButton("Minimize all");
			minAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (JInternalFrame jif : jdp.getAllFrames()) {
						try {
							jif.setIcon(true);
						} catch (PropertyVetoException pve) {
						}
					}
				}
			});
			buttons.add(minAll);

			this.add(buttons, BorderLayout.SOUTH);

			this.setPreferredSize(new Dimension(400, 400));
			this.setSize(this.getPreferredSize());
			this.setMinimumSize(this.getPreferredSize());
		}
	}

	private static class TablePanel extends JPanel {
		private JTable table;

		private static class MyTableRenderer extends JLabel implements
				TableCellRenderer {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Color color = (Color) value;
				this.setForeground(color);
				this.setText("row " + row);
				return this;
			}
		}

		public TablePanel() {
			TableModel dataModel = new AbstractTableModel() {
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					if (columnIndex == 2) {
						return Boolean.class;
					}
					if (columnIndex == 3) {
						return Byte.class;
					}
					if (columnIndex == 4) {
						return Float.class;
					}
					if (columnIndex == 5) {
						return Double.class;
					}
					if (columnIndex == 7) {
						return Date.class;
					}
					if (columnIndex == 8) {
						return ImageIcon.class;
					}
					if (columnIndex == 9) {
						return Color.class;
					}
					return String.class;
				}

				public int getColumnCount() {
					return 10;
				}

				public int getRowCount() {
					return 10;
				}

				public Object getValueAt(int row, int col) {
					if (col == 2) {
						return new Boolean(row % 2 == 0);
					}
					if (col == 3) {
						return new Byte((byte) row);
					}
					if (col == 4) {
						return new Float((float) row);
					}
					if (col == 5) {
						return new Double((double) row);
					}
					if (col == 7) {
						Calendar cal = Calendar.getInstance();
						cal.set(2000 + row, 1 + row, 1 + row);
						return cal.getTime();
					}
					if (col == 8) {
						return SubstanceImageCreator.getHexaMarker(6,
								ColorSchemeEnum.values()[row + 4]);
					}
					if (col == 9) {
						int comp = row * 20;
						return new Color(comp, comp, comp);
					}
					return new String("cell " + row + ":" + col);
				}

				@Override
				public String getColumnName(int column) {
					return this.getColumnClass(column).getSimpleName();
				}

				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return (rowIndex % 2 == 0);
				}
			};
			// TableColumnModel columnModel = new DefaultTableColumnModel() {
			// @Override public int getColumnCount() {
			// return 10;
			// }
			//
			// @Override public TableColumn getColumn(int columnIndex) {
			// return new TableColumn(
			// }
			// };
			this.table = new JTable(dataModel);
			this.table.setDefaultRenderer(Color.class, new MyTableRenderer());
			JScrollPane tableScrollpane = new JScrollPane(this.table);

			// JTable table = new JTable(
			// new String[][]{{"cell11", "cell12", "cell13", "cell14"},
			// {"cell21", "cell22", "cell23", "cell24"},
			// {"cell31", "cell32", "cell33", "cell34"},
			// {"cell41", "cell42", "cell43", "cell44"}},
			// new String[]{"column1", "column2", "column3", "column4"});

			this.table
					.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			ListSelectionModel rowSM = this.table.getSelectionModel();
			rowSM.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					// Ignore extra messages.
					if (e.getValueIsAdjusting()) {
						return;
					}

					ListSelectionModel lsm = (ListSelectionModel) e.getSource();
					if (lsm.isSelectionEmpty()) {
						System.out.println("No rows are selected.");
					} else {
						int selectedRow = lsm.getMinSelectionIndex();
						System.out.println("Row " + selectedRow
								+ " is now selected.");
					}
				}
			});
			// We allow both row and column selection, which
			// implies that we *really* want to allow individual
			// cell selection.
			this.table.setCellSelectionEnabled(true);
			this.table.setColumnSelectionAllowed(true);
			ListSelectionModel colSM = this.table.getColumnModel()
					.getSelectionModel();
			colSM.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					// Ignore extra messages.
					if (e.getValueIsAdjusting()) {
						return;
					}

					ListSelectionModel lsm = (ListSelectionModel) e.getSource();
					if (lsm.isSelectionEmpty()) {
						System.out.println("No columns are selected.");
					} else {
						int selectedCol = lsm.getMinSelectionIndex();
						System.out.println("Column " + selectedCol
								+ " is now selected.");
					}
				}
			});

			this.table.setShowGrid(true);
			this.table.setDragEnabled(false);
			this.table.setTableHeader(new JTableHeader(this.table
					.getColumnModel()));

			this.setLayout(new BorderLayout());
			this.add(tableScrollpane, BorderLayout.CENTER);

			JPanel buttons = new JPanel();
			buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
			JButton btv = new JButton("toggle V");
			btv.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					table.setShowVerticalLines(!table.getShowVerticalLines());
				}
			});
			buttons.add(btv);
			JButton bth = new JButton("toggle H");
			bth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					table.setShowHorizontalLines(!table
							.getShowHorizontalLines());
				}
			});
			buttons.add(bth);

			this.add(buttons, BorderLayout.SOUTH);
		}
	}

	private static class MyListCellRenderer extends JLabel implements
			ListCellRenderer {

		public MyListCellRenderer() {
			super();
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			this.setText((String) value);
			this.setForeground(list.getForeground());

			int comp = 156 + 10 * (index % 9);
			this.setBackground(new Color(comp, comp, comp));

			return this;
		}
	}

	private static class ListPanel extends JPanel {
		public ListPanel() {
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

			ListModel lm1 = new DefaultListModel() {
				@Override
				public int getSize() {
					return 30;
				}

				@Override
				public Object getElementAt(int index) {
					return "element " + index;
				}
			};

			JList list1 = new JList(lm1);
			list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane jsp1 = new JScrollPane(list1);
			this.add(jsp1);

			JList list2 = new JList(lm1);
			list2
					.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			list2.setBackground(new Color(255, 128, 128));
			list2.setForeground(new Color(0, 0, 128));
			JScrollPane jsp2 = new JScrollPane(list2);
			this.add(jsp2);

			JList list3 = new JList(lm1);
			list3
					.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			list3.setCellRenderer(new MyListCellRenderer());
			JScrollPane jsp3 = new JScrollPane(list3);
			this.add(jsp3);

		}
	}

	private static class SliderPanel extends JPanel {
		public SliderPanel() {
			this.setLayout(new BorderLayout());

			JPanel sliders = new JPanel();

			GridLayout gLayout = new GridLayout(1, 2);
			gLayout.setHgap(5);
			gLayout.setVgap(5);
			sliders.setLayout(gLayout);

			JPanel horizPanel = new JPanel();
			horizPanel.setLayout(new BoxLayout(horizPanel, BoxLayout.Y_AXIS));
			sliders.add(horizPanel);

			JPanel vertPanel = new JPanel();
			vertPanel.setLayout(new BoxLayout(vertPanel, BoxLayout.X_AXIS));
			sliders.add(vertPanel);

			this.add(sliders, BorderLayout.CENTER);

			// Horizontal Slider 1
			JPanel plainHorizPanel = new JPanel();
			plainHorizPanel.setBorder(new TitledBorder("Plain"));
			JSlider plainHorizSlider = new JSlider(-10, 100, 20);
			plainHorizPanel.add(plainHorizSlider);
			horizPanel.add(plainHorizPanel);

			// Horizontal Slider 2
			JPanel majorHorizPanel = new JPanel();
			majorHorizPanel.setBorder(new TitledBorder("Major ticks"));
			JSlider majorHorizSlider = new JSlider(100, 1000, 400);
			majorHorizSlider.setPaintTicks(true);
			majorHorizSlider.setMajorTickSpacing(100);
			majorHorizPanel.add(majorHorizSlider);
			horizPanel.add(majorHorizPanel);

			// Horizontal Slider 3
			JPanel minorHorizPanel = new JPanel();
			minorHorizPanel.setBorder(new TitledBorder("Minor ticks"));
			JSlider minorHorizSlider = new JSlider(0, 11, 6);
			minorHorizSlider.setPaintTicks(true);
			minorHorizSlider.setMajorTickSpacing(5);
			minorHorizSlider.setMinorTickSpacing(1);
			minorHorizSlider.setPaintLabels(true);
			minorHorizSlider.setSnapToTicks(true);
			minorHorizSlider.getLabelTable().put(new Integer(11),
					new JLabel(new Integer(11).toString(), JLabel.CENTER));
			minorHorizSlider.setLabelTable(minorHorizSlider.getLabelTable());
			minorHorizPanel.add(minorHorizSlider);
			horizPanel.add(minorHorizPanel);

			// Horizontal Slider 4
			JPanel disabledHorizPanel = new JPanel();
			disabledHorizPanel.setBorder(new TitledBorder("Disabled"));
			BoundedRangeModel brm = new DefaultBoundedRangeModel(80, 0, 0, 100);
			JSlider disabledHorizSlider = new JSlider(brm);
			disabledHorizSlider.setPaintTicks(true);
			disabledHorizSlider.setMajorTickSpacing(20);
			disabledHorizSlider.setMinorTickSpacing(5);
			disabledHorizSlider.setEnabled(false);
			disabledHorizPanel.add(disabledHorizSlider);
			horizPanel.add(disabledHorizPanel);

			// Vertical Slider 1
			JPanel plainVertPanel = new JPanel();
			plainVertPanel.setBorder(new TitledBorder("Plain"));
			JSlider plainVertSlider = new JSlider(JSlider.VERTICAL, -10, 100,
					20);
			plainVertPanel.add(plainVertSlider);
			vertPanel.add(plainVertPanel);

			// Vertical Slider 2
			JPanel majorVertPanel = new JPanel();
			majorVertPanel.setBorder(new TitledBorder("Major ticks"));
			JSlider majorVertSlider = new JSlider(JSlider.VERTICAL, 100, 1000,
					400);
			majorVertSlider.setPaintTicks(true);
			majorVertSlider.setMajorTickSpacing(100);
			majorVertPanel.add(majorVertSlider);
			vertPanel.add(majorVertPanel);

			// Vertical Slider 3
			JPanel minorVertPanel = new JPanel();
			minorVertPanel.setBorder(new TitledBorder("Minor ticks"));
			JSlider minorVertSlider = new JSlider(JSlider.VERTICAL, 0, 100, 60);
			minorVertSlider.setPaintTicks(true);
			minorVertSlider.setMajorTickSpacing(20);
			minorVertSlider.setMinorTickSpacing(5);
			minorVertSlider.setPaintLabels(true);
			minorVertPanel.add(minorVertSlider);
			vertPanel.add(minorVertPanel);

			// Vertical Slider 4
			JPanel disabledVertPanel = new JPanel();
			disabledVertPanel.setBorder(new TitledBorder("Disabled"));
			JSlider disabledVertSlider = new JSlider(JSlider.VERTICAL, 0, 100,
					80);
			disabledVertSlider.setPaintTicks(true);
			disabledVertSlider.setMajorTickSpacing(20);
			disabledVertSlider.setMinorTickSpacing(5);
			disabledVertSlider.setEnabled(false);
			disabledVertPanel.add(disabledVertSlider);
			vertPanel.add(disabledVertPanel);
		}
	}

	private static class ProgressBarPanel extends JPanel {
		private JProgressBar determinateEnHor;

		private JProgressBar indeterminateEnHor;

		private JProgressBar determinateDisHor;

		private JProgressBar indeterminateDisHor;

		private JProgressBar determinateEnVer;

		private JProgressBar indeterminateEnVer;

		private JProgressBar determinateDisVer;

		private JProgressBar indeterminateDisVer;

		private java.util.Timer timer;

		private TimerTask timerTask;

		private JButton startButton;

		private JButton stopButton;

		public ProgressBarPanel() {
			this.setLayout(new BorderLayout());

			JPanel bars = new JPanel();
			GridLayout gLayout = new GridLayout(1, 2);
			gLayout.setHgap(5);
			gLayout.setVgap(5);
			bars.setLayout(gLayout);

			JPanel horizPanel = new JPanel();
			horizPanel.setLayout(new BoxLayout(horizPanel, BoxLayout.Y_AXIS));
			bars.add(horizPanel);

			JPanel vertPanel = new JPanel();
			vertPanel.setLayout(new BoxLayout(vertPanel, BoxLayout.X_AXIS));
			bars.add(vertPanel);

			this.determinateEnHor = new JProgressBar(JProgressBar.HORIZONTAL,
					0, 100);
			this.determinateEnHor.setIndeterminate(false);
			JPanel dehPanel = new JPanel();
			dehPanel.add(this.determinateEnHor);
			dehPanel.setBorder(new TitledBorder("Determ enabled"));
			this.indeterminateEnHor = new JProgressBar(JProgressBar.HORIZONTAL,
					0, 100);
			this.indeterminateEnHor.setIndeterminate(true);
			JPanel iehPanel = new JPanel();
			iehPanel.add(this.indeterminateEnHor);
			iehPanel.setBorder(new TitledBorder("Indeterm enabled"));

			horizPanel.add(dehPanel);
			horizPanel.add(iehPanel);

			this.determinateDisHor = new JProgressBar(JProgressBar.HORIZONTAL,
					0, 100);
			this.determinateDisHor.setIndeterminate(false);
			this.determinateDisHor.setEnabled(false);
			JPanel ddhPanel = new JPanel();
			ddhPanel.add(this.determinateDisHor);
			ddhPanel.setBorder(new TitledBorder("Determ disabled"));
			this.indeterminateDisHor = new JProgressBar(
					JProgressBar.HORIZONTAL, 0, 100);
			this.indeterminateDisHor.setIndeterminate(true);
			this.indeterminateDisHor.setEnabled(false);
			JPanel idhPanel = new JPanel();
			idhPanel.add(this.indeterminateDisHor);
			idhPanel.setBorder(new TitledBorder("Indeterm disabled"));

			horizPanel.add(ddhPanel);
			horizPanel.add(idhPanel);

			this.determinateEnVer = new JProgressBar(JProgressBar.VERTICAL, 0,
					100);
			this.determinateEnVer.setIndeterminate(false);
			JPanel devPanel = new JPanel();
			devPanel.add(this.determinateEnVer);
			devPanel.setBorder(new TitledBorder("Determ enabled"));
			this.indeterminateEnVer = new JProgressBar(JProgressBar.VERTICAL,
					0, 100);
			this.indeterminateEnVer.setIndeterminate(true);
			JPanel ievPanel = new JPanel();
			ievPanel.add(this.indeterminateEnVer);
			ievPanel.setBorder(new TitledBorder("Indeterm enabled"));

			vertPanel.add(devPanel);
			vertPanel.add(ievPanel);

			this.determinateDisVer = new JProgressBar(JProgressBar.VERTICAL, 0,
					100);
			this.determinateDisVer.setIndeterminate(false);
			this.determinateDisVer.setEnabled(false);
			JPanel ddvPanel = new JPanel();
			ddvPanel.add(this.determinateDisVer);
			ddvPanel.setBorder(new TitledBorder("Determ disabled"));
			this.indeterminateDisVer = new JProgressBar(JProgressBar.VERTICAL,
					0, 100);
			this.indeterminateDisVer.setIndeterminate(true);
			this.indeterminateDisVer.setEnabled(false);
			JPanel idvPanel = new JPanel();
			idvPanel.add(this.indeterminateDisVer);
			idvPanel.setBorder(new TitledBorder("Indeterm disabled"));

			vertPanel.add(ddvPanel);
			vertPanel.add(idvPanel);

			this.add(bars, BorderLayout.CENTER);

			JPanel buttons = new JPanel();
			buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
			this.startButton = new JButton("start");
			this.startButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					timerTask = new TimerTask() {
						@Override
						public void run() {
							if (determinateEnHor.getValue() < determinateEnHor
									.getMaximum()) {
								determinateEnHor.setValue(determinateEnHor
										.getValue() + 1);
							}
							if (determinateDisHor.getValue() < determinateDisHor
									.getMaximum()) {
								determinateDisHor.setValue(determinateDisHor
										.getValue() + 1);
							}
							if (determinateEnVer.getValue() < determinateEnVer
									.getMaximum()) {
								determinateEnVer.setValue(determinateEnVer
										.getValue() + 1);
							}
							if (determinateDisVer.getValue() < determinateDisVer
									.getMaximum()) {
								determinateDisVer.setValue(determinateDisVer
										.getValue() + 1);
							}
						}
					};
					timer = new java.util.Timer();
					timer.schedule(timerTask, new Date(), 100);
					startButton.setEnabled(false);
					stopButton.setEnabled(true);
				}
			});

			this.stopButton = new JButton("stop");
			this.stopButton.setEnabled(false);
			this.stopButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					timerTask.cancel();
					timer.cancel();
					stopButton.setEnabled(false);
					startButton.setEnabled(true);
				}
			});

			JButton resetButton = new JButton("reset");
			resetButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					determinateEnHor.setValue(determinateEnHor.getMinimum());
					determinateDisHor.setValue(determinateDisHor.getMinimum());
					determinateEnVer.setValue(determinateEnVer.getMinimum());
					determinateDisVer.setValue(determinateDisVer.getMinimum());
				}
			});

			buttons.add(this.startButton);
			buttons.add(this.stopButton);
			buttons.add(resetButton);

			this.add(buttons, BorderLayout.SOUTH);

		}
	}

	private static class SpinnerPanel extends JPanel {
		public SpinnerPanel() {
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

			JPanel enabledPanel = new JPanel();
			enabledPanel
					.setLayout(new BoxLayout(enabledPanel, BoxLayout.Y_AXIS));

			JPanel basicEnPanel = new JPanel();
			basicEnPanel.setBorder(new TitledBorder("Basic Spinner"));
			JSpinner basicEnSpinner = new JSpinner();
			basicEnPanel.add(basicEnSpinner);
			enabledPanel.add(basicEnPanel);

			JPanel dateEnPanel = new JPanel();
			dateEnPanel.setBorder(new TitledBorder("Date Spinner"));
			JSpinner dateEnSpinner = new JSpinner(new SpinnerDateModel());
			dateEnPanel.add(dateEnSpinner);
			enabledPanel.add(dateEnPanel);

			String weekdaysEn[] = new String[] { "Sunday", "Monday", "Tuesday",
					"Wednesday", "Thursday", "Friday", "Saturday" };
			JPanel listEnPanel = new JPanel();
			listEnPanel.setBorder(new TitledBorder("List Spinner"));
			JSpinner listEnSpinner = new JSpinner(new SpinnerListModel(
					weekdaysEn));
			listEnPanel.add(listEnSpinner);
			enabledPanel.add(listEnPanel);

			JPanel numberEnPanel = new JPanel();
			numberEnPanel.setBorder(new TitledBorder("Number Spinner"));
			JSpinner numberEnSpinner = new JSpinner(new SpinnerNumberModel(0,
					0, 100, 5));
			numberEnPanel.add(numberEnSpinner);
			enabledPanel.add(numberEnPanel);

			basicEnSpinner.setPreferredSize(dateEnSpinner.getPreferredSize());
			listEnSpinner.setPreferredSize(dateEnSpinner.getPreferredSize());
			numberEnSpinner.setPreferredSize(dateEnSpinner.getPreferredSize());

			this.add(enabledPanel);

			JPanel disabledPanel = new JPanel();
			disabledPanel.setLayout(new BoxLayout(disabledPanel,
					BoxLayout.Y_AXIS));

			JPanel basicDisPanel = new JPanel();
			basicDisPanel.setBorder(new TitledBorder("Basic Spinner"));
			JSpinner basicDisSpinner = new JSpinner();
			basicDisSpinner.setEnabled(false);
			basicDisPanel.add(basicDisSpinner);
			disabledPanel.add(basicDisPanel);

			JPanel dateDisPanel = new JPanel();
			dateDisPanel.setBorder(new TitledBorder("Date Spinner"));
			JSpinner dateDisSpinner = new JSpinner(new SpinnerDateModel());
			dateDisSpinner.setEnabled(false);
			dateDisPanel.add(dateDisSpinner);
			disabledPanel.add(dateDisPanel);

			String weekdaysDis[] = new String[] { "Sunday", "Monday",
					"Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
			JPanel listDisPanel = new JPanel();
			listDisPanel.setBorder(new TitledBorder("List Spinner"));
			JSpinner listDisSpinner = new JSpinner(new SpinnerListModel(
					weekdaysDis));
			listDisSpinner.setEnabled(false);
			listDisPanel.add(listDisSpinner);
			disabledPanel.add(listDisPanel);

			JPanel numberDisPanel = new JPanel();
			numberDisPanel.setBorder(new TitledBorder("Number Spinner"));
			JSpinner numberDisSpinner = new JSpinner(new SpinnerNumberModel(0,
					0, 100, 5));
			numberDisSpinner.setEnabled(false);
			numberDisPanel.add(numberDisSpinner);
			disabledPanel.add(numberDisPanel);

			basicDisSpinner.setPreferredSize(dateDisSpinner.getPreferredSize());
			listDisSpinner.setPreferredSize(dateDisSpinner.getPreferredSize());
			numberDisSpinner
					.setPreferredSize(dateDisSpinner.getPreferredSize());

			this.add(disabledPanel);
		}
	}

	private static class TreePanel extends JPanel {
		private JTree tree;

		public TreePanel(boolean isEnabled) {
			this.setLayout(new BorderLayout());

			DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
			DefaultMutableTreeNode son1 = new DefaultMutableTreeNode("son1");
			DefaultMutableTreeNode son2 = new DefaultMutableTreeNode("son2");
			DefaultMutableTreeNode son3 = new DefaultMutableTreeNode("son3");
			DefaultMutableTreeNode gson11 = new DefaultMutableTreeNode("gson11");
			DefaultMutableTreeNode gson12 = new DefaultMutableTreeNode("gson12");
			DefaultMutableTreeNode gson21 = new DefaultMutableTreeNode("gson21");
			DefaultMutableTreeNode gson22 = new DefaultMutableTreeNode("gson22");
			DefaultMutableTreeNode gson31 = new DefaultMutableTreeNode("gson31");
			DefaultMutableTreeNode gson32 = new DefaultMutableTreeNode("gson32");
			DefaultMutableTreeNode ggson111 = new DefaultMutableTreeNode(
					"ggson11");
			DefaultMutableTreeNode ggson112 = new DefaultMutableTreeNode(
					"ggson11");
			DefaultMutableTreeNode ggson113 = new DefaultMutableTreeNode(
					"ggson11");

			gson11.add(ggson111);
			gson11.add(ggson112);
			gson11.add(ggson113);
			son1.add(gson11);
			son1.add(gson12);
			son2.add(gson21);
			son2.add(gson22);
			son3.add(gson31);
			son3.add(gson32);
			root.add(son1);
			root.add(son2);
			root.add(son3);

			this.tree = new JTree(root);
			this.tree.setEnabled(isEnabled);
			JScrollPane jsp = new JScrollPane(this.tree);
			this.add(jsp, BorderLayout.CENTER);

			JPanel buttons = new JPanel();
			buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
			JButton bt = new JButton("lines");
			bt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String val = (String) tree
							.getClientProperty("JTree.lineStyle");
					if ((val == null) || (val.compareTo("Angled") == 0)) {
						val = "None";
					} else {
						val = "Angled";
					}
					tree.putClientProperty("JTree.lineStyle", val);
					SwingUtilities.updateComponentTreeUI(tree);
				}
			});
			buttons.add(bt);
			this.add(buttons, BorderLayout.SOUTH);
		}
	}

	private static class CardPanel extends JPanel implements ItemListener {
		JPanel cards; // a panel that uses CardLayout

		final static String BUTTONPANEL = "JPanel with JButtons";

		final static String TEXTPANEL = "JPanel with JTextField";

		public CardPanel() {
			// Put the JComboBox in a JPanel to get a nicer look.
			JPanel comboBoxPane = new JPanel(); // use FlowLayout
			String comboBoxItems[] = { BUTTONPANEL, TEXTPANEL };
			JComboBox cb = new JComboBox(comboBoxItems);
			cb.setEditable(false);
			cb.addItemListener(this);
			comboBoxPane.add(cb);

			// Create the "cards".
			JPanel card1 = new JPanel();
			card1.add(new JButton("Button 1"));
			card1.add(new JButton("Button 2"));
			card1.add(new JButton("Button 3"));

			JPanel card2 = new JPanel();
			card2.add(new JTextField("TextField", 20));

			// Create the panel that contains the "cards".
			cards = new JPanel(new CardLayout());
			cards.add(card1, BUTTONPANEL);
			cards.add(card2, TEXTPANEL);

			this.add(comboBoxPane, BorderLayout.PAGE_START);
			this.add(cards, BorderLayout.CENTER);
		}

		public void itemStateChanged(ItemEvent evt) {
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, (String) evt.getItem());
		}
	}

	private SimpleDialog simpleDialog;

	private boolean toUseThemeObjs;

	private boolean toUseWatermarksObjs;

	// An inner class to check whether mouse events are the pop-up trigger
	private static class MousePopupListener extends MouseAdapter {
		private JPopupMenu popup;

		private JComponent owner;

		public MousePopupListener(JPopupMenu popup, JComponent owner) {
			this.popup = popup;
			this.owner = owner;
		}

		public void mousePressed(MouseEvent e) {
			checkPopup(e);
		}

		public void mouseClicked(MouseEvent e) {
			checkPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			checkPopup(e);
		}

		private void checkPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				popup.show(this.owner, e.getX(), e.getY());
			}
		}
	}

	private static class PopupPrintListener implements PopupMenuListener {
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			System.out.println("Popup menu will be visible!");
		}

		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			System.out.println("Popup menu will be invisible!");
		}

		public void popupMenuCanceled(PopupMenuEvent e) {
			System.out.println("Popup menu is hidden!");
		}
	}

	public Check() {
		super(
				"Substance test with very very very very very very very very long title");

		this.setIconImage(SubstanceImageCreator.getIconHexaMarker(6,
				SubstanceLookAndFeel.getColorScheme()));
		this.setLayout(new BorderLayout());

		this.jtp = new JTabbedPane();
		this.add(jtp, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton bt = new JButton("top");
		bt.addActionListener(new TabPlacementListener(jtp, JTabbedPane.TOP));
		JButton bb = new JButton("bottom");
		bb.addActionListener(new TabPlacementListener(jtp, JTabbedPane.BOTTOM));
		JButton bl = new JButton("left");
		bl.addActionListener(new TabPlacementListener(jtp, JTabbedPane.LEFT));
		JButton br = new JButton("right");
		br.addActionListener(new TabPlacementListener(jtp, JTabbedPane.RIGHT));

		buttons.add(bt);
		buttons.add(bb);
		buttons.add(bl);
		buttons.add(br);

		this.add(buttons, BorderLayout.SOUTH);

		this.setPreferredSize(new Dimension(400, 400));
		this.setSize(this.getPreferredSize());
		this.setMinimumSize(this.getPreferredSize());

		JPanel checkButtonPanel = new JPanel();
		checkButtonPanel.setLayout(new FlowLayout());
		JButton b1 = new JButton("default with tooltip");
		this.getRootPane().setDefaultButton(b1);
		b1.setToolTipText("Random tooltip text");
		JButton b2 = new JButton("disabled");
		b2.setEnabled(false);
		JButton b3 = new JButton("regular with popup");

		JPopupMenu popup = new JPopupMenu();
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Popup menu item ["
						+ event.getActionCommand() + "] was pressed.");
			}
		};
		JMenuItem item;
		popup.add(item = new JMenuItem("Left"));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(menuListener);
		popup.add(item = new JMenuItem("Center"));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(menuListener);
		popup.add(item = new JMenuItem("Right"));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(menuListener);
		popup.add(item = new JMenuItem("Full"));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(menuListener);
		popup.addSeparator();
		popup.add(item = new JMenuItem("Settings . . ."));
		item.addActionListener(menuListener);
		popup.setLabel("Justification");
		popup.addPopupMenuListener(new PopupPrintListener());
		b3.addMouseListener(new MousePopupListener(popup, b3));

		JButton b4 = new JButton("button with very long title");
		b4.setIcon(SubstanceImageCreator.getCloseIcon(ColorSchemeEnum.AQUA));
		JButton b5 = new JButton(
				"<html><u><font color=\"red\">HTML</font></u> <b>bold</b> <i>button</i>");
		JButton b6 = new JButton("another button with very long title");
		checkButtonPanel.add(b1);
		checkButtonPanel.add(b2);
		checkButtonPanel.add(b3);
		checkButtonPanel.add(b4);
		checkButtonPanel.add(b5);
		checkButtonPanel.add(b6);
		this.jtp.addTab("Buttons", checkButtonPanel);

		JPanel checkCheckBoxPanel = new JPanel();
		checkCheckBoxPanel.setLayout(new FlowLayout());
		JCheckBox cb1 = new JCheckBox("regular");
		JCheckBox cb2 = new JCheckBox("disabled");
		cb2.setEnabled(false);
		JCheckBox cb3 = new JCheckBox("disabled2");
		cb3.setEnabled(false);
		cb3.setSelected(true);
		checkCheckBoxPanel.add(cb1);
		checkCheckBoxPanel.add(cb2);
		checkCheckBoxPanel.add(cb3);
		this.jtp.addTab("Check boxes", checkCheckBoxPanel);

		JPanel checkComboBoxPanel = new JPanel();
		checkComboBoxPanel.setLayout(new BorderLayout());
		final JComboBox combo1 = new JComboBox(new Object[] { "entry1",
				"entry2", "entry3", "entry4", "entry5", "entry6" });
		combo1.setToolTipText("This is my combo 1");
		combo1.setMaximumRowCount(4);
		final JComboBox combo2 = new JComboBox(new Object[] { "entry1",
				"entry2", "entry3" });
		combo2.setEnabled(false);
		final JComboBox combo3 = new JComboBox(new Object[] { "entry31",
				"entry32", "entry33", "entry34", "entry35", "entry36" });
		combo3.setBackground(new Color(255, 128, 128));
		combo3.setForeground(new Color(0, 0, 128));
		JPanel combosPanel = new JPanel(new FlowLayout());
		combosPanel.add(combo1);
		combosPanel.add(combo2);
		combosPanel.add(combo3);
		checkComboBoxPanel.add(combosPanel, BorderLayout.CENTER);
		JPanel combosButtonsPanel = new JPanel(new FlowLayout());
		JButton disableCombosButton = new JButton("Disable all");
		disableCombosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				combo1.setEnabled(false);
				combo2.setEnabled(false);
				combo3.setEnabled(false);
			}
		});
		JButton enableCombosButton = new JButton("Enable all");
		enableCombosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				combo1.setEnabled(true);
				combo2.setEnabled(true);
				combo3.setEnabled(true);
			}
		});
		combosButtonsPanel.add(disableCombosButton);
		combosButtonsPanel.add(enableCombosButton);
		checkComboBoxPanel.add(combosButtonsPanel, BorderLayout.SOUTH);
		this.jtp.addTab("Combo box", checkComboBoxPanel);

		JPanel checkToggleButtonPanel = new JPanel();
		checkToggleButtonPanel.setLayout(new FlowLayout());
		ButtonGroup viewButtonGroup = new ButtonGroup();
		ButtonGroup viewButtonGroupDis = new ButtonGroup();

		Icon detailsViewIcon = UIManager.getIcon("FileChooser.detailsViewIcon");
		Icon listViewIcon = UIManager.getIcon("FileChooser.listViewIcon");

		JToggleButton listViewButton = new JToggleButton(listViewIcon);
		listViewButton.setSelected(true);
		listViewButton.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		listViewButton.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		checkToggleButtonPanel.add(listViewButton);
		viewButtonGroup.add(listViewButton);

		JToggleButton detailsViewButton = new JToggleButton(detailsViewIcon);
		detailsViewButton.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		detailsViewButton.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		checkToggleButtonPanel.add(detailsViewButton);
		viewButtonGroup.add(detailsViewButton);

		JToggleButton listViewButtonDis = new JToggleButton("text",
				listViewIcon);
		listViewButtonDis.setSelected(true);
		listViewButtonDis.setEnabled(false);
		listViewButtonDis.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		listViewButtonDis.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		checkToggleButtonPanel.add(listViewButtonDis);
		viewButtonGroupDis.add(listViewButtonDis);

		JToggleButton detailsViewButtonDis = new JToggleButton("text",
				detailsViewIcon);
		detailsViewButtonDis.setEnabled(false);
		detailsViewButtonDis.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		detailsViewButtonDis.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		checkToggleButtonPanel.add(detailsViewButtonDis);
		viewButtonGroupDis.add(detailsViewButtonDis);

		this.jtp.addTab("Toggle buttons", checkToggleButtonPanel);

		JPanel checkPulsePanel = new JPanel();
		checkPulsePanel.setLayout(new FlowLayout());
		JButton bd = new JButton("dialog");
		bd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDialog sd = new SimpleDialog();
				sd.setModal(false);
				sd.pack();
				Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
				// center the frame in the physical screen
				sd.setLocation((d.width - sd.getWidth()) / 2, (d.height - sd
						.getHeight()) / 2);
				sd.setVisible(true);
				simpleDialog = sd;
			}
		});
		checkPulsePanel.add(bd);
		JButton bcd = new JButton("close");
		bcd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (simpleDialog != null) {
					simpleDialog.removeAll();
					simpleDialog.dispose();
					ReferenceQueue<JButton> weakQueue = new ReferenceQueue<JButton>();
					WeakReference<JButton> weakRef = new WeakReference<JButton>(
							simpleDialog.b1, weakQueue);
					weakRef.enqueue();
					simpleDialog.b1 = null;
					simpleDialog = null;
					System.gc();
					// Wait until the weak reference is on the queue and remove
					// it
					System.out.println("Waiting to remove");
					try {
						Reference ref = weakQueue.remove();
						ref.clear();
					} catch (InterruptedException ie) {
						ie.printStackTrace();
						return;
					}
					System.out.println("Removed");
				}
			}
		});
		checkPulsePanel.add(bcd);
		this.jtp.addTab("Pulsating", checkPulsePanel);

		JPanel checkRadioButtonPanel = new JPanel();
		checkRadioButtonPanel.setLayout(new FlowLayout());
		JRadioButton rb1 = new JRadioButton("radio1");
		rb1.setSelected(true);
		JRadioButton rb2 = new JRadioButton("radio2");
		JRadioButton rb3 = new JRadioButton("radio3");
		rb3.setEnabled(false);
		ButtonGroup group = new ButtonGroup();
		group.add(rb1);
		group.add(rb2);
		group.add(rb3);
		checkRadioButtonPanel.add(rb1);
		checkRadioButtonPanel.add(rb2);
		checkRadioButtonPanel.add(rb3);

		JRadioButton rb4 = new JRadioButton("radio4");
		rb4.setSelected(true);
		rb4.setEnabled(false);
		checkRadioButtonPanel.add(rb4);
		this.jtp.addTab("Radio button", checkRadioButtonPanel);

		JPanel checkGlassPanePanel = new JPanel();
		checkGlassPanePanel.setLayout(new FlowLayout());
		JButton bgp = new JButton("Glass pane");
		bgp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JPanel glassPane = new JPanel() {
					@Override
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						Graphics2D graphics = (Graphics2D) g;
						int height = getHeight();
						int width = getWidth();
						Composite c = AlphaComposite.getInstance(
								AlphaComposite.SRC_OVER, (float) 0.4);
						graphics.setComposite(c);
						for (int i = 0; i < height; i++) {
							Color color = (i % 2 == 0) ? new Color(200, 200,
									255) : new Color(230, 230, 255);
							graphics.setColor(color);
							graphics.drawLine(0, i, width, i);
						}
						Composite c2 = AlphaComposite.getInstance(
								AlphaComposite.SRC_OVER, (float) 1.0);
						graphics.setComposite(c2);
					}
				};
				glassPane.setOpaque(false);
				glassPane.addMouseListener(new MouseAdapter() {
				});
				glassPane.addKeyListener(new KeyAdapter() {
				});
				setGlassPane(glassPane);
				new Thread() {
					public void run() {
						glassPane.setVisible(true);
						try {
							Thread.sleep(5000);
						} catch (InterruptedException ie) {
							ie.printStackTrace();
						}
						glassPane.setVisible(false);
					}
				}.start();
			}
		});
		checkGlassPanePanel.add(bgp);
		this.jtp.addTab("Glass pane", checkGlassPanePanel);

		JPanel checkScrollPanePanel = new JPanel();
		checkScrollPanePanel.setLayout(new BorderLayout());
		checkScrollPanePanel.add(new ScrollPanel(), BorderLayout.CENTER);
		this.jtp.addTab("Scroll pane", checkScrollPanePanel);

		JPanel checkTabbedPanePanel = new JPanel();
		checkTabbedPanePanel.setLayout(new BorderLayout());
		checkTabbedPanePanel.add(new TabPanel(), BorderLayout.CENTER);
		this.jtp.addTab("Tabbed pane", checkTabbedPanePanel);

		JPanel checkTextFieldPanel = new JPanel();
		checkTextFieldPanel.setLayout(new FlowLayout());
		JTextField jtf1 = new JTextField("enabled");
		jtf1.setColumns(20);
		JTextField jtf2 = new JTextField("not editable");
		jtf2.setEditable(false);
		jtf2.setColumns(20);
		JTextField jtf3 = new JTextField("disabled");
		jtf3.setEnabled(false);
		jtf3.setColumns(20);
		JTextField jtf4 = new JTextField("enabled");
		jtf4.setColumns(20);
		checkTextFieldPanel.add(jtf1);
		checkTextFieldPanel.add(jtf2);
		checkTextFieldPanel.add(jtf3);
		checkTextFieldPanel.add(jtf4);
		this.jtp.addTab("Text field", checkTextFieldPanel);

		JPanel checkFormattedTextFieldPanel = new JPanel();
		checkFormattedTextFieldPanel.setLayout(new FlowLayout());
		JFormattedTextField jftf1 = new JFormattedTextField(new DecimalFormat(
				"#,##0.0000"));
		jftf1.setText("2,430.0000");
		jftf1.setColumns(20);
		JFormattedTextField jftf2 = new JFormattedTextField(new DecimalFormat(
				"#,##0.0000"));
		jftf2.setText("2,430.0000");
		jftf2.setEditable(false);
		jftf2.setColumns(20);
		JFormattedTextField jftf3 = new JFormattedTextField(new DecimalFormat(
				"#,##0.0000"));
		jftf3.setText("2,430.0000");
		jftf3.setEnabled(false);
		jftf3.setColumns(20);
		JFormattedTextField jftf4 = new JFormattedTextField(new DecimalFormat(
				"#,##0.0000"));
		jftf4.setText("2,430.0000");
		jftf4.setColumns(20);
		checkFormattedTextFieldPanel.add(jftf1);
		checkFormattedTextFieldPanel.add(jftf2);
		checkFormattedTextFieldPanel.add(jftf3);
		checkFormattedTextFieldPanel.add(jftf4);
		this.jtp.addTab("Formatted text field", checkFormattedTextFieldPanel);

		JPanel checkSplitPanel = new JPanel();
		checkSplitPanel.setLayout(new BorderLayout());
		checkSplitPanel.add(new SplitPanel(), BorderLayout.CENTER);
		this.jtp.addTab("Split", checkSplitPanel);

		JPanel checkDesktopPanePanel = new JPanel();
		checkDesktopPanePanel.setLayout(new BorderLayout());
		checkDesktopPanePanel.add(new DesktopPanel(), BorderLayout.CENTER);
		this.jtp.addTab("Desktop", checkDesktopPanePanel);

		JPanel checkDialogsPanel = new JPanel();
		checkDialogsPanel.setLayout(new FlowLayout());
		JButton bf = new JButton("File");
		bf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.showOpenDialog(Check.this);
			}
		});
		checkDialogsPanel.add(bf);

		JButton bc = new JButton("Color");
		bc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(Check.this,
						"Color chooser", new Color(23, 45, 200));
				if (color != null) {
					System.out.println("Chosen " + color.toString());
				}
			}
		});
		checkDialogsPanel.add(bc);

		JButton bopi = new JButton("Option pane - info");
		bopi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Check.this,
						"Sample info message", "Sample title",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		checkDialogsPanel.add(bopi);
		JButton bope = new JButton("Option pane - error");
		bope.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Check.this,
						"Sample error message", "Sample title",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		checkDialogsPanel.add(bope);
		JButton bopw = new JButton("Option pane - warning");
		bopw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Check.this,
						"Sample warning message", "Sample title",
						JOptionPane.WARNING_MESSAGE);
			}
		});
		checkDialogsPanel.add(bopw);
		JButton bopq = new JButton("Option pane - question");
		bopq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Check.this,
						"Sample question message", "Sample title",
						JOptionPane.QUESTION_MESSAGE);
			}
		});
		checkDialogsPanel.add(bopq);
		this.jtp.addTab("Dialogs", checkDialogsPanel);

		JPanel checkEditorPanePanel = new JPanel();
		checkEditorPanePanel.setLayout(new FlowLayout());
		JEditorPane jep1 = new JEditorPane("text/html;",
				"Some <b>random</b> <u>enabled</u> editor pane");
		checkEditorPanePanel.add(jep1);
		JEditorPane jep2 = new JEditorPane("text/html;",
				"Some <b>random</b> <u>read-only</u> editor pane");
		jep2.setEditable(false);
		checkEditorPanePanel.add(jep2);
		JEditorPane jep3 = new JEditorPane("text/html;",
				"Some <b>random</b> <u>disabled</u> editor pane");
		jep3.setEnabled(false);
		checkEditorPanePanel.add(jep3);
		this.jtp.addTab("Editor pane", checkEditorPanePanel);

		JPanel checkTextAreaPanel = new JPanel();
		checkEditorPanePanel.setLayout(new FlowLayout());
		JTextArea jta1 = new JTextArea("Some random enabled text area", 3, 20);
		checkTextAreaPanel.add(jta1);
		JTextArea jta2 = new JTextArea("Some random non editable text area", 3,
				20);
		jta2.setEditable(false);
		checkTextAreaPanel.add(jta2);
		JTextArea jta3 = new JTextArea("Some random disabled text area", 3, 20);
		jta3.setEnabled(false);
		checkTextAreaPanel.add(jta3);
		this.jtp.addTab("Text area", checkTextAreaPanel);

		JPanel checkTextPanePanel = new JPanel();
		checkTextPanePanel.setLayout(new FlowLayout());
		JTextPane jtp1 = new JTextPane();
		jtp1.replaceSelection("Some random enabled text pane");
		jtp1.setPreferredSize(new Dimension(100, 40));
		checkTextPanePanel.add(jtp1);
		JTextPane jtp2 = new JTextPane();
		jtp2.replaceSelection("Some random non editable text pane");
		jtp2.setPreferredSize(new Dimension(100, 40));
		jtp2.setEditable(false);
		checkTextPanePanel.add(jtp2);
		JTextPane jtp3 = new JTextPane();
		jtp3.replaceSelection("Some random disabled text pane");
		jtp3.setPreferredSize(new Dimension(100, 40));
		jtp3.setEnabled(false);
		checkTextPanePanel.add(jtp3);
		this.jtp.addTab("Text pane", checkTextPanePanel);

		JPanel checkPasswordFieldPanel = new JPanel();
		checkPasswordFieldPanel.setLayout(new FlowLayout());
		JPasswordField jpf1 = new JPasswordField("password", 10);
		checkPasswordFieldPanel.add(jpf1);
		JPasswordField jpf2 = new JPasswordField("password", 10);
		jpf2.setEditable(false);
		checkPasswordFieldPanel.add(jpf2);
		JPasswordField jpf3 = new JPasswordField("password", 10);
		jpf3.setEnabled(false);
		checkPasswordFieldPanel.add(jpf3);
		JPasswordField jpf4 = new JPasswordField("password", 10);
		jpf4.setEchoChar((char) 0);
		checkPasswordFieldPanel.add(jpf4);
		this.jtp.addTab("Passwords", checkPasswordFieldPanel);

		JPanel checkTablePanel = new JPanel();
		checkTablePanel.setLayout(new BorderLayout());
		checkTablePanel.add(new TablePanel(), BorderLayout.CENTER);
		this.jtp.addTab("Table", checkTablePanel);

		JPanel checkListPanel = new JPanel();
		checkListPanel.setLayout(new BorderLayout());
		checkListPanel.add(new ListPanel(), BorderLayout.CENTER);
		this.jtp.addTab("List", checkListPanel);

		JPanel checkSliderPanel = new JPanel();
		checkSliderPanel.setLayout(new BorderLayout());
		checkSliderPanel.add(new SliderPanel(), BorderLayout.CENTER);
		this.jtp.addTab("Slider", checkSliderPanel);

		JPanel checkProgressPanel = new JPanel();
		checkProgressPanel.setLayout(new BorderLayout());
		checkProgressPanel.add(new ProgressBarPanel(), BorderLayout.CENTER);
		this.jtp.addTab("Progress bar", checkProgressPanel);

		JPanel checkSpinnerPanel = new JPanel();
		checkSpinnerPanel.setLayout(new BorderLayout());
		checkSpinnerPanel.add(new SpinnerPanel(), BorderLayout.CENTER);
		this.jtp.addTab("Spinner", checkSpinnerPanel);

		JPanel checkTreePanel = new JPanel();
		checkTreePanel.setLayout(new GridLayout(1, 2));
		checkTreePanel.add(new TreePanel(true), BorderLayout.CENTER);
		checkTreePanel.add(new TreePanel(false), BorderLayout.CENTER);
		this.jtp.addTab("Tree", checkTreePanel);

		JPanel checkCardsPanel = new JPanel();
		checkCardsPanel.setLayout(new BorderLayout());
		checkCardsPanel.add(new CardPanel(), BorderLayout.CENTER);
		this.jtp.addTab("Cards", checkCardsPanel);

		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new GridLayout(2, 2));
		ColorPanel cp1 = new ColorPanel("default");
		colorPanel.add(cp1);
		ColorPanel cp2 = new ColorPanel("green");
		cp2.setBackground(Color.GREEN);
		colorPanel.add(cp2);
		ColorPanel cp3 = new ColorPanel("metallic");
		cp3.setBackground(ColorSchemeEnum.METALLIC.getColorScheme()
				.getLightColor());
		colorPanel.add(cp3);
		ColorPanel cp4 = new ColorPanel("red");
		cp4.setBackground(new Color(255, 0, 0));
		colorPanel.add(cp4);
		this.jtp.addTab("Color panels", colorPanel);

		JPanel bigButtonPanel = new JPanel();
		bigButtonPanel.setLayout(new BorderLayout());
		bigButtonPanel.add(new JButton("One big button"));
		this.jtp.addTab("Big button", bigButtonPanel);

		JPanel verticalButtonPanel = new JPanel();
		verticalButtonPanel.setLayout(new GridLayout(1, 3));
		verticalButtonPanel.add(new JButton("Vert button 1"));
		verticalButtonPanel.add(new JButton("Vert button 2"));
		JPanel smallVerticalButtonPanel = new JPanel();
		smallVerticalButtonPanel.setLayout(new GridLayout(4, 4));
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				smallVerticalButtonPanel.add(new JButton("vert"));
			}
		}
		verticalButtonPanel.add(smallVerticalButtonPanel);
		this.jtp.addTab("Vertical buttons", verticalButtonPanel);

		JPanel coloredPanel = new JPanel();
		coloredPanel.setLayout(new FlowLayout());
		coloredPanel.setBackground(new Color(200, 200, 255));
		JCheckBox colored1 = new JCheckBox("Colored checkbox");
		colored1.setForeground(Color.blue);
		colored1.setBackground(Color.yellow);
		coloredPanel.add(colored1);
		JRadioButton colored2 = new JRadioButton("Colored radiobutton");
		colored2.setForeground(new Color(0, 128, 0));
		colored2.setBackground(new Color(255, 180, 180));
		coloredPanel.add(colored2);
		JSlider colored3 = new JSlider(100, 1000, 400);
		colored3.setPaintTicks(true);
		colored3.setMajorTickSpacing(100);
		colored3.setForeground(new Color(128, 0, 0));
		colored3.setBackground(new Color(180, 255, 180));
		coloredPanel.add(colored3);
		JPanel colored4 = new JPanel();
		colored4.setSize(100, 100);
		colored4.setPreferredSize(colored4.getSize());
		colored4.setBackground(Color.cyan);
		coloredPanel.add(colored4);
		this.jtp.addTab("Colored components", coloredPanel);

		JPanel buttonStripPanel = new JPanel();
		buttonStripPanel.setLayout(new FlowLayout());

		JButtonStrip buttonStrip1 = new JButtonStrip();

		JButton stripButton1 = new JButton(listViewIcon);
		stripButton1.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		stripButton1.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		buttonStrip1.add(stripButton1);

		JButton stripButton2 = new JButton(detailsViewIcon);
		stripButton2.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		stripButton2.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		buttonStrip1.add(stripButton2);

		JButton stripButton3 = new JButton(detailsViewIcon);
		stripButton3.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		stripButton3.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		buttonStrip1.add(stripButton3);

		buttonStripPanel.add(buttonStrip1);

		JButtonStrip buttonStrip2 = new JButtonStrip(StripOrientation.VERTICAL);

		JButton stripButton21 = new JButton(listViewIcon);
		stripButton21.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		stripButton21.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		buttonStrip2.add(stripButton21);

		JButton stripButton22 = new JButton(detailsViewIcon);
		stripButton22.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		stripButton22.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		buttonStrip2.add(stripButton22);

		JButton stripButton23 = new JButton(detailsViewIcon);
		stripButton23.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		stripButton23.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		buttonStrip2.add(stripButton23);

		buttonStripPanel.add(buttonStrip1);
		buttonStripPanel.add(buttonStrip2);

		this.jtp.addTab("Button strip", buttonStripPanel);

		JPanel themesPanel = new JPanel();
		themesPanel.setLayout(new FlowLayout());
		Vector<String> themeVector = new Vector<String>();

		Map<String, String> allThemes = SubstanceLookAndFeel.enumerateThemes();
		for (Map.Entry<String, String> themeEntry : allThemes.entrySet()) {
			final String themeClassName = themeEntry.getValue();

			themeVector.add(themeClassName);
		}
		JComboBox themeCombo = new JComboBox(themeVector);
		themeCombo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				// Get the affected item
				final Object item = evt.getItem();

				if (evt.getStateChange() == ItemEvent.SELECTED) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            SubstanceLookAndFeel.setCurrentTheme((String) item);
                            SwingUtilities.updateComponentTreeUI(getRootPane());
                        }
                    });
				}
			}
		});

		themesPanel.add(themeCombo);

		this.jtp.addTab("Theme combo", themesPanel);

		JMenuBar jmb = new JMenuBar();

		JMenu jmTheme = new JMenu("Theme");

		JCheckBoxMenuItem jmiThemeObjs = new JCheckBoxMenuItem("Use objects");
		jmiThemeObjs.setSelected(true);
		jmiThemeObjs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem src = (JCheckBoxMenuItem) e.getSource();
				toUseThemeObjs = src.isSelected();
			}
		});
		jmTheme.add(jmiThemeObjs);
		toUseThemeObjs = jmiThemeObjs.isSelected();

		ButtonGroup bgTheme = new ButtonGroup();
		for (Map.Entry<String, String> themeEntry : allThemes.entrySet()) {
			String themeName = themeEntry.getKey();
			final String themeClassName = themeEntry.getValue();

			JRadioButtonMenuItem jmiTheme = new JRadioButtonMenuItem(themeName);
			jmiTheme.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (toUseThemeObjs) {
						try {
							SubstanceLookAndFeel
									.setCurrentTheme((SubstanceTheme) Class
											.forName(themeClassName)
											.newInstance());
						} catch (Exception exc) {
							exc.printStackTrace();
						}
					} else {
						SubstanceLookAndFeel.setCurrentTheme(themeClassName);
					}
					SwingUtilities.updateComponentTreeUI(getRootPane());
					System.out.println(UIManager
							.getColor("TabbedPane.selectHighlight"));
				}
			});
			if (themeName.equals(SubstanceLookAndFeel.getCurrentThemeName())) {
				jmiTheme.setSelected(true);
			}
			bgTheme.add(jmiTheme);
			jmTheme.add(jmiTheme);
		}

		jmTheme.addSeparator();

		JRadioButtonMenuItem jmiBottleGreenTheme = new JRadioButtonMenuItem(
				"User - Caribbean blue");
		jmiBottleGreenTheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SubstanceLookAndFeel.setCurrentTheme(new SubstanceTheme(
							new CaribbeanBlueColorScheme(), false,
							"Caribbean blue"));
				} catch (Exception exc) {
					exc.printStackTrace();
				}
				SwingUtilities.updateComponentTreeUI(getRootPane());
				System.out.println(UIManager
						.getColor("TabbedPane.selectHighlight"));
			}
		});
		if ("Caribbean blue".equals(SubstanceLookAndFeel.getCurrentThemeName())) {
			jmiBottleGreenTheme.setSelected(true);
		}
		bgTheme.add(jmiBottleGreenTheme);
		jmTheme.add(jmiBottleGreenTheme);

		JRadioButtonMenuItem jmiRaspberryTheme = new JRadioButtonMenuItem(
				"User - Crimson");
		jmiRaspberryTheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SubstanceLookAndFeel.setCurrentTheme(new SubstanceTheme(
							new CrimsonColorScheme(), false, "Crimson"));
				} catch (Exception exc) {
					exc.printStackTrace();
				}
				SwingUtilities.updateComponentTreeUI(getRootPane());
				System.out.println(UIManager
						.getColor("TabbedPane.selectHighlight"));
			}
		});
		if ("Crimson".equals(SubstanceLookAndFeel.getCurrentThemeName())) {
			jmiRaspberryTheme.setSelected(true);
		}
		bgTheme.add(jmiRaspberryTheme);
		jmTheme.add(jmiRaspberryTheme);

		jmb.add(jmTheme);

		JMenu jmWatermark = new JMenu("Watermark");
		JCheckBoxMenuItem jmiWatermarkObjs = new JCheckBoxMenuItem(
				"Use objects");
		jmiWatermarkObjs.setSelected(true);
		jmiWatermarkObjs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem src = (JCheckBoxMenuItem) e.getSource();
				toUseWatermarksObjs = src.isSelected();
			}
		});
		jmWatermark.add(jmiWatermarkObjs);
		toUseWatermarksObjs = jmiWatermarkObjs.isSelected();

		ButtonGroup bgWatermark = new ButtonGroup();
		Map<String, String> allWatermarks = SubstanceLookAndFeel
				.enumerateWatermarks();
		for (Map.Entry<String, String> watermarkEntry : allWatermarks
				.entrySet()) {
			String watermarkName = watermarkEntry.getKey();
			final String watermarkClassName = watermarkEntry.getValue();

			JRadioButtonMenuItem jmiWatermark = new JRadioButtonMenuItem(
					watermarkName);
			jmiWatermark.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (toUseWatermarksObjs) {
						try {
							SubstanceLookAndFeel
									.setCurrentWatermark((SubstanceWatermark) Class
											.forName(watermarkClassName)
											.newInstance());
						} catch (Exception exc) {
							exc.printStackTrace();
						}
					} else {
						SubstanceLookAndFeel
								.setCurrentWatermark(watermarkClassName);
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

		jmWatermark.addSeparator();

		JRadioButtonMenuItem jmiCoffeeBeansWatermark = new JRadioButtonMenuItem(
				"Coffee Beans");
		jmiCoffeeBeansWatermark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SubstanceLookAndFeel
							.setCurrentWatermark(new SubstanceCoffeeBeansWatermark());
				} catch (Exception exc) {
					exc.printStackTrace();
				}
				SwingUtilities.updateComponentTreeUI(getRootPane());
			}
		});
		if ("Coffee Beans".equals(SubstanceLookAndFeel
				.getCurrentWatermarkName())) {
			jmiCoffeeBeansWatermark.setSelected(true);
		}
		bgWatermark.add(jmiCoffeeBeansWatermark);
		jmWatermark.add(jmiCoffeeBeansWatermark);

		jmb.add(jmWatermark);

		JMenu jm0 = new JMenu("Menu0");
		jm0.setMnemonic('0');
		jm0.add(new JMenuItem("dummy0"));
		jmb.add(jm0);

		JMenu jm1 = new JMenu("Menu1");
		jm1.setMnemonic('1');
		JMenuItem jmi1 = new JMenuItem("Menu item enabled");
		jmi1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				Event.CTRL_MASK));
		JMenuItem jmi2 = new JMenuItem("Menu item disabled");
		jmi2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
				Event.CTRL_MASK));
		jmi2.setEnabled(false);

		jm1.add(jmi1);
		jm1.add(jmi2);

		jm1.addSeparator();

		JCheckBoxMenuItem jcbmi1 = new JCheckBoxMenuItem(
				"Check enabled selected");
		jcbmi1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				Event.CTRL_MASK));
		jcbmi1.setSelected(true);
		JCheckBoxMenuItem jcbmi2 = new JCheckBoxMenuItem(
				"Check enabled unselected");
		jcbmi2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				Event.CTRL_MASK));
		jcbmi2.setSelected(false);
		JCheckBoxMenuItem jcbmi3 = new JCheckBoxMenuItem(
				"Check disabled selected");
		jcbmi3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				Event.CTRL_MASK));
		jcbmi3.setSelected(true);
		jcbmi3.setEnabled(false);
		JCheckBoxMenuItem jcbmi4 = new JCheckBoxMenuItem(
				"Check disabled unselected");
		jcbmi4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				Event.CTRL_MASK));
		jcbmi4.setSelected(false);
		jcbmi4.setEnabled(false);

		jm1.add(jcbmi1);
		jm1.add(jcbmi2);
		jm1.add(jcbmi3);
		jm1.add(jcbmi4);

		jm1.addSeparator();

		JRadioButtonMenuItem jrbmi1 = new JRadioButtonMenuItem(
				"Radio enabled selected");
		jrbmi1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
				Event.CTRL_MASK));
		jrbmi1.setSelected(true);
		JRadioButtonMenuItem jrbmi2 = new JRadioButtonMenuItem(
				"Radio enabled unselected");
		jrbmi2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				Event.CTRL_MASK));
		jrbmi2.setSelected(false);
		ButtonGroup bgRadioMenu1 = new ButtonGroup();
		bgRadioMenu1.add(jrbmi1);
		bgRadioMenu1.add(jrbmi2);
		JRadioButtonMenuItem jrbmi3 = new JRadioButtonMenuItem(
				"Radio disabled selected");
		jrbmi3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				Event.CTRL_MASK));
		jrbmi3.setSelected(true);
		jrbmi3.setEnabled(false);
		JRadioButtonMenuItem jrbmi4 = new JRadioButtonMenuItem(
				"Radio disabled unselected");
		jrbmi4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J,
				Event.CTRL_MASK));
		jrbmi4.setSelected(false);
		jrbmi4.setEnabled(false);
		ButtonGroup bgRadioMenu2 = new ButtonGroup();
		bgRadioMenu2.add(jrbmi3);
		bgRadioMenu2.add(jrbmi4);

		jm1.add(jrbmi1);
		jm1.add(jrbmi2);
		jm1.add(jrbmi3);
		jm1.add(jrbmi4);

		jm1.addSeparator();

		JMenu submenu1 = new JMenu("submenu1");
		submenu1.add(new JMenuItem("submenu item1"));
		submenu1.add(new JMenuItem("submenu item2"));
		submenu1.add(new JMenuItem("submenu item3"));
		JMenu submenu2 = new JMenu("submenu2");
		submenu2.add(new JMenuItem("submenu item11"));
		submenu1.add(submenu2);
		jm1.add(submenu1);

		jmb.add(jm1);

		JMenu jm2 = new JMenu("Menu2");
		jm2.setMnemonic('2');
		jm2.add(new JMenuItem("dummy2"));
		jmb.add(jm2);
		JMenu jm3 = new JMenu("Menu3");
		jm3.add(new JMenuItem("dummy3"));
		jm3.setMnemonic('3');
		jmb.add(jm3);
		JMenu jm4 = new JMenu("Menu4");
		jm4.add(new JMenuItem("dummy4"));
		jm4.setMnemonic('4');
		jmb.add(jm4);
		jm4.setEnabled(false);
		JMenu jm5 = new JMenu("Menu5");
		jm5.add(new JMenuItem("dummy5"));
		jm5.setMnemonic('5');
		// jmb.add(jm5);
		jm5.setEnabled(false);
		JMenu jm6 = new JMenu("Menu6");
		jm6.add(new JMenuItem("dummy6"));
		jm6.setMnemonic('6');
		// jmb.add(jm6);
		jm6.setEnabled(false);
		JMenu jm7 = new JMenu("Menu7");
		jm7.add(new JMenuItem("dummy7"));
		jm7.setMnemonic('7');
		// jmb.add(jm7);
		jm7.setEnabled(false);

		this.setJMenuBar(jmb);

		String fonts[] = { "Serif", "SansSerif", "Monospaced", "Dialog",
				"DialogInput" };

		JToolBar toolBar = new JToolBar("Formatting");

		toolBar.add(new JCheckBox("Allow font selection"));
		toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		toolBar.addSeparator();
		JLabel label = new JLabel("Font");
		toolBar.add(label);

		toolBar.addSeparator();
		JComboBox combo = new JComboBox(fonts);
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Font ["
							+ ((JComboBox) e.getSource()).getSelectedItem()
							+ "] chosen!");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		toolBar.add(combo);

		this.add(toolBar, BorderLayout.NORTH);
	}

	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("org.jvnet.substance.SubstanceLookAndFeel");
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
		// System.setProperty("sun.awt.noerasebackground", "false");
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
		c.setLocation((d.width - c.getWidth()) / 2,
				(d.height - c.getHeight()) / 2);
		c.setVisible(true);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}