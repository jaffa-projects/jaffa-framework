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

package org.jaffa.presentation.portlet.widgets.taglib;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.apache.log4j.Logger;
import java.util.*;
import org.jaffa.presentation.portlet.StartComponentAction;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.CssParser;
import org.jaffa.util.URLHelper;

import javax.servlet.jsp.tagext.*;

import org.jaffa.datatypes.Parser;
import org.jaffa.rules.IPropertyRuleIntrospector;

/** Tag Handler for the Lookup.*/
public class LookupTag extends CustomTag implements IWidgetTag,IFormTag,IBodyTag {
    
    private static Logger log = Logger.getLogger(LookupTag.class);
    private static final String TAG_NAME = "LookupTag";
    private static final String START_COMPONENT_DOT_DO = "startComponent.do";
    private static final String DISPLAY_RESULTS_SCREEN = "displayResultsScreen";
    private static final String TARGET_FIELDS = "targetFields";
    private static final String RETURN_STYLE  = "returnStyle";
    
    /** This pattern is used to extract number at the end of a String.
     * NOTE: We use the reluctant quantifier '?' after '.+', so that we can grab all the digits at the end of the String
     */
    private static final Pattern ENDS_WITH_NUMBER_PATTERN = Pattern.compile("(.+?)([0-9]+)");
    
    /** This pattern is used to extract String, +/-, Number from an input String.
     */
    private static final Pattern STRING_PLUSMINUS_NUMBER_PATTERN = Pattern.compile("(.+) *([+-]{1}) *([0-9]+)");
    
    
    
    /** property declaration for tag attribute: component.
     */
    private String m_component;
    
    /** property declaration for tag attribute: bypassCriteriaScreen.
     */
    private java.lang.Boolean m_bypassCriteriaScreen;
    
    /** property declaration for tag attribute: dynamicParameters.
     */
    private String m_dynamicParameters;
    
    /** property declaration for tag attribute: staticParameters.
     */
    private String m_staticParameters;
    
    /** property declaration for tag attribute: targetFields.
     */
    private String m_targetFields;
    
    /** property declaration for tag attribute: returnStyle.
     */
    private String m_returnStyle;
    
    /** Introspector for the field */
    private IPropertyRuleIntrospector m_propertyRuleIntrospector = null;
    
    
    
    /** Default constructor.
     */
    public LookupTag() {
        super();
        initTag();
    }
    
    private void initTag() {
        m_component = null;
        m_bypassCriteriaScreen = null;
        m_dynamicParameters = null;
        m_staticParameters = null;
        m_targetFields = null;
        m_propertyRuleIntrospector = null;
        m_returnStyle=null;
    }
    
    
    
    /** Called from the doStartTag()
     */
    public void otherDoStartTagOperations() throws JspException {
        
        super.otherDoStartTagOperations();
        
        // Preprocess if within a Property widget
        m_propertyRuleIntrospector = lookupPropertyTag();
        
        // We'll be more lenient and simply not generate anything if the required parameters are not passed.
//        // raise an error, if the 'component' is null
//        if (getComponent() == null) {
//            String str = "The " + TAG_NAME + " requires 'component' parameter to be supplied or it should be within a PropertyTag which has a ForeignKey rule containing the 'component' parameter";
//            log.error(str);
//            throw new MissingParametersRuntimeException(str);
//        }
//
//        // raise an error, if the 'targetFields' is null
//        if (getTargetFields() == null) {
//            String str = "The " + TAG_NAME + " requires 'targetFields' parameter to be supplied or it should be within a PropertyTag which has a ForeignKey rule containing the 'targetFields' parameter";
//            log.error(str);
//            throw new MissingParametersRuntimeException(str);
//        }
        
    }
    
