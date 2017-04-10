package interfaces;

import java.sql.SQLException;

public interface UserDao {

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
	 * 
	 *             <br>
	 * 			<b>Description:</b><br>
	 *             This method create Users table if table no exists.
	 */
	public void CreateDB() throws SQLException;

	/**
	 * 
	 * @throws SQLException
	 *             <br>
	 * 			<b>Description:</b><br>
	 *             This method close Database: close objects that help to
	 *             contact with database. This object perform SQL query and
	 *             return data from database.
	 */
	public void CloseDB() throws SQLException;

	/**
	 * 
	 * @throws SQLException
	 *             <br>
	 * 			<b>Description:</b><br>
	 *             Method that compare current date with date that holds in
	 *             database. And if current date!=date in database, method
	 *             perfor edit date in database.
	 */
	public void check_Date() throws SQLException;

	/**
	 * 
	 * @param password 
	 * @return String
	 * 
	 *         <br>
	 * 		<b>Description:</b><br>
	 *         Method that perform encryption with enter password, and return
	 *         password in encryption view.
	 */
	public String Shifr(String password);

	/**
	 * 
	 * @param st
	 * @return double
	 * @throws SQLException
	 *             <br>
	 * 			<b>Description:</b><br>
	 *             Method that return available for user size(int mb), that user
	 *             can be use for adding information.
	 */
	public double get_Size(String st) throws SQLException;

	/**
	 * 
	 * @param user_name
	 * @param size
	 * @throws SQLException
	 * 
	 *             <br>
	 * 			<b>Description:</b><br>
	 *             Method that edit user available size, when user add any
	 *             information in database.
	 */
	public void edit_Size(String user_name, double size) throws SQLException;

	/**
	 * 
	 * @param login
	 * @param password
	 * @return Boolean
	 * @throws SQLException
	 *             <br>
	 * 			<b>Description:</b><br>
	 *             This method returns true if user is exists, and return false
	 *             if user not exists.
	 */
	public Boolean check_User(String login, String password) throws SQLException;

}
