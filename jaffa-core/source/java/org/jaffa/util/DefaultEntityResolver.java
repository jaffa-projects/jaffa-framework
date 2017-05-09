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

package org.jaffa.util;

import java.util.*;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.IOException;
import org.xml.sax.SAXException;

/** This is an implementation of the EntityResolver interface.
 * It maintains a map of PublicId/DtdUrlString pairs, which are loaded from the org/jaffa/config/dtd.properties file.
 * An instance of this class should be passed to the XML parser, before parsing an XML file.
 * This will ensure that the parser reads the DTD file off the local machine (if specifed by the publicId) or uses the systemId to locate the DTD on an external resource.
 *
 * @author  GautamJ
 */
public class DefaultEntityResolver implements EntityResolver {

    private static Logger log = Logger.getLogger(DefaultEntityResolver.class);
    private static final String CONFIG_RESOURCE = "org.jaffa.config.dtd";

    /** A Map to hold PublicId/DtdUrlString pairs. */
    private static Map c_map = null;

    /** This method will be invoked by an XML parser, when it tries to locate the DTD for validating the XML.
     * If the publicId is passed and the internal Map of PublicId/DtdUrlString pairs contains an entry for it, then an InputStream object will be created for the corresponding DTD.
     * This is useful for cases where the DTD is located locally on the web-server, and hence doesn't need to access any external resource for locating DTDs.
     * A null will be returned, in case there is no entry for the publicId in the internal Map.
     * In such a case, the parser will locate the DTD as specified by the systemId.
     * @param publicId The public identifier of the external entity being referenced, or null if none was supplied.
     * @param systemId The system identifier of the external entity being referenced.
     * @return An InputSource object describing the new input source, or null to request that the parser open a regular URI connection to the system identifier.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     * @throws IOException A Java-specific IO exception, possibly the result of creating a new InputStream or Reader for the InputSource.
     */
    public InputSource resolveEntity(String publicId, String systemId)
    throws SAXException, IOException {
        if (log.isDebugEnabled())
            log.debug("Resolving Entity for PublicId: " + publicId + " and SystemId: " + systemId);
        InputSource inputSource = null;
        if (publicId != null) {
            if (c_map == null)
                loadDtdMappings();
            String dtdUrlString = (String) c_map.get(publicId);
            if (dtdUrlString != null) {
                if (log.isInfoEnabled())
                    log.info("Resolved Entity for PublicId: " + publicId + " with the Url: " + dtdUrlString);
                InputStream dtdInputStream = URLHelper.getInputStream(dtdUrlString);
                inputSource = new InputSource(dtdInputStream);
            }
        }
        return inputSource;
    }



    /** Loads the DtdPublicId/DtdUrl pairs from the properties file and puts it in the internal map. */
    private static synchronized void loadDtdMappings() {
        if (c_map == null) {
            ResourceBundle properties = PropertyResourceBundle.getBundle(CONFIG_RESOURCE);
            c_map = new HashMap();

            // Note: The PublicId can have white space.. hence it is not used as the key in the properties file.
            for (Enumeration enumeration = properties.getKeys(); enumeration.hasMoreElements();) {
                String dtdUrl = (String) enumeration.nextElement();
                String dtdPublicId = properties.getString(dtdUrl);
                c_map.put(dtdPublicId, dtdUrl);
            }
        }
    }

}
