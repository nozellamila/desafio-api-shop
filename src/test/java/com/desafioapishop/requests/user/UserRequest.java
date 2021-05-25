package com.desafioapishop.requests.user;

import com.desafioapishop.GlobalParameters;
import com.desafioapishop.bases.RequestBase;
import com.desafioapishop.requests.auth.AuthBody;
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
        headers.put("Authorization", token);
        jsonBody = userBody;
        method = Method.PUT;
        requestService = "/users/" + userId.toString();
    }

    public void setDeleteUserRequest(String token, String userId){
        headers.put("Authorization", token);
        method = Method.DELETE;
        requestService = "/users/" + userId;
    }

    public void setGetOneUserRequest(String token, Integer userId){
        headers.put("Authorization", token);
        method = Method.GET;
        requestService = "/users/" + userId.toString();
    }

    public void setGetUserWithParamsRequest(String token, Map<String, String> requestParameters){
        headers.put("Authorization", token);
        method = Method.GET;
        queryParameters = requestParameters;
    }
}
