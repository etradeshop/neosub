package org.jvnet.substance.comp;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.*;

/**
 * Button strip component. Provides visual appearance of a strip. The buttons in
 * the strip are drawn horizontally with no horizontal space between them.
 * 
 * @author Kirill Grouchnikov
 */
public class JButtonStrip extends JComponent {
	/**
	 * The UI class ID string.
	 */
	private static final String uiClassID = "ButtonStripUI";

	public enum StripOrientation {
		HORIZONTAL, VERTICAL
	}

	private StripOrientation orientation;

	/**
	 * Simple constructor.
	 */
	public JButtonStrip() {
		this(StripOrientation.HORIZONTAL);
	}

	/**
	 * Simple constructor.
	 */
	public JButtonStrip(StripOrientation orientation) {
		this.orientation = orientation;
		updateUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#add(java.awt.Component, java.lang.Object, int)
	 */
	@Override
	public void add(Component comp, Object constraints, int index) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#add(java.awt.Component, java.lang.Object)
	 */
	@Override
	public void add(Component comp, Object constraints) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#add(java.awt.Component, int)
	 */
	@Override
	public Component add(Component comp, int index) {
		if (!(comp instanceof AbstractButton))
			throw new UnsupportedOperationException();
		return super.add(comp, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#add(java.awt.Component)
	 */
	@Override
	public Component add(Component comp) {
		if (!(comp instanceof AbstractButton))
			throw new UnsupportedOperationException();
		return super.add(comp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#add(java.lang.String, java.awt.Component)
	 */
	@Override
	public Component add(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Sets the new UI delegate.
	 * 
	 * @param ui
	 *            New UI delegate.
	 */
	public void setUI(ButtonStripUI ui) {
		super.setUI(ui);
	}

	/**
	 * Resets the UI property to a value from the current look and feel.
	 * 
	 * @see JComponent#updateUI
	 */
	public void updateUI() {
		setUI((ButtonStripUI) UIManager.getUI(this));
	}

	/**
	 * Returns the UI object which implements the L&F for this component.
	 * 
	 * @return a <code>ButtonStripUI</code> object
	 * @see #setUI
	 */
	public ButtonStripUI getUI() {
		return (ButtonStripUI) ui;
	}

	/**
	 * Returns the name of the UI class that implements the L&F for this
	 * component.
	 * 
	 * @return the string "ToggleButtonGroupUI"
	 * @see JComponent#getUIClassID
	 * @see UIDefaults#getUI
	 */
	public String getUIClassID() {
		return uiClassID;
	}

	/**
	 * Returns the number of buttons in <code>this</code> strip.
	 * 
	 * @return Number of buttons in <code>this</code> strip.
	 */
	public int getButtonCount() {
		return this.getComponentCount();
	}

	/**
	 * Returns the specified button component of <code>this</code> strip.
	 * 
	 * @param index
	 *            Button index.
	 * @return The matching button.
	 */
	public AbstractButton getButton(int index) {
		return (AbstractButton) this.getComponent(index);
	}

	/**
	 * Checks whether the specified button is the first button in
	 * <code>this</code> strip.
	 * 
	 * @param button
	 *            Button to check.
	 * @return <code>true</code> if the specified button is the first
	 *         button in <code>this</code> strip, <code>false</code>
	 *         otherwise.
	 */
	public boolean isFirst(AbstractButton button) {
		return (button == this.getButton(0));
	}

	/**
	 * Checks whether the specified button is the last button in
	 * <code>this</code> strip.
	 * 
	 * @param button
	 *            Button to check.
	 * @return <code>true</code> if the specified button is the last
	 *         button in <code>this</code> strip, <code>false</code>
	 *         otherwise.
	 */
	public boolean isLast(AbstractButton button) {
		return (button == this.getButton(this.getButtonCount() - 1));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintBorder(java.awt.Graphics)
	 */
	@Override
	protected void paintBorder(Graphics g) {
		// the border will be painted in the UI delegate.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintChildren(java.awt.Graphics)
	 */
	@Override
	protected void paintChildren(Graphics g) {
		// the child components (buttons) will be painted in the UI
		// delegate.
	}

	public StripOrientation getOrientation() {
		return orientation;
	}
}
