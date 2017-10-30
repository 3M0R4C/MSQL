/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands.delete;

import com.m0r4.soft.commands.edit.*;
import com.m0r4.soft.methods.create.*;
import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.methods.MSQLMethod;
import com.m0r4.soft.psql.QueryManager;
import com.m0r4.soft.util.MSQLManual;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class DeleteRecord extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("TABLE", null);
        OP_PARAMETERS.put("CONDITION", null);
    }

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "RECORD";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to delete records from the selected table.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("TABLE", "Name for the role being created.");
                parameters.put("CONDITION", "Optional: Filtering to be applied before deleting records.");
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
        PARAMETERS.put("TABLE", parameters[0]);
    }

    @Override
    protected void execute() {
        try {
            String sql = "";
            String table = PARAMETERS.get("TABLE");
            String condition = OP_PARAMETERS.get("CONDITION");
            sql="DELETE FROM "+table+"\n";
            if(condition!=null){
                sql = sql+" WHERE "+condition;
            }
            MSQLConsole.printDebug(sql);
            QueryManager.executeQuery(sql);
            MSQLConsole.printResult("Record successfully deleted!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
