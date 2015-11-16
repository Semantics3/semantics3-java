package com.semantics3.api;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by abishek on 03/04/15.
 */
public class ProductsTest {
    @Test
    public void TestProductsQuery() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException, URISyntaxException {
        Properties property = TestUtils.getConfig("api.config");
        Products products = new Products(property.get("API_KEY").toString(), property.get("API_SECRET").toString());
        products.productsField("search", "iphone");
        JSONObject results = products.getProducts();
        JSONArray resultsArray = (JSONArray) results.get("results");
        assertThat(resultsArray.length() > 0, is(true));
    }

    @Test
    public void TestUPCQuery() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException, URISyntaxException {
        Properties property = TestUtils.getConfig("api.config");
        Products products = new Products(property.get("API_KEY").toString(), property.get("API_SECRET").toString());
        products.productsField("upc", "883974958450");
        products.productsField("fields", "name", "gtins");
        JSONObject results = products.getProducts();
        JSONArray resultsArray = (JSONArray) results.get("results");
        assertThat(resultsArray.length() > 0, is(true));
    }

    @Test
    public void TestURLQuery() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException, URISyntaxException {
        Properties property = TestUtils.getConfig("api.config");
        Products products = new Products(property.get("API_KEY").toString(), property.get("API_SECRET").toString());
        products.productsField("url", "http://www.walmart.com/ip/15833173");
        JSONObject results = products.getProducts();
        JSONArray resultsArray = (JSONArray) results.get("results");
        assertThat(resultsArray.length() > 0, is(true));
    }

    @Test
    public void TestPriceFilterQuery() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException, URISyntaxException {
        Properties property = TestUtils.getConfig("api.config");
        Products products = new Products(property.get("API_KEY").toString(), property.get("API_SECRET").toString());
        products
                .productsField("search", "iphone")
                .productsField("price", "lt", 300);
        JSONObject results = products.getProducts();
        JSONArray resultsArray = (JSONArray) results.get("results");
        assertThat(resultsArray.length() > 0, is(true));
    }

    @Test
    public void TestCategoryIDQuery() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException, URISyntaxException {
        Properties property = TestUtils.getConfig("api.config");
        Products products = new Products(property.get("API_KEY").toString(), property.get("API_SECRET").toString());
        products
                .categoriesField("cat_id", 4992); JSONObject results = products.getCategories(); JSONArray resultsArray = (JSONArray) results.get("results"); assertThat(resultsArray.length() > 0, is(true)); }  @Test
    public void TestSKUQuery() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException, URISyntaxException {
        Properties property = TestUtils.getConfig("api.config");
        Semantics3Request semantics3Request = new Semantics3Request(property.get("API_KEY").toString(), property.get("API_SECRET").toString());
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("site", "abercrombie.com");
        JSONObject jsonObject = semantics3Request.runQuery("skus", "GET", params);
        JSONArray resultsArray = (JSONArray) jsonObject.get("results");
        assertThat(resultsArray.length() > 0, is(true));
    }

    @Ignore
    public void TestPagination() throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException, URISyntaxException {
        Properties property = TestUtils.getConfig("api.config");
        Semantics3Request semantics3Request = new Semantics3Request(property.get("API_KEY").toString(), property.get("API_SECRET").toString());
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("site", "macys.com");
        JSONObject jsonObject = semantics3Request.runQuery("skus", "GET", params);
        while(jsonObject.has("next")){
            JSONObject next = (JSONObject)jsonObject.get("next");
            String page = next.getString("page");
            System.out.println(page);
            params.remove("site");
            params.put("page", page);
            jsonObject = semantics3Request.runQuery("skus", "GET", params);
            System.out.println(jsonObject.toString());
        }
    }
}
