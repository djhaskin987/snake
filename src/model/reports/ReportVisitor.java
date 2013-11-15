package model.reports;

import model.Item;
import model.Product;
import model.ProductGroup;
import model.StorageUnit;
import model.StorageUnits;

public interface ReportVisitor {	
	public void visit(model.StorageUnits storageUnits);
	public void visit(model.StorageUnit storageUnit);
	public void visit(model.ProductGroup productGroup);
	public void visit(model.Item item);
	public void visit(model.Product product);
	public void display();
}
