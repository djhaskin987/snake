package gui.reports.productstats;
import java.util.*;
public class MockReportBuilder implements model.reports.ReportBuilder {
		List<String[][]> tables;
		List<String> headings;
	public MockReportBuilder() {
		tables = new ArrayList<String[][]>();
		headings = new ArrayList<String>();
	}
	
	@Override
	public void buildTable(String[][] table) {
		tables.add(table);		
	}

	@Override
	public void buildSubHeading(String subHeading) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildHeading(String heading) {
		headings.add(heading);
	}

	@Override
	public void buildParagraph(String heading) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildEmptyLine() {
		// TODO Auto-generated method stub
		
	}

	public List<String[][]> getTables() {
		return tables;
	}
	
	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

}
