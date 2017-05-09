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
 * MappingHelper.java
 *
 */
package org.jaffa.soa.transformation.helper;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.dozer.DozerBeanMapper;
import java.util.Calendar;
import javax.xml.datatype.DatatypeConstants;
 

/**This is the mapping helper class.
 * <p>
 * The feature of this class is to map the properties
 * of source object to target object. The mapping
 * information is loaded into DozerBeanMapper.
 *
 * @author SaravananN
 *
 */
public class MappingHelper {

    private static DatatypeFactory df = null;

    /**
     *  Creating the DatatypeFactory on class load to use
     *  it for java.util.Date to  XMLGregorianCalendar
     *  conversion.
     */
    static {
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException("Error while trying to obtain a new instance of DatatypeFactory", e);
        }
    }

    /**
     * @param source. The source object that needs to map to destination.
     * @param destination. The destination object that needs to map from source.
     * @return destination object
     */
    public static Object sourceToDestinationMapping(Object source, Class destination) {

        DozerBeanMapper mapper = DozerBeanMapperSingletonWrapper.instance();
        Object destinationObj = mapper.map(source, destination);

        return destinationObj;
    }

    /**
     * @param <T>.
     * @param <U>.
     * @param source. The source list of objects needs to map to destination object.
     * @param destType. The destination object type.
     * @return list of mapped destination object.
     */
    public static <T, U> List<U> mapList(final List<T> source, final Class<U> destType) {

        DozerBeanMapper mapper = DozerBeanMapperSingletonWrapper.instance();

        final List<U> dest = new ArrayList<U>();

        for (T element : source) {
            dest.add(mapper.map(element, destType));
        }

        return dest;
    }

    /**
     * @param date. The java.util.Data which needs to convert as XMLGregorianCalendar
     * @return XMLGregorianCalendar.
     */
    public static XMLGregorianCalendar asXMLGregorianCalendar(java.util.Date date) {
        if (date == null) {
            return null;
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(date.getTime());
            return df.newXMLGregorianCalendar(gc);
        }
    }

    /**
     * @param date. The java.util.Data which needs to convert as
     * XMLGregorianCalendar
     * @return XMLGregorianCalendar.
     *
     * This method will convert the java.util.Data to XMLGregorianCalendar
     * without time zone
     */
    public static XMLGregorianCalendar asXMLGregorianCalendarDateOnly(java.util.Date date) {
        if (date == null) {
            return null;
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            return df.newXMLGregorianCalendarDate(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH) + 1, gc.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
        }
    }
}
