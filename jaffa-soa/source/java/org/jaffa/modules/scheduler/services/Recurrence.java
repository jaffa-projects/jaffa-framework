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

package org.jaffa.modules.scheduler.services;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import org.jaffa.datatypes.exceptions.BelowMinimumException;
import org.jaffa.datatypes.exceptions.ExceedsMaximumException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;

/** This class is contained by the ScheduleTask class and contains the Trigger attributes it requires.
 */
@XmlRootElement
public abstract class Recurrence implements Serializable {
    
    /** An enumeration of week frequencies. */
    public enum WeekFrequency {FIRST, SECOND, THIRD, FOURTH, LAST, EVERY}
    
    /** An enumeration of week days. */
    public enum WeekDay {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}
    
    /** An enumeration of months. */
    public enum Month {JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER}
    
    
    /** Validates this instance.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public abstract void validate() throws FrameworkException, ApplicationExceptions;
    
    
    /** This class can be used to represent a Recurrence in hours, minutes and seconds.
     */
    @XmlRootElement
    public static class Often extends Recurrence {
        private Integer hours;
        private Integer minutes;
        private Integer seconds;
        
        /** Creates an instance.
         */
        public Often() {
        }
        
        /** Creates an instance.
         * A valid instance should contain non-zero values for at least one of hours, minutes or seconds.
         * @param hours frequency in hours. Should not be less than 0.
         * @param minutes frequency in minutes. Should be between 0 and 59.
         * @param seconds frequency in seconds. Should be between 0 and 59.
         */
        public Often(Integer hours, Integer minutes, Integer seconds) {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }
        
        /**
         * Getter for property hours.
         * @return Value of property hours.
         */
        public Integer getHours() {
            return this.hours;
        }
        
        /**
         * Setter for property hours.
         * @param hours New value of property hours.
         */
        public void setHours(Integer hours) {
            this.hours = hours;
        }
        
        /**
         * Getter for property minutes.
         * @return Value of property minutes.
         */
        public Integer getMinutes() {
            return this.minutes;
        }
        
        /**
         * Setter for property minutes.
         * @param minutes New value of property minutes.
         */
        public void setMinutes(Integer minutes) {
            this.minutes = minutes;
        }
        
        /**
         * Getter for property seconds.
         * @return Value of property seconds.
         */
        public Integer getSeconds() {
            return this.seconds;
        }
        
        /**
         * Setter for property seconds.
         * @param seconds New value of property seconds.
         */
        public void setSeconds(Integer seconds) {
            this.seconds = seconds;
        }
        
        /** Validates this instance.
         * @throws FrameworkException in case any internal error occurs.
         * @throws ApplicationExceptions Indicates application error(s).
         */
        public void validate() throws FrameworkException, ApplicationExceptions {
            ApplicationExceptions appExps = new ApplicationExceptions();
            
            if ((getHours() == null || getHours() == 0) && (getMinutes() == null || getMinutes() == 0) && (getSeconds() == null || getSeconds() == 0))
                appExps.add(new JaffaSchedulerApplicationException(JaffaSchedulerApplicationException.RECURRENCE_MISSING_VALUE));
            
            if (getHours() != null) {
                if (getHours() < 0)
                    appExps.add(new BelowMinimumException("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.OFTEN.Hours]", new Object[] {"0"}));
            }
            if (getMinutes() != null) {
                if (getMinutes() < 0)
                    appExps.add(new BelowMinimumException("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.OFTEN.Minutes]", new Object[] {"0"}));
                else if (getMinutes() > 59)
                    appExps.add(new ExceedsMaximumException("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.OFTEN.Minutes]", new Object[] {"59"}));
            }
            if (getSeconds() != null) {
                if (getSeconds() < 0)
                    appExps.add(new BelowMinimumException("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.OFTEN.Seconds]", new Object[] {"0"}));
                else if (getSeconds() > 59)
                    appExps.add(new ExceedsMaximumException("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.OFTEN.Seconds]", new Object[] {"59"}));
            }
            
            if (appExps != null && appExps.size() > 0)
                throw appExps;
        }
        
