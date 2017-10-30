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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class CreateTable extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("NAME", null);
        PARAMETERS.put("OWNER", null);
        PARAMETERS.put("COLS", null);
        PARAMETERS.put("DTYPES", null);
        OP_PARAMETERS.put("NOTNULL", null);
        OP_PARAMETERS.put("PKEY", null);
    }

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "TABLE";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to create tables on the connected database.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("NAME", "Name for the table being created.");
                parameters.put("OWNER", "Table owner.");
                parameters.put("COLS", "Comma separated string with col names for new table.");
                parameters.put("DTYPES", "Comma separated string with datatypes for columns being created. Amount of types should match with COLS amount.");
                parameters.put("NOTNULL", "Optional: Sets not null constaints for the cols specified through a command separated string on this parameter.");
                parameters.put("PKEY", "Optional: Sets the primary key for the table.");
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
        PARAMETERS.put("NAME", parameters[0]);
        PARAMETERS.put("OWNER", parameters[1]);
        PARAMETERS.put("COLS", parameters[2]);
        PARAMETERS.put("DTYPES", parameters[3]);
    }

    @Override
    protected void execute() {
        try {
            StringBuilder sb = new StringBuilder();
            String name = PARAMETERS.get("NAME");
            String owner = PARAMETERS.get("OWNER");
            String[] cols = PARAMETERS.get("COLS").split(",");
            String[] dtypes = PARAMETERS.get("DTYPES").split(",");
            String notNullParam =OP_PARAMETERS.get("NOTNULL");
            String[] notNull = null;
            if(notNullParam!=null){
                notNull=OP_PARAMETERS.get("NOTNULL").split(",");
            }
            String pkey = OP_PARAMETERS.get("PKEY");
            
            if(cols.length!=dtypes.length)
                throw new InvalidCommandException(Arrays.toString(cols));
            sb.append("CREATE TABLE ").append(name).append("\n(\n" );
            for(int i=0;i<cols.length;i++){
                sb.append(cols[i])
                        .append(" ")
                        .append(dtypes[i])
                        .append(notNull!=null?(Arrays.asList(notNull).contains(cols[i])?" "+"NOT NULL,\n":(i+1<cols.length?",\n":"\n")):(i+1<cols.length?",\n":"\n"));
            }
            if(pkey!=null){
                sb.append(", PRIMARY KEY (").append(pkey).append(")\n");
            }else{
                
            }
            sb.append(")\nWITH (\n   OIDS = FALSE\n);\n\nALTER TABLE ")
                    .append(name)
                    .append("\n OWNER to ")
                    .append(owner)
                    .append(";");
            MSQLConsole.printDebug(sb.toString());
            QueryManager.executeQuery(sb.toString());
            MSQLConsole.printResult("Table successfully created!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
