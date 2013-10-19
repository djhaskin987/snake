package model;

import java.util.Collection;
/**
 * This interface allows the data structures to communicate with the gui.  some
 * of the methods are pointless for certain instances.  For example
 * getThreeMonthSupply will always return null for StorageUnit but it is
 * included to help ease interaction with the gui.
 * @author Kevin
 *
 */
public interface IContextPanelNode {
	/**
	 * Converts an enum into a string to display
	 * 
	 * @return String telling user what unit of measure a particular Item or Product uses
	 */
	public String getUnit();
	
	/**
	 * 
	 * @return Numerical value and unit of measurement of a Three Month Supply
	 */
	public String getThreeMonthSupply();
	
	/**
	 * 
	 * @return List of Products
	 */
	public Collection<IProduct> getProducts();
	
	/**
	 * Gets the name of ProductGroup
	 * 
	 * @return ProductGroup name
	 */
	public String getProductGroupName();
	
	/**
	 * Gets all items related to a specific Product
	 * 
	 * @param productName String Name of a Product 
	 * 
	 * @return ProductGroup name
	 */
	public Collection<IItem> getItems(String productName);
	
	/**
	 * Takes in a barcode and removes an Item from a ProductContainer and sets
     * the exitDate of that Item
     * 
	 * @param barcode the barcode 
	 */
	public void removeItem(Barcode barcode);

}
