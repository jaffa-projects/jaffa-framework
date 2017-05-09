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

package org.jaffa.persistence.engines.jdbcengine.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.jaffa.persistence.IPersistent;

/** This class is a factory for obtaining IPersistent instances.
 */
public class PersistentInstanceFactory {
    
    /** Generates an appropriate instance for the input persistentClass.
     * If the persistentClass is a 'Class', then it should implement the 'IPersistent' interface. The persistence engine will simply instantiate the class.
     * If the persistentClass is an 'Interface', then the persistence engine will generate a dynamic proxy, to implement the IPersistent and the 'persistentClass' interfaces.
     * A RuntimeException will be thrown if any error occurs during instantiation.
     * @param persistentClass The actual persistentClass which can represent a 'Class' or an 'Interface'
     * @return an instance implementing the IPersistent interface.
     */
    public static IPersistent newPersistentInstance(Class persistentClass) {
        try {
            if (persistentClass.isInterface()) {
                Class[] interfaces = IPersistent.class.isAssignableFrom(persistentClass) ?
                new Class[] {persistentClass} : new Class[] {IPersistent.class, persistentClass};
                return (IPersistent) Proxy.newProxyInstance(persistentClass.getClassLoader(), interfaces, new PersistentInstanceInvocationHandler(persistentClass));
            } else {
                return (IPersistent) persistentClass.newInstance();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error in instantiating a persistent class", e);
        }
    }
    
    
    /** This is a helper method to determine the actual class which was used to create an IPersistent instance.
     * It is quite possible that the input object is a dynamic proxy.
     * @param object The object which implements the IPersistent instance.
     * @return The class which was used for instantiating the instance.
     */
    public static Class getActualPersistentClass(IPersistent object) {
        Class clazz = object.getClass();
        if (Proxy.isProxyClass(clazz)) {
            Class[] interfaces = clazz.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                if (interfaces[i] != IPersistent.class) {
                    clazz = interfaces[i];
                    break;
                }
            }
        }
        return clazz;
    }
    
    /** This is used by the MoldingService to initialize the Persistent instance.
     * This will merely add the attribute/value to the Map in the PersistentInstanceInvocationHandler.
     * This will bypass the validations performed during invocation of the setXyz() method on the persistent object.
     * Nothing will be done if the input object is not a Proxy or if its InvocationHandler is not an instance of PersistentInstanceInvocationHandler.
     * @param object The Proxy object for an IPersistence interface.
     * @param attributeName The attribute whose value will be set.
     * @param value The value to be set.
     */
    public static void setInstanceValue(IPersistent object, String attributeName, Object value) {
        if (Proxy.isProxyClass(object.getClass())) {
            InvocationHandler handler = Proxy.getInvocationHandler(object);
            if (handler instanceof PersistentInstanceInvocationHandler)
                ((PersistentInstanceInvocationHandler) handler).addAttributeValue(attributeName, value);
        }
    }
    
}
