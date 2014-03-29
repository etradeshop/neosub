package org.jvnet.substance.comp;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Ribbon band component. Is part of a logical {@link Ri
 * 
 * @author Kirill Grouchnikov
 */
public class JRibbonBand extends JComponent {
	/**
	 * Band title.
	 */
	private String title;

	/**
	 * Optional <code>expand</code> action listener. If present, the title
	 * pane shows button with plus sign. The action listener on the button will
	 * be <code>this</code> listener.
	 */
	private ActionListener expandActionListener;

	/**
	 * The UI class ID string.
	 */
	private static final String uiClassID = "RibbonBandUI";

	/**
	 * Band control panel.
	 */
	private JPanel controlPanel;

	/**
	 * Band expand button. Relevant only if {@link #expandActionListener} is not
	 * <code>null</code>.
	 */
	protected JButton expandButton;

	/**
	 * Simple constructor.
	 * 
	 * @param title
	 *            Band title.
	 */
	public JRibbonBand(String title) {
		this(title, null);
	}

	/**
	 * Simple constructor.
	 * 
	 * @param title
	 *            Band title.
	 * @param expandActionListener
	 *            Optional expand action listener.
	 */
	public JRibbonBand(String title, ActionListener expandActionListener) {
		super();
		this.title = title;
		this.controlPanel = new JPanel();
		this.add(this.controlPanel);
		this.expandActionListener = expandActionListener;
		if (this.expandActionListener != null) {
			this.expandButton = new JButton(UIManager
					.getIcon("RibbonBand.expandIcon"));
			this.expandButton.setPreferredSize(new Dimension(this.expandButton
					.getIcon().getIconWidth(), this.expandButton.getIcon()
					.getIconHeight()));
			this.expandButton.addActionListener(expandActionListener);
			this.add(this.expandButton);
		}
		updateUI();
	}

	/**
	 * Returns the title of <code>this</code> band.
	 * 
	 * @return Title of <code>this</code> band.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets new title for <code>this</code> band.
	 * 
	 * @param title
	 *            New title for <code>this</code> band.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the control panel of <code>this</code> band.
	 * 
	 * @return Control panel of <code>this</code> band.
	 */
	public JPanel getControlPanel() {
		return controlPanel;
	}

	/**
	 * Sets the new UI delegate.
	 * 
	 * @param ui
	 *            New UI delegate.
	 */
	public void setUI(RibbonBandUI ui) {
		super.setUI(ui);
	}

	/**
	 * Resets the UI property to a value from the current look and feel.
	 * 
	 * @see JComponent#updateUI
	 */
	public void updateUI() {
		setUI((RibbonBandUI) UIManager.getUI(this));
	}

	/**
	 * Returns the UI object which implements the L&F for this component.
	 * 
	 * @return a <code>RibbonBandUI</code> object
	 * @see #setUI
	 */
	public RibbonBandUI getUI() {
		return (RibbonBandUI) ui;
	}

	/**
	 * Returns the name of the UI class that implements the L&F for this
	 * component.
	 * 
	 * @return the string "RibbonBandUI"
	 * @see JComponent#getUIClassID
	 * @see UIDefaults#getUI
	 */
	public String getUIClassID() {
		return uiClassID;
	}
}
