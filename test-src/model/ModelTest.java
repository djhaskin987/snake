package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ModelTest {

	Model m;
	
	@Before
	public void setUp() throws Exception
	{
		m = Model.getInstance();
		
	}
	
	@Test
	public void testGetInstance() {
		assertTrue(m == Model.getInstance());
		assertTrue(Model.getInstance() != null);
	}

	@Test
	public void testGetStorageUnits() {
		StorageUnits s = m.getStorageUnits();
		assertTrue(s != null);
	}

	@Test
	public void testSetStorageUnits() {
		StorageUnits s = new StorageUnits();
		m.setStorageUnits(s);
		assertTrue(m.getStorageUnits() == s);
	}

	@Test
	public void testGetProductCollection() {
		assertTrue(m.getProductCollection() != null);
	}

	@Test
	public void testSetProductCollection() {
		ProductCollection p = new ProductCollection();
		m.setProductCollection(p);
		assertTrue(m.getProductCollection() == p);
	}

	@Test
	public void testGetProductContainerFactory() {
		assertTrue(m.getProductContainerFactory() != null);
	}

	@Test
	public void testGetProductFactory() {
		assertTrue(m.getProductFactory() != null);
	}

	@Test
	public void testGetItemFactory() {
		assertTrue(m.getItemFactory() != null);
	}
}
