import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;


/**
 * @author Daniel Carrier
 *
 */
public class ValidDateTest {

	@Test
	public void test() {
		try {
			try {
				new ValidDate(1,1,2000);
			} catch (InvalidHITDateException e) {
				fail("Threw an exception for 1/1/2000.");
			}
			try {
				new ValidDate(12,31,1999);
				fail("Failed to throw an exception for 12/31/1999.");
			} catch (InvalidHITDateException e) {
			}
			try {
				Date today = new Date();
				new ValidDate(today.getMonth(), today.getDay(), today.getYear());
			} catch (InvalidHITDateException e) {
				fail("Threw an exception for today.");
			}
			Date today = new Date();
			Date tomorrow;
			try {
				tomorrow = new Date(today.getMonth(), today.getDay()+1, today.getYear());
			} catch (DateDoesNotExistException e) {
				try {
					tomorrow = new Date(today.getMonth()+1, 1, today.getYear());
				} catch (DateDoesNotExistException e1) {	
					tomorrow = new Date(1, 1, today.getYear()+1);
				}
			}
			try {
				new ValidDate(tomorrow.getMonth(), tomorrow.getDay(), tomorrow.getYear());
				fail("Failed to throw an exception for next year.");
			} catch (InvalidHITDateException e) {
			}
		} catch (DateDoesNotExistException e) {
		}
	}
}
