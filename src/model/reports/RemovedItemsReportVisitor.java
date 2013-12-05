package model.reports;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.Pair;

import model.DateTime;
import model.IProduct;
import model.Item;
import model.Model;
import model.Product;
import model.ProductGroup;
import model.RemovedItems;
import model.StorageUnit;
import model.StorageUnits;


public class RemovedItemsReportVisitor implements ReportVisitor {
	
	private ReportBuilder reportBuilder;
	private Date sinceDate;
	private TreeMap<IProduct, Integer> products;

	public RemovedItemsReportVisitor(ReportBuilder reportBuilder, Date sinceDate) {
		this.reportBuilder = reportBuilder;
		this.sinceDate = sinceDate;
		products = new TreeMap<IProduct, Integer>();
	}

	@Override
	public void visit(StorageUnits storageUnits) {
		//do nothing
	}

	@Override
	public void visit(StorageUnit storageUnit) {
		//do nothing
	}

	@Override
	public void visit(RemovedItems removedItems) {
		//do nothing
	}

	@Override
	public void visit(ProductGroup productGroup) {
		//do nothing
	}

	@Override
	public void visit(Item item) {
		if(item.getExitTime() != null) {
			if(products.containsKey(item.getProduct())) {
				products.put(item.getProduct(), products.get(item.getProduct())+1);
			} else {
				products.put(item.getProduct(), 1);
			}
		}
	}

	@Override
	public void visit(Product product) {
		//do nothing
	}

	@Override
	public void display() {
		String heading;
		if(sinceDate == null) {
			heading = "Items Removed Since Ever";
		} else {
			DateFormat format = new SimpleDateFormat("dd/mm/yy hh:mm aaa");
			heading = "Items Removed Since " + format.format(sinceDate);
		}
		reportBuilder.buildHeading(heading);
		reportBuilder.buildTable(compileTable());
		reportBuilder.display();
	}

	private String[][] compileTable() {
		String[][] table = new String[products.size()+1][];
		table[0] = new String[] {"Description", "Size", "Product Barcode",
				"Removed", "Current Supply"};
		int i=1;
		for(IProduct product : products.keySet()) {
			table[i] = new String[] {
					product.getDescription().getValue(),
					product.getItemSize().toString(),
					product.getBarcode().getValue(),
					Integer.toString(products.get(product)),
					Integer.toString(Model.getInstance().getStorageUnits().getItems(product).size())
			};
			++i;
		}
		return table;
	}

}