        /** Returns debug info.
         * @return debug info.
         */
        public String toString() {
            StringBuilder buf = new StringBuilder("<Often>")
            .append("<hours>").append(hours != null ? hours : "").append("</hours>")
            .append("<minutes>").append(minutes != null ? minutes : "").append("</minutes>")
            .append("<seconds>").append(seconds != null ? seconds : "").append("</seconds>")
            .append("</Often>");
            return buf.toString();
        }
    }
    
    
    /** This class can be used to represent a Recurrence in days.
     */
    @XmlRootElement
    public static class Daily extends Recurrence {
        private Boolean weekDaysOnly;
        
        /** Creates an instance.
         */
        public Daily() {
        }
        
        /** Creates an instance.
         * @param weekDaysOnly true signifies that the Recurrence will be on week days only, else the Recurrence will be every day.
         */
        public Daily(Boolean weekDaysOnly) {
            this.weekDaysOnly = weekDaysOnly;
        }
        
        /**
         * Getter for property weekDaysOnly.
         * @return Value of property weekDaysOnly.
         */
        public Boolean getWeekDaysOnly() {
            return this.weekDaysOnly;
        }
        
        /**
         * Setter for property weekDaysOnly.
         * @param weekDaysOnly New value of property weekDaysOnly.
         */
        public void setWeekDaysOnly(Boolean weekDaysOnly) {
            this.weekDaysOnly = weekDaysOnly;
        }
        
        /** Validates this instance.
         * @throws FrameworkException in case any internal error occurs.
         * @throws ApplicationExceptions Indicates application error(s).
         */
        public void validate() throws FrameworkException, ApplicationExceptions {
            // no validations required
        }
        
        /** Returns debug info.
         * @return debug info.
         */
        public String toString() {
            StringBuilder buf = new StringBuilder("<Daily>")
            .append("<weekDaysOnly>").append(weekDaysOnly != null ? weekDaysOnly : "").append("</weekDaysOnly>")
            .append("</Daily>");
            return buf.toString();
        }
    }
    
    
    /** This class can be used to represent a Recurrence in weeks.
     */
    @XmlRootElement
    public static class Weekly extends Recurrence {
        private WeekFrequency frequency;
        private WeekDay day;
        
        /** Creates an instance.
         */
        public Weekly() {
        }
        
        /** Creates an instance.
         * A valid instance should contain valid values for all the properties.
         * @param frequency the week frequency.
         * @param day the week day.
         */
        public Weekly(WeekFrequency frequency, WeekDay day) {
            this.frequency = frequency;
            this.day = day;
        }
        
        /**
         * Getter for property frequency.
         * @return Value of property frequency.
         */
        public WeekFrequency getFrequency() {
            return this.frequency;
        }
        
        /**
         * Setter for property frequency.
         * @param frequency New value of property frequency.
         */
        public void setFrequency(WeekFrequency frequency) {
            this.frequency = frequency;
        }
        
        /**
         * Getter for property day.
         * @return Value of property day.
         */
        public WeekDay getDay() {
            return this.day;
        }
        
        /**
         * Setter for property day.
         * @param day New value of property day.
         */
        public void setDay(WeekDay day) {
            this.day = day;
        }
        
        /** Validates this instance.
         * @throws FrameworkException in case any internal error occurs.
         * @throws ApplicationExceptions Indicates application error(s).
         */
        public void validate() throws FrameworkException, ApplicationExceptions {
            if (getFrequency() == null || getDay() == null)
                throw new ApplicationExceptions(new JaffaSchedulerApplicationException(JaffaSchedulerApplicationException.RECURRENCE_MISSING_VALUE));
        }
        
