package model;

import java.util.Date;

/**
 * A physical instance of a particular Product.  An Item corresponds to a physical container
 * with a barcode on it.
 * 
 * @author Kevin
 *
 */

public class Item implements IItem{
	private Product product;
	private Barcode barcode;
	private ValidDate entryDate;
	private Date exitTime;	//This is supposed to be DateTime.
	private ValidDate expireDate;
	private ProductContainer container;
	
	public Item(Product product, Barcode barcode, ValidDate expireDate, ProductContainer container) {
		this.product = product;
		this.barcode = barcode;
		this.entryDate = new ValidDate();
		this.exitTime = null;
		this.expireDate = expireDate;
		this.container = container;
	}
	
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
		return product;
	}
	@Override
	public Barcode getBarcode() {
		return barcode;
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
