package model;
/**
 * Enumeration that guarantees
 * that only the specific
 * units of measurement are used
 * @author Daniel Haskin
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
	
	/**
	 * 
	 * @return The string version of the enum
	 */
	public String getStatusCode() {
		return statusCode;
	}
	
	
	
	private static NaturalLanguageTable<Unit> table;
	private static NaturalLanguageTable<Unit> getTable()
	{
		if (table == null)
		{
			initTable();
		}
		return table;
	}
	
	private static void initTable() {
		table = new NaturalLanguageTable<Unit>(true);
		table.addEntry("^\\s*(count|cnt[.]?)\\s*$", Unit.COUNT);
		table.addEntry("^\\s*(pounds|lbs[.]?|[#])\\s*$", Unit.LBS);
		table.addEntry("^\\s*(ounces|oz[.]?)\\s*$", Unit.OZ);
		table.addEntry("^\\s*(grams|g[.]?)\\s*$", Unit.G);
		table.addEntry("^\\s*(kilo[-]?grams|kg[.]?)\\s*$", Unit.KG);
		table.addEntry("^\\s*(gallons|gal[.]?)\\s*$", Unit.GAL);
		table.addEntry("^\\s*(quart|qt[.]?)\\s*$", Unit.QUART);
		table.addEntry("^\\s*(pint|pt[.]?)\\s*$", Unit.PINT);
		table.addEntry("^\\s*(fluid|fl[.]?)\\s+(ounces|oz[.]?)\\s*$", Unit.FLOZ);
		table.addEntry("^\\s*(litre|liter|l[.]?)\\s*$", Unit.LITER);
	}

	public static Unit getInstance(String s)
	{
		Unit correspondingUnit = getTable().match(s);
		if (correspondingUnit == null)
		{
			throw new IllegalArgumentException("Given an invalid unit string '" +
					s + "'");
		}
		return correspondingUnit;
	}
}
