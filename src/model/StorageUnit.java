package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;

/**
 * A Storage Unit is a room, closet, pantry, cupboard, or some other enclosed area where
 * items can be stored.
 * 
 * @author Nathan Standiford
 *
 */
public class StorageUnit extends ProductContainer implements Serializable {

	/**
	 * serial version unique identifier
	 */
	private static final long serialVersionUID = 8776735239406467878L;
	
	/**
	 * Creates a new StorageUnit object
	 * 
	 * @param name the name of the storage unit
	 * 
	 * {@pre name != null}
	 * 
	 * {@post a StorageUnit object}
	 */
	public StorageUnit(NonEmptyString name) {
		super(name);
	}
	
	// for testing only
	StorageUnit() {
		super();
	}
	
	/**
	 * Retrieves a collection of products in the storage unit
	 * 
	 * @return an unmodifiable Collection of Products
	 * 
	 * {@pre productItems != null}
	 * 
	 * {@post unmodifiable Collection of Products}
	 */
	@Override
	public Collection<IProduct> getProducts() {		 
		ArrayList<IProduct> products = new ArrayList<IProduct>(productItems.getProducts());
		for(ProductGroup productGroup : productGroups) {
			products.addAll(productGroup.getProducts());
		}
		return Collections.unmodifiableCollection(products);
	}

	/**
	 * Checks to see if a product is in this StorageUnit
	 * 
	 * @param product the product to search for
	 * 
	 * @return true if product is contained in the StorageUnit. false otherwise 
	 * 
	 * {@pre productItems != null}
	 * 
	 * {@post true if product is contained in the StorageUnit. False otherwise. }
	 */
	@Override
	public boolean contains(Product product) {
		return productItems.getProducts().contains(product);
	}

	/**
	 * Adds an Item to the StorageUnit
	 * 
	 * @param item the Item to add
	 * 
	 * {@pre item != null && productItems != null}
	 * 
	 * {@post item added}
	 */
	@Override
	public void addItem(IItem item) {
		productItems.addItem(item);		
	}

	/**
	 * Add ProductGroup to StorageUnit
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
	 * remove ProductContainer from StorageUnit
	 * 
	 * @param name the name of the productContainer
	 * 
	 * {@pre name != null && name != ""}
	 * 
	 * {@post productContainer deleted if it exists}
	 */
	@Override
	public void deleteProductContainer(String name) {
		productGroups.remove(name);
	}

	/**
	 * Sets the productContainer by the specified name in the StorageUnit
	 * with a new object
	 * 
	 * @param name the name of the productContainer
	 * 
	 * @param productContainer the new productContainer
	 * 
	 * {@pre name != null && name != "" && productContainer != null &&
     * productContainer.getClass() == ProductGroup.class}
     * 
	 * {@post productContainer is set, if name exists}
	 */
	@Override
	public void setProductContainer(String name,
			ProductContainer productContainer) {
		productGroups.setProductGroup(new NonEmptyString(name), (ProductGroup)productContainer);
	}

	/**
	 * Transfers an item to another product container.
	 * 
	 * @param item the item to transfer
	 * 
	 * @param newProductContainer the ProductContainer to transfer the item to
	 * 
	 * {@pre item != null && newProductContainer != null && productItems.contains(items)}
	 * 
	 * {@post item is transferred}
	 */
	@Override
	public void transferItem(IItem item, ProductContainer newProductContainer) {
		productItems.removeItem(item);
		newProductContainer.add(item);
	}

	/**
	 * Transfers a product to another product container.
	 * 
	 * @param product the product to transfer
	 * 
	 * @param newProductContainer the ProductContainer to transfer the item to
	 * 
	 * {@pre product != null && newProductContainer != null && productItems.contains(product)}
	 * 
	 * {@post product is transferred}
	 */
	@Override
	public void transferProduct(IProduct product,
			ProductContainer newProductContainer) {
		List<IItem> itemsToTransfer = new ArrayList<IItem>();
		for (IItem i : productItems.getItems()) {
			if (i.getProduct() == product)
				itemsToTransfer.add(i);
		}
		for (IItem i : itemsToTransfer) {
			productItems.removeItem(i);
			i.setProductContainer(newProductContainer);
		}		
		for (IItem i: itemsToTransfer) {
			newProductContainer.add(i);
		}
	}

	/**
	 * 
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
		for (IProduct p : pc.getProducts()) {
			if (p.getDescription().getValue() == name)
				return true;
		}
		return false;
	}

	/**
	 * Not used. Always returns null
	 * 
	 * @return null
	 * 
	 * {@pre none}
	 * 
	 * {@post null}
	 */
	@Override
	public IProductContainer getParent() {
		return null;
	}

	/**
	 * Not used. Always returns null
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

	/**
	 * Not used. Always returns null
	 * 
	 * @return null
	 * 
	 * {@pre none}
	 * 
	 * {@post null}
	 */
	@Override
	public String getThreeMonthSupply() {
		return null;
	}

	/**
	 * Not used. Always returns null
	 * 
	 * @return null
	 * 
	 * {@pre none}
	 * 
	 * {@post null}
	 */
	@Override
	public String getProductGroupName() {
		return null;
	}

	/**
	 * retrieve a list of items based on the product name.
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
	public List<IItem> getItems(String productName) {
		List<IItem> items = new ArrayList<IItem>();
		for (IItem i : productItems.getItems()) {
			Product p = i.getProduct();
			String pName = p.getDescription().getValue();
			if (pName == productName)
				items.add(i);
		}
		return items;
	}
}
