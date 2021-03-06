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

package org.jaffa.applications.jaffa.modules.admin.components.businessfunction.ui;

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.config.Config;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.util.URLHelper;
import org.jaffa.util.StringHelper;
import java.net.URL;
import org.jaffa.applications.jaffa.modules.admin.components.businessfunction.ui.exceptions.BusinessFunctionException;

/** This is the component controller for the Business Function.
 *
 * @author  Maheshd
 * @version 1.0
 */
public class BusinessFunctionComponent extends Component {
    
    private static Logger log = Logger.getLogger(BusinessFunctionComponent.class);
    private static final String DEFAULT_LOCATION_FOR_XML_FILE = "resources/business-functions.xml";
    /** Holds value of property fileContents. */
    private String m_fileContents;
    
    /** Holds value of property fileContentsFormKey. */
    private FormKey m_fileContentsFormKey;
    
    
    /** Getter for property fileContents.
     * @return Value of property fileContents.
     *
     */
    public String getFileContents() {
        return m_fileContents;
    }
    
    /** Setter for property fileContents.
     * @param fileContents New value of property fileContents.
     *
     */
    public void setFileContents(String fileContents) {
        m_fileContents = fileContents;
    }
    
    /** Getter for property fileContentsFormKey.
     * @return Value of property fileContentsFormKey.
     *
     */
    public FormKey getBusinessFunctionFormKey() {
        if (m_fileContentsFormKey == null)
            m_fileContentsFormKey = new FormKey(BusinessFunctionForm.NAME, getComponentId());
        return m_fileContentsFormKey;
    }
    
    /** This will retrieve the file contents of the business-function xml file  and return the FormKey for rendering the component.
     * @return the FormKey for rendering the component.
     * @throws FrameworkException if any error ocurrs in obtaining the contents of the business-function xml file.
     */
    public FormKey display() throws FrameworkException , ApplicationExceptions{
        
        retrieveFileContents();
        
        return getBusinessFunctionFormKey();
    }
    
    /** This retrieves the the file contents of the business-function xml file.
     * @throws FrameworkException, ApplicationExceptions if any error occurs.
     */
    protected void retrieveFileContents() throws FrameworkException , ApplicationExceptions{
        ApplicationExceptions appExps = new ApplicationExceptions();
        URL url = null;
        BufferedReader reader = null;
        try {
            url = URLHelper.newExtendedURL(DEFAULT_LOCATION_FOR_XML_FILE);
            String absoluteFileName = url.getPath();
            // clear the widget cache
            getUserSession().getWidgetCache(getComponentId()).clear();
            // read the contents of the file
            reader = new BufferedReader(new FileReader(absoluteFileName));
            StringBuffer buf = new StringBuffer();
            String str = null;
            while ((str = reader.readLine()) != null) {
                buf.append(str);
                buf.append("\r\n");
            }
            m_fileContents = buf.toString();
        }catch (MalformedURLException e) {
            appExps.add(new BusinessFunctionException(BusinessFunctionException.PROP_FILENOTFOUND_ERROR, StringHelper.convertToHTML(DEFAULT_LOCATION_FOR_XML_FILE)));
            throw appExps;
        }catch (IOException e) {
                appExps.add(new BusinessFunctionException(BusinessFunctionException.PROP_FILEREAD_ERROR,StringHelper.convertToHTML(e.getMessage())));
                throw appExps;
            }finally {
                if (reader != null)
                    try{
                        reader.close();
                    }catch (IOException e) {
                        String str = "Exception thrown while closing the Reader Stream";
                        log.error(str, e);
                        appExps.add(new BusinessFunctionException(BusinessFunctionException.PROP_FILEREAD_ERROR,StringHelper.convertToHTML(e.getMessage())));
                        throw appExps;
                    }
            }
    }
}