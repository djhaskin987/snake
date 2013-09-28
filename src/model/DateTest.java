package model;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * @author Daniel Carrier
 *
 */
public class DateTest {

	@Test
	public void test() {
		Date today = new Date();
		try {
			new Date(today.getMonth(), today.getDay(), today.getYear());
		} catch (Exception e) {
			fail("Claims today is invalid.");
		}
		try {
			new Date(1,1,1);
		} catch (Exception e) {
			fail("Claims 1/1/0001 is invalid.");
		}
		try {
			new Date(1,1,0);
			fail("Failed to throw exception for year zero.");
		} catch (Exception e) {
			System.out.println(e.getClass());
		}
		try {
			new Date(0,1,1);
			new Date(1,50,1);
			fail("Failed to throw exception for month zero.");
		} catch (Exception e) {
		}
		try {
			new Date(1,0,1);
			fail("Failed to throw exception for day zero.");
		} catch (Exception e) {
		}
		try {
			new Date(13,1,1);
			fail("Failed to throw exception for Undecimber.");
		} catch (Exception e) {
		}

		try {
			new Date(1,32,1);
			fail("Failed to throw exception for 1/32/1.");
		} catch (Exception e) {
		}
		try {
			new Date(3,32,1);
			fail("Failed to throw exception for 3/32/1.");
		} catch (Exception e) {
		}
		try {
			new Date(4,31,1);
			fail("Failed to throw exception for 4/31/1.");
		} catch (Exception e) {
		}
		try {
			new Date(5,32,1);
			fail("Failed to throw exception for 5/32/1.");
		} catch (Exception e) {
		}
		try {
			new Date(6,31,1);
			fail("Failed to throw exception for 6/31/1.");
		} catch (Exception e) {
		}
		try {
			new Date(7,32,1);
			fail("Failed to throw exception for 7/32/1.");
		} catch (Exception e) {
		}
		try {
			new Date(8,32,1);
			fail("Failed to throw exception for 8/32/1.");
		} catch (Exception e) {
		}
		try {
			new Date(9,31,1);
			fail("Failed to throw exception for 9/31/1.");
		} catch (Exception e) {
		}
		try {
			new Date(10,32,1);
			fail("Failed to throw exception for 10/32/1.");
		} catch (Exception e) {
		}
		try {
			new Date(11,31,1);
			fail("Failed to throw exception for 11/31/1.");
		} catch (Exception e) {
		}
		try {
			new Date(12,32,1);
			fail("Failed to throw exception for 12/32/1.");
		} catch (Exception e) {
		}

		try {
			new Date(1,31,1);
		} catch (Exception e) {
			fail("Threw exception for 1/31/1.");
		}
		try {
			new Date(3,31,1);
		} catch (Exception e) {
			fail("Threw exception for 3/31/1.");
		}
		try {
			new Date(4,30,1);
		} catch (Exception e) {
			fail("Threw exception for 4/30/1.");
		}
		try {
			new Date(5,31,1);
		} catch (Exception e) {
			fail("Threw exception for 5/31/1.");
		}
		try {
			new Date(6,30,1);
		} catch (Exception e) {
			fail("Threw exception for 6/30/1.");
		}
		try {
			new Date(7,31,1);
		} catch (Exception e) {
			fail("Threw exception for 7/31/1.");
		}
		try {
			new Date(8,31,1);
		} catch (Exception e) {
			fail("Threw exception for 8/31/1.");
		}
		try {
			new Date(9,30,1);
		} catch (Exception e) {
			fail("Threw exception for 9/30/1.");
		}
		try {
			new Date(10,31,1);
		} catch (Exception e) {
			fail("Threw exception for 10/31/1.");
		}
		try {
			new Date(11,30,1);
		} catch (Exception e) {
			fail("Threw exception for 11/30/1.");
		}
		try {
			new Date(12,31,1);
		} catch (Exception e) {
			fail("Threw exception for 12/31/1.");
		}

		try {
			new Date(2,29,1);
			fail("Failed to throw exception for 2/29/1.");
		} catch (Exception e) {
		}
		try {
			new Date(2,28,1);
		} catch (Exception e) {
			fail("Threw exception for 2/28/1.");
		}

		try {
			new Date(2,30,4);
			fail("Failed to throw exception for 2/30/1 (leap year).");
		} catch (Exception e) {
		}
		try {
			new Date(2,29,4);
		} catch (Exception e) {
			fail("Threw exception for 2/29/1 (leap year).");
		}

		try {
			new Date(2,29,100);
			fail("Failed to throw exception for 2/29/100 (lop leap year).");
		} catch (Exception e) {
		}
		try {
			new Date(2,28,100);
		} catch (Exception e) {
			fail("Threw exception for 2/28/100 (lop leap year).");
		}

		try {
			new Date(2,30,400);
			fail("Failed to throw exception for 2/30/400 (loop lop leap year).");
		} catch (Exception e) {
		}
		try {
			new Date(2,29,400);
		} catch (Exception e) {
			fail("Threw exception for 2/29/400 (loop lop leap year).");
		}
		
		try {
			if(new Date(1,1,1).compareTo(new Date(1,1,1)) != 0) {
				fail("1/1/1 was not found to be equal to 1/1/1");
			}
			if(new Date(1,1,1).compareTo(new Date(1,1,2)) >= 0) {
				fail("1/1/1 was not found to be before to 1/1/2");
			}
			if(new Date(1,1,1).compareTo(new Date(2,1,1)) >= 0) {
				fail("1/1/1 was not found to be before to 2/1/1");
			}
			if(new Date(1,1,1).compareTo(new Date(1,2,1)) >= 0) {
				fail("1/1/1 was not found to be before to 1/2/1");
			}
			if(new Date(1,1,2).compareTo(new Date(1,1,1)) <= 0) {
				fail("1/1/1 was not found to be after to 1/1/2");
			}
			if(new Date(2,1,1).compareTo(new Date(1,1,1)) <= 0) {
				fail("1/1/1 was not found to be after to 2/1/1");
			}
			if(new Date(1,2,1).compareTo(new Date(1,1,1)) <= 0) {
				fail("1/1/1 was not found to be after to 1/2/1");
			}

			if(new Date(1,2,1).compareTo(new Date(2,1,1)) >= 0) {
				fail("1/2/1 was not found to be before to 2/1/1");
			}
			if(new Date(2,1,1).compareTo(new Date(1,1,2)) >= 0) {
				fail("2/1/1 was not found to be before to 1/1/2");
			}
			if(new Date(1,2,1).compareTo(new Date(1,1,2)) >= 0) {
				fail("1/2/1 was not found to be before to 1/1/2");
			}

			if(new Date(2,1,1).compareTo(new Date(1,2,1)) <= 0) {
				fail("2/1/1 was not found to be after to 1/2/1");
			}
			if(new Date(1,1,2).compareTo(new Date(2,1,1)) <= 0) {
				fail("1/1/2 was not found to be after to 2/1/1");
			}
			if(new Date(1,1,2).compareTo(new Date(1,2,1)) <= 0) {
				fail("1/1/2 was not found to be after to 1/2/1");
			}
			
			assertTrue(new Date(1,2,3).toString().equals("1/2/3"));
		} catch (Exception e) {
			fail("Thre an exception for a valid date.");
		}
	}

}
