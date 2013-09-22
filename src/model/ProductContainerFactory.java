package model;

/**
 * Factory Design Pattern that will allow program to not be dependent on ProductContainer constructors if ProductContainer changes
 * @author Kevin
 *
 */
public class ProductContainerFactory {
	
	/**
	 * Creates a specific StorageUnit implementation of the ProductContainer
	 * @param name
	 * @return
	 */
	public IProductContainer createStorageUnit(String name){
		return null;
	}
	
	/**
	 * Creates a specific ProductGroup implementation of the ProductContainer
	 * @param name
	 * @return
	 */
	public IProductContainer createProductGroup(String name){
		return null;
	}

}
