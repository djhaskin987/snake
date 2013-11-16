package model.reports;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import model.IProduct;
import model.Item;
import model.Product;
import model.ProductGroup;
import model.StorageUnit;
import model.StorageUnits;
import model.Unit;


/**
 * used for the 3 month supply report
 * 
 * @author nstandif
 *
 */
public class SupplyReportVisitor implements ReportVisitor {
	private ReportBuilder rb;
	private int months;
	private List<Pair<ProductGroup,StorageUnit>> productGroups;
	private Map<Product,Set<Item>> products;
	private boolean productTableCompiled = false;

	public void init()
	{
		products = new HashMap<Product, Set<Item>>();
		productGroups = new LinkedList<Pair<ProductGroup,StorageUnit>>();
	}
	
	public SupplyReportVisitor() {
		init();
	}

	public SupplyReportVisitor(ReportBuilder rb, int months) {
		init();
		this.rb = rb;
		this.months = months;
	}

	@Override
	public void visit(StorageUnits storageUnits) {
		System.out.println("visited storage unit root " + storageUnits.getName().toString());
	}

	@Override
	public void visit(StorageUnit storageUnit) {
		System.out.println("visited storage unit" + storageUnit.getName().toString());
	}

	@Override
	public void visit(ProductGroup pg) {
		System.out.println("visited storage product group " + pg.getName().toString());
		StorageUnit s = (StorageUnit)pg.getUnitPC();
		Pair<ProductGroup, StorageUnit> a;
		
		productGroups.add(Pair.of(pg,s));
	}

	@Override
	public void visit(Item val) {
		System.out.println("visited item " + val.getBarcode().toString());
		if (val.getExitTime() != null) {
			Product key = (Product)val.getProduct();
			if (!products.containsKey(key))
			{
				products.put(key, new HashSet<Item>());
			}
			products.get(key).add(val);
		}
		else
		{
			System.out.println("Item has been removed");
		}
	}

	@Override
	public void visit(Product product) {
		System.out.println("Visited product " + product.getDescription().toString());
	}

	@Override
	public void display() {
		rb.buildHeading("" + months + "-Month Supply Report");
		rb.buildSubHeading("Products");
		rb.buildTable(productTable());
		rb.buildSubHeading("Product Groups");
		rb.buildTable(productGroupTable());
		rb.display();
		
	}
	
	private String[][] productGroupTable() {
		String [][] returned = new String[1][4];
		returned[0][0] = "A";
		returned[0][1] = "B";
		returned[0][2] = "C";
		returned[0][3] = "D";
		return returned;
	}

	private String [][] productTable()
	{
		List<String []> rows = new LinkedList<String []>();
		String [] heading =
				{"Description","Barcode","" + months + "-Month Supply", 
			"Current Supply"};
		// don't add heading yet, we need to sort
		
		for (Product p : products.keySet())
		{
			int nMonthSupply = (p.getThreeMonthSupply() * months) / 3;
			int numItems = products.get(p).size();
			if (numItems < nMonthSupply)
			{
				double isize = p.getItemSize().getValue();
				String supplyString = "" + (isize * nMonthSupply) + " " +
						p.getItemSize().getUnit().toString();
				String currentSupplyString = "" + (isize * numItems) + " " +
						p.getItemSize().getUnit().toString();
				String [] row = new String[4];
				row[0] = p.getDescription().toString();
				row[1] = p.getBarcode().toString();
				row[2] = supplyString;
				row[3] = currentSupplyString;
				rows.add(row);
			}
		}
			
		java.util.Collections.sort(rows, new Comparator<String []>() {
			public int compare(String[] a, String [] b)
			{
				return a[0].compareTo(b[0]);
			}
		});
		// OK, NOW add the heading
		rows.add(0, heading);
		
		String [][] returned = new String[rows.size()][4];
		int i = 0;
		for (String [] row : rows)
		{
			for (int j = 0; j < 4; j++)
			{
				returned[i][j] = row[j];
			}
			i++;
		}
		productTableCompiled = true;
		return returned;
	}
	
	private String [][] compileTable()
	{
		if (productTableCompiled)
		{
		}
		else
		{
			List<String []> rows = new LinkedList<String []>();
			String [] heading =
					{"Product Group","Storage Unit","" + months + "-Month Supply", 
				"Current Supply"};
			rows.add(heading);
			for (Pair<ProductGroup,StorageUnit> p : productGroups)
			{
				ProductGroup pg = p.getLeft();
				StorageUnit s = p.getRight();
				Unit pgUnit = pg.getThreeMonthSupplyQuantity().getUnit();
				double nMonthSupply = pg.getThreeMonthSupplyQuantity().getValue() * months / 3.0;
				if (nMonthSupply > 0.0)
				{
					double currentSupply = 0.0;
					Set<IProduct> productSet = new HashSet<IProduct>();
					productSet.addAll(pg.getProducts());
					productSet.addAll(pg.getProductsRecursive());
					for (IProduct pd : productSet)
					{
						Product product = (Product) pd;
						if (pgUnit.canConvert(
								product.getItemSize().getUnit()))
						{
							double itemQuantity = 0.0;
							if (products.containsKey(product))
							{
								int itemCount = products.get(product).size();
								itemQuantity += (product.getItemSize().getValue() *
										itemCount);
							}
							try {
								currentSupply += pgUnit.convert(itemQuantity, 
										product.getItemSize().getUnit());
							} catch (Exception e) {
								e.printStackTrace();
								System.exit(1);
							}
						}
					}
					if (currentSupply < nMonthSupply)
					{
						String [] row = new String[4];
						row[0] = "" + pg.getName();
						row[1] = "" + s.getName();
						row[2] = "" + String.format("%.2f", nMonthSupply) + 
								" " + pgUnit.toString();
						row[3] = "" + String.format("%.2f", currentSupply) + 
								" " + pgUnit.toString();
						rows.add(row);
					}
				}
			}
		}
		return null;
	}
}