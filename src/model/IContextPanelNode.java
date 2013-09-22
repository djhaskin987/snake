package model;

import java.util.List;
/**
 * This interface allows the data structures to communicate with the gui.  some of the methods are pointless for
 * certain instances.  For example getThreeMonthSupply will always return null for StorageUnit but it is included
 * to help ease interaction with the gui.
 * @author Kevin
 *
 */
public interface IContextPanelNode {
	/**
	 * Converts an enum into a string to display
	 * @return String telling user what unit of measure a particular Item or Product uses
	 */
	public String getUnit();
	
	/**
	 * 
	 * @return Numerical value and unit of measurement of a Three Month Supply
	 */
	public Quantity getThreeMonthSupply();
	
	/**
	 * 
	 * @return List of Products
	 */
	public List<Product> getProducts();
	
	/**
	 * Gets the name of ProductGroup
	 * @return ProductGroup name
	 */
	public String getProductGroupName();
	
	/**
	 * Get's all items related to a specific Product
	 * @param String Name of a Product 
	 * @return ProductGroup name
	 */
	public List<Item> getItems(String productName);
	
	/**
	 * Takes in a barcode and removes an Item from a ProductContainer and sets the exitDate of that Item
	 * @param Barcode barcode 
	 */
	public void removeItem(Barcode barcode);

}
