package gui.reports;

/** This enumeration types outlines what types of reports we 
 * can generate in HIT.
 * @author Daniel Jay Haskin
 *
 */
public enum Format {
	PDF ("pdf"),
	HTML ("html");
	
	private String statusCode;
	 
	private Format(String s) {
		statusCode = s;
	}
	
	/**
	 * 
	 * @return The string version of the enum
	 */
	public String getStatusCode() {
		return statusCode;
	}
}
