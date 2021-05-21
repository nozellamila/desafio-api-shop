package com.desafioapishop.utils;

import com.desafioapishop.GlobalParameters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {

    public static void executeInitialQuery(){
        Connection connection = null;
        GlobalParameters globalParameters = new GlobalParameters();

        String queriesPath = System.getProperty("user.dir")+"/src/test/java/com/desafioapishop/querys/";
        String query = GeneralUtils.readFileToAString(queriesPath + "DBPopulate.sql");
        try {
            //Class.forName("org.h2.Driver");
            DriverManager.registerDriver(new org.h2.Driver());
            Statement stmt = null;
            connection = DriverManager.getConnection(globalParameters.DB_URL, globalParameters.DB_USER, globalParameters.DB_PASSWORD);

            stmt = connection.createStatement();
            stmt.execute(query);

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
