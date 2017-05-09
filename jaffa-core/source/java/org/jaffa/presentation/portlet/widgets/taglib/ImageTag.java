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
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.*;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.widgets.model.ImageModel;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterFormTagMissingRuntimeException;

/** Tag Handler for the Image widget.*/
public class ImageTag extends CustomModelTag implements IWidgetTag,IFormTag {

    private static Logger log = Logger.getLogger(ImageTag.class);
    private static final String TAG_NAME = "ImageTag";

    private static final String IMAGE_SERVER_URL = "jaffa/getImage";
    private static final String SESSION_COOKIE_NAME = "jsessionid";

    /** The prefix used for the session id.*/
    public static final String SESSION_ID_PREFIX_NAME = "idPrefix";


    /** property declaration for tag attribute: defImage.
     */
    private String m_defImage;


    /** Default constructor.
     */
    public ImageTag() {
        super();
        initTag();
    }

    private void initTag() {
        m_field = null;
        m_defImage = null;
    }




    /** Called from the doStartTag()
     */
    public void otherDoStartTagOperations() throws JspException {

        super.otherDoStartTagOperations();

        // Get the formName
        String formName = TagHelper.getFormTag(pageContext).getHtmlName();
        if(formName == null) {
            String str = "The " + TAG_NAME + " should be inside a FormTag";
            log.error(str);
            throw new OuterFormTagMissingRuntimeException(str);
        }


        // Get the model
        ImageModel model = null;
        try {
        	model = (ImageModel) TagHelper.getModel(pageContext, getField(), TAG_NAME);
		} catch (ClassCastException e) {
			String str = "Wrong WidgetModel for " + TAG_NAME + " on field " + getField();
			log.error(str, e);
            throw new JspWriteRuntimeException(str, e);
		}

        if (model != null) {
            // Generate the HTML
            JspWriter out = pageContext.getOut();
            String idPrefix = getHtmlIdPrefix() + '_' + TagHelper.getFormBase(pageContext).getComponent().getComponentId();
            try {
                out.println( getHtml(idPrefix, formName, model) );
            } catch (IOException e) {
                String str = "Exception in writing the " + TAG_NAME;
                log.error(str, e);
                throw new JspWriteRuntimeException(str, e);
            }
        }
    }


   /** Getter for the attribute defImage.
     * @return Value of attribute defImage.
     */
    public String getDefImage() {
        return m_defImage;
    }

    /** Setter for the attribute defImage.
     * @param value New value of attribute defImage.
     */
    public void setDefImage(String value) {
        m_defImage = value;
    }

    private String getHtml(String idPrefix, String formName, ImageModel model)
    throws IOException {
        String html = null;

        // check if the image is stored as a File in the model
        String url = model.getImageUrl();
        if (url == null) {
            byte[] imageBytes = model.getImage();
            if (imageBytes != null) {
                // Include as a JSP

                //url = "jsp/framework/getImage.jsp;jsessionid=" + this.m_sessionId + "?form=" + m_form + "&field=" + m_name;
                //url = IMAGE_SERVER_URL + ";" + SESSION_COOKIE_NAME + "=" + pageContext.getSession().getId() + "?form=" + formName + "&field=" + getField();

                //url = "jsp/framework/getImage.jsp;jsessionid=" + this.m_sessionId + "?idPrefix=" + idPrefix;
                url = IMAGE_SERVER_URL + ';' + SESSION_COOKIE_NAME + '=' + pageContext.getSession().getId() + "?" + SESSION_ID_PREFIX_NAME + '=' + idPrefix;

                if (log.isDebugEnabled())
                    log.debug("Image URL: " + url);

                // add the contents to the userSession
                TagHelper.getUserSession(pageContext).addImage(idPrefix, imageBytes, model.getMimeType());
            } else {
                // no fileName or bytes found.. use the default
                url = getDefImage();
            }
        }


        // Only return an image tag, if there is an image...
        if(url != null)
            html = "<IMG SRC='" + url + "'>";

        return html;
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
