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
package org.jaffa.components.finder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.NoDefaultException;
import org.jaffa.session.IContextManager;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

public class QuerySaverTest extends MockObjectTestCase {

    public QuerySaverTest(String name) {
        super(name);
    }

    private Mock contextManager;
    private QuerySaver querySaver;
    
    protected void setUp() throws Exception {

    contextManager = mock(IContextManager.class, "contextManager");
    querySaver = new QuerySaver((IContextManager) contextManager.proxy());
    
    }

    public final void testEncodeQueryDataMapSingleMapEntry() 
    {
        
        Map map = new HashMap();
        map.put("name", "AssetFinder");
        
        String expectedString = "name=AssetFinder";
        
        String result = querySaver.encodeQueryDataMap(map);
        assertEquals("Strings are not equal",expectedString, result);
    }

    public final void testEncodeQueryDataMapMultipleMapEntry() 
    {
        
        Map map = new HashMap();
        map.put("name", "AssetFinder");
        map.put("nameDd", "equals");
        String expectedString = "nameDd=equals&name=AssetFinder";
        
        String result = querySaver.encodeQueryDataMap(map);
        assertEquals("Strings are not equal",expectedString, result);
    }

    public final void testEncodeQueryDataMapMultipleMapEntryOneNonString() 
    {
        
        Map map = new HashMap();
        map.put("name", "AssetFinder");
        map.put("value", new Integer(2));
        map.put("nameDd", "equals");
        String expectedString = "nameDd=equals&name=AssetFinder";
        
        String result = querySaver.encodeQueryDataMap(map);
        assertEquals("Strings are not equal",expectedString, result);
    }

    public final void testBuildPropertyStringSingleSuffix()
    {
        String componentName = "AssetFinder";
        String[] suffixes = new String[] {"suffix"};
        
        String expectedString = QuerySaver.S_USERPREF + ".AssetFinder.suffix";
        
        String result = querySaver.buildPropertyString(componentName, suffixes);
        
        assertEquals("Strings are not equal",expectedString, result);
    }
    
    public final void testBuildPropertyStringMultipleSuffix()
    {
        String componentName = "AssetFinder";
        String[] suffixes = new String[] {"suffix1", "suffix2"};
        
        String expectedString = QuerySaver.S_USERPREF + ".AssetFinder.suffix1.suffix2";
        
        String result = querySaver.buildPropertyString(componentName, suffixes);
        
        assertEquals("Strings are not equal",expectedString, result);
    }
    
    public final void testBuildPropertyStringEmptySuffix()
    {
        String componentName = "AssetFinder";
        String[] suffixes = new String[] {};
        
        String expectedString = QuerySaver.S_USERPREF + ".AssetFinder";
        
        String result = querySaver.buildPropertyString(componentName, suffixes);
        
        assertEquals("Strings are not equal",expectedString, result);
    }
    
    public final void testBuildPropertyStringNullSuffix()
    {
        String componentName = "AssetFinder";
        String[] suffixes = null;
        
        String expectedString = QuerySaver.S_USERPREF + ".AssetFinder";
        
        String result;
        try {
            result = querySaver.buildPropertyString(componentName, suffixes);
            fail("Expected Null Pointer Exception");
        } catch (RuntimeException e) {
        }
    }
    
    public final void testSaveQueryNoneSavedNotDefaultNotShortCut() throws IOException
    {
        String getProperty1 = QuerySaver.S_USERPREF + ".AssetFinder.queries";
        
        String componentName = "AssetFinder";
        String queryName = "testQuery1";
        Map parameters = new HashMap();
        parameters.put("AssetId", "12345");
        parameters.put("AssetIdDd", "equals");
        
        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue("")).id("1");
        contextManager.expects(once()).method("setUserPreferences").after("1").id("2");
        
