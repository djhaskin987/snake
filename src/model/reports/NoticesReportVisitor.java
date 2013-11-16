package model.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang3.tuple.Pair;

import model.IProduct;
import model.Item;
import model.Product;
import model.ProductGroup;
import model.Quantity;
import model.StorageUnit;
import model.StorageUnits;

/**
 * Used for the notices report
 * 
 * @author nstandif
 *
 */
public class NoticesReportVisitor implements ReportVisitor {
	private ReportBuilder builder;
	private ProductGroup productGroup;
	private List<Pair<ProductGroup, TreeSet<IProduct>>> products;

	public NoticesReportVisitor(ReportBuilder b) {
		builder = b;
		products = new ArrayList<Pair<ProductGroup, TreeSet<IProduct>>>();
		productGroup = null;
	}

	@Override
	public void visit(StorageUnits storageUnits) {
		productGroup = null;
	}

	@Override
	public void visit(StorageUnit storageUnit) {
		productGroup = null;
	}

	@Override
	public void visit(ProductGroup productGroup) {
		if(productGroup.getThreeMonthSupplyQuantity().getValue() == 0) {
			this.productGroup = null;
		} else {
			this.productGroup = productGroup;
			products.add(Pair.of(productGroup, new TreeSet<IProduct>()));
		}
	}

	@Override
	public void visit(Item item) {
		//do nothing
	}

	@Override
	public void visit(Product product) {
		if(productGroup == null) {
			return;
		}
		//System.out.println("ProductGroup: " + productGroup.getName().getValue());
		Quantity size = product.getItemSize();
		if(size == null) {
			return;
		}
		if(productGroup.getThreeMonthSupplyUnit().canConvert(size.getUnit())) {
			return;
		}
		products.get(products.size()-1).getRight().add(product);
	}

	@Override
	public void display() {
		builder.buildHeading("Notices");
		if(products.isEmpty()) {
			builder.buildParagraph("There is nothing to report.");
		} else {
			builder.buildSubHeading("3-Month Supply Warnings");
			builder.buildEmptyLine();
			for(Pair<ProductGroup, TreeSet<IProduct>> pair : products) {
				StringBuilder paragraph = new StringBuilder();
				if(pair.getRight().size() == 0) {
					continue;
				}
				paragraph.append("Product group ");
				paragraph.append(pair.getLeft().getUnitPC().getName().getValue());
				paragraph.append("::");
				paragraph.append(pair.getLeft().getName().getValue());
				paragraph.append(" has a 3-month supply (");
				paragraph.append(pair.getLeft().getThreeMonthSupply());
				paragraph.append(") that is inconsistent with the following products:");
				builder.buildParagraph(paragraph.toString());
				for(IProduct product : pair.getRight()) {
					paragraph = new StringBuilder();
					paragraph.append("- ");
					paragraph.append(pair.getLeft().getName());
					paragraph.append("::");
					paragraph.append(product.getDescription().getValue());
					paragraph.append(" (size ");
					paragraph.append(product.getItemSize().getValueString());
					paragraph.append(")");
					builder.buildParagraph(paragraph.toString());
				}
				builder.buildEmptyLine();
			}
		}
		builder.display();
	}
}
