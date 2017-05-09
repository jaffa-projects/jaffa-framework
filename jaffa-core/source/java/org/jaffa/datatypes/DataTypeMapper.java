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
 * DataTypeMapper.java
 *
 * Created on February 18, 2004, 6:14 PM
 */

package org.jaffa.datatypes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;
import org.jaffa.util.StringHelper;

/**
 * This class converts and Object between two differnt class types. This can
 * be useful for code that uses reflection to set parameters, where the source
 * data is in different class that the method being invoked expects.
 * <p>
 * <b>Example</b>
 * <code><pre>
 * // Input parameters are of mixed types
 * Object[] params = {"Hello","true",new Long(System.currentTimeMillis())};
 * // Find method with specific parameter types
 * Method m = MyClass.class.getDeclaredMethod("test", new Class[] {String.class, Boolean.TYPE, DateTime.class});
 * // Convert input parameters
 * for(int i=0; i< m.getParameterTypes().length; i++) {
 *     params[i] = DataTypeMapper.instance().map( params[i], m.getParameterTypes()[i] );
 * }
 * m.invoke(null, params);
 * </pre></code>
 * @author  PaulE
 */
public class DataTypeMapper {
    private static Logger log = Logger.getLogger(DataTypeMapper.class);
    
    /* variable to hold singleton instance of class */
    private static DataTypeMapper instance = null;
    
    /**
     * Get singleton instance of this class
     * @return instance of DataTypeMapper
     */
    public static DataTypeMapper instance() {
        if(instance == null)
            createInstance();
        return instance;
    }
    
    /** Creates a singleton instance of this class
     */
    private static synchronized void createInstance() {
        if(instance == null)
            instance = new DataTypeMapper();
    }
    
    /** This is a map where the key is a Class object that is mappable
     *  each entry is a Map, where the key is a Class that indicates what
     *  class it is mappable to, and the value is the method to use for the
     *  translation
     */
    private Map mapping = new LinkedHashMap();
    
