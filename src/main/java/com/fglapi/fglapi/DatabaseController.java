package com.fglapi.fglapi;

import org.mariadb.jdbc.*;
import com.google.gson.*;
import java.io.*;
import java.sql.*;

public class DatabaseController{
    
    private String dbLogin;
    private String dbPass;
    Connection connection;

    // Setup function. Todo: Make connection class field?
    public DatabaseController(){
        // Here we pull the db login and password from a config file. We use an input stream because we must package the program into a jar.
        setLogin();
    }  
    private void setLogin(){
        Reader config = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("/config.json")));
        JsonElement c_tree = JsonParser.parseReader(config);
        if(c_tree.isJsonObject()){
            JsonObject obj = c_tree.getAsJsonObject();
            JsonElement login = obj.get("db_login");
            JsonElement pass = obj.get("db_pass");
            dbLogin = login.getAsString();
            dbPass = pass.getAsString();
        }
    }
    public void _dbTest(){
        try{
            //connection string
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/test?user=" + dbLogin + "&password=" + dbPass);
            Statement statement = connection.createStatement(); //this creates the test statement. Let's select every column and value from a test table.
            ResultSet rs = statement.executeQuery("SELECT * FROM Test"); //select every column and value from table Test
            ResultSetMetaData rsmd = rs.getMetaData(); //get the metadata of the Re sultSet so we can loop through it
            statement.close(); //close the statement to prevent memory leaks.
            int colNum = rsmd.getColumnCount();
            while(rs.next()){ //Debug. Prints columns and values.
                String columnValue = "";
                for(int i = 1; i <= colNum; i++){
                    if(i > 1) columnValue += ", ";
                    columnValue += rs.getString(i);
                }
                System.out.println(String.format("%s", columnValue));
                columnValue = "";
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{ 
            try{
                connection.close(); //close to prevent memory leaks, even when an exception occurs
            }catch(SQLException e){
                System.out.println("Err: Failed to close SQL connection.");
            }
        }
    }
}