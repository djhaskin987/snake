package model.productidentifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProductIdentifierFactory {

	/**
	 * Creates a chain of responsibility for the product identifier and returns the
	 * first link.
	 * 
	 * @return	The first link in the chain of responsibility of IProductIdentifiers
	 */
	IProductIdentifier createProductIdentifier() {
		File directory = new File("plugins");
		File confugrationFile = new File(directory, "plugins.txt");
		Scanner scanner;
		IProductIdentifier first = null;
		IProductIdentifier last = null;
		try {
			scanner = new Scanner(confugrationFile);
			while(scanner.hasNext()) {
				String className = scanner.next();
				try {
					IProductIdentifier current = (IProductIdentifier) Class.forName(className).newInstance();
					if(last != null) {
						last.setNext(current);
					} else {
						first = current;
					}
					last = current;
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e) {
					e.printStackTrace();
					//It might be better to just let it fail gracefully.
					continue;
				}
			}
			scanner.close();
			return first;
		} catch (FileNotFoundException e) {
			System.out.println("Error: File \"" + confugrationFile.getAbsolutePath() + "\" not found.");
		}
		return null;
	}
	
	public static void main(String [ ] args) {
		new ProductIdentifierFactory().createProductIdentifier();
	}
}
