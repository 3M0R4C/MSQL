/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.methods.settings;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.commands.InvalidCommandException;
import com.m0r4.soft.methods.MSQLMethod;
import com.m0r4.soft.util.MSQLConfiguration;
import com.m0r4.soft.util.MSQLManual;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class SettingsSet extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("ID", null);
        PARAMETERS.put("VALUE", null);
    }

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "SET";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to cmodify current settings.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("ID", "Used to specify which setting will be modified..");
                parameters.put("VALUE", "Used to provide the new value.");
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
            PARAMETERS.put("VALUE", parameters[1]);
    }

    @Override
    protected void execute() {
        try {
            String id = PARAMETERS.get("ID");
            String value = PARAMETERS.get("VALUE");
            switch(id.toUpperCase()){
                case "LOGLEVEL":
                    try{
                        MSQLConfiguration.LEVEL newLevel = MSQLConfiguration.LEVEL.valueOf(value.toUpperCase());
                        MSQLConfiguration.LOGLEVEL=newLevel;
                    }catch(Exception ex){
                        throw new InvalidCommandException(value);
                    }
                    break;
                default:
                    throw new InvalidCommandException(id);
            } 
            MSQLConsole.printResult("Settings updated!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
