package gui.reports.removed;

import java.util.Date;

import model.Format;
import model.Model;
import model.reports.ReportsManager;
import gui.common.*;

/**
 * Controller class for the removed items report view.
 */
public class RemovedReportController extends Controller implements
		IRemovedReportController {

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the removed items report view
	 */
	public RemovedReportController(IView view) {
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
	protected IRemovedReportView getView() {
		return (IRemovedReportView) super.getView();
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
		getView().setSinceLast(true);
		getView().setSinceLastValue(getModel().getStorageUnits()
                .getDateSinceLastRemovedItemsReport());
	}

	//
	// IExpiredReportController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * removed items report view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
	}

	/**
	 * This method is called when the user clicks the "OK"
	 * button in the removed items report view.
	 */
	@Override
	public void display() {
		Format f;
		switch(getView().getFormat()) {
		case HTML:
			f = Format.HTML;
			break;
		case PDF:
			f = Format.PDF;
			break;
		default:
			System.err.println("Error: You missed a format in ExpiredReportController.display()");
			return;
		}
		Date sinceDate;
		if(getView().getSinceDate()) {
			sinceDate = getView().getSinceDateValue();
		} else {
			sinceDate = Model.getInstance().getStorageUnits().getDateSinceLastRemovedItemsReport();
		}
		Model.getInstance().getStorageUnits().setDateSinceLastRemovedItemsReport(new Date());
		getModel().getReportsManager().displayRemovedItemsReport(f, sinceDate);
	}

}

