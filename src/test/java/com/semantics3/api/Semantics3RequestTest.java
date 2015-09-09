package com.semantics3.api;

import com.semantics3.errors.Semantics3Exception;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by abishek on 03/04/15.
 */
public class Semantics3RequestTest {
    @Test
    public void testRunQueryPost() throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        Properties property = TestUtils.getConfig("api.config");
        Semantics3Request sem3 = new Semantics3Request(property.get("API_KEY").toString(), property.get("API_SECRET").toString());
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("webhook_uri", " http://sem3-webhooks-verification.ngrok.com");
        try {
            JSONObject result = sem3.runQuery("webhooks", "POST", params);
            assertThat(result.getString("code"), equalTo("OK"));
        } catch (Semantics3Exception e) {
            String message = e.getMessage();
            assertThat(message, anything("Duplicate"));
        }
    }

    @Test
    public void testRunQueryGet() throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        Properties property = TestUtils.getConfig("api.config");
        Semantics3Request sem3 = new Semantics3Request(property.get("API_KEY").toString(), property.get("API_SECRET").toString());
        HashMap<String, Object> params = new HashMap<String, Object>();
        JSONObject result = sem3.runQuery("webhooks", "GET", params);
        assertThat(result.getString("code"), equalTo("OK"));

    }

    @Test
    public void testRunQueryDelete() throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        Properties property = TestUtils.getConfig("api.config");
        Semantics3Request sem3 = new Semantics3Request(property.get("API_KEY").toString(), property.get("API_SECRET").toString());
        HashMap<String, Object> params = new HashMap<String, Object>();
        JSONObject result = sem3.runQuery("webhooks", "GET", params);
        JSONArray data = result.getJSONArray("data");
        if (data.length() > 0) {
            JSONObject webhooks = (JSONObject) data.get(0);
            String webhooksId = webhooks.getString("id");
            String endpoint = "webhooks/" + webhooksId;
            JSONObject response = sem3.runQuery(endpoint, "DELETE", params);
            assertThat(response.getString("code"), equalTo("OK"));
        }
    }

}
