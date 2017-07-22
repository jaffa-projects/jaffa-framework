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

package org.jaffa.loader;

import org.jaffa.transaction.services.configdomain.Config;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.transaction.services.configdomain.TypeInfo;
import org.jaffa.util.JAXBHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.xml.sax.SAXException;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for managing the "jaffa-transaction-config.xml".
 * This class has all the interface methods where consumer can invoke register /unregister the config objects
 * as well as for the xml files.
 */
public class TransactionManager implements IManager {

    /**
     * The name of the configuration file which this class handles.
     */
    private static final String DEFAULT_CONFIGURATION_FILE = "jaffa-transaction-config.xml";
    /**
     * The location of the schema for the configuration file.
     */
    private static final String CONFIGURATION_SCHEMA_FILE = "org/jaffa/transaction/services/configdomain/jaffa-transaction-config_1_0.xsd";

    private IRepository<String, TransactionInfo> transactionRepository;
    private IRepository<String, TypeInfo> typeInfoRepository;

    @Autowired
    @Qualifier("contextOrder")
    public ArrayList<String> contextOrder;

    /**
     * register TransactionInfo to the repository
     * @param dataBeanClassName key for the repository to be used for registering
     * @param transactionInfo registers to the repository
     * @param context with which repository to be associated with
     */
    public void registerTransactionInfo(String dataBeanClassName, TransactionInfo transactionInfo, String context) {
        transactionRepository.register(dataBeanClassName, transactionInfo, context);

    }

    /**
     * register TypeInfo to the repository
     * @param typeInfo registers to the repository
     * @param context with which repository to be associated with
     */
    public void registerTypeInfo(TypeInfo typeInfo, String context) {
        typeInfoRepository.register(typeInfo.getName(), typeInfo, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerXML(Resource resource, String context) throws JAXBException, SAXException, IOException {

        Config config = JAXBHelper.unmarshalConfigFile(Config.class, resource, CONFIGURATION_SCHEMA_FILE);

        if (config.getTransactionOrType() != null) {
            for (final Object o : config.getTransactionOrType()) {
                if (o.getClass() == TransactionInfo.class) {
                    final TransactionInfo transactionInfo = (TransactionInfo) o;
                    registerTransactionInfo(transactionInfo.getDataBean(), transactionInfo, context);
                } else if (o.getClass() == TypeInfo.class) {
                    final TypeInfo typeInfo = (TypeInfo) o;
                    registerTypeInfo(typeInfo, context);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getXmlFileName() {
        return DEFAULT_CONFIGURATION_FILE;
    }

    /**
     * un register TransactionInfo from the repository
     * @param dataBeanClassName key for the repository to be used for registering
     * @param context with which repository to be associated with
     */
    public void unregisterTransactionInfo(String dataBeanClassName, String context) {
        transactionRepository.unregister(dataBeanClassName, context);
    }

    /**
     * un register TypeInfo from the repository
     * @param typeInfoName key for the repository to be used for registering
     * @param context with which repository to be associated with
     */
    public void unregisterTypeInfo(String typeInfoName, String context) {
        typeInfoRepository.unregister(typeInfoName, context);
    }

    /**
     * unregisters all the transactions and typeInfo in the xml from the repository
     * @param uri for the xml location
     */
    public void unregisterXML(String uri) {
        //read XML
        //parse XML
        //call unregisterTransactionInfo
        //call unregisterTypeInfo
    }

    /**
     * retrives the TransactionInfo from the repository
     * @param dataBeanClassName key used for the repository
     * @param contextOrderParam Order of the contexts used for retrieval
     * @return TransactionInfo
     */
    public TransactionInfo getTransactionInfo(String dataBeanClassName, List<String> contextOrderParam) {
        if (contextOrderParam == null)
            contextOrderParam = contextOrder;

        return transactionRepository.query(dataBeanClassName, contextOrderParam);
    }

    /**
     * retrives the TypeInfo from the repository
     * @param typeName key used for the repository
     * @param contextOrderParam Order of the contexts used for retrieval
     * @return TypeInfo
     */
    public TypeInfo getTypeInfo(String typeName, List<String> contextOrderParam) {
        if (contextOrderParam == null)
            contextOrderParam = contextOrder;

        return typeInfoRepository.query(typeName, contextOrderParam);
    }

    /**
     * retrieves all the TransactionInfo from the repository based on the contextOrder provided
     * assumes defaultContextOrder from the configuration when contextOrder is not provided
     * @param contextOrderParam order of the contexts to be searched
     * @return List of all values
     */
    public TransactionInfo[] getAllTransactionInfo(List<String> contextOrderParam) {
        if (contextOrderParam == null)
            contextOrderParam = contextOrder;

        return transactionRepository.getAllValues(contextOrderParam).toArray(new TransactionInfo[0]);
    }

    /**
     * retrieves all the Type Names in the repository
     * @return Set of all Type Names
     */
    public String[] getTypeNames() {
        return typeInfoRepository.getAllKeys().toArray(new String[0]);
    }


    /**
     * gets the contextOrder
     * @return List containing contextOrder
     */
    public List<String> getContextOrder() {
        return contextOrder;
    }


    /**
     * Returns the TransactionInfo object for the input dataBeanClass, as defined in the configuration file.
     *
     * @param dataBeanClass the class for a dataBean.
     * @return the TransactionInfo object for the input dataBeanClass, as defined in the configuration file.
     */
    public TransactionInfo getTransactionInfo(Class dataBeanClass, List<String> contextOrder) {
        final String dataBeanClassName = dataBeanClass.getName();
        TransactionInfo transactionInfo = getTransactionInfo(dataBeanClassName, contextOrder);
        if (transactionInfo == null) {
            // Lookup the class heirarchy. Add a NULL for the dataBeanClassName, even if a TransactionInfo is not found
            while (dataBeanClass.getSuperclass() != null) {
                dataBeanClass = dataBeanClass.getSuperclass();
                transactionInfo = getTransactionInfo(dataBeanClass.getName(), contextOrder);
                if (transactionInfo != null)
                    break;
            }
            registerTransactionInfo(dataBeanClassName, transactionInfo, null);
        }
        return transactionInfo;
    }

    /**
     *
     * @param contextOrder
     */
    public void setContextOrder(ArrayList<String> contextOrder) {
        this.contextOrder = contextOrder;
    }

    /**
     *
     * @return
     */
    public IRepository<String, TransactionInfo> getTransactionRepository() {
        return transactionRepository;
    }

    /**
     *
     * @param transactionRepository
     */
    public void setTransactionRepository(IRepository<String, TransactionInfo> transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     *
     * @return
     */
    public IRepository<String, TypeInfo> getTypeInfoRepository() {
        return typeInfoRepository;
    }

    /**
     *
     * @param typeInfoRepository
     */
    public void setTypeInfoRepository(IRepository<String, TypeInfo> typeInfoRepository) {
        this.typeInfoRepository = typeInfoRepository;
    }
}
