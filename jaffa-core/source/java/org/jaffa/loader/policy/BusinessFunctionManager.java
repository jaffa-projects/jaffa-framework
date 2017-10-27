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

package org.jaffa.loader.policy;

import org.jaffa.loader.ContextKey;
import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.MapRepository;
import org.jaffa.security.businessfunctionsdomain.BusinessFunction;
import org.jaffa.security.businessfunctionsdomain.BusinessFunctions;
import org.jaffa.util.JAXBHelper;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  BusinessFunctionManager
 */
public class BusinessFunctionManager implements IManager {

    /**
     * The name of the configuration file which this class handles.
     */
    private static final String DEFAULT_CONFIGURATION_FILE = "business-functions.xml";

    /**
     * The default location for the jaffa business function configuration file
     */
    private static final String CONFIGURATION_SCHEMA_FILE = "org/jaffa/security/businessfunctionsdomain/business-functions_1_0.xsd";

    private IRepository<BusinessFunction> businessFunctionRepository = new MapRepository<>();

    /**
     * The list of repositories managed by this class
     */
    private IRepository<?>[] managedRepositories = new IRepository<?>[] {businessFunctionRepository};

    /**
     * registerResource - Registers the roles into the business function repository from the business-functions.xml files found in META-INF/roles.xml
     * @param resource the object that contains the xml config file.
     * @param context  key with which config file to be registered.
     * @throws JAXBException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public void registerResource(Resource resource, String context, String variation) throws JAXBException, SAXException, IOException {
        BusinessFunctions businessFunctions = JAXBHelper.unmarshalConfigFile(BusinessFunctions.class, resource, CONFIGURATION_SCHEMA_FILE);
        if (businessFunctions.getBusinessFunction() != null) {
            for (final BusinessFunction businessFunction : businessFunctions.getBusinessFunction()) {
                ContextKey contextKey = new ContextKey(businessFunction.getName(), resource.getURI().toString(), variation, context);
                registerBusinessFunction(contextKey, businessFunction);
            }
        }
    }

    /**
     * getResourceFileName - Returns the default XML file name of the business functions XSD.
     * @return the xml file name
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
    public List<String> getRepositoryNames() {
        List<String> repositoryNames = new ArrayList<>();
        for (IRepository<?> repository : managedRepositories) {
            repositoryNames.add(repository.getName());
        }
        return repositoryNames;
    }

    /**
     * Retrieve an IRepository managed by this IManager via its String name
     * @param name The name of the repository to be retrieved
     * @return The retrieved repository, or null if no matching repository was found.
     */
    @Override
    public IRepository<?> getRepositoryByName(String name) {
        IRepository<?> matchingRepository = null;
        for (IRepository<?> repository : managedRepositories) {
            if (name.equals(repository.getName())) {
                matchingRepository = repository;
            }
        }
        if (matchingRepository == null) {
            matchingRepository = new MapRepository<>();
        }
        return matchingRepository;
    }

    /**
     * getAllBusinessFunctions - Returns a list of all business functions.
     * @return a list of all BusinessFunctions
     */
    public List<BusinessFunction> getAllBusinessFunctions() {
        return getBusinessFunctionRepository().getValues();
    }

    /**
     * getAllBusinessFunctionsStrings - Returns a list of all business functions as a list of strings.
     * @return all business functions as Strings
     */
    public List<String> getAllBusinessFunctionsStrings() {
        List<BusinessFunction> businessFunctions = getAllBusinessFunctions();
        List<String> businessFunctionStrings = new ArrayList<>();
        for (BusinessFunction businessFunction : businessFunctions) {
            businessFunctionStrings.add(businessFunction.getName());
        }
        return businessFunctionStrings;
    }

    /**
     * getBusinessFunction - Returns a business function by name.
     * @param businessFunctionName
     * @return the business function being requested
     */
    public BusinessFunction getBusinessFunction(String businessFunctionName) {
        return businessFunctionRepository.query(businessFunctionName);
    }

    /**
     * registerBusinessFunction - Registers a business function in the business function repository.
     * @param contextKey
     */
    public void registerBusinessFunction(ContextKey contextKey, BusinessFunction businessFunction) {
        getBusinessFunctionRepository().register(contextKey, businessFunction);
    }

    /**
     * unregisterBusinessFunction - Removes a business function from the business function repository.
     * @param contextKey
     */
    public void unregisterBusinessFunction(ContextKey contextKey) {
        getBusinessFunctionRepository().unregister(contextKey);
    }

    /**
     * getBusinessFunctionRepository - Returns an instance oif the business function repository.
     * @return the repository of business functions
     */
    public IRepository<BusinessFunction> getBusinessFunctionRepository() {
        return businessFunctionRepository;
    }

    /**
     * setBusinessFunctionRepository - Allows the business function repository to be set outside of the BusinessFunctionManager class.
     * @param businessFunctionRepository
     */
    public void setBusinessFunctionRepository(IRepository<BusinessFunction> businessFunctionRepository) {
        this.businessFunctionRepository = businessFunctionRepository;
    }
}
