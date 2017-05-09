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
package org.jaffa.qm.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.collections.map.ReferenceMap;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.IDateBase;
import org.jaffa.util.StringHelper;

/** 
 * This class creates an instance of a filter for a given class, based on a supplied set of mapping rules.
 */
public class PropertyFilter {

    private static Logger log = Logger.getLogger(PropertyFilter.class);
    // Cache of PropertyFilter instances per resultGraphRules[] array for a class
    private static final Map<String, Map<String[], PropertyFilter>> c_propertyFilterCache = new ReferenceMap();
    private String[] m_rules;
    private List<String> m_filteredFields;
    private boolean[] m_filterTypes;
    private Pattern[] m_filterPatterns;

    /**
     * Fetches (or creates if it does not exist) the PropertyFilter instance for the given input.
     */
    public static PropertyFilter getInstance(Class clazz) throws IntrospectionException {
        return getInstance(clazz, null);
    }

    /**
     * Fetches (or creates if it does not exist) the PropertyFilter instance for the given input.
     */
    public static PropertyFilter getInstance(Class clazz, String[] rules) throws IntrospectionException {
        if (rules == null || rules.length == 0)
            rules = new String[]{"*"};
        Map<String[], PropertyFilter> m = c_propertyFilterCache.get(clazz.getName());
        if (m == null) {
            synchronized (c_propertyFilterCache) {
                m = c_propertyFilterCache.get(clazz.getName());
                if (m == null) {
                    m = new ReferenceMap();
                    c_propertyFilterCache.put(clazz.getName(), m);
                    if (log.isInfoEnabled())
                        log.info("Created cache for " + clazz.getName());
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Cache found for " + clazz.getName());
                }
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("Cache found for " + clazz.getName());
        }

        PropertyFilter propertyFilter = get(m, rules);
        if (propertyFilter == null) {
            synchronized (m) {
                propertyFilter = get(m, rules);
                if (propertyFilter == null) {
                    propertyFilter = new PropertyFilter(clazz, rules);
                    m.put(rules, propertyFilter);
                    if (log.isInfoEnabled())
                        log.info("Created cache entry for " + clazz.getName() + " / " + Arrays.toString(rules));
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Cached entry found for " + clazz.getName() + " / " + Arrays.toString(rules));
                }
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("Cached entry found for " + clazz.getName() + " / " + Arrays.toString(rules));
        }
        return propertyFilter;
    }

    /**
     * A convenience method that utilizes the Arrays class to locate an entry in
     * the input Map, that has the same key as the input array.
     */
    private static PropertyFilter get(Map<String[], PropertyFilter> m, String[] a) {
        Collection<Map.Entry<String[], PropertyFilter>> entries = new HashSet<Map.Entry<String[], PropertyFilter>>(m.entrySet());
        for (Map.Entry<String[], PropertyFilter> entry : entries) {
            if (Arrays.equals(entry.getKey(), a))
                return entry.getValue();
        }
        return null;
    }

    /**
     * Creates a new instance of PropertyFilter.
     */
    private PropertyFilter(Class clazz, String[] rules) throws IntrospectionException {
        calculateFilter(clazz, rules);
    }

    /** Getter for property rules.
     * @return Value of property rules.
     */
    public String[] getRules() {
        return m_rules;
    }

    /** Returns true if the field is included based on the rules.
     * @param field the field
     * @return true if the field is included based on the rules.
     */
    public boolean isFieldIncluded(String field) {
        return m_filteredFields != null && Collections.binarySearch(m_filteredFields, field) >= 0;
    }

    /** Returns a list of fields to be included based on the rules.
     * @return a list of fields to be included based on the rules.
     */
    public String[] getIncludedFields() {
        return m_filteredFields != null ? m_filteredFields.toArray(new String[m_filteredFields.size()]) : null;
    }

    /** Returns true if the list of included fields contains the fields for given related object.
     * @param fieldPrefix the prefix which identifies a related object.
     * @return true if the list of included fields contains the fields for given related object.
     */
    public boolean areSubFieldsIncluded(String fieldPrefix) {
        return areSubFieldsIncluded(m_filteredFields, fieldPrefix);
    }

    /** Returns true if the list of included fields contains the fields for given related object.
     * @param filteredFields the list of included fields.
     * @param fieldPrefix the prefix which identifies a related object.
     * @return true if the list of included fields contains the fields for given related object.
     */
    private static boolean areSubFieldsIncluded(List<String> filteredFields, String fieldPrefix) {
        if (filteredFields != null) {
            int length = fieldPrefix.length();
            char startChar = fieldPrefix.charAt(0);
            char endChar = fieldPrefix.charAt(length - 1);
            for (String field : filteredFields) {
                if (field.length() > length && field.charAt(0) == startChar && field.charAt(length - 1) == endChar && field.startsWith(fieldPrefix))
                    return true;
            }
        }
        return false;
    }

    /** Get a list of all the possible objects that can be addressed in this domain object graph.
     * @param gdo the Class for a graph data object.
     * @return a list of all possible objects that can be addressed in this domain object graph.
     */
    private static List<String> getFieldList(Class clazz) throws IntrospectionException {
        List<String> fieldList = new ArrayList<String>();
        getFieldList(clazz, fieldList, null, new LinkedList<Class>());
        Collections.sort(fieldList);
        return fieldList;
    }

    private static void getFieldList(Class clazz, List<String> fieldList, String prefix, Deque<Class> classStack) throws IntrospectionException {
        //To avoid recursion, bail out if the input Class has already been introspected
        if (classStack.contains(clazz)) {
            if (log.isDebugEnabled())
                log.debug("Fields from " + clazz + " prefixed by " + prefix + " will be ignored, since the class has already been introspected as per the stack " + classStack);
            return;
        } else
            classStack.addFirst(clazz);

        //Introspect the input Class
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        if (beanInfo != null) {
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            if (pds != null) {
                for (PropertyDescriptor pd : pds) {
                    if (pd.getReadMethod() != null && pd.getWriteMethod() != null) {
                        String name = pd.getName();
                        String qualifieldName = prefix == null || prefix.length() == 0 ? name : prefix + '.' + name;
                        Class type = pd.getPropertyType();
                        if (type.isArray())
                            type = type.getComponentType();
                        if (type == String.class || type == Boolean.class || Number.class.isAssignableFrom(type) || IDateBase.class.isAssignableFrom(type) || Currency.class.isAssignableFrom(type) || type.isPrimitive() || type.isEnum())
                            fieldList.add(qualifieldName);
                        else
                            getFieldList(type, fieldList, qualifieldName, classStack);
                    }
                }
            }
        }

        classStack.removeFirst();
    }

    private void calculateFilter(Class clazz, String[] rules) throws IntrospectionException {
        m_rules = rules != null && rules.length > 0 ? rules : new String[]{"*"};
        List<String> filteredFields = new ArrayList<String>();
        List<String> relToCheck = new LinkedList<String>();

        // Convert rules to RegEx and cache...
        m_filterTypes = new boolean[m_rules.length + 1];
        m_filterPatterns = new Pattern[m_rules.length + 1];
        for (int i = 0; i < m_rules.length; i++) {
            String filter = m_rules[i];
            Pattern p = null;
            boolean exclude = false;
            if (filter != null && filter.length() > 0) {
                exclude = filter.charAt(0) == '-';
                if (exclude || filter.charAt(0) == '+')
                    filter = filter.substring(1);
                /* Convert filter to a regex. Rules are...
                 *  . => \.
                 *  ** => [\w\.]+
                 *  * => [\w]+
                 */
                filter = StringHelper.replace(filter, ".", "\\.");
                filter = StringHelper.replace(filter, "**", "[\\w\\.]+");
                filter = StringHelper.replace(filter, "*", "[\\w]+");
                if (log.isDebugEnabled())
                    log.debug("Converted filter '" + m_rules[i] + "' to pattern '" + filter + "'");
                p = Pattern.compile(filter);
            }
            m_filterTypes[i] = exclude;
            m_filterPatterns[i] = p;
        }

        // Now build list of acceptable fields
        List<String> allFields = getFieldList(clazz);
        if (allFields != null) {
            for (String field : allFields) {
                boolean foreign = field.startsWith("+");
                boolean related = field.startsWith("*");
                if (foreign || related)
                    field = field.substring(1);
                Boolean include = includeField(field);
                if (include != null && include) {
                    filteredFields.add(field);
                    if (related)
                        relToCheck.add(field);
                }
            }
            Collections.sort(filteredFields);
        }

        // Removed related reference if there are no related fields
        for (String field : relToCheck) {
            if (log.isDebugEnabled())
                log.debug("Check related object " + field);
            if (!areSubFieldsIncluded(filteredFields, field)) {
                int i = Collections.binarySearch(filteredFields, field);
                if (i >= 0) {
                    filteredFields.remove(i);
                    if (log.isDebugEnabled())
                        log.debug("Removed related object " + field);
                }
            }
        }

        // Now make sure based on all the filtering, that all parent object referenced
        // are in place. You can't have x.y.z without first having x.y and therefore x
        for (int cursor = 0; cursor < filteredFields.size(); ++cursor) {
            // perform the check recursively since the addition of 'x.y.z' might also require the insertion of 'x.y' and so on..
            String field = (String) filteredFields.get(cursor);
            while (true) {
                int pos = field.lastIndexOf('.');
                if (pos > 0) {
                    field = field.substring(0, pos);
                    int i = Collections.binarySearch(filteredFields, field);
                    if (i < 0) {
                        // Determine the insertion point. Note: the value of i is (-(insertion point) - 1)
                        i = -(i + 1);
                        filteredFields.add(i, field);
                        if (log.isDebugEnabled())
                            log.debug("Added missing parent object " + field);
                        if (i <= cursor)
                            ++cursor;
                    }
                } else {
                    break;
                }
            }
        }

        m_filteredFields = filteredFields;
    }

    /** Returns true if the field matches any rule and is not marked for exclusion.
     * A false is returned if the field matches any rule and is marked for exclusion.
     * A null is returned if the field does not match any rule.
     * <p>
     * This method is expensive and should be used sparingly.
     * It is provided primarily to support custom fields that are
     * not part of the mapping file, and which hence cannot be supported by the
     * more efficient 'isFieldIncluded()' method.
     */
    public Boolean includeField(String field) {
        for (int i = 0; i < m_rules.length; i++) {
            Pattern p = m_filterPatterns[i];
            boolean exclude = m_filterTypes[i];
            if (p != null && p.matcher(field).matches()) {
                if (log.isDebugEnabled())
                    log.debug("field '" + field + "' has been " + (exclude ? "excluded" : "included") + " by pattern '" + p.pattern() + "'");
                return !exclude;
            }
        }
        return null;
    }
}
