package model;

/**
 * A physical instance of a particular Product.  An Item corresponds to a physical container
 * with a barcode on it.
 * 
 * @author Daniel Carrier
 *
 */

public class Item implements IItem{

	private static final long serialVersionUID = 8752461391777156867L;
	
	private Product product;
	private Barcode barcode;
	private ValidDate entryDate;
	private DateTime exitTime;
	private Date expireDate;
	private IProductContainer container;
	
	/**
	 * @param product		The product this item is an instance of
	 * @param barcode		The barcode for this item (not to be confused with the barcode of the product)
	 * @param expireDate	The expiration date
	 * @param container		The product group this item is to be placed in, or the storage unit if it has no product group
	 * @pre					None
	 * @post				An Item is created with that product, barcode, expiration date, and container.
	 *			It is also automatically given the current date as an entry date,
	 *			and null as the exit time.
	 */
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
