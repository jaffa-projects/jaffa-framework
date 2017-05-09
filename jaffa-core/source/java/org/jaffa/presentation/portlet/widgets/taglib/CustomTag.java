/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2005 JAFFA Development Group
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
import java.util.Stack;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TryCatchFinally;
import org.apache.log4j.Logger;
import org.jaffa.util.StringHelper;

/**
 * Extends BodyTagSupport and is inteneded to represent custom tag library
 * classes that do not contain any model data. This class extends the ICustomTag
 * interface (getParentCustomTag(), public void setParentCustomTag(Tag customParent),
 * and public void register(ICustomTag tag)).
 *
 * @author Mark Watson
 */
public abstract class CustomTag extends BodyTagSupport implements ICustomTag, TryCatchFinally {
    
    /** Set up Logging for Log4J */
    private static Logger log = Logger.getLogger(CustomTag.class);
    
    protected static final String STACK_ATTR = "org.jaffa.presentation.portlet.widgets.taglib_component_stack";
    
    protected String NAME = StringHelper.getShortClassName(this.getClass());
    
    public CustomTag() {
        if(log.isDebugEnabled())
            log.debug(this.NAME + "()");
    }
    
    /**
     * Required by the ICustomTag interface.
     * @param parent
     */
    public void setParentTag(Tag parent) {
        this.parent = parent;
    }
    
    /**
     * Required by the ICustomTag interface.
     */
    public Tag getParentTag() {
        if (parent != null) return parent;
        return customParent;
    }
    
    /** Return any HTML that this widget needed to be inserted once at the start of the form,
     *  before any of the widgets are rendered.
     *
     *  Currently not supported by the FormTag
     */
    public String getHeaderHtml() {
        return null;
    }
    
    /** Return any HTML that this widget needed to be inserted once at the end of the form,
     *  after all of the widgets have been rendered
     */
    public String getFooterHtml() {
        return null;
    }
    
    /**
     * 1. Use data in the page context to set Jaffa parent tag
     * 2. Save myself in the containing form widget for the use when
     *    nested Jaffa tags call this method
     * @throws JspException
     */
    public void otherDoStartTagOperations() throws JspException {
        if (log.isDebugEnabled())
            log.debug(this.NAME + ".otherDoStartTagOperations:  this="+this);
        
        if(IFormTag.class.isInstance(this)) {
            /*
            NOTE: The order is very important here. The parent should be stamped first on this Tag, and only then should this tag try to register itself with the Parent.
            For example: If we've Grid -> Property -> GridColumn heirarchy, with the GridColumn designed to get its label from the outer Property, then
            1- We first stamp the parent on the GridColumn
            2- GridColumn would then register itself with the Property, and then with the Grid
            3- The register() method of Grid would then invoke the getLabel() method of GridColumn
            4- The getLabel() of GridColumn, if its value is null, would look for the outer Property tag. It'll get the outer Property only if step#1 was done
             */
            CustomTag.pushParent(this,pageContext);
            doRegister();
        }
    }
    
    /**
     * Get the parent component and call register with the this tag as the argument.
     *
     */
    protected void doRegister() {
        try {
            ICustomTag parent = getParentCustomTag();
            if (parent != null) {
                parent.register(this);
            } else
                log.error(this.NAME + ".doRegister: could not find parent tag");
        } catch (Exception e) {
            log.error(this.NAME + ".doRegister: " + e, e);
        }
    }
    
    /**
     * Get the form tag from the page context and add this tag
     * to the nested component stack maintaing in the FormTag class.
     *
     * This has package static access for the FormTag that can't extent CustomTag
     *
     */
    static void pushParent(ICustomTag thisTag, PageContext pageContext) {
        try {
            // Get stack (never returns null!)
            Stack stack = CustomTag.getNestedComponentStack(pageContext);
            if (stack.empty()) {
                if (log.isDebugEnabled())
                    log.debug("CustomTag.pushParent: stack is EMPTY  stack=" + stack + ", this= " + thisTag);
                thisTag.setParentCustomTag(null);
            } else {
                thisTag.setParentCustomTag((ICustomTag)stack.peek());
            }
            if (log.isDebugEnabled()) {
                log.debug("CustomTag.pushParent: adding " + thisTag + " (parentTag = " + thisTag.getParentCustomTag() + ")");
                log.debug("CustomTag.pushParent: stack size: " + stack.size());
            }
            // Now add this tag to the stack
            stack.push(thisTag);
        } catch (Exception ee) {
            log.error("Error in CustomTag.pushParent: " + ee, ee);
        }
        
    }
    
