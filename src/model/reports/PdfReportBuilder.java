package model.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfReportBuilder implements ReportBuilder {
	private Document document;
	private Font heading;
	private Font subHeading;
	private Font tableHeader;

	public PdfReportBuilder() {
		document = new Document();
		PdfWriter writer;
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream("Report.pdf"));
			document.open();
			/*try {
				cb.setFontAndSize(BaseFont.createFont(), 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		heading = new Font();
		heading.setStyle(Font.BOLD);
		heading.setSize(18);
		subHeading = new Font();
		subHeading.setSize(14);
		tableHeader = new Font();
		tableHeader.setStyle(Font.BOLD);
	}

	@Override
	public void buildTable(String[][] table) {
		PdfPTable pdfTable = new PdfPTable(table[0].length);
		boolean isHeader = true;
		for(String[] row : table) {
			for(String cell : row) {
				if(isHeader) {
					pdfTable.addCell(new Paragraph(cell, tableHeader));
				} else {
					pdfTable.addCell(cell);
				}
			}
			isHeader = false;
		}
		pdfTable.setSpacingBefore(12);
		pdfTable.setSpacingAfter(12);
		try {
			document.add(pdfTable);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void buildSubHeading(String subHeading) {
		try {
			document.add(new Paragraph(subHeading, this.subHeading));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void buildHeading(String heading) {
		Paragraph paragraph = new Paragraph(heading, this.heading);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		try {
			document.add(paragraph);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void buildParagraph(String paragraph) {
		try {
			Paragraph pdfParagraph = new Paragraph(paragraph);
			pdfParagraph.setExtraParagraphSpace(6);
			document.add(pdfParagraph);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void display() {
		document.close();
		try {
			java.awt.Desktop.getDesktop().open(new File("Report.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void buildEmptyLine() {
		try {
			document.add(Chunk.NEWLINE);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
