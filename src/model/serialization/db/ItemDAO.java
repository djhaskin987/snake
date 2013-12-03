package model.serialization.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
	
	public ItemDAO(JDBCWrapper dbConnection) {
		this.dbConnection = dbConnection;
	}

	private List<IItem> readAllBy(String [] columnNames,
			Object [] columnValues)
	{
		ResultSet itemSet = dbConnection.query("Item",
				Arrays.asList(columnNames),
				Arrays.asList(columnValues));
		List<IItem> returned = new LinkedList<IItem>();
		try {
			while (itemSet.next())
			{
				IItem nextItem = new Item(null,
						new Barcode(
								new NonEmptyString(
										itemSet.getString("Barcode"))),
						new ValidDate(itemSet.getDate("EntryDate")), 
						new DateTime(itemSet.getDate("ExitTime")),
						null);
				returned.add(nextItem);
			}
		} catch (SQLException | InvalidHITDateException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return returned;
		
	}
	@Override
	public List<IItem> readAll() {
		String [] columnNames = {};
		Object [] columnValues = {};
		return readAllBy(columnNames, columnValues);
	}

	@Override
	public void create(IItem thing) {
		// TODO Auto-generated method stub

	}

	@Override
	public IItem read(Barcode key) {
		String [] columnNames = {"Barcode"};
		Object [] columnValues = {key.toString()};
		List<IItem> items = readAllBy(columnNames, columnValues);
		if (items.size() == 0)
		{
			return null;
		}
		return items.get(0);
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
	Barcode getProductBarcode(IItem item) {
		String [] columnNames = {"Barcode"};
		Object [] columnValues = {item.getBarcode().toString()};
		
		ResultSet itemSet = dbConnection.query("Item",
				Arrays.asList(columnNames),
				Arrays.asList(columnValues));
		List<IItem> returned = new LinkedList<IItem>();
		try {
			while (itemSet.next())
			{
				IItem nextItem = new Item(null,
						new Barcode(
								new NonEmptyString(
										itemSet.getString("Barcode"))),
						new ValidDate(itemSet.getDate("EntryDate")), 
						new DateTime(itemSet.getDate("ExitTime")),
						null);
				returned.add(nextItem);
			}
		} catch (SQLException | InvalidHITDateException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return returned;
	}


	@Override
	public void setProduct(IItem item, IProduct product) {
		// TODO Auto-generated method stub
		
	}

}
