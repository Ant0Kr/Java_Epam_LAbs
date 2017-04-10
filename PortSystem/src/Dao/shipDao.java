package Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javafx.collections.ObservableList;
import models.Ship;

// TODO: Auto-generated Javadoc
/**
 * The Interface shipDao.
 */
public interface shipDao {
	
	/**
	 * Connection DB.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public void ConnectionDB() throws ClassNotFoundException, SQLException;

	/**
	 * Creates the DB.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void CreateDB() throws SQLException;

	/**
	 * Close DB.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void CloseDB() throws SQLException;

	/**
	 * Write DB.
	 *
	 * @param ship the ship
	 * @throws SQLException the SQL exception
	 */
	public void WriteDB(Ship ship) throws SQLException;

	/**
	 * Read data from DB.
	 *
	 * @return the list
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public List<Ship> Read_Data() throws ClassNotFoundException, SQLException;

	/**
	 * Save updates on DB.
	 *
	 * @param ship the ship
	 * @throws SQLException the SQL exception
	 */
	public void saveUpdates(Ship ship) throws SQLException;
	
}
