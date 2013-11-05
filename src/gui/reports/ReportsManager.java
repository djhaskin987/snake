package gui.reports;

import model.Model;


/** Reports manager singleton that can be called on to generate
 * reports by the controller.
 * @author Daniel Haskin
 *
 */

public class ReportsManager {
	private static ReportsManager instance;
	/**
     * Singleton creation for ReportsManager
     * @return instance of ReportsManager
     */
	public static ReportsManager getInstance()
	{
		if (instance == null)
		{
			instance = new ReportsManager();
		}
		return instance;
	}
	
	/**
	 * private constructor for ReportsManager
	 */
	private ReportsManager() {
		
	}
	
	/**
	 * Displays the notices report in specified format.
	 * 
	 * @param f the display format
	 * 
	 * {@pre f != null}
	 * 
	 * {@post the notices report}
	 */
	private void displayNoticesReport(Format f) {	
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	/**
	 * Displays the Supply Report in specified format.
	 * 
	 * @param f the display format
	 * 
	 * {@pre f != null}
	 * 
	 * {@post the supply report}
	 */
	private void displaySupplyReport(Format f) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	/**
	 * Displays the Removed Items Report in specified format.
	 * 
	 * @param f the display format
	 * 
	 * {@pre f != null}
	 * 
	 * {@post the removed items report}
	 */
	private void displayRemovedItemsReport(Format f) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	/**
	 * Displays the expired Items report in specified format.
	 * 
	 * @param f the display format
	 * 
	 * {@pre f != null}
	 * 
	 * {@post the expired items report}
	 */
	private void displayExpiredItemsReport(Format f) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
