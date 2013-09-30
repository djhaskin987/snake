package model;

import static org.junit.Assert.*;

import java.util.List;

import model.IProduct.InvalidIntegerException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IProductTest {

	@Before
	public void setUp() throws Exception {

		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructorTest() {
		Barcode barcode = new Barcode("123456789012");
		NonEmptyString description = new NonEmptyString("Can of Coca-Cola");
		Quantity itemSize = new Quantity(12.5, Unit.FLOZ);
		Integer shelfLife = 0;
		Integer threeMonthSupply = 0;
		List<ProductContainer> containers = null;
		
		IProduct test= ProductFactory.getInstance().createInstance(barcode, description, itemSize, shelfLife, threeMonthSupply, containers);
		assertTrue(test.getDescription().toString().equals("Can of Coca-Cola"));
		assertTrue(test.getBarcode().getBarcode().toString().equals("123456789012"));
		assertTrue(test.getItemSize().getUnit().equals(Unit.FLOZ));
	}
	
	@Test
	public void setShelfLifeTest(){
		Barcode barcode = new Barcode("123456789012");
		NonEmptyString description = new NonEmptyString("Can of Coca-Cola");
		Quantity itemSize = new Quantity(12.5, Unit.FLOZ);
		Integer shelfLife = 0;
		Integer threeMonthSupply = 0;
		List<ProductContainer> containers = null;
		
		IProduct test= ProductFactory.getInstance().createInstance(barcode, description, itemSize, shelfLife, threeMonthSupply, containers);
		
		assertTrue(test.getShelfLife() == 0);
		try {
			test.setShelfLife(-3);
			fail();
		} catch (InvalidIntegerException e) {
			assertTrue(true);
		}
		try {
			test.setShelfLife(3);
			assertTrue(test.getShelfLife() == 3);
		} catch (InvalidIntegerException e) {
			fail();
		}
		
	}
	
	@Test
	public void setThreeMonthSupplyTest(){
		Barcode barcode = new Barcode("123456789012");
		NonEmptyString description = new NonEmptyString("Can of Coca-Cola");
		Quantity itemSize = new Quantity(12.5, Unit.FLOZ);
		Integer shelfLife = 0;
		Integer threeMonthSupply = 0;
		List<ProductContainer> containers = null;
		
		IProduct test= ProductFactory.getInstance().createInstance(barcode, description, itemSize, shelfLife, threeMonthSupply, containers);
		
		assertTrue(test.getThreeMonthSupply() == 0);
		try {
			test.setThreeMonthSupply(-3);
			fail();
		} catch (InvalidIntegerException e) {
			assertTrue(true);
		}
		try {
			test.setThreeMonthSupply(3);
			assertTrue(test.getThreeMonthSupply() == 3);
		} catch (InvalidIntegerException e) {
			fail();
		}
	}
	
	@Test
	public void setDescriptionTest(){
		Barcode barcode = new Barcode("123456789012");
		NonEmptyString description = new NonEmptyString("Can of Coca-Cola");
		Quantity itemSize = new Quantity(12.5, Unit.FLOZ);
		Integer shelfLife = 0;
		Integer threeMonthSupply = 0;
		List<ProductContainer> containers = null;
		
		IProduct test= ProductFactory.getInstance().createInstance(barcode, description, itemSize, shelfLife, threeMonthSupply, containers);
		assertTrue(test.getDescription().toString().equals("Can of Coca-Cola"));
		test.setDescription(new NonEmptyString("Can of Pepsi"));
		assertTrue(test.getDescription().toString().equals("Can of Pepsi"));
		
	}
}

