/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft;

import com.m0r4.soft.commands.CHist;
import com.m0r4.soft.commands.Create;
import com.m0r4.soft.commands.Delete;
import com.m0r4.soft.commands.Display;
import com.m0r4.soft.commands.Edit;
import com.m0r4.soft.commands.InvalidCommandException;
import com.m0r4.soft.commands.MSQLCommand;
import com.m0r4.soft.commands.Settings;
import com.m0r4.soft.methods.Connect;
import com.m0r4.soft.methods.Disconnect;
import com.m0r4.soft.methods.PSQL;
import com.m0r4.soft.methods.Quit;
import com.m0r4.soft.methods.Use;
import com.m0r4.soft.util.MSQLConstants;
import com.m0r4.soft.util.MSQLManual;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author e.mora.c
 */
public class MSQLInterpreter extends MSQLCommand {

    public MSQLInterpreter() {
        super();
    }

    @Override
    protected void initTasksImpl() {
        TASKS.put("CREATE", new Create());
        TASKS.put("DELETE", new Delete());
        TASKS.put("EDIT", new Edit());
        TASKS.put("DISPLAY", new Display());
        TASKS.put("QUIT", new Quit());
        TASKS.put("DISCONNECT", new Disconnect());
        TASKS.put("CONNECT", new Connect());
        TASKS.put("CHIST", new CHist());
        TASKS.put("USE", new Use());
        TASKS.put("SETTINGS", new Settings());
        TASKS.put("PSQL", new PSQL());
    }

    @Override
    public void execute(String[] command) throws InvalidCommandException {
        String[] optimizedCommand = optmizeCommand(command[0]);
        if(optimizedCommand.length>0 && optimizedCommand[0]!=null && !optimizedCommand[0].isEmpty()){
            process(optimizedCommand);
            TASKS.get(optimizedCommand[0].toUpperCase()).process(Arrays.copyOfRange(optimizedCommand, 1, optimizedCommand.length));
        }
    }
    @Override
    public void process(String[] command) throws InvalidCommandException {        
        if(!isValid(command[0])){
            throw new InvalidCommandException(command[0]);            
        }
    }

    private String[] optmizeCommand(String command) throws InvalidCommandException {
        int statementCount = 0;
        String rCommand="";
        for (int x=command.length()-1;x>=0;x--)
		rCommand = rCommand + command.charAt(x);    
        command = command.substring(0, command.length()-rCommand.indexOf(";")-1);
        
        command = command.replaceAll("\\\\\"", "&%20");
        command = command.replaceAll("\\\\\\[", "&%40");
        command = command.replaceAll("\t", " ");
        
        List<String> optionalCommands = new ArrayList<>();
        Matcher m = Pattern.compile(MSQLConstants.OP_COMMAND_REGEX)
            .matcher(command);
        while (m.find()) {
            String match = m.group();
            optionalCommands.add(match);
            command = command.replace(match, "");
        }
        
        boolean isComplex = false;
        String complexString = "";
        for (String statement : command.split(" ")) {
            if (statement != null && !statement.isEmpty() && !statement.equals(" ")) {
                if(statement.startsWith("\"")&&statement.endsWith("\"")&&statement.length()>1)
                    statementCount++;
                else if(!isComplex && statement.startsWith("\"")){
                    isComplex=true;
                }else if(isComplex && statement.endsWith("\"")){
                    isComplex=false;
                    statementCount++;
                }else if (!isComplex){
                    statementCount++;
                }          
            }
        }
        String[] optimizedCommand = new String[statementCount+optionalCommands.size()];
        statementCount = 0;
        for (String statement : command.split(" ")) {
            if (statement != null && !statement.isEmpty() && !statement.equals(" ")) {
                if(statement.startsWith("\"")&&statement.endsWith("\"")&&statement.length()>1){
                    optimizedCommand[statementCount] = statement.replaceAll("&%20","\\\\\"").replaceAll("&%40","\\[");
                    statementCount++;
                }else if(!isComplex && statement.startsWith("\"")){
                    isComplex=true;
                    complexString+=statement.substring(1);
                }else if(isComplex && statement.endsWith("\"")){
                    isComplex=false;
                    complexString+=" " + statement.substring(0, statement.length()-1);
                    optimizedCommand[statementCount] = complexString.replaceAll("&%20","\\\\\"").replaceAll("&%40","\\[");
                    complexString="";
                    statementCount++;
                }else if(isComplex)
                    complexString+=" "+statement;    
                else{
                    optimizedCommand[statementCount] = statement.replaceAll("&%20","\\\\\"").replaceAll("&%40","\\[");
                    statementCount++;                    
                }
                    
            }           
        }
        for(String optionalCommand:optionalCommands){
            optimizedCommand[statementCount]=optionalCommand;
            statementCount++;
        }
        MSQLConsole.printDebug("Optimized command: "+ Arrays.toString(optimizedCommand));
        
        if(isComplex)
            throw new InvalidCommandException(complexString.replaceAll("&%20","\\\"").replaceAll("&%40","\\["));
        
        return optimizedCommand;
    }

    @Override
    protected void tasksHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "MSQL Console";
            }

            @Override
            public String getDescription() {
                return "This console is used to execute any MSQL command.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                return null;
            }

            @Override
            public Map<String, String> getCommandsMap() {
                Map<String, String> commands = new LinkedHashMap();
                commands.put("CREATE", "Creates objects with MSQL.");
                commands.put("DELETE", "Deletes objects with MSQL.");
                commands.put("EDIT", "Edits objects with MSQL.");
                commands.put("DISPLAY", "Displays objects information with MSQL.");
                commands.put("QUIT", "Closes MSQL console.");
                commands.put("DISCONNECT", "Disconnects from current session.");
                commands.put("CONNECT", "Creates and connects to a new session.");
                commands.put("CHIST", "Enables to explore the command history.");
                commands.put("USE", "Allows the user to switch to another database without disconnecting.");
                commands.put("SETTINGS", "Allows the user to manage current MSQL settings.");
                commands.put("PSQL", "Executes any psql command.");
                return commands;
            }
        }.toString());
    }

}
