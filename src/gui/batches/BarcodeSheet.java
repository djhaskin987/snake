package gui.batches;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import model.Date;
import model.IItem;
import model.Item;
import model.NonEmptyString;
import model.Product;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class BarcodeSheet implements Closeable {

	private Document document;
	private PdfContentByte cb;
	PdfPTable table;
	private int rowIndex = 0;

	public BarcodeSheet() {
		document = new Document();
		table = new PdfPTable(6);
		PdfWriter writer;
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream("Barcodes.pdf"));
			document.open();
			cb = writer.getDirectContent();
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
	}

	public void addBarcode(IItem item) {

		BarcodeEAN codeEAN = new BarcodeEAN();
		codeEAN.setCodeType(Barcode.UPCA);
		codeEAN.setCode(item.getBarcode().getBarcode());
		PdfPCell cell = new PdfPCell();
		Font font = null;
		try {
			font = new Font(BaseFont.createFont(), 5.5f);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//font.setSize(1.0f);
		cell.addElement(new Paragraph(item.getProduct().getDescription().getValue(), font));
		cell.addElement(new Paragraph(item.getEntryDate().toString() + " exp " + item.getExpireDate(), font));
		cell.addElement(new Paragraph(" ", font));
		cell.addElement(codeEAN.createImageWithBarcode(cb, null, null));
		cell.setBorder(0);
		table.addCell(cell);
		rowIndex = (rowIndex+1)%6;
	}

	@Override
	public void close() {
		while(rowIndex != 0) {
			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			table.addCell(cell);
			rowIndex = (rowIndex+1)%6;
		}
		try {
			document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.close();
	}

	public void print() {
		try {
			java.awt.Desktop.getDesktop().open(new File("Barcodes.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//TODO: make this a JUnit test.
/*	public static void main(String[] args) throws IOException, DocumentException {
		BarcodeSheet codes = new BarcodeSheet();
		codes.addBarcode(new Item(new Product(null, new NonEmptyString("test"), null, 1, null), new model.Barcode(), new Date(), null));
		codes.addBarcode(new Item(new Product(null, new NonEmptyString("test2"), null, 2, null), new model.Barcode(), new Date(), null));
		codes.close();
		codes.print();
	}*/
}
