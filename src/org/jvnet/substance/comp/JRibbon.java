package org.jvnet.substance.comp;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

/**
 * Ribbon component.
 * 
 * @author Kirill Grouchnikov
 */
public class JRibbon extends JComponent {
	/**
	 * The list of all {@link RibbonTask} tasks.
	 */
	private ArrayList<RibbonTask> tasks;

	/**
	 * All regular components (to the left of task tabs).
	 */
	private ArrayList<Component> regularComponents;

	/**
	 * Toggle buttons of task tabs.
	 */
	private ArrayList<JToggleButton> taskToggleButtons;

	/**
	 * Bands of the currently shown task.
	 */
	private ArrayList<JRibbonBand> bands;

	/**
	 * Currently selected (shown) task).
	 */
	private RibbonTask currentlySelectedTask;

	/**
	 * Button group for task toggle buttons.
	 */
	private ButtonGroup taskToggleButtonGroup;

	/**
	 * The UI class ID string.
	 */
	private static final String uiClassID = "RibbonUI";

	/**
	 * Simple constructor.
	 */
	public JRibbon() {
		this.tasks = new ArrayList<RibbonTask>();
		this.regularComponents = new ArrayList<Component>();
		this.taskToggleButtons = new ArrayList<JToggleButton>();
		this.bands = new ArrayList<JRibbonBand>();
		this.currentlySelectedTask = new RibbonTask();
		this.taskToggleButtonGroup = new ButtonGroup();
		updateUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#add(java.awt.Component, int)
	 */
	@Override
	public Component add(Component comp, int index) {
		throw new UnsupportedOperationException();
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
	 * @see java.awt.Container#add(java.awt.Component)
	 */
	@Override
	public Component add(Component comp) {
		this.regularComponents.add(comp);
		return super.add(comp);
	}

	/**
	 * Adds new component without putting it into the {@link #regularComponents}
	 * list.
	 * 
	 * @param comp
	 *            Component to add.
	 * @return Added component.
	 */
	private Component addInternal(Component comp) {
		return super.add(comp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#remove(java.awt.Component)
	 */
	@Override
	public void remove(Component comp) {
		this.regularComponents.remove(comp);
		super.remove(comp);
	}

	/**
	 * Adds new task.
	 * 
	 * @param name
	 *            Task name.
	 * @param task
	 *            Task object.
	 */
	public void addTask(String name, final RibbonTask task) {
		this.tasks.add(task);
		JToggleButton taskToggleButton = new JToggleButton(name);
		taskToggleButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.taskToggleButtonGroup.add(taskToggleButton);
		this.taskToggleButtons.add(taskToggleButton);
		this.addInternal(taskToggleButton);
		if (this.tasks.size() == 1) {
			this.setSelectedTask(task);
			taskToggleButton.setSelected(true);
		}
		taskToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						setSelectedTask(task);
					}
				});
			}
		});
	}

	/**
	 * Removes a task.
	 * 
	 * @param task
	 *            Task to remove.
	 */
	public void removeTask(RibbonTask task) {
		int index = this.tasks.indexOf(task);
		this.removeTask(index);
	}

	/**
	 * Removes task at specified index.
	 * 
	 * @param index
	 *            Index of the task to remove.
	 */
	public void removeTask(int index) {
		if (index >= 0) {
			RibbonTask toRemove = this.tasks.get(index);
			this.tasks.remove(toRemove);
			if (this.currentlySelectedTask == toRemove) {
				index--;
				if (index < 0)
					index++;
				if (index < this.tasks.size())
					this.setSelectedTask(this.tasks.get(index));
			}
		}
	}

	/**
	 * Returns the number of tasks in <code>this</code> ribbon.
	 * 
	 * @return Number of tasks in <code>this</code> ribbon.
	 */
	public int getTaskCount() {
		return this.tasks.size();
	}

	/**
	 * Retrieves task at specified index.
	 * 
	 * @param index
	 *            Task index.
	 * @return Task that matches the specified index.
	 */
	public RibbonTask getTask(int index) {
		return this.tasks.get(index);
	}

	/**
	 * Selects the specified task.
	 * 
	 * @param task
	 *            Task to select.
	 */
	protected void setSelectedTask(RibbonTask task) {
		for (JRibbonBand panel : this.bands) {
			this.remove(panel);
		}
		this.bands.clear();

		for (int i = 0; i < task.getBandCount(); i++) {
			JRibbonBand panel = task.getBand(i);
			this.addInternal(panel);
			this.bands.add(panel);
		}

		this.currentlySelectedTask = task;
		this.revalidate();
		this.repaint();
	}

	/**
	 * Sets the new UI delegate.
	 * 
	 * @param ui
	 *            New UI delegate.
	 */
	public void setUI(RibbonUI ui) {
		super.setUI(ui);
	}

	/**
	 * Resets the UI property to a value from the current look and feel.
	 * 
	 * @see JComponent#updateUI
	 */
	public void updateUI() {
		setUI((RibbonUI) UIManager.getUI(this));
	}

	/**
	 * Returns the UI object which implements the L&F for this component.
	 * 
	 * @return a <code>RibbonUI</code> object
	 * @see #setUI
	 */
	public RibbonUI getUI() {
		return (RibbonUI) ui;
	}

	/**
	 * Returns the name of the UI class that implements the L&F for this
	 * component.
	 * 
	 * @return the string "RibbonUI"
	 * @see JComponent#getUIClassID
	 * @see UIDefaults#getUI
	 */
	public String getUIClassID() {
		return uiClassID;
	}

	/**
	 * Gets all regular components of <code>this</code> ribbon.
	 * 
	 * @return All regular components of <code>this</code> ribbon.
	 */
	public ArrayList<Component> getRegularComponents() {
		return regularComponents;
	}

	/**
	 * Gets all bands of currently selected task of <code>this</code> ribbon.
	 * 
	 * @return All bands of currently selected task of <code>this</code>
	 *         ribbon.
	 */
	public ArrayList<JRibbonBand> getBands() {
		return bands;
	}

	/**
	 * Gets all task toggle buttons of <code>this</code> ribbon.
	 * 
	 * @return All task toggle buttons of <code>this</code> ribbon.
	 */
	public ArrayList<JToggleButton> getTaskToggleButtons() {
		return taskToggleButtons;
	}
}
