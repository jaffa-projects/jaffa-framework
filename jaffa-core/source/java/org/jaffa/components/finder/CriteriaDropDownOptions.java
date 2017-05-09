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

package org.jaffa.components.finder;

import java.util.*;
import org.jaffa.datatypes.Formatter;
import org.jaffa.util.MessageHelper;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.Parser;
import org.jaffa.config.Config;
import org.jaffa.session.ContextManagerFactory;

/**
 * This class holds the values displayed in a Criteria dropdown. Currently the labels are hard-coded for each of the value.
 * @author  GautamJ
 */
public class CriteriaDropDownOptions {

    private static final Logger log = Logger.getLogger(CriteriaDropDownOptions.class);
    private static final Map c_allCriteriaDropDownOptions = new LinkedHashMap();
    private static final Map c_numericalCriteriaDropDownOptions = new LinkedHashMap();
    private static final Map c_dateCriteriaDropDownOptions = new LinkedHashMap();
    private static final Map c_booleanCriteriaDropDownOptions = new LinkedHashMap();
    private static final Map c_dropDownCriteriaDropDownOptions = new LinkedHashMap();
    private static final Map c_maxRecordsDropDownOptions = new LinkedHashMap();
    private static Long c_defaultMaxRecordsDropDownOption = null;

    static {
        c_booleanCriteriaDropDownOptions.put("", MessageHelper.tokenize("label.Jaffa.Inquiry.BooleanCriteriaDropDownOption.Any"));
        c_booleanCriteriaDropDownOptions.put("true", MessageHelper.tokenize("label.Jaffa.Inquiry.BooleanCriteriaDropDownOption.True"));
        c_booleanCriteriaDropDownOptions.put("false", MessageHelper.tokenize("label.Jaffa.Inquiry.BooleanCriteriaDropDownOption.False"));

        c_dropDownCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_EQUALS, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.Equals"));
        c_dropDownCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_NOT_EQUALS, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.NotEquals"));
        c_dropDownCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_GREATER_THAN, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.GreaterThan"));
        c_dropDownCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_SMALLER_THAN, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.SmallerThan"));
        c_dropDownCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_GREATER_THAN_EQUAL_TO, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.GreaterThanEqualTo"));
        c_dropDownCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_SMALLER_THAN_EQUAL_TO, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.SmallerThanEqualTo"));
        c_dropDownCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_IS_NOT_NULL, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.IsNotNull"));
        c_dropDownCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_IS_NULL, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.IsNull"));

        c_numericalCriteriaDropDownOptions.putAll(c_dropDownCriteriaDropDownOptions);
        c_numericalCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_BETWEEN, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.Between"));
        c_numericalCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_IN, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.In"));
        c_numericalCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_NOT_IN, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.NotIn"));

        c_dateCriteriaDropDownOptions.putAll(c_numericalCriteriaDropDownOptions);

        c_allCriteriaDropDownOptions.putAll(c_numericalCriteriaDropDownOptions);
        c_allCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_BEGINS_WITH, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.BeginsWith"));
        c_allCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_ENDS_WITH, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.EndsWith"));
        c_allCriteriaDropDownOptions.put(CriteriaField.RELATIONAL_LIKE, MessageHelper.tokenize("label.Jaffa.Inquiry.CriteriaDropDownOption.Like"));


        // Determine the values to be displayed by the 'MaxRecords' dropdown of the Finder/Lookup criteria screens, and its default value
        Long[] maxRecordsDropDownOptions = null;
        try {
            String str = (String) Config.getProperty(Config.PROP_FINDER_MAX_RECORDS_DROP_DOWN_OPTIONS, null);
            if (str != null && str.trim().length() > 0) {
                if (log.isDebugEnabled())
                    log.debug("The String '" + str + "' will be used for displaying the values in the 'MaxRecords' dropdown of the Finder/Lookup criteria screens");
                List list = new ArrayList();
                for (StringTokenizer tknzr = new StringTokenizer(str, ", "); tknzr.hasMoreTokens(); )
                    list.add(Parser.parseInteger(tknzr.nextToken()));
                c_defaultMaxRecordsDropDownOption = (Long) list.get(0);
                Collections.sort(list);
                maxRecordsDropDownOptions = (Long[]) list.toArray(new Long[0]);
            } else {
                if (log.isDebugEnabled())
                    log.debug("No value specified for the property 'framework.finder.maxRecordsDropDownOptions'. The default string '25,50,100,500,1000,2500,5000' will be used for displaying the values in the 'MaxRecords' dropdown of the Finder/Lookup criteria screens");
            }
        } catch (Exception e) {
            // do nothing.. just log a warning
            log.warn("Error thrown while reading the property 'framework.finder.maxRecordsDropDownOptions'. Will use the default values '25,50,100,500,1000,2500,5000'", e);
        } finally {
            if (maxRecordsDropDownOptions == null) {
                maxRecordsDropDownOptions = new Long[] {new Long(25), new Long(50), new Long(100), new Long(500), new Long(1000), new Long(2500), new Long(5000)};
                c_defaultMaxRecordsDropDownOption = new Long(25);
            }
        }
        for (int i = 0; i < maxRecordsDropDownOptions.length; i++)
            c_maxRecordsDropDownOptions.put(maxRecordsDropDownOptions[i], Formatter.format(maxRecordsDropDownOptions[i]));

        String showUnlimited = (String)ContextManagerFactory.instance().getProperty("jaffa.widgets.grid.allowLoadAll");
        if (showUnlimited!=null && Boolean.TRUE.equals(Boolean.parseBoolean(showUnlimited)))
            c_maxRecordsDropDownOptions.put(new Long(0), MessageHelper.tokenize("label.Jaffa.Inquiry.MaxRecordsDropDownOption.Unlimited"));
    }

