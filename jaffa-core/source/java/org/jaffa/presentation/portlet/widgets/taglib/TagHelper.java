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
import java.net.URLEncoder;
import javax.servlet.jsp.PageContext;
import java.util.*;
import org.jaffa.presentation.portlet.FormBase;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterFormTagMissingRuntimeException;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.StringHelper;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.WidgetModelAccessMethodNotFoundRuntimeException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.WidgetModelAccessMethodInvocationRuntimeException;
import org.jaffa.presentation.portlet.session.UserSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.presentation.portlet.StartComponentAction;
import org.jaffa.presentation.portlet.widgets.model.PropertyRuleIntrospectorUsingWidgetModel;
import org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.rules.IRulesEngine;
import org.jaffa.rules.RulesEngineException;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.security.SecurityTag;
import org.jaffa.util.MessageHelper;
import org.jaffa.util.URLHelper;
/** This has helper routines used by the Tag handlers.
 */
public class TagHelper {
    
    private static Logger log = Logger.getLogger(TagHelper.class);
    
    /**
     * This attribute is set by the HeaderTag
     * Its used by the FooterTag, which ultimately removes it from the pageContext
     */
    public static final String ATTRIBUTE_ERROR_BOX_IN_SAME_WINDOW = "org.jaffa.presentation.portlet.widgets.taglib.errorBoxInSameWindow";
    
    /**
     * This attribute is set by any widget which uses a keyboard
     * Its used by the FooterTag, which ultimately removes it from the pageContext
     */
    public static final String ATTRIBUTE_KEYBOARD_IN_USE = "org.jaffa.presentation.portlet.widgets.taglib.keyboardInUse";
    
    /**
     * This attribute is set by any widget which uses a keypad
     * Its used by the FooterTag, which ultimately removes it from the pageContext
     */
    public static final String ATTRIBUTE_KEYPAD_IN_USE = "org.jaffa.presentation.portlet.widgets.taglib.keypadInUse";
    
    /**
     * This attribute is set by the FormTag
     * Its used by the all the enclosed widgets
     * Its removed in the FormTag.release()
     */
    public static final String ATTRIBUTE_FORM_TAG = "org.jaffa.presentation.portlet.widgets.taglib.formTag";
    
    /**
     * This attribute is set by the FormTag
     * Its used by the all the enclosed widgets
     * Its removed in the FormTag.release()
     */
    public static final String ATTRIBUTE_FORM_NAME = "org.jaffa.presentation.portlet.widgets.taglib.formName";
    
    /**
     * This attribute is set by the FormTag
     * Its used by the all the enclosed widgets
     * Its removed in the FormTag.release()
     */
    public static final String ATTRIBUTE_FORM_BASE = "org.jaffa.presentation.portlet.widgets.taglib.formBase";
    
    
    
    /**
     * This is a standard attribute which determines if a widget will have a "name" attribute or not
     * Its removed in the FormTag.release()
     */
    public static final String ATTRIBUTE_ENCLOSED = "org.jaffa.presentation.portlet.widgets.taglib.enclosed";
    
    /**
     * This is a standard attribute which carries the idPrefix of the parent tag
     * Its removed in the FormTag.release()
     */
    public static final String ATTRIBUTE_ID_PREFIX = "org.jaffa.presentation.portlet.widgets.taglib.idPrefix";
    
    /**
     * This is a standard attribute which carries the id of the Parent tag.
     * This may differ from the ATTRIBUTE_ID_PREFIX, which typically carries extra context like the columnNo.
     * Its removed in the FormTag.release()
     */
    public static final String ATTRIBUTE_PARENT_NODE_ID = "org.jaffa.presentation.portlet.widgets.taglib.parentNodeId";
    
    /**
     * This is a standard attribute which carries the eventPrefix, set by the enclosing tags of the parent tag
     * Its removed in the FormTag.release()
     */
    public static final String ATTRIBUTE_EVENT_PREFIX = "org.jaffa.presentation.portlet.widgets.taglib.eventPrefix";
    
    /**
     * This is a standard attribute which carries the IWidgetMap object
     * Its removed in the FormTag.release()
     */
    public static final String ATTRIBUTE_MODEL_MAP = "org.jaffa.presentation.portlet.widgets.taglib.modelMap";
    
