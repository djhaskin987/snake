package gui.reports.productstats;
import java.util.*;
public class MockReportBuilder implements model.reports.ReportBuilder {
		List<String[][]> tables;
		List<String> headings;
		List<String> subHeadings;
		List<String> paragraphs;
		StringBuilder allOfIt;
		int emptyLines;
	public MockReportBuilder() {
		tables = new ArrayList<String[][]>();
		headings = new ArrayList<String>();
		subHeadings = new ArrayList<String>();
		paragraphs = new ArrayList<String>();
		emptyLines = 0;
		allOfIt = new StringBuilder();
	}
	
	@Override
	public void buildTable(String[][] table) {
		tables.add(table);
        for (int i = 0; i < table.length; i++)
        {
        		allOfIt.append("-\n");
                allOfIt.append("|");
                for (int j = 0; j < table[0].length; j++)
                {
                	allOfIt.append(table[i][j] + "|");
                }
                allOfIt.append("\n");
        }
		allOfIt.append("-\n");
	}

	@Override
	public void buildSubHeading(String subHeading) {
		subHeadings.add(subHeading);
		allOfIt.append("-- " + subHeading + " --\n");
	}

	@Override
	public void buildHeading(String heading) {
		headings.add(heading);
		allOfIt.append("- " + heading + " -\n");
	}

	@Override
	public void buildParagraph(String heading) {
		paragraphs.add(heading);
		allOfIt.append("\n    " + heading + "\n");
	}

	@Override
	public void buildEmptyLine() {
		emptyLines++;
		allOfIt.append("\n");
	}

	public List<String[][]> getTables() {
		return tables;
	}
	
	@Override
	public void display() {
	}
	
	public String toString()
	{
		return allOfIt.toString();
	}
}
