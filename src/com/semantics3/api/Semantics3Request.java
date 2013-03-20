package com.semantics3.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import org.json.JSONObject;
import org.json.JSONTokener;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public class Semantics3Request {
	final static private String API_DOMAIN = "api.semantics3.com";
	final static private String API_BASE   = "https://" + API_DOMAIN + "/v1/";
	
	private String apiKey;
	private String apiSecret;
	private String endpoint;
	private String endpointURL;
	private OAuthConsumer consumer;
	private HashMap<String,JSONObject> query = new HashMap<String, JSONObject>();
	private JSONObject queryResult;
	
	public Semantics3Request(String apiKey, String apiSecret, String endpoint) {
		this.apiKey    = apiKey;
		this.apiSecret = apiSecret;
		this.endpoint  = endpoint;
		this.consumer = new DefaultOAuthConsumer(apiKey, apiSecret);
		consumer.setTokenWithSecret("", "");
	}
	
	protected JSONObject fetch(String endpoint, String params) throws
			OAuthMessageSignerException,
			OAuthExpectationFailedException,
			OAuthCommunicationException,
			IOException {
		String req = API_BASE + endpoint +
				"?q=" + URLEncoder.encode(params, "UTF-8");
		URL url = new URL(req);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		consumer.sign(request);
		System.out.println("Sending request...");
		request.connect();
		System.out.println("Response: " + request.getResponseCode() + " "
				+ request.getResponseMessage());
		JSONObject json = new JSONObject(new JSONTokener(request.getInputStream()));
		return json;
	}
	
	public Semantics3Request add(String endpoint, Object... fields) {
		JSONObject endpointQuery;
		if ((endpointQuery = query.get(endpoint)) == null) {
			endpointQuery = new JSONObject();
			query.put(endpoint,endpointQuery);
		}
		JSONObject sq = endpointQuery;
		for (int i=0;i<fields.length-2;i++) {
			JSONObject tmp = sq;
			if(sq.has((String)fields[i])){
				sq = sq.getJSONObject((String)fields[i]);
			} else {
				sq = new JSONObject();
				tmp.put((String)fields[i], sq);
			}
		}
		sq.put((String)fields[fields.length-2], fields[fields.length-1]);
		System.out.println(endpointQuery);
		return this;
	}
	
	protected void runQuery() throws
		OAuthMessageSignerException,
		OAuthExpectationFailedException,
		OAuthCommunicationException,
		IOException {
		runQuery(this.endpoint);
	}
	
	protected void runQuery(String endpoint) throws
		OAuthMessageSignerException,
		OAuthExpectationFailedException,
		OAuthCommunicationException,
		IOException {
		JSONObject q = this.query.get(endpoint);
		this.queryResult = fetch(endpoint,q.toString());
	}
	
	public JSONObject get() {
		try {
			this.runQuery();
			return this.queryResult;
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String args[]) {
		Semantics3Request sreq = new Semantics3Request(
				"",
				"",
				"products");
		
		sreq.add("products", "cat_id", 4992 )
			.add("products", "brand", "Toshiba" )
			.add("products", "weight", "gte", 1000000 )
			.add("products", "weight", "lt", 1500000 )
			.add("products", "sitedetails", "name", "newegg.com" )
			.add("products", "sitedetails", "latestoffers", "currency", "USD" )
			.add("products", "sitedetails", "latestoffers", "price", "gte", 100 );
		
		System.out.println(sreq.get());
	}
}
