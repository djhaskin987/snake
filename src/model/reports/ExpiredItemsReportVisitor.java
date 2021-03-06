package model.reports;

import model.Date;
import model.IProductContainer;
import model.Item;
import model.Model;
import model.Product;
import model.ProductGroup;
import model.RemovedItems;
import model.StorageUnit;
import model.StorageUnits;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;


/**
 * used for the expired items report
 * 
 * @author nstandif
 *
 */
public class ExpiredItemsReportVisitor implements ReportVisitor {
	private ReportBuilder reportBuilder;
	private ArrayList<TreeSet<Item>> items;

	public ExpiredItemsReportVisitor(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
		items = new ArrayList<TreeSet<Item>>();
	}

	@Override
	public void visit(StorageUnits storageUnits) {
		//do nothing
	}

	@Override
	public void visit(StorageUnit storageUnit) {
		visitPC(storageUnit);
	}

	@Override
	public void visit(ProductGroup productGroup) {
		visitPC(productGroup);
	}
	
	private void visitPC(IProductContainer productContainer) {
		items.add(new TreeSet<Item>(new ItemComparator()));
	}

	@Override
	public void visit(Item item) {
		if(item.getExpireDate() != null && item.getExpireDate().compareTo(new Date()) <= 0) {
			items.get(items.size()-1).add(item);
		}
	}

	@Override
	public void visit(RemovedItems removedItems) {
		//do nothing
	}

	@Override
	public void visit(Product product) {
		//do nothing
	}

	@Override
	public void display() {
		reportBuilder.buildHeading("Expired Items Report");
		reportBuilder.buildTable(compileTable());
		reportBuilder.display();
	}

	public class ItemComparator implements Comparator<Item> {

		@Override
		public int compare(Item o1, Item o2) {
			int out = o1.getProduct().getDescription().getValue().compareTo(
					o2.getProduct().getDescription().getValue());
			if(out != 0) {
				return out;
			}
			out = o1.getEntryDate().compareTo(o2.getEntryDate());
			if(out != 0) {
				return out;
			}
			return Model.getInstance().getPosition(o1)
					- Model.getInstance().getPosition(o2);
		}
		
	}

	private String[][] compileTable() {
		int size = 0;
		for(TreeSet<Item> temp : items) {
			size += temp.size();
		}
		String[][] table = new String[size+1][];
		table[0] = new String[] {"Description", "Storage Unit", "Product Group",
				"Entry Date", "Expire Date"};
		int i=1;
		for(TreeSet<Item> temp : items) {
			for(Item item : temp) {
				String productGroup = item.getProductGroupName();
				if(productGroup == null) {
					productGroup = "";
				}
				table[i] = new String[] {
						item.getProduct().getDescription().getValue(),
						item.getStorageUnitName(),
						productGroup,
						item.getEntryDateString(),
						item.getExpireDateString()
				};
				++i;
			}
		}
		return table;
	}

}
