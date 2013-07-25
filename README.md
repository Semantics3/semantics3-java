# semantics3
semantics3-java is a Java client for accessing the Semantics3 Products API, which provides structured information, including pricing histories, for a large number of products.
See https://www.semantics3.com for more information.

## Requirements
* [signpost-core-1.2.1.2.jar](https://oauth-signpost.googlecode.com/files/signpost-core-1.2.1.2.jar) - OAuth2 implementation
  
* [commons-codec-1.7.jar](http://repo1.maven.org/maven2/commons-codec/commons-codec/1.7/commons-codec-1.7.jar) - Apache Commons Codec library for Base64 implementation

## Getting Started

In order to use the client, you must have both an API key and an API secret. To obtain your key and secret, you need to first create an account at
https://www.semantics3.com/
You can access your API access credentials from the user dashboard at https://www.semantics3.com/dashboard/applications

### Setup Work

Let's lay the groundwork.

```java
import com.semantics3.api.Products;

/* Set up a client to talk to the Semantics3 API using your Semantics3 API Credentials */
Products products = new Products(
	"SEM3xxxxxxxxxxxxxxxxxxxxxx",
	"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
);
```

### First Query aka 'Hello World':

Let's make our first query! For this query, we are going to search for all Toshiba products that fall under the category of "Computers and Accessories", whose cat_id is 4992. 

```java
/* Build the query */
products
    .productsField( "cat_id", 4992 )
    .productsField( "brand", "Toshiba" );

/* Make the query */
JSONObject results = products.getProducts();
/* or */
results = products.get();

/* View the results of the query */
System.out.println(results);
```

## Examples

The following examples show you how to interface with some of the core functionality of the Semantics3 Products API. For more detailed examples check out the Quickstart guide: https://www.semantics3.com/quickstart

### Explore the Category Tree

In this example we are going to be accessing the categories endpoint. We are going to be specifically exploiring the "Computers and Accessories" category, which has a cat_id of 4992. For more details regarding our category tree and associated cat_ids check out our API docs at https://www.semantics3.com/docs

```java
/* Build the query */
products.categoriesField( "cat_id", 4992 );

/* Execute the query */
JSONObject results = products.getCategories();

/* View the results of the query */
System.out.println(results);
```

### Nested Search Query

You can intuitively construct all your complex queries but just repeatedly using the products_field() or add() methods.
Here is how we translate the following JSON query:

```javascript
{
	"cat_id" : 4992, 
	"brand"  : "Toshiba",
	"weight" : { "gte":1000000, "lt":1500000 },
	"sitedetails" : {
		"name" : "newegg.com",
		"latestoffers" : {
			"currency": "USD",
			"price"   : { "gte" : 100 } 
		}
	}
}
```


This query returns all Toshiba products within a certain weight range narrowed down to just those that retailed recently on newegg.com for >= USD 100.

```java
/* Build the query */
Products products = new Products( api_key, api_secret );
products
	.field("cat_id", 4992)
	.field("brand", "Toshiba")
	.field("weight", "gte", 1000000)
	.field("weight", "lt", 1500000)
	.siteDetails("name","newegg.com")
	.latestOffers("currency","USD")
	.latestOffers("price","gte",100);
	
/* Let's make a modification - say we no longer want the weight attribute */
products.remove( "products", "weight" );

/* Make the query */
JSONObject results = products.getProducts();
System.out.println(results);
```



### Explore Price Histories
For this example, we are going to look at a particular product that is sold by select merchants and has a price of >= USD 30 and seen after a specific date (specified as a UNIX timestamp).

```java
/* Build the query */
products.offersField("sem3_id", "4znupRCkN6w2Q4Ke4s6sUC");
products.offersField("seller", new String[] { "LFleurs","Frys","Walmart" });
products.offersField("currency", "USD");
products.offersField("price", "gte", 30);
products.offersField("lastrecorded_at", "gte", 1348654600);

/* Make the query */
JSONObject results = products.getOffers()

/* View the results of the query */
System.out.println(results)
```



## Contributing
Use GitHub's standard fork/commit/pull-request cycle.  If you have any questions, email <support@semantics3.com>.

## Author

* Shawn Tan <shawn@semantics3.com>

## Copyright

Copyright (c) 2013 Semantics3 Inc.

## License

    The "MIT" License
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
    OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
    THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
    FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
    DEALINGS IN THE SOFTWARE.


