/**
 * 
 */
package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dhaskin
 *
 */
public class ProductCollectionTest {
	ProductCollection pc;
	Product product1;
	Product product0;
	Item item1;
	Item item0;
	Item item2;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		product0 = new Product(null, null, null, 0, 0, null);
		product1 = new Product(null, null, null, 0, 0, null);
		item0 = new Item(product0, null, null, null);
		item1 = new Item(product0, null, null, null);
		item2 = new Item(product1, null, null, null);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link model.ProductCollection#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		pc = ProductCollection.getInstance();
		ProductCollection cp = ProductCollection.getInstance();
		assertTrue(pc == cp);
		assertFalse(pc == null);
	}

	/**
	 * Test method for {@link model.ProductCollection#add(model.Product)}.
	 */
	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.ProductCollection#getProduct(model.Barcode)}.
	 */
	@Test
	public void testGetProduct() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.ProductCollection#getSize()}.
	 */
	@Test
	public void testGetSize() {
		fail("Not yet implemented");
	}

}
