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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.soa.services;

import org.apache.log4j.Logger;
import org.jaffa.loader.soa.SoaEventManager;
import org.jaffa.soa.services.configdomain.SoaEventInfo;

/**
 * This class implements the Singleton pattern. Use the getInstance() method to get an instance of this class.
 * The Configuration Service reads the soaEventInfo from SoaEventManager which has been build by JavaConfig. 
 * It provides methods to extract information from the SoaEventManager.
 * 
 * <p>
 * An example configuration file
 *     <?xml version="1.0" encoding="UTF-8"?>
 *     <soa-events>
 *       <soa-event name='NAME1' label='Label1' description='Some description'>
 *         <param name='param1' data-type='STRING' description='Some description'/>
 *         <param name='param2' data-type='STRING' description='Some description'/>
 *         <inject-domain-fact domain-class='com.miro.requitioning.part.domain.PartRequest'>
 *           <domain-key>params.requestId</domain-key>
 *         </inject-domain-fact>
 *       </soa-event>
 *
 *       <soa-event ...>
 *         ...
 *         ...
 *       </soa-event>
 *     </soa-events>
 *
 */
public class ConfigurationService {

    private static final Logger log = Logger.getLogger(ConfigurationService.class);

    private static volatile ConfigurationService c_singleton = null;
    private static SoaEventManager soaEventManager;

    /**
	 * @return the soaEventManager
	 */
	public static SoaEventManager getSoaEventManager() {
		return soaEventManager;
	}

	/**
	 * @param soaEventManager the soaEventManager to set
	 */
	public static void setSoaEventManager(SoaEventManager soaEventManager) {
		ConfigurationService.soaEventManager = soaEventManager;
	}

	/**
     * Creates an instance of ConfigurationService, if not already instantiated.
     * @return An instance of the ConfigurationService.
     */
    public static ConfigurationService getInstance() {
        if (c_singleton == null)
            createConfigurationServiceInstance();
        return c_singleton;
    }

    private static synchronized void createConfigurationServiceInstance() {
        if (c_singleton == null) {
            c_singleton = new ConfigurationService();
            if (log.isDebugEnabled())
                log.debug("An instance of the ConfigurationService has been created");
        }
    }
    
    /**
     * private constructor to control instance.
     */
    private ConfigurationService() {
    }

    /**
     * Returns the SoaEventInfo object for the input soaEventName, as defined in the soa event manager.
     * @param soaEventName the name of a SOA Event.
     * @return the SoaEventInfo object for the input soaEventName, as defined in the soa event manager.
     */
    public SoaEventInfo getSoaEventInfo(String soaEventName) {
		return soaEventManager != null ? soaEventManager.getSoaEventInfo(soaEventName) : null;
    }

    /**
     * Returns all SoaEventInfo objects, as defined in the soa event manager.
     * @return all SoaEventInfo objects, as defined in the soa event manager.
     */
    public SoaEventInfo[] getAllSoaEventInfo() {
		return soaEventManager != null ? soaEventManager.getAllSoaEventInfo(null) : null;
    }

    /**
     * Returns an array of SOA Event names, as defined in the soa event manager.
     * @return an array of SOA Event names, as defined in the soa event manager.
     */
    public String[] getSoaEventNames() {
		return soaEventManager != null ? soaEventManager.getSoaEventNames() : null;
    }
}
