package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Carrier
 *
 */
public class ProductItems implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1120419510043040681L;
	
	private Map<IProduct, Collection<IItem>> map;
	
	/**
	 * @pre		None
	 * @post	Constructs an empty ProductItmes
	 */
	public ProductItems() {
		map = new HashMap<IProduct, Collection<IItem>>();
	}
	
	/**
	 * @pre		None
	 * @post	Returns a collection of all the products. Empty if this is empty.
	 * @return	A collection of all the products
	 */
	public Collection<IProduct> getProducts() {
		return map.keySet();
	}
	
	/**
	 * @pre		None
	 * @post	Returns a collection of all the items. Empty if this is empty.
	 * @return	A collection of all the items. Empty if this is empty.
	 */
	public Collection<IItem> getItems() {
		ArrayList<IItem> out = new ArrayList<IItem>();
		for(Collection<IItem> itemList : map.values()) {
			out.addAll(itemList);
		}
		return out;
	}
	
	/**
	 * @pre			item is not already in this
	 * @post		item is added
	 * @param item	Item to be added
	 */
	public void addItem(IItem item) {	//TODO: Should I check if the item is already in the list?
		IProduct product = item.getProduct();
		Collection<IItem> itemList = map.get(product);
		if(itemList == null) {
			itemList = new ArrayList<IItem>();
			itemList.add(item);
			map.put(product, itemList);
		} else {
			itemList.add(item);
		}
	}
	
	/**
	 * @pre			item is in this
	 * @post		item is removed. If item was the last Item of its Product,
	 * 		that product is also removed.
	 * @param item	Item to be removed. Automatically removes the product if that's the last item.
	 */
	public void removeItem(IItem item) {
		IProduct product = item.getProduct();
		Collection<IItem> itemList = map.get(product);
		if(itemList.size() == 1) {
			map.remove(product);
		} else {
			itemList.remove(item);
		}
	}

	/**
	 * @pre				None
	 * @post			Returns true if this ProductItems contains a member of this
     * product. False otherwise.
	 * @param product	The product to check if this ProductItem contains.
	 * @return			True if this ProductItems contains a member of this product.
     * False otherwise.
	 */
	public boolean contains(IProduct product) {
		return map.containsKey(product);
	}
	
	/**
	 * @pre				None
	 * @post			Returns true if this contains item. False otherwise.
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
}
