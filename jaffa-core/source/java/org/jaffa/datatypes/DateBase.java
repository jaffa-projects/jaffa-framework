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

// NOTES: The field m_calendar can probably be made STATIC. This would involve use of the synchronizing keyword... lot of overhead... ignore for the time being !!!
package org.jaffa.datatypes;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
//import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.*;
import java.util.Map;
import java.util.TimeZone;
import org.jaffa.metadata.*;
import org.jaffa.presentation.portlet.session.LocaleContext;
import org.jaffa.util.LocaleHelper;
import org.jaffa.util.StringHelper;


/**
 * This class is used to hold Date data
 */
class DateBase implements Cloneable, Comparable, Serializable {
    
    // *** PRIVATE FIELDS ***
    // NOTE: keep the clone() method in sync for all the fields added/removed
    private java.util.Date m_utilDate = null; // will always exist
    private Calendar m_calendar = null;  //to be created as required
    
    
    // *** CONSTRUCTORS ***
    
    /** Creates an instance with the current datetime.
     */
    DateBase() {
        m_utilDate = new java.util.Date();
    }
    
    /** Creates an instance initialized to the input value.
     */
    DateBase(long timeInMillis) {
        m_utilDate = new java.util.Date(timeInMillis);
    }
    
    /** Creates an instance initialized to the input value.
     */
    DateBase(java.util.Date utilDate) {
        m_utilDate = utilDate != null ? utilDate : new java.util.Date();
    }
    
    /** Creates an instance initialized to the input value.
     */
    DateBase(Calendar calendar) {
        m_calendar = calendar;
        m_utilDate = m_calendar != null ? m_calendar.getTime() : new java.util.Date();
    }
    
    /** Creates an instance initialized to the input value.
     */
    DateBase(int year, int month, int day) {
        this(year, month, day, 0, 0, 0, 0);
    }
    
    /** Creates an instance initialized to the input value.
     */
    DateBase(int year, int month, int day, int hourOfDay, int minute, int second, int milli) {
        m_calendar =  new GregorianCalendar(year, month, day, hourOfDay, minute, second);
        if (milli > 0)
            m_calendar.set(Calendar.MILLISECOND, milli);
        
        // check for validity
        if (year != m_calendar.get(Calendar.YEAR)
        || month != m_calendar.get(Calendar.MONTH)
        || day != m_calendar.get(Calendar.DAY_OF_MONTH)
        || hourOfDay != m_calendar.get(Calendar.HOUR_OF_DAY)
        || minute != m_calendar.get(Calendar.MINUTE)
        || second != m_calendar.get(Calendar.SECOND)
        || milli != m_calendar.get(Calendar.MILLISECOND))
            throw new IllegalArgumentException();
        
        m_utilDate = m_calendar.getTime();
    }
    
    /** Creates an instance from another DateBase instance.
     * Essentially clones the input instance.
     */
    DateBase(DateBase aDateBase) {
        m_utilDate = (java.util.Date) aDateBase.m_utilDate.clone();
        if (aDateBase.m_calendar != null)
            m_calendar = (Calendar) aDateBase.m_calendar.clone();
    }
    
    
    
