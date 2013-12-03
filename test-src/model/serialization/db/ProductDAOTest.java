package model.serialization.db;

import static org.junit.Assert.*;

import java.util.List;

import model.IProduct;
import model.Product;
import model.Quantity;
import model.Unit;

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
		Quantity quantity = new Quantity(1.0, Unit.COUNT);
		assertTrue(productDAO.readAll().size() == 0);
		IProduct product = new Product("barcode", "description", quantity, 0, 1);
		productDAO.create(product);
		List<IProduct> products = productDAO.readAll();
		assertTrue(products.size() == 1);
		IProduct product0 = products.get(0);
		assertEquals(product0.getBarcode().getValue(), "barcode");
		assertEquals(product0.getDescription().getValue(), "description");
		assertEquals(product0.getItemSize(), quantity);
		assertTrue(product0.getShelfLife() == 0);
		assertTrue(product0.getThreeMonthSupply() == 1);
	}

}
