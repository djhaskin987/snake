package model.reports;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	Map<Product, List<Item>> productItems;
	public ProductStatisticsReportVisitor(ReportBuilder rb, int months) {
		builder = rb;
		this.months = months;
		productItems = new HashMap<Product, List<Item>>();
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

	@Override
	public void visit(Product product) {
		if (!productItems.containsKey(product))
			productItems.put(product, new ArrayList<Item>());
	}

	@Override
	public void display() {
		builder.buildHeading("Product Report (" + months + " months)");
		String[][] table = compileTable();
		builder.buildTable(table);
		builder.display();
	}
	
	private String[][] compileTable() {
		int size = productItems.size() + 1;
		String[][] table = new String[size][];
		table[0] = ProductStatisticsUtils.getRowHeader();
		Collection<Product> products = productItems.keySet();
		Iterator<Product> itr = products.iterator();
		int i = 1;
		while (itr.hasNext()){
			Product p = itr.next();
			List<Item> items = productItems.get(p);
			table[i] = ProductStatisticsUtils.getRow(p, items, months);
			++i;
		}
		return table;
	}
}
