package model;
import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Daniel Carrier
 *
 */
public abstract class AbstractDateTime implements Comparable<Object>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4496635728159932757L;

	/**
	 * @pre		None
	 * @post	Returns the the date and time as a java.util.Calendar
	 * @return	the date and time as a java.util.Calendar
	 */
	public abstract Calendar getCalendar();

	/**
	 * @pre		arg0 is an AbstractDateTime
	 * @post	Returns a negative integer if this is earlier than arg0,
	 * 	0 if they're equal, and a positive integer if this is greater than arg0
	 */
	public int compareTo(Object arg0) {
		return getCalendar().compareTo(((AbstractDateTime)arg0).getCalendar());
	}
}
