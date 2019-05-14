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
package org.jaffa.components.finder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.NoDefaultException;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.session.IContextManager;

public class QuerySaver {

  protected static final String S_QUERIES = "queries";
  protected static final String S_USERPREF = "savedQueries";
  protected static final String S_QUERY = "query";
  protected static final String S_NAME = "name";
  protected static final String S_DATA = "data";
  protected static final String S_SHORTCUTS = "shortcuts";
  protected static final String S_DEFAULT = "default";

  private Logger log = Logger.getLogger(this.getClass());

  private final IContextManager m_contextManager;

  QuerySaver(IContextManager contextManager) {
    m_contextManager = contextManager;
  }

  /**
   *
   * @param queryName Name of the query
   * @param componentName Name of the component
   * @param parameters Map of the query parameters
   * @param isDefault declares whether this query should be the default.  This overrides any previous defaults
   * @param isShortCut declares whether this query should be a shortcut
   * @return the id of the query
   * @throws ApplicationExceptions
   *
   * Saves a query using the IContextManager defined in the constructor
   */
  String saveQuery(String queryName,
      String componentName,
      Map parameters,
      boolean isDefault,
      boolean isShortCut)
      throws IOException {

    Map savedQueries = getSavedQueryList(componentName);

    if (savedQueries != null && savedQueries.containsValue(queryName)) {

      Iterator savedQueriesIter = savedQueries.keySet().iterator();
      while (savedQueriesIter.hasNext()) {
        String id  = (String) savedQueriesIter.next();
        String value = (String)savedQueries.get(id);
        if (value.equals(queryName)) {
          removeSavedQuery(componentName, id);
          break;
        }
      }
    }

    String getQueriesString = buildPropertyString(componentName,new String[] {S_QUERIES});
    QueryIdList idList = new QueryIdList((String) m_contextManager.getProperty(getQueriesString));
    String nextId = idList.nextAvailable();

    String queryDataString = encodeQueryDataMap(parameters);
    log.debug("Encoded Query Parameters="+queryDataString);

    Properties p = new Properties();
    p.put(buildPropertyString(componentName,(new String[] {S_QUERIES})), idList.toString());
    p.put(buildPropertyString(componentName,(new String[] {S_QUERY, nextId, S_NAME})), queryName);
    p.put(buildPropertyString(componentName,(new String[] {S_QUERY, nextId, S_DATA})), queryDataString);

    // Need to do default
    if(isDefault) {
      p.put(buildPropertyString(componentName,(new String[] {S_QUERY, S_DEFAULT})), nextId);
    }

    // Need to do shortcut
    if (isShortCut) {
      String queryShortCuts =
          buildPropertyString(null, new String[] {S_QUERY,S_SHORTCUTS});
      String shortCuts = (String)m_contextManager.getProperty(queryShortCuts);
      String thisShortCut = componentName + "." + S_QUERY + "." + nextId;
      if (shortCuts == null) {
        shortCuts = thisShortCut;
      } else {
        shortCuts = shortCuts + "," + thisShortCut;
      }
      p.put(buildPropertyString(null,(new String[] {S_QUERY, S_SHORTCUTS})), shortCuts);
    }

    m_contextManager.setUserPreferences(p);

    return nextId;
  }

  /**
   *
   * @param componentName Name of the component
   * @return a map containing queryId and queryName
   *
   * Gets the names of all saved queries for a component
   */
  Map getSavedQueryList(String componentName) {
    Map retVal = new HashMap();

    String queriesString =
        buildPropertyString(componentName, new String[] {S_QUERIES});
    String queryIds = (String) m_contextManager.getProperty(queriesString);
    if (queryIds != null) {
      StringTokenizer queryIdsTokenizer = new StringTokenizer(queryIds,
          ",");
      while (queryIdsTokenizer.hasMoreTokens()) {
        String queryId = queryIdsTokenizer.nextToken();

        String getNameString = buildPropertyString(componentName,
            new String[] { S_QUERY, queryId, S_NAME });
        String nameForQuery = (String) m_contextManager
            .getProperty(getNameString);

        retVal.put(queryId, nameForQuery);
      }
    }

    return retVal;
  }

