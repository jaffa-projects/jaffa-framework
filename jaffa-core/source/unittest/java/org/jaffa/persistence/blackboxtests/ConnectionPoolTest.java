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

package org.jaffa.persistence.blackboxtests;

import helpers.ConnectionHelper;
import junit.framework.TestCase;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;

/**
 * This has connection pool related tests
 * @author  GautamJ
 * @version
 */
public class ConnectionPoolTest extends TestCase {
    
    /** Creates new QueryTest */
    public ConnectionPoolTest(String name) {
        super(name);
    }
    
    /** This test will create the 'maximumConnections as defined in init.xml' number of UOW objects. It will then try to create one more UOW, which is expected to throw an exception.
     */
    public void testConnectionsExhausted() {
        UOW[] uows = null;
        UOW oneMoreUow = null;
        try {
            // obtain the maximum number of connections from the pool
            int maximumConnections = ConnectionHelper.getMaximumConnections();
            uows = new UOW[maximumConnections];
            for (int i = 0; i < uows.length; i++)
                uows[i] = new UOW();
            
            // now create an additional UOW, which is expected to throw an exception
            try {
                oneMoreUow = new UOW();
                fail("Able to create an UOW even after maximum number of connections were obtained !!");
            } catch (UOWException e) {
                // This is expected.. do nothing
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            
        } finally {
            try {
                if (oneMoreUow != null)
                    oneMoreUow.rollback();
                
                if (uows != null) {
                    for (int i = 0; i < uows.length; i++) {
                        if (uows[i] != null) {
                            uows[i].rollback();
                            uows[i] = null;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }
    }
    
    
    /** This test will obtain the maximumConnections and relase them. The process will be repeated 100 times.
     */
    public void testConnectionPoolRobustness() {
        UOW[] uows = null;
        try {
            int maximumConnections = ConnectionHelper.getMaximumConnections();
            for (int iteration = 0; iteration < 100; iteration++) {
                // obtain the maximum number of connections from the pool
                uows = new UOW[maximumConnections];
                for (int i = 0; i < uows.length; i++)
                    uows[i] = new UOW();
                
                // Now release the connections
                for (int i = 0; i < uows.length; i++) {
                    uows[i].rollback();
                    uows[i] = null;
                }
                uows = null;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            
        } finally {
            try {
                if (uows != null) {
                    for (int i = 0; i < uows.length; i++) {
                        if (uows[i] != null) {
                            uows[i].rollback();
                            uows[i] = null;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }
    }
    
}
