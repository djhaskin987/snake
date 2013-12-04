package model.serialization.db;

import static org.junit.Assert.*;

import java.util.List;

import model.IProduct;
import model.IProductContainer;
import model.Model;
import model.NonEmptyString;
import model.Product;
import model.ProductContainerFactory;
import model.Quantity;
import model.Unit;

import org.apache.commons.lang3.tuple.Pair;
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
		assertTrue(productDAO.readAll().size() == 0);
		
		Quantity quantity = new Quantity(1.0, Unit.COUNT);
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
		IProduct product1 = productDAO.read(new NonEmptyString("barcode"));
		assertTrue(product1 != null);
		
		quantity = new Quantity(2.0, Unit.KG);
		product = new Product("barcode", "description2", quantity, 3, 4);
		productDAO.update(product);
		products = productDAO.readAll();
		assertTrue(products.size() == 1);
		product0 = products.get(0);
		assertEquals(product0.getBarcode().getValue(), "barcode");
		assertEquals(product0.getDescription().getValue(), "description2");
		assertEquals(product0.getItemSize(), quantity);
		assertTrue(product0.getShelfLife() == 3);
		assertTrue(product0.getThreeMonthSupply() == 4);
		
		assertTrue(productDAO.getContainers(product).size() == 0);
		
		IProductContainer storageUnit = Model.getInstance().createStorageUnit("unit");
		Model.getInstance().addStorageUnit(storageUnit);
		IProductContainer productGroup = ProductContainerFactory.getInstance().createProductGroup("group", storageUnit, quantity);
		IProductContainerDAO productContainerDAO = new ProductContainerDAO(fixture.getWrapper());
		productContainerDAO.create(storageUnit);
		productContainerDAO.create(productGroup);
		productContainerDAO.addProductToProductContainer(product, productGroup);
		assertTrue(productDAO.getContainers(product).size() == 1);
		assertEquals(productDAO.getContainers(product).get(0),Pair.of("group", "unit"));
		
		productDAO.delete(product);
		products = productDAO.readAll();
		assertTrue(products.size() == 0);
	}

}
