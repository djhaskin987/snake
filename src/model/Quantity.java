package model;

/**
 * Contains both numerical value and unit of measurement of
 * Product and Item unit quantities
 * @author Kevin
 *
 */
public class Quantity {
	private Double value;
	private Unit unit;
	
	public Quantity(Double value, Unit unit){
		this.value = value;
		this.unit = unit;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
}
