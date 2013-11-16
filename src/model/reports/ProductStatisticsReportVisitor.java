package model.reports;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.DateTime;
import model.Item;
import model.Product;
import model.ProductGroup;
import model.StorageUnit;
import model.StorageUnits;
import model.reports.ReportBuilder;
import model.reports.ReportVisitor;



public class ProductStatisticsReportVisitor implements ReportVisitor {
	private ReportBuilder builder;
	private int months;
	Map<Product, List<Item>> allProductItems;
	Map<Product, List<Item>> currentProductItems;
	Map<Product, List<Item>> exitProductItems;
	
	public ProductStatisticsReportVisitor(ReportBuilder rb, int months) {
		builder = rb;
		this.months = months;
		allProductItems = new HashMap<Product, List<Item>>();
		currentProductItems = new HashMap<Product, List<Item>>();
		exitProductItems = new HashMap<Product, List<Item>>();
	}

	@Override
	public void visit(StorageUnits storageUnits) {
		// do nothing
	}

	@Override
	public void visit(StorageUnit storageUnit) {
		// do nothing
	}

	@Override
	public void visit(ProductGroup productGroup) {
		// do nothing
	}

	@Override
	public void visit(Item item) {
		
		if (isValid(item)) {
			putItem(item, allProductItems);
			if (item.getExitTime() != null) {
				putItem(item, exitProductItems);
			} else {
				putItem(item, currentProductItems);
			}
		}
	}
	
	public void putItem(Item item, Map<Product, List<Item>> productItems) {
		Product p = (Product) item.getProduct();
		List<Item> items = null;
		if (productItems.containsKey(p)) {
			items = productItems.get(p);
		} else {
			items = new ArrayList<Item>();
			productItems.put(p, items);
		}
		items.add(item);
	}

	private boolean isValid(Item item) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1 * months);
		DateTime mExitTime = item.getExitTime();
		if (mExitTime != null) {
			Calendar exitTime = mExitTime.getCalendar();
			return cal.compareTo(exitTime) > 0;
		}
		else return true;
	}
	
	@Override
	public void visit(Product product) {
		if (!allProductItems.containsKey(product))
			allProductItems.put(product, new ArrayList<Item>());
	}

	@Override
	public void display() {
		builder.buildHeading("Product Report (" + months + " months)");
		String[][] table = compileTable();
		builder.buildTable(table);
		builder.display();
	}
	
	@Override
	public String[][] compileTable() {
		int size = allProductItems.size() + 1;
		String[][] table = new String[size][];
		table[0] = ProductStatisticsUtils.getRowHeader();
		Collection<Product> products = allProductItems.keySet();
		Iterator<Product> itr = products.iterator();
		int i = 1;
		while (itr.hasNext()) {
			Product p = itr.next();
			List<Item> allItems = allProductItems.get(p);
			List<Item> currentItems = currentProductItems.get(p);
			List<Item> exitItems = exitProductItems.get(p);
			table[i] = ProductStatisticsUtils.getRow(p, allItems, currentItems, exitItems, months);
			++i;
		}
		return table;
	}
}
