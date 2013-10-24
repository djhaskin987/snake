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
	 * Yields the Storage Units Manager.
	 * 
	 * @return StorageUnits manager
	 * 
	 * {@pre none}
	 * 
	 * {@post no change}
	 * {@returns the Storage Units Manager}
	 */
	@Override
	public IProductContainer getParent() {
		return Model.getInstance().getStorageUnits();
	}

	/**
	 * Does nothing.
	 */
	@Override
	public Unit getThreeMonthSupplyUnit() {
		throw new UnsupportedOperationException("Storage Unit does not have a three month supply");
	}

	/**
	 * Does nothing.
	 */
	@Override
	public String getThreeMonthSupplyValueString() {
		throw new UnsupportedOperationException("Storage Unit does not have a three month supply");
	}

	@Override
	public void setParent(IProductContainer productContainer) {
		throw new UnsupportedOperationException("StorageUnits have but one parent");
	}
}
