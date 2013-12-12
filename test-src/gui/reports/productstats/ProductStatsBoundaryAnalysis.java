package gui.reports.productstats;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import model.*;
import model.reports.*;

public class ProductStatsBoundaryAnalysis {
	private MockReportBuilder builder;
	private ProductStatisticsReportVisitor report;
	private StorageUnits storageUnits;
	private static IProduct product;
	private static StorageUnit storageUnit;
	private static IItem item;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// build data
		Model m = Model.getInstance();
		storageUnit = (StorageUnit) m.createStorageUnit("test");
		m.addStorageUnit(storageUnit);
		StorageUnits storageUnits = m.getStorageUnits();
		storageUnit = (StorageUnit) storageUnits.getStorageUnit("test");
		product = ProductFactory.getInstance().createInstance("1", "1", new Quantity(1.0, Unit.COUNT), 1, 1);
        if (m.getProduct(product.getBarcode().toString()) == null)
        {
        	m.addProduct(product);
        }
		item = ItemFactory.getInstance().createInstance(product, new Barcode("555555555555"), storageUnit);
		m.addItem(item, storageUnit);
	}

	@Before
	public void setUp() throws Exception {
		
		builder = new MockReportBuilder();
		storageUnits = Model.getInstance().getStorageUnits();
		
	}
	
	@Test
	public void testOneInput() {
		try {
			report = new ProductStatisticsReportVisitor(builder, 1);
		} catch (Exception e) {
			fail("supposed to be valid");
		}
	}
	
	@Test
	public void test100Input() {
		try {
			report = new ProductStatisticsReportVisitor(builder, 100);
		} catch (Exception e) {
			fail("supposed to be valid");
		} 
	}
	
	@Test
	public void testEarlyDates() {
		// Artificially inject data indicating a report should be given for a period of time ending Jan. 1, 2001
		Calendar cal = Calendar.getInstance();
		cal.set(2001, Calendar.JANUARY, 1);
		report = new ProductStatisticsReportVisitor(builder, 1, cal);
		report.visit(storageUnits);
		report.display();
		
		// for a one-month period ending Feb. 1, 2001;
		cal.set(2001, Calendar.FEBRUARY, 1);
		report = new ProductStatisticsReportVisitor(builder, 1, cal);
		report.visit(storageUnits);
		report.display();
		
		// for a three-month period ending Feb. 1, 2001 (where nothing goes back that far) to ensure correct behavior
		report = new ProductStatisticsReportVisitor(builder, 3, cal);
		report.visit(storageUnits);
		report.display();
		
		List<String[][]> tables = builder.getTables();
		
		assertEquals("three tables were not produced", 3, tables.size());
	}
	
	@Test 
	public void testThreeMonthEmptyReport() {
		// Ask for a three-month report when there are no products entered in the system to ensure correct behavior
		storageUnits = new StorageUnits();
		storageUnits.addStorageUnit((StorageUnit)ProductContainerFactory.getInstance().createStorageUnit("test"));
		report = new ProductStatisticsReportVisitor(builder, 3);
		report.visit(storageUnits);
		report.display();
		List<String[][]> result = builder.getTables();
		assertEquals("single table was not produced", 1, result.size());
	}
	
	@Test
	public void testAge() {
		//Artificially inject data to ensure that age is properly reported on items that were added just today
		report = new ProductStatisticsReportVisitor(builder, 3);
		report.visit(storageUnits);
		report.display();
		List<String[][]> results = builder.getTables();
		String[][] result = results.get(0);
		String data = result[0][0];
		char digit = data.charAt(0);
		assertEquals('0', digit);
	}
	
	@Test(expected=Exception.class)
	public void testZeroInput() {
		// Test for exception when the user enters 0 as input
		report = new ProductStatisticsReportVisitor(builder, 0);
		report.visit(storageUnits);
	}
	
	@Test(expected=Exception.class)
	public void test101Input() {
		// Test for exception when the user enters 101 as input
		report = new ProductStatisticsReportVisitor(builder, 101);
		report.visit(storageUnits);
	}
}