        String result = querySaver.saveQuery(queryName, componentName, parameters, false, false);
    }

    public final void testSaveQueryNoneSavedDefaultNotShortCut() throws IOException
    {
        String getProperty1 = QuerySaver.S_USERPREF + ".AssetFinder.queries";
        
        String componentName = "AssetFinder";
        String queryName = "testQuery1";
        Map parameters = new HashMap();
        parameters.put("AssetId", "12345");
        parameters.put("AssetIdDd", "equals");
        
        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue("")).id("1");
        contextManager.expects(once()).method("setUserPreferences").after("1").id("2");
        
        String result = querySaver.saveQuery(queryName, componentName, parameters, true, false);
    }

    public final void testSaveQueryNoneSavedDefaultShortCut() throws IOException
    {
        String getProperty1 = QuerySaver.S_USERPREF + ".AssetFinder.queries";
        String getProperty2 = QuerySaver.S_USERPREF + ".query.shortcuts";
        
        String componentName = "AssetFinder";
        String queryName = "testQuery1";
        Map parameters = new HashMap();
        parameters.put("AssetId", "12345");
        parameters.put("AssetIdDd", "equals");
        
        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue("")).id("1");
        contextManager.expects(once()).method("getProperty").with(eq(getProperty2)).after("1").will(returnValue("")).id("2");
        contextManager.expects(once()).method("setUserPreferences").after("2").id("3");
        
        String result = querySaver.saveQuery(queryName, componentName, parameters, true, true);
    }

    public final void testGetSavedQueryList()
    {
        String componentName = "AssetFinder";
        Map expectedMap = new HashMap();
        expectedMap.put("1", "My Assets");
        expectedMap.put("2", "Broken Assets");

        String getProperty1 = QuerySaver.S_USERPREF + ".AssetFinder.queries";
        String getProperty2 = QuerySaver.S_USERPREF + ".AssetFinder.query.1.name";
        String getProperty3 = QuerySaver.S_USERPREF + ".AssetFinder.query.2.name";

        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue("1,2")).id("1");
        contextManager.expects(once()).method("getProperty").with(eq(getProperty2)).after("1").will(returnValue("My Assets")).id("2");
        contextManager.expects(once()).method("getProperty").with(eq(getProperty3)).after("2").will(returnValue("Broken Assets")).id("3");


        Map result = querySaver.getSavedQueryList(componentName);
        assertEquals("Maps are not equal", expectedMap, result);
    }
    
    
    public final void testGetSavedQueryListNullValues()
    {
        String componentName = "AssetFinder";
        Map expectedMap = new HashMap();

        String getProperty1 = QuerySaver.S_USERPREF + ".AssetFinder.queries";

        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue(null)).id("1");


        Map result = querySaver.getSavedQueryList(componentName);
        assertEquals("Maps are not equal", expectedMap, result);
    }
    
    
    public final void testGetSavedQueryFields()
    {
        String componentName = "AssetFinder";
        Map expectedMap = new HashMap();
        expectedMap.put("name", "AssetFinder");
        expectedMap.put("nameDd", "equals");
        String getPropertyResult = "nameDd=equals&name=AssetFinder";

        String getProperty1 = QuerySaver.S_USERPREF + ".AssetFinder.query.1.data";

        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue(getPropertyResult)).id("1");


        Map result = querySaver.getSavedQueryFields(componentName, "1");
        assertEquals("Maps are not equal", expectedMap, result);
    }
    
    
    public final void testGetDefaultQueryId()
    {
        String componentName = "AssetFinder";
        String getPropertyResult = "3";

        String getProperty1 = QuerySaver.S_USERPREF + ".AssetFinder.query.default";

        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue(getPropertyResult)).id("1");


        String result = querySaver.getDefaultQueryId(componentName);
        assertEquals("Values are not equal", getPropertyResult, result);
    }
    
    
    public final void testGetDefaultQueryFields() throws NoDefaultException
    {
        String componentName = "AssetFinder";
        Map expectedMap = new HashMap();
        expectedMap.put("name", "AssetFinder");
        expectedMap.put("nameDd", "equals");
        String getPropertyResult = "nameDd=equals&name=AssetFinder";

        String getDefaultResult = "1";

        String getProperty1 = QuerySaver.S_USERPREF + ".AssetFinder.query.default";
        String getProperty2 = QuerySaver.S_USERPREF + ".AssetFinder.query.1.data";

        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue(getDefaultResult)).id("1");
        contextManager.expects(once()).method("getProperty").with(eq(getProperty2)).after("1").will(returnValue(getPropertyResult)).id("2");


        Map result = querySaver.getDefaultQueryFields(componentName);
        assertEquals("Maps are not equal", expectedMap, result);
    }
    
    
    public final void testGetDefaultQueryFieldsWithNoDefault()
    {
        String componentName = "AssetFinder";
        Map expectedMap = new HashMap();
        expectedMap.put("name", "AssetFinder");
        expectedMap.put("nameDd", "equals");
        String getPropertyResult = "nameDd=equals&name=AssetFinder";

        String getDefaultResult = "";

        String getProperty1 = QuerySaver.S_USERPREF + ".AssetFinder.query.default";
        String getProperty2 = QuerySaver.S_USERPREF + ".AssetFinder.query.1.data";

        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue(getDefaultResult)).id("1");


        Map result;
        try {
            result = querySaver.getDefaultQueryFields(componentName);
            fail("NoDefaultException expected");
        } catch (NoDefaultException e) {
        }
    }
    
    
    public final void testRemoveQueryShortCut() throws IOException
    {
        String componentName = "AssetFinder";

        String getShortCutsResult = "AssetFinder.query.1,AssetFinder.query.2,AssetFinder.query.3";
        String setShortCutsResult = "AssetFinder.query.1,AssetFinder.query.3";

        String getProperty1 = QuerySaver.S_USERPREF + ".query.shortcuts";

        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue(getShortCutsResult)).id("1");
        contextManager.expects(once()).method("setUserPreference").with(eq(getProperty1), eq(setShortCutsResult)).after("1").id("2");


        querySaver.removeSavedQueryShortcut(componentName, "2");
    }
    
    
    public final void testRemoveQueryShortCutBackOfList() throws IOException
    {
        String componentName = "AssetFinder";

        String getShortCutsResult = "AssetFinder.query.1,AssetFinder.query.2,AssetFinder.query.3";
        String setShortCutsResult = "AssetFinder.query.1,AssetFinder.query.2";

        String getProperty1 = QuerySaver.S_USERPREF + ".query.shortcuts";

        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue(getShortCutsResult)).id("1");
        contextManager.expects(once()).method("setUserPreference").with(eq(getProperty1), eq(setShortCutsResult)).after("1").id("2");


        querySaver.removeSavedQueryShortcut(componentName, "3");
    }
    
    
    public final void testRemoveQueryShortCutFrontOfList() throws IOException
    {
        String componentName = "AssetFinder";

        String getShortCutsResult = "AssetFinder.query.1,AssetFinder.query.2,AssetFinder.query.3";
        String setShortCutsResult = "AssetFinder.query.2,AssetFinder.query.3";

        String getProperty1 = QuerySaver.S_USERPREF + ".query.shortcuts";

        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue(getShortCutsResult)).id("1");
        contextManager.expects(once()).method("setUserPreference").with(eq(getProperty1), eq(setShortCutsResult)).after("1").id("2");


        querySaver.removeSavedQueryShortcut(componentName, "1");
    }
    
    
    public final void testRemoveQueryShortCutNotInList() throws IOException
    {
        String componentName = "AssetFinder";

        String getShortCutsResult = "AssetFinder.query.1,AssetFinder.query.2,AssetFinder.query.3";

        String getProperty1 = QuerySaver.S_USERPREF + ".query.shortcuts";

        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue(getShortCutsResult)).id("1");
        contextManager.expects(once()).method("setUserPreference").with(eq(getProperty1), eq(getShortCutsResult)).after("1").id("2");


        querySaver.removeSavedQueryShortcut(componentName, "6");
    }
    
    
    public final void testRemoveQueryShortCutLastInListList() throws IOException
    {
        String componentName = "AssetFinder";

        String getShortCutsResult = "AssetFinder.query.1";

        String getProperty1 = QuerySaver.S_USERPREF + ".query.shortcuts";

        
        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue(getShortCutsResult)).id("1");
        contextManager.expects(once()).method("unSetUserPreference").with(eq(getProperty1)).after("1").id("2");


        querySaver.removeSavedQueryShortcut(componentName, "1");
    }
    
    
    public final void testRemoveSavedQuery() throws IOException
    {
        String componentName = "AssetFinder";
        String getQueriesResult = "1,2";

        String getProperty1 = QuerySaver.S_USERPREF + ".AssetFinder.queries";
        String getProperty2 = QuerySaver.S_USERPREF + ".query.shortcuts";
        String getShortCutsResult1 = "AssetFinder.query.1,AssetFinder.query.2";
        String getShortCutsResult2 = "AssetFinder.query.1";

        contextManager.expects(once()).method("getProperty").with(eq(getProperty1)).will(returnValue(getQueriesResult)).id("1");
        contextManager.expects(once()).method("setUserPreference").with(eq(getProperty1),eq("1")).after("1").id("2");
        contextManager.expects(once()).method("unSetUserPreferences").after("2").id("3");
        contextManager.expects(once()).method("getProperty").with(eq(getProperty2)).after("3").will(returnValue(getShortCutsResult1)).id("4");
        contextManager.expects(once()).method("setUserPreference").with(eq(getProperty2),eq(getShortCutsResult2)).after("4").id("5");


        querySaver.removeSavedQuery(componentName, "2");
    }
    
    
    public final void testDecodeQueryDataMapSingleMapEntry()
    {
        Map expectedMap = new HashMap();
        expectedMap.put("name", "AssetFinder");
        String input = "name=AssetFinder";

        Map result = querySaver.decodeQueryDataMap(input);
        assertEquals("Maps are not equal", expectedMap, result);
        
    }
    
    public final void testDecodeQueryDataMapMultipleMapEntry() 
    {
        
        Map expectedMap = new HashMap();
        expectedMap.put("name", "AssetFinder");
        expectedMap.put("nameDd", "equals");
        String input = "nameDd=equals&name=AssetFinder";
        
        Map result = querySaver.decodeQueryDataMap(input);
        assertEquals("Maps are not equal", expectedMap, result);
    }

    private void assertEquals(String message, Map map1, Map map2)
    {
        //Loop through each map and test the contents of the other

        //Check all map1's keys are in map2 with the same value
        Iterator map1KeysIterator  = map1.keySet().iterator();
        while (map1KeysIterator.hasNext()) {
            String key1 = (String) map1KeysIterator.next();
            String value1 = (String) map1.get(key1);
            String value2 = (String) map2.get(key1);
            if(value1.compareTo(value2)!= 0)
            {
                fail(message);
            }
        }
        
        //Check all map2's keys are in map1 with the same value
        Iterator map2KeysIterator  = map2.keySet().iterator();
        while (map2KeysIterator.hasNext()) {
            String key2 = (String) map2KeysIterator.next();
            String value1 = (String) map1.get(key2);
            String value2 = (String) map2.get(key2);
            if(value1.compareTo(value2)!= 0)
            {
                fail(message);
            }
        }
        
    }
}
