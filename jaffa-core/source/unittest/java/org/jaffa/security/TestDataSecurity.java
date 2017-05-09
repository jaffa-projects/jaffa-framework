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
 * TestDataSecurity.java
 *
 * Created on July 16, 2004
 */

package org.jaffa.security;

import helpers.ConnectionHelper;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;
import junit.framework.TestCase;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.blackboxtests.Wrapper;
import org.jaffa.persistence.domainobjects.Item;

/**
 *
 * @author  maheshd
 * @version
 */
public class TestDataSecurity extends TestCase {
    
    /** Creates new TestDataSecurity */
    public TestDataSecurity(String name) {
        super(name);
    }
    
    static final String ENGINE = "engine";
    static final String URL = "url";
    static final String DRIVER_CLASS = "driverClass";
    static final String USER = "user";
    static final String PASSWORD = "password";
    static final String MAXIMUM_CONNECTIONS = "maximumConnections";
    
    protected void setUp() {
    }
    
    protected void tearDown() {
    }
    
    public void testItemFilterPolicy1() {
        Connection connection = null;
        String sql = null;
        try {
            UOW uow = null;
            connection = ConnectionHelper.getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            sql = "insert into zz_jut_item (item_id,part,qty,sc,status_1,status_2,status_3) " +
                " values('Z-TESTITEM-11,'Z-TESTPART-01','2' , 'JAFFASC1','X','S','A')";
            statement.execute(sql);
            sql = "insert into zz_jut_item (item_id,part,qty,sc,status_1,status_2,status_3) " +
                " values('Z-TESTITEM-12,'Z-TESTPART-01','2' , 'JAFFASC2','X','S','A')";
            statement.execute(sql);
            sql = "insert into zz_jut_item (item_id,part,qty,sc,status_1,status_2,status_3) " +
                " values('Z-TESTITEM-13,'Z-TESTPART-01','2' , 'SOME SC','X','S','A')";
            statement.execute(sql);
            sql = "insert into zz_jut_item (item_id,part,qty,sc,status_1,status_2,status_3) " +
                " values('Z-TESTITEM-14,'Z-TESTPART-01','2' , 'SOME SC','X','S','A')";
            statement.execute(sql);
            connection.commit();
            sql = "exec jaffa_sec.set_userid('TESTUSER',role('Clerk')))";
            statement.execute(sql);
            if(uow == null){
                uow = new UOW();
            }
            
            assertTrue("Should retreive Item - Z-TESTITEM-10 for this user",Item.exists(uow,"Z-TESTITEM-11"));
            assertTrue("Should retreive Item - Z-TESTITEM-11 for this user",Item.exists(uow,"Z-TESTITEM-12"));
            assertTrue("Should not retreive Item - Z-TESTITEM-12 for this user",!Item.exists(uow,"Z-TESTITEM-13"));
            assertTrue("Should not retreive Item - Z-TESTITEM-13 for this user",!Item.exists(uow,"Z-TESTITEM-14"));
            
            sql = "exec jaffa_sec.set_userid('TESTUSER',role('Manager')))";
            statement.execute(sql);
            
            assertTrue("Should retreive Item - Z-TESTITEM-10 for this user",Item.exists(uow,"Z-TESTITEM-11"));
            assertTrue("Should retreive Item - Z-TESTITEM-11 for this user",Item.exists(uow,"Z-TESTITEM-12"));
            assertTrue("Should retreive Item - Z-TESTITEM-12 for this user",Item.exists(uow,"Z-TESTITEM-13"));
            assertTrue("Should retreive Item - Z-TESTITEM-13 for this user",Item.exists(uow,"Z-TESTITEM-14"));
            
            
            for(int i = 1; i<= 4;i++){
                sql = "delete zz_jut_item where item_id = 'Z-TESTITEM-1" + i + "'";
            }
            statement.execute(sql);
            connection.close();
            connection = null;
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
}