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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.MissingParametersRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException;
import org.jaffa.util.MessageHelper;

import org.jaffa.rules.IPropertyRuleIntrospector;

/**
 * LabelTag should not implement IFormTag, as it can be used on a regular page
 */
public class LabelTag extends CustomModelTag implements IWidgetTag {

    private static Logger log = Logger.getLogger(LabelTag.class);
    private static final String TAG_NAME = "LabelTag";
    private static final String UNDEFINED_LABEL = "UNDEFINED ";
    static final String ERROR_LABEL = "**ERROR**";


    /** property declaration for tag attribute: domain.
     */
    private String m_domain;

    /** property declaration for tag attribute: key.
     */
    private String m_key;

    /** property declaration for tag attribute: arg0.
     */
    private String m_arg0;

    /** property declaration for tag attribute: arg1.
     */
    private String m_arg1;

    /** property declaration for tag attribute: arg2.
     */
    private String m_arg2;

    /** property declaration for tag attribute: arg3.
     */
    private String m_arg3;

    /** property declaration for tag attribute: arg4.
     */
    private String m_arg4;



    /** Default constructor.
     */
    public LabelTag() {
        super();
        initTag();
    }

    private void initTag() {
        m_domain = null;
        m_field = null;
        m_key = null;
        m_arg0 = null;
        m_arg1 = null;
        m_arg2 = null;
        m_arg3 = null;
        m_arg4 = null;
    }

    /** Called from the doStartTag()
     */
    public void otherDoStartTagOperations() throws JspException {

        super.otherDoStartTagOperations();

        // Preprocess if within a Property widget
        lookupPropertyTag();

        // determine the key to be used for the label
        String key = null;
        if (m_key != null)
            key = m_key;
        else if (m_domain != null && m_field != null) {
            try {
                key = TagHelper.getFieldMetaData(m_domain, m_field).getLabelToken();
                if (log.isDebugEnabled())
                    log.debug("LabelToken for the MetaField '" + m_field + "' is : " + key);
            } catch (Exception e) {
                String str = "Exception thrown while determining the LabelToken for the MetaField " + m_field
                        + ". Domain=" + m_domain +", Field=" + m_field;
                log.error(str, e);
                try {
                    JspWriter out = pageContext.getOut();
                    out.print(ERROR_LABEL);
                } catch (IOException ioe) {
                    String str2 = "Exception in writing the " + TAG_NAME;
                    log.error(str2, ioe);
                    throw new JspWriteRuntimeException(str2, ioe);
                }
                return;
                //throw new JspWriteRuntimeException(str, e);
            }
        }

        // raise an error, if the key is still null
        if (key == null) {
            String str = "The " + TAG_NAME + " requires either a valid 'key' or 'domain and field' parameters to be supplied";
            log.error(str);
            throw new MissingParametersRuntimeException(str);
        }

        /* NOTE: Key can be of the following formats
         *  xyz           : Invoke MessageHelper.findMessage() to find the label for the key 'xyz'
         *  [xyz]         : Invoke MessageHelper.findMessage() to find the label for the detokenized key 'xyz'
         *  abc [xyz] efg : Invoke MessageHelper.replaceTokens to resolve the text'abc [xyz] efg'
         */

        // get the label from the resource bundle
        String label = null;
        if (MessageHelper.hasTokens(key)) {
            // Remove the begin/end token-markers
            String detokenizedKey = MessageHelper.removeTokenMarkers(key);

            // Check if there are more tokens left
            if (MessageHelper.hasTokens(detokenizedKey))
                label = MessageHelper.replaceTokens(pageContext, key);
            else
                key = detokenizedKey;
        }

        if (label == null) {
            Object[] args = null;
            if (m_arg0 != null || m_arg1 != null || m_arg2 != null || m_arg3 != null || m_arg4 != null)
                args = new Object[] {m_arg0, m_arg1, m_arg2, m_arg3, m_arg4};
            label = MessageHelper.findMessage(pageContext, key, args);
        }

        // write the label
        try {
            JspWriter out = pageContext.getOut();
            out.print((label != null ? label : UNDEFINED_LABEL + '(' + key + ')'));
            out.print(TagHelper.getLabelEditorLink(pageContext, key));
        } catch (IOException e) {
            String str = "Exception in writing the " + TAG_NAME;
            log.error(str, e);
            throw new JspWriteRuntimeException(str, e);
        }

    }



