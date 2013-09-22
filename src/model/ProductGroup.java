package model;

/**
 * A user‐defined group of Products. Product Groups are used by users to aggregate related
 * Products so they can be managed as a collection.
 * 
 * @author Kevin
 *
 */
public class ProductGroup extends ProductContainer{
	private ProductContainer parentContainer;
	private Quantity threeMonthSupply;

}
