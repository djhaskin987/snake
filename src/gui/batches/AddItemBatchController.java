package gui.batches;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.math.NumberUtils;

import common.StringOps;
import model.Barcode;
import model.IItem;
import model.IProduct;
import model.IProductContainer;
import model.InvalidHITDateException;
import model.Model;
import model.NonEmptyString;
import model.ObservableArgs;
import model.StorageUnit;
import model.ValidDate;
import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;

/**
 * Controller class for the add item batch view.
 */
public class AddItemBatchController extends Controller implements
		IAddItemBatchController {

	private ProductContainerData productContainerData;
	//private ArrayList<ProductData> products;
	private ProductItemsData productItems;
	private Commands commands;
	private Timer scannerTimer;
	private TimerTask scannerTimerTask;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add item batch view.
	 * @param target Reference to the storage unit to which items are being added.
	 */
	public AddItemBatchController(IView view, ProductContainerData target) {
		super(view);
		productContainerData = target;
		productItems = new ProductItemsData();
		commands = new Commands();
		//products = new ArrayList<ProductData>();
		scannerTimerTask = new ScannerTimerTask(this);
		scannerTimer = new Timer("Scanner Timer");
		scannerTimer.schedule(scannerTimerTask, 5000);
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
		//TODO, add debug mode to run this.
		//getView().setBarcode(new Barcode().getBarcode());
		getView().setUseScanner(true);
		getView().setCount("1");
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
		if(
				!getView().getUseScanner()
				&& !getView().getBarcode().isEmpty()
				&& NumberUtils.isDigits(getView().getCount())
				&& Integer.parseInt(getView().getCount()) > 0
				) {
			getView().enableItemAction(true);
		} else {
			getView().enableItemAction(false);
		}
		getView().enableUndo(commands.canUndo());
		getView().enableRedo(commands.canRedo());
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
		Date d = getView().getEntryDate();
		try {
			new ValidDate(d);
			getView().enableItemAction(true);
		} catch (Exception e) {
			getView().enableItemAction(false);
		}
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
		if(getView().getUseScanner() && Barcode.isValidBarcode(getView().getBarcode())) {
			String count = getView().getCount();
			if(StringOps.isNumeric(count)
					&& Integer.parseInt(count) > 0) {
				addItem();
			} else {
				getView().displayErrorMessage("Error: Count '" + count + "' is not valid.");
			}
		}
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
		IAddItemBatchView v = getView();
		boolean useScannerEnabled = v.getUseScanner();
		scannerTimer.cancel();
		if (true == useScannerEnabled) {
			v.setBarcode("");		
			scannerTimer.schedule(scannerTimerTask, 5000);
		}
	}
	
	public Timer getScannerTimer() {
		return scannerTimer;
	}
	
	class ScannerTimerTask extends TimerTask {
		private AddItemBatchController controller;
		
		public ScannerTimerTask(AddItemBatchController c) {
			controller = c;
		}
		
		@Override
		public void run() {
			String barcode = controller.getView().getBarcode();
			if (StringOps.isNullOrEmpty(barcode)) {
				controller.addItem();
			}
		}
		
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
		IAddItemBatchView v = getView();
		ProductData pData = v.getSelectedProduct();
		ItemData[] iDatas = productItems.getItemArray(pData);
		v.setItems(iDatas);	
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
		
		// Get the Product
		IProduct product = getProduct();
		if (product == null) {
			getView().displayAddProductView();
			product = getProduct();
			if (product == null) {
				return;
			}
		}
		ProductData pData = (ProductData) product.getTag();
		List<ItemData> iDatas = new ArrayList<ItemData>();
		
		int count = getItemCount();
		// create a list of items
		ObservableArgs<IItem> items = new ObservableArgs<IItem>();
		for (int i = 0; i < count; ++i) {
			IItem item = createItem(product);
			items.add(item);
			iDatas.add((ItemData) item.getTag());
		}
		commands.execute((ICommand) new AddBatchCommand(pData, iDatas, productItems, productContainerData, this));
	}
	
	public void resetControls() {
		IAddItemBatchView v = getView();
		v.setBarcode("");
		v.setCount("1");
		v.setEntryDate(new java.util.Date());
	}
	
	private ItemData createItemData(IItem item) {
		ItemData iData = new ItemData();
		Barcode barcode = item.getBarcode();
		String barcodeStr = barcode.getBarcode();
		iData.setBarcode(barcodeStr);
		String storageUnitName = getStorageUnitName();
		iData.setStorageUnit(storageUnitName);
		String productGroupName = getProductGroupName();
		iData.setProductGroup(productGroupName);
		ValidDate vEntryDate = item.getEntryDate();
		Date entryDate = vEntryDate.toJavaUtilDate();
		iData.setEntryDate(entryDate);
		model.Date dExpireDate = item.getExpireDate();
		Date expireDate;
		if(dExpireDate == null) {
			expireDate = null;
		} else {
			expireDate = dExpireDate.toJavaUtilDate();
		}
		iData.setExpirationDate(expireDate);
		iData.setTag(item);
		item.setTag(iData);
		return iData;
	}
	
	private String getStorageUnitName() {
		IProductContainer pc = (IProductContainer) productContainerData.getTag();
		while (pc.getClass() != StorageUnit.class) {
			pc = pc.getParent();
		}
		NonEmptyString neName = pc.getName();
		String name = neName.getValue();
		return name;
	}
	
	private String getProductGroupName() {
		IProductContainer pc = (IProductContainer) productContainerData.getTag();
		if (pc.getClass() != StorageUnit.class) {
			NonEmptyString neName = pc.getName();
			String name = neName.getValue();
			return name;
		} else
			return "";
	}
	
	private IItem createItem(IProduct p) {
		Model m = Model.getInstance();
		Date entryDate = getEntryDate();
		try {
			IItem item = m.createItem(p, entryDate);
			createItemData(item);
			return item;
		} catch (InvalidHITDateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private IProduct getProduct() {
		Model m = Model.getInstance();
		IAddItemBatchView v = getView();
		String barcode = v.getBarcode();
		if(barcode.isEmpty()) {
			return null;
		}
		IProduct p = m.getProduct(barcode);
		return p;
	}
	
	private int getItemCount() {
		IAddItemBatchView v = getView();
		String countStr = v.getCount();
		int count = Integer.parseInt(countStr);
		return count;
	}
	
	private Date getEntryDate() {
		IAddItemBatchView v = getView();
		Date entryDate = v.getEntryDate();
		return entryDate;
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the add item batch view.
	 */
	@Override
	public void redo() {
		commands.redo();
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the add item batch view.
	 */
	@Override
	public void undo() {
		commands.undo();
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
		getView().close();
		if(productItems.isEmpty()) {
			return;
		}
		BarcodeSheet codes = new BarcodeSheet();
		for (List<ItemData> itemDatas : productItems.itemLists()) {
			for(ItemData itemData : itemDatas) {
				codes.addBarcode((IItem) itemData.getTag());
			}
		}
		codes.close();
		codes.print();
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

