/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.util;

import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public abstract class MSQLManual {
    
    @Override
    public String toString(){
        String man=getName().toUpperCase()+" " + MSQLConstants.MAN_HEADER+ "\n"+
                getDescription()+ "\n"+
                MSQLConstants.LINE_BREAK+ "\n";
        String parameters ="";
        String commands ="";
        if(getParametersMap()!=null)
            for(Map.Entry<String,String> entry:this.getParametersMap().entrySet()){
                parameters+=entry.getKey()+": "+entry.getValue()+"\n";
            }
        
        if(getCommandsMap()!=null)
            for(Map.Entry<String,String> entry:this.getCommandsMap().entrySet()){
                commands+=entry.getKey()+": "+entry.getValue()+"\n";
            }
        if(!commands.equals(""))
            man += "COMMANDS:"+ "\n"+commands+MSQLConstants.LINE_BREAK;
        if(!parameters.equals(""))
            man += "PARAMETERS:"+ "\n"+parameters+MSQLConstants.LINE_BREAK;
        return man;
                
                
               
    }

    public abstract String getName();
    public abstract String getDescription();
    public abstract Map<String,String> getParametersMap();
    public abstract Map<String,String> getCommandsMap();
}
