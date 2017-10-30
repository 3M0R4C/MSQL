/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.methods.create;

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
public class CreateSchema extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("NAME", null);
        PARAMETERS.put("OWNER", null);
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
                return "This method allows the user to create schemas on the connected database.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("NAME", "Name for the schema being created.");
                parameters.put("ONWER", "Owner for schema being created.");
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
        PARAMETERS.put("OWNER", parameters[1]);
    }

    @Override
    protected void execute() {
        try {
            StringBuilder sb = new StringBuilder();
            String name = PARAMETERS.get("NAME");
            String owner = PARAMETERS.get("OWNER");
            sb.append("CREATE SCHEMA \"")
                    .append(name)
                    .append("\"\n" + "    AUTHORIZATION ")
                    .append(owner)
                    .append(";");     
            MSQLConsole.printDebug(sb.toString());
            QueryManager.executeQuery(sb.toString());
            MSQLConsole.printResult("Schema successfully created!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
