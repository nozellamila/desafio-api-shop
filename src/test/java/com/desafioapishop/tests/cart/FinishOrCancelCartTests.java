package com.desafioapishop.tests.cart;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.cart.CartBody;
import com.desafioapishop.requests.cart.CartRequest;
import com.desafioapishop.requests.product.ProductCartBody;
import com.desafioapishop.utils.DBUtils;
import com.desafioapishop.utils.steps.CartSteps;
import com.desafioapishop.utils.steps.UserSteps;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.desafioapishop.utils.steps.CartSteps.cartExists;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName(value = "Testes de finalização e cancelamento de carrinho")
public class FinishOrCancelCartTests extends TestBase {

    @Test
    public void successfulFinishBuy(){
        int expectedStatusCode = HttpStatus.SC_OK;
        String expectedMessage = "Compra finalizada com sucesso";

        GlobalParameters globalParameters = new GlobalParameters();
        String userId = globalParameters.TOREGISTERCART_USERID;

        CartSteps.insertCartWithNewProduct(token);

        CartRequest cartRequest = new CartRequest();
        cartRequest.setFinishCartRequest(token, userId);

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));
    }

    @Test
    public void shouldNotFinishBuyForUserWithNoCart(){
        int expectedStatusCode = HttpStatus.SC_NOT_FOUND;
        String expectedMessage = "Não há carrinho cadastrado para o usuário";

        CartRequest cartRequest = new CartRequest();
        cartRequest.setFinishCartRequest(token, "1");

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));
    }

    @Test
    public void forbiddenFinishBuy(){
        UserSteps.insertUserCart(token);

        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        token = "";

        CartRequest cartRequest = new CartRequest();
        cartRequest.setFinishCartRequest(token, "1");

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);

        UserSteps.cancelUserCart(token);
    }

    @Test
    public void successfulCancelBuy(){
        UserSteps.insertUserCart(token);

        new GlobalParameters();
        String userId = GlobalParameters.NONADMIN_USERID;

        int expectedStatusCode = HttpStatus.SC_OK;
        String expectedMessage = "Compra cancelada com sucesso";

        CartRequest cartRequest = new CartRequest();
        cartRequest.setCancelCartRequest(token, userId);

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));
    }

    @Test
    public void shouldNotCancelBuyForUserWithNoCart(){
        int expectedStatusCode = HttpStatus.SC_NOT_FOUND;
        String expectedMessage = "Não há carrinho cadastrado para o usuário";

        CartRequest cartRequest = new CartRequest();
        cartRequest.setCancelCartRequest(token, "1");

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));
    }

    @Test
    public void forbiddenCancelBuy(){
        UserSteps.insertUserCart(token);

        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        token = "";

        CartRequest cartRequest = new CartRequest();
        cartRequest.setCancelCartRequest(token, "1");

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);

        UserSteps.cancelUserCart(token);
    }
}
