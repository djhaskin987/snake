package model.serialization.db;

import static org.junit.Assert.*;

import java.util.List;

import model.Barcode;
import model.DateTime;
import model.IItem;
import model.Item;
import model.IProduct;
import model.NonEmptyString;
import model.Product;
import model.Quantity;
import model.Unit;
import model.ValidDate;

import org.junit.Before;
import org.junit.Test;

public class ItemDAOTest {
	
	private JDBCFixture fixture;
	private IItemDAO itemDAO;
	
	@Before
	public void setup() {
		fixture = new JDBCFixture();
		itemDAO = new ItemDAO(fixture.getWrapper());
	}

	@Test
	public void test() {
		assertTrue(itemDAO.readAll().size() == 0);
		
		Quantity quantity = new Quantity(1.0, Unit.COUNT);
		IProduct product = new Product(new NonEmptyString("barcode"),
				new NonEmptyString("description"), quantity, 0, 1);
		ValidDate entryDate = new ValidDate();
		DateTime exitTime = new DateTime();
		Barcode itemBarcode = new Barcode();
		IItem item = new Item(product, itemBarcode, entryDate, exitTime, null);
		itemDAO.create(item);
		IItem returnedItem = itemDAO.read(item.getBarcode());
		assertTrue(returnedItem.getProduct() == null);
		List<IItem> itemList = itemDAO.readAll();
		assertTrue(itemList.size() == 1);
		assertEquals(returnedItem.getBarcode(), itemBarcode);
		assertEquals(returnedItem.getEntryDate().getCalendar(), entryDate.getCalendar());
		assertEquals(returnedItem.getExitTime().getCalendar(), exitTime.getCalendar());
		assertEquals(itemDAO.getProductBarcode(itemBarcode),new Barcode(product.getBarcode()));
		
		
	/*	it
		productDAO.create(product);
		List<IjjProduct> products = productDAO.readAll();
		assertTrue(products.size() == 1);
		IProduct product0 = products.get(0);
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
		
		//assertTrue(productDAO.getContainers(product1).size() == 0);
		
		productDAO.delete(product);
		products = productDAO.readAll();
		assertTrue(products.size() == 0);
		*/
	}

}
