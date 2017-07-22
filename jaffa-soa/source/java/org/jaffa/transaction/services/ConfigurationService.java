/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2015 JAFFA Development Group
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
package org.jaffa.transaction.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.log4j.Logger;
import org.jaffa.transaction.services.configdomain.Config;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.transaction.services.configdomain.TypeInfo;
import org.jaffa.util.JAXBHelper;
import org.jaffa.util.URLHelper;
import org.xml.sax.SAXException;

/**
 * This class implements the Singleton pattern. Use the getInstance() method to get an instance of this class.
 * The Configuration Service reads the 'resources/jaffa-transaction-config.xml' file. It then performs the initializations.
 * It provides methods to extract information from the configuration file.
 * <p>
 * An example configuration file
 *     <?xml version="1.0" encoding="UTF-8"?>
 *     <config>
 *       <transaction
 *         dataBean='com.example.ADataBean'
 *         type='AType'
 *         subType='ASubType'
 *         toClass='com.example.AHandlerClass'
 *         toMethod='aHandlerMethod'
 *         description='Some Description'
 *       >
 *         <lock-check>
 *           <param name='name1'>value1</param>
 *           <param expression='true' name='name2'>bean.aProperty</param>
 *         </lock-check>
 *         <header>
 *           <param name='correlationType' loggingName='CorrelationType'>ACorrelationType</param>
 *           <param expression='true' name='description'>bean.description</param>
 *         </header>
 *       </transaction>
 *
 *       <transaction ...>
 *         ...
 *         ...
 *       </transaction>
 *
 *       <type name='AType' description='Some Description'>
 *         <display-param name='correlationType' label='[label.CorrelationType]'/>
 *         <display-param name='jaffa_userId' label='[label.UserId]'/>
 *         <security
 *           browse="Transactions.AType.Browse"
 *           admin="Transactions.AType.Admin"
 *         />
 *       </type>
 *
 *       <type ...>
 *         ...
 *         ...
 *       </type>
 *     </config>
 *
 */
public final class ConfigurationService {

    private static final Logger log = Logger.getLogger(ConfigurationService.class);
    /** The location of the configuration file. */
    private static final String DEFAULT_CONFIGURATION_FILE = "resources/jaffa-transaction-config.xml";
    private static final String CONFIGURATION_FILE = System.getProperty(ConfigurationService.class.getName(), DEFAULT_CONFIGURATION_FILE);
    /** The location of the schema for the configuration file. */
    private static final String CONFIGURATION_SCHEMA_FILE = "org/jaffa/transaction/services/configdomain/jaffa-transaction-config_1_0.xsd";
    /** The singleton instance of this class. */
    private static volatile ConfigurationService c_singleton;
    /** Map of dataBeanClassName and corresponding TransactionInfo instance. */
    private final Map<String, TransactionInfo> m_transactionInfoMap = new LinkedHashMap<>();
    /** Map of typeName and corresponding TypeInfo instance. */
    private final Map<String, TypeInfo> m_typeInfoMap = new LinkedHashMap<>();

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
     * Parses the configuration file.
     * <p>
     * A RuntimeException is thrown
     *   - If the configuration file is not found
     *   - If the configuration file has invalid XML
     */
    private ConfigurationService() {
        try {
            // un-marshal the configuration file
            final Config m_config = parseConfigurationFile();

            // build up the maps/list
            if (m_config.getTransactionOrType() != null) {
                for (final Object o : m_config.getTransactionOrType()) {
                    if (o.getClass() == TransactionInfo.class) {
                        final TransactionInfo transactionInfo = (TransactionInfo) o;
                        m_transactionInfoMap.put(transactionInfo.getDataBean(), transactionInfo);
                    } else if (o.getClass() == TypeInfo.class) {
                        final TypeInfo typeInfo = (TypeInfo) o;
                        m_typeInfoMap.put(typeInfo.getName(), typeInfo);
                    }
                }
            }
        } catch (JAXBException e) {
            final String s = "Error in parsing the configuration file " + CONFIGURATION_FILE;
            log.fatal(s, e);
            throw new RuntimeException(s, e);
        } catch (MalformedURLException e) {
            final String s = "Error in locating the configuration file " + CONFIGURATION_FILE;
            log.fatal(s, e);
            throw new RuntimeException(s, e);
        } catch (SAXException e) {
            final String s = "Error in loading the schema for the configuration file " + CONFIGURATION_SCHEMA_FILE;
            log.fatal(s, e);
            throw new RuntimeException(s, e);
        }
    }

