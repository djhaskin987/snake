package model.serialization.db;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import model.IProductContainer;

public interface IProductContainerDAO extends IDAO<Pair<String, String>, IProductContainer> {
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
	public Pair<String, String> getParent(IProductContainer container);
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
	public void update(String name, String storageUnit, IProductContainer container);
}
