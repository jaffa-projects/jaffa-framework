package org.jaffa.components.finder;

import java.util.Arrays;


import junit.framework.TestCase;

public class QueryIdListTest extends TestCase {

    public QueryIdListTest(String name) {
        super(name);
    }

    public final void testnextAvailable() {
        
        QueryIdList idList = new QueryIdList("1,2,3");
        String next = idList.nextAvailable();
        assertEquals( "Expecting 4", "4", next);
        
        String finalList = idList.toString();
        assertEquals( "Expecting 1,2,3,4", "1,2,3,4", finalList);
        
    }
    public final void testnextAvailableEmptyList() {
        
        QueryIdList idList = new QueryIdList();
        String next = idList.nextAvailable();
        assertEquals( "Expecting 1", "1", next);

        String finalList = idList.toString();
        assertEquals( "Expecting 1", "1", finalList);
        
    }

    public final void testnextAvailableAddInMiddle() {
        
        QueryIdList idList = new QueryIdList("1,3");
        String next = idList.nextAvailable();
        assertEquals( "Expecting 2", "2", next);

        String finalList = idList.toString();
        assertEquals( "Expecting 1,2,3", "1,2,3", finalList);
        
    }

    public final void testreSorting() {
        
        QueryIdList idList = new QueryIdList("3,2,6,1,5");

        String finalList = idList.toString();
        assertEquals( "Expecting 1,2,3,5,6", "1,2,3,5,6", finalList);
        
    }

    public final void testReoveSorting() {
        
        QueryIdList idList = new QueryIdList("3,2,6,1,5");

        idList.remove("3");
        String finalList = idList.toString();
        assertEquals( "Expecting 1,2,5,6", "1,2,5,6", finalList);
        
    }

    public final void testtoArray() {
        
        QueryIdList idList = new QueryIdList("3,2,6,1,5");

        Object[] finalArray = idList.toArray();
        Object[] referenceArray = {"1","2","3","5","6"};
        assertStringArraysEquals( "Expecting 1,2,3,5,6", referenceArray, finalArray);
        
    }

    private void assertStringArraysEquals( String message, Object[] a, Object[] b)
    {
        
        boolean result = false;
        if ( a == b)
        {
            result = true;
        }
        else if(a.length == b.length)
        {
            result = true; 
            for (int loop = 0; loop < a.length; loop++)
            {
                String aStr = (String)a[loop];
                String bStr = (String)b[loop];
                
                if( aStr.compareTo(bStr) != 0)
                {
                    result = false;
                    break;
                }
            }
        }
    }

}
