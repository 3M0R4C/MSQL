/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands.edit;

import com.m0r4.soft.commands.delete.*;
import com.m0r4.soft.methods.create.*;
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
public class EditTable extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("TABLE", null);
        OP_PARAMETERS.put("DEL_COL", null);
        OP_PARAMETERS.put("ADD_COL", null);
        OP_PARAMETERS.put("ADD_FKEY", null);
        OP_PARAMETERS.put("DEL_CON", null);
        OP_PARAMETERS.put("ADD_PKEY", null);
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
                return "This method allows the user to edit tables on the connected database.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("TABLE", "Table to be edited.");
                parameters.put("DEL_COL", "Optional: Deletes specified column");
                parameters.put("ADD_COL", "Optional: Comma separated string with following format to add new column: ColName,ColType");
                parameters.put("ADD_FKEY", "Optional: Comma separated string with following format to add new column: FKeyName,FKeyCol,FTable,FTableCol");
                parameters.put("DEL_CON", "Optional: Deletes specified constraint.");
                parameters.put("ADD_PKEY", "Optional: Comma separated string with following format to add new pkey: PKeyName,ColName");
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
            String delCol = OP_PARAMETERS.get("DEL_COL");
            String addCol= OP_PARAMETERS.get("ADD_COL");
            String addFkey = OP_PARAMETERS.get("ADD_FKEY");
            String addPkey = OP_PARAMETERS.get("ADD_PKEY");
            String delCon = OP_PARAMETERS.get("DEL_CON");
            
            if(delCol!=null){
                sql = "ALTER TABLE "+table+"\n" +
                "  DROP COLUMN "+delCol+";";
                MSQLConsole.printDebug(sql);
                QueryManager.executeQuery(sql);
            }
            else if(addCol!=null){
                String[] command = addCol.split(",");
                if(command.length!=2)
                    throw new InvalidCommandException(addCol);
                
                sql = "ALTER TABLE "+table+"\n" +
                "  ADD COLUMN "+command[0]+" "+command[1]+";";
                MSQLConsole.printDebug(sql);
                QueryManager.executeQuery(sql);
            }
            else if(addFkey!=null){
                String[] command = addFkey.split(",");
                if(command.length!=4)
                    throw new InvalidCommandException(addFkey);
                
                sql = "ALTER TABLE "+table+"\n" +
                "  ADD CONSTRAINT "+command[0]+" FOREIGN KEY ("+command[1]+") REFERENCES "+command[2]+" ("+command[3]+") ON UPDATE NO ACTION ON DELETE NO ACTION;";
                MSQLConsole.printDebug(sql);
                QueryManager.executeQuery(sql);
            }
            else if(delCon!=null){
                String name = delCon;
                sql = "ALTER TABLE "+table+"\n" +
                "  DROP CONSTRAINT "+name+";\n";
                MSQLConsole.printDebug(sql);
                QueryManager.executeQuery(sql);
            }
            else if(addPkey!=null){
                String[] command = addPkey.split(",");
                if(command.length!=2)
                    throw new InvalidCommandException(addPkey);
                
                sql = "ALTER TABLE "+table+"\n" +
                "  ADD CONSTRAINT "+command[0]+" PRIMARY KEY ("+command[1]+");";
                MSQLConsole.printDebug(sql);
                QueryManager.executeQuery(sql);
            }else{
                    throw new InvalidCommandException(table);
                
            }
            MSQLConsole.printResult("Table successfully edited!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
