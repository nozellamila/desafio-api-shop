package com.desafioapishop.utils.steps;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.cart.CartBody;
import com.desafioapishop.requests.cart.CartRequest;
import com.desafioapishop.requests.product.ProductCart;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSteps {

    public static void insertUserCart(String token){
        new GlobalParameters();
        String userId = GlobalParameters.NONADMIN_USERID;

        ProductCart productCart = new ProductCart();
        List<ProductCart> productCarts = new ArrayList<>();
        productCarts.add(productCart);

        CartBody cartBody = new CartBody(productCarts, userId);

        boolean userHasCart = verifyUserHasCart(token, userId);

        if(!userHasCart){
            CartRequest cartRequest = new CartRequest();
            cartRequest.setPostCartRequest(token, cartBody);
            cartRequest.executeRequestNoLog();
        }
    }

    private static boolean verifyUserHasCart(String token, String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        CartRequest cartRequest = new CartRequest();
        cartRequest.setGetCartWithParamsRequest(token, params);

        Response response = cartRequest.executeRequestNoLog();

        if (response.statusCode() == 200){
            return true;
        }
        else{
            return false;
        }
    }
}
