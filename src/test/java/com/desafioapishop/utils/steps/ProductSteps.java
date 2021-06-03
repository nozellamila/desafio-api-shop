package com.desafioapishop.utils.steps;

import com.desafioapishop.requests.product.ProductBody;
import com.desafioapishop.requests.product.ProductRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ProductSteps {

    public static String getNewProductId(String token){
        String name = "Product to insert cart";
        Float price = 20.0f;
        String description = "Product description";
        Integer quantity = 2;

        if(getProductId(name) == null){
            ProductRequest productRequest = new ProductRequest();
            ProductBody productBody = new ProductBody(name, price, description, quantity);
            productRequest.setPostProductRequest(token, productBody);

            Response response = productRequest.executeRequestNoLog();

            return response.body().jsonPath().get("id").toString();
        }else if(getProductId(name) != null && !getProductQuantity(name).equals("[2]")){
            ProductRequest productRequest = new ProductRequest();
            ProductBody productBody = new ProductBody(name, price, description, quantity);
            productRequest.setPutProductRequest(token, productBody, getProductId(name));

            productRequest.executeRequestNoLog();

            return getProductId(name);
        }
        else {
            return getProductId(name);
        }
    }

    public static String getProductId(String name){
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("name", name);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setGetProductWithParamsRequest(requestParameters);

        Response response = productRequest.executeRequestNoLog();

        String responseString = response.getBody().asString().substring(18, 19);

        if(response.statusCode() == 404)
            return null;
        else
            return responseString;
    }

    public static String getProductQuantity(String name){
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("name", name);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setGetProductWithParamsRequest(requestParameters);

        Response response = productRequest.executeRequestNoLog();


        return response.body().jsonPath().get("content.quantity").toString();
    }
}
