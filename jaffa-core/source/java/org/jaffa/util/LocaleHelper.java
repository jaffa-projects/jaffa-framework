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
/*
 * LocaleHelper.java
 *
 * Created on April 11, 2003, 1:00 PM
 */

package org.jaffa.util;
import org.apache.log4j.Logger;
import java.util.PropertyResourceBundle;
import java.util.Locale;
import java.lang.RuntimeException;
import java.util.MissingResourceException;

import org.jaffa.loader.ManagerRepositoryService;
import org.jaffa.loader.config.LocaleResourcesManager;
import org.jaffa.presentation.portlet.session.LocaleContext;

/** This is a helper routine used for the internationalization of datatypes. It extracts formats from a properties file based on datatype and locale country and language codes.
 * @author JonnyR
 */
public class LocaleHelper {
    private static Logger log = Logger.getLogger(LocaleHelper.class);

    private static LocaleResourcesManager localeResourcesManager = (LocaleResourcesManager) ManagerRepositoryService.getInstance().
            getManagerMap().get("LocaleResourcesManager");

    public static LocaleResourcesManager getLocaleResourcesManager() {
        return localeResourcesManager;
    }

    public static void setLocaleResourcesManager(LocaleResourcesManager localeResourcesManager) {
        LocaleHelper.localeResourcesManager = localeResourcesManager;
    }

    /** Returns a format based on country and language from the locale*.properties file
     * @param dataType datatype that the format is requested for
     * @return returns a format string for parsing and formatting a datatype
     */
    public static String getProperty(String dataType) {

        Locale localeObject = LocaleContext.getLocale();
        String key = null;
        String format = null;
        if (localeObject != null) {
            String country = localeObject.getCountry();
            String language = localeObject.getLanguage();
            String variant = localeObject.getVariant();
            key = language + "_" + country + "_" + variant + "." + dataType;
            try {
                format = localeResourcesManager.getLocalePropertiesRepository().query(key);
            } catch (MissingResourceException e1) {
                String str = "Key '" + key + "' not found in the default resource file";
                log.debug(str);
            }
            if (format == null) {
                key = language + "_" + country + "." + dataType;
                try {
                    format = localeResourcesManager.getLocalePropertiesRepository().query(key);
                } catch (MissingResourceException e1) {
                    String str = "Key '" + key + "' not found in the default resource file";
                    log.debug(str);
                }
                
            }
            
            if (format == null) {
                key = language + "." + dataType;
                try {
                    format = localeResourcesManager.getLocalePropertiesRepository().query(key);
                } catch (MissingResourceException e1) {
                    String str = "Key '" + key + "' not found in the default resource file";
                    log.debug(str);
                }
            }
            
            if (format == null) {
                key = dataType;
                try {
                    format = localeResourcesManager.getLocalePropertiesRepository().query(key);
                } catch (MissingResourceException e1) {
                    String str = "Key '" + key + "' not found in the default resource file";
                    log.warn(str);
                }
            }
        } else {
            key = dataType;
            try {
                format = localeResourcesManager.getLocalePropertiesRepository().query(key);
            } catch (MissingResourceException e1) {
                String str = "Key '" + key + "' not found in the default resource file";
                log.warn(str);
            }
            
        }
        return format;
    }

    /** This method will obtain obtain the layout by invoking the getProperty() method,
     * if the inputLayout is enclosed in square brackets. Eg. [Xyz].
     * Otherwise, it simply returns the inputLayout.
     * A null is returned, in case the input is null.
     * @param inputLayout the input layout to be parsed
     * @return the parsed layout.
     */
    public static String determineLayout(String inputLayout) {
        if (inputLayout != null && inputLayout.startsWith("[")) {
            String outputLayout = getProperty(inputLayout.substring(1, inputLayout.lastIndexOf(']')));
            return outputLayout != null ? outputLayout : inputLayout;
        } else
            return inputLayout;
    }

    public static Locale string2Locale(String lstr) {
        if (lstr==null || lstr.length()==0) return null;
        String[] larray = lstr.split("_");
        switch(larray.length) {
            case 1:
                return new Locale(larray[0]);
            case 2:
                return new Locale(larray[0], larray[1]);
            case 3:
                return new Locale(larray[0], larray[1], larray[2]);
        }
        log.warn(lstr + " is not in format for locale.");
        return null;
    }
}
