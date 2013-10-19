package model;

import java.util.List;

/**
 * Factory Design Pattern that will allow program to not be dependent on
 * ProductFactory constructors if ProductFactory changes
 * @author Kevin
 *
 */
public class ProductFactory {
	
	private static ProductFactory instance = null;
	
	
    public ProductFactory() {
    	
    }
   
    /**
     * Singelton creation
     * @return The instance of this class
     */
    public static ProductFactory getInstance() {
      if(instance == null) {
         instance = new ProductFactory();
      }
      return instance;
    }
	
	
	/**
	 * Acting constructor for Product
	 * @param barcode
	 * @param description
	 * @param itemSize
	 * @param shelfLife
	 * @param threeMonthSupply
	 * @param containers
	 * @return A Product
	 * {@post a Product has been created}
	 */
	public IProduct createInstance(String barcode, String description, Quantity itemSize, 
			Integer shelfLife, Integer threeMonthSupply){
		
		return new Product(new Barcode(barcode), new NonEmptyString(description), itemSize, shelfLife, threeMonthSupply);
	}

	public IProduct createInstance(Barcode barcode, NonEmptyString description,
			Quantity itemSize, Integer shelfLife, Integer threeMonthSupply) {

		return new Product(barcode, description, itemSize, shelfLife, threeMonthSupply);
	}

}
