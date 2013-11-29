package model.serialization.db;

import java.util.List;

/** Represents the basic operations of a Data Access Object.
 * 
 * @author djhaskin814
 *
 * @param <K> the key type with which to retrieve an item from the database.
 * @param <T> the type about which this DAO is cheifly concerned.
 */

public interface IDAO<K, T> {
	/** Reads all of the items of type T in the database.
	 * {@pre connection to database is valid}
	 * {@post No change}
	 * @return a list of tyep T containing all of the objects of type T found
	 *     in the database. 
	 *     Note that any many-to-one or one-to-many
	 *     relationships should be left as null.
	 *     Only data which has a one-to-one correspondence with the object of
	 *     type T is loaded into type T.
	 */
	List<T> readAll();
	
	/** Creates the thing in the database.
	 * {@pre thing is properly initialized; connection to database is valid;
	 * no previous thing in the database exists}
	 * {@post thing is inserted into the database, with
	 * 	all relationships filled out correctly (no null's).} 
	 * @param thing The thing to put into the database.
	 */
	void create(T thing);
	
	/** Performs a look-up of a thing based on a key.
	 * {@pre key is properly initialized; connection to database is valid}
	 * {@post no change}
	 * @param key
	 * @return object of type T which corresponds to data in the database
	 * looked up by key.
	 *     Note that any many-to-one or one-to-many
	 *     relationships will be left as null in the returned object.
	 *     Only data which has a one-to-one correspondence with the object of
	 *     type T is loaded into type T.
	 */
	T read(K key);
	
	//TODO: This is a problem in cases where the unique identifier can be changed. For example, if you change the name of a product container,
	//there's no way to tell what you changed it from, and thus which you were changing.
	/** Performs an update operation on thing.
	 * {@pre thing is properly initialized; connection to database is valid}
	 * {@post data corresponding to thing is updated in the database, with
	 * 	all relationships filled out correctly (no null's).}
	 * @param thing
	 *     Note that any many-to-one or one-to-many
	 *     relationships will be left as null in the returned object.
	 *     Only data which has a one-to-one correspondence with the object of
	 *     type T is loaded into type T.
	 */
	void update(T thing);
	
	/** Performs a delete operation on thing.
	 * {@pre thing is properly initialized; connection to database is valid;
	 * data corresponding to 'thing' already exists in database}
	 * {@post data corresponding to thing is deleted in the database, with
	 * 	all relationships to it correctly deleted (no null's).}
	 * @param thing the thing to delete.
	 */
	void delete(T thing);
	
	
}