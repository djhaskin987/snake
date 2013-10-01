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
	 * serial version uid
	 */
	private static final long serialVersionUID = -3892023010991204740L;

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
