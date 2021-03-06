package model.serialization.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.IProduct;
import model.IProductContainer;
import model.Model;
import model.NonEmptyString;
import model.Product;
import model.ProductGroup;
import model.Quantity;
import model.StorageUnit;
import model.Unit;

import org.apache.commons.lang3.tuple.Pair;

import com.sun.org.apache.xpath.internal.operations.Mod;

public class ProductContainerDAO implements IProductContainerDAO {
	private JDBCWrapper wrapper;
	private final String TABLE = "ProductContainer";

	public ProductContainerDAO(JDBCWrapper wrapper) {
		this.wrapper = wrapper;
	}

	private IProductContainer read(ResultSet rs) {
		try {
			if(rs.getString("StorageUnit") == null) {
				return Model.getInstance().createStorageUnit(rs.getString("Name"));
			} else {
				IProductContainer unit = Model.getInstance().getStorageUnits()
						.getStorageUnit(rs.getString("StorageUnit"));
				if(unit == null) {
					System.err.println("Error: No storage unit found with name "
							+ rs.getString("StorageUnit"));
					return null;
				}
				IProductContainer parent = unit.getDescendant(rs.getString("ParentContainer"));
				return Model.getInstance().createProductGroup(
						rs.getString("Name"),
						Double.toString(rs.getDouble("ThreeMonthSupplyValue")),
						rs.getString("ThreeMonthSupplyUnit"),
						parent);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<IProductContainer> readAll() {
		//RefusedBequest
		return null;
	}

	private Pair<List<String>, List<Object>> getLists(IProductContainer productContainer) {
		ArrayList<String> columnNames = new ArrayList<String>();
		ArrayList<Object> columnValues = new ArrayList<Object>();
		columnNames.add("Name");
		columnValues.add(productContainer.getName().getValue());
		if(productContainer instanceof ProductGroup) {
			IProductContainer parent = productContainer.getParent();
			//if(parent != productContainer) {
			columnNames.add("StorageUnit");
			columnValues.add(productContainer.getUnitPC().getName());
			columnNames.add("ParentContainer");
			columnValues.add(parent.getName());
			columnNames.add("IsParentStorageUnit");
			columnValues.add(parent instanceof StorageUnit);
			Quantity quantity = ((ProductGroup) productContainer).getThreeMonthSupplyQuantity();
			columnNames.add("ThreeMonthSupplyValue");
			columnValues.add(quantity.getValue());
			columnNames.add("ThreeMonthSupplyUnit");
			columnValues.add(quantity.getUnit());
		}
		return Pair.of((List<String>) columnNames, (List<Object>) columnValues);
	}

	@Override
	public void create(IProductContainer thing) {
		Pair<List<String>, List<Object>> lists = getLists(thing);
		wrapper.insert(TABLE, lists.getLeft(), lists.getRight());
	}

	@Override
	public IProductContainer read(Pair<String, String> key) {
		String[] names = {"Name", "StorageUnit"};
		Object[] values = {key.getLeft(), key.getRight()};
		return read(wrapper.query(TABLE, Arrays.asList(names), Arrays.asList(values)));
	}

	private Pair<List<String>, List<Object>> getIdentifiers(IProductContainer productContainer) {
		List<String> identifierNames = new ArrayList<String>();
		List<Object> identifierValues = new ArrayList<Object>();
		identifierNames.add("Name");
		identifierValues.add(productContainer.getName().getValue());
		identifierNames.add("StorageUnit");
		if(productContainer instanceof StorageUnit) {
			identifierValues.add(null);
		} else {
			IProductContainer storageUnit = productContainer.getUnitPC();
			identifierValues.add(storageUnit.getName().getValue());
		}
		return Pair.of(identifierNames, identifierValues);
	}

	private Pair<List<String>, List<Object>> getIdentifiers(Pair<String, String> identifier) {
		List<String> identifierNames = new ArrayList<String>();
		List<Object> identifierValues = new ArrayList<Object>();
		identifierNames.add("Name");
		identifierValues.add(identifier.getLeft());
		identifierNames.add("StorageUnit");
		identifierValues.add(identifier.getRight());
		return Pair.of(identifierNames, identifierValues);
	}

	@Override
	public void update(IProductContainer thing) {
		System.err.println("Error: ProductContainerDAO does not support"
				+ " update(IProductContainer).");
	}

	@Override
	public void delete(IProductContainer thing) {
		Pair<List<String>, List<Object>> identifiers = getIdentifiers(thing);
		wrapper.delete(TABLE, identifiers.getLeft(), identifiers.getRight());
	}

	@Override
	public List<String> getProducts(Pair<String, String> key) {
		List<String> identifierNames = new ArrayList<String>();
		List<Object> identifierValues = new ArrayList<Object>();
		identifierNames.add("ProductContainerName");
		identifierValues.add(key.getLeft());
		identifierNames.add("ProductContainerStorageUnit");
		identifierValues.add(key.getRight());
		ResultSet rs = wrapper.query("ProductContainerProductRelation",
				identifierNames, identifierValues);
		List<String> products = new ArrayList<String>();
		try {
			while(rs.next()) {
				products.add(rs.getString("ProductBarcode"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public Pair<String, String> getParent(Pair<String, String> container) {
		Pair<List<String>, List<Object>> identifiers = getIdentifiers(container);
		ResultSet rs = wrapper.query(TABLE, identifiers.getLeft(), identifiers.getRight());
		try {
			if(rs.next()) {
				if(rs.getBoolean("IsParentStorageUnit")) {
					return Pair.of(rs.getString("ParentContainer"), null);
				} else {
					return Pair.of(rs.getString("ParentContainer"), container.getRight());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getItems(IProductContainer container) {
		List<String> identifierNames = new ArrayList<String>();
		List<Object> identifierValues = new ArrayList<Object>();
		identifierNames.add("ProductContainerName");
		identifierValues.add(container.getName());
		identifierNames.add("ProductContainerStorageUnit");
		IProductContainer storageUnit = container.getUnitPC();
		identifierValues.add(storageUnit.getName());
		ResultSet rs = wrapper.query("ProductContainerProductRelation",
				identifierNames, identifierValues);
		List<String> items = new ArrayList<String>();
		try {
			while(rs.next()) {
				items.add(rs.getString("Barcode"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public List<Pair<String, String>> getChildren(IProductContainer container) {
		List<String> identifierNames = new ArrayList<String>();
		List<Object> identifierValues = new ArrayList<Object>();
		identifierNames.add("ParentContainer");
		identifierValues.add(container.getName());
		identifierNames.add("StorageUnit");
		IProductContainer storageUnit = container.getUnitPC();
		String unitName;
		if(storageUnit == container) {
			identifierValues.add(null);
			unitName = null;
		} else {
			identifierValues.add(storageUnit.getName());
			unitName = storageUnit.getName().toString();
		}
		ResultSet rs = wrapper.query("ProductContainer", identifierNames, identifierValues);
		List<Pair<String, String>> children = new ArrayList<Pair<String, String>>();
		try {
			while(rs.next()) {
				children.add(Pair.of(rs.getString("Name"), unitName));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return children;
	}

	@Override
	public void update(String name, String storageUnit,
			IProductContainer container) {
		Pair<List<String>, List<Object>> lists = getLists(container);
		List<String> identifierNames = new ArrayList<String>();
		List<Object> identifierValues = new ArrayList<Object>();
		identifierNames.add("Name");
		identifierValues.add(name);
		identifierNames.add("StorageUnit");
		identifierValues.add(storageUnit);
		wrapper.update(TABLE, lists.getLeft(), lists.getRight(), identifierNames, identifierValues);
	}

	@Override
	public void addProductToProductContainer(IProduct product, IProductContainer productContainer) {
		String[] names = {"ProductContainerName",
				"ProductContainerStorageUnit",
		"ProductBarcode"};
		System.out.println("Product added to product container.");
		List<Object> values = new ArrayList<Object>();
		values.add(productContainer.getName().getValue());
		if(productContainer instanceof StorageUnit) {
			values.add(null);
		} else {
			values.add(productContainer.getUnitPC().getName().getValue());
		}
		values.add(product.getBarcode().getValue());
		wrapper.insert("ProductContainerProductRelation",
				Arrays.asList(names), values);
	}

	@Override
	public void removeProductFromProductContainer(IProduct product,
			IProductContainer productContainer) {
		String[] names = {"ProductContainerName",
				"ProductContainerStorageUnit",
		"ProductBarcode"};
		Object[] values = {productContainer.getName().getValue(),
				null,
				product.getBarcode().getValue()};
		if(!(productContainer instanceof StorageUnit)) {
			values[1] = productContainer.getUnitPC().getName().getValue();
		}
		wrapper.delete("ProductContainerProductRelation",
				Arrays.asList(names), Arrays.asList(values));
	}

	@Override
	public Map<Pair<String, String>, IProductContainer> getMap() {
		Map<Pair<String, String>, IProductContainer> map =
				new HashMap<Pair<String, String>, IProductContainer>();
		ResultSet rs = wrapper.queryAll(TABLE);
		try {
			while(rs.next()) {
				String name = rs.getString("Name");
				String unit = rs.getString("StorageUnit");
				if(unit == null) {
					map.put(Pair.of(name, (String) null),
							Model.getInstance().createStorageUnit(name));
				} else {
					IProductContainer productGroup = Model.getInstance().createProductGroup(
							name,
							Double.toString(rs.getDouble("ThreeMonthSupplyValue")),
							rs.getString("ThreeMonthSupplyUnit"),
							null);
					map.put(Pair.of(name, unit), productGroup);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}

}
