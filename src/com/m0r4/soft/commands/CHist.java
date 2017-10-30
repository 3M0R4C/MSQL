/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.methods.chist.CHistExec;
import com.m0r4.soft.methods.chist.CHistView;
import com.m0r4.soft.methods.create.CreateDataBase;
import com.m0r4.soft.util.MSQLManual;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class CHist extends MSQLCommand{

    @Override
    protected void execute(String[] command) throws InvalidCommandException {  
        TASKS.get(command[0].toUpperCase()).process(Arrays.copyOfRange(command, 1, command.length));
    }

    @Override
    protected void tasksHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "CHIST";
            }

            @Override
            public String getDescription() {
                return "This command provides functionalities through command execution history.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                return null;
            }

            @Override
            public Map<String, String> getCommandsMap() {
                Map<String, String> commands = new LinkedHashMap();
                commands.put("VIEW", "Displays the command history stored for current session.");
                commands.put("EXEC", "Allows to execute again a command from command history.");
                return commands;
            }
        }.toString());
    }

    @Override
    protected void initTasksImpl() {
        TASKS.put("VIEW", new CHistView());
        TASKS.put("EXEC", new CHistExec());
    }

}
