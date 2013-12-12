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
		allOfIt.append("-\n");
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
		System.out.println("Headings: ");
		for (String heading : headings)
		{
			System.out.println("  " + heading);
		}
		System.out.println("Subheadings: ");
		for (String subHeading : subHeadings)
		{
			System.out.println("  " + subHeading);
		}
		System.out.println("Paragraphs: ");
		for (String paragraph : paragraphs)
		{
			System.out.println(paragraph);
		}
		System.out.println("Tables: ");
		for (String [][]table : tables)
		{
			for (int i = 0; i < table.length; i++)
			{
                System.out.println("-");
				System.out.print("|");
				for (int j = 0; j < table[0].length; j++)
				{
					System.out.print(table[i][j] + "|");
				}
				System.out.println();
			}
			System.out.println("-");
		}
		System.out.println("Empty lines: " + emptyLines);
		System.out.println();
		System.out.println("All of it: ");
		System.out.println(allOfIt.toString());
	}
}
