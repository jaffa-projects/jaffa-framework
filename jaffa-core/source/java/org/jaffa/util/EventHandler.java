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
 * EventHandler.java
 *
 * Created on November 21, 2001, 10:50 AM
 */

package org.jaffa.util;

import java.util.*;

/** This class determines how an event posted by a browser is to be handled.
 */
public class EventHandler {

    /**
     * This method will generate an array of EventHandler.Method objects.
     * Each object will have a Name, the signature & the parameters
     * For eg: if eventId="grid1=4;partLookup;clicked"
     * The following methods will be generated:
     * do_grid1_4_partLookup_clicked
     * do_grid1_partLookup_clicked(String v1), v1="4"
     * do_grid1_clicked(String v1, String v2), v1="4" & v2="partLookup"
     * @param eventId The event.
     * @return an array of EventHandler.Method objects.
     */
    public static EventHandler.Method[] getEventMethods(String eventId) {
        List methods = new ArrayList();
        if (eventId != null) {
            String fieldName = null;
            List arguments = new ArrayList();
            String eventName = null;

            // Build the arguments list
            StringTokenizer stz = new StringTokenizer(eventId, " =;");
            while (stz.hasMoreTokens())
                arguments.add(stz.nextToken());

            // the 1st element in the argument list is the fieldName
            if (arguments.size() > 0)
                fieldName = (String) arguments.remove(0);

            // the last element in the argument list is the eventName
            if (arguments.size() > 0)
                eventName = (String) arguments.remove( arguments.size() - 1 );

            if (arguments.size() == 0) {
                Method method = new Method(fieldName);
                if (eventName != null)
                    method.appendName(eventName);
                methods.add(method);
            } else {
                for (int i = 0; i <= arguments.size(); i++) {
                    Method method = new Method(fieldName);

                    // Append arguments to the method-signature
                    int j = 0;
                    for (; j < i; j++)
                        method.appendArgument( String.class, (String) arguments.get(j) );

                    // Append the remaining arguments to the method-name
                    int k = j;
                    for (; k < arguments.size(); k++)
                        method.appendName( (String) arguments.get(k) );

                    method.appendName(eventName);
                    methods.add(method);
                }
            }

        }
        return (EventHandler.Method[]) methods.toArray(new EventHandler.Method[0]);
    }

    /** This class respresents an EventHandler method. It has the method name,
     * the argument types and the argument values.
     */
    public static class Method {
        private StringBuffer m_name = new StringBuffer("do");
        private List m_argumentTypes = new ArrayList();
        private List m_argumentValues = new ArrayList();

        /** Constructor.
         * @param name The name of the event handler method.
         */
        public Method(String name) {
            appendName(name);
        }

        /** Getter for property name.
         * @return Value of property name.
         */
        public String getName() {
            return m_name.toString();
        }

        /** Getter for property argumentTypes.
         * @return Value of property argumentTypes.
         */
        public Class[] getArgumentTypes() {
            return (Class[]) m_argumentTypes.toArray(new Class[0]);
        }

        /** Getter for property argumentValues.
         * @return Value of property argumentValues.
         */
        public Object[] getArgumentValues() {
            return m_argumentValues.toArray();
        }

        /** Returns diagnostic information.
         * @return diagnostic information.
         */
        public String toString() {
            StringBuffer buf = new StringBuffer(getName());
            buf.append('(');
            for (int i = 0; i < m_argumentTypes.size(); i++) {
                if (i > 0) {
                    buf.append(',');
                    buf.append(' ');
                }
                buf.append( ( (Class) m_argumentTypes.get(i) ).getName() );
                buf.append('=');
                buf.append( m_argumentValues.get(i) );
            }
            buf.append(')');
            return buf.toString();
        }

        private void appendName(String name) {
            if (name != null) {
                m_name.append('_');
                m_name.append(name);
            }
        }

        private void appendArgument(Class argumentType, Object argumentValue) {
            if (argumentType != null && argumentValue != null) {
                m_argumentTypes.add(argumentType);
                m_argumentValues.add(argumentValue);
            }
        }
    }

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        try {
//            print(null);
//            print("");
//            print("g1=12;partNo;validate");
//            print("g1=12;partNo;P1;segCode;clicked");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void print(String in) {
//        System.out.println("Input EventId: " + in);
//        EventHandler.Method[] methods = EventHandler.getEventMethods(in);
//        for (int i = 0; i < methods.length; i++)
//            System.out.println(methods[i]);
//        System.out.println();
//
//    }
//

}
