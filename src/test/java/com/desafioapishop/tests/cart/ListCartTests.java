package com.desafioapishop.tests.cart;

import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.cart.CartRequest;
import com.desafioapishop.utils.DBUtils;
import com.desafioapishop.utils.steps.UserSteps;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@DisplayName(value = "Testes de busca de carrinho")
public class ListCartTests extends TestBase {

    @Test
    public void listOneCart(){
        int expectedStatusCode = HttpStatus.SC_OK;

        UserSteps.insertUserCart(token);
        List<String> cartId = DBUtils.getQueryResult("FindCartToList.sql");

        CartRequest cartRequest = new CartRequest();
        cartRequest.setGetOneCartRequest(token, cartId.get(0));

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("id", equalTo(Integer.parseInt(cartId.get(0))));

        UserSteps.cancelUserCart(token);
    }

    @Test//parametrized
    public void listCartWithFilters(){

    }

    @Test//parametrized
    public void shouldNotFindCartToList(){

    }
}
