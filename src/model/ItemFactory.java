package model;

import java.io.Serializable;

/**
 * Factory Design Pattern that will allow program to not be dependent on Item
 * constructors if Item changes
 * @author Kevin
 *
 */
public class ItemFactory implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9029684793970365098L;
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
	public IItem createInstance(IProduct product, Barcode barcode,
            IProductContainer container) {
		return new Item(product, barcode, container);
	}
	/**
	 * Acts as the constructor for the Item class
	 * 
	 * @param product the product
	 * 
	 * @param barcode the barcode
	 * 
	 * @param entryDate the entry date
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
	public IItem createInstance(IProduct product, Barcode barcode,
            ValidDate entryDate, IProductContainer container) {
		return new Item(product, barcode, entryDate, container);
	}

}
