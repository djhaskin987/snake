package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a wrapper class that combines all Products
 * and Items into a single object to help keep
 * them linked together throughout.
 * @author Kevin
 *
 */
public class ProductItems implements Serializable{
	private Map<Product, List<Item>> productItems;

	public ProductItems(Map<Product, List<Item>> productItems) {
		this.productItems = new HashMap<Product, List<Item>>();
	}

	public Map<Product, List<Item>> getProductItems() {
		return productItems;
	}

	public void setProductItems(Map<Product, List<Item>> productItems) {
		this.productItems = productItems;
	}

}
