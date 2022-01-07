package com.desafioapishop.bases;

import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.utils.AuthUtils;
import com.desafioapishop.utils.DBUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

    protected String token;

    @BeforeAll
    public static void beforeSuite(){

        DBUtils.executeInitialQuery();
    }

    @BeforeEach
    public void beforeMethod(){
        AuthBody authBody = new AuthBody();
        AuthUtils authUtils = new AuthUtils();
        token = authUtils.generateToken(authBody);
    }
}
