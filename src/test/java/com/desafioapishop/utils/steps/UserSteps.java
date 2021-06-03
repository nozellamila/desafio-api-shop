package com.desafioapishop.utils.steps;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.requests.cart.CartBody;
import com.desafioapishop.requests.cart.CartRequest;
import com.desafioapishop.requests.product.ProductCartBody;
import com.desafioapishop.requests.user.UserBody;
import com.desafioapishop.requests.user.UserRequest;
import com.desafioapishop.utils.DBUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;

public class UserSteps {

    public static void insertUserCart(String token){
        new GlobalParameters();
        String userId = GlobalParameters.NONADMIN_USERID;

        ProductCartBody productCartBody = new ProductCartBody();
        List<ProductCartBody> productCartBodies = new ArrayList<>();
        productCartBodies.add(productCartBody);

        CartBody cartBody = new CartBody(productCartBodies, userId);

        boolean userHasCart = verifyUserHasCart(token, userId);

        if(!userHasCart){
            CartRequest cartRequest = new CartRequest();
            cartRequest.setPostCartRequest(token, cartBody);
            cartRequest.executeRequestNoLog();
        }
    }

    public static void cancelUserCart(String token){
        new GlobalParameters();
        String userId = GlobalParameters.NONADMIN_USERID;

        boolean userHasCart = verifyUserHasCart(token, userId);

        if(userHasCart){
            CartRequest cartRequest = new CartRequest();
            cartRequest.setCancelCartRequest(token, userId);
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
