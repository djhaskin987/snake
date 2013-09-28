package model;

/**
 * Exception for class Barcode. Thrown when barcode is invalid.
 * @see Barcode
 */
public class InvalidBarcodeException extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * initializes exception
	 * @param s exception message
	 */
	public InvalidBarcodeException(String s)
	{
		super(s);
	}
}
