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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InvalidInputs {


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// build data
		IProduct product;
		StorageUnit storageUnit;
		IItem item;	
		Model m = Model.getInstance();
		StorageUnit stU = (StorageUnit) m.createStorageUnit("test");
		m.addStorageUnit(stU);
		StorageUnits storageUnits = m.getStorageUnits();
		storageUnit = (StorageUnit) storageUnits.getStorageUnit("test");
		product = ProductFactory.getInstance().createInstance("1", "1", new Quantity(1.0, Unit.COUNT), 1, 1);
        m.addProduct(product);
		item = ItemFactory.getInstance().createInstance(product, new Barcode("555555555555"), storageUnit);
		m.addItem(item, storageUnit);
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
	/* Also: 
	 * Artificially inject a report asking for a time span ending on the 31st of all months of the year
Artificially inject data indicating a report should be made for a report starting the 29th of February, for the years 2013, 2012, 2100, and 2400 (not-a-leap-year, leap-year, special-leap-year, and extra-special-leap-year -- checking gregorian adding of the 29th).
Ask for a three-month report when all the products are in different storage units to ensure correct behavior
Ask for a three-month report when all products are in the same storage units to ensure correct behavior
Ask for a three-month report when some products are in the same and some are in different storage units to ensure correct behavior
	 */

}
