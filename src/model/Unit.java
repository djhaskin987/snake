package model;
/**
 * Enumeration that guarantees
 * that only the specific
 * units of measurement are used
 * @author Daniel Haskin
 *
 */
public enum Unit {
	COUNT ("count",		Dimension.COUNT,	1),
	LBS ("lbs",			Dimension.MASS,		453.592),
	OZ ("oz",			Dimension.MASS,		28.3495),
	G ("grams",			Dimension.MASS,		1),
	KG ("kilograms",	Dimension.MASS,		1000),
	GAL ("gallons",		Dimension.VOLUME,	3.78541),
	QUART ("quart",		Dimension.VOLUME,	0.946353),
	PINT ("pint",		Dimension.VOLUME,	0.473176),
	FLOZ ("fluid oz",	Dimension.VOLUME,	0.0295735),
	LITER ("liter",		Dimension.VOLUME,	1);
	
	private String statusCode;
	private Dimension dimension;
	private double conversionConstant;
	 
	private Unit(String s, Dimension d, double c) {
		statusCode = s;
		dimension = d;
		conversionConstant = c;
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public double getConversionConstant() {
		return conversionConstant;
	}
	
	public boolean canConvert(Unit u) {
		return dimension == u.getDimension();
	}
	
	public double convert(double amount, Unit unit) throws Exception {
		if(!canConvert(unit)) {
			throw new Exception("Error: " + statusCode + " cannot be converted to " + unit.getStatusCode());
		}
		return amount*unit.getConversionConstant()/conversionConstant;
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
	
	public enum Dimension {
		COUNT,
		MASS,
		VOLUME,
	}
}
