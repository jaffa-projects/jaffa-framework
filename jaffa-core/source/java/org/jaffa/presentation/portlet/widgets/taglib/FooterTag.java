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
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException;
import org.jaffa.util.MessageHelper;
import org.jaffa.util.StringHelper;


/** Tag Handler for the Footer tag.*/
public class FooterTag extends CustomTag {

    private static Logger log = Logger.getLogger(FooterTag.class);

    /** Default constructor.
     */
    public FooterTag() {
        super();
    }


    private String getErrorBoxHtml() {
        StringBuffer buf = new StringBuffer();

        buf.append("<div id=\"errorBox\"  style=\"display:none ;visibility:hidden; background: white; color: black; border: 10 solid #FA2341;  width:250px; height:120px; position:absolute; font-family: tahoma, arial, helvitica; font-size: 12px; margin: 0; visibility: visible; \">\n");
        buf.append("<h1 style=\"font-family: arial black; font-style: italic; margin-bottom: -15; margin-left: 10; color:#FA2341\">"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.ColumnHeader.Error",null))+"</h1>\n");
        buf.append("<table style=\"width: 100%;\" cellspacing=0 cellpadding=10>\n");
        buf.append("<tr><td height=\"25\"> </td></tr>\n");
        buf.append("<tr><td><table><tr>\n");
        buf.append("<td><p style=\"font-family: tahoma, arial, helvitica; font-size: 12px; margin-left: 10px; margin-right: 10px;\">"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.ColumnHeader.Message",null))+":"+"</p></td>\n");
        buf.append("<td><p style=\"font-family: tahoma, arial, helvitica; font-size: 12px; margin-left: 10px; margin-right: 10px;\" id=\"msg\"></p></td>\n");
        buf.append("</tr></table></td></tr>\n");
        buf.append("<tr><td align=\"RIGHT\"><a href=\"javascript:previous()\"><img src=\"jaffa/imgs/error/prev_up.gif\" id=\"previous\"  border=\"0\" ></a><a href=\"javascript:next()\"><img src=\"jaffa/imgs/error/next_up.gif\" border=\"0\" id=\"next\"   ></a><a href=\"javascript:okerror()\"><img src=\"jaffa/imgs/error/ok_up.gif\" border=\"0\"  onclick=\"document.all.errorBox.style.display = 'none' \" ></a></td></tr>\n");
        buf.append("</table>\n");
        buf.append("</div>\n");

        return buf.toString();
    }

    private String getKeyboardHtml() {
        StringBuffer buf = new StringBuffer();

        buf.append("<div id=\"LowerCaseKeyBoard\" style=\"position:absolute; top:0 ; left:0; width:545px; height:210px; z-index:4; visibility: hidden\">");
        buf.append("<img src=\"jaffa/imgs/keyboard/KB_lcase.gif\" width=\"200\" height=\"98\" usemap=\"#lowercase\" border=\"0\"><map name=\"lowercase\"><area shape=\"rect\" coords=\"9,9,20,23\" href=\"javascript:type('`');\"><area shape=\"rect\" coords=\"19,9,32,23\" href=\"javascript:type('1');\"><area shape=\"rect\" coords=\"33,9,45,23\" href=\"javascript:type('2');\"><area shape=\"rect\" coords=\"46,9,58,23\" href=\"javascript:type('3');\"><area shape=\"rect\" coords=\"57,10,69,22\" href=\"javascript:type('4');\"><area shape=\"rect\" coords=\"69,8,81,22\" href=\"javascript:type('5');\"><area shape=\"rect\" coords=\"81,10,93,24\" href=\"javascript:type('6');\"><area shape=\"rect\" coords=\"91,10,105,22\" href=\"javascript:type('7');\"><area shape=\"rect\" coords=\"106,8,118,21\" href=\"javascript:type('8');\"><area shape=\"rect\" coords=\"118,8,130,23\" href=\"javascript:type('9');\"><area shape=\"rect\" coords=\"131,9,140,24\" href=\"javascript:type('0');\"><area shape=\"rect\" coords=\"142,9,153,23\" href=\"javascript:type('-');\"><area shape=\"rect\" coords=\"153,9,166,21\" href=\"javascript:type('=');\"><area shape=\"rect\" coords=\"165,9,190,23\" href=\"javascript:backSpace();\"><area shape=\"rect\" coords=\"27,27,38,38\" href=\"javascript:type('q');\"><area shape=\"rect\" coords=\"39,25,50,40\" href=\"javascript:type('w');\"><area shape=\"rect\" coords=\"50,26,62,40\" href=\"javascript:type('e');\"><area shape=\"rect\" coords=\"62,26,74,39\" href=\"javascript:type('r');\"><area shape=\"rect\" coords=\"74,27,87,40\" href=\"javascript:type('t');\"><area shape=\"rect\" coords=\"87,26,100,42\" href=\"javascript:type('y');\"><area shape=\"rect\" coords=\"100,26,110,40\" href=\"javascript:type('u');\"><area shape=\"rect\" coords=\"110,26,122,39\" href=\"javascript:type('i');\"><area shape=\"rect\" coords=\"123,25,135,39\" href=\"javascript:type('o');\"><area shape=\"rect\" coords=\"136,28,149,39\" href=\"javascript:type('p');\"><area shape=\"rect\" coords=\"149,27,161,39\" href=\"javascript:type('[');\"><area shape=\"rect\" coords=\"160,26,172,38\" href=\"javascript:type(']');\"><area shape=\"rect\" coords=\"173,26,191,38\" href=\"javascript:type('\\');\"><area shape=\"rect\" coords=\"33,41,44,53\" href=\"javascript:type('a');\"><area shape=\"rect\" coords=\"45,41,57,54\" href=\"javascript:type('s');\"><area shape=\"rect\" coords=\"57,41,68,54\" href=\"javascript:type('d');\"><area shape=\"rect\" coords=\"68,40,78,54\" href=\"javascript:type('f');\"><area shape=\"rect\" coords=\"81,41,90,54\" href=\"javascript:type('g');\"><area shape=\"rect\" coords=\"92,42,105,55\" href=\"javascript:type('h');\"><area shape=\"rect\" coords=\"106,41,118,57\" href=\"javascript:type('j');\"><area shape=\"rect\" coords=\"118,41,128,56\" href=\"javascript:type('k');\"><area shape=\"rect\" coords=\"130,41,140,54\" href=\"javascript:type('l');\"><area shape=\"rect\" coords=\"141,41,153,54\" href=\"javascript:type(';');\"><area shape=\"rect\" coords=\"153,41,166,53\" href=\"javascript:type('\'');\"><area shape=\"rect\" coords=\"39,57,49,72\" href=\"javascript:type('z');\"><area shape=\"rect\" coords=\"49,59,62,72\" href=\"javascript:type('x');\"><area shape=\"rect\" coords=\"63,58,75,72\" href=\"javascript:type('c');\"><area shape=\"rect\" coords=\"76,58,86,72\" href=\"javascript:type('v');\"><area shape=\"rect\" coords=\"86,58,99,73\" href=\"javascript:type('b');\"><area shape=\"rect\" coords=\"99,58,112,74\" href=\"javascript:type('n');\"><area shape=\"rect\" coords=\"112,59,124,74\" href=\"javascript:type('m');\"><area shape=\"rect\" coords=\"124,59,136,72\" href=\"javascript:type(',');\"><area shape=\"rect\" coords=\"137,59,148,72\" href=\"javascript:type('.');\"><area shape=\"rect\" coords=\"148,58,159,71\" href=\"javascript:type('/');\"><area shape=\"rect\" coords=\"52,75,113,92\" href=\"javascript:type(' ');\"><area shape=\"rect\" coords=\"8,75,34,93\" href=\"javascript:clear();\"><area shape=\"rect\" coords=\"165,75,189,90\" href=\"javascript:hideKeyboard();\"><area shape=\"rect\" coords=\"167,42,190,56\" href=\"javascript:type('\n');\"><area shape=\"rect\" coords=\"7,58,38,72\" href=\"javascript:showUpper();\"><area shape=\"rect\" coords=\"161,58,189,72\" href=\"javascript:showUpper();\"></map></div>\n");
        buf.append("<div id=\"UpperCaseKeyBoard\" style=\"position:absolute;  top:0 ; left:0; width:545px; height:210px; z-index:5; visibility: hidden\">\n");
        buf.append("<img src=\"jaffa/imgs/keyboard/KB_caps.gif\" width=\"200\" height=\"98\" usemap=\"#uppercase\" border=\"0\"><map name=\"uppercase\"><area shape=\"rect\" coords=\"9,9,20,23\" href=\"javascript:type('%7E');\"><area shape=\"rect\" coords=\"19,9,32,23\" href=\"javascript:type('!');\"><area shape=\"rect\" coords=\"33,9,45,23\" href=\"javascript:type('@');\"><area shape=\"rect\" coords=\"46,9,58,23\" href=\"javascript:type('#');\"><area shape=\"rect\" coords=\"57,10,69,22\" href=\"javascript:type('$');\"><area shape=\"rect\" coords=\"69,8,81,22\" href=\"javascript:type('%');\"><area shape=\"rect\" coords=\"81,10,93,24\" href=\"javascript:type('^');\"><area shape=\"rect\" coords=\"91,10,105,22\" href=\"javascript:type('&amp;');\"><area shape=\"rect\" coords=\"106,8,118,21\" href=\"javascript:type('*');\"><area shape=\"rect\" coords=\"118,8,130,23\" href=\"javascript:type('(');\"><area shape=\"rect\" coords=\"131,9,140,24\" href=\"javascript:type(')');\"><area shape=\"rect\" coords=\"142,9,153,23\" href=\"javascript:type('_');\"><area shape=\"rect\" coords=\"153,9,166,21\" href=\"javascript:type('+');\"><area shape=\"rect\" coords=\"165,9,190,23\" href=\"javascript:backSpace();\"><area shape=\"rect\" coords=\"27,27,38,38\" href=\"javascript:type('Q');\"><area shape=\"rect\" coords=\"39,25,50,40\" href=\"javascript:type('W');\"><area shape=\"rect\" coords=\"50,26,62,40\" href=\"javascript:type('E');\"><area shape=\"rect\" coords=\"62,26,74,39\" href=\"javascript:type('R');\"><area shape=\"rect\" coords=\"74,27,87,40\" href=\"javascript:type('T');\"><area shape=\"rect\" coords=\"87,26,100,42\" href=\"javascript:type('Y');\"><area shape=\"rect\" coords=\"100,26,110,40\" href=\"javascript:type('U');\"><area shape=\"rect\" coords=\"110,26,122,39\" href=\"javascript:type('I');\"><area shape=\"rect\" coords=\"123,25,135,39\" href=\"javascript:type('O');\"><area shape=\"rect\" coords=\"136,28,149,39\" href=\"javascript:type('P');\"><area shape=\"rect\" coords=\"149,27,161,39\" href=\"javascript:type('{');\"><area shape=\"rect\" coords=\"160,26,172,38\" href=\"javascript:type('}');\"><area shape=\"rect\" coords=\"173,26,191,38\" href=\"javascript:type('|');\"><area shape=\"rect\" coords=\"33,41,44,53\" href=\"javascript:type('A');\"><area shape=\"rect\" coords=\"45,41,57,54\" href=\"javascript:type('S');\"><area shape=\"rect\" coords=\"57,41,68,54\" href=\"javascript:type('D');\"><area shape=\"rect\" coords=\"68,40,78,54\" href=\"javascript:type('F');\"><area shape=\"rect\" coords=\"81,41,90,54\" href=\"javascript:type('G');\"><area shape=\"rect\" coords=\"92,42,105,55\" href=\"javascript:type('H');\"><area shape=\"rect\" coords=\"106,41,118,57\" href=\"javascript:type('J');\"><area shape=\"rect\" coords=\"118,41,128,56\" href=\"javascript:type('K');\"><area shape=\"rect\" coords=\"130,41,140,54\" href=\"javascript:type('L');\"><area shape=\"rect\" coords=\"141,41,153,54\" href=\"javascript:type(':');\"><area shape=\"rect\" coords=\"153,41,166,53\" href=\"javascript:type('&quot;');\"><area shape=\"rect\" coords=\"39,57,49,72\" href=\"javascript:type('Z');\"><area shape=\"rect\" coords=\"49,59,62,72\" href=\"javascript:type('X');\"><area shape=\"rect\" coords=\"63,58,75,72\" href=\"javascript:type('C');\"><area shape=\"rect\" coords=\"76,58,86,72\" href=\"javascript:type('V');\"><area shape=\"rect\" coords=\"86,58,99,73\" href=\"javascript:type('B');\"><area shape=\"rect\" coords=\"99,58,112,74\" href=\"javascript:type('N');\"><area shape=\"rect\" coords=\"112,59,124,74\" href=\"javascript:type('M');\"><area shape=\"rect\" coords=\"124,59,136,72\" href=\"javascript:type('&lt;');\"><area shape=\"rect\" coords=\"137,59,148,72\" href=\"javascript:type('&gt;');\"><area shape=\"rect\" coords=\"148,58,159,71\" href=\"javascript:type('?');\"><area shape=\"rect\" coords=\"52,75,113,92\" href=\"javascript:type(' ');\"><area shape=\"rect\" coords=\"8,75,34,93\" href=\"javascript:clear();\"><area shape=\"rect\" coords=\"165,75,189,90\" href=\"javascript:hideKeyboard();\"><area shape=\"rect\" coords=\"167,42,190,56\" href=\"javascript:type('\n');\"><area shape=\"rect\" coords=\"7,58,38,72\" href=\"javascript:showLower();\"><area shape=\"rect\" coords=\"161,58,189,72\" href=\"javascript:showLower();\"></map></div>\n");

        return buf.toString();
    }

    private String getKeypadHtml() {
        StringBuffer buf = new StringBuffer();

        buf.append("<div id=\"KeyPad\" style=\"position:absolute;  top:0 ; left:0; width:100px; height:100px; z-index:5; visibility: hidden\">\n");
        buf.append("<img src=\"jaffa/imgs/keyboard/calc.gif\" width=\"100\" height=\"100\" border=\"0\" usemap=\"#Map\"><map name=\"Map\"><area shape=\"circle\" coords=\"18,20,9\" href=\"javascript:type('7');\"><area shape=\"circle\" coords=\"39,20,9\" href=\"javascript:type('8');\"><area shape=\"circle\" coords=\"60,20,9\" href=\"javascript:type('9');\"><area shape=\"circle\" coords=\"81,20,10\" href=\"javascript:clear();\"><area shape=\"circle\" coords=\"18,41,8\" href=\"javascript:type('4');\"><area shape=\"circle\" coords=\"40,41,9\" href=\"javascript:type('5');\"><area shape=\"circle\" coords=\"59,41,9\" href=\"javascript:type('6');\"><area shape=\"circle\" coords=\"18,59,8\" href=\"javascript:type('1');\"><area shape=\"circle\" coords=\"39,60,9\" href=\"javascript:type('2');\"><area shape=\"circle\" coords=\"59,60,9\" href=\"javascript:type('3');\"><area shape=\"circle\" coords=\"18,78,8\" href=\"javascript:type('.');\"><area shape=\"circle\" coords=\"39,78,8\" href=\"javascript:type('0');\"><area shape=\"rect\" coords=\"73,52,88,88\" href=\"javascript:hideKeyPad();\"><area shape=\"circle\" coords=\"59,78,8\" href=\"javascript:sign();\"><area shape=\"circle\" coords=\"79,41,7\" href=\"javascript:backSpace();\"></map>\n");
        buf.append("</div>\n");

        return buf.toString();
    }

     /** Called from the doStartTag()
     */
    public void otherDoStartTagOperations() throws JspException {

        super.otherDoStartTagOperations();

        JspWriter writer = pageContext.getOut();
        StringBuffer buf = new StringBuffer();
        Boolean bool = null;

        buf.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/panels/footer.js\"></SCRIPT>\n");

        // Generate the html for the error-popup if required
        bool = (Boolean) pageContext.findAttribute(TagHelper.ATTRIBUTE_ERROR_BOX_IN_SAME_WINDOW);
        if (bool != null && bool.booleanValue() )
            buf.append( getErrorBoxHtml() );

        // Generate the html for the keyboard if required
        bool = (Boolean) pageContext.findAttribute(TagHelper.ATTRIBUTE_KEYBOARD_IN_USE);
        if (bool != null && bool.booleanValue() )
            buf.append( getKeyboardHtml() );

        // Generate the html for the keypad if required
        bool = (Boolean) pageContext.findAttribute(TagHelper.ATTRIBUTE_KEYPAD_IN_USE);
        if (bool != null && bool.booleanValue() )
            buf.append( getKeyboardHtml() );

        try {
            writer.println(buf.toString());
        } catch (IOException e) {
            String str = "Exception in writing the FooterTag";
            log.error(str, e);
            throw new JspWriteRuntimeException(str, e);
        }

    }


    /** Invoked in all cases after doEndTag() for any class implementing Tag, IterationTag or BodyTag. This method is invoked even if an exception has occurred in the BODY of the tag, or in any of the following methods: Tag.doStartTag(), Tag.doEndTag(), IterationTag.doAfterBody() and BodyTag.doInitBody().
     * This method is not invoked if the Throwable occurs during one of the setter methods.
     * This will reset the attributes of the PageContext.
     */
    public void doFinally() {
        super.doFinally();
        pageContext.removeAttribute(TagHelper.ATTRIBUTE_ERROR_BOX_IN_SAME_WINDOW);
        pageContext.removeAttribute(TagHelper.ATTRIBUTE_KEYBOARD_IN_USE);
        pageContext.removeAttribute(TagHelper.ATTRIBUTE_KEYPAD_IN_USE);
    }
}
