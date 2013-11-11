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
	private boolean newProduct;
	
	public AddBatchCommand(ProductData product, List<ItemData> items, ProductItemsData productItems, ProductContainerData productContainerData, AddItemBatchController controller) {
		this.product = product;
		this.items = items;
		this.productItems = productItems;
		this.controller = controller;
		productContainer = (IProductContainer) productContainerData.getTag();
		batch = new ObservableArgs<IItem>();
		newProduct = !productContainer.contains((IProduct) product.getTag());
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
		if(newProduct) {
			m.unaddProductAndBatch(batch, productContainer);
		} else {
			m.unaddBatch(batch, productContainer);
		}
		
		IAddItemBatchView view = controller.getView();
		view.setProducts(productItems.getProductArray());
		if(!newProduct) {
			view.selectProduct(product);
			view.selectItem(productItems.getLastItem(product));
		}
		controller.selectedProductChanged();
		
		controller.resetControls();
		controller.enableComponents();
	}

}
