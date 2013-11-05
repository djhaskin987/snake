package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Interface for Product Container to increase modularity of ProductContainer
 * @author Daniel Jay Haskin
 *
 */
public interface IProductContainer extends Serializable, IModelTagable {
	
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
	 * @param p an IProduct 
	 * 
	 * @return collection of items of a specific product
	 */
	//TODO: Was this supposed to return null or an empty collection if there is no such item?
	public Collection<IItem> getItems(IProduct product);
	
	/**
	 * Takes in a barcode and removes an Item from a ProductContainer and sets
     * the exitDate of that Item
     * 
	 * @param barcode the barcode 
	 */
	public void removeItem(Barcode barcode);
	
	/** Retrieve the name of this product container.
	 * {@pre The product container is initialized and not null.}
	 * {@post No change is made to the state of the object.}
	 * @return the name of the product container.
	 */
	NonEmptyString getName();
	/**
	 * Adds an Item to just one ProductContainer
	 * @param item
	 * {@pre the IProductContainer is initialized}
	 * {@post the specified item is added to the container}
	 */
	public void addItem(IItem item);
	
	public boolean hasItems();
	
	/** 
	 * Returns if there are any items in the entire recursive tree.
	 */
	public boolean hasItemsRecursive();
	
	/**
	 * Allows program to add a ProductGroup to either a StorageUnit or a ProductGroup
	 * @param productGroup
	 * {@pre the specified ProductGroup is non-null and unique relative to the
     * already-present product groups}
	 * {@post the specified ProductGroup is added to the IProductContainer}
	 */
	public void addProductContainer(IProductContainer productGroup);
	
	/**
	 * If this is a StorageUnit must be empty
	 * @param name
	 * {@pre the specified name is actually associated with a product container
     * within this IProductContainer.}
	 * {@post the ProductContainer corresponding to the name is removed
	 * 		from this IProductContainer.}
	 */
	public void deleteProductContainer(String name);
	
	
	/**
	 * 
	 * @param name
	 * @param productContainer
	 * {@pre name is non-null and non-empty, as is productContainer}
	 * {@post the productContainer is associated with name in this IProductContainer}
	 */
	public void setProductContainer(String name, ProductContainer productContainer);
	
	
	/**
	 * Allows program to move a unique Item to a unique ProductContainer
	 * @param item
	 * @param newProductContainer
	 * {@pre item is non-null, and contained by some ProductContainer in this IProductContainer; 
	 * 		item's product is a member of newProductContainer;
	 * 		and newProductContainer is a member of this IProductContainer.}
	 * {@post item is transferred to newProductContainer.}
	 */
	public void transferItem(IItem item, ProductContainer newProductContainer);
	
	
	/**
	 * Allows program to move a unique Product to a unique ProductContainer
	 * @param product
	 * @param newProductContainer
	 * {@pre product is non-null, and contained by some ProductContainer in this
     * IProductContainer; newProductContainer is a member of this
     * IProductContainer}
	 * {@post product is transferred to newProductContainer.}
	 */
	public void transferProduct(IProduct product, ProductContainer newProductContainer);
	
	/**
	 * Searchs ProductContainer to see who has a specific Product.  
	 * Once it finds the Product it stops looking because every ProductContainer Tree 
	 * can only contain one instance of a unique Product
	 * @param iProduct
	 * {@pre name is non-null and non-empty, and actually names a Product}
	 * {@post object remains unchanged.}
	 * @return IproductContainer that holds Product
	 */
	public IProductContainer whoHasProduct(IProduct product);
	
	
	/**
	 * if This is a Storage Unit it will return null
	 * {@pre this IProductContainer is properly initialized.}
	 * {@post object remains unchanged.}
	 * @return IproductContainer that contains ProductContainer
	 */
	public IProductContainer getParent();
	public Unit getThreeMonthSupplyUnit();
	
	public String getThreeMonthSupplyValueString();

	public void setName(String name);
	boolean canAddItems();
	boolean canRemoveItems();
	boolean canDelete();
	boolean contains(IProduct product);
	boolean hasChild(String string);
	void add(IItem item);
	void setParent(IProductContainer productContainer);
	void addBatch(List<IItem> batch);
	public IProductContainer getUnitPC();

	public void removeProduct(IProduct product);
	public void removeProductRecursive(IProduct product);
	public Collection<IItem> getItemsRecursive(IProduct product);

	public Collection<IProduct> getProductsRecursive();

	public void moveProduct(IProduct product, IProductContainer target);

	public void addProduct(IProduct product);

	public Collection<IProductContainer> getChildren();
}
