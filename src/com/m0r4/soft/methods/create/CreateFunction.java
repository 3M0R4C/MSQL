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
public class CreateFunction extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("NAME", null);
        PARAMETERS.put("OWNER", null);
        PARAMETERS.put("RETURN_TYPE", null);
        PARAMETERS.put("COMMAND", null);
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
                return "This method allows the user to create functions on the connected database.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("NAME", "Name for the function being created.");
                parameters.put("OWNER", "Function owner.");
                parameters.put("RETURN_TYPE", "Return type for function being created");
                parameters.put("COMMAND", "Command in SQL language which will be executed.");
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
        PARAMETERS.put("RETURN_TYPE", parameters[2]);
        PARAMETERS.put("COMMAND", parameters[3]);
    }

    @Override
    protected void execute() {
        try {
            StringBuilder sb = new StringBuilder();
            String name = PARAMETERS.get("NAME");
            String owner = PARAMETERS.get("OWNER");
            String returnType = PARAMETERS.get("RETURN_TYPE");
            String command = PARAMETERS.get("COMMAND");
            
            sb.append("CREATE FUNCTION \"").append(name).append("\"()\n")
                    .append("    RETURNS ").append(returnType)
                    .append("\n").append("    LANGUAGE 'sql'\n\nAS $BODY$\n")
                    .append(command).append("\n$BODY$;\n\nALTER FUNCTION ")
                    .append(name).append("()\nOWNER TO ")
                    .append(owner)
                    .append(";");
            
            MSQLConsole.printDebug(sb.toString());
            QueryManager.executeQuery(sb.toString());
            MSQLConsole.printResult("Function successfully created!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
