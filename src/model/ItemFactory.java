package model;

/**
 * Factory Design Pattern that will allow program to not be dependent on Item
 * constructors if Item changes
 * @author Kevin
 *
 */
public class ItemFactory {
	
	private static ItemFactory instance = null;
	
	ItemFactory() {  	
    }
   
    /**
     * Singleton Design Pattern that allows any Class to construct an Item
     * 
     * @return an ItemFactory instance
     * 
     * {@pre none}
     * 
     * {@post an ItemFactory instance}
     */
    public static ItemFactory getInstance() {
      if(instance == null) {
         instance = new ItemFactory();
      }
      return instance;
    }
	
	
	/**
	 * Acts as the constructor for the Item class
	 * 
	 * @param product the product
	 * 
	 * @param barcode the barcode
	 * 
	 * @param expireDate the expire date
	 * 
	 * @param container the container
	 * 
	 * @return an item
	 * 
	 * {@pre See Item}
	 * 
	 * {@post an Item instance}
	 */
	public IItem createInstance(Product product, Barcode barcode,
            Date expireDate, IProductContainer container) {
		return new Item(product, barcode, expireDate, container);
	}

}
