package model;

/**
 * Factory Design Pattern that will allow program to not be dependent on
 *  ProductContainer constructors if ProductContainer changes
 * @author Daniel Haskin
 */
public class ProductContainerFactory {
	
	/**
	 * Creates a specific StorageUnit implementation of the ProductContainer
	 * @param name
	 * {@pre class is initialized.}
	 * {@post no state is changed.}
	 * @return an IProductContainer instance representing a StorageUnit.
	 */
	public IProductContainer createStorageUnit(String name){
		NonEmptyString nesName = new NonEmptyString(name);
		return (IProductContainer) new StorageUnit(nesName);
	}
	
	/**
	 * Creates a specific ProductGroup implementation of the ProductContainer
	 * {@pre class is initialized.}
	 * {@post no state is changed.}
	 * @return an IProductContainer instance representing a ProductGroup.
	 */
	public IProductContainer createProductGroup(String name, ProductContainer parentContainer, Quantity threeMonthSupply){
		NonEmptyString nesName = new NonEmptyString(name);
		return (IProductContainer) new ProductGroup(nesName, parentContainer, threeMonthSupply);
	}

	public IProductContainer createProductGroup(String name) {
		NonEmptyString nesName = new NonEmptyString(name);
		return (IProductContainer) new ProductGroup(nesName);
	}
}
