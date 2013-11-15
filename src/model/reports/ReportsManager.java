package model.reports;

import common.StringOps;

import model.Format;
import model.Model;
import model.StorageUnits;


/** Reports manager singleton that can be called on to generate
 * reports by the controller.
 * @author Daniel Haskin
 *
 */

public class ReportsManager {
	private static ReportsManager instance;
	/**
     * Singleton creation for ReportsManager
     * @return instance of ReportsManager
     */
	public static ReportsManager getInstance()
	{
		if (instance == null)
		{
			instance = new ReportsManager();
		}
		return instance;
	}
	
	/**
	 * private constructor for ReportsManager
	 */
	private ReportsManager() {
		
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
	private void displaySupplyReport(Format f) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	/**
	 * Displays the Removed Items Report in specified format.
	 * 
	 * @param f the display format
	 * 
	 * {@pre f != null}
	 * 
	 * {@post the removed items report}
	 */
	private void displayRemovedItemsReport(Format f) {
		throw new UnsupportedOperationException("Not supported yet.");
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
	private void displayExpiredItemsReport(Format f) {
		throw new UnsupportedOperationException("Not supported yet.");
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

	public boolean canGetProductStatisticsReport(String months) {
		String monthsStr = months;
		boolean returned = !StringOps.isNullOrEmpty(monthsStr);
		returned = returned && monthsStr.matches("^\\d{1,3}$");
		System.out.println("Mathches? " + (returned ? "yes" : "no"));
		if (returned)
		{
			int intMonths = Integer.parseInt(monthsStr);
			returned = returned && intMonths >= 0 && intMonths <= 100;
		}
		return returned;
	}
}
