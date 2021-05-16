package com.desafioapishop.requests.auth;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.RequestBase;
import com.desafioapishop.utils.enums.AuthenticationType;
import io.restassured.http.Method;

public class AuthRequest extends RequestBase {

    public AuthRequest(AuthBody authBody){
        GlobalParameters globalParameters = new GlobalParameters();
        url = globalParameters.URL_DEFAULT;
        requestService = "/auth";
        method = Method.POST;
        jsonBody = authBody;
    }
}
