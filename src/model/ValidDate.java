package model;

import java.io.Serializable;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * @author Daniel Carrier
 *
 */
public class ValidDate extends Date implements Serializable{

	/**
	 * @param day		Day of the month
	 * @param month		Month of the year
	 * @param year		Year AD
	 * @throws DateDoesNotExistException 
	 * @throws DataFormatException	If it's before January 1st, 2000, or it's in the future
	 * @throws InvalidDateException 
	 */
	public ValidDate(int month, int day, int year) throws InvalidHITDateException, DateDoesNotExistException {
		super(month, day, year);
		if(this.compareTo(new Date(1,1,2000)) == -1) {
			throw new InvalidHITDateException("Error: Date should not be before January 1st, 2000.");
		}
		if(this.compareTo(new Date()) == 1) {
			throw new InvalidHITDateException("Error: Date should not be in the future.");
		}
	}
	
	/**
	 * Returns today.
	 */
	public ValidDate() {
		super();
	}

}