  /**
   *
   * @param componentName Name of the component
   * @param queryId id of query
   * @return contains map of fields and values
   *
   * Gets the fields and corresponding values for a numbered query
   */
  Map getSavedQueryFields( String componentName, String queryId) {
    Map retVal = null;
    String queryDataString =
        buildPropertyString(componentName, new String[] {S_QUERY, queryId, S_DATA});
    String dataString = (String) m_contextManager.getProperty(queryDataString);

    if (dataString != null)
      retVal = this.decodeQueryDataMap(dataString);

    return retVal;
  }

  String getSavedQueryName( String componentName, String queryId) {
    String queryDataString =
        buildPropertyString(componentName, new String[] {S_QUERY, queryId, S_NAME});
    String retVal = (String) m_contextManager.getProperty(queryDataString);

    return retVal;
  }

  /**
   *
   * @param componentName name of component
   * @return containing field and value pairs
   *
   * Returns the the fields and values for the default query for a component
   * @throws NoDefaultException
   *
   */
  Map getDefaultQueryFields( String componentName ) throws NoDefaultException {
    String queryId = getDefaultQueryId(componentName);

    if (queryId == null || queryId.length() == 0) {
      throw new NoDefaultException("Component " + componentName + " has no default");
    }

    String queryDataString =
        buildPropertyString(componentName, new String[] {S_QUERY, queryId, S_DATA});
    String dataString = (String) m_contextManager.getProperty(queryDataString);

    Map retVal = this.decodeQueryDataMap(dataString);
    return retVal;
  }

  /** Remove a saved query
   *
   * @param componentName
   * @param queryId
   * @throws IOException
   */
  void removeSavedQuery(String componentName, String queryId) throws IOException {
    String getQueriesString = buildPropertyString(componentName,new String[] {S_QUERIES});
    QueryIdList idList = new QueryIdList((String) m_contextManager.getProperty(getQueriesString));

    idList.remove(queryId);
    String idListString = idList.toString();
    m_contextManager.setUserPreference(getQueriesString, idListString);

    Set removalSet = new HashSet();

    String removeQueryNameString =
        buildPropertyString(componentName, new String[] {S_QUERY, queryId, S_NAME});
    removalSet.add(removeQueryNameString);

    String removeQueryDataString =
        buildPropertyString(componentName, new String[] {S_QUERY, queryId, S_DATA});
    removalSet.add(removeQueryDataString);

    m_contextManager.unSetUserPreferences(removalSet);

    removeSavedQueryShortcut(componentName, queryId);
    removeSavedQueryDefault(componentName, queryId);

  }

  boolean savedQueryHasShortcut(String componentName, String queryId) {
    boolean retVal = false;
    String queryShortCuts =
        buildPropertyString(null, new String[] {S_QUERY,S_SHORTCUTS});
    String shortCuts = (String)m_contextManager.getProperty(queryShortCuts);

    if (shortCuts != null) {
      StringTokenizer shortCutsTokenizer = new StringTokenizer(shortCuts,",");

      String shortCutToTest = componentName + "." + S_QUERY + "." + queryId;

      while (shortCutsTokenizer.hasMoreTokens()) {
        String aShortCut = shortCutsTokenizer.nextToken();
        if(shortCutToTest.compareTo(aShortCut) == 0) {
          retVal = true;
          break;
        }
      }
    }

    return retVal;
  }

  /** Remove a default for a query, if it exists
   *
   * @param componentName
   * @param queryId
   * @throws IOException
   */
  void removeSavedQueryDefault(String componentName, String queryId) throws IOException {
    String queryDefault =
        buildPropertyString(componentName, new String[] {S_QUERY,S_DEFAULT});
    String defaults = (String)m_contextManager.getProperty(queryDefault);

    if (defaults != null  && defaults.equals(queryId))
      m_contextManager.unSetUserPreference(queryDefault);
  }



