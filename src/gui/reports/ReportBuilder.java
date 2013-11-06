package gui.reports;

public interface ReportBuilder {
	/**
	 * Builds a table.
	 * 
	 * @param table a 2D array of Strings to represent tabular data
	 * 
	 * {@pre table != null && table not empty}
	 * {@post table is built into report}
	 */
	public void buildTable(String [][] table);
	
	/**
	 * Builds a subheading.
	 * 
	 * @param subHeading string representing the heading
	 * 
	 * {@pre subHeading != null}
	 * 
	 * {@post subheading is built into report}
	 */
	public void buildSubHeading(String subHeading);
	
	/**
	 * Builds a heading.
	 * 
	 * @param heading string representing the heading
	 * 
	 * {@pre heading != null}
	 * 
	 * {@post heading is built into report}
	 */
	public void buildHeading(String heading);
	
	/**
	 * displays the built report
	 * 
	 * {@pre none}
	 * 
	 * {@post report is displayed}
	 */
	public void display();
}
