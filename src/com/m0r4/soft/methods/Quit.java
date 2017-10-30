/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.methods;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.commands.InvalidCommandException;
import com.m0r4.soft.util.MSQLManual;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class Quit extends MSQLMethod{    
    
    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "QUIT";
            }

            @Override
            public String getDescription() {
                return "Call this method to close MSQL console.";
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
    protected void initParameters() {
    }

    @Override
    protected void processParameters(String[] parameters) {
    }

    @Override
    protected void execute() {
        System.exit(0);
    }
    
}
