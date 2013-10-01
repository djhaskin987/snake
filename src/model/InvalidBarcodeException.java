package model;

/**
 * Exception for class Barcode. Thrown when barcode is invalid.
 * 
 * @see Barcode
 */
public class InvalidBarcodeException extends IllegalArgumentException {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 1213L;

	/**
	 * initializes exception
	 * @param s exception message
	 */
	public InvalidBarcodeException(String s)
	{
		super(s);
	}
}
