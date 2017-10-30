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
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class Disconnect  extends MSQLMethod{

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "DISCONNECT";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to disconnect from current session.";
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
        try{
            if(MSQLConnection.getInstance().disconnect()){
                MSQLConsole.printInfo("Disconnected from existing session.");
            }else{
                MSQLConsole.printErr("No active connections were found.");                 
            }            
        }catch(SQLException ex){
            MSQLConsole.print(ex);
        }
    }

    
}
