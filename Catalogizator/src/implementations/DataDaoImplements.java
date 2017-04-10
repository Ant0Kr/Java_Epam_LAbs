package implementations;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Data;

public class DataDaoImplements implements DataDao {

	private static final DataDaoImplements INSTANCE = new DataDaoImplements();
	public static Connection conn;
	public static Statement statmt;
	public static ResultSet resSet;
	
	public static final Logger LOG = Logger.getLogger(DataDaoImplements.class);

	public DataDaoImplements() {
	}

	public static DataDaoImplements getInstance() {
		return INSTANCE;
	}

	@Override
	public void ConnectionDB() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		conn = null;
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:FileDatabase.sqlite");
		statmt = conn.createStatement();
		resSet = null;
		System.out.println("База File Подключенааа!");
	}

	@Override
	public void CreateDB() throws SQLException {
		// TODO Auto-generated method stub
		statmt.execute("CREATE TABLE IF NOT EXISTS  Data (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "type INTEGER,file_name TEXT,way TEXT,extension text,size DOUBLE);");

		System.out.println("Таблица Data создана!");
	}

	@Override
	public void CloseDB() throws SQLException {
		// TODO Auto-generated method stub
		conn.close();
		statmt.close();

		System.out.println("Соединения File закрыты!");
	}

	@Override
	public void WriteDB(String file_name, String way, String extension, String size, int type) throws SQLException {
		// TODO Auto-generated method stub
		statmt.execute("INSERT INTO Data(type,file_name,way,extension,size)" + "VALUES('" + Integer.toString(type)
				+ "','" + file_name + "','" + way + "','" + extension + "','" + size + "');");
		LOG.info("File:"+file_name+" was added!");
		System.out.println("Запись произведена!");
	}

	@Override
	public ObservableList<Data> Read_Data(int num) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		resSet = statmt.executeQuery("SELECT * FROM Data WHERE type=" + Integer.toString(num) + ";");

		ObservableList<Data> data = FXCollections.observableArrayList();
		while (resSet.next()) {
			File file = new File(resSet.getString("way"));
			if (file.exists()) {
				String file_name = resSet.getString("file_name");
				String way = resSet.getString("way");
				String extension = resSet.getString("extension");
				String size = resSet.getString("size");
				int index = size.lastIndexOf('.');
				size = size.substring(0, index + 3);
				data.addAll(new Data(file_name, way, extension, size));
			} else{
				String name = resSet.getString("file_name");
				String oldWay = resSet.getString("way");
				LOG.info("File:"+name+" on way:"+oldWay+" not found!");
				delete_Entry(resSet.getInt("id"));
				LOG.info("File:"+name+" was delete from catalog!");	
			}
				
		}
		resSet.close();
		return data;

	}

	@Override
	public ObservableList<Data> Search(String st, int num) throws SQLException {
		// TODO Auto-generated method stub
		resSet = statmt.executeQuery(
				"SELECT * FROM Data WHERE type=" + Integer.toString(num) + " " + "and file_name = '" + st + "';");
		ObservableList<Data> data = FXCollections.observableArrayList();
		while (resSet.next()) {
			String file_name = resSet.getString("file_name");
			String way = resSet.getString("way");
			String extension = resSet.getString("extension");
			String size = resSet.getString("size");
			int index = size.lastIndexOf('.');
			size = size.substring(0, index + 3);
			data.addAll(new Data(file_name, way, extension, size));
		}
		resSet.close();
		return data;
	}

	@Override
	public void delete_Entry(int id) throws SQLException {
		// TODO Auto-generated method stub
		Statement sttm = conn.createStatement();
		sttm.execute("DELETE FROM Data WHERE id = " + Integer.toString(id) + ";");
		sttm.close();
	}

}
