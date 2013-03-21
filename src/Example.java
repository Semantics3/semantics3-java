import com.semantics3.api.Products;

public class Example {

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		Products prod = new Products(
				"",
				""
			);
	
		prod.field( "cat_id", 4992 )
			.field( "brand", "Toshiba" )
			.field( "weight", "gte", 1000000 )
			.field( "weight", "lt", 1500000 )
			.siteDetails(  "name",         "newegg.com" )
			.latestOffers( "currency",     "USD" )
			.latestOffers( "price", "gte", 100 );
		//sreq.add("products", "sitedetails", "latestoffers", "price", "gte", 100 );
		//sreq.remove("products", "sitedetails", "latestoffers", "price", "gte");
		System.out.println(prod.get());
	}

}
