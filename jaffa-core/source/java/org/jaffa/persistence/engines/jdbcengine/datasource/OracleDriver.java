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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.persistence.engines.jdbcengine.datasource;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Properties;
import oracle.jdbc.OracleConnection;
import org.apache.log4j.Logger;

/**
 * This class is an extension to the stock OracleDriver class.
 * It adds support for the following properties, which is not the case in the base class (as of Oracle JDBC Driver version - "10.2.0.3.0")
 * - stmt_cache_size
 * - ImplicitStatementCachingEnabled
 *
 * Caching of PreparedStatements at the driver level can thus be controlled through the passing
 * of configuration properties.
 */
public class OracleDriver extends oracle.jdbc.driver.OracleDriver {

    private static final Logger log = Logger.getLogger(OracleDriver.class);

    // Register this driver with the DriverManager; but only after deregistering the original driver
    static {
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            if (drivers != null) {
                Collection<Driver> driversToDeregister = new LinkedList<Driver>();
                while (drivers.hasMoreElements()) {
                    Driver driver = drivers.nextElement();
                    if (driver instanceof oracle.jdbc.driver.OracleDriver)
                        driversToDeregister.add(driver);
                }
                if (log.isDebugEnabled() && driversToDeregister.size() > 0)
                    log.debug("Deregistering existing Oracle drivers: " + driversToDeregister);
                for (Driver driver : driversToDeregister)
                    DriverManager.deregisterDriver(driver);
            }

            if (log.isDebugEnabled())
                log.debug("Registering this custom OracleDriver");
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            String s = "Exception thrown while regsitering the custom OracleDriver with the DriverManager";
            log.fatal(s, e);
            throw new RuntimeException(s, e);
        }
    }

    /**
     * This method is an extension to the similarly named method of the base class.
     * It acquires a Connection by invoking the connect() method of the base class.
     * It stamps the following properties on the Connection, which is not the case in the base class (as of Oracle JDBC Driver version - "10.2.0.3.0")
     * - stmt_cache_size
     * - ImplicitStatementCachingEnabled
     * @param url the URL of the database to which to connect.
     * @param info a list of arbitrary string tag/value pairs as connection arguments. Normally at least a "user" and "password" property should be included.
     * @return a Connection object that represents a connection to the URL.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if (log.isDebugEnabled())
            log.debug("Acquiring Connection with url='" + url + "' and properties=" + info);
        Connection connection = super.connect(url, info);
        if (connection != null && info != null) {
            String s;
            s = info.getProperty("stmt_cache_size");
            if (s != null)
                ((OracleConnection) connection).setStatementCacheSize(Integer.parseInt(s));
            s = info.getProperty("ImplicitStatementCachingEnabled");
            if (s != null)
                ((OracleConnection) connection).setImplicitCachingEnabled(s.equals("true"));
        }
        return connection;
    }
}
