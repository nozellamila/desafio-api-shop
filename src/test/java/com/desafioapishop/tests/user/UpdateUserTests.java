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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName(value = "Testes de atualização de usuário")
public class UpdateUserTests extends TestBase {

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
        int expectedStatusCode = HttpStatus.SC_CONFLICT;

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
}
