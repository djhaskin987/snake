package gui.batches;

import gui.item.ItemData;
import gui.product.ProductData;

import java.util.List;
import java.util.Map;

/** Command used in undo/redo which adds an item in the
 * "Add item batch" view.
 * @author Daniel Jay Haskin
 *
 */
public class AddItemBatchCommand implements ICommand {
	private ItemData item;
	private Map<ProductData, List<ItemData>> displayList;
	
	public AddItemBatchCommand(ProductData product,
			ItemData item,
			Map<ProductData, List<ItemData>> displayList)
	{
		
	}
	/** Adds the given item/product to the display map -AND-
	 * to the model.
	 * @param none
	 * {@pre none}
	 * {@post the item is added to the display list and to the model.} 
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}
	/** Adds the given item/product to the display map -AND-
	 * to the model.
	 * @param none
	 * {@pre the item is added to the display list and to the model.}
	 * {@post the item is un-added to the display list and to the model.} 
	 */
	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
