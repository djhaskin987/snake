package model;

import java.io.Serializable;

/**
 * The 	Barcode class represents a physical barcode that can be found on most purchased items.
 * Barcode conforms to UPC-A standard (see http://en.wikipedia.org/wiki/Universal_Product_Code)
 * @author Kevin
 *
 */

public class Barcode implements Serializable {
	private String barcode;
	
	/*
	 * The constructor will make sure that the string passed in is a valid Barcode string
	 * A valid Barcode string must be numeric and be 12 digits.
	 */
	public Barcode(String barcode) {
		
	}
	
	/**
	 * Checks barcode string for conformity to UPC-A standard. That is, the input string
	 * is 12 characters in length and numeric.
	 * 
	 * @pre string is not null
	 * @param barcode the barcode string
	 * @return true if barcode is valid
	 */
	public static boolean isValidBarcode(String barcode) {
		return false;
	}
	
	/**
	 * Gets the barcode string.
	 * @return the barcode string
	 */
	public String getBarcode() {
		return null;
	}
	
	/**
	 * Sets the barcode string.
	 * 
	 * @pre barcode is not null and is a valid barcode
	 * @param barcode the barcode string
	 */
	public void setBarcode(String barcode) {
	}

	/**
	 * @pre other is not null
	 * @post 
	 */
	@Override
	public boolean equals(Object other) {
		return false;
	}
	
	@Override
	public int hashCode() {
		return -1;
	}
	
	public String toString() {
		return null;
	}

}
