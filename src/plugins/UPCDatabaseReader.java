package plugins;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

import model.productidentifier.AbstractProductIdentifier;

public class UPCDatabaseReader extends AbstractProductIdentifier {
	public UPCDatabaseReader() {}
	
	protected String _getProduct(String barcode) {
		try {
		URL oracle = new URL("http://www.upcdatabase.com/item/" + barcode);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(oracle.openStream()));
		String inputLine;
		StringBuilder input = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			input.append(inputLine);
		}
		in.close();
		Pattern regex = Pattern.compile("\\<td\\>Description\\</td\\>.*?\\<td\\>([^<]+?)\\</td\\>");
		Matcher matcher = regex.matcher(input);
		if(matcher.find()) {
			return(matcher.toMatchResult().group(1));
		} else {
			return null;
		}
		} catch(Exception e) {
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(new UPCDatabaseReader()._getProduct("0028572200215"));
	}
}