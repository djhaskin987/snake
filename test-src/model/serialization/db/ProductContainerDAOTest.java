package model.serialization.db;

import static org.junit.Assert.*;

import java.util.List;

import model.IProduct;
import model.IProductContainer;
import model.Model;
import model.NonEmptyString;
import model.Product;
import model.ProductContainerFactory;
import model.ProductGroup;
import model.Quantity;
import model.StorageUnit;
import model.Unit;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

public class ProductContainerDAOTest {
	
	private JDBCFixture fixture;
	private IProductContainerDAO productContainerDAO;
	
	@Before
	public void setup() {
		fixture = new JDBCFixture();
		productContainerDAO = new ProductContainerDAO(fixture.getWrapper());
	}

	@Test
	public void test() {
		assertTrue(productContainerDAO.getMap().isEmpty());
		
		IProductContainer storageUnit = Model.getInstance().createStorageUnit("unit");
		Model.getInstance().addStorageUnit(storageUnit);
		productContainerDAO.create(storageUnit);
		assertTrue(productContainerDAO.getMap().size() == 1);
		IProductContainer container = productContainerDAO.getMap().values().iterator().next();
		assertTrue(container instanceof StorageUnit);
		assertEquals(container.getName().getValue(), "unit");
		container = productContainerDAO.read(Pair.of("unit", (String) null));
		assertTrue(container != null);
		
		Quantity quantity = new Quantity(1.0, Unit.COUNT);
		IProductContainer productGroup = new ProductGroup(new NonEmptyString("group"), storageUnit, quantity);
		productContainerDAO.create(productGroup);
		assertTrue(productContainerDAO.getMap().size() == 2);
		container = productContainerDAO.read(Pair.of("group", "unit"));
		assertTrue(container != null);
		assertTrue(container instanceof ProductGroup);
		assertEquals(container.getName(), productGroup.getName());
		assertEquals(container.getThreeMonthSupply(), quantity.toString());

		IProductContainer storageUnit2 = Model.getInstance().createStorageUnit("unit2");
		Model.getInstance().addStorageUnit(storageUnit2);
		productContainerDAO.create(storageUnit2);
		assertTrue(productContainerDAO.getMap().size() == 3);
		
		quantity = new Quantity(2.0, Unit.KG);
		productGroup = new ProductGroup(new NonEmptyString("group2"), storageUnit2, quantity);
		productContainerDAO.update("group", "unit", productGroup);
		assertTrue(productContainerDAO.getMap().size() == 3);
		System.out.println("Size: " + productContainerDAO.getMap().size());
		container = productContainerDAO.read(Pair.of("group2", "unit2"));
		assertTrue(container != null);
		assertTrue(container instanceof ProductGroup);
		assertEquals(container.getName(), productGroup.getName());
		assertEquals(container.getThreeMonthSupply(), quantity.toString());
		

		List<String> products = productContainerDAO.getProducts(Pair.of("group2", "unit2"));
		assertTrue(products.isEmpty());
		
		IProduct product = new Product("barcode", "description", quantity, 0, 0);
		productContainerDAO.addProductToProductContainer(product, productGroup);
		products = productContainerDAO.getProducts(Pair.of("group2", "unit2"));
		assertTrue(products.size() == 1);
		assertEquals(products.get(0), "barcode");
		
		productContainerDAO.removeProductFromProductContainer(product, productGroup);
		products = productContainerDAO.getProducts(Pair.of("group2", "unit2"));
		assertTrue(products.isEmpty());
	}
}