    /** This is the name of the Grid Row accessable via Expression Language */
    public static final String ATTRIBUTE_EL_ROW = "row";
    /** This is the name of the Form bean accessable via Expression Language */
    public static final String ATTRIBUTE_EL_FORM = "form";
    
    /**
     * This is a session-level attribute which will contain the prefix to use for hyperlinking to the LabelEditor.
     * It'll contain a blank String, if the user has no access to the LabelEditor component.
     */
    public static final String ATTRIBUTE_LABEL_EDITOR_LINK_PREFIX = "org.jaffa.presentation.portlet.widgets.taglib.labelEditorLinkPrefix";
    
    /**
     * This is a helper constant, which has the value "&nbsp;"
     */
    public static final String HTML_SPACE_CHARACTER = "&nbsp;";
    
    /** This String is used for constructing the hyperlink to the LabelEditor component.
     */
    private static final String LABEL_EDITOR_LINK_SUFFIX = "'>" + HTML_SPACE_CHARACTER + "</a>";
    
    /** This Pattern is used by the getFieldMetaData() to obtain the domain-classname from a form-bean.
     */
    private static final Pattern PATTERN_TO_OBTAIN_DOMAIN_CLASSNAME = Pattern.compile("^(.+)\\.components\\..+\\.(.+)(Lookup|Finder|Maintenance|Viewer)(Form|Component)$");
    
    
    /** Returns a Map containing the attributes ATTRIBUTE_ENCLOSED, ATTRIBUTE_ID_PREFIX, ATTRIBUTE_PARENT_NODE_ID, ATTRIBUTE_EVENT_PREFIX, ATTRIBUTE_MODEL_MAP.
     * @param pageContext The PageContext of the jsp.
     * @return a Map containing the attributes ATTRIBUTE_ENCLOSED, ATTRIBUTE_ID_PREFIX, ATTRIBUTE_PARENT_NODE_ID, ATTRIBUTE_EVENT_PREFIX, ATTRIBUTE_MODEL_MAP.
     */
    public static Map getOriginalAttributes(PageContext pageContext) {
        Map map = new HashMap();
        map.put(ATTRIBUTE_ENCLOSED, pageContext.findAttribute(ATTRIBUTE_ENCLOSED));
        map.put(ATTRIBUTE_ID_PREFIX, pageContext.findAttribute(ATTRIBUTE_ID_PREFIX));
        map.put(ATTRIBUTE_PARENT_NODE_ID, pageContext.findAttribute(ATTRIBUTE_PARENT_NODE_ID));
        map.put(ATTRIBUTE_EVENT_PREFIX, pageContext.findAttribute(ATTRIBUTE_EVENT_PREFIX));
        map.put(ATTRIBUTE_MODEL_MAP, pageContext.findAttribute(ATTRIBUTE_MODEL_MAP));
        map.put(ATTRIBUTE_EL_ROW, pageContext.findAttribute(ATTRIBUTE_EL_ROW));
        return map;
    }
    
    /** Restores the attributes ATTRIBUTE_ENCLOSED, ATTRIBUTE_ID_PREFIX, ATTRIBUTE_PARENT_NODE_ID, ATTRIBUTE_EVENT_PREFIX, ATTRIBUTE_MODEL_MAP in the PageContext to the values passed in the input Map.
     * @param pageContext The PageContext of the jsp.
     * @param map The Map containing the original values.
     */
    public static void restoreOriginalAttributes(PageContext pageContext, Map map) {
        restoreOriginalAttribute(pageContext, map, ATTRIBUTE_ENCLOSED);
        restoreOriginalAttribute(pageContext, map, ATTRIBUTE_ID_PREFIX);
        restoreOriginalAttribute(pageContext, map, ATTRIBUTE_PARENT_NODE_ID);
        restoreOriginalAttribute(pageContext, map, ATTRIBUTE_EVENT_PREFIX);
        restoreOriginalAttribute(pageContext, map, ATTRIBUTE_MODEL_MAP);
        restoreOriginalAttribute(pageContext, map, ATTRIBUTE_EL_ROW);
    }
    
