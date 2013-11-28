package model.serialization.db;

import model.Barcode;
import model.IItem;
import model.IProduct;
/** DAO associated with Item objects. */
public interface IItemDAO extends IDAO<Barcode, IItem> {
	/** Gets a product object corresponding to data for
	 * @return  the product which is associated with this item.
 	 *     Note that any many-to-one or one-to-many
	 *     relationships in the returned product will be left as null.
	 *     Only data which has a one-to-one correspondence with the IProduct
	 *     will be loaded.
	 */
	IProduct getProduct(IItem item);
	void setProduct(IItem item, IProduct product);
}
