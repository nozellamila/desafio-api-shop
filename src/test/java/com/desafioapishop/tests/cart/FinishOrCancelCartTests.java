package com.desafioapishop.tests.cart;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.cart.CartRequest;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

@DisplayName(value = "Testes de finalização e cancelamento de carrinho")
public class FinishOrCancelCartTests extends TestBase {

    @Test
    public void successfulFinishBuy(){
        int expectedStatusCode = HttpStatus.SC_OK;
        String expectedMessage = "Compra finalizada com sucesso";

        GlobalParameters globalParameters = new GlobalParameters();
        String userId = globalParameters.TOFINISHBUY_USERID;

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
        cartRequest.setFinishCartRequest(token, "6");

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));
    }

    @Test
    public void forbiddenFinishBuy(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        token = "";

        CartRequest cartRequest = new CartRequest();
        cartRequest.setFinishCartRequest(token, "1");

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);

    }

    @Test
    public void successfulCancelBuy(){
        new GlobalParameters();
        String userId = GlobalParameters.TOCANCELCART_USERID;

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
        cartRequest.setCancelCartRequest(token, "6");

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));
    }

    @Test
    public void forbiddenCancelBuy(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        token = "";

        CartRequest cartRequest = new CartRequest();
        cartRequest.setCancelCartRequest(token, "1");

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }
}
