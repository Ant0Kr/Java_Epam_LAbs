/*
 * 
 */
package models;

import java.sql.SQLException;
import java.util.LinkedList;
import org.apache.log4j.Logger;
import Dao.shipDaoImplements;
import resources.Structure;

// TODO: Auto-generated Javadoc
/**
 * The Class Ship.
 */
public class Ship extends Thread {
	
	/** The enot counter. */
	private static int enotCounter = 1;
	
	/** The ship name. */
	private String shipName;
	
	/** The port. */
	private Port port;
	
	/** The pier. */
	private Pier pier;
	
	/** The ship task. */
	private Boolean shipTask;
	
	/** The good quene. */
	private LinkedList<containerGood> goodQuene;
	
	/** The ship priority. */
	private String shipPriority;
	
	/** The flag in port. */
	private Boolean flagInPort;
	
	/** The ship capacity. */
	private int shipCapacity;
	
	/** The container task. */
	private int containerTask;
	
	/** The real time. */
	private int realTime;
	
	/** The all time. */
	private int allTime;
	
	/** The ship priority counter. */
	private int shipPriorityCounter;
	
	/** The ship statistics. */
	private String shipStatistics;
	
	/** The Dao. */
	private static shipDaoImplements Dao = new shipDaoImplements();
	
	/** The Constant LOG. */
	public static final Logger LOG = Logger.getLogger(Ship.class);
	

	/**
	 * Instantiates a new ship.
	 *
	 * @param name the name
	 * @param port the port
	 * @param capacity the capacity
	 * @param priority the priority
	 * @param statistics the statistics
	 */
	public Ship(String name, Port port, int capacity,String priority,String statistics) {
		if (name == "") {
			shipName = "UnknownEnot" + Integer.toString(enotCounter);
			enotCounter++;
		} else
			shipName = name;
		this.port = port;
		
		shipStatistics = statistics; 
		shipCapacity = capacity;
		shipPriority = priority;
		this.setShipPriority(shipPriority);
		
		goodQuene = new LinkedList<containerGood>();
		Structure.randGoods(this);
		flagInPort = false;
		shipPriorityCounter = 0;
		
	}

