package model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import model.reports.ReportVisitor;
import gui.common.*;
import gui.item.ItemData;

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
	private transient Tagable tagable;

	public ProductContainer(NonEmptyString name) {
		this.name = name;
		productContainers = new ProductContainers();
		productItems = new ProductItems((IProductContainer)this);
		tagable = new Tagable();
	}

	public ProductContainer()
	{
		this.name = new NonEmptyString("test");
		productContainers = new ProductContainers();
		productItems = new ProductItems((IProductContainer)this);
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
	public List<IItem> getItems(IProduct p) {
		return productItems.getItems(p);
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
		item.setProductContainer(this);
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
		{
			return true;
		}
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
	 * Adds an Item to the ProductUnit. Updates the item to know that this is the product container.
	 *
	 * @param item the Item to add
	 *
	 * {@pre item != null && productItems != null}
	 *
	 * {@post item added && item.getProductContainer() == this}
	 */
	@Override
	public void addItem(IItem item) {
		item.setProductContainer(this);
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
		/*if (getUnitPC() == newProductContainer.getUnitPC())
			throw new IllegalArgumentException(
					"Item cannot be transferred within the same storage unit.");*/
		//Yes they can.
		productItems.removeItem(item);
		newProductContainer.add(item);
	}


	/**
	 * Transfers an item to another product container.
	 *
	 * @param item the item to transfer
	 *
	 * @param newProductContainer the ProductContainer to transfer the item to
	 *
	 * @param position the position to transfer the item to
	 *
	 * {@pre item != null && newProductContainer != null && productItems.contains(items)
	 * 		&& newProductContainer.getItems(item.getProduct()).size() > 0}
	 *
	 * {@post item is transferred into the specified position}
	 */
	@Override
	public void transferItem(IItem item, ProductContainer newProductContainer, int position) {
		/*if (getUnitPC() == newProductContainer.getUnitPC())
			throw new IllegalArgumentException(
					"Item cannot be transferred within the same storage unit.");*/
		//Yes they can.
		productItems.removeItem(item);
		newProductContainer.insertItem(item, item.getProduct(), position);
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
	 * {@pre name is not null,
	 * each product is unique within a storage unit}
	 * {@post ProductContainer object}
	 */
	@Override
	public IProductContainer whoHasProduct(IProduct product) {
		if (productItems.contains(product))
		{
			return this;
		}
		else
		{
			IProductContainer child = null;
			for (IProductContainer pc : productContainers.getProductContainers().values())
			{
				child = pc.whoHasProduct(product);
				if (child != null)
				{
					break;
				}
			}
			return child;
		}
	}

	public Object getTag()
	{
		if (tagable == null)
			tagable = new Tagable();
		return tagable.getTag();
	}

	public void setTag(Object o)
	{
		if (tagable == null)
			tagable = new Tagable();
		tagable.setTag(o);
	}


	public boolean hasTag()
	{
		if (tagable == null)
			tagable = new Tagable();
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

	@Override
	public void removeProduct(IProduct product) {
		productItems.removeProduct(product);
	}

	@Override
	public void removeProductRecursive(IProduct product) {
		for(IProductContainer productContainer : productContainers) {
			productContainer.removeProductRecursive(product);
			productContainer.removeProduct(product);
		}
	}

	@Override
	public List<IItem> getItemsRecursive(IProduct product) {
		List<IItem> items = new ArrayList<IItem>();
		for(IProductContainer productContainer : productContainers) {
			items.addAll(productContainer.getItemsRecursive(product));
			Collection<IItem> c = productContainer.getItems(product);
			if (c != null)
				items.addAll(c);
		}
		return items;
	}

	@Override
	public Collection<IProduct> getProductsRecursive()
	{
		Collection<IProduct> returned = new ArrayList<IProduct>(getProducts());
		for (IProductContainer pc : productContainers)
		{
			returned.addAll(pc.getProductsRecursive());
		}
		return returned;
	}

	@Override
	public void moveProduct(IProduct product, IProductContainer target)
	{
		if (product == null)
		{
			throw new IllegalArgumentException("Product given is null.");
		}
		if (!this.getProducts().contains(product))
		{
			throw new IllegalArgumentException("this Product Container doesn't contain product '" +
					product.getDescription() + "'");
		}
		Collection<IItem> pi = productItems.getItems(product);
		productItems.removeProduct(product);
		target.addProduct(product);
		for (IItem i : pi)
		{
			target.addItem(i);
		}
	}

	public void addProduct(IProduct product)
	{
		productItems.addProduct(product);
	}

	public void insertItem(IItem item, IProduct product, int position) {
		item.setProductContainer(this);
		productItems.insertItem(item, product, position);
	}

	public void clearAllTags() {
		this.tagable = null;
		this.productItems.clearAllTags();
		for (IProductContainer container : productContainers) {
			((ProductContainer)container).clearAllTags();
		}
	}

	public void accept_traverse(ReportVisitor v) {
		Collection<IProduct> products = getProducts();
		for (IProduct p : products) {
			p.accept(v);
			Collection<IItem> items = productItems.getItems(p);
			for (IItem item : items) {
				item.accept(v);
			}
		}
		for (IProductContainer pc : productContainers) {
			pc.accept(v);
		}
	}

	@Override
	public Collection<IProductContainer> getChildren() {
		Collection<IProductContainer> pcs = productContainers.getProductContainers().values();
		return pcs;
	}

	@Override
	public IProductContainer getDescendant(String name) {
		IProductContainer descendant = productContainers.getProductContainers()
				.get(new NonEmptyString(name));
		if(descendant != null) {
			return descendant;
		}
		for(IProductContainer child : getChildren()) {
			descendant = child.getDescendant(name);
			if(descendant != null) {
				return descendant;
			}
		}
		return null;
	}
}
