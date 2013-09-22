package model;

import java.util.Date;
/**
 * A physical instance of a particular Product. An Item corresponds to a physical container
 * with a barcode on it. For example, a case of soda might contain 24 cans of Diet Coke. In
 * this case, the Product is “Diet Coke, 12 fl oz”, while each physical can is a distinct Item.
 * @author Kevin
 *
 */

public class Item implements IItem{
	private Product product;
	private Barcode barcode;
	private ValidDate entryDate;
	private Date exitTime;
	private ValidDate expireDate;
	private ProductContainer container;
	
	@Override
	public void setProductContainer(IProductContainer container) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setEntryDate(ValidDate date) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Product getProduct() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Barcode getBarcode() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ValidDate getEntryDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Date getExitTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ValidDate getExpireDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IProductContainer getProductContainer() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
