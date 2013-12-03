package model.serialization.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		
		String dateFieldName = "dateSinceLastRemovedItemsReport";
		ResultSet reportDateSet = 
				dbConnection.executeQuery("select * from Model where 'id' = '1';");
		reportDateSet.next();
		RemovedItems removedItems = null;
		Date dateSinceLastRemovedItemsReport = reportDateSet.getDate("lastRIR");
		
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
