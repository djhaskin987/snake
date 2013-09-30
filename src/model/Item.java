package model;

/**
 * A physical instance of a particular Product.  An Item corresponds to a physical container
 * with a barcode on it.
 * 
 * @author Daniel Carrier
 *
 */

public class Item implements IItem{
	private Product product;
	private Barcode barcode;
	private ValidDate entryDate;
	private DateTime exitTime;
	private Date expireDate;
	private IProductContainer container;
	
	public Item(Product product, Barcode barcode, Date expireDate, IProductContainer container) {
		this.product = product;
		this.barcode = barcode;
		this.entryDate = new ValidDate();
		this.exitTime = null;
		this.expireDate = expireDate;
		this.container = container;
	}
	@Override
	public void setProductContainer(IProductContainer container) {
		this.container = container;
	}
	@Override
	public void setEntryDate(ValidDate date) {
		entryDate = date;
	}
	@Override
	public void exit() {
		exitTime = new DateTime();
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
		return entryDate;
	}
	@Override
	public DateTime getExitTime() {
		return exitTime;
	}
	@Override
	public Date getExpireDate() {
		return expireDate;
	}
	@Override
	public IProductContainer getProductContainer() {
		return container;
	}
}
