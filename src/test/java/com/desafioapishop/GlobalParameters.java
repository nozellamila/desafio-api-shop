package com.desafioapishop;

import com.desafioapishop.requests.auth.AuthBody;
import com.desafioapishop.requests.auth.AuthRequest;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GlobalParameters {
    public static String ENVIROMENT;
    public static String URL_DEFAULT;
    public static String REPORT_NAME;
    public static String REPORT_PATH;
    public static String DB_URL;
    public static String DB_NAME;
    public static String DB_USER;
    public static String DB_PASSWORD;
    public static String AUTHENTICATOR_USER;
    public static String AUTHENTICATOR_PASSWORD;
    public static String NONADMIN_USER;
    public static String NONADMIN_PASSWORD;
    public static String TOEXCLUDE_USER;

    private Properties properties;

    public GlobalParameters(){
        properties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("src/test/globalParameters.properties");
            properties.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        REPORT_NAME = properties.getProperty("report.name");
        REPORT_PATH = properties.getProperty("report.path");
        ENVIROMENT = properties.getProperty("enviroment");

        if(ENVIROMENT.equals("hml")){
            DB_URL = properties.getProperty("hml.db.url");
            DB_NAME = properties.getProperty("hml.db.name");
            DB_USER = properties.getProperty("hml.db.user");
            DB_PASSWORD = properties.getProperty("hml.db.password");
            URL_DEFAULT = properties.getProperty("hml.url.default");
            AUTHENTICATOR_USER = properties.getProperty("hml.authenticator.user");
            AUTHENTICATOR_PASSWORD = properties.getProperty("hml.authenticator.password");
            NONADMIN_USER = properties.getProperty("hml.nonadmin.user");
            NONADMIN_PASSWORD = properties.getProperty("hml.nonadmin.password");
            TOEXCLUDE_USER = properties.getProperty("hml.toexclude.user");
        }

        if(ENVIROMENT.equals("dev")){
            DB_URL = properties.getProperty("dev.db.url");
            DB_NAME = properties.getProperty("dev.db.name");
            DB_USER = properties.getProperty("dev.db.user");
            DB_PASSWORD = properties.getProperty("dev.db.password");
            URL_DEFAULT = properties.getProperty("dev.url.default");
            AUTHENTICATOR_USER = properties.getProperty("dev.authenticator.user");
            AUTHENTICATOR_PASSWORD = properties.getProperty("dev.authenticator.password");
            NONADMIN_USER = properties.getProperty("dev.nonadmin.user");
            NONADMIN_PASSWORD = properties.getProperty("dev.nonadmin.password");
            TOEXCLUDE_USER = properties.getProperty("dev.toexclude.user");
        }
    }

}
