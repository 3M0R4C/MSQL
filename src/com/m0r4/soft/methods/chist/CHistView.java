/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.methods.chist;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.methods.MSQLMethod;
import com.m0r4.soft.psql.QueryManager;
import com.m0r4.soft.util.MSQLConstants;
import com.m0r4.soft.util.MSQLManual;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class CHistView extends MSQLMethod {

    @Override
    protected void initParameters() {
        OP_PARAMETERS.put("SEARCH", null);
    }

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "VIEW";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to display executed commands.";
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
    }

    @Override
    protected void execute() {
        try {
            StringBuilder hist=new StringBuilder();
            String search = OP_PARAMETERS.get("SEARCH");
            hist.append("COMMAND HISTORY\n").append(MSQLConstants.LINE_BREAK).append("\n");
            int i = 0;
            for(String entry : MSQLConsole.getCOMMANDS_HIST()){
                if(search==null || entry.toUpperCase().contains(search.toUpperCase()))
                    hist.append(String.format("%d. %s\n", i+1,entry.substring(entry.indexOf(".")+1)));
                i++;
            }
            if(i==0){
                hist.append("There are no inputs yet.\n");
            }
            hist.append(MSQLConstants.LINE_BREAK);
            MSQLConsole.printResult(hist.toString());
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
