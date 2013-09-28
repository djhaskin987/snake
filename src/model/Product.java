package model;

import java.util.List;

/**
 * A bar-coded product that can be stored in a Storage Unit.
 * @author Kevin
 *
 */
public class Product implements IProduct{
	private ValidDate creation;
	private Barcode barcode;
	private NonEmptyString description;
	private Quantity itemSize;
	private Integer shelfLife;
	private Integer threeMonthSupply;
	private List<ProductContainer> container;
	
	@Override
	public void setDescription(NonEmptyString description) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setShelfLife(Integer months) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setThreeMonthSupply(Integer count) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ValidDate getCreateDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer getThreeMonthSupply() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<ProductContainer> getContainers() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
