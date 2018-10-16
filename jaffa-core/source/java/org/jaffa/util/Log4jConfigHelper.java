/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2014 JAFFA Development Group
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

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Log4jConfigHelper: This class reads the context params and configure or configureAndWatch based on the params.
 */
public abstract class Log4jConfigHelper {

    public static final String LOG4J_LOCATION = "log4jLocation";
    public static final String WATCH_INTERVAL = "log4jWatchInterval";

    /**
     * @param sc :ServletContext
     */
    public static void initLogging(ServletContext sc) {

        String log4jLocation = sc.getInitParameter(LOG4J_LOCATION);
        if (log4jLocation != null) {
            try {
                sc.log("Initializing log4j from [" + log4jLocation + "]");

                String intervalString = sc.getInitParameter(WATCH_INTERVAL);
                if (StringUtils.hasText(intervalString)) {
                    try {
                        long refreshInterval = Long.parseLong(intervalString);
                        initLogging(sc, log4jLocation, refreshInterval);
                    } catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("Invalid [log4jWatchInterval] interval parameter: " + ex.getMessage());
                    }
                } else {
                    initLogging(sc, log4jLocation, null);
                }
            } catch (FileNotFoundException | MalformedURLException ex) {
                throw new IllegalArgumentException("Invalid [log4jLocation] parameter: " + ex.getMessage());
            }
        }
    }

    /**
     * Initialise the logging.
     *
     * @param sc              : ServletContext
     * @param location
     * @param refreshInterval
     * @throws FileNotFoundException
     * @throws MalformedURLException
     */
    public static void initLogging(ServletContext sc, String location, Long refreshInterval) throws FileNotFoundException, MalformedURLException {
        String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
        URL url = sc.getResource(location);
        if (url == null) {
            url = URLHelper.newExtendedURL(location);
        }
        File file = new File(url.getFile());
        if (!file.exists()) {
            throw new FileNotFoundException("Log4j file [" + resolvedLocation + "] not found");
        }

        if (resolvedLocation.toLowerCase().endsWith(".xml")) {
            if (refreshInterval != null && refreshInterval != 0) {
                DOMConfigurator.configureAndWatch(file.getAbsolutePath(), refreshInterval);
            } else {
                DOMConfigurator.configure(url);
            }
        } else {
            if (refreshInterval != null && refreshInterval != 0) {
                PropertyConfigurator.configureAndWatch(file.getAbsolutePath(), refreshInterval);
            } else {
                PropertyConfigurator.configure(url);
            }
        }
    }

    /**
     * Shutdown logging
     *
     * @param sc: ServletContext
     */
    public static void shutdownLogging(ServletContext sc) {
        sc.log("Shutting down log4j");
        LogManager.shutdown();
    }
}
