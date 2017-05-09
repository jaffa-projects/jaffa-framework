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

package org.jaffa.modules.user.services;

import java.sql.PreparedStatement;
import junit.framework.Test;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateTime;
import org.jaffa.unittest.AbstractDataWrapper;
import org.jaffa.unittest.UnitTestUtil;
import org.jaffa.util.URLHelper;

/** This class has the methods for one time setup/cleanup of user data,
 * before all the tests executed by the suite utilising this class.
 *
 * @author  PaulE
 */
public class UserDataWrapper extends AbstractDataWrapper {

    private static final Logger log = Logger.getLogger(UserDataWrapper.class);

    // Test Data Statics
    public static final String USER1 = "TEST-USER-1";
    public static final String[] USER1_ROLES = new String[] {"TESTROLE1","TESTROLE2"};

    public static final String USER2 = "TEST-USER-2";
    public static final String[] USER2_ROLES = new String[] {};

    public static final String PASSWORD = "CY9rzUYh03PK3k6DJie09g=="; //MD5("dummy")
    public static final String EMAIL = "test@jaffa.sf.net";

    /** The constructor.
     * @param test The Test class, for which the Wrapper will be utilised.
     */
    public UserDataWrapper(Test test) {
        super(test);
    }

    protected void setUpData(Connection connection)
    throws Exception {
        addUsers(connection);
    }

    private void addUsers(Connection connection)
    throws SQLException {
        //1=USER_NAME*, FIRST_NAME*, LAST_NAME*, PASSWORD*, STATUS*,
        //6=E_MAIL_ADDRESS, CREATED_DATETIME, CREATED_USER, LAST_UPDATE_DATETIME, LAST_UPDATE_USER,
        //11=COMMENTS
        String sql = "insert into USERS values (?,?,?,?,?,  ?,?,?,?,?, ?)";
        PreparedStatement pstmnt = connection.prepareStatement(sql);

        // Create first record
        pstmnt.setString(1, USER1);
        pstmnt.setString(2, USER1);
        pstmnt.setString(3, USER1);
        pstmnt.setString(4, PASSWORD);
        pstmnt.setString(5, "A");

        pstmnt.setString(6, EMAIL);
        pstmnt.setTimestamp(7, DateTime.addMonth(new  DateTime(), -2).timestamp());
        pstmnt.setString(8, "user2");
        pstmnt.setTimestamp(9,(new DateTime()).timestamp());
        pstmnt.setString(10, "user3");

        pstmnt.setString(11, "No Comment!");
        pstmnt.execute();
        addUserRoles(connection, USER1, USER1_ROLES);
        pstmnt.clearParameters();

        // Create second record
        pstmnt.setString(1, USER2);
        pstmnt.setString(2, USER2);
        pstmnt.setString(3, USER2);
        pstmnt.setString(4, PASSWORD);
        pstmnt.setString(5, "A");

        pstmnt.setString(6, EMAIL);
        pstmnt.setTimestamp(7, DateTime.addMonth(new  DateTime(), -2).timestamp());
        pstmnt.setString(8, "user2");
        pstmnt.setTimestamp(9,null);
        pstmnt.setString(10, null);

        pstmnt.setString(11, "No Comment!");
        pstmnt.execute();
        addUserRoles(connection, USER2, USER2_ROLES);
        pstmnt.close();
    }

    private void addUserRoles(Connection connection, String user, String[] roles)
    throws SQLException {
		if(user==null || roles==null || roles.length == 0)
		    return;

        //1=USER_NAME*, ROLE_NAME*
        //6=CREATED_ON, CREATED_BY, LAST_CHANGED_ON, LAST_CHANGED_BY
        String sql = "insert into USER_ROLE values (?,?)";
        PreparedStatement pstmnt = connection.prepareStatement(sql);

        // Create first record
        for(String role : roles) {
			pstmnt.setString(1, USER1);
			pstmnt.setString(2, role);
			pstmnt.execute();
			pstmnt.clearParameters();
		}

        pstmnt.close();
    }


    protected void tearDownData(Connection connection)
    throws Exception {
        executeSql(connection, "delete from USERS where USER_NAME like 'TEST-USER-%'");
        executeSql(connection, "delete from USER_ROLE where USER_NAME like 'TEST-USER-%'");
    }

}
