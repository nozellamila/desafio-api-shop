package com.desafioapishop.utils;

import com.desafioapishop.GlobalParameters;

import java.sql.*;
import java.util.ArrayList;

public class DBUtils {

    public static void executeInitialQuery(){
        Connection connection = null;
        GlobalParameters globalParameters = new GlobalParameters();

        ArrayList<String> dbRows = getCurrentDBRows();
        if (dbRows == null){
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

    public static ArrayList<String> getCurrentDBRows(){
        ArrayList<String> arrayList = null;
        Connection connection = null;
        GlobalParameters globalParameters = new GlobalParameters();

        String queriesPath = System.getProperty("user.dir")+"/src/test/java/com/desafioapishop/querys/";
        String query = GeneralUtils.readFileToAString(queriesPath + "DBVerify.sql");
        query = query.replace("$email", globalParameters.AUTHENTICATOR_USER);

        try {
            DriverManager.registerDriver(new org.h2.Driver());
            Statement stmt = null;
            connection = DriverManager.getConnection(globalParameters.DB_URL, globalParameters.DB_USER, globalParameters.DB_PASSWORD);

            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if(!rs.isBeforeFirst()){
                return null;
            }
            else{
                int numberOfColumns;
                ResultSetMetaData metadata=null;

                arrayList = new ArrayList<String>();
                metadata = rs.getMetaData();
                numberOfColumns = metadata.getColumnCount();

                while (rs.next()) {
                    int i = 1;
                    while(i <= numberOfColumns) {
                        arrayList.add(rs.getString(i++));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public static ArrayList<String> getQueryResult(String queryName){
        ArrayList<String> arrayList = null;
        Connection connection = null;
        GlobalParameters globalParameters = new GlobalParameters();

        String queriesPath = System.getProperty("user.dir")+"/src/test/java/com/desafioapishop/querys/";
        String query = GeneralUtils.readFileToAString(queriesPath + queryName);

        try {
            DriverManager.registerDriver(new org.h2.Driver());
            Statement stmt = null;
            connection = DriverManager.getConnection(globalParameters.DB_URL, globalParameters.DB_USER, globalParameters.DB_PASSWORD);

            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if(!rs.isBeforeFirst()){
                return null;
            }
            else{
                int numberOfColumns;
                ResultSetMetaData metadata=null;

                arrayList = new ArrayList<String>();
                metadata = rs.getMetaData();
                numberOfColumns = metadata.getColumnCount();

                while (rs.next()) {
                    int i = 1;
                    while(i <= numberOfColumns) {
                        arrayList.add(rs.getString(i++));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }
}
