package gui.reports.productstats;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import model.Barcode;
import model.DateDoesNotExistException;
import model.DateTime;
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
		product = ProductFactory.getInstance().createInstance("barcode", "description", new Quantity(1.0, Unit.COUNT), 1, 1);
        model.addProduct(product);
		
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
        IItem item;	
		item = ItemFactory.getInstance().createInstance(product, new Barcode("555555555555"), storageUnit);
		model.addItem(item, storageUnit);
		// Remove an item on the 28th of february, look for it as 'used' on the report for 
		// march 31st.
		MockReportBuilder builder = new MockReportBuilder();
        Calendar till = Calendar.getInstance();
        ProductStatisticsReportVisitor visitor;
        item.setEntryDate(new ValidDate(1,2,2001));
        till.set(2011, 2, 28);
        item.setExit(new DateTime(till.getTime()));
        Calendar when = Calendar.getInstance();
        when.set(2011, 3, 31);
        visitor = new ProductStatisticsReportVisitor(builder, 1, when);
        units.accept(visitor);
        visitor.display();
        assertTrue(builder.getTables().get(0)[1][6].matches("0 */ *0"));
        // now test 31st to 31st
        builder = new MockReportBuilder();
        till = Calendar.getInstance();
        item.setEntryDate(new ValidDate(1,2,2001));
        till.set(2011, 7, 31);
        item.setExit(new DateTime(till.getTime()));
        when = Calendar.getInstance();
        when.set(2011, 8, 31);
        visitor = new ProductStatisticsReportVisitor(builder, 1, when);
        units.accept(visitor);
        visitor.display();
        System.out.println(builder.toString());
        assertTrue(builder.getTables().get(0)[1][6].matches("1 */ *0"));
        model.unaddItem(item);
	}
	/*
	 * Artificially inject data indicating a report should be made for a report starting the 29th of February, for the years 2013, 2012, 2100, and 2400 (not-a-leap-year, leap-year, special-leap-year, and extra-special-leap-year -- checking gregorian adding of the 29th).
Ask for a three-month report when all the products are in different storage units to ensure correct behavior
Ask for a three-month report when all products are in the same storage units to ensure correct behavior
Ask for a three-month report when some products are in the same and some are in different storage units to ensure correct behavior
	 */

}
