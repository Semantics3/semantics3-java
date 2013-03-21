import org.json.JSONObject;

import com.semantics3.api.Products;

public class Example {

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		Products products = new Products(
				"",
				""
			);
		products
			.field( "cat_id", 4992 )	
			.field( "brand", "Toshiba" );
		JSONObject results = products.get();
		System.out.println(results.toString(4));
	}

}
