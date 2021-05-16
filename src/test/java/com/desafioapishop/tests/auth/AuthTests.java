package com.desafioapishop.tests.auth;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.requests.auth.AuthRequest;
import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

public class AuthTests {
    @Test
    public void successfulAuthTest(){
        int expectedStatusCode = HttpStatus.SC_OK;
        String type = "Bearer";
        AuthBody authBody = new AuthBody();

        AuthRequest authRequest = new AuthRequest(authBody);
        ValidatableResponse response = authRequest.executeRequest();

        response.statusCode(expectedStatusCode);
        response.body("type", equalTo(type));

        Response responseToken = authRequest.executeRequestNoLog();
        GlobalParameters.setToken(responseToken.path("token").toString());
        System.out.println(GlobalParameters.TOKEN);
    }
}
