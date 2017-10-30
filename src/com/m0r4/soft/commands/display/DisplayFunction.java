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
public class DisplayFunction extends MSQLMethod {

    @Override
    protected void initParameters() {
        OP_PARAMETERS.put("NAME", null);
    }

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "FUNCTION";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to display functions list on the connected database.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("NAME", "Optional: Provide function name to display specific function details.");
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
                sql="select *  from pg_proc where proname= ?; ";
            }else{
                sql="select proname from pg_proc ; ";
            }
            
            MSQLConsole.printDebug(sql);
            if(name!=null){
            QueryManager.executeQueryWithResultSet(sql,name);
            }else{
                QueryManager.executeQueryWithResultSet(sql);
            }
            MSQLConsole.printResult("Functions successfully displayed!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
