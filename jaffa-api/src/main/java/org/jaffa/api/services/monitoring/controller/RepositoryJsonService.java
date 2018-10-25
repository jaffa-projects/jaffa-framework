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
package org.jaffa.api.services.monitoring.controller;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.jaffa.loader.*;
import org.jaffa.loader.config.ApplicationResourcesManager;
import org.jaffa.loader.config.ApplicationRulesManager;
import org.jaffa.api.services.repository.ApplicationRulesUtilities;
import org.jaffa.rules.meta.MetaDataRepository;
import org.jaffa.util.MessageHelper;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;
import java.util.stream.Collectors;

/**
 * RepositoryJsonService - This class will call the Queue service and serializes the response.
 * @author Matthew Wayles
 * @version 1.0
 */
public class RepositoryJsonService implements IRepositoryJsonService {

    public static final String KEY_BEGINS_WITH = "keyBeginsWith";
    public static final String KEY_BEGIN_WITH = "keyBeginWith";
    public static final String KEY_MATCHES = "keyMatches";
    public static final String BUSINESS_RULES = "org.jaffa.session.BusinessRules";
    public static final String VALUE_KEY = "value";
    public static final String CLASS_META_DATA_KEY = "classMetaData";

    /** The object used to save interesting run-time information. */
    private static Logger logger = Logger.getLogger(RepositoryJsonService.class);


    /**
     * If the user accesses the root of the GIT services, redirect them
     * to the CXF Services page to avoid sending a 404 error
     * @return  A redirect HTTP response to CXF Services
     */
    public Response rootRedirectToServices() {
        return Response
            .status(Response.Status.FOUND)
            .header("Location", "/api/services")
            .build();
    }

    /**
     * Retrieve the ManagerRepositoryService singleton instance
     */
    private Gson gson = new Gson();
    private ManagerRepositoryService managerRepositoryService =
            ManagerRepositoryService.getInstance();

    /**
     * getRepositoryNames() - Returns a JSON list of all currently registered
     * repositories that have been loaded from the ResourceLoader.
     * @return String JSON value of the repository list
     */
    @Override
    public String getRepositoryNames() {
        Map<String, IManager> managerMap = managerRepositoryService.getManagerMap();
        ArrayList<Set> repositoryNames = new ArrayList<>();
        for (Map.Entry<String, IManager> managerEntry : managerMap.entrySet()) {
            IManager manager = managerEntry.getValue();
            Set names = manager.getRepositoryNames();
            repositoryNames.addAll(names);
        }
        return gson.toJson(repositoryNames);
    }

    /**
     * getRepository() - Returns the serialized repository in JSON objects
     * representing the named repository.
     * @param repoName  The repoName of the repository to retrieve data from
     * @param uriInfo includes potential query strings
     * @return String repository data in JSON format
     */
    @Override
    public String getRepository(String repoName, UriInfo uriInfo) {
        Map repository = new HashMap<>();
        Map<String, IManager> managerMap = managerRepositoryService.getManagerMap();

        for (Map.Entry<String, IManager> managerEntry : managerMap.entrySet()) {
            IManager manager = managerEntry.getValue();
            if (manager.getRepositoryNames().contains(repoName)) {
                repository = createRepositoryMap(repoName, manager, uriInfo);
            }
        }

        if (repository.isEmpty()) {
            throw new NotFoundException();
        }
        return gson.toJson(repository);
    }

