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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName(value = "Testes de atualização de produto")
public class UpdateProductTests extends TestBase {

    @Test
    public void successfulUpdateProduct(){
        int expectedStatusCode = HttpStatus.SC_OK;
        String expectedMessage = "Produto atualizado com sucesso";

        new GlobalParameters();

        int random = new Random().nextInt(100);
        String name = GlobalParameters.TOREPEAT_PRODUCTNAME;
        Float price = 65.0f;
        String description = "product " + random + " - description";
        Integer quantity = 10;

        ProductRequest productRequest = new ProductRequest();
        ProductBody productBody = new ProductBody(name, price, description, quantity);
        productRequest.setPutProductRequest(token, productBody, "1");

        ValidatableResponse response = productRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));
    }

    @Test
    public void shouldNotUpdateWithDuplicatedName(){
        int expectedStatusCode = HttpStatus.SC_CONFLICT;

        new GlobalParameters();

        String name = GlobalParameters.TOREPEAT_PRODUCTNAME;
        Float price = 20.0f;
        String description = "Product Description";
        Integer quantity = 10;

        ProductRequest productRequest = new ProductRequest();
        ProductBody productBody = new ProductBody(name, price, description, quantity);
        productRequest.setPutProductRequest(token, productBody, "2");

        ValidatableResponse response = productRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo("Produto já cadastrado com o nome: " + name));
    }

    @ParameterizedTest(name = "{index} => name={0}, price={1}, quantity={2}, message={3}")
    @CsvFileSource(resources = "/data/product/productValidations.csv")
    public void invalidParametersUpdateProduct(String name, Float price, Integer quantity, String message){
        int expectedStatusCode = HttpStatus.SC_BAD_REQUEST;
        String description = "";

        ProductRequest productRequest = new ProductRequest();
        ProductBody productBody = new ProductBody(name, price, description, quantity);
        productRequest.setPutProductRequest(token, productBody, "1");

        Response response = productRequest.executeRequestNoLog();

        assertEquals(response.statusCode(), expectedStatusCode);
        assertEquals(message, response.body().jsonPath().get("erro").toString());
    }

    @Test
    public void shouldNotUpdateProductForNonAdminProduct(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        new GlobalParameters();

        AuthBody authBody = new AuthBody(GlobalParameters.NONADMIN_USER, GlobalParameters.NONADMIN_PASSWORD);
        AuthUtils authUtils = new AuthUtils();
        token = authUtils.generateToken(authBody);

        String name = GlobalParameters.TOREPEAT_PRODUCTNAME;
        Float price = 20.0f;
        String description = "Product Description";
        Integer quantity = 10;

        ProductRequest productRequest = new ProductRequest();
        ProductBody productBody = new ProductBody(name, price, description, quantity);
        productRequest.setPutProductRequest(token, productBody, "2");

        ValidatableResponse response = productRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }

    @Test
    public void forbiddenUpdateProduct(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        token = "";

        int random = new Random().nextInt(100);
        String name = "product" + random;
        Float price = 20.0f;
        String description = "product " + random + " - description";
        Integer quantity = 10;

        ProductRequest ProductRequest = new ProductRequest();
        ProductBody productBody = new ProductBody(name, price, description, quantity);
        ProductRequest.setPutProductRequest(token, productBody, "1");

        ValidatableResponse response = ProductRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }
}
