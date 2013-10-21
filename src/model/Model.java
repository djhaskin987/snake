package model;

import java.util.Observable;
import java.util.Observer;


/**
 * One true singleton.
 * When you serialize, this is the only singleton you need to worry about.
 *
 */
public class Model extends ModelObservable implements Observer {
	private static Model instance;
	
	private StorageUnits storageUnits;
	private ProductCollection productCollection;
	private ItemCollection itemCollection;
	private ItemFactory itemFactory;
	private ProductFactory productFactory;
	private ProductContainerFactory productContainerFactory;
	
	
    /**
     * Singleton creation: Model, your one-stop shop
     *   for all your singleton needs
     * @return instance of Model
     */
	public static Model getInstance()
	{
		if (instance == null)
		{
			instance = new Model();
		}
		return instance;
	}

	
	Model()
	{
		storageUnits = new StorageUnits();
		productCollection = new ProductCollection();
		itemCollection = new ItemCollection();
		productContainerFactory = new ProductContainerFactory();
		productFactory = new ProductFactory();
		itemFactory = new ItemFactory();
		storageUnits.addObserver(this);
		productCollection.addObserver(this);
		itemCollection.addObserver(this);
	}
	
	/**
	 * 
	 * @return the StorageUnits instance in this Model.
	 */
	public StorageUnits getStorageUnits()
	{
		return storageUnits;
	}
	
	public void setStorageUnits(StorageUnits units){
		this.storageUnits = units;
	}
	
	public void enableProductContainer(String name){
		
	}
	
	/**
	 * 
	 * @return the ProductCollection instance in this Model.
	 */
	public ProductCollection getProductCollection()
	{
		return productCollection;
	}
	
	
	public void setProductCollection(ProductCollection products){
		this.productCollection = products;
	}
	
	/**
	 * 
	 * @return the ProductCollection instance in this Model.
	 */
	public ItemCollection getItemCollection()
	{
		return itemCollection;
	}
	
	
	public void setItemCollection(ItemCollection items){
		this.itemCollection = items;
	}
	/**
	 * 
	 * @return the ProductContainerFactory instance in this Model.
	 */
	public ProductContainerFactory getProductContainerFactory()
	{
		return productContainerFactory;
	}
	
	/**
	 * 
	 * @return the ProductFactory instance in this Model.
	 */
	public ProductFactory getProductFactory()
	{
		return productFactory;
	}
	
	/**
	 * 
	 * @return the ProductFactory instance in this Model.
	 */
	public ItemFactory getItemFactory()
	{
		return itemFactory;
	}
	
	public IProductContainer createStorageUnit(String name)
	{
		// TODO: add unit tests and javadocs
		ProductContainerFactory f = ProductContainerFactory.getInstance();
		IProductContainer s = f.createStorageUnit(name);
		return s;
	}
	
	public void addStorageUnit(IProductContainer s)
	{
		storageUnits.addStorageUnit((StorageUnit)s); 
	}
	
	public IProductContainer createProductGroup(String name, String supplyValue,
			String supplyUnit, IProductContainer parent)
	{
		Unit unit = Unit.getInstance(supplyUnit);
		Double supply = Double.parseDouble(supplyValue);
		Quantity threeMonthSupply = new Quantity(supply, unit);
		IProductContainer productGroup = productContainerFactory.createProductGroup(name, parent, threeMonthSupply);
		return productGroup;
	}

	public void addProductGroup(IProductContainer p)
	{
		storageUnits.addProductGroup((ProductGroup)p);
	}

	public void renameProductGroup(IProductContainer p)
	{
		storageUnits.changeStorageUnitName((ProductGroup)p, p.getName().toString());
	}
	
	public IProduct createProduct(String barcode, String description, Quantity itemSize, Integer shelfLife, Integer threeMonthSupply)
	{
		return productFactory.createInstance(barcode, description, itemSize, shelfLife, threeMonthSupply);
	}

	public void addProduct(IProduct product) {
		productCollection.add(product);
	}
	
	public void addItem(IItem item) {
		itemCollection.add(item);
	}

	public IProduct getProduct(String barcode) {
		return productCollection.getProduct(new Barcode(barcode));
	}
	
	public IItem getItem(String barcode) {
		return itemCollection.getItem(barcode);
	}
	
	public IItem createItem(IProduct product, java.util.Date date) throws InvalidHITDateException {
		ValidDate entryDate = new ValidDate(date);
		Date expireDate = null;
		if(product.getShelfLife() != 0) {
			expireDate = entryDate.plusMonths(product.getShelfLife());
		}
		Barcode barcode = new Barcode();
		return itemFactory.createInstance(product, barcode, expireDate, null);
	}

	public void addItem(IItem item, IProductContainer productContainer) {
		item.setProductContainer(productContainer);
		productContainer.addItem(item);
		itemCollection.add(item);
	}
	
	public void transferItem(IItem item, IProductContainer target) {
		IProductContainer current = item.getProductContainer();
		current.transferItem(item, (ProductContainer)target);
		ObservableArgs args = new ObservableArgs();
		args.add(current);
		args.add(target);
		args.add(item);
		setChanged();
		notifyObservers(target);
	}

	public boolean canTransferItems() {
		return storageUnits.canTransferItems();
	}

	public boolean canDeleteStorageUnit(String name) {
		return storageUnits.canDelete(name);
	}

	public void deleteStorageUnit(String name) {
		storageUnits.deleteStorageUnit(name);
	}


	public boolean canEditItem(String barcode) {
		return true;
		// FIXME
	}


	public boolean canEditProduct(String barcode) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean canRemoveItem() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
	}


	public boolean canAddStorageUnit(String name) {
		return getStorageUnits().canAddStorageUnit(name);
	}


	public void changeStorageUnitName(IProductContainer stU, String name) {
		getStorageUnits().changeStorageUnitName(stU, name);
		
	}

	public void changeProductGroup(IProductContainer productGroup,
			String productGroupName, String supplyValue, String supplyUnit) {
		IProductContainer parent = productGroup.getParent();
		parent.deleteProductContainer(productGroup.getName().toString());
		productGroup.setName(productGroupName);
		Double amount = Double.parseDouble(supplyValue);
		Unit unit = Unit.getInstance(supplyUnit);
		((ProductGroup)productGroup).setThreeMonthSupply(new Quantity(amount, unit));
		notifyObservers(ModelActions.EDIT_PRODUCT_GROUP, productGroup);
	}
}
