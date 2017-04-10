package test;

import static org.junit.Assert.*;
import resources.File_Chooser;
import java.sql.SQLException;

import implementations.*;

/**
 * 
 * Test class for testing different methods.
 *
 */
public class Test {

	@org.junit.Test
	public void testCheck_User() throws SQLException, ClassNotFoundException {
		UserDaoImplements data = new UserDaoImplements();
		data.ConnectionDB();
		Boolean bool = data.check_User("antoha12018", "0000");
		data.CloseDB();
		assertEquals(true, bool);

	}
	
	@org.junit.Test
	public void testShifr() throws SQLException, ClassNotFoundException {
		UserDaoImplements data = new UserDaoImplements();
		data.ConnectionDB();
		String password = data.Shifr("1234");
		data.CloseDB();
		assertEquals("2345", password);

	}

	@org.junit.Test
	public void testGet_Name() throws SQLException, ClassNotFoundException {
		String name = File_Chooser.get_Name("\\D:\\Music\5_Nizza_(Pyatnica)_-_Neva_Pesni-Tut.mp3");
		assertEquals("\\D:\\Music\5_Nizza_(Pyatnica)_-_Neva_Pesni-Tut", name);

	}

}
