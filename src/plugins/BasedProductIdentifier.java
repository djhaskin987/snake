package plugins;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.productidentifier.AbstractProductIdentifier;

public class BasedProductIdentifier extends AbstractProductIdentifier {

	public BasedProductIdentifier() {
	}

	@Override
	public String _getProduct(String barcode) {
		try {
			URL src = getUrl(barcode);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(src.openStream());
			NodeList nodes = doc.getElementsByTagName("product");
			Node n = nodes.item(1);
			String productName = n.getTextContent();
			return productName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private URL getUrl(String barcode) throws MalformedURLException {
		String urlStr = String.format("http://eandata.com/feed/?v=3&keycode=419BA3F5529E2EE2&mode=xml&find=%s&get=product", barcode);
		URL src = new URL(urlStr);
		return src;
	}
	
	public static void main(String [] args) {
		BasedProductIdentifier ident = new BasedProductIdentifier();
		String product = ident._getProduct("0049000006582");
		System.out.println("0049000006582" + "=" + product);
	}
}
