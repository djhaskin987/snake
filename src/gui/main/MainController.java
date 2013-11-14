package gui.main;

import java.util.Collection;

import model.IItem;
import model.IProduct;
import model.IProductContainer;
import model.Model;
import model.StorageUnits;
import model.Unit;
import gui.common.*;
import gui.inventory.ProductContainerData;
import gui.item.ItemData;
import gui.product.ProductData;

/**
 * Controller class for the main view.  The main view allows the user
 * to print reports and exit the application.
 */
public class MainController extends Controller implements IMainController {

	/**
	 * Constructor.
	 *  
	 * @param view Reference to the main view
	 */
	public MainController(IMainView view) {
		super(view);
		loadValues();
		construct();
	}
	
	@Override
	public void loadValues() {
		super.loadValues();
		Model m = Model.getInstance();
		m.load();
		StorageUnits s = m.getStorageUnits();
		loadValues(s);
	}
	
	private ProductContainerData loadValues(IProductContainer pc) {
		if (pc == null)
			return null;
		ProductContainerData pcd = new ProductContainerData();
		pcd.setName(pc.getName().getValue());
		pcd.setTag(pc);
		pc.setTag(pcd);
		for (IProduct p : pc.getProducts()) {
			ProductData pData = new ProductData();
			pData.setBarcode(p.getBarcode().toString());
			Collection<IItem> items = p.getItems(pc);
			if (items != null) {
				Integer size = items.size();
				pData.setCount(size.toString());
			}
			pData.setDescription(p.getDescription().getValue());
			pData.setShelfLife(p.getShelfLife().toString());
			pData.setSize(p.getItemSize().toString());
			if (p.getItemSize().getUnit() == Unit.COUNT)
				pData.setCount(p.getItemSize().getValueString());
			else 
				pData.setCount("1");
			p.setTag(pData);
			pData.setTag(p);
			for (IItem i : p.getItems(pc)) {
				ItemData iData = new ItemData();
				iData.setBarcode(i.getBarcode().getBarcode());
				iData.setEntryDate(i.getEntryDate().toJavaUtilDate());
				iData.setExpirationDate(i.getExpireDate().toJavaUtilDate());
				iData.setProductGroup(i.getProductGroupName());
				iData.setStorageUnit(i.getStorageUnitName());
				i.setTag(iData);
				iData.setTag(i);
			}
		}
		for (IProductContainer p : pc.getChildren()) {
			ProductContainerData child = loadValues(p);
			if (child != null)
				pcd.addChild(child);
		}
		return pcd;
	}
	
	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IMainView getView() {
		return (IMainView)super.getView();
	}

	//
	// IMainController overrides
	//

	/**
	 * Returns true if and only if the "Exit" menu item should be enabled.
	 */
	@Override
	public boolean canExit() {
		return true;
	}

	/**
	 * This method is called when the user exits the application.
	 */
	@Override
	public void exit() {
		Model m = Model.getInstance();
		m.store();
	}

	/**
	 * Returns true if and only if the "Expired Items" menu item should be enabled.
	 */
	@Override
	public boolean canPrintExpiredReport() {
		return true;
	}

	/**
	 * This method is called when the user selects the "Expired Items" 
	 * menu item.
	 */
	@Override
	public void printExpiredReport() {
		getView().displayExpiredReportView();
	}

	/**
	 * Returns true if and only if the "N-Month Supply" menu item should be enabled.
	 */
	@Override
	public boolean canPrintSupplyReport() {
		return true;
	}

	/**
	 * This method is called when the user selects the "N-Month Supply" menu 
	 * item.
	 */
	@Override
	public void printSupplyReport() {
		getView().displaySupplyReportView();
	}

	/**
	 * Returns true if and only if the "Product Statistics" menu item should be enabled.
	 */
	@Override
	public boolean canPrintProductReport() {
		return true;
	}

	/**
	 * This method is called when the user selects the "Product Statistics" menu 
	 * item.
	 */
	@Override
	public void printProductReport() {
		getView().displayProductReportView();
	}

	/**
	 * Returns true if and only if the "Notices" menu item should be enabled.
	 */
	@Override
	public boolean canPrintNoticesReport() {
		return true;
	}

	/**
	 * This method is called when the user selects the "Notices" menu 
	 * item.
	 */
	@Override
	public void printNoticesReport() {
		getView().displayNoticesReportView();
	}

	/**
	 * Returns true if and only if the "Removed Items" menu item should be enabled.
	 */
	@Override
	public boolean canPrintRemovedReport() {
		return true;
	}

	/**
	 * This method is called when the user selects the "Removed Items" menu 
	 * item.
	 */
	@Override
	public void printRemovedReport() {
		getView().displayRemovedReportView();
	}

}

