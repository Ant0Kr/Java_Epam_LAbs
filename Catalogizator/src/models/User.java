package models;

import java.sql.Date;

/**
 * 
 * <br>
 * <b>Description:</b><br>
 * Class container that contains data that required for work with SQLite
 * database.
 *
 */
@SuppressWarnings("unused")
public class User {

	private String login;
	private String password;
	private double size;
	private Date date;

	User(String log, String pass, double size, Date date) {
		this.login = log;
		this.password = pass;
		this.size = size;
		this.date = date;
	}

}
