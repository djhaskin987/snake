package model.serialization.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import fj.F;
import model.Barcode;
import model.DateTime;
import model.IItem;
import model.InvalidHITDateException;
import model.Item;
import model.ValidDate;

public class ItemDAO implements IItemDAO {
	private JDBCWrapper dbConnection;
	
	private static class ItemRecord {
		public static String TABLE = "Item";
		private Barcode productBarcode;
		private Barcode barcode;
		private ValidDate entryDate;
		private DateTime exitTime;
		private String productContainerName;
		private String productContainerStorageUnit;
		public static List<String> getColumnNames()
		{
			return Arrays.asList(COLUMN_NAMES);
			
		}
		public static final String [] COLUMN_NAMES = {"ProductBarcode",
			
									"Barcode",
									"EntryDate",
									"ExitTime",
									"ProductContainerName",
									"ProductContainerStorageUnit"};
		public static final String [] IDENTIFIER_NAMES = {"Barcode"};
		public static List<String> getIdentifierNames()
		{
			return Arrays.asList(IDENTIFIER_NAMES);
		}
		public List<Object> getIdentifierValues()
		{
			Object [] result = {
					barcode
			};
			return Arrays.asList(result);
		}
		
		public List<Object> getColumnValues()
		{
			Object [] result = {
					productBarcode,
					barcode,
					entryDate,
					exitTime,
					productContainerName,
					productContainerStorageUnit
			};
			return Arrays.asList(result);
		}
		
		public ItemRecord(Barcode barcode,
				Barcode productBarcode,
				ValidDate entryDate)
		{
			this(barcode, productBarcode, entryDate, null, null, null);
		}
		
		public ItemRecord(Barcode barcode,
				Barcode productBarcode,
				ValidDate entryDate,
				DateTime exitTime,
				String productContainerName,
				String productContainerStorageUnit)
		{
			this.barcode = barcode;
			this.exitTime = exitTime;
			this.entryDate = entryDate;
			this.productBarcode = productBarcode;
			this.productContainerName = productContainerName;
			this.productContainerStorageUnit = productContainerStorageUnit;
		}
		
		public boolean isValid()
		{
			return barcode != null &&
					productBarcode != null &&
					entryDate != null;
		}
		
		public boolean isRemoved()
		{
			return productContainerName == null;
		}
		
		public Barcode getBarcode()
		{
			return barcode;
		}
		
		public void addProductContainerStorageUnit(String
				productContainerStorageUnit)
		{
			if (this.productContainerStorageUnit == null)
			{
				this.productContainerStorageUnit =
						productContainerStorageUnit;
			}
		}
		public void addProductContainerName(String productContainerName)
		{
			if (this.productContainerName == null)
			{
				this.productContainerName = productContainerName;
			}
		}
		public void addExitTime(DateTime exitTime)
		{
			if (this.exitTime == null)
			{
				this.exitTime = exitTime;
			}
		}
		public void addEntryDate(ValidDate entryDate)
		{
			if (this.entryDate == null)
			{
				this.entryDate = entryDate;
			}
		}
		public void addProductBarcode(Barcode productBarcode)
		{
			if (this.productBarcode == null)
			{
				this.productBarcode = productBarcode;
			}
		}
		public void addBarcode(Barcode barcode)
		{
			if (this.barcode == null)
			{
				this.barcode = barcode;
			}
		}
		
		public Barcode getProductBarcode() {
			return productBarcode;
		}

		public ValidDate getEntryDate() {
			return entryDate;
		}

		public DateTime getExitTime() {
			return exitTime;
		}

		public String getProductContainerName() {
			return productContainerName;
		}

		public String getProductContainerStorageUnit() {
			return productContainerStorageUnit;
		}
		
