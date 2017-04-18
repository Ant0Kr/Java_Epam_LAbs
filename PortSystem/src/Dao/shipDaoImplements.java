package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Ship;
import resources.Structure;

// TODO: Auto-generated Javadoc
/**
 * The Class shipDaoImplements.
 */
public class shipDaoImplements implements shipDao {

	/** The Constant INSTANCE. */
	private static final shipDaoImplements INSTANCE = new shipDaoImplements();
	
	/** The conn. */
	public static Connection conn;
	
	/** The statmt. */
	public static Statement statmt;
	
	/** The res set. */
	public static ResultSet resSet;

	/**
	 * Instantiates a new ship dao implements.
	 */
	public shipDaoImplements() {
	}

	/**
	 * Gets the single instance of shipDaoImplements.
	 *
	 * @return single instance of shipDaoImplements
	 */
	public static shipDaoImplements getInstance() {
		return INSTANCE;
	}

	/* (non-Javadoc)
	 * @see Dao.shipDao#ConnectionDB()
	 */
	@Override
	public void ConnectionDB() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		conn = null;
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:ShipDatabase.sqlite");
		statmt = conn.createStatement();
		resSet = null;
	}

	/* (non-Javadoc)
	 * @see Dao.shipDao#CreateDB()
	 */
	@Override
	public void CreateDB() throws SQLException {
		// TODO Auto-generated method stub
		statmt.execute(
				"CREATE  TABLE IF NOT EXISTS Ships (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,priority TEXT,"
						+ "capacity INTEGER,statistics TEXT);");
	}

	/* (non-Javadoc)
	 * @see Dao.shipDao#CloseDB()
	 */
	@Override
	public void CloseDB() throws SQLException {
		// TODO Auto-generated method stub
		conn.close();
		statmt.close();

	}

	/* (non-Javadoc)
	 * @see Dao.shipDao#Read_Data()
	 */
	@Override
	public List<Ship> Read_Data() throws ClassNotFoundException, SQLException {

		resSet = statmt.executeQuery("SELECT * FROM Ships ;");

		List<Ship> shipList = new ArrayList<Ship>();
		while (resSet.next()) {
			shipList.add(new Ship(resSet.getString("name"), Structure.getPort(), resSet.getInt("capacity"),
					resSet.getString("priority"), resSet.getString("statistics")));
		}

		resSet.close();
		return shipList;
	}

	/* (non-Javadoc)
	 * @see Dao.shipDao#WriteDB(models.Ship)
	 */
	@Override
	public void WriteDB(Ship ship) throws SQLException {
		// TODO Auto-generated method stub
		statmt.execute("INSERT INTO Ships(name,priority,capacity,statistics)" + "VALUES('" + ship.getShipName() + "',"
				+ "'" + ship.getShipPriority() + "'," + "'" + Integer.toString(ship.getCapacity()) + "'," + "'"
				+ ship.getStatistics() + "');");

	}

	/* (non-Javadoc)
	 * @see Dao.shipDao#saveUpdates(models.Ship)
	 */
	public void saveUpdates(Ship ship) throws SQLException {
		// TODO Auto-generated method stub
		statmt.executeUpdate("UPDATE Ships SET " + "priority = '" + ship.getShipPriority() + "'," + "statistics  = '"
				+ ship.getStatistics() + "'" + " WHERE name = '" + ship.getShipName() + "';");

	}
	
	/**
	 * Check ship.
	 *
	 * @param name the name
	 * @return the boolean
	 */
	public Boolean checkShip(String name){
		
		try {
			resSet = statmt.executeQuery("SELECT * FROM Ships WHERE name = '"+name+"';");
			resSet.next();
			resSet.getString("name");
			resSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}

}
