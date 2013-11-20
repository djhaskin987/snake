package gui.batches;
import gui.item.ItemData;
import gui.product.ProductData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.IProduct;

public class ProductItemsData {
	private Map<ProductData, List<ItemData>> productItems;
	
	public ProductItemsData() {
		productItems = new HashMap<ProductData, List<ItemData>>();
	}
	
	private List<ItemData> getItems(ProductData product) {
		if(productItems.containsKey(product)) {
			return productItems.get(product);
		} else {
			List<ItemData> items = new ArrayList<ItemData>();
			productItems.put(product, items);
			return items;
		}
	}
	
	public void addItemDatas(ProductData product, List<ItemData> items) {
		getItems(product).addAll(items);
	}
	
	public void addItemData(ProductData product, ItemData item) {
		getItems(product).add(item);
	}
	
	/**
	 * Removes given items from given product
	 * Assumes LIFO. It only removes the last items.size() elements.
	 * 
	 * {@pre			items is a list of the items most recently added to this.
	 * 				product is the product that they were added to.}
	 * {@post			product no longer contains those items.}
	 * 
	 * @param product	The product to remove the items from
	 * @param items		The items to remove
	 */
	public void removeItemDatas(ProductData product, List<ItemData> items) {
		List<ItemData> allItems = productItems.get(product);
		if(allItems.size() > items.size()) {
			productItems.put(product, allItems.subList(0, allItems.size()-items.size()));
		} else {
			productItems.remove(product);
		}
	}
	
	public void removeItemData(ProductData product, ItemData item) {
		List<ItemData> items = productItems.get(product);
		if(items.size() == 1) {
			productItems.remove(product);
		} else {
			items.remove(items.size()-1);
		}
	}
	
	public List<ItemData> getItemList(ProductData product) {
		return productItems.get(product);
	}
	
	public ItemData[] getItemArray(ProductData product) {
		List<ItemData> items = productItems.get(product);
		if(items == null) {
			return new ItemData[0];
		} else {
			return items.toArray(new ItemData[0]);
		}
	}
	
	public ProductData[] getProductArray() {
		Collection<ProductData> products = productItems.keySet();
		ProductData[] out = new ProductData[products.size()];
		int i=0;
		for(ProductData product : products) {
			out[i] = new ProductData(product);
			out[i].setCount(Integer.toString(getCount(product)));
			++i;
		}
		return out;
	}

	public int getCount(ProductData product) {
		List<ItemData> items = productItems.get(product);
		if(items == null) {
			return 0;
		} else {
			return items.size();
		}
	}

	public boolean isEmpty() {
		return productItems.isEmpty();
	}

	public Collection<List<ItemData>> itemLists() {
		return productItems.values();
	}

	public ItemData getLastItem(ProductData product) {
		List<ItemData> items = productItems.get(product);
		if(items == null) {
			return null;
		} else {
			return items.get(items.size()-1);
		}
	}

	public boolean contains(ProductData product) {
		return productItems.containsKey(product);
	}
}
