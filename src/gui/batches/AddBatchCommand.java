package gui.batches;

import java.util.List;

import model.IItem;
import model.IProduct;
import model.IProductContainer;
import model.Model;
import model.ObservableArgs;

import gui.product.ProductData;
import gui.inventory.ProductContainerData;
import gui.item.ItemData;

public class AddBatchCommand implements ICommand {
	private ProductData product;
	private List<ItemData> items;
	private ProductItemsData productItems;
	private IProductContainer productContainer;
	private ObservableArgs<IItem> batch;
	private AddItemBatchController controller;
	private AddBatchCase addBatchCase;
	
	/**
	 * @param product
	 * @param items
	 * @param productItems
	 * @param productContainerData
	 * @param controller
	 * 
	 * Constructs a command to add items of product to productContainerData, with controller being the AddItemBatchController creating the command and productItems being its list of products and items.
	 */
	public AddBatchCommand(ProductData product, List<ItemData> items, ProductItemsData productItems, ProductContainerData productContainerData, AddItemBatchController controller) {
		this.product = product;
		this.items = items;
		this.productItems = productItems;
		this.controller = controller;
		productContainer = (IProductContainer) productContainerData.getTag();
		batch = new ObservableArgs<IItem>();
		if(productContainer.contains((IProduct) product.getTag())) {
			addBatchCase = AddBatchCase.NORMAL;
			//This line could be optimized.
		} else if(((IProduct) product.getTag()).getAllItems().size() == 0) {
			addBatchCase = AddBatchCase.ADD_PRODUCT_TO_SYSTEM;
		} else {
			addBatchCase = AddBatchCase.ADD_PRODUCT_TO_STORAGE_UNIT;
		}
		for(ItemData item : items) {
			batch.add((IItem) item.getTag());
		}
		batch.setTag(items);
	}

	@Override
	public void execute() {
		productItems.addItemDatas(product, items);
		
		Model m = Model.getInstance();
		
		//This will already be there on the first run through, but it's adding it to a map,
		//so it just overwrites iteself instead of being duplicated.
		m.getProductCollection().add((IProduct) product.getTag());
		
		m.addBatch(batch, productContainer);
		
		IAddItemBatchView view = controller.getView();
		if(view.getSelectedProduct() == product) {
			controller.refreshItems();
		}
		controller.refreshProducts();
		controller.resetControls();
		controller.enableComponents();
	}

	@Override
	public void undo() {
		productItems.removeItemDatas(product, items);
		
		Model m = Model.getInstance();
		switch(addBatchCase) {
		case ADD_PRODUCT_TO_STORAGE_UNIT:
			m.unaddProductAndBatch(batch, productContainer);
			break;
		case ADD_PRODUCT_TO_SYSTEM:
			m.unaddNewProductAndBatch(batch, productContainer);
			break;
		case NORMAL:
			m.unaddBatch(batch, productContainer);
			break;
		default:
			System.err.println("You missed a case in AddBachCommand.undo()");
			break;
		}
		
		IAddItemBatchView view = controller.getView();
		if(view.getSelectedProduct() == product) {
			controller.refreshItems();
		}
		controller.refreshProducts();
		
		controller.resetControls();
		controller.enableComponents();
	}

}
