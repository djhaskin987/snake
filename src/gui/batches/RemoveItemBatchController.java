package gui.batches;

import java.util.List;

import model.Barcode;
import model.IItem;
import model.Model;
import gui.common.*;
import gui.item.ItemData;
import gui.product.*;

/**
 * Controller class for the remove item batch view.
 */
public class RemoveItemBatchController extends Controller implements
		IRemoveItemBatchController {
	
	private Commands commands;
	private ProductItemsData productItems;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the remove item batch view.
	 */
	public RemoveItemBatchController(IView view) {
		super(view);
		commands = new Commands();
		productItems = new ProductItemsData();
		construct();
	}
	
	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IRemoveItemBatchView getView() {
		return (IRemoveItemBatchView)super.getView();
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
		IRemoveItemBatchView v = getView();
		String barcodeStr = v.getBarcode();
		boolean enableRemove = !barcodeStr.isEmpty();
		//boolean enableRemove = Barcode.isValidBarcode(barcodeStr);
		v.enableItemAction(enableRemove);
		v.enableRedo(commands.canRedo());
		v.enableUndo(commands.canUndo());
	}

	/**
	 * This method is called when the "Item Barcode" field is changed
	 * in the remove item batch view by the user.
	 * 
	 * This will set the activity of the "Remove Item" button, depending
	 * on whether the field is valid or not.
	 * 
	 * {@pre None}
	 * 
	 * {@post activity of "Remove Item" button altered}
	 */
	@Override
	public void barcodeChanged() {
		enableComponents();
	}
	
	/**
	 * This method is called when the "Use Barcode Scanner" setting is changed
	 * in the remove item batch view by the user.
	 * 
	 * This will set the activity of the "Remove Item" button, depending
	 * on whether the field is valid or not.
	 * 
	 * {@pre None}
	 * 
	 * {@post activity of "Remove Item" button altered}
	 */
	@Override
	public void useScannerChanged() {
	}
	
	/**
	 * This method is called when the selected product changes
	 * in the remove item batch view.
	 * 
	 * This will set the activity of the "Remove Item" button, depending
	 * on whether the field is valid or not.
	 * 
	 * {@pre None}
	 * 
	 * {@post activity of "Remove Item" button altered}
	 */
	@Override
	public void selectedProductChanged() {
		IRemoveItemBatchView v = getView();
		ProductData pData = v.getSelectedProduct();
		ItemData[] itemDatas = productItems.getItemArray(pData);
		v.setItems(itemDatas);
		v.selectItem(itemDatas[itemDatas.length-1]);
	}
	
	/**
	 * This method is called when the user clicks the "Remove Item" button
	 * in the remove item batch view.
	 * 
	 * Removes item from batch.
	 * 
	 * {@pre None}
	 * 
	 * {@post item is removed from batch}
	 */
	@Override
	public void removeItem() {
		IItem item = Model.getInstance().getItem(getView().getBarcode());
		if(item == null) {
			getView().displayErrorMessage("Error: Unrecognized barcode");
			return;
		}
		commands.execute(new RemoveItemCommand(item, productItems, this));
		enableComponents();
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the remove item batch view.
	 */
	@Override
	public void redo() {
		commands.redo();
		enableComponents();
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the remove item batch view.
	 */
	@Override
	public void undo() {
		commands.undo();
		enableComponents();
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the remove item batch view.
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

