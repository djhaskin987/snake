package model.serialization.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
				IProductContainer unit = Model.getInstance().getStorageUnits().getStorageUnit(rs.getString("StorageUnit"));
				//System.out.println(rs.getString("StorageUnit"));
				//System.out.println(Model.getInstance().getStorageUnits().getStorageUnitNames().size());
				if(unit == null) {
					System.err.println("Error: No storage unit found with name " + rs.getString("StorageUnit"));
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

	//TODO: Duplicate code. Same as ProductDAO.
	@Override
	public List<IProductContainer> readAll() {
		try {
			ResultSet rs = wrapper.queryAll(TABLE);
			ArrayList<IProductContainer> productContainers = new ArrayList<IProductContainer>();
			while(rs.next()) {
				productContainers.add(read(rs));
			}
			return productContainers;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Pair<List<String>, List<Object>> getLists(IProductContainer productContainer) {
		ArrayList<String> columnNames = new ArrayList<String>();
		ArrayList<Object> columnValues = new ArrayList<Object>();
		columnNames.add("Name");
		columnValues.add(productContainer.getName().getValue());
		if(productContainer instanceof ProductGroup) {
			IProductContainer parent = productContainer.getUnitPC();
		//if(parent != productContainer) {
			columnNames.add("StorageUnit");
			columnValues.add(parent.getName());
			columnNames.add("ParentContainer");
			columnValues.add(productContainer.getParent().getName());
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
		identifierValues.add(productContainer.getName());
		identifierNames.add("StorageUnit");
		IProductContainer storageUnit = productContainer.getUnitPC();
		identifierValues.add(storageUnit.getName());
		return Pair.of(identifierNames, identifierValues);
	}

	@Override
	public void update(IProductContainer thing) {
		System.err.println("Error: ProductContainerDAO does not support update(IProductContainer).");
	}

	@Override
	public void delete(IProductContainer thing) {
		Pair<List<String>, List<Object>> identifiers = getIdentifiers(thing);
		wrapper.delete(TABLE, identifiers.getLeft(), identifiers.getRight());
	}

	@Override
	public List<String> getProducts(IProductContainer container) {
		List<String> identifierNames = new ArrayList<String>();
		List<Object> identifierValues = new ArrayList<Object>();
		identifierNames.add("ProductContainerName");
		identifierValues.add(container.getName());
		identifierNames.add("ProductContainerStorageUnit");
		IProductContainer storageUnit = container.getUnitPC();
		identifierValues.add(storageUnit.getName());
		ResultSet rs = wrapper.query("ProductContainerProductRelation", identifierNames, identifierValues);
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
	public Pair<String, String> getParent(IProductContainer container) {
		Pair<List<String>, List<Object>> identifiers = getIdentifiers(container);
		ResultSet rs = wrapper.query(TABLE, identifiers.getLeft(), identifiers.getRight());
		try {
			if(rs.next()) {
				return Pair.of(rs.getString("Name"), rs.getString("StorageUnit"));
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
		ResultSet rs = wrapper.query("ProductContainerProductRelation", identifierNames, identifierValues);
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
		ResultSet rs = wrapper.query("ProductContainerProductRelation", identifierNames, identifierValues);
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
		Object[] values = {productContainer.getName().getValue(),
				productContainer.getUnitPC().getName().getValue(),
				product.getBarcode().getValue()};
		wrapper.insert("ProductContainerProductRelation",
				Arrays.asList(names), Arrays.asList(values));
	}
	
	@Override
	public void removeProductFromProductContainer(IProduct product, IProductContainer productContainer) {
		String[] names = {"ProductContainerName",
				"ProductContainerStorageUnit",
				"ProductBarcode"};
		Object[] values = {productContainer.getName().getValue(),
				productContainer.getUnitPC().getName().getValue(),
				product.getBarcode().getValue()};
		wrapper.delete("ProductContainerProductRelation",
				Arrays.asList(names), Arrays.asList(values));
	}

}
