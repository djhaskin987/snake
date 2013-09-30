package model;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Daniel Carrier
 *
 */

public class Date extends AbstractDateTime {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1147204010429436435L;
	
	private GregorianCalendar calendar;

	/**
	 * @pre		this is not null
	 * 
	 */
	public Calendar getCalendar() {
		return calendar;
	}
		
	/**
	 * @pre		Date is not null
	 * @post	Returns the day of the month
	 * @return	The day of the month
	 */
	public int getDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @pre		Date is not null
	 * @post	Returns the month as an int
	 * @return	The month as an int
	 */
	public int getMonth() {
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * @pre		Date is not null
	 * @post	Returns the year
	 * @return	The year
	 */
	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}
	
	public Date() {
		calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * @param day	Day of the month
	 * @param month	Month of the year
	 * @param year	Year AD
	 * @throws DateDoesNotExistException If the day and month do not occur during that year, or the year is before 1.
	 */
	public Date(int month, int day, int year) throws DateDoesNotExistException {
		if(year < 1)
			throw new DateDoesNotExistException();
		if(day < 1)
			throw new DateDoesNotExistException();
		switch(month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if(day > 31)
				throw new DateDoesNotExistException();
			break;
		case 2:
			if((year%4 == 0 && year%100 != 0) || year%400 == 0) {
				//Every four years is a leap year, except one is skipped every century, except they do it anyway every four centuries.
				if(day > 29)
					throw new DateDoesNotExistException();
			} else {
				if(day > 28)
					throw new DateDoesNotExistException();
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			if(day > 30)
				throw new DateDoesNotExistException();
			break;
		default:
			throw new DateDoesNotExistException();
		}
		
		calendar = new GregorianCalendar(year, month, day);
	}
	
	public String toString() {
		return getMonth() + "/" + getDay() + "/" + getYear();
	}
	
	public static void main() {
		System.out.println("Hello, world.");
	}
	
}