/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.commands.edit.EditRecord;
import com.m0r4.soft.commands.edit.EditTable;
import com.m0r4.soft.util.MSQLManual;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class Edit extends MSQLCommand{
    
    @Override
    protected void execute(String[] command) throws InvalidCommandException {    
        TASKS.get(command[0].toUpperCase()).process(Arrays.copyOfRange(command, 1, command.length));
    }

   @Override
    protected void tasksHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "EDIT";
            }

            @Override
            public String getDescription() {
                return "This command allows the user to edit database objects.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                return null;
            }

            @Override
            public Map<String, String> getCommandsMap() {
                Map<String, String> commands = new LinkedHashMap();
                commands.put("TABLE", "Edits tables on connected server.");
                commands.put("RECORD", "Edits records on specified table.");
                return commands;
            }
        }.toString());  
    }

    @Override
    protected void initTasksImpl() {
        TASKS.put("TABLE", new EditTable());
        TASKS.put("RECORD", new EditRecord());
    }
}
