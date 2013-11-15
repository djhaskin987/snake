package model;


import java.io.Serializable;

import model.reports.ReportVisitor;

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
	private IProductContainer parentContainer;
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
	public ProductGroup(NonEmptyString name, IProductContainer parent, Quantity threeMonthSupply) {
		super(name);
		this.parentContainer = parent;
		this.threeMonthSupply = threeMonthSupply;
	}
	
	// for testing only
	ProductGroup() {
		super();
	}
	
	// for testing only
	public ProductGroup(NonEmptyString nonEmptyString) {
		super(nonEmptyString);
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
	
	// internal
	@Override
	public void setParent(IProductContainer container) {
		parentContainer = container;
	}

	
	/**
	 * Gets the three month supply for the product group, if it exists.
	 * 
	 * @return string of three month supply if it exists. null otherwise
	 * 
	 * {@pre threeMonthSupply is a valid Quantity (but can be null)}
	 * 
	 * {@post string of threeMonthSupply}
	 */
	@Override
	public String getThreeMonthSupply() {
		if (threeMonthSupply != null)
			return threeMonthSupply.toString();
		else
			return "";
	}
	
	public Quantity getThreeMonthSupplyQuantity()
	{
		return threeMonthSupply;
	}
	/**
	 * @param threeMonthSupply
	 * 
	 * {@pre	n/a}
	 * 
	 * {@post	threMonthSupply is set to the given value}
	 */
	public void setThreeMonthSupply(Quantity threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
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

	@Override
	public Unit getThreeMonthSupplyUnit() {
		return threeMonthSupply.getUnit();
	}

	@Override
	public String getThreeMonthSupplyValueString() {
		return threeMonthSupply.getValueString();
	}

	@Override
	public void accept(ReportVisitor v) {
		// TODO Auto-generated method stub
		
	}

}