    private DataTypeMapper() {
        Map m;
        try {
            //---------------------------------------------------------------
            // Set up tranlators for a source of org.jaffa.datatypes.DateTime
            m = new HashMap();
            //---------------------
            // convert to a Calander
            m.put(Calendar.class, DateTime.class.getMethod("calendar",new Class[] {}));
            //---------------------
            // convert to a Date
            m.put(Date.class, DateTime.class.getMethod("getUtilDate",new Class[] {}));
            //---------------------
            // convert to a String
            m.put(String.class, Formatter.class.getMethod("format", new Class[] {Object.class, String.class}));
            //---------------------
            // convert to a java.sql.Date
            m.put(java.sql.Date.class, DateTime.class.getMethod("sqlDate",new Class[] {}));
            //---------------------
            // convert to a java.sql.Timestamp
            m.put(Timestamp.class, DateTime.class.getMethod("timestamp",new Class[] {}));
            //---------------------
            mapping.put(DateTime.class, m);
            
            
            //---------------------------------------------------------------
            // Set up tranlators for a source of org.jaffa.datatypes.DateOnly
            m = new HashMap();
            //---------------------
            // convert to a Calander
            m.put(Calendar.class, DateOnly.class.getMethod("calendar",new Class[] {}));
            //---------------------
            // convert to a Date
            m.put(Date.class, DateOnly.class.getMethod("getUtilDate",new Class[] {}));
            //---------------------
            // convert to a DateTime
            m.put(DateTime.class, DateOnly.class.getMethod("toDateTime",new Class[] {DateOnly.class}));
            //---------------------
            // convert to a String
            m.put(String.class, Formatter.class.getMethod("format", new Class[] {Object.class, String.class}));
            //---------------------
            // convert to a java.sql.Date
            m.put(java.sql.Date.class, DateOnly.class.getMethod("sqlDate",new Class[] {}));
            //---------------------
            // convert to a java.sql.Timestamp
            m.put(Timestamp.class, DateOnly.class.getMethod("timestamp",new Class[] {}));
            //---------------------
            mapping.put(DateOnly.class, m);
            
            
            //---------------------------------------------------------------
            // Set up tranlators for a source of java.util.Calander
            m = new HashMap();
            //---------------------
            // convert to a DateTime
            m.put(DateTime.class, DateTime.class.getConstructor(new Class[] {Calendar.class}));
            //---------------------
            // convert to a DateTime
            m.put(DateOnly.class, DateOnly.class.getConstructor(new Class[] {Calendar.class}));
            //---------------------
            // convert to a String
            m.put(String.class, Formatter.class.getMethod("format", new Class[] {Object.class, String.class}));
            //---------------------
            mapping.put(Calendar.class, m);
            
            
            //---------------------------------------------------------------
            // Set up tranlators for a source of java.util.Date
            m = new HashMap();
            //---------------------
            // convert to a DateTime
            m.put(DateTime.class, DateTime.class.getConstructor(new Class[] {Date.class}));
            //---------------------
            // convert to a DateOnly
            m.put(DateOnly.class, DateOnly.class.getConstructor(new Class[] {Date.class}));
            //---------------------
            // convert to a String
            m.put(String.class, Formatter.class.getMethod("format", new Class[] {Object.class, String.class}));
            //---------------------
            mapping.put(Date.class, m);
            
            
            //---------------------------------------------------------------
            // Set up tranlators for a source of java.lang.String
            m = new HashMap();
            //---------------------
            // convert to a Boolean or boolean
            m.put(Boolean.class, Parser.class.getMethod("parseBoolean",new Class[] {String.class, String.class}));
            //---------------------
            // convert to a Integer or int
            m.put(Integer.class, new Object[] {
                Parser.class.getMethod("parseInteger",new Class[] {String.class, String.class}),
                Long.class.getMethod("intValue", new Class[0])
            });
            //---------------------
            // convert to a Long or long
            m.put(Long.class, Parser.class.getMethod("parseInteger",new Class[] {String.class}));
            //---------------------
            // convert to a Float or float
            m.put(Float.class, new Object[] {
                Parser.class.getMethod("parseDecimal", new Class[] {String.class, String.class}),
                Double.class.getMethod("floatValue", new Class[0])
            });
            //---------------------
            // convert to a Double or double
            m.put(Double.class, Parser.class.getMethod("parseDecimal",new Class[] {String.class, String.class}));
            //---------------------
            // convert to a Byte or byte
            m.put(Byte.class, new Object[] {
                Parser.class.getMethod("parseInteger",new Class[] {String.class, String.class}),
                Long.class.getMethod("byteValue", new Class[0])
            });
            //---------------------
            // convert to a Short or short
            m.put(Short.class, new Object[] {
                Parser.class.getMethod("parseInteger",new Class[] {String.class, String.class}),
                Long.class.getMethod("shortValue", new Class[0])
            });
            //---------------------
            // convert to a Character or char
            m.put(Character.class, DataTypeMapper.class.getMethod("toCharacter",new Class[] {String.class}));
            //---------------------
            // convert to a org.jaffa.datatypes.DateOnly
            m.put(DateOnly.class, Parser.class.getMethod("parseDateOnly",new Class[] {String.class, String.class}));
            //---------------------
            // convert to a org.jaffa.datatypes.DateTime
            m.put(DateTime.class, Parser.class.getMethod("parseDateTime",new Class[] {String.class, String.class}));
            //---------------------
            // convert to a java.util.Date
            m.put(java.util.Date.class, new Object[] {
                Parser.class.getMethod("parseDateTime", new Class[] {String.class, String.class}),
                DateTime.class.getMethod("getUtilDate", (Class[]) null)
            });
            //---------------------
            // convert to a org.jaffa.datatypes.Currency
            m.put(Currency.class, Parser.class.getMethod("parseCurrency",new Class[] {String.class, String.class}));
            //---------------------
            // convert to a String[]
            m.put(String[].class, DataTypeMapper.class.getMethod("parseString",new Class[] {String.class}));
            //---------------------
            mapping.put(String.class, m);
            
            //---------------------------------------------------------------
            // Set up tranlators for a source of java.lang.Long
            m = new HashMap();
            //---------------------
            // convert to a org.jaffa.datatypes.DateTime
            m.put(DateTime.class, new Object[] {
                Long.class.getMethod("longValue", new Class[] {}),
                DateTime.class.getConstructor(new Class[] {Long.TYPE})
            });
            //---------------------
            // convert to a org.jaffa.datatypes.DateOnly
            m.put(DateOnly.class, new Object[] {
                Long.class.getMethod("longValue", new Class[] {}),
                DateOnly.class.getConstructor(new Class[] {Long.TYPE})
            });
            //---------------------
            // convert to a Double
            m.put(Double.class, new Object[] {
                Long.class.getMethod("doubleValue", new Class[] {}),
                Double.class.getConstructor(new Class[] {Double.TYPE})
            });
            //---------------------
            // convert to a String
            m.put(String.class, Formatter.class.getMethod("format", new Class[] {Object.class, String.class}));
            //---------------------
            mapping.put(Long.class, m);
            
            
            // Translating any object to a String
            m = new HashMap();
            m.put(String.class, Formatter.class.getMethod("format", new Class[] {Object.class, String.class}));
            mapping.put(Object.class, m);
            
            
        } catch (NoSuchMethodException e) {
            log.error("Problem initializing data type mappings", e);
            throw new NoSuchMethodError(e.getMessage());
        }
    }
    
