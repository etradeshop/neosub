package org.jvnet.substance;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;

import org.jvnet.substance.color.ColorSchemeEnum;

/**
 * UI for combo boxes in <b>Substance</b> look and feel.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstanceComboBoxUI extends BasicComboBoxUI {
	/**
	 * Combobox button arrow.
	 */
	public static final Map<ColorSchemeEnum, Icon> COMBO_ARROWS = new HashMap<ColorSchemeEnum, Icon>();

	protected ComboBoxPropertyChangeHandler changeHandler;
	
	static {
		for (ColorSchemeEnum colorSchemeEnum : ColorSchemeEnum.values()) {
			COMBO_ARROWS.put(colorSchemeEnum, SubstanceImageCreator
					.getArrowIcon(SubstanceImageCreator.ARROW_WIDTH,
							SubstanceImageCreator.ARROW_HEIGHT,
							SwingConstants.SOUTH, colorSchemeEnum));
		}
	}

	/**
	 * Updates arrow icons on the {@link ColorSchemeEnum#USER_DEFINED} enum.
	 */
	public static void updateArrows() {
		COMBO_ARROWS.put(ColorSchemeEnum.USER_DEFINED, SubstanceImageCreator
				.getArrowIcon(SubstanceImageCreator.ARROW_WIDTH,
						SubstanceImageCreator.ARROW_HEIGHT,
						SwingConstants.SOUTH, ColorSchemeEnum.USER_DEFINED));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent b) {
		return new SubstanceComboBoxUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicComboBoxUI#createArrowButton()
	 */
	@Override
	protected JButton createArrowButton() {
		ColorSchemeEnum colorSchemeEnum = comboBox.isEnabled() ? SubstanceLookAndFeel
				.getColorScheme()
				: ColorSchemeEnum.METALLIC;
		return new SubstanceComboBoxButton(comboBox, COMBO_ARROWS
				.get(colorSchemeEnum));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicComboBoxUI#createRenderer()
	 */
	@Override
	protected ListCellRenderer createRenderer() {
		return new SubstanceComboBoxRenderer.SubstanceUIResource(this.comboBox);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicComboBoxUI#installListeners()
	 */
	@Override
	protected void installListeners() {
		super.installListeners();
		this.changeHandler = new ComboBoxPropertyChangeHandler();
		comboBox.addPropertyChangeListener(this.changeHandler);
	}
	
	@Override
	protected void uninstallListeners() {
		comboBox.removePropertyChangeListener(this.changeHandler);
		this.changeHandler = null;
		super.uninstallListeners();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicComboBoxUI#createLayoutManager()
	 */
	@Override
	protected LayoutManager createLayoutManager() {
		return new SubstanceComboBoxLayoutManager();
	}

	/**
	 * Layout manager for combo box.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private class SubstanceComboBoxLayoutManager extends
			BasicComboBoxUI.ComboBoxLayoutManager {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
		 */
		@Override
		public void layoutContainer(Container parent) {
			JComboBox cb = (JComboBox) parent;

			cb.setBorder(new SubstanceBorder());

			// ListCellRenderer lcr = cb.getRenderer();
			// if (lcr.getClass().getName().startsWith("java"))
			// cb.setRenderer(new ComboBoxRenderer());

			int width = cb.getWidth();
			int height = cb.getHeight();

			Insets insets = getInsets();
			int buttonWidth = UIManager.getInt("ScrollBar.width") - 1;
			int buttonHeight = height - (insets.top + insets.bottom);

			if (arrowButton != null) {
				if (cb.getComponentOrientation().isLeftToRight()) {
					arrowButton.setBounds(width - (insets.right + buttonWidth),
							insets.top, buttonWidth, buttonHeight);
				} else {
					arrowButton.setBounds(insets.left, insets.top, buttonWidth,
							buttonHeight);
				}
			}
			if (editor != null) {
				editor.setBounds(rectangleForCurrentValue());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#getPreferredSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getPreferredSize(JComponent c) {
		Dimension superRes = super.getPreferredSize(c);
		Dimension res = new Dimension((int) superRes.getWidth() + 4,
				(int) superRes.getHeight() + 4);
		return res;
	}

	/**
	 * This property change handler changes combo box arrow icon based on the
	 * enabled status of the combo box.
	 * 
	 * @author Kirill Grouchnikov
	 */
	public class ComboBoxPropertyChangeHandler extends PropertyChangeHandler {
		public void propertyChange(PropertyChangeEvent e) {
			String propertyName = e.getPropertyName();

			if (propertyName.equals("enabled")) {
				if (arrowButton != null) {
					// fix for bug 55
					ColorSchemeEnum colorSchemeEnum = comboBox.isEnabled() ? SubstanceLookAndFeel
							.getColorScheme()
							: ColorSchemeEnum.METALLIC;
					arrowButton.setIcon(COMBO_ARROWS.get(colorSchemeEnum));
				}
			}
			// Do not call super - fox for bug 63
		}
	}

	@Override
	public void paintCurrentValueBackground(Graphics g, Rectangle bounds,
			boolean hasFocus) {
		// override to prevent the behaviour from the base class, which
		// ignores the painting of the default cell renderer.
	}

	

}
