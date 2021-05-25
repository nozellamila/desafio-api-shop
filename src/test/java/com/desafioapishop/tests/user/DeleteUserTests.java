package com.desafioapishop.tests.user;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.requests.user.UserRequest;
import com.desafioapishop.utils.AuthUtils;
import com.desafioapishop.utils.DBUtils;
import com.desafioapishop.utils.steps.UserSteps;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

@Execution(ExecutionMode.CONCURRENT)
public class DeleteUserTests extends TestBase {

    @Test
    public void excludeUser(){
        int expectedStatusCode = HttpStatus.SC_OK;
        String message = "Usuário excluído com sucesso";

        List<String> userId = DBUtils.getQueryResult("FindUserToExclude.sql");

        UserRequest userRequest = new UserRequest();
        userRequest.setDeleteUserRequest(token, userId.get(0));

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
        UserSteps.insertUserCart(token);

        int expectedStatusCode = HttpStatus.SC_UNPROCESSABLE_ENTITY;
        String expectedMessage = "Não é possível excluir usuário com carrinho";

        GlobalParameters globalParameters = new GlobalParameters();

        String userId = globalParameters.NONADMIN_USERID;

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
