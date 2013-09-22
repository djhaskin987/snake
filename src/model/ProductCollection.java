package model;

import java.util.TreeMap;

/**
 * Singelton Design Pattern because there is only one
 * Collection of Products that the whole program uses
 * It contains all known Products in a map
 * @author Kevin
 *
 */
public class ProductCollection {
	private TreeMap<Barcode, Product> products;
	
	private static ProductCollection instance = null;
	
	
    public ProductCollection() {
    	products = new TreeMap<Barcode, Product>();
    }
   
    /**
     * Singelton Creation
     * @return Instance of ProductCollection
     */
    public static ProductCollection getInstance() {
      if(instance == null) {
         instance = new ProductCollection();
      }
      return instance;
    }
	
    /**
     * Permanently add a product to the Collection
     * @param product to be added to collection
     */
	public void add(Product product){
		
	}
	
	/**
	 * 
	 * @param barcode
	 * @return Product by Barcode
	 */
	public Product getProduct(Barcode barcode){
		return null;
	}
}
