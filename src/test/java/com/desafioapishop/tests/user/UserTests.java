package com.desafioapishop.tests.user;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.requests.auth.AuthRequest;
import com.desafioapishop.requests.user.UserBody;
import com.desafioapishop.requests.user.UserRequest;
import com.desafioapishop.utils.AuthUtils;
import com.desafioapishop.utils.DBUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;


public class UserTests {

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
    public void repeatedEmailUserRegister(){
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
        //DBUtils.executeInitialQuery();

        GlobalParameters globalParameters = new GlobalParameters();

        String email = globalParameters.NONADMIN_USER;
        String name = "fool";
        String password = globalParameters.NONADMIN_PASSWORD;
        List<String> roles = Arrays.asList("USER");

        AuthBody authBody = new AuthBody();
        AuthUtils authUtils = new AuthUtils();
        String token = authUtils.generateToken(authBody);

        UserRequest userRequest = new UserRequest();
        UserBody userBody = new UserBody(email, name, password, roles);
        userRequest.setPutUserRequest(token, userBody, 2);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("name", equalTo(name));
    }

    @Test
    public void forbiddenUserUpdate() throws IOException {
        Properties properties = new Properties();
        InputStream input = null;

        AuthBody authBody = new AuthBody();
        AuthUtils authUtils = new AuthUtils();
        String token = authUtils.generateToken(authBody);

        try {
            input = new FileInputStream("src/test/globalParameters.properties");
            properties.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        properties.replace("dev.token", token);
        input.close();
    }

    @Test
    public void invalidTokenUserUpdate(){

    }

    @Test//Parameterized
    public void invalidParametersUserUpdate(){

    }

    @Test
    public void shouldNotUpdateUserWithOtherUserEmail(){

    }

    @Test
    public void listUsers(){

    }

    @Test
    public void listUsersWithFilters(){

    }

    @Test
    public void shouldNotFindUserToList(){

    }

    @Test
    public void forbiddenListUser(){

    }

    @Test
    public void invalidTokenListUser(){

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
