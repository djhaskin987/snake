package gui.batches;

import java.util.List;

import model.IItem;
import model.IProduct;
import model.IProductContainer;
import model.Model;
import model.ObservableArgs;
import model.ProductContainer;

import com.sun.org.apache.xpath.internal.operations.Mod;

import gui.common.ICommand;
import gui.product.ProductData;
import gui.inventory.ProductContainerData;
import gui.item.ItemData;

public class AddBatchCommand implements ICommand {
	private ProductData product;
	private List<ItemData> items;
	private ProductItemsData productItems;
	private IProductContainer productContainer;
	private ObservableArgs<IItem> batch;
	private int count;
	private AddItemBatchController controller;
	private AddBatchCase addBatchCase;
	
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
		count = productItems.getCount(product);
	}

	@Override
	public void execute() {
		productItems.addItemDatas(product, items);
		//product.setCount(Integer.toString(count+items.size()));
		
		Model m = Model.getInstance();
		m.addBatch(batch, productContainer);
		
		IAddItemBatchView view = controller.getView();
		view.setProducts(productItems.getProductArray());
		view.selectProduct(product);
		controller.selectedProductChanged();
		view.selectItem(items.get(items.size()-1));
		
		controller.resetControls();
		controller.enableComponents();
	}

	@Override
	public void undo() {
		productItems.removeItemDatas(product, items);
		//product.setCount(Integer.toString(count));
		
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
		view.setProducts(productItems.getProductArray());
		if(addBatchCase == AddBatchCase.NORMAL) {
			view.selectProduct(product);
			view.selectItem(productItems.getLastItem(product));
		}
		controller.selectedProductChanged();
		
		controller.resetControls();
		controller.enableComponents();
	}

}
