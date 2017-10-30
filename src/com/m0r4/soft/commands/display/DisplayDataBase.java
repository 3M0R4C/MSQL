/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands.display;

import com.m0r4.soft.commands.delete.*;
import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.methods.MSQLMethod;
import com.m0r4.soft.psql.QueryManager;
import com.m0r4.soft.util.MSQLManual;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class DisplayDataBase extends MSQLMethod {

    @Override
    protected void initParameters() {
        OP_PARAMETERS.put("NAME", null);
    }

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "DATABASE";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to display databases list on the connected server.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("NAME", "Optional: Provide database name to display specific database details.");
                return parameters;
            }

            @Override
            public Map<String, String> getCommandsMap() {
                return null;
            }
        }.toString());
    }

    @Override
    protected void processParameters(String[] parameters) {
    }

    @Override
    protected void execute() {
        try {
            String sql;
            String name = OP_PARAMETERS.get("NAME");
            if(name!=null){
                sql="SELECT *\n" +                
                "FROM\n" +
                " pg_database \n"+
                "WHERE datname= ?";
            }else{
                sql="SELECT\n" +
                " datname\n" +
                "FROM\n" +
                " pg_database;";
            }
            
            MSQLConsole.printDebug(sql);
            if(name!=null){
            QueryManager.executeQueryWithResultSet(sql,name);
            }else{
                QueryManager.executeQueryWithResultSet(sql);
            }
            MSQLConsole.printResult("Databases successfully displayed!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
