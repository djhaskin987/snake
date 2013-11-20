package model;

import java.io.Serializable;
import java.util.zip.DataFormatException;

/**
 * Special case of Date that must be past 2000 and not in the future
 * 
 * @author Daniel Carrier
 *
 */
public class ValidDate extends Date implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -676496083906678513L;

	private void validate() throws InvalidHITDateException {
		try {
			if(this.compareTo(new Date(1,1,2000)) < 0) {
				throw new InvalidHITDateException("Error: Date should not be before January" +
			            " 1st, 2000.");
			}
		} catch (DateDoesNotExistException e) {
			//This is impossible. Date(1,1,2000) is valid.
			e.printStackTrace();
		}
		if(this.compareTo(new Date()) == 1) {
			throw new InvalidHITDateException("Error: Date should not be in the future.");
		}
	}
	
	/**
	 * @param day		Day of the month
	 * @param month		Month of the year
	 * @param year		Year AD
	 * @throws DateDoesNotExistException 
	 * @throws DataFormatException	If it's before January 1st, 2000, or it's in the future
	 * @throws InvalidDateException 
	 */
	public ValidDate(int month, int day, int year) throws InvalidHITDateException,
           DateDoesNotExistException {
		super(month, day, year);
		validate();
	}
	
	public ValidDate(java.util.Date date) throws InvalidHITDateException {
		super(date);
		validate();
	}
	
	/**
	 * Returns today.
	 */
	public ValidDate() {
		super();
	}

	public static boolean isValid(java.util.Date date) {
		if(date == null) {
			return false;
		}
		try {
			if(date.after(new Date(1,1,2000).toJavaUtilDate())) {
				return false;
			}
		} catch (DateDoesNotExistException e) {
			//This is impossible. That date exists.
			e.printStackTrace();
		}
		if(date.before(new java.util.Date())) {
			return false;
		}
		return true;
	}

}
