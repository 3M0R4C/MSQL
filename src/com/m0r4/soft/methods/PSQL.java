/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.methods;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.psql.MSQLConnection;
import com.m0r4.soft.psql.QueryManager;
import com.m0r4.soft.util.MSQLManual;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author e.mora.c
 */
public class PSQL extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("COMMAND", null);
    }

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "PSQL";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to execut a PSQL command.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("COMMAND", "Command to be executed.");
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
        PARAMETERS.put("COMMAND", parameters[0]);
    }

    @Override
    protected void execute() {
        try {            
            String command = PARAMETERS.get("COMMAND");
            MSQLConsole.printDebug(command);
            if(command.toUpperCase().startsWith("SELECT"))
                QueryManager.executeQueryWithResultSet(command);
            else
                QueryManager.executeQuery(command);
            
            MSQLConsole.printResult("Command successfully executed!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }

}
