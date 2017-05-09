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
 * ContextManager.java
 *
 * Created on October 10, 2004, 11:52 PM
 */

package org.jaffa.session;

/**
 * Factory implementation for getting the ContextManager.
 *
 * By default this will use the org.jaffa.session.ContextManager.
 *
 * To change the default you can either set the DEFAULT_CLASS field prior
 * to the first call to ContextManagerFactory.instance(), or set the JVM parameter
 * 'org.jaffa.session.ContextManagerFactory' with the prefered class.
 * <code>
 * java -Dorg.jaffa.session.ContextManagerFactory=com.myapp.MyContextManager ...
 * </code>
 *
 * @author  PaulE
 */
public class ContextManagerFactory {

    /** This will contain the IContextManager implementation.
     * Change it to your desired implementation.
     * NOTE: This change will have no effect once the instance() method has been invoked.
     */
    public static Class DEFAULT_CLASS;

    static {
        try {
            DEFAULT_CLASS = System.getProperty(ContextManagerFactory.class.getName()) != null ?
            Class.forName( System.getProperty(ContextManagerFactory.class.getName()) ) :
                ContextManager.class;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(ContextManagerFactory.class.getName() +
            " Can't Create Context Manager : Class Not Found " +
            System.getProperty(ContextManagerFactory.class.getName()), e);
        }
    }

    private static IContextManager m_manager = null;

    /** Returns an implementation of the IContextManager interface.
     * @return Returns an implementation of the IContextManager interface.
     */
    public static IContextManager instance() {
        if(m_manager == null)
            createInstance(false);
        return m_manager;
    }

    private static synchronized void createInstance(boolean force) {
        if(force || m_manager == null) {
            try {
                m_manager = (IContextManager) DEFAULT_CLASS.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(ContextManagerFactory.class.getName() +
                " Can't Create Context Manager : " + e.getLocalizedMessage(), e);
            }
        }
    }

    /** Drop the old version of the Context Manager and force creation of a new one
     * This should cause the new version to be initialized again, so any cached changes should be lost
     */
    public static void newInstance() {
       createInstance(true);
    }
}