		public ItemRecord(ResultSet set)
		{
			try {
				productBarcode = new Barcode(set.getString("ProductBarcode"));
				barcode = new Barcode(set.getString("Barcode"));
				entryDate = new ValidDate(set.getDate("EntryDate"));
				if(set.getDate("ExitTime") != null) {
					exitTime = new DateTime(set.getDate("ExitTime"));
				}
				productContainerName = set.getString("ProductContainerName");
				productContainerStorageUnit =
						set.getString("ProductContainerStorageUnit");
			}
			catch(SQLException | InvalidHITDateException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public ItemDAO(JDBCWrapper dbConnection) {
		this.dbConnection = dbConnection;
	}

	private fj.data.List<ItemRecord> getResults(String [] columnNames,
			Object [] columnValues)
	{
		ResultSet itemSet = dbConnection.query(ItemRecord.TABLE,
				Arrays.asList(columnNames),
				Arrays.asList(columnValues));
		fj.data.List<ItemRecord> returned = 
				fj.data.List.list();
		try {
			while (itemSet.next())
			{
				ItemRecord record = new ItemRecord(itemSet);
				returned = returned.cons(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return returned;
	}
	
	private static IItem itemRecordToItem(final ItemRecord record)
	{
		return new Item(null, record.getBarcode(),
				record.getEntryDate(),
				record.getExitTime(),
				null);
	}
	
	private static ItemRecord itemToItemRecord(final IItem item)
	{
		ItemRecord result = new ItemDAO.ItemRecord(item.getBarcode(),
				new Barcode(item.getProduct().getBarcode()),
				item.getEntryDate());
		if (item.getProductContainer() != null)
		{
			result.addProductContainerName(
					item.getProductContainer().getName().toString()
					);
			result.addProductContainerStorageUnit(
					item.getProductContainer().getUnit());
		}
		else
		{
			if (item.getExitTime() == null)
			{
				throw new IllegalStateException("Item '" + item.getBarcode().toString() +
						"' has no exit time, yet has no product container either.");
			}
		}
		if (item.getExitTime() != null)
		{
			result.addExitTime(item.getExitTime());
		}
		return result;
	}
	
	private fj.data.List<IItem> readAllBy(String [] columnNames,
			Object [] columnValues)
	{
		fj.data.List<ItemRecord> results = getResults(columnNames, columnValues);
		fj.data.List<IItem> items = results.map(new F<ItemRecord, IItem>() {
			public IItem f(final ItemRecord record)
			{
				return itemRecordToItem(record);
			}
		});
		return items;
	}
	
	@Override
	public List<IItem> readAll() {
		String [] columnNames = {};
		Object [] columnValues = {};
		return new LinkedList<IItem>(
				readAllBy(columnNames,columnValues).toCollection());
	}

	@Override
	public void create(IItem thing) {
		ItemRecord record = itemToItemRecord(thing);
		dbConnection.insert(ItemRecord.TABLE, ItemRecord.getColumnNames(), record.getColumnValues());
	}

	@Override
	public IItem read(Barcode key) {
		String [] columnNames = {"Barcode"};
		Object [] columnValues = {key.getBarcode().toString()};
		fj.data.List<IItem> items = readAllBy(columnNames,columnValues);
		if (items.isEmpty())
		{
			return null;
		}
		else
		{
			return items.head();
		}
	}

	@Override
	public void update(IItem thing) {
		ItemRecord record = itemToItemRecord(thing);
		
		dbConnection.update(ItemRecord.TABLE, ItemRecord.getColumnNames(),
				record.getColumnValues(),
				ItemRecord.getIdentifierNames(),
				record.getIdentifierValues());
	}

	@Override
	public void delete(IItem thing) {
		ItemRecord record = itemToItemRecord(thing);
		dbConnection.delete(ItemRecord.TABLE,
				ItemRecord.getIdentifierNames(),
				record.getIdentifierValues());
	}

	@Override
	public Barcode getProductBarcode(Barcode itemBarcode) {
		String [] columnNames = {"Barcode"};
		Object [] columnValues = {itemBarcode.getBarcode()};
		fj.data.List<ItemRecord> results =
				getResults(columnNames, columnValues);
		if (results.isEmpty())
		{
			return null;
		}
		return results.head().getProductBarcode();
	}
	@Override
	public String getProductContainerName(Barcode itemBarcode) {
		String [] columnNames = {"Barcode"};
		Object [] columnValues = {itemBarcode.getBarcode()};
		fj.data.List<ItemRecord> results =
				getResults(columnNames, columnValues);
		if (results.isEmpty())
		{
			return null;
		}
		return results.head().getProductContainerName();
	}
	@Override
	public String getStorageUnitName(Barcode itemBarcode) {
		String [] columnNames = {"Barcode"};
		Object [] columnValues = {itemBarcode.toString()};
		fj.data.List<ItemRecord> results =
				getResults(columnNames, columnValues);
		if (results.isEmpty())
		{
			return null;
		}
		return results.head().getProductContainerStorageUnit();
	}
}
