package gui.batches;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.math.NumberUtils;

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
import gui.main.GUI;
import gui.product.*;

/**
 * Controller class for the add item batch view.
 */
public class AddItemBatchController extends Controller implements
		IAddItemBatchController {

	private ProductContainerData productContainerData;
	private ArrayList<ProductData> products;
	
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
		//TODO, add debug mode to run this.
		//getView().setBarcode(new Barcode().getBarcode());
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
		IAddItemBatchView v = getView();
		ProductData pData = v.getSelectedProduct();
		ItemData[] iDatas = getItems(pData);
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
		if(product == null) {
			getView().displayAddProductView();
			product = getProduct();
			if(product == null) {
				return;
			} else {
				ProductData pData = (ProductData) product.getTag();
				products.add(pData);
			}
		}
		
		int count = getItemCount();		
		// create a list of items
		ObservableArgs<IItem> items = new ObservableArgs<IItem>();
		IItem lastItem = null;
		for(int i=0; i<count; ++i) {
			IItem item = createItem();
			items.add(item);
			lastItem = item;
		}
		updateCount(count, product);
		
		Model.getInstance().addBatch(items, (IProductContainer) productContainerData.getTag());
		
		getView().setProducts(products.toArray(new ProductData[0]));
		getView().selectProduct((ProductData) product.getTag());
		selectedProductChanged();
		getView().selectItem((ItemData) lastItem.getTag());
		
		resetControls();
	}
	
	private void resetControls() {
		IAddItemBatchView v = getView();
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
	
	private ItemData[] getItems(ProductData pData) {
		IProduct product = (IProduct) pData.getTag();
		IProductContainer pc = (IProductContainer) productContainerData.getTag();
		Collection<IItem> items = product.getItems(pc);
		if (items.size() > 0) {
				ItemData[] iDatas = new ItemData[items.size()];
				Iterator<IItem> itr = items.iterator();
				for (int i = 0; i < iDatas.length; i++) {
					IItem item = itr.next();
					iDatas[i] = (ItemData) item.getTag();
				}
				return iDatas;
		}
		return null;
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
		Date expireDate = dExpireDate.toJavaUtilDate();
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
	
	private IItem createItem() {
		Model m = Model.getInstance();
		Date entryDate = getEntryDate();
		IProduct p = getProduct();
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
		if(products.size() == 0) {
			return;
		}
		BarcodeSheet codes = new BarcodeSheet();
		for(ProductData product : products) {
			ItemData[] itemDatas = getItems(product);
			for(ItemData itemData : itemDatas) {
				codes.addBarcode((IItem) itemData.getTag());
			}
		}
		codes.close();
		codes.print();
	}
	
}

