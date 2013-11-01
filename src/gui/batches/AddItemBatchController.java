package gui.batches;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Map<ProductData, List<ItemData>> productItems;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add item batch view.
	 * @param target Reference to the storage unit to which items are being added.
	 */
	public AddItemBatchController(IView view, ProductContainerData target) {
		super(view);
		productContainerData = target;
		productItems = new HashMap<ProductData, List<ItemData>>();
		//products = new ArrayList<ProductData>();
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
		if (true == useScannerEnabled) {
			v.setBarcode("");
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
		ItemData[] iDatas = productItems.get(pData).toArray(new ItemData[0]);
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
		List<ItemData> iDatas;
		if(productItems.containsKey(pData)) {
			iDatas = productItems.get(pData);
		} else {
			iDatas = new ArrayList<ItemData>();
			productItems.put(pData, iDatas);
		}
		
		int count = getItemCount();
		// create a list of items
		ObservableArgs<IItem> items = new ObservableArgs<IItem>();
		for (int i = 0; i < count; ++i) {
			IItem item = createItem(product);
			items.add(item);
			iDatas.add((ItemData) item.getTag());
		}
		IItem lastItem = items.get(count-1);
		updateCount(count, product);
		
		Model m = Model.getInstance();
		m.addBatch(items, (IProductContainer) productContainerData.getTag());
		
		getView().setProducts(productItems.keySet().toArray(new ProductData[0]));
		getView().selectProduct((ProductData) product.getTag());
		selectedProductChanged();
		getView().selectItem((ItemData) lastItem.getTag());
		
		resetControls();
	}
	
	private void resetControls() {
		IAddItemBatchView v = getView();
		v.setBarcode("");
		v.setCount("1");
		v.setEntryDate(new java.util.Date());
	}
	
	private void updateCount(int numberOfItemsAdded, IProduct product) {
		ProductData pData = (ProductData) product.getTag();
		String pCountStr = pData.getCount();
		if (pCountStr != null && pCountStr != "") {
			int pCount = Integer.parseInt(pCountStr);
			pCount += numberOfItemsAdded;
			pCountStr = "" + pCount;
		} else {
			pCountStr = "" + numberOfItemsAdded;
		}
		pData.setCount(pCountStr);
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
		getView().close();
		if(productItems.size() == 0) {
			return;
		}
		BarcodeSheet codes = new BarcodeSheet();
		for (List<ItemData> itemDatas : productItems.values()) {
			for(ItemData itemData : itemDatas) {
				codes.addBarcode((IItem) itemData.getTag());
			}
		}
		codes.close();
		codes.print();
	}
	
}