    /**
     * Add a new converter for mapping the source class to the target.
     * <code><pre>
     *  // Convert StringBuffer to String
     *  DataTypeMapper.instance().addMapping(StringBuffer.class, String.class,
     *           StringBuffer.class.getMethod("toString", new Class[] {}));
     *  // Convert a StringBuffer to a byte[]
     *  DataTypeMapper.instance().addMapping( String.class, (new byte[0]).getClass(),
     *      new Object[] {
     *          StringBuffer.class.getMethod("toString", new Class[] {}),
     *          String.class.getMethod("getBytes", new Class[] {})
     *      });
     * </pre></code>
     * @param source Class to convert from
     * @param target Class to convert to
     * @param converters This should either be a Constructor, Method or Object[]
     * containing either Contructor or Method objects. These operations will be
     * invoked on the source object such that it is converted to the target object
     */
    public void addMapping(Class source, Class target, Object converters) {
        Map m = null;
        if( mapping.containsKey(source) )
            m = (Map) mapping.get(source);
        else {
            m = new HashMap();
            mapping.put(source, m);
        }
        m.put(target, converters);
        
        log.debug("Added New Converter " + source + " to " + target);
    }
    
    
    /**
     * All static methods on the specified class that have one input parameter,
     * and who's return type is not void, will be added as a mapping
     * @return number of mappers added from this class
     * @param mapper Class that has mapping methods that should be introspected and added
     * @param publicOnly if true then only applicable 'public' methods will be added,
     * if false all applicable methods will be added
     */
    public int addMappings(Class mapper, boolean publicOnly) {
        Method[] methods = mapper.getDeclaredMethods();
        int count=0;
        for(int i=0; i<methods.length; i++) {
            log.debug("Add Method " + methods[i] + "?");
            if(methods[i].getReturnType() != Void.TYPE &&
                    methods[i].getParameterTypes().length == 1 &&
                    (Modifier.isPublic(methods[i].getModifiers()) || !publicOnly) &&
                    Modifier.isStatic(methods[i].getModifiers()) ) {
                if(!methods[i].isAccessible())
                    methods[i].setAccessible(true);
                addMapping(methods[i].getParameterTypes()[0],
                        methods[i].getReturnType(), methods[i] );
                count++;
            }
        }
        return count;
    }
    
    
    /**
     * Removed the specified mapping.
     * @return true if a mapping was found and removed, false if mapping was not found
     * @param source Class to map from
     * @param target Class to map to
     */
    public boolean removeMapping(Class source, Class target) {
        if( mapping.containsKey(source) ) {
            Map m = (Map) mapping.get(source);
            return m.remove(target)!=null;
        } else
            return false;
    }
    
    
    /**
     * See if a mapping between these two classes is possible.
     * If this method returns false, it is guarenteed that trying to
     * perform this converion via the map(..) method with throw
     * a ClassCastException.
     * @param source Class to map from
     * @param target Class to map to
     * @return true if the mapping is allowed, false if not.
     */
    public boolean isMappable(Class source, Class target) {
        if(target.isAssignableFrom(source))
            return true;
        Map m = findSource(source);
        return (m!=null && findTarget(m, target)!=null);
    }
    
