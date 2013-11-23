package model.serialization;
import model.Model;

/**
 * The ISerializer interface is used to load / store / update Model into some persistent
 * module.
 */
public interface ISerializer {
	/**
	 * Loads model into memory from persistent module
	 * 
	 * @param model a Model singleton object
	 */
	public void load(Model model);
	
	/**
	 * Serializes the model and saves it to some persistent module
	 * 
	 * @param model a Model singleton object
	 */
	public void save(Model model);
	
	/**
	 * Updates the persistent Model data to match current Model singleton
	 * stored in memory.
	 * 
	 * @param model a Model singleton object
	 */
	public void update(Model model);
}
