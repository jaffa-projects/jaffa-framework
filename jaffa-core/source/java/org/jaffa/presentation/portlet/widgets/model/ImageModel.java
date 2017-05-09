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

package org.jaffa.presentation.portlet.widgets.model;

import org.apache.log4j.*;
import java.util.*;
import java.io.*;
import org.jaffa.presentation.portlet.widgets.model.exceptions.IllegalExtensionRuntimeException;
import org.jaffa.config.Config;

/** Model for the Image widget. This will keep the image in memory by default.
 * This can however be modified to store the image in a temporary folder on the filesystem.
 */
public class ImageModel implements IWidgetModel  {

    private static Logger log = Logger.getLogger(ImageModel.class);

    private static final boolean KEEP_IN_MEMORY = true;
    private static final String WEB_SERVER_ROOT = (String) Config.getProperty(Config.PROP_WEB_SERVER_ROOT);
    private static final String WEB_TEMP_DIR = "jaffa/imgs/temp/";
    private static final HashMap<String,String> MIME_TYPES = new HashMap<>();
    static {
        MIME_TYPES.put("gif","image/gif");
        MIME_TYPES.put("png","image/x-png");
        MIME_TYPES.put("jpeg","image/jpeg");
        MIME_TYPES.put("jpg","image/jpeg");
        MIME_TYPES.put("jpe","image/jpeg");
        MIME_TYPES.put("bmp","image/x-ms-bmp");
        MIME_TYPES.put("tif","image/tiff");
        MIME_TYPES.put("tiff","image/tiff");
    }
    private static final String DEFAULT_EXTENTION = "gif";

    private String m_extention = null;
    private byte[] m_image = null; // Image data if kept in memory
    private File m_file = null; // Image file if stored on disk


    /** Create a new image model. The default image type is GIF.
     * @param image The image.
     */
    public ImageModel(byte[] image) {
        this(image, null);
    }

    /** Create a new Image Model specifying the image type (if null, GIF is assumed).
     * Currently suppoted image types are : gif,png,jpeg,jpg,jpe,bmp,tif,tiff.
     * This will throw an IllegalExtensionRuntimeException if any other extention is passed.
     * @param image The image.
     * @param extention The image type.
     */
    public ImageModel(byte[] image, String extention) {

        // ensure a valid extension
        if(extention != null) {
            // Convert to lower case
            extention = extention.toLowerCase();

            // Look up extention and see if it is supported
            if(MIME_TYPES.get(extention) == null) {
                String str = "Image Type Not Supported : " + extention;
                log.error(str);
                throw new IllegalExtensionRuntimeException(str);
            }
            m_extention = extention;
        } else {
            m_extention = DEFAULT_EXTENTION;
        }


        // Store image based on storage method (Memory / Disk)
        if(KEEP_IN_MEMORY) {
            m_image = image;
        } else {
            try {
                String dirName = null;
                if (WEB_SERVER_ROOT.endsWith(File.separator))
                    dirName = WEB_SERVER_ROOT + WEB_TEMP_DIR;
                else
                    dirName = WEB_SERVER_ROOT + File.separatorChar + WEB_TEMP_DIR;

                m_file = File.createTempFile("img_", "." + m_extention, new File(dirName));
                if (log.isDebugEnabled())
                    log.debug("Temp Img File : " + m_file.toString() );

                // Set this incase the finalize block doesn't get executed.
                m_file.deleteOnExit();

                OutputStream out = new BufferedOutputStream( new FileOutputStream(m_file) );
                out.write(image);
                out.close();
            } catch (IOException e) {
                // log the error & continue as if InMemory is true
                log.warn("Can't Write Out Image : " + e.getMessage() + "\n Temp Dir :" + WEB_SERVER_ROOT + WEB_TEMP_DIR + "\nContinued execution, with the image kept in memory", e);

                // delete the file
                if (m_file != null) {
                    m_file.delete();
                    m_file = null;
                }
                m_image = image;
            }
        }
    }

    /** Getter for property mimeType.
     * @return Value of property mimeType.
     */
    public String getMimeType() {
        return MIME_TYPES.get(m_extention);
    }

    /** Getter for property extension (image type).
     * @return Value of property extension (image type).
     */
    public String getExtention() {
        return m_extention;
    }

    /** Returns the URL to access the image relative of the web server.
     * @return the URL to access the image relative of the web server.
     */
    public String getImageUrl() {
        if(m_file != null)
            return WEB_TEMP_DIR + m_file.getName();
        else
            return null;
    }

    /** Get the byte array for the image.
     * This may be in-memory, or read in from disk.
     * @throws IOException if any error occurs in reading the image from the filesystem.
     * @return the byte array for the image.
     */
    public byte[] getImage() throws IOException {
        byte[] image = null;

        if(m_image != null) {
            // Return In-Memory Image
            image = m_image;
        } else {
            if(m_file != null) {
                // Read from file
                InputStream stream = new BufferedInputStream( new FileInputStream(m_file) );
                image = new byte[stream.available()];
                stream.read(image);
                stream.close();
            }
        }
        return image;
    }
}