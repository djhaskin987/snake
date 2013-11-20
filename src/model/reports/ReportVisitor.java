package model.reports;

import model.*;

public interface ReportVisitor {

	public void visit(StorageUnits storageUnits);

	public void visit(StorageUnit storageUnit);

	public void visit(ProductGroup productGroup);

	public void visit(Item item);

	public void visit(Product product);

	public void visit(RemovedItems removedItems);

	public void display();
}
