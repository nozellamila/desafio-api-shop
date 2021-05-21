package com.desafioapishop.requests.user;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.RequestBase;
import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.utils.enums.AuthenticationType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.Map;

public class UserRequest extends RequestBase {

    public UserRequest(){
        GlobalParameters globalParameters = new GlobalParameters();
        url = globalParameters.URL_DEFAULT;
        requestService = "/users";
    }

    public void setPostUserRequest(UserBody userBody){
        jsonBody = userBody;
        method = Method.POST;
    }

    public void setPutUserRequest(String token, UserBody userBody, Integer userId){
        GlobalParameters globalParameters = new GlobalParameters();
        headers.put("Authorization", token);
        jsonBody = userBody;
        method = Method.PUT;
        requestService = "/users/" + userId.toString();
    }

    public void setDeleteUserRequest(Integer userId){
        method = Method.DELETE;
        requestService = "/users/" + userId.toString();
    }

    public void setGetOneUserRequest(Integer userId){
        method = Method.GET;
        requestService = "/users/" + userId.toString();
    }

    public void setGetUserWithParamsRequest(Map<String, String> requestParameters){
        method = Method.GET;
        queryParameters = requestParameters;
    }

    public void setRequestMethod(Method requestMethod){
        method = requestMethod;
    }
}