    /**
     * Returns the TransactionInfo object for the input dataBean, as defined in the configuration file.
     * @param dataBean the dataBean.
     * @return the TransactionInfo object for the input dataBean, as defined in the configuration file.
     */
    public TransactionInfo getTransactionInfo(Object dataBean) {
        return getTransactionInfo(dataBean.getClass());
    }

    /**
     * Returns the TransactionInfo object for the input dataBeanClassName, as defined in the configuration file.
     * @param dataBeanClassName the class name for a dataBean.
     * @return the TransactionInfo object for the input dataBeanClassName, as defined in the configuration file.
     * @throws ClassNotFoundException if dataBeanClassName is not found on the classpath
     */
    public TransactionInfo getTransactionInfo(String dataBeanClassName) throws ClassNotFoundException {
        return getTransactionInfo(Class.forName(dataBeanClassName));
    }

    /**
     * Returns the TransactionInfo object for the input dataBeanClass, as defined in the configuration file.
     * @param dataBeanClass the class for a dataBean.
     * @return the TransactionInfo object for the input dataBeanClass, as defined in the configuration file.
     */
    public TransactionInfo getTransactionInfo(Class dataBeanClass) {
        final String dataBeanClassName = dataBeanClass.getName();
        TransactionInfo transactionInfo = m_transactionInfoMap.get(dataBeanClassName);
        if (transactionInfo == null && !m_transactionInfoMap.containsKey(dataBeanClassName)) {
            // Lookup the class heirarchy. Add a NULL for the dataBeanClassName, even if a TransactionInfo is not found
            synchronized (m_transactionInfoMap) {
                transactionInfo = m_transactionInfoMap.get(dataBeanClassName);
                if (transactionInfo == null && !m_transactionInfoMap.containsKey(dataBeanClassName)) {
                    while (dataBeanClass.getSuperclass() != null) {
                        dataBeanClass = dataBeanClass.getSuperclass();
                        transactionInfo = m_transactionInfoMap.get(dataBeanClass.getName());
                        if (transactionInfo != null)
                            break;
                    }
                    m_transactionInfoMap.put(dataBeanClassName, transactionInfo);
                }
            }
        }
        return transactionInfo;
    }

    /**
     * Returns all TransactionInfo objects, as defined in the configuration file.
     * @return all TransactionInfo objects, as defined in the configuration file.
     */
    public TransactionInfo[] getAllTransactionInfo() {
        return m_transactionInfoMap.values().toArray(new TransactionInfo[m_transactionInfoMap.size()]);
    }

    /** 
     * Returns the TypeInfo object for the input typeName, as defined in the configuration file.
     * @param typeName the type name.
     * @return the TypeInfo object for the input typeName, as defined in the configuration file.
     */
    public TypeInfo getTypeInfo(String typeName) {
        return m_typeInfoMap.get(typeName);
    }

    /**
     * Returns true if the input Transaction type is a singleton
     *
     * @param typeName the input type to check
     * @return true if the input type is a singleton
     */
    public boolean isTypeSingleton(final String typeName) {
        final TypeInfo typeInfo = getTypeInfo(typeName);
        return (typeInfo != null) && (typeInfo.getSingleton() != null) && typeInfo.getSingleton();
    }

    /**
     * Returns an array of type names, as defined in the configuration file.
     * @return an array of type names, as defined in the configuration file.
     */
    public String[] getTypeNames() {
        return m_typeInfoMap.keySet().toArray(new String[m_typeInfoMap.size()]);
    }

    /**
     * Loads the configurationFile using JAXB.
     * The XML is validated as per the schema 'org/jaffa/transaction/services/configdomain/jaffa-transaction-config_1_0.xsd'.
     * The XML is then parsed to return a corresponding Java object.
     * @return the Java representation of the XML inside the configuration file.
     * @throws MalformedURLException if the configuration file is not found.
     * @throws JAXBException if any error occurs during the unmarshalling of XML.
     * @throws SAXException if the schema file cannot be loaded.
     */
    private Config parseConfigurationFile()
            throws MalformedURLException, JAXBException, SAXException {
        if (log.isDebugEnabled())
            log.debug("Un-marshalling the configuration file " + CONFIGURATION_FILE);
        final URL configFileUrl = URLHelper.newExtendedURL(CONFIGURATION_FILE);
        final URL configSchemaFileUrl = URLHelper.newExtendedURL(CONFIGURATION_SCHEMA_FILE);
        final JAXBContext jc = JAXBHelper.obtainJAXBContext(Config.class);
        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        final Schema schema = sf.newSchema(configSchemaFileUrl);
        unmarshaller.setSchema(schema);
        return (Config) unmarshaller.unmarshal(configFileUrl);
    }
}