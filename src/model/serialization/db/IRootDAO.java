package model.serialization.db;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import model.StorageUnits;
import model.IProductContainer;

public interface IRootDAO extends IDAO<Object, StorageUnits> {
	public IProductContainer getRemovedItems();
	public List<Pair<String, String>> getStorageUnits();
}