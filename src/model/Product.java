package model;

import gui.common.Tagable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A bar-coded product that can be stored in a Storage Unit.
 * @author Kevin
 *
 */
public class Product extends Tagable implements IProduct {
	private static final long serialVersionUID = 568171183669228421L;
	private ValidDate creation;
	private Barcode barcode;
	private NonEmptyString description;
	private Quantity itemSize;
	private Integer shelfLife;
	private Integer threeMonthSupply;
	private List<IProductContainer> containers;
	/**
	 * 
	 * @param aBarcode
	 * @param aDescription
	 * @param aItemSize
	 * @param aShelfLife
	 * @param aThreeMonthSupply
	 * @param aContainers
	 * {@post a Product is created}
	 */
	public Product(Barcode aBarcode, NonEmptyString aDescription, Quantity aItemSize, 
			Integer aShelfLife, Integer aThreeMonthSupply){
		this.creation = new ValidDate();
		this.barcode = aBarcode;
		this.description = aDescription;
		this.itemSize = aItemSize;
		this.shelfLife = aShelfLife;
		this.threeMonthSupply = aThreeMonthSupply;
		this.containers = new ArrayList<IProductContainer>();
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
	public Barcode getBarcode() {
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
		NonEmptyString neDescription = getDescription();
		String description = neDescription.getValue();
		return pc.getItems(description);
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

	public void setItemSize(Quantity q) {
		itemSize = q;
	}
}