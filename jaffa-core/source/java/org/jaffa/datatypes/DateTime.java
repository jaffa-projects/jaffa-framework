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

import org.jaffa.datatypes.exceptions.DateTimeRoundingPrecisionException;

/**
 * This class is used to hold DateTime data.
 */
public class DateTime implements Cloneable, Comparable, Serializable, IDateBase {
    
    // *** PRIVATE FIELDS ***
    // NOTE: keep the clone() method in sync for all the fields added/removed
    private DateBase m_dateBase = null;
    
    
    
    // *** CONSTRUCTORS ***
    /** Creates an instance with the current datetime.
     */
    public DateTime() {
        m_dateBase = new DateBase();
    }
    
    /** Creates an instance initialized to the input value.
     * @param timeInMillis the initial value.
     */
    public DateTime(long timeInMillis) {
        m_dateBase = new DateBase(timeInMillis);
    }
    
    /** Creates an instance initialized to the input value.
     * @param utilDate the initial value.
     */
    public DateTime(java.util.Date utilDate) {
        m_dateBase = new DateBase(utilDate);
    }
    
    /** Creates an instance initialized to the input value.
     * @param calendar the initial value.
     */
    public DateTime(Calendar calendar) {
        m_dateBase = new DateBase(calendar);
    }
    
    /** Creates an instance initialized to the input value.
     * @param year the year.
     * @param month the month.
     * @param day the day.
     */
    public DateTime(int year,int month,int day) {
        m_dateBase = new DateBase(year, month, day);
    }
    
    /** Creates an instance initialized to the input value.
     * @param year the year.
     * @param month the month.
     * @param day the day.
     * @param hourOfDay the hour of the day (i.e. a 24 hour clock).
     * @param minute the minute.
     * @param second the second.
     * @param milli the milli.
     */
    public DateTime(int year, int month, int day, int hourOfDay, int minute, int second, int milli) {
        m_dateBase = new DateBase(year, month, day, hourOfDay, minute, second, milli);
    }
    
    private DateTime(DateBase aDateBase) {
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
            ((DateTime) obj).m_dateBase = (DateBase) m_dateBase.clone();
        return obj;
    }
    
