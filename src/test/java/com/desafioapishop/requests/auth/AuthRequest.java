package com.desafioapishop.requests.auth;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.RequestBase;
import com.desafioapishop.utils.enums.AuthenticationType;
import io.restassured.http.Method;

public class AuthRequest extends RequestBase {

    public AuthRequest(AuthBody authBody){
        authenticationType = AuthenticationType.OAUTH2;
        requestService = "/auth";
        method = Method.POST;
        jsonBody = authBody;
    }
}
