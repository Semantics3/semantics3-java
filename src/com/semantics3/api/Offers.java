package com.semantics3.api;

import org.json.JSONObject;

public class Offers extends Semantics3Request {

	public Offers(String apiKey, String apiSecret) {
		super(apiKey, apiSecret, "offers");
	}

	public Offers field(Object... fields) {
		super.field(fields);
		return this;
	}

	public JSONObject getOffers() {
		return this.get();
	}

	public Offers offersField(Object... fields) {
		return this.field(fields);
	}

}
