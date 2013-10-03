package model;
import java.util.Collection;
import java.util.List;

/**
 * @author Daniel Carrier
 *
 */
public class ProductContainer implements IProductContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7498283844315351927L;
	
	protected NonEmptyString name;
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
	
	public Collection<Product> getProducts()
	{
		return productItems.getProducts();
	}
	
	//This is what you'd use for a StorageUnit.
	/*public Collection<Product> getProducts() {
		ArrayList<Product> products = new ArrayList<Product>(productItems.getProducts());
		for(ProductGroup productGroup : productGroups) {
			products.addAll(productGroup.getProducts());
		}
		return products;
	}*/
	@Override
	public Collection<Item> getItems() {
		return productItems.getItems();
	}
	
	public void add(Item item) {
		Product product = item.getProduct();
		for (ProductGroup productGroup : productGroups) {
			if(productGroup.contains(product)) {
				productGroup.add(item);
				return;
			}
		}
		productItems.addItem(item);
		return;
	}
	
	public boolean contains(Product product)
	{
		return productItems.contains(product);
	}

	@Override
	public void removeItem(Barcode barcode) {
		
		
	}

	@Override
	public void addItem(Item item) {
		productItems.addItem(item);
	}

	@Override
	public void addProductGroup(ProductGroup productGroup) {
		productGroups.add(productGroup);
	}

	@Override
	public void deleteProductContainer(String name) {
		
	}

	@Override
	public void setProductContainer(String name,
			ProductContainer productContainer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transferItem(Item item, ProductContainer newProductContainer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transferProduct(Product product,
			ProductContainer newProductContainer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IProductContainer whoHasProduct(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProductContainer getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
