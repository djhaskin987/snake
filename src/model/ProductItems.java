package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Tracks the products and items in a ProductContainer.
 * Allows you to just worry about the items, and it takes
 * care of the products automatically.
 * 
 * @author Daniel Carrier
 *
 */
public class ProductItems extends Observable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1120419510043040681L;
	
	private Map<IProduct, List<IItem>> map;
	private IProductContainer productContainer;
	
	/**
	 * {@pre	None}
	 * {@post	Constructs an empty ProductItems}
	 * @param iProductContainer 
	 */
	public ProductItems(IProductContainer iProductContainer) {
		map = new HashMap<IProduct, List<IItem>>();
		productContainer = iProductContainer;
	}
	
	/**
	 * {@pre	None}
	 * {@post	Returns a collection of all the products. Empty if this is empty.}
	 * @return	A collection of all the products
	 */
	public Collection<IProduct> getProducts() {
		return map.keySet();
	}
	
	/**
	 * {@pre	None}
	 * {@post	Returns a collection of all the items. Empty if this is empty.}
	 * @return	A collection of all the items. Empty if this is empty.
	 */
	public Collection<IItem> getItems() {
		ArrayList<IItem> out = new ArrayList<IItem>();
		for(Collection<IItem> itemList : map.values()) {
			if(itemList != null) {
				out.addAll(itemList);
			}
		}
		return out;
	}
	
	public Collection<IItem> getItems(IProduct p) {
		return map.get(p);
	}
	
	/**
	 * @pre			{item is not already in this}
	 * @post		{item is added}
	 * @param item	Item to be added
	 */
	public void addItem(IItem item) {	//TODO: Should I check if the item is already in the list?
		IProduct product = item.getProduct();
		List<IItem> itemList = map.get(product);
		if(itemList == null) {
			itemList = new ArrayList<IItem>();
			itemList.add(item);
			map.put(product, itemList);
			product.addProductContainer(productContainer);
		} else {
			itemList.add(item);
		}
	}
	
	/**
	 * {@pre		item is in this}
	 * {@post		item is removed. If item was the last Item of its Product,
	 * 		that product is now empty.}
	 * @param item	Item to be removed. Leaves an empty product if that's the last item.
	 */
	public void removeItem(IItem item) {
		IProduct product = item.getProduct();
		Collection<IItem> itemList = map.get(product);
		if (itemList.size() > 0)
			itemList.remove(item);	
	}

	/**
	 * {@pre			None}
	 * {@post			Returns true if this ProductItems contains a member of this
     * product. False otherwise.}
	 * @param product	The product to check if this ProductItem contains.
	 * @return			True if this ProductItems contains a member of this product.
     * False otherwise.
	 */
	public boolean contains(IProduct product) {
		return map.containsKey(product);
	}
	
	/**
	 * {@pre			None}
	 * {@post			Returns true if this contains item. False otherwise.}
	 * @param item		The item to check if this ProductItem contains.
	 * @return			True if this contains item. False otherwise.
	 */
	public boolean contains(IItem item) {
		IProduct product = item.getProduct();
		Collection<IItem> itemList = map.get(product);
		if(itemList == null) {
			return false;
		} else {
			return itemList.contains(item);
		}
	}

	/**
	 * Adds a batch of IItem objects to ProductItems
	 * 
	 * @param batch the batch of items
	 *
	 * {@pre batch != null && batch.size() > 0}
	 * 
	 * {@post batch is added}
	 */
	public void addBatch(List<IItem> batch) {
		// get the product
		IProduct product = batch.get(0).getProduct();
		// get the item list
		List<IItem> itemList = map.get(product);
		// create a new one if it doesn't already exist
		if (itemList == null) {
			itemList = new ArrayList<IItem>();
			product.addProductContainer(productContainer);
			map.put(product, itemList);
		}
		// add items to list
		itemList.addAll(batch);	
	}

	public void removeProduct(IProduct product) {
		//TODO: It says this is an optional operation. I need to make sure it actually exists.
		map.remove(product);
		product.removeProductContainer(productContainer);
	}
}