        /** Returns debug info.
         * @return debug info.
         */
        public String toString() {
            StringBuilder buf = new StringBuilder("<Weekly>")
            .append("<frequency>").append(frequency != null ? frequency : "").append("</frequency>")
            .append("<day>").append(day != null ? day : "").append("</day>")
            .append("</Weekly>");
            return buf.toString();
        }
    }
    
    
    /** This class can be used to represent a Recurrence in months.
     */
    @XmlRootElement
    public static class Monthly extends Recurrence {
        private Integer day;
        private WeekFrequency weekFrequency;
        private WeekDay weekDay;
        private Month[] months;
        
        /** Creates an instance.
         */
        public Monthly() {
        }
        
        /** Creates an instance.
         * A valid instance should contain valid values for either day or weekFrequency+weekDay, and also at least one element in the months array.
         * @param day the day of the month. Should be 1 and 31.
         * @param weekFrequency the week frequency.
         * @param weekDay the week day.
         * @param months an array of months.
         */
        public Monthly(Integer day, WeekFrequency weekFrequency, WeekDay weekDay, Month[] months) {
            this.day = day;
            this.weekFrequency = weekFrequency;
            this.weekDay = weekDay;
            this.months = months;
        }
        
        /**
         * Getter for property day.
         * @return Value of property day.
         */
        public Integer getDay() {
            return this.day;
        }
        
        /**
         * Setter for property day.
         * @param day New value of property day.
         */
        public void setDay(Integer day) {
            this.day = day;
        }
        
        /**
         * Getter for property weekFrequency.
         * @return Value of property weekFrequency.
         */
        public WeekFrequency getWeekFrequency() {
            return this.weekFrequency;
        }
        
        /**
         * Setter for property weekFrequency.
         * @param weekFrequency New value of property weekFrequency.
         */
        public void setWeekFrequency(WeekFrequency weekFrequency) {
            this.weekFrequency = weekFrequency;
        }
        
        /**
         * Getter for property weekDay.
         * @return Value of property weekDay.
         */
        public WeekDay getWeekDay() {
            return this.weekDay;
        }
        
        /**
         * Setter for property weekDay.
         * @param weekDay New value of property weekDay.
         */
        public void setWeekDay(WeekDay weekDay) {
            this.weekDay = weekDay;
        }
        
        /**
         * Getter for property months.
         * @return Value of property months.
         */
        public Month[] getMonths() {
            return this.months;
        }
        
        /**
         * Setter for property months.
         * @param months New value of property months.
         */
        public void setMonths(Month[] months) {
            this.months = months;
        }
        
        /** Validates this instance.
         * @throws FrameworkException in case any internal error occurs.
         * @throws ApplicationExceptions Indicates application error(s).
         */
        public void validate() throws FrameworkException, ApplicationExceptions {
            ApplicationExceptions appExps = new ApplicationExceptions();
            
            if (getMonths() == null || getMonths().length == 0 ||
                    ((getDay() == null || getDay() == 0) && (getWeekFrequency() == null || getWeekDay() == null)))
                appExps.add(new JaffaSchedulerApplicationException(JaffaSchedulerApplicationException.RECURRENCE_MISSING_VALUE));
            
            if (getDay() != null) {
                if (getDay() < 1)
                    appExps.add(new BelowMinimumException("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Day]", new Object[] {"1"}));
                else if (getDay() > 31)
                    appExps.add(new ExceedsMaximumException("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Day]", new Object[] {"31"}));
            }
            
            if (appExps != null && appExps.size() > 0)
                throw appExps;
        }
        
        /** Returns debug info.
         * @return debug info.
         */
        public String toString() {
            StringBuilder buf = new StringBuilder("<Monthly>")
            .append("<day>").append(day != null ? day : "").append("</day>")
            .append("<weekFrequency>").append(weekFrequency != null ? weekFrequency : "").append("</weekFrequency>")
            .append("<weekDay>").append(weekDay != null ? weekDay : "").append("</weekDay>")
            .append("<months>").append(months != null ? months : "").append("</months>")
            .append("</Monthly>");
            return buf.toString();
        }
    }
    
    
    /** This class can be used to represent a Recurrence in years.
     */
    @XmlRootElement
    public static class Yearly extends Recurrence {
        private Integer frequency;
        private Month on;
        
