package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class StorageUnitTest {
	StorageUnit storageUnit;
	Product product;
	Item item;
	
	@Before
	public void setUp() throws Exception {
		storageUnit = new StorageUnit();
		product = new Product(new Barcode("123456789012"), new NonEmptyString(" "), new Quantity(1.0, Unit.COUNT), 1, 1, new ArrayList<ProductContainer>());
		Barcode barcode = new Barcode("123456789012");
		Date expireDate = new Date();
		IProductContainer container = (IProductContainer) new StorageUnit();
		Item item = new Item(product, barcode, expireDate, container);
		storageUnit.add(item);
	}

	@Test
	public void testGetProducts() {
		fail("Not yet implemented");
	}

	@Test
	public void testContains() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddProductGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteProductContainer() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetProductContainer() {
		fail("Not yet implemented");
	}

	@Test
	public void testTransferItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testTransferProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testWhoHasProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetParent() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUnit() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetThreeMonthSupply() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProductGroupName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetItemsString() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveItem() {
		fail("Not yet implemented");
	}

}
