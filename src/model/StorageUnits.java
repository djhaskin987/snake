package model;

/**
 * Singleton Design Pattern that allows for tracking
 * all StorageUnits
 */
import java.util.List;
import java.util.TreeMap;

public class StorageUnits implements IContextPanelNode {
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
		
	}
	
	/** Adds a storage unit to the internal storage units collection.
	 * @pre 'storageUnit' is properly intialized.
	 * @post 'storageUnit' is associated by its name ('storageUnit.getName()') in this class.
	 * @param storageUnit
	 */
	public void addStorageUnit(StorageUnit storageUnit){
		
	}
	
	public List<String> getStorageUnitNames(){
		return null;
	}
	
	public StorageUnit getStorageUnit(String name){
		return null;
	}
	
	/**
	 * Deletes a Storage Unit only if it is empty
	 * @param name
	 */
	public void deleteStorageUnit(String name){
		
	}
	
	/**
	 * Checks to make sure a StorageUnit can be deleted
	 * before actually trying to delete a StorageUnit
	 * @param name
	 * @return
	 */
	public boolean canDelete(String name){
		return false;
	}

	@Override
	public String getUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getThreeMonthSupply() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProductGroupName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getItems(String productName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeItem(Barcode barcode) {
		// TODO Auto-generated method stub
		
	}
	

}