    /**
     * Pop this tag from the top of the stack on exit. Validate that this is the top
     * of the stack as well.
     *
     * This has package static access for the FormTag that can't extent CustomTag
     *
     * @return top ICustomTag from the nested component stack
     */
    static ICustomTag popParent(ICustomTag thisTag, PageContext pageContext) {
        try {
            Stack stack = CustomTag.getNestedComponentStack(pageContext);
            if (stack == null) {
                log.fatal("CustomTag.popParent: Stack Was NULL, expected " + thisTag);
                return null;
            }
            ICustomTag me = (ICustomTag) stack.pop();
            if (log.isDebugEnabled())
                log.debug("CustomTag.popParent: parent from top of stack pageContext stack: " + thisTag.getParent());
            if(!thisTag.equals(me))
                log.fatal("CustomTag.popParent: Top of Stack Was " + me + ", expected " + thisTag);
            return me;
        } catch (Exception ee) {
            log.error("Error in CustomTag.popParent: " + ee);
        }
        return null;
    }
    
    /**
     * Required by the ICustomTag interface
     * @return custom tag containing this tag
     */
    public ICustomTag getParentCustomTag() {
        return customParent;
    }
    
    /**
     * Required by the ICustomTag interface
     * Set the custom tag that contains this tag
     */
    public void setParentCustomTag(ICustomTag jParent) {
        if (jParent!=null)
            customParent = jParent;
    }
    
    /**
     * Utility to return a stack containing nested parent components from the page context.
     *
     * NOTE: this was made static to use in FormTag. Before this refactoring, there was nearly
     *       identical code in two places (violating DRY)
     *
     * @return Returns the current stack of ICustomTag tags that this one is nested in.
     *         If there are no outer tags, an empty stack is returned
     */
    public static Stack getNestedComponentStack(PageContext pageContext) {
        Stack cstack = (Stack)pageContext.getRequest().getAttribute(STACK_ATTR);
        if (log.isDebugEnabled())
            log.debug("CustomTag.getNestedComponentStack(): cstack="+cstack);
        if (cstack == null) {
            cstack = new Stack();
            pageContext.getRequest().setAttribute(STACK_ATTR, cstack);
        }
        return cstack;
    }
    
    /**
     * The default method is defined in the TagSupport class (base class of
     * BodyTagSupport) and is called by the JSP container.
     *
     * Note: the default setParent() method does nothing.
     *
     * @param parent
     */
    public void setParent(final Tag parent) {
        this.parent=parent;
    }
    
    /**
     * The default method is defined in the TagSupport class (base class of
     * BodyTagSupport) and is called by the JSP container.
     *
     * Note: the default getParent() method does nothing.
     *
     * @return Tag
     */
    public Tag getParent() {
        return parent;
    }
    
    
    /**
     * This register() method is a call back from the doOtherStartOperations() of the
     * child widgets, so this parent can do anthing special based on an inner widget.
     * The default implementation is for this tag to pass the registration up the chain
     * to its parent, so every tag can react to this new widget. Typically the FormTag
     * using this to know what special code to add to the page based on the widgets
     * painted on the JSP.
     *
     * @param tag
     */
    public void register(ICustomTag tag) {
        if (log.isDebugEnabled())
            log.debug(this.NAME + ".register: " + tag + " is registering with " + this);
        if (getParentCustomTag() != null)
            getParentCustomTag().register(tag);
    }
    
    
    /** This method is called just once, before the body is evaluated.
     * It sets the initial context.
     * @throws JspException if any error occurs.
     */
    public void doInitBody() throws JspException {
        if (log.isDebugEnabled())
            log.debug(this.NAME+".doInitBody: START. pageContext="+pageContext+ ", BodyContent="+getBodyContent() );
    }
    
