package gui.reports.productstats;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import model.Barcode;
import model.DateDoesNotExistException;
import model.IItem;
import model.IProduct;
import model.InvalidHITDateException;
import model.ItemFactory;
import model.Model;
import model.NonEmptyString;
import model.ProductFactory;
import model.Quantity;
import model.StorageUnit;
import model.StorageUnits;
import model.Unit;
import model.ValidDate;
import model.reports.ProductStatisticsReportVisitor;
import model.reports.ReportVisitor;
import model.reports.ReportsManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.itextpdf.text.pdf.AcroFields.Item;

public class EquivClasses1To5 {
	private Model model;
	private StorageUnits units;
	private IProduct product;
	private StorageUnit storageUnit;
	private IItem item;	
	private ReportsManager rm;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp()
	{
		// build data
		model = Model.getInstance();
		units = model.getStorageUnits();
		storageUnit = (StorageUnit) model.createStorageUnit("test");
		rm = model.getReportsManager();
		model.addStorageUnit(storageUnit);
		units = model.getStorageUnits();
		storageUnit = (StorageUnit) units.getStorageUnit("test");
		product = ProductFactory.getInstance().createInstance("1", "1", new Quantity(1.0, Unit.COUNT), 1, 1);
        model.addProduct(product);
		item = ItemFactory.getInstance().createInstance(product, new Barcode("555555555555"), storageUnit);
		model.addItem(item, storageUnit);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/* Also: 
	 * Artificially inject a report asking for a time span ending on the 31st of all months of the year
Artificially inject data indicating a report should be made for a report starting the 29th of February, for the years 2013, 2012, 2100, and 2400 (not-a-leap-year, leap-year, special-leap-year, and extra-special-leap-year -- checking gregorian adding of the 29th).
Ask for a three-month report when all the products are in different storage units to ensure correct behavior
Ask for a three-month report when all products are in the same storage units to ensure correct behavior
Ask for a three-month report when some products are in the same and some are in different storage units to ensure correct behavior
	 */
	@Test
	public void test31st() throws InvalidHITDateException, DateDoesNotExistException {
		
		MockReportBuilder builder = new MockReportBuilder();
		ReportVisitor visitor = new ProductStatisticsReportVisitor(builder, 3);
		
		int year = 2012;
		int day = 15;
		
		Integer [] monthsEndingIn31Data = { 1, 3, 5, 7, 8, 10, 12 };
		
		Set<Integer> monthsEndingIn31 =
				new HashSet<Integer>();
		monthsEndingIn31.addAll(java.util.Arrays.asList(monthsEndingIn31Data));
		for (int month : monthsEndingIn31)
		{
            item.setEntryDate(new ValidDate(month,day,year));
            Calendar when = Calendar.getInstance();
            when.set(year, month,31);
            visitor = new ProductStatisticsReportVisitor(builder, 3, when);
            visitor.visit(units);
            System.out.println("For month " + month);
            builder.display();
		}
		
	}
	/*
	 * Artificially inject a report asking for a time span ending on the 31st of all months of the year
Artificially inject data indicating a report should be made for a report starting the 29th of February, for the years 2013, 2012, 2100, and 2400 (not-a-leap-year, leap-year, special-leap-year, and extra-special-leap-year -- checking gregorian adding of the 29th).
Ask for a three-month report when all the products are in different storage units to ensure correct behavior
Ask for a three-month report when all products are in the same storage units to ensure correct behavior
Ask for a three-month report when some products are in the same and some are in different storage units to ensure correct behavior
	 */

}
