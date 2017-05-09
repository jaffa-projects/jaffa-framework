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

package org.jaffa.components.attachment.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.FormKey;

/** Contains helper methods to support the attachment components
 */
public class AttachmentComponentHelper {
    
    private static final Logger log = Logger.getLogger(AttachmentComponentHelper.class);
    
    /** Creates a temporary file with the input data and adds it as an attribute to the request stream.
     * Returns a FormKey pointing to Jaffa's FileExplorer servlet which downloads the temporary file.
     * @param form the Form instance to which an error will be added, in case an IOException occurs while creating the temporary file.
     * @param request the request stream.
     * @param fileName Its suffix is used for creating the temporary file. May be null.
     * @param data The contents to be downloaded. May be null, in which case nothing is done.
     * @return the FormKey for the FileExplorer servlet. A null is returned if the input data is null.
     */
    public static FormKey loadAttachmentData(FormBase form, HttpServletRequest request, String fileName, byte[] data) {
        FormKey fk = null;
        if (data != null && data.length > 0) {
            OutputStream outputStream = null;
            try {
                // Determine the suffix
                int i = fileName != null ? fileName.lastIndexOf('.') : -1;
                String suffix = i >= 0 ? fileName.substring(i) : null;
                
                // Create a temporary file with the suffix
                File attachmentDataFile = File.createTempFile("jaffa", suffix);
                
                // Add the data to the file
                outputStream = new BufferedOutputStream(new FileOutputStream(attachmentDataFile));
                outputStream.write(data);
                outputStream.flush();
                
                // Return a FormKey pointing to Jaffa's FileExplorer servlet
                request.setAttribute("org.jaffa.applications.jaffa.modules.admin.components.fileexplorer", attachmentDataFile);
                request.setAttribute("org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.fileName", fileName);
                return new FormKey("jaffa_admin_fileExplorer_download", null);
            } catch (IOException e) {
                log.error("Exception in writing the attachment data to a temporary file", e);
                form.raiseError(request,ActionMessages.GLOBAL_MESSAGE,"error.framework.general");
            } finally {
                try {
                    if (outputStream != null)
                        outputStream.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
        return fk;
    }
    
}
