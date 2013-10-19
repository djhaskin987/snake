package model;

import java.io.Serializable;
/**
 * This is a Wrapper class to help enforce contracts throughout the program
 * that require non-empty strings
 * @author Daniel Jay Haskin
 * {@invariant internal string value is immutable and can only be set
 *   at construction time.}
 */
public class NonEmptyString implements Serializable, Comparable<NonEmptyString> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4693405565781696366L;
	private final String value;
	
	/**
	 * The constructor ensures that the String is non-empty
	 * {@pre value is non-empty and non-null.}
	 * {@post the NonEmptyString's value is set to the string given by the 'value' parameter.}
	 * @param value
	 */
	public NonEmptyString(String value){
		if (value == null || value.equals(""))
		{
			throw new IllegalArgumentException("NonEmptyString must receive a non-empty string!");
		}
		this.value = value;
	}
	
	/** Returns the value of the NonEmptyString.
	 * {@pre The NonEmptyString is properly constructed.}
	 * {@post The NonEmptyString remains unchanged.}
	 * @return the value of the NonEmptyString.
	 */
	public final String getValue(){
		return value;
	}
	
	/** Returns the value of the NonEmptyString.
	 * {@pre The NonEmptyString is properly constructed.}
	 * {@post The NonEmptyString remains unchanged.}
	 * @return the value of the NonEmptyString.
	 */
	public final String toString(){
		return value;
	}
	
	/** Returns the hash code of the underlying string.
	 * {@pre The NonEmptyString is properly constructed.}
	 * {@post The NonEmptyString remains unchanged.}
	 * @return The hash code of the non-empty string's value.
	 */
	@Override
	public int hashCode(){
		return getValue().hashCode();
	}
	
	/** Returns whether the object 'other' and this object are equal.
	 * This means they must both be NonEmptyStrings and have the same
	 * underlying string value.
	 * {@pre The NonEmptyString is properly constructed. 'other' is not null.}
	 * {@post The NonEmptyString remains unchanged.}
	 * @return Whether the two objects are instanceof NonEmptyString 
	 *   and if their underlying values are equal.
	 */
	
	@Override
	public boolean equals(Object other)
	{
		if (other instanceof NonEmptyString){
			return ((NonEmptyString)other).getValue() ==
					getValue();
		}
		return false;
	}
	
	public int compareTo(NonEmptyString other)
	{
		return value.compareTo(other.value);
	}
}