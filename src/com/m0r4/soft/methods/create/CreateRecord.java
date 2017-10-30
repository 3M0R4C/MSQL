/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.methods.create;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.commands.InvalidCommandException;
import com.m0r4.soft.methods.MSQLMethod;
import com.m0r4.soft.psql.QueryManager;
import com.m0r4.soft.util.MSQLManual;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class CreateRecord extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("TABLE", null);
        PARAMETERS.put("COLS", null);
        PARAMETERS.put("VALUES", null);
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
                return "This method allows the user to create records on the selected table.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("TABLE", "Name of table where record will be created.");
                parameters.put("COLS", "Comma separated string with column names for values being inserted.");
                parameters.put("VALUES", "Values to be inserted.");
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
            String cols = PARAMETERS.get("COLS");
            String[] values = PARAMETERS.get("VALUES").split(",");
            if(cols.split(",").length!=values.length)
                throw new InvalidCommandException(cols);
            sb.append("INSERT INTO ")
                    .append(table).append("(\n")
                    .append(cols).append(")\n    VALUES (");
            for(int i=0;i<values.length;i++){
                sb.append("?").append(i+1<values.length?",":"");
            }
            
            sb.append(");");
            MSQLConsole.printDebug(sb.toString());
            QueryManager.executeQuery(sb.toString(),values);
            MSQLConsole.printResult("Record successfully created!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
