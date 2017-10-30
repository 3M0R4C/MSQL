/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands.delete;

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
public class DeleteSchema extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("NAME", null);
        PARAMETERS.put("MODE", null);
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
                return "This method allows the user to delete schemas on the connected database.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("NAME", "Name for the schema being deleted.");
                parameters.put("MODE", "FORCE or WEEK value.");
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
        PARAMETERS.put("NAME", parameters[0]);
        PARAMETERS.put("MODE", parameters[1]);
    }

    @Override
    protected void execute() {
        try {
            String sql = "";
            String name = PARAMETERS.get("NAME");
            String mode = PARAMETERS.get("MODE");
            
            if(!mode.equalsIgnoreCase("WEAK") && !mode.equalsIgnoreCase("FORCE"))
                throw new InvalidCommandException(mode);
            
            sql = "DROP SCHEMA "+name+" "+(mode.equalsIgnoreCase("FORCE")?"CASCADE":"RESTRICT");
            
            MSQLConsole.printDebug(sql);
            QueryManager.executeQuery(sql);
            MSQLConsole.printResult("Schema successfully deleted!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
