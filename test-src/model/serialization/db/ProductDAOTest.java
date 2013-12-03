package model.serialization.db;

import static org.junit.Assert.*;
import model.IProduct;

import org.junit.Before;
import org.junit.Test;

public class ProductDAOTest {
	
	private JDBCWrapper wrapper;
	private JDBCFixture fixture;
	
	@Before
	public void setup() {
		fixture = new JDBCFixture();
		wrapper = fixture.getWrapper();
	}

	@Test
	public void test() {
		//IProduct product = new Pr
		fail("Not yet implemented");
	}

}
