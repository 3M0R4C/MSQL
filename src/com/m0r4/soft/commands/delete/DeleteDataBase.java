/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands.delete;

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
public class DeleteDataBase extends MSQLMethod {

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
                return "DATABASE";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to delete databases on the connected server.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("NAME", "Name for the database being created.");
                parameters.put("OWNER", "Owner name for the database being created.");
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
            String permissions = PARAMETERS.get("PERMISSIONS");
            String connLimit = OP_PARAMETERS.get("CONN_LIMIT");
            String parents = OP_PARAMETERS.get("PARENT_ROLES");
            
            MSQLConsole.printDebug(sb.toString());
            QueryManager.executeQuery(sb.toString());
            MSQLConsole.printResult("Database successfully deleted!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
