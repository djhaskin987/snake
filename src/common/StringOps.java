package common;

import java.nio.charset.Charset;

/**
 * Utility class providing String operations.
 */
public final class StringOps {

	/**
	 * Private Constructor.
	 */
	private StringOps() {
		assert false;
	}
	
	/**
	 * Returns true if string is null or empty, and false otherwise.
	 * 
	 * @param s String to be tested.
	 * 
	 * {@pre s != null}
	 * 
	 * {@post Returns true if string is null or empty, and false otherwise.}
	 */
	public static boolean isNullOrEmpty(String s) {
		return ((s == null) || (s.length() == 0));
	}
	
	/**
	 * Returns true if string is is a numeric. False otherwise.
	 * 
	 * @param s the string to be tested
	 * 
	 * @return true if string is is a numeric. False otherwise.
	 * 
	 * {@pre s != null}
	 * 
	 * {@post Returns true if string is a numeric. False othewise.}
	 */
	public static boolean isNumeric(String s) {
		if (s != null) {
			final byte ASCII_NUMERIC_MIN = 0x30;
			final byte ASCII_NUMERIC_MAX = 0x39;
			Charset cs = Charset.forName("US-ASCII");
			byte[] bytes = s.getBytes(cs);
			int i;
			for (i = 0; i < bytes.length; i++) {
				if (bytes[i] < ASCII_NUMERIC_MIN || bytes[i] > ASCII_NUMERIC_MAX)
					break;
			}
			if (i == bytes.length) {
				return true;
			}
		}
		return false;
	}
}

