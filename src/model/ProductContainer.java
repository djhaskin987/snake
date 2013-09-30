package model;
import java.util.Collection;

/**
 * @author Daniel Carrier
 *
 */
public abstract class ProductContainer implements IProductContainer {
	private NonEmptyString name;
	private ProductItems productItems;
	private Collection<ProductGroup> productGroups;

	public NonEmptyString getName() {
		return name;
	}
	
	public abstract Collection<Product> getProducts();
	
	//This is what you'd use for a StorageUnit.
	/*public Collection<Product> getProducts() {
		ArrayList<Product> products = new ArrayList<Product>(productItems.getProducts());
		for(ProductGroup productGroup : productGroups) {
			products.addAll(productGroup.getProducts());
		}
		return products;
	}*/
	
	public Collection<Item> getItems() {
		return productItems.getItems();
	}
	
	public void add(Item item) {
		Product product = item.getProduct();
		for(ProductGroup productGroup : productGroups) {
			if(productGroup.contains(product)) {
				productGroup.add(item);
				return;
			}
		}
		productItems.addItem(item);
		return;
	}
	
	public abstract boolean contains(Product product);
}
