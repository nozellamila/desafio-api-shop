package com.desafioapishop.bases;

import static io.restassured.RestAssured.*;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import com.desafioapishop.GlobalParameters;
import com.desafioapishop.utils.RestAssuredUtils;
import com.desafioapishop.utils.enums.AuthenticationType;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.*;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class RequestBase {
    protected String url;
    protected String requestService;
    protected Method method = null;
    protected Object jsonBody = null;
    protected Map<String, String> headers = new HashMap<String, String>();
    protected Map<String, String> cookies = new HashMap<String, String>();
    protected Map<String, String> queryParameters = new HashMap<String, String>();
    protected AuthenticationType authenticationType = AuthenticationType.NONE;
    protected String authenticatorUser = GlobalParameters.AUTHENTICATOR_USER;
    protected String authenticatorPassword = GlobalParameters.AUTHENTICATOR_PASSWORD;

    public RequestBase(){
        config = RestAssuredConfig.newConfig().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL));
        enableLoggingOfRequestAndResponseIfValidationFails();
        headers.put("content-type", "application/json");
        //headers.put("Authorization", "Bearer "+GlobalParameters.TOKEN); //<== EXEMPLO DE COMO ADICIONAR TOKEN DEFAULT CASO NECESSÁRIO
    }

    public ValidatableResponse executeRequest() {
        Response response = RestAssuredUtils.executeRestRequest(url, requestService, method, headers, cookies, queryParameters, jsonBody, authenticatorUser, authenticatorPassword, authenticationType);
        //ExtentReportsUtils.addRestTestInfo(url, requestService, method.toString(), headers, cookies, queryParameters, jsonBody, authenticationType, authenticatorUser, authenticatorPassword, response);

        return response.then();
    }

    public Response executeRequestNoLog() {
        Response response = RestAssuredUtils.executeRestRequest(url, requestService, method, headers, cookies, queryParameters, jsonBody, authenticatorUser, authenticatorPassword, authenticationType);

        return response;
    }


    public void removeHeader(String header){
        headers.remove(header);
    }

    public void removeCookie(String cookie){
        cookies.remove(cookie);
    }

    public void removeQueryParameters(String parameter){
        queryParameters.remove(parameter);
    }

    public void setMethod(Method method){
        this.method = method;
    }
}

