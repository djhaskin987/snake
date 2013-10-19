package gui.batches;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.math.NumberUtils;

import model.Barcode;
import model.IItem;
import model.IProduct;
import model.IProductContainer;
import model.InvalidHITDateException;
import model.Model;
import model.StorageUnit;
import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.main.GUI;
import gui.product.*;

/**
 * Controller class for the add item batch view.
 */
public class AddItemBatchController extends Controller implements
		IAddItemBatchController {

	ProductContainerData productContainerData;
	ArrayList<ProductData> products;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add item batch view.
	 * @param target Reference to the storage unit to which items are being added.
	 */
	public AddItemBatchController(IView view, ProductContainerData target) {
		super(view);
		productContainerData = target;
		products = new ArrayList<ProductData>();
		construct();
	}

	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IAddItemBatchView getView() {
		return (IAddItemBatchView) super.getView();
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
		getView().setBarcode(new Barcode().getBarcode());
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
		if(
				!getView().getUseScanner()
				&& Barcode.isValidBarcode(getView().getBarcode())
				&& NumberUtils.isDigits(getView().getCount())
				&& Integer.parseInt(getView().getCount()) > 0
				) {
			getView().enableItemAction(true);
		} else {
			getView().enableItemAction(false);
		}
		getView().enableUndo(false);
		getView().enableRedo(false);
	}

	/**
	 * This method is called when the "Entry Date" field in the
	 * add item batch view is changed by the user.
	 * 
	 * This will set the activity of the "Add Item" button, depending
	 * on whether the field is valid or not.
	 * 
	 * {@pre None}
	 * 
	 * {@post activity of "Add Item" button altered}
	 */
	@Override
	public void entryDateChanged() {
	}

	/**
	 * This method is called when the "Count" field in the
	 * add item batch view is changed by the user.
	 * 
	 * This will set the activity of the "Add Item" button, depending
	 * on whether the field is valid or not.
	 * 
	 * {@pre None}
	 * 
	 * {@post activity of "Add Item" button altered}
	 */
	@Override
	public void countChanged() {
		enableComponents();
	}

	/**
	 * This method is called when the "Product Barcode" field in the
	 * add item batch view is changed by the user.
	 * 
	 * This will set the activity of the "Add Item" button, depending
	 * on whether the field is valid or not.
	 * 
	 * {@pre None}
	 * 
	 * {@post activity of "Add Item" button altered}
	 */
	@Override
	public void barcodeChanged() {
		enableComponents();
	}

	/**
	 * This method is called when the "Use Barcode Scanner" setting in the
	 * add item batch view is changed by the user.
	 *
	 * This will set the activity of the "Add Item" button, depending
	 * on whether the field is valid or not.
	 * 
	 * {@pre None}
	 * 
	 * {@post activity of "Add Item" button altered}
	 */
	@Override
	public void useScannerChanged() {
		enableComponents();
	}

	/**
	 * This method is called when the selected product changes
	 * in the add item batch view.
	 * 
	 * This will set the activity of the "Add Item" button, depending
	 * on whether the field is valid or not.
	 * 
	 * {@pre None}
	 * 
	 * {@post activity of "Add Item" button altered}
	 */
	@Override
	public void selectedProductChanged() {
		getView().setItems((ItemData[]) getView().getSelectedProduct().getTag());
	}

	/**
	 * This method is called when the user clicks the "Add Item" button
	 * in the add item batch view.
	 * 
	 * items in batch are added
	 * 
	 * {@pre none}
	 * 
	 * {@post items in batch added}
	 */
	@Override
	public void addItem() {
		IProduct product = Model.getInstance().getProduct(getView().getBarcode());
		if(product == null) {
			getView().displayAddProductView();
			product = Model.getInstance().getProduct(getView().getBarcode());
			if(product == null) {
				return;
			}
		}
		IProductContainer current = (IProductContainer) productContainerData.getTag();
		while(!(current instanceof StorageUnit)) {
			current = current.getParent();
		}
		String storageUnitName = current.getName().toString();
		IItem item;
		try {
			item = Model.getInstance().createItem(product, getView().getEntryDate());
		} catch (InvalidHITDateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		int count = Integer.parseInt(getView().getCount());
		ItemData[] items = new ItemData[count];
		for(int i=0; i<count; ++i) {
			ItemData itemData = new ItemData();
			itemData.setBarcode(item.getBarcode().getBarcode());
			itemData.setEntryDate(getView().getEntryDate());
			if(item.getExpireDate() != null) {
				itemData.setExpirationDate(item.getExpireDate().toJavaUtilDate());
			}
			itemData.setProductGroup(productContainerData.getName());
			itemData.setStorageUnit(storageUnitName);
			itemData.setTag(item);
			item.setTag(itemData);
			//It might be best to wait until the window is closed.
			//Model.getInstance().addItem(item, (IProductContainer) productContainerData.getTag());
			items[i] = itemData;
		}
		ProductData baseProductData = (ProductData) product.getTag();
		ProductData productData = new ProductData();
		productData.setBarcode(baseProductData.getBarcode());
		productData.setDescription(baseProductData.getDescription());
		productData.setShelfLife(baseProductData.getShelfLife());
		productData.setSize(baseProductData.getSize());
		productData.setSupply(baseProductData.getSupply());

		productData.setCount(getView().getCount());

		productData.setTag(items);
		
		products.add(productData);
		getView().setProducts(products.toArray(new ProductData[0]));
		getView().setItems(items);
		
		getView().setCount("1");
		getView().setEntryDate(new java.util.Date());
		
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the add item batch view.
	 */
	@Override
	public void redo() {
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the add item batch view.
	 */
	@Override
	public void undo() {
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the add item batch view.
	 * 
	 * Closes view
	 * 
	 * {@pre view is not closed}
	 * 
	 * {@post closes view}
	 */
	@Override
	public void done() {
		for(ProductData product : products) {
			for(ItemData item : (ItemData[]) product.getTag()) {
				Model.getInstance().addItem((IItem) item.getTag(), (IProductContainer) productContainerData.getTag());
			}
		}
		getView().close();
	}
	
}

