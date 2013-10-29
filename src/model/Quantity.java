package model;

import java.io.Serializable;
import java.text.DecimalFormat;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
/**
 * Contains both numerical value and unit of measurement of
 * Product and Item unit quantities
 * 
 * @author Nathan Standiford
 */
public class Quantity implements Serializable {
	//TODO: Currently, this only allows a value of one if the unit is count.
	//That should be true in item, but in other places it only needs to be an integer.
	/**
	 * the serial version id
	 */
	private static final long serialVersionUID = 2290428901076814180L;

	/**
	 * The quantity value
	 */
	private Double value;
	
	/**
	 * the quantity unit
	 */
	private Unit unit;
	
	/**
	 * Quantity constructor.
	 * 
	 * @param value the value of the quantity
	 * 
	 * @param unit the unit to use
	 * 
	 * @throws QuantityNotValidException if value less than or equal to zero
	 * and unit is not Unit.COUNT or value is not 1.0 and unit is Unit.COUNT
	 * 
	 * 
	 * {@pre unit != null && value != null && value > 0 && (unit == Unit.COUNT &&
     * value == 1.0 || unit != Unit.COUNT)}
	 * 
	 * {@post a Quantity object}
	 */
	public Quantity(Double value, Unit unit) {
		checkQuantity(value, unit);
		this.value = value;
		this.unit = unit;
	}

	/**
	 * Checks to see if value and unit are valid for a given Quantity.
	 * 
	 * @param value the quantity value
	 * 
	 * @param unit the quantity unit
	 * 
	 * @return true if value and unit can be added safely to the quantity
	 * 
	 * {@pre value != null & unit != null}
	 * 
	 * {@post boolean indicating the validity of the arguments}
	 */
	public static boolean isValidQuantity(Double value, Unit unit)
	{
		return
				value != null
				&& value >= 0
				&& unit != null
				&& (unit == Unit.COUNT && value % 1.0 == 0
                	|| unit != Unit.COUNT);
		//return false;
	}
	
	private static void checkQuantity(Double value, Unit unit)
	{
		if (!isValidQuantity(value, unit)) {
			throw new InvalidQuantityException("Quantity '" + value + "' not valid for '"
                    + unit.toString() + "' unit");
		}
	}
	
	public static Quantity createInstance(String supplyValue, 
			String supplyUnit)
	{
		return new Quantity(Double.parseDouble(supplyValue),
							Unit.getInstance(supplyUnit));
	}
	/**
	 * Gets the value
	 * 
	 * @return the Quantity value
	 * 
	 * {@pre none }
	 * 
	 * {@post the value}
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * Setter for value variable
	 * 
	 * @param value the value (quantity) of the unit
	 * 
	 * {@pre value != null && value > 0 && unit != null && (this.unit ==
     * unit.COUNT && value == 1.0 || unit != Unit.COUNT)}
	 * 
	 * {@post this.value = value}
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * Getter for unit
	 * 
	 * @return the Quantity unit
	 * 
	 * {@pre unit != null}
	 * 
	 * {@post the unit}
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Setter for unit
	 * 
	 * @param unit the unit
	 * 
	 * {@pre unit != null && value != null && value > 0 && (this.unit ==
     * unit.COUNT && value == 1.0 || unit != Unit.COUNT)}
	 * 
	 * {@post this.unit = unit}
	 */
	public void setUnit(Unit unit) {
		checkQuantity(this.value, unit);
		this.unit = unit;
	}
	
	/**
	 * Gets a string representation of the quantity
	 * 
	 * @return the string representation of the quantity
	 * 
	 * {@pre unit != null && value != null}
	 * 
	 * {@post string of quantity}
	 */	
	public String toString()
	{
		return getValueString() + " " + unit.toString();
	}
	
	/**
	 * Gets a string representation of the value of the quantity
	 * 
	 * @return the string representation of the value of the quantity,
	 * 		as an integer if unit is count and two decimal digits otherwise.
	 * 
	 * {@pre unit != null && value !- null}
	 * 
	 * {@post string of value of quantity}
	 */
	public String getValueString() {
		if(unit == Unit.COUNT) {
			return Integer.toString(value.intValue());
		} else {
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format(value);
		}
	}
	
	public boolean equals(Object obj)
	{
		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != getClass()) {
			return false;
		}
		
		Quantity rhs = ((Quantity)obj);
		return new EqualsBuilder()
			.append(unit, rhs.unit)
			.append(value,rhs.value)
			.isEquals();
	}
	
	public int hashCode()
	{
		return new HashCodeBuilder(817,997)
			.append(unit)
			.append(value)
			.toHashCode();
	}
}
