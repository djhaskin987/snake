package model.productidentifier;

public interface IProductIdentifier {

	/**
	 * Sets the next IProductIdentifier in the chain of responsibility, to be checked
	 * if this one fails.
	 * 
	 * @param identifier	Next product identifier
	 */
	void setNext(IProductIdentifier identifier);
	
	/**
	 * Returns the description of the product with the corresponding barcode.
	 * If it fails, it asks the next in the chain of command.
	 * If it's the end of the chain, it returns null.
	 * 
	 * @param barcode	The barcode of the product to check
	 * 
	 * @return			The description of the product with the corresponding barcode.
	 * If it fails, it asks the next in the chain of command.
	 * If it's the end of the chain, it returns null.
	 */
	String getProduct(String barcode);
	
}
