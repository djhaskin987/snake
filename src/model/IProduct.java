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
	 * {@pre NonEmptyString giving details about this product}
	 * {@param description of the Product}
	 */
	public void setDescription(NonEmptyString description);

	/**
	 * {@pre Integer must be non-negative}
	 * @param months
	 * @throws InvalidIntegerException 
	 * {@post this.shelfLife is now equal to months}
	 */
	public void setShelfLife(Integer months) throws InvalidIntegerException;

	/**
	 * @pre {Integer must be a non-negative}
	 * @param count
	 * @throws InvalidIntegerException 
	 * @post {this.threeMonthSupply is now equal to count}
	 */
	public void setThreeMonthSupply(Integer count) throws InvalidIntegerException;

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
	
	/**
	 * 
	 * @return A ValidDate of when the product was first created
	 */
	public ValidDate getCreation();

	/**
	 * 
	 * @return A Barcode that is unique to this product
	 */
	public Barcode getBarcode();

	/**
	 * 
	 * @return A ValidDate of when the product was first created
	 */
	public NonEmptyString getDescription();
	
	/**
	 * 
	 * @return A Quantity object that tells us the size an unit
	 * of measure for this particular product
	 */
	public Quantity getItemSize();
	
	/**
	 * 
	 * @return A ValidDate of when the product was first created
	 */
	public Integer getShelfLife();
	
	@SuppressWarnings("serial")
	public class InvalidIntegerException extends Exception{
	}

}
