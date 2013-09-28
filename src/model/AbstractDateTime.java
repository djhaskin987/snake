package model;
import java.util.Calendar;

public abstract class AbstractDateTime implements Comparable<AbstractDateTime> {

	public abstract Calendar getCalendar();

	@Override
	public int compareTo(AbstractDateTime arg0) {
		return getCalendar().compareTo(arg0.getCalendar());
	}

}
