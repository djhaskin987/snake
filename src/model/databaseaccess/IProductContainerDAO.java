package model.databaseaccess;

import java.util.List;

import model.IProduct;
import model.IProductContainer;

public interface IProductContainerDAO {
	/**
	 * @return A list of every product container in the database,
	 * with all entries that contain other objects left blank
	 */
	public List<IProductContainer> getAll();
	/**
	 * @param container		The container to get the products in
	 * @return				A list of barcodes of all the products immediately in
	 * 						container. That is, not the ones in subcontainers.
	 */
	public List<String> getProducts(IProductContainer container);
	/**
	 * @param container		The container to find the parent of
	 * @return				Database ID of Parent container, or null if container
	 * 						is a StorageUnit
	 */
	public int getParent(IProductContainer container);
	/**
	 * @param container		The container to find the items in
	 * @return				A list of barcodes of all items immediately in this container.
	 *  					That is, not the ones in subcontainers.
	 */
	public List<String> getItems(IProductContainer container);
	/**
	 * @param container		The container to find the children of
	 * @return				A list of database IDs of children of the given container
	 */
	public List<Integer> getChildren(IProductContainer container);

	/**
	 * 						Adds the given container to the database
	 * 
	 * @param container		Container to add to the database
	 * {@pre				container is not in the database}
	 * {@post				container is in the database}
	 */
	public void add(IProductContainer container);
	/**
	 * 						Removes the given container from the database
	 * 
	 * @param container
	 * {@pre				container is in the database}
	 * {@post				container is not in the database}
	 */
	public void remove(IProductContainer container);
	/**
	 * 						Changes the specified container to have the given values.
	 * 
	 * @param name			Name of the container to modify
	 * @param storageUnit	Name of the storage unit it's in
	 * @param container		New version of the container
	 * 
	 * {@pre				There is a container with the name name in the storage unit
	 * 						storageUnit}
	 * {@post				The entry in the database corresponding to the container
	 * 						just listed is replaced with the parameter container.
	 * 						The database ID remains the same.}
	 */
	public void modify(String name, String storageUnit, IProductContainer container);
}
