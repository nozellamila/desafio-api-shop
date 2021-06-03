package com.desafioapishop.tests.user;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.requests.user.UserRequest;
import com.desafioapishop.utils.AuthUtils;
import com.desafioapishop.utils.DBUtils;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

@DisplayName(value = "Testes de exclusão de usuário")
public class DeleteUserTests extends TestBase {

    @Test
    public void excludeUser(){
        int expectedStatusCode = HttpStatus.SC_OK;
        String message = "Usuário excluído com sucesso";

        String userId = "5";

        UserRequest userRequest = new UserRequest();
        userRequest.setDeleteUserRequest(token, userId);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(message));
    }

    @Test
    public void shouldNotExcludeNoRegisteredUser(){
        int expectedStatusCode = HttpStatus.SC_OK;
        String message = "Nenhum usuário excluído";

        String userId = "999";

        UserRequest userRequest = new UserRequest();
        userRequest.setDeleteUserRequest(token, userId);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(message));
    }

    @Test
    public void shouldNotExcludeUserWithCart(){
        int expectedStatusCode = HttpStatus.SC_UNPROCESSABLE_ENTITY;
        String expectedMessage = "Não é possível excluir usuário com carrinho";

        GlobalParameters globalParameters = new GlobalParameters();

        String userId = globalParameters.TOREGISTERCART_USERID;

        UserRequest userRequest = new UserRequest();
        userRequest.setDeleteUserRequest(token, userId);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));
    }

    @Test
    public void forbiddenExcludeUser(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        AuthBody authBody = new AuthBody("nonexisting@email.com", "nonexistingpwd");
        AuthUtils authUtils = new AuthUtils();
        token = authUtils.generateToken(authBody);

        String userId = "1";

        UserRequest userRequest = new UserRequest();
        userRequest.setDeleteUserRequest(token, userId);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }

    @Test
    public void invalidTokenExcludeUser(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        token = "anyToken";

        String userId = "1";

        UserRequest userRequest = new UserRequest();
        userRequest.setDeleteUserRequest(token, userId);

        ValidatableResponse response = userRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }
}
