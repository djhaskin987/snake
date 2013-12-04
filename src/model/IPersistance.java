package model;

/**
 * Interface for all methods of saving persistent copies of the data model
 * @author kjdevocht
 *
 */
public interface IPersistance {
	
	/**
	 * Actually writes to where ever the data is stored
	 * @pre the data model must contain everything that is wanted to be stored
     * and any updates made that are needed
	 * @post a persistent copy of the data model is stored
	 */
	public void store();
	
	/**
	 * {@pre There must be a persistent copy of the data stored}
	 * {@post The data that was stored has now been loaded back into the data model}
	 */
	public void load();
	
}
