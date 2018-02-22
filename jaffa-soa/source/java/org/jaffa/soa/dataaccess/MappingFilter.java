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
package org.jaffa.soa.dataaccess;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.map.ReferenceMap;
import org.apache.log4j.Logger;
import org.jaffa.flexfields.IFlexFields;
import org.jaffa.soa.graph.GraphDataObject;
import org.jaffa.util.StringHelper;

/** This class creates an instance of a filter for a given
 * domain object graph, based on a supplied set of mapping rules.
 *
 * @author  PaulE
 * @version 1.0
 */
public class MappingFilter {

    private static Logger log = Logger.getLogger(MappingFilter.class);
    // Cache of MappingFilter instances per resultGraphRules[] array for a Graph class
    private static final Map<String, Map<String[], MappingFilter>> c_mappingFilterCache = new ReferenceMap();
    private String[] m_rules;
    private List<String> m_filteredFields;
    private boolean[] m_filterTypes;
    private Pattern[] m_filterPatterns;
    private Filter[] m_filters;
    //private static final Pattern PATH_PATTERN = Pattern.compile("\\.");

    /**
     * Fetches (or creates if it does not exist) the MappingFilter instance for the given input.
     */
    public static MappingFilter getInstance(GraphMapping graph) {
        return getInstance(graph, null);
    }

