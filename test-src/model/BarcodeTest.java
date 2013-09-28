package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * BarcodeTest tests Barcode class
 * 
 * @author Nathan
 *
 */
public class BarcodeTest {
	private Barcode barcode;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * constructs a new barcode
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		barcode = new Barcode("123456789999");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * tests hashcode
	 */
	@Test
	public void testHashCode() {		
		final String uuid = "7247bc6f-6b5b-4833-ba21-a1af972da4af";
		String barcodeStr = "123456789999";
		int actual = uuid.hashCode() ^ barcodeStr.hashCode();
		int expected = barcode.hashCode();
		assertEquals("Hash codes must be the same", expected, actual);
	}

	/**
	 * tests if constructor throws an error
	 */
	@Test(expected = model.InvalidBarcodeException.class)
	public void testBarcode() {
		Barcode barcode = new Barcode("not a valid barcode");
	}
	
	/**
	 * tests barcode validator
	 */
	@Test
	public void testIsValidBarcode() {
		String validBarcode = "123456789999";
		boolean result = Barcode.isValidBarcode(validBarcode);
		assertTrue("Barcode should be valid.", result);
		
		String invalidBarcode = "not a valid barcode";
		result = Barcode.isValidBarcode(invalidBarcode);
		assertTrue("Barcode should not be valid.", result == false);
	}

	/**
	 * tests getter
	 */
	@Test
	public void testGetBarcode() {
		String expected = "123456789999";
		String actual = barcode.getBarcode();
		assertEquals("must be equal to expected value.", expected, actual);
	}

	/**
	 * tests setter
	 */
	@Test
	public void testSetBarcode() {
		String expected = "123456789998";
		barcode.setBarcode(expected);
		String actual = barcode.getBarcode();
		assertEquals("not set to correct value", expected, actual);
	}

	/**
	 * tests for equality
	 */
	@Test
	public void testEquals() {
		Barcode other = new Barcode("123456789999");
		boolean result = barcode.equals(other);
		assertTrue("must be equal to other Barcode", result);
		other = new Barcode("123456789998");
		result = barcode.equals(other);
		assertTrue("must be not be equal to other barcode", result == false);
	}

}
