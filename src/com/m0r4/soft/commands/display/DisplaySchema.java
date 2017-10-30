/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands.display;

import com.m0r4.soft.commands.delete.*;
import com.m0r4.soft.methods.create.*;
import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.commands.InvalidCommandException;
import com.m0r4.soft.methods.MSQLMethod;
import com.m0r4.soft.psql.QueryManager;
import com.m0r4.soft.util.MSQLManual;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class DisplaySchema extends MSQLMethod {

    @Override
    protected void initParameters() {
        OP_PARAMETERS.put("NAME", null);
    }

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "SCHEMA";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to display schemas list on the connected database.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("NAME", "Optional: Provide schema name to display specific schema details.");
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
                sql="select *\n" +
                "from information_schema.schemata"+
                " WHERE schema_name= ?";
            }else{
                sql="select schema_name from information_schema.schemata;";
            }
            
            MSQLConsole.printDebug(sql);
            if(name!=null){
                QueryManager.executeQueryWithResultSet(sql,name);
                sql="SELECT * FROM pg_catalog.pg_tables WHERE schemaname= ?";
                QueryManager.executeQueryWithResultSet(sql,name);
            }else{
                QueryManager.executeQueryWithResultSet(sql);
            }
            MSQLConsole.printResult("Schemas successfully displayed!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
