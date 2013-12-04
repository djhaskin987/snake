package model.serialization;

import gui.inventory.InventoryController;
import gui.item.ItemData;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.tuple.Pair;

import model.Barcode;
import model.IItem;
import model.IModelTagable;
import model.IProduct;
import model.IProductContainer;
import model.Item;
import model.ItemFactory;
import model.Model;
import model.ModelActions;
import model.NonEmptyString;
import model.ObservableArgs;
import model.ProductContainer;
import model.ProductContainerFactory;
import model.StorageUnits;
import model.serialization.db.*;

/**
 * An SqlSerializer will serialize a Model singleton to / from the database.
 * We use the JDBC driver to implement this. 
 */
public class SqlSerializer implements ISerializer, Observer {
	private JDBCWrapper conn;
	private IRootDAO rdao;
	private IProductContainerDAO productContainerDAO;
	private IProductDAO productDAO;
	private IItemDAO itemDAO;
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
		rdao = new RootDAO(conn);
		productContainerDAO = new ProductContainerDAO(conn);
		productDAO = new ProductDAO(conn);
		itemDAO = new ItemDAO(conn);
		List<StorageUnits> l = rdao.readAll();
		if (l.size() > 0) {
			StorageUnits su = l.get(0);
			Model m = Model.getInstance();
			m.setStorageUnits(su);
			loadProductContainer(su);
			Model.getInstance().addObserver(this);
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
		for (IProductContainer child : pc.getChildren()) {
			loadProductContainer(child);
		}
	}
	
