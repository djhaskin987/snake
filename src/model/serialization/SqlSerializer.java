package model.serialization;


import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.tuple.Pair;

import model.Barcode;
import model.IItem;
import model.IModelTagable;
import model.IProduct;
import model.IProductContainer;
import model.ItemCollection;
import model.Model;
import model.ModelActions;
import model.NonEmptyString;
import model.ObservableArgs;
import model.ProductCollection;
import model.StorageUnit;
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
	
	private StorageUnits storageUnits;
	private Map<Pair<String, String>, IProductContainer> productContainers;
	private ProductCollection products;
	private ItemCollection items;
	/**
	 * Creates a new SqlSerializer object
	 * 
	 * {@pre none}
	 * 
	 * {@post an SqlSerializer object}
	 */
	public SqlSerializer() {
		init();

	}
	
	// region initializers
	
	private void init() {
		initConn();
		initDAOs();
	}
	
	private void initConn() {
		conn = new JDBCWrapper();
	}
	
	private void initDAOs() {
		rdao = new RootDAO(conn);
		productContainerDAO = new ProductContainerDAO(conn);
		productDAO = new ProductDAO(conn);
		itemDAO = new ItemDAO(conn);
	}
	
	// end region

	/* (non-Javadoc)
	 * @see model.serialization.ISerializer#load(model.Model)
	 */
	@Override
	public void load(Model model) {
		storageUnits = getRoot(model);
		createObjects();
		linkObjects();
		model.setStorageUnits(storageUnits);
		model.setProductCollection(products);
		model.setItemCollection(items);
		model.addObserver(this);
	}
	
	private void createObjects() {
		productContainers = productContainerDAO.getMap();
		for(IProduct product : productDAO.readAll()) {
			products.add(product);
		}
		for(IItem item : itemDAO.readAll()) {
			items.add(item);
		}
	}
	
	private void linkObjects() {
		for(Pair<String, String> key : productContainers.keySet()) {
			IProductContainer container = productContainers.get(key);
			if(container instanceof StorageUnit) {
				storageUnits.addStorageUnit((StorageUnit) container);
			} else {
				IProductContainer parent = productContainers.get(
						productContainerDAO.getParent(key));
				parent.addProductContainer(container);
				container.setParent(parent);
				for(String barcode : productContainerDAO.getProducts(container)) {
					IProduct product = products.getProduct(new NonEmptyString(barcode));
					container.addProduct(product);
					product.addProductContainer(container);
				}
			}
		}
		for(String itemBarcode : items.getBarcodes()) {
			IItem item = items.getItem(itemBarcode);
			String productBarcode = itemDAO.getProductBarcode(new Barcode(
					itemBarcode)).getBarcode();
			IProduct product = products.getProduct(new NonEmptyString(productBarcode));
			item.setProduct(product);
			
			if(item.getExitTime() != null) {
				String containerName = itemDAO.getProductContainerName(
						new Barcode(itemBarcode));
				String unitName = itemDAO.getStorageUnitName(new Barcode(
						itemBarcode));
				IProductContainer container = productContainers.get(
						Pair.of(containerName, unitName));
				container.add(item);
				item.setProductContainer(container);
			}
		}
	}
	
	private StorageUnits getRoot(Model model) {
		StorageUnits su = null;
		List<StorageUnits> l = rdao.readAll();
		if (l.size() > 0) {
			su = l.get(0);
		} else {
			su = model.getStorageUnits();
			rdao.create(su);
		}
		return su;
	}

	/* (non-Javadoc)
	 * @see model.serialization.ISerializer#save(model.Model)
	 */
	@Override
	public void save(Model model) {
		Model m = Model.getInstance();
		StorageUnits su = m.getStorageUnits();
		rdao.update(su);
	}


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
