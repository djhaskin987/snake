package model;
import java.io.*;
import com.itextpdf.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class BarcodeLabels {

	/**
	 * Output the labels given iostream.
	 * 
	 * @param os output stream in which the pdf is written to
	 * 
	 * @param items an array of files containing barcodes
	 * 
	 * {@pre s != null && items != null}
	 * 
	 * {@post pdf is written to s}
	 * 
	 * @throws DocumentException 
	 */
	public static void createPdf(OutputStream os, IItem[] items) throws DocumentException {
		Document doc = new Document();
		PdfWriter writer = PdfWriter.getInstance(doc, os);
		doc.open();
		PdfContentByte cb = writer.getDirectContent();
		BarcodeEAN codeEAN = new BarcodeEAN();
		codeEAN.setCodeType(com.itextpdf.text.pdf.Barcode.UPCA);
		for (IItem i : items) {
			model.Barcode b = i.getBarcode();
			String bStr = b.getBarcode();
			
		}
	}
	
	/**
	 * Output the labels to given iostream.
	 * 
	 * @param os output stream in which the pef is written to\
	 * 
	 * @param products an array of products containing barcodes
	 * 
	 * {@pre s != null && products != null}
	 * 
	 * {@post pdf is written to s}
	 */
	public static void createPdf(OutputStream os, IProduct[] products) {
		
	}
}
