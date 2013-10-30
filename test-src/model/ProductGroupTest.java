package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class ProductGroupTest {

	ProductGroup productGroup;
	Product product;
	Item item;
	
	@Before
	public void setUp() throws Exception {
		productGroup = new ProductGroup(new NonEmptyString("testPG"));
		product = new Product(new NonEmptyString("123"), new NonEmptyString("bagel"), new Quantity(1.0, Unit.COUNT), 1, 1);
		Barcode barcode = new Barcode();
		//Date expireDate = new Date();
		item = new Item(product, barcode, productGroup);
		productGroup.add(item);
	}

	@Test
	public void testGetProducts() {
		Collection<IProduct> pc = productGroup.getProducts();
		assertTrue("should contain product", pc.contains(product));
	}

	@Test
	public void testContains() {
		assertTrue("should contain product", productGroup.contains(product));
	}

	@Test
	public void testAddItem() {
		Barcode barcode = new Barcode();
		//Date expireDate = new Date();
		Item item = new Item(product, barcode, productGroup);
		productGroup.add(item);
		Collection<IItem> items = productGroup.getItems(product);
		assertTrue("should contain item", items.contains(item));
	}

	@Test
	public void testAddProductGroup() {
		ProductGroup pg = new ProductGroup(new NonEmptyString("testPG"));
		productGroup.addProductContainer(pg);
		Collection<IProductContainer> pgCollection = productGroup.getProductContainers();
		assertTrue("should contain a product group", pgCollection.contains(pg));
	}

	@Test
	public void testDeleteProductContainer() {
		ProductGroup pg = new ProductGroup(new NonEmptyString("testPG"));
		productGroup.addProductContainer(pg);
		productGroup.deleteProductContainer("testPG");
		Collection<IProductContainer> pgCollection = productGroup.getProductContainers();
		assertTrue("should not contain a product group", !pgCollection.contains(pg));
	}

	@Test
	public void testSetProductContainer() {
		ProductGroup pg = new ProductGroup(new NonEmptyString("testPG"));
		productGroup.addProductContainer(pg);
		ProductGroup pg2 = new ProductGroup(new NonEmptyString("testPG"));
		Product product = new Product(new NonEmptyString("123"), new NonEmptyString("bagel"), new Quantity(1.0, Unit.COUNT), 1, 1);
		pg2.add(new Item(product, new Barcode(), pg2));
		productGroup.setProductContainer("testPG", pg2);
		Collection<IProductContainer> pgCollection = productGroup.getProductContainers();
		assertTrue("must contain the last item", pgCollection.contains(pg2));
	}

	@Test
	public void testTransferItem() {
		ProductGroup su = new ProductGroup(new NonEmptyString("test"));
		productGroup.transferItem(item, su);
		assertTrue("item not transferred", !su.getItems(product).isEmpty() && productGroup.getItems(product).isEmpty());
	}

	@Test
	public void testTransferProduct() {
		ProductGroup su = new ProductGroup(new NonEmptyString("test"));
		productGroup.transferProduct(product, su);
		assertTrue("product not transferred", !su.getProducts().isEmpty());
	}

	@Test
	public void testWhoHasProduct() {
		IProductContainer pc = productGroup.whoHasProduct(product);
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
		Collection<IItem> items = productGroup.getItems(product);
		assertTrue("must contain item", items.contains(item));
	}

	@Test
	public void testRemoveItem() {
		productGroup.removeItem(item.getBarcode());
		assertTrue("must not have any items", productGroup.getItems(product).isEmpty());
	}
}
