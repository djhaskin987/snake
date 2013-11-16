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

	@Test
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

	@Test
	public void testSetUnit() {
		Unit expected = Unit.FLOZ;
		quantity.setUnit(expected);
		Unit actual = quantity.getUnit();
		assertEquals("unit must be equal", expected, actual);
		Unit bad = Unit.COUNT;
		quantity.setUnit(bad);
	}
	
	@Test
	public void testCanConvert()
	{
		assertTrue(Unit.COUNT.canConvert(Unit.COUNT));
		assertFalse(Unit.COUNT.canConvert(Unit.FLOZ));
		assertFalse(Unit.COUNT.canConvert(Unit.GAL));
		assertFalse(Unit.COUNT.canConvert(Unit.PINT));
		assertFalse(Unit.COUNT.canConvert(Unit.QUART));
		assertFalse(Unit.COUNT.canConvert(Unit.LITER));
		assertFalse(Unit.COUNT.canConvert(Unit.G));
		assertFalse(Unit.COUNT.canConvert(Unit.KG));
		assertFalse(Unit.COUNT.canConvert(Unit.LBS));
		assertFalse(Unit.COUNT.canConvert(Unit.OZ));
		assertTrue(Unit.G.canConvert(Unit.KG));
		assertTrue(Unit.LBS.canConvert(Unit.OZ));
		assertTrue(Unit.FLOZ.canConvert(Unit.LITER));
		assertTrue(Unit.LBS.canConvert(Unit.KG));
		assertFalse(Unit.G.canConvert(Unit.FLOZ));
		assertFalse(Unit.KG.canConvert(Unit.GAL));
		assertFalse(Unit.GAL.canConvert(Unit.G));
		assertFalse(Unit.KG.canConvert(Unit.FLOZ));
		assertFalse(Unit.KG.canConvert(Unit.COUNT));
		assertFalse(Unit.GAL.canConvert(Unit.COUNT));
	}
	
	@Test
	public void testConvert()
	{
		try {
			assertEquals(Unit.COUNT.convert(3.4, Unit.COUNT),3.4,0.0001);
		} catch (Exception e1) {
			fail("No exception should be thrown.");
		}
		try {
			Unit.COUNT.convert(1.0, Unit.FLOZ);
			fail("Unit count shouldn't convert anything.");
		}
		catch (Exception e)
		{
			
		}
		try {
			Unit.FLOZ.convert(1.0, Unit.LBS);
			fail("volume isn't weight.");
		}
		catch (Exception e)
		{
			
		}
		try {
			Unit.KG.convert(1.0,  Unit.LITER);
			fail("weight isn't volume.");
		}
		catch (Exception e)
		{
			
		}
		
		try {
			assertEquals(Unit.GAL.convert(1.0,Unit.LITER),0.264172,0.0001);
		}
		catch (Exception e)
		{
			fail("No exceptions should be thrown.");
		}
		
		try {
			assertEquals(Unit.LITER.convert(1.0,Unit.GAL),3.78541,0.0001);
		}
		catch (Exception e)
		{
			fail("No exceptions should be thrown.");
		}
		try {
			assertEquals(Unit.FLOZ.convert(1.0,  Unit.GAL),0.0078125, 0.0001);
		}
		catch (Exception e)
		{
			fail("No exceptions should be thrown.");
		}
		try {
			assertEquals(Unit.KG.convert(1000.0, Unit.G),1.0, 0.00001);
		}
		catch (Exception e)
		{
			fail("No exceptions should be thrown.");
		}
		try {
			assertEquals(Unit.LBS.convert(3.5, Unit.KG),7.71618,0.0001);
		}
		catch (Exception e)
		{
			fail("No exceptions should be thrown.");
		}
	}
}
