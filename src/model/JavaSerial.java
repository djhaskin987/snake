package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * This takes all data and serializes it into two files to be stored on the local machine
 * It also loads the .ser files and all the data back into the program.
 * This class's update function does nothing but is merely here to fulfill the interface contract
 */
public class JavaSerial implements IPersistance{
	/**
	 * {@pre There must be a StorageUnits and a ProductCollection}
	 * {@post There are two files stored on the local machine.  StorageUnits.ser
     * and ProductCollection.ser}
	 */
	@Override
	public void store() {
	     try
	      {
	         FileOutputStream fileOutStorage = new FileOutputStream("StorageUnits.ser");
	         ObjectOutputStream outStorage = new ObjectOutputStream(fileOutStorage);
	         outStorage.writeObject(Model.getInstance().getStorageUnits());
	         outStorage.close();
	         fileOutStorage.close();
	         FileOutputStream fileOutProduct = new FileOutputStream("ProductCollection.ser");
	         ObjectOutputStream outProduct = new ObjectOutputStream(fileOutProduct);
	         outProduct.writeObject(Model.getInstance().getProductCollection());
	         outProduct.close();
	         fileOutProduct.close();
	         
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
		
	}
	
	/**
	 * This function does nothing
	 */
	@Override
	public void update() {
		
	}
	/**
	 * {@pre There is no need for anything to be in the data model}
	 * {@post All data stored in the .ser files are now loaded back into the data model}
	 */
	@Override
	public void load() {
	      StorageUnits stu = null;
	      ProductCollection prdc = null;
	      try
	      {
	         FileInputStream fileInS = new FileInputStream("StorageUnits.ser");
	         ObjectInputStream inS = new ObjectInputStream(fileInS);
	         stu = (StorageUnits) inS.readObject();
	         inS.close();
	         fileInS.close();
	         
	         FileInputStream fileInP = new FileInputStream("ProductCollection.ser");
	         ObjectInputStream inP = new ObjectInputStream(fileInP);
	         prdc = (ProductCollection) inP.readObject();
	         inP.close();
	         fileInP.close();
	         
	         Model.getInstance().setStorageUnits(stu);
	         Model.getInstance().setProductCollection(prdc);
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	         return;
	      }
	}

}
