/**
 * 
 */
package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author djhaskin814
 *
 */
public class NonEmptyStringTest {

	
	private NonEmptyString a;
	private String aString;
	private NonEmptyString b;
	private String bString;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		aString = "Mary had a little lamb. I'm a long string.";
		bString = "I";
		a = new NonEmptyString(aString);
		b = new NonEmptyString(bString);
	}

	/**
	 * Test method for {@link model.NonEmptyString#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		assertEquals(a.hashCode(), aString.hashCode());
		assertEquals(b.hashCode(), bString.hashCode());
	}

	/**
	 * Test method for {@link model.NonEmptyString#NonEmptyString(java.lang.String)}.
	 */
	@Test
	public void testNonEmptyString() {
		try {
			new NonEmptyString(null);
			fail("Assertion was not thrown when given a null string.");
		}
		catch (IllegalArgumentException e) {}
		try {
			new NonEmptyString("");
			fail("Assertion was not thrown when given an empty string.");
		}
		catch (IllegalArgumentException e) {}
		try {
			new NonEmptyString("hello");
		}
		catch (Exception e)
		{
			fail("Exception thrown on a valid construction of NonEmptyString");
		}
	}

	/**
	 * Test method for {@link model.NonEmptyString#getValue()}.
	 */
	@Test
	public void testGetValue() {
		assertEquals(a.getValue(), aString);
		assertEquals(b.getValue(), bString);
	}

	/**
	 * Test method for {@link model.NonEmptyString#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(a.toString(), aString);
		assertEquals(b.toString(), bString);
	}

	/**
	 * Test method for {@link model.NonEmptyString#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		NonEmptyString aEquals = new NonEmptyString(aString);
		NonEmptyString bEquals = new NonEmptyString(bString);
		assertTrue(a.equals(aEquals));
		assertTrue(b.equals(bEquals));
		assertTrue(aEquals.equals(a));
		assertTrue(bEquals.equals(b));
		// Strings and NonEmptyStrings are distinct objects
		assertFalse(a.equals(aString));
		assertFalse(b.equals(bString));
		assertFalse(a.equals(b));
		assertFalse(b.equals(a));
	}
}
