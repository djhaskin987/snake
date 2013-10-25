package model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gui.common.*;

/**
 * Abstract superclass of StorageUnit and ProductGroup.
 * Basically, it's for the classes that contain products, items, and ProductGroups.
 * 
 * @author Daniel Carrier
 *
 */
public abstract class ProductContainer extends ModelObservable implements IProductContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7498283844315351927L;
	
	protected NonEmptyString name;
	protected ProductItems productItems;
	protected ProductContainers productContainers;
	private Tagable tagable;

	public ProductContainer(NonEmptyString name) {
		this.name = name;
		productContainers = new ProductContainers();
		productItems = new ProductItems();
		tagable = new Tagable();
	}
	
	public ProductContainer()
	{
		this.name = new NonEmptyString("test");
		productContainers = new ProductContainers();
		productItems = new ProductItems();
		tagable = new Tagable();
	}

	public Collection<IProductContainer> getProductContainers() {
		return productContainers.getProductContainers().values();
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
			IProduct p = i.getProduct();
			String pName = p.getDescription().getValue();
			if (pName == productName)
				items.add(i);
		}
		return items;
	}
	
	public void add(IItem item) {
		IProduct product = item.getProduct();
		for (IProductContainer productContainer : productContainers) {
			if(productContainer.contains(product)) {
				productContainer.add(item);
				return;
			}
		}
		productItems.addItem(item);
		return;
	}
	
	@Override
	public boolean hasItems()
	{
		return productItems.getItems().size() > 0;
	}
	
	@Override
	public boolean hasItemsRecursive()
	{
		if (hasItems())
			return true;
		for (IProductContainer productContainer : productContainers)
		{
			if (productContainer.hasItemsRecursive())
			{
				return true;
			}
		}
		return false;
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
	public boolean contains(IProduct product)
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
	 * Adds a batch to the ProductUnit
	 * 
	 * @param item the Item to add
	 * 
	 * {@pre item != null && productItems != null}
	 * 
	 * {@post item added}
	 */
	@Override
	public void addBatch(List<IItem> batch) {
		productItems.addBatch(batch);
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
	public void addProductContainer(IProductContainer PC) {
		PC.setParent(this);
		productContainers.add(PC);
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
		productContainers.remove(name);
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
		productContainers.setProductGroup(new NonEmptyString(name), (ProductGroup)productContainer);
	}
	
	public void setName(String name){
		this.name = new NonEmptyString(name);
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
		if (getUnitPC() == newProductContainer.getUnitPC())
			throw new IllegalArgumentException("Item cannot be transferred within the same storage unit.");
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
		
		for (IProductContainer pc : productContainers.getProductContainers().values()) {
			if (hasProduct(pc, name))
				return pc;
		}
		return null;
	}
	
	private static boolean hasProduct(IProductContainer pc, String name)
	{
		for (IProduct p : pc.getProducts()) {
			if (p.getDescription().getValue() == name)
				return true;
		}
		return false;
	}
	
	public Object getTag()
	{
		return tagable.getTag();
	}
	
	public void setTag(Object o)
	{
		tagable.setTag(o);
	}
	
	
	public boolean hasTag()
	{
		return tagable.hasTag();
	}
	
	@Override
	public boolean canAddItems()
	{
		return true;
	}
	
	@Override
	public boolean canRemoveItems()
	{
		return hasItemsRecursive();
	}
	
	@Override
	public boolean canDelete() {
		return !hasItemsRecursive();
	}
	
	@Override
	public String getUnit()
	{
		IProductContainer unit = getUnitPC();
		if (unit == null)
		{
			return "All";
		}
		else
		{
			return unit.getName().toString();
		}
	}
	
	@Override
	public IProductContainer getUnitPC()
	{

		IProductContainer child = null;
		IProductContainer parent = this;
		IProductContainer grandParent = getParent();
		while (grandParent != null && grandParent != parent)
		{
			child = parent;
			parent = grandParent;
			grandParent = grandParent.getParent();
		}
		if (child == null)
		{
			return null;
		}
		else
		{
			return child;
		}
	}
	

	/**
	 * Used to display product group name in context panel.
	 * Not all product containers have a product group.
	 * By default, returns the empty string.
	 * 
	 * @return null
	 * 
	 * {@pre none}
	 * 
	 * {@post none}
	 * {@returns the empty string}
	 */
	@Override
	public String getProductGroupName() {
		return "";
	}
	
	/**
	 * Used to display three month supply in context panel.
	 * Not all product containers have a three month supply.
	 * By default, returns the empty string.
	 * 
	 * @return null
	 * 
	 * {@pre none}
	 * 
	 * {@post none}
	 * {@returns the empty string}
	 */
	@Override
	public String getThreeMonthSupply() {
		return "";
	}
	
	@Override
	public boolean hasChild(String pcName)
	{
		NonEmptyString key = new NonEmptyString(pcName);
		return productContainers.getProductContainers().containsKey(key);
	}
}
