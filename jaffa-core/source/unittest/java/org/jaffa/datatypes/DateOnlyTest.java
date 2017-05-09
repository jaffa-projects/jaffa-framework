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

package org.jaffa.datatypes;

import junit.framework.*;

public class DateOnlyTest extends TestCase {
    
    public DateOnlyTest(java.lang.String testName) {
        super(testName);
    }
    
    /** Test of clone method, of class org.jaffa.datatypes.DateOnly. */
    public void testClone() {
        try {
            DateOnly dt = new DateOnly(2000, DateOnly.FEBRUARY, 15);
            DateOnly newDt = (DateOnly) dt.clone();
            assertTrue("The cloned object does not equal the original: " + newDt + ", " + dt, dt.equals(newDt));
            assertTrue("The cloned object should not be the same as the original", dt != newDt);
            
            dt = new DateOnly();
            newDt = (DateOnly) dt.clone();
            assertTrue("The cloned object does not equal the original: " + newDt + ", " + dt, dt.equals(newDt));
            assertTrue("The cloned object should not be the same as the original", dt != newDt);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to clone a DateOnly object: " + e.toString());
        }
    }
    
    /** Test of compareTo method, of class org.jaffa.datatypes.DateOnly. */
    public void testCompareTo() {
        DateOnly dt1 = new DateOnly(2000, DateOnly.FEBRUARY, 15);
        DateOnly dt2 = new DateOnly(2000, DateOnly.FEBRUARY, 16);
        DateOnly dt3 = new DateOnly(2000, DateOnly.FEBRUARY, 16);
        
        assertTrue("dt1 should be less than dt2: " + dt1 + ", " + dt2, dt1.compareTo(dt2) < 0);
        assertTrue("dt2 should be greater than dt1: " + dt2 + ", " + dt1, dt2.compareTo(dt1) > 0);
        assertTrue("dt3 should equal dt2: " + dt3 + ", " + dt2, dt3.compareTo(dt2) == 0);
    }
    
    /** Test of equals method, of class org.jaffa.datatypes.DateOnly. */
    public void testEquals() {
        DateOnly dt1 = new DateOnly(2000, DateOnly.FEBRUARY, 15);
        DateOnly dt2 = new DateOnly(2000, DateOnly.FEBRUARY, 16);
        DateOnly dt3 = new DateOnly(2000, DateOnly.FEBRUARY, 16);
        
        assertTrue("dt1 should not equal dt2: " + dt1 + ", " + dt2, !dt1.equals(dt2));
        assertTrue("dt3 should equal dt2: " + dt3 + ", " + dt2, dt3.equals(dt2));
    }
    
    /** Test of parse method, of class org.jaffa.datatypes.DateOnly. */
    public void testParse() {
        try {
            DateOnly today = new DateOnly();
            DateOnly tomorrow = DateOnly.addDay(today, 1);
            DateOnly yesterday = DateOnly.addDay(tomorrow, -2);
            DateOnly nextMonth = DateOnly.addMonth(today, 1);
            DateOnly nextMonthPlusDay = DateOnly.addDay(nextMonth, 1);
            DateOnly nextMonthPlusDayMinusYear = DateOnly.addYear(nextMonthPlusDay, -1);
            DateOnly nextMonthPlusDayMinusYearMinus2Days = DateOnly.addDay(nextMonthPlusDayMinusYear, -2);
            
            
            assertTrue("t: ", DateOnly.parse("t").equals(today));
            assertTrue("n: ", DateOnly.parse("n").equals(today));
            assertTrue("t+1: ", DateOnly.parse("t+1").equals(tomorrow));
            assertTrue("n+1: ", DateOnly.parse("n+1").equals(tomorrow));
            assertTrue("n-1: ", DateOnly.parse("n-1").equals(yesterday));
            assertTrue("n+1m1: ", DateOnly.parse("n+1m1").equals(nextMonthPlusDay));
            assertTrue("n+1m1d-1y2: ", DateOnly.parse("n+1m1d-1y2").equals(nextMonthPlusDayMinusYearMinus2Days));
            assertTrue("null: ", DateOnly.parse(null) == null);
            assertTrue("Empty String: ", DateOnly.parse("") == null);
            
        } catch( Exception e) {
            e.printStackTrace();
            fail("Failed to parse DateOnly object: " + e.toString());
        }
        
    }
    
}

