/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.methods;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.psql.MSQLConnection;
import com.m0r4.soft.util.MSQLManual;
import java.io.Console;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class Connect extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("HOST", null);
        PARAMETERS.put("PORT", null);
        PARAMETERS.put("USER", null);
        PARAMETERS.put("DATABASE", null);
    }

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "CONNECT";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to connect and create a session with the database server.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("HOST", "Host where the database is running.");
                parameters.put("PORT", "Port where the database is listening.");
                parameters.put("USER", "User to authenticate");
                parameters.put("DATABASE", "Database name where the session will be started.");
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
        PARAMETERS.put("HOST", parameters[0]);
        PARAMETERS.put("PORT", parameters[1]);
        PARAMETERS.put("USER", parameters[2]);
        PARAMETERS.put("DATABASE", parameters[3]);
    }

    @Override
    protected void execute() {
        try {
            Console cons;
            char[] passwd = null;
            if ((cons = System.console()) != null &&
                (passwd = cons.readPassword("[%s]", "Password:")) != null) {                
            }
            MSQLConnection.getInstance()
                    .withDatabase(PARAMETERS.get("DATABASE"))
                    .withHost(PARAMETERS.get("HOST"))
                    .withPassword(Arrays.toString(passwd))
                    .withUser(PARAMETERS.get("USER"))
                    .withPort(PARAMETERS.get("PORT"))
                    .connect();
            MSQLConsole.printResult("Successfull connection!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }

}