    /**
     * createRepositoryMap() - Add values to a repositoryMap for access by web services
     * @param name  The repositoryMap name
     * @param manager   The manager hosting the requested repositoryMap
     * @param uriInfo if present, only keys that begin with this will have their
     *                      values retrieved; otherwise, all keys and values
     */
    private Map createRepositoryMap(String name,
                                    IManager manager,
                                    UriInfo uriInfo) {
        Map repositoryMap = new HashMap<>();

        if (ApplicationResourcesManager.APPLICATION_RESOURCES_PROPERTIES.equalsIgnoreCase(name)) {
            Map allResourcesMap = new HashMap(); // to be used for inserting embedded values
            createApplicationResourcesMap(allResourcesMap, manager, null);
            createApplicationResourcesMap(repositoryMap, manager, uriInfo);
            replaceLabelKeysWithValues(repositoryMap);
        }
        else if (ApplicationRulesManager.APPLICATION_RULES_PROPERTIES.equalsIgnoreCase(name)) {
            // collect the rule values
            IRepository repository = manager.getRepositoryByName(name);
            populateMapFromRepository(repositoryMap, repository, uriInfo);

            // Collect the metadata
            HashMap<String, Object> metaDataMap = new HashMap<>();
            ApplicationRulesUtilities rulesUtilities = new ApplicationRulesUtilities();
            try {
                metaDataMap = rulesUtilities.getRuleMetaData(BUSINESS_RULES,
                                                            MetaDataRepository.instance());
            } catch (Exception e) {
                logger.error("Unable to collect application rules - " + e.getMessage());
                // TODO something better
            }
            mergeRuleValuesAndMetaData(repositoryMap, metaDataMap);
        }
        else {
            IRepository repository = manager.getRepositoryByName(name);
            populateMapFromRepository(repositoryMap, repository, uriInfo);
        }
        return repositoryMap;
    }

    /**
     * Merge each rule's value and metadata
     * @param repositoryMap a map with keys that are rule IDs, and values that are rule values
     * @param businessRulesMetaDataMap a map with keys that are rule IDs, and values that are metadata
     */
    private void mergeRuleValuesAndMetaData(Map repositoryMap,
                                            HashMap<String, Object> businessRulesMetaDataMap) {
        Map<String, Object> metaDataMap = businessRulesMetaDataMap;
        // All "real" rules are accumulated under the pseudo-rule "BusinessRules"
        Object businessRulesMapValue = businessRulesMetaDataMap.get("BusinessRules");

        // Replace the simple values with combined values and metadata
        if (businessRulesMapValue instanceof Map) {
            Set<String> mergedKeySet = new HashSet<>();
            Map rulesMetadataMap = (Map)businessRulesMapValue;
            mergedKeySet.addAll(repositoryMap.keySet());
            mergedKeySet.addAll(rulesMetadataMap.keySet());
            for (Object id : mergedKeySet) {
                Object ruleValue = repositoryMap.get(id);
                if (ruleValue == null) {
                    ruleValue = "";
                }
                Object metaData = rulesMetadataMap.get(id);
                Map<String, Object> mergedRule =
                        mergeRuleValueAndMetaData(ruleValue, metaData);
                // overwrite existing value with merged value and metadata
                repositoryMap.put(id.toString(), mergedRule);
            }
        }
    }

    /**
     * Create a label string by replacing embedded label keys
     * with the corresponding label values.
     * @param repositoryMap the map whose labels are being updated
     */
    private void replaceLabelKeysWithValues(Map repositoryMap) {
        Set repoKeys = repositoryMap.keySet();
        for (Object key : repoKeys) {
            Object value = repositoryMap.get(key);

            if (value != null) {
                String label = value.toString();
                label = MessageHelper.replaceTokens(label);
                repositoryMap.put(key, label);
            }
        }
    }

    /**
     * Populates a map with the combined values from the default properties
     * and locale properties repositories
     * @param repositoryMap the map to populate
     * @param manager the repository manager
     * @param uriInfo if present, only keys that begin with this will have their
     *                      values retrieved; otherwise, all keys and values
     */
    private void createApplicationResourcesMap(Map repositoryMap, IManager manager, UriInfo uriInfo) {
        IRepository dRepo = manager.getRepositoryByName(ApplicationResourcesManager.DEFAULT_PROPERTIES);
        populateMapFromRepository(repositoryMap, dRepo, uriInfo);
        // Potentially overwrite generic resources with locale-specific resources
        IRepository lRepo = manager.getRepositoryByName(ApplicationResourcesManager.LOCALE_PROPERTIES);
        populateMapFromRepository(repositoryMap, lRepo, uriInfo);
    }

