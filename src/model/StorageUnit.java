package model;

import java.util.Collection;

/**
 * A Storage Unit is a room, closet, pantry, cupboard, or some other enclosed area where
 * items can be stored.
 * @author Kevin
 *
 */
public class StorageUnit extends ProductContainer{

	@Override
	public Collection<Product> getProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(Product product) {
		// TODO Auto-generated method stub
		return false;
	}

}
