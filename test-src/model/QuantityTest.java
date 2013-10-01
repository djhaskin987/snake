package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sun.management.counter.Units;

public class QuantityTest {
	Quantity quantity;
	@Before
	public void setUp() throws Exception {
		quantity = new Quantity(2.0, Unit.G);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = model.InvalidQuantityException.class)
	public void testQuantity() {
		Quantity instance = new Quantity(2.0, Unit.COUNT);
	}

	@Test
	public void testGetValue() {
		Double expected = 2.0;
		Double actual = quantity.getValue();
		assertEquals("values must be equal", expected, actual);
	}

	@Test
	public void testSetValue() {
		Double expected = 2.0;
		quantity.setValue(expected);
		Double actual = quantity.getValue();
		assertEquals("values must be equal", expected, actual);
	}

	@Test
	public void testGetUnit() {
		Unit expected = Unit.G;
		Unit actual = quantity.getUnit();
		assertEquals("unit must be equal", expected, actual);
	}

	@Test(expected = model.InvalidQuantityException.class)
	public void testSetUnit() {
		Unit expected = Unit.FLOZ;
		quantity.setUnit(expected);
		Unit actual = quantity.getUnit();
		assertEquals("unit must be equal", expected, actual);
		Unit bad = Unit.COUNT;
		quantity.setUnit(bad);
	}

}
