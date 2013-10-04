package model;

/**
 * @author Daniel Carrier
 * Exception thrown when trying to construct an impossible day, like January
 * 50th, or something in year zero.
 * 
 */
public class DateDoesNotExistException extends Exception {
	/**
	 * @pre		None
	 * @post	None
	 */
	public DateDoesNotExistException() {
		super();
	}
}
