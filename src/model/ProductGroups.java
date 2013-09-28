package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * Wrapper for a map of all ProductGroups
 * @author Kevin
 *
 */
public class ProductGroups implements Serializable{
	private Map<NonEmptyString, ProductGroup> productGroups;
	
	public ProductGroups(){
		productGroups = new HashMap<NonEmptyString, ProductGroup>();
	}
	

	public Map<NonEmptyString, ProductGroup> getProductGroups() {
		return productGroups;
	}

	public void setProductGroups(Map<NonEmptyString, ProductGroup> productGroups) {
		this.productGroups = productGroups;
	}

}
