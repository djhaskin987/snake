package model;

import java.io.Serializable;

import common.StringOps;

/**
 * The 	Barcode class represents a physical barcode that can be found on most purchased items.
 * Barcode conforms to UPC-A standard (see http://en.wikipedia.org/wiki/Universal_Product_Code)
 * 
 * @author Nathan
 */

public class Barcode implements Serializable {
	/**
	 * The bar code
	 */
	private String barcode;
	
	/**
	 * The constructor will make sure that the string passed in is a valid Barcode string
	 * 
	 * 
	 * @param barcode the Barcode
	 * 
	 * @throws model.InvalidBarcodeException if barcode is not valid.
	 * 
	 * {@pre barcode is valid. A valid Barcode string must be numeric and be 12 digits.}
	 * 
	 * {@post barcode != null}
	 */
	public Barcode(String barcode) {
		checkBarcode(barcode);
		this.barcode = barcode;
	}
	
	/**
	 * Checks to see if barcode is valid
	 * 
	 * @param barcode the bar code
	 * 
	 * {@pre barcode is valid. A valid Barcode string must be numeric and be 12 digits.}
	 * {@post throws InvalidBarcodeException if preconditions not met} 
	 */
	private static void checkBarcode(String barcode)
	{
		if (!Barcode.isValidBarcode(barcode))
			throw new InvalidBarcodeException("barcode '" + barcode + "' is not a valid barcode.");
	}
	
	/**
	 * Checks barcode string for conformity to UPC-A standard. That is, the input string
	 * is 12 characters in length and numeric.
	 * 
	 * @param barcode the barcode string
	 *
	 * @return true if barcode is valid
	 * 
	 * {@pre barcode != null}
	 * 
	 * {@post barcode is valid. A valid Barcode string must be numeric and be 12 digits.}
	 * 
	 */
	public static boolean isValidBarcode(String barcode) {
		return (barcode != null && barcode.length() == 12 && StringOps.isNumeric(barcode));
	}
	
	/**
	 * Gets the barcode string.
	 * 
	 * @return the barcode string
	 * 
	 * {@pre barcode != null}
	 * 
	 * {@post returns instance of barcode string}
	 */
	public String getBarcode() {
		return this.barcode;
	}
	
	/**
	 * Sets the barcode string.
	 * 
	 * @param barcode the barcode string
	 * 
	 * {@pre barcode != null and is a valid barcode}
	 * 
	 * {@post barcode string is set}
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/**
	 * Checks to see if Barcode is equal to an Object
	 * 
	 * @param object an object to compare
	 * 
	 * @return true if object is equal to current barcode
	 *
	 * {@pre other != null}
	 * 
	 * {@post true if other is a Barcode and equal to barcode}
	 */
	@Override
	public boolean equals(Object other) {
		if (other.getClass() == Barcode.class) {
			return (this.barcode == ((Barcode)other).barcode);
		}
		else return false;
	}
	
	/**
	 * Gets the hashcode for Barcode
	 * 
	 * @return the hash code
	 *
	 * {@pre barcode is not null}
	 *
	 * {@post returns unique hash for barcode object}
	 */
	@Override
	public int hashCode() {
		final String uuid = "7247bc6f-6b5b-4833-ba21-a1af972da4af";
		return uuid.hashCode() ^ this.barcode.hashCode();
	}
}
