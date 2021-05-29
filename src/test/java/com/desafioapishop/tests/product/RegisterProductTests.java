package com.desafioapishop.tests.product;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.requests.product.ProductBody;
import com.desafioapishop.requests.product.ProductRequest;
import com.desafioapishop.utils.AuthUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Execution(ExecutionMode.CONCURRENT)
public class RegisterProductTests extends TestBase {

    @Test
    public void successfulRegisterProduct(){
        int expectedStatusCode = HttpStatus.SC_CREATED;

        new GlobalParameters();

        int random = new Random().nextInt(100);
        String name = "product" + random;
        Float price = 20.0f;
        String description = "product " + random + " - description";
        Integer quantity = 10;

        ProductRequest productRequest = new ProductRequest();
        ProductBody productBody = new ProductBody(name, price, description, quantity);
        productRequest.setPostProductRequest(token, productBody);

        ValidatableResponse response = productRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("name", equalTo(name),
                "description", equalTo(description),
                "quantity", equalTo(quantity));
    }

    @Test
    public void shouldNotRegisterDuplicatedName(){
        int expectedStatusCode = HttpStatus.SC_UNPROCESSABLE_ENTITY;

        new GlobalParameters();

        String name = GlobalParameters.TOREPEAT_PRODUCTNAME;
        Float price = 20.0f;
        String description = "Product Description";
        Integer quantity = 10;

        ProductRequest productRequest = new ProductRequest();
        ProductBody productBody = new ProductBody(name, price, description, quantity);
        productRequest.setPostProductRequest(token, productBody);

        ValidatableResponse response = productRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo("Produto já cadastrado com o nome: " + name));
    }

    @ParameterizedTest(name = "{index} => name={0}, price={1}, quantity={2}, message={3}")
    @CsvFileSource(resources = "/data/product/productValidations.csv")
    public void invalidParametersRegisterProduct(String name, Float price, Integer quantity, String message){
        int expectedStatusCode = HttpStatus.SC_BAD_REQUEST;
        String description = "";

        ProductRequest productRequest = new ProductRequest();
        ProductBody productBody = new ProductBody(name, price, description, quantity);
        productRequest.setPostProductRequest(token, productBody);

        Response response = productRequest.executeRequestNoLog();

        assertEquals(response.statusCode(), expectedStatusCode);
        assertEquals(message, response.body().jsonPath().get("erro").toString());
    }

    @Test
    public void shouldNotRegisterProductForNonAdminProduct(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        new GlobalParameters();

        AuthBody authBody = new AuthBody(GlobalParameters.NONADMIN_USER, GlobalParameters.NONADMIN_PASSWORD);
        AuthUtils authUtils = new AuthUtils();
        token = authUtils.generateToken(authBody);

        int random = new Random().nextInt(100);
        String name = "product" + random;
        Float price = 20.0f;
        String description = "product " + random + " - description";
        Integer quantity = 10;

        ProductRequest ProductRequest = new ProductRequest();
        ProductBody productBody = new ProductBody(name, price, description, quantity);
        ProductRequest.setPostProductRequest(token, productBody);

        ValidatableResponse response = ProductRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }

    @Test
    public void forbiddenRegisterProduct(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        token = "";

        int random = new Random().nextInt(100);
        String name = "product" + random;
        Float price = 20.0f;
        String description = "product " + random + " - description";
        Integer quantity = 10;

        ProductRequest ProductRequest = new ProductRequest();
        ProductBody productBody = new ProductBody(name, price, description, quantity);
        ProductRequest.setPostProductRequest(token, productBody);

        ValidatableResponse response = ProductRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }
}
