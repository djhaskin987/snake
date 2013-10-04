package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ProductGroupsTest {

	ProductGroup productGroup;
	Product product;
	Item item;
	
	@Before
	public void setUp() throws Exception {
		productGroup = new ProductGroup(new NonEmptyString("testPG"));
		product = new Product(new Barcode(), new NonEmptyString("bagel"), new Quantity(1.0, Unit.COUNT), 1, 1, new ArrayList<ProductContainer>());
		Barcode barcode = new Barcode();
		Date expireDate = new Date();
		item = new Item(product, barcode, expireDate, productGroup);
		productGroup.add(item);
	}

	@Test
	public void testGetProducts() {
		Collection<Product> pc = productGroup.getProducts();
		assertTrue("should contain product", pc.contains(product));
	}

	@Test
	public void testContains() {
		assertTrue("should contain product", productGroup.contains(product));
	}

	@Test
	public void testAddItem() {
		Barcode barcode = new Barcode();
		Date expireDate = new Date();
		Item item = new Item(product, barcode, expireDate, productGroup);
		productGroup.add(item);
		Collection<Item> items = productGroup.getItems();
		assertTrue("should contain item", items.contains(item));
	}

	@Test
	public void testAddProductGroup() {
		ProductGroup pg = new ProductGroup(new NonEmptyString("testPG"));
		productGroup.addProductGroup(pg);
		Collection<ProductGroup> pgCollection = productGroup.getProductGroups();
		assertTrue("should contain a product group", pgCollection.contains(pg));
	}

	@Test
	public void testDeleteProductContainer() {
		ProductGroup pg = new ProductGroup(new NonEmptyString("testPG"));
		productGroup.addProductGroup(pg);
		productGroup.deleteProductContainer("testPG");
		Collection<ProductGroup> pgCollection = productGroup.getProductGroups();
		assertTrue("should not contain a product group", !pgCollection.contains(pg));
	}

	@Test
	public void testSetProductContainer() {
		ProductGroup pg = new ProductGroup(new NonEmptyString("testPG"));
		productGroup.addProductGroup(pg);
		ProductGroup pg2 = new ProductGroup(new NonEmptyString("testPG"));
		Product product = new Product(new Barcode(), new NonEmptyString("bagel"), new Quantity(1.0, Unit.COUNT), 1, 1, new ArrayList<ProductContainer>());
		pg2.add(new Item(product, new Barcode(), new Date(), pg2));
		productGroup.setProductContainer("testPG", pg2);
		Collection<ProductGroup> pgCollection = productGroup.getProductGroups();
		assertTrue("must contain the last item", pgCollection.contains(pg2));
	}

	@Test
	public void testTransferItem() {
		ProductGroup su = new ProductGroup(new NonEmptyString("test"));
		productGroup.transferItem(item, su);
		assertTrue("item not transferred", !su.getItems().isEmpty() && productGroup.getItems().isEmpty());
	}

	@Test
	public void testTransferProduct() {
		ProductGroup su = new ProductGroup(new NonEmptyString("test"));
		productGroup.transferProduct(product, su);
		assertTrue("product not transferred", !su.getProducts().isEmpty());
	}

	@Test
	public void testWhoHasProduct() {
		IProductContainer pc = productGroup.whoHasProduct("bagel");
		assertTrue("storage unit has this product", pc == productGroup);
	}

	@Test
	public void testGetParent() {
		assertTrue(productGroup.getParent() == null);
	}

	@Test
	public void testGetUnit() {
		assertTrue(productGroup.getParent() == null);
	}


	@Test
	public void testGetProductGroupName() {
		assertTrue(productGroup.getProductGroupName() == "testPG");
	}

	@Test
	public void testGetItemsString() {
		List<Item> items = productGroup.getItems("bagel");
		assertTrue("must contain item", items.contains(item));
	}

	@Test
	public void testRemoveItem() {
		productGroup.removeItem(item.getBarcode());
		assertTrue("must not have any items", productGroup.getItems().isEmpty());
	}
}
