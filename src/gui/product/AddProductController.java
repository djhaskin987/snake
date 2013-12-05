package gui.product;

import org.apache.commons.lang3.math.NumberUtils;

import model.Barcode;
import model.IProduct;
import model.Model;
import model.Quantity;
import model.Unit;
import gui.common.*;

/**
 * Controller class for the add item view.
 */
public class AddProductController extends Controller implements
		IAddProductController, Runnable {
	
	String barcode;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add product view
	 * @param barcode Barcode for the product being added
	 */
	public AddProductController(IView view, String barcode) {
		super(view);
		this.barcode = barcode;
		construct();
	}

	//
	// Controller overrides
	//
	
	/**
	 * Returns a reference to the view for this controller.
	 * 
	 * {@pre None}
	 * 
	 * {@post Returns a reference to the view for this controller.}
	 */
	@Override
	protected IAddProductView getView() {
		return (IAddProductView)super.getView();
	}

	/**
	 * Sets the enable/disable state of all components in the controller's view.
	 * A component should be enabled only if the user is currently
	 * allowed to interact with that component.
	 * 
	 * {@pre None}
	 * 
	 * {@post The enable/disable state of all components in the controller's view
	 * have been set appropriately.}
	 */
	@Override
	protected void enableComponents() {
		if(getView().getSizeUnit() == SizeUnits.Count) {
			getView().setSizeValue("1");
			getView().enableSizeValue(false);
		} else {
			getView().enableSizeValue(true);
		}
		if(
				!getView().getBarcode().isEmpty()
				&& !getView().getDescription().isEmpty()
				&& NumberUtils.isNumber(getView().getSizeValue())
				&& Double.parseDouble(getView().getSizeValue()) > 0
				&& NumberUtils.isDigits(getView().getShelfLife())
				&& Integer.parseInt(getView().getShelfLife()) >= 0
				&& NumberUtils.isDigits(getView().getSupply())
				&& Integer.parseInt(getView().getSupply()) >= 0
				) {
			getView().enableOK(true);
		} else {
			getView().enableOK(false);
		}
	}

	/**
	 * Loads data into the controller's view.
	 * 
	 *  {@pre None}
	 *  
	 *  {@post The controller has loaded data into its view}
	 */
	@Override
	protected void loadValues() {
		enableComponents();
		getView().setShelfLife("0");
		getView().setSupply("0");
		getView().enableBarcode(false);
		getView().enableDescription(false);
		getView().enableOK(false);
		getView().setBarcode(barcode);
		getView().setDescription("Identifying Product – Please Wait");
		new Thread(this).start();
		//Identifies the product in another thread to allow the gui to update.
		//TODO: Is the barcode ever enabled?
		
	}

	/**
	 * Identifies the product. This has its own method because
	 * it must be done in a seperate thread. This should only be called from Thread.
	 */
	@Override
	public void run() {
		String description = Model.getInstance().getProductIdentifier().getProduct(barcode);
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		getView().setDescription(description);
		getView().enableDescription(true);
		this.enableComponents();
	}

	//
	// IAddProductController overrides
	//
	
	/**
	 * This method is called when any of the fields in the
	 * add product view is changed by the user.
	 * 
	 * This will set the activity of the "OK" button, depending
	 * on whether the field is valid or not.
	 * 
	 * {@pre None}
	 * 
	 * {@post activity of "OK" button altered}
	 */
	@Override
	public void valuesChanged() {
		enableComponents();
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the add product view.
	 * 
	 * Adds Product to products and refreshes list.
	 * 
	 * {@pre None}
	 * 
	 * {@post Product is added}
	 */
	@Override
	public void addProduct() {
		/*ProductData productData = new ProductData();
		productData.setBarcode(getView().getBarcode());
		productData.setDescription(getView().getDescription());
		productData.setSize(getView().getSizeValue() + " " + getView().getSizeUnit());*/
		
		String barcode = getView().getBarcode();
		String description = getView().getDescription();
		double sizeValue = Double.parseDouble(getView().getSizeValue());
		Unit sizeUnit = SizeUnitsUnitConversion.sizeUnitsToUnit(getView().getSizeUnit());
		Quantity itemSize = new Quantity(sizeValue, sizeUnit);
		int shelfLife = Integer.parseInt(getView().getShelfLife());
		int threeMonthSupply = Integer.parseInt(getView().getSupply());
		IProduct product = Model.getInstance().createProduct(
				barcode, description, itemSize, shelfLife, threeMonthSupply);
		
		ProductData productData = new ProductData();
		productData.setBarcode(barcode);
		productData.setDescription(description);
		productData.setSize(itemSize.toString());
		productData.setShelfLife(Integer.toString(shelfLife));
		productData.setSupply(Integer.toString(threeMonthSupply));
		
		productData.setTag(product);
		product.setTag(productData);
		
		Model.getInstance().addProduct(product);
	}

}

