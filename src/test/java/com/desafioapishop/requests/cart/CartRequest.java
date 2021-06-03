package com.desafioapishop.requests.cart;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.RequestBase;
import com.desafioapishop.requests.auth.AuthBody;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.Map;

public class CartRequest extends RequestBase {

    public CartRequest(){
        GlobalParameters globalParameters = new GlobalParameters();
        url = globalParameters.URL_DEFAULT;
        requestService = "/carts";
    }

    public void setPostCartRequest(String token, CartBody cartBody){
        headers.put("Authorization", token);
        jsonBody = cartBody;
        method = Method.POST;
    }

    public void setFinishCartRequest(String token, String userId){
        headers.put("Authorization", token);
        method = Method.DELETE;
        requestService = "/carts/finish-buy";
        jsonBody = "{\"userId\": " + userId +"}";
    }

    public void setCancelCartRequest(String token, String userId){
        headers.put("Authorization", token);
        method = Method.DELETE;
        requestService = "/carts/cancel-buy";
        jsonBody = "{\"userId\": " + userId +"}";
    }

    public void setGetOneCartRequest(String token, String cartId){
        headers.put("Authorization", token);
        method = Method.GET;
        requestService = "/carts/" + cartId;
    }

    public void setGetCartWithParamsRequest(String token, Map<String, String> requestParameters){
        headers.put("Authorization", token);
        method = Method.GET;
        queryParameters = requestParameters;
    }
}
