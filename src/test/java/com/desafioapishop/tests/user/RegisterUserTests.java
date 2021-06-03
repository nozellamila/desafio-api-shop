package com.desafioapishop.tests.user;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.user.UserBody;
import com.desafioapishop.requests.user.UserRequest;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName(value = "Testes de registro de usuário")
public class RegisterUserTests extends TestBase {

    @Test
    public void successfulRegisterUser(){
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
    public void invalidParametersRegisterUser(String email, String name, String password, String message){
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
        int expectedStatusCode = HttpStatus.SC_CONFLICT;

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
}