    static void restoreOriginalAttribute(PageContext pageContext, Map map, String attributeName) {
        // NOTE: This should probably use the original scope of the attribute when invoking the setAttribute() method.
        // That would involve storing the original scope in the map returned by getOriginalAttributes() method.
        // Will do that, if the need arises. Until then, will use the request-scope
        Object obj = map.get(attributeName);
        if (obj == null)
            pageContext.removeAttribute(attributeName);
        else
            pageContext.setAttribute(attributeName, obj, pageContext.REQUEST_SCOPE);
    }
    
    /** Returns the tag handler for the FormTag.
     * @param pageContext The PageContext of the jsp.
     * @return the tag handler for the FormTag.
     */
    public static FormTag getFormTag(PageContext pageContext) {
        return (FormTag) pageContext.findAttribute(ATTRIBUTE_FORM_TAG);
    }
    
    /** Returns the name attribute used by the HTML form tag
     * @param pageContext The PageContext of the jsp.
     * @return the tag handler for the FormTag.
     */
    public static String getHtmlFormName(PageContext pageContext) {
        FormTag f = (FormTag) pageContext.findAttribute(ATTRIBUTE_FORM_TAG);
        return f==null?null:f.getHtmlName();
    }
    
    /** Returns the name of the Form.
     * @param pageContext The PageContext of the jsp.
     * @return the name of the Form in the page context. This is the same as the Struts form name.
     */
    public static String getFormName(PageContext pageContext) {
        return (String) pageContext.findAttribute(ATTRIBUTE_FORM_NAME);
    }
    
    /** Returns the Form object.
     * @param pageContext The PageContext of the jsp.
     * @return the Form object.
     */
    public static FormBase getFormBase(PageContext pageContext) {
        return (FormBase) pageContext.findAttribute(ATTRIBUTE_FORM_BASE);
    }
    
    /** Returns a true if the current widget is enclosed inside another widget.
     * @param pageContext The PageContext of the jsp.
     * @return a true if the current widget is enclosed inside another widget.
     */
    public static boolean isEnclosed(PageContext pageContext) {
        Boolean result = (Boolean) pageContext.findAttribute(ATTRIBUTE_ENCLOSED);
        if (result != null)
            return result.booleanValue();
        else
            return false;
    }
    
    /** Returns the id prefix.
     * @param pageContext The PageContext of the jsp.
     * @return the id prefix.
     */
    public static String getIdPrefix(PageContext pageContext) {
        return (String) pageContext.findAttribute(ATTRIBUTE_ID_PREFIX);
    }
    
    /** Returns the id of the Parent tag.
     * @param pageContext The PageContext of the jsp.
     * @return the id of the Parent tag.
     */
    public static String getParentNodeId(PageContext pageContext) {
        return (String) pageContext.findAttribute(ATTRIBUTE_PARENT_NODE_ID);
    }
    
    /** Returns the event prefix.
     * @param pageContext The PageContext of the jsp.
     * @return the event prefix.
     */
    public static String getEventPrefix(PageContext pageContext) {
        return (String) pageContext.findAttribute(ATTRIBUTE_EVENT_PREFIX);
    }
    
