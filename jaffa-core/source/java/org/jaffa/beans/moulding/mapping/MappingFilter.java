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
 * MappingFilter.java
 *
 * Created on February 17, 2004, 4:35 PM
 */

package org.jaffa.beans.moulding.mapping;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.jaffa.beans.moulding.data.domain.DomainDAO;
import org.jaffa.util.SplitString;
import org.jaffa.util.StringHelper;

/** This class creates an instance of a filter for a given
 * domain object graph, based on a supplied set of mapping rules.
 *
 * @author  PaulE
 */
public class MappingFilter {

    private static Logger log = Logger.getLogger(MappingFilter.class);

    /** Holds value of property rules. */
    private String[] rules;
    private GraphMapping graph;
    private List allFields;
    private List filteredFields;
    private boolean[] filterTypes;
    private Pattern[] filterPatterns;

    /** Creates a new instance of MappingFilter */
    public MappingFilter(GraphMapping graph) {
        this(graph,null);
    }

    /** Creates a new instance of MappingFilter */
    public MappingFilter(GraphMapping graph, String[] rules) {
        this.graph = graph;
        allFields = getFieldList(graph.getDataClass());
        setRules(rules);
    }

    /** Getter for property rules.
     * @return Value of property rules.
     *
     */
    public String[] getRules() {
        return this.rules;
    }

    /** Setter for property rules.
     * @param rules New value of property rules.
     *
     */
    public void setRules(String[] rules) {
        this.rules = rules;
        if(rules==null)
            this.rules = new String[] {"*"};
        calculateFilter();
    }


    /** Get a list of all the possible objects that can be
     * addressed in this domain object graph
     */
    public static List getFieldList(Class dao) {
        List out = new ArrayList();
        getFieldList(dao, out, null, true);
        return out;
    }

    /** return -1 if field is excluded, 1 if included, 0 if no matched
     **/
    public boolean isFieldIncluded(String field) {
        return filteredFields.contains(field);
    }

    public String[] getIncludedFields() {
        return (String[]) filteredFields.toArray(new String[0]);
    }

    /**
    public boolean isIncluded(String fieldRef) {
        return filteredFields.contains(fieldRef);
    }
     */


    public boolean areSubFieldsIncluded(String fieldPrefix) {
        for(int j = 0; j<filteredFields.size(); j++) {
            String field = (String)filteredFields.get(j);
            if(field.startsWith(fieldPrefix) && field.length() > fieldPrefix.length())
                return true;
        }
        return false;
    }


    private static void getFieldList(Class dao, List out, String prefix, boolean includeKeys) {
        String lastField = null;
        if(prefix != null) {
            SplitString ss = new SplitString(prefix, ".", false);
            if(ss.isValid())
                lastField = ss.getSuffix();
            else
                lastField = ss.getPrefix();
        }

        GraphMapping mapper = MappingFactory.getInstance(dao);
        if(mapper==null) {
            log.error("Can't find mapping for class " + dao.getName());
            return;
        }
        String[] fields = mapper.getDataFieldNames();
        if(fields!=null) {
            for(int i=0;i<fields.length;i++) {
                String name = fields[i];
                if(includeKeys || !mapper.isKeyField(name)) {
                    String fullName = name;
                    if(prefix!=null)
                       fullName = prefix + name;
                    PropertyDescriptor pd = mapper.getDataFieldDescriptor(name);
                    if(pd != null && pd.getReadMethod()!=null) {
                        Class c = pd.getReadMethod().getReturnType();
                        boolean array = c.isArray();
                        if(array)
                            c = c.getComponentType();
                        if(DomainDAO.class.isAssignableFrom(c) ) {
                            if(lastField==null || !lastField.equals(name)) {
                                getFieldList(c, out, fullName + ".", array);
                                if(array)
                                    fullName = "*"+fullName;
                                else
                                    fullName = "+"+fullName;
                            } else {
                                log.debug("Stopped Recursion @ Path=" + fullName);
                                fullName = null;
                            }
                        }
                    } else
                        log.debug("Can't introspect field " + name);

                    if(fullName != null) // Don't add if nulled out to prevent recursion
                        out.add(fullName);
                }
            }
        }
    }

