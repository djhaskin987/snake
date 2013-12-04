package model.serialization;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import model.Barcode;
import model.IItem;
import model.IProduct;
import model.IProductContainer;
import model.Item;
import model.ItemFactory;
import model.Model;
import model.NonEmptyString;
import model.ProductContainer;
import model.ProductContainerFactory;
import model.StorageUnits;
import model.serialization.db.*;

/**
 * An SqlSerializer will serialize a Model singleton to / from the database.
 * We use the JDBC driver to implement this. 
 */
public class SqlSerializer implements ISerializer {
	private JDBCWrapper conn;
	/**
	 * Creates a new SqlSerializer object
	 * 
	 * {@pre none}
	 * 
	 * {@post an SqlSerializer object}
	 */
	public SqlSerializer() {
		conn = new JDBCWrapper();
	}

	/* (non-Javadoc)
	 * @see model.serialization.ISerializer#load(model.Model)
	 */
	@Override
	public void load(Model model) {
		IRootDAO rdao = new RootDAO(conn);
		List<StorageUnits> l = rdao.readAll();
		if (l.size() > 0) {
			StorageUnits su = l.get(0);
			Model m = Model.getInstance();
			m.setStorageUnits(su);
			loadProductContainer(su);
		}
	}
	
	private void loadProductContainer(IProductContainer pc) {
		IProductContainerDAO pcDao = new ProductContainerDAO(conn);
		pcDao.getChildren(pc);
		List<Pair<String, String>> children = pcDao.getChildren(pc);
		loadProductContainerChildren(pc, children);
		List<String> itemsStr = pcDao.getItems(pc);
		List<String> productsStr = pcDao.getProducts(pc);
		loadProductItems(pc, itemsStr, productsStr);
	}
	
	private void loadProductContainerChildren(IProductContainer pc, List<Pair<String, String>> children) {
		for (Pair<String, String> child : children) {
			String name = child.getLeft();
			String storageUnit = child.getRight();
			IProductContainer pcChild;
			if (pc.getClass() == StorageUnits.class) {
				pcChild = ProductContainerFactory.getInstance().createStorageUnit(name);
			} else {
				pcChild = ProductContainerFactory.getInstance().createProductGroup(name);
			}
			pc.addProductContainer(pcChild);
		}
	}
	
	private void loadProductItems(IProductContainer pc, List<String> itemsStr, List<String> productsStr) {
		loadProducts(pc, itemsStr);
		loadItems(pc, itemsStr);
	}
	
	private void loadItems(IProductContainer pc, List<String> itemsStr) {
		ItemDAO iDao = new ItemDAO(conn);
		for (String itemStr : itemsStr) {
			Barcode iBarcode = new Barcode(itemStr);
			String pBarcode = iDao.getProductBarcode(iBarcode);
			IItem i = iDao.read(iBarcode);
			IItem item = createItem(i, pBarcode, pc);
			pc.addItem(item);
		}
	}
	
	private IItem createItem(IItem i, String pBarcode, IProductContainer pc) {
		Model m = Model.getInstance();
		IProduct product = m.getProduct(pBarcode);
		ItemFactory factory = ItemFactory.getInstance();
		IItem item = new Item(product, i.getBarcode(), i.getEntryDate(), i.getExitTime(), pc);
		return item;
	}
	
	private void loadProducts(IProductContainer pc, List<String> productsStr) {
		for (String productStr : productsStr) {
			ProductDAO pDao = new ProductDAO(conn);
			NonEmptyString neProductStr = new NonEmptyString(productStr);
			IProduct p = pDao.read(neProductStr);
			
		}
	}
	
	private IProduct getProduct(String product) {
		
	}

	/* (non-Javadoc)
	 * @see model.serialization.ISerializer#save(model.Model)
	 */
	@Override
	public void save(Model model) {
		Model m = Model.getInstance();
		StorageUnits su = m.getStorageUnits();
		RootDAO rdao = new RootDAO(conn);
		rdao.update(su);
	}

	/* (non-Javadoc)
	 * @see model.serialization.ISerializer#save(model.Model)
	 */
	@Override
	public void update(Model model) {
		save(model);
	}

}
