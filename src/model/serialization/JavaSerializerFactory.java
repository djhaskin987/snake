package model.serialization;

/**
 * The JavaSerializerFactory instance creates a new JavaSerializer object.
 */
public class JavaSerializerFactory implements ISerializerFactory {
	
	private JavaSerializerFactory() {
		// no instantiation
	}
	
	/**
	 * Gets an instance of this factory object as a ISerializerFactory instance
	 * 
	 * @return an ISerializerFactory instance
	 * 
	 * {@pre none}
	 * {@post an ISerializerFactory instance}
	 */
	public ISerializerFactory getInstance() {
		return new JavaSerializerFactory();
	}

	/**
	 * Creates a JavaSerializer object.
	 * 
	 * @return a JavaSerializer object
	 * 
	 * {@pre none}
	 * 
	 * {@post an ISerializer instance}
	 */
	@Override
	public ISerializer createSerializer() {
		// TODO Auto-generated method stub
		return new JavaSerializer();
	}

}