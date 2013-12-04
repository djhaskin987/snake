package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProductItemsTest {

	@Test
	public void test() {
		ProductItems productItems = new ProductItems(null);
		Product product0 = new Product("Product0", null, null, 0, 0);
		Product product1 = new Product("Product1", null, null, 0, 0);
		Item item00 = new Item(product0, null, null, null);
		Item item01 = new Item(product0, null, null, null);
		Item item10 = new Item(product1, null, null, null);
		assertTrue(productItems.getProducts().size() == 0);
		assertTrue(productItems.getItems().size() == 0);
		assertFalse(productItems.contains(product0));
		assertFalse(productItems.contains(product1));
		assertFalse(productItems.getProducts().contains(product0));
		assertFalse(productItems.getProducts().contains(product1));
		assertFalse(productItems.contains(item00));
		assertFalse(productItems.contains(item01));
		assertFalse(productItems.contains(item10));
		assertFalse(productItems.getItems().contains(item00));
		assertFalse(productItems.getItems().contains(item01));
		assertFalse(productItems.getItems().contains(item10));
		
		productItems.addItem(item00);
		assertTrue(productItems.getProducts().size() == 1);
		assertTrue(productItems.getItems().size() == 1);
		assertTrue(productItems.contains(product0));
		assertFalse(productItems.contains(product1));
		assertTrue(productItems.getProducts().contains(product0));
		assertFalse(productItems.getProducts().contains(product1));
		assertTrue(productItems.contains(item00));
		assertFalse(productItems.contains(item01));
		assertFalse(productItems.contains(item10));
		assertTrue(productItems.getItems().contains(item00));
		assertFalse(productItems.getItems().contains(item01));
		assertFalse(productItems.getItems().contains(item10));
		
		productItems.removeItem(item00);
		assertTrue(productItems.getProducts().size() == 1);
		assertTrue(productItems.getItems().size() == 0);
		assertTrue(productItems.contains(product0));
		assertFalse(productItems.contains(product1));
		assertTrue(productItems.getProducts().contains(product0));
		assertFalse(productItems.getProducts().contains(product1));
		assertFalse(productItems.contains(item00));
		assertFalse(productItems.contains(item01));
		assertFalse(productItems.contains(item10));
		assertFalse(productItems.getItems().contains(item00));
		assertFalse(productItems.getItems().contains(item01));
		assertFalse(productItems.getItems().contains(item10));
		
		productItems.addItem(item00);
		productItems.addItem(item01);
		assertTrue(productItems.getProducts().size() == 1);
		assertTrue(productItems.getItems().size() == 2);
		assertTrue(productItems.contains(product0));
		assertFalse(productItems.contains(product1));
		assertTrue(productItems.getProducts().contains(product0));
		assertFalse(productItems.getProducts().contains(product1));
		assertTrue(productItems.contains(item00));
		assertTrue(productItems.contains(item01));
		assertFalse(productItems.contains(item10));
		assertTrue(productItems.getItems().contains(item00));
		assertTrue(productItems.getItems().contains(item01));
		assertFalse(productItems.getItems().contains(item10));
		
		productItems.removeItem(item00);
		assertTrue(productItems.getProducts().size() == 1);
		assertTrue(productItems.getItems().size() == 1);
		assertTrue(productItems.contains(product0));
		assertFalse(productItems.contains(product1));
		assertTrue(productItems.getProducts().contains(product0));
		assertFalse(productItems.getProducts().contains(product1));
		assertFalse(productItems.contains(item00));
		assertTrue(productItems.contains(item01));
		assertFalse(productItems.contains(item10));
		assertFalse(productItems.getItems().contains(item00));
		assertTrue(productItems.getItems().contains(item01));
		assertFalse(productItems.getItems().contains(item10));
		
		productItems.addItem(item10);
		assertTrue(productItems.getProducts().size() == 2);
		assertTrue(productItems.getItems().size() == 2);
		assertTrue(productItems.contains(product0));
		assertTrue(productItems.contains(product1));
		assertTrue(productItems.getProducts().contains(product0));
		assertTrue(productItems.getProducts().contains(product1));
		assertFalse(productItems.contains(item00));
		assertTrue(productItems.contains(item01));
		assertTrue(productItems.contains(item10));
		assertFalse(productItems.getItems().contains(item00));
		assertTrue(productItems.getItems().contains(item01));
		assertTrue(productItems.getItems().contains(item10));
		
		productItems.addItem(item00);
		assertTrue(productItems.getProducts().size() == 2);
		assertTrue(productItems.getItems().size() == 3);
		assertTrue(productItems.contains(product0));
		assertTrue(productItems.contains(product1));
		assertTrue(productItems.getProducts().contains(product0));
		assertTrue(productItems.getProducts().contains(product1));
		assertTrue(productItems.contains(item00));
		assertTrue(productItems.contains(item01));
		assertTrue(productItems.contains(item10));
		assertTrue(productItems.getItems().contains(item00));
		assertTrue(productItems.getItems().contains(item01));
		assertTrue(productItems.getItems().contains(item10));
	}

}
