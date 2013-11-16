package model.reports;

import model.Item;
import model.Product;
import model.ProductGroup;
import model.StorageUnit;
import model.StorageUnits;

public interface ReportVisitor {	
	
	public void visit(StorageUnits storageUnits);
	
	public void visit(StorageUnit storageUnit);
	
	public void visit(ProductGroup productGroup);
	
	public void visit(Item item);
	
	public void visit(Product product);
	
	public void display();

	public String[][] compileTable();
}
