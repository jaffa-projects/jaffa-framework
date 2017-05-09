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

import java.io.Serializable;
import java.util.Calendar;
//import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;


/**
 * This class is used to hold DateOnly data.
 */
public class DateOnly implements Cloneable, Comparable, Serializable, IDateBase {
    
    // *** PRIVATE FIELDS ***
    // NOTE: keep the clone() method in sync for all the fields added/removed
    private DateBase m_dateBase = null;
    
    
    // *** CONSTRUCTORS ***
    /** Creates an instance with the current date.
     */
    public DateOnly() {
        this(new DateBase());
    }
    
    /** Creates an instance initialized to the input value.
     * @param timeInMillis the initial value.
     */
    public DateOnly(long timeInMillis) {
        this(new DateBase(timeInMillis));
    }
    
    /** Creates an instance initialized to the input value.
     * @param utilDate the initial value.
     */
    public DateOnly(java.util.Date utilDate) {
        this(new DateBase(utilDate));
    }
    
    /** Creates an instance initialized to the input value.
     * @param calendar the initial value.
     */
    public DateOnly(Calendar calendar) {
        this(new DateBase(calendar));
    }
    
    /** Creates an instance initialized to the input value.
     * @param year the year.
     * @param month the month.
     * @param day the day.
     */
    public DateOnly(int year,int month,int day) {
        m_dateBase = new DateBase(year, month, day);
    }
    
    private DateOnly(DateBase aDateBase) {
        aDateBase.clearTime();
        m_dateBase = aDateBase;
    }
    
    
    
