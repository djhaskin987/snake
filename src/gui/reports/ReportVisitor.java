package gui.reports;

public abstract class ReportVisitor {

	public ReportVisitor() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract void visit(model.StorageUnits storageUnits);
	public abstract void visit(model.StorageUnit storageUnit);
	public abstract void visit(model.ProductGroup productGroup);
	public abstract void visit(model.Item item);
	public abstract void visit(model.Product product);
	public void display() {
		
	}
}
