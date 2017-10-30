/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.MSQLTask;
import com.m0r4.soft.methods.MSQLMethod;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author e.mora.c
 */
public abstract class MSQLCommand  extends MSQLTask{
    
    protected final Map<String, MSQLTask> TASKS = new LinkedHashMap<>();   
    
    public MSQLCommand(){
        initTasks();
    }
        
    public boolean isValid(String command){
        return TASKS.containsKey(command.toUpperCase());
    }
    
    protected abstract void tasksHelp();
    
    protected abstract void initTasksImpl();
    protected abstract void execute(String[] command) throws InvalidCommandException ;

    private void initTasks(){
        TASKS.put("?", new MSQLMethod() {
            @Override
            protected void initParameters() {
            }

            @Override
            public void paramsHelp() {
                execute();
            }

            @Override
            protected void processParameters(String[] parameters) {
            }

            @Override
            public void execute() {
                tasksHelp();
            }
        });
        initTasksImpl();
    }
    
    @Override
    public void process(String[] command) throws InvalidCommandException {
        if(command.length==0){
            MSQLConsole.printErr("Non valid action. Try '?' for help.");  
            return;
        }
        if(!isValid(command[0])){
            throw new InvalidCommandException(command[0]);            
        }
        try{
            execute(command);
        }catch(Exception ex){
            MSQLConsole.print(ex);
        }
    }
    
}
