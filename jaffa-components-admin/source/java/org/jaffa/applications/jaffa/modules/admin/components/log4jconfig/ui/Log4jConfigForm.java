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

package org.jaffa.applications.jaffa.modules.admin.components.log4jconfig.ui;

import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.EditBoxModel;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.controller.EditBoxController;
import org.jaffa.presentation.portlet.widgets.controller.GridController;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.jaffa.util.DefaultErrorHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import org.jaffa.util.MessageHelper;
import org.jaffa.util.URLHelper;
import org.jaffa.util.StringHelper;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.applications.jaffa.modules.admin.components.log4jconfig.ui.exceptions.Log4jConfigException;
import org.apache.log4j.xml.Log4jEntityResolver;
/** This is the FormBean for the Log4jConfig.
 *
 * @author  Maheshd
 * @version 1.0
 */
public class Log4jConfigForm extends FormBase {
    
    private static Logger log = Logger.getLogger(Log4jConfigForm.class);
    
    /** The constant which returns the struts-forward for displaying this form */
    public static final String NAME = "admin_log4jConfig";
    
    /** Getter for property fileContents.
     * @return Value of property fileContents.
     */
    public String getFileContents() {
        return ( (Log4jConfigComponent) getComponent() ).getFileContents();
    }
    
    /** Setter for property fileContents.
     * @param fileContents New value of property fileContents.
     */
    public void setFileContents(String fileContents) {
        ( (Log4jConfigComponent) getComponent() ).setFileContents(fileContents);
    }
    
    /** Getter for property fileContents. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property fileContents.
     */
    public EditBoxModel getFileContentsWM() {
        EditBoxModel fileContentsModel = (EditBoxModel) getWidgetCache().getModel("fileContents");
        if (fileContentsModel == null) {
            if (getFileContents() != null)
                fileContentsModel = new EditBoxModel( getFileContents() );
            else
                fileContentsModel = new EditBoxModel();
            getWidgetCache().addModel("fileContents", fileContentsModel);
        }
        return fileContentsModel;
    }
    
    /** Setter for property fileContents. This is invoked by the servlet, when a post is done on the main screen.
     * @param value New value of property fileContents.
     */
    public void setFileContentsWV(String value) {
        EditBoxController.updateModel(value, getFileContentsWM());
    }
    
    
    /** This method should be invoked to copy the fields from the FormBean to the component after successful validation.
     * @return true of the values are copied successfully
     */
    public boolean doValidate() throws FrameworkException , ApplicationExceptions{
        String value = null;
        ApplicationExceptions appExps = new ApplicationExceptions();
        value = getFileContentsWM().getValue();
        if (value==null || (value != null && value.trim().length() == 0)) {
            appExps.add(new Log4jConfigException(Log4jConfigException.PROP_XML_FILE_PARSE_ERROR,StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Admin.Log4JConfig.NoContent",null))));
            throw appExps;
        }

        try{
            // Create a factory object for creating DOM parsers
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Specifies that the parser produced by this factory will validate documents as they are parsed.
            factory.setValidating(true);
            // Now use the factory to create a DOM parser
            DocumentBuilder parser = factory.newDocumentBuilder();
            // Specifies the EntityResolver onceto resolve DTD used in XML documents
            parser.setEntityResolver(new Log4jEntityResolver());
            // Specifies the ErrorHandler to handle warning/error/fatalError conditions
            parser.setErrorHandler(new DefaultErrorHandler());
            Document document = parser.parse(new InputSource(new StringReader(value)));
        }catch(ParserConfigurationException e){
            // Cannot pass e.toString() and pass as parameter as the the meesage contains quotes
            // which dows not work properly with displaying the messages
            appExps.add(new Log4jConfigException(Log4jConfigException.PROP_XML_FILE_PARSE_ERROR,StringHelper.convertToHTML(e.getMessage())));
            throw appExps;
        }catch(SAXException e){
            appExps.add(new Log4jConfigException(Log4jConfigException.PROP_XML_FILE_PARSE_ERROR,StringHelper.convertToHTML(e.getMessage())));
            throw appExps;
        }catch(IOException e){
            appExps.add(new Log4jConfigException(Log4jConfigException.PROP_XML_FILE_PARSE_ERROR,StringHelper.convertToHTML(e.getMessage())));
            throw appExps;
        }
        setFileContents(value);
        return true;
    }
}
