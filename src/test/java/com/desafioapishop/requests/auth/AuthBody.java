package com.desafioapishop.requests.auth;

import com.desafioapishop.GlobalParameters;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthBody {
    private String email;
    private String password;

    public AuthBody(){
        GlobalParameters globalParameters = new GlobalParameters();
        this.email = GlobalParameters.AUTHENTICATOR_USER;
        this.password = GlobalParameters.AUTHENTICATOR_PASSWORD;
    }

}
