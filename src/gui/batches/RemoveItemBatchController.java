package gui.batches;

import java.util.Collection;

import model.Barcode;
import model.IItem;
import model.IProduct;
import model.Model;
import model.ProductCollection;
import gui.common.*;
import gui.item.ItemData;
import gui.product.*;

/**
 * Controller class for the remove item batch view.
 */
public class RemoveItemBatchController extends Controller implements
		IRemoveItemBatchController {
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the remove item batch view.
	 */
	public RemoveItemBatchController(IView view) {
		super(view);

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
		IRemoveItemBatchView v = getView();
		Model m = Model.getInstance();
		ProductCollection pc = m.getProductCollection();
		Collection<IProduct> products = pc.getProducts();
		ProductData[] productDatas = new ProductData[products.size()];
		int i = 0;
		for (IProduct product : products) {
			ProductData pData = (ProductData) product.getTag();
			productDatas[i] = pData;
			++i;
		}
		v.setProducts(productDatas);
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
		boolean enableRemove = Barcode.isValidBarcode(barcodeStr);
		v.enableItemAction(enableRemove);
		// not implemented
		v.enableRedo(false);
		v.enableUndo(false);
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
		IProduct product = (IProduct) pData.getTag();
		Model m = Model.getInstance();
		Collection<IItem> items = product.getAllItems();
		ItemData[] itemDatas = new ItemData[items.size()];
		int i = 0;
		for (IItem item : items) {
			ItemData iData = (ItemData) item.getTag();
			itemDatas[i] = iData;
			++i;
		}
		v.setItems(itemDatas);
		v.selectItem(itemDatas[i - 1]);
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
		IRemoveItemBatchView v = getView();
		Model m = Model.getInstance();
		if (m.canRemoveItem(v.getBarcode())) {
			IItem item = m.getItem(v.getBarcode());
			m.removeItem(item);
		}
		v.setBarcode("");
		v.giveBarcodeFocus();
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the remove item batch view.
	 */
	@Override
	public void redo() {
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the remove item batch view.
	 */
	@Override
	public void undo() {
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the remove item batch view.
	 */
	@Override
	public void done() {
		getView().close();
	}

}

