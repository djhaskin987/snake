package model;

import java.io.Serializable;

/**
 * A Storage Unit is a room, closet, pantry, cupboard, or some other enclosed area where
 * items can be stored.
 * 
 * @author Nathan Standiford
 *
 */
public class StorageUnit extends ProductContainer implements Serializable {

	/**
	 * serial version unique identifier
	 */
	private static final long serialVersionUID = 8776735239406467878L;
	
	/**
	 * Creates a new StorageUnit object
	 * 
	 * @param name the name of the storage unit
	 * 
	 * {@pre name != null}
	 * 
	 * {@post a StorageUnit object}
	 */
	StorageUnit(NonEmptyString name) {
		super(name);
	}
	
	// for testing only
	StorageUnit() {
		super();
	}


	/**
	 * Not used. Always returns null
	 * 
	 * @return null
	 * 
	 * {@pre none}
	 * 
	 * {@post null}
	 */
	@Override
	public IProductContainer getParent() {
		return null;
	}

	/**
	 * Not used. Always returns null
	 * 
	 * @return null
	 * 
	 * {@pre none}
	 * 
	 * {@post null}
	 */
	@Override
	public String getUnit() {
		return getName().getValue();
	}

	/**
	 * Not used. Always returns null
	 * 
	 * @return null
	 * 
	 * {@pre none}
	 * 
	 * {@post null}
	 */
	@Override
	public String getThreeMonthSupply() {
		return "";
	}

	/**
	 * Not used. Always returns null
	 * 
	 * @return null
	 * 
	 * {@pre none}
	 * 
	 * {@post null}
	 */
	@Override
	public String getProductGroupName() {
		return "";
	}

	/**
	 * Does nothing.
	 */
	@Override
	public Unit getThreeMonthSupplyUnit() {
		return null;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public String getThreeMonthSupplyValueString() {
		return "";
	}

	@Override
	public void setParent(ProductContainer productContainer) {
		throw new UnsupportedOperationException("StorageUnits have but one parent");
	}
}
