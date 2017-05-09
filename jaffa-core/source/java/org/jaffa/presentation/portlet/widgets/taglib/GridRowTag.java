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
 * 1. Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3. The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4. Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5. Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterGridTagMissingRuntimeException;


/** Tag Handler for the GridRow widget.*/
public class GridRowTag extends CustomTag implements IFormTag, IBodyTag {
    
    private static Logger log = Logger.getLogger(GridRowTag.class);
    private static final String TAG_NAME = "GridRowTag";
    
    //----------------------------------------------
    //-- TLD Attributes
    /** property declaration for tag attribute: rowCssClass.*/
    private String m_rowCssClass;
    /** property declaration for tag attribute: style.*/
    private String m_style;
    //----------------------------------------------
    
    private Properties attributes = new Properties();
    
    /** Default constructor. */
    public GridRowTag() {
        super();
        initTag();
    }
    
    private void initTag() {
        m_rowCssClass = null;
        m_style = null;
        attributes = new Properties();
    }
    
    /** Adds a ColumnHead to the outer GridTag. */
    public void otherDoStartTagOperations() throws JspException {
        
        // This will register this row with the grid.
        super.otherDoStartTagOperations();
        
        // GridTag should be present
        GridTag gridTag = (GridTag) findCustomTagAncestorWithClass(this, GridTag.class);
        if (gridTag == null) {
            String str = "The " + TAG_NAME + " should be inside a GridTag";
            log.error(str);
            throw new OuterGridTagMissingRuntimeException(str);
        }
    }
    
    /** Returns a false
     * @return false
     */
    public boolean theBodyShouldBeEvaluated()  {
        return true;
    }
    
    public int doAfterBody() throws JspException {
        log.debug("entering doafterbody method.");
        if (getBodyContent() != null) {
            try {
                attributes.load(new ByteArrayInputStream(getBodyContent().getString().getBytes()));
                // remove possible duplicated attributes
                for (Iterator i=attributes.keySet().iterator(); i.hasNext(); ) {
                    String k = (String) i.next();
                    if (k.equalsIgnoreCase("style")) 
                        attributes.remove(k);
                    else if (k.equalsIgnoreCase("rowCssClass")) 
                        attributes.remove(k);
                }
                log.debug("grid row content = "+attributes.toString());
            } catch (IOException ex) {
                if (log.isDebugEnabled()) {
                    log.debug("loading GridRowTag body failed.");
                    ex.printStackTrace();
                }
            }
        }
        return SKIP_BODY;
    }
    
    /** Invoked in all cases after doEndTag() for any class implementing Tag, IterationTag or BodyTag. This method is invoked even if an exception has occurred in the BODY of the tag, or in any of the following methods: Tag.doStartTag(), Tag.doEndTag(), IterationTag.doAfterBody() and BodyTag.doInitBody().
     * This method is not invoked if the Throwable occurs during one of the setter methods.
     * This will reset the internal fields, so that the Tag can be re-used.
     */
    public void doFinally() {
        super.doFinally();
        initTag();
    }
    
    //---------------------------------------------------
    //-- Start: TLD Attributes
    //---------------------------------------------------
    
    
    /** Setter for the attribute rowCssClass.
     * @param value New value of attribute rowCssClass.
     */
    public void setRowCssClass(String value) {
        if(value!=null && value.trim().length()==0)
            value=null;
        m_rowCssClass = value;
    }
    
    /** Getter for the attribute rowCssClass.
     * @return Value of attribute rowCssClass.
     */
    public String getRowCssClass() {
        return m_rowCssClass;
    }
    
    /** Setter for the attribute style.
     * @param value New value of attribute style.
     */
    public void setStyle(String value) {
        if(value!=null && value.trim().length()==0)
            value=null;
        m_style = value;
    }
    
    /** Getter for the attribute style.
     * @return Value of attribute style.
     */
    public String getStyle() {
        return m_style;
    }
    
    /** Getter for all other attributes.
     * @return a list of attribute name=value pairs in a string
     */
    public Properties getAttributes() {
      return this.attributes;
    }
    //---------------------------------------------------
    
}
