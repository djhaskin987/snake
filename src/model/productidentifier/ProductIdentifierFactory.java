package model.productidentifier;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ProductIdentifierFactory {
	
	/**
	 * Constructs a ProductIdentifierFactory
	 */
	public ProductIdentifierFactory() {}

	/**
	 * Creates a chain of responsibility for the product identifier and returns the
	 * first link.
	 * 
	 * @return	The first link in the chain of responsibility of IProductIdentifiers
	 */
	public IProductIdentifier createProductIdentifier() {
		File directory = new File("./plugins");
		System.out.println("Looking for plugins in " + directory.getAbsolutePath());
		File[] files = directory.listFiles();
		List<URL> fileURLs = new LinkedList<URL>();
		for (int fi = 0; fi < files.length; fi++) {
			System.out.println("  File found in the plugins directory: " +
					files[fi].getAbsolutePath());
			if (!files[fi].isDirectory() && files[fi].getName().matches("[.]jar$"))
			{
				String MIMEType = "";
				try {
					MIMEType = Files.probeContentType(files[fi].toPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (MIMEType.equals("application/x-java-archive"))
				{
					System.out.println("  File " + files[fi].getName() +
							" verified as a valid JAR file.");
				}
				else if (MIMEType.equals(""))
				{
					System.err.println("  File " + files[fi].getName() +
							" NOT verified as a valid JAR file.");
					System.err.println("  Hoping it is and carrying on.");
				}
				else
				{
					System.err.println("  File " + files[fi].getName() +
							" does NOT look like a valid JAR file.");
					System.err.println("  Refusing to load it.");
					continue;
				}
				
				URL fileURL = null;
				try {
					fileURL = files[fi].toURI().toURL();
					fileURLs.add(fileURL);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		File confugrationFile = new File(directory, "plugins.txt");
		Scanner scanner;
		IProductIdentifier first = null;
		IProductIdentifier last = null;
		try {
			scanner = new Scanner(confugrationFile);
			while(scanner.hasNext()) {
				String className = scanner.next();
				try {
					URL [] URLs = new URL[0];
					URLClassLoader urlClassLoader = 
							new URLClassLoader(fileURLs.toArray(URLs));
					IProductIdentifier current = (IProductIdentifier) 
							urlClassLoader.loadClass(className).newInstance();
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
