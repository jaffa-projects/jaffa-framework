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

package org.jaffa.security.taglib;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.ServletRequest;
import java.io.PrintWriter;
import java.io.IOException;
import org.jaffa.security.SecurityTag;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/** FunctionGuardTag
 *
 * Guard used to filter content based on business function access
 */
public class FunctionGuardTag extends BodyTagSupport {

    private static Logger log = Logger.getLogger(FunctionGuardTag.class);

    /** property declaration for tag attribute: name.
     */
    private String m_name;

    /** property declaration for tag attribute: hasAccess.
     */
    private boolean m_hasAccess = true;

    /** Default Constructor of Tag
     */
    public FunctionGuardTag() {
        super();
    }


    ////////////////////////////////////////////////////////////////
    ///                                                          ///
    ///   User methods.                                          ///
    ///                                                          ///
    ///   Modify these methods to customize your tag handler.    ///
    ///                                                          ///
    ////////////////////////////////////////////////////////////////


    //
    // methods called from doStartTag()
    //
    /**
     *
     * Fill in this method to perform other operations from doStartTag().
     *
     */
    public void otherDoStartTagOperations()  {
    }

    /**
     *
     * Fill in this method to determine if the tag body should be evaluated
     * Called from doStartTag().
     *
     * @return true, if body should be evaluated
     */
    public boolean theBodyShouldBeEvaluated()  {
        if(! (pageContext.getRequest() instanceof HttpServletRequest) ) {
            log.error("Tag only supports HTTP requests, Tag Aborted with Paranoid Security!");
            return false;
        }

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        if(SecurityTag.hasFunctionAccess(request, m_name))
            return m_hasAccess;
        else
            return !m_hasAccess;
    }


    //
    // methods called from doEndTag()
    //
    /**
     *
     * Fill in this method to perform other operations from doEndTag().
     *
     */
    public void otherDoEndTagOperations()  {
    }

    /**
     *
     * Fill in this method to determine if the rest of the JSP page
     * should be generated after this tag is finished.
     * Called from doEndTag().
     *
     * @return true, if rest of page should be processed
     */
    public boolean shouldEvaluateRestOfPageAfterEndTag()  {
        return true;
    }


    /**
     * methods called from doAfterBody()
     *
     * @return true, if the body need evealuating again
     */
    public boolean theBodyShouldBeEvaluatedAgain()  {
        return false;
    }


    ////////////////////////////////////////////////////////////////
    ///                                                          ///
    ///   Tag Handler interface methods.                         ///
    ///                                                          ///
    ///   Do not modify these methods; instead, modify the       ///
    ///   methods that they call.                                ///
    ///                                                          ///
    ////////////////////////////////////////////////////////////////


    /** .//GEN-BEGIN:doStartTag
     *
     * This method is called when the JSP engine encounters the start tag,
     * after the attributes are processed.
     * Scripting variables (if any) have their values set here.
     * @return EVAL_BODY_BUFFERED if the JSP engine should evaluate the tag body, otherwise return SKIP_BODY.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     * @throws JspException
     * @throws JspException  */
    public int doStartTag() throws JspException, JspException {
        otherDoStartTagOperations();

        if (theBodyShouldBeEvaluated()) {
            return EVAL_BODY_BUFFERED;
        } else {
            return SKIP_BODY;
        }
    }//GEN-END:doStartTag

    /** .//GEN-BEGIN:doEndTag
     *
     *
     * This method is called after the JSP engine finished processing the tag.
     * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP page, otherwise return SKIP_PAGE.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     * @throws JspException
     * @throws JspException  */
    public int doEndTag() throws JspException, JspException {
        otherDoEndTagOperations();

        if (shouldEvaluateRestOfPageAfterEndTag()) {
            return EVAL_PAGE;
        } else {
            return SKIP_PAGE;
        }
    }//GEN-END:doEndTag

    /** .//GEN-BEGIN:doAfterbody
     *
     *
     * This method is called after the JSP engine processes the body content of the tag.
     * @return EVAL_BODY_AGAIN if the JSP engine should evaluate the tag body again, otherwise return SKIP_BODY.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     * @throws JspException
     * @throws JspException  */
    public int doAfterBody() throws JspException, JspException {
        try {
            //
            // This code is generated for tags whose bodyContent is "JSP"
            //
            JspWriter out = getPreviousOut();
            BodyContent bodyContent = getBodyContent();

            writeTagBodyContent(out, bodyContent);
        } catch (Exception ex) {
            throw new JspException("error in FunctionGuardTag: " + ex);
        }

        if (theBodyShouldBeEvaluatedAgain()) {
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }//GEN-END:doAfterbody

    /** get business function name
     * @return  function name
     */
    public String getName() {
        return m_name;
    }

    /** set business function name
     * @param value function name
     */
    public void setName(String value) {
        m_name = value;
    }

    /** is this tag testing for access to function?
     * @return true, if test is for access, false for no access
     */
    public boolean getHasAccess() {
        return m_hasAccess;
    }

    /** Set if this tag testing for access to function?
     * @param value true, if test is for access, false for no access
     */
    public void setHasAccess(boolean value) {
        m_hasAccess = value;
    }

    /**
     * Fill in this method to process the body content of the tag.
     * You only need to do this if the tag's BodyContent property
     * is set to "JSP" or "tagdependent."
     * If the tag's bodyContent is set to "empty," then this method
     * will not be called.
     * @param out wirter for context
     * @param bodyContent current body content
     * @throws IOException  if any unpredicted IO errors occur
     */
    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        //
        // write the body content (after processing by the JSP engine) on the output Writer
        //
        bodyContent.writeOut(out);

        // clear the body content for the next time through.
        bodyContent.clearBody();
    }

}
