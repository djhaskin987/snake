package model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Daniel Carrier
 * DateTime gives the date and the time that it was constructed.
 *
 */
public class DateTime extends AbstractDateTime {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4159531343246193042L;
	
	private GregorianCalendar calendar;

	@Override
	public Calendar getCalendar() {
		return calendar;
	}
	
	/**
	 * {@pre	None}
	 * {@post	DateTime is set to the current date and time}
	 */
	public DateTime() {
		calendar = new GregorianCalendar();
	}
	
	public DateTime(Date date)
	{
		this();
		calendar.setTime(date);
	}
	
	/**
	 * {@pre	None}
	 * {@post	Returns the date and time in dd/mm/yyyy hh:mm:ss AM/PM format}
	 */
	public String toString() {
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);
		String ampm;
		if(calendar.get(Calendar.AM_PM) == Calendar.AM) {
			ampm = "AM";
		} else {
			ampm = "PM";
		}
		int hour = calendar.get(Calendar.HOUR);
		if(hour == 0) {
			hour = 12;
		}
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		return month + "/" + day + "/" + year + " " + hour + ":" + minute +
            ":" + second + " " + ampm;
	}
}
