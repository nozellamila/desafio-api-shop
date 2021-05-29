package com.desafioapishop.tests.product;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.requests.product.ProductRequest;
import com.desafioapishop.utils.AuthUtils;
import com.desafioapishop.utils.DBUtils;
import com.desafioapishop.utils.steps.UserSteps;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@Execution(ExecutionMode.CONCURRENT)
public class DeleteProductTests extends TestBase {

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
