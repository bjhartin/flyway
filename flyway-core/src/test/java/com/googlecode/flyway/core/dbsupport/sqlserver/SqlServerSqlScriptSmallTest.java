/**
 * Copyright (C) 2010-2013 the original author or authors.
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
package com.googlecode.flyway.core.dbsupport.sqlserver;

import com.googlecode.flyway.core.dbsupport.SqlScript;
import com.googlecode.flyway.core.dbsupport.SqlStatement;
import com.googlecode.flyway.core.dbsupport.sqlserver.SQLServerDbSupport;
import com.googlecode.flyway.core.util.ClassPathResource;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for SqlServerSqlScript.
 */
public class SqlServerSqlScriptSmallTest {
    @Test
    public void parseSqlStatementsWithInlineComments() throws Exception {
        String source = new ClassPathResource("migration/dbsupport/sqlserver/sql/comments/V1__InlineComments.sql").loadAsString("UTF-8");

        SqlScript sqlScript = new SqlScript(source, new SQLServerDbSupport(null));
        List<SqlStatement> sqlStatements = sqlScript.getSqlStatements();
        assertEquals(4, sqlStatements.size());
        assertEquals(1, sqlStatements.get(0).getLineNumber());
        assertEquals(3, sqlStatements.get(0).getLineNumber());
        assertEquals(5, sqlStatements.get(0).getLineNumber());
        assertEquals(7, sqlStatements.get(0).getLineNumber());
        assertTrue(sqlStatements.get(1).getSql().contains(" --This works"));
        assertTrue(sqlStatements.get(3).getSql().contains("-- This works"));
        assertTrue(sqlStatements.get(5).getSql().contains("--This does not work"));
        assertTrue(sqlStatements.get(7).getSql().contains(" -- This works"));
    }
}