    /** .
     * This method is called after the JSP engine processes the body content of the tag.
     * @return EVAL_BODY_AGAIN if the JSP engine should evaluate the tag body again, otherwise return SKIP_BODY.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     */
    public int doAfterBody() throws JspException {
        if (log.isDebugEnabled())
            log.debug(this.NAME+".doAfterBody: START. pageContext="+pageContext+ ", BodyContent="+getBodyContent() );
        
        if(IBodyTag.class.isInstance(this)) {
            // We should output the body content
            BodyContent bodyContent = getBodyContent();
            
            if (log.isDebugEnabled())
                log.debug(this.NAME+".doAfterBody: Got Body Size = " +
                        (bodyContent.getString() == null ? "null" : ""+bodyContent.getString().length()));
            try {
                
                JspWriter out = getPreviousOut();
                writeTagBodyContent(out, bodyContent);
            } catch (IOException ex) {
                log.error(this.NAME + ".doAfterBody(): Error on "+this, ex);
                throw new JspException("Error in " + this.NAME + ".doAfterBody() on "+this);
            }
        }
        
        if (theBodyShouldBeEvaluatedAgain()) {
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
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
        // Do Nothing
    }
    
    /** Invoked by the doEndTag, if overridden use a call the this base version with 'super'
     */
    public void otherDoEndTagOperations() throws JspException {
        // Do Nothing
    }
    
    /**
     * Fill in this method to process the body content of the tag.
     * You only need to do this if the tag's BodyContent property
     * is set to "JSP" or "tagdependent."
     * If the tag's bodyContent is set to "empty," then this method
     * will not be called.
     */
    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        // write the body content (after processing by the JSP engine) on the output Writer
        bodyContent.writeOut(out);
        // clear the body content for the next time through.
        bodyContent.clearBody();
    }
    
    
    /**
     *  Determines whether the body should be evaluated.
     *  By default this is true. Override and return false if this tag has no body content to process
     * @return true
     */
    public boolean theBodyShouldBeEvaluated()  {
        return true;
    }
    
    /**
     *  Determines whether the tag body should be evaluated
     *  By default this is false. Override and return true if your tag should iterate
     * @return false
     */
    public boolean theBodyShouldBeEvaluatedAgain() throws JspException {
        return false;
    }
    
    /**
     *  Determine whether the rest of the page
     *  should be evaluated after the tag is processed.
     *  By default this is true
     *
     * @return true
     */
    public boolean shouldEvaluateRestOfPageAfterEndTag()  {
        return true;
    }
    
    /**
     * This method is called when the JSP engine encounters the start tag,
     * after the attributes are processed.
     * Scripting variables (if any) have their values set here.
     * @return EVAL_BODY_TAG if the JSP engine should evaluate the tag body, otherwise return SKIP_BODY.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     *
     */
    public int doStartTag() throws JspException {
        if(log.isDebugEnabled())
            log.debug(this.NAME + ".doStartTag() - From CustomTag.doStartTag()");
        otherDoStartTagOperations();
        if (theBodyShouldBeEvaluated()) {
            if(IBodyTag.class.isInstance(this))
                // See if we are evaluating the body
                return EVAL_BODY_BUFFERED;
            else
                return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
    
    /**
     * This method is called after the JSP engine finished processing the tag.
     * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP page, otherwise return SKIP_PAGE.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     *
     */
    public int doEndTag() throws JspException {
        try {
            otherDoEndTagOperations();
            if (shouldEvaluateRestOfPageAfterEndTag()) {
                return EVAL_PAGE;
            } else {
                return SKIP_PAGE;
            }
        } finally {
            if(IFormTag.class.isInstance(this)) {
                // Remove from stack
                CustomTag.popParent(this,pageContext);
            }
        }
    }
    
    private Tag parent;
    private ICustomTag customParent = null;
    
    public String toString() {
        return (new StringBuffer("<j:"))
        .append(!this.NAME.endsWith("Tag") ? this.NAME : this.NAME.substring(0,this.NAME.length()-3))
        .append(" hash='")
        .append(hashCode())
        .append("'>")
        .toString();
    }
    
    /** This is a convenience method to return the nearest ancestor that implements the interface or is an instance of the class specified.
     * @param from The instance from where to start looking.
     * @param klass The subclass of ICustomTag or interface to be matched.
     * @return the nearest ancestor that implements the interface or is an instance of the class specified.
     */
    public static ICustomTag findCustomTagAncestorWithClass(ICustomTag from, Class klass) {
        ICustomTag parent = from.getParentCustomTag();
        while (parent != null) {
            if (klass.isInstance(parent)) {
                return parent;
            }
            parent = parent.getParentCustomTag();
        }
        return null;
    }
    
}
