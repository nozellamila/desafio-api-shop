package com.desafioapishop.tests.user;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.requests.user.UserBody;
import com.desafioapishop.requests.user.UserRequest;
import com.desafioapishop.utils.AuthUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@Execution(ExecutionMode.CONCURRENT)
public class UserTests extends TestBase {

    @Test
    public void successfulUserRegister(){
        int expectedStatusCode = HttpStatus.SC_CREATED;

        int random = new Random().nextInt(100);
        String email = "fool-" + random + "@email.com" ;
        String name = "fool";
        String password = "123456";
        List<String> roles = Arrays.asList("USER");

        UserRequest userRequest = new UserRequest();
        UserBody userBody = new UserBody(email, name, password, roles);
        userRequest.setPostUserRequest(userBody);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("name", equalTo(name),
                        "email", equalTo(email),
                        "role[0]", equalTo(roles.get(0)));
    }

    @ParameterizedTest(name = "{index} => email={0}, name={1}, password={2}, message={3}")
    @CsvFileSource(resources = "/data/user/userValidations.csv")
    public void invalidParametersUserRegister(String email, String name, String password, String message){
        int expectedStatusCode = HttpStatus.SC_BAD_REQUEST;
        String errorMessage = message;
        List<String> roles = Arrays.asList("USER");

        UserRequest userRequest = new UserRequest();
        UserBody userBody = new UserBody(email, name, password, roles);
        userRequest.setPostUserRequest(userBody);

        Response response = userRequest.executeRequestNoLog();

        assertEquals(response.statusCode(), expectedStatusCode);
        assertEquals(errorMessage, response.body().jsonPath().get("erro").toString());
    }

    @Test
    public void shouldNotRegisterUserWithOtherUsersEmail(){
        int expectedStatusCode = HttpStatus.SC_BAD_REQUEST;

        GlobalParameters globalParameters = new GlobalParameters();
        String email = globalParameters.AUTHENTICATOR_USER;
        String name = "fool";
        String password = "123456";
        List<String> roles = Arrays.asList("USER");

        UserRequest userRequest = new UserRequest();
        UserBody userBody = new UserBody(email, name, password, roles);
        userRequest.setPostUserRequest(userBody);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo("Usuário já cadastrado com o e-mail: " + email));
    }

    @Test
    public void successfulUserUpdate(){
        int expectedStatusCode = HttpStatus.SC_OK;

        GlobalParameters globalParameters = new GlobalParameters();

        String email = globalParameters.NONADMIN_USER;
        String name = "fool";
        String password = globalParameters.NONADMIN_PASSWORD;
        List<String> roles = Arrays.asList("USER");

        UserRequest userRequest = new UserRequest();
        UserBody userBody = new UserBody(email, name, password, roles);
        userRequest.setPutUserRequest(token, userBody, 2);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("name", equalTo(name));
    }

    @Test
    public void forbiddenUserUpdate(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        AuthBody authBody = new AuthBody("nonexisting@email.com", "nonexistingpwd");
        AuthUtils authUtils = new AuthUtils();
        token = authUtils.generateToken(authBody);

        GlobalParameters globalParameters = new GlobalParameters();

        String email = globalParameters.NONADMIN_USER;
        String name = "fool";
        String password = globalParameters.NONADMIN_PASSWORD;
        List<String> roles = Arrays.asList("USER");

        UserRequest userRequest = new UserRequest();
        UserBody userBody = new UserBody(email, name, password, roles);
        userRequest.setPutUserRequest(token, userBody, 2);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }

    @Test
    public void invalidTokenUserUpdate(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        token = "anyToken";

        GlobalParameters globalParameters = new GlobalParameters();

        String email = globalParameters.NONADMIN_USER;
        String name = "fool";
        String password = globalParameters.NONADMIN_PASSWORD;
        List<String> roles = Arrays.asList("USER");

        UserRequest userRequest = new UserRequest();
        UserBody userBody = new UserBody(email, name, password, roles);
        userRequest.setPutUserRequest(token, userBody, 2);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }

    @ParameterizedTest(name = "{index} => email={0}, name={1}, password={2}, message={3}")
    @CsvFileSource(resources = "/data/user/userValidations.csv")
    public void invalidParametersUserUpdate(String email, String name, String password, String message){
        int expectedStatusCode = HttpStatus.SC_BAD_REQUEST;
        String errorMessage = message;
        List<String> roles = Arrays.asList("USER");

        UserRequest userRequest = new UserRequest();
        UserBody userBody = new UserBody(email, name, password, roles);
        userRequest.setPutUserRequest(token, userBody, 2);

        Response response = userRequest.executeRequestNoLog();

        assertEquals(response.statusCode(), expectedStatusCode);
        assertEquals(errorMessage, response.body().jsonPath().get("erro").toString());
    }

    @Test
    public void shouldNotUpdateUserWithOtherUsersEmail(){
        int expectedStatusCode = HttpStatus.SC_BAD_REQUEST;

        GlobalParameters globalParameters = new GlobalParameters();

        String alreadyExistingEmail = globalParameters.AUTHENTICATOR_USER;
        String name = "fool";
        String password = globalParameters.NONADMIN_PASSWORD;
        List<String> roles = Arrays.asList("USER");

        UserRequest userRequest = new UserRequest();
        UserBody userBody = new UserBody(alreadyExistingEmail, name, password, roles);
        userRequest.setPutUserRequest(token, userBody, 2);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo("Usuário já cadastrado com o e-mail: " + alreadyExistingEmail));
    }

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

    @Test
    public void excludeUser(){

    }

    @Test
    public void shouldNotExcludeUserWithCart(){

    }

    @Test
    public void forbiddenExcludeUser(){

    }

    @Test
    public void invalidTokenExcludeUser(){

    }
}
