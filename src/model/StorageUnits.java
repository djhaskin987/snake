package model;

/**
 * Singleton Design Pattern that allows for tracking
 * all StorageUnits
 */
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class StorageUnits implements IContextPanelNode, Serializable{

	private static final long serialVersionUID = 8036575061038335165L;
	private TreeMap<NonEmptyString, StorageUnit> storageUnits;
	

	StorageUnits() {
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
	 * Does nothing.
	 */
	@Override
	public Collection<IItem> getItems(String productName) {
		return null;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void removeItem(Barcode barcode) {
		
	}
}
