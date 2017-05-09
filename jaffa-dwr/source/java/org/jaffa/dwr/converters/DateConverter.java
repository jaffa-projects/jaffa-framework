/*
 * ====================================================================
 * JAFFA - Java Application Framework For Aerospace
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
 *      Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *      this Software without prior written permission. For written permission,
 *      please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *      appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
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
package org.jaffa.dwr.converters;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;
import org.directwebremoting.extend.InboundContext;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.MarshallException;
import org.directwebremoting.extend.OutboundContext;
import org.directwebremoting.extend.OutboundVariable;
import org.jaffa.datatypes.DateTime;

/**
 * An implementation of Converter for Dates.
 * This is an extension to the JaffaDateConverter, which takes timezone differences into consideration
 * when (de)serializing date values.
 * This converter should be used in place of DWR's default date converter.
 */
public class DateConverter extends JaffaDateConverter {

    private static final Logger log = Logger.getLogger(DateConverter.class);

    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertInbound(java.lang.Class, org.directwebremoting.InboundVariable, org.directwebremoting.InboundContext)
     */
    @Override
    public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException {
        // Error out if an unsupported class is passed
        if (paramType != Date.class && paramType != java.sql.Date.class && paramType != Time.class && paramType != Timestamp.class && paramType != Calendar.class) {
            log.warn("Unsupported input. Class=" + paramType);
            throw new MarshallException(paramType);
        }

        // invoke the base routine, which will convert the input to a DateTime after taking timezone differences into consideration
        DateTime dt = (DateTime) super.convertInbound(DateTime.class, iv, inctx);

        // now convert DateTime to the desired type
        Object output = null;
        if (dt != null) {
            if (paramType == Date.class)
                output = dt.getUtilDate();
            else if (paramType == java.sql.Date.class)
                output = dt.sqlDate();
            else if (paramType == Time.class)
                output = dt.sqlTime();
            else if (paramType == Timestamp.class)
                output = dt.timestamp();
            else if (paramType == Calendar.class)
                output = dt.calendar();
        }

        if (log.isDebugEnabled())
            log.debug("Inbound '" + iv.getValue() + "' converted to '" + output + '\'');
        return output;
    }

    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertOutbound(java.lang.Object, org.directwebremoting.OutboundContext)
     */
    @Override
    public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException {
        // Error out if an unsupported class is passed
        if (!(data instanceof Date) && !(data instanceof Calendar)) {
            log.warn("Unsupported input. Class=" + data.getClass() + ", data=" + data);
            throw new MarshallException(data.getClass());
        }

        // convert the input to a DateTime
        data = data instanceof Date ? new DateTime((Date) data) : new DateTime((Calendar) data);

        // invoke the base routine, which will serialize the DateTime after taking timezone differences into consideration
        return super.convertOutbound(data, outctx);
    }
}
