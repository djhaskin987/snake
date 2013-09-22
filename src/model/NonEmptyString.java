package model;

import java.io.Serializable;
/**
 * This is a Wrapper class to help enforce contracts throughout the program
 * that require non-empty strings
 * @author Kevin
 *
 */
public class NonEmptyString implements Serializable {
	private String value;
	
	/**
	 * The constructor ensures that the String is non-empty
	 * @param value
	 */
	public NonEmptyString(String value){
		
	}
	
	public String getValue(){
		return null;
	}

	public void setValue(String value){
		
	}
}