    /** Getter for the attribute domain.
     * @return Value of attribute domain.
     */
    public String getDomain() {
        return m_domain;
    }

    /** Setter for the attribute domain.
     * @param value New value of attribute domain.
     */
    public void setDomain(String value) {
        m_domain = value;
    }


    /** Getter for the attribute key.
     * @return Value of attribute key.
     */
    public String getKey() {
        return m_key;
    }

    /** Setter for the attribute key.
     * @param value New value of attribute key.
     */
    public void setKey(String value) {
        m_key = value;
    }

    /** Getter for the attribute arg0.
     * @return Value of attribute arg0.
     */
    public String getArg0() {
        return m_arg0;
    }

    /** Setter for the attribute arg0.
     * @param value New value of attribute arg0.
     */
    public void setArg0(String value) {
        m_arg0 = value;
    }

    /** Getter for the attribute arg1.
     * @return Value of attribute arg1.
     */
    public String getArg1() {
        return m_arg1;
    }

    /** Setter for the attribute arg1.
     * @param value New value of attribute arg1.
     */
    public void setArg1(String value) {
        m_arg1 = value;
    }

    /** Getter for the attribute arg2.
     * @return Value of attribute arg2.
     */
    public String getArg2() {
        return m_arg2;
    }

    /** Setter for the attribute arg2.
     * @param value New value of attribute arg2.
     */
    public void setArg2(String value) {
        m_arg2 = value;
    }

    /** Getter for the attribute arg3.
     * @return Value of attribute arg3.
     */
    public String getArg3() {
        return m_arg3;
    }

    /** Setter for the attribute arg3.
     * @param value New value of attribute arg3.
     */
    public void setArg3(String value) {
        m_arg3 = value;
    }

    /** Getter for the attribute arg4.
     * @return Value of attribute arg4.
     */
    public String getArg4() {
        return m_arg4;
    }

    /** Setter for the attribute arg4.
     * @param value New value of attribute arg4.
     */
    public void setArg4(String value) {
        m_arg4 = value;
    }

    /** Checks for the nearest outer PropertyTag.
     * Will set the 'key' on this tag, if not specified, with the value from the rules engine.
     * Will set the 'domain' and field' on this tag, if not specifed, with the values from the outer PropertyTag.
     * @return the IPropertyRuleIntrospector from the outer PropertyTag.
     */
    private IPropertyRuleIntrospector lookupPropertyTag() {
        // Cannnot use findCustomTagAncestorWithClass, since LabelTag doesn't implement IFormTag, and hence does not have a ParentTag stamped on it
        PropertyTag propertyTag = (PropertyTag) findAncestorWithClass(this, PropertyTag.class);

        if (propertyTag != null) {
            if (log.isDebugEnabled()) log.debug("Found Property Tag for Label. Field=" + propertyTag.getField());
            IPropertyRuleIntrospector propertyRuleIntrospector = propertyTag.getPropertyRuleIntrospector();
            if (getKey() == null && getDomain() == null && getField() == null) {
                if (propertyRuleIntrospector != null) {
                    setKey(propertyRuleIntrospector.getLabel());
                    if (log.isDebugEnabled()) log.debug("Found Label via Rules (Field=" + propertyTag.getField()+") = " + getKey());
                }
                setDomain(propertyTag.getPropertyClass());
                setField(propertyTag.getPropertyName());
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
