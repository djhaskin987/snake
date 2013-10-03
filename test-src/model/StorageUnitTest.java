package model;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class StorageUnitTest {
	StorageUnit storageUnit;
	Product product;
	Item item;
	
	@Before
	public void setUp() throws Exception {
		storageUnit = new StorageUnit(new NonEmptyString("testSR"));
		product = new Product(new Barcode(), new NonEmptyString("bagel"), new Quantity(1.0, Unit.COUNT), 1, 1, new ArrayList<ProductContainer>());
		Barcode barcode = new Barcode();
		Date expireDate = new Date();
		item = new Item(product, barcode, expireDate, storageUnit);
		storageUnit.add(item);
	}

	@Test
	public void testGetProducts() {
		Collection<Product> pc = storageUnit.getProducts();
		assertTrue("should contain product", pc.contains(product));
	}

	@Test
	public void testContains() {
		assertTrue("should contain product", storageUnit.contains(product));
	}

	@Test
	public void testAddItem() {
		Barcode barcode = new Barcode();
		Date expireDate = new Date();
		Item item = new Item(product, barcode, expireDate, storageUnit);
		storageUnit.add(item);
		Collection<Item> items = storageUnit.getItems();
		assertTrue("should contain item", items.contains(item));
	}

	@Test
	public void testAddProductGroup() {
		ProductGroup pg = new ProductGroup(new NonEmptyString("testPG"));
		storageUnit.addProductGroup(pg);
		Collection<ProductGroup> pgCollection = storageUnit.getProductGroups();
		assertTrue("should contain a product group", pgCollection.contains(pg));
	}

	@Test
	public void testDeleteProductContainer() {
		ProductGroup pg = new ProductGroup(new NonEmptyString("testPG"));
		storageUnit.addProductGroup(pg);
		storageUnit.deleteProductContainer("testPG");
		Collection<ProductGroup> pgCollection = storageUnit.getProductGroups();
		assertTrue("should not contain a product group", !pgCollection.contains(pg));
	}

	@Test
	public void testSetProductContainer() {
		ProductGroup pg = new ProductGroup(new NonEmptyString("testPG"));
		storageUnit.addProductGroup(pg);
		ProductGroup pg2 = new ProductGroup(new NonEmptyString("testPG"));
		Product product = new Product(new Barcode(), new NonEmptyString("bagel"), new Quantity(1.0, Unit.COUNT), 1, 1, new ArrayList<ProductContainer>());
		pg2.add(new Item(product, new Barcode(), new Date(), pg2));
		storageUnit.setProductContainer("testPG", pg2);
		Collection<ProductGroup> pgCollection = storageUnit.getProductGroups();
		assertTrue("must contain the last item", pgCollection.contains(pg2));
	}

	@Test
	public void testTransferItem() {
		StorageUnit su = new StorageUnit(new NonEmptyString("test"));
		storageUnit.transferItem(item, su);
		assertTrue("item not transferred", !su.getItems().isEmpty() && storageUnit.getItems().isEmpty());
	}

	@Test
	public void testTransferProduct() {
		StorageUnit su = new StorageUnit(new NonEmptyString("test"));
		storageUnit.transferProduct(product, su);
		assertTrue("product not transferred", !su.getProducts().isEmpty());
	}

	@Test
	public void testWhoHasProduct() {
		IProductContainer pc = storageUnit.whoHasProduct("bagel");
		assertTrue("storage unit has this product", pc == storageUnit);
	}

	@Test
	public void testGetParent() {
		assertTrue(storageUnit.getParent() == null);
	}

	@Test
	public void testGetUnit() {
		assertTrue(storageUnit.getParent() == null);
	}

	@Test
	public void testGetThreeMonthSupply() {
		assertTrue(storageUnit.getThreeMonthSupply() == null);
	}

	@Test
	public void testGetProductGroupName() {
		assertTrue(storageUnit.getProductGroupName() == null);
	}

	@Test
	public void testGetItemsString() {
		List<Item> items = storageUnit.getItems("bagel");
		assertTrue("must contain item", items.contains(item));
	}

	@Test
	public void testRemoveItem() {
		storageUnit.removeItem(item.getBarcode());
		assertTrue("must not have any items", storageUnit.getItems().isEmpty());
	}

}
