package gui.reports.productstats;

import model.Format;
import model.reports.*;

import gui.common.*;

/**
 * Controller class for the product statistics report view.
 */
public class ProductStatsReportController extends Controller implements
		IProductStatsReportController {
	FileFormat f;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the item statistics report view
	 */
	public ProductStatsReportController(IView view) {
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
	protected IProductStatsReportView getView() {
		return (IProductStatsReportView)super.getView();
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
		boolean enableOk = getModel().canGetProductStatisticsReport(getView().getMonths());
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
	// IProductStatsReportController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * product statistics report view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		f = getView().getFormat();
		enableComponents();
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the product statistics report view.
	 */
	@Override
	public void display() {
		String monthsStr = getView().getMonths();
		int months = Integer.parseInt(monthsStr);
		ReportVisitor rv = ReportsManager.getInstance().createProductStatisticsReport(
				(f.equals(FileFormat.HTML) ? Format.HTML : Format.PDF),
				months);
		rv.display();
	}

}

