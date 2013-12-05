package plugins;

import java.util.Map;

import com.factual.driver.Factual;
import com.factual.driver.Query;
import com.factual.driver.ReadResponse;

import model.productidentifier.AbstractProductIdentifier;

public class DanHaskinProductIdentifier extends AbstractProductIdentifier {
	private Factual factualConnection;

	public DanHaskinProductIdentifier() {
		factualConnection = new Factual(
				"yFtEd817Gy55rmr508fYczhVQoff0GngVAY5Orji",
				"k1z3e19c3opMpEDysMYURMSZPYDcdBlCmBWqEdg7");
	}

	@Override
	public String _getProduct(String barcode) {
		try {
			ReadResponse read = factualConnection.fetch("products-cpg",
					new Query().field("upc")
						       .isEqual(barcode));
			Map<String, Object> record = read.first();
			if (record == null)
			{
				return null;
			}
			Object result =  record.get("product_name");
			if (result == null)
			{
				return null;
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String [] args) {
		DanHaskinProductIdentifier ident = new DanHaskinProductIdentifier();
		String product = ident._getProduct("049000006582");
		String otherProduct = ident._getProduct("052000131512");
		String poppycock = ident._getProduct("whodj");
		System.out.println("049000006582" + " = " + product);
		System.out.println("052000131512" + " = " + otherProduct);
		System.out.println("Bogus: " + poppycock);
	}
}
