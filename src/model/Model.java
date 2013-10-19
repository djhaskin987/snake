package model;


/**
 * One true singleton.
 * When you serialize, this is the only singleton you need to worry about.
 *
 */
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

	
	Model()
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
	
	public IProductContainer createProductGroup(String name, ProductContainer parentContainer, Quantity threeMonthSupply)
	{
		IProductContainer productGroup = productContainerFactory.createProductGroup(name, parentContainer, threeMonthSupply);
		return productGroup;
	}

	public void addProductGroup(IProductContainer p)
	{
		storageUnits.addProductGroup((ProductGroup)p);
	}

	public void renameProductGroup(IProductContainer p)
	{
		storageUnits.renameProductGroup((ProductGroup)p);
	}
	
	public IProduct createProduct(String barcode, String description, Quantity itemSize, Integer shelfLife, Integer threeMonthSupply)
	{
		return productFactory.createInstance(barcode, description, itemSize, shelfLife, threeMonthSupply);
	}

	public void addProduct(IProduct product) {
		productCollection.add(product);
	}

	public IProduct getProduct(String barcode) {
		return productCollection.getProduct(new Barcode(barcode));
	}
	
	public IItem createItem(IProduct product, java.util.Date date) throws InvalidHITDateException {
		ValidDate entryDate = new ValidDate(date);
		Date expireDate = null;
		if(product.getShelfLife() != 0) {
			expireDate = entryDate.plusMonths(product.getShelfLife());
		}
		Barcode barcode = new Barcode();	//TODO: Is this right, or are there rules on making these?
		return itemFactory.createInstance(product, barcode, expireDate, null);
	}

	public void addItem(IItem item, IProductContainer productContainer) {
		item.setProductContainer(productContainer);
		productContainer.addItem(item);
	}
}
