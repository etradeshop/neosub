package org.jvnet.substance.comp;

import java.util.ArrayList;

/**
 * Single ribbon task in {@link org.jvnet.substance.comp.JRibbon}. This is a
 * logical entity that groups {@link org.jvnet.substance.comp.JRibbonBand}
 * components.
 * 
 * @author Kirill Grouchnikov
 */
public class RibbonTask {
	/**
	 * List of all bands.
	 */
	private ArrayList<JRibbonBand> bands;

	/**
	 * Simple constructor.
	 */
	public RibbonTask() {
		this.bands = new ArrayList<JRibbonBand>();
	}

	/**
	 * Adds new band to <code>this</code> task.
	 * 
	 * @param band
	 *            Band to add.
	 */
	public void addBand(JRibbonBand band) {
		this.bands.add(band);
	}

	/**
	 * Removes the specified band from <code>this</code> task.
	 * 
	 * @param band
	 *            Band to remove.
	 */
	public void removeBand(JRibbonBand band) {
		this.bands.remove(band);
	}

	/**
	 * Removes band at the specified index from <code>this</code> task.
	 * 
	 * @param index
	 *            Index of band to remove.
	 */
	public void removeBand(int index) {
		this.bands.remove(index);
	}

	/**
	 * Returns the number of bands in <code>this</code> task.
	 * 
	 * @return Number of bands in <code>this</code> task.
	 */
	public int getBandCount() {
		return this.bands.size();
	}

	/**
	 * Returns band at the specified index from <code>this</code> task.
	 * 
	 * @param index
	 *            Band index.
	 * @return Band at the specified index.
	 */
	public JRibbonBand getBand(int index) {
		return this.bands.get(index);
	}
}
