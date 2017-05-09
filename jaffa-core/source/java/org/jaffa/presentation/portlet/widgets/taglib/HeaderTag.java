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

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.Parser;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.URLHelper;
import org.jaffa.util.MessageHelper;
import org.jaffa.util.StringHelper;

/** Tag Handler for the Header tag.*/
public class HeaderTag extends CustomTag {

    private static Logger log = Logger.getLogger(HeaderTag.class);

    public static final String RULE_SERVER_TIME = "jaffa.widgets.useServerTime";

    /** property declaration for tag attribute: errorBoxInSameWindow.
     */
    private boolean m_errorBoxInSameWindow;

    /** property declaration for tag attribute: noCache.
     */
    private boolean m_noCache = true;

    /** Default constructor.
     */
    public HeaderTag() {
        super();
        initTag();
    }

    private void initTag() {
        m_errorBoxInSameWindow = false;
        m_noCache = true;
    }




    /** Returns the html for the BASE tag.
     * @return the html for the BASE tag.
     */
    private String getBaseTag() {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        StringBuffer buf = new StringBuffer("<base href=\"");
        buf.append(URLHelper.getBase((HttpServletRequest) pageContext.getRequest()));
        buf.append("\"/>\n" );
        return buf.toString();
    }


    /** Returns the html for disabling caching.
     * @return the html for disabling caching.
     */
    private String getNoCacheTag() {
        if (getNoCache()) {
            StringBuffer buf = new StringBuffer();
            buf.append("<META http-equiv=\"Pragma\" content=\"no-cache\"/>\n");
            buf.append("<META http-equiv=\"Cache-Control\" content=\"no-cache\"/>\n");
            buf.append("<META http-equiv=\"Expires\" content=\"0\"/>\n");
            return buf.toString();
        } else {
            return "";
        }
    }

