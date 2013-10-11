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


import com.googlecode.flyway.core.dbsupport.db2.DB2DbSupport;
import com.googlecode.flyway.core.dbsupport.derby.DerbyDbSupport;
import com.googlecode.flyway.core.dbsupport.h2.H2DbSupport;
import com.googlecode.flyway.core.dbsupport.hsql.HsqlDbSupport;
import com.googlecode.flyway.core.dbsupport.mysql.MySQLDbSupport;
import com.googlecode.flyway.core.dbsupport.oracle.OracleDbSupport;
import com.googlecode.flyway.core.dbsupport.postgresql.PostgreSQLDbSupport;
import com.googlecode.flyway.core.dbsupport.sqlserver.SQLServerDbSupport;
import org.junit.Test;

import static org.mockito.Mockito.mock;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DbSupportFactoryTest {
    @Test
    public void registerDbSupportObjects(){
        assertEquals(DerbyDbSupport.class, DbSupportFactory.lookupDbSupport("Apache Derby"));
        assertEquals(H2DbSupport.class, DbSupportFactory.lookupDbSupport("H2"));
        assertEquals(HsqlDbSupport.class, DbSupportFactory.lookupDbSupport("HSQL Database Engine"));
        assertEquals(SQLServerDbSupport.class, DbSupportFactory.lookupDbSupport("Microsoft SQL Server"));
        assertEquals(MySQLDbSupport.class, DbSupportFactory.lookupDbSupport("MySQL"));
        assertEquals(OracleDbSupport.class, DbSupportFactory.lookupDbSupport("Oracle"));
        assertEquals(PostgreSQLDbSupport.class, DbSupportFactory.lookupDbSupport("PostgreSQL"));
        assertEquals(DB2DbSupport.class, DbSupportFactory.lookupDbSupport("DB2"));
    }

    @Test
    public void createDbSupport() throws SQLException {
        Connection conn = mock(Connection.class);
        DatabaseMetaData metaData = mock(DatabaseMetaData.class);
        when(conn.getMetaData()).thenReturn(metaData);
        when(metaData.getDatabaseProductName()).thenReturn("H2");
        when(metaData.getDatabaseMajorVersion()).thenReturn(1);
        when(metaData.getDatabaseMinorVersion()).thenReturn(0);
        assert(DbSupportFactory.createDbSupport(conn) instanceof H2DbSupport);
    }
}
