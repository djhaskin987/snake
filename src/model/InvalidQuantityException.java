package model;

/**
 * Exception for Quantity class.
 * 
 * @see Quantity
 * 
 * @author nstandif
 *
 */
public class InvalidQuantityException extends IllegalArgumentException {

	/**
	 * Creates a new InvalidQuantityException instance
	 * 
	 * @param message the message to display
	 * 
	 * {@pre message != null}
	 * 
	 * {@post InvalidQuantityException instance}
	 */
	public InvalidQuantityException(String message) {
		super(message);
	}

}