  /** Rmove a shortcut for a query, if it exists
   *
   * @param componentName
   * @param queryId
   * @throws IOException
   */
  void removeSavedQueryShortcut(String componentName, String queryId) throws IOException {
    String queryShortCuts =
        buildPropertyString(null, new String[] {S_QUERY,S_SHORTCUTS});
    String shortCuts = (String)m_contextManager.getProperty(queryShortCuts);

    if (shortCuts != null) {
      StringTokenizer shortCutsTokenizer = new StringTokenizer(shortCuts,",");

      String shortCutToDelete = componentName + "." + S_QUERY + "." + queryId;

      String revisedShortCuts = new String();

      while (shortCutsTokenizer.hasMoreTokens()) {
        String aShortCut = shortCutsTokenizer.nextToken();
        if(shortCutToDelete.compareTo(aShortCut) != 0) {
          if (revisedShortCuts.length() > 0) {
            revisedShortCuts = revisedShortCuts + ",";
          }
          revisedShortCuts = revisedShortCuts + aShortCut;
        }
      }

      if (revisedShortCuts.length() > 0) {
        m_contextManager.setUserPreference(queryShortCuts, revisedShortCuts);
      } else {
        m_contextManager.unSetUserPreference(queryShortCuts);
      }
    }
  }

  /**
   * @return queryId of the default query for component
   */
  String getDefaultQueryId(String componentName) {
    String queryDefaultQueryString =
        buildPropertyString(componentName, new String[] {S_QUERY, S_DEFAULT});
    String defaultQueryString = (String) m_contextManager.getProperty(queryDefaultQueryString);

    return defaultQueryString;
  }


  protected String encodeQueryDataMap(Map queryData) {
    StringBuffer sb = new StringBuffer();
    Iterator queryDataKeysIterator = queryData.keySet().iterator();
    boolean isOutputted = false;
    while (queryDataKeysIterator.hasNext()) {
      String keyString = (String) queryDataKeysIterator.next();
      Object valueObject = queryData.get(keyString);
      if (valueObject != null) {
        if (valueObject instanceof String) {
          if (isOutputted) {
            sb.append("&");
          }
          sb.append(keyString);
          sb.append("=");
          sb.append((String) valueObject);
          isOutputted = true;
        } else if (valueObject instanceof Integer) {
          if (isOutputted) {
            sb.append("&");
          }
          sb.append(keyString);
          sb.append("=");
          sb.append(((Integer) valueObject).toString());
          isOutputted = true;
        } else if (valueObject instanceof Long) {
          if (isOutputted) {
            sb.append("&");
          }
          sb.append(keyString);
          sb.append("=");
          sb.append(((Long) valueObject).toString());
          isOutputted = true;
        } else if (valueObject instanceof Double) {
          if (isOutputted) {
            sb.append("&");
          }
          sb.append(keyString);
          sb.append("=");
          sb.append(((Double) valueObject).toString());
          isOutputted = true;
        } else if (valueObject instanceof Float) {
          if (isOutputted) {
            sb.append("&");
          }
          sb.append(keyString);
          sb.append("=");
          sb.append(((Float) valueObject).toString());
          isOutputted = true;
        } else if (valueObject instanceof Boolean) {
          if (isOutputted) {
            sb.append("&");
          }
          sb.append(keyString);
          sb.append("=");
          sb.append(((Boolean) valueObject).toString());
          isOutputted = true;
        } else {
          log.error("Unable to encode Object of type "
              + valueObject.getClass().getName());
        }
      } else {
        if (isOutputted) {
          sb.append("&");
        }
        sb.append(keyString);
        sb.append("=");
        isOutputted = true;
      }

    }
    return sb.toString();
  }

