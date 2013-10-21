package gui.productgroup;

import org.apache.commons.lang3.math.NumberUtils;

import model.IProductContainer;
import model.ProductGroup;
import model.Quantity;
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
		IProductContainer productContainer = ((IProductContainer) productContainerData.getTag());
		IProductContainer parent = productContainer.getParent();
		ProductContainerData parentData = (ProductContainerData) parent.getTag();
		if(
				getView().getProductGroupName().equals("")
				|| !NumberUtils.isNumber(getView().getSupplyValue())
				|| !Quantity.isValidQuantity(
						Double.parseDouble(getView().getSupplyValue()),
						SizeUnitsUnitConversion.sizeUnitsToUnit(getView().getSupplyUnit()))
				) {
			getView().enableOK(false);
			return;
		}
		if(getView().getProductGroupName().equals(productContainerData.getName())) {
			getView().enableOK(true);
			return;
		}
		for(int i=0; i<parentData.getChildCount(); ++i) {
			if(getView().getProductGroupName().equals(parentData.getChild(i).getName())) {
				getView().enableOK(false);
				return;
			}
		}
		getView().enableOK(true);
		return;
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
		ProductGroup productGroup = (ProductGroup)productContainerData.getTag();
		getView().setProductGroupName(productGroup.getName().toString());
		getView().setSupplyUnit(SizeUnitsUnitConversion.unitToSizeUnits(productGroup.getThreeMonthSupplyUnit()));
		getView().setSupplyValue(productGroup.getThreeMonthSupplyValueString());
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
		IProductContainer productGroup = (IProductContainer) productContainerData.getTag();
		if(!productContainerData.getName().equals(getView().getProductGroupName())) {
			productContainerData.setName(getView().getProductGroupName());
			getModel().changeProductGroup(productGroup,
					getView().getProductGroupName(),
					getView().getSupplyValue(),
					getView().getSupplyUnit().toString());
		}
	}

}

