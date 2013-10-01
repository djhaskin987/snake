package model;

/**
 * @author Daniel Carrier
 * Exception thrown when constructing a ValidDate that is before 2000, or in the future.
 * Needs a better name
 */
public class InvalidHITDateException extends Exception {

       private static final long serialVersionUID = -5196755021811211427L;
       /**
	 * @param s
	 */
	InvalidHITDateException(String s) {
		super(s);
	}
}
