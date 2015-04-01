package com.semantics3.api;

import java.io.IOException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.json.JSONObject;

public class Offers extends Semantics3Request {

	public Offers(String apiKey, String apiSecret) {
		super(apiKey, apiSecret, "offers");
	}

	public Offers field(Object... fields) {
		super.field(fields);
		return this;
	}

	public JSONObject getOffers() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {
		return this.get();
	}

	public Offers offersField(Object... fields) {
		return this.field(fields);
	}

}
