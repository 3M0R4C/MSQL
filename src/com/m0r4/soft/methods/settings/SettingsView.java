/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.methods.settings;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.methods.MSQLMethod;
import com.m0r4.soft.util.MSQLConfiguration;
import com.m0r4.soft.util.MSQLConstants;
import com.m0r4.soft.util.MSQLManual;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class SettingsView extends MSQLMethod {

    @Override
    protected void initParameters() {
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
                return "This method allows the user to display current settings.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                return null;
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
            StringBuilder settings=new StringBuilder();
            settings.append("CURRENT SETTINGS\n").append(MSQLConstants.LINE_BREAK).append("\n");
            settings.append(MSQLConfiguration.getConfiguration());
            settings.append(MSQLConstants.LINE_BREAK);
            MSQLConsole.printResult(settings.toString());
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
