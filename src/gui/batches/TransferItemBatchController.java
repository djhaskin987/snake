package gui.batches;

import java.util.ArrayList;
import java.util.Collection;

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
		private IProductContainer target;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the transfer item batch view.
	 * @param target Reference to the storage unit to which items are being transferred.
	 */
	public TransferItemBatchController(IView view, ProductContainerData target) {
		super(view);
		this.target = (IProductContainer) target.getTag();
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

		Model m = Model.getInstance();
		ItemCollection ic = m.getItemCollection();
		Collection<IItem> c = ic.getItems();
		ArrayList<ItemData> iDataList = new ArrayList<ItemData>();
		for(IItem i : c) {
			ItemData iData = (ItemData) i.getTag();
			iDataList.add(iData);
		}
		ItemData[] iDataAry = new ItemData[iDataList.size()];
		iDataAry = iDataList.toArray(iDataAry);
		ITransferItemBatchView v = getView();
		v.setItems(iDataAry);
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
		ITransferItemBatchView v = getView();
		ItemData iData = v.getSelectedItem();
		IItem item = (IItem) iData.getTag();
		Model m = Model.getInstance();
		m.transferItem(item, target);
		loadValues();	
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the transfer item batch view.
	 */
	@Override
	public void redo() {
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the transfer item batch view.
	 */
	@Override
	public void undo() {
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the transfer item batch view.
	 */
	@Override
	public void done() {
		getView().close();
	}

}

