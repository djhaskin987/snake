package gui.reports.supply;

import model.Format;
import model.reports.ReportVisitor;
import model.reports.ReportsManager;
import gui.common.*;

/**
 * Controller class for the N-month supply report view.
 */
	public class SupplyReportController extends Controller implements
		ISupplyReportController {
/*---	STUDENT-INCLUDE-BEGIN
 */
		private FileFormat f;

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the N-month supply report view
	 */	
	public SupplyReportController(IView view) {
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
	protected ISupplyReportView getView() {
		return (ISupplyReportView)super.getView();
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
		getView().enableFormat(true);
		getView().enableMonths(true);
		boolean enableOk = getModel().canGetNMonthSupplyReport(getView().getMonths());
		getView().enableOK(enableOk);
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
		getView().setMonths("3");
		getView().setFormat(FileFormat.HTML);
		enableComponents();
	}

	//
	// IExpiredReportController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * N-month supply report view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		f = getView().getFormat();
		enableComponents();
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the N-month supply report view.
	 */
	@Override
	public void display() {
		String monthsStr = getView().getMonths();
		int months = Integer.parseInt(monthsStr);
		ReportVisitor rv = ReportsManager.getInstance().createNMonthSupplyReport(
				(f.equals(FileFormat.HTML) ? Format.HTML : Format.PDF),
				months);
		rv.display();
	}

}

