package gui.reports.productstats;

import gui.common.FileFormat;
import java.util.List;
import java.util.LinkedList;

public class MockProductStatsReportView implements IProductStatsReportView {
	private boolean okEnabled;
	private boolean formatEnabled;
	private boolean monthsEnabled;
	private String months;
	
	private List<String> errorMessages;
	private List<String> warningMessages;
	private FileFormat format;
	private List<String> informationMessages;
	
	public MockProductStatsReportView()
	{
		okEnabled = true;
		formatEnabled = true;
		monthsEnabled = true;
		months = "";
		errorMessages = new LinkedList<String>();
		warningMessages = new LinkedList<String>();
		informationMessages = new LinkedList<String>();
				
	}

	@Override
	public void displayInformationMessage(String message) {
		informationMessages.add(message);
	}
	
	public List<String> getInformationMessages()
	{
		return errorMessages;
	}
	
	@Override
	public void displayWarningMessage(String message) {
		warningMessages.add(message);

	}
	
	public List<String> getWarningMessages()
	{
		return warningMessages;
	}

	@Override
	public void displayErrorMessage(String message) {
		errorMessages.add(message);
	}

	public List<String> getErrorMessages()
	{
		return errorMessages;
	}

	@Override
	public FileFormat getFormat() {
		return format;
	}

	@Override
	public void setFormat(FileFormat value) {
		format = value;
	}
	
	@Override
	public void enableFormat(boolean value) {
		formatEnabled = true;
	}
	
	public boolean getFormatEnabled() {
		return formatEnabled;
	}

	@Override
	public String getMonths() {
		return months;
	}

	@Override
	public void setMonths(String value) {
		months = value;
	}

	@Override
	public void enableMonths(boolean value) {
		monthsEnabled = value;
	}
	
	public boolean getMonthsEnabled()
	{
		return monthsEnabled;
	}

	@Override
	public void enableOK(boolean value) {
		okEnabled = value;
	}
	
	public boolean okEnabled()
	{
		return okEnabled;
	}
}
