package model.databaseaccess;

import java.util.List;

import model.IProduct;
import model.IProductContainer;

public interface IProductDAO {
	/**
	 * @return A list of every product in the database,
	 * with all entries that contain other objects left blank
	 */
	public List<IProduct> getAll();
	/**
	 * @param product		The product to get the containers of
	 * @return				A list of database IDs of all the containers immediately
	 * 						containing this product. That is, not the parent containers
	 * 						of those containers.
	 */
	public List<Integer> getContainers(IProduct product);

	/**
	 * 						Adds the given product to the database
	 * 
	 * @param product		Product to add to the database
	 * {@pre				product is not in the database}
	 * {@post				product is in the database}
	 */
	public void add(IProduct product);
	/**
	 * 						Removes the given product from the database
	 * 
	 * @param product
	 * {@pre				product is in the database}
	 * {@post				product is not in the database}
	 */
	public void remove(IProduct product);
	/**
	 * 						Replaces the product with the barcode of the given produc
	 * 						with the given product. Since you can't modify the barcode,
	 * 						the new product will have the same barcode, and it's
	 * 						pointless to modify the barcode of the old one.
	 * 
	 * @param product		Replacement product
	 * 
	 * {@pre				There is a Product in the database with the same barcode as product}
	 * 
	 * {@post				product is in the database, and is the only product with that barcode}
	 */
	public void modify(IProduct product);
}
