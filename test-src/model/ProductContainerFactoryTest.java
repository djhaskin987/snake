package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProductContainerFactoryTest {
	private ProductContainerFactory factory;
	@Before
	public void setUp() throws Exception {
		factory =
				new ProductContainerFactory();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateStorageUnit() {
		
		StorageUnit s;
		try {
			s = (StorageUnit) factory.createStorageUnit("Hello");
			assertTrue(s != null);
		}
		catch (ClassCastException e)
		{
			fail("Should have returned a storage unit instance");
		}
		ProductGroup p;
		try {
			p = (ProductGroup) factory.createStorageUnit("Goodbye");
			fail("Should have thrown an exception");
		}
		catch (ClassCastException e) {}
	}

	@Test
	public void testCreateProductGroup() {
		
		StorageUnit s = null;
		try {
			s = (StorageUnit) factory.createProductGroup("Hello");
			fail("Should have thrown an exception");
		}
		catch (ClassCastException e) {}
		ProductGroup p;
		try {
			p = (ProductGroup) factory.createProductGroup("Goodbye");
			assertTrue(p != null);
		}
		catch (ClassCastException e) {
			fail("Should have returned a storage unit instance");
		}
	}
}
