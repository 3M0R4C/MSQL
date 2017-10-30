/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.methods.chist;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.methods.MSQLMethod;
import com.m0r4.soft.util.MSQLManual;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class CHistExec extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("ID", null);
    }

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "EXEC";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to create databases on the connected server.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("SEARCH", "Optional: Used to filter commands by provided string.");
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
            PARAMETERS.put("ID", parameters[0]);
    }

    @Override
    protected void execute() {
        try {
            String id = PARAMETERS.get("ID");
            int i = 0;
            for(String entry : MSQLConsole.getCOMMANDS_HIST()){
                if(Integer.toString(i+1).equals(id)){
                    MSQLConsole.scheduleCommand(entry.substring(entry.indexOf(".")+1));
                    MSQLConsole.printResult("Task found! Executing: "+entry);
                    return;
                }
                i++;
            }
            MSQLConsole.printErr("Task not found!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
