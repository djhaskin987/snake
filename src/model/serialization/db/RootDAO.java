package model.serialization.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

import org.apache.commons.lang3.tuple.Pair;

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
		StorageUnits su = read(null);
		if (su != null) {
			returned.add(su);
		}
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
			return null; //System.exit(1);
		}
		Date dateSinceLastRemovedItemsReport = null;
		try {
			dateSinceLastRemovedItemsReport = reportDateSet.getDate("lastRIR");
		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
			// System.exit(1);
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
		Date d = thing.getDateSinceLastRemovedItemsReport();
		if (d != null) {
			long epoch = d.getTime();
			String[] columnNames = {"lastRIR" };
			Object[] columnValues = { new Long(epoch).toString()};
			String[] identifierNames = {"id" };
			Object[] identifierValues = {"1"};
			dbConnection.update("Model",  Arrays.asList(columnNames), 
					Arrays.asList(columnValues),
					Arrays.asList(identifierNames),
					Arrays.asList(identifierValues));
		}
	}

	@Override
	public void delete(StorageUnits thing) {
		Object[] identifierValues = {"1"};
		dbConnection.delete("Model", "id", identifierValues);
	}

	@Override
	public IProductContainer getRemovedItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pair<String, String>> getStorageUnits() {
		List<Pair<String, String>> storageUnits = new ArrayList<Pair<String,String>>();
		ResultSet rs = dbConnection.executeQuery(
				"select name from ProductContainer where StorageUnit is null");
		try {
			while (rs.next()) {
				String name = rs.getString(1);
				Pair<String, String> p = Pair.of(name, null);
				storageUnits.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return storageUnits;
	}
}
