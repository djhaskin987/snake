package model;

import gui.item.ItemData;

import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;


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
	private RemovedItems removedItems;
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
		removedItems = new RemovedItems();
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
		IProductContainer productGroup =
				productContainerFactory.createProductGroup(
						name, parent, threeMonthSupply);
		return productGroup;
	}

	public void addProductGroup(IProductContainer p)
	{
		storageUnits.addProductGroup((ProductGroup)p);
	}
	
	public IProduct createProduct(String barcode, String description,
			Quantity itemSize, Integer shelfLife, Integer threeMonthSupply)
	{
		return productFactory.createInstance(barcode, description, itemSize,
				shelfLife, threeMonthSupply);
	}

	public void addProduct(IProduct product) {
		productCollection.add(product);
		notifyObservers(ModelActions.INSERT_PRODUCT, (IModelTagable) product);
	}
	
	public void addItem(IItem item) {
		itemCollection.add(item);
	}

	public IProduct getProduct(String barcode) {
		return productCollection.getProduct(new NonEmptyString(barcode));
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
		return itemFactory.createInstance(product, barcode, null);
	}

	public void addItem(IItem item, IProductContainer productContainer) {
		item.setProductContainer(productContainer);
		productContainer.addItem(item);
		itemCollection.add(item);
	}


	public void addBatch(ObservableArgs<IItem> batch, IProductContainer productContainer) {
		for(IItem item : batch) {
			addItem(item, productContainer);
		}
		//productContainer.addBatch(batch);
		notifyObservers(ModelActions.INSERT_ITEMS, (IModelTagable)batch);
	}
	
	public void unaddItem(IItem item) {
		item.getProductContainer().removeItem(item.getBarcode());
		itemCollection.remove(item);
	}

	public void unaddBatch(ObservableArgs<IItem> batch,
			IProductContainer productContainer) {
		for(IItem item : batch) {
			unaddItem(item);
		}
		notifyObservers(ModelActions.UNDO_INSERT_ITEMS, (IModelTagable) batch);
	}

	public void unaddProductAndBatch(ObservableArgs<IItem> batch,
			IProductContainer productContainer) {
		for(IItem item : batch) {
			unaddItem(item);
		}
		productContainer.removeProduct(batch.get(0).getProduct());
		notifyObservers(ModelActions.UNDO_INSERT_PRODUCT_AND_ITEMS, (IModelTagable) batch);
	}

	public void unaddNewProductAndBatch(ObservableArgs<IItem> batch,
			IProductContainer productContainer) {
		for(IItem item : batch) {
			unaddItem(item);
		}
		productCollection.removeProduct(batch.get(0).getProduct());
		notifyObservers(ModelActions.UNDO_INSERT_NEW_PRODUCT_AND_ITEMS, (IModelTagable) batch);
	}

	public void changeStorageUnitName(IProductContainer StU, String name) {
		storageUnits.changeStorageUnitName(StU, name);
	}

	public void transferItem(IItem item, IProductContainer target) {
		// TODO Add unit tests and javadocs
		IProductContainer current = item.getProductContainer();
		current.transferItem(item, (ProductContainer)target);
		notifyObservers(ModelActions.TRANSFER_ITEMS, item);
	}

	public boolean canTransferItems() {
		return storageUnits.canTransferItems();
	}

	public boolean canDeleteProductContainer(IProductContainer pc) {
		return pc.canDelete();
	}

	public void deleteStorageUnit(String name) {
		storageUnits.deleteStorageUnit(name);
	}


	public boolean canEditItem(String barcode) {
		return true;
		// FIXME
	}


	public boolean canEditProduct(String barcode) {
		// FIXME
		return true;
	}


	public boolean canRemoveItem(String barcode) {
		// TODO Add unit Tests and javadoc
		return true;
		// FIXME doesn't actually do anything meaningful
	}

	public void removeItem(IItem item) {
		IProductContainer productContainer = item.getProductContainer();
		if (productContainer != null)
			item.exit();
			productContainer.transferItem(item, removedItems);
		Pair<ModelActions, IModelTagable> p = Pair.of(ModelActions.REMOVE_ITEMS, (IModelTagable) item);
		setChanged();
		notifyObservers(p);
	}


	//This is similar to addItem, but it does not add it to the item collection, and it notivies observers.
	public void unremoveItem(IItem item, IProductContainer productContainer, int position) {
		item.setProductContainer(productContainer);
		productContainer.insertItem(item, item.getProduct(), position);
		item.unexit();
		notifyObservers(ModelActions.UNDO_REMOVE_ITEMS, item);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		setChanged();
		this.notifyObservers(arg1);
	}


	public boolean canAddStorageUnit(String name) {
		return storageUnits.canAddStorageUnit(name);
	}


	public boolean canEditStorageUnit(String storageUnitName,
			IProductContainer tag) {
		return storageUnits.canEditStorageUnit(storageUnitName, tag);
	}


	public void changeProductGroup(IProductContainer productContainer,
			String productGroupName, String supplyValue, String supplyUnit) {
		Unit newUnit = Unit.getInstance(supplyUnit);
		Quantity newQuantity = new Quantity(
				Double.parseDouble(supplyValue), newUnit);
		ProductGroup pg = ((ProductGroup)productContainer);
		pg.getParent().deleteProductContainer(pg.getName().toString());
		if (!productGroupName.equals(productContainer.getName().toString()))
		{
			pg.setName(productGroupName);
		}
		if (!pg.getThreeMonthSupplyQuantity().equals(newQuantity))
		{
			pg.setThreeMonthSupply(newQuantity);
		}
		pg.getParent().addProductContainer(pg);
		notifyObservers(ModelActions.EDIT_PRODUCT_GROUP, pg);
	}

	private boolean areValidProductGroupSpecifiers(
			String name,
			String supplyValue,
			String supplyUnit)
	{
		return !name.equals("")
				&& NumberUtils.isNumber(supplyValue)
				&& Quantity.isValidQuantity(
						Double.parseDouble(supplyValue),
						Unit.getInstance(supplyUnit));
	}
	
	public boolean canAddProductGroup(
			IProductContainer parent,
			String name,
			String supplyValue,
			String supplyUnit)
	{
		return areValidProductGroupSpecifiers(name,
				supplyValue, supplyUnit) && 
				!parent.hasChild(name);
	}
	
	public boolean canEditProductGroup(IProductContainer productContainer,
			String productGroupName, String supplyValue, String supplyUnit) {
		if (!areValidProductGroupSpecifiers(productGroupName,
				supplyValue, supplyUnit))
		{
			return false;
		}
		Quantity correspondingQuantity =
				Quantity.createInstance(supplyValue, supplyUnit);
		ProductGroup pg = (ProductGroup)productContainer;

		boolean trampling = pg.getParent().hasChild(productGroupName);
		if (!pg.getThreeMonthSupplyQuantity().equals(correspondingQuantity))
		{
			trampling = trampling && !pg.getName().toString().equals(productGroupName);
		}
		return !trampling;
	}
	
	public void editItem(IItem item) {
		notifyObservers(ModelActions.EDIT_ITEM, item);
	}


	public boolean canDeleteProduct(IProductContainer iProductContainer, IProduct product) {
		return
				iProductContainer != null
				&& product != null
				&& (
						iProductContainer.getItems(product) == null
						|| iProductContainer.getItems(product).size() == 0
				);
	}

	//TODO: This doesn't look like it would totally remove the items of that product.
	public void deleteProduct(IProductContainer productContainer, IProduct product) {
		productContainer.removeProduct(product);
	}

	public void editProduct(IProduct product) {
		notifyObservers(ModelActions.EDIT_PRODUCT, product);	
	}

	public RemovedItems getRemovedItems() {
		return removedItems;
	}
	
	public boolean canDeleteProductGroup(IProductContainer pc)
	{
		return canDeleteProductContainer(pc);
	}
	public boolean canDeleteStorageUnit(IProductContainer pc)
	{
		return canDeleteProductContainer(pc);
	}

	public void deleteProductGroup(IProductContainer pc) {
		pc.getParent().deleteProductContainer(
				pc.getName().toString());
	}

	public void moveItemToContainer(IItem item, IProductContainer target) {
		if (!moveProduct(item.getProduct(), target))
		{
			item.move(target);
			notifyObservers(ModelActions.MOVE_ITEM, item);
		}
	}

	//Returns true if the product is already in the target storage unit.
	private boolean moveProduct(IProduct product, IProductContainer target)
	{
		IProductContainer targetUnit = target.getUnitPC();
		IProductContainer ExistingPC = targetUnit.whoHasProduct(product);
		if (ExistingPC != null)
		{
			ExistingPC.moveProduct(product, target);
			ObservableArgs<IModelTagable> args = new ObservableArgs<IModelTagable>();
			args.add(product);
			args.add(target);
			notifyObservers(ModelActions.TRANSFER_PRODUCT, args);
			return true;
		}
		else
		{
			return false;
		}
	}
	public void addProductToContainer(IProduct product, IProductContainer target) {
		if (!moveProduct(product, target))
		{
			target.addProduct(product);
		}
	}
	
	public void store() {
		storageUnits.store();
	}


	public int getPosition(IItem item) {
		return item.getProductContainer().getItems(item.getProduct()).indexOf(item);
	}
	
	public void load() {
		storageUnits.load();
		System.out.println("model loaded");
	}

}
