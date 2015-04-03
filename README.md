# semantics3-java
semantics3-java is a Java client for accessing the Semantics3 Products API, which provides structured information, including pricing histories, for a large number of products.
See https://www.semantics3.com for more information.

## Installation
* The JAVA version used for this library is JDK 7. 

* To build and install from the latest source:

```git clone git@github.com:Semantics3/semantics3-java.git```

## Requirements
* [signpost-core-1.2.1.2.jar](https://oauth-signpost.googlecode.com/files/signpost-core-1.2.1.2.jar) - OAuth2 implementation
  
* [commons-codec-1.7.jar](http://repo1.maven.org/maven2/commons-codec/commons-codec/1.7/commons-codec-1.7.jar) - Apache Commons Codec library for Base64 implementation

* JDK >= 6

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

### First Request aka 'Hello World':

Let's make our first Request! We are going to run a simple search fo the word "iPhone" as follows:

```java
/* Build the Request */
products
    .productsField( "search", "iphone" );

/* Make the Request */
JSONObject results = products.getProducts();
/* or */
results = products.get();

/* View the results of the Request */
System.out.println(results);
```

## Sample Requests

The following Requests show you how to interface with some of the core functionality of the Semantics3 Products API :

### UPC Query

Running a UPC/EAN/GTIN request is as simple as running a search request:

 ```java
/* Build the Request */
products
	.productsField( "upc", "883974958450" )
	.productsField( "fields", "name","gtins" );

/* Make the Request */
JSONObject results = products.getProducts();
/* or */
results = products.get();

/* View the results of the Request */
System.out.println(results);
```

### URL Query

Get the picture? You can run URL Requests as follows:

```java
products
	.productsField( "url", "http://www.walmart.com/ip/15833173" );
	JSONObject results = products.getProducts();
	System.out.println(results);
```

### Price Filter

Filter by price using the "lt" (less than) tag:

```java
products
	.productsField( "search", "iphone" )
	.productsField( "price", "lt", 300 );
	JSONObject results = products.getProducts();
	System.out.println(results);
```

### Category ID Query

To lookup details about a cat_id, run your request against the categories resource:

```java
/* Build the query */
products
	.categoriesField( "cat_id", 4992 );

/* Execute the query */
	JSONObject results = products.getCategories();

/* View the results of the query */
	System.out.println(results);
```

### Webhooks

You can use webhooks to get near-real-time price updates from Semantics3.

## Creating a webhook

You can register a webhook with Semantics3 by sending a POST request to ```"webhooks"``` endpoint. To verify that your URL is active, a GET request will be sent to your server with a ```verification_code``` parameter. Your server should respond with ```verification_code``` in the response body to complete the verification process.

```
    String apiKey = "SEM3xxxxxxxxxxxxxxxxxxxxxx";
    String apiSecret = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    String endpoint = "webhooks";
    Semantics3Request request = new Semantics3Request(apiKey, apiSecret, endpoint);
    HashMap params = new HashMap();
    params.put("webhook_uri", "http://mydomain.com/webhooks-callback-url");
    JSONObject results = request.runQuery(endpoint, "POST", params);
    System.out.println(results.toString(4));
```
To fetch existing webhooks

```  
    String endpoint = "webhooks";
    Semantics3Request request = new Semantics3Request(apiKey, apiSecret, endpoint);
    HashMap params = new HashMap();
	params.put("", "");
	JSONObject results = request.runQuery(endpoint, "GET", params);
	System.out.println(results.toString(4));
```
To remove a webhook

```
    String webhookId = "7JcGN81u";
    String endpoint = "webhooks/" + webhookId;
    Semantics3Request request = new Semantics3Request(apiKey, apiSecret, endpoint);
    
	HashMap params = new HashMap();
	params.put("", "");

	JSONObject results = request.runQuery(endpoint, "DELETE", params);
	System.out.println(results.toString(4));
```

### Registering events

Once you register a webhook, you can start adding events to it. Semantics3 server will send you notifications when these events occur. To register events for a specific webhook send a POST request to the ```"webhooks/{webhook_id}/events"``` endpoint

```
    String webhookId = "7JcGN81u";
    String endpoint = "webhooks/" +webhookId+ "/events";
	
	Semantics3Request request = new Semantics3Request(apiKey, apiSecret, endpoint);
	
	String json = "{\"type\": \"price.change\"," +
			"\"product\": " +
			"{\"sem3_id\": \"1QZC8wchX62eCYS2CACmka\"}," +
			"\"constraints\": " +
			"{ \"gte\": 10, \"lte\": 100}}";
	
	HashMap params = new HashMap();
	params.put("params", "json");
	
	JSONObject results = request.runQuery(endpoint, "POST", params);
	System.out.println(results.toString(4));
```

To fetch all registered events for a give webhook

```
    String webhookId = "7JcGN81u";
    String endpoint = "webhooks/" +webhookId+ "/events";
    
	Semantics3Request request = new Semantics3Request(apiKey, apiSecret, endpoint);
	HashMap params = new HashMap();
	
	params.put("", "");
	JSONObject results = request.runQuery(endpoint, "GET", params);
```
## Webhook Notifications

Once you have created a webhook and registered events on it, notifications will be sent to your registered webhook URI via a POST request when the corresponding events occur. Make sure that your server can accept POST requests. Here is how a sample notification object looks like

```
{
    "type": "price.change",
    "event_id": "XyZgOZ5q",
    "notification_id": "X4jsdDsW",
    "changes": [{
        "site": "abc.com",
        "url": "http://www.abc.com/def",
        "previous_price": 45.50,
        "current_price": 41.00
    }, {
        "site": "walmart.com",
        "url": "http://www.walmart.com/ip/20671263",
        "previous_price": 34.00,
        "current_price": 42.00
    }]
}

```
## Contributing
Use GitHub's standard fork/commit/pull-request cycle.  If you have any questions, email <support@semantics3.com>.

## Authors

* Shawn Tan

* Asmit Kumar <asmit@semantics3.com>

## Copyright

Copyright (c) 2015 Semantics3 Inc.

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


