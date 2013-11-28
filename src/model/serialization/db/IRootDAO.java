package model.serialization.db;
import java.util.List;

import model.StorageUnits;
import model.IProductContainer;

public interface IRootDAO extends IDAO<Object, StorageUnits> {
	public List<IProductContainer> getStorageUnits();
	public void addStorageUnit(IProductContainer StU);
	public void rmStorageUnit(IProductContainer StU);
}