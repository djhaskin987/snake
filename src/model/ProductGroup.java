package model;

import java.util.Collection;
import java.util.List;

/**
 * A user-defined group of Products. Product Groups are used by users to aggregate
 * related Products so they can be managed as a collection.
 * @author Kevin
 *
 */
public class ProductGroup extends ProductContainer {
	private ProductContainer parentContainer;
	private Quantity threeMonthSupply;
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
