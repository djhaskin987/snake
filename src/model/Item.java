package model;

import java.io.Serializable;

import model.reports.ReportVisitor;
import gui.common.ITagable;
import gui.common.Tagable;

/**
 * A physical instance of a particular Product.  An Item corresponds to a physical container
 * with a barcode on it.
 * 
 * @author Daniel Carrier
 *
 */

public class Item implements IItem, ITagable, Serializable {

	private static final long serialVersionUID = 8752461391777156867L;
	
	private IProduct product;
	private Barcode barcode;
	private ValidDate entryDate;
	private DateTime exitTime;
	private Date expireDate;
	private IProductContainer container;
	private transient Tagable tagable;
	
	/**
	 * @param product		The product this item is an instance of
	 * @param barcode		The barcode for this item (not to be confused with
     * the barcode of the product)
	 * @param expireDate	The expiration date
	 * @param container		The product group this item is to be placed in, or
     * the storage unit if it has no product group
	 * {@pre				None}
	 * {@post				An Item is created with that product, barcode,
     * expiration date, and container.
	 *			It is also automatically given the current date as an entry date,
	 *			and null as the exit time.}
	 */
	public Item(IProduct product, Barcode barcode, IProductContainer container) {
		this(product, barcode, new ValidDate(), null, container);
	}
	/**
	 * @param product		The product this item is an instance of
	 * @param barcode		The barcode for this item (not to be confused with
     * the barcode of the product)
     * @param entryDate		The entry date
	 * @param expireDate	The expiration date
	 * @param container		The product group this item is to be placed in, or
     * the storage unit if it has no product group
	 * {@pre				None}
	 * {@post				An Item is created with that product, barcode,
     * expiration date, and container.
	 *			It is also automatically given the current date as an entry date,
	 *			and null as the exit time.}
	 */
	public Item(IProduct product, Barcode barcode, ValidDate entryDate,
			IProductContainer container) {
		this(product, barcode, entryDate, null, container);
	}

	public Item(IProduct product, Barcode barcode,
			ValidDate entryDate, DateTime exitTime, IProductContainer container)
	{
		this.product = product;
		this.barcode = barcode;
		this.entryDate = entryDate;
		this.exitTime = exitTime;
		this.container = container;
		this.tagable = new Tagable();
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
	public void setExit(DateTime d)
	{
		exitTime = d;
	}
	@Override
	public IProduct getProduct() {
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
		Integer shelfLife = product.getShelfLife();
		if (shelfLife != null && shelfLife > 0)
			return entryDate.plusMonths(product.getShelfLife());
		else
			return null;
	}
	@Override
	public IProductContainer getProductContainer() {
		return container;
	}
	@Override
	public String getProductGroupName() {
		if (container == null || container.getClass() == StorageUnit.class) {
			return null;
		} else {
			NonEmptyString nes = container.getName();
			String val = nes.getValue();
			return val;
		}
	}
	@Override
	public String getStorageUnitName() {
		if (container == null) {
			return null;
		} else {
			IProductContainer cur = container;
			while (cur.getClass() != StorageUnit.class && cur.getClass() != RemovedItems.class) {
				cur = cur.getParent();
			}
			NonEmptyString nes = cur.getName();
			String val = nes.getValue();
			return val;
		}
	}
	@Override
	public StorageUnit getStorageUnit() {
		IProductContainer current = container;
		while(!(current instanceof StorageUnit)) {
			if(current == null) {
				return null;
			}
			current = current.getParent();
		}
		return (StorageUnit) current;
	}
	@Override
	public void move(IProductContainer target) {
		getProductContainer().removeItem(getBarcode());
		target.add(this);
	}
	@Override
	public Object getTag() {
		if (tagable == null)
			tagable = new Tagable();
	return tagable.getTag();
    }

	@Override
	public void unexit() {
		exitTime = null;
	}
	@Override
	public void setTag(Object o) {
		if (tagable == null)
			tagable = new Tagable();
		tagable.setTag(o);
	}
	@Override
	public boolean hasTag() {
		if (tagable == null)
			tagable = new Tagable();
		return tagable.hasTag();
	}
	@Override
	public void accept(ReportVisitor v) {
		v.visit(this);
	}
	@Override
	public String getEntryDateString() {
		if(entryDate == null) {
			return "";
		} else {
			return entryDate.toString();
		}
	}
	@Override
	public String getExpireDateString() {
		if(expireDate == null) {
			return "";
		} else {
			return expireDate.toString();
		}
	}
	@Override
	public void setProduct(IProduct product) {
		this.product = product;
	}
	
	
}
