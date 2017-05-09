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
 * Factory.java
 *
 * Created on November 9, 2001, 11:02 AM
 */

package org.jaffa.middleware;

import org.jaffa.config.Config;
import org.apache.log4j.Logger;
import java.util.PropertyResourceBundle;
import java.util.MissingResourceException;

/**
 * Factory used by component managers to get a handle on the correct Implementation of the
 * Transaction Controller to use.
 * There is a System Setting in the framework.properties file that controls which implementation
 * the factory returns. This setting is either 1, 2 or 3
 *
 * Setting 1 - This means that the Factory is in test mode, this means that it will go to an additional
 *            properties file (middleware_test.properties) where it will look for a property with the
 * 	       same (full) name as the interface class.
 * 	       It expects to find a setting here to give the 'real' setting for the factory. If there is
 * 	       no property or any other error, it assumes a '2-Tier' setting. If there is a propery
 * 	       it must have the value 1, 2 or 3
 * Setting 2 - This means the Factory is in 2-tier Mode for all Interfaces
 * Setting 3 - This means the Factory is in 3-tier Mode for all Interfaces
 *
 * How do the different Modes Work? Assuming the interface is in package XXX and is called IYYY.java
 * 	Test Mode - This assumes there is an implementing class called XXX.test.YYYTx.java.
 * 		    This class should have fake implementations for all transaction methods such
 * 		    that the presentation tier for the component can be fully tested.
 * 	2-Tier Mode - This assumes there is an implementing class called XXX.tx.YYYTx.java. This
 * 		      implementing class should be the full implementation, and will be executed in
 * 	  	      the presentation tier.
 * 	3-Tier Mode - This assumes there is an implementing class called XXX.ejb.YYYPx.java. This
 * 	 	      implementing class is a Proxy to the real Transaction Controller. The proxy will
 * 		      communicate with a Session Bean which will wrapper the real tranaction class
 * 		      (normally called XXX.tx.YYYTx.java)
 *
 * @author GautamJ/PaulE
 * @version 2.0
 */
public class Factory {
    private static final Logger log = Logger.getLogger(Factory.class);

    private static final byte TIER_TEST = 1;
    private static final byte TIER2 = 2;
    private static final byte TIER3 = 3;

    private static final String MIDDLEWARE_RESOURCE = "middleware_test";

    private static Byte c_tier = null;

    /** Creates the Real Transaction Controller Object To Be Used
     * Based on the configuration settings. The real
     * object may be either a Test Rig, a Local Implemntation or a Proxy
     * @param interfaceClass The name of the interface that the transaction controller bust be implementing
     * @throws CreateServiceException Thrown if the correct object could not be created
     * @return Returns the specified obejct that implements the
     * reqiured interface. This object on return should be
     * casted back to the interface name that was passed in,
     * not to a specific object instance.
     */
    public static Object createObject(Class interfaceClass)
    throws CreateServiceException {

        Byte tier = null;
        String className = null;
        try {
            // Make sure that an interface is passed in
            if ( !interfaceClass.isInterface() ) {
                log.error("The Input Should be an Interface For A Component Transaction Controller");
                throw new CreateServiceException(CreateServiceException.NOT_AN_INTERFACE, new Object[] {interfaceClass.getName()});
            }

            // Extract the componentName & packageName from the input
            String componentName = extractComponentName(interfaceClass);
            String packageName = extractPackageName(interfaceClass);

            // Read the Tier property
            if (c_tier == null)
                c_tier = readTier();

            // If this is 'TIER_TEST', then the real tier should be read from the 'middleware_test.properties' file
            if(c_tier.byteValue() == TIER_TEST) {
		        tier = readTestTier( interfaceClass.getName() );
		        if(log.isDebugEnabled())
               		log.debug("Test Tier Setting Is " + tier);
            } else
                // The tier to be used should be in the static varaiable
                tier = c_tier;

            // Determine the class to instantiate based on the tier property
            if (tier.byteValue() == TIER2) {
                className = (packageName == null ? "tx." : packageName + ".tx.") + componentName + "Tx";
            } else if (tier.byteValue() == TIER3) {
                className = (packageName == null ? "ejb." : packageName + ".ejb.") + componentName + "Px";
            } else {
		        // Assume it is a test tier
		        className = (packageName == null ? "test." : packageName + ".test.") + componentName + "Tx";
            }

            if(log.isDebugEnabled())
               log.debug("Middleware Factory: Setting="+c_tier+", Using Mode=" + tier +", Use Class=" + className);

            // instantiate the class
            return ( Class.forName(className) ).newInstance();

        } catch (Exception e) {
            log.error("Cannot instantiate the Transaction controller : " + className, e);
            throw new CreateServiceException(CreateServiceException.INSTANTIATION_FAILED, new Object[] {className}, e);
        }
    }

    private static String extractComponentName(Class interfaceClass)
    throws CreateServiceException {
        String componentName = interfaceClass.getName();
        int i = componentName.lastIndexOf('.');
        if ( i >= 0 && ++i < componentName.length() )
            componentName = componentName.substring(i);
        if (componentName.startsWith("I") && componentName.length() > 1)
            componentName = componentName.substring(1);
        else {
            log.error("The interface " + componentName + " should be prefixed by an 'I'");
            throw new CreateServiceException(CreateServiceException.INVALID_INPUT, new Object[] {componentName});
        }

        return componentName;
    }

    private static String extractPackageName(Class interfaceClass) {
        // the getPackage() doesnt work under a web-server
//        String packageName = null;
//        Package p = interfaceClass.getPackage();
//        if (p != null) {
//            packageName = p.getName();
//            if ( packageName != null && packageName.equals("") )
//                packageName = null;
//        }
//        return packageName;

        String packageName = null;
        String className = interfaceClass.getName();
        int i = className.lastIndexOf('.');
        if (i >= 0)
            packageName = className.substring(0, i);
        return packageName;
    }

    private static Byte readTier() {
        String tier = (String) Config.getProperty(Config.PROP_TIER, "" + TIER2);
        if(log.isDebugEnabled())
           log.debug("Initialize Middleware Factory: Setting="+tier);
        return new Byte(tier);
    }


    /** Read the tier setting from the middleware_test.properties file */
    private static Byte readTestTier(String className) {
        PropertyResourceBundle prop = null;
        try {
            prop = ( PropertyResourceBundle )PropertyResourceBundle.getBundle(MIDDLEWARE_RESOURCE);
        } catch (MissingResourceException e) {
            log.warn("Factory Set To Test Mode, But Config File Is Missing!, Mode 2 will be assumed! ", e);
            return new Byte(TIER2);
        }

        try {
            String tier = (String) prop.getObject(className);
            if(tier == null || "123".indexOf(tier) == -1) {
               log.info("Factory Test Mode For Interface '" + className + "' Not Valid. Value is " + tier + ", Mode 2 will be assumed! ");
               tier = "" + TIER2;
			}
            return new Byte(tier);
        } catch (MissingResourceException e) {
            log.info("Factory Set To Test Mode, But Definition For Interface '" + className + "' not found, Mode 2 will be assumed! ");
            return new Byte(TIER2);
        }
    }
}
