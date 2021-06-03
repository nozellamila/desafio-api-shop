package com.desafioapishop.tests.cart;

import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.cart.CartRequest;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

@DisplayName(value = "Testes de busca de carrinho")
public class ListCartTests extends TestBase {

    @Test
    public void listOneCart(){
        int expectedStatusCode = HttpStatus.SC_OK;

        String cartId = "1";

        CartRequest cartRequest = new CartRequest();
        cartRequest.setGetOneCartRequest(token, cartId);

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("id", equalTo(Integer.parseInt(cartId)));

    }

    @Test//parametrized
    public void listCartWithFilters(){

    }

    @Test//parametrized
    public void shouldNotFindCartToList(){

    }
}
