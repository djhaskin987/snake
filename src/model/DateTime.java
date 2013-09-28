package model;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateTime extends AbstractDateTime {
	private GregorianCalendar calendar;

	@Override
	public Calendar getCalendar() {
		return calendar;
	}
	
	public DateTime() {
		calendar = new GregorianCalendar();
	}
	
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
		return month + "/" + day + "/" + year + " " + hour + ":" + minute + ":" + second + " " + ampm;
	}
}