    /**
     * Convert a given object to a new type
     * @param source Object to be converted
     * @param target Class object should be converted to
     * @throws ClassCastException Thrown if source object can't be mapped to target class
     * @return  Returns an object that can be cast to the target class.
     * In all cases of {class} it is true that
     * <CODE>{class} obj = ({class})map({source},{class}.class);</CODE>
     * will not throw a ClassCastException.
     */
    public Object map(Object source, Class target) throws ClassCastException {
        return map(source, target, null);
    }

    /**
     * Convert a given object to a new type
     * @param source Object to be converted
     * @param target Class object should be converted to
     * @param layout the layout to be used for the conversion
     * @throws ClassCastException Thrown if source object can't be mapped to target class
     * @return  Returns an object that can be cast to the target class.
     * In all cases of {class} it is true that
     * <CODE>{class} obj = ({class})map({source},{class}.class);</CODE>
     * will not throw a ClassCastException.
     */
    public Object map(Object source, Class target, String layout) throws ClassCastException {
        // Map null straight back!
        if(source==null)
            return null;
        
        if(!target.isAssignableFrom(source.getClass()))
            try {
                log.debug("Looking for source class: " + source.getClass());
                Map m = findSource(source.getClass());
                if(m==null)
                    throw new ClassCastException("Class is not a mappable - no translation exists");
                log.debug("Looking for target translator: " + target);
                
                // See if we have a single or array of translators
                Object trans = findTarget(m, target);
                Object[] translators = null;
                if(trans!=null && trans.getClass().isArray())
                    translators  = (Object[]) trans;
                else
                    translators = new Object[] {trans};
                
                // Loop through all the translators
                for(int i= 0; i<translators.length; i++) {
                    if(translators[i] == null)
                        throw new ClassCastException("Class can't be mapped - No Mapper Found");
                    if(translators[i] instanceof Method) {
                        Method me = (Method) translators[i];
                        if(me.getParameterTypes()==null || me.getParameterTypes().length==0)
                            // no inputs
                            source = me.invoke(source, new Object[] {});
                        else if (me.getParameterTypes().length == 1)
                            // assume static with source as input
                            source = me.invoke(null, new Object[] {source});
                        else if (me.getParameterTypes().length == 2)
                            // assume static with source and layout as inputs
                            source = me.invoke(null, new Object[] {source, layout});
                        else
                            throw new IllegalArgumentException("Translator requires unsupported arguments");
                    } else if (translators[i] instanceof Constructor) {
                        Constructor con = (Constructor) translators[i];
                        source = con.newInstance(new Object[] {source});
                    } else
                        throw new ClassCastException("Class can't be mapped - unknown translator");
                }
            } catch (ClassCastException e) {
                throw e;
            } catch (Exception e) {
                if(e instanceof ClassCastException)
                    throw (ClassCastException)e;
                log.error("Problem during mapping. Source=" + source.getClass() + ", Target=" + target,e);
                throw new ClassCastException("Problem during mapping");
            }
        return source;
    }
    
    /** Lookup the source class, and return a map of target classes */
    private Map findSource(Class source) {
        source = autoBox(source);
        if( mapping.containsKey(source) )
            return (Map) mapping.get(source);
        log.debug("Look as base classes for " + source);
        // Loop through all source objects to see if there is baseclass
        for(Iterator i = mapping.keySet().iterator();i.hasNext();) {
            Class s = (Class) i.next();
            if (s.isAssignableFrom(source))
                return (Map) mapping.get(s);
        }
        log.debug("Can't find source mapping for " + source);
        return null;
    }
    
