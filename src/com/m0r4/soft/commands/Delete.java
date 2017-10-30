/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.commands.delete.DeleteDataBase;
import com.m0r4.soft.commands.delete.DeleteFunction;
import com.m0r4.soft.commands.delete.DeleteRecord;
import com.m0r4.soft.commands.delete.DeleteRole;
import com.m0r4.soft.commands.delete.DeleteSchema;
import com.m0r4.soft.commands.delete.DeleteTable;
import com.m0r4.soft.util.MSQLManual;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class Delete extends MSQLCommand{
    
    @Override
    protected void execute(String[] command) throws InvalidCommandException { 
        TASKS.get(command[0].toUpperCase()).process(Arrays.copyOfRange(command, 1, command.length));
    }

    @Override
    protected void tasksHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "DELETE";
            }

            @Override
            public String getDescription() {
                return "This command allows the user to delete database objects.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                return null;
            }

            @Override
            public Map<String, String> getCommandsMap() {
                Map<String, String> commands = new LinkedHashMap();
                commands.put("DATABASE", "Deletes a database on connected server.");
                commands.put("ROLE", "Deletes roles on connected server.");
                commands.put("FUNCTION", "Deletes functions on connected server.");
                commands.put("SCHEMA", "Deletes schemas on connected server.");
                commands.put("TABLE", "Deletes tables on connected server.");
                commands.put("RECORD", "Deletes records from specified table.");
                return commands;
            }
        }.toString());  
    }

    @Override
    protected void initTasksImpl() {
        TASKS.put("DATABASE", new DeleteDataBase());
        TASKS.put("ROLE", new DeleteRole());
        TASKS.put("FUNCTION", new DeleteFunction());
        TASKS.put("SCHEMA", new DeleteSchema());
        TASKS.put("TABLE", new DeleteTable());
        TASKS.put("RECORD", new DeleteRecord());
    }
}
