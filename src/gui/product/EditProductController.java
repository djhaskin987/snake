package gui.product;

import common.StringOps;

import model.Barcode;
import model.IProduct;
import model.IProduct.InvalidIntegerException;
import model.Model;
import model.NonEmptyString;
import model.Quantity;
import model.Unit;
import gui.common.*;

/**
 * Controller class for the edit product view.
 */
public class EditProductController extends Controller 
										implements IEditProductController {
	private ProductData productData;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the edit product view
	 * @param target Product being edited
	 */
	public EditProductController(IView view, ProductData target) {
		super(view);
		this.productData = target;
		construct();
	}

	//
	// Controller overrides
	//
	
	/**
	 * Returns a reference to the view for this controller.
	 * 
	 * {@pre None}
	 * 
	 * {@post Returns a reference to the view for this controller.}
	 */
	@Override
	protected IEditProductView getView() {
		return (IEditProductView)super.getView();
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
		IEditProductView epv = getView();
		epv.enableBarcode(false);
		SizeUnits unit = epv.getSizeUnit();
		if (unit == SizeUnits.Count) {
			epv.setSizeValue("1");
			epv.enableSizeValue(false);
		} else {
			epv.enableSizeValue(true);
		}
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
		IProduct product = (IProduct) productData.getTag();
		IEditProductView epv = getView();
		Barcode barcode = product.getBarcode();
		String barcodeStr = barcode.getBarcode();
		epv.setBarcode(barcodeStr);
		NonEmptyString neDescription = product.getDescription();
		String description = neDescription.getValue();
		epv.setDescription(description);
		Quantity itemSize = product.getItemSize();
		Unit unit = itemSize.getUnit();
		SizeUnits sUnit = SizeUnitsUnitConversion.unitToSizeUnits(unit);
		epv.setSizeUnit(sUnit);
		epv.setSizeValue(itemSize.getValueString());
		Integer nMonthSupply = product.getThreeMonthSupply();
		epv.setSupply(nMonthSupply.toString());
		Integer shelfLife = product.getShelfLife();
		epv.setShelfLife(shelfLife.toString());
		enableComponents();
	}

	//
	// IEditProductController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * edit product view is changed by the user.
	 * 
	 * This will set the activity of the "OK" button, depending
	 * on whether the field is valid or not.
	 * 
	 * {@pre None}
	 * 
	 * {@post activity of "OK" button altered}
	 */
	@Override
	public void valuesChanged() {
		IEditProductView epv = getView();
		enableComponents();
		boolean enableOk = true;
		String shelfLifeStr = epv.getShelfLife();
		enableOk &= isValidInteger(shelfLifeStr);
		String threeMonthSupplyStr = epv.getSupply();
		enableOk &= isValidInteger(threeMonthSupplyStr);
		enableOk &= isValidItemSize();
		epv.enableOK(enableOk);
	}
	
	public boolean isValidItemSize() {
		IEditProductView epv = getView();
		SizeUnits sUnit = epv.getSizeUnit();
		Unit unit = SizeUnitsUnitConversion.sizeUnitsToUnit(sUnit);
		if (unit == Unit.COUNT)
			return true;
		String valueStr = epv.getSizeValue();
		try {
			Double value = Double.parseDouble(valueStr);
			return Quantity.isValidQuantity(value, unit);
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public boolean isValidInteger(String s) {
		if (!StringOps.isNullOrEmpty(s)) {
			if (StringOps.isNumeric(s)) {
				Integer value = Integer.parseInt(s);
				return (value > 0);
			}
		}
		return false;
	}
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the edit product view.
	 * 
	 * product is edited. view is updated.
	 * 
	 * {@pre none}
	 * 
	 * {@post product is altered}
	 */
	@Override
	public void editProduct() {
		IProduct product = (IProduct) productData.getTag();
		IEditProductView epv = getView();
		String description = epv.getDescription();
		NonEmptyString neDescription = new NonEmptyString(description);
		product.setDescription(neDescription);
		String valStr = epv.getSizeValue();
		double value = Double.parseDouble(valStr);
		SizeUnits sizeUnit = epv.getSizeUnit();
		Unit unit = SizeUnitsUnitConversion.sizeUnitsToUnit(sizeUnit);
		Quantity quantity = new Quantity(value, unit);
		product.setItemSize(quantity);
		String shelfLifeStr = epv.getShelfLife();
		Integer shelfLife = Integer.parseInt(shelfLifeStr);
		String nMonthSupplyStr = epv.getSupply();
		Integer nMonthSupply = Integer.parseInt(nMonthSupplyStr);
		try {
			product.setShelfLife(shelfLife);
			product.setThreeMonthSupply(nMonthSupply);
		} catch (InvalidIntegerException e) {
			epv.displayErrorMessage(e.getMessage());
			return;
		}	
		Model m = Model.getInstance();
		m.editProduct(product);
	}

}

