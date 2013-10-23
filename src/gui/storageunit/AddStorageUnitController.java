package gui.storageunit;

import gui.common.*;
import model.*;
import gui.inventory.*;

/**
 * Controller class for the add storage unit view.
 */
public class AddStorageUnitController extends Controller implements
		IAddStorageUnitController {
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to add storage unit view
	 */
	public AddStorageUnitController(IView view) {
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
	protected IAddStorageUnitView getView() {
		return (IAddStorageUnitView)super.getView();
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
		IAddStorageUnitView v = getView();
		String name = v.getStorageUnitName();
		Model m = Model.getInstance();
		boolean isEnabled = m.canAddStorageUnit(name);
		v.enableOK(isEnabled);
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
		IAddStorageUnitView v = getView();
		v.setStorageUnitName("");
	}

	//
	// IAddStorageUnitController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * add storage unit view is changed by the user.
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
	 * button in the add storage unit view.
	 * 
	 * Storage Unit is added to the root node of the tree.
	 * View refreshes.
	 * 
	 * {@pre None}
	 * 
	 * {@post storage unit added and list refreshed}
	 */
	@Override
	public void addStorageUnit() {
		IAddStorageUnitView v = getView();
		String name = v.getStorageUnitName();
		Model m = Model.getInstance();
		IProductContainer s = m.createStorageUnit(name);
		ProductContainerData pcd = new ProductContainerData(name);
		pcd.setTag(s);
		((ITagable)s).setTag((Object)pcd);
		m.addStorageUnit(s);
	}

}

