package implementations;

import java.sql.*;

import org.apache.log4j.Logger;

import interfaces.UserDao;

public class UserDaoImplements implements UserDao {

	private static final UserDaoImplements INSTANCE = new UserDaoImplements();
	public static Connection conn;
	public static Statement statmt;
	public static ResultSet resSet;
	public static final Logger LOG = Logger.getLogger(DataDaoImplements.class);

	public UserDaoImplements() {
	}

	public static UserDaoImplements getInstance() {
		return INSTANCE;
	}

	@Override
	public void ConnectionDB() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		conn = null;
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:UserDataBase.sqlite");
		statmt = conn.createStatement();
		System.out.println("База Users Подключена!");
	}

	@Override
	public void CreateDB() throws SQLException {
		// TODO Auto-generated method stub
		statmt.execute("CREATE TABLE IF NOT EXISTS  Users (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "login TEXT,password TEXT,size DOUBLE,date TEXT);");
		resSet = statmt.executeQuery("SELECT * FROM Users WHERE login = 'admin';");
		if (!resSet.next()) {
			Date date = new Date(System.currentTimeMillis());
			statmt.execute("Insert into Users(login,password,date) Values('admin','2222','" + date.toString() + "');");
		}

		resSet = statmt.executeQuery("SELECT * FROM Users WHERE login = 'antoha12018';");
		if (!resSet.next()) {
			statmt.execute("Insert into Users(login,password,size) Values('antoha12018','1111','10.0');");
		}

		System.out.println("Таблица Users создана!");
	}

	@Override
	public void CloseDB() throws SQLException {
		// TODO Auto-generated method stub
		conn.close();
		statmt.close();

		System.out.println("Соединения Users закрыты!");
	}

	@Override
	public void check_Date() throws SQLException {
		// TODO Auto-generated method stub
		Date date = new Date(System.currentTimeMillis());
		Statement sttm = conn.createStatement();
		resSet = sttm.executeQuery("SELECT * FROM Users WHERE login='admin';");
		resSet.next();

		String date_Base = resSet.getString("date");
		if (!date.toString().equals(date_Base)) {
			sttm.execute("Update Users Set size=10.0 Where login!='admin';");
			sttm.execute("Update Users Set date ='" + date.toString() + "' where login='admin';");
		}
		resSet.close();

	}

	@SuppressWarnings("static-access")
	@Override
	public String Shifr(String password) { // JUnit
		// TODO Auto-generated method stub
		char[] buf = password.toCharArray();
		int counter = password.length() - 1;
		char symbol;
		while (counter >= 0) {
			symbol = password.charAt(counter);
			buf[counter] = (char) (symbol + 1);
			counter--;

		}
		return password.copyValueOf(buf);
	}

	@Override
	public double get_Size(String st) throws SQLException {
		// TODO Auto-generated method stub
		resSet = statmt.executeQuery("SELECT size FROM Users WHERE login='" + st + "';");

		resSet.next();
		double size = Double.parseDouble(resSet.getString("size"));
		resSet.close();
		return size;
	}

	@Override
	public void edit_Size(String user_name, double size) throws SQLException {
		// TODO Auto-generated method stub
		statmt.execute("UPDATE Users SET size = '" + Double.toString(size) + "'" + "WHERE login ='" + user_name + "';");
	}

	@Override
	public Boolean check_User(String login, String password) throws SQLException { // junit
		// TODO Auto-generated method stub
		String st = Shifr(password);
		resSet = statmt
				.executeQuery("SELECT id FROM Users " + "Where login='" + login + "' and password='" + st + "';");
		if (!resSet.next()) {
			resSet.close();
			return false;
		}

		else {
			resSet.close();
			return true;
		}

	}
	
	public void WriteDB(String login,String password) throws SQLException {
		// TODO Auto-generated method stub
		statmt.execute("INSERT INTO Users(login,password,size,date)" + "VALUES('" + login
				+ "','" + password + "','" + Integer.toString(10) + "','18.04.2017 ');");
		LOG.info("User:"+login+" was added!");
		System.out.println("Запись произведена!");
	}

}