    /** Returns a false if the property is hidden or read-only,
     * or if the required paramteres - component & targetFields - have not been passed,
     * or if the user does not have access to the component.
     * @return a false if the property is hidden or read-only.
     */
    public boolean theBodyShouldBeEvaluated() {
        if (m_propertyRuleIntrospector != null && (m_propertyRuleIntrospector.isHidden() || m_propertyRuleIntrospector.isReadOnly())) {
            if (log.isDebugEnabled())
                log.debug("LookupTag will not be rendered since the property has been marked as hidden/read-only");
            return false;
        } else if (getComponent() == null || getTargetFields() == null) {
            if (log.isDebugEnabled())
                log.debug("LookupTag will not be rendered since the required parameters - component & targetFields - have not been passed");
            return false;
        } else {
            try {
                if (!SecurityManager.checkComponentAccess(getComponent())) {
                    if (log.isDebugEnabled())
                        log.debug("LookupTag will not be rendered since the user does not have access to the component " + getComponent());
                    return false;
                }
            } catch (SecurityException e) {
                if (log.isDebugEnabled())
                    log.debug("LookupTag will not be rendered due to invalid component " + getComponent());
                return false;
            }
            return super.theBodyShouldBeEvaluated();
            
        }
    }
    
    
    
    
    /** Getter for the attribute component.
     * @return Value of attribute component.
     */
    public String getComponent() {
        return m_component;
    }
    
    /** Setter for the attribute component.
     * @param value New value of attribute component.
     */
    public void setComponent(String value) {
        m_component = value;
    }
    
    /** Getter for the attribute bypassCriteriaScreen.
     * @return Value of attribute bypassCriteriaScreen.
     */
    public java.lang.Boolean getBypassCriteriaScreen() {
        return m_bypassCriteriaScreen;
    }
    
    /** Setter for the attribute bypassCriteriaScreen.
     * @param value New value of attribute bypassCriteriaScreen.
     */
    public void setBypassCriteriaScreen(java.lang.Boolean value) {
        m_bypassCriteriaScreen = value;
    }
    
    /** Getter for the attribute dynamicParameters.
     * This will be a semicolon separated list of parameters to pass to the lookup.
     * It should conform to the pattern {FieldNameOnLookup=FieldNameOnTarget{;FieldNameOnLookup=FieldNameOnTarget}}
     * @return Value of attribute dynamicParameters.
     */
    public String getDynamicParameters() {
        return m_dynamicParameters;
    }
    
    /** Setter for the attribute dynamicParameters.
     * This should be a semicolon separated list of parameters to pass to the lookup.
     * It should conform to the pattern {FieldNameOnLookup=FieldNameOnTarget{;FieldNameOnLookup=FieldNameOnTarget}}
     * @param value New value of attribute dynamicParameters.
     */
    public void setDynamicParameters(String value) {
        m_dynamicParameters = value;
    }
    
    /** Getter for the attribute staticParameters.
     * This will be a semicolon separated static list of field/value pairs to pass to the lookup.
     * It should conform to the pattern {FieldNameOnLookup=SomeValue{;FieldNameOnLookup=SomeValue}}
     * @return Value of attribute staticParameters.
     */
    public String getStaticParameters() {
        return m_staticParameters;
    }
    
    /** Setter for the attribute staticParameters.
     * This should be a semicolon separated static list of field/value pairs to pass to the lookup.
     * It should conform to the pattern {FieldNameOnLookup=SomeValue{;FieldNameOnLookup=SomeValue}}
     * @param value New value of attribute staticParameters.
     */
    public void setStaticParameters(String value) {
        m_staticParameters = value;
    }
    
    /** Getter for the attribute targetFields.
     * This will be a semicolon list of fields to be updated on the target screen, from the lookup.
     * It should conform to the pattern {FieldNameOnTarget=FieldNameOnLookup{;FieldNameOnTarget=FieldNameOnLookup}}
     * @return Value of attribute targetFields.
     */
    public String getTargetFields() {
        return m_targetFields;
    }
    
