package common;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringOpsTest {

	@Test
	public void testIsNumeric() {
		String bad = "java";
		boolean result = StringOps.isNumeric(bad);
		assertTrue("is not a numeric", result == false);
		
		String good = "123456";
		result = StringOps.isNumeric(good);
		assertTrue("is a numeric", result);
	}

}
