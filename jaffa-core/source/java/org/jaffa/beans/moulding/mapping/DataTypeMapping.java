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
 * DataTypeMapping.java
 *
 * Created on February 18, 2004, 6:14 PM
 */

package org.jaffa.beans.moulding.mapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;

/**
 * @deprecated - New class org.jaffa.datatypes.DataTypeMapper should be used instead of this one
 *
 * @author  PaulE
 */
public class DataTypeMapping {
    private static Logger log = Logger.getLogger(DataTypeMapping.class);
/*
    interchange - with no data loss
        DateOnly -> DateTime
        DateTime -> Long, Calander, Date
        Character -> Short, String
        Short -> Integer
        Integer -> Long
        Float -> Double
        Boolean -> String
    interchange - with possible data loss
        DateTime -> DateOnly
        Long -> Integer
        Integer -> Short
        String -> Boolean, Long, DateTime, Double
        Double -> Float
*/

    /** This is a map where the key is a Class object that is mappable
     *  each entry is a Map, where the key is a Class that indicates what
     *  class it is mappable to, and the value is the method to use for the
     *  translation
     */
    private static Map mapping = new HashMap();


    static {
        Map m;
        Method method;
        Constructor cons;
        try {
            //---------------------------------------------------------------
            // Set up tranlators for a source of org.jaffa.datatypes.DateTime
            m = new HashMap();
            //---------------------
            // convert to a Calander
            method = DateTime.class.getMethod("getCalendar",new Class[] {});
            m.put(Calendar.class, method);
            //---------------------
            // convert to a Date
            method = DateTime.class.getMethod("getUtilDate",new Class[] {});
            m.put(Date.class, method);
            //---------------------
            mapping.put(DateTime.class, m);

            //---------------------------------------------------------------
            // Set up tranlators for a source of org.jaffa.datatypes.DateOnly
            m = new HashMap();
            //---------------------
            // convert to a Calander
            method = DateOnly.class.getMethod("getCalendar",new Class[] {});
            m.put(Calendar.class, method);
            //---------------------
            // convert to a Date
            method = DateOnly.class.getMethod("getUtilDate",new Class[] {});
            m.put(Date.class, method);
            //---------------------
            // convert to a DateTime
            method = DateOnly.class.getMethod("toDateTime",new Class[] {DateOnly.class});
            m.put(DateTime.class, method);
            //---------------------
            mapping.put(DateOnly.class, m);

            //---------------------------------------------------------------
            // Set up tranlators for a source of java.util.Calander
            m = new HashMap();
            //---------------------
            // convert to a DateTime
            cons = DateTime.class.getConstructor(new Class[] {Calendar.class});
            m.put(DateTime.class, cons);
            //---------------------
            // convert to a DateTime
            cons = DateOnly.class.getConstructor(new Class[] {Calendar.class});
            m.put(DateOnly.class, cons);
            //---------------------
            mapping.put(Calendar.class, m);

            //---------------------------------------------------------------
            // Set up tranlators for a source of java.util.Date
            m = new HashMap();
            //---------------------
            // convert to a DateTime
            cons = DateTime.class.getConstructor(new Class[] {Date.class});
            m.put(DateTime.class, cons);
            //---------------------
            // convert to a DateTime
            cons = DateOnly.class.getConstructor(new Class[] {Date.class});
            m.put(DateOnly.class, cons);
            //---------------------
            mapping.put(Date.class, m);

        } catch (NoSuchMethodException e) {
            log.error("Problem findTarget(m, target)initializing data type mappings", e);
            throw new NoSuchMethodError(e.getMessage());
        }
    }


    public static boolean isMappable(Class source, Class target) {
        Map m = findSource(source);
        return (m!=null && findTarget(m, target)!=null);
    }

    /** Creates a new instance of DataTypeMapping */
    public static Object map(Object source, Class target) throws ClassCastException {
        // Map null straight back!
        if(source==null)
            return null;

        try {
            log.debug("Looking for source class: " + source.getClass());
            Map m = findSource(source.getClass());
            if(m==null)
                throw new ClassCastException("Class is not a mappable - no translation exists");
            log.debug("Looking for target translator: " + target);
            Object translator = findTarget(m, target);
            if(translator == null)
                throw new ClassCastException("Class can't be mapped - null translator");
            if(translator instanceof Method) {
                Method me = (Method) translator;
                if(me.getParameterTypes()==null || me.getParameterTypes().length==0)
                    // no imputs
                    return me.invoke(source, new Object[] {});
                else
                    // assume static with source as input
                    return me.invoke(null, new Object[] {source});
            } else if (translator instanceof Constructor) {
                Constructor con = (Constructor) translator;
                return con.newInstance(new Object[] {source});
            } else
                throw new ClassCastException("Class can't be mapped - unknown translator");
        } catch (ClassCastException e) {
            throw e;
        } catch (Exception e) {
            if(e instanceof ClassCastException)
                throw (ClassCastException)e;
            log.error("Problem during mapping. Source=" + source.getClass() + ", Target=" + target,e);
            throw new ClassCastException("Problem during mapping");
        }
    }

    private static Map findSource(Class source) {
        if( mapping.containsKey(source) )
            return (Map) mapping.get(source);
        // Loop through all source objects to see if there is baseclass
        for(Iterator i = mapping.keySet().iterator();i.hasNext();) {
            Class s = (Class) i.next();
            if (s.isAssignableFrom(source))
                return (Map) mapping.get(s);
        }
        log.debug("Can't find source mapping for " + source);
        return null;
    }

    private static Object findTarget(Map methods, Class target) {
        if( methods.containsKey(target) )
            return methods.get(target);
        // Loop through all source objects to see if there is baseclass
        for(Iterator i = methods.keySet().iterator();i.hasNext();) {
            Class s = (Class) i.next();
            if (target.isAssignableFrom(s))
                return (Map) methods.get(s);
        }
        log.debug("Can't find target mapping for " + target);
        return null;
    }

}
