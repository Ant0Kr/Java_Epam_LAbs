/*
 * 
 */
package models;

import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Class Pier.
 */
public class Pier {

	/** The pier name. */
	private String pierName;
	
	/** The pier ship. */
	private Ship pierShip;
	
	/** The pier storage. */
	private LinkedList<containerGood> pierStorage;

	/**
	 * Instantiates a new pier.
	 *
	 * @param name the name
	 */
	public Pier(String name) {

		pierName = name;
		pierShip = null;
		pierStorage = new LinkedList<containerGood>();
	}

	/**
	 * Gets the good.
	 *
	 * @return the good
	 */
	public containerGood getGood() {
		return pierStorage.poll();
	}

	/**
	 * Gets the storage count.
	 *
	 * @return the storage count
	 */
	public int getStorageCount() {
		return pierStorage.size();
	}

	/**
	 * Sets the good.
	 *
	 * @param Good the new good
	 */
	public void setGood(containerGood Good) {
		pierStorage.add(Good);
	}

	/**
	 * Gets the ship.
	 *
	 * @return the ship
	 */
	public Ship getShip() { // корабль на причале
		return pierShip;
	}

	/**
	 * Sets the ship.
	 *
	 * @param ship the new ship
	 */
	public void setShip(Ship ship) { // швартует корабль на причал
		pierShip = ship;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Boolean getStatus() {
		if (pierShip == null)
			return false;
		else
			return true;
	}

	/**
	 * Gets the pier name.
	 *
	 * @return the pier name
	 */
	public String getPierName() {
		return pierName;
	}

	/**
	 * Gets the quene good.
	 *
	 * @return the quene good
	 */
	public LinkedList<containerGood> getQueneGood() {
		return pierStorage;
	}
}
