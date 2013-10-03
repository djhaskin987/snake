package model;

import java.io.Serializable;

/**
 * A user-defined group of Products. Product Groups are used by users to aggregate
 * related Products so they can be managed as a collection.
 * @author Kevin
 *
 */
public class ProductGroup extends ProductContainer implements Serializable {
	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = -3590780103998287913L;
	private ProductContainer parentContainer;
	private Quantity threeMonthSupply;
	
	/**
	 * Creates a new ProductGroup object
	 * 
	 * @param name the name of the product group
	 * 
	 * {@pre name != null}
	 * 
	 * {@post a ProductGroup object}
	 */
	public ProductGroup(NonEmptyString name) {
		super(name);
	}
	
	// for testing only
	ProductGroup() {
		super();
	}
	


	
	/** 
	 * Gets the parent ProductContainer
	 * 
	 * @return the parent container
	 * 
	 * {@pre parentContainer != null}
	 * 
	 * {@post the parentContainer}
	 */
	@Override
	public IProductContainer getParent() {
		return parentContainer;
	}
	
	/**
	 * Nothing. Always returns null
	 * 
	 * @return null
	 * 
	 * {@pre none}
	 * 
	 * {@post null}
	 */
	@Override
	public String getUnit() {
		return null;
	}
	
	@Override
	public String getThreeMonthSupply() {
		return threeMonthSupply.toString();
	}
	
	/**
	 * Gets the product group name
	 * 
	 * @return the product group name
	 * 
	 * {@pre name != null}
	 * 
	 * {@post the name of the product group}
	 */
	@Override
	public String getProductGroupName() {
		return name.getValue();
	}
	
}
