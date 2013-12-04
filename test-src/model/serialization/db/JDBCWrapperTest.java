package model.serialization.db;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class JDBCWrapperTest {
	
	private JDBCWrapper wrapper;
	private JDBCFixture fixture;
	
	@Before
	public void setup() {
		fixture = new JDBCFixture();
		wrapper = fixture.getWrapper();
	}

	@Test
	public void test() {
		//wrapper.insert("productcontainer", );
		wrapper.executeUpdate("insert into productcontainer(name) values ('thisisatest');");
		//ResultSet rs = wrapper.executeQuery("select * from productcontainer where name='thisisatest' and storageunit is null;");
		String[] names = {"name", "storageUnit"};
		Object[] values = {"thisisatest", null};
		ResultSet rs = wrapper.query("productcontainer", Arrays.asList(names), Arrays.asList(values));
		try {
			assertTrue(rs.next());
			assertTrue(rs.getString("StorageUnit") == null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		//wrapper.query("", columnNames, columnValues)
		
//		fail("Not yet implemented");
	}

}
