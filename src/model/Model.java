package model;


public class Model {
	private static Model instance;
	
	private StorageUnits storageUnits;
	private ProductCollection productCollection;
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

	
	public Model()
	{
		storageUnits = new StorageUnits();
		productCollection = new ProductCollection();
		productContainerFactory = new ProductContainerFactory();
		productFactory = new ProductFactory();
		itemFactory = new ItemFactory();
	}
	
	/**
	 * 
	 * @return the StorageUnits instance in this Model.
	 */
	public StorageUnits getStorageUnits()
	{
		return storageUnits;
	}
	
	/**
	 * 
	 * @return the ProductCollection instance in this Model.
	 */
	public ProductCollection getProductCollection()
	{
		return productCollection;
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
}