package model;

import java.io.Serializable;

/**
 * Interface for Product Container to increase modularity of ProductContainer
 * @author Daniel Jay Haskin
 *
 */
public interface IProductContainer extends Serializable, IContextPanelNode {
	
	/** Retrieve the name of this product container.
	 * @pre The product container is initialized and not null.
	 * @post No change is made to the state of the object.
	 * @return the name of the product container.
	 */
	NonEmptyString getName();
	/**
	 * Adds an Item to just one ProductContainer
	 * @param item
	 * @pre the IProductContainer is initialized
	 * @post the specified item is added to the container
	 */
	public void addItem(IItem item);
	
	/**
	 * Allows program to add a ProductGroup to either a StorageUnit or a ProductGroup
	 * @param productGroup
	 * @pre the specified ProductGroup is non-null and unique relative to the
     * already-present product groups
	 * @post the specified ProductGroup is added to the IProductContainer
	 */
	public void addProductGroup(ProductGroup productGroup);
	
	/**
	 * If this is a StorageUnit must be empty
	 * @param name
	 * @pre the specified name is actually associated with a product container
     * within this IProductContainer.
	 * @post the ProductContainer corresponding to the name is removed from this IProductContainer.
	 */
	public void deleteProductContainer(String name);
	
	
	/**
	 * 
	 * @param name
	 * @param productContainer
	 * @pre name is non-null and non-empty, as is productContainer
	 * @post the productContainer is associated with name in this IProductContainer
	 */
	public void setProductContainer(String name, ProductContainer productContainer);
	
	
	/**
	 * Allows program to move a unique Item to a unique ProductContainer
	 * @param item
	 * @param newProductContainer
	 * @pre item is non-null, and contained by some ProductContainer in this IProductContainer; 
	 * 		item's product is a member of newProductContainer;
	 * 		and newProductContainer is a member of this IProductContainer.
	 * @post item is transferred to newProductContainer.
	 */
	public void transferItem(IItem item, ProductContainer newProductContainer);
	
	
	/**
	 * Allows program to move a unique Product to a unique ProductContainer
	 * @param product
	 * @param newProductContainer
	 * @pre product is non-null, and contained by some ProductContainer in this
     * IProductContainer; newProductContainer is a member of this
     * IProductContainer
	 * @post product is transferred to newProductContainer.
	 */
	public void transferProduct(IProduct product, ProductContainer newProductContainer);
	
	/**
	 * Searchs ProductContainer to see who has a specific Product.  
	 * Once it finds the Product it stops looking because every ProductContainer Tree 
	 * can only contain one instance of a unique Product
	 * @param name
	 * @pre name is non-null and non-empty, and actually names a Product
	 * @post object remains unchanged.
	 * @return IproductContainer that holds Product
	 */
	public IProductContainer whoHasProduct(String name);
	
	
	/**
	 * if This is a Storage Unit it will return null
	 * @pre this IProductContainer is properly initialized.
	 * @post object remains unchanged.
	 * @return IproductContainer that contains ProductContainer
	 */
	public IProductContainer getParent();

}
