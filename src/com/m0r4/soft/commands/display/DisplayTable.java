/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands.display;

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
public class DisplayTable extends MSQLMethod {

    @Override
    protected void initParameters() {
        OP_PARAMETERS.put("NAME", null);
    }

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "TABLE";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to display tables  list on the connected database.";
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
                " pg_catalog.pg_tables \n"+
                "WHERE tablename= ?";
            }else{
                sql="SELECT schemaname, tablename FROM pg_catalog.pg_tables;";
            }
            
            MSQLConsole.printDebug(sql);
            if(name!=null){
                QueryManager.executeQueryWithResultSet(sql,name);
                sql="SELECT *\n" +
                "FROM information_schema.columns\n" +
                "WHERE table_name   = ?";
                QueryManager.executeQueryWithResultSet(sql,name);
            }else{
                QueryManager.executeQueryWithResultSet(sql);
            }
            MSQLConsole.printResult("Tables successfully displayed!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
