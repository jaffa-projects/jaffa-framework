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

package org.jaffa.persistence.engines.jdbcengine.variants;

import org.apache.log4j.Logger;
import java.util.PropertyResourceBundle;
import java.util.MissingResourceException;
import java.util.*;


/** This class manages all the access to the properties of the various database variants.
 * Each database variant will have its own property file in this package. For eg. a variant 'xxx' will have a 'xxx.properties' file in this package.
 * If the file does not exist, or if a particular property does not exist in the variant, then the 'default.properties' will be used.
 * Each property will have a static name associated with it.
 *
 * @author  GautamJ
 * @version 1.0
 */
public class Variant {
    private static Logger log = Logger.getLogger(Variant.class);
    
    /** This will hold Variant-PropertyResourceBundle pairs. */
    private static Map c_cache = new WeakHashMap();
    
    /** The prefix used to locate the properties file for a variant. */
    private static final String VARIANT_RESOURCE_PREFIX = "org.jaffa.persistence.engines.jdbcengine.variants.";
    
    /** The default resource. */
    private static final String DEFAULT_RESOURCE_NAME = VARIANT_RESOURCE_PREFIX + "default";
    private static PropertyResourceBundle c_defaultResource = null;
    static {
        try {
            c_defaultResource = (PropertyResourceBundle) PropertyResourceBundle.getBundle(DEFAULT_RESOURCE_NAME);
        } catch (MissingResourceException e) {
            String str = "Can't load the resource file: " + DEFAULT_RESOURCE_NAME + ".properties";
            log.fatal(str, e);
            throw new RuntimeException(str, e);
        }
    }
    
    
    
    
    // **** List static names for all the properties ***
    /** Static : The LOCK construct in a SELECT statement */
    public static final String PROP_LOCK_CONSTRUCT_IN_SELECT_STATEMENT = "lockConstructInSelectStatement";
    
    /** Static : The LOCK construct in the fROM portion if a SELECT statement */
    public static final String PROP_LOCK_CONSTRUCT_IN_FROM_SELECT_STATEMENT = "lockConstructInFromSelectStatement";

    /** Static : This indicates if the to_date SQL function is used when  querying Date fields */
    public static final String PROP_USE_TO_DATE_SQL_FUNCTION = "useToDateSqlFunction";
    
    /** Static : This is used to get the default packing code for boolean fields in a DBMS. Can be one of BOOLEAN_BIT, BOOLEAN_TF, BOOLEAN_YN, BOOLEAN_10 */
    public static final String PROP_DEFAULT_PACKING_CODE_FOR_BOOLEAN = "defaultPackingCodeForBoolean";
    
    /** Static : This is used to get the default table name to be used when evaluating functions */
    public static final String PROP_DEFAULT_FUNCTION_TABLE_NAME = "defaultFunctionTableName";
    
    /** Static : This is used to obtain the name of the function for trimming a column */
    public static final String PROP_TRIM_FUNCTION_PREFIX = "trimFunctionPrefix";

    /** Static : This is used to close the trim function */
    public static final String PROP_TRIM_FUNCTION_SUFFIX = "trimFunctionSuffix";

    /** Static : This is used to get the plugin for providing pagination during queries */
    public static final String PROP_PAGING_PLUGIN = "pagingPlugin";
    
    
    
    /** Returns the value of a property, for a given variant. The default resource will be used, in case the variant resource is not found or if the variant does not have the given property.
     * @param variant the name of the variant. If a null value is passed, then the default will be used.
     * @param key the name of the property.
     * @throws MissingResourceException if the property is not found in both the variant and the default resource files.
     * @return the value of a property.
     */
    public static String getProperty(String variant, String key)
    throws MissingResourceException {
        String value = null;
        
        PropertyResourceBundle resource = null;
        if (variant != null) {
            variant = VARIANT_RESOURCE_PREFIX + variant;
            // no need to find the resource if 'default' is passed in
            if (!variant.equals(DEFAULT_RESOURCE_NAME)) {
                if (c_cache.containsKey(variant))
                    resource = (PropertyResourceBundle) c_cache.get(variant);
                else
                    resource = loadResource(variant);
            }
            
            if (resource == null) {
                resource = c_defaultResource;
                if (log.isDebugEnabled())
                    log.debug("Resource file not found for the variant: " + variant + ". Will use the default resource");
            }
        } else {
            resource = c_defaultResource;
        }
        
        
        // Now get the property from the resource
        try {
            value = resource.getString(key);
        } catch (MissingResourceException e) {
            // check if the key is present in the default resource
            if (resource != c_defaultResource) {
                if (log.isDebugEnabled())
                    log.debug("Key '" + key + "' not found in the variant resource file '" + variant + "'. Will check the default resource");
                try {
                    value = c_defaultResource.getString(key);
                } catch (MissingResourceException e1) {
                    String str = "Key '" + key + "' not found in the default resource file";
                    log.error(str, e1);
                    throw e1;
                }
            } else {
                String str = "Key '" + key + "' not found in the default resource file";
                log.error(str, e);
                throw e;
            }
        }
        
        return value;
    }
    
    
    
    private synchronized static PropertyResourceBundle loadResource(String variant) {
        PropertyResourceBundle resource = null;
        if (c_cache.containsKey(variant))
            resource = (PropertyResourceBundle) c_cache.get(variant);
        else {
            try {
                resource = (PropertyResourceBundle) PropertyResourceBundle.getBundle(variant);
                c_cache.put(variant, resource);
            } catch (MissingResourceException e) {
                // Add a null object for the variant into the cache. This will avoid repeated searches for the variant resource.
                c_cache.put(variant, null);
            }
        }
        return resource;
    }
    
}
