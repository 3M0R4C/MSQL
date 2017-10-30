/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands.display;

import com.m0r4.soft.commands.edit.*;
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
public class DisplayRecord extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("TABLES", null);
        PARAMETERS.put("COLS", null);
        OP_PARAMETERS.put("CONDITION", null);
        OP_PARAMETERS.put("JOIN", null);
        OP_PARAMETERS.put("JCONDITION", null);
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
                return "This method allows the user to display records on the selected table.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("TABLES", "Comma separated string with tables where data will be read. Max 2.");
                parameters.put("COLS", "Comma separated string with column names to be displayed.");
                parameters.put("CONDITION", "Optional: Filtering to be applied on information being retrieved");
                parameters.put("JOIN", "Optional: JOIN type to be applied.");
                parameters.put("JCONDITION", "Optional: Join condition.");
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
        PARAMETERS.put("TABLES", parameters[0]);
        PARAMETERS.put("COLS", parameters[1]);
    }

    @Override
    protected void execute() {
        try {
            StringBuilder sb = new StringBuilder();
            String[] tables = PARAMETERS.get("TABLES").split(",");
            String cols = PARAMETERS.get("COLS");
            String condition = OP_PARAMETERS.get("CONDITION");
            String join = OP_PARAMETERS.get("JOIN");
            String jcondition = OP_PARAMETERS.get("JCONDITION");
            if(tables.length>2)
                throw new InvalidCommandException(Arrays.toString(tables));
            else if(tables.length==1 && join!=null)
                throw new InvalidCommandException(join);
            else if(jcondition!=null && join==null)
                throw new InvalidCommandException(jcondition);
            sb.append("SELECT ")
                    .append(cols).append(" \nFROM ")
                    .append(tables[0]);
            if(join!=null){
                sb.append(" ").append(join).append(" JOIN ").append(tables[1]);
            }else{
                if(tables.length>1)
                    sb.append(", ").append(tables[1]); 
            }
            sb.append("\n");
            if(jcondition!=null){                
                sb.append("ON ").append(jcondition).append("\n");
            }
            if(condition!=null){                
                sb.append("WHERE ").append(condition).append("\n");
            }            
            sb.append(";");
            MSQLConsole.printDebug(sb.toString());
            QueryManager.executeQueryWithResultSet(sb.toString());
            MSQLConsole.printResult("Record(s) successfully displayed!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
