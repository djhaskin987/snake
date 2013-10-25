package gui.item;

import java.util.Date;

import model.Barcode;
import model.IItem;
import model.Model;
import model.ValidDate;
import gui.common.*;

/**
 * Controller class for the edit item view.
 */
public class EditItemController extends Controller 
										implements IEditItemController {
		private ItemData target;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to edit item view
	 * @param target Item that is being edited
	 */
	public EditItemController(IView view, ItemData target) {
		super(view);
		this.target = target;

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
	protected IEditItemView getView() {
		return (IEditItemView)super.getView();
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
		IEditItemView eiv = getView();
		eiv.enableBarcode(false);
		eiv.enableDescription(false);
		eiv.enableEntryDate(true);
	}
	
	private IItem getModelItem()
	{
		return (IItem) target.getTag();
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
		getView().setBarcode(target.getBarcode());
		getView().setDescription(getModelItem().getProduct().getDescription().getValue());
		enableComponents();
	}

	//
	// IEditItemController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * edit item view is changed by the user.
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
	 * button in the edit item view.
	 * 
	 * edits item and refreshes view
	 * 
	 * {@pre none}
	 * 
	 * {@post item is edited and view is refreshed}
	 */
	@Override
	public void editItem() {
		IEditItemView eiv = getView();
		Date entryDate = eiv.getEntryDate();
		try {
			IItem item = getModelItem();
			ValidDate validEntryDate = new ValidDate(entryDate);
			item.setEntryDate(validEntryDate);
			Model m = Model.getInstance();
			m.editItem(item);
		} catch (Exception e) {
			eiv.displayErrorMessage(e.getMessage());
			return;
		}
	}

}

