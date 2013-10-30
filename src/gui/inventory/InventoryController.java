package gui.inventory;

import gui.common.*;
import gui.item.*;
import gui.product.*;

import java.util.*;

import model.IItem;
import model.IProduct;
import model.IProductContainer;
import model.Model;
import model.ModelActions;
import model.NonEmptyString;
import model.ObservableArgs;
import model.Quantity;
import model.StorageUnit;
import model.StorageUnits;
import model.Unit;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Controller class for inventory view.
 */
public class InventoryController extends Controller
									implements IInventoryController {

	/**
	 * Constructor.
	 *
	 * @param view Reference to the inventory view
	 */
	public InventoryController(IInventoryView view) {
		super(view);
		construct();
		initObservers();

	}

	private void initObservers() {
		Model.getInstance().addObserver(this);
	}

	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IInventoryView getView() {
		return (IInventoryView)super.getView();
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
		ProductContainerData root = new ProductContainerData();
		StorageUnits su = Model.getInstance().getStorageUnits();
		su.setTag(root);
		root.setTag(su);
		getView().setProductContainers(root);
		System.out.println("Inventory Controller values loaded");
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

	//
	// IInventoryController overrides
	//

	/**
	 * Returns true iff the "Add Storage Unit" menu item should be enabled.
	 */
	@Override
	public boolean canAddStorageUnit() {
		// per the functional specs.
		return true;
	}

	/**
	 * Returns true iff the "Add Items" menu item should be enabled.
	 */
	@Override
	public boolean canAddItems() {
		return getSelectedModelProductContainer().canAddItems();
	}

	/**
	 * Returns true iff the "Transfer Items" menu item should be enabled.
	 */
	@Override
	public boolean canTransferItems() {
		return Model.getInstance().canTransferItems();
	}

	/**
	 * Returns true iff the "Remove Items" menu item should be enabled.
	 */
	@Override
	public boolean canRemoveItems() {
		System.out.println("Can remove items");
		return getSelectedModelProductContainer().canRemoveItems();
	}
	
	public IProductContainer getSelectedModelProductContainer()
	{
		ProductContainerData pcd = getView().getSelectedProductContainer();
		IProductContainer pc = (IProductContainer) pcd.getTag();
		return pc;
	}
	/**
	 * Returns true iff the "Delete Storage Unit" menu item should be enabled.
	 */
	@Override
	public boolean canDeleteStorageUnit() {
		return getModel().canDeleteStorageUnit(
				getSelectedModelProductContainer());
	}

	private void blankOutContext()
	{
		getView().setContextGroup("");
		getView().setContextSupply("");
		getView().setContextUnit("");
	}
	
	/**
	 * This method is called when the user selects the "Delete Storage Unit" menu item.
	 */
	@Override
	public void deleteStorageUnit() {
		blankOutContext();
		getModel().deleteStorageUnit(
				getView().getSelectedProductContainer().getName());
		getView().deleteProductContainer(getView().getSelectedProductContainer());
	}

	/**
	 * Returns true iff the "Edit Storage Unit" menu item should be enabled.
	 */
	@Override
	public boolean canEditStorageUnit() {
		// per the functional specs.
		return true;
	}

	/**
	 * Returns true iff the "Add Product Group" menu item should be enabled.
	 */
	@Override
	public boolean canAddProductGroup() {
		// per page 15 of the spec, this is always true.
		return true;
	}

	/**
	 * Returns true iff the "Delete Product Group" menu item should be enabled.
	 */
	@Override
	public boolean canDeleteProductGroup() {
		return getModel().canDeleteProductGroup(
				getSelectedModelProductContainer());
		
	}

	/**
	 * Returns true iff the "Edit Product Group" menu item should be enabled.
	 */
	@Override
	public boolean canEditProductGroup() {
		return true;
	}

	/**
	 * This method is called when the user selects the "Delete Product Group" menu item.
	 *
	 * product group is deleted
	 *
	 * {@pre none}
	 *
	 * {@post product group is deleted}
	 */
	@Override
	public void deleteProductGroup() {
		getView().setContextGroup("");
		getView().setContextSupply("");
		getView().setContextUnit("");
		getModel().deleteProductGroup(
				getSelectedModelProductContainer());
		getView().deleteProductContainer(getView().getSelectedProductContainer());
	}

	/**
	 * This method is called when the selected item container changes.
	 */
	@Override
	public void productContainerSelectionChanged() {
		ProductContainerData pcd = getView().getSelectedProductContainer();
		IProductContainer node = (IProductContainer) pcd.getTag();
		getView().setContextGroup(node.getProductGroupName());
		getView().setContextUnit(node.getUnit());
		getView().setContextSupply(node.getThreeMonthSupply());
		refreshProducts();
		refreshItems();
	}

	/**
	 * This method is called when the selected item changes.
	 */
	@Override
	public void productSelectionChanged() {
		ProductContainerData pcd = getView().getSelectedProductContainer();
		IProductContainer node = (IProductContainer) pcd.getTag();
		ProductData productData = getView().getSelectedProduct();
		Collection<IItem> items = node.getItems((IProduct) productData.getTag());
		if(items == null) {
			getView().setItems(new ItemData[0]);
			return;
		}

		ItemData[] itemDatas = new ItemData[items.size()];
		int i=0;
		for(IItem item : items) {
			itemDatas[i] = (ItemData) item.getTag();
			++i;
		}
		getView().setItems(itemDatas);
	}

	/**
	 * This method is called when the selected item changes.
	 */
	@Override
	public void itemSelectionChanged() {
		return;
	}

	/**
	 * Returns true if and only if the "Delete Product" menu item should be enabled.
	 */
	@Override
	public boolean canDeleteProduct() {
		return getModel().canDeleteProduct(
				(IProductContainer) getView().getSelectedProductContainer().getTag(),
				(IProduct) getView().getSelectedProduct().getTag()
				);
	}

	/**
	 * This method is called when the user selects the "Delete Product" menu item.
	 */
	@Override
	public void deleteProduct() {
		getModel().deleteProduct(
				(IProductContainer) getView().getSelectedProductContainer().getTag(),
				(IProduct) getView().getSelectedProduct().getTag()
				);
		refreshProducts();
	}

	/**
	 * Returns true if and only if the "Edit Item" menu item should be enabled.
	 */
	@Override
	public boolean canEditItem() {
		if(getView().getSelectedItem() == null) {
			return false;
		}
		return Model.getInstance().canEditItem(
				getView().getSelectedItem().getBarcode());
	}

	/**
	 * This method is called when the user selects the "Edit Item" menu item.
	 */
	@Override
	public void editItem() {
		getView().displayEditItemView();
	}

	/**
	 * Returns true if and only if the "Remove Item" menu item should be enabled.
	 */
	@Override
	public boolean canRemoveItem() {
		IInventoryView v = getView();
		ItemData iData = v.getSelectedItem();
		if(iData == null) {
			return false;
		}
		return getModel().canRemoveItem(iData.getBarcode());
	}

	/**
	 * This method is called when the user selects the "Remove Item" menu item.
	 */
	@Override
	public void removeItem() {
		Model m = Model.getInstance();
		IInventoryView v = getView();
		ItemData iData = v.getSelectedItem();
		IItem item = (IItem) iData.getTag();
		m.removeItem(item);
	}

	/**
	 * Returns true if and only if the "Edit Product" menu item should be enabled.
	 */
	@Override
	public boolean canEditProduct() {
		if(getView().getSelectedProduct() == null) {
			return false;
		}
		return Model.getInstance().canEditProduct(
				getView().getSelectedProduct().getBarcode());
	}

	/**
	 * This method is called when the user selects the "Add Product Group" menu item.
	 */
	@Override
	public void addProductGroup() {
		getView().displayAddProductGroupView();
	}

	/**
	 * This method is called when the user selects the "Add Items" menu item.
	 */
	@Override
	public void addItems() {
		getView().displayAddItemBatchView();
	}

	/**
	 * This method is called when the user selects the "Transfer Items" menu item.
	 */
	@Override
	public void transferItems() {
		getView().displayTransferItemBatchView();
	}

	/**
	 * This method is called when the user selects the "Remove Items" menu item.
	 */
	@Override
	public void removeItems() {
		getView().displayRemoveItemBatchView();
	}

	/**
	 * This method is called when the user selects the "Add Storage Unit" menu item.
	 */
	@Override
	public void addStorageUnit() {
		getView().displayAddStorageUnitView();
	}

	/**
	 * This method is called when the user selects the "Edit Product Group" menu item.
	 */
	@Override
	public void editProductGroup() {
		getView().displayEditProductGroupView();
	}

	/**
	 * This method is called when the user selects the "Edit Storage Unit" menu item.
	 */
	@Override
	public void editStorageUnit() {
		getView().displayEditStorageUnitView();
	}

	/**
	 * This method is called when the user selects the "Edit Product" menu item.
	 */
	@Override
	public void editProduct() {
		getView().displayEditProductView();
	}

	/**
	 * This method is called when the user drags a product into a
	 * product container.
	 *
	 * @param productData Product dragged into the target product container
	 * @param containerData Target product container
	 */
	@Override
	public void addProductToContainer(ProductData productData,
										ProductContainerData containerData) {
		getModel().addProductToContainer(
				(IProduct)productData.getTag(),
				(IProductContainer) containerData.getTag());
		getView().selectProductContainer(containerData);
		refreshProducts();
		refreshItems();
	}

	/**
	 * This method is called when the user drags an item into
	 * a product container.
	 *
	 * @param itemData Item dragged into the target product container
	 * @param containerData Target product container
	 */
	@Override
	public void moveItemToContainer(ItemData itemData,
									ProductContainerData containerData) {
		IItem item = (IItem) itemData.getTag();
		IProductContainer target = (IProductContainer) containerData.getTag();
		getModel().moveItemToContainer(item, target);
		getView().selectProductContainer(containerData);
		refreshProducts();
		refreshItems();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// This method assumes that the only thing that InventoryController is
		// observing is the StorageUnits instance
		Pair<ModelActions, ITagable> pair = pairExtract(arg1);
		ModelActions action = pair.getLeft();
		ITagable payload = pair.getRight();
		switch (action)
		{
			case INSERT_STORAGE_UNIT:
				insertStorageUnit(payload);
				break;
			case EDIT_STORAGE_UNIT:
				editStorageUnit(payload);
				break;
			case INSERT_PRODUCT_GROUP:
				insertProductGroup(payload);
				break;
			case EDIT_PRODUCT_GROUP:
				editProductGroup(payload);
				break;
			case INSERT_ITEMS:
				insertItems(payload);
				break;
			case REMOVE_ITEMS:
				removeItems(payload);
				break;
			case TRANSFER_ITEMS:
				transferItems(payload);
				break;
			case EDIT_ITEM:
				editItem(payload);
				break;
			case MOVE_ITEM:
				moveItem(payload);
				break;
			case INSERT_PRODUCT:
				insertProduct(payload);
				break;
			case EDIT_PRODUCT:
				editProduct(payload);
				break;
			case TRANSFER_PRODUCT:
				transferProduct(payload);
				break;
			default:
				throw new IllegalStateException("Could not detect what changed");
		}
	}



	@SuppressWarnings("unchecked")
	private Pair<ModelActions, ITagable> pairExtract(Object arg1) {
		return (Pair<ModelActions, ITagable>) arg1;
	}

	private void refreshProducts() {
		IProductContainer productContainer = (IProductContainer)
				getView().getSelectedProductContainer().getTag();
		Collection<IProduct> products = productContainer.getProducts();
		ProductData[] productDatas = new ProductData[products.size()];
		int i = 0;
		for(IProduct product : products) {
			productDatas[i] = (ProductData) product.getTag();
			/*The way this is currently set up, there is only one productData for each product,
			 * so count can't be kept accurate. This next line adjusts the count to
			 * whatever is appropriate for that product group.*/
			productDatas[i].setCount(Integer.toString(productContainer.getItems(product).size()));
			++i;
		}
		getView().setProducts(productDatas);
	}
	
	private void refreshItems() {
		IProductContainer productContainer = (IProductContainer)
				getView().getSelectedProductContainer().getTag();
		if(productContainer != null && getView().getSelectedProduct() != null) {
			Collection<IItem> items = productContainer.getItems(
					(IProduct) getView().getSelectedProduct().getTag());
			ItemData[] itemDatas = new ItemData[items.size()];
			int i = 0;
			for(IItem item : items) {
				itemDatas[i] = (ItemData) item.getTag();
				++i;
			}
			getView().setItems(itemDatas);
		} else {
			getView().setItems(new ItemData[0]);
		}
	}
	
	private void insertItems(ITagable payload) {
		ObservableArgs<IItem> insteredItems = (ObservableArgs<IItem>) payload;
		ProductData productData = (ProductData) insteredItems.get(0).getProduct().getTag();
		if(getView().getSelectedProduct() == productData) {
			refreshItems();
		} else {
			//This might not be necessary, but you clearly already have that product
			//if it's selected.
			refreshProducts();
		}
	}

	private void removeItems(ITagable payload) {
		IItem item = (IItem) payload;
		ItemData iData = (ItemData) item.getTag();
		// remove item from storage unit and product group
		iData.setProductGroup("");
		iData.setStorageUnit("");
		refreshProducts();
		refreshItems();
	}

	private void transferItems(ITagable payload) {
		IItem item = (IItem) payload;
		ItemData iData = (ItemData) item.getTag();
		iData.setProductGroup(item.getProductGroupName());
		iData.setStorageUnit(item.getStorageUnitName());
		refreshItems();
	}


	private void editProduct(ITagable payload) {
		IProduct product = (IProduct) payload;
		ProductData pData = (ProductData) product.getTag();
		NonEmptyString neDescription = product.getDescription();
		String description = neDescription.getValue();
		pData.setDescription(description);
		Integer shelfLife = product.getShelfLife();
		pData.setShelfLife(shelfLife.toString());
		Integer nMonthSupply = product.getThreeMonthSupply();
		pData.setSupply(nMonthSupply.toString());
		Quantity itemSize = product.getItemSize();
		if (itemSize.getUnit() == Unit.COUNT) {
			pData.setCount(itemSize.getValueString());
		} else {
			pData.setCount("1");
		}
		pData.setSize(itemSize.toString());
		refreshProducts();
	}

	private void editStorageUnit(ITagable payload) {
		IProductContainer StU = (IProductContainer) payload;
		ProductContainerData pcd = (ProductContainerData)
				payload.getTag();
		renameProductContainerSorted(getRoot(), pcd, StU.getName().getValue());
	}

	private ProductContainerData getRoot() {
		return (ProductContainerData) getStorageUnitsManager().getTag();
	}
	
	private void insertProductGroup(ITagable payload) {
		ProductContainerData pcd = (ProductContainerData) payload.getTag();
		ProductContainerData parent = (ProductContainerData)
				((IProductContainer) payload).getParent().getTag();
		IInventoryView v = getView();
		insertProductContainerSorted(parent, pcd);
		v.selectProductContainer(pcd);
		productContainerSelectionChanged();
	}

	private void insertProductContainerSorted(
			ProductContainerData parent, ProductContainerData pcd) {
		IInventoryView v = getView();
		// insert product container in sorted order
		int next = getNewProductContainerIndex(parent,pcd);
		v.insertProductContainer(parent, pcd, next);
		// select product container
	}
	
	private int getNewProductContainerIndex(
			ProductContainerData parent,
			ProductContainerData pcd)
	{
		return getNewProductContainerIndex(
				parent, pcd, pcd.getName());
	}
	
	private int getNewProductContainerIndex(
			ProductContainerData parent,
			ProductContainerData pcd, String name)
	{
		int next;
		// populating this list is for the case where we're renaming
		List<ProductContainerData> lst = new ArrayList<ProductContainerData>();
		for (next = 0; next < parent.getChildCount(); next++) {
			// verified: This if statement filters correctly
			if (!(parent.getChild(next) == pcd))
			{
				lst.add(parent.getChild(next));
			}
		}
		for (next = 0; next < lst.size(); next++) {
			ProductContainerData existing = lst.get(next);
			String existingName = existing.getName();
			if (existingName.compareTo(name) > 0)
			{
				return next;
			}
		}
		return next;
	}
	
	private void renameProductContainerSorted(
			ProductContainerData parent,
			ProductContainerData pcd, String newName) {
		getView().renameProductContainer(pcd, newName,
				getNewProductContainerIndex(parent, pcd, newName));
		// the following line is a hack because renameProductContainer doesn't 
		// work properly
		getView().setProductContainers(getRoot());
		getView().selectProductContainer(pcd);
		productContainerSelectionChanged();
	}

	private void editProductGroup(ITagable payload) {
		IProductContainer pg = (IProductContainer) payload;
		ProductContainerData pcd = (ProductContainerData)
				payload.getTag();
		renameProductContainerSorted(
				(ProductContainerData)pg.getParent().getTag(),
				pcd, pg.getName().getValue());
	}

	private void insertProduct(ITagable payload) {
	}

	private void editItem(ITagable payload) {
		IItem item = (IItem) payload;
		ItemData iData = (ItemData) item.getTag();
		model.ValidDate vDate = item.getEntryDate();
		Date date = vDate.toJavaUtilDate();
		iData.setEntryDate(date);
		refreshItems();
	}
	
	private void moveItem(ITagable payload) {
		IItem item = (IItem) payload;
		ItemData iData = (ItemData) item.getTag();
		iData.setProductGroup(item.getProductGroupName());
		iData.setStorageUnit(item.getStorageUnitName());
		refreshItems();//TODO: Is this necessary?
	}
	
	private void transferProduct(ITagable payload) {
		ObservableArgs<ITagable> args = (ObservableArgs<ITagable>) payload;
		IProduct product = (IProduct) args.get(0);
		IProductContainer productContainer = (IProductContainer) args.get(1);
		for(IItem item : productContainer.getItems(product)) {
			ItemData itemData = (ItemData) item.getTag();
			System.out.println("Product group name:\t" + item.getProductGroupName());
			itemData.setProductGroup(item.getProductGroupName());
		}
	}
	
	private void insertStorageUnit(ITagable payload) {
		ProductContainerData pcd = (ProductContainerData) payload.getTag();
		StorageUnits su = getStorageUnitsManager();
		// get the root ProductContainerData object
		ProductContainerData root = (ProductContainerData) su.getTag();
		IInventoryView v = getView();
		insertProductContainerSorted(root, pcd);
		v.selectProductContainer(pcd);
		productContainerSelectionChanged();
	}

	private StorageUnits getStorageUnitsManager() {
		Model m = Model.getInstance();
		StorageUnits su = m.getStorageUnits();
		return su;
	}
}

