package model.serialization;

/**
 * The SqlSerializerFactory creates a new SqlSerializer object.
 */
public class SqlSerializerFactory implements ISerializerFactory {

	private SqlSerializerFactory() {
		// private instantiation
	}
	
	/**
	 * Gets an SqlSerializerFactory instance which implements the ISerializerFactory interface
	 * 
	 * @return an ISerializerFactory instance
	 * 
	 * {@pre none}
	 * 
	 * {@post an ISerializerFactory instance}
	 */
	public ISerializerFactory getInstance() {
		return null;
	}
	
	/**
	 * Creates an SqlSerializer object, which implements the ISerializer interface.
	 * 
	 * @return an ISerializer object
	 * 
	 * {@pre none}
	 * 
	 * {@post an ISerializer object}
	 * @see model.serialization.ISerializerFactory#createSerializer()
	 */
	@Override
	public ISerializer createSerializer() {
		// TODO Auto-generated method stub
		return null;
	}

}
