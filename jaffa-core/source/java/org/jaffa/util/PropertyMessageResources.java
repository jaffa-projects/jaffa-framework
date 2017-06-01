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

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import org.apache.struts.util.MessageResourcesFactory;
import org.jaffa.config.ApplicationResourceLoader;
import org.jaffa.security.VariationContext;

/**
 * Concrete subclass of <code>MessageResources</code> that reads message keys
 * and corresponding strings from named property resources in the same manner
 * that java.util.PropertyResourceBundle does. The
 * base property defines the base property resource name, and
 * must be specified.
 *
 * IMPLEMENTATION NOTE - This class trades memory for
 * speed by caching all messages located via generalizing the Locale under
 * the original locale as well.
 * This results in specific messages being stored in the message cache
 * more than once, but improves response time on subsequent requests for
 * the same locale + key combination.
 *
 * This class is based on the struts class org.apache.struts.util.PropertyMessageResources
 * It loads the properties file using File I/O. This allows us to view the changes to the file, without having to reload the webapp.
 * It supports the flushing of the message-resource cache.
 * It also overrides the localeKey() method, to append the variation for the authenticated user to the toString() output of the Locale object.
 * @author  GautamJ
 */
public class PropertyMessageResources extends org.apache.struts.util.PropertyMessageResources {
    
    /**
     * Construct a new PropertyMessageResources according to the
     * specified parameters.
     *
     * @param factory The MessageResourcesFactory that created us
     * @param config The configuration parameter for this MessageResources
     */
    public PropertyMessageResources(MessageResourcesFactory factory, String config) {
        super(factory, config);
    }
    
    
    /**
     * Construct a new PropertyMessageResources according to the
     * specified parameters.
     *
     * @param factory The MessageResourcesFactory that created us
     * @param config The configuration parameter for this MessageResources
     * @param returnNull The returnNull property we should initialize with
     */
    public PropertyMessageResources(MessageResourcesFactory factory, String config, boolean returnNull) {
        super(factory, config, returnNull);        
    }
    
    
    /** This method flushes the cache that holds all the messages read from the resource.
     */
    public synchronized void flushCache() {
	synchronized (super.formats) {
            super.formats.clear();
        }
        synchronized (super.messages) {
            super.messages.clear();
        }
        super.locales.clear();
    }
    
    
    
    
    
    /** This method is a copy of the implementation in the base class
     * The only difference is that it tries to first load the properties file using File I/O; if that doesn't work, then it uses the ClassLoader to load the file.
     *
     * Load the messages associated with the specified Locale key.  For this
     * implementation, the <code>config</code> property should contain a fully
     * qualified package and resource name, separated by periods, of a series
     * of property resources to be loaded from the class loader that created
     * this PropertyMessageResources instance.  This is exactly the same name
     * format you would use when utilizing the
     * <code>java.util.PropertyResourceBundle</code> class.
     *
     * @param localeKey Locale key for the messages to be retrieved
     */
    protected synchronized void loadLocale(String localeKey) {
        
        if (log.isTraceEnabled()) {
            log.trace("loadLocale(" + localeKey + ")");
        }
        
        // Have we already attempted to load messages for this locale?
        if (locales.get(localeKey) != null) {
            return;
        }
        locales.put(localeKey, localeKey);
        
        // Set up to load the property resource for this locale key, if we can
        String name = config.replace('.', '/');
        if (localeKey.length() > 0) {
            name += "_" + localeKey;
        }
        name += ".properties";
        InputStream is = null;
        Properties props = null;
        
        // Load the specified property resource
        if (log.isTraceEnabled()) {
            log.trace("  Loading resource '" + name + "'");
        }
        
        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //if (classLoader == null) {
            //classLoader = this.getClass().getClassLoader();
        //}
        //is = classLoader.getResourceAsStream(name);
        
        // Try to load the file using ClassLoader.getResource(name).openStream()
        // This allows us to view the changes to the file, without having to reload the webapp
        try {
			// Note: TODO- This one finds a ApplicationResource.properties from
			// inside jar resources folder. We need to move
			// ApplicationResource.properties to META-INF if there is one.
            is = URLHelper.getInputStream(name);
        } catch (Exception e) {
            log.error("loadLocale()", e);
        }
        
        if (is != null) {
            try {
				props = new Properties();
				props.load(is);
                
            } catch (IOException e) {
                log.error("loadLocale()", e);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("loadLocale()", e);
                }
            }
        }
        
        if (log.isTraceEnabled()) {
            log.trace("  Loading resource completed");
        }
        
		// If the props not loaded from File I/O, then load it from
		// ApplicationResourceLoader.
		if (props == null) {
			props = ApplicationResourceLoader.getInstance().getLocaleProperties(localeKey);
		}
        
        // Copy the corresponding values into our cache
        if (props==null || props.size() < 1) {
            return;
        }
        
        synchronized (messages) {
            Iterator names = props.keySet().iterator();
            while (names.hasNext()) {
                String key = (String) names.next();
                if (log.isTraceEnabled()) {
                    log.trace("  Saving message key '" + messageKey(localeKey, key));
                }
                messages.put(messageKey(localeKey, key), props.getProperty(key));
            }
        }
        
    }
    
    
    /** Compute and return a key to be used in caching information by a Locale.
     * This appends the variation for the authenticated user to the toString() output of the Locale object.
     * @param locale The locale for which a key is desired
     * @return a key to be used in caching information by a Locale.
     */
    protected String localeKey(Locale locale) {
        if (locale == null)
            return "";
        else if (locale.getVariant() != null && locale.getVariant().length() > 0)
            return locale.toString();
        else
            return locale.toString() + '_' + VariationContext.getVariation();
    }

}