    /** Setter for the attribute targetFields.
     * This should be a semicolon list of fields to be updated on the target screen, from the lookup.
     * It should conform to the pattern {FieldNameOnTarget=FieldNameOnLookup{;FieldNameOnTarget=FieldNameOnLookup}}
     * @param value New value of attribute targetFields.
     */
    public void setTargetFields(String value) {
        m_targetFields = value;
    }
    
    /** Getter for the attribute returnStyle.
     * @return Value of attribute returnStyle.
     */
    public String getReturnStyle() {
        return m_returnStyle;
    }
    
    /** Setter for the attribute returnStyle.
     * @param returnStyle New value of attribute returnStyle.
     */
    public void setReturnStyle(String returnStyle) {
        m_returnStyle = returnStyle;
    }
    
    /**
     * Fill in this method to process the body content of the tag.
     * You only need to do this if the tag's BodyContent property
     * is set to "JSP" or "tagdependent."
     * If the tag's bodyContent is set to "empty," then this method
     * will not be called.
     * @param out The JspWriter object.
     * @param bodyContent The BodyContent object.
     * @throws IOException if any I/O error occurs.
     */
    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        
        out.println(getHtml());
        
        // write the body content (after processing by the JSP engine) on the output Writer
        bodyContent.writeOut(out);
        
        out.println( getEndingHtml() );
        
