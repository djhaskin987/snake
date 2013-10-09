package gui.storageunit;

import gui.common.*;
import gui.main.GUI;


@SuppressWarnings("serial")
public class AddStorageUnitView extends StorageUnitView implements IAddStorageUnitView {

	public AddStorageUnitView(GUI parent, DialogBox dialog) {
		super(parent, dialog);
		
		construct();		

		_controller = new AddStorageUnitController(this);
	}

	/**
	 * Gets the controller
	 * 
	 * @return the controller
	 * 
	 * {@pre none}
	 * 
	 * {@post the controller object}
	 */
	@Override
	public IAddStorageUnitController getController() {
		return (IAddStorageUnitController)super.getController();
	}

	/**
	 * Updates storage unit and refreshes list.
	 * 
	 * {@pre None}
	 * 
	 * {@post storage unit modified and list refreshed}
	 */
	@Override
	protected void valuesChanged() {
		getController().valuesChanged();
	}

	/**
	 * Action is cancelled. Do nothing.
	 * 
	 * {@pre None}
	 * 
	 * {@post None}
	 */
	@Override
	protected void cancel() {
		return;
	}

	/**
	 * Adds storage unit and refreshes list.
	 * 
	 * {@pre None}
	 * 
	 * {@post storage unit modified and list refreshed}
	 */
	@Override
	protected void ok() {
		getController().addStorageUnit();
	}

}