	private void loadProductContainerChildren(IProductContainer pc, List<Pair<String, String>> children) {
		for (Pair<String, String> child : children) {
			ProductContainerDAO pcDao = new ProductContainerDAO(conn);
			IProductContainer pcChild = pcDao.read(child);
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
		IProduct product = getProduct(pBarcode);
		IItem item = new Item(product, i.getBarcode(), i.getEntryDate(), i.getExitTime(), pc);
		return item;
	}
	
	private void loadProducts(IProductContainer pc, List<String> productsStr) {
		for (String productStr : productsStr) {
			IProduct product = getProduct(productStr);
			pc.addProduct(product);
		}
	}
	
	private IProduct getProduct(String barcode) {
		Model m = Model.getInstance();
		IProduct product = m.getProduct(barcode);
		if (product == null) {
			ProductDAO pDao = new ProductDAO(conn);
			NonEmptyString neProductStr = new NonEmptyString(barcode);
			product = pDao.read(neProductStr);	
		}
		return product;	
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
	public void update(Observable arg0, Object arg1) {
		// This method assumes that the only thing that InventoryController is
		// observing is the StorageUnits instance
		Pair<ModelActions, IModelTagable> pair = pairExtract(arg1);
		ModelActions action = pair.getLeft();
		IModelTagable payload = pair.getRight();
		switch (action)
		{
			case INSERT_STORAGE_UNIT:
				insertStorageUnit(payload);
				break;
			case EDIT_STORAGE_UNIT:
				editStorageUnit(payload);
				break;
			case INSERT_PRODUCT_GROUP:
				insertProductGroup(payload);
				break;
			case EDIT_PRODUCT_GROUP:
				editProductGroup(payload);
				break;
			case INSERT_ITEMS:
				insertItems(payload);
				break;
			case UNDO_INSERT_ITEMS:
				undoInsertItems(payload);
				break;
			case UNDO_INSERT_PRODUCT_AND_ITEMS:
				undoInsertProductAndItems(payload);
				break;
			case UNDO_INSERT_NEW_PRODUCT_AND_ITEMS:
				undoInsertNewProductAndItems(payload);
				break;
			case REMOVE_ITEMS:
				removeItems(payload);
				break;
			case UNDO_REMOVE_ITEMS:
				undoRemoveItems(payload);
				break;
			case TRANSFER_ITEM:
				transferItem(payload);
				break;
			case EDIT_ITEM:
				editItem(payload);
				break;
			case MOVE_ITEM:
				moveItem(payload);
				break;
			case INSERT_PRODUCT:
				insertProduct(payload);
				break;
			case EDIT_PRODUCT:
				editProduct(payload);
				break;
			case TRANSFER_PRODUCT:
				transferProduct(payload);
				break;
			case NEW_PRODUCT:
				newProduct(payload);
				break;
			default:
				throw new IllegalStateException("Could not detect what changed");
		}
	}

	private void insertStorageUnit(IModelTagable payload) {
		productContainerDAO.create((IProductContainer) payload);
	}

	private void editStorageUnit(IModelTagable payload) {
		productContainerDAO.update((IProductContainer) payload);
	}

	private void insertProductGroup(IModelTagable payload) {
		productContainerDAO.create((IProductContainer) payload);
	}

	private void editProductGroup(IModelTagable payload) {
		productContainerDAO.update((IProductContainer) payload);
	}

	private void insertItems(IModelTagable payload) {
		ObservableArgs<IItem> batch = (ObservableArgs<IItem>) payload;
		for(IItem item : batch) {
			itemDAO.create(item);
		}
	}

	private void undoInsertItems(IModelTagable payload) {
		ObservableArgs<IItem> batch = (ObservableArgs<IItem>) payload;
		for(IItem item : batch) {
			itemDAO.delete(item);
		}
	}

	//TODO: This could be improved by modifying the payload.
	private void undoInsertProductAndItems(IModelTagable payload) {
		ObservableArgs<IItem> batch = (ObservableArgs<IItem>) payload;
		IProductContainer target = batch.get(0).getProductContainer();
		IProductContainer storageUnit = target.getUnitPC();
		String storageUnitName = storageUnit.getName().getValue();
		IProduct product = batch.get(0).getProduct();
		List<Pair<String, String>> containers = productDAO.getContainers(product);
		IProductContainer origin = null;
		for(Pair<String, String> container : containers) {
			if(container.getRight() == storageUnitName) {
				origin = storageUnit.getDescendant(container.getLeft());
			}
		}

		productContainerDAO.removeProductFromProductContainer(product, origin);
		productContainerDAO.addProductToProductContainer(product, target);
	}

	private void undoInsertNewProductAndItems(IModelTagable payload) {
		ObservableArgs<IItem> batch = (ObservableArgs<IItem>) payload;
		productDAO.delete(batch.get(0).getProduct());
		for(IItem item : batch) {
			itemDAO.delete(item);
		}
	}

	private void removeItems(IModelTagable payload) {
		editItem(payload);
	}

	private void undoRemoveItems(IModelTagable payload) {
		editItem(payload);
	}

	private void transferItem(IModelTagable payload) {
		editItem(payload);
	}

	private void editItem(IModelTagable payload) {
		IItem item = (IItem) payload;
		itemDAO.update(item);
	}

	private void moveItem(IModelTagable payload) {
		editItem(payload);
	}

	private void newProduct(IModelTagable payload) {
		productDAO.create((IProduct) payload);
	}

	private void insertProduct(IModelTagable payload) {
		ObservableArgs<IModelTagable> args = (ObservableArgs<IModelTagable>) payload;
		IProduct product = (IProduct) args.get(0);
		IProductContainer target = (IProductContainer) args.get(1);
		productContainerDAO.addProductToProductContainer(product, target);
	}

	private void editProduct(IModelTagable payload) {
		productDAO.update((IProduct) payload);
	}

	private void transferProduct(IModelTagable payload) {
		ObservableArgs<IModelTagable> args = (ObservableArgs<IModelTagable>) payload;
		IProduct product = (IProduct) args.get(0);
		IProductContainer target = (IProductContainer) args.get(1);
		IProductContainer origin = (IProductContainer) args.get(2);
		for(IItem item : target.getItems(product)) {
			itemDAO.update(item);
		}
		productContainerDAO.removeProductFromProductContainer(product, origin);
		productContainerDAO.addProductToProductContainer(product, target);
	}

	//TODO: Code duplication
	@SuppressWarnings("unchecked")
	private Pair<ModelActions, IModelTagable> pairExtract(Object arg1) {
		return (Pair<ModelActions, IModelTagable>) arg1;
	}

}
