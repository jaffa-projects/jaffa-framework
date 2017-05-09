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
 * 1.    Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.    Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 * 3.    The name "JAFFA" must not be used to endorse or promote products derived from
 *     this Software without prior written permission. For written permission,
 *     please contact mail to: jaffagroup@yahoo.com.
 * 4.    Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *     appear in their names without prior written permission.
 * 5.    Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

import java.net.URL;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jaffa.config.Config;

/** Convenience methods related to logging
 *
 * @author  PaulE
 */
public class LoggerHelper {
    
    private static Logger log = null;
    private static boolean c_log4jInitialized = false;
    
    /** Initializes Log4J using the setting of the 'framework.Log4JConfig' property in the config.properties file.
     * Ensures that Log4J will be initialized just once.
     */
    public static void init() {
        try {
            
            if (!c_log4jInitialized) {
                //Read setting from configuration file
                String fileName = (String) Config.getProperty(Config.PROP_LOG4J_CONFIG, "default");
                
                if ( fileName.equalsIgnoreCase("none") ) {
                    // do nothing.. Assume that log4j would have been initialized by some other container
                    initializeLogField();
                    log.info("Skipped log4j configuration. Should be done by Web/J2EE Server first!");
                } else if ( fileName.equalsIgnoreCase("default") ) {
                    defaultLog4j();
                } else {
                    try {
                        URL u = URLHelper.newExtendedURL(fileName);
                        DOMConfigurator.configure(u);
                        initializeLogField();
                        if ( log.isInfoEnabled() )
                            log.info("Configured log4j using the configuration file (relative to classpath): " + fileName );
                    } catch (Exception e) {
                        System.err.println( "Error in initializing Log4j using the configFile (relative to classpath): " + fileName );
                        e.printStackTrace();
                        defaultLog4j();
                    }
                }
                c_log4jInitialized = true;
            }
        } catch (Exception e) {
            System.err.println( "Error in initializing Log4j: Unexpected Exception " + e.getMessage() );
        }
    }
    
    private static void defaultLog4j() {
        BasicConfigurator.configure();
        initializeLogField();
        if ( log.isInfoEnabled() )
            log.info("Configured log4j using the Basic Configurator" );
    }
    
    private static void initializeLogField() {
        if (log == null)
            log = Logger.getLogger(LoggerHelper.class);
    }
    
}
