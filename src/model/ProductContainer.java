package model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Daniel Carrier
 *
 */
public abstract class ProductContainer implements IProductContainer {
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
	
	/**
	 * Retrieves a collection of products in the product group
	 * 
	 * @return an unmodifiable Collection of Products
	 * 
	 * {@pre productItems != null}
	 * 
	 * {@post unmodifiable Collection of Products}
	 */
	public Collection<IProduct> getProducts()
	{
		return productItems.getProducts();
	}
	
	
	 /** retrieve a list of items based on the product name.
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
	public Collection<IItem> getItems(String productName) {
		List<IItem> items = new ArrayList<IItem>();
		for (IItem i : productItems.getItems()) {
			Product p = i.getProduct();
			String pName = p.getDescription().getValue();
			if (pName == productName)
				items.add(i);
		}
		return items;
	}
	
	public void add(IItem item) {
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
	
	/**
	 * Checks to see if a product is in this ProductContainer
	 * 
	 * @param product the product to search for
	 * 
	 * @return true if product is contained in the ProductContainer. false otherwise 
	 * 
	 * {@pre productItems != null}
	 * 
	 * {@post true if product is contained in the ProductContainer. False otherwise. }
	 */
	public boolean contains(Product product)
	{
		return productItems.contains(product);
	}
	
	/**
	 * remove item from ProductContainer by barcode
	 * 
	 * @param barcode the barcode of item
	 * 
	 * {@pre barcode != null && productItems != null}
	 * 
	 * {@post item is removed from StorageUnit if Barcode exists}
	 */
	@Override
	public void removeItem(Barcode barcode) {
		for (IItem i : productItems.getItems()) {
			Barcode bc = i.getBarcode();
			if (bc == barcode) {
				productItems.removeItem(i);
				break;
			}
		}
	}

	
	/**
	 * Adds an Item to the ProductUnit
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
	 * Add ProductGroup to ProductContainer
	 *
	 * @param productGroup the ProductGroup to add
	 * 
	 * {@pre productGroups != null && productGroup != null}
	 * 
	 * {@post productGroup added}
	 */
	@Override
	public void addProductGroup(ProductGroup productGroup) {
		productGroup.setParent(this);
		productGroups.add(productGroup);
	}

	
	/**
	 * remove ProductContainer from ProductContainer
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
	 * Sets the product container 
	 * 
	 * @param name the product container name
	 * 
	 * @param productContainer the product container
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
		for (IProduct p : pc.getProducts()) {
			if (p.getDescription().getValue() == name)
				return true;
		}
		return false;
	}
	
}
