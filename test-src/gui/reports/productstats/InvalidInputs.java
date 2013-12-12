package gui.reports.productstats;

import static org.junit.Assert.*;

import java.io.File;

import model.Barcode;
import model.IItem;
import model.IProduct;
import model.ItemFactory;
import model.Model;
import model.NonEmptyString;
import model.ProductFactory;
import model.Quantity;
import model.StorageUnit;
import model.StorageUnits;
import model.Unit;
import model.reports.ProductStatisticsReportVisitor;
import model.reports.ReportsManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InvalidInputs {

	private Model model;
	private StorageUnits units;
	private IProduct product;
	private StorageUnit storageUnit;
	private IItem item;	
	private ReportsManager rm;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		StorageUnits storageUnits = model.getStorageUnits();
		storageUnit = (StorageUnit) storageUnits.getStorageUnit("test");
		product = ProductFactory.getInstance().createInstance("1", "1", new Quantity(1.0, Unit.COUNT), 1, 1);
        if (model.getProduct(product.getBarcode().toString()) == null)
        {
        	model.addProduct(product);
        }
		item = ItemFactory.getInstance().createInstance(product, new Barcode("555555555555"), storageUnit);
		model.addItem(item, storageUnit);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void invalidMonths()
	{
		// test some invalid inputs
		assertFalse(rm.canGetProductStatisticsReport("-100000000000000000000000000000"));
		assertFalse(rm.canGetProductStatisticsReport("100000000000000000000000000000"));
		assertFalse(rm.canGetProductStatisticsReport("0"));
		assertTrue(rm.canGetProductStatisticsReport("0000000000000000000000000000001"));
		assertFalse(rm.canGetProductStatisticsReport("10.6"));
		assertTrue(rm.canGetProductStatisticsReport("98"));
		// no punctuation allowed
		assertFalse(rm.canGetProductStatisticsReport("+1"));
		assertFalse(rm.canGetProductStatisticsReport("This is a non-numeric string."));
		assertFalse(rm.canGetProductStatisticsReport("10a"));
		MockReportBuilder builder = new MockReportBuilder();
		try {

			ProductStatisticsReportVisitor report = new ProductStatisticsReportVisitor(builder, -3);
			fail("Just tried to make a report visitor with negative months. That shouldn't have worked.");
		}
		catch (Exception e)
		{
		}
		try {
			ProductStatisticsReportVisitor report = new ProductStatisticsReportVisitor(builder, Integer.MIN_VALUE);
			fail("Just tried to make a report visitor with enormous months. That shouldn't have worked.");
		}
		catch (Exception e)
		{
		}
		try {
			ProductStatisticsReportVisitor report = new ProductStatisticsReportVisitor(builder, Integer.MAX_VALUE);
			fail("Just tried to make a report visitor with enormous months. That shouldn't have worked.");
		}
		catch (Exception e)
		{
		}
		try {
			ProductStatisticsReportVisitor report = new ProductStatisticsReportVisitor(builder, 0);
			fail("Just tried to make a report visitor with 0 months. That shouldn't have worked.");
		}
		catch (Exception e)
		{
		}
	}
	@Test
	public void faultyProducts()
	{
		IProduct p = Model.getInstance().getProductFactory().createInstance(new NonEmptyString ("asdf"), new NonEmptyString("----"), new Quantity(1.0, Unit.COUNT), 1, 1);
		IProduct q = Model.getInstance().getProductFactory().createInstance(new NonEmptyString ("asdf"), new NonEmptyString("----"), new Quantity(1.0, Unit.COUNT), 1, 1);
		StorageUnit storageUnit = Model.getInstance().getStorageUnits().getStorageUnit("test");
		try {
			p.addProductContainer(storageUnit);
			q.addProductContainer(storageUnit);
			Model.getInstance().addProduct(p);
			Model.getInstance().addProduct(q);
			fail("Two products have identical barcodes in storage unit. FAIL.");
		}
		catch (Exception e)
		{
		}
	}
}
