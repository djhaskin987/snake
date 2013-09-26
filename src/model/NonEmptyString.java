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
		if (value.equals(""))
		{
			throw new IllegalArgumentException("NonEmptyString must receive a non-empty string!");
		}
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
	
	public String toString(){
		return value;
	}
	
	public int hashCode(){
		return value.hashCode();
	}

	public void setValue(String value){
		if (value == "")
		{
			throw new IllegalArgumentException("NonEmptyString must receive a non-empty string!");
		}
		this.value = value;
	}
}