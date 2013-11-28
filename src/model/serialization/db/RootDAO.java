package model.serialization.db;

import java.sql.Connection;
import java.util.List;

import model.IProductContainer;
import model.StorageUnits;

public class RootDAO implements IRootDAO {
	private Connection dbConnection;

	public RootDAO(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}

	@Override
	public List<StorageUnits> readAll() {
		
		return null;
	}

	@Override
	public void create(StorageUnits thing) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StorageUnits read(Object key) {
		// TODO Auto-generated method stub
		return null;
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
