package model.serialization.db;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProductDAOTest {
	
	private JDBCFixture fixture;
	private IProductDAO productDAO;
	
	@Before
	public void setup() {
		fixture = new JDBCFixture();
		productDAO = new ProductDAO(fixture.getWrapper());
	}

	@Test
	public void test() {
		//Quantity quantity = new Quantity(1.0, Unit.COUNT);
		assertTrue(productDAO.readAll().size() == 0);
		/*IProduct product = new Product("barcode", "description", quantity, 0, 1);
		productDAO.
		fail("Not yet implemented");*/
	}

}
