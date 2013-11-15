package gui.batches;

import java.util.Collection;
import java.util.List;

import model.*;
import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;

/**
 * Controller class for the transfer item batch view.
 */
public class TransferItemBatchController extends Controller implements
		ITransferItemBatchController {
		private StorageUnit target;
		private Commands commands;
		private ProductItemsData productItems;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the transfer item batch view.
	 * @param target Reference to the storage unit to which items are being transferred.
	 */
	public TransferItemBatchController(IView view, ProductContainerData target) {
		super(view);
		this.target = (StorageUnit) target.getTag();
		commands = new Commands();
		productItems = new ProductItemsData();
		construct();
	}
	
	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected ITransferItemBatchView getView() {
		return (ITransferItemBatchView)super.getView();
	}

	/**
	 * Loads data into the controller's view.
	 * 
	 *  {@pre None}
	 *  
	 *  {@post The controller has loaded data into its view}
	 */
	@Override
	protected void loadValues() {
		enableComponents();
	}

	/**
	 * Sets the enable/disable state of all components in the controller's view.
	 * A component should be enabled only if the user is currently
	 * allowed to interact with that component.
	 * 
	 * {@pre None}
	 * 
	 * {@post The enable/disable state of all components in the controller's view
	 * have been set appropriately.}
	 */
	@Override
	protected void enableComponents() {
		ITransferItemBatchView v = getView();
		String barcodeStr = v.getBarcode();
		boolean enableTransfer = Barcode.isValidBarcode(barcodeStr);
		v.enableItemAction(enableTransfer);
		v.enableRedo(commands.canRedo());
		v.enableUndo(commands.canUndo());
	}

	/**
	 * This method is called when the "Item Barcode" field in the
	 * transfer item batch view is changed by the user.
	 * 
	 * This will set the activity of the "Transfer Item" button, depending
	 * on whether the field is valid or not.
	 * 
	 * {@pre None}
	 * 
	 * {@post activity of "Transfer Item" button altered}
	 */
	@Override
	public void barcodeChanged() {
		enableComponents();
	}
	
	/**
	 * This method is called when the "Use Barcode Scanner" setting in the
	 * transfer item batch view is changed by the user.
	 * 
	 * This will set the activity of the "Transfer Item" button, depending
	 * on whether the field is valid or not.
	 * 
	 * {@pre None}
	 * 
	 * {@post activity of "Transfer Item" button altered}
	 */
	@Override
	public void useScannerChanged() {
	}
	
	/**
	 * This method is called when the selected product changes
	 * in the transfer item batch view.
	 * 
	 * This will set the activity of the "Transfer Item" button, depending
	 * on whether the field is valid or not.
	 * 
	 * {@pre None}
	 * 
	 * {@post activity of "Transfer Item" button altered}
	 */
	@Override
	public void selectedProductChanged() {
		ITransferItemBatchView v = getView();
		ProductData pData = v.getSelectedProduct();
		ItemData[] itemDatas = productItems.getItemArray(pData);
		v.setItems(itemDatas);
		enableComponents();
	}
	
	/**
	 * This method is called when the user clicks the "Transfer Item" button
	 * in the transfer item batch view.
	 * 
	 * this will transfer the item to another storage unit or product group.
	 * view is refreshed
	 * 
	 * {@pre None}
	 * 
	 * {@post item is transferred and view is refreshed}
	 */
	@Override
	public void transferItem() {
		IItem item = Model.getInstance().getItem(getView().getBarcode());
		if(item == null) {
			getView().displayErrorMessage("Error: Barcode \"" + getView().getBarcode() + "\" is unrecognized");
			return;
		}
		commands.execute(new TransferItemCommand(target, (ItemData) item.getTag(), productItems, this, getView()));
		getView().setBarcode("");
		getView().giveBarcodeFocus();
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the transfer item batch view.
	 */
	@Override
	public void redo() {
		commands.redo();
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the transfer item batch view.
	 */
	@Override
	public void undo() {
		commands.undo();
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the transfer item batch view.
	 */
	@Override
	public void done() {
		getView().close();
	}

	public void refreshProducts() {
		ProductData selected = getView().getSelectedProduct();
		productItems.refreshCount();
		getView().setProducts(productItems.getProductArray());
		if(productItems.contains(selected)) {
			getView().selectProduct(selected);
		}
	}

	public void refreshItems() {
		ItemData selected = getView().getSelectedItem();
		getView().setItems(productItems.getItemArray(getView().getSelectedProduct()));
		List<ItemData> items = productItems.getItemList(getView().getSelectedProduct());
		if(items != null && items.contains(selected)) {
			getView().selectItem(selected);
		}
	}

}

