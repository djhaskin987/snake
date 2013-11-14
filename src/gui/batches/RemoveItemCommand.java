package gui.batches;

import java.util.List;

import model.IItem;
import model.IProduct;
import model.IProductContainer;
import model.Model;
import gui.common.ICommand;
import gui.item.ItemData;
import gui.product.ProductData;

public class RemoveItemCommand implements ICommand {
	ProductItemsData productItems;
	ItemData item;
	ProductData product;
	IProductContainer container;
	RemoveItemBatchController controller;
	int position;

	public RemoveItemCommand(IItem modelItem, ProductItemsData productItems, RemoveItemBatchController controller) {
		item = (ItemData) modelItem.getTag();
		product = (ProductData) modelItem.getProduct().getTag();
		this.productItems = productItems;
		container = modelItem.getProductContainer();
		this.controller = controller;
		position = Model.getInstance().getPosition(modelItem);
	}

	@Override
	public void execute() {
		productItems.addItemData(product, item);
		Model m = Model.getInstance();
		m.removeItem((IItem) item.getTag());
		IRemoveItemBatchView v = controller.getView();
		v.setBarcode("");
		v.giveBarcodeFocus();
		if(v.getSelectedProduct() == product) {
			controller.refreshItems();
		}
		controller.refreshProducts();
	}

	@Override
	public void undo() {
		productItems.removeItemData(product, item);
		IRemoveItemBatchView v = controller.getView();
		Model m = Model.getInstance();
		m.unremoveItem((IItem) item.getTag(), container, position);
		v.setBarcode("");
		v.giveBarcodeFocus();
		if(!productItems.contains(product)) {
			v.setProducts(productItems.getProductArray());
		}
		if(v.getSelectedProduct() == product) {
			controller.refreshItems();
		}
		controller.refreshProducts();
	}

}
