package com.desafioapishop.utils.steps;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.requests.cart.CartBody;
import com.desafioapishop.requests.cart.CartRequest;
import com.desafioapishop.requests.product.ProductCartBody;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartSteps {

    public static void cancelBuy(String token, String userId){
        CartRequest cartRequest = new CartRequest();
        cartRequest.setCancelCartRequest(token, userId);
        cartRequest.executeRequestNoLog();
    }

    public static boolean cartExists(String token, String userId){
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("userId", userId);

        CartRequest cartRequest = new CartRequest();
        cartRequest.setGetCartWithParamsRequest(token, requestParameters);

        Response response = cartRequest.executeRequestNoLog();

        if(response.statusCode() == 404)
            return false;
        else
            return true;
    }

    public static void insertCartWithNewProduct(String token){
        GlobalParameters globalParameters = new GlobalParameters();
        String userId = globalParameters.TOREGISTERCART_USERID;

        if(CartSteps.cartExists(token, userId))
            CartSteps.cancelBuy(token, userId);

        String productId = ProductSteps.getNewProductId(token);

        List<ProductCartBody> productCartBodyList = new ArrayList<>();
        productCartBodyList.add(new ProductCartBody(productId, "2"));
        CartBody cartBody = new CartBody(productCartBodyList, userId);

        CartRequest cartRequest = new CartRequest();
        cartRequest.setPostCartRequest(token, cartBody);
        cartRequest.executeRequest();
    }
}
