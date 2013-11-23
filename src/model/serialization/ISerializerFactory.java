package model.serialization;

/**
 * The ISerializerFactory interface is used to create an object which implements
 * an ISerializer object.
 */
public interface ISerializerFactory {
	
	/**
	 * Creates a new ISerializer instance
	 * 
	 * @return an ISerializer instance
	 * 
	 * {@pre none}
	 * 
	 * {@post an ISerializer instance}
	 */
	public ISerializer createSerializer();
	
}
