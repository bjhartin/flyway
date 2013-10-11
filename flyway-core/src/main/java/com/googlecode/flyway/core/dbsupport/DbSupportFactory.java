/**
 * Copyright 2010-2013 Axel Fontaine and the many contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.flyway.core.dbsupport;

import com.googlecode.flyway.core.api.FlywayException;
import com.googlecode.flyway.core.dbsupport.db2.DB2DbSupport;
import com.googlecode.flyway.core.dbsupport.derby.DerbyDbSupport;
import com.googlecode.flyway.core.dbsupport.h2.H2DbSupport;
import com.googlecode.flyway.core.dbsupport.hsql.HsqlDbSupport;
import com.googlecode.flyway.core.dbsupport.mysql.MySQLDbSupport;
import com.googlecode.flyway.core.dbsupport.oracle.OracleDbSupport;
import com.googlecode.flyway.core.dbsupport.postgresql.PostgreSQLDbSupport;
import com.googlecode.flyway.core.dbsupport.sqlserver.SQLServerDbSupport;
import com.googlecode.flyway.core.util.logging.Log;
import com.googlecode.flyway.core.util.logging.LogFactory;

import javax.management.RuntimeErrorException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for obtaining the correct DbSupport instance for the current connection.
 */
public class DbSupportFactory {
    /**
     * Logger.
     */
    private static final Log LOG = LogFactory.getLog(DbSupportFactory.class);

    /**
     * Prevent instantiation.
     */
    private DbSupportFactory() {
        //Do nothing
    }

    /**
     * Initializes the appropriate DbSupport class for the database product used by the data source.
     *
     * @param connection The Jdbc connection to use to query the database.
     * @return The appropriate DbSupport class.
     */
    public static DbSupport createDbSupport(Connection connection) {
        String databaseProductName = getDatabaseProductName(connection);

        LOG.debug("Database: " + databaseProductName);

        Class dbSupportClass = lookupDbSupport(databaseProductName);


        try {
            return (DbSupport) dbSupportClass.getConstructor(Connection.class).newInstance(connection);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create instance of " + dbSupportClass.getName(), e);
        }
    }

    public static Class lookupDbSupport(String databaseProductName) {
        Map<String, Class> registry = new HashMap<String, Class>();
        registry.put("Apache Derby", DerbyDbSupport.class);
        registry.put("H2", H2DbSupport.class);
        registry.put("HSQL Database Engine", HsqlDbSupport.class);
        registry.put("Microsoft SQL Server", SQLServerDbSupport.class);
        registry.put("MySQL", MySQLDbSupport.class);
        registry.put("Oracle", OracleDbSupport.class);
        registry.put("PostgreSQL", PostgreSQLDbSupport.class);
        registry.put("DB2", DB2DbSupport.class);

        for(String key:registry.keySet()) {
            if(databaseProductName.startsWith(key))
                return registry.get(key);
        }

        throw new FlywayException("Unsupported Database: " + databaseProductName);
    }

    /**
     * Retrieves the name of the database product.
     *
     * @param connection The connection to use to query the database.
     * @return The name of the database product. Ex.: Oracle, MySQL, ...
     */
    private static String getDatabaseProductName(Connection connection) {
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            if (databaseMetaData == null) {
                throw new FlywayException("Unable to read database metadata while it is null!");
            }

            String databaseProductName = databaseMetaData.getDatabaseProductName();
            if (databaseProductName == null) {
                throw new FlywayException("Unable to determine database. Product name is null.");
            }

            int databaseMajorVersion = databaseMetaData.getDatabaseMajorVersion();
            int databaseMinorVersion = databaseMetaData.getDatabaseMinorVersion();

            return databaseProductName + " " + databaseMajorVersion + "." + databaseMinorVersion;
        } catch (SQLException e) {
            throw new FlywayException("Error while determining database product name", e);
        }
    }

}
