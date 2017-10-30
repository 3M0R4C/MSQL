/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.methods.create;

import com.m0r4.soft.MSQLConsole;
import com.m0r4.soft.commands.InvalidCommandException;
import com.m0r4.soft.methods.MSQLMethod;
import com.m0r4.soft.psql.QueryManager;
import com.m0r4.soft.util.MSQLManual;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author e.mora.c
 */
public class CreateRole extends MSQLMethod {

    @Override
    protected void initParameters() {
        PARAMETERS.put("NAME", null);
        PARAMETERS.put("PASSWORD", null);
        PARAMETERS.put("PERMISSIONS", null);
        OP_PARAMETERS.put("CONN_LIMIT", null);
        OP_PARAMETERS.put("PARENT_ROLES", null);
    }

    @Override
    public void paramsHelp() {
        MSQLConsole.printResult(new MSQLManual() {
            @Override
            public String getName() {
                return "ROLE";
            }

            @Override
            public String getDescription() {
                return "This method allows the user to create roles on the connected server.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("NAME", "Name for the role being created.");
                parameters.put("PASSWORD", "Role password.");
                parameters.put("PERMISSIONS", "Binary 6 digits string with permissions specification. Order: LOGIN, SUPERUSER, CREATEDB, CREATEROLE, INHERIT, REPLICATION. Ex: 100000 for only login permissions");
                parameters.put("CONN_LIMIT", "Optional: Sets the connection limit for the corresponding role.");
                parameters.put("PARENT_ROLES", "Optional: Specifies through a comma separated string which parent roles should be applied.");
                return parameters;
            }

            @Override
            public Map<String, String> getCommandsMap() {
                return null;
            }
        }.toString());
    }

    @Override
    protected void processParameters(String[] parameters) {
        PARAMETERS.put("NAME", parameters[0]);
        PARAMETERS.put("PASSWORD", parameters[1]);
        PARAMETERS.put("PERMISSIONS", parameters[2]);
    }

    @Override
    protected void execute() {
        try {
            StringBuilder sb = new StringBuilder();
            String permissions = PARAMETERS.get("PERMISSIONS");
            String connLimit = OP_PARAMETERS.get("CONN_LIMIT");
            String parents = OP_PARAMETERS.get("PARENT_ROLES");
            String name =PARAMETERS.get("NAME");
            boolean login,superuser,createdb,createrole,inherit,replication;
            if(permissions.length()==6 && permissions.matches("[10]{6}")){
                login=permissions.substring(0, 1).equals("1");
                superuser=permissions.substring(1, 2).equals("1");
                createdb=permissions.substring(2, 3).equals("1");
                createrole=permissions.substring(3, 4).equals("1");
                inherit=permissions.substring(4, 5).equals("1");               
                replication=permissions.substring(5, 6).equals("1");   
            }else{
                throw new InvalidCommandException(permissions);
            }
            if(connLimit==null){
                connLimit="-1";
            }
            try{Integer.parseInt(connLimit);}
            catch(Exception ex){
                throw new InvalidCommandException(connLimit);
            }
            
            sb.append("CREATE USER \"")
                    .append(name)
                    .append("\" WITH \n")
                    .append(!login?"NO":"").append("LOGIN\n")
                    .append(!superuser?"NO":"").append("SUPERUSER\n")
                    .append(!createdb?"NO":"").append("CREATEDB\n")
                    .append(!createrole?"NO":"").append("CREATEROLE\n")
                    .append(!inherit?"NO":"").append("INHERIT\n")
                    .append(!replication?"NO":"").append("REPLICATION\n")
                    .append("CONNECTION LIMIT ")
                    .append(connLimit)
                    .append("\n")
                    .append("PASSWORD '").append(PARAMETERS.get("PASSWORD")).append("';\n\n");            
            if(parents!=null){
                for(String parent : parents.split(",")){
                    if(!parent.isEmpty()){
                        sb.append("GRANT \"").append(parent).append("\" TO \"").append(name).append("\";\n");
                    }
                }            
            }
            MSQLConsole.printDebug(sb.toString());
            QueryManager.executeQuery(sb.toString());
            MSQLConsole.printResult("Role successfully created!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
