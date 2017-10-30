/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.commands.display.DisplayDataBase;
import com.m0r4.soft.commands.display.DisplayFunction;
import com.m0r4.soft.commands.display.DisplayRecord;
import com.m0r4.soft.commands.display.DisplayRole;
import com.m0r4.soft.commands.display.DisplaySchema;
import com.m0r4.soft.commands.display.DisplayTable;
import com.m0r4.soft.util.MSQLManual;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class Display extends MSQLCommand{

    public Display(){
        super();
    }
    
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
                commands.put("DATABASE", "Displays database list on connected server.");
                commands.put("ROLE", "Displays roles list on connected server.");
                commands.put("FUNCTION", "Displays functions list on connected server.");
                commands.put("SCHEMA", "Displays schemas list on connected server.");
                commands.put("TABLE", "Displays tables list on connected server.");
                commands.put("RECORD", "Displays records on specified table(s).");
                return commands;
            }
        }.toString());  
    }

    @Override
    protected void initTasksImpl() {
        TASKS.put("DATABASE", new DisplayDataBase());
        TASKS.put("ROLE", new DisplayRole());
        TASKS.put("FUNCTION", new DisplayFunction());
        TASKS.put("SCHEMA", new DisplaySchema());
        TASKS.put("TABLE", new DisplayTable());
        TASKS.put("RECORD", new DisplayRecord());
    }

}
