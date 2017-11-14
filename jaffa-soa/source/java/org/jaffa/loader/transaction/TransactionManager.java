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

package org.jaffa.loader.transaction;

import org.jaffa.loader.ContextKey;
import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.MapRepository;
import org.jaffa.transaction.services.configdomain.Config;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.transaction.services.configdomain.TypeInfo;
import org.jaffa.util.JAXBHelper;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.xml.sax.SAXException;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

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

    /** Create the TransactionInfo and TypeInfo repositories */
    private IRepository<TransactionInfo> transactionRepository = new MapRepository<>("TransactionInfo");
    private IRepository<TypeInfo> typeInfoRepository = new MapRepository<>("TypeInfo");

    /**
     * The list of repositories managed by this class
     */
    private HashMap managedRepositories = new HashMap<String, IRepository>() {
        {
            put(transactionRepository.getName(), transactionRepository);
            put(typeInfoRepository.getName(), typeInfoRepository);
        }

    };

    /**
     * register TransactionInfo to the repository
     * @param transactionInfo registers to the repository
     * @param contextKey with which repository to be associated with
     */
    public void registerTransactionInfo(ContextKey contextKey, TransactionInfo transactionInfo) {
        transactionRepository.register(contextKey, transactionInfo);

    }

    /**
     * register TypeInfo to the repository
     * @param typeInfo registers to the repository
     * @param contextKey with which repository to be associated with
     */
    public void registerTypeInfo(ContextKey contextKey, TypeInfo typeInfo) {
        typeInfoRepository.register(contextKey, typeInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerResource(Resource resource, String context, String variation) throws JAXBException, SAXException, IOException {

        Config config = JAXBHelper.unmarshalConfigFile(Config.class, resource, CONFIGURATION_SCHEMA_FILE);

        if (config.getTransactionOrType() != null) {
            for (final Object o : config.getTransactionOrType()) {
                if (o.getClass() == TransactionInfo.class) {
                    final TransactionInfo transactionInfo = (TransactionInfo) o;
                    ContextKey contextKey = new ContextKey(transactionInfo.getDataBean(), resource.getURI().toString(), variation, context);
                    registerTransactionInfo(contextKey, transactionInfo);
                } else if (o.getClass() == TypeInfo.class) {
                    final TypeInfo typeInfo = (TypeInfo) o;
                    ContextKey contextKey = new ContextKey(typeInfo.getName(), resource.getURI().toString(), variation, context);
                    registerTypeInfo(contextKey, typeInfo);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceFileName() {
        return DEFAULT_CONFIGURATION_FILE;
    }

    /**
     * getRepositoryNames - Retrieve a String list of all the IRepositories managed by this IManager
     * @return A list of repository names managed by this manager
     */
    @Override
    public Set getRepositoryNames() {
        return managedRepositories.keySet();
    }

    /**
     * Retrieve an IRepository managed by this IManager via its String name
     * @param name The name of the repository to be retrieved
     * @return The retrieved repository, or empty if no matching repository was found.
     */
    @Override
    public IRepository<?> getRepositoryByName(String name) {
        return (IRepository<?>) managedRepositories.get(name);
    }

    /**
     * un register TransactionInfo from the repository
     * @param contextKey with which repository to be associated with
     */
    public void unregisterTransactionInfo(ContextKey contextKey) {
        transactionRepository.unregister(contextKey);
    }

    /**
     * un register TypeInfo from the repository
     * @param contextKey with which repository to be associated with
     */
    public void unregisterTypeInfo(ContextKey contextKey) {
        typeInfoRepository.unregister(contextKey);
    }

    /**
     * unregisters all the transactions and typeInfo in the xml from the repository
     * @param uri for the xml location
     */
    public void unregisterXML(String uri, String context, String variation) throws JAXBException, SAXException, IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Config config = JAXBHelper.unmarshalConfigFile(Config.class, resolver.getResource(uri), CONFIGURATION_SCHEMA_FILE);

        if (config.getTransactionOrType() != null) {
            for (final Object o : config.getTransactionOrType()) {
                if (o.getClass() == TransactionInfo.class) {
                    final TransactionInfo transactionInfo = (TransactionInfo) o;
                    ContextKey contextKey = new ContextKey(transactionInfo.getDataBean(), uri, variation, context);
                    unregisterTransactionInfo(contextKey);
                } else if (o.getClass() == TypeInfo.class) {
                    final TypeInfo typeInfo = (TypeInfo) o;
                    ContextKey contextKey = new ContextKey(typeInfo.getName(), uri, variation, context);
                    unregisterTypeInfo(contextKey);
                }
            }
        }
    }

    /**
     * retrives the TransactionInfo from the repository
     * @param dataBeanClassName key used for the repository
     * @return TransactionInfo
     */
    public TransactionInfo getTransactionInfo(String dataBeanClassName) {
        return transactionRepository.query(dataBeanClassName);
    }

    /**
     * retrives the TypeInfo from the repository
     * @param typeName key used for the repository
     * @return TypeInfo
     */
    public TypeInfo getTypeInfo(String typeName) {
        return typeInfoRepository.query(typeName);
    }

    /**
     * retrieves all the TransactionInfo from the repository based on the contextOrder provided
     * assumes defaultContextOrder from the configuration when contextOrder is not provided
     * @return List of all values
     */
    public TransactionInfo[] getAllTransactionInfo() {
        return transactionRepository.getValues().toArray(new TransactionInfo[0]);
    }

    /**
     * retrieves all the Type Names in the repository
     * @return Set of all Type Names
     */
    public String[] getTypeNames() {
        return typeInfoRepository.getKeyIds().toArray(new String[0]);
    }


    /**
     * Returns the TransactionInfo object for the input dataBeanClass, as defined in the configuration file.
     *
     * @param dataBeanClass the class for a dataBean.
     * @return the TransactionInfo object for the input dataBeanClass, as defined in the configuration file.
     */
    public TransactionInfo getTransactionInfo(Class dataBeanClass) {
        final String dataBeanClassName = dataBeanClass.getName();
        TransactionInfo transactionInfo = getTransactionInfo(dataBeanClassName);
        if (transactionInfo == null) {
            // Lookup the class heirarchy. Add a NULL for the dataBeanClassName, even if a TransactionInfo is not found
            ContextKey superClassContextKey = null;
            while (dataBeanClass.getSuperclass() != null) {
                dataBeanClass = dataBeanClass.getSuperclass();
                transactionInfo = getTransactionInfo(dataBeanClass.getName());
                superClassContextKey = getTransactionRepository().findKey(dataBeanClass.getName());
                if (transactionInfo != null)
                    break;
            }
            if(superClassContextKey!=null) {
                registerTransactionInfo(new ContextKey(transactionInfo.getDataBean(), superClassContextKey.getFileName()
                        ,superClassContextKey.getVariation(), superClassContextKey.getPrecedence()), transactionInfo);
            }
        }
        return transactionInfo;
    }

    /**
     *
     * @return
     */
    public IRepository<TransactionInfo> getTransactionRepository() {
        return transactionRepository;
    }

    /**
     *
     * @param transactionRepository
     */
    public void setTransactionRepository(IRepository<TransactionInfo> transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     *
     * @return
     */
    public IRepository<TypeInfo> getTypeInfoRepository() {
        return typeInfoRepository;
    }

    /**
     *
     * @param typeInfoRepository
     */
    public void setTypeInfoRepository(IRepository<TypeInfo> typeInfoRepository) {
        this.typeInfoRepository = typeInfoRepository;
    }
}
