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

/*
 * TestPolicy.java
 *
 * Created on April 5, 2002, 2:11 PM
 */
package org.jaffa.security;

import org.jaffa.security.securityrolesdomain.*;
import org.jaffa.security.businessfunctionsdomain.*;
import java.io.InputStream;
import java.util.List;
import java.util.Iterator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.jaffa.util.XmlHelper;
import java.io.IOException;
import org.jaffa.util.JAXBHelper;
import org.xml.sax.SAXException;

/**
 *
 * @author  paule
 * @version
 */
public class TestPolicy {

    static String FUNCTION_FILE = "resources/business-functions.xml";
    static String ROLE_FILE = "resources/Roles.xml";
    private static final String BF_SCHEMA = "org/jaffa/security/businessfunctionsdomain/business-functions_1_0.xsd";
    private static final String ROLE_SCHEMA = "org/jaffa/security/securityrolesdomain/security-roles_1_0.xsd";
    BusinessFunctions bfs = null;
    Roles rls = null;

    /** Creates new TestPolicy */
    public TestPolicy() {
    }

    public void loadBF() {

        // Load business functions
        InputStream in = ClassLoader.getSystemResourceAsStream(FUNCTION_FILE);
        try {
            // create a JAXBContext capable of handling classes generated into the package
            JAXBContext jc = JAXBContext.newInstance("org.jaffa.security.businessfunctionsdomain");

            // create an Unmarshaller
            Unmarshaller u = jc.createUnmarshaller();

            // enable validation
            u.setSchema(JAXBHelper.createSchema(BF_SCHEMA));

            // unmarshal a document into a tree of Java content objects composed of classes from the package.
            bfs = (BusinessFunctions) u.unmarshal(XmlHelper.stripDoctypeDeclaration(in));
        } catch (JAXBException e) {
            bfs = null;
            e.printStackTrace();
        } catch (SAXException e) {
            bfs = null;
            e.printStackTrace();
        } catch (IOException e) {
            bfs = null;
            e.printStackTrace();
        }
        System.out.println("Loaded BusinessFunctions");

        List l = bfs.getBusinessFunction();
        for (Iterator it = l.iterator(); it.hasNext();) {
            BusinessFunction bf = (BusinessFunction) it.next();
            System.out.println("Function: " + bf.getName() + " (" + bf.getDescription() + ")");
        }
    }

    public void loadRoles() {

        // Load business functions
        InputStream in = ClassLoader.getSystemResourceAsStream(ROLE_FILE);
        try {
            // create a JAXBContext capable of handling classes generated into the package
            JAXBContext jc = JAXBContext.newInstance("org.jaffa.security.securityrolesdomain");

            // create an Unmarshaller
            Unmarshaller u = jc.createUnmarshaller();

            // enable validation
            u.setSchema(JAXBHelper.createSchema(ROLE_SCHEMA));

            // unmarshal a document into a tree of Java content objects composed of classes from the package.
            rls = (Roles) u.unmarshal(XmlHelper.stripDoctypeDeclaration(in));
        } catch (JAXBException e) {
            rls = null;
            e.printStackTrace();
        } catch (SAXException e) {
            rls = null;
            e.printStackTrace();
        } catch (IOException e) {
            rls = null;
            e.printStackTrace();
        }
        System.out.println("Loaded Roles");

        List l = rls.getRole();
        for (Iterator it = l.iterator(); it.hasNext();) {
            Role rl = (Role) it.next();
            System.out.println("Role: " + rl.getName() + " (" + rl.getDescription() + ")");
            List l2 = rl.getGrantFunctionAccess();
            for (Iterator it2 = l2.iterator(); it2.hasNext();) {
                GrantFunctionAccess gfa = (GrantFunctionAccess) it2.next();
                System.out.println("    Grant: " + gfa.getName());
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        TestPolicy t = new TestPolicy();

        t.loadBF();
        t.loadRoles();
    }
}