	/**
	 * Instantiates a new ship.
	 *
	 * @param ship the ship
	 */
	public Ship(Ship ship) {
		shipName = ship.getShipName();
		port = ship.getPort();
		goodQuene = ship.getQueneGood();
		shipTask = ship.getTask();
		this.shipPriority = ship.shipPriority;
		this.shipStatistics = ship.shipStatistics; 
		this.shipCapacity = ship.shipCapacity;
		this.shipPriority = ship.shipPriority;
		this.flagInPort = ship.flagInPort;
		this.shipPriorityCounter = ship.shipPriorityCounter;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {

		flagInPort = true;
		try {
			goToPort();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		pier.setShip(this);
		if (shipTask) {
			
			System.out.println("Ship " + shipName + " loading on pier ¹" + pier.getPierName());
			LOG.info("Ship " + shipName + " loading on pier ¹" + pier.getPierName());
			try {
				loadShip();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Ship " + shipName + " unloading on pier ¹" + pier.getPierName());
			LOG.info("Ship " + shipName + " unloading on pier ¹" + pier.getPierName());
			try {
				unloadShip();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			goFromPort();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Go to port.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void goToPort() throws InterruptedException {

		getPier();
		System.out.println("Ship "+ shipName + " moored to the pier ¹" + pier.getPierName());
		LOG.info("Ship "+ shipName + " moored to the pier ¹" + pier.getPierName());

	}

	/**
	 * Gets the pier.
	 *
	 * @return the pier
	 * @throws InterruptedException the interrupted exception
	 */
	public void getPier() throws InterruptedException {
		do {
			pier = port.getPier();

			if (pier == null) {
				Thread.sleep(1000);
			}

		} while (pier == null);
	}

	/**
	 * Load ship.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void loadShip() throws InterruptedException {

		for (int i = 0; i < containerTask; i++) {
			if (this.getQueneSize() >= shipCapacity) {
				System.out.println("There isn't such space on : " + shipName + "!");
				LOG.info("There isn't such space on : " + shipName + "!");
			}
			realTime++;
			Thread.sleep(1000);
			realTime++;
			sleep(1000);
			containerGood Good = pier.getGood();
			if (Good != null) {
				goodQuene.add(Good);
			} else {
				System.out.println("Storage of pier ¹" + pier.getPierName() + " is empty!");
				LOG.info("Storage of pier ¹" + pier.getPierName() + " is empty!");
				return;
			}
		}
		LOG.info("Ship " + shipName + " loaded!");
		System.out.println("Ship " + shipName + " loaded!");
	}

	/**
	 * Unload ship.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void unloadShip() throws InterruptedException {

		for (int i = 0; i < containerTask; i++) {
			realTime++;
			sleep(1000);
			realTime++;
			sleep(1000);
			containerGood Good = goodQuene.poll();
			if (Good != null) {
				pier.setGood(Good);
			} else {
				System.out.println("Hold of ship " + shipName + " is empty!");
				LOG.info("Hold of ship " + shipName + " is empty!");
				return;
			}

		}
		System.out.println("Ship " + shipName + " unloaded!");
		LOG.info("Ship " + shipName + " unloaded!");
	}

	/**
	 * Go from port.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void goFromPort() throws SQLException, ClassNotFoundException {

		System.out.println("Ship " + shipName + " went from port " + port.getPortName());
		LOG.info("Ship " + shipName + " went from port " + port.getPortName());
		flagInPort = false;
		
		port.returnPier(pier);
		pier.setShip(null);
		
		if (realTime > allTime) {
			setShipPriority("MIN");
			shipPriorityCounter = 0;
			shipStatistics = "Bad";
		} else {
			shipPriorityCounter++;
			if (shipPriorityCounter >= 1) {
				upShipPriority(shipPriority);
				shipPriorityCounter = 0;
				shipStatistics = "Good";
				
			}
		}
		realTime = 0;

		Ship ship = new Ship(this);
		int j;
		for (j = 0; j < Structure.getVectShip().size(); j++) {
			if (Structure.getVectShip().get(j).getShipName().equals(ship.getShipName()))
				break;
		}

		synchronized (Structure.getVectShip()) {
			Structure.getVectShip().remove(j);
			Structure.getVectShip().add(j, ship);
		}
		synchronized(Dao){
			Dao.ConnectionDB();
			Dao.saveUpdates(ship);
			Dao.CloseDB();
		}
		System.out.println("Pier ¹" + pier.getPierName() + " is free!");
		LOG.info("Pier ¹" + pier.getPierName() + " is free!");
	}

	/**
	 * Sets the ship priority.
	 *
	 * @param priority the new ship priority
	 */
	public void setShipPriority(String priority) {
		switch (priority) {
		case "MIN":
			this.setPriority(MIN_PRIORITY);
			break;
		case "NORM":
			this.setPriority(NORM_PRIORITY);
			break;
		case "MAX":
			this.setPriority(MAX_PRIORITY);
			break;
		default:
			this.setPriority(MIN_PRIORITY);
			break;
		}
		shipPriority = priority;
	}

	/**
	 * Gets the task.
	 *
	 * @return the task
	 */
	public Boolean getTask() {
		return shipTask;
	}

	/**
	 * Sets the task.
	 *
	 * @param task the new task
	 */
	public void setTask(Boolean task) {
		this.shipTask = task;
	}

	/**
	 * Gets the ship name.
	 *
	 * @return the ship name
	 */
	public String getShipName() {
		return shipName;
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public Port getPort() {
		return port;
	}

	/**
	 * Gets the good.
	 *
	 * @param i the i
	 * @return the good
	 */
	public containerGood getGood(int i) {
		return goodQuene.get(i);
	}

	/**
	 * Gets the quene size.
	 *
	 * @return the quene size
	 */
	public int getQueneSize() {
		return goodQuene.size();
	}

	/**
	 * Sets the vect good.
	 *
	 * @param good the new vect good
	 */
	public void setVectGood(containerGood good) {
		this.goodQuene.add(good);
	}

	/**
	 * Gets the quene good.
	 *
	 * @return the quene good
	 */
	public LinkedList<containerGood> getQueneGood() {
		return goodQuene;
	}

	/**
	 * Gets the ship priority.
	 *
	 * @return the ship priority
	 */
	public String getShipPriority() {
		return shipPriority;
	}

	/**
	 * Gets the flag in port.
	 *
	 * @return the flag in port
	 */
	public Boolean getFlagInPort() {
		return flagInPort;
	}

	/**
	 * Sets the capacity.
	 *
	 * @param capacity the new capacity
	 */
	public void setCapacity(int capacity) {
		shipCapacity = capacity;
	}

	/**
	 * Gets the capacity.
	 *
	 * @return the capacity
	 */
	public int getCapacity() {
		return shipCapacity;
	}

	/**
	 * Sets the container task.
	 *
	 * @param count the new container task
	 */
	public void setContainerTask(int count) {
		containerTask = count;
	}

	/**
	 * Sets the all time.
	 *
	 * @param time the new all time
	 */
	public void setAllTime(int time) {
		allTime = time;
	}

	/**
	 * Up ship priority.
	 *
	 * @param shipPriority the ship priority
	 */
	public void upShipPriority(String shipPriority) {

		switch (shipPriority) {
		case "MIN":
			this.shipPriority = "NORM";
			break;
		case "NORM":
			this.shipPriority = "MAX";
			break;
		case "MAX":
			break;
		default:
			this.shipPriority = "MIN";
			break;

		}
		setShipPriority(this.shipPriority);

	}

	/**
	 * Gets the all time.
	 *
	 * @return the all time
	 */
	public int getAllTime() {
		return allTime;
	}

	/**
	 * Gets the real time.
	 *
	 * @return the real time
	 */
	public int getRealTime() {
		return realTime;
	}
	
	/**
	 * Gets the statistics.
	 *
	 * @return the statistics
	 */
	public String getStatistics(){
		return shipStatistics;
	}
	
	/**
	 * Gets the dao.
	 *
	 * @return the dao
	 */
	public static shipDaoImplements getDao(){
		return Dao;
	}
	
	/**
	 * Inc enot counter.
	 */
	public void incEnotCounter(){
		enotCounter++;
	}
	
}
