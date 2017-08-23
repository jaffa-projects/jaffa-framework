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

import org.apache.log4j.Logger;
import org.jaffa.loader.transaction.TransactionManager;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.transaction.services.configdomain.TypeInfo;

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
    private static volatile ConfigurationService c_singleton;
    private static TransactionManager transactionManager;

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
        return transactionManager.getTransactionInfo(dataBeanClassName);
    }

    /**
     * Returns the TransactionInfo object for the input dataBeanClass, as defined in the configuration file.
     * @param dataBeanClass the class for a dataBean.
     * @return the TransactionInfo object for the input dataBeanClass, as defined in the configuration file.
     */
    public TransactionInfo getTransactionInfo(Class dataBeanClass) {
        return transactionManager.getTransactionInfo(dataBeanClass);
    }

    /**
     * Returns all TransactionInfo objects, as defined in the configuration file.
     * @return all TransactionInfo objects, as defined in the configuration file.
     */
    public TransactionInfo[] getAllTransactionInfo() {
        return transactionManager.getAllTransactionInfo();
    }

    /** 
     * Returns the TypeInfo object for the input typeName, as defined in the configuration file.
     * @param typeName the type name.
     * @return the TypeInfo object for the input typeName, as defined in the configuration file.
     */
    public TypeInfo getTypeInfo(String typeName) {
        return transactionManager.getTypeInfo(typeName);
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
        return transactionManager.getTypeNames();
    }

    public static TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public static void setTransactionManager(TransactionManager transactionManager) {
        ConfigurationService.transactionManager = transactionManager;
    }
}