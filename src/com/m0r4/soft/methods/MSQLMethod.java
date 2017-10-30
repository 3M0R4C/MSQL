/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.methods;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.MSQLTask;
import com.m0r4.soft.commands.InvalidCommandException;
import com.m0r4.soft.util.MSQLConstants;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public abstract class MSQLMethod extends MSQLTask{

    protected Map<String, String> PARAMETERS = new LinkedHashMap<>();
    protected Map<String, String> OP_PARAMETERS = new LinkedHashMap<>();

    public MSQLMethod() {
    }
    
    @Override
    public void process(String[] command) throws InvalidCommandException{
        initParameters();
        if(command.length==1 && command[0].equals("?")){
            paramsHelp();
        }else if(command.length<PARAMETERS.size()){
            throw new InvalidCommandException(PARAMETERS.size(),command.length);
        }else{
            try{
                processParameters(command);
                processOptionalParameters(Arrays.copyOfRange(command, PARAMETERS.size(), command.length));
                Date dateStart = new Date();
                execute();
                Date dateEnd = new Date();
                MSQLConsole.printDebug(String.format("Execution time: %dms",dateEnd.getTime()-dateStart.getTime()));
            }catch(Exception ex){
                MSQLConsole.print(ex);
            }
        }
        resetParams();
    }
    
    protected void processOptionalParameters(String[] parameters) throws InvalidCommandException{
        if(parameters.length>0){
            for(String parameter : parameters){
                if(!parameter.matches(MSQLConstants.OP_COMMAND_REGEX)){
                    throw new InvalidCommandException(parameter);
                }
                String name = parameter.substring(0,parameter.indexOf("["));
                String value = parameter.substring(parameter.indexOf("[")+1,parameter.length()-1);
                if(!OP_PARAMETERS.containsKey(name.toUpperCase())){
                    throw new InvalidCommandException(name);
                }else{
                    OP_PARAMETERS.put(name.toUpperCase(), value);
                }
            }
        }
    }
    
    private void resetParams(){
        PARAMETERS = new LinkedHashMap<>();
        OP_PARAMETERS = new LinkedHashMap<>();
    }

    public abstract void paramsHelp();
    protected abstract void initParameters();  
    protected abstract void processParameters(String[] parameters);  
    protected abstract void execute();    
}
