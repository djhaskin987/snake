package model;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * 
 * @author djhaskin814
 *
 */
public class StorageUnitsTest {

	private StorageUnit StU_alpha;
	private StorageUnit StU_beta;
	private Model model;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		model = Model.getInstance();
		StU_alpha =
				(StorageUnit) model.getProductContainerFactory()
					.createStorageUnit("Alpha");
		StU_beta =
				(StorageUnit) model.getProductContainerFactory()
					.createStorageUnit("Beta");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetStorageUnit() {
		StorageUnits StUs = new StorageUnits();

		StUs.addStorageUnit(StU_alpha);
		assertTrue(StU_alpha == StUs.getStorageUnit("Alpha"));
		StUs.setStorageUnit("Alpha", StU_beta);
		assertTrue(StU_beta == StUs.getStorageUnit("Alpha"));
	}

	@Test
	public void testGetStorageUnitNames() {
		StorageUnits StUs = new StorageUnits();

		StUs.addStorageUnit(StU_alpha);
		StUs.addStorageUnit(StU_beta);
		List<String> names = StUs.getStorageUnitNames();
		List<String> actualNames = new LinkedList<String>();
		actualNames.add(StU_alpha.getName().toString());
		actualNames.add(StU_beta.getName().toString());
		Iterator<String> currentTestedName = names.iterator();
		Iterator<String> currentActualName = actualNames.iterator();
		while (currentTestedName.hasNext() &&
				currentActualName.hasNext())
		{
			if (!currentTestedName.next()
					.equals(currentActualName.next()))
			{
				fail("List of names are not the same!");
			}
		}
		if (currentTestedName.hasNext() ||
				currentActualName.hasNext())
		{
			fail("Name lists are not of the same length.");
		}
	}

	@Test
	public void testDeleteStorageUnit() {
		StorageUnits StUs = new StorageUnits();

		StUs.addStorageUnit(StU_alpha);
		StUs.addStorageUnit(StU_beta);
		assertTrue(StUs.canDelete(StU_alpha.getName().toString()));
		StUs.deleteStorageUnit(StU_alpha.getName().toString());
		assertTrue(StUs.getStorageUnit(StU_alpha.getName().toString()) == null);
		assertTrue(StUs.canDelete("Hodge Podge! Whick Whackery!") == false);
	}

	@Test
	public void testGetUnit() {
		StorageUnits StUs = new StorageUnits();
		assertEquals(StUs.getUnit(),"All");
		StUs.addStorageUnit(StU_alpha);
		assertEquals(StUs.getUnit(),"All");
		StUs.addStorageUnit(StU_beta);
		assertEquals(StUs.getUnit(),"All");
	}

	@Test
	public void testGetThreeMonthSupply() {
		StorageUnits StUs = new StorageUnits();
		assertEquals(StUs.getThreeMonthSupply(),"");
		StUs.addStorageUnit(StU_alpha);
		assertEquals(StUs.getThreeMonthSupply(),"");
		StUs.addStorageUnit(StU_beta);
		assertEquals(StUs.getThreeMonthSupply(),"");
	}

	@Test
	public void testGetProducts() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetProductGroupName() {
		StorageUnits StUs = new StorageUnits();
		assertEquals(StUs.getProductGroupName(),"");
		StUs.addStorageUnit(StU_alpha);
		assertEquals(StUs.getProductGroupName(),"");
		StUs.addStorageUnit(StU_beta);
		assertEquals(StUs.getProductGroupName(),"");
	}

	@Test
	public void testGetItems() {
		//fail("Not yet implemented");
	}

	@Test
	public void testRemoveItem() {
		//fail("Not yet implemented");
	}

}
