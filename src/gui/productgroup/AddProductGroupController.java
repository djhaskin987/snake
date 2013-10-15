package gui.productgroup;

import model.ProductGroup;
import model.Quantity;
import model.Unit;
import gui.common.*;
import gui.inventory.*;

/**
 * Controller class for the add product group view.
 */
public class AddProductGroupController extends Controller implements
		IAddProductGroupController {
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to add product group view
	 * @param container Product container to which the new product group is being added
	 */
	public AddProductGroupController(IView view, ProductContainerData container) {
		super(view);
		
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
	protected IAddProductGroupView getView() {
		return (IAddProductGroupView)super.getView();
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
	 * Loads data into the controller's view.
	 * 
	 *  {@pre None}
	 *  
	 *  {@post The controller has loaded data into its view}
	 */
	@Override
	protected void loadValues() {
	}

	//
	// IAddProductGroupController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * add product group view is changed by the user.
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
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the add product group view.
	 * 
	 * Adds product group to existing storage unit and refreshes view
	 * 
	 * {@pre None}
	 * 
	 * {@post ProductGroup is added. view refreshed.}
	 */
	@Override
	public void addProductGroup() {
		String name = getView().getProductGroupName();
		String supplyValue = getView().getSupplyValue();
		SizeUnits supplyUnit = getView().getSupplyUnit();
		//IInventoryController inventoryController = InventoryController.getInventoryController();
		//model.ProductGroup productGroup = (ProductGroup) model.Model.getInstance().getProductContainerFactory().createProductGroup(name);
		//ProductContainerData parent = inventoryController.getSelectedProductContainer();
		model.Unit unit;
		switch(supplyUnit) {	//TODO: We need to put this somewhere else.
		case Count:
			unit = Unit.COUNT;
			break;
		case FluidOunces:
			unit = Unit.FLOZ;
			break;
		case Gallons:
			unit = Unit.GAL;
			break;
		case Grams:
			unit = Unit.G;
			break;
		case Kilograms:
			unit = Unit.KG;
			break;
		case Liters:
			unit = Unit.LITER;
			break;
		case Ounces:
			unit = Unit.OZ;
			break;
		case Pints:
			unit = Unit.PINT;
			break;
		case Pounds:
			unit = Unit.LBS;
			break;
		case Quarts:
			unit = Unit.QUART;
			break;
		default:
			unit = null;
			break;
		}
		Quantity quantity = new Quantity(Double.parseDouble(supplyValue), unit);
		//model.ProductGroup productGroup = (ProductGroup) model.Model.getInstance().createProductGroup(name, (model.ProductContainer) parent.getTag(), quantity);
		gui.inventory.ProductContainerData child = new gui.inventory.ProductContainerData(name);
		//child.setTag(productGroup);
		//parent.addChild(child);
		//inventoryController.reloadValues();
	}

}

