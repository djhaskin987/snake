package gui.inventory;

import gui.common.*;
import gui.item.*;
import gui.product.*;

import java.util.*;

import model.IContextPanelNode;
import model.IItem;
import model.IProduct;
import model.Model;
import model.ModelActions;
import model.StorageUnits;

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
		StorageUnits storageUnitsManager = getStorageUnitsManager();
		storageUnitsManager.addObserver(this);
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
		return;
	}

	//
	// IInventoryController overrides
	//

	/**
	 * Returns true iff the "Add Storage Unit" menu item should be enabled.
	 */
	@Override
	public boolean canAddStorageUnit() {
		return true;
	}

	/**
	 * Returns true iff the "Add Items" menu item should be enabled.
	 */
	@Override
	public boolean canAddItems() {
		return true;
	}

	/**
	 * Returns true iff the "Transfer Items" menu item should be enabled.
	 */
	@Override
	public boolean canTransferItems() {
		return true;
	}

	/**
	 * Returns true iff the "Remove Items" menu item should be enabled.
	 */
	@Override
	public boolean canRemoveItems() {
		return true;
	}

	/**
	 * Returns true iff the "Delete Storage Unit" menu item should be enabled.
	 */
	@Override
	public boolean canDeleteStorageUnit() {
		return true;
	}

	/**
	 * This method is called when the user selects the "Delete Storage Unit" menu item.
	 */
	@Override
	public void deleteStorageUnit() {
	}

	/**
	 * Returns true iff the "Edit Storage Unit" menu item should be enabled.
	 */
	@Override
	public boolean canEditStorageUnit() {
		return true;
	}

	/**
	 * Returns true iff the "Add Product Group" menu item should be enabled.
	 */
	@Override
	public boolean canAddProductGroup() {
		return true;
	}

	/**
	 * Returns true iff the "Delete Product Group" menu item should be enabled.
	 */
	@Override
	public boolean canDeleteProductGroup() {
		return true;
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
	}

	/**
	 * This method is called when the selected item container changes.
	 */
	@Override
	public void productContainerSelectionChanged() {
		ProductContainerData pcd = getView().getSelectedProductContainer();
		IContextPanelNode node = (IContextPanelNode) pcd.getTag();
		getView().setContextGroup(node.getProductGroupName());
		getView().setContextUnit(node.getUnit());
		getView().setContextSupply(node.getThreeMonthSupply());

				productDataList.add(productData);
			}
		}
		getView().setProducts(productDataList.toArray(new ProductData[0]));

		getView().setItems(new ItemData[0]);
	}

	/**
	 * This method is called when the selected item changes.
	 */
	@Override
	public void productSelectionChanged() {
		List<ItemData> itemDataList = new ArrayList<ItemData>();
		ProductData selectedProduct = getView().getSelectedProduct();
		if (selectedProduct != null) {
			Date now = new Date();
			GregorianCalendar cal = new GregorianCalendar();
			int itemCount = Integer.parseInt(selectedProduct.getCount());
			for (int i = 1; i <= itemCount; ++i) {
				cal.setTime(now);
				ItemData itemData = new ItemData();
				itemData.setBarcode(getRandomBarcode());
				cal.add(Calendar.MONTH, -rand.nextInt(12));
				itemData.setEntryDate(cal.getTime());
				cal.add(Calendar.MONTH, 3);
				itemData.setExpirationDate(cal.getTime());
				itemData.setProductGroup("Some Group");
				itemData.setStorageUnit("Some Unit");

				itemDataList.add(itemData);
			}
		}
		getView().setItems(itemDataList.toArray(new ItemData[0]));
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
		return true;
	}

	/**
	 * This method is called when the user selects the "Delete Product" menu item.
	 */
	@Override
	public void deleteProduct() {
	}

	/**
	 * Returns true if and only if the "Edit Item" menu item should be enabled.
	 */
	@Override
	public boolean canEditItem() {
		return getStorageUnitsManager().canEditItem(
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
		return getStorageUnitsManager().canRemoveItem();
	}

	/**
	 * This method is called when the user selects the "Remove Item" menu item.
	 */
	@Override
	public void removeItem() {
	}

	/**
	 * Returns true if and only if the "Edit Product" menu item should be enabled.
	 */
	@Override
	public boolean canEditProduct() {
		return getStorageUnitsManager().canEditProduct(
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
	}



	@Override
	public void update(Observable arg0, Object arg1) {
		// This method assumes that the only thing that InventoryController is
        // observing is the StorageUnits instance
		Pair<ModelActions, ITagable> pair = (Pair<ModelActions, ITagable>) arg1;
		ModelActions action = pair.getLeft();
		ITagable payload = pair.getRight();
		switch(action)
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
			case RENAME_PRODUCT_GROUP:
				renameProductGroup(payload);
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
			case INSERT_PRODUCT:
				insertProduct(payload);
				break;
			case EDIT_PRODUCT:
				editProduct(payload);
				break;
			default:
				throw new IllegalStateException("Could not detect what changed");
		}
	}

	private void insertItems(ITagable payload) {
		// TODO Auto-generated method stub
		
	}



	private void removeItems(ITagable payload) {
		// TODO Auto-generated method stub
		
	}



	private void transferItems(ITagable payload) {
		// TODO Auto-generated method stub
		
	}



	private void editProduct(ITagable payload) {
		
	}



	private void editStorageUnit(ITagable payload) {
		IProductContainer StU = (IProductContainer) payload;
		ProductContainerData pcd = (ProductContainerData)
				payload.getTag();
		renameProductContainerSorted(getRoot(), pcd, StU.getName().getValue());
		// the following line is a hack because renameProductContainer doesn't 
		// work properly
		getView().setProductContainers(getRoot());
		getView().selectProductContainer(pcd);
		productContainerSelectionChanged();
	}

	private ProductContainerData getRoot()
	{
		return (ProductContainerData) getStorageUnitsManager().getTag();
	}
	
	private void insertProductGroup(ITagable payload) {
        ProductContainerData pcd = (ProductContainerData) payload.getTag();
        ProductContainerData parent = (ProductContainerData) ((model.ProductGroup)payload).getParent().getTag();
        IInventoryView v = getView();
        insertProductContainerSorted(parent,pcd);
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
		int next;
		// populating this list is for the case where we're renaming
		List<ProductContainerData> lst = new ArrayList<ProductContainerData>
			(parent.getChildCount());
		for (next = 0; next < parent.getChildCount(); next++) {
			if (!parent.getChild(next).equals(pcd))
			{
				lst.add(parent.getChild(next));
			}
		}
		for (next = 0; next < lst.size(); next++) {
            ProductContainerData existing = lst.get(next);
            String existingName = existing.getName();
            String pcdName = pcd.getName();
            if (existingName.compareTo(pcdName) > 0)
            	return next;
        }
        return next;
	}
	
	private void renameProductContainerSorted(
			ProductContainerData parent,
			ProductContainerData pcd, String newName) {
		getView().renameProductContainer(pcd, newName,
        		getNewProductContainerIndex(parent, pcd));
	}

	private void renameProductGroup(ITagable payload) {
        ProductContainerData pcd = (ProductContainerData) payload.getTag();
        ProductContainerData parent = (ProductContainerData) ((model.ProductGroup)payload).getParent().getTag();
        IInventoryView v1 = getView();
        // insert product container in sorted order
        int index = 0;
        for (int i = 0; i < parent.getChildCount(); i++) {
            ProductContainerData existing = parent.getChild(i);
            if(existing == pcd) {
            	continue;
            }
            index++;
            String existingName = existing.getName();
            String pcdName = pcd.getName();
            if (existingName.compareTo(pcdName) > 0)
                break;
        }
        v1.renameProductContainer(pcd, pcd.getName(), index);
        // select product container
        v1.selectProductContainer(pcd);

	}

	private void insertProduct(ITagable payload) {

	}

	private void insertItem(ITagable payload) {

	}

	private void editItem(ITagable payload) {

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