    /**
     * Fetches (or creates if it does not exist) the MappingFilter instance for the given input.
     */
    public static MappingFilter getInstance(GraphMapping graph, String[] rules) {
        if (rules == null || rules.length == 0)
            rules = new String[]{"*"};
        Map<String[], MappingFilter> m = c_mappingFilterCache.get(graph.getDataClassName());
        if (m == null) {
            synchronized (c_mappingFilterCache) {
                m = c_mappingFilterCache.get(graph.getDataClassName());
                if (m == null) {
                    m = new ReferenceMap();
                    c_mappingFilterCache.put(graph.getDataClassName(), m);
                    if (log.isDebugEnabled())
                        log.debug("Created cache for " + graph.getDataClassName());
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Cache found for " + graph.getDataClassName());
                }
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("Cache found for " + graph.getDataClassName());
        }

        MappingFilter mappingFilter = get(m, rules);
        if (mappingFilter == null) {
            synchronized (m) {
                mappingFilter = get(m, rules);
                if (mappingFilter == null) {
                    mappingFilter = new MappingFilter(graph, rules, false);
                    m.put(rules, mappingFilter);
                    if (log.isDebugEnabled())
                        log.debug("Created cache entry for " + graph.getDataClassName() + " / " + Arrays.toString(rules));
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Cached entry found for " + graph.getDataClassName() + " / " + Arrays.toString(rules));
                }
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("Cached entry found for " + graph.getDataClassName() + " / " + Arrays.toString(rules));
        }
        return mappingFilter;
    }

    /**
     * A convenience method that utilizes the Arrays class to locate an entry in
     * the input Map, that has the same key as the input array.
     */
    private static MappingFilter get(Map<String[], MappingFilter> m, String[] a) {
        Collection<Map.Entry<String[], MappingFilter>> entries = new HashSet<Map.Entry<String[], MappingFilter>>(m.entrySet());
        for (Map.Entry<String[], MappingFilter> entry : entries) {
            if (Arrays.equals(entry.getKey(), a))
                return entry.getValue();
        }
        return null;
    }

    /**
     * Creates a new instance of MappingFilter.
     * @deprecated Use getInstance() method, since that caches MappingFilter.
     */
    public MappingFilter(GraphMapping graph) {
        this(graph, null);
    }

    /**
     * Creates a new instance of MappingFilter.
     * @deprecated Use getInstance() method, since that caches MappingFilter.
     */
    public MappingFilter(GraphMapping graph, String[] rules) {
        this(graph, null, true);
    }

    /**
     * Creates a new instance of MappingFilter.
     */
    private MappingFilter(GraphMapping graph, String[] rules, boolean useCache) {
        if (useCache) {
            MappingFilter cachedInstance = getInstance(graph, rules);
            this.m_rules = cachedInstance.m_rules;
            this.m_filteredFields = cachedInstance.m_filteredFields;
            this.m_filterTypes = cachedInstance.m_filterTypes;
            this.m_filterPatterns = cachedInstance.m_filterPatterns;
            this.m_filters = cachedInstance.m_filters;
        } else {
            calculateFilter(graph, rules);
        }
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
     * Unmapped fields will not be included in the output.
     * @param gdo the Class for a graph data object.
     * @return a list of all possible objects that can be addressed in this domain object graph.
     */
    public static List<String> getFieldList(Class<? extends GraphDataObject> gdo) {
        return getFieldList(gdo, false);
    }

    /** Get a list of all the possible objects that can be addressed in this domain object graph.
     * @param gdo the Class for a graph data object.
     * @param includeUnmappedFields flag to control the inclusion of unmapped fields in the output.
     * @return a list of all possible objects that can be addressed in this domain object graph.
     */
    public static List<String> getFieldList(Class<? extends GraphDataObject> gdo, boolean includeUnmappedFields) {
        List<String> fieldList = new ArrayList<String>();
        getFieldList(gdo, fieldList, null, null, true, null, false, includeUnmappedFields);
        Collections.sort(fieldList);
        return fieldList;
    }

    /** Returns a Map of all the possible objects that can be addressed in this domain object graph.
     * Unmapped fields will not be included in the output.
     * For each object, the Map will contain the Class and the associated Collection of Fields.
     * @param gdo the Class for a graph data object.
     * @return a Map of all possible objects that can be addressed in this domain object graph.
     */
    public static Map<Class, Collection<String>> getFieldMap(Class<? extends GraphDataObject> gdo) {
        return getFieldMap(gdo, false);
    }

    /** Returns a Map of all the possible objects that can be addressed in this domain object graph.
     * For each object, the Map will contain the Class and the associated Collection of Fields.
     * @param gdo the Class for a graph data object.
     * @param includeUnmappedFields flag to control the inclusion of unmapped fields in the output.
     * @return a Map of all possible objects that can be addressed in this domain object graph.
     */
    public static Map<Class, Collection<String>> getFieldMap(Class<? extends GraphDataObject> gdo, boolean includeUnmappedFields) {
        Map<Class, Collection<String>> fieldMap = new LinkedHashMap<Class, Collection<String>>();
        getFieldList(gdo, null, fieldMap, null, true, null, false, includeUnmappedFields);
        return fieldMap;
    }

    /** Returns a Map of all the possible objects and fields that are being excluded from this domain object graph,
     * based on the declared scope in the mapping files.
     * For each object, the Map will contain the Class and the associated Collection of Fields.
     * If an object is being completely excluded, the associated Collection will be null.
     * @param gdo the Class for a graph data object.
     * @return a Map of all the possible objects and fields that are being excluded from this domain object graph.
     */
    public static Map<Class, Collection<String>> getExcludedFieldMap(Class<? extends GraphDataObject> gdo) {
        //classes+fields in the default scope
        Map<Class, Collection<String>> allFields = new LinkedHashMap<Class, Collection<String>>();
        getFieldList(gdo, null, allFields, null, true, null, true, true);

        //classes+fields in the declared scope
        Map<Class, Collection<String>> includedFields = new LinkedHashMap<Class, Collection<String>>();
        getFieldList(gdo, null, includedFields, null, true, null, false, true);

        //Create the Map of excluded fields from the above 2 maps
        Map<Class, Collection<String>> excludedFields = new LinkedHashMap<Class, Collection<String>>();
        for (Map.Entry<Class, Collection<String>> me : allFields.entrySet()) {
            if (includedFields.containsKey(me.getKey())) {
                Collection<String> all = me.getValue();
                all.removeAll(includedFields.get(me.getKey()));
                if (!all.isEmpty())
                    excludedFields.put(me.getKey(), all);
            } else {
                excludedFields.put(me.getKey(), null);
            }
        }
        return excludedFields;
    }

    private static void getFieldList(Class<? extends GraphDataObject> gdo, List<String> fieldList, Map<Class, Collection<String>> fieldMap, String prefix, boolean includeKeys, String targetScope, boolean defaultScopeOnly, boolean includeUnmappedFields) {
        GraphMapping mapper = MappingFactory.getInstance(gdo);
        if (mapper == null) {
            log.error("Can't find mapping for class " + gdo.getName());
            return;
        }
        Map<String, PropertyDescriptor> propertyDescriptorMap = createPropertyDescriptorMap(mapper, targetScope, defaultScopeOnly, includeUnmappedFields);
        for (Map.Entry<String, PropertyDescriptor> me : propertyDescriptorMap.entrySet()) {
            String name = me.getKey();
            if (includeKeys || !mapper.isKeyField(name)) {
                String fullName = name;
                if (prefix != null)
                    fullName = prefix + name;
                PropertyDescriptor pd = me.getValue();
                Class c = pd.getReadMethod().getReturnType();
                boolean array = c.isArray();
                if (array)
                    c = c.getComponentType();
                if (GraphDataObject.class.isAssignableFrom(c)) {
                    if (fieldMap != null && fieldMap.containsKey(c)) {
                        if (log.isDebugEnabled())
                            log.debug("Stopped further Introspection @ Path=" + fullName);
                    } else if (prefix == null || !repeatingPatternExists(fullName)) {
                        //Include key-fields for related-objects, both in 1-to-many and 1-to-1 relationships
                        //However the key-fields will have to be included when creating the fieldMap
                        //Utilize the targetScope for the foreign/related object, if declared. Else use the input targetScope
                        String targetScopeToPass = null;
                        if (defaultScopeOnly || !mapper.containsDataField(name))
                            targetScopeToPass = null;
                        else {
                            targetScopeToPass = mapper.getTargetScope(name);
                            if (targetScopeToPass == null)
                                targetScopeToPass = targetScope;
                        }
                        getFieldList(c, fieldList, fieldMap, fullName + ".", fieldMap != null || array || !mapper.isForeignField(name), targetScopeToPass, defaultScopeOnly, includeUnmappedFields);
                        if (array)
                            fullName = "*" + fullName;
                        else
                            fullName = "+" + fullName;
                    } else {
                        if (log.isDebugEnabled())
                            log.debug("Stopped Recursion @ Path=" + fullName);
                        fullName = null;
                    }
                }

                // Don't add if nulled out to prevent recursion
                if (fullName != null) {
                    if (fieldList != null)
                        fieldList.add(fullName);
                    else if (fieldMap != null) {
                        Collection<String> col = fieldMap.get(gdo);
                        if (col == null) {
                            col = new LinkedHashSet<String>();
                            fieldMap.put(gdo, col);
                        }
                        col.add(name);
                    }
                }
            }
        }

        // Add flex fields
        if (IFlexFields.hasFlexBean(gdo)) {
            try {
                if (fieldList != null) {
                    String fullName = (prefix != null ? prefix : "") + "flexBean";
                    fieldList.add('+' + fullName);
                } else if (fieldMap != null) {
                    Collection<String> col = fieldMap.get(gdo);
                    if (col == null) {
                        col = new LinkedHashSet<String>();
                        fieldMap.put(gdo, col);
                    }
                    col.add("flexBean");
                }
            } catch (Exception e) {
                String s = "Exception thrown while obtaining the FlexClass associated to the graph " + gdo;
                log.error(s, e);
                throw new RuntimeException(s, e);
            }
        }
    }

    /** Returns a Map of fieldNames and corresponding PropertyDescriptors. If requested, unmappedFields will also be included in the output. */
    private static Map<String, PropertyDescriptor> createPropertyDescriptorMap(GraphMapping mapper, String targetScope, boolean defaultScopeOnly, boolean includeUnmappedFields) {
        Map<String, PropertyDescriptor> output = new LinkedHashMap<String, PropertyDescriptor>();
        String[] fields = mapper.getDataFieldNames(defaultScopeOnly ? null : targetScope);
        if (fields != null) {
            for (String name : fields) {
                PropertyDescriptor pd = mapper.getDataFieldDescriptor(name);
                if (pd != null && pd.getReadMethod() != null) {
                    output.put(name, pd);
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Can't introspect field " + name);
                }
            }
        }

        // Add unmapped fields, if requested
        if (includeUnmappedFields) {
            for (Map.Entry<String, PropertyDescriptor> me : GraphMapping.getPropertyDescriptorMap(mapper.getDataClass()).entrySet()) {
                String name = me.getKey();
                if (!mapper.containsDataField(name) && !name.equals("flexBean")) {
                    PropertyDescriptor pd = me.getValue();
                    if (pd != null && pd.getReadMethod() != null && pd.getReadMethod().getDeclaringClass() != GraphDataObject.class && pd.getReadMethod().getDeclaringClass() != Object.class) {
                        output.put(name, pd);
                    } else {
                        if (log.isDebugEnabled())
                            log.debug("Can't introspect field " + name);
                    }
                }
            }
        }

        return output;
    }

    private void calculateFilter(GraphMapping graph, String[] rules) {
		m_rules = rules != null && rules.length > 0 ? rules
				: new String[] { "*" };
		List<String> filteredFields = new ArrayList<String>();
		List<String> relToCheck = new LinkedList<String>();

		long mem = Runtime.getRuntime().freeMemory();

		if (log.isDebugEnabled())
			log.debug("Getting Field list for " + graph.getDataClass()
					+ ",Mem= "
					+ (mem - Runtime.getRuntime().freeMemory() >> 10));

		// build the rules
		m_filters = new Filter[m_rules.length];

		for (int n = 0; n < m_rules.length; n++) {
			String r = m_rules[n];
			m_filters[n] = new Filter(r);
		}

		// Get the complete field list
		List<String> allFields = getFieldList(graph.getDataClass());

		if (log.isDebugEnabled())
			log.debug("Returned Field list for " + graph.getDataClass()
					+ " Size is " + (allFields != null ? allFields.size() : 0)
					+ ",Mem= "
					+ (mem - Runtime.getRuntime().freeMemory() >> 10));

		// Look at all the included fields based on the rules
		for (Filter f : m_filters) {
			if (!f.isExcluded()) {
				if (f.isRegEx()) {

					Iterator<String> allIt = allFields.iterator();
					while(allIt.hasNext()) {
						String fld = allIt.next();
						
						boolean foreign = fld.startsWith("+");
		                boolean related = fld.startsWith("*");
		                if (foreign || related)
		                	fld = fld.substring(1);
		                
		                if(f.matches(fld)) {
		                	filteredFields.add(fld);
							if (related)
		                        relToCheck.add(fld);
							allIt.remove();
		                }
					}
				} else {

					int i = Collections.binarySearch(allFields, f.getRule());
					if (i < 0)
						i = Collections.binarySearch(allFields,
								"+" + f.getRule());
					if (i < 0)
						i = Collections.binarySearch(allFields,
								"*" + f.getRule());
					if (i >= 0)
						filteredFields.add(f.getRule());
				}
			}
		}

		// Remove any explicitly excluded fields
		for (Filter f : m_filters) {
			if (f.isExcluded()) {
				Iterator<String> itFil = filteredFields.iterator();
				while(itFil.hasNext()) {
					String fld = itFil.next();
					if (f.matches(fld)) {
						itFil.remove();
					}
				}
			}
		}
		//if(filteredFields2.size()>0)
		//	filteredFields = filteredFields2;
		
		Collections.sort(filteredFields);

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

		// Now make sure based on all the filtering, that all parent object
		// referenced
		// are in place. You can't have x.y.z without first having x.y and
		// therefore x
		for (int cursor = 0; cursor < filteredFields.size(); ++cursor) {
			// perform the check recursively since the addition of 'x.y.z' might
			// also require the insertion of 'x.y' and so on..
			String field = (String) filteredFields.get(cursor);
			while (true) {
				int pos = field.lastIndexOf('.');
				if (pos > 0) {
					field = field.substring(0, pos);
					int i = Collections.binarySearch(filteredFields, field);
					if (i < 0) {
						// Determine the insertion point. Note: the value of i
						// is (-(insertion point) - 1)
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

		if (log.isDebugEnabled())
			log.debug("Filtered field list for " + graph.getDataClass()
					+ " Size is "
					+ (filteredFields != null ? filteredFields.size() : 0)
					+ ",Mem= "
					+ (mem - Runtime.getRuntime().freeMemory() >> 10));
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
    	for(Filter filter : m_filters) {
    		boolean exclude = filter.isExcluded();
    		
    		if(filter.matches(field)) {
    			if (log.isDebugEnabled())
                    log.debug("field '" + field + "' has been " + (exclude ? "excluded" : "included") + " by filter '" + filter.getRule() + "'");
    			return !exclude;
    		}
    	}
    	return null;
    }

    /**
     * Return true if the input dot-separated path contains repeating patterns.
     * For example if the input path is any of the following:
     * hello.world.x.x
     * hello.world.x.y.x.y
     * hello.world.x.y.z.x.y.z
     */
    private static boolean repeatingPatternExists(String path) {
        boolean result = false;

        //Split the input path by the dot-separator
        //String[] elements = PATH_PATTERN.split(path);
        String[] elements = StringHelper.split(path, '.');

        //Number of scans should not exceed elements.length/2
        for (int iteration = 1; iteration <= elements.length / 2; iteration++) {
            result = true;
            for (int index = 0; index < iteration; index++) {
                String elementX = elements[elements.length - 1 - index];
                String elementY = elements[elements.length - 1 - index - iteration];
                if (!elementX.equals(elementY)) {
                    result = false;
                    break;
                }
            }
            if (result)
                break;
        }
        return result;
    }
    
    public static Object clearCache(GraphMapping graph){
        if(log.isDebugEnabled()){
            log.debug("Cleaing cacahe for :"+graph.getDataClassName());
        }
        return c_mappingFilterCache.remove(graph.getDataClassName());
    }

    public static void main(String[] args) {
        String a = "hello";
        String r = "[\\w]+";
        System.out.println(a + " matches pattern " + r + " = " + Pattern.matches(r, a));
    }
}
