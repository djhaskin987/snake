package model.reports;

import java.util.List;

import model.Item;
import model.Product;
import model.ProductGroup;
import model.StorageUnit;
import model.StorageUnits;

/**
 * Used for the notices report
 * 
 * @author nstandif
 *
 */
public class NoticesReportVisitor implements ReportVisitor {
	private ReportBuilder builder;
	
	public NoticesReportVisitor(ReportBuilder b) {
		builder = b;
		builder.buildHeading("Notices Report");
	}
	
	@Override
	public void visit(StorageUnits storageUnits) {
		
	}

	@Override
	public void visit(StorageUnit storageUnit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ProductGroup productGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display() {
		builder.display();
	}

	@Override
	public String[][] compileTable() {
		// TODO Auto-generated method stub
		return null;
	}

}
