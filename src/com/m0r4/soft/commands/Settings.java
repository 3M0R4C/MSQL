/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.methods.chist.CHistExec;
import com.m0r4.soft.methods.chist.CHistView;
import com.m0r4.soft.methods.settings.SettingsSet;
import com.m0r4.soft.methods.settings.SettingsView;
import com.m0r4.soft.util.MSQLManual;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class Settings extends MSQLCommand{

    @Override
    protected void execute(String[] command) throws InvalidCommandException {  
        TASKS.get(command[0].toUpperCase()).process(Arrays.copyOfRange(command, 1, command.length));
    }

    @Override
    protected void tasksHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "SETTINGS";
            }

            @Override
            public String getDescription() {
                return "This command allows to configure current session settings.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                return null;
            }

            @Override
            public Map<String, String> getCommandsMap() {
                Map<String, String> commands = new LinkedHashMap();
                commands.put("VIEW", "Displays the current settings.");
                commands.put("SET", "Allows to modify current settings.");
                return commands;
            }
        }.toString());
    }

    @Override
    protected void initTasksImpl() {
        TASKS.put("VIEW", new SettingsView());
        TASKS.put("SET", new SettingsSet());
    }

}
