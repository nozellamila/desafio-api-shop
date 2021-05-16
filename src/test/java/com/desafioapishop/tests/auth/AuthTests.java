package com.desafioapishop.tests.auth;

import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.requests.auth.AuthRequest;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

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

    }

    @Test
    public void noExistingUserAuthTest(){
        int expectedStatusCode = HttpStatus.SC_BAD_REQUEST;
        AuthBody authBody = new AuthBody("fool@email.com", "123456");

        AuthRequest authRequest = new AuthRequest(authBody);
        ValidatableResponse response = authRequest.executeRequest();

        response.statusCode(expectedStatusCode);
    }

    @ParameterizedTest(name = "{index} => email={0}, password={1}, message={2}")
    @CsvFileSource(resources = "/data/auth/authValidations.csv")
    public void invalidCredentialsAuthTest(String email, String password, String message){
        int expectedStatusCode = HttpStatus.SC_BAD_REQUEST;
        String errorMessage = message;
        AuthBody authBody = new AuthBody(email, password);

        AuthRequest authRequest = new AuthRequest(authBody);
        Response response = authRequest.executeRequestNoLog();

        assertEquals(response.statusCode(), expectedStatusCode);
        assertEquals(errorMessage, response.body().jsonPath().get("erro").toString());
    }
}
