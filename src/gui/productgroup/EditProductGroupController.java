package gui.productgroup;

import model.IProductContainer;
import gui.common.*;
import gui.inventory.*;

/**
 * Controller class for the edit product group view.
 */
public class EditProductGroupController extends Controller 
										implements IEditProductGroupController {

	private ProductContainerData productContainerData;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to edit product group view
	 * @param target Product group being edited
	 */
	public EditProductGroupController(IView view, ProductContainerData target) {
		super(view);
		productContainerData = target;
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
	protected IEditProductGroupView getView() {
		return (IEditProductGroupView)super.getView();
	}

	private IProductContainer getProductContainer()
	{
		return ((IProductContainer)productContainerData.getTag());
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
		if (getModel().canEditProductGroup(
				getProductContainer(),
				getView().getProductGroupName(),
				getView().getSupplyValue(),
				getView().getSupplyUnit().toString()))
		{
			getView().enableOK(true);
		}
		else
		{
			getView().enableOK(false);
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
		getView().setProductGroupName(getProductContainer().getName().toString());
		getView().setSupplyUnit(SizeUnitsUnitConversion.unitToSizeUnits(
				getProductContainer().getThreeMonthSupplyUnit()));
		getView().setSupplyValue(
				getProductContainer().getThreeMonthSupplyValueString());
		valuesChanged();
	}

	//
	// IEditProductGroupController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * edit product group view is changed by the user.
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
		enableComponents();
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the edit product group view.
	 * 
	 * Alters the desired product group and refreshes view.
	 * 
	 * {@pre None}
	 * 
	 * {@post product group is altered and view is refreshed}
	 */
	@Override
	public void editProductGroup() {
		getModel().changeProductGroup(getProductContainer(),
				getView().getProductGroupName(),
				getView().getSupplyValue(),
				getView().getSupplyUnit().toString());
	}

}