    /** Returns a ModelMap object from the PageContext.
     * @param pageContext The PageContext of the jsp.
     * @return a ModelMap object from the PageContext.
     */
    public static Map getModelMap(PageContext pageContext) {
        return (Map) pageContext.findAttribute(ATTRIBUTE_MODEL_MAP);
    }
    
    
    /** For an enclosed tag, this will return the model from the pageContext attribute, and it may be null.
     * If the tag is not enclosed, it will get the model from the FormBase, and it will throw a RuntimeException if the model is not found.
     * @param pageContext The PageContext of the jsp.
     * @param fieldName The field name.
     * @param tagName The tag name.
     * @return a WidgetModel object.
     */
    public static Object getModel(PageContext pageContext, String fieldName, String tagName) {
        Object model = null;
        if ( isEnclosed(pageContext) ) {
            Map map = getModelMap(pageContext);
            if (map != null)
                model = map.get(fieldName);
        } else {
            FormBase formObject = getFormBase(pageContext);
            if (formObject != null) {
                // Work out the method to get the model...should be like getFieldNameWM()
                Class formClass = formObject.getClass();
                String methodStr = "get" + StringHelper.getUpper1(fieldName) + "WM";
                if ( log.isDebugEnabled() )
                    log.debug("Introspect. Looking for " + methodStr + " on " + formClass.getName() );
                Method method = null;
                try {
                    method = formClass.getMethod(methodStr, new Class[] {});
                } catch (NoSuchMethodException e) {
                    String str = "Method :" + methodStr + " not found";
                    log.error(str, e);
                    throw new WidgetModelAccessMethodNotFoundRuntimeException(str, e);
                }
                
                try {
                    model = method.invoke(formObject, new Object[] {});
                    if ( log.isDebugEnabled() )
                        log.debug("Introspect. Got Model from FormBean");
                } catch (Exception e) {
                    String str = "Error while invoking the method :" + methodStr;
                    log.error(str, e);
                    throw new WidgetModelAccessMethodInvocationRuntimeException(str, e);
                }
                
            } else {
                String str = "The " + tagName + " for field " + fieldName + " should be inside a FormTag";
                log.error(str);
                throw new OuterFormTagMissingRuntimeException(str);
            }
        }
        return model;
    }
    
    /** For an enclosed tag, this will return the fieldValue from the pageContext attribute, and it may be null.
     * If the tag is not enclosed, it will get the fieldValue from the FormBase, and it will throw a RuntimeException if the property is not found.
     * @param pageContext The PageContext of the jsp.
     * @param fieldName The field name.
     * @param tagName The tag name.
     * @return a value for the field.
     */
    public static Object getFieldValue(PageContext pageContext, String fieldName, String tagName) {
        Object value = null;
        if (isEnclosed(pageContext)) {
            Map map = getModelMap(pageContext);
            if (map != null)
                value = map.get(fieldName);
        } else {
            FormBase formObject = getFormBase(pageContext);
            if (formObject != null) {
                try {
                    value = BeanHelper.getField(formObject, fieldName);
                } catch (NoSuchMethodException e) {
                    // Lets look for a getXyzWM() method
                    try {
                        value = BeanHelper.getField(formObject, fieldName + "WM");
                    } catch (NoSuchMethodException e1) {
                        String str = "Getter Method for field " + fieldName + " not found while evaluating the " + tagName;
                        log.error(str, e);
                        throw new WidgetModelAccessMethodNotFoundRuntimeException(str, e);
                    }
                }
            } else {
                String str = "The " + tagName + " for field " + fieldName + " should be inside a FormTag";
                log.error(str);
                throw new OuterFormTagMissingRuntimeException(str);
            }
        }

        if (value != null && value instanceof SimpleWidgetModel)
            value = ((SimpleWidgetModel) value).getWidgetValue();

        return value;
    }

    /** A convenience method to return an IPropertyRuleIntrospector instance for the current formBean and input propertyName.
     * @param pageContext The PageContext of the jsp.
     * @param propertyName The property name.
     * @return an IPropertyRuleIntrospector instance for the current formBean and input propertyName.
     * A null will be returned if there is no RulesEngine implementation.
     * @throws JspException if any error occurs in instantiating the RulesEngine.
     */
    public static IPropertyRuleIntrospector getPropertyRuleIntrospector(PageContext pageContext, String propertyName)
    throws JspException {
        return getPropertyRuleIntrospector(pageContext, propertyName, null);
    }
    
