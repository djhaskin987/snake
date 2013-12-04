/**
 * 
 */
package model.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.Model;

/**
 * Creates the JavaSerializer object
 */
public class JavaSerializer implements ISerializer {

	/**
	 * Creates a new JavaSerializer object
	 */
	public JavaSerializer() {
		// does nothing
	}

	/** (non-Javadoc)
	 * @see model.serialization.ISerializer#load(model.Model)
	 */
	@Override
	public void load(Model model) {
		try {
			Path p = Paths.get("inventory-tracker.ser");
			if (Files.exists(p)) {
					FileInputStream fileIn = new FileInputStream(p.toFile());
					ObjectInputStream in = new ObjectInputStream(fileIn);
					
					Model m = (Model) in.readObject();
					model.setInstance(m);
					in.close();
					fileIn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();	
		}
		System.out.println("model loaded");
	}
	
	/**
	 * @see model.serialization.ISerializer#save(model.Model)
	 */
	@Override
	public void save(Model model) {
		try {
			Path p = Paths.get("inventory-tracker.ser");
			Files.deleteIfExists(p);
			FileOutputStream fileOut = new FileOutputStream("inventory-tracker.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(model);
			out.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see model.serialization.ISerializer#update(model.Model)
	 */
	@Override
	public void update(Model model) {
		save(model);
	}

}