    // *** PUBLIC METHODS ***
    // - Some standard Methods
    
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        Object obj = super.clone();
        if (m_dateBase != null)
            ((DateOnly) obj).m_dateBase = (DateBase) m_dateBase.clone();
        return obj;
    }
    
    /** Compares this object with another DateOnly object.
     * @param obj the other DateOnly object.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    public int compareTo(Object obj) {
        DateOnly target = (DateOnly) obj;
        return m_dateBase.compareTo(target.m_dateBase);
    }
    
    /** Compares this object with another DateOnly object.
     * Returns a true if both the objects have the same value.
     * @param obj the other DateOnly object.
     * @return a true if both the objects have the same value.
     */
    public boolean equals(Object obj) {
        if (obj instanceof DateOnly) {
            DateOnly target = (DateOnly) obj;
            return m_dateBase.equals(target.m_dateBase);
        } else {
            return false;
        }
    }
    
    /** Returns the hashCode of the value.
     * @return the hashCode of the value.
     */
    public int hashCode() {
        return m_dateBase.hashCode();
    }
    
    /** Returns the diagnostic information.
     * @return the diagnostic information.
     */
    public String toString() {
        return Formatter.format(this);
    }
    
    
    
    
    // - Getters/Setters
    /** Returns the value as a java.util.Date object.
     * @return the value as a java.util.Date object.
     */
    public java.util.Date getUtilDate() {
        return m_dateBase.getUtilDate();
    }
    
    /** Sets the date.
     * NOTE: This method is meant for the various tools that instantiate objects using reflection.
     * @param utilDate the new date.
     */
    public void setUtilDate(java.util.Date utilDate) {
        m_dateBase.setUtilDate(utilDate);
        m_dateBase.clearTime();
    }
    
    
    
    
    // - Return standard Java Date Objects
    
    /** Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this object.
     * @return the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this object.
     */
    public long timeInMillis() {
        return m_dateBase.timeInMillis();
    }
    
    /** Returns the Calendar.
     * @return the Calendar.
     */
    public Calendar calendar() {
        return m_dateBase.calendar();
    }
    
    /** Returns the value as a java.sql.Date object.
     * @return the value as a java.sql.Date object.
     */
    public java.sql.Date sqlDate() {
        return m_dateBase.sqlDate();
    }
    
    /** Returns the value as a java.sql.Time object.
     * @return the value as a java.sql.Time object.
     */
    public Time sqlTime() {
        return m_dateBase.sqlTime();
    }
    
    /** Returns the value as a java.sql.Timestamp object.
     * @return the value as a java.sql.Timestamp object.
     */
    public Timestamp timestamp() {
        return m_dateBase.timestamp();
    }
    
    
    
    
    // - Additional Getters
    
    /** Returns the year.
     * @return the year.
     */
    public int year() {
        return m_dateBase.year();
    }
    
    /** Returns the month.
     * @return the month.
     */
    public int month() {
        return m_dateBase.month();
    }
    
    /** Returns the day.
     * @return the day.
     */
    public int day() {
        return m_dateBase.day();
    }
    
    /** Returns the day of the week.
     * @return the day of the week.
     */
    public int dayOfWeek() {
        return m_dateBase.dayOfWeek();
    }
    
    /** Returns the day of the week in the month.
     * @return the day of the week in the month.
     */
    public int dayOfWeekInMonth() {
        return m_dateBase.dayOfWeekInMonth();
    }
    
    /** Returns the day of the year.
     * @return the day of the year.
     */
    public int dayOfYear() {
        return m_dateBase.dayOfYear();
    }
    
    /** Returns the week of the month.
     * @return the week of the month.
     */
    public int weekOfMonth() {
        return m_dateBase.weekOfMonth();
    }
    
    /** Returns the week of the year.
     * @return the week of the year.
     */
    public int weekOfYear() {
        return m_dateBase.weekOfYear();
    }
    
    /** Returns the first day of the week.
     * @return the first day of the week.
     */
    public int firstDayOfWeek() {
        return m_dateBase.firstDayOfWeek();
    }
    
    /** Returns a true if this date is after the input date.
     * @param when the input date.
     * @return a true if this date is after the input date.
     */
    public boolean isAfter(DateOnly when) {
        return m_dateBase.isAfter(when.m_dateBase);
    }
    
    /** Returns a true if this date is before the input date.
     * @param when the input date.
     * @return a true if this date is before the input date.
     */
    public boolean isBefore(DateOnly when) {
        return m_dateBase.isBefore(when.m_dateBase);
    }
    
    /** Returns the corresponding julian value.
     * @return the corresponding julian value.
     */
    public int julian() {
        return m_dateBase.julian();
    }
    
    
    
    // - Date Manipulation methods
    
    /** Create a new DateOnly object by adding the input years to the value of the input date.
     * If the input years is a negative number, then the years will be subtracted.
     * @param date the input date.
     * @param years the years to add.
     * @return a DateOnly object with the value of the input date, incremented by the input years.
     */
    public static DateOnly addYear(DateOnly date, int years) {
        return new DateOnly(new DateBase(date.m_dateBase).addYear(years));
    }
    
    /** Create a new DateOnly object by adding the input months to the value of the input date.
     * If the input months is a negative number, then the months will be subtracted.
     * @param date the input date.
     * @param months the months to add.
     * @return a DateOnly object with the value of the input date, incremented by the input months.
     */
    public static DateOnly addMonth(DateOnly date, int months) {
        return new DateOnly(new DateBase(date.m_dateBase).addMonth(months));
    }
    
    /** Create a new DateOnly object by adding the input days to the value of the input date.
     * If the input days is a negative number, then the days will be subtracted.
     * @param date the input date.
     * @param days the days to add.
     * @return a DateOnly object with the value of the input date, incremented by the input days.
     */
    public static DateOnly addDay(DateOnly date, int days) {
        return new DateOnly(new DateBase(date.m_dateBase).addDay(days));
    }
    
    /** Returns the number of days between two dates.
     * @param date1 the first date.
     * @param date2 the second date.
     * @return the number of days between two dates.
     */
    public static int daysBetween(DateOnly date1, DateOnly date2) {
        return (int) DateBase.daysBetween(date1.m_dateBase, date2.m_dateBase);
    }
    
    /** This parses the input String, returning a DateOnly object with the corresponding value.
     * The input String can have values of the type - N, N + 1, N - 6..., where N = current datetime.
     * It can also have values of the type - T, T - 1, T + 2..., where T = todays date.
     * Additionally the input String can be a formatted String based on the default layout of the DateOnlyFieldMetaData.
     * @param dateString the String to be parsed.
     * @return a DateOnly object which has the value based on the input.
     * @throws ParseException if any error occurs in parsing.
     */
    public static DateOnly parse(String dateString) throws ParseException {
        return parse(dateString, null);
    }
    
    /** This parses the input String, returning a DateOnly object with the corresponding value.
     * The input String can have values of the type - N, N + 1, N - 6..., where N = current datetime.
     * It can also have values of the type - T, T - 1, T + 2..., where T = todays date.
     * Additionally the input String can be a formatted String based on the input layout or the default layout of the DateOnlyFieldMetaData.
     * @param dateString the String to be parsed.
     * @param layout the format used by the input String.
     * @return a DateOnly object which has the value based on the input.
     * @throws ParseException if any error occurs in parsing.
     */
    public static DateOnly parse(String dateString, String layout) throws ParseException {
        DateBase d = DateBase.parse(dateString, layout, false);
        return d != null ? new DateOnly(d) : null;
    }
    
    /** Returns a DateTime object initialized to the input date.
     * @param date the input date.
     * @return a DateTime object initialized to the input date.
     */
    public static DateTime toDateTime(DateOnly date) {
        return new DateTime(date.timeInMillis());
    }
    
}
