package model;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

public class ProductGroupsTest {
	ProductGroups pg;
	
	@Before
	public void setUp() throws Exception {
		pg = new ProductGroups();
	}

	@Test
	public void testGetProductGroups() {
		Map<NonEmptyString, ProductGroup> map = pg.getProductGroups();
		assertTrue("must not be null", map != null);
	}

	@Test
	public void testSetProductGroup() {
		ProductGroup instance = new ProductGroup();
		pg.setProductGroup(instance.getName(), instance);
		Map<NonEmptyString, ProductGroup> map = pg.getProductGroups();
		assertTrue(map.containsKey(instance.getName()));
		assertTrue(map.containsValue(instance));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddAll() {
		pg.addAll(null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testClear() {
		pg.clear();
	}

	@Test
	public void testContains() {
		ProductGroup instance = new ProductGroup();
		pg.setProductGroup(instance.getName(), instance);
		assertTrue("must contain this", pg.contains(instance));
	}

	@Test(expected = NullPointerException.class)
	public void testContainsAll() {
		pg.containsAll(null);
	}

	@Test
	public void testIsEmpty() {
		assertTrue("must be empty", pg.isEmpty());
	}

	@Test
	public void testIterator() {
		Iterator<ProductGroup> itr = pg.iterator();
		assertTrue("must not be null", itr != null);
	}

	@Test
	public void testRemove() {
		ProductGroup instance = new ProductGroup();
		pg.add(instance);
		pg.remove(instance);
	}

	@Test
	public void testRemove2() {
		ProductGroup instance = new ProductGroup();
		pg.add(instance);
		pg.remove(instance.getName());
	}

	@Test
	public void testRemove3() {
		ProductGroup instance = new ProductGroup();
		pg.add(instance);
		pg.remove(instance.getName().toString());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveAll() {
		pg.removeAll(null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRetainAll() {
		pg.retainAll(null);
	}

	@Test
	public void testSize() {
		assertEquals("must be 0", 0, pg.size());
	}

	@Test
	public void testToArray() {
		assertTrue("must not be null", pg.toArray() != null);
	}

	@Test(expected = NullPointerException.class)
	public void testToArrayTArray() {
		pg.toArray(null);
	}
}
