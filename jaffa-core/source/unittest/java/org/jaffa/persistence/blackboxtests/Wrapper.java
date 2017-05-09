/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

/*
 * Wrapper.java
 *
 * Created on April 2, 2002, 11:16 AM
 */

package org.jaffa.persistence.blackboxtests;

import helpers.ConnectionHelper;
import junit.extensions.TestSetup;
import junit.framework.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;
import java.io.*;
import java.util.*;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.jaffa.config.Config;
import org.jaffa.util.URLHelper;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Init;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Database;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Param;
import org.jaffa.persistence.engines.jdbcengine.variants.Variant;
import org.jaffa.datatypes.Parser;
import org.jaffa.util.XmlHelper;

/** This class has the methods for one time setup/cleanup of data, before all the tests executed by the suite utilising this class.
 *
 * @author  GautamJ
 */
public class Wrapper extends TestSetup {

    private static StringBuffer buf = new StringBuffer();
    static {
        for (int i = 0; i < 100000; i++)
            buf.append('Z');
    }
    static final String CATZ_REMARKS = buf.toString();

    /** The constructor.
     * @param test The Test class, for which the Wrapper will be utilised.
     */
    public Wrapper(Test test) {
        super(test);
    }

    /** This sets up the data before the test fixtures are invoked.
     */
    protected void setUp() {
        Connection connection = null;
        try {
            connection = ConnectionHelper.getConnection();
            String engineType = ConnectionHelper.getEngineType();

            // cleanup any errorneous data
            dropTables(connection, engineType);

            // create brand new tables
            createTables(connection, engineType);

            // now add new data
            addNewData(connection);

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to setup initial data: " + e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Failed to setup initial data: " + e.toString());
                }
            }
        }
    }

    /** This cleans up the data after all the test fixtures are invoked.
     */
    protected void tearDown() {
        Connection connection = null;
        try {
            connection = ConnectionHelper.getConnection();
            dropTables(connection, ConnectionHelper.getEngineType());
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to cleanup initial data: " + e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Failed to cleanup initial data: " + e.toString());
                }
            }
        }
    }


    private void addNewData(Connection connection)
    throws Exception {
        addToInstrument(connection);
        addToPart(connection);
        addToPartRem(connection);
        addToCondition(connection);
        addToItem(connection);
        addToValidFieldValue(connection);
    }

    private void addToInstrument(Connection connection)
    throws SQLException {
        String sql = "insert into ZZ_JUT_INSTRUMENT(CATEGORY_INSTRUMENT, DESCRIPTION, SUPPORT_EQUIP_B, CALCULATE_MTBF_B) values('Z-TESTCI-01', 'Z-TESTCIDESC-01', '1', 'F')";
        executeSql(connection, sql);
    }

    private void addToPart(Connection connection)
    throws SQLException {
        String sql = "insert into ZZ_JUT_PART(PART, NOUN, CATEGORY_INSTRUMENT) values('Z-TESTPART-01', 'Z-TESTNOUN-01', 'Z-TESTCI-01')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_PART(PART, NOUN, CATEGORY_INSTRUMENT) values('Z-TESTPART-02', 'Z-TESTNOUN-02', 'Z-TESTCI-01')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_PART(PART, NOUN, CATEGORY_INSTRUMENT) values('Z-TESTPART-03', 'Z-TESTNOUN-03', 'Z-TESTCI-01')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_PART(PART, NOUN, CATEGORY_INSTRUMENT) values('Z-TESTPART-04', 'Z-TESTNOUN-03', 'Z-TESTNOUN-03')";
        executeSql(connection, sql);
    }

    private void addToPartRem(Connection connection)
    throws SQLException {
        //String sql = "insert into ZZ_JUT_PART_REM VALUES('Z-TESTPART-01', 'SOME REMARKS'";
        //executeSql(connection, sql);

        // the following is needed, since jdbc requires that fields having 4000+ characters be passed in via streams
        String sql = "insert into ZZ_JUT_PART_REM VALUES(?,?)";
        PreparedStatement pstmnt = connection.prepareStatement(sql);
        pstmnt.setString(1, "Z-TESTPART-01");
        pstmnt.setCharacterStream(2, new BufferedReader(new StringReader(CATZ_REMARKS)), CATZ_REMARKS.length());
        pstmnt.execute();
        pstmnt.close();
    }

    private void addToCondition(Connection connection)
    throws SQLException {
        String sql = "insert into ZZ_JUT_CONDITION(CONDITION, DESCRIPTION) values('Z-TESTSYCD-01', 'Z-TESTSYCDDESC-01')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_CONDITION(CONDITION, DESCRIPTION) values('Z-TESTSYCD-02', 'Z-TESTSYCDDESC-02')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_CONDITION(CONDITION, DESCRIPTION) values('Z-TESTSYCD-03', 'Z-TESTSYCDDESC-03')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_CONDITION(CONDITION, DESCRIPTION) values('Z-TESTSYCD-04', 'Z-TESTNOUN-03')";
        executeSql(connection, sql);
    }

    private void addToItem(Connection connection)
    throws Exception {
        String date1, date2, date3;
        String engineType = ConnectionHelper.getEngineType();
        if (Parser.parseBoolean( Variant.getProperty(engineType, Variant.PROP_USE_TO_DATE_SQL_FUNCTION) ).booleanValue()) {
            date1 = "to_date('2003-09-10 20:30:40', 'yyyy-MM-dd hh24:mi:ss')";
            date2 = "to_date('2003-09-10', 'yyyy-MM-dd')";
            date3 = "null";
        } else {
            date1 = "'2003-09-10 20:30:40'";
            date2 = "'2003-09-10'";
            date3 = "null";
        }
        String sql = "insert into ZZ_JUT_ITEM(ITEM_ID, RECEIVED_ITEM_ID, SC, PART, PRIME, CONDITION, STATUS_1, STATUS_2, STATUS_3, QTY, KEY_REF, CREATED_DATETIME) VALUES('Z-TESTITEM-01', 'Z-TESTITEM-01', 'SOME SC', 'Z-TESTPART-01', 'Z-TESTPRIME-01', 'Z-TESTSYCD-01', 'X', 'S', 'A', 2, 'Z-TEST-KEY-REF', " + date1 + ")";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_ITEM(ITEM_ID, RECEIVED_ITEM_ID, SC, PART, PRIME, CONDITION, STATUS_1, STATUS_2, STATUS_3, QTY, KEY_REF, CREATED_DATETIME) VALUES('Z-TESTITEM-02', 'Z-TESTITEM-02', 'SOME SC', 'Z-TESTPART-01', 'Z-TESTPRIME-01', 'Z-TESTSYCD-01', 'X', 'S', 'A', 2, 'Z-TEST-KEY-REF', " + date2 + ")";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_ITEM(ITEM_ID, RECEIVED_ITEM_ID, SC, PART, PRIME, CONDITION, STATUS_1, STATUS_2, STATUS_3, QTY, KEY_REF, CREATED_DATETIME) VALUES('Z-TESTITEM-03', 'Z-TESTITEM-03', 'SOME SC', 'Z-TESTPART-01', 'Z-TESTPRIME-01', 'Z-TESTSYCD-01', 'X', 'S', 'A', 2, 'Z-TEST-KEY-REF', " + date3 + ")";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_ITEM(ITEM_ID, RECEIVED_ITEM_ID, SC, PART, PRIME, CONDITION, STATUS_1, STATUS_2, STATUS_3, QTY, KEY_REF, CREATED_DATETIME) VALUES('Z-TESTITEM-09', 'Z-TESTITEM-04', 'Z-TEST1SC', 'Z-TESTPART-04', 'Z-TESTPRIME-01', 'Z-TESTSYCD-01', 'Z', 'S', 'A', 2, 'Z-TEST-KEY-REF', " + date3 + ")";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_ITEM(ITEM_ID, RECEIVED_ITEM_ID, SC, PART, PRIME, CONDITION, STATUS_1, STATUS_2, STATUS_3, QTY, KEY_REF, CREATED_DATETIME) VALUES('Z-TESTITEM-10', 'Z-TESTITEM-05', 'Z-TEST1SC', 'Z-TESTPART-05', 'Z-TESTPRIME-01', 'Z-TESTSYCD-01', 'X', 'S', 'A', 2, 'Z-TEST-KEY-REF', " + date3 + ")";
        executeSql(connection, sql);
    }

    private void addToValidFieldValue(Connection connection)
    throws SQLException {
        String sql = null;

        // Valid values for the field STATUS fields of ZZ_JUT_ITEM
        sql = "insert into ZZ_JUT_VALID_FIELD_VALUE(TABLE_NAME, FIELD_NAME, LEGAL_VALUE, DESCRIPTION, REMARKS) VALUES('ZZ_JUT_ITEM', 'STATUS', 'X', '', '')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_VALID_FIELD_VALUE(TABLE_NAME, FIELD_NAME, LEGAL_VALUE, DESCRIPTION, REMARKS) VALUES('ZZ_JUT_ITEM', 'STATUS', 'S', '', '')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_VALID_FIELD_VALUE(TABLE_NAME, FIELD_NAME, LEGAL_VALUE, DESCRIPTION, REMARKS) VALUES('ZZ_JUT_ITEM', 'STATUS', 'A', '', '')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_VALID_FIELD_VALUE(TABLE_NAME, FIELD_NAME, LEGAL_VALUE, DESCRIPTION, REMARKS) VALUES('ZZ_JUT_ITEM', 'STATUS', 'Y', '', '')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_VALID_FIELD_VALUE(TABLE_NAME, FIELD_NAME, LEGAL_VALUE, DESCRIPTION, REMARKS) VALUES('ZZ_JUT_ITEM', 'STATUS', 'T', '', '')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_VALID_FIELD_VALUE(TABLE_NAME, FIELD_NAME, LEGAL_VALUE, DESCRIPTION, REMARKS) VALUES('ZZ_JUT_ITEM', 'STATUS', 'B', '', '')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_VALID_FIELD_VALUE(TABLE_NAME, FIELD_NAME, LEGAL_VALUE, DESCRIPTION, REMARKS) VALUES('ZZ_JUT_ITEM', 'STATUS', '1', '', '')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_VALID_FIELD_VALUE(TABLE_NAME, FIELD_NAME, LEGAL_VALUE, DESCRIPTION, REMARKS) VALUES('ZZ_JUT_ITEM', 'STATUS', '2', '', '')";
        executeSql(connection, sql);

        // Valid values for the field KEY_REF of ZZ_JUT_ITEM
        sql = "insert into ZZ_JUT_VALID_FIELD_VALUE(TABLE_NAME, FIELD_NAME, LEGAL_VALUE, DESCRIPTION, REMARKS) VALUES('ZZ_JUT_ITEM', 'KEY_REF', 'Z-TEST-KEY-REF', '', '')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_VALID_FIELD_VALUE(TABLE_NAME, FIELD_NAME, LEGAL_VALUE, DESCRIPTION, REMARKS) VALUES('ZZ_JUT_ITEM', 'KEY_REF', 'Z-TEST-KEY-REF-2', '', '')";
        executeSql(connection, sql);
        sql = "insert into ZZ_JUT_VALID_FIELD_VALUE(TABLE_NAME, FIELD_NAME, LEGAL_VALUE, DESCRIPTION, REMARKS) VALUES('ZZ_JUT_ITEM', 'KEY_REF', 'Z-TEST-KEY-REF-3', '', '')";
        executeSql(connection, sql);
    }

    private void executeSql(Connection connection, String sql)
    throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
    }




    private void createTables(Connection connection, String engine) throws IOException, SQLException {
        executeSql(connection, loadCreateTableScript("ZZ_JUT_INSTRUMENT", engine));
        executeSql(connection, loadCreateTableScript("ZZ_JUT_CONDITION", engine));
        executeSql(connection, loadCreateTableScript("ZZ_JUT_PART", engine));
        executeSql(connection, loadCreateTableScript("ZZ_JUT_PART_REM", engine));
        executeSql(connection, loadCreateTableScript("ZZ_JUT_PART_PIC", engine));
        executeSql(connection, loadCreateTableScript("ZZ_JUT_PART_REM_PIC", engine));
        executeSql(connection, loadCreateTableScript("ZZ_JUT_ITEM", engine));
        executeSql(connection, loadCreateTableScript("ZZ_JUT_VALID_FIELD_VALUE", engine));
        executeSql(connection, loadCreateTableScript("ZZ_JUT_ASSET", engine));
        executeSql(connection, loadCreateTableScript("ZZ_JUT_PART_ADDITIONAL", engine));

        // create the stored-procedure and its body
        executeSql(connection, loadCreateTableScript("ZZ_JUT_VOUCHER", engine));
        executeSql(connection, loadCreateTableScript("ZZ_JUT_VOUCHER_BODY", engine));
        executeSql(connection, loadCreateTableScript("ZZ_JUT_ITEM_SP", engine));
        executeSql(connection, loadCreateTableScript("ZZ_JUT_ITEM_SP_BODY", engine));


        if("oracle".equals(engine)) {
            // Special set up for auto key generation
            executeSql(connection, "CREATE SEQUENCE asset_tk_sequence START WITH 1");
            executeSql(connection, "CREATE OR REPLACE TRIGGER zz_jut_asset_asset_tk BEFORE INSERT ON zz_jut_asset FOR EACH ROW BEGIN select asset_tk_sequence.NEXTVAL into :new.asset_tk from dual; END;");
        }

    }

    private void dropTables(Connection connection, String engine) {

        if("oracle".equals(engine)) {
            // Special teardown for auto key generation
            try {
                executeSql(connection, "DROP SEQUENCE asset_tk_sequence");
            } catch (Exception e) {
            }
        }

        try {
            executeSql(connection, loadDropTableScript("ZZ_JUT_ITEM_SP", engine));
        } catch (Exception e) {
        }

        try {
            executeSql(connection, loadDropTableScript("ZZ_JUT_VOUCHER", engine));
        } catch (Exception e) {
        }

        try {
            executeSql(connection, loadDropTableScript("ZZ_JUT_PART_ADDITIONAL", engine));
        } catch (Exception e) {
        }

        try {
            executeSql(connection, loadDropTableScript("ZZ_JUT_ASSET", engine));
        } catch (Exception e) {
        }

        try {
            executeSql(connection, loadDropTableScript("ZZ_JUT_VALID_FIELD_VALUE", engine));
        } catch (Exception e) {
        }

        try {
            executeSql(connection, loadDropTableScript("ZZ_JUT_ITEM", engine));
        } catch (Exception e) {
        }

        try {
            executeSql(connection, loadDropTableScript("ZZ_JUT_PART_REM_PIC", engine));
        } catch (Exception e) {
        }

        try {
            executeSql(connection, loadDropTableScript("ZZ_JUT_PART_PIC", engine));
        } catch (Exception e) {
        }

        try {
            executeSql(connection, loadDropTableScript("ZZ_JUT_PART_REM", engine));
        } catch (Exception e) {
        }

        try {
            executeSql(connection, loadDropTableScript("ZZ_JUT_PART", engine));
        } catch (Exception e) {
        }

        try {
            executeSql(connection, loadDropTableScript("ZZ_JUT_CONDITION", engine));
        } catch (Exception e) {
        }

        try {
            executeSql(connection, loadDropTableScript("ZZ_JUT_INSTRUMENT", engine));
        } catch (Exception e) {
        }
    }


    private String loadCreateTableScript(String tableName, String engine) throws IOException {
        return loadFile("resources/create-table-scripts/" + engine + '/' + tableName + ".sql");
    }

    private String loadDropTableScript(String tableName, String engine) throws IOException {
        return loadFile("resources/drop-table-scripts/" + engine + '/' + tableName + ".sql");
    }

    private String loadFile(String resourceName) throws IOException {
        StringBuffer buf = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(URLHelper.getInputStream(resourceName)));
        String line = null;
        while ((line = reader.readLine()) != null) {
            buf.append(line);
            buf.append('\n');
        }
        return buf.toString();
    }
}
