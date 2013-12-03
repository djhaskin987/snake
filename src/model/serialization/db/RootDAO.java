package model.serialization.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

import model.IProductContainer;
import model.RemovedItems;
import model.StorageUnits;

public class RootDAO implements IRootDAO {
	private JDBCWrapper dbConnection;

	public RootDAO(JDBCWrapper dbConnection) {
		this.dbConnection = dbConnection;
	}

	@Override
	public List<StorageUnits> readAll() {
		List<StorageUnits> returned = new ArrayList<StorageUnits>();
		returned.add(read(null));
		return returned;
	}

	@Override
	public void create(StorageUnits thing) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StorageUnits read(Object key) {
		
		ResultSet reportDateSet = 
				dbConnection.executeQuery("select * from Model where 'id' = '1';");
		try {
			reportDateSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Date dateSinceLastRemovedItemsReport = null;
		try {
			dateSinceLastRemovedItemsReport = reportDateSet.getDate("lastRIR");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		StorageUnits returned = new StorageUnits();
		returned
			.setDateSinceLastRemovedItemsReport(
					dateSinceLastRemovedItemsReport);
		returned
			.setTag(null);
		returned
			.setName("root");
		return returned;
	}

	/* Just updates 1-1. */
	public void update(StorageUnits thing) {
		
		long epoch = thing.getDateSinceLastRemovedItemsReport().getTime();
		String[] columnNames = {"lastRIR" };
		Object[] columnValues = { new Long(epoch).toString()};
		String[] identifierNames = {"id" };
		Object[] identifierValues = {"1"};
		dbConnection.update("Model",  Arrays.asList(columnNames), 
				Arrays.asList(columnValues),
				Arrays.asList(identifierNames),
				Arrays.asList(identifierValues));
	}

	@Override
	public void delete(StorageUnits thing) {
		Object[] identifierValues = {"1"};
		dbConnection.delete("Model", "id", identifierValues);
	}
}