    /** Compares this object with another DateTime object.
     * @param obj the other DateTime object.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    public int compareTo(Object obj) {
        DateTime target = (DateTime) obj;
        return m_dateBase.compareTo(target.m_dateBase);
    }
    
    /** Compares this object with another DateTime object.
     * Returns a true if both the objects have the same value.
     * @param obj the other DateTime object.
     * @return a true if both the objects have the same value.
     */
    public boolean equals(Object obj) {
        if (obj instanceof DateTime) {
            DateTime target = (DateTime) obj;
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
    
    /** Returns the hour.
     * @return the hour.
     */
    public int hour() {
        return m_dateBase.hour();
    }
    
    /** Returns the hour (a 24 hour clock).
     * @return the hour.
     */
    public int hourOfDay() {
        return m_dateBase.hourOfDay();
    }
    
    /** Returns the minute.
     * @return the minute.
     */
    public int minute() {
        return m_dateBase.minute();
    }
    
    /** Returns the second.
     * @return the second.
     */
    public int second() {
        return m_dateBase.second();
    }
    
    /** Returns the milliseconds.
     * @return the milliseconds.
     */
    public int milli() {
        return m_dateBase.milli();
    }
    
    /** Returns a true if the datetime is before 12:00 pm.
     * @return a true if the datetime is before 12:00 pm.
     */
    public boolean am() {
        return m_dateBase.am();
    }
    
    /** Returns a true if the datetime is after 12:00 pm.
     * @return a true if the datetime is after 12:00 pm.
     */
    public boolean pm() {
        return m_dateBase.pm();
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
    public boolean isAfter(DateTime when) {
        return m_dateBase.isAfter(when.m_dateBase);
    }
    
    /** Returns a true if this date is before the input date.
     * @param when the input date.
     * @return a true if this date is before the input date.
     */
    public boolean isBefore(DateTime when) {
        return m_dateBase.isBefore(when.m_dateBase);
    }
    
    /** Returns the corresponding julian value.
     * @return the corresponding julian value.
     */
    public int julian() {
        return m_dateBase.julian();
    }
    
    
    
    // - Date Manipulation methods
    
    /** Create a new DateTime object by adding the input years to the value of the input datetime.
     * If the input years is a negative number, then the years will be subtracted.
     * @param datetime the input datetime.
     * @param years the years to add.
     * @return a DateTime object with the value of the input date, incremented by the input years.
     */
    public static DateTime addYear(DateTime datetime, int years) {
        return new DateTime(new DateBase(datetime.m_dateBase).addYear(years));
    }
    
    /** Create a new DateTime object by adding the input months to the value of the input datetime.
     * If the input months is a negative number, then the months will be subtracted.
     * @param datetime the input datetime.
     * @param months the months to add.
     * @return a DateTime object with the value of the input date, incremented by the input months.
     */
    public static DateTime addMonth(DateTime datetime, int months) {
        return new DateTime(new DateBase(datetime.m_dateBase).addMonth(months));
    }
    
    /** Create a new DateTime object by adding the input days to the value of the input datetime.
     * If the input days is a negative number, then the days will be subtracted.
     * @param datetime the input datetime.
     * @param days the days to add.
     * @return a DateTime object with the value of the input date, incremented by the input days.
     */
    public static DateTime addDay(DateTime datetime, int days) {
        return new DateTime(new DateBase(datetime.m_dateBase).addDay(days));
    }
    
    /** Create a new DateTime object by adding the input hours to the value of the input datetime.
     * If the input hours is a negative number, then the hours will be subtracted.
     * @param datetime the input datetime.
     * @param hours the hours to add.
     * @return a DateTime object with the value of the input date, incremented by the input hours.
     */
    public static DateTime addHour(DateTime datetime, int hours) {
        return new DateTime(new DateBase(datetime.m_dateBase).addHour(hours));
    }
    
    /** Create a new DateTime object by adding the input minutes to the value of the input datetime.
     * If the input minutes is a negative number, then the minutes will be subtracted.
     * @param datetime the input datetime.
     * @param minutes the minutes to add.
     * @return a DateTime object with the value of the input date, incremented by the input minutes.
     */
    public static DateTime addMinute(DateTime datetime, int minutes) {
        return new DateTime(new DateBase(datetime.m_dateBase).addMinute(minutes));
    }
    
    /** Create a new DateTime object by adding the input seconds to the value of the input datetime.
     * If the input seconds is a negative number, then the seconds will be subtracted.
     * @param datetime the input datetime.
     * @param seconds the seconds to add.
     * @return a DateTime object with the value of the input date, incremented by the input seconds.
     */
    public static DateTime addSecond(DateTime datetime, int seconds) {
        return new DateTime(new DateBase(datetime.m_dateBase).addSecond(seconds));
    }
    
    /** Create a new DateTime object by adding the input milliseconds to the value of the input datetime.
     * If the input milliseconds is a negative number, then the milliseconds will be subtracted.
     * @param datetime the input datetime.
     * @param millis the milliseconds to add.
     * @return a DateTime object with the value of the input date, incremented by the input milliseconds.
     */
    public static DateTime addMilli(DateTime datetime, int millis) {
        return new DateTime(new DateBase(datetime.m_dateBase).addMilli(millis));
    }
    
    /** Returns a new DateTime object, having the date of the input object, but the time portion set to zero.
     * @param datetime the input datetime.
     * @return a new DateTime object, having the date of the input object, but the time portion set to zero.
     */
    public static DateTime clearTime(DateTime datetime) {
        return new DateTime(new DateBase(datetime.m_dateBase).clearTime());
    }
    
    /** Returns the number of days between two dates.
     * @param date1 the first date.
     * @param date2 the second date.
     * @return the number of days between two dates.
     */
    public static float daysBetween(DateTime date1, DateTime date2) {
        return DateBase.daysBetween(date1.m_dateBase, date2.m_dateBase);
    }
    
    /** This parses the input String, returning a DateTime object with the corresponding value.
     * The input String can have values of the type - N, N + 1, N - 6..., where N = current datetime.
     * It can also have values of the type - T, T - 1, T + 2..., where T = todays date.
     * Additionally the input String can be a formatted String based on the default layout of the DateTimeFieldMetaData.
     * @param dateString the String to be parsed.
     * @return a DateTime object which has the value based on the input.
     * @throws ParseException if any error occurs in parsing.
     */
    public static DateTime parse(String dateString) throws ParseException {
        return parse(dateString, null);
    }
    
    /** This parses the input String, returning a DateTime object with the corresponding value.
     * The input String can have values of the type - N, N + 1, N - 6..., where N = current datetime.
     * It can also have values of the type - T, T - 1, T + 2..., where T = todays date.
     * Additionally the input String can be a formatted String based on the input layout or the default layout of the DateTimeFieldMetaData.
     * @param dateString the String to be parsed.
     * @param layout the format used by the input String.
     * @return a DateTime object which has the value based on the input.
     * @throws ParseException if any error occurs in parsing.
     */
    public static DateTime parse(String dateString, String layout) throws ParseException {
        DateBase d = DateBase.parse(dateString, layout, true);
        return d != null ? new DateTime(d.timeInMillis()) : null;
    }
    
    /** Returns a DateOnly object initialized to the input date.
     * @param datetime the input date.
     * @return a DateOnly object initialized to the input date.
     */
    public static DateOnly toDateOnly(DateTime datetime) {
        return new DateOnly(datetime.timeInMillis());
    }

    /** Performs rounding to minute field.
     *
     * @param precision must be a factor of 60, such as 30, 20, 15, 10, 6, 5, 4, 2, 1, 0.
     *                  When 0, sets minute field to 0.
     *                  When 1, rounds second field to minute
     *                  When >1, precision is the unit of interval in minutes.
     * @param rule U, round up (ceiling); D, round down (floor); N, round to nearest (default)
     */
    public void roundMinutes(int precision, String rule) throws DateTimeRoundingPrecisionException {
        if (precision<0 || (precision>1 && 60 != (60/precision)*precision)) {
            throw new DateTimeRoundingPrecisionException();
        }

        Calendar d = calendar();
        if (precision==0) {
            d.set(Calendar.MILLISECOND, 0);
            d.set(Calendar.SECOND, 0);
            d.set(Calendar.MINUTE, 0);
        } else if ("U".equals(rule)) {
            if (d.get(Calendar.MILLISECOND)>0) {
                d.add(Calendar.SECOND,1);
                d.set(Calendar.MILLISECOND, 0);
            }
            if (d.get(Calendar.SECOND)>0) {
                d.add(Calendar.MINUTE,1);
                d.set(Calendar.SECOND, 0);
            }
            if (precision>1) d.set(Calendar.MINUTE, ((d.get(Calendar.MINUTE)+precision-1)/precision)*precision);
        } else if ("D".equals(rule)) {
            d.set(Calendar.MILLISECOND, 0);
            d.set(Calendar.SECOND, 0);
            if (precision>1) d.set(Calendar.MINUTE, (d.get(Calendar.MINUTE)/precision)*precision);
        } else {
            if ((d.get(Calendar.MILLISECOND)/500)==1) {
                d.add(Calendar.SECOND, 1);
            }
            d.set(Calendar.MILLISECOND, 0);
            if ((d.get(Calendar.SECOND)/30)==1) {
                d.add(Calendar.MINUTE, 1);
            }
            d.set(Calendar.SECOND, 0);
            if (precision>1) d.set(Calendar.MINUTE, ((d.get(Calendar.MINUTE)+precision/2)/precision)*precision);
        }

        setUtilDate(d.getTime());
    }
}