    /**
     * Populates a map with values from a repository
     * @param repositoryMap the map to populate
     * @param repository the repository whose values will be added to the map
     * @param uriInfo if present, only keys that begin with this will have their
     *                      values retrieved; otherwise, all keys and values
     */
    private void populateMapFromRepository(Map repositoryMap,
                                           IRepository repository,
                                           UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParameters = null;
        Set<String> keySet = null;

        if (uriInfo != null) {
            queryParameters = uriInfo.getQueryParameters();
            keySet = queryParameters.keySet();
        }

        if (validQueryParameters(keySet)) {

            if (keySet != null && !keySet.isEmpty()) {

                if (keySet.contains(KEY_BEGINS_WITH) || keySet.contains(KEY_BEGIN_WITH)) {
                    List<String> keyBeginsWithList = queryParameters.get(KEY_BEGINS_WITH);

                    if (keyBeginsWithList == null || keyBeginsWithList.isEmpty()) {
                        keyBeginsWithList = queryParameters.get(KEY_BEGIN_WITH);
                    }
                    // For now, we only handle "keyBeginsWith"/"keyBeginWith"
                    String beginsWith = keyBeginsWithList.get(0);
                    populateMapKeyBeginsWith(repositoryMap, repository, beginsWith);
                }
                else if (keySet.contains(KEY_MATCHES)) {
                    List<String> keyMatchesWithList = queryParameters.get(KEY_MATCHES);
                    String matchesWith = keyMatchesWithList.get(0);
                    populateMapKeyMatchesWith(repositoryMap, repository, matchesWith);
                }
            }
            else { // default case - return all key-value pairs
                populateMapWithAll(repositoryMap, repository);
            }
        }
        else { // TODO determine requirement
        }
    }

