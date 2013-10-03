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
	
	
    public StorageUnits() {
	   storageUnits = new TreeMap<NonEmptyString, StorageUnit>();
    }
	
    /** Set the storage unit associated with 'name' to 'storageUnit'.
     * @pre 'name' is non-null and non-empty; storageUnit is nonNull
     * @post 'name' is now associated with 'storageUnit' in the StorageUnits class.
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
	 * @pre 'storageUnit' is properly intialized.
	 * @post 'storageUnit' is associated by its name ('storageUnit.getName()') in this class.
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
	
	public StorageUnit getStorageUnit(String name){
		return storageUnits.get(new NonEmptyString(name));
	}
	
	/**
	 * Deletes a Storage Unit only if it is empty
	 * @param name
	 */
	public void deleteStorageUnit(String name){
		storageUnits.remove(new NonEmptyString(name));
	}
	
	/**
	 * Checks to make sure a StorageUnit can be deleted
	 * before actually trying to delete a StorageUnit
	 * @param name
	 * @return
	 */
	public boolean canDelete(String name){
		return storageUnits.containsKey(new NonEmptyString(name));
	}

	@Override
	public String getUnit() {
		return "All";
	}

	@Override
	public String getThreeMonthSupply() {
		return "";
	}

    @Override
    public Collection<IProduct> getProducts() {
		return Model.getInstance().getProductCollection().getProducts();
	}

	@Override
	public String getProductGroupName() {
		return "";
	}

	@Override
	public Collection<IItem> getItems(String productName) {
		return null;
	}

	@Override
	public void removeItem(Barcode barcode) {
		
	}
}
