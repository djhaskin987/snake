package model.serialization.db;

import java.sql.Connection;
import java.util.List;

import model.Barcode;
import model.IItem;
import model.IProduct;

public class ItemDAO implements IItemDAO {
	private Connection dbConnection;
	
	public ItemDAO(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}

	@Override
	public List<IItem> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(IItem thing) {
		// TODO Auto-generated method stub

	}

	@Override
	public IItem read(Barcode key) {
		// TODO Auto-generated method stub
		return null;
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
	public IProduct getProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProduct getProduct(IItem item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProduct(IItem item, IProduct product) {
		// TODO Auto-generated method stub
		
	}

}
