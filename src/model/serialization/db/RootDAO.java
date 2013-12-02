package model.serialization.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import model.IProductContainer;
import model.RemovedItems;
import model.StorageUnits;

public class RootDAO implements IRootDAO {
	private Connection dbConnection;
	private Statement dbStatement;

	public RootDAO(Connection dbConnection) {
		this.dbConnection = dbConnection;
		try {
			dbStatement = dbConnection.createStatement();
		} catch (SQLException e) {
			System.err.println("Failed to create mechanism allowing the program" +
					" to talk to the database");
			e.printStackTrace();
		}
	}

	@Override
	public List<StorageUnits> readAll() {
		List<StorageUnits> returned = new ArrayList<StorageUnits>(1);
		returned.add(read(null));
		return returned;
	}

	@Override
	public void create(StorageUnits thing) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StorageUnits read(Object key) {
		String dateName =
				"dateSinceLastRemovedItemsReport";
		ResultSet reportDateSet =
				dbStatement.executeQuery("select * from Model where " +
						+ "'name' = '" + dateName +
						"';");
		
		reportDateSet.next();
		RemovedItems removedItems = null;
		Date dateSinceLastRemovedItemsReport = reportDateSet.getDate("value");
		
		StorageUnits returned = new StorageUnits();
		returned
			.setDateSinceLastRemovedItemsReport(
					dateSinceLastRemovedItemsReport);
		returned
			.setTag(null);
		returned
			.setName(name);
	}

	@Override
	public void update(StorageUnits thing) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(StorageUnits thing) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IProductContainer> getStorageUnits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStorageUnit(IProductContainer StU) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rmStorageUnit(IProductContainer StU) {
		// TODO Auto-generated method stub
		
	}

}