        // clear the body content for the next time through.
        bodyContent.clearBody();
    }
    
    
    
    /** returns </button> tag
     */
    private String getEndingHtml() {
        return "</button>";
    }
    
    
    /** returns <button href='#' onclick='SomeJavaScript'> tag
     */
    private String getHtml() throws UnsupportedEncodingException {
        try {
            StringBuffer buf = new StringBuffer("<button");
            buf.append(" class='Lookup'");
            buf.append(" type='button'");
            buf.append(" onclick='" + getOnClickCode() + "'>");
            return buf.toString();
        } catch (ParseException e) {
            log.error("Invalid returnStyle value.", e);
            return e.getMessage();
        }
    }
    
    
    private String getOnClickCode() throws UnsupportedEncodingException, ParseException {
        StringBuffer buf = new StringBuffer("javascript:");
        String url = getUrl();
        buf.append(url);
        if (getDynamicParameters() != null && getDynamicParameters().length() > 0) {
            StringTokenizer tokenizer = new StringTokenizer(getDynamicParameters(), ";");
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                int i = token.indexOf('=');
                if (i <= 0 || i == token.length()) {
                    String str = "Illegal dynamicParameters passed: " + getDynamicParameters();
                    log.error(str);
                    throw new IllegalArgumentException(str);
                }
                buf.append("if (document.getElementById(\"" + determineFieldId(token.substring(i+1)) + "\"))"); 
                buf.append("v_url += \"&" + token.substring(0,i) + "=\" + ");
                buf.append("encodeURIComponent(document.getElementById(\"" + determineFieldId(token.substring(i+1)) + "\").value);");
            }
//            buf.append("window.open(v_url);");
//        } else {
//            buf.append("window.open(\"" + url + "\");");
        }
        buf.append("window.open(v_url);");
        buf.append("return false;");
        return buf.toString();
    }
    
    /** returns http://www.example.org/myapp/startComponent.do?component=Abc&killComponents=false&displayResultsScreen=aBoolean&targetFields=aList&aStaticParameter=someValue...
     */
    private String getUrl() throws UnsupportedEncodingException, ParseException {
    	StringBuffer buf = new StringBuffer("var v_url = \"");
        buf.append(URLHelper.getBase((HttpServletRequest) pageContext.getRequest()));
        buf.append(START_COMPONENT_DOT_DO);
        buf.append('?');
        
        // add the 'component' parameter
        buf.append(StartComponentAction.COMPONENT_PARAMETER);
        buf.append('=');
        buf.append(URLEncoder.encode(getComponent(), "UTF-8"));
        
        // add the 'returnStyle' parameter
        if (getReturnStyle() != null) {
            // Need to convert all field names used in the style into page level DOM ids
            Map m = CssParser.parse(getReturnStyle());
            Map m2 = new LinkedHashMap();
            if(m!=null&&!m.isEmpty()) {
                for(Iterator it=m.entrySet().iterator();it.hasNext();) {
                    Map.Entry e = (Map.Entry)it.next();
                    m2.put(determineFieldId((String)e.getKey()),e.getValue());
                }
                buf.append('&')
                .append(RETURN_STYLE)
                .append('=')
                .append(CssParser.toString(m2));
            }
        }
        
        // add the 'displayResultsScreen' parameter
        if (getBypassCriteriaScreen() != null && getBypassCriteriaScreen().booleanValue()) {
            buf.append('&');
            buf.append(DISPLAY_RESULTS_SCREEN);
            buf.append('=');
            buf.append(getBypassCriteriaScreen().booleanValue());
        }
        
        // add the 'targetFields' parameter
        buf.append('&');
        buf.append(TARGET_FIELDS);
        buf.append("=\";");
        
        if(getTargetFields()!=null) {
        	buf.append("var v_parms = \"\";");
	        StringTokenizer tokenizer = new StringTokenizer(getTargetFields(), ";");
	        
	        //boolean firstPass = true;
	        while (tokenizer.hasMoreTokens()) {
	            String token = tokenizer.nextToken();
	            int i = token.indexOf('=');
	            if (i <= 0 || i == token.length()) {
	                String str = "Illegal targetFields passed: " + getTargetFields();
	                log.error(str);
	                throw new IllegalArgumentException(str);
	            }
	            
	            buf.append("if (document.getElementById(\"" + determineFieldId(token.substring(0,i)) + "\")) v_parms+=(v_parms?\";\":\"\") + \"");
	          
	            buf.append(determineFieldId(token.substring(0,i)));
	            buf.append(';');
	            buf.append(token.substring(i+1));
	            buf.append("\";");
	
	        }
	        
	        buf.append("if (v_parms) v_url += v_parms;");
        }
	        
        // add the static parameters
        if (getStaticParameters() != null) {
            buf.append("v_url+=\"");
            StringTokenizer tkzr = new StringTokenizer(getStaticParameters(), ";");
            while (tkzr.hasMoreTokens()) {
                String token = tkzr.nextToken();
                int i = token.indexOf('=');
                if (i <= 0 || i == token.length()) {
                    String str = "Illegal staticParameters passed: " + getStaticParameters();
                    log.error(str);
                    throw new IllegalArgumentException(str);
                }
            
                buf.append('&');
                buf.append(token.substring(0,i));
                buf.append('=');
                buf.append(URLEncoder.encode(token.substring(i+1), "UTF-8"));
            }
            buf.append("\";");
        }
        
        return buf.toString();
    }
    
    /** returns the id for a field on the JSP
     * examples of fieldname:
     * 1- serial (this will look for the field 'serial' within the current context)
     * 2- /serial (this will look for the field 'serial' at the root (i.e form) context
     *
     * good to have (@todo)
     * 1- ../Xyz/serial (this will look for the field in the appropriate context)
     */
    private String determineFieldId(String fieldName) {
        String fieldId = null;
        if (fieldName != null) {
            if (fieldName.length() > 1 && fieldName.indexOf('/') == 0) {
                fieldId = TagHelper.getFormTag(pageContext).getHtmlIdPrefix() + '_' + fieldName.substring(1);
            } else {
                // use the current context
                fieldId = (String) pageContext.findAttribute(TagHelper.ATTRIBUTE_ID_PREFIX);
                if (fieldName.indexOf('+') > -1 || fieldName.indexOf('-') > -1) {
                    // The field exists in a different GridRow of the current context
                    fieldId = parseNadd(fieldId, fieldName);
                } else {
                    // The field exists in the current context
                    fieldId += '_' + fieldName;
                }
            }
        }
        return fieldId;
    }
    
    /** This will perform the following.
     * Assuming fieldId="abc_21" and fieldName="xyz+20"
     * This will return "abc_41_xyz".
     * If the inputs do not match the above pattern, {fieldId}_{fieldName} will be returned.
     */
    private String parseNadd(String fieldId, String fieldName) {
        String output = null;
        
        // Ensure that the fieldId ends with a number. For eg: xxxxxxNN
        Matcher fieldIdMatcher = ENDS_WITH_NUMBER_PATTERN.matcher(fieldId);
        if (fieldIdMatcher.matches()) {
            String prefix = fieldIdMatcher.group(1); //xxxxxx from xxxxxxNN
            int currentRowNumber = Integer.parseInt(fieldIdMatcher.group(2)); //NN from xxxxxxNN
            
            // Ensure that the fieldName has the pattern xxxxxx+NN, xxxxxx-NN
            Matcher fieldNameMatcher = STRING_PLUSMINUS_NUMBER_PATTERN.matcher(fieldName);
            if (fieldNameMatcher.matches()) {
                String suffix = fieldNameMatcher.group(1); //xxxxxx from xxxxxx+NN
                char operator = fieldNameMatcher.group(2).charAt(0); //+ from xxxxxx+NN
                int rowNumber = Integer.parseInt(fieldNameMatcher.group(3)); //NN from xxxxxx+NN
                if (operator == '-')
                    rowNumber *= -1;
                output = prefix + (currentRowNumber + rowNumber) + '_' + suffix;
            }
        }
        
        // If the above has failed, simply add the input fieldId, underscore and fieldName
        if (output == null)
            output = fieldId + '_' + fieldName;
        
        return output;
    }
    
    /** Checks for the nearest outer PropertyTag.
     * Will set the various attributes on this tag, if not specifed, with the values from the ForeignKey rule for the outer property.
     * @return the IPropertyRuleIntrospector from the outer PropertyTag.
     */
    private IPropertyRuleIntrospector lookupPropertyTag() {
        PropertyTag propertyTag = (PropertyTag) findCustomTagAncestorWithClass(this, PropertyTag.class);
        if (propertyTag != null) {
            IPropertyRuleIntrospector propertyRuleIntrospector = propertyTag.getPropertyRuleIntrospector();
            if (propertyRuleIntrospector != null) {
                Properties foreignKeyInfo = propertyRuleIntrospector.getForeignKeyInfo();
                if (foreignKeyInfo != null) {
                    if (m_component == null)
                        m_component = foreignKeyInfo.getProperty("component", null);
                    if (m_dynamicParameters == null)
                        m_dynamicParameters = foreignKeyInfo.getProperty("dynamicParameters", null);
                    if (m_staticParameters == null)
                        m_staticParameters = foreignKeyInfo.getProperty("staticParameters", null);
                    if (m_targetFields == null)
                        m_targetFields = foreignKeyInfo.getProperty("targetFields", null);
                    if (m_bypassCriteriaScreen == null)
                        m_bypassCriteriaScreen = Parser.parseBoolean(foreignKeyInfo.getProperty("bypassCriteriaScreen", null));
                }
            }
            return propertyRuleIntrospector;
        } else
            return null;
    }
    
    /** Invoked in all cases after doEndTag() for any class implementing Tag, IterationTag or BodyTag. This method is invoked even if an exception has occurred in the BODY of the tag, or in any of the following methods: Tag.doStartTag(), Tag.doEndTag(), IterationTag.doAfterBody() and BodyTag.doInitBody().
     * This method is not invoked if the Throwable occurs during one of the setter methods.
     * This will reset the internal fields, so that the Tag can be re-used.
     */
    public void doFinally() {
        super.doFinally();
        initTag();
    }
}
