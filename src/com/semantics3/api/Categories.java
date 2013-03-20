package com.semantics3.api;

import org.json.JSONObject;

public class Categories extends Semantics3Request {

	public Categories(String apiKey, String apiSecret) {
		super(apiKey, apiSecret, "categories");
	}

	public Categories field(Object... fields) {
		super.field(fields);
		return this;
	}

	public JSONObject getCategories() {
		return this.get();
	}

	public Categories categoriesField(Object... fields) {
		return this.field(fields);
	}

}
