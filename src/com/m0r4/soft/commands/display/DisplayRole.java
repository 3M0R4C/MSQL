/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands.display;

import com.m0r4.soft.commands.delete.*;
import com.m0r4.soft.methods.create.*;
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
public class DisplayRole extends MSQLMethod {

    @Override
    protected void initParameters() {
        OP_PARAMETERS.put("NAME", null);
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
                return "This method allows the user to display roles list on the connected server.";
            }

            @Override
            public Map<String, String> getParametersMap() {
                Map<String, String> parameters = new LinkedHashMap();
                parameters.put("NAME", "Optional: Provide database name to display specific database details.");
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
    }

    @Override
    protected void execute() {
        try {
            String sql;
            String name = OP_PARAMETERS.get("NAME");
            if (name != null) {
                sql = "SELECT u.usename AS \"Role name\",\n" +
"  CASE WHEN u.usesuper AND u.usecreatedb THEN CAST('superuser, create\n" +
"database' AS pg_catalog.text)\n" +
"       WHEN u.usesuper THEN CAST('superuser' AS pg_catalog.text)\n" +
"       WHEN u.usecreatedb THEN CAST('create database' AS\n" +
"pg_catalog.text)\n" +
"       ELSE CAST('' AS pg_catalog.text)\n" +
"  END AS \"Attributes\", * \n" +
"FROM pg_catalog.pg_user u WHERE u.usename=?";
            } else {
                sql = "SELECT u.usename AS \"Role name\",\n" +
"  CASE WHEN u.usesuper AND u.usecreatedb THEN CAST('superuser, create\n" +
"database' AS pg_catalog.text)\n" +
"       WHEN u.usesuper THEN CAST('superuser' AS pg_catalog.text)\n" +
"       WHEN u.usecreatedb THEN CAST('create database' AS\n" +
"pg_catalog.text)\n" +
"       ELSE CAST('' AS pg_catalog.text)\n" +
"  END AS \"Attributes\"\n" +
"FROM pg_catalog.pg_user u";
            }

            MSQLConsole.printDebug(sql);
            if (name != null) {
                QueryManager.executeQueryWithResultSet(sql, name);

                sql = "SELECT * \n"
                        + "FROM information_schema.role_table_grants "
                        + "WHERE grantee=?";

                QueryManager.executeQueryWithResultSet(sql, name);

            } else {
                QueryManager.executeQueryWithResultSet(sql);
            }
            MSQLConsole.printResult("Roles successfully displayed!");
        } catch (Exception ex) {
            MSQLConsole.print(ex);
        }
    }
}
