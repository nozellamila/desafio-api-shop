package com.desafioapishop.requests.product;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.RequestBase;
import com.desafioapishop.requests.product.ProductBody;
import io.restassured.http.Method;

import java.util.Map;

public class ProductRequest extends RequestBase {

    public ProductRequest(){
        GlobalParameters globalParameters = new GlobalParameters();
        url = globalParameters.URL_DEFAULT;
        requestService = "/products";
    }

    public void setPostProductRequest(String token, ProductBody productBody){
        headers.put("Authorization", token);
        jsonBody = productBody;
        method = Method.POST;
    }

    public void setPutProductRequest(String token, ProductBody productBody, Integer productId){
        headers.put("Authorization", token);
        jsonBody = productBody;
        method = Method.PUT;
        requestService = "/products/" + productId.toString();
    }

    public void setDeleteProductRequest(String token, String productId){
        headers.put("Authorization", token);
        method = Method.DELETE;
        requestService = "/products/" + productId;
    }

    public void setGetProductWithParamsRequest(Map<String, String> requestParameters){
        method = Method.GET;
        queryParameters = requestParameters;
    }
}
