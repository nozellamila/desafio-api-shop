package com.desafioapishop.tests.product;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.requests.product.ProductBody;
import com.desafioapishop.requests.product.ProductRequest;
import com.desafioapishop.requests.user.UserBody;
import com.desafioapishop.requests.user.UserRequest;
import com.desafioapishop.utils.AuthUtils;
import com.desafioapishop.utils.DBUtils;
import com.desafioapishop.utils.steps.UserSteps;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import javax.swing.*;
import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Execution(ExecutionMode.CONCURRENT)
public class ProductTests extends TestBase {

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
        productRequest.setPutProductRequest(token, productBody, 1);

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
        productRequest.setPutProductRequest(token, productBody, 2);

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
        productRequest.setPutProductRequest(token, productBody, 1);

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
        productRequest.setPutProductRequest(token, productBody, 2);

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
        ProductRequest.setPutProductRequest(token, productBody, 1);

        ValidatableResponse response = ProductRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }

    @ParameterizedTest(name = "{index} => parameterKey={0}, parameterValue={1}, expectedReturn={2} ")
    @CsvFileSource(resources = "/data/product/productFilters.csv")
    public void listProductWithFilters(String parameterKey, String parameterValue, String expectedReturn){
        int expectedStatusCode = HttpStatus.SC_OK;

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put(parameterKey, parameterValue);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setGetProductWithParamsRequest(requestParameters);

        Response response = productRequest.executeRequestNoLog();

        assertEquals(response.statusCode(), expectedStatusCode);
        assertEquals(expectedReturn, response.body().jsonPath().get("content." + parameterKey).toString());
    }

    @ParameterizedTest(name = "{index} => id={0}, name={1}, price={2}, description={3}, quantity={4}")
    @CsvFileSource(resources = "/data/product/productInvalidFilters.csv")
    public void shouldNotFindProductToList(String id, String name, String price, String description, String quantity){
        int expectedStatusCode = HttpStatus.SC_NOT_FOUND;
        String expectedMessage = "Produto não encontrado";

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("id", id);
        requestParameters.put("name", name);
        requestParameters.put("price", price);
        requestParameters.put("description", description);
        requestParameters.put("quantity", quantity);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setGetProductWithParamsRequest(requestParameters);

        Response response = productRequest.executeRequestNoLog();

        assertEquals(response.statusCode(), expectedStatusCode);
        assertEquals(expectedMessage, response.body().jsonPath().get("message").toString());
    }

    @Test
    public void excludeProduct(){
        int expectedStatusCode = HttpStatus.SC_OK;
        String expectedMessage = "Produto excluído com sucesso";

        List<String> productId = DBUtils.getQueryResult("FindProductToExclude.sql");

        new GlobalParameters();

        ProductRequest productRequest = new ProductRequest();
        productRequest.setDeleteProductRequest(token, productId.get(0));

        ValidatableResponse response = productRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));
    }

    @Test
    public void shouldNotExcludeNonExistentProduct(){
        int expectedStatusCode = HttpStatus.SC_OK;
        String expectedMessage = "Nenhum produto excluído";

        new GlobalParameters();

        ProductRequest productRequest = new ProductRequest();
        productRequest.setDeleteProductRequest(token, "99999");

        ValidatableResponse response = productRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));
    }

    @Test
    public void shouldNotExcludeProductForNonAdminUser(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        new GlobalParameters();

        AuthBody authBody = new AuthBody(GlobalParameters.NONADMIN_USER, GlobalParameters.NONADMIN_PASSWORD);
        AuthUtils authUtils = new AuthUtils();
        token = authUtils.generateToken(authBody);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setDeleteProductRequest(token, "1");

        ValidatableResponse response = productRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }

    @Test
    public void shouldNotExcludeProductBelongingToACart(){
        UserSteps.insertUserCart(token);

        int expectedStatusCode = HttpStatus.SC_UNPROCESSABLE_ENTITY;
        String expectedMessage = "Não é possível excluir produto pertencente a algum carrinho";

        new GlobalParameters();

        String productId = "1";

        ProductRequest productRequest = new ProductRequest();
        productRequest.setDeleteProductRequest(token, productId);

        ValidatableResponse response = productRequest.executeRequest();
        response.statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedMessage));
    }

    @Test
    public void forbiddenExcludeProduct(){
        int expectedStatusCode = HttpStatus.SC_FORBIDDEN;

        token = "";

        ProductRequest ProductRequest = new ProductRequest();
        ProductRequest.setDeleteProductRequest(token, "1");

        ValidatableResponse response = ProductRequest.executeRequest();
        response.statusCode(expectedStatusCode);
    }
}
