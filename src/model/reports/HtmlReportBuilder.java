package model.reports;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * creates a report in Html format.
 * @author nstandif
 *
 */
public class HtmlReportBuilder implements ReportBuilder {
	private StringBuilder htmlBuilder;
	
	public HtmlReportBuilder() {
		htmlBuilder = new StringBuilder();
		htmlBuilder.append("<html>\n<head>\n<title>&nbsp;</title>\n</head><body>");
	}

	@Override
	public void buildTable(String[][] table) {
		htmlBuilder.append("<table border=\"1\">\n");
		buildHeader(table);
		buildBody(table);
		htmlBuilder.append("</table>");
	}
	
	private void buildHeader(String[][] table) {
		String[] header = table[0];
		htmlBuilder.append("<thead>\n");
		htmlBuilder.append("<tr>\n");
		for (String heading : header) {
			htmlBuilder.append("<th>");
			String escapedHeading = StringEscapeUtils.escapeHtml4(heading);
			htmlBuilder.append(escapedHeading);
			htmlBuilder.append("</th>\n");
		}
		htmlBuilder.append("</tr>\n");
		htmlBuilder.append("</thead>\n");
	}
	
	private void buildBody(String[][] table) {
		if (table.length > 1) {
			htmlBuilder.append("<tbody>\n");
			for (int i = 1; i < table.length; i++) {
				String[] row = table[i];
				buildTableRow(row);
			}
			htmlBuilder.append("</tbody>\n");
		}
	}
	
	private void buildTableRow(String[] row) {
		htmlBuilder.append("<tr>\n");
		for (String element : row) {
			htmlBuilder.append("<td>");
			String escapedEle = StringEscapeUtils.escapeHtml4(element);
			htmlBuilder.append(escapedEle);
			htmlBuilder.append("</td>\n");
		}
		htmlBuilder.append("</tr>\n");
	}

	@Override
	public void buildSubHeading(String subHeading) {
		htmlBuilder.append("<h2>");
		String escapedHeading = StringEscapeUtils.escapeHtml4(subHeading);
		htmlBuilder.append(escapedHeading);
		htmlBuilder.append("</h2>\n");
	}

	@Override
	public void buildHeading(String heading) {
		htmlBuilder.append("<h1>");
		String escapedHeading = StringEscapeUtils.escapeHtml4(heading);
		htmlBuilder.append(escapedHeading);
		htmlBuilder.append("</h1>\n");
	}

	@Override
	public void display() {
		try {
			FileWriter fw = new FileWriter("report.html");
			fw.append(htmlBuilder.toString());
			fw.close();
			java.awt.Desktop.getDesktop().open(new File("report.html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void buildParagraph(String heading) {
		htmlBuilder.append("<p>");
		String escapedHeading = StringEscapeUtils.escapeHtml4(heading);
		htmlBuilder.append(escapedHeading);
		htmlBuilder.append("</p>\n");
	}
}
