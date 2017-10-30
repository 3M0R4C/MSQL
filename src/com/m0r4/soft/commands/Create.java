/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.methods.create.CreateDataBase;
import com.m0r4.soft.methods.create.CreateFunction;
import com.m0r4.soft.methods.create.CreateRecord;
import com.m0r4.soft.methods.create.CreateRole;
import com.m0r4.soft.methods.create.CreateSchema;
import com.m0r4.soft.methods.create.CreateTable;
import com.m0r4.soft.util.MSQLManual;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class Create extends MSQLCommand{

    @Override
    protected void execute(String[] command) throws InvalidCommandException {  
        TASKS.get(command[0].toUpperCase()).process(Arrays.copyOfRange(command, 1, command.length));
    }

    @Override
    protected void tasksHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "CREATE";
            }

            @Override
            public String getDescription() {
                return "This command allows the user to create new database objects.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                return null;
            }

            @Override
            public Map<String, String> getCommandsMap() {
                Map<String, String> commands = new LinkedHashMap();
                commands.put("DATABASE", "Creates a database on connected server.");
                commands.put("ROLE", "Creates roles on connected server.");
                commands.put("FUNCTION", "Creates functions on connected server.");
                commands.put("SCHEMA", "Creates schemas on connected server.");
                commands.put("TABLE", "Creates tables on connected server.");
                commands.put("RECORD", "Creates records into specified table.");
                return commands;
            }
        }.toString());
    }

    @Override
    protected void initTasksImpl() {
        TASKS.put("DATABASE", new CreateDataBase());
        TASKS.put("ROLE", new CreateRole());
        TASKS.put("FUNCTION", new CreateFunction());
        TASKS.put("SCHEMA", new CreateSchema());
        TASKS.put("TABLE", new CreateTable());
        TASKS.put("RECORD", new CreateRecord());
    }

}
