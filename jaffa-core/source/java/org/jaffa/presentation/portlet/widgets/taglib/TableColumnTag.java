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
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterTableTagMissingRuntimeException;
import org.jaffa.util.MessageHelper;

/** Tag Handler for the TableColumn widget.*/
public class TableColumnTag extends CustomTag implements IFormTag {
    
    private static Logger log = Logger.getLogger(TableColumnTag.class);
    private static final String TAG_NAME = "TableColumn";
    
    
    /** property declaration for tag attribute: column.
     */
    private String m_column;
    
    /** property declaration for tag attribute: title.
     */
    private String m_title;
    
    /** property declaration for tag attribute: width.
     */
    private String m_width;
    
    /** This variable contains the parent (enclosing) tag. */
    private TableTag m_tableTag = null;
    
    /** The value for this field will be computed based on m_title */
    private String m_labelEditorLink;
    
    /** Default constructor.
     */
    public TableColumnTag() {
        super();
        initTag();
    }
    
    private void initTag() {
        m_column = null;
        m_title = null;
        m_width = null;
        m_tableTag = null;
        m_labelEditorLink = "";
    }
    
    
    /** This adds a column to the outer TableTag.
     * Called from the doStartTag()
     */
    public void otherDoStartTagOperations() throws JspException {
        
        super.otherDoStartTagOperations();
        if (m_tableTag == null) {
            m_tableTag = (TableTag) findCustomTagAncestorWithClass(this, TableTag.class);
        }
        
        // TableTag should be present
        if (m_tableTag == null) {
            String str = "The " + TAG_NAME + " for column " + getColumn() + " should be inside a TableTag";
            log.error(str);
            throw new OuterTableTagMissingRuntimeException(str);
        }
        
        // just add the column to the TableTag
        m_tableTag.addColumn(m_column, m_title, m_width, m_labelEditorLink);
    }
    
    
    /** Getter for the attribute column.
     * @return Value of attribute column.
     */
    public String getColumn() {
        return m_column;
    }
    
    /** Setter for the attribute column.
     * @param value New value of attribute column.
     */
    public void setColumn(String value) {
        m_column = value;
    }
    
    /** Getter for the attribute title.
     * @return Value of attribute title.
     */
    public String getTitle() {
        return m_title;
    }
    
    /** Setter for the attribute title.
     * @param value New value of attribute title.
     */
    public void setTitle(String value) {
        if (value != null) {
            m_title = MessageHelper.replaceTokens(pageContext, value);
            if (MessageHelper.hasTokens(value))
                m_labelEditorLink = TagHelper.getLabelEditorLink(pageContext, value);
        } else
            m_title = value;
    }
    
    /** Getter for the attribute width.
     * @return Value of attribute width.
     */
    public String getWidth() {
        return m_width;
    }
    
    /** Setter for the attribute width.
     * @param value New value of attribute width.
     */
    public void setWidth(String value) {
        m_width = value;
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
