package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Observable;
import java.util.TreeMap;

public class ItemCollection extends Observable implements Serializable {

	private static final long serialVersionUID = -874183618321467668L;

	private TreeMap<String, IItem> items;
    
    private static ItemCollection instance = null;
    
    
    public ItemCollection() {
        items = new TreeMap<String, IItem>();
    }
   
    /**
     * Singelton Creation
     * {@pre no guarantees.}
     * {@post the ProductCollection singleton instance is initialized. The instance is returned.}
     * @return Instance of ProductCollection
     */
    public static ItemCollection getInstance() {
      if(instance == null) {
         instance = new ItemCollection();
      }
      return instance;
    }
    
    /**
     * Permanently add a product to the Collection
     * {@pre the ProductCollection singleton instance is initialized.}
     * {@post the specified product is added to this ProductCollection.}
     * @param product to be added to collection
     */
    public void add(IItem item){
        this.items.put(item.getBarcode().getBarcode(), item);
    }
    
    
    
    /**
     * 
     * @param barcode
     * {@pre the ProductCollection singleton instance is initialized.}
     * {@post the ProductCollection is unchanged.}
     * @return Product by Barcode
     */
    public IItem getItem(String barcode) {
        return items.get(barcode);
    }
    
    
    /**
     * 
     * {@pre the ProductCollection singleton instance is initialized.}
     * {@post the ProductCollection is unchanged.}
     * @return the size of this ProductCollection.
     */
    public int getSize(){
        return this.items.size();
    }
    
    public Collection<IItem> getItems() {
    	return items.values();
    	
    }

	public void remove(IItem item) {
		items.remove(item.getBarcode().getBarcode());
	}
}
