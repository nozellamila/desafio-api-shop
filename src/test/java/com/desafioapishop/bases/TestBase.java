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
    }

    @BeforeEach
    public void beforeMethod() throws InterruptedException {
        AuthBody authBody = new AuthBody();
        AuthUtils authUtils = new AuthUtils();

        DBUtils.cleanDB();

        Thread.sleep(3000);

        DBUtils.executeInitialQuery();

        Thread.sleep(3000);

        token = authUtils.generateToken(authBody);
    }
}
