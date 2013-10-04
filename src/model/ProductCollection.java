package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeMap;

/**
 * Singelton Design Pattern because there is only one
 * Collection of Products that the whole program uses
 * It contains all known Products in a map
 * @author Daniel Haskin
 * @invariant all the Products are added both to StorageUnits and this class.
 *
 */
public class ProductCollection implements Serializable{

    private static final long serialVersionUID = 414358389788920747L;

    private TreeMap<String, IProduct> products;
    
    private static ProductCollection instance = null;
    
    
    public ProductCollection() {
        products = new TreeMap<String, IProduct>();
    }
   
    /**
     * Singelton Creation
     * @pre no guarantees.
     * @post the ProductCollection singleton instance is initialized. The instance is returned.
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
     * @pre the ProductCollection singleton instance is initialized.
     * @post the specified product is added to this ProductCollection.
     * @param product to be added to collection
     */
    public void add(Product product){
        this.products.put(product.getBarcode().getBarcode(), product);
    }
    
    /**
     * 
     * @param barcode
     * @pre the ProductCollection singleton instance is initialized.
     * @post the ProductCollection is unchanged.
     * @return Product by Barcode
     */
    public IProduct getProduct(Barcode barcode){
        return products.get(barcode.getBarcode());
    }
    
    /**
     * 
     * @pre the ProductCollection singleton instance is initialized.
     * @post the ProductCollection is unchanged.
     * @return the size of this ProductCollection.
     */
    public int getSize(){
        return this.products.size();
    }
    
    public Collection<IProduct> getProducts(){
    	return products.values();
    	
    }
}

