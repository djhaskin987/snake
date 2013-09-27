package model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author Daniel Carrier
 *
 */
public class ProductItems {
	private Map<Product, Collection<Item>> map;
	
	/**
	 * @return	A collection of all the products
	 */
	public Collection<Product> getProducts() {
		return map.keySet();
	}
	
	/**
	 * @return	A collection of all the items
	 */
	public Collection<Item> getItems() {
		ArrayList<Item> out = new ArrayList<Item>();
		for(Collection<Item> itemList : map.values()) {
			out.addAll(itemList);
		}
		return out;
	}
	
	/**
	 * @param item	Item to be added
	 */
	public void addItem(Item item) {	//TODO: Should I check if the item is already in the list?
		Product product = item.getProduct();
		Collection<Item> itemList = map.get(product);
		if(itemList == null) {
			itemList = new ArrayList<Item>();
			itemList.add(item);
			map.put(product, itemList);
		} else {
			itemList.add(item);
		}
	}
	
	/**
	 * @param item	Item to be removed. Automatically removes the product if that's the last item.
	 */
	public void removeItem(Item item) {
		Product product = item.getProduct();
		Collection<Item> itemList = map.get(product);
		if(itemList.size() == 1) {
			map.remove(product);
		} else {
			itemList.remove(item);
		}
	}

	/**
	 * @param product	The product to check if this ProductItem contains.
	 * @return	True if this ProductItems contains a member of this product. False otherwise.
	 */
	public boolean contains(Product product) {
		return map.containsKey(product);
	}
	
	/**
	 * @param item	The item to check if this ProductItem contains.
	 * @return	True if this ProductItems contains this item. False otherwise.
	 */
	public boolean contains(Item item) {
		Product product = item.getProduct();
		Collection<Item> itemList = map.get(product);
		if(itemList == null) {
			return false;
		} else {
			return itemList.contains(item);
		}
	}
}
