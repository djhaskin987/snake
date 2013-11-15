package model.reports;

class ProductStatisticsUtils {
	public static String[] getRowHeader() {
		String[] rowHeader = new String[] { "Description", "Barcode", "Size", "3-Month Supply", "Supply:\nCur/Avg" , 
				"Supply:\nMin/Max", "Supply:\nUsed/Added", "Shelf Life", "Used Age:\nMin/Max", "Cur Age:Min/Max" };
		return rowHeader;
	}
	
	public static String[] getRow(Product p, List<Item> items, int months) {
		String description = getDescription(p);
		String barcode = getBarcode(p);
		String size = getSize(p);
		String threeMonthSupply = getThreeMonthSupply(p);
		String supplyCurAvg = getCurAvgSupply(items, months);
		String supplyMinMax = getSupplyMinMax(items, months);
		String supplyUsedAdded = getSupplyUsedAdded(items, months);
		String shelfLife = getShelfLife(p);
		String usedAgeAvgMax = getUsedAgeAvgMax(items, months);
		String curAgeAvgMax = getCurAgeAvgMax(items, months);
		String[] row = new String[] { description, barcode, size, threeMonthSupply, supplyCurAvg, supplyMinMax, supplyUsedAdded, shelfLife, usedAgeAvgMax, curAgeAvgMax };
		return row;
	}
	
	public static String getDescription(Product p) {
		return "";
	}
	
	public static String getBarcode(Product p) {
		return "";
	}
	
	public static String getThreeMonthSupply(Product p) {
		return "";
	}
	
	public static String getSize(Product p) {
		return "";
	}
	
	public static String getCurAvgSupply(List<Item> items, int months) {
		int cur = getCurrentSupply(items);
		int avg = getAverageSupply(items, months);
		return String.format("%s days / %s days", cur, avg);
	}
	
	public static int getCurrentSupply(List<Item> items) {
		return -1;
	}
	
	public static int getAverageSupply(List<Item> items, int months) {
		return -1;
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
		return "";
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

