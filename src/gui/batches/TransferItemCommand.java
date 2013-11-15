package gui.batches;

import gui.item.ItemData;
import gui.product.ProductData;

import model.IItem;
import model.IProduct;
import model.Model;
import model.StorageUnit;

/** Command used in undo/redo which transfers an item in the
 * "Transfer Items Batch" view.
 * @author Daniel Jay Haskin
 *
 */
public class TransferItemCommand implements ICommand {

	private StorageUnit storageUnitA;
	private StorageUnit storageUnitB;
	private ProductData product;
	private ItemData item;
	private ProductItemsData productItems;
	private ITransferItemBatchController controller;
	private ITransferItemBatchView view;
	boolean newProduct;
	int position;
	
	/**
	 * @param storageUnitB		Storage unit to transfer items to
	 * @param item				Item to transfer
	 * @param productItems		ProductItemsData that tracks the items transferred
	 * @param controller		ITransferItemBatchController that issued this command
	 * @param view				Corresponding ITransferItemBatchView
	 * 
	 * Creates a command to transfer item to storageUnitB
	 */
	public TransferItemCommand(
			StorageUnit storageUnitB,
			ItemData item,
			ProductItemsData productItems,
			ITransferItemBatchController controller,
			ITransferItemBatchView view)
	{
		storageUnitA = ((IItem) item.getTag()).getStorageUnit();
		this.storageUnitB = storageUnitB;
		IProduct modelProduct = ((IItem) item.getTag()).getProduct();
		product = (ProductData) modelProduct.getTag();
		this.item = item;
		this.productItems = productItems;
		this.controller = controller;
		this.view = view;
		newProduct = !storageUnitB.contains(modelProduct);
		position = Model.getInstance().getPosition((IItem) item.getTag());
	}
	/** Command associated with the transfer item batch command view.
	 * 
	 * @param none
	 * {@pre none}
	 * {@post adds the given item/product to the display map -AND-
	 * transfers it from one storage unit to another in the model.} 
	 */
	@Override
	public void execute() {
		Model m = Model.getInstance();
		m.transferItem((IItem) item.getTag(), storageUnitB);
		productItems.addItemData(product, item);
		if(view.getSelectedProduct() == product) {
			controller.refreshItems();
		}
		controller.refreshProducts();
		((TransferItemBatchController) controller).enableComponents();
	}
	/** Adds the given item/product to the display map -AND-
	 * transfers it in the model.
	 * @param none
	 * {@pre product/item has already been transferred. 
	 * {@post the item is un-added to the display list and un-added to the model.} 
	 */
	@Override
	public void undo() {
		Model m = Model.getInstance();
		m.transferItem((IItem) item.getTag(), storageUnitA, position);
		if(newProduct) {
			storageUnitB.removeProduct((IProduct) product.getTag());
		}
		productItems.removeItemData(product, item);
		if(view.getSelectedProduct() == product) {
			controller.refreshItems();
		}
		controller.refreshProducts();
		((TransferItemBatchController) controller).enableComponents();
	}
}
