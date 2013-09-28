package model;

/**
 * Facotry Design Pattern that will allow program to not be dependent on Item constructors if Item changes
 * @author Kevin
 *
 */
public class ItemFactory {
	
	private static ItemFactory instance = null;
	
	
    public ItemFactory() {
    	
    }
   
    /**
     * Singalton Design Pattern that allows any Class to construct an Item
     * @return
     */
    public static ItemFactory getInstance() {
      if(instance == null) {
         instance = new ItemFactory();
      }
      return instance;
    }
	
	
	/**
	 * Acts as the constructor for the Item class
	 * @param product
	 * @param barcode
	 * @param entryDate
	 * @param expireDate
	 * @param container
	 * @return
	 */
	public IItem createInstance(Product product, Barcode barcode, ValidDate entryDate, ValidDate expireDate, ProductContainer container){
		return null;
	}

}
