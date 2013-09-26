package model;

/**
 * Singleton Design Pattern that allows for tracking
 * all StorageUnits
 */
import java.util.List;
import java.util.TreeMap;

public class StorageUnits {
	private TreeMap<NonEmptyString, StorageUnit> storageUnits;
	private static StorageUnits instance = null;
	
	
    public StorageUnits() {
	   storageUnits = new TreeMap<NonEmptyString, StorageUnit>();
    }
   
    /**
     * Singleton creation
     * @return instance of StorageUnits
     */
    public static StorageUnits getInstance() {
      if(instance == null) {
         instance = new StorageUnits();
      }
      return instance;
    }

	
	public void setStorageUnit(String name, StorageUnit storageUnit){
		
	}
	
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
	

}
