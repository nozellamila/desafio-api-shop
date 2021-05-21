package com.desafioapishop.utils;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.requests.auth.AuthRequest;
import io.restassured.response.Response;

public class AuthUtils {
    public static String generateToken(AuthBody authBody){
        AuthRequest getToken = new AuthRequest(authBody);
        Response response = getToken.executeRequestNoLog();

        return "Bearer " + response.body().jsonPath().get("token").toString();
    }
}
