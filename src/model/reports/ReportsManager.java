package model.reports;

import java.io.Serializable;
import java.util.Date;

import common.StringOps;
import model.Format;
import model.Model;
import model.StorageUnits;


/** Reports manager singleton that can be called on to generate
 * reports by the controller.
 * @author Daniel Haskin
 *
 */

public class ReportsManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 262325059963893896L;
	/**
     * Singleton creation for ReportsManager
     * @return instance of ReportsManager
     */
	public static ReportsManager getInstance()
	{
		return Model.getInstance().getReportsManager();
	}
	
	/**
	 * private constructor for ReportsManager
	 */
	public ReportsManager() {
		
	}
	
	/**
	 * Displays the notices report in specified format.
	 * 
	 * @param f the display format
	 * 
	 * {@pre f != null}
	 * 
	 * {@post the notices report}
	 */
	public void displayNoticesReport(Format f) {
		ReportBuilder rb = getReportBuilder(f);
		ReportVisitor nrv = new NoticesReportVisitor(rb);
		Model m = Model.getInstance();
		StorageUnits su = m.getStorageUnits();
		su.accept(nrv);
		nrv.display();	
	}
	
	/**
	 * Displays the Supply Report in specified format.
	 * 
	 * @param f the display format
	 * 
	 * {@pre f != null}
	 * 
	 * {@post the supply report}
	 */
	public void displaySupplyReport(Format f) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	/**
	 * Displays the Removed Items Report in specified format.
	 * 
	 * @param f the display format
	 * 
	 * @param sinceDate the date and time to show it from
	 * 
	 * {@pre f != null}
	 * 
	 * {@post the removed items report}
	 */
	public void displayRemovedItemsReport(Format f, Date sinceDate) {
		ReportBuilder rb = getReportBuilder(f);
		ReportVisitor rv = new RemovedItemsReportVisitor(rb, sinceDate);
		Model m = Model.getInstance();
		StorageUnits su = m.getStorageUnits();
		su.accept(rv);
		rv.display();
	}
	
	/**
	 * Displays the expired Items report in specified format.
	 * 
	 * @param f the display format
	 * 
	 * {@pre f != null}
	 * 
	 * {@post the expired items report}
	 */
	public void displayExpiredItemsReport(Format f) {
		ReportBuilder rb = getReportBuilder(f);
		ReportVisitor rv = new ExpiredItemsReportVisitor(rb);
		Model m = Model.getInstance();
		StorageUnits su = m.getStorageUnits();
		su.accept(rv);
		rv.display();
	}
	
	public ReportVisitor createProductStatisticsReport(Format f, int months) {
		ReportBuilder rb = getReportBuilder(f);
		ReportVisitor rv = new ProductStatisticsReportVisitor(rb, months);
		Model m = Model.getInstance();
		StorageUnits su = m.getStorageUnits();
		su.accept(rv);

		return rv;
	}
	
	private ReportBuilder getReportBuilder(Format f) {
		ReportBuilder rb = null;
		switch(f) {
		case PDF:
			rb = new PdfReportBuilder();
			break;
		case HTML:
			rb = new HtmlReportBuilder();
			break;
		}
		return rb;
	}
	
	private boolean isValidReportDuration(String months)
	{
		String monthsStr = months;
		boolean returned = !StringOps.isNullOrEmpty(monthsStr);
		returned = returned && monthsStr.matches("^0*\\d{1,3}$");
		System.out.println("Mathches? " + (returned ? "yes" : "no"));
		if (returned)
		{
			int intMonths = Integer.parseInt(monthsStr);
			// per the specs, pages 30 and 32
			returned = returned && intMonths >= 1 && intMonths <= 100;
		}
		return returned;
	}

	public boolean canGetProductStatisticsReport(String months) {
		return isValidReportDuration(months);
	}

	public boolean canGetNMonthSupplyReport(String months) {
		return isValidReportDuration(months);
	}

	public ReportVisitor createNMonthSupplyReport(Format f, int months) {
		ReportBuilder rb = getReportBuilder(f);
		ReportVisitor rv = new SupplyReportVisitor(rb, months);
		Model m = Model.getInstance();
		StorageUnits su = m.getStorageUnits();
		su.accept(rv);
		return rv;
	}
}
