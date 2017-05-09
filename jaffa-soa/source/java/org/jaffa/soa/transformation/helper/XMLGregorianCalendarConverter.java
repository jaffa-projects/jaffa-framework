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

/*
 * XMLGregorianCalendarConverter.java
 *
 */
package org.jaffa.soa.transformation.helper;

import org.dozer.CustomConverter;
import org.dozer.MappingException;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * This converter is used to convert the convert
 * source DateTime/DateOnly object to XMLGregorianCalendar or source XMLGregorianCalendar to DateTime/DateOnly object.
 * 
 * Example usage in dozer mapping:
 *  <field custom-converter="org.jaffa.soa.transformation.helper.XMLGregorianCalendarConverter">
 *     <a>documentedOn</a>
 *     <b>documentedDateTime</b>
 *  </field>
 *  
 * @author SaravananN
 */
public class XMLGregorianCalendarConverter implements CustomConverter {

    /**
     * @param destination. The destination object.
     * @param source. The source object that needs be converted from/to XMLGregorianCalendar.
     * @param destinationClass. The destination class.
     * @param sourceClass. The source class.
     * @return XMLGregorianCalendar or DateTime/DateOnly
     */
    @Override
    public Object convert(Object destination, Object source, Class destinationClass, Class sourceClass) {
        if (source == null) {
            return null;
        }

        if (source instanceof DateTime) {
            
            DateTime dateTime = (DateTime) source;

            return XMLDateConverter.getCalendarFromDate(dateTime.getUtilDate());
            
        } else if (source instanceof DateOnly) {
            
            DateOnly dateOnly = (DateOnly) source;

            return XMLDateConverter.getCalendarFromDate(dateOnly.getUtilDate());
            
        } else if (source instanceof XMLGregorianCalendar && destinationClass.isAssignableFrom(DateTime.class)){
                return new DateTime(((XMLGregorianCalendar)source).toGregorianCalendar().getTime());        } else if(source instanceof XMLGregorianCalendar && destinationClass.isAssignableFrom(DateOnly.class)){
                return new DateOnly(((XMLGregorianCalendar)source).toGregorianCalendar().getTime());
        } else {
            throw new MappingException("Converter XMLGregorianCalendarConverter used incorrectly. Arguments passed in were:" + destination + " and " + source);
        }



    }
}