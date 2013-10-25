package model;

import gui.common.ITagable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Interface of Product to increase modularity of the Product Object
 * @author Kevin
 *
 */
public interface IProduct extends Serializable, ITagable {

	/**
	 * This must be a NonEmptyString
	 * 
	 * @param description the description
	 * 
	 * {@pre NonEmptyString giving details about this product}
	 * {@post description of the Product}
	 */
	public void setDescription(NonEmptyString description);

	/**
	 * 
	 * @param months the shelf life in months
	 * 
	 * @throws InvalidIntegerException 
	 * {@pre Integer must be non-negative}
	 * {@post this.shelfLife is now equal to months}
	 */
	public void setShelfLife(Integer months) throws InvalidIntegerException;

	/**
	 * 
	 * @param count the three month supply count
	 *  
	 * @throws InvalidIntegerException 
	 * 
	 * {@pre Integer must be a non-negative}
	 * 
	 * {@post this.threeMonthSupply is now equal to count}
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
	
	public void setItemSize(Quantity q);
	/**
	 * 
	 * @return A ValidDate of when the product was first created
	 */
	public Integer getShelfLife();
	
	public Collection<IItem> getItems(IProductContainer pc);
	
	public Collection<IItem> getAllItems();
	
	public void addProductContainer(IProductContainer pc);
	
	public void removeProductContainer(IProductContainer productContainer);
	
	@SuppressWarnings("serial")
	public class InvalidIntegerException extends Exception{
	}	
}
