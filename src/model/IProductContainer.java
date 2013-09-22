package model;

import java.io.Serializable;

/**
 * Interface for Product Container to increase modularity of ProductContainer
 * @author Kevin
 *
 */
public interface IProductContainer extends Serializable, IContextPanelNode{
	
	/**
	 * Adds an Item to just one ProductContainer
	 * @param item
	 */
	public void addItem(Item item);
	
	/**
	 * Allows program to add a ProductGroup to either a StorageUnit or a ProductGroup
	 * @param productGroup
	 */
	public void addProductGroup(ProductGroup productGroup);
	
	/**
	 * If this is a StorageUnit must be empty
	 * @param name
	 */
	public void deleteProductContainer(String name);
	
	
	/**
	 * 
	 * @param name
	 * @param productContainer
	 */
	public void setProductContainer(String name, ProductContainer productContainer);
	
	
	/**
	 * Allows program to move a unique Item to a unique ProductContainer
	 * @param item
	 * @param newProductContainer
	 */
	public void transferItem(Item item, ProductContainer newProductContainer);
	
	
	/**
	 * Allows program to move a unique Product to a unique ProductContainer
	 * @param product
	 * @param newProductContainer
	 */
	public void transferProduct(Product product, ProductContainer newProductContainer);
	
	/**
	 * Searchs ProductContainer to see who has a specific Product.  
	 * Once it finds the Product it stops looking because every ProductContainer Tree 
	 * can only contain one instance of a unique Product
	 * @param name
	 * @return IproductContainer that holds Product
	 */
	public IProductContainer whoHasProduct(String name);
	
	
	/**
	 * if This is a Storage Unit it will return null
	 * @return IproductContainer that contains ProductContainer
	 */
	public IProductContainer getParent();

}
