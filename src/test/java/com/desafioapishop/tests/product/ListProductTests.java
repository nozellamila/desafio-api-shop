package com.desafioapishop.tests.product;

import com.desafioapishop.bases.TestBase;
import com.desafioapishop.requests.product.ProductRequest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Execution(ExecutionMode.CONCURRENT)
public class ListProductTests extends TestBase {

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

}
