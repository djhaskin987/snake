package model.serialization;

import model.Model;

/**
 * An SqlSerializer will serialize a Model singleton to / from the database.
 * We use the JDBC driver to implement this. 
 */
public class SqlSerializer implements ISerializer {

	/**
	 * Creates a new SqlSerializer object
	 * 
	 * {@pre none}
	 * 
	 * {@post an SqlSerializer object}
	 */
	public SqlSerializer() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see model.serialization.ISerializer#load(model.Model)
	 */
	@Override
	public void load(Model model) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see model.serialization.ISerializer#save(model.Model)
	 */
	@Override
	public void save(Model model) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see model.serialization.ISerializer#save(model.Model)
	 */
	@Override
	public void update(Model model) {
		// TODO Auto-generated method stub

	}

}
