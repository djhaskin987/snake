package model.productidentifier;

public abstract class AbstractProductIdentifier implements IProductIdentifier {
	
	private IProductIdentifier next;

	@Override
	public void setNext(IProductIdentifier identifier) {
		next = identifier;
	}

	@Override
	public String getProduct(String barcode) {
		String out = _getProduct(barcode);
		if(out == null && next != null) {
			return next.getProduct(barcode);
		} else {
			return out;
		}
	}

	/**
	 * Returns the description of the product with the corresponding barcode.
	 * If it fails, it returns null.
	 * 
	 * @param barcode	The barcode of the product to check
	 * 
	 * @return			The description of the product with the corresponding barcode.
	 * If it fails, it returns null.
	 */
	protected abstract String _getProduct(String barcode);
}
