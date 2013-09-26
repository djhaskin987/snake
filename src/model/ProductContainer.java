package model;

import java.util.List;
/**
 * A super class for StorageUnits and ProductGroups that contains
 * shared methods and invariants
 * @author Kevin
 *
 */
public class ProductContainer  implements IProductContainer{
	private NonEmptyString name;
	private ProductItems productItems;
	private ProductGroups productGroups;
	@Override
	public String getUnit() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Quantity getThreeMonthSupply() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Product> getProducts() {
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
	
}
