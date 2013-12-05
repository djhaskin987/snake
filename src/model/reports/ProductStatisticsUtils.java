package model.reports;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import model.DateTime;
import model.Item;
import model.NonEmptyString;
import model.Product;
import model.Quantity;
import model.ValidDate;

public class ProductStatisticsUtils {
	public static String[] getRowHeader() {
		String[] rowHeader = new String[] { "Description", "Barcode", "Size",
				"3-Month Supply", "Supply:\nCur/Avg" ,"Supply:\nMin/Max",
				"Supply:\nUsed/Added", "Shelf Life", "Used Age:\nMin/Max",
				"Cur Age:Min/Max" };
		return rowHeader;
	}
	
	public static String[] getRow(Product p, List<Item> allItems,
			List<Item> currentItems, List<Item> exitItems, int months) {
		String description = getDescription(p);
		String barcode = getBarcode(p);
		String size = getSize(p);
		String threeMonthSupply = getThreeMonthSupply(p);
		String supplyCurAvg = getCurAvgSupply(allItems, currentItems, months);
		String supplyMinMax = getSupplyMinMax(allItems, months);
		String supplyUsedAdded = getSupplyUsedAdded(allItems, exitItems, months);
		String shelfLife = getShelfLife(p);
		String usedAgeAvgMax = getUsedAgeAvgMax(exitItems, months);
		String curAgeAvgMax = getCurAgeAvgMax(currentItems, months);
		String[] row = new String[] { description, barcode, size,
				threeMonthSupply, supplyCurAvg, supplyMinMax, supplyUsedAdded,
				shelfLife, usedAgeAvgMax, curAgeAvgMax };
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
		return String.format("%s / %.2f", cur, avg);
	}
	
	public static int getCurrentSupply(List<Item> currentItems) {
		return currentItems.size();
	}
	
	
	public static double getAverageSupply(List<Item> allItems, int months) {
		long lifespans = 0;
		for(Item item : allItems) {
			lifespans += getLifeSpan(item, months, true);
		}
		int daysFromNow = getDaysFromNow(months);
		return (double) lifespans / (double)daysFromNow;
	}
	
	private static int[] getSupplyDays(List<Item> allItems, int months) {
		Calendar cal = getCal(months);
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		int days = getDaysFromNow(months);
		int[] supplyDays = new int[days];
		while (DateUtils.truncatedEquals(now, cal, Calendar.DATE) == false) {
			days--;
			supplyDays[days] = 0;
			for (Item item : allItems) {
				ValidDate mEntryDate = item.getEntryDate();
				Calendar entryDate = toCal(mEntryDate);
				Calendar exitDate = toCal(item.getExitTime());
				if (cal.compareTo(entryDate) >= 0 && (exitDate == null
						|| cal.compareTo(exitDate) <= 0)) {
					supplyDays[days] += 1;
				}
			}
			cal.add(Calendar.DATE, 1);
		}
		return supplyDays;
	}
	
	private static Calendar toCal(ValidDate mDate) {
		Date date = mDate.toJavaUtilDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	private static Calendar toCal(DateTime mDateTime) {
		if (mDateTime != null)
			return mDateTime.getCalendar();
		else
			return null;
	}
	
	private static long getLifeSpan(Item item, int months, boolean truncate) {
		ValidDate mValidDate = item.getEntryDate();
		Date date = mValidDate.toJavaUtilDate();
		Calendar entry = Calendar.getInstance();
		entry.setTime(date);
		Calendar start;
		if (truncate)
			start = getMostRecentDate(getCal(months), entry);
		else
			start = entry;
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
		int[] supplyDays = getSupplyDays(items, months);
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < supplyDays.length; i++) {
			if (min > supplyDays[i])
				min = supplyDays[i];
		}
		return min;
	}
	
	private static int getSupplyMax(List<Item> items, int months) {
		int[] supplyDays = getSupplyDays(items, months);
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < supplyDays.length; i++) {
			if (max < supplyDays[i])
				max = supplyDays[i];
		}
		return max;
	}
	
	public static String getSupplyUsedAdded(List<Item> allItems, List<Item> exitItems, int months) {
		int used = getSupplyUsed(exitItems, months);
		int added = getSupplyAdded(allItems, months);
		return String.format("%s / %s", used, added);
	}
	
	private static int getSupplyUsed(List<Item> exitItems, int months) {
		if (exitItems != null)
			return exitItems.size();
		else
			return 0;
	}
	
	private static int getSupplyAdded(List<Item> allItems, int months) {
		Calendar begin = getCal(months);
		int count = 0;
		for (Item item : allItems) {
			ValidDate mEntryDate = item.getEntryDate();
			Date entryDate = mEntryDate.toJavaUtilDate();
			Calendar iCal = Calendar.getInstance();
			iCal.setTime(entryDate);
			if (iCal.compareTo(begin) >= 0)
				count += 1;
		}
		return count;
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
		long min = getAgeMin(items);
		long max = getAgeMax(items);
		return String.format("%s days / %s days", min, max);
	}
	
	private static long[] getLifeSpans(List<Item> items) {
		if (items == null)
			return new long[] { 0 };
		long[] lifeSpans = new long[items.size()];
		for(int i = 0; i < items.size(); i++) {
			lifeSpans[i] = getLifeSpan(items.get(i), -1, false);
		}
		return lifeSpans;
	}
	
	private static long getAgeMin(List<Item> items) {
		long[] lifeSpans = getLifeSpans(items);
		long min = Long.MAX_VALUE;
		for (long lifeSpan : lifeSpans) {
			if (min > lifeSpan)
				min = lifeSpan;
		}
		return min;
	}
	
	private static long getAgeMax(List<Item> items) {
		long[] lifeSpans = getLifeSpans(items);
		long max = Integer.MIN_VALUE;
		for (long lifeSpan : lifeSpans) {
			if (max < lifeSpan) {
				max = lifeSpan;
			}
		}
		return max;
	}
	
	public static String getCurAgeAvgMax(List<Item> currentItems, int months) {
		long min = getAgeMin(currentItems);
		long max = getAgeMax(currentItems);
		return String.format("%s days / %s days", min, max);
	}
	
	
}