    private void calculateFilter() {
        filteredFields = new ArrayList();
        List relToCheck = new ArrayList();
        List foToCheck = new ArrayList();

        // Convert rules to RegEx and cache...
        filterTypes = new boolean[rules.length+1];
        filterPatterns = new Pattern[rules.length+1];
        for(int i = 0; i<rules.length; i++) {
            String filter = rules[i];
            Pattern p = null;
            boolean exclude = false;
            if(filter!=null&&filter.length()>0) {
                exclude = filter.charAt(0)=='-';
                if(exclude || filter.charAt(0)=='+')
                    filter=filter.substring(1);
                /* Convert filter to a regex. Rules are...
                 *  . => \.
                 *  ** => [\w\.]+
                 *  * => [\w]+
                 */
                filter = StringHelper.replace(filter, ".", "\\.");
                filter = StringHelper.replace(filter, "**", "[\\w\\.]+");
                filter = StringHelper.replace(filter, "*", "[\\w]+");
                log.debug("Converted filter '"+rules[i]+"' to pattern '"+filter+"'");
                p = Pattern.compile(filter);
            }
            filterTypes[i] = exclude;
            filterPatterns[i] = p;
        }


        // Now build list of acceptable fields
        if(allFields!=null)
            for(int i = 0; i<allFields.size(); i++) {
                String field = (String)allFields.get(i);
                boolean foreign = field.startsWith("+");
                boolean related = field.startsWith("*");
                if(foreign||related)
                    field = field.substring(1);
                if(includeField(field)) {
                    filteredFields.add(field);
                    if(foreign)
                      foToCheck.add(field);
                    if(related)
                      relToCheck.add(field);
                }
            }

        // Add foreign object, and remove PK references
        /*
        if(foToCheck.size() > 0)
            for(int i = 0; i<foToCheck.size(); i++) {
                String field = (String)foToCheck.get(i);
            }

        // Add foreign objects, and all its PK references
        if(foToCheck.size() > 0)
            for(int i = 0; i<foToCheck.size(); i++) {
                String field = (String)foToCheck.get(i);
                //@todo not sure if these should be forced in at this point?
                // how do i get a reference on the foreign object mapping at this
                // point to find out what the PK's arfe for this object>???
                ?
            }
        */

        // Removed related reference if there are no relatd fields
        if(relToCheck.size() > 0)
            for(Iterator i = relToCheck.iterator(); i.hasNext();) {
                String field = (String)i.next();
                log.debug("Check related object " + field);
                boolean found = false;
                if(filteredFields!=null)
                    for(int j = 0; j<filteredFields.size() && !found; j++) {
                        String field2 = (String)filteredFields.get(j);
                        if(field2.startsWith(field) && field2.length() > field.length()) {
                            found=true;
                            log.debug("Found field on related object " + field2);
                        }
                    }
                if(!found) {
                    if(filteredFields.remove(field))
                        log.debug("Removed related object " + field);
                }
            }
        // Now make sure based on al the filtering, that all parent object referenced
        // are in place. You can't have x.y.z without first having x.y and therefore x
        for(int j = 0; j<filteredFields.size(); j++) {
            String field = (String)filteredFields.get(j);
            int pos = field.lastIndexOf('.');
            if(pos>0) {
                field = field.substring(0,pos);
                if(!filteredFields.contains(field)) {
                    log.debug("Added missing parent object " + field);
                    filteredFields.add(field);
                }
            }
        }

    }

    /** return -1 if field is excluded, 1 if included, 0 if no matched
     **/
    private boolean includeField(String field) {
        for(int i = 0; i<rules.length; i++) {
            Pattern p = filterPatterns[i];
            boolean exclude = filterTypes[i];
            if(p != null && p.matcher(field).matches()) {
                log.debug("field '" + field + "' has been " +
                (exclude?"excluded":"included") + " by pattern '" + p.pattern() + "'");
                return !exclude;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String a = "hello";
        String r = "[\\w]+";
        System.out.println(a + " matches pattern " + r + " = " + Pattern.matches(r,a) );
    }
}
