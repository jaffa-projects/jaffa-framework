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

package org.jaffa.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.log4j.Logger;
import javax.naming.NamingException;

/** Settings refers to configuration 'values' that need to be made available in the
 * application. This class which should be extended, provided access to setting values
 * that are defined in web.xml as environment parameters.
 * <p>
 * An example of an entry in web.xml
 * <pre>
 *   &lt;env-entry&gt;
 *    &lt;env-entry-name&gt;myapp.email.smtpHost&lt;/env-entry-name&gt;
 *    &lt;env-entry-value&gt;mail.yahoo.com&lt;/env-entry-value&gt;
 *    &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;/env-entry&gt;
 * </pre>
 * <p>
 * An example of a method in a sub-class that access this setting
 * <pre>
 * public static String getEmailHost() {
 *     return (String) getProperty("myapp.email.smtpHost", String.class, null);
 * }
 * </pre>
 *
 * @version 1.0
 * @author PaulE
 * @since 1.3
 */
public abstract class SettingsBase {

    private static Logger log = Logger.getLogger(SettingsBase.class);

    protected static String JNDIBase = "java:comp/env";

    // Cache the context, so each call doesn't need to look it up
    private static Context envCtx = null;

    /** Return the value of the property from the JNDI resource
     * @param name Name of the property
     */
    protected static Object getProperty(String name) {
        if(getContext() == null)
            return null;
        try {
            return envCtx.lookup(name);
        } catch (NamingException e) {
            return null;
        }
    }

    /** Return the value of the property from the JNDI resource.
     * @param name Name of the property
     * @param type Specify the class of the return type, if the returned object does not match the
     * specified type a runtime error is raised!
     */
    protected static Object getProperty(String name, Class type) {
        if(getContext() == null)
            return null;
        try{
            Object o = envCtx.lookup(name);
            if (o == null)
                return null;
            else if (type.isInstance(o) )
                return o;
            else
                throw new RuntimeException("Type Mismatch in JNDI Setting. Object " + JNDIBase +
                "/" + name + " was of type " + o.getClass().getName() +
                " expected " + type.getName() );
        } catch (NamingException e) {
            return null;
        }
    }

    /** Return the value of the property from the JNDI resource.
     * @param name Name of the property
     * @param type Specify the class of the return type, if the returned object does not match the
     * @param defValue Specify the default laue to be returned if the property is not found or is Null
     * specified type a runtime error is raised!
     */
    protected static Object getProperty(String name, Class type, Object defValue) {
        if(getContext() == null) {
            log.warn("Failed to set conext for : " + JNDIBase);
            return defValue;
        }
        try{
            Object o = envCtx.lookup(name);
            log.debug("Found Entry : " + name);
            if(o != null) {
                return o;
            } else {
                log.debug("Entry Was Null!");
            }
        } catch (NamingException e) {
            log.debug("No Entry : " + name);
        }
        return defValue;
    }

    /** Get the JNDI context and cache it */
    private static synchronized Context getContext() {
        if(envCtx == null) {
            try {
                Context initCtx = new InitialContext();
                envCtx = (Context)initCtx.lookup(JNDIBase);
            } catch (NamingException e) {
                // not found
            }
            if(envCtx == null)
                log.fatal("Can't Find JNDI Context " + JNDIBase);
        }
        return envCtx;
    }
}
