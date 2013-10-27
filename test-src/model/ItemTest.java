package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ItemTest {

	@Test
	public void test() {
		Product product = new Product(new Barcode("123456789012"), new NonEmptyString(" "), new Quantity(1.0, Unit.COUNT), 1, 1);
		Barcode barcode = new Barcode("123456789012");
		//Date expireDate = new Date();
		IProductContainer container = (IProductContainer) new StorageUnit();
		Item item = new Item(product, barcode, container);
		assertTrue(item.getProduct() == product);
		assertTrue(item.getBarcode() == barcode);
		assertTrue(item.getEntryDate().compareTo(new Date()) == 0);
		assertTrue(item.getExitTime() == null);
		//assertTrue(item.getExpireDate() == expireDate);
		assertTrue(item.getProductContainer() == container);
		ValidDate newEntryDate = new ValidDate();
		assertTrue(item.getEntryDate() != newEntryDate);
		item.setEntryDate(newEntryDate);
		assertTrue(item.getEntryDate() == newEntryDate);
		IProductContainer newContainer = (IProductContainer) new StorageUnit();
		assertTrue(item.getProductContainer() != newContainer);
		item.setProductContainer(newContainer);
		assertTrue(item.getProductContainer() == newContainer);
		DateTime before = new DateTime();
		item.exit();
		DateTime after = new DateTime();
		assertTrue(before.compareTo(item.getExitTime()) < 1);
		assertTrue(item.getExitTime().compareTo(after) < 1);
	}

}