    /** Returns a Map containg value-label pairs to be displayed in a criteria dropdown field.
     * @return a Map containg value-label pairs to be displayed in a criteria dropdown field.
     */
    public static Map getAllCriteriaDropDownOptions() {
        return c_allCriteriaDropDownOptions;
    }

    /** Returns a Map containg value-label pairs to be displayed in a criteria dropdown field.
     * This will not have the BeginsWith, EndWith and Like options, since they are meaningless for numerical fields.
     * @return a Map containg value-label pairs to be displayed in a criteria dropdown field.
     */
    public static Map getNumericalCriteriaDropDownOptions() {
        return c_numericalCriteriaDropDownOptions;
    }

    /** Returns a Map containg value-label pairs to be displayed in a criteria dropdown field.
     * This will not have the BeginsWith, EndWith and Like options, since they are meaningless for date fields.
     * @return a Map containg value-label pairs to be displayed in a criteria dropdown field.
     */
    public static Map getDateCriteriaDropDownOptions() {
        return c_dateCriteriaDropDownOptions;
    }

    /** Returns a Map containg value-label pairs to be displayed in a criteria dropdown field.
     * This will not have the Between and In options, since they are not supported for DropDown fields.
     * This will not have the BeginsWith, EndWith and Like options, since they are meaningless for DropDown fields.
     * @return a Map containg value-label pairs to be displayed in a criteria dropdown field.
     */
    public static Map getDropDownCriteriaDropDownOptions() {
        return c_dropDownCriteriaDropDownOptions;
    }

    /** Returns a Map containg value-label pairs to be displayed in a criteria dropdown for Boolean fields.
     * @return a Map containg value-label pairs to be displayed in a criteria dropdown for Boolean fields.
     */
    public static Map getBooleanCriteriaDropDownOptions() {
        return c_booleanCriteriaDropDownOptions;
    }

    /** Returns a Map containg value-label pairs to be displayed in a dropdown for selecting the Max Records to be displayed in the Results page.
     * The key will be a Long object, while the value will be a formatted String object.
     * @return a Map containg value-label pairs to be displayed in a dropdown for selecting the Max Records to be displayed in the Results page.
     */
    public static Map getMaxRecordsDropDownOptions() {
        return c_maxRecordsDropDownOptions;
    }

    /** Returns the default value to be displayed in the 'MaxRecords' dropdown of the Finder/Lookup criteria screens.
     * @return the default value to be displayed in the 'MaxRecords' dropdown of the Finder/Lookup criteria screens.
     */
    public static Long getDefaultMaxRecordsDropDownOption() {
        return c_defaultMaxRecordsDropDownOption;
    }

}
