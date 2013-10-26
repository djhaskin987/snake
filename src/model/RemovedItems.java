package model;

public class RemovedItems extends StorageUnit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1965849427692615153L;

	public void deleteItemsByProduct(IProduct product) {
		productItems.removeProduct(product);
	}

}
