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
import org.jaffa.security.VariationContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;


/**
 * Helper routine to retrieve the context from the "META_INF/MANIFEST.MF"
 */
public class ContextHelper {

    public static final String CONTEXT_SALIENCE = "Context-Salience";
    public static final String VARIATION_SALIENCE = "Variation-Salience";

    public static final String META_INF_MANIFEST_FILE = "META-INF/MANIFEST.MF";

    private static Map<String, String> contextSalienceMap = new HashMap<>();
    private static Map<String, String> variationSalienceMap = new HashMap<>();

    private static Logger logger = Logger.getLogger(ContextHelper.class);

    /**
     * retrieves the context salience from the path supplied
     *
     * @param contextPath
     * @return string containing context salience
     */
    public static String getContextSalience(String contextPath) {
        String contextSalience = "100-Highest";

        if (!contextPath.startsWith("jar") && !contextPath.startsWith("zip")) {
            // Class not from JAR
            logger.info("Context Salience is being resolved from an external file, not from a classpath JAR");
        } else {
            try {
                String manifestPath = contextPath.substring(0, contextPath.lastIndexOf("!") + 1) + "/" + META_INF_MANIFEST_FILE;
                if (!getContextSalienceMap().containsKey(manifestPath)) {
                    if (logger.isDebugEnabled())
                        logger.debug("manifestPath={}" + manifestPath);
                    String contextSalienceRead = getManifestParameter(manifestPath, true);
                    contextSalience = contextSalienceRead != null && !"".equals(contextSalienceRead.trim()) ? contextSalienceRead : contextSalience;
                    getContextSalienceMap().put(manifestPath, contextSalience);
                } else {
                    contextSalience = getContextSalienceMap().get(manifestPath);
                }
            } catch (Exception w) {
                logger.error("Exception occurred while getting context salience " + w);
            }
        }

        return contextSalience;
    }

    /**
     * retrieves the context salience from the path supplied
     *
     * @param contextPath
     * @return string containing context salience
     */
    public static String getVariationSalience(String contextPath)  {
        String variationSalience = VariationContext.NULL_VARIATION;

        if (!contextPath.startsWith("jar") && !contextPath.startsWith("zip")) {
            // Class not from JAR
            logger.info("Variation Salience is being resolved from an external file, not from a classpath JAR");
        } else {
            try {
                String manifestPath = contextPath.substring(0, contextPath.lastIndexOf("!") + 1) + "/" + META_INF_MANIFEST_FILE;
                if (!getVariationSalienceMap().containsKey(manifestPath)) {
                    if (logger.isDebugEnabled())
                        logger.debug("manifestPath={}" + manifestPath);
                    String variationSalienceRead = getManifestParameter(manifestPath, false);
                    variationSalience = variationSalienceRead != null && !"".equals(variationSalienceRead.trim()) ? variationSalienceRead : variationSalience;
                    getVariationSalienceMap().put(manifestPath, variationSalience);
                } else {
                    variationSalience = getVariationSalienceMap().get(manifestPath);
                }
            } catch (Exception w) {
                logger.error("Exception occurred while getting variation salience " + w);
            }
        }

        return variationSalience;
    }


    /**
     * retrieves the context salience from the path supplied
     *
     * @param manifestPath
     * @return string containing context salience
     * @throws IOException
     */
    public static String getManifestParameter(String manifestPath, boolean readContextSalience)  {
        Manifest manifest;
        try {
            manifest = new Manifest(new URL(manifestPath).openStream());
        } catch (Exception ee) {
            logger.error(ee.getMessage(), ee);
            return null;
        }

        return getManifestParameter(manifest, readContextSalience);
    }

    /**
     * retrieves the context salience from the class provided
     *
     * @param clazz
     * @return string containing context salience
     * @throws IOException
     */
    public static String getManifestParameter(Class clazz) throws IOException {
        Manifest mf = new Manifest();
        try (InputStream stream = clazz.getClassLoader().getResourceAsStream(META_INF_MANIFEST_FILE)) {
            mf.read(stream); 
        } catch (IOException e) {
            logger.error("Cannot read from manifest: ", e);
        }

        return getManifestParameter(mf, true);
    }

    /**
     * retrieves the context salience from the Manifest object provided
     *
     * @param manifest
     * @return string containing context salience
     */
    public static String getManifestParameter(Manifest manifest, boolean readContextSalience) {
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(readContextSalience ? CONTEXT_SALIENCE : VARIATION_SALIENCE);
    }

    public static Map<String, String> getContextSalienceMap() {
        return contextSalienceMap;
    }

    public static void setContextSalienceMap(Map<String, String> contextSalienceMap) {
        ContextHelper.contextSalienceMap = contextSalienceMap;
    }

    public static Map<String, String> getVariationSalienceMap() {
        return variationSalienceMap;
    }

    public static void setVariationSalienceMap(Map<String, String> variationSalienceMap) {
        ContextHelper.variationSalienceMap = variationSalienceMap;
    }
}
