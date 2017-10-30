/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.methods;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.psql.MSQLConnection;
import com.m0r4.soft.util.MSQLManual;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author e.mora.c
 */
public class Use extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("DATABASE", null);
    }

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "USE";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to switch the database which is connected to.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("DATABASE", "Database name which will be in use now.");
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
        PARAMETERS.put("DATABASE", parameters[0]);
    }

    @Override
    protected void execute() {
        String currentDB = null;
        try {            
            currentDB=MSQLConnection.getInstance().getDatabase();
            MSQLConnection.getInstance()
                    .withDatabase(PARAMETERS.get("DATABASE"))
                    .connect();
            MSQLConsole.printResult("Database switched.");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
            try {
                MSQLConnection.getInstance()
                        .withDatabase(currentDB)
                        .connect();
            } catch (SQLException ex1) {
                MSQLConsole.printErr("Not able to rollback. Session disconnected.");
                MSQLConsole.print(ex);
            }
        }
    }

}