    /** Lookup the target class in the map */
    private Object findTarget(Map methods, Class target) {
        target = autoBox(target);
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
    
    /**
     * So we don't have to have entries in the Map for all primative, we will
     * use this to 'box' them as there wrapper class when performing a lookup.
     * <p>
     * This has been made public static so other obejcts can use it as a utility class.
     * @param clazz Class to box
     * @return returns the primative wrapper class, if input was a primative,
     * otherwise it just returns the input class
     */
    public static Class autoBox(Class clazz) {
        if (clazz == Boolean.TYPE)
            return Boolean.class;
        else if (clazz == Integer.TYPE)
            return Integer.class;
        else if (clazz == Long.TYPE)
            return Long.class;
        else if (clazz == Float.TYPE)
            return Float.class;
        else if (clazz == Double.TYPE)
            return Double.class;
        else if(clazz == Byte.TYPE)
            return Byte.class;
        else if (clazz == Short.TYPE)
            return Short.class;
        else if (clazz == Character.TYPE)
            return Character.class;
        else
            return clazz;
    }
    
    /**
     * Special converter for converting a String to a Character, as there is no
     * <code>new Character(String)</code> constructor, as with other privative
     * class wrappers.
     * @param s String to convert. Must me null or have a length of 1 character
     * @throws ClassCastException Throw if there is an error converting the class
     * @return returns a Character which is either a ' ' (space)
     * if the source string was null, or the first character
     * in the source string
     */
    public static Character toCharacter(String s) throws ClassCastException {
        if (s == null || s.length() == 0)
            return new Character(' ');
        else if (s.length() == 1)
            return new Character(s.charAt(0));
        else
            throw new ClassCastException("Cannot convert the input String to a Character: " + s);
    }
    
    
    /**
     * Special converter for a String to a String[]. The default seperator will be a ,
     * @param s String to convert.
     * @return returns an array of strings
     */
    public static String[] parseString(String s) {
        return StringHelper.parseString(s,",");
    }
    
    
    /** Used for debugging...
     * public String debug() {
     * StringBuffer sb = new StringBuffer("Data Type Mapping Matrix\n");
     * int ci=0;
     * for(Iterator it = mapping.keySet().iterator();it.hasNext();) {
     * Class source = (Class) it.next();
     * sb.append(" ").append(ci++).append(") ").append(source).append("\n");
     * Map m = (Map) mapping.get(source);
     * int ci2=0;
     * for(Iterator it2 = m.keySet().iterator();it2.hasNext();) {
     * Class target = (Class) it2.next();
     * sb.append("       ").append(ci2++).append("] ").append(target).append("\n");
     * Object trans = m.get(target);
     * Object[] translators = null;
     * if(m.get(target).getClass().isArray())
     * translators  = (Object[]) m.get(target);
     * else
     * translators = new Object[] {m.get(target)};
     *
     * // Loop through all the translators
     * for(int i= 0; i<translators.length; i++) {
     * sb.append("          ").append(i+1).append("> ").append(translators[i]).append("\n");
     * }
     * }
     * }
     * return sb.toString();
     * }
     */
    
    /**
     * This main method is used to validate the class can be constructed without
     * any runtime errors based on looking up various methods and constructors.
     * @param args Not Used
     * @throws Exception Error if class singleton could not be created
     */
    public static void main(String[] args) throws Exception {
        org.jaffa.util.LoggerHelper.init();
        DataTypeMapper.instance();
        
        String s = "a,b,c";
        System.out.println("Convert? " + DataTypeMapper.instance().isMappable(String.class,String[].class) );
        Object x = DataTypeMapper.instance().map(s,String[].class);
        System.out.println("Converted it to " + x);
        System.out.println("Convert " + s + " to " + StringHelper.printArray((String[]) x) );
    }
    
}
