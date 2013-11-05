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
     * @return instance of Model
     */
	public static Model getInstance()
	{
		if (instance == null)
		{
			instance = new Model();
		}
		return instance;
	}
}
