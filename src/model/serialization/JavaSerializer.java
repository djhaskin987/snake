/**
 * 
 */
package model.serialization;

import model.Model;

/**
 * Creates the JavaSerializer object
 */
public class JavaSerializer implements ISerializer {

	/**
	 * Creates a new JavaSerializer object
	 */
	public JavaSerializer() {
		// does nothing
	}

	/** (non-Javadoc)
	 * @see model.serialization.ISerializer#load(model.Model)
	 */
	@Override
	public void load(Model model) {
		model.load();
	}
	
	/**
	 * @see model.serialization.ISerializer#save(model.Model)
	 */
	@Override
	public void save(Model model) {
		model.store();

	}

	/**
	 * @see model.serialization.ISerializer#update(model.Model)
	 */
	@Override
	public void update(Model model) {
		model.store();
	}

}