        /** Creates an instance.
         */
        public Yearly() {
        }
        
        /** Creates an instance.
         * A valid instance should contain valid values for all the properties.
         * @param frequency frequency in years. Should be at least 1.
         * @param on a month.
         */
        public Yearly(Integer frequency, Month on) {
            this.frequency = frequency;
            this.on = on;
        }
        
        /**
         * Getter for property frequency.
         * @return Value of property frequency.
         */
        public Integer getFrequency() {
            return this.frequency;
        }
        
        /**
         * Setter for property frequency.
         * @param frequency New value of property frequency.
         */
        public void setFrequency(Integer frequency) {
            this.frequency = frequency;
        }
        
        /**
         * Getter for property on.
         * @return Value of property on.
         */
        public Month getOn() {
            return this.on;
        }
        
        /**
         * Setter for property on.
         * @param on New value of property on.
         */
        public void setOn(Month on) {
            this.on = on;
        }
        
        /** Validates this instance.
         * @throws FrameworkException in case any internal error occurs.
         * @throws ApplicationExceptions Indicates application error(s).
         */
        public void validate() throws FrameworkException, ApplicationExceptions {
            ApplicationExceptions appExps = new ApplicationExceptions();
            
            if (getFrequency() == null || getFrequency() == 0 || getOn() == null)
                appExps.add(new JaffaSchedulerApplicationException(JaffaSchedulerApplicationException.RECURRENCE_MISSING_VALUE));
            
            if (getFrequency() != null) {
                if (getFrequency() < 1)
                    appExps.add(new BelowMinimumException("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.YEARLY.Frequency]", new Object[] {"1"}));
            }
            
            if (appExps != null && appExps.size() > 0)
                throw appExps;
        }
        
        /** Returns debug info.
         * @return debug info.
         */
        public String toString() {
            StringBuilder buf = new StringBuilder("<Yearly>")
            .append("<frequency>").append(frequency != null ? frequency : "").append("</frequency>")
            .append("<on>").append(on != null ? on : "").append("</on>")
            .append("</Yearly>");
            return buf.toString();
        }
    }
    
    
    /** This class can be used to represent a Recurrence in a custom expression.
     */
    @XmlRootElement
    public static class Custom extends Recurrence {
        private String customPattern;
        
        /** Creates an instance.
         */
        public Custom() {
        }
        
        /** Creates an instance.
         * A valid instance should contain valid values for all the properties.
         * @param customPattern A custom expression.
         */
        public Custom(String customPattern) {
            this.customPattern = customPattern;
        }
        
        /**
         * Getter for property customPattern.
         * @return Value of property customPattern.
         */
        public String getCustomPattern() {
            return this.customPattern;
        }
        
        /**
         * Setter for property customPattern.
         * @param customPattern New value of property customPattern.
         */
        public void setCustomPattern(String customPattern) {
            this.customPattern = customPattern;
        }
        
        /** Validates this instance.
         * @throws FrameworkException in case any internal error occurs.
         * @throws ApplicationExceptions Indicates application error(s).
         */
        public void validate() throws FrameworkException, ApplicationExceptions {
            if (getCustomPattern() == null)
                throw new ApplicationExceptions(new JaffaSchedulerApplicationException(JaffaSchedulerApplicationException.RECURRENCE_MISSING_VALUE));
        }
        
        /** Returns debug info.
         * @return debug info.
         */
        public String toString() {
            StringBuilder buf = new StringBuilder("<Custom>")
            .append("<customPattern>").append(customPattern != null ? customPattern : "").append("</customPattern>")
            .append("</Custom>");
            return buf.toString();
        }
    }
    
}

