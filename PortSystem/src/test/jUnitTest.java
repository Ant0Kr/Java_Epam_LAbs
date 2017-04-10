/*
 * 
 */
package test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import Dao.shipDaoImplements;


// TODO: Auto-generated Javadoc
/**
 * The Class jUnitTest.
 */
public class jUnitTest {
	
	/**
	 * Check.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@org.junit.Test
	public void check() throws ClassNotFoundException, SQLException{
		
		shipDaoImplements data = new shipDaoImplements();
		data.ConnectionDB();
		data.CreateDB();
		Boolean bool = data.checkShip("Golandec");
		data.CloseDB();
		assertEquals(true, bool);
		
	}
	
}
