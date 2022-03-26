package com.desafioapishop;

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
    public static String NONADMIN_USERID;
    public static String NONADMIN_USER;
    public static String NONADMIN_PASSWORD;
    public static String TONOTEXCLUDECART_USERID;
    public static String TOREGISTERCART_USERID;
    public static String TOCANCELCART_USERID;
    public static String TOFINISHBUY_USERID;
    public static String TOEXCLUDE_USERID;
    public static String TOEXCLUDE_USER;
    public static String TOREPEAT_PRODUCTNAME;

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

        AUTHENTICATOR_USER = properties.getProperty("properties.authenticator.user");
        AUTHENTICATOR_PASSWORD = properties.getProperty("properties.authenticator.password");
        NONADMIN_USERID = properties.getProperty("properties.nonadmin.userid");
        NONADMIN_USER = properties.getProperty("properties.nonadmin.user");
        NONADMIN_PASSWORD = properties.getProperty("properties.nonadmin.password");
        TONOTEXCLUDECART_USERID = properties.getProperty("properties.tonotexcludecart.userid");
        TOCANCELCART_USERID = properties.getProperty("properties.tocancelcart.userid");
        TOFINISHBUY_USERID = properties.getProperty("properties.tofinishbuy.userid");
        TOREGISTERCART_USERID = properties.getProperty("properties.toregistercart.userid");
        TOEXCLUDE_USERID = properties.getProperty("properties.toexclude.userid");
        TOEXCLUDE_USER = properties.getProperty("properties.toexclude.user");
        TOREPEAT_PRODUCTNAME = properties.getProperty("properties.torepeat.productname");

        if(ENVIROMENT.equals("hml")){
            DB_URL = properties.getProperty("hml.db.url");
            DB_NAME = properties.getProperty("hml.db.name");
            DB_USER = properties.getProperty("hml.db.user");
            DB_PASSWORD = properties.getProperty("hml.db.password");
            URL_DEFAULT = properties.getProperty("hml.url.default");
        }

        if(ENVIROMENT.equals("dev")){
            DB_URL = properties.getProperty("dev.db.url");
            DB_NAME = properties.getProperty("dev.db.name");
            DB_USER = properties.getProperty("dev.db.user");
            DB_PASSWORD = properties.getProperty("dev.db.password");
            URL_DEFAULT = properties.getProperty("dev.url.default");
        }
    }
}
