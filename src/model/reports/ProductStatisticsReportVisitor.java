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
import model.NonEmptyString;
import model.Product;
import model.ProductGroup;
import model.Quantity;
import model.StorageUnit;
import model.StorageUnits;
import model.ValidDate;

import model.reports.ReportBuilder;
import model.reports.ReportVisitor;
import org.apache.commons.lang3.time.DateUtils;

class ProductStatisticsUtils {
	public static String[] getRowHeader() {
		String[] rowHeader = new String[] { "Description", "Barcode", "Size", "3-Month Supply", "Supply:\nCur/Avg" , 
				"Supply:\nMin/Max", "Supply:\nUsed/Added", "Shelf Life", "Used Age:\nMin/Max", "Cur Age:Min/Max" };
		return rowHeader;
	}
	
	public static String[] getRow(Product p, List<Item> allItems, List<Item> currentItems, List<Item> exitItems, int months) {
		String description = getDescription(p);
		String barcode = getBarcode(p);
		String size = getSize(p);
		String threeMonthSupply = getThreeMonthSupply(p);
		String supplyCurAvg = getCurAvgSupply(allItems, currentItems, months);
		String supplyMinMax = getSupplyMinMax(allItems, months);
		String supplyUsedAdded = getSupplyUsedAdded(allItems, months);
		String shelfLife = getShelfLife(p);
		String usedAgeAvgMax = getUsedAgeAvgMax(allItems, months);
		String curAgeAvgMax = getCurAgeAvgMax(allItems, months);
		String[] row = new String[] { description, barcode, size, threeMonthSupply, supplyCurAvg, supplyMinMax, supplyUsedAdded, shelfLife, usedAgeAvgMax, curAgeAvgMax };
		return row;
	}
	
	public static String getDescription(Product p) {
		NonEmptyString neDescription = p.getDescription();
		String description = neDescription.getValue();
		return description;
	}
	
	public static String getBarcode(Product p) {
		NonEmptyString neBarcode = p.getBarcode();
		return neBarcode.getValue();
	}
	
	public static String getThreeMonthSupply(Product p) {
		Integer iSupply = p.getThreeMonthSupply();
		String supply = iSupply.toString();
		return supply;
	}
	
	public static String getSize(Product p) {
		Quantity qItemSize = p.getItemSize();
		String itemSize = qItemSize.getValueString();
		return itemSize;
	}
	
	public static String getCurAvgSupply(List<Item> allItems, List<Item> currentItems, int months) {
		int cur = getCurrentSupply(currentItems);
		double avg = getAverageSupply(allItems, months);
		return String.format("%s days / %.2f days", cur, avg);
	}
	
	public static int getCurrentSupply(List<Item> currentItems) {
		return currentItems.size();
	}
	
	
	public static double getAverageSupply(List<Item> allItems, int months) {
		long lifespans = 0;
		for(Item item : allItems) {
			lifespans += getLifeSpan(item, months);
		}
		int daysFromNow = getDaysFromNow(months);
		return (double) lifespans / (double)daysFromNow;
	}
	
	private static long getLifeSpan(Item item, int months) {
		ValidDate mValidDate = item.getEntryDate();
		Date date = mValidDate.toJavaUtilDate();
		Calendar entry = Calendar.getInstance();
		entry.setTime(date);
		Calendar start = getCal(months);
		start = getMostRecentDate(start, entry);
		DateTime mExit = item.getExitTime();
		Calendar exit;
		if (mExit != null) {
			exit = mExit.getCalendar();
		} else {
			exit = Calendar.getInstance();
			exit.setTime(new Date());
		}
		long startMillis = start.getTimeInMillis();
		long exitMillis = exit.getTimeInMillis();
		int lifespan = (int) ((exitMillis - startMillis) / DateUtils.MILLIS_PER_DAY);
		return lifespan;
	}
	
	private static Calendar getMostRecentDate(Calendar cal1, Calendar cal2) {
		if (cal1.compareTo(cal2) > 0)
			return cal1;
		else
			return cal2;
	}
	
	public static Calendar getCal(int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1 * months);
		return cal;
	}
	
	public static int getDaysFromNow(int months) {
		Calendar cal = getCal(months);
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		long diff = now.getTimeInMillis() - cal.getTimeInMillis();
		int days = (int) (diff / DateUtils.MILLIS_PER_DAY);
		return days;
	}
	
	public static String getSupplyMinMax(List<Item> items, int months) {
		int min = getSupplyMin(items, months);
		int max = getSupplyMax(items, months);
		return String.format("%s / %s", min, max);
	}
	
	private static int getSupplyMin(List<Item> items, int months) {
		return -1;
	}
	
	private static int getSupplyMax(List<Item> items, int months) {
		return -1;
	}
	
	public static String getSupplyUsedAdded(List<Item> items, int months) {
		int used = getSupplyUsed(items, months);
		int added = getSupplyAdded(items, months);
		return String.format("%s / %s", used, added);
	}
	
	private static int getSupplyUsed(List<Item> items, int months) {
		return -1;
	}
	
	private static int getSupplyAdded(List<Item> items, int months) {
		return -1;
	}
	
	public static String getShelfLife(Product p) {
		Integer iShelfLife = p.getShelfLife();
		if (iShelfLife > 0) {
		String shelfLife = iShelfLife.toString() + " months";
		return shelfLife;
		} else {
			return "";
		}
	}
	
	public static String getUsedAgeAvgMax(List<Item> items, int months) {
		int avg = getUsedAgeAverage(items, months);
		int max = getUsedAgeMax(items, months);
		return String.format("%s days / %s days", avg, max);
	}
	
	private static int getUsedAgeAverage(List<Item> items, int months) {
		return -1;
	}
	
	private static int getUsedAgeMax(List<Item> items, int months) {
		return -1;
	}
	
	public static String getCurAgeAvgMax(List<Item> items, int months) {
		int avg = getCurrentAgeAverage(items, months);
		int max = getCurrentAgeMax(items, months);
		return String.format("%s days / %s days", avg, max);
	}
	
	private static int getCurrentAgeAverage(List<Item> items, int months) {
		return -1;
	}
	
	private static int getCurrentAgeMax(List<Item> items, int months) {
		return -1;
	}
}

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
	
	private String[][] compileTable() {
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
		}
		return table;
	}
}
