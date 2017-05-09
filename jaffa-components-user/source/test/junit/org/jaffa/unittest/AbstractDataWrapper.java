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

package org.jaffa.unittest;

import junit.framework.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import org.jaffa.config.Config;
import org.jaffa.util.URLHelper;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Init;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Database;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Param;
import org.jaffa.util.XmlHelper;

/** This class has the methods for one time setup/cleanup of data, before all the
 *  tests executed by the suite utilising this class. It expected that this class 
 *  will be subclassed for specific data setup, and then used to wrapper a test
 * <code>
 * public class MyTest extends TestCase {
 *
 *     public static Test suite() {
 *         return new MyDataWrapper(new TestSuite(MyTest.class));
 *     }
 *
 *     ...
 * }
 * </code>
 * Where MyDataWrapper extends AbstractDataWrapper
 *
 * @author  PaulE
 */
public abstract class AbstractDataWrapper extends ContextWrapper {
    
    private static final Logger log = Logger.getLogger(AbstractDataWrapper.class);
    
    static final String ENGINE = "engine";
    static final String URL = "url";
    static final String DRIVER_CLASS = "driverClass";
    static final String USER = "user";
    static final String PASSWORD = "password";
    static final String MAXIMUM_CONNECTIONS = "maximumConnections";
    
    /** The constructor.
     * @param test The Test class, for which the Wrapper will be utilised.
     */
    public AbstractDataWrapper(Test test) {
        super(test);
    }
    
    /** This sets up the data before the test fixtures are invoked.
     */
    protected void setUp() {
        super.setUp();
        Connection connection = null;
        try {
            Map info = getDatabaseInfo();
            connection = getConnection((String) info.get(DRIVER_CLASS), (String) info.get(URL), (String) info.get(USER), (String) info.get(PASSWORD));

            // cleanup any errorneous data
            tearDownData(connection);
            
            // create brand new tables and add new data
            setUpData(connection);
            
            connection.commit();
            log.info("SETUP: Reference Data Created");
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
            Map info = getDatabaseInfo();
            connection = getConnection((String) info.get(DRIVER_CLASS), (String) info.get(URL), (String) info.get(USER), (String) info.get(PASSWORD));
            //dropTables(connection, (String) info.get(ENGINE));
            tearDownData(connection);
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
        
        super.tearDown();
        log.info("TEARDOWN: Reference Data Dropped");
    }
    
    
    static Map getDatabaseInfo() throws Exception {
        URL initUrl = URLHelper.newExtendedURL((String) Config.getProperty(Config.PROP_JDBC_ENGINE_INIT));
        
        // create a JAXBContext capable of handling classes generated into the package
        JAXBContext jc = JAXBContext.newInstance("org.jaffa.persistence.engines.jdbcengine.configservice.initdomain");
        
        // create an Unmarshaller
        Unmarshaller u = jc.createUnmarshaller();
        
        // enable validation
        u.setValidating(true);
        
        // unmarshal a document into a tree of Java content objects composed of classes from the package.
        Init init = (Init) u.unmarshal(XmlHelper.stripDoctypeDeclaration(initUrl));
        Database database = null;
        for (Iterator i = init.getDatabase().iterator(); i.hasNext(); ) {
            database = (Database) i.next();
            if (database.getName().equals("default"))
                break;
        }
        
        if (database == null)
            throw new Exception("The 'default' database has not been defined in init.xml ");
        
        Map info = new HashMap();
        info.put(ENGINE, database.getEngine());
        for (Iterator i = database.getConnectionFactory().getParam().iterator(); i.hasNext(); ) {
            Param param = (Param) i.next();
            if (URL.equals(param.getName()))
                info.put(URL, param.getValue());
            else if (DRIVER_CLASS.equals(param.getName()))
                info.put(DRIVER_CLASS, param.getValue());
            else if (USER.equals(param.getName()))
                info.put(USER, param.getValue());
            else if (PASSWORD.equals(param.getName()))
                info.put(PASSWORD, param.getValue());
            else if (MAXIMUM_CONNECTIONS.equals(param.getName()))
                info.put(MAXIMUM_CONNECTIONS, param.getValue());
        }
        return info;
    }
    
    private Connection getConnection(String driverClass, String url, String user, String password)
    throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        return connection;
    }

    protected void executeSql(Connection connection, String sql)
    throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
    }

    /** Need to implement this to possibly create tables and set up the data */
    protected abstract void setUpData(Connection connection) throws Exception;

    /** Need to implement this to delete the data or drop the tables */
    protected abstract void tearDownData(Connection connection) throws Exception;
    
}
