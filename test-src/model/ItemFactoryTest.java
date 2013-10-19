package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ItemFactoryTest {

	@Test
	public void test() {
		assertTrue(ItemFactory.getInstance() != null);
		assertTrue(ItemFactory.getInstance() == ItemFactory.getInstance());

		Product product = new Product(new Barcode("123456789012"), new NonEmptyString(" "), new Quantity(1.0, Unit.COUNT), 1, 1);
		Barcode barcode = new Barcode("123456789012");
		Date expireDate = new Date();
		IProductContainer container = (IProductContainer) new StorageUnit();
		IItem item = ItemFactory.getInstance().createInstance(product, barcode, expireDate, container);
		assertTrue(item.getProduct() == product);
		assertTrue(item.getBarcode() == barcode);
		assertTrue(item.getEntryDate().compareTo(new Date()) == 0);
		assertTrue(item.getExitTime() == null);
		assertTrue(item.getExpireDate() == expireDate);
		assertTrue(item.getProductContainer() == container);
	}

}
