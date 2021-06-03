package com.desafioapishop.tests.cart;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.cart.CartBody;
import com.desafioapishop.requests.cart.CartRequest;
import com.desafioapishop.requests.product.ProductCartBody;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName(value = "Testes de registro de carrinho")
public class RegisterCartTests extends TestBase {

    @Test
    public void successfulRegisterCart(){
        int expectedStatusCode = HttpStatus.SC_CREATED;
        String expectedMessage = "Cadastro realizado com sucesso";

        String patternStr = "^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}$";
        Pattern pattern = Pattern.compile(patternStr);

        GlobalParameters globalParameters = new GlobalParameters();
        String userId = globalParameters.NONADMIN_USERID;

        List<ProductCartBody> productCartBodyList = new ArrayList<>();
        productCartBodyList.add(new ProductCartBody());
        CartBody cartBody = new CartBody(productCartBodyList, userId);

        CartRequest cartRequest = new CartRequest();
        cartRequest.setPostCartRequest(token, cartBody);

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));

        Matcher matcher = pattern.matcher(response.extract().path("date").toString());
        assertTrue(matcher.matches());

    }

    @Test
    public void shouldNotRegisterCartForUserWithAlreadyExistingCart(){

        int expectedStatusCode = HttpStatus.SC_UNPROCESSABLE_ENTITY;
        String expectedMessage = "Não é possível cadastrar dois carrinhos para o mesmo usuário";

        GlobalParameters globalParameters = new GlobalParameters();

        String userId = globalParameters.TOREGISTERCART_USERID;
        List<ProductCartBody> productCartBodyList = new ArrayList<>();
        productCartBodyList.add(new ProductCartBody());

        CartBody cartBody = new CartBody(productCartBodyList, userId);

        CartRequest cartRequest = new CartRequest();
        cartRequest.setPostCartRequest(token, cartBody);

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));

    }

    @Test
    public void shouldNotRegisterCartWithNonExistingProduct(){

        int expectedStatusCode = HttpStatus.SC_NOT_FOUND;
        String expectedMessage = "Produto não existe";

        String userId = "6";
        List<ProductCartBody> productCartBodyList = new ArrayList<>();
        productCartBodyList.add(new ProductCartBody("1000", "1"));

        CartBody cartBody = new CartBody(productCartBodyList, userId);

        CartRequest cartRequest = new CartRequest();
        cartRequest.setPostCartRequest(token, cartBody);

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));
    }


    @ParameterizedTest(name = "{index} => productId={0}, productQuantity={1}, userId={2}, responseTag={3}, responseMessage={4}")
    @CsvFileSource(resources = "/data/cart/cartValidations.csv")
    public void shouldNotRegisterCartWithInvalidParameters(String productId,
                                                           String productQuantity,
                                                           String userId,
                                                           String responseTag,
                                                           String responseMessage){

        List<ProductCartBody> productCartBodyList = new ArrayList<>();
        productCartBodyList.add(new ProductCartBody(productId, productQuantity));
        CartBody cartBody = new CartBody(productCartBodyList, userId);

        CartRequest cartRequest = new CartRequest();
        cartRequest.setPostCartRequest(token, cartBody);

        Response response = cartRequest.executeRequestNoLog();
        assertEquals(responseMessage, response.body().jsonPath().get(responseTag).toString());
    }

    @Test
    public void forbiddenRegisterCart(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        token = "";

        String userId = "1";
        List<ProductCartBody> productCartBodyList = new ArrayList<>();
        productCartBodyList.add(new ProductCartBody());

        CartBody cartBody = new CartBody(productCartBodyList, userId);

        CartRequest cartRequest = new CartRequest();
        cartRequest.setPostCartRequest(token, cartBody);

        ValidatableResponse response = cartRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }
}
