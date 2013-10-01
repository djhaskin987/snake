package model;
import java.util.Collection;

/**
 * @author Daniel Carrier
 *
 */
public abstract class ProductContainer implements IProductContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7498283844315351927L;
	
	private NonEmptyString name;
	protected ProductItems productItems;
	protected ProductGroups productGroups;

	public ProductContainer(NonEmptyString name) {
		this.name = name;
		productGroups = new ProductGroups();
		productItems = new ProductItems();
	}
	
	public ProductContainer()
	{
		this.name = new NonEmptyString("test");
	}

	public Collection<ProductGroup> getProductGroups() {
		return productGroups.getProductGroups().values();
	}
	
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
		Collection<ProductGroup> pgCollection = getProductGroups();
		for(ProductGroup productGroup : pgCollection) {
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
