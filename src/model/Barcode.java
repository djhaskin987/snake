package model;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Random;

import common.StringOps;

/**
 * The 	Barcode class represents a physical barcode that can be found on most purchased items.
 * Barcode conforms to UPC-A standard (see http://en.wikipedia.org/wiki/Universal_Product_Code)
 * 
 * @author Nathan
 */

public class Barcode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4269482204582670982L;
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
	 * {@pre barcode is valid. A valid Barcode string must be numeric and be 12 digits and have a valid check digit.}
	 * 
	 * {@post barcode != null}
	 */
	public Barcode(String barcode) {
		checkBarcode(barcode);
		this.barcode = barcode;
	}
	
	// generates a random barcode for testing
	Barcode()
	{
		final int TEN = 10;
		final int MAX_DIGITS = 12;
		Random rand = new Random();
		String sBarcode = "";
		for (int i = 0; i < MAX_DIGITS; i++)
			sBarcode += (rand.nextInt() % TEN);
		int checkBit = getExpectedCheckDigit(sBarcode);
		sBarcode = sBarcode.substring(0, MAX_DIGITS - 1) + checkBit;
		this.barcode = sBarcode;
	}
	
	/**
	 * Checks to see if barcode is valid
	 * 
	 * @param barcode the bar code
	 * 
	 * {@pre barcode is valid. A valid Barcode string must be numeric and be 12 digits and have a valid check digit.}
	 *
	 * {@post throws InvalidBarcodeException if preconditions not met} 
	 */
	private static void checkBarcode(String barcode) {
		if (!Barcode.isValidBarcode(barcode))
			throw new InvalidBarcodeException("barcode '" + barcode + "' is not a valid barcode.");
	}
	
	/**
	 * Checks barcode string for conformity to UPC-A standard. That is, the input string
	 * is 12 characters in length and numeric. It must also have a valid check digit
	 * (see http://en.wikipedia.org/wiki/Universal_Product_Code#Check_digits)
	 * 
	 * @param barcode the barcode string
	 *
	 * @return true if barcode is valid
	 * 
	 * {@pre barcode != null}
	 * 
	 * {@post barcode is valid. A valid Barcode string must be numeric and be 12 digits. Also, barcode must have a valid check digit}
	 * 
	 */
	public static boolean isValidBarcode(String barcode) {
		return (barcode != null && barcode.length() == 12 && StringOps.isNumeric(barcode) && hasValidCheckDigit(barcode));
	}
	
	
	/**
	 * Checks barcode string if it has a valid check digit.
	 * 
	 * @param barcode the barcode string
	 * 
	 * @return true if barcode has a valid check digit
	 * 
	 * {@pre barcode != null && barcode is numeric && barcode is 12 digits}
	 * 
	 * {@post boolean value indicating whether the check digit is valid}
	 */
	public static boolean hasValidCheckDigit(String barcode) {
		int expectedCheckDigit = getExpectedCheckDigit(barcode);
		int actualCheckDigit = getActualCheckDigit(barcode);
		return (expectedCheckDigit == actualCheckDigit);
	}
	
	private static int getActualCheckDigit(String barcode) {
		Charset cs = Charset.forName("US-ASCII");
		byte[] bytes = barcode.getBytes(cs);
		byte checkDigitByte = bytes[bytes.length - 1];
		int checkDigit = fromAscii(checkDigitByte);
		return checkDigit;
	}
	
	private static int getExpectedCheckDigit(String barcode) {
		Charset cs = Charset.forName("US-ASCII");
		byte[] bytes = barcode.getBytes(cs);
		int result = 0;
		
		// add the digits in the odd-numbered positions
		for (int i = 0; i < (bytes.length - 1); i += 2) {
			byte b = bytes[i];
			int value = fromAscii(b);
			result += value;
		}
		// multiply by three
		final int THREE = 3;
		result *= THREE;
		
		// add the digits in the even-numbred positions
		for (int i = 1; i < (bytes.length - 2); i += 2) {
			byte b = bytes[i];
			int value = fromAscii(b);
			result += value;
		}
		
		// find the result modulo 10
		final int TEN = 10;
		result = result % TEN;
		
		// if the result is non-zero subtract from ten
		if (result > 0)
			result = TEN - result;
		
		return result;
	}
	
	private static int fromAscii(byte b) {
		return (b - StringOps.ASCII_NUMERIC_MIN);
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
