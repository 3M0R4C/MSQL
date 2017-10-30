/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft;

import com.m0r4.soft.commands.InvalidCommandException;
import com.m0r4.soft.psql.MSQLConnection;
import com.m0r4.soft.util.MSQLConfiguration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;


/**
 *
 * @author e.mora.c
 */
public class MSQLConsole {
    
    private static String INPUT_PROMPT = "MSQL##!!> ";
    private static final String ERROR_PROMPT = "ERROR! ";
    private static final String WARN_PROMPT = "WARN! ";
    private static final String INFO_PROMPT = "Info: ";
    private static final String DEBUG_PROMPT = "Debug: ";
    private static final String TRACE_PROMPT = "Trace: ";
    private static final String RESULT_PROMPT = "Result: ";
    private static final MSQLInterpreter INTERPRETER = new MSQLInterpreter();
    private static final Set<String> COMMANDS_HIST = new LinkedHashSet<>();   
    private static final Queue<String> COMMAND_QUEUE = new LinkedList();
    
    public MSQLConsole(){
        
    }
    
    public void start(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        addShutDownHook();
        try {
            String line;
            printInfo("Welcome to MSQL. Try '?' for help.");
            while (true){
                StringBuilder command = new StringBuilder();
                prompt();
                while ((line = br.readLine()) != null) {
                    command.append(line).append(" ");
                    if(line.trim().endsWith(";")||line.trim().endsWith("?")){
                        try {
                            String commandRequest = command.toString();
                            INTERPRETER.execute(new String[]{commandRequest});                            
                            COMMANDS_HIST.add(Integer.toString(COMMANDS_HIST.size()+1)+". " + commandRequest);
                            for(int i=0;i<COMMAND_QUEUE.size();i++){
                                INTERPRETER.execute(new String[]{COMMAND_QUEUE.poll()});                                
                            }
                        } catch (InvalidCommandException ex) {
                            print(ex);
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            printErr(e.getMessage());
        } finally{
            try {
                br.close();
            } catch (IOException ex) {
                printErr(ex.getMessage());
            }
        }
    }    

    public static void scheduleCommand(String command){
        COMMAND_QUEUE.add(command);
    }
    
    public static Set<String> getCOMMANDS_HIST() {
        return COMMANDS_HIST;
    }
    
    public static void print(Exception ex){
            printErr(ex.getMessage());
            printTrace(Arrays.toString(ex.getStackTrace()));
    }
    
    public static void printInfo(String line){
            print(line,INFO_PROMPT);
    }
    public static void printErr(String line){
        print(line,ERROR_PROMPT);
    }
    public static void printWarn(String line){
        if(MSQLConfiguration.LOGLEVEL.equals(MSQLConfiguration.LEVEL.WARN) ||
                MSQLConfiguration.LOGLEVEL.equals(MSQLConfiguration.LEVEL.DEBUG)||
                MSQLConfiguration.LOGLEVEL.equals(MSQLConfiguration.LEVEL.TRACE))
            print(line,WARN_PROMPT);
    }
    public static void printResult(String line){
        print(line,RESULT_PROMPT);
    }
    public static void printDebug(String line){
        if(MSQLConfiguration.LOGLEVEL.equals(MSQLConfiguration.LEVEL.DEBUG)||
                MSQLConfiguration.LOGLEVEL.equals(MSQLConfiguration.LEVEL.TRACE))
            print(line,DEBUG_PROMPT);
    }
    public static void printTrace(String line){
        if(MSQLConfiguration.LOGLEVEL.equals(MSQLConfiguration.LEVEL.TRACE))
            print(line,TRACE_PROMPT);
    }
    
    public static void print(String line, String prompt){
        System.out.println(prompt+line);
    }
    public static void prompt(){
        if(MSQLConnection.getInstance().isConnected()){
            INPUT_PROMPT=INPUT_PROMPT.replaceAll("##", "#"+MSQLConnection.getInstance().getUser());
            INPUT_PROMPT=INPUT_PROMPT.replaceAll("!!", "!"+MSQLConnection.getInstance().getDatabase());
        }else{     
            INPUT_PROMPT=INPUT_PROMPT.replaceAll("##", "");
            INPUT_PROMPT=INPUT_PROMPT.replaceAll("!!", "");
        }
        System.out.print(INPUT_PROMPT); 
        INPUT_PROMPT = "MSQL##!!> ";
    }

    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
          @Override
          public void run()
          {
              try {
                  printInfo("Checking existing connections before closing MSQL.");
                  if(MSQLConnection.getInstance().disconnect()){
                      printInfo("Disconnected from existing session.");
                  }else{
                      printInfo("No active connections were found.");                 
                  }
                  printInfo("Closing MSQL now.");
              } catch (SQLException ex) {
                  print(ex);
              }
          }
        });
    }
}
