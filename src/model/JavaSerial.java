package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JavaSerial implements IPersistance{

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

	@Override
	public void update() {
		
	}

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
