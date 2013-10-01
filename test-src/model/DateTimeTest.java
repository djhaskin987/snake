package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateTimeTest {

	@Test
	public void test() {
		DateTime before = new DateTime();
		DateTime after = new DateTime();
		assertTrue(before.compareTo(after) <= 0);
		assertTrue(after.compareTo(before) == -before.compareTo(after));
		before = new DateTime();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		after = new DateTime();
		assertTrue(before.compareTo(after) < 0);
		assertTrue(after.compareTo(before) == -before.compareTo(after));
	}

}
