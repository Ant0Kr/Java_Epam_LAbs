/*
 * 
 */
package models;

import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

// TODO: Auto-generated Javadoc
/**
 * The Class Port.
 */
public class Port {

	/** The port name. */
	private String portName;
	
	/** The pier num. */
	private static int pierNum = 2;
	
	/** The semaphore. */
	private final Semaphore semaphore;
	
	/** The pier quene. */
	private LinkedList<Pier> pierQuene = new LinkedList<Pier>();
	
	/** The pier vector. */
	private Vector<Pier> pierVector = new Vector<Pier>();
	
	/** The Constant LOG. */
	public static final Logger LOG = Logger.getLogger(Port.class);
	
	/**
	 * Instantiates a new port.
	 *
	 * @param name the name
	 * @param pier1 the pier 1
	 * @param pier2 the pier 2
	 */
	public Port(String name, Pier pier1, Pier pier2) {

		portName = name;
		semaphore = new Semaphore(pierNum, true);
		pierQuene.add(pier1);
		pierQuene.add(pier2);
		pierVector.addElement(pier1);
		pierVector.addElement(pier2);
	}

	/**
	 * Gets the pier.
	 *
	 * @return the pier
	 * @throws InterruptedException the interrupted exception
	 */
	public Pier getPier() throws InterruptedException {

		if (semaphore.tryAcquire(100, TimeUnit.MILLISECONDS)) {
			synchronized (this) {
				Pier pier = pierQuene.poll();

				if (pier != null) {
					
					System.out.println("Pier ¹" + pier.getPierName() + " preserved!");
					LOG.info("Pier ¹" + pier.getPierName() + " preserved!");
				}
				return pier;
			}
		} else {
			return null;
		}
	}

	/**
	 * Gets the pier combo.
	 *
	 * @param pierName the pier name
	 * @return the pier combo
	 */
	public String getPierCombo(String pierName) {
		for (int i = 0; i < pierVector.size(); i++) {
			if (pierName.equals(pierVector.get(i).getPierName())) {
				return pierVector.get(i).getPierName();
			}
		}
		return "null";
	}

	/**
	 * Gets the real pier.
	 *
	 * @param pierName the pier name
	 * @return the real pier
	 */
	public Pier getRealPier(String pierName) {
		for (int i = 0; i < pierVector.size(); i++) {
			if (pierName.equals(pierVector.get(i).getPierName())) {
				return pierVector.get(i);
			}
		}
		return null;
	}

	/**
	 * Gets the port name.
	 *
	 * @return the port name
	 */
	public String getPortName() {
		return portName;
	}

	/**
	 * Gets the full pier vector.
	 *
	 * @return the full pier vector
	 */
	public Vector<Pier> getFullPierVector() {
		return pierVector;
	}

	/**
	 * Gets the vect pier size.
	 *
	 * @return the vect pier size
	 */
	public int getVectPierSize() {
		return pierVector.size();
	}

	/**
	 * Return pier.
	 *
	 * @param pier the pier
	 */
	public void returnPier(Pier pier) {
		pierQuene.add(pier);
		semaphore.release();
	}

	/**
	 * Gets the num pier.
	 *
	 * @return the num pier
	 */
	public int getNumPier() {
		return pierNum;
	}

	/**
	 * Pier num inc.
	 */
	public void pierNumInc() {
		pierNum++;
	}

	/**
	 * Gets the pier quene.
	 *
	 * @return the pier quene
	 */
	public LinkedList<Pier> getPierQuene() {
		return pierQuene;
	}

	/**
	 * Release semaphore.
	 */
	public void releaseSemaphore() {
		semaphore.release();
	}

}