  protected Map decodeQueryDataMap(String queryDataString) {
    Map retVal = new HashMap();

    StringTokenizer st = new StringTokenizer(queryDataString, "&");
    while( st.hasMoreTokens() ) {
      String element = st.nextToken();
      int equalsPos = element.indexOf("=");
      if (equalsPos > -1) {
        String nameStr = element.substring(0, equalsPos);

        if (element.length() -1 == equalsPos) {
          String valueStr = null;
          retVal.put(nameStr, valueStr);
        } else {
          String valueStr = element.substring(equalsPos + 1);
          if (valueStr.matches("^Integer\\(\\d+\\)$")) {
            String intValStr = valueStr.replaceAll("^Integer\\(", "");
            intValStr = intValStr.replaceAll("\\)$", "");
            Integer intVal = new Integer(intValStr);
            retVal.put(nameStr, intVal);
          } else if (valueStr.matches("^Long\\(\\d+\\)$")) {
            String longValStr = valueStr.replaceAll("^Long\\(", "");
            longValStr = longValStr.replaceAll("\\)$", "");
            Long longVal = new Long(longValStr);
            retVal.put(nameStr, longVal);
          } else if (valueStr.matches("^Float\\(\\d+\\)$")) {
            String floatValStr = valueStr.replaceAll("^Float\\(", "");
            floatValStr = floatValStr.replaceAll("\\)$", "");
            Float floatVal = new Float(floatValStr);
            retVal.put(nameStr, floatVal);
          } else if (valueStr.matches("^Double\\(\\d+\\)$")) {
            String doubleValStr = valueStr.replaceAll("^Double\\(", "");
            doubleValStr = doubleValStr.replaceAll("\\)$", "");
            Double doubleVal = new Double(doubleValStr);
            retVal.put(nameStr, doubleVal);
          } else if (valueStr.matches("^Long\\(\\d+\\)$")) {
            String longValStr = valueStr.replaceAll("^Long\\(", "");
            longValStr = longValStr.replaceAll("\\)$", "");
            Long longVal = new Long(longValStr);
            retVal.put(nameStr, longVal);
          } else if (valueStr.matches("^Boolean\\((true)|(false)\\)$")) {
            String boolValStr = valueStr.replaceAll("^Boolean\\(", "");
            boolValStr = boolValStr.replaceAll("\\)$", "");
            Boolean boolVal = new Boolean(boolValStr);
            retVal.put(nameStr, boolVal);
          } else {
            retVal.put(nameStr, valueStr);
          }
        }
      }
    }
    return retVal;
  }

  protected String buildPropertyString(String componentName, String[] suffixes) {
    String retVal = S_USERPREF;
    if (componentName != null && componentName.length()>0) {
      retVal = retVal + "." + componentName;
    }

    for (int loop = 0 ; loop < suffixes.length ; loop++) {
      retVal = retVal + "." + suffixes[loop];
    }
    return retVal;
  }

  /**
   * Returns all the saved queries which were labeled as shortcuts.
   * These saved queries are grouped by their component names.
   * All the saved queries of one component are stored in SavedQueryList object.
   * The returned object is a list of the SavedQueryList objects.
   */
  public static List<SavedQueryBean> getSavedShortcutQueryBeans() {
    IContextManager contextManager = ContextManagerFactory.instance();
    QuerySaver querySaver = new QuerySaver(contextManager);

    // get all the component names
    Set propertyNames = contextManager.getPropertyNames(S_USERPREF+".*");
    Set componentNames = new HashSet();

    if (propertyNames != null) {
      for (Object propertyName : propertyNames) {
        String pn = (String) propertyName;
        pn = pn.replaceFirst(S_USERPREF + ".", "");
        if (pn.indexOf("." + S_QUERY) > 0) {
          pn = pn.substring(0, pn.indexOf("." + S_QUERY));
          if (pn.length() > 0) {
            componentNames.add(pn);
          }
        }
      }
    }

    List out = new ArrayList();
    String[] cpns = (String[]) componentNames.toArray(new String[0]);
    Arrays.sort(cpns);
    for (int i=0; i<cpns.length; i++) {
      // get all saved queries
      Map asq = querySaver.getSavedQueryList(cpns[i]);
      // filter out the queries that are shortcuts
      List bag = new ArrayList();
      for (Iterator it=asq.keySet().iterator(); it.hasNext(); ) {
        String qId = (String) it.next();
        if (! querySaver.savedQueryHasShortcut(cpns[i], qId)) {
          bag.add(qId);
        }
      }
      for (int j=0; j<bag.size(); j++) {
        String qId = (String) bag.get(j);
        asq.remove(qId);
      }

      if (asq.size()>0) {
        // assemble into the output list
        SavedQueryBean sql = new SavedQueryBean(cpns[i]);
        for (Iterator it=asq.keySet().iterator(); it.hasNext(); ) {
          String qId = (String) it.next();
          String qname = (String) asq.get(qId);
          // construct the url
          String url="startComponent.do?finalUrl=jaffa_closeBrowser&component="+
              cpns[i]+"&displayResultsScreen=true&useQuery="+qId;
          sql.setSavedQueryUrl(qId, qname, url);
        }
        out.add(sql);
      }
    }
    return out;
  }

  public static void removeShortcutFlag(String componentName, String savedQueryId) throws IOException {
    IContextManager contextManager = ContextManagerFactory.instance();
    QuerySaver qs = new QuerySaver(contextManager);
    qs.removeSavedQueryShortcut(componentName, savedQueryId);
  }
}
