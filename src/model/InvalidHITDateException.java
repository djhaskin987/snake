package model;

/**
 * @author Daniel Carrier
 * Exception thrown when constructing a ValidDate that is before 2000, or in the future.
 * Needs a better name
 */
public class InvalidHITDateException extends Exception {
	/**
	 * @param s
	 */
	InvalidHITDateException(String s) {
		super(s);
	}
}
