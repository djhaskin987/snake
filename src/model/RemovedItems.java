package model;

import model.reports.ReportVisitor;


public class RemovedItems extends StorageUnit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1965849427692615153L;

	public void deleteItemsByProduct(IProduct product) {
		productItems.removeProduct(product);
	}

	public RemovedItems()
	{
		// FIXME What are we supposed to do here?
		super();
	}
	
	@Override
	public void accept(ReportVisitor v) {
		v.visit(this);
		super.accept_traverse(v);
	}
}
