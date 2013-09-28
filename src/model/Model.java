package model;

public class Model {
	private static Model instance;
	
	private StorageUnits storageUnits;
	private ProductCollection productCollection;
	private ItemFactory itemFactory;
	private ProductFactory productFactory;
	private ProductContainerFactory productContainerFactory;
	
    /**
     * Singleton creation
     * @return instance of StorageUnits
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
	
	public StorageUnits getStorageUnits()
	{
		return storageUnits;
	}
	
	public ProductCollection getProductCollection()
	{
		return productCollection;
	}
	
	public ProductContainerFactory getProductContainerFactory()
	{
		return productContainerFactory;
	}
	
	public ProductFactory getProductFactory()
	{
		return productFactory;
	}
	
	public ItemFactory getItemFactory()
	{
		return itemFactory;
	}
}
