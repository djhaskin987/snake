package gui.batches;

import model.IItem;
import model.IProductContainer;
import model.Model;
import gui.item.ItemData;
import gui.product.ProductData;

public class RemoveItemCommand implements ICommand {
	ProductItemsData productItems;
	ItemData item;
	ProductData product;
	IProductContainer container;
	RemoveItemBatchController controller;
	int position;

	/**
	 * @param modelItem		IItem to be removed
	 * @param productItems	ProductItemsData that keeps track of which items have been removed
	 * @param controller	RemoveItemBatchController that constructed this command
	 * 
	 * Constructs a command to remove modelItem
	 */
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
