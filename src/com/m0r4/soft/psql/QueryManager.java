/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.psql;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.util.MSQLConstants;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author e.mora.c
 */
public class QueryManager {
    
    public static void executeQuery(String query,String... parameters) throws Exception{
        Connection conn = MSQLConnection.getConnection();
        try (PreparedStatement st = conn.prepareStatement(query)) {
            setStatementParameters(st, parameters);

            st.executeUpdate();
        }catch (Exception e) {
            throw e;
        }
    }
    
    public static void executeQueryWithResultSet(String query,String... parameters) throws Exception{
        Connection conn = MSQLConnection.getConnection();
        try (PreparedStatement st = conn.prepareStatement(query)) {
            setStatementParameters(st, parameters);
            try (ResultSet rs = st.executeQuery()) {
                MSQLConsole.printInfo("QUERY RESULT:");
                System.out.println(MSQLConstants.LINE_BREAK);
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",\t");
                    System.out.print(rsmd.getColumnName(i));
                }
                System.out.println("");
                System.out.println(MSQLConstants.LINE_BREAK);
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",\t");
                        String columnValue = rs.getString(i);
                        System.out.print(columnValue);
                    }
                    System.out.println("");
                }
            }catch (Exception e) {
                throw e;
            }
        }catch (Exception e) {
            throw e;
        }
    }
    
    private static void setStatementParameters(PreparedStatement st, String[] parameters) throws Exception{
        for(int i = 1; i<=parameters.length; i++){
            try{
                Double value = Double.parseDouble(parameters[i-1]);
                st.setDouble(i,value);
            }catch(Exception ex){
                if(parameters[i-1].startsWith("@"))
                    parameters[i-1]=parameters[i-1].substring(1);
                try{
                    Double value = Double.parseDouble(parameters[i-1]);
                    st.setDouble(i,value);
                }catch(Exception e){
                    st.setString(i,parameters[i-1]);
                }
                st.setString(i,parameters[i-1]);
            }
        }
    }
}