    /**
     * Determine whether the supplied query keys can be handled by our code
     * @param keySet the query keys provided in the URL
     * @return true if the keys are recognized; false otherwise
     */
    private boolean validQueryParameters(Set<String> keySet) {
        boolean isValid = true;

        if (keySet != null) {
            for (String key : keySet) {
                // For now, we only handle "keyBeginsWith"/"keyBeginWith"
                if (!KEY_BEGINS_WITH.equals(key)
                        && !KEY_BEGIN_WITH.equals(key)
                        && !KEY_MATCHES.equals(key)) {
                    logger.warn("Unknown query key: " + key);
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    /**
     * Populates a map with all values from a repository
     * @param repositoryMap the map to populate
     * @param repository the repository whose values will be added to the map
     */
    private void populateMapWithAll(Map repositoryMap, IRepository repository) {
        for (Object repositoryKey : repository.getAllKeys()) {
            ContextKey contextKey = (ContextKey) repositoryKey;
            String contextKeyId = contextKey.getId();
            Object value = repository.query(contextKeyId);
            repositoryMap.put(contextKeyId, value);
        }
    }

    /**
     * Populates a map with values from a repository whose key starts with the
     * provided string
     * @param repositoryMap the map to populate
     * @param repository the repository whose values will be added to the map
     * @param keyBeginsWith only keys that begin with this will have their
     *                      values retrieved
     */
    private void populateMapKeyBeginsWith(Map repositoryMap, IRepository repository, String keyBeginsWith) {
        if (keyBeginsWith != null && !keyBeginsWith.isEmpty()) {
            // Only return key-value pairs whose key starts with the designated string
            Set<String> keyIds = repository.getAllKeyIds();
            Set<String> idSet = keyIds.stream()
                    .filter(id -> id.startsWith(keyBeginsWith))
                    .collect(Collectors.toSet());
            for (String contextKeyId : idSet) {
                Object value = repository.query(contextKeyId);
                repositoryMap.put(contextKeyId, value);
            }
        }
    }

    /**
     * Populates a map with values from a repository whose key starts with the
     * provided string
     * @param repositoryMap the map to populate
     * @param repository the repository whose values will be added to the map
     * @param keyPattern only keys that match with this pattern will have their
     *                      values retrieved
     */
    private void populateMapKeyMatchesWith(Map repositoryMap, IRepository repository, String keyPattern) {
        if (keyPattern != null && !keyPattern.isEmpty()) {
            // Only return key-value pairs whose key starts with the designated string
            Set<String> keyIds = repository.getAllKeyIds();
            Set<String> idSet = keyIds.stream()
                    .filter(id -> id.matches(keyPattern))
                    .collect(Collectors.toSet());
            for (String contextKeyId : idSet) {
                Object value = repository.query(contextKeyId);
                repositoryMap.put(contextKeyId, value);
            }
        }
    }

    /**
     * getRepositoryValue() - Returns the serialized value of the provided query
     * key from the named repository.
     * @param name  The name of the repository to retrieve the data from
     * @param id    The ID of the key whose value is to be retrieved
     * @return  The corresponding map value
     */
    @Override
    public String getRepositoryValue(String name, String id) {
        Object queryResponse = null;
        Map<String, IManager> managerMap = managerRepositoryService.getManagerMap();
        for (Map.Entry<String, IManager> managerEntry : managerMap.entrySet()) {
            IManager manager = managerEntry.getValue();
            if (manager.getRepositoryNames().contains(name)) {
                queryResponse = getQueryResponse(name, id, manager);
                break;
            }
        }
        if (queryResponse == null) {
            throw new NotFoundException();
        }
        return gson.toJson(queryResponse);
    }

    /**
     * Returns the serialized value of the provided query
     * key from the named repository.
     * @param name  The name of the repository to retrieve the data from
     * @param id    The ID of the key whose value is to be retrieved
     * @param manager The manager to use to find the value
     * @return  The corresponding map value
     */
    private Object getQueryResponse(String name, String id, IManager manager) {
        Object queryResponse = null;

        // Application resources may come from multiple repositories
        if (ApplicationResourcesManager.APPLICATION_RESOURCES_PROPERTIES.equalsIgnoreCase(name)) {
            // Locale-specific resources take precedence over generic resources
            queryResponse = getResponseFromRepository(ApplicationResourcesManager.LOCALE_PROPERTIES, id, manager);
            // If there is no locale-specific resource, get the default resource
            if (queryResponse == null) {
                queryResponse = getResponseFromRepository(ApplicationResourcesManager.DEFAULT_PROPERTIES, id, manager);
            }
            if (queryResponse != null) {
                queryResponse = MessageHelper.replaceTokens(queryResponse.toString());
            }
        }
        else if (ApplicationRulesManager.APPLICATION_RULES_PROPERTIES.equalsIgnoreCase(name)) {
            Object value = getResponseFromRepository(name, id, manager);
            ApplicationRulesUtilities rulesUtilities = new ApplicationRulesUtilities();
            Map<String, Object> propertyMetaData = new HashMap<>();
            try {
                propertyMetaData =
                        rulesUtilities.addPropertyMetaData(BUSINESS_RULES, id,null, propertyMetaData);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);  // TODO something better?
            }
            queryResponse = mergeRuleValueAndMetaData(value, propertyMetaData.get(id));
        }
        else { // normal case - one repository to search
            queryResponse = getResponseFromRepository(name, id, manager);
        }
        return queryResponse;
    }

    /**
     * Combines a rule's value and metadata into a single map
     * @param value the value for the rule
     * @param metaData the metadata associated with the rule, if any
     * @return a map with two keys - "value" and "metaData".
     */
    private Map<String, Object> mergeRuleValueAndMetaData(Object value, Object metaData) {
        Map<String, Object> oneRule = new HashMap<>();
        oneRule.put(VALUE_KEY, value);
        if (metaData != null) {
            oneRule.put(CLASS_META_DATA_KEY, metaData);
        }
        return oneRule;
    }

    /**
     * Returns the serialized value of the provided query
     * key from the named repository.
     * @param name  The name of the repository to retrieve the data from
     * @param id    The ID of the key whose value is to be retrieved
     * @param manager The manager to use to find the value
     * @return  The corresponding map value
     */
    private Object getResponseFromRepository(String name, String id, IManager manager) {
        Object queryResponse = null;
        IRepository repository = manager.getRepositoryByName(name);
        ContextKey key = repository.findKey(id);
        if (key != null) {
            queryResponse = repository.query(id);
        }
        return queryResponse;
    }


}