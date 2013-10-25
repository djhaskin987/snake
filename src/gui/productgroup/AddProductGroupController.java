package gui.productgroup;

import org.apache.commons.lang3.math.NumberUtils;

import model.IProductContainer;
import model.Quantity;
import model.Unit;
import gui.common.*;
import gui.inventory.*;

/**
 * Controller class for the add product group view.
 */
public class AddProductGroupController extends Controller implements
		IAddProductGroupController {

	private ProductContainerData parent;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to add product group view
	 * @param container Product container to which the new product group is being added
	 */
	public AddProductGroupController(IView view, ProductContainerData container) {
		super(view);
		parent = container;
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

	private IProductContainer getParentProductContainer()
	{
		return ((IProductContainer)parent.getTag());
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
		if(getModel().canAddProductGroup(getParentProductContainer(),
				getView().getProductGroupName(), getView().getSupplyValue(),
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
		getView().setSupplyValue("0");
		getView().setSupplyUnit(SizeUnits.Count);
		enableComponents();
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
		enableComponents();
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
		String supplyUnit = getView().getSupplyUnit().toString();
		IProductContainer parentPC =
				(IProductContainer) parent.getTag();
		
		IProductContainer newPC = getModel().createProductGroup(name, supplyValue, supplyUnit, parentPC);
		ProductContainerData child = new ProductContainerData(name);
		child.setTag(newPC);
		newPC.setTag(child);
		getModel().addProductGroup(newPC);
	}

}

