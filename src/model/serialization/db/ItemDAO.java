package model.serialization.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import fj.F;
import model.Barcode;
import model.DateTime;
import model.IItem;
import model.IProduct;
import model.InvalidHITDateException;
import model.Item;
import model.NonEmptyString;
import model.ValidDate;

public class ItemDAO implements IItemDAO {
	private JDBCWrapper dbConnection;
	
	private class ItemRecord {
		private Barcode productBarcode;
		private Barcode barcode;
		private ValidDate entryDate;
		private DateTime exitTime;
		private String productContainerName;
		private String productContainerStorageUnit;
		public ItemRecord(Barcode barcode,
				ValidDate entryDate,
				DateTime exitTime)
		{
			this.barcode = barcode;
			this.exitTime = exitTime;
			this.entryDate = entryDate;
			this.productBarcode = null;
			this.productContainerName = null;
			this.productContainerStorageUnit = null;
		}
		
		public boolean isComplete()
		{
			return barcode != null &&
					exitTime != null &&
					entryDate != null &&
					productContainerName != null &&
					productContainerStorageUnit != null;
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
				productBarcode = new Barcode(set.getString("Barcode"));
				barcode = new Barcode(set.getString("Barcode"));
				entryDate = new ValidDate(set.getDate("EntryDate"));
				exitTime = new DateTime(set.getDate("ExitTime"));
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
		ResultSet itemSet = dbConnection.query("Item",
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
	
	private static IItem ItemRecordToItem(final ItemRecord record)
	{
		return new Item(null, record.getBarcode(),
				record.getEntryDate(),
				record.getExitTime(),
				null);
	}
	
	private static ItemRecord ItemToItemRecord(final IItem item)
	{
		return new ItemRec
		
	}
	
	private fj.data.List<IItem> readAllBy(String [] columnNames,
			Object [] columnValues)
	{
		fj.data.List<ItemRecord> results = getResults(columnNames, columnValues);
		fj.data.List<IItem> items = results.map(new F<ItemRecord, IItem>() {
			public IItem f(final ItemRecord record)
			{
				return ItemRecordToItem(record);
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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(IItem thing) {
		// TODO Auto-generated method stub

	}

	@Override
	public Barcode getProductBarcode(IItem item) {
		String [] columnNames = {"Barcode"};
		Object [] columnValues = {item.getBarcode().toString()};
		fj.data.List<ItemRecord> results =
				getResults(columnNames, columnValues);
		if (results.isEmpty())
		{
			return null;
		}
		return results.head().getProductBarcode();
	}
	
	@Override
	public void setProduct(IItem item, IProduct product) {
		ItemRecord record = new ItemRecord(item.getBarcode(),
				item.getEntryDate(),
				item.getExitTime());
		record.addProductBarcode(new Barcode(product.getBarcode()));
		
	}



	@Override
	public IProduct getProduct() {
		// TODO Auto-generated method stub
		return null;
	}

}
