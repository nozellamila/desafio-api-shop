package com.desafioapishop.tests.user;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.requests.user.UserRequest;
import com.desafioapishop.utils.AuthUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName(value = "Testes de busca de usuário")
public class ListUserTests extends TestBase {

    @Test
    public void listOneUser(){
        int expectedStatusCode = HttpStatus.SC_OK;

        GlobalParameters globalParameters = new GlobalParameters();
        String expectedEmail = globalParameters.AUTHENTICATOR_USER;

        int userId = 1;

        UserRequest userRequest = new UserRequest();
        userRequest.setGetOneUserRequest(token, userId);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("email", equalTo(expectedEmail));
    }

    @ParameterizedTest(name = "{index} => parameterKey={0}, parameterValue={1}, expectedReturn={2}")
    @CsvFileSource(resources = "/data/user/userFilters.csv")
    public void listUsersWithFilters(String parameterKey, String parameterValue, String expectedReturn){
        int expectedStatusCode = HttpStatus.SC_OK;

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put(parameterKey, parameterValue);

        UserRequest userRequest = new UserRequest();
        userRequest.setGetUserWithParamsRequest(token, requestParameters);

        Response response = userRequest.executeRequestNoLog();

        assertEquals(response.statusCode(), expectedStatusCode);
        assertEquals(expectedReturn, response.body().jsonPath().get("content.email").toString());
    }

    @ParameterizedTest(name = "{index} => id={0}, name={1}, email={2}")
    @CsvFileSource(resources = "/data/user/userInvalidFilters.csv")
    public void shouldNotFindUserToList(String id, String name, String email){
        int expectedStatusCode = HttpStatus.SC_NOT_FOUND;
        String expectedMessage = "Usuário não encontrado";

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("id", id);
        requestParameters.put("name", name);
        requestParameters.put("email", email);

        UserRequest userRequest = new UserRequest();
        userRequest.setGetUserWithParamsRequest(token, requestParameters);

        Response response = userRequest.executeRequestNoLog();

        assertEquals(response.statusCode(), expectedStatusCode);
        assertEquals(expectedMessage, response.body().jsonPath().get("message").toString());
    }

    @Test
    public void forbiddenListUser(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        AuthBody authBody = new AuthBody("nonexisting@email.com", "nonexistingpwd");
        AuthUtils authUtils = new AuthUtils();
        token = authUtils.generateToken(authBody);

        GlobalParameters globalParameters = new GlobalParameters();

        String email = globalParameters.NONADMIN_USER;

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("email", email);

        UserRequest userRequest = new UserRequest();
        userRequest.setGetUserWithParamsRequest(token, requestParameters);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }

    @Test
    public void invalidTokenListUser(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        token = "anyToken";

        GlobalParameters globalParameters = new GlobalParameters();

        String email = globalParameters.NONADMIN_USER;

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("email", email);

        UserRequest userRequest = new UserRequest();
        userRequest.setGetUserWithParamsRequest(token, requestParameters);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }
}
