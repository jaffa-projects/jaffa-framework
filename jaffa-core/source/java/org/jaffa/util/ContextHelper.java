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


import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;


/**
 * Helper routine to retrieve the context from the "META_INF/MANIFEST.MF"
 */
public class ContextHelper {

    public static final String CONTEXT_SALIENCE = "Context-Salience";

    public static final String META_INF_MANIFEST_FILE = "META-INF/MANIFEST.MF";

    public static Map<String, String> contextSalienceMap = new HashMap<>();

    private static Logger logger = Logger.getLogger(ContextHelper.class);

    /**
     * retrieves the context salience from the path supplied
     * @param contextPath
     * @return string containing context salience
     * @throws IOException
     */
    public static String getContextSalience(String contextPath) throws IOException {
        String contextSalience = "100-Highest";

        if (!contextPath.startsWith("jar")) {
            // Class not from JAR
            logger.warn("The Context Path is not frm the JAR");
        } else {
            String manifestPath = contextPath.substring(0, contextPath.lastIndexOf("!") + 1) + "/" + META_INF_MANIFEST_FILE;
            if(!contextSalienceMap.containsKey(manifestPath)) {
                if (logger.isDebugEnabled())
                    logger.debug("manifestPath={}" + manifestPath);
                String contextSalienceRead = getContextSalienceFromManifestPath(manifestPath);
                contextSalience = contextSalienceRead != null ? contextSalienceRead : contextSalience;
                contextSalienceMap.put(manifestPath, contextSalience);
            }else {
                contextSalience = contextSalienceMap.get(manifestPath);
            }
        }

        return contextSalience;
    }


    /**
     * retrieves the context salience from the path supplied
     * @param manifestPath
     * @return string containing context salience
     * @throws IOException
     */
    public static String getContextSalienceFromManifestPath(String manifestPath) throws IOException {
        Manifest manifest;
        try {
            manifest = new Manifest(new URL(manifestPath).openStream());
        }catch(IOException ee){
            logger.error(ee.getMessage(), ee);
            return null;
        }

        return getContextSalienceFromManifest(manifest);
    }

    /**
     * retrieves the context salience from the class provided
     * @param clazz
     * @return string containing context salience
     * @throws IOException
     */
    public static String getContextSalienceFromClass(Class clazz) throws IOException {
        Manifest mf = new Manifest();
        mf.read(clazz.getClassLoader().getResourceAsStream(META_INF_MANIFEST_FILE));

        return getContextSalienceFromManifest(mf);
    }

    /**
     * retrieves the context salience from the Manifest object provided
     * @param manifest
     * @return string containing context salience
     */
    public static String getContextSalienceFromManifest(Manifest manifest) {
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(CONTEXT_SALIENCE);
    }
}
