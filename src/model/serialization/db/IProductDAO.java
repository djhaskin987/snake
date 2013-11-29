package model.serialization.db;

import java.util.List;

import model.IProduct;
import model.NonEmptyString;

public interface IProductDAO extends IDAO<NonEmptyString, IProduct> {
	/**
	 * @param product		The product to get the containers of
	 * @return				A list of database IDs of all the containers immediately
	 * 						containing this product. That is, not the parent containers
	 * 						of those containers.
	 */
	public List<Integer> getContainers(IProduct product);
}
