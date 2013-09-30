package model;

import java.io.Serializable;
/**
 * This is a Wrapper class to help enforce contracts throughout the program
 * that require non-empty strings
 * @author Kevin
 *
 */
public class NonEmptyString implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4693405565781696366L;
	private final String value;
	
	/**
	 * The constructor ensures that the String is non-empty
	 * @param value
	 */
	public NonEmptyString(String value){
		if (value == null || value.equals(""))
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
	
	@Override
	public int hashCode(){
		return getValue().hashCode();
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other instanceof NonEmptyString){
			return ((NonEmptyString)other).getValue() ==
					getValue();
		}
		return false;
	}
}