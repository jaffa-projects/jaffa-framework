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
package org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/** Servlet needed if you want to use the FileExplorer to download file
 * <p>Include in web.xml with the following configuration
<code><pre>
&lt;servlet&gt;
&lt;servlet-name&gt;FileExplorerDownload&lt;/servlet-name&gt;
&lt;servlet-class&gt;org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.FileExplorerDownload&lt;/servlet-class&gt;
&lt;/servlet&gt;

&lt;servlet-mapping&gt;
&lt;servlet-name&gt;FileExplorerDownload&lt;/servlet-name&gt;
&lt;url-pattern&gt;/jaffa/admin/fileexplorer/download&lt;/url-pattern&gt;
&lt;/servlet-mapping&gt;
</pre><code>
 *
 * @author PaulE
 * @version 1.0
 */
@WebServlet(value="/jaffa/admin/fileexplorer/download", name="FileExplorerDownload")
public class FileExplorerDownload extends HttpServlet {

    private static Logger log = Logger.getLogger(FileExplorerDownload.class);
    public static final String SESSION_ATTR_FILE_PREFIX = "org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.";
    private static final String PARAM_UUID = "UUID";
    private static final String ATTR_FILE = "org.jaffa.applications.jaffa.modules.admin.components.fileexplorer";
    private static final String ATTR_FILE_NAME = "org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.fileName";
    private static final String ATTR_CONTENT_TYPE = "org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.contentType";
    protected static final Map c_fileTypes = new HashMap();

    static {
        c_fileTypes.put("csv", "application/vnd.ms-excel ");
        c_fileTypes.put("doc", "application/msword");
        c_fileTypes.put("htm", "text/html");
        c_fileTypes.put("html", "text/html");
        c_fileTypes.put("pdf", "application/pdf");
        c_fileTypes.put("ppt", "application/vnd.ms-powerpoint");
        c_fileTypes.put("txt", "text/plain");
        c_fileTypes.put("xls", "application/vnd.ms-excel ");
        c_fileTypes.put("xml", "text/xml");
        c_fileTypes.put("zip", "application/zip");
    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        File file;
        String fileName;
        String contentType;

        // The presence of a UUID as a request parameter, indicates that the file has been previously added to the session
        String UUID = request.getParameter(PARAM_UUID);
        if (UUID != null) {
            String sessionAttribute = SESSION_ATTR_FILE_PREFIX + UUID;
            file = (File) request.getSession().getAttribute(sessionAttribute);
            request.getSession().removeAttribute(sessionAttribute);
            if (file == null || !file.exists()) {
                String s = "Download of file " + (file == null ? "NULL" : file.getAbsolutePath()) + " failed.";
                log.error(s);
                throw new ServletException(s);
            }
            if (log.isDebugEnabled())
                log.debug("Downloading file cached in the session: " + file.getAbsolutePath());
            fileName = file.getName();
            contentType = determineContentType(fileName);
        } else {
            // Expect the file to down load to be a request attribute
            file = (File) request.getAttribute(ATTR_FILE);
            request.removeAttribute(ATTR_FILE);
            if (file == null || !file.exists()) {
                String s = "Download of file " + (file == null ? "NULL" : file.getAbsolutePath()) + " failed.";
                log.error(s);
                throw new ServletException(s);
            }
            if (log.isDebugEnabled())
                log.debug("Downloading file " + file.getAbsolutePath());

            // Determine the fileName
            fileName = (String) request.getAttribute(ATTR_FILE_NAME);
            request.removeAttribute(ATTR_FILE_NAME);
            if (fileName != null) {
                // retain only the last name if the fileName contains a path sequence
                int pos = fileName.lastIndexOf('/');
                if (pos >= 0)
                    fileName = fileName.substring(pos + 1);
                else {
                    pos = fileName.lastIndexOf('\\');
                    if (pos >= 0)
                        fileName = fileName.substring(pos + 1);
                }
            } else
                fileName = file.getName();

            // Find content type.
            contentType = (String) request.getAttribute(ATTR_CONTENT_TYPE);
            request.removeAttribute(ATTR_CONTENT_TYPE);
            if (contentType == null)
                determineContentType(fileName);
        }

        if (log.isDebugEnabled()) {
            log.debug("Downloading file as " + fileName);
            log.debug("ContentType is " + contentType);
        }

        // Set the appropriate output headers
        response.setContentType(contentType);
        try {
            response.setBufferSize(2048);
        } catch (IllegalStateException e) {
            // do nothing
        }
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setHeader("Pragma", "private");
        response.setHeader("Cache-Control", "no-store,no-cache");

        // send file
        InputStream in = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
        try {
            byte[] buffer = new byte[1000];
            while (in.available() > 0)
                out.write(buffer, 0, in.read(buffer));
            out.flush();
        } catch (IOException e) {
            log.error("Problem Serving Resource " + file.getAbsolutePath(), e);
        } finally {
            out.close();
            in.close();
        }
        if (log.isDebugEnabled())
            log.debug("Downloaded file " + file.getAbsolutePath());
    }

    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /** determines the content type based on the input fileName. */
    private String determineContentType(String fileName) {
        String contentType = getServletConfig().getServletContext().getMimeType(fileName);
        // If not found, use internal list to lookup it up for common types
        if (contentType == null) {
            int pos = fileName.lastIndexOf(".");
            if (pos >= 0)
                contentType = (String) c_fileTypes.get(fileName.substring(pos + 1));

            // Force the contentType to a nonstandard value such as 'application/x-download', so that the suggested filename will be used when saving it from the browser.
            // If we do not pass any contentType, then both IE and FireFox will treat the file as HTMl and append a '.html' extension to the filename.
            // If we pass contentType as 'application/octet-stream', IE appends a '.txt' extension to the filename.
            // If we pass contentType as 'application/base64', FireFox appends a random extension to the filename.
            // Check http://www.onjava.com/pub/a/onjava/excerpt/jebp_3/index3.html for more information.
            if (contentType == null)
                contentType = "application/x-download";
        }
        return contentType;
    }
}
