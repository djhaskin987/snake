package model;

import java.util.Collection;
import java.util.List;
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
	public StorageUnit(NonEmptyString name) {
		super(name);
	}
	
	StorageUnit()
	{
		super();
	}
	
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

	@Override
	public void addItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProductGroup(ProductGroup productGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProductContainer(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProductContainer(String name,
			ProductContainer productContainer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transferItem(Item item, ProductContainer newProductContainer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transferProduct(Product product,
			ProductContainer newProductContainer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IProductContainer whoHasProduct(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProductContainer getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getThreeMonthSupply() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProductGroupName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getItems(String productName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeItem(Barcode barcode) {
		// TODO Auto-generated method stub
		
	}

}
