package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A user-defined group of Products. Product Groups are used by users to aggregate
 * related Products so they can be managed as a collection.
 * @author Kevin
 *
 */
public class ProductGroup extends ProductContainer implements Serializable {
	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = -3590780103998287913L;
	private ProductContainer parentContainer;
	private Quantity threeMonthSupply;
	
	/**
	 * Creates a new ProductGroup object
	 * 
	 * @param name the name of the product group
	 * 
	 * {@pre name != null}
	 * 
	 * {@post a ProductGroup object}
	 */
	public ProductGroup(NonEmptyString name) {
		super(name);
	}
	
	// for testing only
	ProductGroup() {
		super();
	}
	
	/**
	 * Retrieves a collection of products in the product group
	 * 
	 * @return an unmodifiable Collection of Products
	 * 
	 * {@pre productItems != null}
	 * 
	 * {@post unmodifiable Collection of Products}
	 */
	@Override
	public Collection<Product> getProducts() {
		return productItems.getProducts();
	}
	
	/**
	 * Checks to see if a product is in this ProductGroup
	 * 
	 * @param product the product to search for
	 * 
	 * @return true if product is contained in the ProductGroup. false otherwise 
	 * 
	 * {@pre productItems != null}
	 * 
	 * {@post true if product is contained in the ProductGroup. False otherwise. }
	 */
	@Override
	public boolean contains(Product product) {
		return productItems.contains(product);
	}
	
	/**
	 * Adds an Item to the ProductGroup
	 * 
	 * @param item the Item to add
	 * 
	 * {@pre item != null && productItems != null}
	 * 
	 * {@post item added}
	 */
	@Override
	public void addItem(Item item) {
		productItems.addItem(item);		
	}
	
	/**
	 * Add ProductGroup to ProductGroup
	 *
	 * @param productGroup the ProductGroup to add
	 * 
	 * {@pre productGroups != null && productGroup != null}
	 * 
	 * {@post productGroup added}
	 */
	@Override
	public void addProductGroup(ProductGroup productGroup) {
		productGroups.add(productGroup);
	}
	
	/**
	 * Removes the product container
	 * 
	 * @param name the container name
	 * 
	 * {@pre name != null}
	 * 
	 * {@post product container is removed if it exists}
	 */
	@Override
	public void deleteProductContainer(String name) {
		productGroups.remove(name);	
	}
	
	/**
	 * Sets the product container 
	 * 
	 * @param name the product container name
	 * 
	 * @param productcontainer the product container
	 * 
	 * {@pre name != null && productContainer != null && productContainer is a ProductGroup}
	 * 
	 * {@post productContainer is set}
	 */
	@Override
	public void setProductContainer(String name,
			ProductContainer productContainer) {
		productGroups.setProductGroup(new NonEmptyString(name), (ProductGroup)productContainer);
	}
	
	/**
	 * Transfers the item
	 * 
	 * @param item the item
	 * 
	 * @param newProductContainer
	 * 
	 * {@pre item != null && newProductContainer != null}
	 * 
	 * {@post transfers the item}
	 */
	@Override
	public void transferItem(Item item, ProductContainer newProductContainer) {
		productItems.removeItem(item);
		newProductContainer.add(item);		
	}
	
	/**
	 * transfers the product
	 * 
	 * @param product the product
	 * 
	 * @param newProductContainer the new product container
	 * 
	 * {@pre product != null && productContainer != null}
	 * 
	 * {@post product is transfers}
	 */
	@Override
	public void transferProduct(Product product,
			ProductContainer newProductContainer) {
		List<Item> itemsToTransfer = new ArrayList<Item>();
		for (Item i : productItems.getItems()) {
			if (i.getProduct() == product)
				itemsToTransfer.add(i);
		}
		for (Item i : itemsToTransfer) {
			productItems.removeItem(i);
			i.setProductContainer(newProductContainer);
		}		
		for (Item i: itemsToTransfer) {
			newProductContainer.add(i);
		}
	}
	/**
	 * Retrieves the product container from the children elements of the ProductGroup
	 * 
	 * @param name 	the name of the product
	 * 
	 * @return 		IProductContainer object containing Product with description of 'name'.
	 * 				Null if there nothing is there.
	 *
	 * {@pre name is not null}
	 * {@post ProductContainer object}
	 */
	@Override
	public IProductContainer whoHasProduct(String name) {
		if (hasProduct(this, name))
			return this;
		
		for (ProductGroup pg : productGroups.getProductGroups().values()) {
			if (hasProduct(pg, name))
				return pg;
		}
		return null;
	}
	
	private static boolean hasProduct(ProductContainer pc, String name)
	{
		for (Product p : pc.getProducts()) {
			if (p.getDescription().getValue() == name)
				return true;
		}
		return false;
	}
	
	/** 
	 * Gets the parent ProductContainer
	 * 
	 * @return the parent container
	 * 
	 * {@pre parentContainer != null}
	 * 
	 * {@post the parentContainer}
	 */
	@Override
	public IProductContainer getParent() {
		return parentContainer;
	}
	
	/**
	 * Nothing. Always returns null
	 * 
	 * @return null
	 * 
	 * {@pre none}
	 * 
	 * {@post null}
	 */
	@Override
	public String getUnit() {
		return null;
	}
	
	@Override
	public String getThreeMonthSupply() {
		return threeMonthSupply.getString();
	}
	
	/**
	 * Gets the product group name
	 * 
	 * @return the product group name
	 * 
	 * {@pre name != null}
	 * 
	 * {@post the name of the product group}
	 */
	@Override
	public String getProductGroupName() {
		return name.getValue();
	}
	
	/**
	 * Retrieve a list of items based on the product name.
	 * 
	 * @param productName the name of the product
	 * 
	 * @return list of items found 
	 * 
	 * {@pre productName != null && productName != "" && productItems != null}
	 * 
	 * {@post a list of items}
	 */
	@Override
	public List<Item> getItems(String productName) {
		List<Item> items = new ArrayList<Item>();
		for (Item i : productItems.getItems()) {
			Product p = i.getProduct();
			String pName = p.getDescription().getValue();
			if (pName == productName)
				items.add(i);
		}
		return items;
	}
	
	/**
	 * remove item from ProductGroup by barcode
	 * 
	 * @param barcode the barcode of item
	 * 
	 * {@pre barcode != null && productItems != null}
	 * 
	 * {@post item is removed from StorageUnit if Barcode exists}
	 */
	@Override
	public void removeItem(Barcode barcode) {
		for (Item i : productItems.getItems()) {
			Barcode bc = i.getBarcode();
			if (bc == barcode) {
				productItems.removeItem(i);
				break;
			}
		}		
	}

}
