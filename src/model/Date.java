import java.util.Calendar;

/**
 * @author Daniel Carrier
 *
 */

public class Date implements Comparable<Date> {
	private int day;
	private int month;
	private int year;
		
	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
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
		
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	/**
	 * Returns today.
	 */
	public Date() {
		day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		month = Calendar.getInstance().get(Calendar.MONTH);
		year = Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * Returns the date in mm/dd/yyyy
	 */
	@Override
	public String toString() {
		return month + "/" + day + "/" + year;
	}

	@Override
	public int compareTo(Date arg0) {
		if(year > arg0.getYear()) {
			return 1;
		} else if(year < arg0.getYear()) {
			return -1;
		} else if(month > arg0.getMonth()) {
			return 1;
		} else if(month < arg0.getMonth()) {
			return -1;
		} else if(day > arg0.getDay()) {
			return 1;
		} else if(day < arg0.getDay()) {
			return -1;
		} else {
			return 0;
		}
	}
	
}