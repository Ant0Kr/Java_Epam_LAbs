package interfaces;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import models.Data;

/**
 * 
 * Class interface that perform contact between user and Database.
 *
 */
public interface DataDao {

	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 *             <br>
	 * 			<b>Description:</b><br>
	 *             This method perform connection to SQLite Database
	 */
	public void ConnectionDB() throws ClassNotFoundException, SQLException;

	/**
	 * 
	 * @throws SQLException
	 *             <br>
	 * 			<b>Description:</b><br>
	 *             This method create Data table if table no exists.
	 */
	public void CreateDB() throws SQLException;

	/**
	 * 
	 * @throws SQLException
	 * 
	 *             <br>
	 * 			<b>Description:</b><br>
	 *             This method close Database: close objects that help to
	 *             contact with database. This object perform SQL query and
	 *             return data from database.
	 */
	public void CloseDB() throws SQLException;

	/**
	 * 
	 * @param file_name
	 * @param way
	 * @param extension
	 * @param size
	 * @param type
	 * @throws SQLException
	 * 
	 *             <br>
	 * 			<b>Description:</b><br>
	 *             This method perform adding information in Database.<br>
	 */
	public void WriteDB(String file_name, String way, String extension, String size, int type) throws SQLException;

	/**
	 * 
	 * @param num
	 * @return ObservableList<Data>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 *             <br>
	 * 			<b>Description:</b><br>
	 *             Method that provides data from database.
	 * 
	 */
	public ObservableList<Data> Read_Data(int num) throws ClassNotFoundException, SQLException;

	/**
	 * 
	 * @param st
	 * @param num
	 * @return ObservableList<Data>
	 * @throws SQLException
	 *             <br>
	 * 			<b>Description:</b><br>
	 *             This method provides data from database. Return data
	 *             depending from text that user entry in searchField.
	 */
	public ObservableList<Data> Search(String st, int num) throws SQLException;

	/**
	 * 
	 * @param id
	 * @throws SQLException
	 * 
	 *             <br>
	 * 			<b>Description:</b><br>
	 *             Method that perform delete entry from database when tableView
	 *             is initializate.
	 */
	public void delete_Entry(int id) throws SQLException;

}