    /** A convenience method to return an IPropertyRuleIntrospector instance for the input propertyClass and input propertyName.
     * @param pageContext The PageContext of the jsp.
     * @param propertyName The property name.
     * @param propertyClass The class which contains the property. If null, then getFormBase() will be used to obtain the class.
     * @return an IPropertyRuleIntrospector instance for the input propertyClass and input propertyName.
     * A null will be returned if there is no RulesEngine implementation.
     * @throws JspException if any error occurs in instantiating the RulesEngine.
     */
    public static IPropertyRuleIntrospector getPropertyRuleIntrospector(PageContext pageContext, String propertyName, String propertyClass)
    throws JspException {
        try {
            IRulesEngine rulesEngine = RulesEngineFactory.getRulesEngine();
            if (rulesEngine != null) {
                Object bean = getFormBase(pageContext);
                if (bean != null && (propertyClass == null || propertyClass.equals(bean.getClass().getName())))
                    return rulesEngine.getPropertyRuleIntrospector(bean, propertyName);
                else
                    return rulesEngine.getPropertyRuleIntrospector(Class.forName(propertyClass), propertyName);
            } else
                return null;
        } catch (ClassNotFoundException e) {
            throw new JspException(e);
        } catch (RulesEngineException e) {
            throw new JspException(e);
        }
    }
    
    /**
     * A convenience method to wrap the sourcePropertyRuleIntrospector with the widgetModel.
     * @return an IPropertyRuleIntrospector instance with a widgetModel wrapper.
     * @param sourcePropertyRuleIntrospector The PropertyRuleIntrospector to be wrapped.
     * @param widgetModel The widgetModel to wrap around the input sourcePropertyRuleIntrospector.
     * @throws JspException if any error occurs in instantiating the RulesEngine.
     */
    public static IPropertyRuleIntrospector wrapPropertyRuleIntrospector(IPropertyRuleIntrospector sourcePropertyRuleIntrospector, SimpleWidgetModel widgetModel)
    throws JspException {
        return  new PropertyRuleIntrospectorUsingWidgetModel(sourcePropertyRuleIntrospector, widgetModel);
    }
    
    /** A convenience method to return the text to display when rendering hidden fields on a JSP.
     * @param pageContext The PageContext of the jsp.
     * @return the label for the token 'label.Jaffa.Widgets.Hidden' from the associated ResourceBundle.
     */
    public static String getTextForHiddenField(PageContext pageContext) {
        return MessageHelper.findMessage(pageContext, "label.Jaffa.Widgets.Hidden", null);
    }
    
    /** A convenience method to get the UserSession.
     * @param pageContext The PageContext of the jsp.
     * @return the UserSession.
     */
    public static UserSession getUserSession(PageContext pageContext) {
        return UserSession.getUserSession((HttpServletRequest) pageContext.getRequest());
    }
    
    /** A convenience method to return a hyperlink to the LabelEditor component.
     * A blank string will be returned, if the user does not have access to the component 'Jaffa.Admin.LabelEditor'.
     * @param pageContext The PageContext of the jsp.
     * @param labelFilter The label to be edited. The labelFilter should be of the type 'xyz', '[xyz]'. Values of the type 'abc [xyz] efg [zzz]' will be ignored and a blank string will be returned.
     * @return the HTML for the hyperlink to the LabelEditor component.
     */
    public static String getLabelEditorLink(PageContext pageContext, String labelFilter) {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        
        // Perform the logic only if the user has been authenticated
        if (request.getUserPrincipal() != null) {
            String labelEditorPrefix = null;
            HttpSession session = request.getSession(false);
            if (session != null) {
                // Check the session for the cached prefix
                labelEditorPrefix = (String) session.getAttribute(ATTRIBUTE_LABEL_EDITOR_LINK_PREFIX);
                if (labelEditorPrefix == null) {
                    labelEditorPrefix = constructLabelEditorLinkPrefix(request);
                    session.setAttribute(ATTRIBUTE_LABEL_EDITOR_LINK_PREFIX, labelEditorPrefix);
                }
            } else {
                // No session, so simply create the prefix each time
                labelEditorPrefix = constructLabelEditorLinkPrefix(request);
            }
            
            if (labelEditorPrefix.length() > 0) {
                // Ensure that the labelFilter is of the type 'xyz' or '[xyz]'
                // Remove the outer token markers, if any
                // Then proceed only if no more token-markers exist
                labelFilter = MessageHelper.removeTokenMarkers(labelFilter);
                if (!MessageHelper.hasTokens(labelFilter))
                    return labelEditorPrefix + labelFilter + LABEL_EDITOR_LINK_SUFFIX;
            }
        }
        
        // We'll reach this point if the user is not aunthenticated or has no access to the component or if the labelFilter is invalid
        // Just return a blank String
        return "";
    }
    
