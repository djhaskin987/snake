package model.reports;

import model.Item;
import model.Product;
import model.ProductGroup;
import model.StorageUnit;
import model.StorageUnits;


/**
 * used for the 3 month supply report
 * 
 * @author nstandif
 *
 */
public class SupplyReportVisitor implements ReportVisitor {
	private ReportBuilder rb;
	private int months;

	public SupplyReportVisitor() {
		// TODO Auto-generated constructor stub
	}

	public SupplyReportVisitor(ReportBuilder rb, int months) {
		this.rb = rb;
		this.months = months;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void visit(StorageUnits storageUnits) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String [][] compileTable()
	{
		return null;
	}


}
