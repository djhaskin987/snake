package model;

import gui.common.ITagable;
import gui.common.Tagable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.reports.ReportVisitor;

/**
 * A bar-coded product that can be stored in a Storage Unit.
 * @author Kevin
 *
 */
public class Product implements IProduct, ITagable, Serializable {
	private static final long serialVersionUID = 568171183669228421L;
	private ValidDate creation;
	private NonEmptyString barcode;
	private NonEmptyString description;
	private Quantity itemSize;
	private Integer shelfLife;
	private Integer threeMonthSupply;
	private List<IProductContainer> containers;
	private transient Tagable tagable;
	/**
	 * @param aBarcode
	 * @param aDescription
	 * @param aItemSize
	 * @param aShelfLife
	 * @param aThreeMonthSupply
	 * @param aContainers
	 * {@post a Product is created}
	 */
	public Product(NonEmptyString aBarcode, NonEmptyString aDescription, Quantity aItemSize, 
			Integer aShelfLife, Integer aThreeMonthSupply){
		this.creation = new ValidDate();
		this.barcode = aBarcode;
		this.description = aDescription;
		this.itemSize = aItemSize;
		this.shelfLife = aShelfLife;
		this.threeMonthSupply = aThreeMonthSupply;
		this.containers = new ArrayList<IProductContainer>();
		this.tagable = new Tagable();
	}
	
	public Product(String aBarcode, String aDescription, Quantity aItemSize, 
			Integer aShelfLife, Integer aThreeMonthSupply) {
		this(new NonEmptyString(aBarcode), new NonEmptyString(aDescription),
				aItemSize, aShelfLife, aThreeMonthSupply);
	}

	/**
	 * Gives a detailed textual description of the  product
	 * {@pre NonEmptyString}
	 * {@post this.description is now the NonEmptyString that was passed in}
	 */
	@Override
	public void setDescription(NonEmptyString description) {
		this.description = description;
		
	}
	
	/**
	 * The products shelf life measured in months
	 * 0 means unspecified
	 * {@pre Non-Negative Integer}
	 * @throws InvalidIntegerException
	 * {@post this.shelfLife is now the Integer that was passed in}
	 */
	@Override
	public void setShelfLife(Integer months) throws InvalidIntegerException {
		if(months < 0 || months == null){
			throw new  InvalidIntegerException();
		}
		this.shelfLife = months;
		
	}
	
	/**
	 * The number of this product needed for a three month supply
	 * 0 means not specified
	 * {@pre Non-Negative Integer}
	 * @throws InvalidIntegerException 
	 * {@post this.threeMonthSupply is now the Integer that was passed in}
	 */
	@Override
	public void setThreeMonthSupply(Integer count) throws InvalidIntegerException {
		if(count < 0 || count == null){
			throw new  InvalidIntegerException();
		}
		this.threeMonthSupply = count;
		
	}
	
	/**
	 * @return A ValidDate of when this product was first Created
	 * {@post Must be a non-null ValidDate}
	 */
	@Override
	public ValidDate getCreateDate() {
		return this.creation;
	}
	
	/**
	 * If the the integer is zero then no three month supply has been set
	 * @return The three month supply for this product
	 * {@post Must be a non-null, non-negative integer}
	 */
	@Override
	public Integer getThreeMonthSupply() {
		return this.threeMonthSupply;
	}
	
	/**
	 * @return A list of all containers that have this product inside
	 * {@post Must be a non-null List of ProductContainers}
	 */
	@Override
	public List<IProductContainer> getContainers() {
		return this.containers;
	}
	
	/**
	 * {@post Must be a non-null ValidDate}
	 */
	//Do we really need this we already have getCreateDate
	@Override
	public ValidDate getCreation() {
		return creation;
	}
	
	/**
	 * @return Barcode associated with this product
	 * {@post Must be a non-null Barcode}
	 */
	@Override
	public NonEmptyString getBarcode() {
		return barcode;
	}
	
	/**
	 * @return NonEmptyString containing the full description of this product
	 * {@post Must be a non-null NonEmptyString}
	 */
	@Override
	public NonEmptyString getDescription() {
		return description;
	}
	
	/**
	 * @return Integer of the size and unit of measure of this product
	 * {@post Must be a non-null Quantity}
	 */
	@Override
	public Quantity getItemSize() {
		return itemSize;
	}
	
	/**
	 * If shelfLife is 0 then no shelf life has been set
	 * @return Integer of how long a Product is good for in months
	 * {@post Must be a non-null, non-negative integer}
	 */
	@Override
	public Integer getShelfLife() {
		return shelfLife;
	}

	@Override
	public Collection<IItem> getItems(IProductContainer pc) {
		return pc.getItems(this);
	}

/*	@Override
	public void remove() {
		IProductContainer[] tempContainers = containers.toArray(new IProductContainer[0]);
		for(IProductContainer productContainer : tempContainers) {
			productContainer.removeProduct(this);
		}
	}*/

	public Collection<IItem> getAllItems() {
		ArrayList<IItem> out = new ArrayList<IItem>();
		for (IProductContainer container : containers) {
			Collection<IItem> items = this.getItems(container);
			out.addAll(items);
		}
		return out;
	}

	@Override
	public void addProductContainer(IProductContainer pc) {
		containers.add(pc);	
	}

	@Override
	public void removeProductContainer(IProductContainer pc) {
		containers.remove(pc);
    }

	@Override
	public void removeItem(IItem i) {
		
	}
	
	public void setItemSize(Quantity q) {
		itemSize = q;
	}

	@Override
	public Object getTag() {
		if (tagable == null)
			tagable = new Tagable();
		return tagable.getTag();
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
	public int compareTo(IProduct o) {
		int out = description.compareTo(o.getDescription());
		if(out != 0) {
			return out;
		}
		return barcode.getValue().compareTo(o.getBarcode().getValue());
	}
	
	@Override
	public String toString() {
		String barcode = this.barcode.getValue();
		String description = this.description.getValue();
		String itemSize = this.itemSize.getValueString();
		String shelfLife = this.shelfLife.toString();
		String threeMonthSupply = this.threeMonthSupply.toString();
		String container = this.containers.toString();
		String tagable = this.tagable.toString();
		String str = String.format(
				"<model.Product barcode='%s' description='%s' itemSize='%s'"
				+ " shelfLife=%s threeMonthSupply=%s containers=%s tagable=%s>",
				barcode, description, itemSize, shelfLife, threeMonthSupply,
				containers, tagable);
		return str;
	}
}
