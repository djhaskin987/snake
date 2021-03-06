package model;

import java.io.Serializable;

import model.reports.IReportNode;
import gui.reports.*;

/**
 * Interface of Item to increase modularity of the Item Object
 * @author Kevin
 *
 */
public interface IItem extends IModelTagable, Serializable, IReportNode {
	
	/**
	 * Connects the IItem with a specific IProductContainer
	 * @param container
	 */
	public void setProductContainer(IProductContainer container);
	
	/**
	 * Sets the Date that an IItem entered the ProductContainer
	 * @param date
	 */
	public void setEntryDate(ValidDate date);
	
	/**
	 * Sets the exitDate of the IItem
	 */
	public void exit();
	
	
	/**
	 * Indicates what type of Product the IItem is
	 * @return Product type of the Item
	 */
	public IProduct getProduct();
	
	/**
	 * The barcode corresponding with the IItem
	 * @return Barcode associated with IItem
	 */
	public Barcode getBarcode();
	
	/**
	 * The date the IItem was entered
	 * @return ValidDate of when IItem was entered
	 */
	public ValidDate getEntryDate();
	
	/**
	 * This will be null if the Item is still in the system
	 * @return Date when the item left the ProductContainer
	 */
	public DateTime getExitTime();
	
	/**
	 * This will be null if the ProductGroup this IItem is in has not been set
	 * @return ValidDate of when the IItem will expire
	 */
	public Date getExpireDate();
	
	
	/**
	 * This will be null if the IItem has been removed from the ProductContainer
	 * @return The unique ProductContainer that the IItem is inside
	 */
	public IProductContainer getProductContainer();
	
	public String getProductGroupName();
	
	public String getStorageUnitName();

	public void move(IProductContainer target);

	/**
	 * Sets the exit time to null. Used for undoing an exit.
	 */
	public void unexit();

	public StorageUnit getStorageUnit();

	String getEntryDateString();

	String getExpireDateString();

	public void setProduct(IProduct product);

	void setExit(DateTime d);

}
