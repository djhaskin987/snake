package model.serialization.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.IProduct;
import model.NonEmptyString;
import model.Product;
import model.Quantity;
import model.Unit;

import org.apache.commons.lang3.tuple.Pair;

public class ProductDAO implements IProductDAO {
	private JDBCWrapper wrapper;
	private final String TABLE = "Product";

	public ProductDAO(JDBCWrapper wrapper) {
		this.wrapper = wrapper;
	}

	private IProduct read(ResultSet rs) {
		try {
			return new Product(
					rs.getString("Barcode"),
					rs.getString("Description"),
					new Quantity(rs.getDouble("SizeValue"),
							Unit.getInstance(rs.getString("SizeUnit"))),
					rs.getInt("ShelfLife"),
					rs.getInt("ThreeMonthSupply"));
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<IProduct> readAll() {
		try {
			ResultSet rs = wrapper.queryAll(TABLE);
			ArrayList<IProduct> products = new ArrayList<IProduct>();
			while(rs.next()) {
				products.add(read(rs));
			}
			return products;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Pair<List<String>, List<Object>> getLists(IProduct product) {
		ArrayList<String> columnNames = new ArrayList<String>();
		ArrayList<Object> columnValues = new ArrayList<Object>();
		columnNames.add("CreationDate");
		columnValues.add(product.getCreateDate());
		columnNames.add("Barcode");
		columnValues.add(product.getBarcode());
		columnNames.add("Description");
		columnValues.add(product.getDescription());
		columnNames.add("SizeValue");
		columnValues.add(product.getItemSize().getValue());
		columnNames.add("SizeUnit");
		columnValues.add(product.getItemSize().getUnit().toString());
		columnNames.add("ShelfLife");
		columnValues.add(product.getShelfLife());
		columnNames.add("ThreeMonthSupply");
		columnValues.add(product.getThreeMonthSupply());
		return Pair.of((List<String>) columnNames, (List<Object>) columnValues);
	}

	@Override
	public void create(IProduct thing) {
		Pair<List<String>, List<Object>> lists = getLists(thing);
		wrapper.insert(TABLE, lists.getLeft(), lists.getRight());
	}

	@Override
	public IProduct read(NonEmptyString key) {
		return read(wrapper.query(TABLE, "Barcode", key.getValue()));
	}

	@Override
	public void update(IProduct thing) {
		Pair<List<String>, List<Object>> lists = getLists(thing);
		wrapper.update(TABLE, lists.getLeft(), lists.getRight(), "Barcode",
				thing.getBarcode().getValue());
	}

	@Override
	public void delete(IProduct thing) {
		wrapper.delete(TABLE, "Barcode", thing.getBarcode().getValue());
	}

	@Override
	public List<Pair<String, String>> getContainers(IProduct product) {
		ResultSet rs = wrapper.query("ProductContainerProductRelation",
				"ProductBarcode", product.getBarcode().getValue());
		List<Pair<String, String>> out = new ArrayList<Pair<String, String>>();
		try {
			while(rs.next()) {
				out.add(Pair.of(rs.getString("ProductContainerName"),
						rs.getString("ProductContainerStorageUnit")));
			}
			return out;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
