package org.jvnet.substance;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

/**
 * Tracer for memory usage patterns of <b>Substance</b> look-and-feel. The
 * tracer is started when VM has <code>-Dsubstancelaf.traceFile</code> flag.
 * The value of this flag specifies the location of trace log file. When
 * activated, the tracer runs a thread that collects information on memory usage
 * and appends it to the trace log file every <code>X</code> seconds. The
 * <code>X</code> (delay) is specified in the constructor.
 * 
 * @author Kirill Grouchnikov
 */
public class MemoryAnalyzer extends Thread {
	/**
	 * Sleep delay between trace log iterations.
	 */
	private long delay;

	/**
	 * Trace logfile name.
	 */
	private String filename;

	/**
	 * Singleton instance.
	 */
	private static MemoryAnalyzer instance;

	/**
	 * If <code>true</code>, <code>this</code> tracer has received a
	 * request to stop.
	 */
	private static boolean isStopRequest = false;

	/**
	 * Usage strings collected during the sleep time.
	 */
	private static ArrayList<String> usages;

	/**
	 * Formatting object.
	 */
	private static SimpleDateFormat sdf;

	/**
	 * Simple constructor.
	 * 
	 * @param delay
	 *            Sleep delay between trace log iterations.
	 * @param filename
	 *            Trace logfile name.
	 */
	private MemoryAnalyzer(long delay, String filename) {
		this.delay = delay;
		this.filename = filename;
	}

	/**
	 * Starts the memory tracing.
	 * 
	 * @param delay
	 *            Sleep delay between trace log iterations.
	 * @param filename
	 *            Trace logfile name.
	 */
	public static synchronized void commence(long delay, String filename) {
		if (instance == null) {
			instance = new MemoryAnalyzer(delay, filename);
			usages = new ArrayList<String>();
			// yeah, yeah, it's not multi-thread safe.
			sdf = new SimpleDateFormat("HH:mm:ss.SSS");
			instance.start();
		}
	}

	/**
	 * Issues request to stop tracing.
	 */
	public static synchronized void requestStop() {
		isStopRequest = true;
	}

	/**
	 * Checks whether a request to stop tracing has been issued.
	 */
	private static synchronized boolean hasStopRequest() {
		return isStopRequest;
	}

	/**
	 * Checks whether tracer is running.
	 */
	public static boolean isRunning() {
		return (instance != null);
	}

	/**
	 * Adds usage string.
	 * 
	 * @param usage
	 *            Usage string. Will be output to the trace file at next
	 *            iteration of the tracer.
	 */
	public static synchronized void enqueueUsage(String usage) {
		if (instance != null) {
			usages.add(sdf.format(new Date()) + ": " + usage);
		}
	}

	/**
	 * Returns all queued usages.
	 */
	public static synchronized ArrayList<String> getUsages() {
		ArrayList<String> copy = new ArrayList<String>();
		for (String usage : usages)
			copy.add(usage);
		usages.clear();
		return copy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// output all settings from UIManager
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(
					new FileWriter(new File(filename), true));
			bw.write(sdf.format(new Date()) + "\n");

			UIDefaults uidefs = UIManager.getLookAndFeelDefaults();
			// Retrieve the keys. Can't use an iterator since the map
			// may be modified during the iteration. So retrieve all at
			// once.
			Set<Object> keySet = uidefs.keySet();
			List<String> keyList = new LinkedList<String>();
			for (Object key : keySet) {
				keyList.add((String)key);
			}
			Collections.sort(keyList);
		    
		    for (String key : keyList) {
		        Object v = uidefs.get(key);

				if (v instanceof Integer) {
					int intVal = uidefs.getInt(key);
					bw.write(key + " (int) : " + intVal);
				} else if (v instanceof Boolean) {
					boolean boolVal = uidefs.getBoolean(key);
					bw.write(key + " (bool) : " + boolVal);
				} else if (v instanceof String) {
					String strVal = uidefs.getString(key);
					bw.write(key + " (string) : " + strVal);
				} else if (v instanceof Dimension) {
					Dimension dimVal = uidefs.getDimension(key);
					bw.write(key + " (Dimension) : " + dimVal.width + "*"
							+ dimVal.height);
				} else if (v instanceof Insets) {
					Insets insetsVal = uidefs.getInsets(key);
					bw.write(key + " (Insets) : " + insetsVal.top + "*"
							+ insetsVal.left + "*" + insetsVal.bottom + "*"
							+ insetsVal.right);
				} else if (v instanceof Color) {
					Color colorVal = uidefs.getColor(key);
					bw.write(key + " (int) : " + colorVal.getRed() + ","
							+ colorVal.getGreen() + ","
							+ colorVal.getBlue());
				} else if (v instanceof Font) {
					Font fontVal = uidefs.getFont(key);
					bw.write(key + " (Font) : " + fontVal.getFontName()
							+ "*" + fontVal.getSize());
				} else {
					bw.write(key + " (Object) : " + uidefs.get(key));
				}
				bw.write("\n");
			}
		} catch (IOException ioe) {
			requestStop();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (Exception exc) {
				}
			}
		}
		while (!hasStopRequest()) {
			// gather statistics and print them to file
			bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(new File(this.filename),
						true));
				bw.write(sdf.format(new Date()) + "\n");
				bw.write(PulseTracker.getMemoryUsage() + "\n");
				// bw.write(RolloverButtonListener.getMemoryUsage() + "\n");
				// bw.write(RolloverScrollBarButtonListener.getMemoryUsage()
				// + "\n");
				bw.write(SubstanceBackgroundDelegate.getMemoryUsage() + "\n");
				bw.write(SubstanceCheckBoxUI.getMemoryUsage() + "\n");
				bw.write(SubstanceComboBoxUI.getMemoryUsage() + "\n");
				bw.write(SubstanceGradientBackgroundDelegate.getMemoryUsage()
						+ "\n");
				bw.write(SubstanceProgressBarUI.getMemoryUsage() + "\n");
				bw.write(SubstanceRadioButtonUI.getMemoryUsage() + "\n");
				bw.write(SubstanceRootPaneUI.getMemoryUsage() + "\n");
				bw.write(SubstanceScrollBarUI.getMemoryUsage() + "\n");
				bw.write(SubstanceTabbedPaneUI.getMemoryUsage() + "\n");
				ArrayList<String> usages = getUsages();
				for (String usage : usages) {
					bw.write(usage + "\n");
				}
				bw.write("\n");
			} catch (IOException ioe) {
				requestStop();
			} finally {
				if (bw != null) {
					try {
						bw.close();
					} catch (Exception exc) {
					}
				}
			}

			// sleep
			try {
				sleep(this.delay);
			} catch (InterruptedException ie) {
			}
		}
	}
}
