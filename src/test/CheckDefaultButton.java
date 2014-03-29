package test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ref.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import org.jvnet.substance.SubstanceLookAndFeel;

public class CheckDefaultButton extends JFrame {

	private static class SimpleDialog extends JDialog {
		public JButton b1;
        public JButton b0;

		public SimpleDialog() {
			this.setLayout(new FlowLayout());
            b0 = new JButton("default-0");
			b1 = new JButton("default-1");
			JButton b2 = new JButton("disabled");
			b2.setEnabled(false);
			JButton b3 = new JButton("regular");
            this.add(b0);
			this.add(b1);
			this.add(b2);
			this.add(b3);

            JButton b4 = new JButton("toggle b1.enabled()");
            b4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    b1.setEnabled(!b1.isEnabled());
                }
            });
            this.add(b4);

            JButton b5 = new JButton("toggle b0-b1 default");
            b5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton defButton = getRootPane().getDefaultButton();
                    if (defButton == b0)
                        getRootPane().setDefaultButton(b1);
                    else
                        getRootPane().setDefaultButton(b0);
                }
            });
            this.add(b5);

			this.getRootPane().setDefaultButton(b1);
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
	}

	private Set<SimpleDialog> simpleDialogs = new HashSet<SimpleDialog>();

	public CheckDefaultButton() {
		super("Substance default button test");

		this.setLayout(new FlowLayout());
		JButton b1 = new JButton("Default");
		this.add(b1);

		JButton bd = new JButton("Open new dialog");
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
				simpleDialogs.add(sd);
			}
		});
		this.add(bd);
		JButton bcd = new JButton("Close all dialogs");
		bcd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (SimpleDialog simpleDialog : simpleDialogs) {
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
				simpleDialogs.clear();
			}
		});
		this.add(bcd);

		this.getRootPane().setDefaultButton(b1);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		try {
//			UIManager
//			.setLookAndFeel("com.oyoaha.swing.plaf.oyoaha.OyoahaLookAndFeel");
			UIManager.setLookAndFeel(new SubstanceLookAndFeel());
		} catch (Exception ulafe) {
			ulafe.printStackTrace();
		}
		CheckDefaultButton c = new CheckDefaultButton();
		c.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		// center the frame in the physical screen
		c.setLocation((d.width - c.getWidth()) / 2,
				(d.height - c.getHeight()) / 2);
		c.setVisible(true);
	}

}
