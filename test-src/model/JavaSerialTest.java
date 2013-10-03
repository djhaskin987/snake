package model;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;


public class JavaSerialTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {

	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File delS = new File("StorageUnits.ser");
		File delP = new File("ProductCollection.ser");
		if(delS.exists()){
			delS.delete();
		}
		if(delP.exists()){
			delP.delete();
		}
	}

	@Test
	public void testSerialize() {
		NonEmptyString stUName = new NonEmptyString("Closet");
		StorageUnit stuTest = new StorageUnit(stUName);
		NonEmptyString prdGName = new NonEmptyString("Soda");
		ProductGroup prodGTest = new ProductGroup(prdGName);
		stuTest.addProductGroup(prodGTest);
		
		List<ProductContainer> containers = new ArrayList();
		containers.add(prodGTest);
		Barcode barcode = new Barcode("123456789012");
		NonEmptyString description = new NonEmptyString("Can of Coca-Cola");
		Quantity itemSize = new Quantity(12.5, Unit.FLOZ);
		Integer shelfLife = 0;
		Integer threeMonthSupply = 0;
		
		Product product1 = new Product(barcode, description, itemSize, shelfLife, threeMonthSupply, containers);
		Model.getInstance().getProductCollection().add(product1);
		
		
		Item item1 = new Item(product1, barcode, null, prodGTest);
		
		
		Model.getInstance().getStorageUnits().addStorageUnit(stuTest);
		
		IPersistance java = new JavaSerial();
		File test1 = new File("StorageUnits.ser");
		File test2 = new File("ProductCollection.ser");
		assertFalse(test1.exists());
		assertFalse(test2.exists());
		java.store();
		assertTrue(test1.exists());
		assertTrue(test2.exists());
		System.out.println("Size: "+Model.getInstance().getProductCollection().getSize());
		assertTrue(Model.getInstance().getProductCollection().getSize() == 1);
		
	}
	
	@Test
	public void testLoad(){
		System.out.println("Size: "+Model.getInstance().getStorageUnits().getProducts().size());
		assertNull(Model.getInstance().getStorageUnits().getProducts());
		
		IPersistance java = new JavaSerial();
		java.load();
		//assertTrue(Model.getInstance().getStorageUnits().getProducts().size() == 1);
		assertTrue(Model.getInstance().getProductCollection().getSize() == 1);
		StorageUnit test = Model.getInstance().getStorageUnits().getStorageUnit("Closet");
	}

}
