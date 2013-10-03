package model;

import java.io.Serializable;
/**
 * Contains both numerical value and unit of measurement of
 * Product and Item unit quantities
 * 
 * @author Nathan Standiford
 */
public class Quantity implements Serializable {
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
		return value != null && unit != null && (unit == Unit.COUNT && value == 1.0
                || unit != Unit.COUNT && value > 0);
		//return false;
	}
	
	private static void checkQuantity(Double value, Unit unit)
	{
		if (!isValidQuantity(value, unit)) {
			throw new InvalidQuantityException("Quantity '" + value + "' not valid for '"
                    + unit.toString() + "' unit");
		}
	}
	
	/**
	 * 
	 * @return
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
}
