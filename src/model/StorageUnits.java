package model;

/**
 * Singleton Design Pattern that allows for tracking
 * all StorageUnits
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;


public class StorageUnits extends ProductContainer implements Serializable {

	private static final long serialVersionUID = 8036575061038335165L;
	private TreeMap<NonEmptyString, StorageUnit> storageUnits;
	
	StorageUnits() {
		super(new NonEmptyString("Storage Units"));
		storageUnits = new TreeMap<NonEmptyString, StorageUnit>();
    }
	
    /** Set the storage unit associated with 'name' to 'storageUnit'.
     * {@pre 'name' is non-null and non-empty; storageUnit is nonNull}
     * {@post 'name' is now associated with 'storageUnit' in the StorageUnits class.}
     * @param name
     * @param storageUnit
     */
	public void setStorageUnit(String name, StorageUnit storageUnit){
		if (name == null || storageUnit == null)
		{
			throw new IllegalArgumentException("Arguments cannot be null.");
		}
		NonEmptyString realName = new NonEmptyString(name);
		storageUnits.remove(realName);
		storageUnits.put(realName, storageUnit);
	}
	
	/** Adds a storage unit to the internal storage units collection.
	 * {@pre 'storageUnit' is properly intialized.}
	 * {@post 'storageUnit' is associated by its name ('storageUnit.getName()') in this class.}
	 * @param storageUnit
	 */
	public void addStorageUnit(StorageUnit storageUnit){
		storageUnits.put(storageUnit.getName(), storageUnit);
		notifyObservers(ModelActions.INSERT_STORAGE_UNIT,
				storageUnit);
	}
	
	public List<String> getStorageUnitNames(){
		
		List<String> returned = new LinkedList<String>();
		for (NonEmptyString name : storageUnits.keySet())
		{
			returned.add(name.getValue());
		}
		return returned;
	}
	
	/**
	 * Gets a storage unit
	 * 
	 * @param name the storage unit name
	 * 
	 * @return a storage unit
	 * 
	 * {@pre none}
	 * 
	 * {@post a stroage unit}
	 */
	public StorageUnit getStorageUnit(String name){
		return storageUnits.get(new NonEmptyString(name));
	}
	
	/**
	 * Deletes a Storage Unit only if it is empty
	 * @param name
	 * 
	 * {@pre name != null && name != ""}
	 * 
	 * {@post storage unit is removed}
	 */
	public void deleteStorageUnit(String name){
		storageUnits.remove(new NonEmptyString(name));
	}
	
	/**
	 * Checks to make sure a StorageUnit can be deleted
	 * before actually trying to delete a StorageUnit
	 * @param name
	 * @return true if it is delete-able
	 * 
	 * {@pre name != null && name != ""}
	 * 
	 * {@post boolean value}
	 */
	public boolean canDelete(String name){
		return storageUnits.containsKey(new NonEmptyString(name));
	}

	/**
	 * Always returns "All"
	 */
	@Override
	public String getUnit() {
		return "All";
	}

	/**
	 * Does nothing.
	 */
	@Override
	public String getThreeMonthSupply() {
		return "";
	}
	
	/**
	 * Gets all of the products
	 * 
	 * @return a collection of products
	 *
	 * {@pre none}
	 * 
	 * {@post a collection of products}
	 */	
    @Override
    public Collection<IProduct> getProducts() {
		return Model.getInstance().getProductCollection().getProducts();
	}

    /**
     * Does nothing.
     */
	@Override
	public String getProductGroupName() {
		return "";
	}

	/**
	 * Returns a list of all the items of that product.
	 * 
	 * @return A list of all the items of that product.
	 */
	@Override
	public Collection<IItem> getItems(String productName) {
		return new ArrayList<IItem>();
		//TODO: Make it show all the items of that product, instead of none.
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void removeItem(Barcode barcode) {
		
	}

	public void addProductGroup(IProductContainer p) {
		p.getParent().addProductContainer(p);
		notifyObservers(ModelActions.INSERT_PRODUCT_GROUP,
				p);
	}


	
	public boolean canAddStorageUnit(String name) {
		return name != null &&
				!name.isEmpty() &&
				!getStorageUnitNames().contains(name);
	}

	public boolean canEditStorageUnit(String storageUnitName,
			IProductContainer storageUnit) {
		if (storageUnitName == null || storageUnitName.isEmpty() ||
				storageUnitName.equals(storageUnit.getName().toString()))
		{
			return false;
		}
		List<String> lst = getStorageUnitNames();
		lst.remove(storageUnit.getName().toString());
		return !lst.contains(storageUnitName);
	}
	
	public void changeStorageUnitName(IProductContainer stU, String name) {
		storageUnits.remove(stU.getName());
		stU.setName(name);
		storageUnits.put(stU.getName(), (StorageUnit) stU);
		notifyObservers(ModelActions.EDIT_STORAGE_UNIT,
				stU);
	}
	
	@Override
	public boolean hasItems()
	{
		for (StorageUnit s : storageUnits.values())
		{
			if (s.hasItems())
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean hasItemsRecursive()
	{
		for (StorageUnit s : storageUnits.values())
		{
			if (s.hasItemsRecursive())
			{
				return true;
			}
		}
		return false;
	}

	public boolean canTransferItems() {
		return hasItemsRecursive();
	}

	@Override
	public IProductContainer getParent() {
		return (IProductContainer) this;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public Unit getThreeMonthSupplyUnit() {
		throw new UnsupportedOperationException("StorageUnits class doesn't have a three month supply");
	}

	/**
	 * Does nothing.
	 */
	@Override
	public String getThreeMonthSupplyValueString() {
		return "";
	}

	@Override
	public void setParent(IProductContainer productContainer) {
		throw new UnsupportedOperationException("StorageUnits class IS the parent!");
	}
	
	@Override
	public void removeProduct(IProduct product) {
		Model.getInstance().getRemovedItems().deleteItemsByProduct(product.getDescription().getValue());
		removeProductRecursive(product);
	}
}
