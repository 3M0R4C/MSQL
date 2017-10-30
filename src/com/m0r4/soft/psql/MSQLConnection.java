/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.psql;

import com.m0r4.soft.MSQLConsole;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author e.mora.c
 */
public class MSQLConnection  {

   private static MSQLConnection msqlConnection = null;
   private Connection connection = null;
   private String host;
   private String port;
   private String database;
   private String user;
   private String password;
   
   public static MSQLConnection getInstance() {
      if(msqlConnection == null) {
         msqlConnection = new MSQLConnection();
      }
      return msqlConnection;
   }
    
   public void connect() throws SQLException{
        disconnect();
        String connectionStr = "jdbc:postgresql://"+host+":"+port+"/"+database;
        connection = DriverManager.getConnection(connectionStr, user,password);

        if (isConnected()) {
            MSQLConsole.printDebug("Connected to: " + connectionStr);
        } else {
            throw new SQLException("Failed to make connection!");
        }
   }
   
   public static Connection getConnection() throws SQLException{
        if(getInstance().connection==null)
            throw new SQLException("No database connection established! Please execute CONNECT command before any other action.");           
        return getInstance().connection;
   }
   
   public boolean disconnect() throws SQLException{
        if(isConnected()){
            connection.close();
            connection=null;
            return true;
        }
        return false;
   }

    public MSQLConnection withHost(String host) {
        this.host = host;
        return msqlConnection;
    }

    public MSQLConnection withPort(String port) {
        this.port = port;
        return msqlConnection;
    }

    public MSQLConnection withDatabase(String database) {
        this.database = database;
        return msqlConnection;
    }

    public MSQLConnection withUser(String user) {
        this.user = user;
        return msqlConnection;
    }

    public MSQLConnection withPassword(String password) {
        this.password = password;
        return msqlConnection;
    }

    public String getUser() {
        return user;
    }

    public String getDatabase() {
        return database;
    }       
    
    public boolean isConnected() {
        return connection!=null;
    }
   
}
