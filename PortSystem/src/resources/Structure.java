/*
 * 
 */
package resources;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.apache.log4j.Logger;

import models.Pier;
import models.Port;
import models.Ship;
import models.containerGood;
import Dao.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Structure.
 */
public class Structure {

	/** The port. */
	private static Port port;

	/** The Titanic. */
	private static Ship Titanic;
	
	/** The Golandec. */
	private static Ship Golandec;
	
	/** The Lodka. */
	private static Ship Lodka;
	
	/** The Layner. */
	private static Ship Layner;

	/** The pictures. */
	private static containerGood pictures;
	
	/** The tv. */
	private static containerGood TV;
	
	/** The car. */
	private static containerGood car;
	
	/** The mobile. */
	private static containerGood mobile;
	
	/** The silk. */
	private static containerGood silk;

	/** The ship vector. */
	private static Vector<Ship> shipVector;
	
	/** The good vector. */
	private static Vector<containerGood> goodVector;

	/** The state. */
	private static CheckState state;
	
	/** The Constant LOG. */
	public static final Logger LOG = Logger.getLogger(Structure.class);

	/**
	 * Start init.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public static void startInit() throws InterruptedException, ClassNotFoundException, SQLException {

		pictures = new containerGood("Picture", 20);
		TV = new containerGood("TV", 30);
		car = new containerGood("Car", 5);
		mobile = new containerGood("Mobile", 500);
		silk = new containerGood("Silk", 400);
		goodVector = new Vector<containerGood>();
		goodVector.add(pictures);
		goodVector.add(TV);
		goodVector.add(car);
		goodVector.add(mobile);
		goodVector.add(silk);

		port = new Port("Syniavka", new Pier("1"), new Pier("2"));
		shipDaoImplements.getInstance().ConnectionDB();
		shipDaoImplements.getInstance().CreateDB();
		List<Ship> shipList = shipDaoImplements.getInstance().Read_Data();
		shipDaoImplements.getInstance().CloseDB();
		shipVector = new Vector<Ship>();
		int size = shipList.size();
		for (int i = 0; i < size; i++)
			shipVector.add(shipList.get(i));

		state = new CheckState(port);
		state.start();
	}

	/**
	 * Adds the new ship.
	 *
	 * @param shipName the ship name
	 * @param shipPriority the ship priority
	 * @param shipCapacity the ship capacity
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void addNewShip(String shipName, String shipPriority, String shipCapacity)
			throws SQLException, ClassNotFoundException {

		Ship ship;

		int capacity;
		String priority;
		if (shipCapacity.equals("-capacity-"))
			capacity = 20;
		else
			capacity = Integer.parseInt(shipCapacity);

		if (shipPriority.equals("-priority-"))
			priority = "MIN";
		else
			priority = shipPriority;
		Boolean check;
		
		while (true) {
			ship = new Ship(shipName, port, capacity, priority, "Good");
			synchronized (Ship.getDao()) {
				shipDaoImplements.getInstance().ConnectionDB();
				check = shipDaoImplements.getInstance().checkShip(ship.getShipName());
				shipDaoImplements.getInstance().CloseDB();
			}
			if (check) {
				ship.incEnotCounter();
				continue;
			}

			else {
				synchronized (Ship.getDao()) {
					shipDaoImplements.getInstance().ConnectionDB();
					shipDaoImplements.getInstance().WriteDB(ship);
					shipDaoImplements.getInstance().CloseDB();
				}
				break;
			}
		}
		System.out.println("Ship "+ship.getShipName()+" added!");
		LOG.info("Ship "+ship.getShipName()+" added!");
		shipVector.add(ship);
	}

	/**
	 * Rand goods.
	 *
	 * @param ship the ship
	 */
	public static void randGoods(Ship ship) {
		Random random = new Random();
		int rand = random.nextInt(ship.getCapacity()) + 1;
		for (int j = 0; j < rand; j++) {
			if (ship.getCapacity() < j) {
				return;
			}
			int goodRand = random.nextInt(5);
			ship.setVectGood(goodVector.get(goodRand));

		}
	}

	/**
	 * Start thread.
	 *
	 * @param i the i
	 */
	public static void startThread(int i) {
		if (i >= shipVector.size())
			return;
		else {
			synchronized (shipVector) {

				// shipVector.remove(i);
				shipVector.get(i).start();
			}

		}
	}

	/**
	 * Gets the ship.
	 *
	 * @param shipName the ship name
	 * @return the ship
	 */
	public static String getShip(String shipName) {
		synchronized (shipVector) {
			for (int i = 0; i < shipVector.size(); i++) {
				if (shipName.equals(shipVector.get(i).getShipName()))
					return shipVector.get(i).getShipName();
			}
			return "null";
		}
	}

	/**
	 * Gets the real ship.
	 *
	 * @param shipName the ship name
	 * @return the real ship
	 */
	public static Ship getRealShip(String shipName) {
		for (int i = 0; i < shipVector.size(); i++) {
			if (shipName.equals(shipVector.get(i).getShipName()))
				return shipVector.get(i);
		}
		return null;
	}

	/**
	 * Gets the num ship.
	 *
	 * @param shipName the ship name
	 * @return the num ship
	 */
	public static int getNumShip(String shipName) {
		for (int i = 0; i < shipVector.size(); i++) {
			if (shipName.equals(shipVector.get(i).getShipName()))
				return i;
		}
		return -1;
	}

	/**
	 * Gets the vect ship.
	 *
	 * @return the vect ship
	 */
	public static Vector<Ship> getVectShip() {
		return shipVector;
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public static Port getPort() {
		return port;
	}

	/**
	 * Gets the gheck thread.
	 *
	 * @return the gheck thread
	 */
	public static CheckState getGheckThread() {
		return state;
	}
}
