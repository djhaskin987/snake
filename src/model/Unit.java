package model;
/**
 * Enumeration that guarantees
 * that only the specific
 * units of measurement are used
 * @author Kevin
 *
 */
public enum Unit {
	COUNT ("count"),
	LBS ("lbs"),
	OZ ("oz"),
	G ("grams"),
	KG ("kilograms"),
	GAL ("gallons"),
	QUART ("quart"),
	PINT ("pint"),
	FLOZ ("fluid oz"),
	LITER ("liter");
	
	private String statusCode;
	 
	private Unit(String s) {
		statusCode = s;
	}
 
	public String getStatusCode() {
		return statusCode;
	}
}
