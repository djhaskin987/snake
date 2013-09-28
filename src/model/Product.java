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
	private List<ProductContainer> containers;
	
	public Product(Barcode aBarcode, NonEmptyString aDescription, Quantity aItemSize, 
			Integer aShelfLife, Integer aThreeMonthSupply, List<ProductContainer> aContainers){
		this.creation = new ValidDate();
		this.barcode = aBarcode;
		this.description = aDescription;
		this.itemSize = aItemSize;
		this.shelfLife = aShelfLife;
		this.threeMonthSupply = aThreeMonthSupply;
		this.containers = aContainers;
	}
	
	/**
	 * Gives a detailed textual description of the  product
	 * @pre NonEmptyString
	 * @post this.description is now the NonEmptyString that was passed in
	 */
	@Override
	public void setDescription(NonEmptyString description) {
		this.description = description;
		
	}
	
	/**
	 * The products shelf life measured in months
	 * 0 means unspecified
	 * @pre Non-Negative Integer
	 * @throws InvalidIntegerException
	 * @post this.shelfLife is now the Integer that was passed in
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
	 * @pre Non-Negative Integer
	 * @throws InvalidIntegerException 
	 * @post this.threeMonthSupply is now the Integer that was passed in
	 */
	@Override
	public void setThreeMonthSupply(Integer count) throws InvalidIntegerException {
		if(count < 0 || count == null){
			throw new  InvalidIntegerException();
		}
		this.threeMonthSupply = count;
		
	}
	
	/**
	 * @post Must be a non-null ValidDate
	 */
	@Override
	public ValidDate getCreateDate() {
		return this.creation;
	}
	
	/**
	 * @post Must be a non-null, non-negative integer
	 */
	@Override
	public Integer getThreeMonthSupply() {
		return this.threeMonthSupply;
	}
	
	/**
	 * {@post Must be a non-null List of ProductContainers}
	 */
	@Override
	public List<ProductContainer> getContainers() {
		return this.containers;
	}
	
	/**
	 * @post Must be a non-null ValidDate
	 */
	@Override
	public ValidDate getCreation() {
		return creation;
	}
	
	/**
	 * @post Must be a non-null Barcode
	 */
	@Override
	public Barcode getBarcode() {
		return barcode;
	}
	
	/**
	 * @post Must be a non-null NonEmptyString
	 */
	@Override
	public NonEmptyString getDescription() {
		return description;
	}
	
	/**
	 * @post Must be a non-null Quantity
	 */
	@Override
	public Quantity getItemSize() {
		return itemSize;
	}
	
	/**
	 * @post Must be a non-null, non-negative integer
	 */
	@Override
	public Integer getShelfLife() {
		return shelfLife;
	}
}