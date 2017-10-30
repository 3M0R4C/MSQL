/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands.edit;

import com.m0r4.soft.methods.create.*;
import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.commands.InvalidCommandException;
import com.m0r4.soft.methods.MSQLMethod;
import com.m0r4.soft.psql.QueryManager;
import com.m0r4.soft.util.MSQLManual;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class EditRecord extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("TABLE", null);
        PARAMETERS.put("COLS", null);
        PARAMETERS.put("VALUES", null);
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
                return "This method allows the user to edit records on the selected table.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("TABLE", "Name of table where record will be edited.");
                parameters.put("COLS", "Comma separated string with column names for values being edited.");
                parameters.put("VALUES", "Values to be applied.");
                parameters.put("CONDITION", "Optional: Filtering to be applied on information being edited");
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
        PARAMETERS.put("COLS", parameters[1]);
        PARAMETERS.put("VALUES", parameters[2]);
    }

    @Override
    protected void execute() {
        try {
            StringBuilder sb = new StringBuilder();
            String table = PARAMETERS.get("TABLE");
            String[] cols = PARAMETERS.get("COLS").split(",");
            String[] values = PARAMETERS.get("VALUES").split(",");
            String condition = OP_PARAMETERS.get("CONDITION");
            
            if(cols.length!=values.length)
                throw new InvalidCommandException(Arrays.toString(cols));
            sb.append("UPDATE ")
                    .append(table).append("\nSET ");
            for(int i=0;i<values.length;i++){
                sb.append(cols[i]).append("=?").append(i+1<values.length?",":"\n");
            }
            if(condition!=null)
                sb.append("WHERE ").append(condition).append("\n");
            MSQLConsole.printDebug(sb.toString());
            QueryManager.executeQuery(sb.toString(),values);
            MSQLConsole.printResult("Record successfully edited!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
