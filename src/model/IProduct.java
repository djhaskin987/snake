package model;

import java.io.Serializable;
import java.util.List;

/**
 * Interface of Product to increase modularity of the Product Object
 * @author Kevin
 *
 */
public interface IProduct extends Serializable {

	/**
	 * This must be a NonEmptyString
	 * @param description of the Product
	 */
	public void setDescription(NonEmptyString description);

	/**
	 * Must be a positive Integer
	 * @param months
	 */
	public void setShelfLife(Integer months);

	/**
	 * Must be a non-negative Integer
	 * @param count
	 */
	public void setThreeMonthSupply(Integer count);

	/**
	 * This is the first time this Product was added to the ProductCollection
	 * @return A ValidDate of when the product was added
	 */
	public ValidDate getCreateDate();

	/**
	 *
	 * @return Integer of how many Items you will need for a Three month supply of this Product
	 */
	public Integer getThreeMonthSupply();

	/**
	 *
	 * @return List of all Product Containers that contain this Product
	 */
	public List<ProductContainer> getContainers();

}