    /** Called from the doStartTag()
     */
    public void otherDoStartTagOperations() throws JspException {

        super.otherDoStartTagOperations();


        JspWriter writer = pageContext.getOut();
        StringBuffer buf = new StringBuffer();

        buf.append(getNoCacheTag());
        buf.append(getBaseTag());
        buf.append( "<script type=\"text/javascript\" src=\"jaffa/js/panels/header.js\"></script>\n" );
        buf.append( "<script type=\"text/javascript\" src=\"jaffa/js/panels/errorpopup.js\"></script>\n" );
        buf.append( "<script type=\"text/javascript\" src=\"jaffa/js/rules/coreRules.js\"></script>\n" );
        buf.append( "<script type=\"text/javascript\" src=\"jaffa/js/rules/rulesValidator.js\"></script>\n" );
        buf.append( "<script type=\"text/javascript\" src=\"jaffa/js/rules/initializeRules.js\"></script>\n" );
        buf.append( "<script type=\"text/javascript\" src=\"jaffa/js/widgets/datetimePicker.js\"></script>\n" );
        buf.append( "<script type=\"text/javascript\" src=\"jaffa/js/widgets/dropdownManager.js\"></script>\n" );
        buf.append( "<script type=\"text/javascript\" src=\"jaffa/js/widgets/widgetCache.js\"></script>\n" );

        String firstDayOfWeek = (String) ContextManagerFactory.instance().getProperty("jaffa.date.firstDayOfWeek");
        if (firstDayOfWeek!=null){
          Pattern p = Pattern.compile( "[0-6]" );
          Matcher m = p.matcher(firstDayOfWeek);
          if (!m.matches()){
            firstDayOfWeek="0";
          }

          buf.append( "<script type=\"text/javascript\">"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.ColumnHeader.firstDayOfWeek",null))+" = "+ firstDayOfWeek + " </script>\n" );
        }

        if ( getErrorBoxInSameWindow() ) {
            buf.append("<script> var windowType = \"false\" </script>");
            pageContext.setAttribute(TagHelper.ATTRIBUTE_ERROR_BOX_IN_SAME_WINDOW, Boolean.TRUE, pageContext.REQUEST_SCOPE);
        } else {
            buf.append("<script> var windowType = \"true\" </script>");
            pageContext.setAttribute(TagHelper.ATTRIBUTE_ERROR_BOX_IN_SAME_WINDOW, Boolean.FALSE, pageContext.REQUEST_SCOPE);
        }

        buf.append(generateGetServerDateFunction());

        try {
            writer.println(buf.toString());
        } catch (IOException e) {
            String str = "Exception in writing the HeaderTag";
            log.error(str, e);
            throw new JspWriteRuntimeException(str, e);
        }
    }



    /** Getter for the attribute errorBoxInSameWindow.
     * @return Value of attribute errorBoxInSameWindow.
     */
    public boolean getErrorBoxInSameWindow() {
        return m_errorBoxInSameWindow;
    }

    /** Setter for the attribute errorBoxInSameWindow.
     * @param value New value of attribute errorBoxInSameWindow.
     */
    public void setErrorBoxInSameWindow(boolean value) {
        m_errorBoxInSameWindow = value;
    }

    /** Getter for the attribute noCache.
     * @return Value of attribute noCache.
     */
    public boolean getNoCache() {
        return m_noCache;
    }

    /** Setter for the attribute noCache.
     * @param value New value of attribute noCache.
     */
    public void setNoCache(boolean value) {
        m_noCache = value;
    }

    /** Invoked if a Throwable occurs while evaluating the BODY inside a tag or in any of the following methods: Tag.doStartTag(), Tag.doEndTag(), IterationTag.doAfterBody() and BodyTag.doInitBody().
     * This method is not invoked if the Throwable occurs during one of the setter methods.
     * This method will merely re-throw the input Throwable.
     * @param t The throwable exception navigating through this tag
     * @throws Throwable The throwable exception navigating through this tag.
     */
    public void doCatch(Throwable t) throws Throwable {
        throw t;
    }

    /** Invoked in all cases after doEndTag() for any class implementing Tag, IterationTag or BodyTag. This method is invoked even if an exception has occurred in the BODY of the tag, or in any of the following methods: Tag.doStartTag(), Tag.doEndTag(), IterationTag.doAfterBody() and BodyTag.doInitBody().
     * This method is not invoked if the Throwable occurs during one of the setter methods.
     * This will reset the internal fields, so that the Tag can be re-used.
     */
    public void doFinally() {
        super.doFinally();
        initTag();
    }

    /** This method generates the javascript function getServerDate(), which returns the date corresponding to the web-server.
     */
    public static String generateGetServerDateFunction() {
        StringBuffer buf = new StringBuffer();
        // If a property has been set to use ServerTime at the Javascript level the following function must be placed on to the page
        String serverTime = (String) ContextManagerFactory.instance().getProperty(RULE_SERVER_TIME);
        if(serverTime!=null && Parser.parseBoolean(serverTime).booleanValue()  ) {
            // Calculate the offset based on time zone and daylight savings
            org.jaffa.datatypes.DateTime dt = new org.jaffa.datatypes.DateTime();
            int serverOffset = dt.calendar().get(Calendar.ZONE_OFFSET) + dt.calendar().get(Calendar.DST_OFFSET);
            if(log.isDebugEnabled())
                log.debug("Using Server Time. Offset is " + serverOffset);
            //function getServerDate() {
            //  return new Date((new Date()).getTime()+((new Date()).getTimezoneOffset() * 60000)+{serverOffset});
            //}
            buf.append("<script language='javascript'>\n")
            .append("function getServerDate() {\n")
            .append("    return new Date((new Date()).getTime()+((new Date()).getTimezoneOffset() * 60000)+")
            .append(serverOffset)
            .append(");\n")
            .append("}\n</script>");
        }
        return buf.toString();
    }

}
