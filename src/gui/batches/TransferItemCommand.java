package gui.batches;

import gui.inventory.ProductContainerData;
import gui.item.ItemData;
import gui.product.ProductData;

import java.util.List;
import java.util.Map;

/** Command used in undo/redo which transfers an item in the
 * "Transfer Items Batch" view.
 * @author Daniel Jay Haskin
 *
 */
public class TransferItemCommand implements ICommand {
	private ItemData item;
	private Map<ProductData, List<ItemData>> displayList;
	
	public TransferItemCommand(ProductContainerData storageUnitA,
			ProductContainerData storageUnitB,
			ProductData product,
			ItemData item,
			Map<ProductData, List<ItemData>> displayList)
	{
		
	}
	/** Command associated with the transfer item batch command view.
	 * 
	 * @param none
	 * {@pre none}
	 * {@post adds the given item/product to the display map -AND-
	 * transfers it from one storage unit to another in the model.} 
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}
	/** Adds the given item/product to the display map -AND-
	 * transferrs it in the model.
	 * @param none
	 * {@pre product/item has already been transferred. 
	 * {@post the item is un-added to the display list and un-added to the model.} 
	 */
	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}
}
