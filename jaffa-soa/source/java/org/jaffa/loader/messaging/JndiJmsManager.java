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

package org.jaffa.loader.messaging;

import org.jaffa.loader.ContextKey;
import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.MapRepository;
import org.jaffa.modules.messaging.services.ConfigurationService;
import org.jaffa.modules.messaging.services.configdomain.JmsConfig;
import org.jaffa.modules.messaging.services.configdomain.JndiConfig;
import org.jaffa.util.JAXBHelper;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;


/**
 * A class that manages JNDI JMS specifications, as read in from a
 * configuration file.
 * Created by kcassell on 7/29/2017.
 */
public class JndiJmsManager implements IManager {

    /**
     * The location of the configuration file.
     */
    private static final String DEFAULT_JMS_JNDI_CONFIGURATION_FILE =
            "resources/jms-jndi-config.xml";

    private String jmsJndiConfigurationFile =
            System.getProperty(ConfigurationService.class.getName(),
                    DEFAULT_JMS_JNDI_CONFIGURATION_FILE);

    /**
     * The location of the schema for the configuration file.
     */
    private static final String JMS_JNDI_CONFIGURATION_SCHEMA_FILE =
            "org/jaffa/modules/messaging/services/configdomain/jms-jndi" +
                    "-config_1_0.xsd";

    /**
     * The JNDI repository.  The key is the name;
     * the value in the JndiConfig object.
     */
    private IRepository<JmsConfig> jmsRepository =
            new MapRepository<>();


    /**
     * Unmarshall the contents of the configuration to create and register
     * JmsConfig objects.
     * @param resource the object that contains the xml config file.
     * @param context key with which config file to be registered.
     * @param variation key with which config file to be registered.
     * @throws JAXBException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public void registerResource(Resource resource, String context, String variation)
            throws JAXBException, SAXException, IOException {
        JndiConfig config = JAXBHelper.unmarshalConfigFile(JndiConfig.class, resource,
                JMS_JNDI_CONFIGURATION_SCHEMA_FILE);
        JmsConfig jmsConfig = config.getJmsConfig();
        ContextKey contextKey = new ContextKey(jmsConfig.getUser(), resource.getURI().toString(), variation, context);
        jmsRepository.register(contextKey, jmsConfig);
    }

    @Override
    public String getResourceFileName() {
        return jmsJndiConfigurationFile;
    }

    public IRepository<JmsConfig> getJmsRepository() {
        return jmsRepository;
    }

    public void setJmsRepository(IRepository<JmsConfig> repo) {
        this.jmsRepository = repo;
    }

    /**
     * Get the JmsConfig object from the repository
     * @return the single config object
     */
    public JmsConfig getJmsConfig() {
        //There should be only one entry of jmsJndiConfig
        return jmsRepository.getAllValues()!=null && jmsRepository.getAllValues().size() > 0 ? jmsRepository.getAllValues().get(0) : null;
    }
}
