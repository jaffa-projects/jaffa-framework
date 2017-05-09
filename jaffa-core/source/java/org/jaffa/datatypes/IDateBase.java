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

import java.util.Calendar;

/**
 * This interface has the various constants used by the Date classes.
 */
public interface IDateBase {

    /** The constant for the month - January.*/
    public static final int JANUARY = Calendar.JANUARY;

    /** The constant for the month - February.*/
    public static final int FEBRUARY = Calendar.FEBRUARY;

    /** The constant for the month - March.*/
    public static final int MARCH = Calendar.MARCH;

    /** The constant for the month - April.*/
    public static final int APRIL = Calendar.APRIL;

    /** The constant for the month - May.*/
    public static final int MAY = Calendar.MAY;

    /** The constant for the month - June.*/
    public static final int JUNE = Calendar.JUNE;

    /** The constant for the month - July.*/
    public static final int JULY = Calendar.JULY;

    /** The constant for the month - August.*/
    public static final int AUGUST = Calendar.AUGUST;

    /** The constant for the month - September.*/
    public static final int SEPTEMBER = Calendar.SEPTEMBER;

    /** The constant for the month - October.*/
    public static final int OCTOBER = Calendar.OCTOBER;

    /** The constant for the month - November.*/
    public static final int NOVEMBER = Calendar.NOVEMBER;

    /** The constant for the month - December.*/
    public static final int DECEMBER = Calendar.DECEMBER;

    /** The constant for the month - Undecimber - the 13th month.*/
    public static final int UNDECIMBER = Calendar.UNDECIMBER;

    /** The constant for the day - Sunday.*/
    public static final int SUNDAY = Calendar.SUNDAY;

    /** The constant for the day - Monday.*/
    public static final int MONDAY = Calendar.MONDAY;

    /** The constant for the day - Tuesday.*/
    public static final int TUESDAY = Calendar.TUESDAY;

    /** The constant for the day - Wednesday.*/
    public static final int WEDNESDAY = Calendar.WEDNESDAY;

    /** The constant for the day - Thursday.*/
    public static final int THURSDAY = Calendar.THURSDAY;

    /** The constant for the day - Friday.*/
    public static final int FRIDAY = Calendar.FRIDAY;

    /** The constant for the day - Saturday.*/
    public static final int SATURDAY = Calendar.SATURDAY;

    /**
     * Returns the data as a Java Util Date
     *
     * @return the data as a Java Util Date
     */
    java.util.Date getUtilDate();
}