    // - Some standard Methods
    
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        Object obj = super.clone();
        ((DateBase) obj).m_utilDate = (java.util.Date) m_utilDate.clone();
        if (m_calendar != null)
            ((DateBase) obj).m_calendar = (Calendar) m_calendar.clone();
        return obj;
    }
    
    /** Compares this object with another DateBase object.
     * @param obj the other DateBase object.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    public int compareTo(Object obj) {
        DateBase target = (DateBase) obj;
        //It is quite possible that the utilDate is a java.sql.Date object in one instance, while its a java.util.Date object in the other
        //A ClassCastException is thrown in such a scenario. Hence its best to compare the timeInMillis
        return new Long(timeInMillis()).compareTo(new Long(target.timeInMillis()));
    }
    
    /** Compares this object with another DateBase object.
     * Returns a true if both the objects have the same value.
     * @param obj the other DateBase object.
     * @return a true if both the objects have the same value.
     */
    public boolean equals(Object obj) {
        if (obj instanceof DateBase) {
            DateBase target = (DateBase) obj;
            //It is quite possible that the utilDate is a java.sql.Date object in one instance, while its a java.util.Date object in the other
            //A ClassCastException is thrown in such a scenario. Hence its best to compare the timeInMillis
            return new Long(timeInMillis()).equals(new Long(target.timeInMillis()));
        } else {
            return false;
        }
    }
    
    /** Returns the hashCode of the value.
     * @return the hashCode of the value.
     */
    public int hashCode() {
        return m_utilDate.hashCode();
    }
    
    /** Returns the diagnostic information.
     * @return the diagnostic information.
     */
    public String toString() {
        return timestamp().toString();
    }
    
    
    
    
    // - Getters/Setters
    java.util.Date getUtilDate() {
        /*
         There is an outstanding bug with JBossWS (see http://jira.jboss.org/jira/browse/JBWS-800, http://www.jboss.com/index.html?module=bb&op=viewtopic&p=3954637)
         It doesn't handle the subclasses of java.util.Date well. Hence we'll convert a subclass to a java.util.Date
         @todo: remove this hack after JBoss fixes the issue in a post jboss-4.0.4.GA release
         */
        if (m_utilDate != null && m_utilDate.getClass() != java.util.Date.class)
            m_utilDate = new java.util.Date(m_utilDate.getTime());
        
        return m_utilDate;
    }
    
    /** Returns the value as a java.util.Date object.
     * @return the value as a java.util.Date object.
     */
    void setUtilDate(java.util.Date utilDate) {
        m_utilDate = utilDate != null ? utilDate : new java.util.Date();
        m_calendar = null;
    }
    
    
    
    
    // - Return standard Java Date Objects
    long timeInMillis() {
        return m_utilDate.getTime();
    }
    
    Calendar calendar() {
        return (Calendar) createCalendar().clone();
    }
    
    java.sql.Date sqlDate() {
        return new java.sql.Date(m_utilDate.getTime());
    }
    
    Time sqlTime() {
        return new Time(m_utilDate.getTime());
    }
    
    Timestamp timestamp() {
        return new Timestamp(m_utilDate.getTime());
    }
    
    
    
    
    // - Addtional Getters
    int year() {
        return get(Calendar.YEAR);
    }
    
    int month() {
        return get(Calendar.MONTH);
    }
    
    int day() {
        return get(Calendar.DAY_OF_MONTH);
    }
    
    int dayOfWeek() {
        return get(Calendar.DAY_OF_WEEK);
    }
    
    int dayOfWeekInMonth() {
        return get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }
    
    int dayOfYear() {
        return get(Calendar.DAY_OF_YEAR);
    }
    
    int weekOfMonth() {
        return get(Calendar.WEEK_OF_MONTH);
    }
    
    int weekOfYear() {
        return get(Calendar.WEEK_OF_YEAR);
    }
    
    int hour() {
        return get(Calendar.HOUR);
    }
    
    int hourOfDay() {
        return get(Calendar.HOUR_OF_DAY);
    }
    
    int minute() {
        return get(Calendar.MINUTE);
    }
    
    int second() {
        return get(Calendar.SECOND);
    }
    
    int milli() {
        return get(Calendar.MILLISECOND);
    }
    
    boolean am() {
        return get(Calendar.AM_PM) == Calendar.AM;
    }
    
    boolean pm() {
        return !am();
    }
    
    int firstDayOfWeek() {
        return createCalendar().getFirstDayOfWeek();
    }
    
    boolean isAfter(DateBase when) {
        return compareTo(when) > 0 ? true : false;
    }
    
    boolean isBefore(DateBase when) {
        return compareTo(when) < 0 ? true : false;
    }
    
    
    /**
     * @return The Julian day number that begins at noon of
     * this day
     * Positive year signifies A.D., negative year B.C.
     * Remember that the year after 1 B.C. was 1 A.D.
     *
     * A convenient reference point is that May 23, 1968 noon
     * is Julian day 2440000.
     *
     * Julian day 0 is a Monday.
     *
     * This algorithm is from Press et al., Numerical Recipes
     * in C, 2nd ed., Cambridge University Press 1992
     */
    int julian() {
        int year = year();
        int month = month() + 1;
        int day = day();
        
        int jy = year;
        if (year < 0) jy++;
        int jm = month;
        if (month > 2)
            jm++;
        else {
            jy--;
            jm += 13;
        }
        
        int jul = (int) (java.lang.Math.floor(365.25 * jy)
        + java.lang.Math.floor(30.6001*jm) + day + 1720995.0);
        
        int IGREG = 15 + 31*(10+12*1582);
        // Gregorian Calendar adopted Oct. 15, 1582
        
        if (day + 31 * (month + 12 * year) >= IGREG) {
            // change over to Gregorian calendar
            int ja = (int)(0.01 * jy);
            jul += 2 - ja + (int)(0.25 * ja);
        }
        return jul;
    }
    
    
    // - Date Manipulation methods
    DateBase addYear(int years) {
        return add(Calendar.YEAR, years);
    }
    
    DateBase addMonth(int months) {
        return add(Calendar.MONTH, months);
    }
    
    DateBase addDay(int days) {
        return add(Calendar.DAY_OF_MONTH, days);
    }
    
    DateBase addHour(int hours) {
        return add(Calendar.HOUR_OF_DAY, hours);
    }
    
    DateBase addMinute(int minutes) {
        return add(Calendar.MINUTE, minutes);
    }
    
    DateBase addSecond(int seconds) {
        return add(Calendar.SECOND, seconds);
    }
    
    DateBase addMilli(int millis) {
        return add(Calendar.MILLISECOND, millis);
    }
    
    DateBase clearTime() {
        if (hourOfDay() > 0 || minute() > 0 || second() > 0 || milli() > 0) {
            createCalendar().set(year(), month(), day(), 0, 0, 0);
            m_calendar.set(Calendar.MILLISECOND, 0);
            m_utilDate = m_calendar.getTime();
        }
        return this;
    }
    
    // difference in days... milliseconds are ignored !!!
    static float daysBetween(DateBase date1, DateBase date2) {
        int dayDiff = date1.julian() - date2.julian();
        
        int sec1 = date1.hourOfDay() * 3600 + date1.minute() * 60 + date1.second();
        int sec2 = date2.hourOfDay() * 3600 + date2.minute() * 60 + date2.second();
        float timeDiff = ((float) (sec1 - sec2))/(24 * 60 * 60);
        
        return dayDiff + timeDiff;
    }
    
    /** This parses the input String, returning a DateBase object with the corresponding value.
     * The input String can have values of the type - N, N + 1, N - 6..., where N = current datetime.
     * It can also have values of the type - T, T - 1, T + 2..., where T = todays date.
     * Additionally the input String can be a formatted String based on the input layout.
     * If the layout is null, then the default values are used from DateTimeFieldMetaData(if displayTime is true),
     * or DateOnlyFieldMetaData.
     * @param dateString the String to be parsed.
     * @param layout the format used by the input String.
     * @param displayTime determines which Default layout is to be used for parsing.
     * @return a DateBase object which has the value based on the input.
     * @throws ParseException if any error occurs in parsing.
     */
    static DateBase parse(String dateString, String layout, boolean displayTime) throws ParseException {
        DateBase dateBase = null;
        if (dateString != null) {
            String upperCaseString = dateString.trim().toUpperCase();
            if (upperCaseString.length() == 0)
                return dateBase;
            char startChar = upperCaseString.charAt(0);
            if (startChar == 'N') {
                dateBase = new DateBase();
                parseNadd(dateBase, upperCaseString);
                
                // This hack has been put, so that the Finders treat the 'n' as an absolute datetime and do not wild-card the hours/minutes/seconds
                // Hopefully no one will care if the 'n' doesnt ever return the seconds as zero !!!
                if (dateBase.second() == 0)
                    dateBase.addSecond(1);
            } else if (startChar == 'T') {
                dateBase = new DateBase();
                dateBase.clearTime();
                parseNadd(dateBase, upperCaseString);
            } else {
                String[] layouts = null;
                Date utilDate = null;
                ParseException parseException = null;
                if (layout != null)
                    layouts = new String[] {layout};
                else if (displayTime)
                    layouts = DateTimeFieldMetaData.getDateTimeParse();
                else
                    layouts = DateOnlyFieldMetaData.getDateOnlyParse();
                for (int i = 0; i < layouts.length; i++) {
                    // parse based on each layout, unitl successful !!!
                    try {
                        layout = LocaleHelper.determineLayout(layouts[i]);
                        DateFormat df = obtainDateFormat(layout);
                        df.setLenient(false);
                        utilDate = df.parse(dateString);
                        break;
                    } catch (ParseException e) {
                        // hold on to the first exception, since it holds the most significant layout
                        if (parseException == null)
                            parseException = e;
                        
                        // raise an exeception if we are done with all the layouts
                        if ((i + 1) == layouts.length) {
                            // attempt one final parse, assuming that the input dateString holds the time in milliseconds
                            try {
                                utilDate = new Date(Long.parseLong(dateString));
                                break;
                            } catch (Exception ex) {
                                throw parseException;
                            }
                        }
                    }
                }
                dateBase = new DateBase(utilDate);
            }
        }
        return dateBase;
    }

    /**
     * Returns a DateFormat instance, which can be used to parse/format dates
     * as per the input pattern.
     * @param pattern the pattern describing the date and time format.
     * @return a DateFormat instance.
     */
    static DateFormat obtainDateFormat(String pattern) {
        // The input pattern may contain additional configuration parameters; for eg. "yyyyMMdd HHmmss?timeZone=UTC"
        // Remove the configuration parameters from the pattern, and apply it on the DateFormat instance.
        Map<String, String> config = null;
        int i = pattern.indexOf('?');
        if (i > 0 && (i + 1) < pattern.length()) {
            String configString = pattern.substring(i + 1);
            pattern = pattern.substring(0, i);
            config = StringHelper.extractNameValuePairs(configString);
        }

        // Create the DateFormat
        SimpleDateFormat df;
        if (LocaleContext.getLocale() != null)
            df = new SimpleDateFormat(pattern, LocaleContext.getLocale());
        else
            df = new SimpleDateFormat(pattern);

        // Apply the config, if provided
        if (config != null) {
            //For now, only the timeZone parameter is supported
            String timeZone = config.get("timeZone");
            if (timeZone != null)
                df.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, 2000);
        df.set2DigitYearStart(cal.getTime());
        return df;
    }

    // *** PRIVATE METHODS ***
    // create a GregorianCalendar, if not already existing.. i.e only when needed
    private Calendar createCalendar() {
        if (m_calendar == null) {
            m_calendar = new GregorianCalendar();
            m_calendar.setTime(m_utilDate);
        }
        return m_calendar;
    }
    
    private int get(int field) {
        return createCalendar().get(field);
    }
    
    
    private DateBase add(int field, int value) {
        createCalendar().add(field, value);
        m_utilDate = m_calendar.getTime();
        return this;
    }
    
    private static void parseNadd(DateBase dateBase, String aString) throws ParseException {
        aString = aString.trim().toUpperCase();  //redundant.. but just in case
        StringBuffer buf = new StringBuffer();
        char operator = '#';  //some value other than +/-
        boolean isOperatorSet = false;
        
        for (int i = 1, length = aString.length(); i < length; i++) {
            char ch = aString.charAt(i);
            if (ch >= '0' && ch <= '9') {
                buf.append(ch);
                
            } else if (ch == '+' || ch == '-') {
                // evaluate in case buffer already has a value
                if (buf.length() > 0) {
                    parseNadd1(dateBase, 'D', buf, operator, i);
                    isOperatorSet = false;
                }
                
                // throw an exception in case the operator has already been set
                if (isOperatorSet) {
                    throw new ParseException("Too Many Operators", i);
                } else {
                    operator = ch;
                    isOperatorSet = true;
                }
                
            } else if (ch == ' ') {
                // evaluate in case buffer already has a value
                if (buf.length() > 0) {
                    parseNadd1(dateBase, 'D', buf, operator, i);
                    isOperatorSet = false;
                }
                
            } else {
                // The buffer should have a value
                if (buf.length() == 0)
                    throw new ParseException("Missing Value After Operator", i);
                
                parseNadd1(dateBase, ch, buf, operator, i);
                isOperatorSet = false;
                
                //NOTE: the operator itself isnt reset... this is to reuse the old operator
            }
        }
        
        // one final check
        if (buf.length() > 0)
            parseNadd1(dateBase, 'D', buf, operator, 0);
    }
    
    
    private static void parseNadd1(DateBase dateBase, char ch, StringBuffer buf, char operator, int offset) throws ParseException {
        int value = Integer.parseInt(buf.toString());
        if (operator == '+')
            value = value;
        else if (operator == '-')
            value = -value;
        else
            throw new ParseException("Invalid Operator " + operator,offset);
        
        
        if (ch == 'D')
            dateBase.addDay(value);
        else if (ch == 'Y')
            dateBase.addYear(value);
        else if (ch == 'M')
            dateBase.addMonth(value);
        else if (ch == 'H')
            dateBase.addHour(value);
        else
            throw new ParseException("Invalid Time Unit " + operator + ". Must be in H,D,M,Y", offset);
        
        // clear the buffer
        buf.setLength(0);
    }
    
}
