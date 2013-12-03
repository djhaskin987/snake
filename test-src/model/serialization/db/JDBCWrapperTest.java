package model.serialization.db;

import static org.junit.Assert.*;

import java.io.File;

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
		//wrapper.query("", columnNames, columnValues)
		
		fail("Not yet implemented");
	}

}
