package com.semantics3.api;

import java.io.IOException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.json.JSONObject;

public class Categories extends Semantics3Request {

	public Categories(String apiKey, String apiSecret) {
		super(apiKey, apiSecret, "categories");
	}

	public Categories field(Object... fields) {
		super.field(fields);
		return this;
	}

	public JSONObject getCategories() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {
		return this.get();
	}

	public Categories categoriesField(Object... fields) {
		return this.field(fields);
	}

}