    /** Returns the FieldMetaData object from the meta class for the input class for the input field.
     * This will first assume that the input class represents a domain classname. It'll append 'Meta' to it and look for that class in the same package.
     * If not found, it'll then assume that the class represents a formbean and will look for a corresponding 'Meta' class in the domain package.
     * @param className The name of the class.
     * @param fieldName the field name.
     * @throws Exception if if the Meta class for the input class is not found.
     */
    public static FieldMetaData getFieldMetaData(String className, String fieldName)
    throws Exception {
        FieldMetaData fieldMetaData = null;
        try {
            // assume that the input classname represents a domain class
            fieldMetaData = PersistentHelper.getFieldMetaData(className, fieldName);
        } catch (Exception e) {
            // assume that the input classname represents a formbean, extract the domain classname
            Matcher m = PATTERN_TO_OBTAIN_DOMAIN_CLASSNAME.matcher(className);
            if (m.matches() && m.groupCount() == 4) {
                className = m.group(1) + ".domain." + m.group(2);
                fieldMetaData = PersistentHelper.getFieldMetaData(className, fieldName);
            } else {
                // not a formbean.. throw the original exception
                throw e;
            }
        }
        
        return fieldMetaData;
    }
    
    /** Generates a hyperlink to the target component.
     * @param pageContext The PageContext of the jsp.
     * @param value The value to be displayed in the hyperlink.
     * @param targetComponentName the component to be rendered by the hyperlink.
     * @param targetFieldNames a semi-colon separated list of field-names of the target component.
     * @param currentFieldNames a semi-colon separated list of field-names of the current component, such that it matches the targetFieldNames.
     * @return a hyperlink.
     */
    public static String generateHyperlink(PageContext pageContext, String value, String targetComponentName, String targetFieldNames, String currentFieldNames) {
        String output = value;
        try {
            if (SecurityManager.checkComponentAccess(targetComponentName)) {
                String[] inputs = targetFieldNames.split(";");
                String[] values = currentFieldNames.split(";");
                StringBuffer href = new StringBuffer("startComponent.do?finalUrl=jaffa_closeBrowser&amp;component=");
                href.append(URLEncoder.encode(targetComponentName, "UTF-8"));
                for (int i = 0; i < inputs.length; i++) {
                    Object fieldValue = getFieldValue(pageContext, values[i], "TextTag");
                    if (fieldValue != null)
                        href.append("&amp;").append(inputs[i]).append('=').append(URLEncoder.encode(fieldValue.toString(), "UTF-8"));
                }
                StringBuffer buf = new StringBuffer("<a class='PKLink' target='_blank' href='").append(href).append("'>").append(value).append("</a>");
                output = buf.toString();
            } else {
                if (log.isDebugEnabled())
                    log.debug("Hyperlink will not be generated since the user does not have access to the component " + targetComponentName);
            }
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception thrown when attempting to create a hyperlink", e);
        }
        return output;
    }

    /** This will create a prefix for the hyperlink to the LabelEditor component.
     * A blank String will be returned if the user has no access to the component.
     */
    private static String constructLabelEditorLinkPrefix(HttpServletRequest request) {
        try {
            if(SecurityTag.hasComponentAccess(request, "Jaffa.Admin.LabelEditor")) {
                String labelEditorPrefix = new StringBuffer("<a class='LabelEditor' target='_blank' href='")
                .append(URLHelper.getBase(request))
                .append("startComponent.do?")
                .append(StartComponentAction.COMPONENT_PARAMETER)
                .append("=Jaffa.Admin.LabelEditor&")
                .append(StartComponentAction.FINALURL_PARAMETER)
                .append("=jaffa_closeBrowser&")
                .append("labelFilter=")
                .toString();
                if (log.isDebugEnabled())
                    log.debug("LabelEditorPrefix = " + labelEditorPrefix);
                return labelEditorPrefix;
            }
        } catch (SecurityException e) {
            // do nothing
        }
        if (log.isDebugEnabled())
            log.debug("User has no access to 'Jaffa.Admin.LabelEditor' component. Returning a blank LabelEditorPrefix");
        return "";
    }
    
}
